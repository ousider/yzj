var editIndex = undefined;
var prUrl = '/supplyChian/';
var searchMainListUrl = prUrl + 'searchInputStoreToPage.do';
var searchDetailListUrl = prUrl + 'searchInputBillDetailToList.do';
var saveUrl=prUrl+'';
var deleteUrl=prUrl+'';
//搜索提交入库信息Url
var searchMaterialInputCommitDetailToListUrl = prUrl + 'searchMaterialInputCommitDetailToList.do';
//确认提交入库Url
var materialInputCommitSureUrl = prUrl + 'editMaterialInputCommit.do';
//作废提交入库Url
var materialInputScrapUrl = prUrl + 'editMaterialInputScrap.do';
/** *********页面渲染开始*************************************************************************/
// 物料入库添加提交按钮
var scRkdtjtjbz = "OFF";
$.ajax({  
    url : basicUrl+'/param/getSettingValueByCode.do?settingCode=SC_RKDTJTJBZ', 
    async:false,
    type : "POST",  
    success : function(data) { 
    	var obj = eval('(' + data + ')');
    	scRkdtjtjbz = obj.rows;
    }
});

//物料入库添加打印按钮
var scRkdbxdytj = "OFF";
$.ajax({  
    url : basicUrl+'/param/getSettingValueByCode.do?settingCode=SC_RKDBXDYTJ', 
    async:false,
    type : "POST",  
    success : function(data) { 
    	var obj = eval('(' + data + ')');
//    	scRkdbxdytj = obj.rows;
    	scRkdbxdytj = "OFF";
    }
});

$(document).ready(function(){
	dafengModelConfirm();
	
	/**
	 * 主表加载
	 */
	$('#table').datagrid(
			j_grid({
				singleSelect:true,
				toolbar:"#tableToolbarList",
				url:searchMainListUrl,
				columnFields:'rowId,billCode,billDate,eventCodeName,statusName,inputerName,notes',
				// ,purchaseBillCode,purchaseSupplier,receipt
				// ,对应采购单编号,采购供应商,发票号
				columnTitles:'ID,入库单编号,入库日期,入库类型,状态,入库员,备注',
				hiddenFields:'rowId',
				onDblClickRowFun:showInputBillDetail
			}));
	
	// 物料入库添加提交按钮
	if(scRkdtjtjbz == "ON"){
		$('#materialInputCommit').css('display','inline-block');
	}
	
	//物料入库添加打印按钮
	if(scRkdbxdytj == "ON"){
		$('#materialInputPrint').css('display','inline-block');
	}

	setTimeout(function(){
		//入库员
		xnCombobox({
			id:'advancedInputerId',
			valueField:'rowId',
			textField:'employeeName',
			url:'/supplyChian/searchEmployeeByIdToList.do',
			hasAll:true
		});
		
		
		// 高级查询入库类型
		xnCdCombobox({
			id:'advancedEventCode',
			typeCode:'SUPPLYCHIAN_EVENT_CODE',
			editable:false,
			includeValue:['INPUT_OUTPUT','INPUT_RESCRAP','INPUT_ALLOT','INPUT_NOT_BILL','FEED_INPUT_ARRIVE','MATERIAL_INPUT_ARRIVE','INPUT_SALE'],
			hasAll:true
		});
		
	},500)
});

//查看
function showInputBillDetail(index,row){
	var columnFields = '';
	var columnTitles = '';
	if(row.eventCode == 'INPUT_ARRIVE' || row.eventCode == 'MATERIAL_INPUT_ARRIVE' || row.eventCode == 'FEED_INPUT_ARRIVE'){
		columnFields='materialName,materialFirstClassName,materialSecondClassName,specAll,purchaseOrFree,inputWarehouseName,inputQtyName,productionDate,effectiveDate,notes';
		columnTitles='物料,大类,中类,规格,购料/赠料,入库仓库,入库数量,生产日期,有效日期,备注';
	}else{
		columnFields='materialName,materialFirstClassName,materialSecondClassName,specAll,inputWarehouseName,inputQtyName,productionDate,effectiveDate,notes';
		columnTitles='物料,大类,中类,规格,入库仓库,入库数量,生产日期,有效日期,备注';
	}
	
	$('#inputBillDetailTable').datagrid(
			j_grid_view({
				haveCheckBox:false,
				url:searchDetailListUrl+'?inputId='+row.rowId+'&status='+row.status,
				columnFields:columnFields,
				columnTitles:columnTitles,
				hiddenFields:'',
				fit:false,
				pagination:false,
				height:'100%',
				width:'100%'
			})
		);
	leftSilpFun('inputBillDetailId');
}

