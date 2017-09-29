var title = '需求单';
var editIndex = undefined;
var prUrl = '/supplyChian/';
var searchMainListUrl = prUrl + 'searchRequireStoreToPage.do';
var searchDetailListUrl = prUrl + 'searchRequireStoreDetailToList.do';
var saveUrl=prUrl+'editRequireStore.do';
var scrapUrl=prUrl+'editScrapRequireStore.do';
var submitUrl=prUrl+'editSubmitRequireStore.do';
var createBillType = 'self';
//是否过滤已选择物料Flag ture表示过滤
var filterMaterialFlag = true;
//搜索套餐物料
var searchFixedGroup = true;
/** *********页面渲染开始*************************************************************************/
$(document).ready(function(){
	/**
	 * 主表加载
	 */
	$('#table').datagrid(
			j_grid_view({
				toolbarList:"#tableToolbarList",
				url:searchMainListUrl,
				columnFields:'emergencyTypeName,billCode,statusName,billDate,applyTypeName,requireTypeName,worker,notes',
				columnTitles:'紧急程度,需求单编号,状态,需求日期,申报类型,需求类型,需求员,备注',
				hiddenFields:'',
				onDblClickRowFun:selectRequireBill,
			}));
		
		//核算单位
		xnCombobox({
			id:'accountsUnitId',
			valueField:'rowId',
		    textField:'companyName',
		    firstDataIsDefault:true,
		    readonly:true,
		    url:'/supplyChian/searchAccountsUnit.do',
		});
		addFocus('accountsUnitId','grid');
		
		//申请类型
		xnCdCombobox({
			id:'applyType',
			typeCode:'MATERIAL_FIRST_CLASS',
			includeValue:['21','22','30','40','50','90'],
			required:true,
			readonly:true,
			editable:false,
			onChange:changeApplyType
		});
		
		//需求类型
		xnCdCombobox({
			id:'requireType',
			typeCode:'REQUIRE_TYPE',
			required:true,
			editable:false
		});
		
		//紧急程度
		xnCdCombobox({
			id:'emergencyType',
			typeCode:'EMERGENCY_TYPE',
			required:true,
			editable:false
		});
		
});

//猪只需求单listTable初始化
function initPigListTable(tableObj){
	tableObj.datagrid(
			j_detailGrid({
				toolbar:"#listTableToolbar",
				columns:[[
			      	{field:'ck',checkbox:true},
	              	j_gridText({field:'materialName',title:'物料名称',width:100,sortable:false}),
	            	j_gridText({field:'materialFirstClassName',title:'大类',width:60,sortable:false}),
	            	j_gridText({field:'materialSecondClassName',title:'中类',width:60,sortable:false}),
	              	j_gridText({field:'breedName',title:'品种',width:100,sortable:false}),
	              	j_gridText({field:'strain',title:'品系',width:100,sortable:false}),
	              	j_gridText({field:'unit',title:'计量单位',width:100,sortable:false}),
	              	j_gridNumber({field:'requireQty',title:'需求量',formatter:function(value,row){
              			value = value == null || value == undefined || value == '' ? 0 : value;
	              		return value+row.unit;
	              	},min:0,precision:3,width:80,sortable:false}),
	              	j_gridText({field:'notes',title:'备注',width:80,editor:'textbox',sortable:false})
			    ]],
			    onEndEdit:function(index,row){
			    	var requireQty = tableObj.datagrid('getEditor', {
		                index: index,
		                field: 'requireQty'
		            });
			    	if(requireQty != null){
			    		var requireQtyValue;
			    		if($(requireQty.target).numberspinner('getValue')){
			    			requireQtyValue = new BigDecimal($(requireQty.target).numberspinner('getValue'));
			    		}else{
			    			requireQtyValue = new BigDecimal('0');
			    		}
			    		var outputMinQty = row.outputMinQty == '' || row.outputMinQty == undefined ? new BigDecimal("1") : new BigDecimal(row.outputMinQty);
			    		if(requireQtyValue.remainder(outputMinQty) != 0){
			    			$.messager.alert('提示','第'+(index+1)+'行物料'+row.materialName+'需求量输入不符合要求，不是最小领用量的整数倍,请重新输入！');
			    			row.requireQty = 0;
			    		}
			    	}
			    }
		})
	);
}

