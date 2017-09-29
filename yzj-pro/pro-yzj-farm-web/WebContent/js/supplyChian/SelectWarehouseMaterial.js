var selectWarehouseMaterialUrl = '';
$(document).ready(function(){
	$('#selectWarehouseMaterialListTable').datagrid(
			j_grid_view({
				fit:false,
				height:'80%',
				width:'100%',
//				url:selectWarehouseMaterialUrl,
				columnFields:'rowId,materialName,materialSecondClassName,supplierSortName,manufacturer,specAll,outputMinQtyName,existsQtyName',
				columnTitles:'ID,物料名称,中类,供应商,厂家,规格,最小领用数量,库存量',
				hiddenFields:'rowId',
			})
	);
	
	//出库仓库
	xnCombobox({
		id:'outputWarehouseId',
		valueField:'rowId',
		textField:'warehouseName',
		hasAll:true,
		url:'/supplyChian/searchWarehouseByMaterialTypeToList.do?dafengModel='+$('#dafengModel').val(),
		onChange:outputWarehouseChange
	});
	
	//物料名称
	$('#showWarehouseMaterialName').searchbox({
		searcher:function(value){
			var outputWarehouseId = $('#outputWarehouseId').combobox('getValue');
			if($('#supplychianEventCode').val()!='OUTPUT_USE'){
				var rows = $('#listTable').datagrid('getRows');
				var filterIds = [];
				$.each(rows, function(i,data){
					filterIds.push(data.rowId);
				});
				$('#selectWarehouseMaterialListTable').datagrid('reload',{warehouseId:outputWarehouseId,filterIds:filterIds.join(","),materialName:value});
			}else{
				var filterMaterialIds = [];
				$('#selectWarehouseMaterialListTable').datagrid('reload',{warehouseId:outputWarehouseId,filterMaterialIds:filterMaterialIds.join(","),materialName:value});
			}
	    }
	})
})

function outputWarehouseChange(newValue,oldValue){
	if($('#supplychianEventCode').val()!='OUTPUT_USE'){
		var rows = $('#listTable').datagrid('getRows');
		var filterIds = [];
		$.each(rows, function(i,data){
			filterIds.push(data.rowId);
		});
		$('#selectWarehouseMaterialListTable').datagrid('reload',{warehouseId:newValue,filterIds:filterIds.join(",")});
	}else{
		var filterMaterialIds = [];
		$('#selectWarehouseMaterialListTable').datagrid('reload',{warehouseId:newValue,filterMaterialIds:filterMaterialIds.join(",")});
	}
}
function selectWarehouseMaterialSure(){
	var selectRows = $('#selectWarehouseMaterialListTable').datagrid('getSelections');
	if(selectRows.length < 1){
		$.messager.alert('警告', '请选择一条以上的数据！');
	}else{
		var materialIds = [];
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
			
			if($('#supplychianEventCode').val()=='OUTPUT_USE'){
				// 搜索固定套餐
//				var groupData = jAjax.submit("/supplyChian/searchFixedUseMaterialToList.do?eventName="+$('#supplychianEventCode').val()+'&dafengModel='+$('#dafengModel').val()+'&warehouseId='+$('#outputWarehouseId').combobox('getValue')+'&materialId='+data.materialId);
//				if(groupData.length!=0){
//					$.each(groupData,function(index,groupDataDetail){
//						var groupRow = new Object(); 
//						if(typeof(detailDefaultValues) == "undefined"){
//							groupRow ={
//								status:'1',
//								deletedFlag:'0'	
//							};
//						}else{
//							for(var p in detailDefaultValues) { 
//								var name=p;//属性名称 
//								var value=detailDefaultValues[p];//属性对应的值 
//								groupRow[name]=detailDefaultValues[p]; 
//							} 
//						}
//						
//						listTableAppendRow(groupRow,groupDataDetail)
//					});
//				}else{
//					listTableAppendRow(row,data);
//				}
				
				listTableAppendRow(row,data);
			}else{
				materialIds.push(data.materialId);
			}
		});
		
		var oldRows = $('#listTable').datagrid('getRows');
		$('#showWarehouseMaterialName').searchbox('setValue','');
		var oldFilterIds = [];
		$.each(oldRows, function(i,data){
			oldFilterIds.push(data.rowId);
		});
		
		var outputWarehouseId = $('#outputWarehouseId').combobox('getValue');
		if($('#supplychianEventCode').val()!='OUTPUT_USE'){
			var rows01 = jAjax.submit('/supplyChian/searchMaterialByMaterialIdToList.do?materialIds='+materialIds+'&warehouseId='+outputWarehouseId+'&filterIds='+oldFilterIds.join(","));
			var allData = $('#listTable').datagrid('getData');
			allData.rows = allData.rows.concat(rows01);
			allData.total = allData.rows.length;
			$('#listTable').datagrid('loadData', allData);
			
			var newFilterIds = [];
			$.each(allData.rows, function(i,data){
				newFilterIds.push(data.rowId);
			});
			
			$('#selectWarehouseMaterialListTable').datagrid('reload',{warehouseId:outputWarehouseId,filterIds:newFilterIds.join(",")});
		}else{
			var filterMaterialIds = [];
			$('#selectWarehouseMaterialListTable').datagrid('reload',{warehouseId:outputWarehouseId,filterMaterialIds:filterMaterialIds.join(",")});
		}
	}
}

function selectWarehouseMaterialCancel(){
	rightSlipFun('selectWarehouseMaterialWin',780,true);
}

function listTableAppendRow(row,data){
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
	$('#listTable').datagrid('appendRow',row);
}