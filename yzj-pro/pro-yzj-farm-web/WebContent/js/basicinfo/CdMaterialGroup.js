var title = '物料组维护';
var model = 'cdMaterialGroup';
var editIndex = undefined;
var prUrl='/material/';
var saveUrl=prUrl+'editCdMaterialGroup.do';
var deleteUrl=prUrl+'deleteCdMaterialGroups.do';
var searchMainListUrl=prUrl+'searchCdMaterialGroupToPage.do';
var searchDetailListUrl=prUrl+'searchCdMaterialGroupToList.do';
/** *********页面渲染开始*************************************************************************/
//查询按钮
var buttonList = getButtonByLimit({});
$(document).ready(function(){
	/**
	 * 主表加载
	 */
	$('#table').datagrid(
			j_grid({
				toolbarList:buttonList,
				url:searchMainListUrl,
				columnFields:'rowId,deletedFlag,businessCode,groupName,supGroupId,subjectId,materialType,notes',
				columnTitles:'ID,deletedFlag,物料组编码,物料组名称,上级物料组,对应科目,物料类型,备注',
				hiddenFields:'rowId,deletedFlag'
			})
	);
	
	//上级物料组
	xnComboGrid({
		id:'supGroupId',
		idField:'rowId',
		textField:'groupName',
		url:'/material/searchCdMaterialGroupToList.do',
		width:550,
		columns:[[ 	
		           	{field:'rowId',title:'ID',width:100,hidden:true},
			        {field:'businessCode',title:'物料编码',width:100},
			        {field:'groupName',title:'物料组名称',width:100}
			    ]]
	});

	//物料类型
	xnCdCombobox({
		id:'materialType',
		typeCode:'MATERIAL_TYPE',
		required:true
	});
});
/*************页面渲染结束******************************************************************************/
