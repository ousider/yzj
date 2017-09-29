var editIndex = undefined;
var title = '发票填写';
var prUrl='/supplyChian/';
var searchMainListUrl = prUrl + 'searchReceiptBillToPage.do';
var searchInvoiceTableDetialToListUrl = prUrl + 'searchInvoiceTableDetialToList.do';
var searchListTableDetialToListUrl = prUrl + 'searchListTableDetialToList.do';
var searchReceiptFillInInputDetailToListUrl = prUrl + 'searchReceiptFillInInputDetailToList.do';
var saveUrl = prUrl + 'editReceiptFillIn.do'
var invoiceTableEditIndex = undefined;
var purchaseFlag = '1';
$(document).ready(function(){
	
	// 判断登陆的角色可以购买哪些饲料
	materialOrFreePurchase();

	/**
	 * 主表加载
	 */
	
	$('#table').datagrid(
			j_grid_view({
				toolbarList:"#tableToolbarList",
				url:searchMainListUrl,
				columnFields:'rowId,billDate,supplierName,receiptBranchName,requireFarmSortName,notes',
				columnTitles:'ID,上传日期,供应商,公司（法人）,猪场,备注',
				hiddenFields:'rowId',
				onDblClickRowFun:showReceiptBillDetail
			},'table')
	);
	
	$('#listTable').datagrid(
			j_detailGrid({
				haveCheckBox:true,
				toolbar:'listTableTableToolbarList',
//				columnFields:'purchaseBillDate,purchaseBillCode,inputBillDate,inputBillCode,materialName,materialSecondClassName,specAll,unit,purchaseOrFree,inputQty,receiptQty,actualPrice,totalPrice',
//				columnTitles:'订单日期,订单单号,入库日期,入库单号,物料名称,中类,规格,计量单位,购/赠,入库数量,发票数量,单价,金额',
//				columnWidths:'80,80,80,80,200,100,100,150,100,80,100,100,100',
//				hiddenFields:'',
//				fit:false,
//				pagination:false,
				height:'100%',
				width:'100%',
				columns:[[
			      	{field:'ck',checkbox:true},
			      	j_gridText({field:'farmSortName',title:'猪场',width:50}),
	              	j_gridText({field:'purchaseBillDate',title:'订单日期',width:80,sortable:true}),
	             	j_gridText({field:'purchaseBillCode',title:'订单单号',width:80,sortable:true}),
	             	j_gridText({field:'inputBillDate',title:'入库日期',width:80,sortable:true}),
	              	j_gridText({field:'inputBillCode',title:'入库单号',width:80,sortable:true}),
	              	j_gridText({field:'materialName',title:'物料名称',width:200,sortable:true}),
	              	j_gridText({field:'materialSecondClassName',title:'中类',width:60}),
	              	j_gridText({field:'specAll',title:'规格',width:100}),
	              	j_gridText({field:'unit',title:'单位',width:40}),
	              	j_gridText({field:'purchaseOrFree',title:'购/赠',width:40}),
	              	j_gridText({field:'receiptQtyInputQty',title:'已开票量/入库量',width:110}),
	              	j_gridNumber({field:'actualPrice',title:'单价',formatter:function(value,row){
              			value = value == null || value == undefined || value == '' ? 0 : value;
	              		return value+'元';
	              	},min:0,precision:3,width:80,
	              	onChange:function(newValue,oldValue){
              			if(editIndex == undefined){
	              			return;
	              		}
              			if(newValue == ""){
	            			newValue = "0";
	            		}
              			var rows = $('#listTable').datagrid('getRows');
              			rows[editIndex].actualPrice = newValue;
              			
              			var qty = new BigDecimal("0");
              			if(rows[editIndex].qty != undefined){
              				qty = new BigDecimal(rows[editIndex].qty);
              			}
              			var actualPrice = new BigDecimal(newValue);

          				rows[editIndex].totalPrice = qty.multiply(actualPrice).setScale(2, MathContext.ROUND_HALF_UP).toString();

              			countTotalMaterialPrice();
	              	}
	              	}),
	              	j_gridNumber({field:'qty',title:'开票数量',formatter:function(value,row){
              			value = value == null || value == undefined || value == '' ? 0 : value;
	              		return value+row.unit;
	              	},min:0,precision:2,width:100,validType:'minWithoutBlank[0]',required:true,
	              	onChange:function(newValue,oldValue){
              			if(editIndex == undefined){
	              			return;
	              		}
              			
              			if(newValue == "" || strToFloat(newValue) == 0){
              				return;
	            		}
              			var rows = $('#listTable').datagrid('getRows');
              			rows[editIndex].qty = newValue;

              			var totalPrice = new BigDecimal("0");
              			if(rows[editIndex].totalPrice != undefined){
              				totalPrice = new BigDecimal(rows[editIndex].totalPrice);
              			}
              			var qty = new BigDecimal(newValue);
          				rows[editIndex].actualPrice = totalPrice.divide(qty, 3, MathContext.ROUND_HALF_UP).toString();
          				
              			countTotalMaterialPrice();
	              	}
	              	}),
	              	j_gridNumber({field:'totalPrice',title:'总价',formatter:function(value,row){
              			value = value == null || value == undefined || value == '' ? 0 : value;
	              		return value+'元';
	              	},min:0,precision:2,width:100,
	              	onChange:function(newValue,oldValue){
              			if(editIndex == undefined){
	              			return;
	              		}
              			if(newValue == ""){
	            			newValue = "0";
	            		}
              			var rows = $('#listTable').datagrid('getRows');
              			if(rows[editIndex].qty == undefined || rows[editIndex].qty == "0"){
              				$.message.alert('错误',"开票数量不能为零！");
              				return;
              			}
              			rows[editIndex].totalPrice = newValue;
              			
              			var totalPrice = new BigDecimal(newValue);
              			var qty = new BigDecimal(rows[editIndex].qty);
          				rows[editIndex].actualPrice = totalPrice.divide(qty, 3, MathContext.ROUND_HALF_UP).toString();
          				
              			countTotalMaterialPrice();
	              	}}),
			    ]],
			    onEndEdit:function(index,row){
			    	
			    },
				onCheck:function(index,row){
					countTotalMaterialPrice();
				},
				onUncheck:function(index,row){
					countTotalMaterialPrice();
				},
				onCheckAll:function(rows){
					countTotalMaterialPrice();
				},
				onUncheckAll:function(rows){
					countTotalMaterialPrice();
				},
                onLoadSuccess:function(data){
                	if(!data.success && data.success != undefined){
        				$.messager.alert({
        					title: '错误',
        					msg: data.errorMsg
        				});
        	    	}else{
//        	    		$('#listTable').datagrid('selectAll');
        	    		$('#listTable').datagrid('checkAll');
        	    		countTotalMaterialPrice();
        	    	}
                }
			}));
	
	$('#invoiceTable').edatagrid({
		rownumbers:true,
		height:200,
		fitColumns:true,
		toolbar:'#invoiceTableBar',
		singleSelect:true,
		rowStyler: function(index,row){
			if ((index+1) % 2 == 0){
				return 'background-color:#f7f7f7;';
			}
		},
		columns:[[
					j_gridText({field:'rowId',title:'ID',hidden:true,sortable:false}),
					j_gridText({field:'deletedFlag',title:'deletedFlag',hidden:true,sortable:false}),
					j_gridText({field:'operate',title:'操作',width:40,sortable:false,
	              		formatter:function(value,row,index){
	              			return '<a class="editcls" onclick="invoiceDetailDelete('+index+')" href="javascript:void(0)">删除</a>';
	              		}
	              	}),
					j_gridText({field:'receiptNumber',title:'发票号',width:80,editor:'textbox',sortable:false}),
					j_gridNumber({field:'price',title:'金额',onChange:function(newValue,oldValue){
						var invoiceTableEditIndex = $('#invoiceTable').edatagrid('getEditIndex');
						var rows = $('#invoiceTable').datagrid('getRows');
						var totalPrice = 0;;
						var totalPriceHaveRransportPrice = 0;
						$.each(rows,function(i,item){
							totalPriceHaveRransportPrice += strToFloat(item.transportPrice);
							if(i == invoiceTableEditIndex){
								item.price = newValue;
								totalPrice += strToFloat(newValue);
								totalPriceHaveRransportPrice += strToFloat(newValue);
							}else{
								totalPrice += strToFloat(item.price);
								totalPriceHaveRransportPrice += strToFloat(item.price);
							}
						})
						$('#totalReceiptPrice').numberbox('setValue',totalPrice.toFixed(2));
						$('#totalReceiptPriceHaveRransportPrice').numberbox('setValue',totalPriceHaveRransportPrice.toFixed(2));
					},width:80,min:0,precision:2,sortable:false}),
					j_gridNumber({field:'transportPrice',title:'运费',width:80,min:0,precision:2,sortable:false,formatter:function(value,row){
              			value = value == null || value == undefined || value == '' ? 0 : value;
              			if(value == 0){
              				row.transportPrice = 0;
              			}
              			return value;
	              	},onChange:function(newValue,oldValue){
						var invoiceTableEditIndex = $('#invoiceTable').edatagrid('getEditIndex');
						var rows = $('#invoiceTable').datagrid('getRows');
						var totalPriceHaveRransportPrice = 0;
						$.each(rows,function(i,item){
							var itemPrice = item.price
							if(!itemPrice){
								itemPrice = 0;
							}
							totalPriceHaveRransportPrice += strToFloat(itemPrice);
							if(i == invoiceTableEditIndex){
								item.transportPrice = newValue;
								totalPriceHaveRransportPrice += strToFloat(newValue);
							}else{
								totalPriceHaveRransportPrice += strToFloat(item.transportPrice);
							}
						})
						$('#totalReceiptPriceHaveRransportPrice').numberbox('setValue',totalPriceHaveRransportPrice.toFixed(2));
					}}),
					j_gridText({field:'file',title:'上传附件',width:180,sortable:false,formatter:function(value,row,index){
						if(value){
							return value+'&nbsp;&nbsp;<a class="editcls" onclick="reUpLoad('+index+')" href="javascript:void(0)">重新上传</a>';
						}else{
							return '<input type="file" name="invoiceFile" onChange="invoiceFileOnChange('+index+',this)" accept="image/jpg,image/jpeg,image/png" value="'+value+'"/>'
						}
					}}),
					j_gridText({field:'preview',title:'附件预览',width:40,sortable:false,
						formatter:function(value,row,index){
							return '<a class="editcls" onclick="imgPreviewFun('+index+')" href="javascript:void(0)">附件预览</a>';
						}
					}),
	              	j_gridText({field:'notes',title:'备注',width:80,sortable:false,editor:'textbox'})
			    ]],
                onLoadSuccess:function(data){
                	if(!data.success && data.success != undefined){
        				$.messager.alert({
        					title: '错误',
        					msg: data.errorMsg
        				});
        	    	}else{
						var rows = $('#invoiceTable').datagrid('getRows');
						var totalPrice = 0;;
						var totalPriceHaveRransportPrice = 0;
						$.each(rows,function(i,item){
							totalPrice += strToFloat(item.price);
							totalPriceHaveRransportPrice += strToFloat(item.price);
							totalPriceHaveRransportPrice += strToFloat(item.transportPrice);
						})
						$('#totalReceiptPrice').numberbox('setValue',totalPrice.toFixed(2));
						$('#totalReceiptPriceHaveRransportPrice').numberbox('setValue',totalPriceHaveRransportPrice.toFixed(2));
        	    	}
                }
	});

	// 供应商
	xnCombobox({
		id:"supplierId",
		// url:'/supplyChian/searchSupplierForReceiptBillToList.do?purchaseFlag='+purchaseFlag,
		valueField:"supplierId",
		textField:"supplierSortName",
		required:true,
		onChange:supplierIdChange
	});
	
	// 法人公司
	xnCombobox({
		id:"branchId",
		valueField:"branchId",
		textField:"branchName",
		required:true,
		onChange:branchIdChange
	});
	
	// 猪场
	xnCombobox({
		id:"farmId",
		valueField:"farmId",
		textField:"farmSortName",
		editable:false,
		multiple:true,
//		required:true,
		onChange:farmIdChange
	});

});

