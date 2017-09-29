var title = '用户编码';
var model = 'cdBcode';
var editIndex = undefined;
var prUrl='/backEnd/';
var saveUrl=prUrl+'editCdBcodeT.do';
var deleteUrl=prUrl+'deleteCdBcodeTs.do';
var searchMainListUrl=prUrl+'searchCdBcodeTToPage.do';
var searchDetailListUrl=prUrl+'searchCdBcodeTToList.do';
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
				columnFields:'rowId,deletedFlag,bcodeName,prifixCode,houseId,isUseBdate,serialLength,limitNum',
				columnTitles:'ID,deletedFlag,业务编码名称,前缀,猪舍,是否时间,流水码长度,起始编码',
				hiddenFields:'rowId,deletedFlag',
				rightColumnFields:''
			})
	);
	//猪舍
	xnComboGrid({
		id:'houseId',
		idField:'rowId',
		textField:'houseName',
		width:550,
		columns:[[ 	
		           	{field:'rowId',title:'ID',width:100,hidden:true},
			        {field:'businessCode',title:'猪舍代码',width:100},
			        {field:'houseName',title:'猪舍名称',width:100},
			        {field:'houseVolume',title:'猪舍容量',width:100},
			        {field:'pigQty',title:'猪只数量',width:100},
			        {field:'houseTypeName',title:'猪舍类别',width:100},
			        {field:'notes',title:'备注',fitColumns:true}
			    ]]
	});
	
	//是否使用日期
	xnCdCombobox({
		id:'isUseBdate',
		typeCode:'IS_USE_BDATE'
	});
});

/*************页面渲染结束******************************************************************************/
