var editIndex = undefined;
var prUrl = '/supplyChian/';
var searchMainListUrl = prUrl + 'searchOutputStoreToPage.do';
var searchDetailListUrl = prUrl + 'searchOutputBillDetailToList.do';
var saveUrl=prUrl + 'editOutputUse.do';
var deleteUrl=prUrl + '';
/** *********页面渲染开始*************************************************************************/
$(document).ready(function(){
	dafengModelConfirm();
	getDeptInfo();
	/**
	 * 主表加载
	 */
	$('#table').datagrid(
		j_grid({
			toolbar:"#tableToolbarList",
			url:searchMainListUrl + '?searchType=personal&eventCode=OUTPUT_USE',
			columnFields:'rowId,billCode,billDate,eventCodeName,statusName,userName,outputerName,notes',
			columnTitles:'ID,出库单编号,出库日期,出库类型,状态,领用人,出库员,备注',
			hiddenFields:'rowId',
			onDblClickRowFun:showOutputBillDetail
		},'table')
	);
	
	setTimeout(function(){
		//领用仓库
		xnComboGrid({
			id:'warehouseId',
			idField:'rowId',
			textField:'warehouseName',
			prompt:'改变领用仓库会清空明细',
			url:'/supplyChian/searchWarehouseByMaterialTypeToList.do?dafengModel='+$('#dafengModel').val(),
			required:true,
			columns:[[ 	
						{field:'rowId',title:'ID',width:100,hidden:true},
						{field:'warehouseName',title:'仓库名称',width:100},
				        {field:'warehouseTypeName',title:'仓库类型',width:100}
				    ]],
			onChange:warehouseChange
		});
//		//领用去向
		xnCdCombobox({
			id:'outputDestination',
			editable:false,
			typeCode:'OUTPUT_DESTINATION',
			onChange:outputDestinationChange
		});

		//领用去向部门
		jAjax.submit('/supplyChian/searchDepTree.do',null,function(data){
			$('#outputDesDeptId').combotree({
				data:data,
				panelHeight:300,
				editable:false,
				required:true,
				onChange:clearListTable
			})
		});
		
		$('#listTable').datagrid(
				j_detailGrid({
					toolbar:"#receptionListTableToolbar",
					dbClickEdit:true,
					columns:[[
				      	{field:'ck',checkbox:true},
//		              	j_gridText({field:'deptName',title:'部门',width:70,}),
		              	//猪舍
		              	j_gridText(houseGridComboGrid({
		              		width:70,
		              		plinkField:['houseProductPigQty','houseChildPigQty','houseBackUpPigQty','houseType','pigType','pigTypeName','pigQty'],
		              		pfunction:function(row,copyRow){
		              			materialPercentCount(row);
		              		},
		              		onSelect:houseSelectFun,
		              		onChange:houseChangeFun
	              		})),
	              		//批次
	              		j_gridText(swineryGridComboGrid({
		              		width:110,
		              		plinkField:['houseId','houseIdName','houseProductPigQty','houseChildPigQty','houseBackUpPigQty','houseType','pigType','pigTypeName','swineryPigQty','pigQty'],
		              		pfunction:function(row,copyRow){
		              			materialPercentCount(row);
		              		},
		              		idField:'swineryId',
		              		onSelect:swinerySelectFun
	              		})),
		              	j_gridText({field:'pigType',title:'猪只品名',width:70,
		              		pfunction:function(row,copyRow){
//		              			if(row.houseType == 6){
//		              				row.pigType = copyRow.pigType;
//		              				if(row.pigType == 1){
//		              					// 后备猪
//		              					row.pigQty = row.houseBackUpPigQty;
//		              				}else if(row.pigType == 2){
//		              					// 生产猪
//		              					row.pigQty = row.houseProductPigQty;
//		              				}else{
//		              					// 仔猪
//		              					row.pigQty = row.houseChildPigQty;
//		              				}
//		              			}else{
//		              				row.pigType = '';
//		              				row.pigTypeName = '';
//		              			}
		              			
	              				row.pigType = copyRow.pigType;
	              				if(row.pigType == 1){
	              					// 后备猪
	              					row.pigQty = row.houseBackUpPigQty;
	              				}else if(row.pigType == 2){
	              					// 生产猪
	              					row.pigQty = row.houseProductPigQty;
	              				}else{
	              					var outputDestination = $('#outputDestination').combobox('getValue');
	              					if(outputDestination == 'pigHouse'){
	              					// 仔猪
	              					row.pigQty = row.houseChildPigQty;
	              					}else if(outputDestination == 'swinery'){
		              					// 仔猪
		              					row.pigQty = row.swineryPigQty;
	              					}
	              				}
		              		},
		              		formatter:function(value,row){
		              			return row.pigTypeName;
		              		},
		              		editor:
		              		xnGridCdComboBox({
		              			field:'pigType',
		              			typeCode:'SC_PIG_TYPE',
		              			excludeValue:[3],
		              			editable:false
		              		})
		              	}),
		              	j_gridText({field:'pigQty',title:'猪只数量',width:70}),
		              	j_gridText({field:'materialId',title:'物料名称',width:200,
		              		plinkField:['materialName','materialFirstClass','specAll','unit','warehouseId','existsQty','existsQtyName','outputMinQty'],
				      		formatter:function(value,row){
			              		return row.materialIdName;
			              	},
				      		editor:xnGridComboGrid({
								field:'materialId',
			        			idField:'materialId',
			        			textField:'materialName',
			        			width:600,
			        			editable:true,
			        			columns:[[ 	
			        			            {field:'materialId',title:'物料ID',width:50,hidden:true},
									        {field:'materialName',title:'物料',width:250},
									        {field:'materialFirstClassName',title:'大类',width:60},
									        {field:'materialSecondClassName',title:'中类',width:60},
									        {field:'specAll',title:'规格',width:120},
									        {field:'existsQtyName',title:'库存数量',width:120},
									        {field:'supplierSortName',title:'供应商',width:100},
									        {field:'manufacturer',title:'生产厂家',width:100}
			        				    ]]
		              			}),
				      		}),
		              	j_gridText({field:'specAll',title:'规格',width:110}),
		              	j_gridText({field:'existsQtyName',title:'库存数量',width:80}),
		              	j_gridNumber({field:'outputQty',title:'领用数量',formatter:function(value,row){
	              			value = value == null || value == undefined || value == '' ? 0 : value;
	              			if(row.unit){
	              				return value+row.unit;
	              			}else{
	              				return value;
	              			}
		              	},min:0,precision:3,width:80,
	              		pfunction:function(row,copyRow){
	              			materialPercentCount(row);
	              		}}),
		              	j_gridText({field:'materialPercent',title:'头均耗料',width:70}),
		              	j_gridText({field:'notes',title:'备注',width:80,editor:'textbox'})
				    ]],
				    onBeginEdit:function(index,row){
				    	var materialId = $('#listTable').datagrid('getEditor', {
					        index: index,
					        field: 'materialId'
					    });
				    	if(materialId != null){
			    			$(materialId.target).combogrid('grid').datagrid('reload',basicUrl + prUrl + "searchWarehouseMaterialToList.do?eventName="+$('#supplychianEventCode').val()+'&dafengModel='+$('#dafengModel').val()+'&warehouseId='+$('#warehouseId').combobox('getValue')+'&deptId='+$('#outputDesDeptId').combotree('getValue'));
						}
				    	var houseId = $('#listTable').datagrid('getEditor', {
					         index: index,
					         field: 'houseId'
					     });
						var swineryId = $('#listTable').datagrid('getEditor', {
					        index: index,
					        field: 'swineryId'
					    });
						var outputDestination = $('#outputDestination').combobox('getValue');
						if(outputDestination == 'pigHouse'){
							if(houseId != null){
								$(houseId.target).combogrid('enable');
								var houseIdGrid = $(houseId.target).combogrid('grid');
								houseIdGrid.datagrid('reload',basicUrl+'/supplyChian/searchHouseToList.do?searchDate='+$('#billDate').textbox('getValue')+'&deptId='+$('#outputDesDeptId').combotree('getValue'));
							}
							if(swineryId != null){
								$(swineryId.target).combogrid('setValue','');
								$(swineryId.target).combogrid('disable');
							}
						}else if(outputDestination == 'swinery'){
							if(houseId != null){
								$(houseId.target).combogrid('enable');
								var houseIdGrid = $(houseId.target).combogrid('grid');
								houseIdGrid.datagrid('reload',basicUrl+'/supplyChian/searchHouseToList.do?searchDate='+$('#billDate').textbox('getValue')+'&deptId='+$('#outputDesDeptId').combotree('getValue'));
							}
							if(swineryId != null){
								$(swineryId.target).combogrid('enable');
								var swineryIdGrid = $(swineryId.target).combogrid('grid');
								var houseId = row.houseId == null || row.houseId == undefined || row.houseId == ''? 0 : row.houseId;
								swineryIdGrid.datagrid('reload',basicUrl+'/supplyChian/searchSwineryHavePigToList.do?searchDate='+$('#billDate').textbox('getValue')+'&houseId='+houseId+'&deptId='+$('#outputDesDeptId').combotree('getValue'));
							}
						}else{
							if(houseId != null){
								$(houseId.target).combogrid('setValue','');
								$(houseId.target).combogrid('disable');
							}
							if(swineryId != null){
								$(swineryId.target).combogrid('setValue','');
								$(swineryId.target).combogrid('disable');
							}
						}

			            var pigType = $('#listTable').datagrid('getEditor', {
			                index: index,
			                field: 'pigType'
			            });
			            if(pigType != null){
			            	if(outputDestination == 'pigHouse'){
			            		$(pigType.target).combobox('enable');
			            		$(pigType.target).combobox('readonly',false);
			            	}else if(outputDestination == 'swinery'){
			            		$(pigType.target).combobox('enable');
			            		$(pigType.target).combobox('setValue','3');
			            		$(pigType.target).combobox('setText','商品猪');
			            		$(pigType.target).combobox('readonly',true);
			            	}else{
			            		$(pigType.target).combobox('setValue','3');
			            		$(pigType.target).combobox('setText','');
			            		$(pigType.target).combobox('disable');
			            	}
//			            	if(row.houseType == 6){
//			            		if(outputDestination == 'pigHouse'){
//			            			$(pigType.target).combobox('enable');
//			            		}else{
//			            			$(pigType.target).combobox('setValue','');
//				            		$(pigType.target).combobox('disable');
//			            		}
//			            	}else{
//			            		$(pigType.target).combobox('setValue','');
//			            		$(pigType.target).combobox('disable');
//			            	}
//			            	$(pigType.target).combobox('readonly',true);
			            }
				    },
				    onEndEdit:function(index,row){
				    	var materialId = $('#listTable').datagrid('getEditor', {
					        index: index,
					        field: 'materialId'
					    });
				    	if(materialId != null){
				    		var data = $(materialId.target).combogrid('grid').datagrid('getSelected');
				    		if(data){
								row.materialId = data.materialId;
								row.materialName = data.materialName;
								row.materialIdName = data.materialName;
								row.materialFirstClass = data.materialFirstClass;
								row.materialFirstClassName = data.materialFirstClassName;
								row.materialSecondClass = data.materialSecondClass;
								row.materialSecondClassName = data.materialSecondClassName;
								row.specAll = data.specAll;
								row.unit = data.unit;
								row.outputMinQty = data.outputMinQty;
								row.outputMinQtyName = data.outputMinQtyName;
								row.existsQty = data.existsQty;
								row.existsQtyName = data.existsQtyName;
				    		}
						}
			            var houseId = $('#listTable').datagrid('getEditor', {
			                index: index,
			                field: 'houseId'
			            });
			            if(houseId != null){
			            	row.houseIdName = $(houseId.target).combogrid('getText');
			            	var data = $(houseId.target).combogrid('grid').datagrid('getSelected');
			            	if(data){
			            		row.houseType = data.houseType;
			            	}
			            }
			            var swineryId = $('#listTable').datagrid('getEditor', {
			                index: index,
			                field: 'swineryId'
			            });
			            if(swineryId != null){
			            	row.swineryIdName = $(swineryId.target).combogrid('getText');
			            }
						var outputDestination = $('#outputDestination').combobox('getValue');

			            var pigType = $('#listTable').datagrid('getEditor', {
			                index: index,
			                field: 'pigType'
			            });
			            if(pigType != null){
			            	row.pigTypeName = $(pigType.target).combobox('getText');
//			            	if(row.pigType == '2'){
//			            		row.pigQty = row.houseSowPigQty;
//			            	}else if(row.pigType == '3'){
//			            		row.pigQty = row.houseChildPigQty;
//			            	}
			            	if(outputDestination == 'pigHouse'){
				            	if(row.pigType == '1'){
				            		row.pigQty = row.houseBackUpPigQty;
				            	}else if(row.pigType == '2'){
				            		row.pigQty = row.houseProductPigQty;
				            	}else if(row.pigType == '3'){
				            		row.pigQty = row.houseChildPigQty;
				            	}
			            	}
			            }
				    	var outputQty = $('#listTable').datagrid('getEditor', {
			                index: index,
			                field: 'outputQty'
			            });
				    	if(outputQty != null){
				    		var outputQtyValue;
				    		if($(outputQty.target).numberspinner('getValue')){
				    			outputQtyValue = new BigDecimal($(outputQty.target).numberspinner('getValue'));
				    		}else{
				    			outputQtyValue = new BigDecimal('0');
				    		}
				    		var outputMinQty = row.outputMinQty == '' || row.outputMinQty == undefined ? new BigDecimal("1") : new BigDecimal(row.outputMinQty);
				    		if(outputQtyValue.remainder(outputMinQty) != 0){
				    			$.messager.alert('提示','第'+(index+1)+'行物料'+row.materialName+'领用数量输入不符合要求，不是最小领用量：'+outputMinQty+'的整数倍,请重新输入！');
				    			row.outputQty = 0;
				    		}
				    	}
				    	
				    	materialPercentCount(row);
				    }
			})
		);
	},500)
});

