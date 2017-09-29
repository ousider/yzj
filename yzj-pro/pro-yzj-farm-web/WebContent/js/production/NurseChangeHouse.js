var prUrl='/production/';
var saveUrl=prUrl+'nurseChangeHouse.do';
var editIndex = undefined;
var eventName = 'NURSE_CHANGE_HOUSE';
//默认值为2
var oldPigType = 2;
var changeHouseData = [];
var zcfxycbbFlag = 'off';
var dnflag = 'off';
var backfatScoreArr = {};
$.ajax({  
    url : basicUrl+'/param/getSettingValueByCode.do?settingCode=DNZZSDYDZS', 
    async:false,
    type : "POST",  
    success : function(data) { 
    	var obj = eval('(' + data + ')');
    	dnflag = obj.rows;
    }
});
$(document).ready(function(){
	var columns = [
	              	j_gridText({field:'pigInfo',title:'耳缺号-品种-状态(胎次)-日龄-猪舍-猪栏',width:550}),
	              	j_gridText({field:'pigQty',title:'带仔数',width:80}),
	              	j_gridText({field:'enterDate',title:'转舍日期',width:150,
	              		editor:{
	              			type:'datebox',
	              			options:{
	              				editable:false,
	              				onChange:enterDateChangeFun
	              			}
	              		}
	              	}),
	              	j_gridNumber({field:'weanNum',title:'断奶仔猪数',width:90,min:0,onChange:weanNumChangeFun}),
	              	j_gridNumber({field:'weanWeight',
	              		precision:2,
	              		increment:0.1,
	              		min:0,max:150,
	              		formatter:function(value,row){
	              			value = value == null || value == undefined || value == '' ? 0.00 : value;
	              			row.weanWeight = value;
		              		return value+'KG';
		              	},
	              		title:'断奶窝重',width:80}),
		            j_gridNumber({field:'dieNum',title:'死亡仔猪数',width:90,min:0,onChange:dieNumChangeFun}),
	              	j_gridNumber({field:'fosterQty',title:'寄养数量',width:80,min:0,onChange:fosterQtyChang}),
	              	j_gridText(boardSowGridComboGrid({width:100})),
	                //猪舍
	              	j_gridText(houseGridComboGrid({
	              		field:'inHouseId',
	              		title:'奶妈转入猪舍',
	              		width:100,
	              		formatter:function(value,row){
	              			return row.inHouseIdName
	              			},
	              		url:'/basicInfo/searchHouseToList.do?lineId=0&houseType=6',
	              		onChange:inHouseChangeFun
	              		})),
	              	//猪栏
	              	j_gridText(pigpenGridComboGrid({
	              		field:'inPigpenId',
	              		title:'奶妈转入猪栏',
	              		width:100,
	              		formatter:function(value,row){
	              			return row.inPigpenIdName
	              			},
		              	onChange:inPigpenChangeFun
	              		})),
			        j_gridText({field:'inPigBrand',title:'原栏位母猪',width:90}),

			    ];
  	columns.push(
         	//猪舍
          	j_gridText(houseGridComboGrid({
          		field:'inPigHouseId',
          		title:'原转入猪舍',
          		width:100,
          		formatter:function(value,row){
          			return row.inPigHouseIdName
          			},
          		url:'/basicInfo/searchHouseToList.do?lineId=0&houseType=2,3,4,5',
          		onChange:inPigHouseChangeFun
          		})),
          	//猪栏
          	j_gridText(pigpenGridComboGrid({
          		field:'inPigPigpenId',
          		title:'原转入猪栏',
          		width:100,
          		formatter:function(value,row){
          			return row.inPigPigpenIdName
          			}
          		})),
  	// 技术员
  	j_gridText(workerGridComboGrid({width:100})),
  	j_gridText({field:'notes',title:'备注',width:100,editor:'textbox'}));
  	
	$('#listTable').datagrid(
			j_eventGrid({
			fitColumns:false,
			dbClickEdit:true,
			frozenColumns:[[{field:'ck',checkbox:true},
			              	j_gridText({field:'rowId',title:'ID',hidden:true}),
			              	j_gridText({field:'pigId',title:'猪只ID',hidden:true}),
			              	j_gridText({field:'houseId',title:'猪舍ID',hidden:true}),
			              	j_gridText({field:'deletedFlag',title:'deletedFlag',hidden:true}),
			              	j_gridText({field:'minValidDate',title:'minValidDate',hidden:true}),
			              	j_gridText({field:'maxValidDate',title:'maxValidDate',hidden:true}),
			              	//耳牌号
			              	j_gridText(earBrandTextBox({width:150}))]],
			columns:[columns],
		    onEndEdit:function(index,row){
	            var inHouse = $('#listTable').datagrid('getEditor', {
	                index: index,
	                field: 'inHouseId'
	            });
	            if(inHouse != null){
	            	returnTarName({
	            		tarType:'grid',
	            		tarName:'houseName',
	            		tarFiled:'inHouseId',
	            		target:inHouse.target
	            	},row)
	            }
//	            if(inHouse != null){
//	            	row.inHouseIdName = $(inHouse.target).combogrid('getText');
//	            }
	            var inPigpen = $('#listTable').datagrid('getEditor', {
	                index: index,
	                field: 'inPigpenId'
	            });
	            if(inPigpen != null){
	            	 row.inPigpenIdName = $(inPigpen.target).combogrid('getText');
	            }
	            var inPigHouse = $('#listTable').datagrid('getEditor',{
	            	index: index,
	            	field: 'inPigHouseId'
	            });
	            if(inPigHouse != null){
	            	row.inPigHouseIdName = $(inPigHouse.target).combogrid('getText');
	            }
	            var inPigPigpen = $('#listTable').datagrid('getEditor',{
	            	index: index,
	            	field: 'inPigPigpenId'
	            });
	            if(inPigPigpen != null){
	            	 row.inPigPigpenIdName = $(inPigPigpen.target).combogrid('getText');
	            }
		    	var boardSow = $('#listTable').datagrid('getEditor', {
	                index: index,
	                field: 'boardSowId'
	            });
		    	if(boardSow != null){
		    		row.boardSowEarBrand = $(boardSow.target).combobox('getText');
		    	}
	        },
			onBeginEdit:function(index,row){

				var inPigpen = $('#listTable').datagrid('getEditor', {
			        index: index,
			        field: 'inPigpenId'
			    });
				if(inPigpen != null){
					var inPigpenGrid = $(inPigpen.target).combogrid('grid');
					var inHouseId = row.inHouseId == null || row.inHouseId == undefined || row.inHouseId == ''? 0 : row.inHouseId;
					inPigpenGrid.datagrid('reload',basicUrl+'/basicInfo/searchPigpenToList.do?mainId='+inHouseId);
				}
				var inPigPigpen = $('#listTable').datagrid('getEditor', {
			        index: index,
			        field: 'inPigPigpenId'
			    });
				var inPigHouse = $('#listTable').datagrid('getEditor',{
					index: index,
					field: 'inPigHouseId'
				});
				var inPig = row.inPigId;
				if(inPigHouse != null){
					if(!inPig){
						$(inPigHouse.target).combobox('disable');
					}
				}
				if(inPigPigpen != null){
					if(inPig){
						var inPigPigpenGrid = $(inPigPigpen.target).combogrid('grid');
						var inPigHouseId = row.inPigHouseId == null || row.inPigHouseId == undefined || row.inPigHouseId == ''? 0 : row.inPigHouseId;
						inPigPigpenGrid.datagrid('reload',basicUrl+'/basicInfo/searchPigpenToList.do?mainId='+inPigHouseId);
					}else{
						$(inPigPigpen.target).combobox('disable');
					}
				}

		    	var boardSow = $('#listTable').datagrid('getEditor', {
			        index: index,
			        field: 'boardSowId'
			    });
				if(boardSow != null){
					var rows = $('#listTable').datagrid('getRows');
					var hasSelectRows = $('#listTable').datagrid('getData').rows;
		        	var pigIdsArray = [];
		        	$.each(hasSelectRows,function(i,data){
		        		if(data.pigId != null && data.pigId != '' && i != editIndex){
		        			pigIdsArray.push(data.pigId);
		        		}
		        	});
		        	var pigIds = '';
		        	$.each(pigIdsArray,function(i,data){
		        		if(i == pigIdsArray.length - 1){
		        			pigIds += data;
		        		}else{
		        			pigIds += data + ',';
		        		}
		        	});
		    		$.ajax({  
				        url : basicUrl+'/production/searchValidPigToPage.do', 
				        data:{
				        	queryType:1,
			            	pigType:2,
			            	specifyPigs:0,
			            	choice:1,
			            	eventName:'BOAR_FOSTER',
			            	date1:rows[index].enterDate,
			            	pigIds:pigIds,
			            	houseIds:rows[index].houseId
				        },
				        type : "POST",  
				        dataType : "json",  
				        success : function(response) { 
			        		$(boardSow.target).combobox('loadData',response.rows);
				        }  
				    }); 
				}
			}
		})
	);
	
	$('#preSaveRecordTable').datagrid(
			j_grid_view({
				fitColumns:false,
				haveCheckBox:false,
				columnFields:'pigId,earBrand,pigInfo,enterDate,weanNum,weanWeight,dieNum,fosterQty,boardSowEarBrand,inHouseIdName,inPigpenIdName,inPigBrand,inPigHouseIdName,inPigPigpenIdName,workerName,notes',
				columnTitles:'猪只ID,耳牌号,耳缺号-品种-状态(胎次)-日龄-猪舍-猪栏,事件日期,断奶数,断奶窝重,死亡数,寄养数,寄养母猪,奶妈转入猪舍,奶妈转入猪栏,原栏位母猪,原转入猪舍,原转入猪栏,技术员,备注',
				hiddenFields:'pigId,notes,workerName',
				columnWidths:'100,100,350,100,100,100,100,100,100,90,100,80,100,100,100,100',
				fit:false,
				width:'auto',
				height:'100%',
				pagination:false
			},'preSaveRecordTable')
	);
	
	// 是否测试背膘，后台验证用hidden项
	$('#zcfxycbbFlag').val(zcfxycbbFlag);
});
/**
 *  猪舍改变方法
 * @param newValue
 * @param oldValue
 */
