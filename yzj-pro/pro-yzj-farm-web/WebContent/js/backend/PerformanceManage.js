var prUrl='/backEnd/';
var saveUrl=prUrl+'editPerformanceManage.do';
var deleteUrl=prUrl+'deletePerformanceManage.do';
var searchPerformanceManageToPageUrl = prUrl+'performanceManageToPage.do';
var title = '绩效管理';
var editIndex = undefined;

$(document).ready(function(){
	
	/**
	 * 主表加载
	 */
	$('#table').datagrid(
			j_grid({
				toolbar:"#tableToolbar",
				url:searchPerformanceManageToPageUrl,
				columnFields:'rowId,businessCode,assessRegionName,assessContentName,startDate,endDate,assessIndex,minAssessIndex,maxAssessIndex,reward,offset,increasedAmount,performanceDateAreaName,designFormulas,isStartName',
				columnTitles:'单据编号,businessCode,区域,考核内容,开始日期,结束日期,达标指标,第一梯度,第二梯度,奖励金额,增减量,增加金额,计算日期范围,计算公式,是否开启',
				hiddenFields:'rowId,businessCode',
				columnWidths:'50,80,80,150,100,100,80,100,100,80,80,100,150,150,80',
			}));

	xnCdCombobox({
		id:'assessRegion',
		required:true,
		typeCode:'AREA_TYPE',
	});
	xnCdCombobox({
		id:'assessRegion_',
		typeCode:'AREA_TYPE',
	});
	xnCdCombobox({
		id:'isStart',
		required:true,
		typeCode:'START_FLAG',
	});
	xnCdCombobox({
		id:'assessContent',
		required:true,
		typeCode:'ASSESS_CONTENT',
	});
	xnCdCombobox({
		id:'assessContent_',
		typeCode:'ASSESS_CONTENT',
	});
	xnCdCombobox({
		id:'performanceDateArea',
		required:true,
		typeCode:'PERFORMANCE_DATE_AREA',
	});
});


