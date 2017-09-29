var title = '采购订单';
var editIndex = undefined;
var prUrl = '/supplyChian/';
var searchMainListUrl = prUrl + 'searchSelfPurchaseStoreToPage.do';
var searchDetailListUrl = prUrl + '';
var searchInputingEditFromPurchaseDetailUrl = prUrl + 'searchInputingEditFromPurchaseDetailToList.do';
var searchInputInfoFromPurchaseDetailListUrl = prUrl + 'searchInputInfoFromPurchaseDetailToList.do';
var saveUrl=prUrl + '';
var scrapUrl=prUrl + 'editScrapGroupPurchaseStore.do';
var overUrl=prUrl + 'editOverGroupPurchaseStore.do';
var finishUrl=prUrl + 'editFinishPurchaseStore.do'
var nodeIndex = 0;
// purchaseFlag判断可以采购的类型[1]-全部[2]-饲料[3]-兽药
var purchaseFlag = '1';
var canPurchaseSupplierId = '';
//var feedFlag = false;
var saveFlag = 'insert';
/** *********页面渲染开始*************************************************************************/
$(document).ready(function(){
	
	// 判断登陆的角色可以购买哪些饲料
	getPurchaserInfo();
	
	/**
	 * 主表加载
	 */
	$('#table').datagrid(
			j_grid({
				toolbar:"#tableToolbarList",
				url:searchMainListUrl+'?purchaseFlag='+purchaseFlag+'&canPurchaseSupplierId='+canPurchaseSupplierId,
				columnFields:'rowId,billCode,billDate,eventCodeName,statusName,purchaserName,farmNames,totalPriceName,supplierSortName,notes',
				columnTitles:'ID,采购单编号,采购日期,采购类型,状态,采购员,猪场,总价,供应商,备注',
				hiddenFields:'rowId',
				onDblClickRowFun:showPurchaseBillDetail
			}));
	setTimeout(function(){

		//供应商
		xnCombobox({
			id:"supplierId",
			url:'/supplyChian/searchCompanyFromRequireBillToList.do?searchType=All&purchaseFlag='+purchaseFlag+'&canPurchaseSupplierId='+canPurchaseSupplierId,
			valueField:"supplierId",
			textField:"supplierSortName",
			prompt:'修改供应商会清空明细',
			onChange:changeSupplier
		});
		
		$('#purchaseBillDetailTable').datagrid(
				j_grid_view({
					haveCheckBox:false,
					columnFields:'materialName,materialFirstClassName,materialSecondClassName,specAll,actualPriceName,purchaseQtyName,actualFreePercent,totalPriceName,purchaseOrFree,arriveQtyName,arrivePurchasePercent,notes',
					columnTitles:'采购物料,大类,中类,规格,单价,采购数量,赠送比率,总价,购料/赠料,到库数量,入库率,备注',
					hiddenFields:'',
					fit:false,
					pagination:false,
					height:'100%',
					width:'100%',
					onDblClickRowFun:showInputInfoDetail
				},'purchaseBillDetailTable'));
		
		$('#inputInfoDetailTable').datagrid(
				j_grid_view({
					haveCheckBox:false,
					columnFields:'materialName,specAll,inputQtyName,productionDate,effectiveDate,billCode,billDate',
					columnTitles:'实入物料,实入规格,实入数量,生产日期,有效日期,入库单据号,入库日期',
					hiddenFields:'productionDate,effectiveDate,billCode,billDate',
					fit:false,
					pagination:false,
					height:'100%',
					width:'100%'
				},'inputInfoDetailTable'));
		
		//高级查询 状态
		xnCdCombobox({
			id:'searchStatus',
			typeCode:'PURCHASE_STORE_STATUS',
			editable:false,
			multiple:true,
			defaultValue:[3,4,5]
		});
		
		//高级查询 猪场
		xnCombobox({
			id:"searchFarmId",
			url:'/supplyChian/searchCompanyByFarmIdToList.do',
			valueField:"farmId",
			textField:"farmSortName",
			hasAll:true
		});
		
		//高级查询 供应商
		xnCombobox({
			id:"searchSupplierId",
			url:'/supplyChian/searchCompanyFromRequireBillToList.do?searchType=All&purchaseFlag='+purchaseFlag+'&canPurchaseSupplierId='+canPurchaseSupplierId,
			valueField:"supplierId",
			textField:"supplierSortName",
			hasAll:true
		});
		
	},500)
});

/**
 * 改变采购类型，清空明细
 */
function changeEventCode(newValue, oldValue){
	if($('#listTable').css('display') == 'none'){
		$('#listTable').datagrid('loadData',{success:true,total: 0, rows: []});
	}

	$('#totalPrice').html('0元');
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
		showPurchaseBillDetail(index,record[0]);
	}
}

// 查看
function showPurchaseBillDetail(index,row){
	$('#purchaseBillDetailTable').datagrid('load',basicUrl + searchInputingEditFromPurchaseDetailUrl+'?purchaseId='+row.rowId);
	rightSlipFun('inputInfoDetailId',390);
	leftSilpFun('purchaseBillDetailId');
}
// 查看入库的详情
function showInputInfoDetail(index,row){
	$('#inputInfoDetailTable').datagrid('load',basicUrl + searchInputInfoFromPurchaseDetailListUrl+'?rowIds='+row.rowIds);
	leftSilpFun('inputInfoDetailId');
}

