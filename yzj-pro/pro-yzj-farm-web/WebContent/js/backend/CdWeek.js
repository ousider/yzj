var title = '周次维护';
var model = 'cdWeek';
var editIndex = undefined;
var prUrl='/backEnd/';
//var saveUrl=prUrl+'editcdWeek.do';
//var deleteUrl=prUrl+'deletescdWeek.do';
var searchMainListUrl=prUrl+'searchWeekByPage.do';
//var searchDetailListUrl=prUrl+'searchCdCodeDetailToList.do';
/** *********页面渲染开始*************************************************************************/
//查询按钮
var buttonList = getButtonByLimit({});

$(document).ready(function(){
	/**
	 * 主表加载
	 */
	$('#table').datagrid(
			j_grid_view({
				url:searchMainListUrl,
				columnFields:'rowId,deletedFlag,companyId,year,week,startDate,endDate',
				columnTitles:'ID,deletedFlag,公司ID,年,周,开始日期,结束日期 ',
				hiddenFields:'rowId,deletedFlag'
			})
	);
});

/*************页面渲染结束******************************************************************************/

/********************页面特殊方法开始***************************************************************************/
/**
 * 上级分类选择事件方法,主表选择上级分类，明细表必填
 */
function supTypeOnChangeFun(newValue,oldValue){
	var listTable = $('#listTable');
	listTable.datagrid('selectAll');
	var record = listTable.datagrid('getSelections');
	$.each(record,function(index,data){
		var row = listTable.datagrid('getRowIndex',data);
		data.deletedFlag = "1";
		listTable.datagrid('deleteRow',row);
	});
	listTable.datagrid('unselectAll');
	var supTypeValue = newValue;
	var option = listTable.datagrid('getColumnOption','linkValue');
	if(supTypeValue == null || supTypeValue == ''){
		option.editor.options.required = false;
	}else{
		option.editor.options.required = true;
		option.editor.options.url = basicUrl+searchDetailListUrl+'?mainId='+supTypeValue;
	}
}
/********************页面特殊方法结束***************************************************************************/