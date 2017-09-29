var title = '批次管理';
var model = 'cdModule';
var editIndex = undefined;
var prUrl='/production/';
var saveUrl=prUrl+'editSwinery.do';
var deleteUrl=prUrl+'deleteSwinerys.do';
var searchMainListUrl=prUrl+'searchSwineryToPage.do';
//var searchDetailListUrl=prUrl+'searchSwineryToList.do';
var closeUrl=prUrl+'closeSwinery.do';
var openUrl=prUrl+'openSwinery.do';
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
		onChange:true,
		defaultValue:2
	});
	/**
	 * 主表加载
	 */
	$('#table').datagrid(
			j_grid({
				toolbar:'#swineryTableToolbar',
				url:searchMainListUrl,
				columnFields:'rowId,deletedFlag,businessCode,swineryName,createSwineryTypeName,houseTypeName,statusName,pigTypeName,planHead,pigQty,avgDate,beginDate,endDate,weekNum,notes',
				columnTitles:'ID,deletedFlag,批次代码,批次名称,建立方式,猪舍类型,状态,猪类别,计划头数,猪只数,日龄,开始日期,结束日期,对应周,备注',
				hiddenFields:'rowId,deletedFlag'
			})
	);
	//产线
	xnComboGrid({
		id:'lineId',
		idField:'rowId',
		textField:'lineName',
		url:'/basicInfo/searchLineToList.do',
		width:550,
		required:true,
		columns:[[ 	
		           	{field:'rowId',title:'ID',width:100,hidden:true},
			        {field:'businessCode',title:'产线代码',width:100},
			        {field:'lineName',title:'产线名称',width:100}
			    ]]
	});
	
	//猪类别
	xnCdCombobox({
	    id:'pigTypeName',
	    typeCode:'PIG_TYPE',
	    hasAll:true
	});

	//猪舍类型
	xnCombobox({
		id:"houseType",
		valueField:"rowId",
		textField:"houseTypeName",
		url:'/backEnd/searchPigHouseToList.do',
		required:true
	});
	
	//高级搜索猪舍类型
	xnCombobox({
		id:"searchHouseType",
		valueField:"rowId",
		textField:"houseTypeName",
		url:'/backEnd/searchPigHouseToList.do',
		includeValue:[2,3,6,8,9],
		hasAll:true
	});
	
});
/*************页面渲染结束******************************************************************************/

function onPigTypeChange(object){
	onPigTypeChangeHouseType($(object).val());
}

function onPigTypeChangeHouseType(value){
	var includeValue = [];
	if(value == '1'){
		includeValue = [2];
	}else if (value == '2'){
		includeValue = [2,3];
	}else if (value == '3'){
		includeValue = [6,8,9];
	}
	
	//猪舍类型
	xnCombobox({
		id:"houseType",
		valueField:"rowId",
		textField:"houseTypeName",
		url:'/backEnd/searchPigHouseToList.do',
		required:true,
		includeValue:includeValue
	});
	
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
    	    var pigTypes = document.getElementsByName('pigType');
    	    var len = pigTypes.length;
    	    for(var i = 0;i<len;i++){
    		pigTypes[i].disabled=true;
    	    }
		$('#editWin').window({
			title:'编辑'+ title
		});
		
		onPigTypeChangeHouseType(record[0].pigType);
		
		if(record[0].createType==1){
			disableForm();
		}
		$('#editWin').window('open');
		$('#editForm').form('load',record[0]);
		
		$('#btnSave').css('display','inline-block');
		var listTable = document.getElementById('listTable');
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
	
	onPigTypeChangeHouseType('2');
	
	$('#editWin').window('open');
	$('#editForm').form('reset');
	$('#btnSave').css('display','inline-block');
	var listTable = document.getElementById('listTable');
	if(listTable!=null){
		$('#listTable').datagrid('loadData',{success:true,total: 0, rows: [] });
	}
	enableForm();
}
/**
 * 复制新增
 */
