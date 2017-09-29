var title = '桌面小部件';
var model = 'parts';
var editIndex = undefined;
var prUrl='/backEnd/';
var saveUrl=prUrl+'editParts.do';
var deleteUrl=prUrl+'deletesParts.do';
var searchMainListUrl=prUrl+'searchPartsByPage.do';
//var searchDetailListUrl=prUrl+'searchCdCodeDetailToList.do';
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
				columnFields:'rowId,deletedFlag,partsCode,partsName,moduleName,iconCls,glyph,usingFlag',
				columnTitles:'ID,deletedFlag,部件编号,部件名称,模块名,图标地址,图标字体,能否使用',
				hiddenFields:'rowId,deletedFlag'
			})
	);
	//是否启用
	xnRadioBox({
		id:'usingFlag',
		name:'usingFlag',
		typeCode:'IS_ENABLE'
	});
	xnCombobox({
		id:"moduleName",
		url:"/backEnd/searchMenuBobox.do",
		valueField:"moduleId",
		textField:"moduleName"
	});
	
});

/*************页面渲染结束******************************************************************************/
