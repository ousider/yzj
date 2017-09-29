var title = '发票查询';
var prUrl='/supplyChian/';
var searchMainListUrl = prUrl + 'searchReceiptDetailListToPage.do';
var searchInvoiceTableDetialToListUrl = prUrl + 'searchInvoiceTableDetialToList.do';
var searchListTableDetialToListUrl = prUrl + 'searchListTableDetialToList.do';
var searchReceiptFillInInputDetailToListUrl = prUrl + 'searchReceiptFillInInputDetailToList.do';
var saveUrl = prUrl + 'editReceiptFillIn.do'
var invoiceTableEditIndex = undefined;
$(document).ready(function(){
	
	/**
	 * 主表加载
	 */
	
	$('#table').datagrid(
			j_grid_view({
				toolbarList:"#tableToolbarList",
				url:searchMainListUrl,
				columnFields:'rowId,billDate,supplierName,receiptNumber,companyName,farmSortName,price,transportPrice',
				columnTitles:'ID,上传日期,供应商,发票号,公司,猪场,金额,运费',
				hiddenFields:'rowId',
				formatterFields:'receiptNumber',
				formatter:[linkReceipt],
				onDblClickRowFun:showReceiptBillMaterialDetail
			},'table')
	);
	
	// 高级搜索部分隐藏项
	$('#hiddenItem').css('display','none');
	
	//高级查询 状态
	xnCdCombobox({
		id:'searchModel',
		typeCode:'RECEIPT_SEARCH_MODEL',
		editable:false,
		onChange:searchModelOnChange
	});
	
	//高级查询公司
	xnCombobox({
		id:"companyId",
		url:'/supplyChian/searchCompanyToList.do',
		valueField:"companyId",
		textField:"companyName",
		editable:true,
		hasAll:true
	});
	
	//高级查询猪场
	xnCombobox({
		id:"farmId",
		url:'/supplyChian/searchCompanyByFarmIdToList.do?hasOwnFlag=true',
		valueField:"farmId",
		textField:"farmSortName",
		editable:true,
		hasAll:true
	});

	//高级查询供应商
	xnCombobox({
		id:"supplierId",
		url:'/supplyChian/searchCompanyFromRequireBillToList.do?searchType=All&purchaseFlag=1&canPurchaseSupplierId=0',
		valueField:"supplierId",
		textField:"supplierSortName",
		editable:true,
		hasAll:true
	});
	
	setTimeout(function(){
		$('#receiptBillMaterialDetailTable').datagrid(
				j_grid_view({
					haveCheckBox:false,
					columnFields:'farmSortName,purchaseBillDate,purchaseBillCode,inputBillDate,inputBillCode,materialName,materialFirstClassName,materialSecondClassName,specAll,purchaseOrFree,unit,thisReceiptQtyInputQty,actualPrice,totalPrice',
					columnTitles:'猪场,订单日期,订单单号,入库日期,入库单号,物料,大类,中类,规格,购/赠,单位,开票量/入库量,单价,金额',
					columnWidths:'60,80,80,80,80,150,60,60,100,50,50,110,80,100',
					hiddenFields:'materialFirstClassName,purchaseBillDate,purchaseBillCode,inputBillDate,inputBillCode',
					fit:false,
					pagination:false,
					height:'100%',
					width:'100%',
				},'receiptBillMaterialDetailTable'));
		},500)
		
});
/**
 * 发票模式的发票超链接
 */
function linkReceipt(value,rowData,rowIndex){
	//添加超链接 
	return '<a href="javascript:void(0);" class="editcls" onclick="showReceiptIMG(\''+rowData.fileName+'\');">'+value+'</a>';  
}

/**
 * 物料模式的发票超链接
 */
function linkReceiptMaterial(value,rowData,rowIndex){
	//添加超链接 
	var receiptContext='';
	$.each(rowData.receiptInfo,function(i,item){
		receiptContext +='<a href="javascript:void(0);" class="editcls" onclick="showReceiptIMG(\''+item.fileName+'\');">'+item.receiptNumber+'</a>&nbsp;&nbsp;';
	})
	return receiptContext;  
}

