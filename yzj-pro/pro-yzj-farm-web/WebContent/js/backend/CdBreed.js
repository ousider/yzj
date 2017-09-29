var title = '品种库';
var model = 'cdModule';
var editIndex = undefined;
var prUrl='/backEnd/';
var saveUrl=prUrl+'editBreed.do';
var deleteUrl=prUrl+'deleteBreeds.do';
var searchMainListUrl=prUrl+'searchBreedToPage.do';
var searchDetailListUrl=prUrl+'searchBreedToList.do';
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
				columnFields:'rowId,deletedFlag,sapCode,breedCode,breedName,notes',
				columnTitles:'ID,deletedFlag,sap编码,品种代码,品种名称,备注',
				hiddenFields:'rowId,deletedFlag'
			})
	);
	
});
/*************页面渲染结束******************************************************************************/