//初始化除入库中采购订单明细表
function initPurchaseBillListTable(){
	$("#selectRequireMaterial").removeAttr("disabled");
	$("#selectRequireMaterial").css('background-color','rgb(113, 175, 76)');
	$("#selectMaterial").removeAttr("disabled");
	$("#selectMaterial").css('background-color','rgb(113, 175, 76)');
	$("#selectFeed").attr("disabled",true);
	$("#selectFeed").css('background-color','rgb(220, 220, 220)');
	$("#detailDelete").removeAttr("disabled");
	$("#detailDelete").css('background-color','rgb(113, 175, 76)');
	$("#detailClear").removeAttr("disabled");
	$("#detailClear").css('background-color','rgb(113, 175, 76)');
	$("#group").removeAttr("disabled");
	searchDetailListUrl = prUrl + 'searchPurchaseStoreDetailToList.do';
	$('#listTable').datagrid(
			j_detailGrid({
				toolbar:"#listTableToolbar",
				dbClickEdit:true,
				columns:[[
			      	{field:'ck',checkbox:true},
	              	j_gridText({field:'materialName',title:'物料名称',width:100,sortable:false}),
	              	j_gridText({field:'materialFirstClassName',title:'大类',width:60,sortable:false}),
	            	j_gridText({field:'materialSecondClassName',title:'中类',width:60,sortable:false}),
	              	j_gridText({field:'specAll',title:'规格',width:100,sortable:false}),
	              	j_gridNumber({field:'actualPrice',title:'单价',formatter:function(value,row){
              			value = value == null || value == undefined || value == '' ? 0 : value;
	              		return value+'元';
	              	},min:0,precision:3,onChange:priceChangeFun,width:80,sortable:false}),
	              	j_gridNumber({field:'purchaseQty',title:'采购数量',formatter:function(value,row){
              			value = value == null || value == undefined || value == '' ? 0 : value;
              			return value+row.unit;
	              	},min:0,precision:3,onChange:purchaseQtyChangeFun,width:80,sortable:false}),
	              	j_gridText({field:'actualFreePercent',title:'赠送比率',width:70,sortable:false}),
	              	j_gridText({field:'totalPrice',title:'总价',formatter:function(value,row){
              			value = value == null || value == undefined || value == '' ? 0 : value;
              			row.totalPrice = value
	              		return value+'元';
	              	},width:100,sortable:false}),
	              	j_gridText({field:'notes',title:'备注',editor:{type:'textbox'},width:70,sortable:false})
			    ]],
			    onBeginEdit:function(index,row){
			    	if(row.notBillIds){
				    	var purchaseQty = $('#listTable').datagrid('getEditor', {
					        index: index,
					        field: 'purchaseQty'
					    });
				    	if(purchaseQty != null){
			    			$(purchaseQty.target).numberspinner('disable');
						}
			    	}
			    },
			    onEndEdit:function(index,row){

			    },
			    view: detailview,
			    detailFormatter:function(index,row){
			    	if(!row.hasOwnProperty('freeInfo') || row.freeInfo.length==0){
						var fixedFreeData = jAjax.submit('/supplyChian/searchFixedFreeMaterialToList.do?relatedId='+row.relatedId+'&requireFarm='+$('#farmId').val()+'&onlyFree=true&mustBeFree=Y');
						row.freeInfo = fixedFreeData;
						$('#freeDiv-'+row.nodeIndex).removeClass('hasChange');
			    	}
			    	
			    	if(!$('#freeDiv-'+row.nodeIndex).hasClass('hasChange')){
			    		row.nodeIndex = nodeIndex;
			    		var detailHtml =  '<div id="freeDiv-'+nodeIndex+'" style="padding:2px"><table class="ddv" id="freeTable-'+nodeIndex+'" style="width:100%;">'+
	                    '<tr><td><button type="button" onclick="addFree('+nodeIndex+','+index+')" class="listTableToolbarBtn-detail">新增</button>'+
         			    '<button type="button" onclick="deleteFree('+nodeIndex+','+index+')" class="listTableToolbarBtn-detail">删除</button></td></tr>' +
         			    '<tr class="detail-row"><td style="display:none;">赠送ID</td><td>赠送物料</td><td>赠送物料规格</td><td>赠送数量</td><td>备注</td></tr>';
			    		if(row.freeInfo != undefined && row.freeInfo != null && row.freeInfo != '' && !$('#freeDiv-'+index).hasClass('hasDelete')){
			    			$.each(row.freeInfo,function(i,info){
			    				// 采购数量的比例
			    				var groupPurchaseMaterialQty = new BigDecimal(info.groupPurchaseMaterialQty);
			    				// 赠料的比例
			    				var groupFreeMaterialQty =  new BigDecimal(info.groupFreeMaterialQty);
			    				// 采购的数量
			    				var groupPurchaseQty;
			    				if(!row.hasOwnProperty('purchaseQty') || !row.purchaseQty){
			    					groupPurchaseQty = new BigDecimal("0");
			    				}else{
			    					groupPurchaseQty = new BigDecimal(row.purchaseQty.toString());
			    				}
			    				info.purchaseQty = groupPurchaseQty.multiply(groupFreeMaterialQty).divide(groupPurchaseMaterialQty,2);
			    				detailHtml += '<tr><td style="display:none;">'+info.materialId+'</td><td>'+info.materialName+'</td><td>'+info.specAll+'</td><td style="display:none">'+info.purchaseQty+'</td><td>'+info.purchaseQty+info.unit+'</td><td>'+info.notes+'</td><td style="display:none">'+info.unit+'</td><td style="display:none">'+info.groupPurchaseMaterialQty+'</td><td style="display:none">'+info.groupFreeMaterialQty+'</td></tr>';
			    			})
			    		}
			    		detailHtml += '</table></div>';
			    		nodeIndex ++;
			    		return detailHtml;
			    	}
                },
                onLoadSuccess:function(data){
                	if(!data.success && data.success != undefined){
        				$.messager.alert({
        					title: '错误',
        					msg: data.errorMsg
        				});
        	    	}else{
        	    		var totalPriceSum = 0;
        	    		$.each(data.rows,function(r,row){
        	    			totalPriceSum += strToFloat(row.totalPrice);
        	    		});
        	    		$('#totalPrice').html(totalPriceSum+'元');
        	    	}
                }
		})
	);
	$('#listTableToolbar').css('display','block');
}
var reFarmChangeFlag = false;
function requireFarmChange(newValue,oldValue){
	if(editIndex==undefined){
		return;
	}
	if(reFarmChangeFlag){
		if(oldValue!=''){
			var row = $('#listTable').datagrid('getRows')[editIndex];
			$.messager.confirm('提示', '改变需求公司会清空赠料，确定吗？', function(r){
				if(r){
					deleteFree(row.nodeIndex, editIndex);
				}else{
					reFarmChangeFlag = false;
					var requireFarm = $('#listTable').datagrid('getEditor', {
				        index: editIndex,
				        field: 'requireFarm'
				    });
			    	if(requireFarm != null){
			    		 $(requireFarm.target).combobox('setValue',oldValue);
			    		 row.requireFarmSortName = $(requireFarm.target).combobox('getText');
			    	}
				}
			});
		}
	}else{
		reFarmChangeFlag = true;
	}
	
}
function initInputingPurchaseBillListTable(){
	searchDetailListUrl = searchInputingEditFromPurchaseDetailUrl;
	$('#listTable').datagrid(
			j_detailGrid({
				toolbar:"#listTableToolbar",
				dbClickEdit:true,
				columns:[[
			      	{field:'ck',checkbox:true},
	              	j_gridText({field:'materialName',title:'物料名称',width:100,sortable:false}),
	              	j_gridText({field:'materialFirstClassName',title:'大类',width:60,sortable:false}),
	            	j_gridText({field:'materialSecondClassName',title:'中类',width:60,sortable:false}),
	              	j_gridText({field:'specAll',title:'规格',width:100,sortable:false}),
	              	j_gridText({field:'arriveQty',title:'已入库数量',formatter:function(value,row){
              			value = value == null || value == undefined || value == '' ? 0 : value;
	              		return value+row.unit;
	              	},width:80,sortable:false}),
	              	j_gridText({field:'purchaseOrFree',title:'购料/赠料',width:100,sortable:false}),
	              	j_gridNumber({field:'actualPrice',title:'单价',formatter:function(value,row){
              			value = value == null || value == undefined || value == '' ? 0 : value;
	              		return value+'元';
	              	},min:0,precision:3,onChange:priceChangeFun,width:80,sortable:false}),
	              	j_gridNumber({field:'purchaseQty',title:'采购数量',formatter:function(value,row){
              			value = value == null || value == undefined || value == '' ? 0 : value;
              			return value+row.unit;
	              	},min:0,precision:3,onChange:inputingPurchaseQtyChangeFun,width:80,sortable:false}),
	              	j_gridText({field:'actualFreePercent',title:'赠送比率',width:70,sortable:false}),
	              	j_gridText({field:'totalPrice',title:'总价',formatter:function(value,row){
              			value = value == null || value == undefined || value == '' ? 0 : value;
	              		return value+'元';
	              	},width:100,sortable:false}),
	              	j_gridText({field:'notes',title:'备注',editor:{type:'textbox'},width:70,sortable:false}),
			    ]],
			    onBeginEdit:function(index,row){
			    	if(row.purchaseOrFree == '赠料'){
				    	var actualPrice = $('#listTable').datagrid('getEditor', {
					        index: index,
					        field: 'actualPrice'
					    });
				    	if(actualPrice != null){
			    			$(actualPrice.target).numberspinner('disable');
						}
			    	}
			    },
			    onEndEdit:function(index,row){
			    	
			    },
                onLoadSuccess:function(data){
                	if(!data.success && data.success != undefined){
        				$.messager.alert({
        					title: '错误',
        					msg: data.errorMsg
        				});
        	    	}else{
        	    		var totalPriceSum = 0;
        	    		$.each(data.rows,function(r,row){
        	    			totalPriceSum += strToFloat(row.totalPrice);
        	    		});
        	    		$('#totalPrice').html(totalPriceSum+'元');
        	    	}
                }
		})
	);
	$('#listTableToolbar').css('display','none');
	$('td[field="_expander"]').remove();
}
function changeSupplier(newValue,oldValue){
	if($('#supplierId').combobox('isValid') && newValue != ''){
		changeSelectRequiredMaterial(newValue);
	}
}

