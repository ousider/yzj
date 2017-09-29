var prUrl='/production/';
var saveUrl=prUrl+'breed.do';
var editIndex = undefined;
var eventName = 'BREED';
//默认值为2（母猪）
var oldPigType = 2;
var breedTypeData = [];
var breedTypeDefaultValue = null;
var breedTypeDefaultName = '';
var breedBoarTiems = 1;
$.ajax({  
    url : basicUrl+'/param/searchByTypeCode.do?typeCode=BREED_TYPE',
    async : false, 
    type : "POST",  
    dataType : "json",  
    success : function(response) { 
    	breedTypeData = response;
    	$.each(breedTypeData,function(index,row){
    		if(row.isDefault == 'Y'){
    			breedTypeDefaultValue = row.codeValue;
    			breedTypeDefaultName = row.codeName;
    		}
    	});
    }  
});
//人工授精使用库存精液（不自动采精）
var pzqcj = "OFF";
$.ajax({  
    url : basicUrl+'/param/getSettingValueByCode.do?settingCode=PZQCJ', 
    async:false,
    type : "POST",  
    success : function(data) { 
    	var obj = eval('(' + data + ')');
    	pzqcj = obj.rows;
    }
});

var detailDefaultValues = {
		status:'1',
		deletedFlag:'0',
		//设置默认值
		breedType:breedTypeDefaultValue,
		breedTypeName:breedTypeDefaultName
};
//判断输精次数
var columns = [
                j_gridText({field:'pigInfo',title:'耳缺号    -  品种   -  胎次   -  状态(天)  -  日龄  - 猪舍  - 猪栏',width:450}),
             	j_gridText({field:'breedType',title:'配种方式',width:150,copyable:false,
              		formatter:function(value,row){
              			return row.breedTypeName;
              		},
              		editor:
              		xnGridCdComboBox({
              			field:'breedType',
              			data:breedTypeData,
              			onChange:breedTypeChangeFun
              		})
              	}),
              	//猪舍
              	j_gridText(houseGridComboGrid({
              		field:'inHouseId',
              		title:'转入猪舍',
              		width:120,
              		formatter:function(value,row){
              			return row.inHouseIdName
              			},
              		url:'/basicInfo/searchHouseToList.do?lineId=0&houseType=1,2,3,4,5',
              		onChange:inHouseChangeFun
          		})),
              	//猪栏
              	j_gridText(pigpenGridComboGrid({
              		field:'inPigpenId',
              		title:'转入猪栏',
              		width:120,
              		formatter:function(value,row){
              			return row.inPigpenIdName
              			}
          		})),
              	j_gridText({field:'enterDate',title:'配种日期',width:150,
              		editor:{
              			type:'datebox',
              			options:{
              				editable:false,
              				onChange:enterDateChangeFun
              				}
              			}
              	}),
              	//配种公猪
              	j_gridText(breedBoarGridComboBox({width:150}))];
