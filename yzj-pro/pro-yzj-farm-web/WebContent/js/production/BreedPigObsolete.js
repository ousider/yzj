var prUrl='/production/';
var saveUrl=prUrl+'obsolete.do';
var editIndex = undefined;
var eventName = 'BREED_PIG_OBSOLETE';
var oldPigType = '';
$(document).ready(function(){
	$('#listTable').datagrid(
			j_eventGrid({
			dbClickEdit:true,
			columns:[[
			          	{field:'ck',checkbox:true},
						j_gridText({field:'rowId',title:'ID',hidden:true}),
						j_gridText({field:'pigId',title:'猪只ID',hidden:true}),
						j_gridText({field:'houseId',title:'猪舍ID',hidden:true}),
						j_gridText({field:'minValidDate',title:'minValidDate',hidden:true}),
						j_gridText({field:'maxValidDate',title:'maxValidDate',hidden:true}),
						j_gridText({field:'deletedFlag',title:'deletedFlag',hidden:true}),
						//耳牌号
						j_gridText(earBrandTextBox({width:100})),
						j_gridText({field:'pigInfo',title:'耳缺号-品种-状态(胎次)-日龄-猪舍-猪栏',width:350}),
						j_gridText({field:'enterDate',title:'淘汰日期',width:100,editor:{type:'datebox',options:{editable:false}}}),
						j_gridText({field:'leaveReason',title:'淘汰原因',width:100,
		              		formatter:function(value,row){
		              			return row.leaveReasonName;
		              		},
		              		editor:
		              		xnGridCdComboBox({
		              			field:'leaveReason',
		              			typeCode:'OBSOLETE_REASON'
		              		})
		              	}),
		              	//技术员
		              	j_gridText(workerGridComboGrid({width:100})),
		              	j_gridText({field:'notes',title:'备注',width:100,editor:'textbox'})
				    ]],
		    onEndEdit:function(index,row){
		    	 var obsoleteReason = $('#listTable').datagrid('getEditor', {
	                index: index,
	                field: 'leaveReason'
	            });
		    	if(obsoleteReason != null){
		    		row.leaveReasonName = $(obsoleteReason.target).combogrid('getText');
		    	}
		    }
		})
	);
	
	$('#preSaveRecordTable').datagrid(
			j_grid_view({
				fitColumns:false,
				haveCheckBox:false,
				columnFields:'pigId,earBrand,pigInfo,enterDate,leaveReasonName,workerName,notes',
				columnTitles:'猪只ID,耳牌号,耳缺号-品种-状态(胎次)-日龄-猪舍-猪栏,淘汰日期,淘汰原因,技术员,备注',
				hiddenFields:'pigId,notes',
				columnWidths:'100,100,350,90,100,100,100',
				fit:false,
				width:'auto',
				height:'100%',
				pagination:false
			},'preSaveRecordTable')
	);
});