function invoiceDetailAdd(){
	$('#invoiceTable').edatagrid('addRow');
}

function invoiceDetailDelete(index){
	$('#invoiceTable').edatagrid('editRow',index).edatagrid('cancelRow',index).datagrid('refreshRow',index);
}

function invoiceFileOnChange(index,obj){
	var rows = $('#invoiceTable').datagrid('getRows');
	var valueArry = $(obj).val().split("\\"); 
	rows[index].file = valueArry[valueArry.length - 1];
	fileUp(obj,rows[index]);
}

function fileUp(obj,row){
	if(window.FormData){
		if($(obj).context.ownerDocument.activeElement.files){
			var files = $(obj).context.ownerDocument.activeElement.files;
			var allIsFile = true;
			for(var i = 0; i < files.length; i++){
				if(!files[i]){
					allIsFile = false;
				}
			}

			var picSrc = [];
			if(allIsFile){
				for(var i = 0; i < files.length; i++){
					var reader = new FileReader();   
					reader.onload = function(e){  
						picSrc.push(e.target.result);
//						row.picSrc = e.target.result;
					}
					reader.readAsDataURL(files[i]); 
				}
				row.picSrc = picSrc;
				var index = $('#invoiceTable').datagrid('getRowIndex',row);
				uploadFile(files,index);

			}else{
				$.messager.alert('错误','上传未知文件！');
				row.picSrc = undefined;
			}
		}
	}else{
		$.messager.alert('错误','当前浏览器不支持此上传方法，请更换浏览器！');
	}
}
function reUpLoad(index){
	var rows = $('#invoiceTable').datagrid('getRows');
	rows[index].file = undefined;
	rows[index].picSrc = undefined;
	rows[index].fileNameList = [];
	var eidtIndex = $('#invoiceTable').edatagrid('getEditIndex');
	if(eidtIndex == index){
		$('#invoiceTable').datagrid('endEdit',index).edatagrid('editRow',index);
	}else{
		$('#invoiceTable').datagrid('refreshRow',index);
	}
}
function imgPreviewFun(index){
	var picSrc = $('#invoiceTable').datagrid('getRows')[index].picSrc;
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
	// 防止combobox farmId 和此控件 访问两次后台的BUG
	var loadFlag = true;
	if($('#supplierId').combobox('isValid')){
		$('#branchId').combobox('setValue','');
		$('#farmId').combobox('setValues',[]).combobox('loadData',[]);
		var data = jAjax.submit('/supplyChian/searchBranchIdForReceiptBillToList.do?purchaseFlag='+purchaseFlag+'&supplierId='+newValue);
		$('#branchId').combobox('loadData',data);
		if(data && data.length==1){
			$('#branchId').combobox('setValue',data[0].branchId);
			loadFlag = false;
		}
	}

	if(!$('#supplierId').combobox('isValid') || !$('#branchId').combobox('isValid')){
		if($('#rowId').val()==0){
			$('#listTable').datagrid('load','');
			$('#listTable').datagrid('loadData',{success:true,total: 0, rows: [] });
		}
		return
	}
	
	var branchId = $('#branchId').combobox('getValue');
	var farmId = $('#farmId').combobox('getValues');
	if(loadFlag){
		if($('#rowId').val()==0){
//			$('#listTable').datagrid('options').sortName = '';
//			$('#listTable').datagrid('options').sortOrder = '';
			$('#listTable').datagrid('reload', basicUrl+searchReceiptFillInInputDetailToListUrl+'?supplierId='+newValue+'&branchId='+branchId+'&farmId='+farmId);
		}
	}
}

