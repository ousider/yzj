var searchMaterialFromPurchaseUrl = '/supplyChian/searchMaterialFromPurchaseTableToPage.do';
$(document).ready(function(){
	$('#purchaseBillListTable').datagrid(
			j_grid_view({
				singleSelect:true,
				fit:false,
				height:'100%',
				width:'100%',
//				url:searchMaterialFromPurchaseUrl,
				columnFields:'rowId,billCode,billDate,eventCodeName,arriveDate,statusName,totalPriceName,purchaserName,supplierSortName,notes',
				columnTitles:'ID,采购单据编号,采购日期,采购类型,预计到库日期,状态,总价,采购员,供应商,备注',
				hiddenFields:'rowId',
			})
	);
})

function selectPurchaseBillSure(){
	var selectRows = $('#purchaseBillListTable').datagrid('getSelections');
	var searchDetailType = $('#searchDetailType').val();
	if(selectRows.length < 1){
		$.messager.alert('警告', '请选择一条以上的数据！');
	}else if(selectRows.length > 1){
		$.messager.alert('警告','只能选择一条数据！');
	}else{
		$.each(selectRows,function(index,data){
			$('#purchaseBillCode').html(data.billCode);
			$('#purchaseDate').html(data.billDate);
			$('#purchaserName').html(data.purchaserName);
			$('#supplierName').html(data.supplierSortName);
			$('#purchaseId').val(data.rowId);
			$('#purchaseEventCode').val(data.eventCode);
			$('#supplierId').val(data.supplierId);
			if(searchDetailType == 'input'){
				var columns = [
						      	{field:'ck',checkbox:true},
				              	j_gridText({field:'materialName',title:'物料',width:200}),
				              	j_gridText({field:'materialFirstClassName',title:'大类',width:60}),
				            	j_gridText({field:'materialSecondClassName',title:'中类',width:60}),
				              	j_gridText({field:'specAll',title:'规格',width:100}),
				              	j_gridText({field:'actualPrice',title:'单价',formatter:function(value,row){
			              			value = value == null || value == undefined || value == '' ? 0 : value;
				              		return value+'元';
				              	},precision:2,width:70}),
				              	j_gridText({field:'purchaseQty',title:'购买数量',formatter:function(value,row){
			              			value = value == null || value == undefined || value == '' ? 0 : value;
				              		return value+row.unit;
				              	},width:70}),
				              	j_gridText({field:'totalPrice',title:'总价',formatter:function(value,row){
			              			value = value == null || value == undefined || value == '' ? 0 : value;
				              		return value+'元';
				              	},width:70}),
				              	j_gridText({field:'arriveQty',title:'已入库数量',formatter:function(value,row){
			              			value = value == null || value == undefined || value == '' ? 0 : value;
				              		return value+row.unit;
				              	},width:80}),
			              		j_gridText({field:'warehouseId',title:'入库仓库',
			              		formatter:function(value,row){
			              			return row.inputWarehouseName;
			              		},
			              		editor:	xnGridComboGrid({
			              				field:'warehouseId',
				              			idField:'rowId',
				            			textField:'warehouseName',
				            			columns:[[ 	
				            						{field:'rowId',title:'ID',width:100,hidden:true},
				            						{field:'warehouseName',title:'仓库名称',width:100},
				            				        {field:'warehouseTypeName',title:'仓库类型',width:100}
				            				    ]]
				              		}),width:100}),
				              	j_gridNumber({field:'inputQty',title:'入库数量',formatter:function(value,row){
			              			value = value == null || value == undefined || value == '' ? 0 : value;
				              		return value+row.unit;
				              	},min:0,precision:3,width:80})
						    ];
				if(data.eventCode == 'FEED_PURCHASE'){
					columns.push(j_gridText({field:'productionDate',title:'生产日期',width:100,editor:{type:'datebox',options:{editable:false}}}));
				}else{
					columns.push(j_gridText({field:'effectiveDate',title:'有效日期',width:100,editor:{type:'datebox',options:{editable:false}}}));
				}
				columns.push(j_gridText({field:'notes',title:'备注',width:80,editor:'textbox'}));
				
				$('#listTable').datagrid(
					j_detailGrid({
						toolbar:"#materialListTableToolbar",
						dbClickEdit:true,
						columns:[columns],
					    onBeginEdit:function(index,row){
					    	var inputWarehouse = $('#listTable').datagrid('getEditor', {
				                index: index,
				                field: 'warehouseId'
				            });
							if(inputWarehouse != null){
								var inputWarehouseGrid = $(inputWarehouse.target).combogrid('grid');
								inputWarehouseGrid.datagrid('load',basicUrl+prUrl + 'searchWarehouseByMaterialTypeToList.do?materialFirstClass='+row.materialFirstClass+'&roleFilterFlag=true');
							}
					    },
					    onEndEdit:function(index,row){
					    	var inputWarehouse = $('#listTable').datagrid('getEditor', {
				                index: index,
				                field: 'warehouseId'
				            });
					    	if(inputWarehouse != null){
					    		row.inputWarehouseName = $(inputWarehouse.target).combogrid('getText');
					    	}
					    	var inputQty = $('#listTable').datagrid('getEditor', {
				                index: index,
				                field: 'inputQty'
				            });
					    	if(inputQty != null){
					    		var inputQtyValue;
					    		if($(inputQty.target).numberspinner('getValue')){
					    			inputQtyValue = new BigDecimal($(inputQty.target).numberspinner('getValue'));
					    		}else{
					    			inputQtyValue = new BigDecimal('0');
					    		}
					    		var outputMinQty = row.outputMinQty == '' || row.outputMinQty == undefined ? new BigDecimal("1") : new BigDecimal(row.outputMinQty);
					    		if(inputQtyValue.remainder(outputMinQty) != 0){
					    			$.messager.alert('提示','第'+(index+1)+'行物料'+row.materialName+'入库数量输入不符合要求，不是最小领用量的整数倍,请重新输入！');
					    			row.inputQty = 0;
					    		}
					    	}
					    }
					})
				);
				
				$('#listTable').datagrid('load',basicUrl+prUrl+'searchInputFromPurchaseDetailToList.do?purchaseId='+data.rowId+'&eventCode='+data.eventCode);
			}else{
				$('#listTable').datagrid('load',basicUrl+prUrl+'searchOutputFromPurchaseDetailToList.do?purchaseId='+data.rowId);
			}
		});
		var filterId = selectRows[0].rowId;
		$('#purchaseBillListTable').datagrid('reload', {filterId:filterId});
	}
}

function selectPurchaseBillReset(){
	var filterId = $('#purchaseId').val();
	$('#purchaseBillListTable').datagrid('reload', {filterId:filterId});
}