$.ajax({  
    url : basicUrl+'/param/getSettingValueByCode.do?settingCode=SJCS', 
    async:false,
    type : "POST",  
    success : function(data) { 
    	var obj = eval('(' + data + ')');
    	breedBoarTiems = eval('(' + data + ')');
		if(obj.rows == 2){
			columns.push(j_gridText(breedBoarGridComboBox({
          		field:'breedBoarSecond',
          		title:'二配公猪',
          		width:150,
          		formatter:function(value,row){
  		          return row.breedBoarSecondEarBrand;  
    			} 
          	})))
		}
		if(obj.rows == 3){
			columns.push(j_gridText(breedBoarGridComboBox({
          		field:'breedBoarSecond',
          		title:'二配公猪',
          		width:150,
          		formatter:function(value,row){
  		          return row.breedBoarSecondEarBrand;  
    			} 
          	})));
			columns.push(j_gridText(breedBoarGridComboBox({
          		field:'breedBoarThird',
          		title:'三配公猪',
          		width:150,
          		formatter:function(value,row){
  		          return row.breedBoarThirdEarBrand;  
    			} 
          	})));
		}
		columns.push(j_gridText({field:'semenQuality',title:'精液质量',width:150,
      		formatter:function(value,row){
      			return row.semenQualityName;
      		},
      		editor:
      		xnGridCdComboBox({
      			field:'semenQuality',
      			typeCode:'SEMEN_QUALITY'
      		})
      	}));
		//技术员
		columns.push(j_gridText(workerGridComboGrid({width:80})));
		columns.push(j_gridText({field:'notes',title:'备注',width:150,editor:'textbox'}));
    }
});
//配种公猪ID
var sememIdsArray = [];
$(document).ready(function(){
	$('#listTable').datagrid(
			j_eventGrid({
			dbClickEdit:true,
			fitColumns:false,
			//固定列
			frozenColumns:[[
			                {field:'ck',checkbox:true},
			              	j_gridText({field:'rowId',title:'ID',hidden:true}),
			              	j_gridText({field:'pigId',title:'猪只ID',hidden:true}),
			              	j_gridText({field:'minValidDate',title:'minValidDate',hidden:true}),
			              	j_gridText({field:'maxValidDate',title:'maxValidDate',hidden:true}),
			              	j_gridText({field:'deletedFlag',title:'deletedFlag',hidden:true}),
			              	//耳牌号
			              	j_gridText(earBrandTextBox({width:150}))
			              	]],
			columns:[columns],
		    onEndEdit:function(index,row){
		    	 var breedType = $('#listTable').datagrid('getEditor', {
	                index: index,
	                field: 'breedType'
	            });
		    	if(breedType != null){
		    		row.breedTypeName = $(breedType.target).combobox('getText');
		    	}
		    	var semenQuality = $('#listTable').datagrid('getEditor', {
	                index: index,
	                field: 'semenQuality'
	            });
		    	if(semenQuality != null){
		    		row.semenQualityName = $(semenQuality.target).combobox('getText');
		    	}
		    	var breedBoarFirst = $('#listTable').datagrid('getEditor', {
	                index: index,
	                field: 'breedBoarFirst'
	            });
		    	if(breedBoarFirst != null){
		    		row.breedBoarFirstEarBrand = $(breedBoarFirst.target).combogrid('getText');
		    	}
		    	var breedBoarSecond = $('#listTable').datagrid('getEditor', {
	                index: index,
	                field: 'breedBoarSecond'
	            });
		    	if(breedBoarSecond != null){
		    		row.breedBoarSecondEarBrand = $(breedBoarSecond.target).combogrid('getText');
		    	}
		    	var breedBoarThird = $('#listTable').datagrid('getEditor', {
	                index: index,
	                field: 'breedBoarThird'
	            });
		    	if(breedBoarThird != null){
		    		row.breedBoarThirdEarBrand = $(breedBoarThird.target).combogrid('getText');
		    	}
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
	            var inPigpen = $('#listTable').datagrid('getEditor', {
	                index: index,
	                field: 'inPigpenId'
	            });
	            if(inPigpen != null){
	            	returnTarName({
	            		tarType:'grid',
	            		tarName:'pigpenName',
	            		tarFiled:'inPigpenId',
	            		target:inPigpen.target
	            	},row)
	            }
	        },onBeginEdit:function(index,row){
	        	if(index == undefined || row == undefined){
	        		return;
	        	}
				var breedBoarFirst = $('#listTable').datagrid('getEditor', {
			        index: index,
			        field: 'breedBoarFirst'
			    });
				var breedBoarSecond = $('#listTable').datagrid('getEditor', {
			        index: index,
			        field: 'breedBoarSecond'
			    });
				var breedBoarThird = $('#listTable').datagrid('getEditor', {
			        index: index,
			        field: 'breedBoarThird'
			    });
	    		if(row.breedType != null && row.breedType != '' && row.enterDate != null && row.enterDate != ''){
					if(breedBoarFirst != null){
						var sememIds = '';
			        	$.each(sememIdsArray,function(i,data){
			        		if(data != row.breedBoarFirst){
			        			if(i == 0){
			        				sememIds += data;
				        		}else{
				        			sememIds += ',' + data;
				        		}
			        		}
			        	});
			        	if(sememIds.substring(0,1) == ','){
			        		sememIds = sememIds.substring(1,sememIds.length);
			        	}
			        	// 不使用存库精液，允许公猪重复
			        	if(pzqcj== 'OFF'){
			        		sememIds = '';
			        	}
						$.ajax({  
					        url : basicUrl+'/production/searchSemenToList.do', 
					        data:{
					        	queryType:1,
					        	query:'',
					        	breedType:row.breedType,
					        	breedDate:row.enterDate,
					        	sememIds:sememIds
					        },
					        async : false, 
					        type : "POST",  
					        dataType : "json",  
					        success : function(response) { 
					        	$(breedBoarFirst.target).combobox('loadData',response.rows);
					        }  
					    }); 
					}
	    			if(breedBoarSecond != null){
	    				var sememIds = '';
			        	$.each(sememIdsArray,function(i,data){
			        		if(data != row.breedBoarSecond){
			        			if(i == 0){
			        				sememIds += data;
				        		}else{
				        			sememIds += ',' + data;
				        		}
			        		}
			        	});
			        	if(sememIds.substring(0,1) == ','){
			        		sememIds = sememIds.substring(1,sememIds.length);
			        	}
			        	// 不使用存库精液，允许公猪重复
			        	if(pzqcj== 'OFF'){
			        		sememIds = '';
			        	}
	    				$.ajax({  
					        url : basicUrl+'/production/searchSemenToList.do', 
					        data:{
					        	queryType:1,
					        	query:'',
					        	breedType:row.breedType,
					        	breedDate:row.enterDate,
					        	sememIds:sememIds
					        },
					        async : false, 
					        type : "POST",  
					        dataType : "json",  
					        success : function(response) { 
					        	$(breedBoarSecond.target).combobox('loadData',response.rows);
					        }  
					    }); 
	    			}
					if(breedBoarThird != null){
						var sememIds = '';
			        	$.each(sememIdsArray,function(i,data){
			        		if(data != row.breedBoarThird){
			        			if(i == 0){
			        				sememIds += data;
				        		}else{
				        			sememIds += ',' + data;
				        		}
			        		}
			        	});
			        	if(sememIds.substring(0,1) == ','){
			        		sememIds = sememIds.substring(1,sememIds.length);
			        	}
			        	// 不使用存库精液，允许公猪重复
			        	if(pzqcj== 'OFF'){
			        		sememIds = '';
			        	}
						$.ajax({  
					        url : basicUrl+'/production/searchSemenToList.do', 
					        data:{
					        	queryType:1,
					        	query:'',
					        	breedType:row.breedType,
					        	breedDate:row.enterDate,
					        	sememIds:sememIds
					        },
					        async : false, 
					        type : "POST",  
					        dataType : "json",  
					        success : function(response) { 
					        	$(breedBoarThird.target).combobox('loadData',response.rows);
					        }  
					    }); 
					}
	    		}
				var inPigpen = $('#listTable').datagrid('getEditor', {
			        index: index,
			        field: 'inPigpenId'
			    });
				if(inPigpen != null){
					var inPigpenGrid = $(inPigpen.target).combogrid('grid');
					var inHouseId = row.inHouseId == null || row.inHouseId == undefined || row.inHouseId == ''? 0 : row.inHouseId;
					inPigpenGrid.datagrid('reload',basicUrl+'/basicInfo/searchPigpenToList.do?mainId='+inHouseId);
				}
			}
		})
	);
	
	$('#preSaveRecordTable').datagrid(
			j_grid_view({
				fitColumns:false,
				haveCheckBox:false,
				columnFields:'pigId,earBrand,pigInfo,breedTypeName,enterDate,breedBoarFirstEarBrand,breedBoarSecondEarBrand,breedBoarThirdEarBrand,semenQualityName,workerName,notes',
				columnTitles:'猪只ID,耳牌号,耳缺号    -  品种   -  胎次   -  状态(天)  -  日龄  - 猪舍  - 猪栏,配种方式,配种日期,配种公猪,二配公猪,三配公猪,精液质量,技术员,备注',
				hiddenFields:'pigId,notes,workerName'+(breedBoarTiems<2?'breedBoarSecondEarBrand':'')+(breedBoarTiems<3?'breedBoarThirdEarBrand':''),
				columnWidths:'100,100,350,100,90,100,100,100,90,100,100',
				fit:false,
				width:'auto',
				height:'100%',
				pagination:false
			},'preSaveRecordTable')
	);
});
/**
 * 配种类型改变方法
 * @param newValue
 * @param oldValue
 */
