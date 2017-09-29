var editIndex = undefined;
var prUrl = '/supplyChian/';
var searchMainListUrl = prUrl + 'searchOutputStoreToPage.do';
var searchDetailListUrl = prUrl + 'searchOutputBillDetailToList.do';
var saveUrl=prUrl + '';
var deleteUrl=prUrl + '';
/** *********页面渲染开始*************************************************************************/
$(document).ready(function(){
	dafengModelConfirm();
	/**
	 * 主表加载
	 */
	$('#table').datagrid(
		j_grid({
			toolbar:"#tableToolbarList",
			url:searchMainListUrl+'?dafengModel='+$('#dafengModel').val(),
			columnFields:'rowId,billCode,billDate,eventCodeName,outputWarehouseName,statusName,userName,outputerName,notes',
			columnTitles:'ID,出库单编号,出库日期,出库类型,出库仓库,状态,领用人/报废人/负责人,出库员,备注',
			hiddenFields:'rowId',
			onDblClickRowFun:showOutputBillDetail
		},'table')
	);
	
	setTimeout(function(){
		// 高级查询  领用人
		xnCombobox({
			id:'advancedUserId',
			valueField:'rowId',
			textField:'employeeName',
			url:'/supplyChian/searchEmployeeByIdToList.do',
			hasAll:true
		});
		
		// 高级查询  出库人员
		xnCombobox({
			id:'advancedOutputerId',
			valueField:'rowId',
			textField:'employeeName',
			url:'/supplyChian/searchEmployeeByIdToList.do',
			hasAll:true
		});
	
		// 高级查询 出库类型
		xnCdCombobox({
			id:'advancedEventCode',
			typeCode:'SUPPLYCHIAN_EVENT_CODE',
			editable:false,
			includeValue:['OUTPUT_USE','OUTPUT_ALLOT','OUTPUT_SCRAP','OUTPUT_PURCHASE','OUTPUT_SALE'],
			hasAll:true
		});
		
//		//领用仓库
//		xnComboGrid({
//			id:'receptOutputWarehouseId',
//			idField:'rowId',
//			textField:'warehouseName',
//			url:'/supplyChian/searchWarehouseByMaterialTypeToList.do',
//			required:true,
//			columns:[[ 	
//						{field:'rowId',title:'ID',width:100,hidden:true},
//						{field:'warehouseName',title:'仓库名称',width:100},
//				        {field:'warehouseTypeName',title:'仓库类型',width:100}
//				    ]]
//		});
//		//领用去向
//		xnCdCombobox({
//			id:'outputDestination',
//			editable:false,
//			typeCode:'OUTPUT_DESTINATION',
//			onChange:outputDestinationChange
//		});
//		//领用去向猪群
//		swineryComboGrid({
//			id:'outputDesSwineryId',
//			url:'/production/searchSwineryToList.do?lineId=0',
//			required:true
//		});
//		//领用去向猪舍
//		houseComboGrid({
//			id:'outputDesHouseId',
//			url:'/basicInfo/searchHouseToList.do?lineId=0&eventName='+$('#supplychianEventCode').val(),
//			required:true
//		});
//		//领用去向部门
//		jAjax.submit('/OrganizationalStructure/searchDepTree.do',null,function(data){
//			$('#outputDesDeptId').combotree({
//				data:data,
//				panelHeight:300,
//				required:true
//			})
//		})
//		//报废人
//		xnComboGrid({
//			id:'scraperId',
//			idField:'rowId',
//			textField:'employeeName',
//			url:'/supplyChian/searchEmployeeByIdToList.do',
//			required:true,
//			columns:[[ 	
//						{field:'rowId',title:'ID',width:100,hidden:true},
//						{field:'employeeName',title:'人员名称',width:100},
//				        {field:'employeeCode',title:'人员编码',width:100}
//				    ]]
//		});
		//报废出库人员
//		xnComboGrid({
//			id:'scrapOutputerId',
//			idField:'rowId',
//			textField:'employeeName',
//			url:'/supplyChian/searchEmployeeByIdToList.do',
//			required:true,
//			columns:[[ 	
//						{field:'rowId',title:'ID',width:100,hidden:true},
//						{field:'employeeName',title:'人员名称',width:100},
//				        {field:'employeeCode',title:'人员编码',width:100}
//				    ]]
//		});
		//报废仓库
		xnComboGrid({
			id:'scrapOutputWarehouseId',
			idField:'rowId',
			textField:'warehouseName',
			url:'/supplyChian/searchWarehouseByMaterialTypeToList.do?roleFilterFlag=true',
			required:true,
			columns:[[ 	
						{field:'rowId',title:'ID',width:100,hidden:true},
						{field:'warehouseName',title:'仓库名称',width:100},
				        {field:'warehouseTypeName',title:'仓库类型',width:100}
				    ]],
			prompt:'修改仓库会清空明细',
			onChange:clearDetailChange
		});
//		//采购退货负责人
//		xnComboGrid({
//			id:'principalId',
//			idField:'rowId',
//			textField:'employeeName',
//			url:'/supplyChian/searchEmployeeByIdToList.do',
//			required:true,
//			columns:[[ 	
//						{field:'rowId',title:'ID',width:100,hidden:true},
//						{field:'employeeName',title:'人员名称',width:100},
//				        {field:'employeeCode',title:'人员编码',width:100}
//				    ]]
//		});
		
		//出库
		xnCdCombobox({
			id:'searchStatus',
			typeCode:'PURCHASE_STORE_STATUS',
			editable:false,
			multiple:true,
			defaultValue:[3,4,5]
		})
		
		//退货来源
		xnCdCombobox({
			id:'returnOrgin',
			typeCode:'RETURN_ORGIN',
			onChange:returnOrginChangeFun,
			editable:false,
			required:true
		});
		//退货仓库
		xnComboGrid({
			id:'returnOutputWarehouseId',
			idField:'rowId',
			textField:'warehouseName',
			url:'/supplyChian/searchWarehouseByMaterialTypeToList.do?roleFilterFlag=true',
			required:true,
			columns:[[ 	
						{field:'rowId',title:'ID',width:100,hidden:true},
						{field:'warehouseName',title:'仓库名称',width:100},
				        {field:'warehouseTypeName',title:'仓库类型',width:100}
				    ]],
			prompt:'修改仓库会清空明细',
			onChange:clearDetailChange
		});
		
		/**
		 * 
		 */
		//调拨员
//		xnComboGrid({
//			id:'userId',
//			idField:'rowId',
//			textField:'employeeName',
//			url:'/supplyChian/searchEmployeeByIdToList.do',
//			required:true,
//			columns:[[ 	
//						{field:'rowId',title:'ID',width:100,hidden:true},
//						{field:'employeeName',title:'人员名称',width:100},
//				        {field:'employeeCode',title:'人员编码',width:100}
//				    ]]
//		});
		//物资调拨去向
		xnCdCombobox({
			id:'allotDestination',
			typeCode:'TRANS_DESTINATION',
			editable:false,
			required:true,
			prompt:'修改去向会清空明细',
			onChange:allotDestinationChange
		});
		//调出仓库
		xnComboGrid({
			id:'allotOutputWarehouseId',
			idField:'rowId',
			textField:'warehouseName',
			url:'/supplyChian/searchWarehouseByMaterialTypeToList.do?roleFilterFlag=true',
			required:true,
			columns:[[ 	
						{field:'rowId',title:'ID',width:100,hidden:true},
						{field:'warehouseName',title:'仓库名称',width:100},
				        {field:'warehouseTypeName',title:'仓库类型',width:100}
				    ]],
			prompt:'修改仓库会清空明细',
			onChange:allotOutputWarehouseIdChange
		});
		//调入仓库
		xnComboGrid({
			id:'allotWareHouseId',
			idField:'rowId',
			textField:'warehouseName',
			url:'/supplyChian/searchWarehouseByMaterialTypeToList.do',
			required:true,
			columns:[[ 	
						{field:'rowId',title:'ID',width:100,hidden:true},
						{field:'warehouseName',title:'仓库名称',width:100},
				        {field:'warehouseTypeName',title:'仓库类型',width:100}
				    ]],
			onBeforeSelect:allotWareHouseIdOnBeforeSelect
		});

		//调拨去向猪场
		xnComboGrid({
			id:'allotFarmId',
			idField:'rowId',
			textField:'companyName',
			url:'/supplyChian/searchBrotherCompanyToList.do?haveOwnFarmFlag=false',
			required:true,
			columns:[[ 	
						{field:'rowId',title:'ID',width:100,hidden:true},
						{field:'companyName',title:'公司名称',width:100},
				    ]]
		});
	},500)
});

