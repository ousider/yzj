var title = '猪舍管理';
var editIndex = undefined;
var prUrl='/basicInfo/';
var saveUrl=prUrl+'editHouse.do';
var deleteUrl=prUrl+'deleteHouses.do';
var searchMainListUrl=prUrl+'searchHouseToPage.do';
var searchDetailListUrl=prUrl+'searchPigpenToList.do';
var searchDetailListUrlForPiggen = prUrl +'searchPigpenForSwineryPigToList.do';
var disableUrl=prUrl+'editDisablesPigHouse.do';//猪舍停用
var enableUrl=prUrl+'editEnablesPigHouse.do';//猪舍启用
//var searchHouseToPageForAdvancedSearchUrl=prUrl+'searchHouseToPageForAdvancedSearch.do';	// 高级搜索
/** *********页面渲染开始*************************************************************************/
//查询按钮
var buttonList = getButtonByLimit({});

$(document).ready(function(){
	/**
	 * 主表加载
	 */
	$('#table').datagrid(
			j_grid({
//				toolbarList:buttonList,
				toolbar:'#tableToolbarList',				
				url:searchMainListUrl,
				columnFields:'rowId,deletedFlag,area,businessCode,sapCode,houseName,houseTypeName,depreciationPolicy,cost,houseVolume,pigQty,notes,status',
				columnTitles:'ID,deletedFlag,面积,猪舍代码,SAP编码,猪舍名称,猪舍类型,折旧期,造价,猪舍容量,猪只数量,备注,启用情况',
				hiddenFields:'rowId,deletedFlag,area',
				formatterFields:'houseName',
				formatter:[linkHouseName],
				rightColumnFields:'listQty'
				
			})
	);
	
	//是否是培育舍
	xnCdCombobox({
		id:'isFosterHouse',
		typeCode:'YES_OR_NO',
		editable:false,
		required:true,
		defaultValue:'N'
	});
	
	//延时加载细表
	setTimeout(function () {
		/**
		 * 细表加载
		 */
		$('#listTable').datagrid(
				j_detailGrid({
				dbClickEdit:true,
				columns:[[
			              	{field:'ck',checkbox:true},
			              	j_gridText({field:'rowId',title:'ID',hidden:true}),
			              	j_gridText({field:'deletedFlag',title:'deletedFlag',hidden:true}),
			              	j_gridText({field:'businessCode',title:'栏位代码'}),
			              	j_gridText({field:'pigpenName',title:'栏位名称',editor:'textbox'}),
			              	j_gridNumber({field:'length',title:'长度'}),
			              	j_gridNumber({field:'width',title:'宽度'}),
			              	j_gridNumber({field:'pigNum',title:'栏均饲养量'})
					    ]]
			})
		);
		/**
		 * 封装控件调用方法
		 */
		//产线
		xnComboGrid({
			id:'lineId',
			idField:'rowId',
			textField:'lineName',
			url:'/basicInfo/searchLineToList.do',
			width:550,
			required:false,
			columns:[[ 	
			           	{field:'rowId',title:'ID',width:100,hidden:true},
				        {field:'businessCode',title:'产线代码',width:100},
				        {field:'lineName',title:'产线名称',width:100}
				    ]]
		});
		//猪舍类型
		xnComboGrid({
			id:'houseType',
			idField:'rowId',
			textField:'houseTypeName',
			url:'/backEnd/searchPigHouseToList.do',
			width:550,
			required:true,
			onChange:houseTypeChange,
			columns:[[ 	
			           	{field:'rowId',title:'ID',width:100,hidden:true},
				        {field:'businessCode',title:'猪舍类别代码',width:100},
				        {field:'houseTypeName',title:'猪舍类别名称',width:100},
				        {field:'notes',title:'备注',width:100}
				    ]]
		});
		
		//高级搜索用的猪舍类型
		xnComboGrid({
			id:'houseTypeForAdvancedSearch',
			idField:'rowId',
			textField:'houseTypeName',
			url:'/basicInfo/searchPigHouseToListForAdvancedSearch.do',
			width:550,
			required:false,
			columns:[[ 	
			           	{field:'rowId',title:'ID',width:100,hidden:true},
				        {field:'businessCode',title:'猪舍类别代码',width:100},
				        {field:'houseTypeName',title:'猪舍类别名称',width:100},
				        {field:'notes',title:'备注',width:100}
				    ]]
		});
		$('#showHouseHis1').datagrid(
				j_grid_view({
					haveCheckBox:false,
					columnFields:'rowId,houseName,houseTypeName,swineryName,pigClass,housePigQty,area,disinfectMethod,disinfectDay,userName,serchDate',
					columnTitles:'ID,猪舍,猪舍类型,批次,猪只类型,当前存栏,面积,消毒方法,消毒时间,饲养员,查询时间',
					hiddenFields:'rowId',
					columnWidths:'5,100,200,200,150,50,50,100,100,100,100',
					fit:false,
					height:'23%',
					width:'100%'
				},'showPigHis')
		);
		$('#showHouseHis').datagrid(
				j_grid_view({
					haveCheckBox:false,
					columnFields:'rowId,swineryName,earBrand,pigClassName,pigClassDay,changeHouseDate,changeHouseDay,birthDate,day',
					columnTitles:'ID,批次,耳号,状态,状态日龄,转入日期,转入日龄,出生日期,出生日龄',
					hiddenFields:'rowId',
					columnWidths:'5,100,100,70,50,100,50,100,50',
					fit:false,
					height:'70%',
					width:'100%'
				},'showPigHis')
		);
    },500);
	
	$('#houseTypeForAdvancedSearch').combogrid({onLoadSuccess:function(data){
		$('#houseTypeForAdvancedSearch').combogrid('setValue',-1);
	}});
	
});

