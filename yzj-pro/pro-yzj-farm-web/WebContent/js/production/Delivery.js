var prUrl='/production/';
var saveUrl=prUrl+'delivery.do';
var editIndex = undefined;
var eventName = 'DELIVERY';
//默认值为1
var oldPigType = 2;
var MZFMGLZZ_flag = jAjax.submit('/param/getSettingValueByCode.do?settingCode=MZFMGLZZ');
var operateHidden = MZFMGLZZ_flag == "ON" || MZFMGLZZ_flag == "on" ? false : true;
var RZBSCL_flag = jAjax.submit('/param/getSettingValueByCode.do?settingCode=RZBSCL');
var FMSFLZ_flag = jAjax.submit('/param/getSettingValueByCode.do?settingCode=FMSFLZ');
var detailDefaultValues = {
		status:'1',
		deletedFlag:'0',
		//设置默认值
		deliveryType:'N',
		deliveryTypeName:'否'
};
$(document).ready(function(){
	var columns = [
	                {field:'ck',checkbox:true},
	              	j_gridText({field:'rowId',title:'ID',hidden:true}),
	              	j_gridText({field:'pigId',title:'猪只ID',hidden:true}),
	              	j_gridText({field:'houseId',title:'猪舍ID',hidden:true}),
	              	j_gridText({field:'minValidDate',title:'minValidDate',hidden:true}),
	              	j_gridText({field:'maxValidDate',title:'maxValidDate',hidden:true}),
	              	j_gridText({field:'deletedFlag',title:'deletedFlag',hidden:true}),
	              	//耳牌号
	              	j_gridText(earBrandTextBox({width:150})),
		          	j_gridText({field:'pigInfo',title:'耳缺号-品种-状态(胎次)-日龄-猪舍-猪栏',width:500}),
					j_gridText({
	              		field:'deliveryType',
	              		title:'是否助产',
	              		width:80,
	              		formatter:function(value,row){
	              			return row.deliveryTypeName;
	              		},
	              		editor:
	              		xnGridCdComboBox({
	              			field:'deliveryType',
	              			typeCode:'YES_OR_NO'
	              		})
	              	}),
	              	j_gridText({field:'enterDate',title:'分娩日期',width:150,editor:{type:'datebox',options:{editable:false}}}),
	              	j_gridNumber({field:'labor',title:'产程',min:1,max:48,precision:2,increment:0.1,
	              		formatter:function(value,row){
	              			value = value == null || value == undefined || value == '' ? 0 : value;
		              		return value+'H';
		              	},
	              	width:80}),
	              	j_gridNumber({field:'aliveLitterWeight',title:'活仔窝重',min:0,max:50,
	              		precision:2,
						increment:0.1,
	              		formatter:function(value,row){
		              			value = value == null || value == undefined || value == '' ? 0.00 : value;
		              			return value+'KG';
		              	},
	              	width:80,onChange:weightChangeFun}),
	              	j_gridNumber({field:'healthyNum',title:'健仔数',width:80,min:0,max:25,onChange:healthyNumChangeFun}),
	              	j_gridNumber({field:'weakNum',title:'弱仔数',width:80,min:0,max:30,onChange:weakNumNumChangeFun}),
	              	j_gridNumber({field:'stillbirthNum',title:'死胎',width:80,min:0,onChange:stillbirthNumChangeFun}),
	              	j_gridNumber({field:'mummyNum',title:'木乃伊',width:80,min:0,onChange:mummyNumChangeFun}),
	              	j_gridNumber({field:'mutantNum',title:'畸形',width:80,min:0,onChange:mutantNumChangeFun}),
	               ];
	if(FMSFLZ_flag=='on' || FMSFLZ_flag=='ON'){
		columns.push(j_gridNumber({field:'aliveLitterY',title:'活仔公',width:80,min:0,onChange:aliveLitterYChangeFun}));
		columns.push(j_gridNumber({field:'aliveLitterX',title:'活仔母',width:80,min:0,onChange:aliveLitterXChangeFun}));
	}
  	columns.push(
  		  	// 技术员
  		  	j_gridText(workerGridComboGrid({})),
  		  	j_gridText({field:'notes',title:'备注',editor:'textbox'}));
	$('#listTable').datagrid(
			j_eventGrid({
			dbClickEdit:true,
			fitColumns:false,
			columns:[columns],
		    onEndEdit:function(index,row){
		    	 var deliveryType = $('#listTable').datagrid('getEditor', {
	                index: index,
	                field: 'deliveryType'
	            });
		    	if(deliveryType != null){
		    		row.deliveryTypeName = $(deliveryType.target).combogrid('getText');
		    	}
	        }
		})
	);
	
	$('#preSaveRecordTable').datagrid(
			j_grid_view({
				fitColumns:false,
				haveCheckBox:false,
				columnFields:'pigId,earBrand,pigInfo,deliveryTypeName,enterDate,labor,aliveLitterWeight,healthyNum,weakNum,stillbirthNum,mutantNum,mummyNum,blackNum,aliveLitterY,aliveLitterX,workerName,notes',
				columnTitles:'猪只ID,耳牌号,耳缺号-品种-状态(胎次)-日龄-猪舍-猪栏,是否助产,分娩日期,产程(H),活仔窝重(KG),健仔数,弱仔数,死胎,畸形,木乃伊,黑胎,活仔公,活仔母,技术员,备注',
				hiddenFields:'pigId,labor,workerName,notes'+((FMSFLZ_flag=='on' || FMSFLZ_flag=='ON')?'':',aliveLitterY,aliveLitterX'),
				columnWidths:'100,100,350,90,90,100,110,80,80,80,80,80,80,80,80,100,100',
				fit:false,
				width:'auto',
				height:'100%',
				pagination:false
			},'preSaveRecordTable')
	);
	
	$('#FMSFLZ_flag').val(FMSFLZ_flag);

});
//仔猪操作方法
function operateFun(){
	alert(0);
}
/**
 * 活仔公改变事件
 * @param newValue
 * @param oldValue
 */
