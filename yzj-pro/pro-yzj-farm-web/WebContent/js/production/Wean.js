var prUrl='/production/';
var saveUrl=prUrl+'wean.do';
var editIndex = undefined;
var eventName = 'WEAN';
//默认值为2
var oldPigType = 2;
var dnflag = 'off';
var dnxycbbFlag = 'off';
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
$.ajax({  
    url : basicUrl+'/param/getSettingValueByCode.do?settingCode=DNXYCBB', 
    async:false,
    type : "POST",  
    success : function(data) { 
    	var obj = eval('(' + data + ')');
    	dnxycbbFlag = obj.rows;
    }
});
$.ajax({  
    url : basicUrl+prUrl+'searchBackfatScore.do',
    async : false, 
    type : "POST",  
    dataType : "json",  
    success : function(response) { 
    	backfatScoreArr = response.rows;
    }  
});
var detailDefaultValues = {
		status:'1',
		deletedFlag:'0',
};

$(document).ready(function(){
	var columns = [
		              	j_gridText({field:'pigInfo',title:'耳缺号    -  品种   -  胎次   -  状态(天)  -  日龄  - 猪舍  - 猪栏',width:500}),
	                  	
		              	j_gridNumber({field:'weanNum',title:'断奶仔猪数',width:100,min:0,onChange:weanNumChangeFun}),
		              	j_gridNumber({field:'dieNum',title:'死亡仔猪数',width:100,min:0,onChange:dieNumChangeFun,hidden:true}),
		              	j_gridText({field:'enterDate',title:'断奶日期',width:150,editor:{type:'datebox',options:{editable:false}}}),
		              	j_gridNumber({field:'weanWeight',
		              		precision:2,
		              		increment:0.1,
		              		min:0,max:150,
		              		formatter:function(value,row){
		              			value = value == null || value == undefined || value == '' ? 0.00 : value;
		              			//row.weanWeight = value;
			              		return value+'KG';
			              	},
		              		title:'断奶窝重',width:100,onChange:weightChangeFun})
		              	];
	if(dnxycbbFlag=='on' || dnxycbbFlag=='ON'){
		columns.push(j_gridNumber({field:'backfat',title:'背膘',plinkField:['score'],width:100,min:5,max:30,onChange:changeBackfatScore}),
        j_gridText({field:'score',title:'评分',width:100}));
	}
	if(dnflag == 'on' || dnflag == 'ON'){
		columns.splice(1,0,j_gridText({field:'pigQty',title:'带仔数',width:100}));
	}
	columns.push(
			//猪舍
          	j_gridText(houseGridComboGrid({
          		field:'inHouseId',
          		width:100,
          		formatter:function(value,row){
          			return row.inHouseIdName
          			},
          		url:'/basicInfo/searchHouseToList.do?lineId=0&houseType=2,3,4,5',
          		onChange:inHouseChangeFun
      		})),
          	//猪栏
          	j_gridText(pigpenGridComboGrid({
          		field:'inPigpenId',
          		width:100,
          		formatter:function(value,row){
          			return row.inPigpenIdName
          			}
      		})),
      		//技术员
          	j_gridText(workerGridComboGrid({width:100})),
          	j_gridText({field:'notes',title:'备注',width:100,editor:'textbox'}));
	
	$('#listTable').datagrid(
			j_eventGrid({
			dbClickEdit:true,
			fitColumns:false,
			//固定列
			frozenColumns:[[
			                {field:'ck',checkbox:true},
			              	j_gridText({field:'rowId',title:'ID',hidden:true}),
			              	j_gridText({field:'pigId',title:'猪只ID',hidden:true}),
			              	j_gridText({field:'houseId',title:'猪舍ID',hidden:true}),
			              	j_gridText({field:'minValidDate',title:'minValidDate',hidden:true}),
			              	j_gridText({field:'maxValidDate',title:'maxValidDate',hidden:true}),
			              	j_gridText({field:'deletedFlag',title:'deletedFlag',hidden:true}),
			              	//耳牌号
			              	j_gridText(earBrandTextBox({width:150}))
			              	]],
			columns:[columns],
		    onEndEdit:function(index,row){
	            var inHouse = $('#listTable').datagrid('getEditor', {
	                index: index,
	                field: 'inHouseId'
	            });
	            if(inHouse != null){
	            	row.inHouseIdName = $(inHouse.target).combogrid('getText');
	            }
	            var inPigpen = $('#listTable').datagrid('getEditor', {
	                index: index,
	                field: 'inPigpenId'
	            });
	            if(inPigpen != null){
	            	 row.inPigpenIdName = $(inPigpen.target).combogrid('getText');
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
			}
		})
	);
	$('#preSaveRecordTable').datagrid(
			j_grid_view({
				fitColumns:false,
				haveCheckBox:false,
				columnFields:'pigId,earBrand,pigInfo,pigQty,weanNum,dieNum,enterDate,weanWeight,backfat,score,inHouseIdName,inPigpenIdName,workerName,notes',
				columnTitles:'猪只ID,耳牌号,耳缺号    -  品种   -  胎次   -  状态(天)  -  日龄  - 猪舍  - 猪栏,带仔数,断奶仔猪数,死亡仔猪数,断奶日期,断奶窝重(KG),背膘,评分,猪舍,猪栏,技术员,备注',
				hiddenFields:'pigId,notes,workerName'+((dnxycbbFlag=='on' || dnxycbbFlag=='ON')?'':'backfat,score'),
				columnWidths:'100,100,350,80,100,100,90,120,100,80,100,100,100,100',
				fit:false,
				width:'auto',
				height:'100%',
				pagination:false
			},'preSaveRecordTable')
	);
	
	// 是否测试背膘，后台验证用hidden项
	$('#dnxycbbFlag').val(dnxycbbFlag);
});
/**
 *  猪舍改变方法
 * @param newValue
 * @param oldValue
 */
function inHouseChangeFun(newValue,oldValue){
	if(oldValue == ''){
		return;
	}
	var rows = $('#listTable').datagrid('getRows');
	rows[editIndex].inPigpenId = '';
	rows[editIndex].inPigpenName = '';
	changeTableDisplayValue('listTable',editIndex,[{
		field:'inPigpenId',
		value:''
	}]);
//	setTimeout(function () {
//		$('#listTable').datagrid('endEdit',editIndex);
//		$('#listTable').datagrid('updateRow',{
//			index:editIndex,
//			row:rows[editIndex]
//		});
//	 },100);
}
/**
 * 断奶仔猪数改变方法
 * @param newValue
 * @param oldValue
 */
function weanNumChangeFun(newValue,oldValue){
	if(editIndex != undefined){
		var rows = $('#listTable').datagrid('getRows');
		if(dnflag == 'ON' || dnflag == 'on'){
			var pigQty = rows[editIndex].pigQty;
			var autoFosterDieFlag = $('#autoFosterDie').is(':checked');
			if(newValue > pigQty && !autoFosterDieFlag){
				$.messager.alert('警告', '断奶仔猪数大于带仔数，请重新输入！');
				$(this).numberspinner('setValue', oldValue);
			}else{
//				setTimeout(function () {
//					$('#listTable').datagrid('endEdit',editIndex);
//					$('#listTable').datagrid('updateRow',{
//						index:editIndex,
//						row:rows[editIndex]
//					});
//				 },100);
			}
		}
		var weanNumSum = 0,dieNumSum = 0;
		if(newValue != ''){
			weanNumSum = strToInt(newValue);
			if(dnflag == 'ON' || dnflag == 'on'){
				if(!autoFosterDieFlag){
					dieNumSum = rows[editIndex].dieNum
				}else{
					dieNumSum = 0;
				}
				//dieNumSum = rows[editIndex].pigQty - strToInt(newValue);
			}
		}
		$.each(rows,function(i,item){
			if( i != editIndex && item.weanNum != undefined && item.weanNum != ''){
				weanNumSum += strToInt(item.weanNum);
			}
			if(dnflag == 'ON' || dnflag == 'on'){
				if( i != editIndex && item.dieNum != undefined && item.dieNum != ''){
					dieNumSum += strToInt(item.dieNum);
				}
			}
		})
		$('#weanNumSum').html(weanNumSum);
		$('#weanNumAvg').html((weanNumSum/rows.length).toFixed(2));
		if(dnflag == 'ON' || dnflag == 'on'){
			$('#dieNumSum').html(dieNumSum);
		}
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
		if(dnflag == 'ON' || dnflag == 'on'){
			var pigQty = rows[editIndex].pigQty;
			if(newValue > pigQty){
				$.messager.alert('警告', '死亡仔猪数大于带仔数，请重新输入！');
				$(this).numberspinner('setValue', oldValue);
			}else{
				rows[editIndex].weanNum = pigQty - newValue;
				changeTableDisplayValue('listTable',editIndex,[{
					field:'weanNum',
					value:rows[editIndex].weanNum
				}]);
//				setTimeout(function () {
//					$('#listTable').datagrid('endEdit',editIndex);
//					$('#listTable').datagrid('updateRow',{
//						index:editIndex,
//						row:rows[editIndex]
//					});
//				 },100);
			}
		}
		var dieNumSum = 0,weanNumSum = 0;
		if(newValue != ''){
			dieNumSum = strToInt(newValue);
			if(dnflag == 'ON' || dnflag == 'on'){
				if(!$('#autoFosterDie').is(':checked')){
					weanNumSum = rows[editIndex].weanNum
				}else{
					weanNumSum = 0;
				}
			}
		}
		$.each(rows,function(i,item){
			if( i != editIndex && item.dieNum != undefined && item.dieNum != ''){
				dieNumSum += strToInt(item.dieNum);
			}
			if(dnflag == 'ON' || dnflag == 'on'){
				if( i != editIndex && item.weanNum != undefined && item.weanNum != ''){
					weanNumSum += strToInt(item.weanNum);
				}
			}
		})
		$('#dieNumSum').html(dieNumSum);
		if(dnflag == 'ON' || dnflag == 'on'){
			$('#weanNumSum').html(weanNumSum);
		}
	}
}
/**
 * 窝重改变方法
 * @param newValue
 * @param oldValue
 */
function weightChangeFun(newValue,oldValue){
	if(editIndex == undefined){
		return;
	}
	var rows = $('#listTable').datagrid('getRows');

	var weightAvg = 0;
	if(newValue != ''){
		weightAvg = parseFloat(newValue);
	}
	$.each(rows,function(i,item){
		if( i != editIndex && item.weanWeight != undefined && item.weanWeight != ''){
			weightAvg += parseFloat(item.weanWeight);
		}
	})
	$('#weanWeightAvg').html((weightAvg/rows.length).toFixed(2));
}

function changeAutoFosterDieFun(newValue,oldValue){
	if(editIndex == undefined){
		return;
	}
	var rows = $('#listTable').datagrid('getRows');
	if(newValue == '1'){
		
		rows[editIndex].dieNum = 0;
		changeTableDisplayValue('listTable',editIndex,[{
			field:'dieNum',
			value:rows[editIndex].dieNum
		}]);
	}else{
		rows[editIndex].weanNum = 0;
		changeTableDisplayValue('listTable',editIndex,[{
			field:'weanNum',
			value:rows[editIndex].weanNum
		}]);
	}
	var dieNumSum = strToInt(rows[editIndex].dieNum);
	var weanNumSum = strToInt(rows[editIndex].weanNum);
	$.each(rows,function(i,item){
		if( i != editIndex && item.dieNum != undefined && item.dieNum != ''){
			dieNumSum += strToInt(item.dieNum);
		}
		if(dnflag == 'ON' || dnflag == 'on'){
			if( i != editIndex && item.weanNum != undefined && item.weanNum != ''){
				weanNumSum += strToInt(item.weanNum);
			}
		}
	})
	$('#dieNumSum').html(strToInt(dieNumSum));
	if(dnflag == 'ON' || dnflag == 'on'){
		$('#weanNumSum').html(strToInt(weanNumSum));
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
	changeTableDisplayValue('listTable',editIndex,[{
		field:'score',
		value:row.score
	}]);
//	setTimeout(function () {
//		$('#listTable').datagrid('endEdit',editIndex);
//		$('#listTable').datagrid('updateRow',{
//			index:editIndex,
//			row:row
//		});
//	 },100);
}

/**
 * 删除明细
 */
function detailDelete(){
	var listTable = $('#listTable');
	var record = listTable.datagrid('getSelections');
	var length = record.length;
	if(length < 1){
		$.messager.alert('警告','请选择一条数据！');
	}else{
		$.messager.confirm('提示', '确定要删除这'+record.length+'条记录吗？', function(r){
			if (r){
				if(endEditing()){
					$.each(record,function(index,data){
						var row = listTable.datagrid('getRowIndex',data);
						data.deletedFlag = "1";
						listTable.datagrid('deleteRow',row);
					});
					var listRows = listTable.datagrid('getRows');
					if(listRows.length <= 0){
						$('#weanNumSum').html('0');
						$('#dieNumSum').html('0');
					}else{
						var weanNumSum = 0,dieNumSum = 0,weanWeightSum = 0;
						$.each(listRows,function(i,row){
							weanNumSum += strToInt(row.weanNum == undefined ? 0 : row.weanNum);
							dieNumSum += strToInt(row.dieNum == undefined ? 0 : row.dieNum);
							weanWeightSum += strToInt(row.weanWeight == undefined ? 0 : row.weanWeight);
						});
						$('#weanNumSum').html(weanNumSum);
						$('#dieNumSum').html(dieNumSum);
						$('#weanNumAvg').html((weanNumSum/listRows.length).toFixed(2));
						$('#weanWeightAvg').html((weanWeightSum/listRows.length).toFixed(2));
					}
				}
			}
		});
	}
}
/**
 * 清空
 */
function detailClear(){
	var listTable = $('#listTable');
	$.messager.confirm('提示', '确定要清空吗？', function(r){
		if (r){
			$('#listTable').datagrid('loadData',{success:true,total: 0, rows: [] });
			$('#weanNumSum').html('0');
			$('#dieNumSum').html('0');
			$('#weanWeightAvg').html('0');
			$('#weanNumAvg').html('0');
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
		var saveValidFalg = true;
		var errorEarBrand = null;
		var houseIdArray = [];
		var houseId_ = null;
		var autoFosterDieFlag = $(autoFosterDie).is(':checked') == false ? 0  : 1;
		$.each(eval(queryParams.gridList),function(i,item){
			if(item.status == -1){
				saveValidFalg = false;
				errorEarBrand = item.earBrand;
				return false;
			}
			if(autoFosterDieFlag == 1){
				houseIdArray.push(item.houseId);
			}
		});
		if(houseIdArray != null && houseIdArray.length > 1){
			for(var i=0;i<houseIdArray.length;i++){
				for(var j=i+1;j < houseIdArray.length; j++){
					if(houseIdArray[i] != houseIdArray[j]){
						$.messager.alert('提示','前台提示--自动寄养死亡需要选择相同猪舍，请重新选择');
						return false;
					}
				}
			}
		}
		
		queryParams.autoFosterDieFlag = autoFosterDieFlag;
		if(!saveValidFalg){
			$.messager.alert('提示','前台提示--耳牌号：【'+errorEarBrand+'】没有对应的猪只数据，请重新选择！');
			return;
		}
	}
	$.messager.progress();	
	$('#editForm').form('submit',{
		url:basicUrl+saveUrl,
		queryParams:queryParams,
		onSubmit: function(){
			var isValid = $('#editForm').form('validate');
			if (!isValid){
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
					if(autoFosterDieFlag){
						$.each(eval(preRecord.rows),function(i,item){
							delete item.dieNum;
						});
					}
					$('#preSaveRecordTable').datagrid('loadData',preRecord);
					if(listTable!= null){
						$('#listTable').datagrid('loadData',{success:true,total: 0, rows: [] });
					}
					$('#weanNumSum').html('0');
					$('#dieNumSum').html('0');
					$('#weanWeightAvg').html('0');
					$('#weanNumAvg').html('0');
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
			if(!$(autoFosterDie).is(':checked')){
				$('#listTable').datagrid('showColumn', 'dieNum'); 
			}else{
				$('#listTable').datagrid('hideColumn', 'dieNum');
			}
			$.messager.progress('close');
		}
	});
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
			}
		}
	});
}

/**
 * 猪舍grid编辑
 * @param pms
 * @returns {___anonymous4549_5528}
 */
function houseGridComboGrid(pms){
	var result = {
			width:pms.width == null ? null : pms.width,
			field:pms.field == null ? 'houseId' : pms.field,
			title:pms.title == null ? '猪舍名称' : pms.title,
	        formatter:pms.formatter == null ? function(value,row){
	           return row.houseIdName;  
			} : pms.formatter,
			editor:
				xnGridComboGrid({
					field:pms.field == null ? 'houseId' : pms.field,
					idField:pms.idField == null ? 'rowId' : pms.idField,
					textField:pms.textField == null ? 'houseName' : pms.textField,
					width:pms.combogridWidth == null ? 550 : pms.combogridWidth,
					url:pms.url == null ? null : pms.url,
					editable:true,
					onChange:pms.onChange == null ? function(newValue,oldValue){
						return;
						}:pms.onChange,
					onSelect:pms.onSelect == null ? function(index,row){
					return;
					}:pms.onSelect,
					columns:pms.columns == null ? [[ 	
								{field:'rowId',title:'ID',width:100,hidden:true},
//						        {field:'businessCode',title:'猪舍代码',width:100},
						        {field:'houseName',title:'猪舍名称',width:100},
						        {field:'houseVolume',title:'猪舍容量',width:100},
						        {field:'pigQty',title:'猪只数量',width:100},
						        {field:'houseTypeName',title:'猪舍类别',width:100},
						        {field:'notes',title:'备注',fitColumns:true}
						    ]] : pms.columns
	  			})
	      	}	
		return result;
}

function autoFosterDieFun(autoFosterDie){
	var checkedFlag = $(autoFosterDie).is(':checked');
	var rows = $('#listTable').datagrid('getRows');
	if(rows.length > 0){
		$.messager.confirm('提示', '重置将清空数据，确定要重置吗？', function(r){
			if (r){
				if(!checkedFlag){
					$('#listTable').datagrid('showColumn', 'dieNum'); 
				}else{
					$('#listTable').datagrid('hideColumn', 'dieNum');
				}
				$('#listTable').datagrid('loadData',{success:true,total: 0, rows: [] });
				$('#weanNumSum').html('0');
				$('#dieNumSum').html('0');
			}else{
				if(checkedFlag){
					document.getElementById("autoFosterDie").checked=false
				}else{
					document.getElementById("autoFosterDie").checked=true
				}
			}
		});
	}else{
		if(!checkedFlag){
			$('#listTable').datagrid('showColumn', 'dieNum'); 
		}else{
			$('#listTable').datagrid('hideColumn', 'dieNum');
		}
	}
}