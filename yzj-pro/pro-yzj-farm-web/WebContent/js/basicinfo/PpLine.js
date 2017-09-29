var title = '产线';
var model = 'cdModule';
var editIndex = undefined;
var prUrl='/basicInfo/';
var saveUrl=prUrl+'editLine.do';
var deleteUrl=prUrl+'deleteLines.do';
var searchMainListUrl=prUrl+'searchLineToPage.do';
var searchDetailListUrl=prUrl+'searPpLineToList.do';
var saerchLineByConditionToPageUrl=prUrl+'saerchLineByConditionToPage.do';
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
				columnFields:'rowId,deletedFlag,businessCode,lineName,notes',
				columnTitles:'ID,deletedFlag,产线代码,产线名称,备注',
				hiddenFields:'rowId,deletedFlag'
			})
	);
	
});
/*************页面渲染结束******************************************************************************/

/**
 * 高级查询
 */
function onBtnSearch(){
	
	rightSlipFun('rightSlipWin_390',390);
	jAjax.submit_form_grid('searchForm','table',saerchLineByConditionToPageUrl);
}