function materialPercentCount(row){
	if(row.materialFirstClass){
	    if(row.materialFirstClass == '21' || row.materialFirstClass == '22'){
		    if(row.pigQty && row.outputQty){
		    	var outputQtyBigDecimal = new BigDecimal(row.outputQty.toString());
		    	var pigQtyBigDecimal = new BigDecimal(row.pigQty.toString());
	    		row.materialPercent = outputQtyBigDecimal.divide(pigQtyBigDecimal);
		    }else{
		    	row.materialPercent = '';
		    }
	    }
	}
}
/**
 * 猪舍选择方法
 */
function houseSelectFun(index,row){
	var rows = $('#listTable').datagrid('getRows');
	rows[editIndex].houseProductPigQty = row.houseProductPigQty;
	rows[editIndex].houseChildPigQty = row.houseChildPigQty;
	rows[editIndex].houseBackUpPigQty = row.houseBackUpPigQty;
	
	var outputDestination = $('#outputDestination').combobox('getValue');
	if(outputDestination == 'pigHouse'){
//		if(row.houseProductPigQty != 0){
//			rows[editIndex].pigQty = row.houseProductPigQty;
//			rows[editIndex].pigType = '2';
//			rows[editIndex].pigTypeName = '生产猪';
//		}else if(row.houseBackUpPigQty !=0){
//			rows[editIndex].pigQty = row.houseBackUpPigQty;
//			rows[editIndex].pigType = '1';
//			rows[editIndex].pigTypeName = '后备猪';
//		}else{
			rows[editIndex].pigQty = row.pigQty;
			rows[editIndex].pigType = '';
			rows[editIndex].pigTypeName = '';
//		}
	} else if(outputDestination == 'swinery'){
		rows[editIndex].pigQty = row.houseChildPigQty;
		rows[editIndex].pigType = '3';
		rows[editIndex].pigTypeName = '商品猪';
	}else{
		rows[editIndex].pigType = '';
		rows[editIndex].pigTypeName = '';
	}
	rows[editIndex].housePigQty = row.pigQty;
	changeTableDisplayValue('listTable',editIndex,[{
		field:'pigQty',
		value:rows[editIndex].pigQty
	},{
		field:'pigType',
		value:rows[editIndex].pigTypeName
	}]);
}

