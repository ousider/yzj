var prUrl='/production/';
var saveUrl=prUrl+'foster.do';
var editIndex = undefined;
var eventName = 'FOSTER';
//默认值为2
var oldPigType = 2;
var fosterReasonData = [];
var fosterReasonDefaultValue = null;
var fosterReasonDefaultName = '';
$.ajax({  
    url : basicUrl+'/param/searchByTypeCode.do?typeCode=FOSTER_REASON',
    async : false, 
    type : "POST",  
    dataType : "json",  
    success : function(response) { 
    	fosterReasonData = response;
    	$.each(fosterReasonData,function(index,row){
    		if(row.isDefault == 'Y'){
    			fosterReasonDefaultValue = row.codeValue;
    			fosterReasonDefaultName = row.codeName;
    		}
    	});
    }  
});

var detailDefaultValues = {
		status:'1',
		deletedFlag:'0',
		//设置默认值
		fosterReason:fosterReasonDefaultValue,
		fosterReasonName:fosterReasonDefaultName
};
$(document).ready(function(){
	$('#listTable').datagrid(
			j_eventGrid({
			dbClickEdit:true,
			columns:[[
			          	{field:'ck',checkbox:true},
		              	j_gridText({field:'rowId',title:'ID',hidden:true}),
		              	j_gridText({field:'pigId',title:'猪只ID',hidden:true}),
		              	j_gridText({field:'houseId',title:'猪舍ID',hidden:true}),
		              	j_gridText({field:'minValidDate',title:'minValidDate',hidden:true}),
		              	j_gridText({field:'maxValidDate',title:'maxValidDate',hidden:true}),
		              	j_gridText({field:'deletedFlag',title:'deletedFlag',hidden:true}),
		              	//耳牌号
		              	j_gridText(earBrandTextBox({width:100})),
		              	j_gridText({field:'pigInfo',title:'耳缺号    -  品种   -  胎次   -  状态(天)  -  日龄  - 猪舍  - 猪栏',width:500}),
			          	j_gridText({field:'fosterReason',title:'寄养原因',width:80,
		              		formatter:function(value,row){
		              			return row.fosterReasonName;
		              		},
		              		editor:
		              		xnGridCdComboBox({
		              			field:'fosterReason',
		              			data:fosterReasonData,
		              			onChange:fosterReasonChangeFun
		              		})
		              	}),
		              	j_gridText({field:'pigQty',title:'带仔数',width:80}),
		              	j_gridNumber({field:'fosterQty',title:'寄养数量',width:80,min:0,onChange:fosterQtyChang}),
		              	j_gridNumber({field:'fosterWeight',title:'寄养总重',min:1,max:50,
		              		precision:2,
							increment:0.1,
		              		formatter:function(value,row){
		              			value = value == null || value == undefined || value == '' ? 0.00 : value;
		              			row.fosterWeight = value;
		              			return value+'KG';
			              	},
			              	width:80}),
		              	j_gridText({field:'enterDate',title:'寄养日期',width:80,
		              		editor:{
		              			type:'datebox',
		              			options:{
		              				editable:false,
		              				onChange:enterDateChangeFun
		              			}
		              		}
		              	}),
		              	j_gridText(boardSowGridComboGrid({width:100})),
		              	//技术员
		              	j_gridText(workerGridComboGrid({width:80})),
		              	j_gridText({field:'notes',title:'备注',width:80,editor:'textbox'})
				    ]],
		    onEndEdit:function(index,row){
		    	var fosterReason = $('#listTable').datagrid('getEditor', {
	                index: index,
	                field: 'fosterReason'
	            });
		    	if(fosterReason != null){
		    		row.fosterReasonName = $(fosterReason.target).combogrid('getText');
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
		    	var boardSow = $('#listTable').datagrid('getEditor', {
			        index: index,
			        field: 'boardSowId'
			    });
				if(boardSow != null){
					var rows = $('#listTable').datagrid('getRows');
		    		$.ajax({  
				        url : basicUrl+'/production/searchValidPigToPage.do', 
				        data:{
				        	queryType:1,
			            	pigType:2,
			            	specifyPigs:0,
			            	choice:1,
			            	eventName:'BOAR_FOSTER',
			            	date1:rows[index].enterDate,
			            	pigIds:rows[index].pigId,
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
				columnFields:'pigId,earBrand,pigInfo,fosterReasonName,pigQty,fosterQty,fosterWeight,enterDate,boardSowEarBrand,workerName,notes',
				columnTitles:'猪只ID,耳牌号,耳缺号    -  品种   -  胎次   -  状态(天)  -  日龄  - 猪舍  - 猪栏,寄养原因,带仔数,寄养数量,寄养总重(KG),寄养日期,代养母猪,技术员,备注',
				hiddenFields:'pigId,notes,workerName',
				columnWidths:'100,100,350,100,80,90,110,90,100,100,90',
				fit:false,
				width:'auto',
				height:'100%',
				pagination:false
			},'preSaveRecordTable')
	);
});
/**
 * 寄养日期改变方法
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
 * 
 * @param newValue
 * @param oldValue
 */
function fosterReasonChangeFun(newValue,oldValue){
	if(oldValue == ''){
		return;
	}
	//母猪死亡
	if(newValue == 2){
		var rows = $('#listTable').datagrid('getRows');
		rows[editIndex].fosterQty = rows[editIndex].pigQty;
		changeTableDisplayValue('listTable',editIndex,[{
			field:'fosterQty',
			value:rows[editIndex].fosterQty
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
function fosterQtyChang(newValue,oldValue){
	if(editIndex == undefined){
		return;
	}
	var rows = $('#listTable').datagrid('getRows');
	var fosterQtySum = 0;
	if(newValue != ''){
		fosterQtySum = strToInt(newValue);
	}
	$.each(rows,function(i,item){
		if( i != editIndex && item.fosterQty != undefined && item.fosterQty != ''){
			fosterQtySum += strToInt(item.fosterQty);
		}
	})
	$('#fosterQtySum').html(fosterQtySum);
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
						$('#fosterQtySum').html('0');
					}else{
						var fosterQtySum = 0;
						$.each(listRows,function(i,row){
							fosterQtySum += strToInt(row.fosterQty == undefined ? 0 : row.fosterQty);
						});
						$('#fosterQtySum').html(fosterQtySum);
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
			$('#fosterQtySum').html('0');
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
				$('#fosterQtySum').html('0');
			}
		}
	});
}