function breedTypeChangeFun(newValue,oldValue){
	if(oldValue == ''){
		return;
	}
	var rows = $('#listTable').datagrid('getRows');
	if(rows[editIndex].breedBoarFirst != null && rows[editIndex].breedBoarFirst != '' && rows[editIndex].breedBoarFirst != undefined){
		$.each(sememIdsArray,function(i,item){
			if(item == rows[editIndex].breedBoarFirst){
				sememIdsArray.splice(i,1);
				return false;
			}
		})
	}
	if(rows[editIndex].breedBoarSecond != null && rows[editIndex].breedBoarSecond != '' && rows[editIndex].breedBoarSecond != undefined){
		$.each(sememIdsArray,function(i,item){
			if(item == rows[editIndex].breedBoarSecond){
				sememIdsArray.splice(i,1);
				return false;
			}
		})
	}
	if(rows[editIndex].breedBoarThird != null && rows[editIndex].breedBoarThird != '' && rows[editIndex].breedBoarThird != undefined){
		$.each(sememIdsArray,function(i,item){
			if(item == rows[editIndex].breedBoarThird){
				sememIdsArray.splice(i,1);
				return false;
			}
		})
	}
	rows[editIndex].breedBoarFirst = '';
	rows[editIndex].breedBoarFirstEarBrand = '';
	rows[editIndex].breedBoarSecond = '';
	rows[editIndex].breedBoarSecondEarBrand = '';
	rows[editIndex].breedBoarThird = '';
	rows[editIndex].breedBoarThirdEarBrand = '';
	changeTableDisplayValue('listTable',editIndex,[{
		field:'breedBoarFirst',
		value:''
	},{
		field:'breedBoarSecond',
		value:''
	},{
		field:'breedBoarSecond',
		value:''
	}]);
//	setTimeout(function () {
//		$('#listTable').datagrid('endEdit',editIndex);
//		$('#listTable').datagrid('updateRow',{
//			index:editIndex,
//			row:rows[editIndex]
//		});
//	 },100);
	/*if(newValue != null && newValue != ''){
		var breedBoarFirst = $('#listTable').datagrid('getEditor', {
	        index: editIndex,
	        field: 'breedBoarFirst'
	    });
		var breedBoarSecond = $('#listTable').datagrid('getEditor', {
	        index: editIndex,
	        field: 'breedBoarSecond'
	    });
		var breedBoarThird = $('#listTable').datagrid('getEditor', {
	        index: editIndex,
	        field: 'breedBoarThird'
	    });
		var enterDate = $('#listTable').datagrid('getEditor', {
	        index: editIndex,
	        field: 'enterDate'
	    });
		if(enterDate != null){
			var breedDateValue = $(enterDate.target).datebox('getValue');
			var isDate = checkDate(breedDateValue);
			if(isDate){
				if(breedDateValue != null && breedDateValue != ''){
					var sememIds = '';
		        	$.each(sememIdsArray,function(i,data){
	        			if(i == sememIdsArray.length - 1){
	        				sememIds += data;
		        		}else{
		        			sememIds += data + ',';
		        		}
		        	});
					var data = [];
					$.ajax({  
				        url : basicUrl+'/production/searchSemenToList.do', 
				        data:{
				        	queryType:1,
				        	breedType:newValue,
				        	breedDate:breedDateValue,
				        	sememIds:sememIds
				        },
				        async : false, 
				        type : "POST",  
				        dataType : "json",  
				        success : function(response) { 
				        	data = response.rows;
				        }  
				    }); 
					if( breedBoarFirst != null){
						$(breedBoarFirst.target).combobox('setValue','');
						$(breedBoarFirst.target).combobox('loadData',data);
					}
					if( breedBoarSecond != null){
						$(breedBoarSecond.target).combobox('setValue','');
						$(breedBoarSecond.target).combobox('loadData',data);
					}
					if( breedBoarThird != null){
						$(breedBoarThird.target).combobox('setValue','');
						$(breedBoarThird.target).combobox('loadData',data);
					}
				}
			}
		}
	}*/
}
/**
 * 配种日期改变方法
 * @param newValue
 * @param oldValue
 */