//物料需求单listTable初始化
function initMaterialListTable(tableObj){
	tableObj.datagrid(
			j_detailGrid({
				toolbar:"#listTableToolbar",
				columns:[[
			      	{field:'ck',checkbox:true},
	              	j_gridText({field:'materialName',title:'物料名称',width:100,sortable:false}),
	            	j_gridText({field:'materialFirstClassName',title:'大类',width:60,sortable:false}),
	            	j_gridText({field:'materialSecondClassName',title:'中类',width:60,sortable:false}),
	              	j_gridText({field:'specAll',title:'规格',width:100,sortable:false}),
	              	j_gridText({field:'existsQty',title:'结存量',formatter:function(value,row){
              			value = value == null || value == undefined || value == '' ? 0 : value;
	              		return value+row.unit;
	              	},width:80,sortable:false}),
	              	j_gridNumber({field:'requireQty',title:'需求量',formatter:function(value,row){
              			value = value == null || value == undefined || value == '' ? 0 : value;
	              		return value+row.unit;
	              	},min:0,precision:3,width:80,sortable:false}),
	              	j_gridText({field:'notes',title:'备注',width:80,editor:'textbox',sortable:false})
			    ]],
			    onEndEdit:function(index,row){
			    	var requireQty = tableObj.datagrid('getEditor', {
		                index: index,
		                field: 'requireQty'
		            });
			    	if(requireQty != null){
			    		var requireQtyValue;
			    		if($(requireQty.target).numberspinner('getValue')){
			    			requireQtyValue = new BigDecimal($(requireQty.target).numberspinner('getValue'));
			    		}else{
			    			requireQtyValue = new BigDecimal('0');
			    		}
			    		var outputMinQty = row.outputMinQty == '' || row.outputMinQty == undefined ? new BigDecimal("1") : new BigDecimal(row.outputMinQty);
			    		if(requireQtyValue.remainder(outputMinQty) != 0){
			    			$.messager.alert('提示','第'+(index+1)+'行物料'+row.materialName+'需求量输入不符合要求，不是最小领用量的整数倍,请重新输入！');
			    			row.requireQty = 0;
			    		}
			    	}
			    }
		})
	);
}

//新增猪只需求单
function onBtnAddPigRequire(){
	createBillType = 'self';
	$('#editWin').window({
		title:'新增猪只'+ title
	});
	$('#editWin').window('open');
	$('#editForm').form('reset');
	$('#billType').val('2');
	$('#btnSave').css('display','inline-block');
	$('#applyType').combobox('readonly',false);
	$('#billDate').datebox('setValue',getCurrentDate());
	initPigListTable($('#listTable'));
	var listTable = document.getElementById('listTable');
	if(listTable!=null){
		$('#listTable').datagrid('loadData',{success:true,total: 0, rows: [] });
	}
}

//新增物料需求单并改变createBillType
function onBtnChangeCreateBillType(){
	createBillType = 'self';
	onBtnAddMaterialRequire();
}

//新增物料需求单
function onBtnAddMaterialRequire(){
	$('#editWin').window({
		title:'新增物料'+ title
	});
	$('#editWin').window('open');
	$('#editForm').form('reset');
	$('#billType').val('1');
	$('#copyId').val('');
	$('#btnSave').css('display','inline-block');
	$('#applyType').combobox('readonly',false);
	$('#billDate').datebox('setValue',getCurrentDate());
	initMaterialListTable($('#listTable'));
	saveUrl = prUrl+'editRequireStore.do?editType=insert';
	var listTable = document.getElementById('listTable');
	if(listTable!=null){
		$('#listTable').datagrid('loadData',{success:true,total: 0, rows: [] });
	}
}

//修改
function onBtnEdit(){
	createBillType = 'self';
	var record = $('#table').datagrid('getSelections');
	var length = record.length;
	if(length < 1){
		$.messager.alert('警告','请选择一条数据！');
	}else if(length > 1){
		$.messager.alert('警告','只能选择一条数据！');
	}else{
		$('#copyId').val('');
		var index = $('#table').datagrid('getRowIndex',record[0]);
		saveUrl = prUrl+'editRequireStore.do?editType=update';
		editRequireBill(index,record[0],'update');
	}
}

/**
 * 复制新增
 */
function onBtnCopyAdd(){
	var record = $('#table').datagrid('getSelections');
	var length = record.length;
	if(length < 1){
		$.messager.alert('警告','请选择一条数据！');
	}else if(length > 1){
		$.messager.alert('警告','只能选择一条数据！');
	}else{
		saveUrl = prUrl+'editRequireStore.do?editType=insert';
		$('#copyId').val(record[0].rowId);
		editRequireBill(null, record[0] ,'copy')
	}
}

function selectRequireBill(index,row){
	editRequireBill(index,row,'select')
}

