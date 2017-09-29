var prUrl='/production/';
var saveUrl=prUrl+'leave.do';
var editIndex = undefined;
var eventName = 'BREED_PIG_DIE';
//默认值为1
var oldPigType = '';
$(document).ready(function(){
	$('#listTable').datagrid(
			j_eventGrid({
			dbClickEdit:true,
			columns:[[
			          	{field:'ck',checkbox:true},
						j_gridText({field:'rowId',title:'ID',hidden:true}),
						j_gridText({field:'pigId',title:'猪只ID',hidden:true}),
						j_gridText({field:'pigQty',title:'带仔数',hidden:true}),
						j_gridText({field:'pigType',title:'猪只类别',hidden:true}),
						j_gridText({field:'pigClass',title:'猪只状态',hidden:true}),
						j_gridText({field:'houseId',title:'猪舍',hidden:true}),
						j_gridText({field:'pigPenId',title:'猪栏',hidden:true}),
						j_gridText({field:'minValidDate',title:'minValidDate',hidden:true}),
						j_gridText({field:'maxValidDate',title:'maxValidDate',hidden:true}),
						j_gridText({field:'deletedFlag',title:'deletedFlag',hidden:true}),
						//耳牌号
						j_gridText(earBrandTextBox({width:100})),
						//耳缺号
						j_gridText(earBrandTextBox({field:'earShort',textField:'earShort',valueField:'earShort',title:'耳缺号',width:100})),
						j_gridText({field:'pigInfo',title:'耳缺号    -  品种   -  胎次   -  状态(天)  -  日龄  - 猪舍  - 猪栏',width:450}),
						j_gridText({field:'enterDate',title:'死亡日期',width:150,editor:{type:'datebox',options:{editable:false}}}),
						j_gridNumber({field:'leaveWeight',title:'死亡体重',min:0,max:500,
							precision:2,
							increment:0.1,
//		              		formatter:function(value,row){
//		              			value = value == null || value == undefined || value == '' ? 0.00 : value;
//		              			row.leaveWeight = value;
//			              		return value+'KG';
//			              	},
			              	width:80}),
						j_gridText({field:'leaveReason',title:'死亡原因',width:80,
		              		formatter:function(value,row){
		              			return row.leaveReasonName;
		              		},
		              		editor:
		              		xnGridCdComboBox({
		              			field:'leaveReason',
		              			typeCode:'BOAR_DIE_REASON'
		              		})
		              	}),
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
		              	//技术员
		              	j_gridText(workerGridComboGrid({width:80})),
		              	j_gridText({field:'notes',title:'备注',width:80,editor:'textbox'})
				    ]],
		    onEndEdit:function(index,row){
		    	var dieReason = $('#listTable').datagrid('getEditor', {
	                index: index,
	                field: 'leaveReason'
	            });
		    	if(dieReason != null){
		    		row.leaveReasonName = $(dieReason.target).combogrid('getText');
		    	}
		    },
		    onBeginEdit:function(index,row){
		    	if(row.pigType != 2 || row.pigClass != 10){
		    		var weanWeight = $('#listTable').datagrid('getEditor', {
				        index: index,
				        field: 'weanWeight'
				    });
		    		if(weanWeight != null){
		    			$(weanWeight.target).combobox('disable');
		    			$(weanWeight.target).combobox('getText',0.0);
		    		}
		    	}
		    }
		})
	);
	
	$('#preSaveRecordTable').datagrid(
			j_grid_view({
				fitColumns:false,
				haveCheckBox:false,
				columnFields:'pigId,earBrand,pigInfo,enterDate,leaveWeight,leaveReasonName,weanWeight,workerName,notes',
				columnTitles:'猪只ID,耳牌号,耳缺号    -  品种   -  胎次   -  状态(天)  -  日龄  - 猪舍  - 猪栏,死亡日期,死亡体重(KG),死亡原因,断奶窝重,技术员,备注',
				hiddenFields:'pigId,notes,workerName',
				columnWidths:'100,100,350,90,110,90,100,100,100',
				fit:false,
				width:'auto',
				height:'100%',
				pagination:false
			},'preSaveRecordTable')
	);
});
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
		if(queryParams == null){
			$.messager.alert('提示','前台提示--未添加明细不能提交！');
			return;
		}
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
	var rows = $('#listTable').datagrid('getRows');
	var str = '';
	$.each(rows,function(index,row){
		//判断选择的猪只是否是哺乳母猪
		if(row.pigType == 2 && row.pigClass == 10){
			str += '第'+(index+1)+"行是哺乳母猪，带仔数量为"+row.pigQty+"，哺乳母猪死亡，仔猪全部断奶<br>";
		}
	});
	if(str == ''){
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
						leftSilpFun('preSaveRecord',true,9002);
						if(preRecord.success == undefined){
							preRecord.success = true;
						}
						$('#preSaveRecordTable').datagrid('loadData',preRecord);
						if(listTable!= null){
							$('#listTable').datagrid('loadData',{success:true,total: 0, rows: [] });
						}
					}
					$('#editForm').form('reset');
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
	}else{
		$.messager.confirm('提示', str, function(r){
			if (r){
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
								leftSilpFun('preSaveRecord',true,9002);
								if(preRecord.success == undefined){
									preRecord.success = true;
								}
								$('#preSaveRecordTable').datagrid('loadData',preRecord);
								if(listTable!= null){
									$('#listTable').datagrid('loadData',{success:true,total: 0, rows: [] });
								}
							}
							$('#editForm').form('reset');
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
		});
	}
}