function inHouseChangeFun(newValue,oldValue){
	if(editIndex == undefined){
		return;
	}
	var rows = $('#listTable').datagrid('getRows');
	rows[editIndex].inPigpenId = '';
	rows[editIndex].inPigpenIdName = '';
	rows[editIndex].inPigId = '';
	rows[editIndex].inPigBrand = '';
	rows[editIndex].inPigHouseId = '';
	rows[editIndex].inPigHouseIdName = '';
	rows[editIndex].inPigPigpenId = '';
	rows[editIndex].inPigPigpenIdName = '';


	//2016-09-22 修改刷新行值方法 begin
	changeTableDisplayValue('listTable',editIndex,[{
		field:'inPigpenId',
		value:''
	}]);
	focusEditCell('listTable',editIndex,'inHouseId');
	// end
	// 删除 begin
	//	setTimeout(function () {
	//		
	//		$('#listTable').datagrid('updateRow',{
	//			index:editIndex,
	//			row:rows[editIndex]
	//		});
	//	 },100);
	// 删除 end
}
/**
 *  猪舍改变方法
 * @param newValue
 * @param oldValue
 */
function inPigHouseChangeFun(newValue,oldValue){
	if(editIndex == undefined){
		return;
	}
	var rows = $('#listTable').datagrid('getRows');
	rows[editIndex].inPigPigpenId = '';
	rows[editIndex].inPigPigpenIdName = '';
	//2016-09-22 修改刷新行值方法 begin
	changeTableDisplayValue('listTable',editIndex,[{
		field:'inPigPigpenId',
		value:''
	}]);
	focusEditCell('listTable',editIndex,'inPigHouseId');
	// end
	// 删除 begin
	//	setTimeout(function () {
	//		
	//		$('#listTable').datagrid('updateRow',{
	//			index:editIndex,
	//			row:rows[editIndex]
	//		});
	//	 },100);
	// 删除 end
}

