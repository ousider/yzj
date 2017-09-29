var searchSelectRequireMaterialListTableUrl = '/supplyChian/searchMaterialFromRequireTableToPage.do';
$(document).ready(function(){
	$('#selectRequiredMaterialListTable').datagrid(
			j_grid_view({
				fit:false,
				toolbarList:"#sRMLTToolbarList",
				height:'100%',
				width:'100%',
//				url:searchSelectRequireMaterialListTableUrl,
				columnFields:'requireFarmSortName,requireTypeName,materialName,materialFirstClassName,materialSecondClassName,specAll,requireQtyUnit,supplierSortName,manufacturer',
				columnTitles:'需求公司,类型,物料,大类,中类,规格,需求量,供应商,厂家',
				columnWidths:'80,50,180,60,80,80,80,80,80',
				hiddenFields:'',
				onDblClickRowFun:selectRequiredMaterialDetail
			})
	);
	
	$('#selectRequiredMaterialDetailListTable').datagrid(
			j_grid_view({
				haveCheckBox:false,
				columnFields:'materialName,emergencyTypeName,requireQtyUnit,notes,billCode,billDate',
				columnTitles:'物料名称,紧急程度,需求数量,备注,需求单据号,需求日期',
				hiddenFields:'billCode,billDate',
				fit:false,
				pagination:false,
				height:'100%',
				width:'100%'
			},'selectRequiredMaterialDetailListTable'));
})

// 查看需求单明细和备注
function selectRequiredMaterialDetail(index,row){
	$('#selectRequiredMaterialDetailListTable').datagrid('load',basicUrl + '/supplyChian/searchMaterialFromRequireTableDetailToList.do?rowIds='+row.requireIds+'&materialId='+row.materialId);
	leftSilpFun('selectRequiredMaterialDetailId');
}

function rightSlipRequireAll(){
	rightSlipFun('selectRequiredMaterialWin',780, true);
	rightSlipFun('selectRequiredMaterialDetailId',390);
}

function selectRequiredMaterialSure(){
	var selectRows = $('#selectRequiredMaterialListTable').datagrid('getSelections');
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
			row.rowIds = data.rowIds
			row.requireIds = data.requireIds
			row.requireQtys = data.requireQtys
			row.billCode = data.billCode;
			row.requireFarm = data.requireFarm;
			row.requireFarmSortName = data.requireFarmSortName;
			row.requireFarmName = data.requireFarmName;
			row.materialId = data.materialId;
			row.materialName = data.materialName;
			row.materialFirstClassName = data.materialFirstClassName,
			row.materialSecondClassName = data.materialSecondClassName,
			row.specAll = data.specAll;
			row.spec = data.spec;
			row.specNum = data.specNum;
			row.requireQty = data.requireQty;
			row.actualPrice = data.price;
			row.actualFreePercent = data.freePercent;
			row.unit = data.unit;
			row.groupId = data.groupId;
			row.outputMinQty = data.outputMinQty;
			row.purchaseQty = data.requireQty;
			row.relatedId = data.relatedId;
			row.notes = data.requireDetailNotes;
			row.totalPrice = (strToFloat(row.purchaseQty)*strToFloat(row.actualPrice)).toFixed(2);
			var orginFreePercent = data.freePercent;
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
		var filterIds = [];
		$.each(rows,function(i,item){
			if(typeof(item.rowIds) != 'undefined'){
				var rowIds = JSON.parse(item.rowIds);
				$.each(rowIds,function(j,rowIdsItem){
					filterIds.push(rowIdsItem.rowId);
				});
			}
			if(item.totalPrice != undefined){
				totalPriceSum += strToFloat(item.totalPrice);
			}
		});
		$('#totalPrice').html(totalPriceSum.toFixed(2)+'元');
		
		$('#selectRequiredMaterialListTable').datagrid('reload', {filterIds:filterIds.join(",")});
	}
}

function selectRequiredMaterialReset(){
	var rows = $('#listTable').datagrid('getRows');
	var filterIds = [];
	$.each(rows,function(i,item){
		if(typeof(item.rowIds) != 'undefined'){
			var rowIds = JSON.parse(item.rowIds);
			$.each(rowIds,function(j,rowIdsItem){
				filterIds.push(rowIdsItem.rowId);
			});
		}
	});
	$('#selectRequiredMaterialListTable').datagrid('reload', {filterIds:filterIds.join(",")});
}
//单据拆分
function onBtnSplit(){
	var record = $('#selectRequiredMaterialListTable').datagrid('getSelections');
	var length = record.length;
	if(length < 1){
		$.messager.alert('警告','请选择一条数据！');
	}else if(length > 1){
		$.messager.alert('警告','只能选择一条数据！');
	}else{
		$('#splitMaterialWin').dialog({
			modal:true
		});
		$('#splitMaterialWin').dialog('open');
		$('#splitMaterialForm').form('reset');
		$('#requireQty').val(record[0].requireQty);
	}
}
//取消拆分
function splitMaterialCancel(){
	$('#splitMaterialWin').dialog('close');
}
//确定拆分
function splitMaterialSure(){
	var isValid = $('#splitMaterialForm').form('validate');
	if(isValid){
		var requireQty = strToFloat($('#requireQty').val());
		var requireQtyNew = strToFloat($('#requireQtyNew').numberspinner('getValue'));
		if(requireQtyNew >= requireQty){
			$.messager.alert('警告','拆分后的需求量大于原需求量，请重新填写！');
		}else if(requireQtyNew <= 0){
			$.messager.alert('警告','拆分后的需求量不能小于等于零，请重新填写！');
		}else{
			//拆分方法
			var row = $('#selectRequiredMaterialListTable').datagrid('getSelections')[0];
			jAjax.submit('/supplyChian/editSplitMaterial.do?editType=require',{data:row.rowIds,splitQty:requireQtyNew}
			,function(response){
				$('#splitMaterialWin').dialog('close');
				$('#selectRequiredMaterialListTable').datagrid('reload');
				$.messager.progress('close');	
			},function(response){
	    		jAjax.errorFunc(response.errorMsg);
	    		$.messager.progress('close');
	    	},null,true,null,null);
		}
	}
}

//单据合并
function onBtnMerge(){
	var record = $('#selectRequiredMaterialListTable').datagrid('getSelections');
	var length = record.length;
	if(length != 2){
		$.messager.alert('警告','请选择两条数据！');
	}else{
		if(record[0].materialId != record[1].materialId){
			$.messager.alert('警告','选择的单据需求公司和物料不一致，请重新选择！');
		}else{
			var firstData = record[0].rowIds;
			var secondData = record[1].rowIds;
			jAjax.submit('/supplyChian/editMergeMaterial.do?editType=require',{firstData:firstData,secondData:secondData}
			,function(response){
				$('#selectRequiredMaterialListTable').datagrid('reload');
				$.messager.progress('close');	
			},function(response){
	    		jAjax.errorFunc(response.errorMsg);
	    		$.messager.progress('close');
	    	},null,true,null,null);
		}
	}
}