function searchModelOnChange(newValue, oldValue){
	if(newValue == "1"){
		searchMainListUrl = prUrl + 'searchReceiptDetailListToPage.do';
		$('#table').datagrid(
				j_grid_view({
					toolbarList:"#tableToolbarList",
					url:searchMainListUrl,
					columnFields:'rowId,billDate,supplierName,receiptNumber,companyName,farmSortName,price,transportPrice',
					columnTitles:'ID,上传日期,供应商,发票号,公司,猪场,金额,运费',
					hiddenFields:'rowId',
					formatterFields:'receiptNumber',
					formatter:[linkReceipt],
					onDblClickRowFun:showReceiptBillMaterialDetail
				},'table')
		);
		
		$('#hiddenItem').css('display','none');
	}else if(newValue == "2"){
		searchMainListUrl = prUrl + 'searchReceiptMaterialDetailListToPage.do?searchModel='+newValue;
		$('#table').datagrid(
				j_grid_view({
					toolbarList:"#tableToolbarList",
					url:searchMainListUrl,
					columnFields:'rowId,inputBillDate,inputBillCode,materialName,specAll,unit,receiptQtyInputQty,actualPrice,totalPrice,supplierName,companyName,farmSortName,receiptNumber',
					columnTitles:'ID,入库日期,入库单号,物料名称,规格,单位,开票量/入库量,单价,总价,供应商,公司,猪场,发票号',
					columnWidths:'80,80,80,200,100,50,95,55,80,165,165,60,100',
					hiddenFields:'rowId',
					formatterFields:'receiptNumber',
					formatter:[linkReceiptMaterial],
					onDblClickRowFun:function(){return;}
				},'table')
		);
		
		$('#hiddenItem').css('display','block');
	}else if(newValue == "3"){
		searchMainListUrl = prUrl + 'searchReceiptMaterialDetailListToPage.do?searchModel='+newValue;
		$('#table').datagrid(
				j_grid_view({
					toolbarList:"#tableToolbarList",
					url:searchMainListUrl,
					columnFields:'rowId,supplierName,companyName,farmSortName,inputBillDate,inputBillCode,materialName,specAll,unit,receiptQtyInputQty,actualPrice,totalPrice,receiptNumber',
					columnTitles:'ID,供应商,公司,猪场,入库日期,入库单号,物料名称,规格,单位,开票量/入库量,单价,总价,发票号',
					columnWidths:'80,165,165,60,80,80,200,80,50,95,55,60,100',
					hiddenFields:'rowId',
					formatterFields:'receiptNumber',
					formatter:[linkReceiptMaterial],
					onDblClickRowFun:function(){return;}
				},'table')
		);
		
		$('#hiddenItem').css('display','block');
	}
}

function showReceiptBillMaterialDetail(index,row){
	$('#receiptBillMaterialDetailTable').datagrid('load',basicUrl + searchListTableDetialToListUrl + '?receiptId='+row.receiptId);
	leftSilpFun('receiptBillMaterialDetail');
}

function invoiceDetailAdd(){
	$('#invoiceTable').edatagrid('addRow');
}
function invoiceDetailDelete(index){
	$('#invoiceTable').edatagrid('editRow',index).edatagrid('cancelRow',index).datagrid('refreshRow',index);
}

function showReceiptIMG(fileName){
	var picSrc = [fileName];
	imgPreviewFun(picSrc);
}

function imgPreviewFun(picSrc){
	$('#imgPreviewDIV').empty();
	$('#imgPreviewDIV').append('<ul id="imgPreviewUl"></ul>');
	if(picSrc != "undefined" && picSrc != undefined){
		var base_url = $('#base_url').val();
		for(var i = 0; i < picSrc.length ; i++){
			$('#imgPreviewUl').append('<li style="display:none;"><img id="imgPreview'+i+'" class="img-preveiw" alt="附件预览"/></li>'); 
			if(picSrc[i].substring(0,5) == 'data:'){
				document.getElementById('imgPreview'+i).setAttribute("src",picSrc[i]); 
			}else{
				document.getElementById('imgPreview'+i).setAttribute("src",base_url + picSrc[i]); 
			}
		}
	}
	
	$('#imgPreviewUl').viewer({
		inline:true
	});
	
	$('#imgPreviewPanel').dialog('open');
}
function dialogCancel(dialog){
	$('#'+dialog).dialog('close');
}

function supplierIdChange(newValue, oldValue){
	if(!$('#supplierId').combobox('isValid') || !$('#farmId').combobox('isValid')){
		$('#listTable').datagrid('loadData',{success:true,total: 0, rows: [] });
		return
	}
	
	var farmId = $('#farmId').combobox('getValue');
	if($('#rowId').val()==0){
		$('#listTable').datagrid('load', basicUrl+searchReceiptFillInInputDetailToListUrl+'?supplierId='+newValue+'&farmId='+farmId);
	}
}

