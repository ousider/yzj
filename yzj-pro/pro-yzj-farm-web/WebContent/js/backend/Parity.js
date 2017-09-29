var prUrl='/portal';
var saveUrl=prUrl+'/editParity.do';
var editIndex = undefined;
$(document).ready(function(){
	$('#listTable').datagrid(
		j_detailGrid({	
			width:'100%',
			height:'100%',
			fit:true,
			url:'/portal/searchParityToList.do',
			columns:[[
			          	{field:'ck',checkbox:true},
		              	j_gridText({field:'rowId',title:'ID',hidden:true}),		              	
		              	j_gridText({field:'parity',title:'胎次',editor:{
		              		type:'numberspinner',
		              		options:{
		              			min:0
		              		}
		              	},
		              	style:true,color:'#66CDAA'}),
		              	j_gridText({field:'percent',title:'比例',editor:{
		              		type:'numberspinner',
		              		options:{
		              			min:0,
		              			precision:2
		              		}
		              	},
		              	style:true,color:'#66CDAA'}),
		              	j_gridText({field:'version',title:'版本',style:true}),
				    ]]
		})
	);
});

//保存为新版
function doSaveNew(){
	$('#listTable').datagrid('endEdit',editIndex);
		var rows = $('#listTable').datagrid('getRows');
		var ids = [];
		$.each(rows,function(index,data){
			if(data.rowId != -1){
				ids.push(data.rowId);
			}
		});
		var jsonString = JSON.stringify(rows);
		$('#btnSaveNew').attr('disabled',true).addClass('btn-disabled');
		$.messager.progress();
		$.post({
			url:basicUrl + '/portal/editNewParity.do',
			data:{
				ids:ids,
				gridList:jsonString
			},
			success:function(response){
				response=eval('('+response+')');
				if(response.success){
					$('#listTable').datagrid('reload');
					$.messager.alert('提示','保存成功！');
				}else{
					$.messager.alert({
	                    title: '错误',
	                    msg: response.errorMsg
	                });
				}
				$('#btnSaveNew').attr('disabled',false).removeClass('btn-disabled');
				$.messager.progress('close');
			}
		});

	
}
//保存
function doSave(){
	
	$('#listTable').datagrid('endEdit',editIndex);
	var changes  = $('#listTable').datagrid('getChanges','updated');
	if(changes.length > 0){
//		$.messager.progress();
		var rows = $('#listTable').datagrid('getRows');
		var ids = [];
		$.each(rows,function(index,data){
			if(data.rowId != -1){
				ids.push(data.rowId);
			}
		});
		var jsonString = JSON.stringify(rows);
		$('#btnSave').attr('disabled',true).addClass('btn-disabled');
		$.messager.progress();
		$.post({
			url:basicUrl + '/portal/editParity.do',
			data:{
				ids:ids,
				gridList:jsonString
			},
			success:function(response){
				response=eval('('+response+')');
				if(response.success){
					$('#listTable').datagrid('reload');
					$.messager.alert('提示','保存成功！');
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
	}else{
		$.messager.alert('提示','没有修改任何数据！');
	}
}	

//删除	
function doDelete(){
	var listTable = $('#listTable');
	var record = listTable.datagrid('getSelections');
	var length = record.length;
	if(length < 1){
		$.messager.alert('警告','请选择一条数据！');
	}else{
		$.messager.confirm('提示', '确定要删除这'+record.length+'条记录吗？', function(r){
			if (r){
				
				var ids = [];
				$.each(record,function(index,data){
					if(data.rowId != null && data.rowId != undefined){
						ids.push(data.rowId);
					}
					var row = listTable.datagrid('getRowIndex',data);
					data.deletedFlag = "1";
					listTable.datagrid('deleteRow',row);
				});
				$.post({
					url:basicUrl + '/portal/deleteParity.do',
					data:{
						ids:ids,
					},
					success:function(response){
						response=eval('('+response+')');
						if(response.success){
							$.messager.progress('close');
//							$('#listTable').datagrid('reload');
							$.messager.alert('提示','删除成功！');
						}else{
							$.messager.alert({
			                    title: '错误',
			                    msg: response.errorMsg
			                });
						}
					}
				});
			}
		});
	}
}	

//新增
function doAdd(){

	var listTable = $('#listTable');
	var allRows = listTable.datagrid('getRows');
	var length = allRows.length;
	if(length == 11){
		$.messager.alert('警告','胎次表数据不能超过11行！');
	}else{
			var rows = [];
				var defaultValues = new Object(); 
				if(typeof(detailDefaultValues) == "undefined"){
					defaultValues ={
						status:'1',
						deletedFlag:'0'	
					};
				}else{
					//复制detailDefaultValues
					for(var p in detailDefaultValues) { 
						var name=p;//属性名称 
						var value=detailDefaultValues[p];//属性对应的值 
						defaultValues[name]=detailDefaultValues[p]; 
					} 
				}
				var data = {success:true,total: length };
				if(length != 0){
					rows = allRows;
				}
				rows.push(defaultValues);
			
			data.rows = rows;
			listTable.datagrid('loadData',data);
	}
}