//奶妈转入猪栏改变方法
function inPigpenChangeFun(newValue,oldValue){
	if(editIndex==undefined){
		return;
	}
	if(newValue == ''){
		return;
	}else{
		$.ajax({  
	        url : basicUrl+'/production/searchValidPigByPigpen.do?inPigpenId='+newValue, 
	        async : false, 
	        type : "POST",  
	        dataType : "json",  
	        success : function(response) {
	        	if(response.success){
	        		newData = response.rows;
	        		var row = $('#listTable').datagrid('getRows')[editIndex];
	        		if(newData.length == 0){
	        			row.inPigBrand = null;
		        		row.inPigId = null;
	        		}else{
	        			row.inPigBrand = newData[0]['earBrand'];
		        		row.inPigId = newData[0]['pigId'];
	        		}
	        	}else{
	        		var row = $('#listTable').datagrid('getRows')[editIndex];
        			row.inPigBrand = null;
	        		row.inPigId = null;
	        		 $.messager.alert({
	                     title: '错误',
	                     msg: response.errorMsg
	                 });
	        	}
	        }  
	    });	
	}

}
//背膘评分
function changeBackfatScore(newValue,oldValue){
	if(editIndex==undefined){
		return;
	}
	var row = $('#listTable').datagrid('getRows')[editIndex];
	if(newValue == ''){
		row.score = '';
	}else if(newValue<12){
		row.score = backfatScoreArr.LVL1;
	}else if(newValue>=12 && newValue<=13){
		row.score = backfatScoreArr.LVL2;
	}else if(newValue>=14 && newValue<=15){
		row.score = backfatScoreArr.LVL3;
	}else if(newValue>=16 && newValue<=17){
		row.score = backfatScoreArr.LVL4;
	}else if(newValue>=18 && newValue<=19){
		row.score = backfatScoreArr.LVL5;
	}else if(newValue>=20){
		row.score = backfatScoreArr.LVL6;
	}
	//2016-09-22 修改刷新行值方法 begin
	changeTableDisplayValue('listTable',editIndex,[{
		field:'score',
		value:row.score
	}]);
	// end
	// 删除 begin
//	
//	setTimeout(function () {
//		$('#listTable').datagrid('endEdit',editIndex);
//		$('#listTable').datagrid('updateRow',{
//			index:editIndex,
//			row:row
//		});
//	 },100);
	// 删除 end
}
/**
 * 断奶仔猪数改变方法
 * @param newValue
 * @param oldValue
 */
