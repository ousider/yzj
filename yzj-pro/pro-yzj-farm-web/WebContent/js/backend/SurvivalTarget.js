var title = '目标成活率';
var editIndex = undefined;
var prUrl = '/backEnd/';
var searchSurvivalTargetList = prUrl + 'searchSurvivalTargetList.do';
var saveUrl=prUrl+'editSurvaivalTargetList.do';
var deleteUrl = prUrl+'deleteSurvaivalTarget.do';
/** *********页面渲染开始*************************************************************************/
$(document).ready(function(){
	$('#targetNumber').numberspinner({
    	min: 0,
    	max: 100,
    	prompt:'请输入目标数值',
    	increment:1
	});
	/**
	 * 主表加载
	 */
	$('#table').datagrid(
			j_grid_view({
				toolbarList:"#tableToolbar",
				url:searchSurvivalTargetList,
				columnFields:'rowId,startDate,endDate,targetNumber,createDate,notes',
				columnTitles:'ID,开始日期,结束日期,目标数值,创建日期,备注',
				hiddenFields:'rowId',
			}));
});