function aliveLitterYChangeFun(newValue,oldValue){
	if(editIndex == undefined){
		return;
	}
	newValue = strToInt(newValue == '' ? 0 : newValue);
	var rows = $('#listTable').datagrid('getRows');
	var healthyNum = strToInt(rows[editIndex].healthyNum);
	if(healthyNum == undefined){
		$.messager.alert('提示', '请先输入健仔数！');
		return;
	}
	if(RZBSCL_flag == "OFF" || RZBSCL_flag == 'off'){
		if(rows[editIndex].weakNum != undefined){
			healthyNum += strToInt(rows[editIndex].weakNum);
		}
	}
	if(newValue > healthyNum){
		if(RZBSCL_flag == "OFF" || RZBSCL_flag == 'off'){
			$.messager.alert('提示', '活仔公数量大于健仔数+弱仔数,请重新输入！');
		}else{
			$.messager.alert('提示', '活仔公数量大于健仔数,请重新输入！');
		}
		var aliveLitterY = $('#listTable').datagrid('getEditor', {
	        index: editIndex,
	        field: 'aliveLitterY'
	    });
		if(aliveLitterY != null){
			$(aliveLitterY.target).numberspinner('setValue',oldValue);
		}
	}else{
		rows[editIndex].aliveLitterX = healthyNum - newValue;
		changeTableDisplayValue('listTable',editIndex,[{
			field:'aliveLitterX',
			value:rows[editIndex].aliveLitterX
		}]);
//		setTimeout(function () {
//			$('#listTable').datagrid('endEdit',editIndex);
//			$('#listTable').datagrid('updateRow',{
//				index:editIndex,
//				row:rows[editIndex]
//			});
//		 },100);
	}
}
/**
 * 活仔母改变事件
 * @param newValue
 * @param oldValue
 */
function aliveLitterXChangeFun(newValue,oldValue){
	if(editIndex == undefined){
		return;
	}
	newValue = strToInt(newValue == '' ? 0 : newValue);
	var rows = $('#listTable').datagrid('getRows');
	var healthyNum = strToInt(rows[editIndex].healthyNum);
	if(healthyNum == undefined){
		$.messager.alert('提示', '请先输入健仔数！');
		return;
	}
	if(RZBSCL_flag == "OFF" || RZBSCL_flag == 'off'){
		if(rows[editIndex].weakNum != undefined){
			healthyNum += strToInt(rows[editIndex].weakNum);
		}
	}
	if(newValue > healthyNum){
		if(RZBSCL_flag == "OFF" || RZBSCL_flag == 'off'){
			$.messager.alert('提示', '活仔母数量大于健仔数+弱仔数,请重新输入！');
		}else{
			$.messager.alert('提示', '活仔母数量大于健仔数,请重新输入！');
		}
		var aliveLitterX = $('#listTable').datagrid('getEditor', {
	        index: editIndex,
	        field: 'aliveLitterX'
	    });
		if(aliveLitterX != null){
			$(aliveLitterX.target).numberspinner('setValue',oldValue);
		}
	}else{
		rows[editIndex].aliveLitterY = healthyNum - newValue;
		changeTableDisplayValue('listTable',editIndex,[{
			field:'aliveLitterY',
			value:rows[editIndex].aliveLitterY
		}]);
//		setTimeout(function () {
//			$('#listTable').datagrid('endEdit',editIndex);
//			$('#listTable').datagrid('updateRow',{
//				index:editIndex,
//				row:rows[editIndex]
//			});
//		 },100);
	}
}
/**
 * 健仔数改变方法
 * @param newValue
 * @param oldValue
 */