function branchIdChange(newValue, oldValue){
	// 防止combobox branchId 和此控件 访问两次后台的BUG
	var loadFlag = true;
	var supplierId = '';
	if($('#supplierId').combobox('isValid') && $('#branchId').combobox('isValid')){
		supplierId = $('#supplierId').combobox('getValue')

		var data = jAjax.submit('/supplyChian/searchFarmIdForReceiptBillToList.do?purchaseFlag='+purchaseFlag+'&supplierId='+supplierId+'&branchId='+newValue);
		$('#farmId').combobox('loadData',data);
		if(data && data.length==1){
			$('#farmId').combobox('setValues', [data[0].farmId]);
			loadFlag = false;
		}else{
			var farmId = $('#farmId').combobox('getValues');
			if(!farmId.length == 0){
				$('#farmId').combobox('setValues',[]);
				loadFlag = false;
			}
		}
	}

	if(!$('#supplierId').combobox('isValid') || !$('#branchId').combobox('isValid')){
		if($('#rowId').val()==0){
			$('#listTable').datagrid('load','');
			$('#listTable').datagrid('loadData',{success:true,total: 0, rows: [] });
		}
		return
	}
	
	if(loadFlag){
		if($('#rowId').val()==0){
//			$('#listTable').datagrid('options').sortName = '';
//			$('#listTable').datagrid('options').sortOrder = '';
			$('#listTable').datagrid('reload', basicUrl+searchReceiptFillInInputDetailToListUrl+'?supplierId='+supplierId+'&branchId='+newValue+'&farmId='+[]);
		}
	}
}

