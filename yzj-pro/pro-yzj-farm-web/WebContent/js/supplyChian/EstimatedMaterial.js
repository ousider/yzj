var title = '预计用料';
var prUrl = '/supplyChian/';
var searchMainListUrl = prUrl + 'searchDailyRecordToPage.do?searchType=personal';
var saveUrl = prUrl + 'editDailyRecord.do';
var deleteUrl = prUrl + 'deleteDailyRecord.do';
var editIndex = undefined;
var detailDefaultValues = {
		planDate:getCurrentDate()
}
//是否过滤已选择物料Flag ture表示过滤
var filterMaterialFlag = true;
// 搜索套餐物料
var searchFixedGroup = true;
/** *********页面渲染开始*************************************************************************/
//查询按钮
var buttonList = getButtonByLimit({});

$(document).ready(function(){
	dafengModelConfirm();
	/**
	 * 主表加载
	 */
	
	$('#table').datagrid(
			j_grid_view({
				toolbarList:"#tableToolbarList",
				url:searchMainListUrl,
				columnFields:'rowId,planDate,statusName,materialName,materialFirstClassName,materialSecondClassName,supplierSortName,manufacturer,specAll,quantityUnit,notes',
				columnTitles:'ID,计划需求日期,状态,物料名称,大类,中类,供应商,厂家,规格,需求量,备注',
				hiddenFields:'rowId',
			},'table')
	);
	
	setTimeout(function(){
		$('#listTable').datagrid(
				j_detailGrid({
					toolbar:"#tableToolbar",
					width:'100%',
					height:'100%',
					dbClickEdit:true,
					checkOnSelect:true,
					columns:[[
				      	{field:'ck',checkbox:true},
		              	j_gridText({field:'planDate',title:'计划需求日期',width:100,editor:{type:'datebox',options:{editable:false}}}),
		              	j_gridText({field:'materialName',title:'物料名称',width:100}),
		             	j_gridText({field:'materialFirstClassName',title:'大类',width:60,sortable:false}),
		            	j_gridText({field:'materialSecondClassName',title:'中类',width:60,sortable:false}),
		              	j_gridText({field:'supplierSortName',title:'供应商',width:100}),
		              	j_gridText({field:'manufacturer',title:'厂家',width:100}),
		              	j_gridText({field:'specAll',title:'规格',width:60}),
		              	j_gridNumber({field:'quantity',title:'需求量',formatter:function(value,row){
	              			value = value == null || value == undefined || value == '' ? 0 : value;
		              		return value+row.unit;
		              	},min:0,precision:3,width:80}),
		              	j_gridText({field:'notes',title:'备注',width:80,editor:'textbox'}),
				    ]],
				    onEndEdit:function(index,row){
//				    	var isWarehouse = $('#listTable').datagrid('getEditor', {
//			                index: index,
//			                field: 'isWarehouse'
//			            });
//				    	if(isWarehouse != null){
//				    		row.isWarehouseName = $(isWarehouse.target).combogrid('getText');
//				    	}
				    	var quantity = $('#listTable').datagrid('getEditor', {
			                index: index,
			                field: 'quantity'
			            });
				    	if(quantity != null){
				    		var quantityValue;
				    		if($(quantity.target).numberspinner('getValue')){
				    			quantityValue = new BigDecimal($(quantity.target).numberspinner('getValue'));
				    		}else{
				    			quantityValue = new BigDecimal('0');
				    		}
				    		var outputMinQty = row.outputMinQty == '' || row.outputMinQty == undefined ? new BigDecimal("1") : new BigDecimal(row.outputMinQty);
				    		if(quantityValue.remainder(outputMinQty) != 0){
				    			$.messager.alert('提示','第'+(index+1)+'行物料'+row.materialName+'领用量输入不符合要求，不是最小领用量的整数倍,请重新输入！');
				    			row.quantity = 0;
				    		}
				    	}
				    }
			})
		);
		
		
	},500);
});