function healthyNumChangeFun(newValue,oldValue){
	if(editIndex == undefined){
		return;
	}
	var rows = $('#listTable').datagrid('getRows');
	rows[editIndex].aliveLitterY = 0;
	rows[editIndex].aliveLitterX = 0;
//	setTimeout(function () {
//		$('#listTable').datagrid('endEdit',editIndex);
//		$('#listTable').datagrid('updateRow',{
//			index:editIndex,
//			row:rows[editIndex]
//		});
//	 },100);
	changeTableDisplayValue('listTable',editIndex,[{
		field:'aliveLitterY',
		value:0
	},{
		field:'aliveLitterX',
		value:0
	}]);
	var healthyNumSum = 0;
	if(newValue != ''){
		healthyNumSum = strToInt(newValue);
	}
	$.each(rows,function(i,item){
		if( i != editIndex && item.healthyNum != undefined && item.healthyNum != ''){
			healthyNumSum += strToInt(item.healthyNum);
		}
	})
	$('#healthyNumSum').html(healthyNumSum);
	$('#healthyNumAvg').html((healthyNumSum/rows.length).toFixed(2));
}
/**
 * 弱仔数改变方法
 * @param newValue
 * @param oldValue
 */
function weakNumNumChangeFun(newValue,oldValue){
	if(editIndex == undefined){
		return;
	}
	var rows = $('#listTable').datagrid('getRows');
	if(RZBSCL_flag == "OFF" || RZBSCL_flag == "off" ){
		rows[editIndex].aliveLitterY = 0;
		rows[editIndex].aliveLitterX = 0;
		changeTableDisplayValue('listTable',editIndex,[{
			field:'aliveLitterY',
			value:0
		},{
			field:'aliveLitterX',
			value:0
		}]);
//		setTimeout(function () {
//			$('#listTable').datagrid('endEdit',editIndex);
//			$('#listTable').datagrid('updateRow',{
//				index:editIndex,
//				row:rows[editIndex]
//			});
//		 },100);
	}
	var weakNumSum = 0;
	if(newValue != ''){
		weakNumSum = strToInt(newValue);
	}
	$.each(rows,function(i,item){
		if( i != editIndex && item.weakNum != undefined && item.weakNum != ''){
			weakNumSum += strToInt(item.weakNum);
		}
	})
	$('#weakNumSum').html(weakNumSum);
}
/**
 * 死胎总数方法
 * @param newValue
 * @param oldValue
 */
function stillbirthNumChangeFun(newValue,oldValue){
	if(editIndex == undefined){
		return;
	}
	var rows = $('#listTable').datagrid('getRows');
	var stillbirthSum = 0;
	if(newValue != ''){
		stillbirthSum = strToInt(newValue);
	}
	$.each(rows,function(i,item){
		if( i != editIndex && item.stillbirthNum != undefined && item.stillbirthNum != ''){
			stillbirthSum += strToInt(item.stillbirthNum);
		}
	})
	$('#stillbirthNumSum').html(stillbirthSum);
}
/**
 * 木乃伊总数方法
 * @param newValue
 * @param oldValue
 */
function mummyNumChangeFun(newValue,oldValue){
	if(editIndex == undefined){
		return;
	}
	var rows = $('#listTable').datagrid('getRows');

	var mummySum = 0;
	if(newValue != ''){
		mummySum = strToInt(newValue);
	}
	$.each(rows,function(i,item){
		if( i != editIndex && item.mummyNum != undefined && item.mummyNum != ''){
			mummySum += strToInt(item.mummyNum);
		}
	})
	$('#mummyNumSum').html(mummySum);
}
/**
 * 畸形总数方法
 * @param newValue
 * @param oldValue
 */