function weanNumChangeFun(newValue,oldValue){
	if(editIndex != undefined){
		var rows = $('#listTable').datagrid('getRows');
			var pigQty = rows[editIndex].pigQty;
			var fosterQty = strToInt(rows[editIndex].fosterQty == undefined ? 0:rows[editIndex].fosterQty);
			if(strToInt(newValue) > pigQty){
				$.messager.alert('警告', '断奶仔猪数大于带仔数，请重新输入！');
				$(this).numberspinner('setValue', oldValue);
				return;
			}else{
				if(strToInt(newValue)+strToInt(fosterQty)>pigQty){
					$.messager.alert('警告', '断奶仔猪数+寄养仔猪数大于带仔数，请重新输入！');
					$(this).numberspinner('setValue', oldValue);
					return;
				}else{
					rows[editIndex].dieNum = pigQty - strToInt(newValue) - fosterQty;
					changeTableDisplayValue('listTable',editIndex,[{
						field:'dieNum',
						value:rows[editIndex].dieNum
					}]);
				}

//				setTimeout(function () {
//					$('#listTable').datagrid('endEdit',editIndex);
//					$('#listTable').datagrid('updateRow',{
//						index:editIndex,
//						row:rows[editIndex]
//					});
//				 },100);
			}
		var weanNumSum = 0,dieNumSum = 0,fosterQtySum = 0;
		if(newValue != ''){
			weanNumSum = strToInt(newValue);
			dieNumSum = rows[editIndex].pigQty - strToInt(newValue) - fosterQty;
			fosterQtySum = fosterQty;
		}else{
			dieNumSum = rows[editIndex].pigQty - fosterQty;
			fosterQtySum = fosterQty;
		}
		$.each(rows,function(i,item){
			if( i != editIndex && item.weanNum != undefined && item.weanNum != ''){
				weanNumSum += strToInt(item.weanNum);
			}
			
			if( i != editIndex && item.dieNum != undefined && item.dieNum != ''){
				dieNumSum += strToInt(item.dieNum);
			}
			if( i != editIndex && item.fosterQty != undefined && item.fosterQty != ''){
				fosterQtySum += strToInt(item.fosterQty);
			}
		})
		$('#weanNumSum').html(weanNumSum);
		$('#dieNumSum').html(dieNumSum);
		$('#fosterQtySum').html(fosterQtySum);
	}
}
/**
 * 死亡仔猪数改变方法
 * @param newValue
 * @param oldValue
 */