/*************页面渲染结束******************************************************************************/
/**
 * 耳号的超链接
 */
function linkHouseName(value,rowData,rowIndex){
	
	//添加超链接 
	return "<a href='javacript:;' class='editcls' onclick='showHouseCard("+rowData.rowId+");'>"+value+"</a>";  
}
function showHouseCard(houseId){
	/*alert(0);*/
	leftSilpFun('pigHouseCard');
	$('#showHouseHis1').datagrid('load',basicUrl+prUrl+'searchHouseDetailedToPage.do'+'?houseId='+houseId);
	$('#showHouseHis').datagrid('load',basicUrl+prUrl+'searchHousePigDetailedToPage.do'+'?houseId='+houseId);
//	alert(0);
}
/********************页面特殊方法开始***************************************************************************/
function noEventDetailAdd(){
	var isValid = $('#addNum').numberspinner('isValid');
	if(isValid){
		var listTable = $('#listTable');
		var allRows = listTable.datagrid('getRows');
		var length = allRows.length;
		var num = parseInt($('#addNum').val(),10);
		var feedPigNum = parseInt($('#feedPigNum').val(),10);
		var pigpenLength = parseInt($('#pigpenLength').val(),10);
		var pigpenWidth = parseInt($('#pigpenWidth').val(),10);
		if(length + num > 999){
			$.messager.alert('警告','明细表数据不能超过999行！');
		}else{
			for(var i = 0 ; i < num ; i++){
				var defaultValues = {
						status:'1',
						deletedFlag:'0',
						length:pigpenLength,
						width:pigpenWidth,
						pigNum:feedPigNum
					}; 
				listTable.datagrid('appendRow',defaultValues);
			}
			if (endEditing()){
				editIndex = length;
				var fistEditField = getFistEditField(listTable);
				listTable.datagrid('editCell', {index:editIndex,field:fistEditField});
				var editor = listTable.datagrid('getEditor', {index:editIndex,field:fistEditField});
				var eidtCellInput = $(editor.target[0].parentNode).find('.textbox-text.validatebox-text');
				if(eidtCellInput.length == 0){
					$(editor.target)[0].focus();
					$(editor.target).keydown(function(e){
						editCellTabDownFun(e,listTable,editIndex,fistEditField);
				    });
				}else{
					eidtCellInput[0].focus();
					eidtCellInput.keydown(function(e){
						editCellTabDownFun(e,listTable,editIndex,fistEditField);
				    });
				}
			}
		}
	}
}

///**
// * 高级查询
// */
//function onBtnSearch(){
//	
//	rightSlipFun('rightSlipWin_390',390);
//	jAjax.submit_form_grid('searchForm','table',searchHouseToPageForAdvancedSearchUrl);
//}
///**
// * 高级查询重置
// */
//function onBtnReSearch(){
//	$('#searchForm').form('reset');
//	$('#houseTypeForAdvancedSearch').combogrid('setValue',-1);
//}
/**
 * 猪舍类型改变方法
 * @param newValue
 * @param oldValue
 */
function houseTypeChange(newValue,oldValue){
	if(newValue == 9){
		$('#isFosterHouseItem').css('visibility','visible');
	}else{
		$('#isFosterHouseItem').css('visibility','hidden');
	}
	
	if(oldValue == ""){
		return;
	}
	var isValid = $('#houseType').combogrid('isValid');
	var columns = [
	              	{field:'ck',checkbox:true},
	              	j_gridText({field:'rowId',title:'ID',hidden:true}),
	              	j_gridText({field:'deletedFlag',title:'deletedFlag',hidden:true}),
	              	j_gridText({field:'businessCode',title:'栏位代码'}),
	              	j_gridText({field:'pigpenName',title:'栏位名称',editor:'textbox'}),
	              	j_gridNumber({field:'length',title:'长度'}),
	              	j_gridNumber({field:'width',title:'宽度'})
			    ];	
	//$('#listTable').datagrid('load',basicUrl+searchDetailListUrl+'?mainId='+$('#rowId').val());
	if(isValid && newValue == 6){
		columns.push(	j_gridNumber({field:'pigNum',title:'栏均仔猪饲养量'}));
		$('#listTable').datagrid({
			url:basicUrl+searchDetailListUrl+'?mainId='+$('#rowId').val(),
			columns:[columns]
		});
	}else{
		columns.push(	j_gridNumber({field:'pigNum',title:'栏均饲养量'}));
		$('#listTable').datagrid({
			url:basicUrl+searchDetailListUrl+'?mainId='+$('#rowId').val(),
			columns:[columns]
		});
	}
}
/**
 * 新增
 * pms{
 * 	model:模块代码
 * 	title:模块名称
 * }
 */