function onBtnCopyAdd(){
	var record = $('#table').datagrid('getSelections');
	var length = record.length;
	if(length < 1){
		$.messager.alert('警告','请选择一条数据！');
	}else if(length > 1){
		$.messager.alert('警告','只能选择一条数据！');
	}else{
		$('#editWin').window({
			title:'复制新增'+ title
		});
		$('#editWin').window('open');
//		var listTable = document.getElementById('listTable');
//		if(listTable!=null){
//			var mainId;
//			if(record[0].ROW_ID!=null){
//				mainId=record[0].ROW_ID;
//			}else{
//				mainId=record[0].rowId;
//			}
//			$('#listTable').datagrid('load',basicUrl+searchDetailListUrl+'?mainId='+mainId);
//		}
		var rowId = record[0].rowId;
		record[0].rowId = 0;
		onPigTypeChangeHouseType(record[0].pigType);
		$('#editForm').form('load',record[0]);
		
		record[0].rowId = rowId;
		$('#btnSave').css('display','inline-block');
		enableForm();
	}
}

function enableForm(){
    $('#beginDate').datebox('enable');
    $('#swineryName').textbox('enable');
    $('#planHead').numberspinner('enable');
    $('#weekNum').numberspinner('enable');
    $('#inputNotes').textbox('enable');
    $('#houseType').textbox('enable');
    var pigTypes = document.getElementsByName('pigType');
    var len = pigTypes.length;
    for(var i = 0;i<len;i++){
    	pigTypes[i].disabled=false;
    }
}

function disableForm(){
    var pigTypes = document.getElementsByName('pigType');
    var len = pigTypes.length;
    for(var i = 0;i<len;i++){
    	pigTypes[i].disabled=true;
    }
    $('#beginDate').datebox('disable');
    $('#swineryName').textbox('disable');
    $('#planHead').numberspinner('disable');
    $('#weekNum').numberspinner('disable');
    $('#inputNotes').textbox('disable');
    $('#houseType').textbox('disable');
}
/**
 * 开启猪群
 */
function onBtnOpen(){
	var rows = $('#table').datagrid("getSelections");
	if(rows.length==0){
		$.messager.alert('提示','请选择猪群');
	}
	var ids=[];
	for(var i = 0;i<rows.length;i++){
//		if(rows[i].createSwineryTypeName == '自动'){
//			$.messager.alert('提示','选中的猪群中存在系统自动建的猪群，无法开启！');
//			return;
//		}
		if(rows[i].statusName == '开启'){
			$.messager.alert('提示','选中的猪群中存在开启状态猪群，请勿重复操作！');
			return;
		}
		ids.push(rows[i].rowId);
	}
	$.post({
		url:basicUrl + openUrl,
		data:{
			ids:ids,
		},
		success:function(response){
			response=eval('('+response+')');
			if(response.success){
				$.messager.progress('close');
				$('#table').datagrid('reload');
				$.messager.alert('提示','操作成功！');
			}else{
				$.messager.alert({
                    title: '错误',
                    msg: response.errorMsg
                });
			}
		}
	});
}
/**
 * 关闭猪群
 */
function onBtnClose(){
	var rows = $('#table').datagrid("getSelections");
	if(rows.length==0){
		$.messager.alert('提示','请选择猪群');
	}
	var ids=[];
	for(var i = 0;i<rows.length;i++){
//		if(rows[i].createSwineryTypeName == '自动'){
//			$.messager.alert('提示','选中的猪群中存在系统自动建的猪群，无法关闭！');
//			return;
//		}
		if(rows[i].pigQty>0){
			$.messager.alert('提示','选中的猪群中尚有猪只，无法关闭！');
			return;
		}
		if(rows[i].statusName == '关闭'){
			$.messager.alert('提示','选中的猪群中存在关闭状态猪群，请勿重复操作！');
			return;
		}
		ids.push(rows[i].rowId);
	}
	$.post({
		url:basicUrl + closeUrl,
		data:{
			ids:ids,
		},
		success:function(response){
			response=eval('('+response+')');
			if(response.success){
				$.messager.progress('close');
				$('#table').datagrid('reload');
				$.messager.alert('提示','操作成功！');
			}else{
				$.messager.alert({
                    title: '错误',
                    msg: response.errorMsg
                });
			}
		}
	});
}

