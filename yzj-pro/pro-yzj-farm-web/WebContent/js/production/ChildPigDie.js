var prUrl='/production/';
var saveUrl=prUrl+'childPigDie.do';
var editIndex = undefined;
var eventName = 'CHILD_PIG_DIE';
//默认值为2
var oldPigType = 2;
$(document).ready(function(){
	$('#listTable').datagrid(
			j_eventGrid({
			dbClickEdit:true,
			columns:[[
			          	{field:'ck',checkbox:true},
		              	j_gridText({field:'rowId',title:'ID',hidden:true}),
		              	j_gridText({field:'pigId',title:'猪只ID',hidden:true}),
		              	j_gridText({field:'minValidDate',title:'minValidDate',hidden:true}),
		              	j_gridText({field:'maxValidDate',title:'maxValidDate',hidden:true}),
		              	j_gridText({field:'deletedFlag',title:'deletedFlag',hidden:true}),
		              	//耳牌号
		              	j_gridText(earBrandTextBox({width:100})),
		              	j_gridText({field:'pigInfo',title:'耳缺号    -  品种   -  胎次   -  状态(天)  -  日龄  - 猪舍  - 猪栏',width:450}),
			          	j_gridText({field:'leaveReason',title:'死亡原因',width:80,
		              		formatter:function(value,row){
		              			return row.leaveReasonName;
		              		},
		              		editor:
		              		xnGridCdComboBox({
		              			field:'leaveReason',
		              			typeCode:'GOOD_DIE_REASON'
		              		})
		              	}),
		              	j_gridText({field:'pigQty',title:'带仔数',width:80}),
		              	j_gridNumber({field:'leaveQty',title:'死亡数量',width:80}),
		              	j_gridNumber({field:'leaveWeight',title:'死亡总重',min:0,
		              		precision:2,
							increment:0.1,
		              		formatter:function(value,row){
		              			value = value == null || value == undefined || value == '' ? 0.00 : value;
		              			row.leaveWeight = value;
		              			return value+'KG';
			              	},
			              	width:80}),
		              	j_gridText({field:'enterDate',title:'死亡日期',width:100,editor:{type:'datebox',options:{editable:false}}}),
		              //技术员
		              	j_gridText(workerGridComboGrid({width:80})),
		              	j_gridText({field:'notes',title:'备注',width:80,editor:'textbox'})
				    ]],
		    onEndEdit:function(index,row){
		    	var leaveReason = $('#listTable').datagrid('getEditor', {
	                index: index,
	                field: 'leaveReason'
	            });
		    	if(leaveReason != null){
		    		row.leaveReasonName = $(leaveReason.target).combogrid('getText');
		    	}
		    }
		})
	);
	
	$('#preSaveRecordTable').datagrid(
			j_grid_view({
				fitColumns:false,
				haveCheckBox:false,
				columnFields:'pigId,earBrand,pigInfo,leaveReasonName,pigQty,leaveQty,leaveWeight,enterDate,workerName,notes',
				columnTitles:'猪只ID,耳牌号,耳缺号    -  品种   -  胎次   -  状态(天)  -  日龄  - 猪舍  - 猪栏,死亡原因,带仔数,死亡数量,死亡总重(KG),死亡日期,技术员,备注',
				hiddenFields:'pigId,notes,workerName',
				columnWidths:'100,100,350,90,80,80,110,90,100,100',
				fit:false,
				width:'auto',
				height:'100%',
				pagination:false
			},'preSaveRecordTable')
	);
});

