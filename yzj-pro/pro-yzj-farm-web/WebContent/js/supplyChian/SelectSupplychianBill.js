var searchSelectSupplychianBillListTableUrl = '/supplyChian/searchSupplychianBillToPage.do';
$(document).ready(function(){
	$('#supplychianBillListTable').datagrid(
			j_grid_view({
				singleSelect:true,
				fit:false,
				height:'80%',
				width:'100%',
//				url:searchSelectMaterialListTableUrl,
				columnFields:'rowId,billCode,eventCodeName,billDate,userName,outputFarmIdName,outputerName,notes',
				columnTitles:'ID,出库单据编号,出库类型,出库日期,领用人/报废人/负责人,猪场,出库人,备注',
//				columnWidths:'100,100,100,100,160,100,100',
				hiddenFields:'rowId',
			})
	);
	
	// 供应链单据事件
	xnCdCombobox({
		id:'supplychianEventCode',
		readonly:true,
		typeCode:'SUPPLYCHIAN_EVENT_CODE',
		onChange:supplychianEventCodeChange
	});
})

function selectSupplychianBillSure(){
	var selectRows = $('#supplychianBillListTable').datagrid('getSelections');
	if(selectRows.length < 1){
		$.messager.alert('警告', '请选择一条以上的数据！');
	}else if(selectRows.length > 1){
		$.messager.alert('警告','只能选择一条数据！');
	}else{
		var eventCode = $('#supplychianEventCode').combobox('getValue');
		$.each(selectRows,function(index,data){
			if(eventCode=="OUTPUT_USE"){
				$('#outputUseBillCode').html(data.billCode);
				$('#outputUseBillDate').html(data.billDate);
				$('#outputUseUserName').html(data.userName);
				$('#outputUseOutputerName').html(data.outputerName);
				$('#useId').val(data.rowId);
				$('#outputFarmId').val(data.outputFarmId);
			}

			if(eventCode=="OUTPUT_SCRAP"){
				$('#outputScrapBillCode').html(data.billCode);
				$('#outputScrapBillDate').html(data.billDate);
				$('#outputScrapOutputerName').html(data.outputerName);
				$('#scrapId').val(data.rowId);
			}
			
			if(eventCode=="OUTPUT_ALLOT"){
				$('#outputAllotBillCode').html(data.billCode);
				$('#outputAllotBillDate').html(data.billDate);
				$('#outputAllotOutputerName').html(data.userName);
				$('#allotId').val(data.rowId);
			}
			
			$('#listTable').datagrid('load',basicUrl + prUrl + 'searchSupplychianBillDetailToList.do?eventCode='+eventCode+'&outputId='+data.rowId +'&dafengModel='+$('#dafengModel').val());
		});
		var filterId = selectRows[0].rowId;
		$('#supplychianBillListTable').datagrid('reload', {filterId:filterId});
	}
}

function selectSupplychianBillReset(){
	var eventCode = $('#supplychianEventCode').combobox('getValue');
	var filterId = '';
	if(eventCode=="OUTPUT_USE"){
		filterId = $('#useId').val();
	}
	if(eventCode=="OUTPUT_SCRAP"){
		filterId = $('#scrapId').val();
	}
	if(eventCode=="OUTPUT_ALLOT"){
		filterId = $('#allotId').val();
	}
	
	$('#supplychianBillListTable').datagrid('reload', {filterId:filterId});
}

function supplychianEventCodeChange(newValue,oldValue){
	$('#supplychianBillListTable').datagrid('load',basicUrl + searchSelectSupplychianBillListTableUrl+'?supplychianEventCode='+ newValue +'&dafengModel='+$('#dafengModel').val());
}