/**
 * 猪舍改变方法
 */
function houseChangeFun(newValue,oldValue){
	if(oldValue == ''){
		return;
	}
	var rows = $('#listTable').datagrid('getRows');
	rows[editIndex].pigQty = '';
	rows[editIndex].swineryId = '';
	rows[editIndex].swineryIdName = '';
	changeTableDisplayValue('listTable',editIndex,[{
		field:'pigQty',
		value:rows[editIndex].pigQty
	},{
		field:'swineryId',
		value:rows[editIndex].swineryId
	}]);
}

/**
 * 批次选择方法
 */
function swinerySelectFun(index,row){
	var rows = $('#listTable').datagrid('getRows');
	rows[editIndex].pigQty = row.pigQty;
	rows[editIndex].swineryPigQty = row.pigQty;
	changeTableDisplayValue('listTable',editIndex,[{
		field:'pigQty',
		value:rows[editIndex].pigQty
	}]);
}

function warehouseChange(newValue, oldValue){
	clearListTable();
}

/**
 * 查看
 * pms{
 * 	model:模块代码
 * 	title:模块名称
 * }
 */
function onBtnView(){
	var record = $('#table').datagrid('getSelections');
	var length = record.length;
	if(length < 1){
		$.messager.alert('警告','请选择一条数据！');
	}else if(length > 1){
		$.messager.alert('警告','只能选择一条数据！');
	}else{
		var index = $('#table').datagrid('getRowIndex',record[0]);
		showOutputBillDetail(index,record[0]);
	}
}