function editRequireBill(index,row,type){
	createBillType = 'self';
	var winTitle = null;
	if(type=='update'){
		winTitle = '编辑'
	}else if(type=='copy'){
		winTitle = '复制新增'
	}else{
		winTitle = '查看'
	}
	
	if(row.eventCode == 'MATERIAL_REQUIRE'){
		winTitle += '物料';
		if(type=='update'){
			if(row.status == '1'){
				$.messager.alert('错误','已经提交，无法修改！');
				return;
			}
			if(row.status == '2'){
				$.messager.alert('错误','已经审批未通过，无法修改，请选中复制新增修改数据！');
				return;
			}
			if(row.status == '3'){
				$.messager.alert('错误','已经审批通过，无法修改！');
				return;
			}
			if(row.status == '4'){
				$.messager.alert('错误','已经部分完成订单，无法修改！');
				return;
			}
			if(row.status == '5'){
				$.messager.alert('错误','已经完成订单，无法修改！');
				return;
			}
			if(row.status == '6'){
				$.messager.alert('错误','已经报废，无法修改！！');
				return;
			}
		}
		initMaterialListTable($('#listTable'));
	}else{
		winTitle += '猪只';
		initPigListTable($('#listTable'));
	}
	$('#applyType').combobox('readonly');
	$('#editWin').window({
		title:winTitle + title
	});
	$('#editWin').window('open');
	
	// 防止进行changeApplyType操作
	changeFlag = false;
	
	$('#editForm').form('load',row);
	if(type=='update' || type=='copy'){
		$('#btnSave').css('display','inline-block');
	}else{
		$('#btnSave').css('display','none');
	}
	var listTable = document.getElementById('listTable');
	if(listTable!=null){
		$('#listTable').datagrid('load',basicUrl+searchDetailListUrl+'?requiredBillId='+row.rowId);
	}
}

//选择物料
function selectMaterial(){
//	var billType = $('#billType').val();
	var applyType = $('#applyType').combobox('getValue');
	if(applyType==''){
		$.messager.alert('提示', '请选择申报类型！');
	}else{
		$('#showMaterialName').searchbox('setValue','');
		var materialSecondClass = $('#showMaterialSecondClass').combobox('getValue');
		var supplierId = $('#supplier').combobox('getValue');
		
		$('#selectMaterialListTable').datagrid('load','');
		var rows = $('#listTable').datagrid('getRows');
		var filterIds = [];
		$.each(rows, function(i,data){
			filterIds.push(data.materialId);
		});
		$('#selectMaterialListTable').datagrid('reload',{filterIds:filterIds.join(",")});
		$('#selectMaterialListTable').datagrid('load', basicUrl + searchSelectMaterialListTableUrl+'?applyType='+applyType+'&supplierId='+supplierId+'&materialSecondClass='+materialSecondClass);
		leftSilpFun('selectMaterialWin',true,9001);
	}
}

//添加数据
function requireBillAddData(selectRows,fromDate){
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
		row.materialId = data.materialId;
		row.materialName = data.materialName;
		row.materialFirstClass = data.materialFirstClass;
		row.materialFirstClassName = data.materialFirstClassName;
		row.materialSecondClass = data.materialSecondClass;
		row.materialSecondClassName = data.materialSecondClassName;
		row.specAll = data.specAll;
		row.unit = data.unit;
		row.requireQty = data.quantity;
		row.existsQty = data.existsQty;
		row.detailRowIds = data.detailRowIds;
		$('#listTable').datagrid('appendRow',row);
	});
	if(fromDate != null){
		$('#applyType').combobox('setValue',fromDate.applyType);
		$('#applyType').combobox('readonly');
	}
}

var changeFlag = true;
function changeApplyType(newValue,oldValue){
		if(changeFlag){
			if(oldValue!=''){
				$.messager.confirm('警告','改变申报类型会清空明细！',function(r){
					if (r){
						changeSelectMaterial(newValue);
					}else{
						changeFlag = false;
						$('#applyType').combobox('setValue',oldValue);
					}
				});
			}else{
				changeSelectMaterial(newValue);
			}
		}else{
			changeFlag = true;
		}
}

