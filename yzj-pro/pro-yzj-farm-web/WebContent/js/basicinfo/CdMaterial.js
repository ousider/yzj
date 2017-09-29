var editIndex = undefined;
var prUrl='/material/';
var saveUrl=prUrl+'editMaterial.do';
var deleteUrl=prUrl+'deleteMaterials.do';
var searchMainListUrl=prUrl+'searchMaterialToPage.do';
/*var searchDetailListUrl=prUrl+'searchMaterialToList.do';*/
var searchCdMaterialToPageForAdvancedSearchUrl = prUrl+'searchCdMaterialToPageForAdvancedSearch.do'; //高级查询
/** *********页面渲染开始*************************************************************************/
//查询按钮
var buttonList = getButtonByLimit({});
var materialId = 0;
$(document).ready(function(){
	
	/**
	 * 主表加载
	 */
	xnCdCombobox({ 
		id:'showMaterialType',
		typeCode:'MATERIAL_TYPE',
		onSelect:changeMaterialType,
		hasAll:true
	});
	$('#table').datagrid(
			j_grid({
				url:searchMainListUrl,
				columnFields:'rowId,deletedFlag,businessCode,materialName,groupId,materialType,materialTypeName,unit,isWarehouseName,isPurchaseName,isSellName,defaultWarehouse,notes',
				columnTitles:'ID,deletedFlag,物料代码,物料名称,物料组,物料类型(英文),物料类型,单位,仓库物料,采购物料,销售物料,默认仓库,备注',
				hiddenFields:'rowId,deletedFlag,materialType,groupId'
			})
	);
	
	setTimeout(function(){
		//仓库物料
		xnRadioBox({
			id:'isWarehouse',
			name:'isWarehouse',
			typeCode:'YES_OR_NO',
			//是否有监听事件
			onChange:false,
			defaultValue:'N'
		});

		//采购物料
		xnRadioBox({
			id:'isPurchase',
			name:'isPurchase',
			typeCode:'YES_OR_NO',
			//是否有监听事件
			onChange:false,
			defaultValue:'N'
		});

		//销售物料
		xnRadioBox({
			id:'isSell',
			name:'isSell',
			typeCode:'YES_OR_NO',
			//是否有监听事件
			onChange:false,
			defaultValue:'N'
		});

		//物料组
//		xnComboGrid({
//			id:'groupId',
//			idField:'rowId',
//			textField:'materialName',
//			url:'/material/searchCdMaterialGroupToList.do',
//			width:550,
//			columns:[[ 	
//			           	{field:'rowId',title:'ID',width:100,hidden:true},
//				        {field:'businessCode',title:'物料代码',width:100},
//				        {field:'groupName',title:'物料组名称',width:100},
//				    ]]
//		});

		//计量单位
		xnCdCombobox({
			id:'unit',
			required:true,
			sameField:true,
			typeCode:'UNIT',
			onChange:changeOutputMinQtyUnit
		});
		
		//物料类型
		xnCdCombobox({
			id:'materialType',
			readonly:true,
			typeCode:'MATERIAL_TYPE'
		});
		
		//高级查询物料类型
		xnCdCombobox({
			id:'materialTypeForAdvancedSearch',
			readonly:false,
			typeCode:'MATERIAL_TYPE'
		});
		
		//供应商
		xnComboGrid({
			id:'supplierId',
			required:true,
			idField:'rowId',
			textField:'companyName',
			url:'/basicInfo/searchCustomerAndSupplierToList.do?cussupType=S',
			width:550,
			columns:[[ 	
			           	{field:'rowId',title:'ID',width:100,hidden:true},
				        {field:'companyCode',title:'供应商编码',width:100},
				        {field:'companyName',title:'供应商名称',width:100},
				        {field:'companyNameEn',title:'英文名称',width:100}
				    ]]
		});
		
		// 物料大类
		xnCdCombobox({
		    id:'materialFirstClass',
		    typeCode:'MATERIAL_FIRST_CLASS',
		    editable:false,
//		    panelHeight:300,
		    required:true,
		    linkField:'materialSecondClass',
		    linkCode:'MATERIAL_SECOND_CLASS'
		});
		
		// 物料中类
		xnCdCombobox({
		    id:'materialSecondClass',
		    required:true,
		    editable:false
		});
		
	},500);
});
/*************页面渲染结束******************************************************************************/
/**
 * 新增
 */
function onBtnAdd(){
	$("#manufacturer").textbox('readonly',false);
	$("#supplierId").combobox('readonly',false);
	$("#materialFirstClass").combobox('readonly',false);
	$("#materialSecondClass").combobox('readonly',false);
	
	var materialType = $('#showMaterialType').combobox('getValue');
	var materialTypeText = $('#showMaterialType').combobox('getText');
	if(materialType == null || materialType == ''){
		$.messager.alert('警告','请选择物料类型！');
	}else{
		loadDetatil('add','新增'+materialTypeText,null,materialType);
	 	if ("Boar"==materialType || "Sow"==materialType || "PorkPig"==materialType || "Sperm"==materialType){
    		$('#supplyChianComInfo').css('display','none');
    		$("#manufacturer").textbox('disable');
    		$("#supplierId").combobox('disable');
    		$("#materialFirstClass").combobox('disable');
    		$("#materialSecondClass").combobox('disable');
       	}else{
        	$('#supplyChianComInfo').css('display','block');
    		$("#manufacturer").textbox('enable');
    		$("#supplierId").combobox('enable');
    		$("#materialFirstClass").combobox('enable');
    		$("#materialSecondClass").combobox('enable');
        }
	}
}

