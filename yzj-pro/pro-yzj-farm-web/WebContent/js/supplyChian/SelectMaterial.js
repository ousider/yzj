var searchSelectMaterialListTableUrl = '/supplyChian/searchSelectMaterialListTableToPage.do';
$(document).ready(function(){
	$('#selectMaterialListTable').datagrid(
			j_grid_view({
				fit:false,
				height:'70%',
				width:'100%',
//				url:searchSelectMaterialListTableUrl,
				columnFields:'rowId,materialName,materialFirstClassName,materialSecondClassName,specAll,supplierSortName,manufacturer,materialNotes',
				columnTitles:'ID,物料,大类,中类,规格,供应商,厂家,备注',
				columnWidths:'50,200,80,80,80,80,80,80',
				hiddenFields:'rowId',
			})
	);
	
	// 供应商
	xnCombobox({
		id:"supplier",
		url:'/supplyChian/searchCompanyFromRequireBillToList.do?searchType=All&dafengModel='+$('#dafengModel').val(),
		valueField:"supplierId",
		textField:"supplierSortName",
		hasAll:true,
		onChange:searchMaterialBySupplierToPage
	});
	
	// 物料大类
	xnCdCombobox({ 
		id:'showMaterialFirstClass',
		typeCode:'MATERIAL_FIRST_CLASS',
		editable:false,
		hasAll:true,
		includeValue:['21','22','30','40','50','90'],
	    linkField:'showMaterialSecondClass',
	    linkCode:'MATERIAL_SECOND_CLASS',
		onChange:searchMaterialBySupplierByMaterialFirstClassToPage
	});
	
	// 物料中类
	xnCdCombobox({ 
		id:'showMaterialSecondClass',
		editable:false,
		hasAll:true,
		onChange:searchMaterialBySupplierByMaterialSecondClassToPage
	});
	
	//物料名称
	$('#showMaterialName').searchbox({
		searcher:function(value){
			
			var supplierId = $('#supplier').combobox('getValue');
			var materialFirstClass = $('#showMaterialFirstClass').combobox('getValue');
			var materialSecondClass = $('#showMaterialSecondClass').combobox('getValue');
			
			searchMaterialByConditionToPage(supplierId,materialFirstClass,materialSecondClass,value);
	    }
	})
})

/*===条件查询START===*/
function searchMaterialBySupplierToPage(newValue,oldValue){
	if(!$('#supplier').combobox('isValid')){
		return;
	}
	
	var materialFirstClass = $('#showMaterialFirstClass').combobox('getValue');
	var materialSecondClass = $('#showMaterialSecondClass').combobox('getValue');
	
	searchMaterialByConditionToPage(newValue,materialFirstClass,materialSecondClass,'');
}

function searchMaterialBySupplierByMaterialFirstClassToPage(newValue,oldValue){
	$('#showMaterialName').searchbox('setValue','');

	var supplierId = $('#supplier').combobox('getValue');
	var materialSecondClass = $('#showMaterialSecondClass').combobox('getValue');

	searchMaterialByConditionToPage(supplierId,newValue,materialSecondClass,'');
}

function searchMaterialBySupplierByMaterialSecondClassToPage(newValue,oldValue){
	$('#showMaterialName').searchbox('setValue','');
	
	var supplierId = $('#supplier').combobox('getValue');
	var materialFirstClass = $('#showMaterialFirstClass').combobox('getValue');

	searchMaterialByConditionToPage(supplierId,materialFirstClass,newValue,'');
}

function searchMaterialByConditionToPage(supplierId,materialFirstClass,materialSecondClass,materialName){
	var searchUrl = basicUrl + searchSelectMaterialListTableUrl+'?dafengModel='+$('#dafengModel').val();
	var searchCondition = [];
	if(supplierId){
		searchCondition.push('supplierId='+supplierId);
	}
	if(materialFirstClass){
		searchCondition.push('materialFirstClass='+materialFirstClass);
	}
	if(materialSecondClass){
		searchCondition.push('materialSecondClass='+materialSecondClass);
	}
	if(materialName){
		searchCondition.push('materialName='+materialName);
	}
	if($('#applyType').length > 0){
		searchCondition.push('applyType='+$('#applyType').combobox('getValue'));
	}
	if($('#eventCode').length > 0){
		if($('#eventCode').hasClass('combobox-f')){
			searchCondition.push('eventCode='+$('#eventCode').combobox('getValue'));
		}
	}
	if($('#isPurchase').length > 0){
		searchCondition.push('isPurchase='+$('#isPurchase').val());
	}
	
	$.each(searchCondition,function(index,data){
		searchUrl = searchUrl + '&' + data;
	});
	
	$('#selectMaterialListTable').datagrid('load', encodeURI(searchUrl));
}

