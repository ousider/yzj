var title = '账号绑定';
var prUrl='/basicInfo/';
var saveUrl=prUrl+'editFarmBind.do'; //角色CU操作
var searchMainListUrl = prUrl+'searchFarmBind.do';
var deleteUrl=prUrl+'deleteFarmBind.do';
var searchCompanyTreeUrl='/OrganizationalStructure/searchCompanyTreeExceptDept.do'; 
$(document).ready(function(){
	
	//角色table
	$('#table').datagrid(
			j_detailGrid({
				height:'94%',
				url:searchMainListUrl,
				columnFields:'rowId,bindUserId,deletedFlag,companyCode,farmName,userName,password,isAsync',
				columnTitles:'ID,绑定用户Id,deletedFlag,公司编码,猪场名称,用户名,密码,是否同步',
				hiddenFields:'rowId,deletedFlag,bindUserId,password,isAsync',
			})
	);
	
})
function onBtnBindFarm(){
	leftSilpFun('bindFarmWin',true,9001);
	$('#treeTableDiv').html('<div id="treeTable"></div>');
	$('#treeTable').treegrid(
			j_treeGrid({
				url :searchCompanyTreeUrl,
				toolbar:[],
			    columns:[[
			        {field:'text',title:'公司名称',width:600}
			    ]],
				checkbox:true,
				onCheckNode:onCheckNodeFun
			},function(){
				var farmIdArry = [];
				var tablerows = $('#table').datagrid('getRows');
				$.each(tablerows,function(i,item){
					if($.inArray(item.farmId, farmIdArry) == -1 && item.farmId != $('#farmId').val()){
						farmIdArry.push(item.farmId);
						var checkedNode = $('#treeTable').treegrid('find',item.farmId);
						if( checkedNode!= null && checkedNode.checkState == 'unchecked'){
							$('#treeTable').treegrid('checkNode',item.farmId);
						}
					}
				});
			})
	);
}
function onCheckNodeFun(row,checked){
	if(row.farmId == $('#farmId').val() && checked){
		$.messager.alert('提示','不能选择本场！');
		$('#treeTable').treegrid('uncheckNode',row.id);
		return;
	}
	var tablerows = $('#table').datagrid('getRows');
	$.each(tablerows,function(i,item){
		if(row.farmId == item.farmId && !checked){
			$.messager.alert('提示','已绑定帐号不能取消，若要去掉请点击删除按钮！');
			$('#treeTable').treegrid('checkNode',item.farmId);
			return;
		}
	});
}
function saveBindFarm(){
	$.messager.progress();
	var checkedNodes = $('#treeTable').treegrid('getCheckedNodes');
	var farmIds = [];
	var companyIds = [];
	var companyCodes = [];
	var tablerows = $('#table').datagrid('getRows');
	$.each(checkedNodes,function(i,item){
		var exitFlag = false;
		$.each(tablerows,function(j,tablerow){
			if(item.farmId == tablerow.farmId){
				exitFlag = true;
				return false;
			}
		})
		if(!exitFlag){
			farmIds.push(item.farmId);
			companyIds.push(item.parentId);
			companyCodes.push(item.companyCode);
		}
	});
	jAjax.submit(
			prUrl+'saveSynchronizeFarm.do',
			{farmIds:farmIds,companyIds:companyIds,companyCodes:companyCodes},
			function(data){
				if(data.success){
					$.messager.progress('close');
					$.messager.alert('提示','保存成功！');							 
					rightSlipFun('bindFarmWin',9001,true);
					$('#table').datagrid('reload');
				}else{
					$.messager.progress('close');
					$.messager.alert({
							title: '错误',
		                    msg: data.errorMsg
		            });							 
				}
			},
			null,	   
			'post' ,
			true,
			null,
			true
		);
}

/**
 * 删除
 * pms{
 * 	model:模块代码
 * }
 */
function onBtnDelete(){
	var record = $('#table').datagrid('getSelections');
	var length = record.length;
	if(length < 1){
		$.messager.alert('警告','请选择一条数据！');
	}else{
		$.messager.confirm('提示', '确定要删除这'+record.length+'条记录吗？', function(r){
			if (r){
				$.messager.progress();	
				var ids = [];
				var isAsyncs = [];
				$.each(record,function(index,data){
					ids.push(data.bindUserId);
					isAsyncs.push(data.isAsync);
				});
				$.ajax({
				    type: 'POST',
				    url: basicUrl+deleteUrl ,
				    data: {
				    	'ids':ids,
				    	'isAsyncs':isAsyncs
				    } ,
				    success: function(response){
				    	$.messager.progress('close');
				    	if(response.success){
				    		$.messager.alert('警告','删除成功！');
				    		$('#table').datagrid('reload');
				    		$('#treeTable').treegrid('reload');
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