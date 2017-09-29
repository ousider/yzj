var title = '猪舍类型';
var model = 'cdModule';
var editIndex = undefined;
var prUrl='/backEnd/';
var saveUrl=prUrl+'editPigHouse.do';
var deleteUrl=prUrl+'deletePigHouses.do';
var searchMainListUrl=prUrl+'searchPigHouseToPage.do';
var searchDetailListUrl=prUrl+'searchPigHouseToList.do';
/** *********页面渲染开始*************************************************************************/
//查询按钮
$(document).ready(function(){
	/**
	 * 主表加载
	 */
	$('#table').datagrid(
			j_grid_view({
				url:searchMainListUrl,
				columnFields:'rowId,deletedFlag,houseTypeName,disinfectDay,disinfectMethod,notes',
				columnTitles:'ID,deletedFlag,猪舍类别,清洗消毒天数,消毒方法,备注',
				hiddenFields:'rowId,deletedFlag'
			})
	);
	
});
/*************页面渲染结束******************************************************************************/