function changeSelectRequiredMaterial(newValue){
	if($('#listTable').css('display') == 'none'){
		$('#listTable').datagrid('loadData',{success:true,total: 0, rows: []});
	}

	$('#totalPrice').html('0元');
}

//选择物料
function selectMaterial(){
	if(!$('#eventCode').combobox('isValid')){
		$.messager.alert('提示','请先选择采购类型！');
		return;
	}
	if(!$('#supplierId').combobox('isValid')){
		$.messager.alert('提示','请先选择有效供应商！');
		return;
	}
	var supplierId = $('#supplierId').combobox('getValue');
	if(supplierId==''){
		$.messager.alert('提示','请先选择供应商！');
	}else{
		leftSilpFun('selectMaterialWin',true,9001);
		var selectMaterialSupplierId = $('#supplier').combobox('getValue');
		// 未改变供应商
		if(selectMaterialSupplierId == supplierId){
				$('#showMaterialName').searchbox('setValue','');
				var supplier = $('#supplier').combobox('getValue');
				var materialFirstClass = $('#showMaterialFirstClass').combobox('getValue');
				var materialSecondClass = $('#showMaterialSecondClass').combobox('getValue');
				
				searchMaterialByConditionToPage(supplier,materialFirstClass,materialSecondClass,'');
		}else{
			$('#supplier').combobox('setValue',supplierId);
		}
		$('#supplier').combobox('readonly');
	}
}