/*===条件查询END===*/

function selectMaterialSure(){
	var selectRows = $('#selectMaterialListTable').datagrid('getSelections');
	if(selectRows.length < 1){
		$.messager.alert('警告', '请选择一条以上的数据！');
	}else{
		$.each(selectRows,function(index,data){
			var row = new Object(); 
			if(typeof(detailDefaultValues) == "undefined"){
				row ={
					status:'1',
					deletedFlag:'0'	
				};
			}else{
				for(var p in detailDefaultValues) { 
					var name=p;//属性名称 
					var value=detailDefaultValues[p];//属性对应的值 
					row[name]=detailDefaultValues[p]; 
				} 
			}
			
			if(typeof(searchFixedGroup) != 'undefined'){
				if(searchFixedGroup){
					var addFlag = true;
					if(searchFixedGroup){
						var listTableRows = $('#listTable').datagrid('getRows');
						$.each(listTableRows,function(index,listTableDetail){
							if(listTableDetail.materialId == data.materialId){
								addFlag=false;
							}
						});
					}
//					
					if(addFlag){
						// 搜索固定套餐
						var groupData = jAjax.submit('/supplyChian/searchFixedGroupMaterialToList.do?relatedId='+data.relatedId);
						if(groupData.length!=0){
							$.each(groupData,function(index,groupDataDetail){
								var groupRow = new Object(); 
								if(typeof(detailDefaultValues) == "undefined"){
									groupRow ={
										status:'1',
										deletedFlag:'0'	
									};
								}else{
									for(var p in detailDefaultValues) { 
										var name=p;//属性名称 
										var value=detailDefaultValues[p];//属性对应的值 
										groupRow[name]=detailDefaultValues[p]; 
									} 
								}
								
								listTableAppendRow(groupRow,groupDataDetail,true)
							});
						}else{
							listTableAppendRow(row,data,false);
						}
					}
				}else{
					listTableAppendRow(row,data,false);
				}
			}else{
				listTableAppendRow(row,data,false);
			}
		});
		
		if(typeof(filterMaterialFlag) != 'undefined'){
			if(filterMaterialFlag){
				var rows = $('#listTable').datagrid('getRows');
				var filterIds = [];
				$.each(rows, function(i,data){
					filterIds.push(data.materialId);
				});
				$('#selectMaterialListTable').datagrid('reload',{filterIds:filterIds.join(",")});
			}else{
				$('#selectMaterialListTable').datagrid('reload');
			}
		}else{
			$('#selectMaterialListTable').datagrid('reload');
		}
	}
}

function selectMaterialReset(){
	$('#materialFirstClass').combobox('setValue','')
	$('#materialSecondClass').combobox('setValue','')
	$('#selectMaterialListTable').datagrid('reload');
}

function listTableAppendRow(row,data,groupFlag){
	var addFlag = true;
	if(typeof(searchFixedGroup) != 'undefined'){
		if(searchFixedGroup){
			var listTableRows = $('#listTable').datagrid('getRows');
			$.each(listTableRows,function(index,listTableDetail){
				if(listTableDetail.materialId == data.materialId){
					addFlag=false;
				}
			});
		}
	}
	if(addFlag){
		row.materialId = data.materialId;
		row.materialName = data.materialName;
		row.materialFirstClassName = data.materialFirstClassName;
		row.materialSecondClassName = data.materialSecondClassName;
		row.supplierId = data.supplierId;
		row.supplierSortName = data.supplierSortName;
		row.supplierName = data.supplierName;
		row.manufacturer = data.manufacturer;
		row.specAll = data.specAll;
		row.spec = data.spec;
		row.specNum = data.specNum;
		row.unit = data.unit;
		row.freePercent = data.freePercent;
		row.actualFreePercent = data.freePercent;
		row.actualPrice = data.price;
		row.quantity = data.quantity;
		row.existsQty = data.existsQty;
		row.outputMinQty = data.outputMinQty;
		row.warehouseId = data.defaultWarehouse;
		row.inputWarehouseName = data.defaultWarehouseName;
		row.relatedId = data.relatedId;
		if(groupFlag){
			row.mainRelatedId = data.mainRelatedId;
			row.mainProportionQty = data.mainProportionQty;
		}
		row.materialFirstClassName = data.materialFirstClassName;
		row.materialSecondClassName = data.materialSecondClassName;
		$('#listTable').datagrid('appendRow',row);
	}
}