function dieNumChangeFun(newValue,oldValue){
	if(editIndex != undefined){
		var rows = $('#listTable').datagrid('getRows');
			var pigQty = rows[editIndex].pigQty;
			var weanNum = strToInt(rows[editIndex].weanNum == undefined ? 0:rows[editIndex].weanNum);
			if(newValue > pigQty){
				$.messager.alert('警告', '死亡仔猪数大于带仔数，请重新输入！');
				$(this).numberspinner('setValue', oldValue);
				return;
			}else{
				if(strToInt(newValue)+weanNum>pigQty){
					$.messager.alert('警告', '死亡仔猪数+断奶仔猪数大于带仔数，请重新输入！');
					$(this).numberspinner('setValue', oldValue);
					return;
				}else{
					rows[editIndex].fosterQty = pigQty - strToInt(newValue) - weanNum;
					changeTableDisplayValue('listTable',editIndex,[{
						field:'fosterQty',
						value:rows[editIndex].fosterQty
					}]);
				}
//				setTimeout(function () {
//					$('#listTable').datagrid('endEdit',editIndex);
//					$('#listTable').datagrid('updateRow',{
//						index:editIndex,
//						row:rows[editIndex]
//					});
//				 },100);
			}
		var dieNumSum = 0,weanNumSum = 0,fosterQtySum = 0;
		if(newValue != ''){
			dieNumSum = strToInt(newValue);
			weanNumSum = weanNum;
			fosterQtySum = rows[editIndex].pigQty-strToInt(newValue)-weanNum;
		}else{
			weanNumSum = weanNum;
			fosterQtySum = rows[editIndex].pigQty-weanNum;
		}
		$.each(rows,function(i,item){
			if( i != editIndex && item.dieNum != undefined && item.dieNum != ''){
				dieNumSum += strToInt(item.dieNum);
			}
			
			if( i != editIndex && item.weanNum != undefined && item.weanNum != ''){
				weanNumSum += strToInt(item.weanNum);
			}
			
			if( i != editIndex && item.fosterQty != undefined && item.fosterQty != ''){
				fosterQtySum += strToInt(item.fosterQty);
			}
		})
		$('#dieNumSum').html(dieNumSum);
		$('#weanNumSum').html(weanNumSum);
		$('#fosterQtySum').html(fosterQtySum);

	}
}
function fosterQtyChang(newValue,oldValue){
	if(editIndex != undefined){
		var rows = $('#listTable').datagrid('getRows');
			var pigQty = rows[editIndex].pigQty;
			var weanNum = strToInt(rows[editIndex].weanNum == undefined ? 0:rows[editIndex].weanNum);
			if(newValue > pigQty){
				$.messager.alert('警告', '死亡仔猪数大于带仔数，请重新输入！');
				$(this).numberspinner('setValue', oldValue);
				return;
			}else{
				if(strToInt(newValue)+weanNum>pigQty){
					$.messager.alert('警告', '断奶仔猪数+寄养仔猪数大于带仔数，请重新输入！');
					$(this).numberspinner('setValue', oldValue);
					return;
				}else{
					rows[editIndex].dieNum = pigQty - strToInt(newValue) - weanNum;
					changeTableDisplayValue('listTable',editIndex,[{
						field:'dieNum',
						value:rows[editIndex].dieNum
					}]);
				}
//				setTimeout(function () {
//					$('#listTable').datagrid('endEdit',editIndex);
//					$('#listTable').datagrid('updateRow',{
//						index:editIndex,
//						row:rows[editIndex]
//					});
//				 },100);
			}
		var dieNumSum = 0,weanNumSum = 0,fosterQtySum = 0;
		if(newValue != ''){
			weanNumSum = weanNum;
			fosterQtySum = strToInt(newValue);
			dieNumSum = rows[editIndex].pigQty - strToInt(newValue) - weanNum;
		}else{
			weanNumSum = weanNum;
			dieNumSum = rows[editIndex].pigQty - weanNum;
		}
		$.each(rows,function(i,item){
			if( i != editIndex && item.dieNum != undefined && item.dieNum != ''){
				dieNumSum += strToInt(item.dieNum);
			}
			
			if( i != editIndex && item.fosterQty != undefined && item.fosterQty != ''){
				fosterQtySum += strToInt(item.fosterQty);
			}
			if( i != editIndex && item.weanNum != undefined && item.weanNum != ''){
				weanNumSum += strToInt(item.weanNum);
			}
		})
		$('#dieNumSum').html(dieNumSum);
		$('#weanNumSum').html(weanNumSum);
		$('#fosterQtySum').html(fosterQtySum);
	}
}
function onNurseChange(newValue,oldValue){
	if(editIndex == undefined){
		return;
	}
	var rows = $('#listTable').datagrid('getRows');
	var sowCombobox = $(this);
	$.ajax({  
        url : basicUrl+'/production/searchValidPigToPage.do', 
        data:{
        	queryType:1,
        	query:sowCombobox.combobox('getText'),
        	pigType:2,
        	specifyPigs:0,
        	choice:1,
        	eventName:'BOAR_FOSTER',
        	date1:rows[editIndex].enterDate,
        	pigIds:rows[editIndex].pigId
        },
        type : "POST",  
        dataType : "json",  
        success : function(response) { 
        	sowCombobox.combobox('loadData',response.rows);
        }  
    }); 
}
/**
 * 保存
 * pms{
 * 	model:模块代码
 * }
 */
