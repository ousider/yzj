var title = '产线';
var model = 'cdModule';
var editIndex = undefined;
var prUrl='/basicInfo/';
var saveUrl=prUrl+'editEmployee.do';
var deleteUrl=prUrl+'deleteEmployees.do';
var searchMainListUrl=prUrl+'searchEmployeeToPage.do';
var searchDetailListUrl=prUrl+'searchEmployeeToList.do';
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
				columnFields:'rowId,deletedFlag,employeeCode,employeeName,sex,notes',
				columnTitles:'ID,deletedFlag,员工代码 ,姓名 ,性别,备注',
				hiddenFields:'rowId,deletedFlag'
			})
	);
	
});
/*************页面渲染结束******************************************************************************/
