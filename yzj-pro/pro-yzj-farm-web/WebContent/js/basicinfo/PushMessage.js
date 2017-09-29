var title = '消息推送';
//var model = 'cdCode';
var editIndex = undefined;
var prUrl='/backEnd/';
//消息推送CU操作
var saveUrl=prUrl+'editPushMessage.do'; 
//消息推送删
var deleteUrl=prUrl+'deletePushMessage.do'; 
//消息推送停用启用
var disableOrEnableUrl=prUrl+'editDisableOrEnable.do';
//消息推送主页加载
var searchMainListUrl=prUrl+'searchpushMessageToPage.do'; 
//消息推送选择人员搜索
var searchChooseUserToPageUrl=prUrl+'searchChooseUserToPage.do'; 
//消息推送新增删
var deleteAddPushMessageUrl=prUrl+'deleteAddPushMessage.do'; 
//
var searchDetailListUrl=prUrl+'searchDetailList.do'; 
$(document).ready(function(){
	/**
	 * 主表加载
	 */
	$('#table').datagrid(
		j_grid({
			toolbar:'#tableToolbarList',
			url:searchMainListUrl,
			columnFields:'rowId,messageCode,messageTitle,messageTypeName,content,useFlag,description,picUrl,contentUrl,closedDate',
			columnTitles:'rowId,消息代码,消息标题,消息类型,内容,是否停用,描述,推送图片地址,推送内容地址,关闭日期',
			hiddenFields:'rowId,description,picUrl,contentUrl,closedDate'
		})
	);
	//消息类型
	xnCdCombobox({
		id:'messageType',
		typeCode:'MESSAGE_TYPE',
		required:true,
		defaultValue:'1',
		onChange:messageTypeChange
	});
	$('#listTable').datagrid(
			j_detailGrid({
			dbClickEdit:true,
			toolbar:'#listTableToolbar',
			columns:[[
			          	{field:'ck',checkbox:true},
						j_gridText({field:'rowId',title:'ID',hidden:true}),
						j_gridText({field:'employeeId',title:'employeeId',hidden:true}),
						j_gridText({field:'qyUserId',title:'企业微信人员ID',width:80}),
						j_gridText({field:'employeeName',title:'人员名称',width:80}),
		              	j_gridText({field:'notes',title:'备注',width:80,editor:'textbox'})
				    ]]
			})
	 );
	//选择人员
	$('#chooseUserTable').datagrid(
			j_grid_view({
				width:'auto',
				height:'auto',
				columnFields:'rowId,qyUserId,employeeName',
				columnTitles:'rowId,企业微信人员ID,人员名称',
				hiddenFields:'rowId',
		},'chooseUserTable')
	);
	
})
function messageTypeChange(newValue,oldValue){
	if(newValue == '1'){
		$('#picUrl').textbox({required:false});
		$('#contentUrl').textbox({required:false});
		$('#content').textbox({required:true});
	}else if(newValue == '2'){
		$('#picUrl').textbox({required:true});
		$('#contentUrl').textbox({required:true});
		$('#content').textbox({required:false});
	}
}
function onBtnDisableEnable(){
	var record = $('#table').datagrid('getSelections');
	var length = record.length;
	if(length < 1){
		$.messager.alert('警告','请选择一条数据！');
	}else{
		$.messager.confirm('提示', '确定要启用或者停用这'+record.length+'条记录吗？', function(r){
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
				    url: basicUrl+disableOrEnableUrl ,
				    data: {
				    	'ids':ids
				    } ,
				    success: function(response){
				    	$.messager.progress('close');
				    	if(response.success){
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


function onBtnDetailAdd(){
	leftSilpFun('chooseUserWin',true);
	var hasSelectRows = $('#listTable').datagrid('getData').rows;
	var rowIdsArray = [];
	$.each(hasSelectRows,function(i,data){
		if(data.employeeId != null && data.employeeId != ''){
			rowIdsArray.push(data.employeeId);
		}
	});
	var rowIds = '';
	$.each(rowIdsArray,function(i,data){
		if(i == rowIdsArray.length - 1){
			rowIds += data;
		}else{
			rowIds += data + ',';
		}
	});
	 $('#chooseUserTable').datagrid({
    	url:basicUrl+'/backEnd/searchChooseUserToPage.do',
    	queryParams:{
    		rowIds:rowIds
    	}
    });
}

/**
 * 选择人员搜索
 */
function chooseUserSearch(){
	var employeeName =  $('#employeeName').textbox('getValue');
	var hasSelectRows = $('#listTable').datagrid('getData').rows;
	var rowIdsArray = [];
	$.each(hasSelectRows,function(i,data){
		if(data.rowId != null && data.rowId != ''){
			rowIdsArray.push(data.rowId);
		}
	});
	var rowIds = '';
	$.each(rowIdsArray,function(i,data){
		if(i == rowIdsArray.length - 1){
			rowIds += data;
		}else{
			rowIds += data + ',';
		}
	});
	$('#chooseUserTable').datagrid('load',{
    	employeeName:employeeName,
    	rowIds:rowIds
    });
}

 function chooseUserSure(){
 	var selectRows = $('#chooseUserTable').datagrid('getSelections');
 	if(selectRows.length < 1){
 		$.messager.alert('警告', '请选择一条以上的数据！');
 	}else{
 		$.each(selectRows,function(index,data){
 			var row = new Object(); 
 			if(typeof(detailDefaultValues) == "undefined"){
 				row ={
 					status:'1',
 					deletedFlag:'0'	
 				};
 			}else{
 				for(var p in detailDefaultValues) { 
 					var name=p;//属性名称 
 					var value=detailDefaultValues[p];//属性对应的值 
 					row[name]=detailDefaultValues[p]; 
 				} 
 			}
 			row.employeeId = data.rowId;
 			row.qyUserId = data.qyUserId;
 			row.employeeName = data.employeeName;
			
 			$('#listTable').datagrid('appendRow',row);
 		});
 		var hasSelectRows = $('#listTable').datagrid('getData').rows;
 		var rowIdsArray = [];
 		$.each(hasSelectRows,function(i,data){
 			if(data.employeeId != null && data.employeeId != ''){
 				rowIdsArray.push(data.employeeId);
 			}
 		});
 		var rowIds = '';
 		$.each(rowIdsArray,function(i,data){
 			if(i == rowIdsArray.length - 1){
 				rowIds += data;
 			}else{
 				rowIds += data + ',';
 			}
 		});
 		var employeeName =  $('#employeeName').textbox('getValue');
 		$('#chooseUserTable').datagrid('load',{
         	employeeName:employeeName,
			rowIds:rowIds
         });
 	}
 }
 
 