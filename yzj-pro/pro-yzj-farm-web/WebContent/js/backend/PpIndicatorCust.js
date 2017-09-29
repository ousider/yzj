var editIndex = undefined;
$(document).ready(function(){
	$('#listTable').datagrid(
		j_detailGrid({	
			width:'100%',
			height:'100%',
			fit:true,
			url:'/backEnd/searchIndicatorCustToList.do',
			columns:[[
		              	j_gridText({field:'rowId',title:'ID',hidden:true}),
		              	j_gridText({field:'businessCode',title:'businessCode',hidden:true}),
		              	j_gridText({field:'indName',title:'指标名称',style:true}),
		              	j_gridText({field:'businessValue',title:'行标',style:true}),
		              	j_gridText({field:'minValue',title:'最小值',editor:{
		              		type:'numberspinner',
		              		options:{
		              			precision:2,
		              			increment:0.1,
		              			min:0
		              		}
		              	},style:true,color:'#66CDAA'}),
		              	j_gridText({field:'maxValue',title:'最大值',editor:{
		              		type:'numberspinner',
		              		options:{
		              			precision:2,
		              			increment:0.1,
		              			min:0
		              		}
		              	},style:true,color:'#66CDAA'}),
		              	j_gridText({field:'section',title:'正负区间',style:true,hidden:true}),
		              	j_gridText({field:'unit',title:'单位',style:true,hidden:true}),
		              	j_gridText({field:'formula',title:'计算公式',style:true,hidden:true}),
		            	j_gridText({field:'standardValue',title:'目标',editor:{
		              		type:'numberspinner',
		              		options:{
		              			precision:2,
		              			increment:0.1,
		              			min:0
		              		}
		              	},style:true,color:'#66CDAA'}),
		              	j_gridText({field:'weekStandardValue',title:'周目标',editor:{
		              		type:'numberspinner',
		              		options:{
		              			precision:2,
		              			increment:0.1,
		              			min:0
		              		}
		              	},style:true,color:'#66CDAA',width:60}),
		              	j_gridText({field:'monthStandardValue',title:'月目标',editor:{
		              		type:'numberspinner',
		              		options:{
		              			precision:2,
		              			increment:0.1,
		              			min:0
		              		}
		              	},style:true,color:'#66CDAA',width:60}),
		              	j_gridText({field:'quarterStandardValue',title:'季度目标',editor:{
		              		type:'numberspinner',
		              		options:{
		              			precision:2,
		              			increment:0.1,
		              			min:0
		              		}
		              	},style:true,color:'#66CDAA',width:60}),
		              	j_gridText({field:'yearStandardValue',title:'年目标',editor:{
		              		type:'numberspinner',
		              		options:{
		              			precision:2,
		              			increment:0.1,
		              			min:0
		              		}
		              	},style:true,color:'#66CDAA',width:60}),
		              	j_gridText({field:'description',title:'描述',style:true})
				    ]]
		})
	);
});
/**
 * 保存
 */
function doSave(){
	$('#listTable').datagrid('endEdit',editIndex);
	var changes  = $('#listTable').datagrid('getChanges','updated');
	if(changes.length > 0){
		$.messager.progress();
		var rows = $('#listTable').datagrid('getRows');
		var ids = [];
		$.each(rows,function(index,data){
			if(data.rowId != -1){
				ids.push(data.rowId);
				data.rowId = null;
			}
		});
		var jsonString = JSON.stringify(rows);
		$('#btnSave').attr('disabled',true).addClass('btn-disabled');
		$.messager.progress();
		$.post({
			url:basicUrl + '/backEnd/eidtIndicatorCust.do',
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
/**
 *恢复
 */
function doRecover(){
	$.messager.confirm('提示', '恢复后数据将与系统标准值一致，确定恢复吗？', function(r){
		if(r){
			var rows = $('#listTable').datagrid('getRows');
			var ids = [];
			$.each(rows,function(index,data){
				if(data.rowId != -1){
					ids.push(data.rowId);
					data.rowId = null;
				}
			});
			$.post({
				url:basicUrl + '/backEnd/recoverIndicatorCust.do',
				data:{
					ids:ids
				},
				success:function(response){
					response=eval('('+response+')');
					if(response.success){
						$.messager.progress('close');
						$('#listTable').datagrid('reload');
						$.messager.alert('提示','恢复成功！');
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