function farmIdChange(newValue, oldValue){
	if(!$('#supplierId').combobox('isValid') || !$('#farmId').combobox('isValid')){
		$('#listTable').datagrid('loadData',{success:true,total: 0, rows: [] });
		return
	}
	
	var supplierId = $('#supplierId').combobox('getValue');
	if($('#rowId').val()==0){
		$('#listTable').datagrid('load', basicUrl+searchReceiptFillInInputDetailToListUrl+'?supplierId='+supplierId+'&farmId='+newValue);
	}
}

function countTotalMaterialPrice(){
	var record = $('#listTable').datagrid('getSelections');
	var totalMaterialPrice = 0;
	$.each(record,function(i,item){
		if( item.totalPrice != undefined){
			totalMaterialPrice += strToFloat(item.totalPrice);
		}
	})
	$('#totalMaterialPrice').numberspinner('setValue', totalMaterialPrice.toFixed(2));
}

function onBtnAdd(){
	$('#editWin').window({title:'新增'+ title});
	$('#editWin').window('open');
	$('#editForm').form('reset');
	$('#btnSave').css('display','inline-block');
	
	$('#billDate').datebox('setValue',getCurrentDate());
	$('#rowId').val(0);
//	$('#groupName').textbox('readonly', false);
//	$('#supplierId').combobox('readonly', false);
//	$('#groupModel').combobox('readonly', false);
	$('#invoiceTable').datagrid('loadData',{success:true,total: 0, rows: [] });
	$('#listTable').datagrid('loadData',{success:true,total: 0, rows: [] });
}

function showReceiptBillDetail(index,row){
	$('#editWin').window({title:'查看'+ title});
	$('#editWin').window('open');
	$('#editForm').form('reset');
	$('#btnSave').css('display','none');
	
	$('#editForm').form('load', row);
	
	$('#invoiceTable').datagrid('load', basicUrl + searchInvoiceTableDetialToListUrl + '?receiptId='+row.rowId);
	$('#listTable').datagrid('load',basicUrl + searchListTableDetialToListUrl + '?receiptId='+row.rowId);
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
				$('#editWin').window('close');
				$.messager.alert('提示','保存成功！');
				
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

	var rowIdValue=$('#rowId').val();
	
	if(rowIdValue == '' || rowIdValue == 0){
		if(rows.length < 1){
			return null;
		}
		
		// 发票数据
		var receiptRecord = $('#invoiceTable').datagrid('getRows');
		
		for(var i = 0 ; i < receiptRecord.length ; i ++){
			$('#invoiceTable').datagrid('endEdit',i);
		}
		
		//给提交数据加上行号
		$.each(receiptRecord,function(index,data){
			receiptRecord[index].lineNumber = index+1;
		})
		var receiptRecordJsonString = JSON.stringify(receiptRecord);
		
		// 入库数据
		var listTableRecord = $('#listTable').datagrid('getSelections');
		//给提交数据加上行号
		$.each(listTableRecord,function(index,data){
			listTableRecord[index].lineNumber = index+1;
		})
		var listTableRecordJsonString = JSON.stringify(listTableRecord);
		queryParams = {
				status:1,
				deletedFlag:0,
				listTableRecord:listTableRecordJsonString,
				receiptRecord:receiptRecordJsonString
			};
	}else{
		// 现存总行数
		var existsRows = listTable.datagrid('getRows').length;
		//获取新增行
		//var insertRows = listTable.datagrid('getChanges','inserted');
		//获取更新行
		//var upDataRows = listTable.datagrid('getChanges','updated');
		var upDataRows = listTable.datagrid('getRows');
		//获取删除行
		var deleteRows = listTable.datagrid('getChanges','deleted');
		//合并行
		var editRows = upDataRows.concat(deleteRows);
		//给提交数据加上行号
		$.each(editRows,function(index,data){
			editRows[index].lineNumber = listTable.datagrid('getRowIndex',data)+1;
		})
		var jsonStr = JSON.stringify(editRows);
		queryParams = {
				status:1,
				deletedFlag:0,
				existsRows:existsRows,
				gridList:jsonStr
			};
	}
	return queryParams;
}