//查看
function showOutputBillDetail(index,row){
	var columnTitles = '';
	if(row.eventCode == 'OUTPUT_USE'){
		columnTitles = '物料名称,中类,去向 ,猪舍, 批次,规格,购料/赠料,出库仓库,出库数量';
	}else{
		columnTitles = '物料名称,中类,去向 ,猪舍, 批次,规格,购料/赠料,出库仓库,领用数量';
	}

	$('#outputBillDetailTable').datagrid(
		j_grid_view({
			haveCheckBox:false,
			url:searchDetailListUrl+'?outputId='+row.rowId,
			columnFields:'materialName,materialSecondClassName,outputDestinationName,outputHoseName,outputSwineryName,specAll,purchaseOrFree,outputWarehouseName,outputQtyName',
			columnTitles:columnTitles,
			hiddenFields:'',
			fit:false,
			pagination:false,
			height:'100%',
			width:'100%'
		})
	);
	leftSilpFun('outputBillDetailId');
}

//物资领用（新增）
function materialReception(){
	$('#editWin').window({
		title:'物资领用'
	});
	$('#editWin').window('open');
	$('#editForm').form('reset');
	$('#btnSave').css('display','inline-block');
	$('#listTable').datagrid('loadData',{success:true,total: 0, rows: [] });
	$('#supplychianEventCode').val('OUTPUT_USE');
	$('#outputDesDeptId').combotree('setValue', $('#deptId').val());
	$('#outputDesDeptId').combotree('enable');
//	$('#outputDesSwineryId').parent().css('display','none');
//	$('#outputDesHouseId').parent().css('display','none');
//	$('#outputDesDeptId').parent().css('display','none');
//	$('#outputDesSwineryId').combogrid('disable');
//	$('#outputDesHouseId').combogrid('disable');
//	$('#outputDesDeptId').combotree('disable');
}
//选择仓库物料
function selectWarehouseMaterial(){
	var wareHouseId = $('#warehouseId').combobox('getValue');
	if(wareHouseId == null || wareHouseId == ''){
		$.messager.alert('提示','请选择领用仓库');
		return;
	}
	
	var outputDesDeptIdIsValid = $('#outputDesDeptId').combotree('isValid');
	if(!outputDesDeptIdIsValid){
		$.messager.alert('提示','请先选中部门！');
		return;
	}
	
	$('#outputWarehouseId').combobox('setValue',wareHouseId);
	$('#outputWarehouseId').combobox('disable');
	$('#showWarehouseMaterialName').searchbox('setValue','');
	
	$('#selectWarehouseMaterialListTable').datagrid('load',basicUrl + prUrl + "searchWarehouseMaterialToPage.do?eventName="+$('#supplychianEventCode').val()+'&dafengModel='+$('#dafengModel').val());
	leftSilpFun('selectWarehouseMaterialWin',true,9001);
}