function farmIdChange(newValue, oldValue){
	if(!$('#supplierId').combobox('isValid') || !$('#branchId').combobox('isValid') || !$('#farmId').combobox('isValid')){
		if($('#rowId').val()==0){
			$('#listTable').datagrid('load','');
			$('#listTable').datagrid('loadData',{success:true,total: 0, rows: [] });
		}
		return
	}
	
	var supplierId = $('#supplierId').combobox('getValue');
	var branchId = $('#branchId').combobox('getValue');
	if($('#rowId').val()==0){
//		$('#listTable').datagrid('options').sortName = '';
//		$('#listTable').datagrid('options').sortOrder = '';
		$('#listTable').datagrid('load', basicUrl+searchReceiptFillInInputDetailToListUrl+'?supplierId='+supplierId+'&branchId='+branchId+'&farmId='+newValue);
	}
}

function countTotalMaterialPrice(){
	var record = $('#listTable').datagrid('getChecked');
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
	
	$('#billDate').combobox('enable');
	$('#supplierId').combobox('enable');
	$('#branchId').combobox('enable');
	$('#farmId').combobox('enable');
	
	$("#invoiceTableAddBtn").removeAttr("disabled");
	$("#invoiceTableAddBtn").css('background-color','rgb(113, 175, 76)');
	$("#noReceipt").removeAttr("checked");
	
	$('#editWin').window('open');
	$('#editForm').form('reset');
	$('#btnSave').css('display','inline-block');
	$('#invoiceTableBar').css('display','block');
	
	$('#billDate').datebox('setValue',getCurrentDate());
	$('#rowId').val(0);
	var data = jAjax.submit('/supplyChian/searchSupplierForReceiptBillToList.do?purchaseFlag='+purchaseFlag);
	$('#supplierId').combobox('loadData',data);
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
	$('#invoiceTableBar').css('display','none');
	
	$('#rowId').val(row.rowId);
	
	$('#billDate').combobox('disable');
	$('#supplierId').combobox('disable');
	$('#branchId').combobox('disable');
	$('#farmId').combobox('disable');
	$('#billDate').combobox('setText', row.billDate);
	$('#supplierId').combobox('setText', row.supplierName);
	$('#branchId').combobox('setText', row.receiptBranchName);
	$('#farmId').combobox('setText', row.requireFarmSortName);
	
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
	var billDate = new Date($('#billDate').datebox('getValue'));
	var billDateMon = billDate.getMonth() +1;
	var billDateYear = billDate.getFullYear();
	var billDateYearMon = billDateYear.toString()+'-'+billDateMon.toString();
	
	// 发票信息
	var receiptRecordTable = $('#invoiceTable');
	var receiptRecord = receiptRecordTable.datagrid('getRows');
	var receiptRecordActualInsertRow = [];
	for(var i = 0 ; i < receiptRecord.length ; i ++){
		receiptRecordTable.datagrid('endEdit',i);
	}
	
	//给提交数据加上行号
	$.each(receiptRecord,function(index,data){
		receiptRecord[index].lineNumber = index+1;

		// 拷贝元素（除去picSrc）
		var receiptRecordItem = new Object();
		receiptRecordItem.status = data.status;
		receiptRecordItem.deletedFlag = data.deletedFlag;
		receiptRecordItem.lineNumber = data.lineNumber;
		receiptRecordItem.receiptNumber = data.receiptNumber;
		receiptRecordItem.price = data.price;
		receiptRecordItem.transportPrice = data.transportPrice;
		receiptRecordItem.notes = data.notes;
		receiptRecordItem.fileNameList = data.fileNameList;
		receiptRecordActualInsertRow.push(receiptRecordItem);
	});
	
	var receiptRecordJsonString = JSON.stringify(receiptRecordActualInsertRow);
	
	// 物料信息
	var listTable = $('#listTable');
	// 关闭所有编辑行
	var rows =  listTable.datagrid('getRows');
	for(var i = 0 ; i < rows.length ; i ++){
		listTable.datagrid('endEdit',i);
	}
	
	var alertLineNumbers = '';
	
	// 获取勾选住的行
	var listTableRows = listTable.datagrid('getChecked');
	for(var i = 0 ; i < listTableRows.length ; i ++){
		listTableRows[i].lineNumber = i + 1;
		var inputDate = new Date(listTableRows[i].inputBillDate);
		var inputDateMon = inputDate.getMonth() +1;
		var inputDateYear = inputDate.getFullYear();
		var inputDateYearMon = inputDateYear.toString()+'-'+inputDateMon.toString();
		if(billDateYearMon != inputDateYearMon){
			if(alertLineNumbers.length == 0){
				alertLineNumbers = listTable.datagrid('getRowIndex',listTableRows[i]) + 1;
			}else{
				alertLineNumbers = alertLineNumbers + ',' + (listTable.datagrid('getRowIndex',listTableRows[i]) + 1);
			}
		}
	}
	
	var listTableJsonString = JSON.stringify(listTableRows);
	
	var noReceiptCheck = $("#noReceipt").is(':checked');
	var noReceipt = 'N';
	if(noReceiptCheck){
		noReceipt = 'Y';
	}
	
	var queryParams = {
			status:1,
			deletedFlag:0,
			noReceipt:noReceipt,
			listTableRecord:listTableJsonString,
			receiptRecord:receiptRecordJsonString
	};
	
	if(alertLineNumbers.length == 0){
		onBtnSaveSubmit(queryParams);
	}else{
		$.messager.confirm('提示', '第【'+alertLineNumbers+'】行的【入库日期】和【发票日期】不在同一个【月份】，确定提交吗？', function(r){
			if(r){
				onBtnSaveSubmit(queryParams);
			}
//			else{
//				$('#btnSave').attr('disabled',false).removeClass('btn-disabled');
//				$.messager.progress('close');
//			}
		});
	}
}