function mutantNumChangeFun(newValue,oldValue){
	if(editIndex == undefined){
		return;
	}
	var rows = $('#listTable').datagrid('getRows');

	var mutantSum = 0;
	if(newValue != ''){
		mutantSum = strToInt(newValue);
	}
	$.each(rows,function(i,item){
		if( i != editIndex && item.mutantNum != undefined && item.mutantNum != ''){
			mutantSum += strToInt(item.mutantNum);
		}
	})
	$('#mutantNumSum').html(mutantSum);
}
/**
 * 窝均重方法
 * @param newValue
 * @param oldValue
 */
function weightChangeFun(newValue,oldValue){
	if(editIndex == undefined){
		return;
	}
	var weight = parseFloat(newValue);
	if(weight == 50){
		$.messager.confirm('提示', '确定活仔窝重为50kg？',function(r){
			if(!r){
//					var rows = $('#listTable').datagrid('getRows');
//					rows[editIndex].aliveLitterWeight = oldValue;
				var itemValueEditor = $('#listTable').datagrid('getEditor',{index:editIndex,field:'aliveLitterWeight'});
				$(itemValueEditor.target).numberspinner('setValue', oldValue);
			}
		});
	}

	var rows = $('#listTable').datagrid("getRows");
	var weightAvg = 0;
	if(newValue != ''){
		weightAvg = parseFloat(newValue);
	}
	$.each(rows,function(i,item){
		if( i != editIndex && item.aliveLitterWeight != undefined && item.aliveLitterWeight != ''){
			weightAvg += parseFloat(item.aliveLitterWeight);
		}
	})
	$('#weightAvg').html((weightAvg/rows.length).toFixed(2));
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
						$('#healthyNumSum').html('0');
						$('#weakNumSum').html('0');
						$('#healthyNumAvg').html('0');
						$('#weightAvg').html('0');
					}else{
						var healthyNumSum = 0,weakNumSum = 0,weightSum = 0,stillbirthNumSum = 0,mummyNumSum = 0,mutantNumSum = 0;
						$.each(listRows,function(i,row){
							healthyNumSum += strToInt(row.healthyNum == undefined ? 0 : row.healthyNum);
							weakNumSum += strToInt(row.weakNum == undefined ? 0 : row.weakNum);
							weightSum += parseFloat(row.weakNum == undefined ? 0 : row.aliveLitterWeight);
							stillbirthNumSum += strToInt(row.stillbirthNum == undefined ? 0 : row.stillbirthNum);
							mummyNumSum += strToInt(row.mummyNum == undefined ? 0 : row.mummyNum);
							mutantNumSum += strToInt(row.mutantNum == undefined ? 0 : row.mutantNum);
						});
						$('#healthyNumSum').html(healthyNumSum);
						$('#weakNumSum').html(weakNumSum);
						$('#stillbirthNumSum').html(stillbirthNumSum);
						$('#mummyNumSum').html(mummyNumSum);
						$('#mutantNumSum').html(mutantNumSum);
						$('#healthyNumAvg').html((healthyNumSum/length).toFixed(2));
						$('#weightAvg').html((weightSum/length).toFixed(2));
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
			$('#healthyNumSum').html('0');
			$('#weakNumSum').html('0');
			$('#stillbirthNumSum').html('0');
			$('#mummyNumSum').html('0');
			$('#mutantNumSum').html('0');
			$('#weightAvg').html('0');
			$('#healthyNumAvg').html('0');
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
		$.each(eval(queryParams.gridList),function(i,item){
			if(item.status == -1){
				saveValidFalg = false;
				errorEarBrand = item.earBrand;
				return false;
			}
		});
		if(!saveValidFalg){
			$.messager.alert('提示','前台提示--耳牌号：【'+errorEarBrand+'】没有对应的猪只数据，请重新选择！');
			return;
		}
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
					$('#healthyNumSum').html('0');
					$('#weakNumSum').html('0');
					$('#stillbirthNumSum').html('0');
					$('#mummyNumSum').html('0');
					$('#mutantNumSum').html('0');
					$('#weightAvg').html('0');
					$('#healthyNumAvg').html('0');
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
				$('#healthyNumSum').html('0');
				$('#weakNumSum').html('0');
			}
		}
	});
}