//领用去向改变方法
function outputDestinationChange(newValue,oldValue){
	if(newValue == 'farm'){
		$('#outputDesDeptId').combotree('setValue', '');
		$('#outputDesDeptId').combotree('disable');
	}else{
		$('#outputDesDeptId').combotree('setValue', $('#deptId').val());
		$('#outputDesDeptId').combotree('enable');
	}
	clearListTable();
}
/**
 * 获取明细表提交数据数据（有一个明细表，如有多个明细表需重写）
 */
function collectDetailData(){
	var listTable = $('#listTable');
	var rows =  listTable.datagrid('getRows');
	for(var i = 0 ; i < rows.length ; i ++){
		listTable.datagrid('endEdit',i);
	}
	var queryParams;
	//明细表获取数据
	
	if(rows.length < 1){
		return null;
	}
	//获取新增行
	var insertRows = listTable.datagrid('getRows');
	
	var validData = [];
	var notSubmitIndex = '';
	$.each(insertRows,function(index,data){
		if(data.hasOwnProperty('outputQty') && data.outputQty != 0){
			validData.push(data);
		}else{
			if(notSubmitIndex.length==0){
				notSubmitIndex = index + 1;
			}else{
				notSubmitIndex = notSubmitIndex + ',' + (index + 1);
			}
		}
	})
	
	//给提交数据加上行号
	$.each(validData,function(index,data){
		validData[index].lineNumber = index+1;
	})
	var jsonString = JSON.stringify(validData);
	queryParams = {
			deletedFlag:0,
			gridList:jsonString,
			notSubmitIndex:notSubmitIndex
	};
	
	return queryParams;
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
	
	var isValid = $('#editForm').form('validate');
	if (!isValid){
		$.messager.alert('提示','必填项没有填写！');
		$('#btnSave').attr('disabled',false).removeClass('btn-disabled');
		$.messager.progress('close');	
		return;
	}
	
	//判断是否有明细表
	if(listTable!= null){
		queryParams = collectDetailData();
		if(queryParams == null){
			$.messager.alert('提示','前台提示--未添加明细不能提交！');
			return;
		}
		
		if(queryParams.gridList == '[]'){
			$.messager.alert('提示','填写数量都为零！');
			return;
		}
	}else{
		queryParams = {
			status:1,
			deletedFlag:0,
		};
	}

	if(queryParams.notSubmitIndex!=''){
		$.messager.confirm('提示', '第【'+queryParams.notSubmitIndex+'】行的数量为零，不会提交，确定吗？', function(r){
			delete queryParams.notSubmitIndex;
			if(r){
				onbtnSaveSubmit(queryParams);
			}
		});
	}else{
		delete queryParams.notSubmitIndex;
		onbtnSaveSubmit(queryParams);
	}
}