function onBtnSaveSubmit(queryParams){
	$('#btnSave').attr('disabled',true).addClass('btn-disabled');
	$.messager.progress();
	$('#editForm').form('submit',{
		url:basicUrl+saveUrl,
		queryParams:queryParams,
		onSubmit: function(){
			var isValid = $('#editForm').form('validate');
			if (!isValid){
				$.messager.alert('提示','头部存在必填项没有填写！');
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

function uploadFile(files,index){
	var formData = new FormData();
	for(var i = 0; i < files.length; i++){
		formData.append('file'+i, files[i]);
	}
	var rows = $('#invoiceTable').edatagrid('getRows');
	$.messager.progress();
	jAjax.submit_file(prUrl + 'editReceiptTempFile.do', formData
	, function(data){
		rows[index].fileNameList = data;
		rows[index].file = '上传'+data.length+'个附件';
		$('#invoiceTable').edatagrid('editRow',index).datagrid('endEdit',index);
		$.messager.progress('close');
	}, function(response){
		jAjax.errorFunc(response.errorMsg);
		$.messager.progress('close');
	},true);
}

function materialOrFreePurchase(){
	var data = jAjax.submit('/supplyChian/searchPurchaseTypeByEmployeeIdToList.do')[0];
	purchaseFlag = data.purchaseType;
	
	if(purchaseFlag != '1' && purchaseFlag != '2' && purchaseFlag != '3'){
		// 非采购人员进入此页面
		// 是页面崩溃，无法操作
		$('#eventCode').combobox('11122', '');
	}
}

function noReceiptChange(checkboxItem){
	var checkedFlag = $(checkboxItem).is(':checked');
	if(checkedFlag){
		$('#invoiceTable').datagrid('loadData',{success:true,total: 0, rows: [] });
		$("#invoiceTableAddBtn").attr({"disabled":"disabled"});
		$("#invoiceTableAddBtn").css('background-color','rgb(220, 220, 220)');
	}else{
		$("#invoiceTableAddBtn").removeAttr("disabled");
		$("#invoiceTableAddBtn").css('background-color','rgb(113, 175, 76)');
	}
}
