var searchSelectMaterialFeedListTableUrl = '/supplyChian/searchMaterialFeedFromInputingToPage.do';
$(document).ready(function(){
	$('#selectMaterialFeedListTable').datagrid(
			j_grid_view({
				fit:false,
				toolbarList:"#sMFLTToolbarList",
				height:'100%',
				width:'100%',
//				url:searchSelectMaterialFeedListTableUrl,
				columnFields:'requireFarmSortName,materialName,materialFirstClassName,materialSecondClassName,specAll,inputQtyName,supplierSortName,manufacturer,notes',
				columnTitles:'入库公司,物料名称,大类,中类,规格,入库数量,供应商,厂家,备注',
				hiddenFields:'',
			})
	);
})

function selectMaterialFeedSure(){
	var selectRows = $('#selectMaterialFeedListTable').datagrid('getSelections');
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
			
			row.notBillIds = data.notBillIds;
			row.rowIds = data.rowIds;
			row.requireFarm = data.requireFarm;
			row.requireFarmSortName = data.requireFarmSortName;
			row.requireFarmName = data.requireFarmName;
			row.materialId = data.materialId;
			row.materialName = data.materialName;
			row.materialSecondClassName = data.materialSecondClassName,
			row.materialFirstClassName = data.materialFirstClassName,
			row.specAll = data.specAll;
			row.spec = data.spec;
			row.specNum = data.specNum;
			row.requireQty = data.inputQty;
			row.actualPrice = data.price;
			row.actualFreePercent = data.freePercent;
			row.unit = data.unit;
			row.groupId = data.groupId;
			row.outputMinQty = data.outputMinQty;
			row.purchaseQty = data.inputQty;
			row.materialOnlys = data.materialOnlys;
			row.totalPrice = (strToFloat(row.purchaseQty)*strToFloat(row.actualPrice)).toFixed(2);
			row.transportId = data.transportId;
			row.relatedId = data.relatedId;
//			var orginFreePercent = data.freePercent;
//			var requiredQtyFloat = strToFloat(data.requiredQty);
//			var specNumFloat = strToFloat(data.specNum);
//			var outputMinQtyFloat = strToFloat(data.outputMinQty);
//			var maxQty;
//			var minQty;
			
//			if(orginFreePercent.indexOf('%') > 0){
//				var freePercent = orginFreePercent.substring(0,orginFreePercent.indexOf('%'));
//				var freePercentInt = strToInt(freePercent)/100
//
//				var max = Math.ceil(requiredQtyFloat/(specNumFloat*(1+freePercentInt*outputMinQtyFloat)));
//				var min = max-1;
//				
//				maxQty = max*specNumFloat;
//				minQty = min*specNumFloat;
//			}else if(orginFreePercent.indexOf('/') > 0){
//				var freePercentMin = strToInt(orginFreePercent.substring(orginFreePercent.indexOf('/')+1,orginFreePercent.length+1));
//				var freePercentMax = strToInt(orginFreePercent.substring(0,orginFreePercent.indexOf('/')+1));
//				
//				var max = Math.ceil(requiredQtyFloat/(freePercentMax+freePercentMin));
//				var min = max-1;
//				
//				maxQty = max*freePercentMax;
//				minQty = min*freePercentMax;
//			}
//			
//			var data = [];
//			if(minQty<=0){
//				data[0]={value:maxQty};
//			}else{
//				data[0]={value:minQty};
//				data[1]={value:maxQty};
//			}
//			row.purchaseQtyData = data;
			
			$('#listTable').datagrid('appendRow',row);
		});
		
		var totalPriceSum = 0;
		var rows = $('#listTable').datagrid('getRows');
		$.each(rows,function(i,item){
			if( item.totalPrice != undefined){
				totalPriceSum += strToFloat(item.totalPrice);
			}
		})
		$('#totalPrice').html(totalPriceSum.toFixed(2)+'元');
	}
}

function selectMaterialFeedReset(){
	$('#selectMaterialFeedListTable').datagrid('reload');
}

function startDateChange(newDate, oldDate){
	var endDate = $('#endDate').datebox('getValue');
	var supplierId = $('#supplierId').combobox('getValue');
	$('#selectMaterialFeedListTable').datagrid('load',basicUrl+searchSelectMaterialFeedListTableUrl+'?startDate='+newDate+'&endDate='+endDate+'&supplierId='+supplierId);
}

function endDateChange(newDate, oldDate){
	var startDate = $('#startDate').datebox('getValue');
	var supplierId = $('#supplierId').combobox('getValue');
	$('#selectMaterialFeedListTable').datagrid('load',basicUrl+searchSelectMaterialFeedListTableUrl+'?startDate='+startDate+'&endDate='+newDate+'&supplierId='+supplierId);
}