function onbtnSaveSubmit(queryParams){
	$('#btnSave').attr('disabled',true).addClass('btn-disabled');
	$.messager.progress();
	$('#editForm').form('submit',{
		url:basicUrl+saveUrl+'?dafengModel='+$('#dafengModel').val(),
		queryParams:queryParams,
		success: function(response){
			response=eval('('+response+')');
			if(response.success){
				$('#editWin').window('close');
				$.messager.alert('提示','保存成功！');
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

/**
 * 非事件新增明细行
 */
function noEventDetailAdd(){
	var warehouseIsValid = $('#warehouseId').combobox('isValid');
	if(!warehouseIsValid){
		$.messager.alert('提示','请先选中领用仓库！');
		return;
	}
	
	var outputDesDeptIdIsValid = $('#outputDesDeptId').combotree('isValid');
	if(!outputDesDeptIdIsValid){
		$.messager.alert('提示','请先选中部门！');
		return;
	}
	
	var isValid = $('#addNum').numberspinner('isValid');
	if(isValid){
		var listTable = $('#listTable');
		var allRows = listTable.datagrid('getRows');
		var length = allRows.length;
		var num = parseInt($('#addNum').val(),10);
		if(length + num > 999){
			$.messager.alert('警告','明细表数据不能超过999行！');
		}else{
//			var deptId = $('#deptId').val();
//			var deptName = $('#deptName').val();
			for(var i = 0 ; i < num ; i++){
				var defaultValues = new Object(); 
				if(typeof(detailDefaultValues) == "undefined"){
					defaultValues ={
						status:'1',
						deletedFlag:'0',
//						deptId:deptId,
//						deptName:deptName
					};
				}else{
					//复制detailDefaultValues
					for(var p in detailDefaultValues) { 
						var name=p;//属性名称 
						var value=detailDefaultValues[p];//属性对应的值 
						defaultValues[name]=detailDefaultValues[p]; 
					} 
				}
				listTable.datagrid('appendRow',defaultValues);
			}
			if (endEditing()){
				editIndex = length;
				var fistEditField = getFistEditField(listTable);
				listTable.datagrid('editCell', {index:editIndex,field:fistEditField});
				var editor = listTable.datagrid('getEditor', {index:editIndex,field:fistEditField});
				var eidtCellInput = $(editor.target[0].parentNode).find('.textbox-text.validatebox-text');
				if(eidtCellInput.length == 0){
					$(editor.target)[0].focus();
					$(editor.target).keydown(function(e){
						editCellTabDownFun(e,listTable,editIndex,fistEditField);
				    });
				}else{
					eidtCellInput[0].focus();
					eidtCellInput.keydown(function(e){
						editCellTabDownFun(e,listTable,editIndex,fistEditField);
				    });
				}
			}
		}
	}
}

function autoCountPigInfo(checkboxItem){
	var wareHouseId = $('#warehouseId').combobox('getValue');
	if(wareHouseId == null || wareHouseId == ''){
		$.messager.alert('提示','请选择领用仓库');
		$(checkboxItem).removeAttr('checked');
		return;
	}
	
	var outputDesDeptIdIsValid = $('#outputDesDeptId').combotree('isValid');
	if(!outputDesDeptIdIsValid){
		$.messager.alert('提示','请先选中部门！');
		$(checkboxItem).removeAttr('checked');
		return;
	}
	
	var checkedFlag = $(checkboxItem).is(':checked');
	if(checkedFlag){
		var outputDestination = $('#outputDestination').combobox('getValue');
		if(outputDestination == 'pigHouse'){
			var rows = jAjax.submit('/supplyChian/searchHouseHavePigToList.do?searchDate='+$('#billDate').textbox('getValue')+'&deptId='+$('#outputDesDeptId').combotree('getValue'));
			$('#listTable').datagrid('loadData',rows);
		}else if(outputDestination == 'swinery'){
			var rows = jAjax.submit('/supplyChian/searchSwineryHavePigToList.do?searchDate='+$('#billDate').textbox('getValue')+'&deptId='+$('#outputDesDeptId').combotree('getValue'));
			$('#listTable').datagrid('loadData',rows);
		}
	}else{
		$('#listTable').datagrid('loadData',{success:true,total: 0, rows: []});
	}
}

function clearListTable(){
	$("#checkboxItem").removeAttr('checked');
	$('#listTable').datagrid('loadData',{success:true,total: 0, rows: []});
}

function getDeptInfo(){
	var deptData = jAjax.submit('/supplyChian/searchDeptToList.do')[0];
	if(deptData){
		$('#deptId').val(deptData.deptId);
		$('#deptName').val(deptData.deptName);
	}
}