function onBtnSave(){
	var queryParams;
	var listTable = document.getElementById("listTable");
	//判断是否有明细表
	if(listTable!= null){
		queryParams = collectDetailData();
	}else{
		queryParams = {
			status:1,
			deletedFlag:0,
		};
	}
	//判断是否是事件保存
	if(typeof(eventName) != "undefined"){
		queryParams.eventName = eventName;
	}
	$('#btnSave').attr('disabled',true).addClass('btn-disabled');
	$.messager.progress();	
	$('#editForm').form('submit',{
		url:basicUrl+saveUrl,
		queryParams:queryParams,
		onSubmit: function(){
			var isValid = $('#editForm').form('validate');
			if (!isValid){
				$('#btnSave').attr('disabled',false).removeClass('btn-disabled');
				$.messager.progress('close');	
			}
			return isValid;
		},
		success: function(response){
			response=eval('('+response+')');
			if(response.success){
				if(typeof(eventName) == "undefined"){
					$('#editWin').window('close');
					$.messager.alert('提示','保存成功！');
				}else{
					//重置
					var preRecord = $('#listTable').datagrid('getData');
					$('#editForm').form('reset');
					leftSilpFun('preSaveRecord',true,9002);
					if(preRecord.success == undefined){
						preRecord.success = true;
					}
					$('#preSaveRecordTable').datagrid('loadData',preRecord);
					if(listTable!= null){
						$('#listTable').datagrid('loadData',{success:true,total: 0, rows: [] });
					}
					$('#weanNumSum').html('0');
					$('#dieNumSum').html('0');
					$('#fosterQtySum').html('0');
				}
				var table = document.getElementById("table");
				if(table != null){
					$('#table').datagrid('reload');
				}
			}else{
				 $.messager.alert({
                     title: '错误',
                     msg: response.errorMsg
                 });
			}
			$('#btnSave').attr('disabled',false).removeClass('btn-disabled');
			$.messager.progress('close');
		}
	});
}

function enterDateChangeFun(newValue,oldValue){
	if(oldValue == ''){
		return;
	}
	if(newValue != null && newValue != ''){
		var isDate = checkDate(newValue);
		if(isDate){
			var rows = $('#listTable').datagrid('getRows');
			rows[editIndex].boardSowId = '';
			rows[editIndex].boardSowEarBrand = '';
			changeTableDisplayValue('listTable',editIndex,[{
				field:'boardSowId',
				value:''
			}]);
			setTimeout(function () {
//				$('#listTable').datagrid('endEdit',editIndex);
//				$('#listTable').datagrid('updateRow',{
//					index:editIndex,
//					row:rows[editIndex]
//				});
				focusEditCell('listTable',editIndex,'enterDate');
//				$('#listTable').datagrid('editCell', {index:editIndex,field:'enterDate'});
//				var editor = $('#listTable').datagrid('getEditor', {index:editIndex,field:'enterDate'});
//				var eidtCellInput = $(editor.target[0].parentNode).find('.textbox-text.validatebox-text');
//				eidtCellInput[0].focus();
//				eidtCellInput.keydown(function(e){
//			    	editCellTabDownFun(e,$('#listTable'),editIndex,'enterDate');
//			    });
			 },100);
		}
	}
}
/**
 * 重置方法
 */
function onBtnReset(){
	$.messager.confirm('提示', '重置将清空数据，确定要重置吗？', function(r){
		if (r){
			$('#editForm').form('reset');
			var listTable = document.getElementById("listTable");
			if(listTable!= null){
				$('#listTable').datagrid('loadData',{success:true,total: 0, rows: [] });
				$('#weanNumSum').html('0');
				$('#dieNumSum').html('0');
				$('#fosterQtySum').html('0');
			}
		}
	});
}