function allotOutputWarehouseIdChange(newValue, oldValue){
	if($('#listTable').css('display') == 'none'){
		$('#listTable').datagrid('loadData',{success:true,total: 0, rows: []});
	}
	var allotOutputWarehouseId = $('#allotOutputWarehouseId').combogrid('getValue');
	$('#allotWareHouseId').combogrid('grid').datagrid('reload',basicUrl+'/supplyChian/searchAllotWareHouseToList.do?allotOutputWarehouseId='+allotOutputWarehouseId);
}

function allotWareHouseIdOnBeforeSelect(index,row){
	var allotOutputWarehouseId = $('#allotOutputWarehouseId').combogrid('getValue');
	if(!allotOutputWarehouseId){
		$.messager.alert('提示','请先选择调出仓库！');
		return false;
	}
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
		columnFields='outputWarehouseName,outputDestinationName,outputDeptName,outputHoseName,outputSwineryName,materialName,materialSecondClassName,specAll,purchaseOrFree,outputQtyName,notes';
		columnTitles = '出库仓库,去向,部门,猪舍,批次,物料名称,中类,规格,购/赠,领用数量,备注';
	}else if(row.eventCode == 'OUTPUT_ALLOT'){
		columnFields='outputWarehouseName,outputDestinationName,materialName,materialSecondClassName,specAll,purchaseOrFree,outputQtyName,notes';
		columnTitles = '出库仓库,去向,物料名称,中类,规格,购/赠,调拨数量,备注';
	}else{
		columnFields='outputWarehouseName,materialName,materialSecondClassName,specAll,purchaseOrFree,outputQtyName,notes';
		columnTitles = '出库仓库,物料名称,中类,规格,购/赠,出库数量,备注';
	}

	$('#outputBillDetailTable').datagrid(
		j_grid_view({
			haveCheckBox:false,
			url:searchDetailListUrl+'?outputId='+row.rowId+'&dafengModel='+$('#dafengModel').val(),
			columnFields:columnFields,
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

//新增打开win
function addInput(title){
	$('#editWin').window({
		title:title
	});
	$('#editWin').window('open');
	$('#editForm').form('reset');
	$('#btnSave').css('display','inline-block');
	$('#listTable').datagrid('loadData',{success:true,total: 0, rows: [] });
}
//根据不同功能显示不同信息
function infoDisplayByType(type){
	$('#outputDesSwineryId').parent().css('display','none');
	$('#outputDesHouseId').parent().css('display','none');
	$('#outputDesDeptId').parent().css('display','none');
//	$('#outputDesSwineryId').combogrid('disable');
//	$('#outputDesHouseId').combogrid('disable');
//	$('#outputDesDeptId').combotree('disable');
	$('#allotWareHouseId').parent().css('display','none');
	$('#allotFarmId').parent().css('display','none');
	$('#allotWareHouseId').combogrid('disable');
	$('#allotFarmId').combogrid('disable');
	$('#outputDestinationLabel').css('display','none');
	$('#allotDestinationLabel').css('display','none');
	$('#returnOutputWarehouseId').parent().css('display','none');
	$('#returnOutputWarehouseIdLabel').css('display','none');
	if(type == 'reception'){
		$('#receptionInfo').css('display','block');
		$('#billDate').datebox('enable');
//		$('#userId').combogrid('enable');
		//$('#outputerId').combogrid('enable');
		$('#receptOutputWarehouseId').combogrid('enable');
		$('#outputDestination').combobox('enable');
	}else{
		$('#receptionInfo').css('display','none');
		$('#billDate').datebox('disable');
//		$('#userId').combogrid('disable');
		//$('#outputerId').combogrid('disable');
//		$('#receptOutputWarehouseId').combogrid('disable');
//		$('#outputDestination').combobox('disable');
	}
	if(type == 'scrap'){
		$('#scrapOutputInfo').css('display','block');
		$('#scrapDate').datebox('enable');
//		$('#scraperId').combogrid('enable');
		$('#scrapOutputerId').combogrid('enable');
		$('#scrapOutputWarehouseId').combogrid('enable');
	}else{
		$('#scrapOutputInfo').css('display','none');
		$('#scrapDate').datebox('disable');
//		$('#scraperId').combogrid('disable');
		$('#scrapOutputerId').combogrid('disable');
		$('#scrapOutputWarehouseId').combogrid('disable');
	}
	if(type == 'return'){
		$('#returnPurchaseInfo').css('display','block');
		$('#returnDate').datebox('enable');
//		$('#principalId').combogrid('enable');
		$('#returnOrgin').combobox('enable');
		$('#returnOutputWarehouseId').combogrid('disable');
	}else{
		$('#returnPurchaseInfo').css('display','none');
		$('#returnDate').datebox('disable');
//		$('#principalId').combogrid('disable');
		$('#returnOrgin').combobox('disable');
		$('#returnOutputWarehouseId').combogrid('disable');
	}
	if(type == 'allot'){
		$('#allotInfo').css('display','block');
		$('#allotDate').datebox('enable');
//		$('#alloterId').combogrid('enable');
		$('#allotDestination').combobox('enable');
		$('#allotOutputWarehouseId').combogrid('enable');
	}else{
		$('#allotInfo').css('display','none');
		$('#allotDate').datebox('disable');
//		$('#alloterId').combogrid('disable');
		$('#allotDestination').combobox('disable');
		$('#allotOutputWarehouseId').combogrid('disable');
	}
	
//	$('#receptionInfo').css('display','block');
//	$('#scrapOutputInfo').css('display','block');
//	$('#returnPurchaseInfo').css('display','block');
//	$('#allotInfo').css('display','block');
//	$('#outputDesSwineryId').parent().css('display','block');
//	$('#outputDesHouseId').parent().css('display','block');
//	$('#outputDesDeptId').parent().css('display','block');
//	$('#allotWareHouseId').parent().css('display','block');
//	$('#allotFarmId').parent().css('display','block');
//	$('#returnOutputWarehouseId').parent().css('display','block');
//	if(type == 'reception' || type == 'scrap' || type == 'return'){
//		$('#outputWarehouseId').combobox('enable');
//		$('#outputWarehouseId').combobox('setValue','');
//	}else{
//		$('#outputWarehouseId').combobox('disable');
//	}
}
//物资领用
function materialReception(){
	initMaterialReceptionListTable();
	addInput('物资领用');
	saveUrl=prUrl + 'editOutputUse.do';
	$('#supplychianEventCode').val('OUTPUT_USE');
	infoDisplayByType('reception');
}
//初始化物资领用明细
function initMaterialReceptionListTable(){
	if($("#returnListTableToolbar").hasClass("datagrid-toolbar")){
		$('#returnListTableToolbar').appendTo($('#toolbarDiv'));
		$("#returnListTableToolbar").removeClass("datagrid-toolbar");
	}
	$('#listTable').datagrid(
			j_detailGrid({
				toolbar:"#receptionListTableToolbar",
				dbClickEdit:true,
				columns:[[
			      	{field:'ck',checkbox:true},
	              	j_gridText({field:'materialName',title:'物料名称',width:100,sortable:false}),
	            	j_gridText({field:'materialFirstClassName',title:'大类',width:60,sortable:false}),
	            	j_gridText({field:'materialSecondClassName',title:'中类',width:60,sortable:false}),
	              	j_gridText({field:'specAll',title:'规格',width:100,sortable:false}),
	              	j_gridText({field:'warehouseName',title:'所属仓库',width:100,sortable:false}),
	              	j_gridText({field:'existsQty',title:'库存数量',formatter:function(value,row){
              			value = value == null || value == undefined || value == '' ? 0 : value;
	              		return value+row.unit;
	              	},width:80,sortable:false}),
	              	j_gridText({field:'outputMinQty',title:'最小领用数量',formatter:function(value,row){
              			value = value == null || value == undefined || value == '' ? 0 : value;
	              		return value+row.unit;
	              	},width:80,sortable:false}),
	              	j_gridNumber({field:'outputQty',title:'领用数量',formatter:function(value,row){
              			value = value == null || value == undefined || value == '' ? 0 : value;
	              		return value+row.unit;
	              	},min:0,precision:3,width:80,sortable:false}),
	              	j_gridText({field:'notes',title:'备注',width:80,editor:'textbox',sortable:false})
			    ]],
			    onEndEdit:function(index,row){
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
			    			$.messager.alert('提示','第'+(index+1)+'行物料'+row.materialName+'领用数量输入不符合要求，不是最小领用量的整数倍,请重新输入！');
			    			row.outputQty = 0;
			    		}
			    	}
			    }
		})
	);
}
//报废出库
function scrapOutput(){
	initMaterialScrapAndAllotListTable();
	addInput('报废出库');
	saveUrl=prUrl + 'editOutputScrap.do';
	$('#supplychianEventCode').val('OUTPUT_SCRAP');
	infoDisplayByType('scrap');
}
// 修改抬头值，清空明细
function clearDetailChange(newValue,oldValue){
	if($('#listTable').css('display') == 'none'){
		$('#listTable').datagrid('loadData',{success:true,total: 0, rows: []});
	}
}

//调拨
function allotOutput(){
	initMaterialScrapAndAllotListTable();
	addInput('调拨');
	saveUrl=prUrl + 'editOutputAllot.do';
	$('#supplychianEventCode').val('OUTPUT_ALLOT');
	infoDisplayByType('allot');
}
//初始化报废出库,调拨明细
function initMaterialScrapAndAllotListTable(){
	if($("#returnListTableToolbar").hasClass("datagrid-toolbar")){
		$('#returnListTableToolbar').appendTo($('#toolbarDiv'));
		$("#returnListTableToolbar").removeClass("datagrid-toolbar");
	}
	$('#listTable').datagrid(
			j_detailGrid({
				toolbar:"#receptionListTableToolbar",
				dbClickEdit:true,
				columns:[[
			      	{field:'ck',checkbox:true},
	              	j_gridText({field:'materialName',title:'物料名称',width:100,sortable:false}),
	            	j_gridText({field:'materialFirstClassName',title:'大类',width:100,sortable:false}),
	            	j_gridText({field:'materialSecondClassName',title:'中类',width:100,sortable:false}),
	              	j_gridText({field:'specAll',title:'规格',width:100,sortable:false}),
	              	j_gridText({field:'warehouseName',title:'所属仓库',width:100,sortable:false}),
	              	j_gridText({field:'existsQty',title:'库存数量',formatter:function(value,row){
              			value = value == null || value == undefined || value == '' ? 0 : value;
	              		return value+row.unit;
	              	},width:80,sortable:false}),
	              	j_gridText({field:'outputMinQty',title:'最小出库数量',formatter:function(value,row){
              			value = value == null || value == undefined || value == '' ? 0 : value;
	              		return value+row.unit;
	              	},width:80,sortable:false}),
	              	j_gridText({field:'actualPrice',title:'买入单价',formatter:function(value,row){
              			value = value == null || value == undefined || value == '' ? 0 : value;
	              		return value+'元';
	              	},width:80,sortable:false}),
	              	j_gridText({field:'productionDate',title:'生产日期',width:100,sortable:false}),
	              	j_gridText({field:'effectiveDate',title:'有效日期',width:100,sortable:false}),
	              	j_gridNumber({field:'outputQty',title:'出库数量',formatter:function(value,row){
              			value = value == null || value == undefined || value == '' ? 0 : value;
	              		return value+row.unit;
	              	},min:0,precision:3,width:80,sortable:false}),
	              	j_gridText({field:'notes',title:'备注',width:80,editor:'textbox',sortable:false})
			    ]],
			    onEndEdit:function(index,row){
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
			    			$.messager.alert('提示','第'+(index+1)+'行物料'+row.materialName+'出库数量输入不符合要求，不是最小领用量的整数倍,请重新输入！');
			    			row.outputQty = 0;
			    		}
			    	}
			    }
		})
	);
}
//采购退货
function returnPurchase(){
	initPurchaseBillRetrunListTable();
	addInput('采购退货');
	saveUrl=prUrl + 'editOutputPurchase.do';
	$('#supplychianEventCode').val('OUTPUT_PURCHASE');
	infoDisplayByType('return');
}
//选择仓库物料
function selectWarehouseMaterial(){
	var wareHouseId = null;
	var allotWareHouseId = null;
	if($('#receptionInfo').css('display') == 'block'){
		wareHouseId = $('#receptOutputWarehouseId').combogrid('getValue');
		if(wareHouseId == null || wareHouseId == ''){
			$.messager.alert('提示','请选择领用仓库');
			return;
		}
	}
	if($('#scrapOutputInfo').css('display') == 'block'){
		wareHouseId = $('#scrapOutputWarehouseId').combogrid('getValue');
		if(wareHouseId == null || wareHouseId == ''){
			$.messager.alert('提示','请选择报废仓库');
			return;
		}
	}
	if($('#returnPurchaseInfo').css('display') == 'block'){
		wareHouseId = $('#returnOutputWarehouseId').combogrid('getValue');
		if(wareHouseId == null || wareHouseId == ''){
			$.messager.alert('提示','请选择退货仓库');
			return;
		}
	}
	if($('#allotInfo').css('display') == 'block'){
		wareHouseId = $('#allotOutputWarehouseId').combogrid('getValue');
		allotWareHouseId = $('#allotWareHouseId').combogrid('getValue');
//		if(wareHouseId == null || wareHouseId == ''){
//			$.messager.alert('提示','请选择调出仓库');
//			return;
//		}
		var isValid = $('#editForm').form('validate');
		if (!isValid){
			$.messager.alert('提示','请将头部信息先填写完');
			return;
		}
	}
	var oldWareHouseId = $('#outputWarehouseId').combobox('getValue');
	$('#outputWarehouseId').combobox('setValue',wareHouseId);
	$('#outputWarehouseId').combobox('disable');
	$('#showWarehouseMaterialName').searchbox('setValue','');
	
//	$('#selectWarehouseMaterialListTable').datagrid({url:''});
	$('#selectWarehouseMaterialListTable').datagrid('load','');
	if(oldWareHouseId == wareHouseId){
		var rows = $('#listTable').datagrid('getRows');
		var filterIds = [];
		$.each(rows, function(i,data){
			filterIds.push(data.rowId);
		});
		$('#selectWarehouseMaterialListTable').datagrid('reload',{warehouseId:wareHouseId,filterIds:filterIds.join(",")});
	}
	
	$('#selectWarehouseMaterialListTable').datagrid('load',basicUrl + prUrl + "searchWarehouseMaterialToPage.do?eventName="+$('#supplychianEventCode').val()+'&allotDestination='+$('#allotDestination').combobox('getValue')+'&allotWareHouseId='+allotWareHouseId);
	
	leftSilpFun('selectWarehouseMaterialWin',true,9001);
}
//选择采购单
function selectPurchaseBill(){
	$('#purchaseBillListTable').datagrid('load',basicUrl + prUrl + "searchWareHouseMaterialFromPurchaseTableToPage.do");
	$('#searchDetailType').val('output');
	leftSilpFun('selectPurchaseBillWin',true,9001);
}
//初始化采购退货来源为物料退货的明细
function initMaterialRetrunListTable(){
	if($("#returnListTableToolbar").hasClass("datagrid-toolbar")){
		$('#returnListTableToolbar').appendTo($('#toolbarDiv'));
		$("#returnListTableToolbar").removeClass("datagrid-toolbar");
	}
	$('#listTable').datagrid(
			j_detailGrid({
				toolbar:"#receptionListTableToolbar",
				dbClickEdit:true,
				columns:[[
			      	{field:'ck',checkbox:true},
	              	j_gridText({field:'materialName',title:'物料名称',width:100,sortable:false}),
	            	j_gridText({field:'materialFirstClassName',title:'大类',width:60,sortable:false}),
	            	j_gridText({field:'materialSecondClassName',title:'中类',width:60,sortable:false}),
	              	j_gridText({field:'specAll',title:'规格',width:100,sortable:false}),
	              	j_gridText({field:'warehouseName',title:'所属仓库',width:100,sortable:false}),
	              	j_gridText({field:'existsQty',title:'库存数量',formatter:function(value,row){
              			value = value == null || value == undefined || value == '' ? 0 : value;
	              		return value+row.unit;
	              	},width:80,sortable:false}),
	              	j_gridText({field:'actualPrice',title:'买入单价',formatter:function(value,row){
              			value = value == null || value == undefined || value == '' ? 0 : value;
	              		return value+'元';
	              	},width:100,sortable:false}),
	              	j_gridText({field:'productionDate',title:'生产日期',width:100,sortable:false}),
	              	j_gridText({field:'effectiveDate',title:'有效日期',width:100,sortable:false}),
	              	j_gridNumber({field:'outputQty',title:'退货数量',formatter:function(value,row){
              			value = value == null || value == undefined || value == '' ? 0 : value;
	              		return value+row.unit;
	              	},min:0,precision:3,width:80,sortable:false}),
	              	j_gridText({field:'notes',title:'备注',width:80,editor:'textbox',sortable:false})
			    ]],
			    onEndEdit:function(index,row){
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
			    			$.messager.alert('提示','第'+(index+1)+'行物料'+row.materialName+'退货数量输入不符合要求，不是最小领用量的整数倍,请重新输入！');
			    			row.outputQty = 0;
			    		}
			    	}
			    }
		})
	);
}
//初始化采购退货来源为采购单的明细
function initPurchaseBillRetrunListTable(){
	if($("#receptionListTableToolbar").hasClass("datagrid-toolbar")){
		$('#receptionListTableToolbar').appendTo($('#toolbarDiv'));
		$("#receptionListTableToolbar").removeClass("datagrid-toolbar");
	}
	$('#listTable').datagrid(
			j_detailGrid({
				toolbar:"#returnListTableToolbar",
				dbClickEdit:true,
				columns:[[
			      	{field:'ck',checkbox:true},
	              	j_gridText({field:'materialName',title:'物料名称',width:100,sortable:false}),
	            	j_gridText({field:'materialFirstClassName',title:'大类',width:60,sortable:false}),
	            	j_gridText({field:'materialSecondClassName',title:'中类',width:60,sortable:false}),
	              	j_gridText({field:'specAll',title:'规格',width:100,sortable:false}),
	              	j_gridText({field:'warehouseName',title:'所属仓库',width:100,sortable:false}),
	              	j_gridText({field:'existsQty',title:'库存数量',formatter:function(value,row){
              			value = value == null || value == undefined || value == '' ? 0 : value;
	              		return value+row.unit;
	              	},width:100,sortable:false}),
	              	j_gridText({field:'actualPrice',title:'买入单价',formatter:function(value,row){
              			value = value == null || value == undefined || value == '' ? 0 : value;
	              		return value+'元';
	              	},width:100,sortable:false}),
	              	j_gridText({field:'productionDate',title:'生产日期',width:100,sortable:false}),
	              	j_gridText({field:'effectiveDate',title:'有效日期',width:100,sortable:false}),
	              	j_gridNumber({field:'outputQty',title:'退货数量',formatter:function(value,row){
              			value = value == null || value == undefined || value == '' ? 0 : value;
	              		return value+row.unit;
	              	},min:0,precision:3,width:80,sortable:false}),
	              	j_gridText({field:'notes',title:'备注',width:80,editor:'textbox',sortable:false})
			    ]],
			    onEndEdit:function(index,row){
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
			    			$.messager.alert('提示','第'+(index+1)+'行物料'+row.materialName+'退货数量输入不符合要求，不是最小领用量的整数倍,请重新输入！');
			    			row.outputQty = 0;
			    		}
			    	}
			    }
		})
	);
}
//退货来源改变方法
function returnOrginChangeFun(newValue,oldValue){
	if(oldValue == ''){
		return;
	}
	if(newValue == '1'){
		$('#returnOutputWarehouseId').parent().css('display','none');
		$('#returnOutputWarehouseId').combogrid('disable');
		$('#returnOutputWarehouseIdLabel').css('display','none');
		initPurchaseBillRetrunListTable();
		$('#listTable').datagrid('loadData',{success:true,total: 0, rows: [] });
	}else{
		$('#returnOutputWarehouseId').parent().css('display','inline-block');
		$('#returnOutputWarehouseId').combogrid('enable');
		$('#returnOutputWarehouseIdLabel').css('display','inline-block');
		initMaterialRetrunListTable();
		$('#listTable').datagrid('loadData',{success:true,total: 0, rows: [] });
	}
}
//卖猪
function pigSell(){
	var tabNum = parseInt((window.parent.document.getElementById("header").clientWidth - 175 - 74 -20)/130);
	var liLength = $(".tabList", parent.document).length;
	//判断打开的tab是否已经超过最大值
	if(liLength >= 15){
		$.messager.alert('警告', '最多只能打开15个页面，请关闭无用页面重新点击！');
	}else{
		//获取英文事件名称
		var eventName = 'PigMoveIn';
		//获取中文事件名称
		var chinseEventName = '猪只销售';
		var eventUrl = '/jsp/production/GoodPigSell.jsp';

		//判断是否已经存在事件tab flag=true不存在 false存在
		var flag = true;
		$.each($(".tabTetx", parent.document),function(index,data){
			if(data.innerText == chinseEventName){
				flag = false;
			}
		});
		//如果已经存在则显示tab内容且选中，否则添加事件tab并选中
		if(flag){
			if(liLength >= tabNum){
				$("#tabMore", parent.document).css('display','block');
				turnLeftFun();
			}
			//动态生成html
			var addHtml = '<li class="listSelected" data-eventname="'+eventName+'">'+
								'<div class="tabTetx fl" onClick="tabClickFun(this)">'+chinseEventName+'</div>'+
								'<div class="close fl" onClick="tabCloseFun(this)"></div>'+
						  '</li>';
			var randomnumber=Math.floor(Math.random()*100000);
			$('#main_right', parent.document).append('<div id="tabContent_'+eventName+'" style="height:100%"><iframe scrolling="no" frameborder="0" id="iframe_'+eventName+'" src="'+basicUrl+eventUrl+'?randomnumber='+randomnumber+'&fontSize='+parent.baseFontSize+'" style="width:100%;height:100%;"></iframe></div>');
			$('#tabContent_'+eventName+ ' iframe', parent.document).on('load',function(){
				$('.tabList', parent.document).find('ul').append(addHtml);
				changeTabContent(eventName);
			});
		}else{
			if(liLength >= tabNum){
				var isHide = false;
				$.each($('.tabList', parent.document).find('li'),function(index,data){
					if(data.dataset.eventname == eventName && data.style.display == 'none'){
						isHide = true;
					}
				});
				if(isHide){
					$('#tabMore', parent.document).css('display','block');
					showHideTab(para);
				}
			}
			changeTabContent(eventName);
		}
	}
}
function changeTabContent(eventName){
	$.each($('.tabList', parent.document).find('li'),function(index,data){
		if(data.dataset.eventname == eventName){
			if(data.className == ''){
				data.className = 'listSelected';
				$('#tabContent_'+data.dataset.eventname, parent.document).css('display','block');
			}
		}else{
			if(data.className == 'listSelected'){
				data.className = '';
				$('#tabContent_'+data.dataset.eventname, parent.document).css('display','none');
			}
		}
	});
	if($('.hompageBorder', parent.document)[0].className == 'hompageBorder fl listSelected'){
		$('.hompageBorder', parent.document)[0].className = 'hompageBorder fl';
		$('#tabContent_homePage', parent.document).css('display','none');
	}
}

//调拨去向改变方法
function allotDestinationChange(newValue,oldValue){
	if($('#listTable').css('display') == 'none'){
		$('#listTable').datagrid('loadData',{success:true,total: 0, rows: []});
	}
	$('#allotWareHouseId').combogrid('reset');
	$('#allotFarmId').combogrid('reset');
	if(newValue == '1'){
		$('#allotDestinationLabel').css('display','inline-block');
		$('#allotDestinationLabel').next().css('display','inline-block');
		$('#allotDestinationLabel').find('label').html('调入仓库');
		$('#allotWareHouseId').parent().css('display','inline-block');
		$('#allotFarmId').parent().css('display','none');
		$('#allotWareHouseId').combogrid('enable');
		$('#allotFarmId').combogrid('disable');
	}else{
		$('#allotDestinationLabel').css('display','inline-block');
		$('#allotDestinationLabel').next().css('display','inline-block');
		$('#allotDestinationLabel').find('label').html('调入猪场');
		$('#allotFarmId').parent().css('display','inline-block');
		$('#allotWareHouseId').parent().css('display','none');
		$('#allotWareHouseId').combogrid('disable');
		$('#allotFarmId').combogrid('enable');
	}
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
		url:basicUrl+saveUrl,
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