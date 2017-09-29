var editIndex = undefined;
var prUrl = '/production/';
//var searchMainListUrl = prUrl + 'searchOutputStoreToPage.do';
//var searchPlanTypeListUrl = prUrl + 'searchOutputBillDetailToList.do';
var saveUrl=prUrl + 'editNewPlan.do';
var deleteUrl=prUrl + '';
/** *********页面渲染开始*************************************************************************/
$(document).ready(function(){
	/**
	 * 主表加载
	 */
	$('#table').datagrid(
		j_grid({
			url:'/production/searchPlanByALL.do?',
			toolbar:"#tableToolbarList",
			columnFields:'billid,planType,planTypeName,planYear,planMonth,planDate,notes',
			columnTitles:'ID,计划类型编号,计划类型,计划年,计划月,计划时间,备注',
			hiddenFields:'billid,planType,planYear,planMonth',
			singleSelect:true
		},'table')
	);
});
//新增
function newValue(){
	addInput('新增销售计划');
	$('#receptionListTableToolbar').css('display','block');
	planListTable();
	$('#listTable').datagrid('loadData',{success:true,total: 0, rows: [] });

}
//编辑
function editorValue(){
	var record = $('#table').datagrid('getSelections');
	var length = record.length;
	if(length < 1){
		$.messager.alert('警告','请选择一条数据！');
	}else if(length > 1){
		$.messager.alert('警告','只能选择一条数据！');
	}else{
		$('#editWin').window({
			title:'编辑销售计划'
		});
		$('#editWin').window('open');
		planListTable();		
		$('#editForm').form('load',record[0]);
		$('#btnSave').css('display','inline-block');
		$('#receptionListTableToolbar').css('display','none');		
		var listTable = document.getElementById('listTable');
		
		if(listTable!=null){
			var billid;
			if(record[0].billid!=null){
				billid=record[0].billid;
			}else{
				billid='单号出错';
			}
//			$('#listTable').datagrid('load',basicUrl+searchDetailListUrl+'?mainId='+mainId);
			$('#listTable').datagrid('load',basicUrl+'/production/searchHistoryByPlanType.do?billid='+billid);				
			
		}
	}
	

}
//查看
function lookView(){
	$('#btnSave').css('display','none');				
	var record = $('#table').datagrid('getSelections');
	var length = record.length;
	if(length < 1){
		$.messager.alert('警告','请选择一条数据！');
	}else if(length > 1){
		$.messager.alert('警告','只能选择一条数据！');
	}else{

		
		$('#editWin').window({
			title:'查看销售计划'
		});
		$('#editWin').window('open');
		planListTable();		
		$('#editForm').form('load',record[0]);
//		$('#btnSave').css('display','inline-block');
		$('#receptionListTableToolbar').css('display','none');		
		var listTable = document.getElementById('listTable');
		
		if(listTable!=null){
			var billid;
			if(record[0].billid!=null){
				billid=record[0].billid;
			}else{
				billid='单号出错';
			}
//			$('#planType').textbox('disable');
//			$('#planYear').textbox('disable');			
//			$('#planMonth').textbox('disable');
//			$('#planWeek').textbox('disable');
//			$('#listTable').datagrid('load',basicUrl+searchDetailListUrl+'?mainId='+mainId);
			$('#listTable').datagrid('load',basicUrl+'/production/searchHistoryByPlanType.do?billid='+billid);				
			
		}
	}
	

}
//新增打开win
function addInput(title){
	$('#editWin').window({
		title:title
	});
	$('#editWin').window('open');
	$('#editForm').form('reset');
	$('#btnSave').css('display','inline-block');
}
function planListTable(){
	$('#listTable').datagrid(
			j_detailGrid({	
//				width:'100%',
//				height:'100%',
				fit:false,
				columns:[[
			              	j_gridText({field:'rowId',title:'ID',hidden:true}),
			              	j_gridText({field:'farmId',title:'公司id',hidden:true}),
			              	j_gridText({field:'farmName',title:'猪场',hidden:true}),	
			              	j_gridText({field:'sortName',title:'猪场',width:150,style:true}),				              	
			              	j_gridText({field:'farmChildPig',title:'场内苗猪',width:150,style:true,editor:'textbox'}),
			              	j_gridText({field:'farmFatPig',title:'场内肉猪',width:150,style:true,editor:'textbox'}),
			              	j_gridText({field:'preChildPig',title:'预测苗猪',width:150,style:true}),
			              	j_gridText({field:'preFatPig',title:'预测肉猪',width:150,style:true}),
			              	j_gridText({field:'handChildPig',title:'当前保育猪',width:150,style:true}),
			              	j_gridText({field:'handFatPig',title:'当前育肥猪',width:150,style:true}),
					    ]]
			})
		);
	//创建类型
	xnCdCombobox({
		id:'planType',
		editable:false,
		typeCode:'plantype',
		onChange:planTypeChange
	});
	// 月份
	xnCdCombobox({
		id:'planMonth',
		typeCode:'MONTH',
		required:true,
		editable:false,

	});
	$('#planWeek').textbox('disable');
	$('#planMonth').textbox('enable');	
}


