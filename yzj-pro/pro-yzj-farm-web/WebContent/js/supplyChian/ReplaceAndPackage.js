var title = '套餐及赠料管理';
var prUrl = '/supplyChian/';
var searchMainListUrl = prUrl + 'searchReplaceAndPackageToPage.do';
var saveUrl = prUrl + 'editReplaceAndPackage.do';
var deleteUrl = prUrl + 'deleteReplaceAndPackage.do';
var searchDetailListUrl = prUrl + 'searchReplaceAndPackageDetailToList.do';
var editIndex = undefined;
/** *********页面渲染开始*************************************************************************/
//查询按钮
var buttonList = getButtonByLimit({});
var detailDefaultValues = {
		status:'1',
		deletedFlag:'0',
		//设置默认值
		mustBeFree:'Y',
		mustBeFreeName:'是'
};
$(document).ready(function(){
	/**
	 * 主表加载
	 */
	
	$('#table').datagrid(
			j_grid_view({
				toolbarList:"#tableToolbarList",
				url:searchMainListUrl,
				columnFields:'rowId,groupName,supplierSortName,groupModelName,notes',
				columnTitles:'ID,分组名称,供应商,模式,备注',
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
		              	j_gridText({field:'materialName',title:'物料名称',width:100}),
		              	j_gridText({field:'materialFirstClassName',title:'大类',width:60,sortable:false}),
		            	j_gridText({field:'materialSecondClassName',title:'中类',width:60,sortable:false}),
		              	j_gridText({field:'supplierSortName',title:'供应商',width:100}),
		              	j_gridText({field:'manufacturer',title:'厂家',width:100}),
		              	j_gridText({field:'specAll',title:'规格',width:60}),
		              	j_gridNumber({field:'mainProportionQty',title:'比例数量',formatter:function(value,row){
	              			value = value == null || value == undefined || value == '' ? 0 : value;
		              		return value;
		              	},min:0,precision:0,width:80})
				    ]]
			})
		);
		
		//供应商
		xnCombobox({
			id:"supplierId",
			url:'/supplyChian/searchCompanyFromRequireBillToList.do?searchType=All&purchaseFlag=1',
			valueField:"supplierId",
			textField:"supplierSortName",
			required:true,
			prompt:'修改供应商会清空明细',
			onChange:changeSupplier
		});
		
		//分组模式
		xnCdCombobox({
			id:'groupModel',
			required:true,
			// 移除互相替换物料（功能删除）
			// 固定领用去除
			excludeValue:[1,4],
			typeCode:'REPLACE_AND_PACKAGE_GROUP_MODEL',
			onChange:groupModelChange
		});
		
	},500);
});