function changeSelectMaterial(newValue){
	if(createBillType == 'self'){
		if($('#listTable').css('display') == 'none'){
			$('#listTable').datagrid('loadData',{success:true,total: 0, rows: []});
		}
	};
	
	$('#showMaterialFirstClass').combobox('setValue',newValue);
	$('#showMaterialFirstClass').combobox('readonly',true);
	
}
//作废
var scrapIds = [];
function onBtnScrap(){
	scrapIds = [];
	createBillType = 'self';
	var record = $('#table').datagrid('getSelections');
	var length = record.length;
	if(length < 1){
		$.messager.alert('警告','请选择一条数据！');
	}else{
		var ids = [];
		var errorFlag = '0';
		$.each(record,function(index,data){
			if(data.status == '1'){
				var dataIndex = $('#table').datagrid('getRowIndex',data)+1;
				$.messager.alert('错误','第'+dataIndex+'行已提交，无法作废！');
				$.messager.progress('close');
				errorFlag = '1';
				return false;
			}
			if(data.status == '2'){
				var dataIndex = $('#table').datagrid('getRowIndex',data)+1;
				$.messager.alert('错误','第'+dataIndex+'行已未通过审批，无法作废！');
				$.messager.progress('close');
				errorFlag = '1';
				return false;
			}
			if(data.status == '3'){
				var dataIndex = $('#table').datagrid('getRowIndex',data)+1;
				$.messager.alert('错误','第'+dataIndex+'行已通过审批，无法作废！');
				$.messager.progress('close');
				errorFlag = '1';
				return false;
			}
			if(data.status == '4'){
				var dataIndex = $('#table').datagrid('getRowIndex',data)+1;
				$.messager.alert('错误','第'+dataIndex+'行已部分完成订单，无法作废！');
				$.messager.progress('close');
				errorFlag = '1';
				return false;
			}
			if(data.status == '5'){
				var dataIndex = $('#table').datagrid('getRowIndex',data)+1;
				$.messager.alert('错误','第'+dataIndex+'行已完成订单，无法作废！');
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
function dialogCancel(id){
	$('#'+ id).dialog('close');
}
function scrapReasonEnter(){
	$.messager.progress();	
	var scrapReason = $('#scrapReason').textbox('getValue');
	jAjax.submit(scrapUrl,{'ids':scrapIds,scrapReason:scrapReason}
	,function(response){
		$.messager.alert('提示','作废成功！');
		$('#table').datagrid('reload');
		var statisticalMaterialIframe = parent.document.getElementById("iframe_StatisticalMaterial");
		if(statisticalMaterialIframe){
			var statisticalMaterialContentWindow = statisticalMaterialIframe.contentWindow;
			statisticalMaterialContentWindow.tableRefresh();
		}
		$.messager.progress('close');
		$('#scrapReasonWin').dialog('close');
	}
	,function(response){
		jAjax.errorFunc(response.errorMsg);
		$.messager.progress('close');
		$('#scrapReasonWin').dialog('close');
	},null,true,null,null);
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
			$.messager.alert('提示','未添加明细不能提交！');
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
				
				var statisticalMaterialIframe = parent.document.getElementById("iframe_StatisticalMaterial");
				if(statisticalMaterialIframe){
					var statisticalMaterialContentWindow = statisticalMaterialIframe.contentWindow;
					statisticalMaterialContentWindow.tableRefresh();
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
						if(data.rowId){
							var dataIndex = $('#listTable').datagrid('getRowIndex',data)+1;
							$.messager.alert('错误','第'+dataIndex+'行数据可能是日常用料统计而成，无法删除，只能修改！');
							return false
						}
						data.deletedFlag = "1";
						listTable.datagrid('deleteRow',row);
					});
				}
			}
		});
	}
}
function onBtnSubmit(){
	var record = $('#table').datagrid('getSelections');
	var length = record.length;
	if(length < 1){
		$.messager.alert('警告','请选择一条数据！');
	}else{
		$.messager.progress();
		var ids = [];
		var errorFlag = '0';
		$.each(record,function(index,data){
			if(data.status == '1'){
				var dataIndex = $('#table').datagrid('getRowIndex',data)+1;
				$.messager.alert('错误','第'+dataIndex+'行已提交，无法提交！');
				$.messager.progress('close');
				errorFlag = '1';
				return false;
			}
			if(data.status == '2'){
				var dataIndex = $('#table').datagrid('getRowIndex',data)+1;
				$.messager.alert('错误','第'+dataIndex+'行已未通过审批，无法提交！');
				$.messager.progress('close');
				errorFlag = '1';
				return false;
			}
			if(data.status == '3'){
				var dataIndex = $('#table').datagrid('getRowIndex',data)+1;
				$.messager.alert('错误','第'+dataIndex+'行已通过审批，无法提交！');
				$.messager.progress('close');
				errorFlag = '1';
				return false;
			}
			if(data.status == '4'){
				var dataIndex = $('#table').datagrid('getRowIndex',data)+1;
				$.messager.alert('错误','第'+dataIndex+'行已部分完成订单，无法提交！');
				$.messager.progress('close');
				errorFlag = '1';
				return false;
			}
			if(data.status == '5'){
				var dataIndex = $('#table').datagrid('getRowIndex',data)+1;
				$.messager.alert('错误','第'+dataIndex+'行已完成订单，无法提交！');
				$.messager.progress('close');
				errorFlag = '1';
				return false;
			}
			if(data.status == '6'){
				var dataIndex = $('#table').datagrid('getRowIndex',data)+1;
				$.messager.alert('错误','第'+dataIndex+'行已作废，无法提交！');
				$.messager.progress('close');
				errorFlag = '1';
				return false;
			}
			ids.push(data.rowId);
		});
		if(errorFlag=='0'){
			jAjax.submit(submitUrl,{'ids':ids}
			,function(response){
				$.messager.alert('提示','提交成功！');
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