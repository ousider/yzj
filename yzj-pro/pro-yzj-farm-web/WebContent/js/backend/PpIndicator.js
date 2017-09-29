var title = '指标库建模';
var model = 'ppIndicator';
var editIndex = undefined;
var prUrl='/backEnd/';
var saveUrl=prUrl+'editIndicator.do';
var deleteUrl=prUrl+'deleteIndicators.do';
var searchMainListUrl=prUrl+'searchIndicatorToPage.do';
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
				columnFields:'rowId,deletedFlag,businessCode,indName,standardValue,minValue,maxValue,section,unit,formula,description',
				columnTitles:'ID,deletedFlag,编号,指标名称,标准值,最小值,最大值,正负区间,单位,计算公式,描述',
				hiddenFields:'rowId,deletedFlag',
				rightColumnFields:'standardValue,minValue,maxValue,section,formula'
			})
	);
});

/*************页面渲染结束******************************************************************************/