function groupModelChange(newValue,oldValue){
	$('#listTable').datagrid('loadData',{success:true,total: 0, rows: [] });
	if(newValue=='2'){
		$('#listTable').datagrid(
				j_detailGrid({
					toolbar:"#tableToolbar",
					width:'100%',
					height:'100%',
					dbClickEdit:true,
					checkOnSelect:true,
					columns:[[
				      	{field:'ck',checkbox:true},
		              	j_gridText({field:'materialName',title:'物料名称',width:100}),
		              	j_gridText({field:'materialFirstClassName',title:'大类',width:100,sortable:false}),
		            	j_gridText({field:'materialSecondClassName',title:'中类',width:100,sortable:false}),
		              	j_gridText({field:'supplierSortName',title:'供应商',width:100}),
		              	j_gridText({field:'manufacturer',title:'厂家',width:100}),
		              	j_gridText({field:'specAll',title:'规格',width:60}),
		              	j_gridText({field:'purchaseOrFree',title:'购/赠料',width:60,
		              		formatter:function(value,row){
		              			return row.purchaseOrFreeName;
		              		},
		              		editor:
		              		xnGridCdComboBox({
		              			field:'purchaseOrFree',
		              			typeCode:'PURCHASE_OR_FREE',
		              			editable:false
		              		})
		              	}),
		              	j_gridText({field:'mustBeFree',title:'自动带出',width:60,
		              		formatter:function(value,row){
		              			return row.mustBeFreeName;
		              		},
		              		editor:
			              		xnGridCdComboBox({
			              			field:'mustBeFree',
			              			typeCode:'YES_OR_NO',
			              			editable:false,
			              			defaultValue:'Y'
			              		})
		              	}),
		              	j_gridNumber({field:'mainProportionQty',title:'比例数量',formatter:function(value,row){
	              			value = value == null || value == undefined || value == '' ? 0 : value;
		              		return value;
		              	},min:0,precision:0,width:80})
				    ]],
				    onEndEdit:function(index,row){
				    	var purchaseOrFree = $('#listTable').datagrid('getEditor', {
			                index: index,
			                field: 'purchaseOrFree'
			            });
				    	if(purchaseOrFree != null){
				    		row.purchaseOrFreeName = $(purchaseOrFree.target).combogrid('getText');
				    	}
				    	var mustBeFree = $('#listTable').datagrid('getEditor', {
			                index: index,
			                field: 'mustBeFree'
			            });
				    	if(mustBeFree != null){
				    		row.mustBeFreeName = $(mustBeFree.target).combogrid('getText');
				    	}
				    }
			})
		);
	}else{
		$('#listTable').datagrid(
				j_detailGrid({
					toolbar:"#tableToolbar",
					width:'100%',
					height:'100%',
					dbClickEdit:true,
					checkOnSelect:true,
					columns:[[
				      	{field:'ck',checkbox:true},
		              	j_gridText({field:'materialName',title:'物料名称',width:100}),
		              	j_gridText({field:'materialFirstClassName',title:'大类',width:100,sortable:false}),
		            	j_gridText({field:'materialSecondClassName',title:'中类',width:100,sortable:false}),
		              	j_gridText({field:'supplierSortName',title:'供应商',width:100}),
		              	j_gridText({field:'manufacturer',title:'厂家',width:100}),
		              	j_gridText({field:'specAll',title:'规格',width:60}),
		              	j_gridNumber({field:'mainProportionQty',title:'比例数量',formatter:function(value,row){
	              			value = value == null || value == undefined || value == '' ? 0 : value;
		              		return value;
		              	},min:0,precision:0,width:80})
				    ]]
			})
		);
	}
}
function onBtnAdd(){
	$('#editWin').window({title:'新增'+ title});
	$('#editWin').window('open');
	$('#onDetailAdd').css('display','inline-block');
//	$('#onDetailDelete').css('display','inline-block');
	$('#btnSave').css('display','inline-block');
	
	$('#rowId').val(0);
	$('#groupName').textbox('readonly', false);
	$('#supplierId').combobox('readonly', false);
	$('#groupModel').combobox('readonly', false);
	$('#btnSave').attr('disabled',false).removeClass('btn-disabled');
	$('#listTable').datagrid('loadData',{success:true,total: 0, rows: [] });
}

/**
 * 编辑
 * pms{
 * 	model:模块代码
 * 	title:模块名称
 * }
 */
function onBtnEdit(){
	var record = $('#table').datagrid('getSelections');
	var length = record.length;
	if(length < 1){
		$.messager.alert('警告','请选择一条数据！');
	}else if(length > 1){
		$.messager.alert('警告','只能选择一条数据！');
	}else{
		$('#editWin').window({
			title:'编辑'+ title
		});
		$('#editWin').window('open');
		$('#editForm').form('load',record[0]);
		$('#btnSave').css('display','inline-block');
		
		$('#groupName').textbox('readonly');
		$('#supplierId').combobox('readonly');
		$('#groupModel').combobox('readonly');
		
		var listTable = document.getElementById('listTable');
		if(listTable!=null){
			var mainId;
			if(record[0].ROW_ID!=null){
				mainId=record[0].ROW_ID;
			}else{
				mainId=record[0].rowId;
			}
			$('#listTable').datagrid('load',basicUrl+searchDetailListUrl+'?mainId='+mainId);
		}
	}
}

function changeSupplier(newValue,oldValue){
	if($('#listTable').css('display') == 'none'){
		$('#listTable').datagrid('loadData',{success:true,total: 0, rows: []});
	}
}

//选择物料
function onDetailAdd(){
	if(!$('#supplierId').combobox('isValid')){
		$.messager.alert('提示','请先选择有效供应商！');
		return;
	}
	var supplierId = $('#supplierId').combobox('getValue');
	if(supplierId==''){
		$.messager.alert('提示','请先选择供应商！');
	}else{
		leftSilpFun('selectMaterialWin',true,9001);
		$('#supplier').combobox('setValue',supplierId);
		$('#supplier').combobox('readonly');
	}
}