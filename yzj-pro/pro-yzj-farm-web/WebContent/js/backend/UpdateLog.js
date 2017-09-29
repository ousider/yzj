var title = '更新提醒';
var editIndex = undefined;
var prUrl = '/backEnd/';
var saveUrl=prUrl + 'updateLog.do';
var deleteUrl=prUrl+'deleteUpdateLog.do?';
/** *********页面渲染开始*************************************************************************/
$(document).ready(function(){
	/**
	 * 主表加载
	 */
	$('#table').datagrid(
		j_grid({
			url:'/backEnd/searchUpdateLog.do?',
			toolbar:"#tableToolbarList",
			columnFields:'rowId,updateDate,updateCuTime,version,updateLog,updateTypeName,creater,notes',
			columnTitles:'ID,更新日期,更新时间,版本,更新内容,更新类型,创建人,备注',
			hiddenFields:'rowId',
		},'table')
	);
	//更新类型
	xnCdCombobox({
		id:'updateType',
		typeCode:'updatetype',		
		editable:false,
		required:true,
		
	});	
});