//已入库饲料
function selectFeed(){
	if(!$('#supplierId').combobox('isValid')){
		$.messager.alert('提示','请先选择有效供应商！');
		return;
	}
	
	var supplierId = $('#supplierId').combobox('getValue');
	if(supplierId==''){
		$.messager.alert('提示','请先选择供应商！');
	}else{
		var supplierId = $('#supplierId').combobox('getValue');
		var data = jAjax.submit('/supplyChian/searchFeedStartDateToList.do?supplierId='+supplierId)[0];
		if(data && data.hasOwnProperty('startDate')){
			$('#startDate').datebox('setValue', data.startDate);
			$('#endDate').datebox('setValue', getCurrentDate());
		}else{
			$('#startDate').datebox('setValue', getCurrentDate());
			$('#endDate').datebox('setValue', getCurrentDate());
		}
		leftSilpFun('SelectMaterialFeedWin',true,9001);
	}
}

//单价改变方法
function priceChangeFun(newValue,oldValue){
	if(editIndex == undefined){
		return;
	}
	var row = $('#listTable').datagrid('getRows')[editIndex];
	if(row.purchaseQty != undefined){
		row.totalPrice = (strToFloat(newValue)*strToFloat(row.purchaseQty)).toFixed(2);
		changeTableDisplayValue('listTable',editIndex,[{
			field:'totalPrice',
			value:row.totalPrice
		}]);

		var totalPriceSum = 0;
		var rows = $('#listTable').datagrid('getRows');
		$.each(rows,function(i,item){
			if(item.totalPrice != undefined){
				totalPriceSum += strToFloat(item.totalPrice);
			}
		})
		$('#totalPrice').html(totalPriceSum.toFixed(2)+'元');
	}
}

//初始订单中采购数量改变方法
function purchaseQtyChangeFun(newValue,oldValue){
	if(editIndex == undefined){
		return;
	}
	var row = $('#listTable').datagrid('getRows')[editIndex];
	var flag = false;
	if(row.actualPrice != undefined){
		row.totalPrice = (strToFloat(newValue)*strToFloat(row.actualPrice)).toFixed(2);
		changeTableDisplayValue('listTable',editIndex,[{
			field:'totalPrice',
			value:row.totalPrice
		}]);
		flag = true;
		var totalPriceSum = 0;
		var rows = $('#listTable').datagrid('getRows');
		$.each(rows,function(i,item){
			if( item.totalPrice != undefined){
				totalPriceSum += strToFloat(item.totalPrice);
			}
		})
		$('#totalPrice').html(totalPriceSum.toFixed(2)+'元');
	}
	if(row.actualFreePercent != undefined){
		flag = true;
	}
	
	if(flag){
		focusEditCell('listTable',editIndex,'purchaseQty');
	}
}

