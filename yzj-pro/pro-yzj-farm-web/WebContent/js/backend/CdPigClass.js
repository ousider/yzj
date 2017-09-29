var title = '猪只类型';
var model = 'cdModule';
var editIndex = undefined;
var prUrl='/backEnd/';
var saveUrl=prUrl+'editPigClass.do';
var deleteUrl=prUrl+'deletePigClass.do';
var searchMainListUrl=prUrl+'searchPigClassToPage.do';
var searchDetailListUrl=prUrl+'searchPigClassToList.do';
/** *********页面渲染开始*************************************************************************/
//查询按钮
var buttonList = getButtonByLimit({});
$(document).ready(function(){
	//猪只类型
	xnRadioBox({
		id:'pigType',
		name:'pigType',
		typeCode:'PIG_TYPE',
		//是否有监听事件
		onChange:false,
		defaultValue:2
	});
	/**
	 * 主表加载
	 */
	$('#table').datagrid(
			j_grid({
				toolbarList:buttonList,
				url:searchMainListUrl,
				columnFields:'rowId,deletedFlag,businessCode,pigClassName,pigType,description,notes',
				columnTitles:'ID,deletedFlag,猪只类别代码,猪只类别名称,猪类别,定义,备注',
				hiddenFields:'rowId,deletedFlag'
			})
	);
	
});
/*************页面渲染结束******************************************************************************/