function onBtnAdd(){
	$('#editWin').window({
		title:'新增'+ title
	});
	$('#isFosterHouseItem').css('visibility','hidden');
	$('#editWin').window('open');
	$('#notBillInputForm').form('reset');
	$('#houseType').combogrid('readonly',false);
	$('#btnSave').css('display','inline-block');
	var listTable = document.getElementById('listTable');
	if(listTable!=null){
		$('#listTable').datagrid('loadData',{success:true,total: 0, rows: [] });
	}
}
/**
 * 编辑
 * pms{
 * 	model:模块代码
 * 	title:模块名称
 * }
 */
function onBtnEdit(){
	var record = $('#table').datagrid('getSelections');
	var length = record.length;
	if(length < 1){
		$.messager.alert('警告','请选择一条数据！');
	}else if(length > 1){
		$.messager.alert('警告','只能选择一条数据！');
	}else{
		$('#isFosterHouseItem').css('visibility','hidden');
		$('#editWin').window({
			title:'编辑'+ title
		});
		$('#editWin').window('open');
		$('#editForm').form('load',record[0]);
		$('#btnSave').css('display','inline-block');
		$('#houseType').combogrid('readonly',true);
		var listTable = document.getElementById('listTable');
		if(listTable!=null){
			var mainId;
			if(record[0].ROW_ID!=null){
				mainId=record[0].ROW_ID;
			}else{
				mainId=record[0].rowId;
			}
			$('#listTable').datagrid('load',basicUrl+searchDetailListUrl+'?mainId='+mainId);
		}
	}
}
function onBtnDisableStart(){
	var record = $('#table').datagrid('getSelections');
	var length = record.length;
	if(length < 1){
		$.messager.alert('警告','至少一条数据！');
	}
	else{
		$('#stopDatePanel').dialog('open');
	}	
	
}
function dialogCancel(dialog){
	$('#'+dialog).dialog('close');
}
function onBtnDisableEnter(){
	var record = $('#table').datagrid('getSelections');
	var stopDate = $('#stopDate').val();
	var length = record.length;
	if(length < 1){
		$.messager.alert('警告','请选择一条数据！');
	}else{
		$.messager.confirm('提示', '确定要停用这'+record.length+'条记录吗？', function(r){
			if (r){
				$.messager.progress();	
				var ids = [];
				$.each(record,function(index,data){
					if(data.ROW_ID!=null){
						ids.push(data.ROW_ID);
					}else{
						ids.push(data.rowId);
					}
					
				});
				$.ajax({
				    type: 'POST',
				    url: basicUrl+disableUrl ,
				    data: {
				    	'ids':ids,'stopDate':stopDate
				    } ,
				    success: function(response){
				    	$.messager.progress('close');
				    	if(response.success){
				    		$.messager.alert('警告','停用成功！');
							$('#stopDatePanel').dialog('close')				    		
				    		$('#table').datagrid('reload');
				    	}else{
				    		$.messager.alert({
			                     title: '错误',
			                     msg: response.errorMsg
			                 });
				    	}
				    	
				    },
				    dataType:'JSON'
				});
			}
		});
		
	}
}
function onBtnEnable(){
	var record = $('#table').datagrid('getSelections');
	var length = record.length;
	if(length < 1){
		$.messager.alert('警告','请选择一条数据！');
	}else{
		$.messager.confirm('提示', '确定要启用这'+record.length+'条记录吗？', function(r){
			if (r){
				$.messager.progress();	
				var ids = [];
				$.each(record,function(index,data){
					if(data.ROW_ID!=null){
						ids.push(data.ROW_ID);
					}else{
						ids.push(data.rowId);
					}
					
				});
				$.ajax({
				    type: 'POST',
				    url: basicUrl+enableUrl ,
				    data: {
				    	'ids':ids
				    } ,
				    success: function(response){
				    	$.messager.progress('close');
				    	if(response.success){
				    		$.messager.alert('警告','启用成功！');
				    		$('#table').datagrid('reload');
				    	}else{
				    		$.messager.alert({
			                     title: '错误',
			                     msg: response.errorMsg
			                 });
				    	}
				    	
				    },
				    dataType:'JSON'
				});
			}
		});
		
	}
}


/********************页面特殊方法结束***************************************************************************/