function onBtnAdd(){
	$('#editWin').window({title:'新增'+ title});
	$('#editWin').window('open');
	$('#onDetailAdd').css('display','inline-block');
	$('#onDetailDelete').css('display','inline-block');
	$('#btnSave').css('display','inline-block');
	$('#editType').val('insert');
	$('#listTable').datagrid('loadData',{success:true,total: 0, rows: [] });
	detailAdd();
}

function onBtnEdit(){
	var record = $('#table').datagrid('getSelections');
	var length = record.length;
	if(length < 1){
		$.messager.alert('警告','请选择一条数据！');
	}else{
		var errorFlag = '0';
		$.each(record,function(index,data){
			if(data.status=="2"){
				var dataIndex = $('#table').datagrid('getRowIndex',data)+1;
				$.messager.alert('错误','第'+dataIndex+'行数据已统计成需求单的数据无法编辑！');
				$.messager.progress('close');
				errorFlag = '1';
				return false;
			}
		});
		if(errorFlag=='0'){
			$('#editWin').window({title:'编辑'+ title});
			$('#editWin').window('open');
			$('#onDetailAdd').css('display','none');
//			$('#onDetailDelete').css('display','none');
			$('#btnSave').css('display','inline-block');
			$('#editType').val('update');
			$('#listTable').datagrid('loadData',{success:true,total: 0, rows:record});
		}
	}
}

/**
 * 删除
 * pms{
 * 	model:模块代码
 * }
 */
function onBtnDelete(){
	var record = $('#table').datagrid('getSelections');
	var length = record.length;
	if(length < 1){
		$.messager.alert('警告','请选择一条数据！');
	}else{
		$.messager.confirm('提示', '确定要删除这'+record.length+'条记录吗？', function(r){
			if (r){
				$.messager.progress();	
				var ids = [];
				var errorFlag = '0';
				$.each(record,function(index,data){
					if(data.status=="2"){
						var dataIndex = $('#table').datagrid('getRowIndex',data)+1;
						$.messager.alert('错误','第'+dataIndex+'行已统计成需求单的数据无法删除！');
						$.messager.progress('close');
						errorFlag = '1';
						return false;
					}
					ids.push(data.rowId);
				});
				if(errorFlag=='0'){
					$.ajax({
					    type: 'POST',
					    url: basicUrl+deleteUrl ,
					    data: {
					    	'ids':ids
					    } ,
					    success: function(response){
					    	$.messager.progress('close');
					    	if(response.success){
					    		$.messager.alert('警告','删除成功！');
					    		$('#table').datagrid('reload');
					    	}else{
					    		$.messager.alert({
				                     title: '错误',
				                     msg: response.errorMsg
				                 });
					    	}
					    	
					    },
					    dataType:'JSON'
					});
				}
			}
		});
	}
}

/**
 * 非事件新增明细行
 */
function detailAdd(){
	$('#showMaterialName').searchbox('setValue','');
	var showMaterialFirstClass = $('#showMaterialFirstClass').combobox('getValue');
	var showMaterialSecondClass = $('#showMaterialSecondClass').combobox('getValue');
	var supplierId = $('#supplier').combobox('getValue');
	
	$('#selectMaterialListTable').datagrid('load','');
	var rows = $('#listTable').datagrid('getRows');
	var filterIds = [];
	$.each(rows, function(i,data){
		filterIds.push(data.materialId);
	});
	$('#selectMaterialListTable').datagrid('reload',{filterIds:filterIds.join(",")});
	
	$('#selectMaterialListTable').datagrid('load', basicUrl + searchSelectMaterialListTableUrl+'?supplierId='+supplierId+'&showMaterialFirstClass='+showMaterialFirstClass+'&showMaterialSecondClass='+showMaterialSecondClass+'&dafengModel='+$('#dafengModel').val());
	leftSilpFun('selectMaterialWin',true,9001);
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