/**
 * 编辑
 */
function onBtnEdit(){
	var record = $('#table').datagrid('getSelections');
	var length = record.length;
	if(length < 1){
		$.messager.alert('警告','请选择一条数据！');
	}else if(length > 1){
		$.messager.alert('警告','只能选择一条数据！');
	}else{
		loadDetatil('edit','编辑',record[0],null);
		var materialType = record[0].materialType;
		$("#manufacturer").textbox('readonly',true);
		$("#supplierId").combobox('readonly',true);
		$("#materialFirstClass").combobox('readonly',true);
		$("#materialSecondClass").combobox('readonly',true);
		
		if ("Boar"==materialType || "Sow"==materialType || "PorkPig"==materialType || "Sperm"==materialType){
    		$('#supplyChianComInfo').css('display','none');
    		$("#manufacturer").textbox('disable');
    		$("#supplierId").combobox('disable');
    		$("#materialFirstClass").combobox('disable');
    		$("#materialSecondClass").combobox('disable');
       	}else{
        	$('#supplyChianComInfo').css('display','block');
    		$("#manufacturer").textbox('enable');
    		$("#supplierId").combobox('enable');
    		$("#materialFirstClass").combobox('enable');
    		$("#materialSecondClass").combobox('enable');
        }
	}
}
/**
 * 双击查看
 * @param index
 * @param row
 */
function onDblClickRow(index,row){
	loadDetatil('view','查看',row,null);
}
/**
 * 查看
 */
function onBtnView(){
	
	var record = $('#table').datagrid('getSelections');
	var length = record.length;
	if(length < 1){
		$.messager.alert('警告','请选择一条数据！');
	}else if(length > 1){
		$.messager.alert('警告','只能选择一条数据！');
	}else{
		loadDetatil('view','查看',record[0],null);
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
				var materialList = [];
				$.each(record,function(index,data){
					materialList.push({
						materialId:data.rowId,
						materialType:data.materialType
						});
				});
				
				var jsonMaterialList = JSON.stringify(materialList);
				$.ajax({
				    type: 'POST',
				    url: basicUrl+deleteUrl ,
				    data: {
				    	'materialList':jsonMaterialList
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
		});
		
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
		loadDetatil('copyadd','复制新增',record[0],null);
	}
}

/**
 * 根本物料类型重新查询
 */
function changeMaterialType(rec){
	
	$('#table').datagrid('load',{materialType:rec.codeValue});
}

/**
 *加载明细的操作
 */
function loadDetatil(oprType, title, record,type) {
	var materialType;
	var mainRowId;
	
	// 查看操作
	if (oprType == "view") {
		
		$('#btnSave').css('display','none');
	} else {
		$('#btnSave').css('display','inline-block');
	}
	
	// 新增操作,title直接根据下拉框组合，传值
	if (oprType == "add") {
		materialType=type;
	} else {
		materialType=record.materialType
		title=title+record.materialTypeName;
		mainRowId=record.rowId;
		materialId = record.rowId;
	}
	//物料组名称
	//$('#groupId').combogrid('grid').datagrid('load',{pigType:materialType});
	
	$('#include').load(basicUrl + '/jsp/basicinfo/Cd'+materialType + '.jsp', function() {
		
				$('#editWin').window({
					title : title,
				});
				$('#editWin').window('open');

				$('#editForm').form('reset');
				// 新增操作
				if (oprType != "add"){
					var data = searchDetailMaterial(record);
					//复制新增操作
					if (oprType == "copyadd") {
						data.rowId = 0;
					}else if(oprType == "edit"){
						data.rowId = mainRowId;
					}
					
					$('#editForm').form('load', data);
				}
				$('#materialType').combobox('setValue',materialType);
			});
}

/**
 * @param recode 选中的那行数据
 */
function searchDetailMaterial(recode){

	return jAjax.submit(prUrl+'searchDetailMaterialToList.do', recode);
}

/**
 * 高级查询搜索
 */
function onBtnSearch(para,url){
	var winId = $(para).parent().parent().attr('id');
    rightSlipFun(winId,390);
    var selectedData = $('#searchForm #materialTypeForAdvancedSearch').combo('getValue');
    jAjax.submit_form_grid('searchForm','table',url);
    $('#showMaterialType').combobox('setValue', selectedData);
    
}

/**
 * 自动填写最小领用的单位
 */
function changeOutputMinQtyUnit(newValue,oldValue){
	$('#outputMinQtyUnit').html(newValue);
}