//入库中采购数量改变方法
function inputingPurchaseQtyChangeFun(newValue,oldValue){
	if(editIndex == undefined){
		return;
	}
	var row = $('#listTable').datagrid('getRows')[editIndex];
	var flag = false;
	if(row.actualPrice != undefined){
		row.totalPrice = (strToFloat(newValue)*strToFloat(row.actualPrice)).toFixed(2);
		changeTableDisplayValue('listTable',editIndex,[{
			field:'totalPrice',
			value:row.totalPrice
		}]);
		flag = true;
		var totalPriceSum = 0;
		var rows = $('#listTable').datagrid('getRows');
		$.each(rows,function(i,item){
			if( item.totalPrice != undefined){
				totalPriceSum += strToFloat(item.totalPrice);
			}
		})
		$('#totalPrice').html(totalPriceSum.toFixed(2)+'元');
	}
	if(row.actualFreePercent != undefined){
		flag = true;
	}
	
	if(flag){
		focusEditCell('listTable',editIndex,'purchaseQty');
	}
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
						$('#totalPrice').html('0元');
					}else{
						var totalPriceSum = 0;
						$.each(listRows,function(i,row){
							totalPriceSum += strToFloat(row.totalPrice == undefined ? 0 : row.totalPrice);
						});
						$('#totalPrice').html(totalPriceSum+'元');
					}
				}
			}
		});
	}
}

//清空
function detailClear(){
	var listTable = $('#listTable');
	$.messager.confirm('提示', '确定要清空吗？', function(r){
		if (r){
			$('#listTable').datagrid('loadData',{success:true,total: 0, rows: [] });
			$('#totalPrice').html('0元');
		}
	});
}

//新增
function onBtnAddSelf(){
	$('#billDate').datebox('readonly',false);
	$('#arriveDate').datebox('readonly',false);
	initPurchaseBillListTable();
	nodeIndex = 0;
	onBtnAdd();
	$('#editForm').form('reset');
	$('#billDate').datebox('setValue',getCurrentDate());
	var date = new Date();
	date.setDate(date.getDate()+7);
	var dateString = date.format("yyyy-MM-dd");
	$('#arriveDate').datebox('setValue',dateString);
	$('#supplierId').combobox('readonly',false);
	$('#checkboxDiv').css('visibility','visible');
	
	$('#rowId').val(0);
	
	//采购类型
	xnCdCombobox({
		id:'eventCode',
		typeCode:'PURCHASE_BILL_TYPE',
		required:true,
		onChange:changeEventCode
	});
	
	if(purchaseFlag == '1'){
		$('#eventCode').combobox('readonly', false);
	}else if(purchaseFlag == '2'){
		$('#eventCode').combobox('setValue', 'FEED_PURCHASE');
		$('#eventCode').combobox('readonly', true);
	}else if(purchaseFlag == '3'){
		$('#eventCode').combobox('setValue', 'MATERIAL_PURCHASE');
		$('#eventCode').combobox('readonly', true);
	}else{
		$('#eventCode').combobox('setValue', '');
		$('#eventCode').combobox('readonly', true);
	}
	
	saveFlag = 'insert';
}
function addFree(nodeIndex,index){
	 $('#listTable').datagrid('endEdit',index);
	var row = $('#listTable').datagrid("getRows")[index];
	
	$('#freeQtyPanel').dialog({
		modal:true
	});
	$('#freeQtyPanel').dialog('open');
	$('#freeInfoForm').form('reset');
	$('#billIndex').val(index);
	$('#nodeIndex').val(nodeIndex);
	
	var freeMaterialUrl = '/supplyChian/searchFixedFreeMaterialToList.do?relatedId='+row.relatedId+'&requireFarm='+$('#farmId').val()+'&onlyFree=true&mustBeFree=N';
	//赠送物料
	xnComboGrid({
		id:'freeMaterialId',
		idField:'rowId',
		textField:'materialName',
		url:freeMaterialUrl,
		editable:false,
		required:true,
		columns:[[ 	
					{field:'rowId',title:'ID',width:100,hidden:true},
					{field:'materialName',title:'物料名称',width:100},
			        {field:'materialSecondClassName',title:'中类',width:100},
			        {field:'specAll',title:'规格',width:100}
			    ]],
	    onSelect:function(indexMaterial,row){
	    	$('#freeMaterialId').val(row.rowId);
	    	$('#freeMaterialName').val(row.materialName);
	    	$('#specAll').val(row.specAll);
	    	$('#freeUnit').val(row.unit);
	    }
	});
}
function freeQtyInfoSure(){
	var isValid = $('#freeInfoForm').form('validate');
	if(isValid){
		var freeMaterialId = $('#freeMaterialId').val(),
		freeQty = $('#freeQty').val();
		freeUnit = $('#freeUnit').val();
		freeMaterialName = $('#freeMaterialName').val(),
		specAll = $('#specAll').val();
		notes = $('#freeNotes').val();
		var billIndex = $('#billIndex').val();
		var nodeIndex = $('#nodeIndex').val();

    	var row = $('#listTable').datagrid('getRows')[billIndex];
    	var detail = new Object();
    	detail.materialId=freeMaterialId;

    	if(row.hasOwnProperty('freeInfo') && row.freeInfo){
    		row.freeInfo.push(detail);
    	}else{
    		row.freeInfo = [];
    		row.freeInfo.push(detail);
    	}

		var tdInfo = {};
		$('#freeDiv-'+nodeIndex).addClass('hasChange');
		var html = '<tr><td style="display:none;">'+freeMaterialId+'</td><td>'+freeMaterialName+'</td><td>'+specAll+'</td><td style="display:none">'+freeQty+'</td><td>'+freeQty+freeUnit+'</td><td>'+notes+'</td><td style="display:none">'+freeUnit+'</td><td style="display:none;"></td><td style="display:none;"></td></tr>';
		$('#freeTable-'+nodeIndex).append(html);
		$('#listTable').datagrid('fixDetailRowHeight',billIndex);
		$('#freeQtyPanel').dialog('close');
	}
}

