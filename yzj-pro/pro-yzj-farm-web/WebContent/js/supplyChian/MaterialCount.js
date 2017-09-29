var editIndex = undefined;
var prUrl = '/supplyChian/';
var searchMainListUrl = prUrl + 'searchMonthAccountToList.do';
var saveUrl = prUrl + 'editMonthAccount.do'
/** *********页面渲染开始*************************************************************************/

$(document).ready(function(){
	/**
	 * 主表加载
	 */
	
	$('#listTable').datagrid(
			j_detailGrid({
				toolbar:"#tableToolbarList",
				width:'100%',
				height:'100%',
//				url:searchMainListUrl,
				dbClickEdit:true,
				checkOnSelect:true,
				columns:[[
//			      	{field:'ck',checkbox:true},
			      	j_gridText({field:'materialName',title:'物料名称',width:200}),
			    	j_gridText({field:'materialFirstClassName',title:'大类',width:60,sortable:false}),
			    	j_gridText({field:'materialSecondClassName',title:'中类',width:60,sortable:false}),
			      	j_gridText({field:'specAll',title:'规格',width:100}),
			      	j_gridText({field:'purchaseOrFree',title:'购/赠料',width:60}),
			      	j_gridText({field:'supplierSortName',title:'供应商',width:100}),
			      	j_gridText({field:'manufacturer',title:'厂家',width:70}),
			      	j_gridText({field:'existsQtyName',title:'库存数量',width:60}),
	              	j_gridNumber({field:'comfirmQty',title:'确认数量',formatter:function(value,row){
              			value = value == null || value == undefined || value == '' ? 0 : value;
              			return value+row.unit;
	              	},min:0,precision:3,width:80}),
	              	j_gridText({field:'minEffectiveDate',title:'最近生产/有效日期',width:100})
			    ]],
			    onBeginEdit:function(index,row){

			    },
			    onEndEdit:function(index,row){

			    }
		})
	);
	
	//盘点仓库
	xnComboGrid({
		id:'warehouseId',
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
		onChange:warehouseChange
	});
});

function warehouseChange(newValue, oldValue){
	$('#listTable').datagrid('reload',basicUrl + searchMainListUrl + '?warehouseId=' +newValue);
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
				if(listTable!= null){
					$('#listTable').datagrid('loadData',{success:true,total: 0, rows: [] });
				}
				$.messager.alert('提示','盘点成功！');
				$('#editForm').form('reset');
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