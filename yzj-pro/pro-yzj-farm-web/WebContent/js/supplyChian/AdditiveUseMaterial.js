var title = '添加药领用';
var editIndex = undefined;
var prUrl = '/supplyChian/';
var searchMainListUrl = prUrl + 'searchOutputStoreToPage.do';
var searchDetailListUrl = prUrl + 'searchOutputBillDetailToList.do';
var saveUrl=prUrl + 'editAdditiveUseMaterial.do';
var deleteUrl=prUrl + '';
/** *********页面渲染开始*************************************************************************/
$(document).ready(function(){
	$('#editWin').window({
		title:title
	});
	// 添加药仓库
	xnComboGrid({
		id:'allotOutputWarehouseId',
		idField:'rowId',
		textField:'warehouseName',
		url:'/supplyChian/searchWarehouseByMaterialTypeToList.do?roleFilterFlag=true&warehouseCategory=3',
		required:true,
		columns:[[ 	
					{field:'rowId',title:'ID',width:100,hidden:true},
					{field:'warehouseName',title:'仓库名称',width:100},
			        {field:'warehouseTypeName',title:'仓库类型',width:100}
			    ]],
		prompt:'修改仓库会清空明细',
	});	
	
	//调拨去向猪场
	xnComboGrid({
		id:'allotFarmId',
		idField:'rowId',
		textField:'companyName',
		url:'/supplyChian/searchAdditiveUseMaterialFarmToList.do',
		required:true,
		columns:[[ 	
					{field:'rowId',title:'ID',width:100,hidden:true},
					{field:'companyName',title:'公司名称',width:100},
			    ]]
	});
	
	// 表行
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
//			    	var outputQty = $('#listTable').datagrid('getEditor', {
//		                index: index,
//		                field: 'outputQty'
//		            });
//			    	if(outputQty != null){
//			    		var outputQtyValue;
//			    		if($(outputQty.target).numberspinner('getValue')){
//			    			outputQtyValue = new BigDecimal($(outputQty.target).numberspinner('getValue'));
//			    		}else{
//			    			outputQtyValue = new BigDecimal('0');
//			    		}
//			    		var outputMinQty = row.outputMinQty == '' || row.outputMinQty == undefined ? new BigDecimal("1") : new BigDecimal(row.outputMinQty);
//			    		if(outputQtyValue.remainder(outputMinQty) != 0){
//			    			$.messager.alert('提示','第'+(index+1)+'行物料'+row.materialName+'领用数量输入不符合要求，不是最小领用量的整数倍,请重新输入！');
//			    			row.outputQty = 0;
//			    		}
//			    	}
			    }
		})
	);
	
});

//选择仓库物料
function selectWarehouseMaterial(){
	var wareHouseId = $('#allotOutputWarehouseId').combogrid('getValue');

	var isValid = $('#editForm').form('validate');
	if (!isValid){
		$.messager.alert('提示','请将头部信息先填写完');
		return;
	}
	var oldWareHouseId = $('#outputWarehouseId').combobox('getValue');
	$('#outputWarehouseId').combobox('setValue',wareHouseId);
	$('#outputWarehouseId').combobox('disable');
	$('#showWarehouseMaterialName').searchbox('setValue','');
	
	$('#selectWarehouseMaterialListTable').datagrid({url:''});
	if(oldWareHouseId == wareHouseId){
		var rows = $('#listTable').datagrid('getRows');
		var filterIds = [];
		$.each(rows, function(i,data){
			filterIds.push(data.rowId);
		});
		$('#selectWarehouseMaterialListTable').datagrid('reload',{warehouseId:wareHouseId,filterIds:filterIds.join(",")});
	}
	
	$('#selectWarehouseMaterialListTable').datagrid('load',basicUrl + prUrl + "searchWarehouseMaterialToPage.do");
	
	leftSilpFun('selectWarehouseMaterialWin',true,9001);
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
//				$('#editWin').window('close');
				$.messager.alert('提示','保存成功！');
				$('#editForm').form('reset');
//				var table = document.getElementById("table");
//				if(table != null){
//					$('#table').datagrid('reload');
//				}
				$('#listTable').datagrid('loadData',{success:true,total: 0, rows: [] });
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