function deleteFree(nodeIndex,index){
	var row = $('#listTable').datagrid('getRows')[index];
	row.freeInfo = [];
	
	$('#freeDiv-'+nodeIndex).addClass('hasChange');
	$('#freeDiv-'+nodeIndex).addClass('hasDelete');
	$('#freeDiv-'+nodeIndex).empty();
	var html = '<table class="ddv" id="freeTable-'+nodeIndex+'" style="width:100%;">'+
    	'<tr><td><button type="button" onclick="addFree('+nodeIndex+','+index+')" class="listTableToolbarBtn-detail">新增</button>'+
	    '<button type="button" onclick="deleteFree('+nodeIndex+','+index+')" class="listTableToolbarBtn-detail">删除</button></td></tr>' +
	    '<tr class="detail-row"><td style="display:none;">赠送ID</td><td>赠送物料</td><td>赠送物料规格</td><td>赠送数量</td><td>备注</td></tr>'+'</table>';
	$('#freeDiv-'+nodeIndex).append(html);
	$('#listTable').datagrid('fixDetailRowHeight',index);
}
function dialogCancel(id){
	$('#'+ id).dialog('close');
}
function collectDetailData(){
	var listTable = $('#listTable');
	var rows =  listTable.datagrid('getRows');
	for(var i = 0 ; i < rows.length ; i ++){
		listTable.datagrid('endEdit',i);
	}
	var queryParams;
	//明细表获取数据
	var rowId;
	rowId=document.getElementById('rowId');
	var rowIdValue = rowId.value;
	if(rowIdValue == '' || rowIdValue == 0){
		if(rows.length < 1){
			return null;
		}
		//获取新增行
		var insertRows = listTable.datagrid('getRows');
		//给提交数据加上行号、赠送明细
		$.each(insertRows,function(index,data){
			insertRows[index].lineNumber = listTable.datagrid('getRowIndex',data)+1;
			var freeDiv_tr = $('#freeDiv-'+data.nodeIndex).find('tr');
			var freeInfo = [];
			$.each(freeDiv_tr,function(t,tr){
				if(t > 1){
					var freeDiv_td = $(tr).find('td');
					var tdInfo = {};
					$.each(freeDiv_td,function(d,td){
						if(d == 0){
							tdInfo.materialId = $(td).html();
						}
						if(d == 1){
							tdInfo.materialName = $(td).html();
						}
						if(d == 2){
							tdInfo.specAll = $(td).html();
						}
						if(d == 3){
							tdInfo.purchaseQty = $(td).html();
						}
						if(d == 5){
							tdInfo.notes = $(td).html();
						}
						if(d == 6){
							tdInfo.unit = $(td).html();
						}
						if(d == 7){
							tdInfo.groupPurchaseMaterialQty = $(td).html();
						}
						if(d == 8){
							tdInfo.groupFreeMaterialQty = $(td).html();
						}
					});
					freeInfo.push(tdInfo);
				}
			});
			insertRows[index].freeInfo = freeInfo;
		})
		var jsonString = JSON.stringify(insertRows);
		
		$('#status').val(1);
		
		queryParams = {
				deletedFlag:0,
				gridList:jsonString
			};
	}else{
		// 现存总行数
		var existsRows = listTable.datagrid('getRows').length;
		
		var upDataRows = listTable.datagrid('getRows');
		//获取删除行
		var deleteRows = listTable.datagrid('getChanges','deleted');
		//合并行
		var editRows = upDataRows.concat(deleteRows);
		//给提交数据加上行号
		$.each(editRows,function(index,data){
			editRows[index].lineNumber = listTable.datagrid('getRowIndex',data)+1;
			var freeDiv_tr = $('#freeDiv-'+data.nodeIndex).find('tr');
			var freeInfo = [];
			$.each(freeDiv_tr,function(t,tr){
				if(t > 1){
					var freeDiv_td = $(tr).find('td');
					var tdInfo = {};
					$.each(freeDiv_td,function(d,td){
						if(d == 0){
							tdInfo.materialId = $(td).html();
						}
						if(d == 1){
							tdInfo.materialName = $(td).html();
						}
						if(d == 2){
							tdInfo.specAll = $(td).html();
						}
						if(d == 3){
							tdInfo.purchaseQty = $(td).html();
						}
						if(d == 5){
							tdInfo.notes = $(td).html();
						}
						if(d == 6){
							tdInfo.unit = $(td).html();
						}
						if(d == 7){
							tdInfo.groupPurchaseMaterialQty = $(td).html();
						}
						if(d == 8){
							tdInfo.groupFreeMaterialQty = $(td).html();
						}
					});
					freeInfo.push(tdInfo);
				}
			});
			editRows[index].freeInfo = freeInfo;
		})
		var jsonStr = JSON.stringify(editRows);
		
		var status = $('#status').val();
		if(status == '1' || status == '2'){
			$('#status').val(1);
		}
		
		queryParams = {
				deletedFlag:0,
				existsRows:existsRows,
				gridList:jsonStr
			};
	}
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
	//判断是否有明细表
	if(listTable!= null){
		queryParams = collectDetailData();
		if(queryParams == null){
			$.messager.alert('提示','前台提示--未添加明细不能提交！');
			return;
		}
	}else{
		queryParams = {
			status:1,
			deletedFlag:0,
		};
	}
	
	if(saveFlag=='insert'){
		saveUrl = prUrl+'editSelfPurchaseStore.do?editType=insert';
	}else if(saveFlag=='update'){
		saveUrl = prUrl+'editSelfPurchaseStore.do?editType=update';
	}else if(saveFlag=='inputingUpdate'){
		saveUrl = prUrl+'editInputingPurchaseStore.do';
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
					leftSilpFun('preSaveRecord',true,9002);
					if(preRecord.success == undefined){
						preRecord.success = true;
					}
					$('#preSaveRecordTable').datagrid('loadData',preRecord);
					if(listTable!= null){
						$('#listTable').datagrid('loadData',{success:true,total: 0, rows: [] });
					}
				}
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

//修改
function onBtnEdit(){
	nodeIndex = 0;
	var record = $('#table').datagrid('getSelections');
	var length = record.length;
	if(length < 1){
		$.messager.alert('警告','请选择一条数据！');
	}else if(length > 1){
		$.messager.alert('警告','只能选择一条数据！');
	}else{
		var index = $('#table').datagrid('getRowIndex',record[0]);
		editPurchaseBill(index,record[0]);
	}
}

function editPurchaseBill(index,row){
	var winTitle = '编辑';
	$('#editForm').form('reset');
	
	//采购类型
	xnCdCombobox({
		id:'eventCode',
		typeCode:'PURCHASE_BILL_TYPE',
		required:true,
		readonly:true,
		onChange:changeEventCode
	});
	
	winTitle += '物料';
	$('#billDate').datebox('readonly',false);
	$('#arriveDate').datebox('readonly',false);
	$('#status').val(row.status);
	if(row.status == '1' || row.status == '2'){
		saveFlag = 'update';
		initPurchaseBillListTable();
	}
	if(row.status == '3' || row.status == '4' || row.status == '5' || row.status == '8' || row.status == '9'){
		saveFlag = 'inputingUpdate';
		$('#billDate').datebox('readonly');
		$('#arriveDate').datebox('readonly');
		initInputingPurchaseBillListTable();
	}
	if(row.status == '6'){
		$.messager.alert('错误','已经作废，无法修改！！');
		return;
	}
	if(row.status == '7'){
		$.messager.alert('错误','已经订单完成，无法修改！');
		return;
	}

	$('#supplierId').combobox('readonly');
	$('#supplierId').combobox('loadData', jAjax.submit('/supplyChian/searchCompanyFromRequireBillToList.do?searchType=All&purchaseFlag='+purchaseFlag+'&canPurchaseSupplierId='+canPurchaseSupplierId));
	
	$('#checkboxDiv').css('visibility','hidden');
	
	$('#editWin').window({
		title:winTitle + title
	});
	$('#editWin').window('open');
	$('#editForm').form('load',row);
	var listTable = document.getElementById('listTable');
	if(listTable!=null){
		$('#listTable').datagrid('load',basicUrl+searchDetailListUrl+'?purchaseId='+row.rowId+'&eventCode='+$('#eventCode').combobox('getValue'));
	}
}
// 作废
var scrapIds = [];
function onBtnScrap(){
	scrapIds = [];
	var record = $('#table').datagrid('getSelections');
	var length = record.length;
	if(length < 1){
		$.messager.alert('警告','请选择一条数据！');
	}else{
		var ids = [];
		var errorFlag = '0';
		$.each(record,function(index,data){
			if(data.status == '4'){
				var dataIndex = $('#table').datagrid('getRowIndex',data)+1;
				$.messager.alert('错误','第'+dataIndex+'行入库中，无法作废！');
				$.messager.progress('close');
				errorFlag = '1';
				return false;
			}
			if(data.status == '5'){
				var dataIndex = $('#table').datagrid('getRowIndex',data)+1;
				$.messager.alert('错误','第'+dataIndex+'行已完成入库，无法作废！');
				$.messager.progress('close');
				errorFlag = '1';
				return false;
			}
			if(data.status == '6'){
				var dataIndex = $('#table').datagrid('getRowIndex',data)+1;
				$.messager.alert('错误','第'+dataIndex+'行已作废，无法作废！');
				$.messager.progress('close');
				errorFlag = '1';
				return false;
			}
			if(data.status == '7'){
				var dataIndex = $('#table').datagrid('getRowIndex',data)+1;
				$.messager.alert('错误','第'+dataIndex+'行已订单完成，无法作废！');
				$.messager.progress('close');
				errorFlag = '1';
				return false;
			}
			if(data.status == '8'){
				var dataIndex = $('#table').datagrid('getRowIndex',data)+1;
				$.messager.alert('错误','第'+dataIndex+'行已订单完结，无法作废！');
				$.messager.progress('close');
				errorFlag = '1';
				return false;
			}
			ids.push(data.rowId);
			scrapIds = ids;
		});
		if(errorFlag=='0'){
			$('#scrapReasonWin').dialog({
				modal:true
			});
			$('#scrapReasonWin').dialog('open');
			$('#scrapReason').textbox('reset');
		}
	}
}
function scrapReasonEnter(){
	$.messager.progress();	
	var scrapReason = $('#scrapReason').textbox('getValue');
	jAjax.submit(scrapUrl,{'ids':scrapIds,scrapReason:scrapReason}
	,function(response){
		$.messager.alert('提示','作废成功！');
		$('#table').datagrid('reload');
		$('#scrapReasonWin').dialog('close');
		$.messager.progress('close');
	}
	,function(response){
		jAjax.errorFunc(response.errorMsg);
		$('#scrapReasonWin').dialog('close');
		$.messager.progress('close');
	},null,true,null,null);
}
function onBtnFinish(){
	var record = $('#table').datagrid('getSelections');
	var length = record.length;
	if(length < 1){
		$.messager.alert('警告','请选择一条数据！');
	}else if(length > 1){
		$.messager.alert('警告','只能选择一条数据！');
	}else{
		if(record[0].status != '5' && record[0].status != '8'){
			$.messager.alert('错误','【完成入库】和【完结】状态才能进行【订单完成】操作！！');
			return;
		}
		$.messager.progress();	
		jAjax.submit(finishUrl,{'rowId':record[0].rowId}
		,function(response){
			$.messager.alert('提示','操作成功！');
			$('#table').datagrid('reload');
			$.messager.progress('close');
		}
		,function(response){
			jAjax.errorFunc(response.errorMsg);
			$.messager.progress('close');
		},null,true,null,null);
	}
}

function rightSlipAll(){
	rightSlipFun('purchaseBillDetailId',780);
	rightSlipFun('inputInfoDetailId',390);
}

function onBtnOver(){
	var record = $('#table').datagrid('getSelections');
	var length = record.length;
	if(length < 1){
		$.messager.alert('警告','请选择一条数据！');
	}else{
		var ids = [];
		var errorFlag = '0';
		$.each(record,function(index,data){
			if(data.status == '6'){
				var dataIndex = $('#table').datagrid('getRowIndex',data)+1;
				$.messager.alert('错误','第'+dataIndex+'行已作废，无法作废！');
				$.messager.progress('close');
				errorFlag = '1';
				return false;
			}
			if(data.status == '7'){
				var dataIndex = $('#table').datagrid('getRowIndex',data)+1;
				$.messager.alert('错误','第'+dataIndex+'行已订单完成，无法作废！');
				$.messager.progress('close');
				errorFlag = '1';
				return false;
			}
			if(data.status == '8'){
				var dataIndex = $('#table').datagrid('getRowIndex',data)+1;
				$.messager.alert('错误','第'+dataIndex+'行已订单完结，无法作废！');
				$.messager.progress('close');
				errorFlag = '1';
				return false;
			}
			ids.push(data.rowId);
		});
		if(errorFlag=='0'){
			$.messager.progress();
			jAjax.submit(overUrl,{'ids':ids}
			,function(response){
				$.messager.alert('提示','完结成功！');
				$('#table').datagrid('reload');
	    		$.messager.progress('close');
			}
			,function(response){
	    		jAjax.errorFunc(response.errorMsg);
	    		$.messager.progress('close');
			},null,true,null,null);
		}
	}
}

function getPurchaserInfo(){
	var data = jAjax.submit('/supplyChian/searchPurchaseTypeByEmployeeIdToList.do')[0];
	purchaseFlag = data.purchaseType;
	if(data.canPurchaseSupplierId){
		canPurchaseSupplierId = data.canPurchaseSupplierId;
	}
	if(purchaseFlag != '1' && purchaseFlag != '2' && purchaseFlag != '3'){
		// 非采购人员进入此页面
		// 是页面崩溃，无法操作
		$('#eventCode').combobox('11122', '');
	}
}

/**
 * 高级查询搜索
 */
function onBtnSearch(para,url){
	var winId = $(para).parent().parent().attr('id');
    rightSlipFun(winId,390);
	 $("#table").datagrid('load',form2Json("searchForm"));
}

/**
 * 将表单数据转为json
 */
function form2Json(id) {
   var arr = $("#" + id).serializeArray()
   var jsonStr = "";
   jsonStr += '{';
   for (var i = 0; i < arr.length; i++) {
	   if(arr[i].name != 'searchStatus'){
		   jsonStr += '"' + arr[i].name + '":"' + arr[i].value + '",'
	   }
   }

   var searchStatus = '';
   var items = $('input[name="searchStatus"]');
	$.each(items,function(index,data){
		searchStatus += data.value;
		if(index != items.length-1){
			searchStatus += ",";
		}
	});
   jsonStr += '"searchStatus":"' + searchStatus + '",'
   jsonStr = jsonStr.substring(0, (jsonStr.length - 1));
   jsonStr += '}'
   var json = JSON.parse(jsonStr)
   return json
}