function enterDateChangeFun(newValue,oldValue){
	if(oldValue == ''){
		return;
	}
	if(newValue != null && newValue != ''){
		var isDate = checkDate(newValue);
		if(isDate){
			var rows = $('#listTable').datagrid('getRows');
			if(rows[editIndex].breedBoarFirst != null && rows[editIndex].breedBoarFirst != '' && rows[editIndex].breedBoarFirst != undefined){
				$.each(sememIdsArray,function(i,item){
					if(item == rows[editIndex].breedBoarFirst){
						sememIdsArray.splice(i,1);
						return false;
					}
				})
			}
			if(rows[editIndex].breedBoarSecond != null && rows[editIndex].breedBoarSecond != '' && rows[editIndex].breedBoarSecond != undefined){
				$.each(sememIdsArray,function(i,item){
					if(item == rows[editIndex].breedBoarSecond){
						sememIdsArray.splice(i,1);
						return false;
					}
				})
			}
			if(rows[editIndex].breedBoarThird != null && rows[editIndex].breedBoarThird != '' && rows[editIndex].breedBoarThird != undefined){
				$.each(sememIdsArray,function(i,item){
					if(item == rows[editIndex].breedBoarThird){
						sememIdsArray.splice(i,1);
						return false;
					}
				})
			}
			rows[editIndex].breedBoarFirst = '';
			rows[editIndex].breedBoarFirstEarBrand = '';
			rows[editIndex].breedBoarSecond = '';
			rows[editIndex].breedBoarSecondEarBrand = '';
			rows[editIndex].breedBoarThird = '';
			rows[editIndex].breedBoarThirdEarBrand = '';
			changeTableDisplayValue('listTable',editIndex,[{
				field:'breedBoarFirst',
				value:''
			},{
				field:'breedBoarSecond',
				value:''
			},{
				field:'breedBoarSecond',
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
	/*if(newValue != null && newValue != ''){
		var isDate = checkDate(newValue);
		if(isDate){
			var breedBoarFirst = $('#listTable').datagrid('getEditor', {
		        index: editIndex,
		        field: 'breedBoarFirst'
		    });
			var breedBoarSecond = $('#listTable').datagrid('getEditor', {
		        index: editIndex,
		        field: 'breedBoarSecond'
		    });
			var breedBoarThird = $('#listTable').datagrid('getEditor', {
		        index: editIndex,
		        field: 'breedBoarThird'
		    });
			var breedType = $('#listTable').datagrid('getEditor', {
		        index: editIndex,
		        field: 'breedType'
		    });
			if(breedType != null){
				var breedTypeValue = $(breedType.target).combobox('getValue');
				if(breedTypeValue != null && breedTypeValue != ''){
					var sememIds = '';
		        	$.each(sememIdsArray,function(i,data){
	        			if(i == sememIdsArray.length - 1){
	        				sememIds += data;
		        		}else{
		        			sememIds += data + ',';
		        		}
		        	});
					var data = [];
					$.ajax({  
				        url : basicUrl+'/production/searchSemenToList.do', 
				        data:{
				        	queryType:1,
				        	breedType:breedTypeValue,
				        	breedDate:newValue,
				        	sememIds:sememIds
				        },
				        async : false, 
				        type : "POST",  
				        dataType : "json",  
				        success : function(response) { 
				        	data = response.rows;
				        }  
				    }); 
					if( breedBoarFirst != null){
						$(breedBoarFirst.target).combobox('setValue','');
						$(breedBoarFirst.target).combobox('loadData',data);
					}
					if( breedBoarSecond != null){
						$(breedBoarSecond.target).combobox('setValue','');
						$(breedBoarSecond.target).combobox('loadData',data);
					}
					if( breedBoarThird != null){
						$(breedBoarThird.target).combobox('setValue','');
						$(breedBoarThird.target).combobox('loadData',data);
					}
				}
			}
		}
	}*/
}
/**删除行
 * pms{
 * model:明细模块代码
 * }*/
function detailDelete(){
	var record = $('#listTable').datagrid('getSelections');
	var length = record.length;
	if(length < 1){
		$.messager.alert('警告','请选择一条数据！');
	}else{
		$.messager.confirm('提示', '确定要删除这'+record.length+'条记录吗？', function(r){
			if (r){
				$.each(record,function(index,data){
					var row = $('#listTable').datagrid('getRowIndex',data);
					data.deletedFlag = "1";
					$('#listTable').datagrid('deleteRow',row);
					if(data.breedBoarFirst != null && data.breedBoarFirst != '' && data.breedBoarFirst != undefined){
						$.each(sememIdsArray,function(i,item){
							if(item == data.breedBoarFirst){
								sememIdsArray.splice(i,1);
								return false;
							}
						})
					}
					if(data.breedBoarSecond != null && data.breedBoarSecond != '' && data.breedBoarSecond != undefined){
						$.each(sememIdsArray,function(i,item){
							if(item == data.breedBoarSecond){
								sememIdsArray.splice(i,1);
								return false;
							}
						})
					}
					if(data.breedBoarThird != null && data.breedBoarThird != '' && data.breedBoarThird != undefined){
						$.each(sememIdsArray,function(i,item){
							if(item == data.breedBoarThird){
								sememIdsArray.splice(i,1);
								return false;
							}
						})
					}
				});
			}
		});
	}
}
/**
 * 清除方法
 */
function detailClear(){
	var listTable = $('#listTable');
	$.messager.confirm('提示', '确定要清空吗？', function(r){
		if (r){
			$('#listTable').datagrid('loadData',{success:true,total: 0, rows: [] });
		}
	});
	sememIdsArray = [];
}
/**
 * 排除已选公猪
 * @param sememIds
 * @param pigId
 */
function exitSemenId(sememIds,pigId){
	$.each(sememIdsArray,function(i,data){
		if(data != pigId){
			if(i == 0){
				sememIds += data;
    		}else{
    			sememIds += ',' + data;
    		}
		}
	});
	if(sememIds.substring(0,1) == ','){
		sememIds = sememIds.substring(1,sememIds.length);
	}
	return sememIds;
}
/**
 *  转入猪舍改变方法
 * @param newValue
 * @param oldValue
 */
function inHouseChangeFun(newValue,oldValue){
	if(oldValue == ''){
		return;
	}
	var rows = $('#listTable').datagrid('getRows');
	rows[editIndex].inPigpenId = '';
	rows[editIndex].inPigpenIdName = '';
	changeTableDisplayValue('listTable',editIndex,[{
		field:'inPigpenId',
		value:''
	}]);
	/*setTimeout(function () {
		$('#listTable').datagrid('endEdit',editIndex);
		$('#listTable').datagrid('updateRow',{
			index:editIndex,
			row:rows[editIndex]
		});
	 },100);*/
}


//重置
function onBtnReset(){
	$.messager.confirm('提示', '重置将清空数据，确定要重置吗？', function(r){
		if (r){
			$('#editForm').form('reset');
			var listTable = document.getElementById("listTable");
			if(listTable!= null){
				$('#listTable').datagrid('loadData',{success:true,total: 0, rows: [] });
				sememIdsArray = [];
			}
		}
	});
}