function planTypeChange(newValue,oldValue){
	if(newValue == '1')	{
		$('#planMonth').textbox('disable');
		$('#planWeek').textbox('disable');
	}else if(newValue == '2'){
		$('#planMonth').textbox('enable');
		$('#planWeek').textbox('disable');
	}else if(newValue == '3')	{
		$('#planMonth').textbox('enable');
		$('#planWeek').textbox('enable');
	}
}
function selectMarketPlan(){
	
	var plantype = $('#planType').textbox('getValue');
	var planyear = $('#planYear').textbox('getValue');
	var planmonth = $('#planMonth').textbox('getValue');
	var planweek = $('#planWeek').textbox('getValue');
	if(plantype == 1){
		if(planyear == null || planyear == ''){
			$.messager.alert('提示','请选择计划年');
			return;
		}

		$('#listTable').datagrid('load',basicUrl+'/production/searchFarmHandByPlanType.do?year='+planyear+'&type='+plantype);				
	}else if(plantype == 2){
		if(planyear == null || planyear == ''){
			$.messager.alert('提示','请选择计划年');
			return;
		}
		if(planmonth == null || planmonth == ''){
			$.messager.alert('提示','请选择计划月');
			return;
		}		
		$('#listTable').datagrid('load',basicUrl+'/production/searchFarmHandByPlanType.do?year='+planyear+'&month='+planmonth+'&type='+plantype);					
	}else if(plantype == 3){
		if(planyear == null || planyear == ''){
			$.messager.alert('提示','请选择计划年');
			return;
		}
		if(planmonth == null || planmonth == ''){
			$.messager.alert('提示','请选择计划月');
			return;
		}
		if(planweek == null || planweek == ''){
			$.messager.alert('提示','请选择计划周');
			return;
		}			
		$('#listTable').datagrid('load',basicUrl+'/production/searchFarmHandByPlanType.do?year='+planyear+'&month='+planmonth+'&week='+planweek+'&type='+plantype);						
	}
	
}
function onBtnSave(){
	var queryParams;
	var listTable = document.getElementById("listTable");
	
	var isValid = $('#editForm').form('validate');
	if (!isValid){
		$.messager.alert('提示','必填项没有填写！');
		$('#btnSave').attr('disabled',false).removeClass('btn-disabled');
		$.messager.progress('close');	
		return;
	}
	
	//判断是否有明细表
	if(listTable!= null){
		queryParams = collectDetailData();
		if(queryParams == null){
			$.messager.alert('提示','前台提示--未添加明细不能提交！');
			return;
		}
		
		if(queryParams.gridList == '[]'){
			$.messager.alert('提示','填写数量都为零！');
			return;
		}
	}else{
		queryParams = {
			status:1,
			deletedFlag:0,
		};
	}

	if(queryParams.notSubmitIndex!=''){
		$.messager.confirm('提示', '第【'+queryParams.notSubmitIndex+'】行的数量为零，不会提交，确定吗？', function(r){
			delete queryParams.notSubmitIndex;
			if(r){
				onbtnSaveSubmit(queryParams);
			}
		});
	}else{
		delete queryParams.notSubmitIndex;
		onbtnSaveSubmit(queryParams);
	}
}

function onbtnSaveSubmit(queryParams){
	$('#btnSave').attr('disabled',true).addClass('btn-disabled');
	$.messager.progress();
	$('#editForm').form('submit',{
		url:basicUrl+saveUrl,
		queryParams:queryParams,
		success: function(response){
			response=eval('('+response+')');
			if(response.success){
				$('#editWin').window('close');
				$.messager.alert('提示','保存成功！');
				$('#editForm').form('reset');
				var table = document.getElementById("table");
				if(table != null){
					$('#table').datagrid('reload');
				}
			}else{
				 $.messager.alert({
                     title: '错误',
                     msg: response.errorMsg
                 });
			}
			$('#btnSave').attr('disabled',false).removeClass('btn-disabled');
			$.messager.progress('close');
		}
	});
}
/**
 * 获取明细表提交数据数据（有一个明细表，如有多个明细表需重写）
 */
function collectDetailData(){
	var listTable = $('#listTable');
	var rows =  listTable.datagrid('getRows');
	for(var i = 0 ; i < rows.length ; i ++){
		listTable.datagrid('endEdit',i);
	}
	var queryParams;
	//明细表获取数据
	
	if(rows.length < 1){
		return null;
	}
	//获取新增行
	var insertRows = listTable.datagrid('getRows');
	
	var validData = [];
	var notSubmitIndex = '';
	$.each(insertRows,function(index,data){
		if(data.hasOwnProperty('farmChildPig') && data.outputQty != 0){
			validData.push(data);
		}else{
			if(notSubmitIndex.length==0){
				notSubmitIndex = index + 1;
			}else{
				notSubmitIndex = notSubmitIndex + ',' + (index + 1);
			}
		}
	})
	
	//给提交数据加上行号
	$.each(validData,function(index,data){
		validData[index].lineNumber = index+1;
	})
	var jsonString = JSON.stringify(validData);
	queryParams = {
			deletedFlag:0,
			gridList:jsonString,
			notSubmitIndex:notSubmitIndex
		};	
	
	return queryParams;
}