//初始化物资入库明细
function initMaterialListTable(){
	if($("#returnListTableToolbar").hasClass("datagrid-toolbar")){
		$('#returnListTableToolbar').appendTo($('#toolbarDiv'));
		$("#returnListTableToolbar").removeClass("datagrid-toolbar");
	}
	if($("#trunOverScrapListTableToolbar").hasClass("datagrid-toolbar")){
		$('#trunOverScrapListTableToolbar').appendTo($('#toolbarDiv'));
		$("#trunOverScrapListTableToolbar").removeClass("datagrid-toolbar");
	}
	if($("#allotListTableToolbar").hasClass("datagrid-toolbar")){
		$('#allotListTableToolbar').appendTo($('#toolbarDiv'));
		$("#allotListTableToolbar").removeClass("datagrid-toolbar");
	}
	
	$('#purchaseBillCode').html('');
	$('#purchaseDate').html('');
	$('#purchaserName').html('');
	$('#supplierName').html('');
	$('#purchaseId').val('');
	$('#purchaseEventCode').val('');
	
	$('#listTable').datagrid(
			j_detailGrid({
				toolbar:"#materialListTableToolbar",
				dbClickEdit:true,
				columns:[[
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
	              	},min:0,precision:3,width:80}),
	              	j_gridText({field:'effectiveDate',title:'有效日期',width:100,editor:{type:'datebox',options:{editable:false}}}),
	              	j_gridText({field:'notes',title:'备注',width:80,editor:'textbox'})
			    ]],
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
}
//实际入库物料改变方法
function actualInputMaterialSelectFun(index,row){
	if(editIndex == undefined){
		return;
	}
	var rows = $('#listTable').datagrid('getRows');
	rows[editIndex].actMaterialSpecAll = row.specAll;
	rows[editIndex].actMaterialSpec = row.spec;
	rows[editIndex].actMaterialSpecNum = row.specNum;
	changeTableDisplayValue('listTable',editIndex,[{
		field:'actMaterialSpecAll',
		value:rows[editIndex].actMaterialSpecAll
	}]);
};
//初始化退货入库明细
function initReturnListTable(){
	if($("#materialListTableToolbar").hasClass("datagrid-toolbar")){
		$('#materialListTableToolbar').appendTo($('#toolbarDiv'));
		$("#materialListTableToolbar").removeClass("datagrid-toolbar");
	}
	if($("#trunOverScrapListTableToolbar").hasClass("datagrid-toolbar")){
		$('#trunOverScrapListTableToolbar').appendTo($('#toolbarDiv'));
		$("#trunOverScrapListTableToolbar").removeClass("datagrid-toolbar");
	}
	if($("#allotListTableToolbar").hasClass("datagrid-toolbar")){
		$('#allotListTableToolbar').appendTo($('#toolbarDiv'));
		$("#allotListTableToolbar").removeClass("datagrid-toolbar");
	}
	
	$('#outputUseBillCode').html('');
	$('#outputUseBillDate').html('');
	$('#outputUseUserName').html('');
	$('#outputUseOutputerName').html('');
	$('#useId').val('');
	
	$('#listTable').datagrid(
			j_detailGrid({
				toolbar:"#returnListTableToolbar",
				dbClickEdit:true,
				columns:[[
			      	{field:'ck',checkbox:true},
	              	j_gridText({field:'materialName',title:'物料',width:100,sortable:false}),
	             	j_gridText({field:'materialFirstClassName',title:'大类',width:60,sortable:false}),
	            	j_gridText({field:'materialSecondClassName',title:'中类',width:60,sortable:false}),
	              	j_gridText({field:'specAll',title:'规格',width:100,sortable:false}),
	              	j_gridText({field:'productionDate',title:'生产日期',width:100,sortable:false}),
	              	j_gridText({field:'effectiveDate',title:'有效日期',width:100,sortable:false}),
	              	j_gridText({field:'outputDestinationName',title:'去向',width:70,sortable:false}),
	              	j_gridText({field:'outputFarmIdName',title:'猪场',width:50,sortable:false}),
	              	j_gridText({field:'outputDeptIdName',title:'部门',width:50,sortable:false}),
	              	j_gridText({field:'outputHouseIdName',title:'猪舍',width:100,sortable:false}),
	              	j_gridText({field:'outputSwineryIdName',title:'批次',width:100,sortable:false}),
	              	j_gridText({field:'canReturnQty',title:'可入库数量',formatter:function(value,row){
              			value = value == null || value == undefined || value == '' ? 0 : value;
	              		return value+row.unit;
	              	},width:80,sortable:false}),
	              	j_gridText({field:'inputWarehouseName',title:'退入仓库',width:100,sortable:false}),
	              	j_gridNumber({field:'inputQty',title:'入库数量',formatter:function(value,row){
              			value = value == null || value == undefined || value == '' ? 0 : value;
	              		return value+row.unit;
	              	},min:0,precision:3,width:80,sortable:false}),
	              	j_gridText({field:'notes',title:'备注',width:80,editor:'textbox',sortable:false})
			    ]],
			    onBeginEdit:function(index,row){
			    },
			    onEndEdit:function(index,row){
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
}
//初始化反报废明细
function initTurnOverScrapListTable(){
	if($("#materialListTableToolbar").hasClass("datagrid-toolbar")){
		$('#materialListTableToolbar').appendTo($('#toolbarDiv'));
		$("#materialListTableToolbar").removeClass("datagrid-toolbar");
	}
	if($("#returnListTableToolbar").hasClass("datagrid-toolbar")){
		$('#returnListTableToolbar').appendTo($('#toolbarDiv'));
		$("#returnListTableToolbar").removeClass("datagrid-toolbar");
	}
	if($("#allotListTableToolbar").hasClass("datagrid-toolbar")){
		$('#allotListTableToolbar').appendTo($('#toolbarDiv'));
		$("#allotListTableToolbar").removeClass("datagrid-toolbar");
	}
	
	$('#outputScrapBillCode').html('');
	$('#outputScrapBillDate').html('');
	$('#outputScrapOutputerName').html('');
	$('#scrapId').val('');
	
	$('#listTable').datagrid(
			j_detailGrid({
				toolbar:"#trunOverScrapListTableToolbar",
				dbClickEdit:true,
				columns:[[
			      	{field:'ck',checkbox:true},
	              	j_gridText({field:'materialName',title:'物料',width:100,sortable:false}),
	             	j_gridText({field:'materialFirstClassName',title:'大类',width:60,sortable:false}),
	            	j_gridText({field:'materialSecondClassName',title:'中类',width:60,sortable:false}),
	              	j_gridText({field:'specAll',title:'规格',width:100,sortable:false}),
	              	j_gridText({field:'canReturnQty',title:'可入库数量',formatter:function(value,row){
              			value = value == null || value == undefined || value == '' ? 0 : value;
	              		return value+row.unit;
	              	},width:80,sortable:false}),
	              	j_gridText({field:'inputWarehouseName',title:'退入仓库',width:100,sortable:false}),
	              	j_gridNumber({field:'inputQty',title:'入库数量',formatter:function(value,row){
              			value = value == null || value == undefined || value == '' ? 0 : value;
	              		return value+row.unit;
	              	},min:0,precision:3,width:80,sortable:false}),
	              	j_gridText({field:'notes',title:'备注',width:80,editor:'textbox',sortable:false})
			    ]],
			    onBeginEdit:function(index,row){
			    	
			    },
			    onEndEdit:function(index,row){
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
}
function initWriteBillCodeListTable(){
	if($("#materialListTableToolbar").hasClass("datagrid-toolbar")){
		$('#materialListTableToolbar').appendTo($('#toolbarDiv'));
		$("#materialListTableToolbar").removeClass("datagrid-toolbar");
	}
	if($("#returnListTableToolbar").hasClass("datagrid-toolbar")){
		$('#returnListTableToolbar').appendTo($('#toolbarDiv'));
		$("#returnListTableToolbar").removeClass("datagrid-toolbar");
	}
	if($("#trunOverScrapListTableToolbar").hasClass("datagrid-toolbar")){
		$('#trunOverScrapListTableToolbar').appendTo($('#toolbarDiv'));
		$("#trunOverScrapListTableToolbar").removeClass("datagrid-toolbar");
	}
	if($("#allotListTableToolbar").hasClass("datagrid-toolbar")){
		$('#allotListTableToolbar').appendTo($('#toolbarDiv'));
		$("#allotListTableToolbar").removeClass("datagrid-toolbar");
	}
	$('#listTable').datagrid(
			j_detailGrid({
				toolbar:"",
				dbClickEdit:true,
				columns:[[
			      	{field:'ck',checkbox:true},
	              	j_gridText({field:'billCode',title:'入库单据编号',width:100,sortable:false}),
	              	j_gridText({field:'billDate',title:'入库日期',width:100,sortable:false}),
	              	j_gridText({field:'inputer',title:'入库员',width:100,sortable:false}),
	              	j_gridText({field:'billType',title:'入库类型',width:100,sortable:false}),
	              	j_gridText({field:'purchaseBillCode',title:'对应采购单号',width:100,sortable:false}),
	              	j_gridText({field:'purchaseSupplier',title:'采购供应商',width:100,sortable:false}),
	              	j_gridText({field:'notes',title:'备注',width:100,sortable:false})
			    ]]
		})
	);
}

function initAllotListTable(){
	if($("#returnListTableToolbar").hasClass("datagrid-toolbar")){
		$('#returnListTableToolbar').appendTo($('#toolbarDiv'));
		$("#returnListTableToolbar").removeClass("datagrid-toolbar");
	}
	if($("#trunOverScrapListTableToolbar").hasClass("datagrid-toolbar")){
		$('#trunOverScrapListTableToolbar').appendTo($('#toolbarDiv'));
		$("#trunOverScrapListTableToolbar").removeClass("datagrid-toolbar");
	}
	if($("#materialListTableToolbar").hasClass("datagrid-toolbar")){
		$('#materialListTableToolbar').appendTo($('#toolbarDiv'));
		$("#materialListTableToolbar").removeClass("datagrid-toolbar");
	}
	
	$('#outputAllotBillCode').html('');
	$('#outputAllotBillDate').html('');
	$('#outputAllotOutputerName').html('');
	$('#allotId').val('');
	
	$('#listTable').datagrid(
			j_detailGrid({
				toolbar:"#allotListTableToolbar",
				dbClickEdit:true,
				columns:[[
			      	{field:'ck',checkbox:true},
	              	j_gridText({field:'materialName',title:'物料',width:100,sortable:false}),
	             	j_gridText({field:'materialFirstClassName',title:'大类',width:60,sortable:false}),
	            	j_gridText({field:'materialSecondClassName',title:'中类',width:60,sortable:false}),
	              	j_gridText({field:'specAll',title:'规格',width:100,sortable:false}),
	              	j_gridText({field:'actualPrice',title:'单价',formatter:function(value,row){
              			value = value == null || value == undefined || value == '' ? 0 : value;
	              		return value+'元';
	              	},precision:2,width:80,sortable:false}),
	              	j_gridText({field:'totalPrice',title:'总价',formatter:function(value,row){
              			value = value == null || value == undefined || value == '' ? 0 : value;
	              		return value+'元';
	              	},width:100,sortable:false}),
	              	j_gridText({field:'inputQty',title:'入库数量',formatter:function(value,row){
              			value = value == null || value == undefined || value == '' ? 0 : value;
	              		return value+row.unit;
	              	},width:80,sortable:false}),
	              	j_gridText({field:'productionDate',title:'生产日期',width:100,sortable:false}),
	              	j_gridText({field:'effectiveDate',title:'有效日期',width:100,sortable:false}),
              		j_gridText({field:'warehouseId',title:'入库仓库',
              		formatter:function(value,row){
              			return row.inputWarehouseName;
              		},
              		editor:	xnGridComboGrid({
              				field:'warehouseId',
	              			idField:'rowId',
	            			textField:'warehouseName',
	            			columns:[[ 	
	            						{field:'rowId',title:'ID',width:100,hidden:true,sortable:false},
	            						{field:'warehouseName',title:'仓库名称',width:100,sortable:false},
	            				        {field:'warehouseTypeName',title:'仓库类型',width:100,sortable:false}
	            				    ]]
	              		}),width:100}),
              		j_gridText({field:'notes',title:'备注',width:80,editor:'textbox',sortable:false})
			    ]],
			    onBeginEdit:function(index,row){
			    	var inputWarehouse = $('#listTable').datagrid('getEditor', {
		                index: index,
		                field: 'warehouseId'
		            });
					if(inputWarehouse != null){
						if(row.outputToWarehouseId != undefined && row.outputToWarehouseId != '' && row.outputToWarehouseId != null){
							$(inputWarehouse.target).combobox('disable');
							$(inputWarehouse.target).combobox('setText',row.inputWarehouseName);
						}else{
							var inputWarehouseGrid = $(inputWarehouse.target).combogrid('grid');
							inputWarehouseGrid.datagrid('load',basicUrl+prUrl + 'searchWarehouseByMaterialTypeToList.do?materialFirstClass='+row.materialFirstClass+'&roleFilterFlag=true');
						}
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
}
//物资入库
function materialInput(){
	initMaterialListTable();
	addInput('物资入库');
	saveUrl=prUrl + 'editMaterialInput.do';
	infoDisplayByType('input');
	$('#btnSave').attr('disabled',false).removeClass('btn-disabled');
}
//精液入库
function spermInput(){
	initMaterialListTable();
	addInput('精液入库');
	saveUrl=prUrl + 'editSpermInput.do';
	infoDisplayByType('input');
	$('#btnSave').attr('disabled',false).removeClass('btn-disabled');
}
//退货入库
function returnInput(){
	initReturnListTable();
	addInput('退货入库');
	saveUrl=prUrl + 'editReturnInput.do';
	infoDisplayByType('return');
	$('#btnSave').attr('disabled',false).removeClass('btn-disabled');
}
//反报废
function trunOverScrap(){
	initTurnOverScrapListTable();
	addInput('反报废');
	saveUrl=prUrl + 'editTrunOverScrap.do';
	infoDisplayByType('trunOverScrap');
	$('#btnSave').attr('disabled',false).removeClass('btn-disabled');
}
//调拨入库
function allotInput(){
	initAllotListTable();
	addInput('调拨入库');
	saveUrl=prUrl + 'editAllotInput.do';
	infoDisplayByType('input');
	$('#btnSave').attr('disabled',false).removeClass('btn-disabled');
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
	if(type == 'input'){
		$('#inputInfo').css('display','block');
		$('#billDate').datebox('enable');
		$('#inputerId').combogrid('enable');
		$('#searcherId').combogrid('enable');
	}else{
		$('#inputInfo').css('display','none');
		$('#billDate').datebox('disable');
		$('#inputerId').combogrid('disable');
		$('#searcherId').combogrid('disable');
	}
	if(type == 'return'){
		$('#returnInfo').css('display','block');
		$('#returnDate').datebox('enable');
		$('#returnerId').combogrid('enable');
	}else{
		$('#returnInfo').css('display','none');
		$('#returnDate').datebox('disable');
		$('#returnerId').combogrid('disable');
	}
	if(type == 'trunOverScrap'){
		$('#trunOverScrapInfo').css('display','block');
		$('#trunOverScrapDate').datebox('enable');
		$('#trunOverScraperId').combogrid('enable');
	}else{
		$('#trunOverScrapInfo').css('display','none');
		$('#trunOverScrapDate').datebox('disable');
		$('#trunOverScraperId').combogrid('disable');
	}

}
//选择采购订单
function selectPurchaseBill(){
	var filterId = $('#purchaseId').val();
	if(filterId==''){
		// 清空参数
		$('#purchaseBillListTable').datagrid('options').queryParams = {};
	}
	$('#purchaseBillListTable').datagrid('load',basicUrl + searchMaterialFromPurchaseUrl);
	$('#searchDetailType').val('input');
	leftSilpFun('selectPurchaseBillWin',true,9001);
}
//选择领用单
function selectReceptionBill(){
	var filterId = $('#useId').val();
	if(filterId==''){
		// 清空参数
		$('#supplychianBillListTable').datagrid('options').queryParams = {};
	}
	if($('#supplychianEventCode').combobox('getValue') == 'OUTPUT_USE'){
		$('#supplychianBillListTable').datagrid('reload');
	}else{
		$('#supplychianEventCode').combobox('setValue','OUTPUT_USE');
	}
	leftSilpFun('selectSupplychianBillWin',true,9001);
}
//选择报废单
function selectScrapBill(){
	var filterId = $('#scrapId').val();
	if(filterId==''){
		// 清空参数
		$('#supplychianBillListTable').datagrid('options').queryParams = {};
	}
	if($('#supplychianEventCode').combobox('getValue') == 'OUTPUT_SCRAP'){
		$('#supplychianBillListTable').datagrid('reload');
	}else{
		$('#supplychianEventCode').combobox('setValue','OUTPUT_SCRAP');
	}
	leftSilpFun('selectSupplychianBillWin',true,9001);
}
//选择调拨单
function selectAllotBill(){
	var filterId = $('#allotId').val();
	if(filterId==''){
		// 清空参数
		$('#supplychianBillListTable').datagrid('options').queryParams = {};
	}
	if($('#supplychianEventCode').combobox('getValue') == 'OUTPUT_ALLOT'){
		$('#supplychianBillListTable').datagrid('reload');
	}else{
		$('#supplychianEventCode').combobox('setValue','OUTPUT_ALLOT');
	}
	leftSilpFun('selectSupplychianBillWin',true,9001);
}
//拆分
function splitOutput(){
	var record = $('#listTable').datagrid('getSelections');
	var length = record.length;
	if(length < 1){
		$.messager.alert('警告','请选择一条数据！');
	}else if(length > 1){
		$.messager.alert('警告','只能选择一条数据！');
	}else{
		$('#splitOutputPanel').dialog({
			modal:true
		});
		$('#splitOutputPanel').dialog('open');
		$('#splitOutputForm').form('reset');
		$('#purchaseQty').val(record[0].purchaseQty);
		$('#arriveQty').val(record[0].arriveQty);
	}
}
//拆分确定
function splitOutputSure(){
	var isValid = $('#splitOutputForm').form('validate');
	if(isValid){
		var purchaseQty = strToFloat($('#purchaseQty').val());
		var arriveQty = strToFloat($('#arriveQty').val());
		var purchaseQtyNew = strToFloat($('#purchaseQtyNew').numberspinner('getValue'));
		if('MATERIAL_PURCHASE' == $('#purchaseEventCode').val()){
			if(purchaseQtyNew >= purchaseQty - arriveQty){
				$.messager.alert('警告','拆分后的采购量大于等于可入库数量（购买数量-已入库数量），请重新填写！');
				return;
			}
		}
		if(purchaseQtyNew >= purchaseQty){
			$.messager.alert('警告','拆分后的采购量大于等于购买数量，请重新填写！');
		}else if(purchaseQtyNew <= 0){
			$.messager.alert('警告','拆分后的采购量不能小于等于零，请重新填写！');
		}else{
			//拆分方法
			var row = $('#listTable').datagrid('getSelections')[0];
			jAjax.submit('/supplyChian/editSplitMaterial.do?editType=purchase',{rowId:row.splitId,splitQty:purchaseQtyNew}
			,function(response){
				$('#splitOutputPanel').dialog('close');
				$('#listTable').datagrid('reload');
				$.messager.progress('close');	
			},function(response){
	    		jAjax.errorFunc(response.errorMsg);
	    		$.messager.progress('close');
	    	},null,true,null,null);
		}
	}
}

//合并
function dialogCancel(dialog){
	$('#'+dialog).dialog('close');
}
function mergeOutput(){
	var record = $('#listTable').datagrid('getSelections');
	var length = record.length;
	if(length != 2){
		$.messager.alert('警告','请选择两条数据！');
	}else{
		if(record[0].materialOnly != record[1].materialOnly){
			$.messager.alert('警告','选择合并的数据不是采购时的同一数据，请重新选择！');
		}else{
			var firstData = record[0].rowId;
			var secondData = record[1].rowId;
			jAjax.submit('/supplyChian/editMergeMaterial.do?editType=purchase',{firstData:firstData,secondData:secondData}
			,function(response){
				$('#listTable').datagrid('reload');
				$.messager.progress('close');	
			},function(response){
	    		jAjax.errorFunc(response.errorMsg);
	    		$.messager.progress('close');
	    	},null,true,null,null);
		}
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
		if(data.hasOwnProperty('inputQty') && data.inputQty != 0){
			data.errorAlertLineNumber = index + 1;
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
	$('#btnSave').attr('disabled',true).addClass('btn-disabled');
	$.messager.progress();
	
	var queryParams;
	var listTable = document.getElementById("listTable");
	
	var isValid = $('#editForm').form('validate');
	if (!isValid){
		$.messager.alert('提示','头部必填项没有填写！');
		$('#btnSave').attr('disabled',false).removeClass('btn-disabled');
		$.messager.progress('close');
		return;
	}
	
	//判断是否有明细表
	if(listTable!= null){
		queryParams = collectDetailData();
		if(queryParams == null){
			$.messager.alert('提示','前台提示--未添加明细不能提交！');
			$('#btnSave').attr('disabled',false).removeClass('btn-disabled');
			$.messager.progress('close');
			return;
		}
		
		if(queryParams.gridList == '[]'){
			$.messager.alert('提示','填写数量都为零！');
			$('#btnSave').attr('disabled',false).removeClass('btn-disabled');
			$.messager.progress('close');
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
			}else{
				$('#btnSave').attr('disabled',false).removeClass('btn-disabled');
				$.messager.progress('close');
			}
		});
	}else{
		delete queryParams.notSubmitIndex;
		onbtnSaveSubmit(queryParams);
	}
}

function onbtnSaveSubmit(queryParams){
	$('#editForm').form('submit',{
		url:basicUrl+saveUrl,
		queryParams:queryParams,
		success: function(response){
			response=eval('('+response+')');
			if(response.success){
				$('#listTable').datagrid('loadData',{success:true,total: 0, rows: [] });
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

// 提交入库
function materialInputCommit(){
	var record = $('#table').datagrid('getSelections');
	var length = record.length;
	if(length < 1){
		$.messager.alert('警告','请选择一条数据！');
	}else if(length > 1){
		$.messager.alert('警告','只能选择一条数据！');
	}else{
		// 入库单，并且未提交
		if((record[0].eventCode=='FEED_INPUT_ARRIVE' || record[0].eventCode=='MATERIAL_INPUT_ARRIVE') && record[0].status=='0'){
			$('#showMaterialCommitPanel').dialog('open');
			$('#commitInputId').val(record[0].rowId);
			$('#showMaterialDetailCommit').datagrid(
				j_grid_view({
					haveCheckBox:false,
					url:searchMaterialInputCommitDetailToListUrl+'?inputId='+record[0].rowId,
					columnFields:'materialName,materialSecondClassName,specAll,purchaseOrFree,actualPriceName,inputQtyName,inputWarehouseName,productionDate,effectiveDate,supplierSortName,notes',
					columnTitles:"物料,中类,规格,购/赠,单价,入库数量,入库仓库,生产日期,有效日期,供应商,备注",
					hiddenFields:'',
					pagination:false,
				})
			);
		}else{
			$.messager.alert('警告','只能选择未提交的入库单！');
		}
	}
}

// 提交确定
function materialInputCommitSure(){
	$.messager.progress();
	$('#showMaterialCommitForm').form('submit',{
		url:basicUrl+materialInputCommitSureUrl,
		success: function(response){
			response=eval('('+response+')');
			if(response.success){
				$.messager.alert('提示','保存成功！');
				dialogCancel('showMaterialCommitPanel');
				$('#showMaterialCommitForm').form('reset');
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
			$.messager.progress('close');
		}
	});
}

// 作废入库单
function materialInputScrap(){
	var record = $('#table').datagrid('getSelections');
	var length = record.length;
	if(length < 1){
		$.messager.alert('警告','请选择一条数据！');
	}else if(length > 1){
		$.messager.alert('警告','只能选择一条数据！');
	}else{
		// 入库单，并且未提交
		if((record[0].eventCode=='FEED_INPUT_ARRIVE' || record[0].eventCode=='MATERIAL_INPUT_ARRIVE') && record[0].status=='0'){
			$.messager.progress();
			jAjax.submit(materialInputScrapUrl,{'id':record[0].rowId}
			,function(response){
				$.messager.alert('提示','作废成功！');
				$('#table').datagrid('reload');
				$.messager.progress('close');
			}
			,function(response){
				jAjax.errorFunc(response.errorMsg);
				$.messager.progress('close');
			},null,true,null,null);
		}else{
			$.messager.alert('警告','只能选择未提交的入库单！');
		}
	}
}