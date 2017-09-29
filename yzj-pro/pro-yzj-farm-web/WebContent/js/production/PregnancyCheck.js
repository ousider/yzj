var prUrl='/production/';
var saveUrl=prUrl+'pregnancyCheck.do';
var editIndex = undefined;
var eventName = 'PREGNANCY_CHECK';
//默认值为1
var oldPigType = 2;
var pregnancyTypeData = [];
var pregnancyTypeDefaultValue = null;
var pregnancyTypeDefaultName = '';
$.ajax({  
    url : basicUrl+'/param/searchByTypeCode.do?typeCode=PREGNANCY_TYPE',
    async : false, 
    type : "POST",  
    dataType : "json",  
    success : function(response) { 
    	pregnancyTypeData = response;
    	$.each(pregnancyTypeData,function(index,row){
    		if(row.isDefault == 'Y'){
    			pregnancyTypeDefaultValue = row.codeValue;
    			pregnancyTypeDefaultName = row.codeName;
    		}
    	});
    }  
});
var pregnancyResultData = [];
var pregnancyResultDefaultValue = null;
var pregnancyResultDefaultName = '';
$.ajax({  
    url : basicUrl+'/param/searchByTypeCode.do?typeCode=PREGNANCY_RESULT',
    async : false, 
    type : "POST",  
    dataType : "json",  
    success : function(response) { 
    	pregnancyResultData = response;
    	$.each(pregnancyResultData,function(index,row){
    		if(row.isDefault == 'Y'){
    			pregnancyResultDefaultValue = row.codeValue;
    			pregnancyResultDefaultName = row.codeName;
    		}
    	});
    }  
});

var detailDefaultValues = {
		status:'1',
		deletedFlag:'0',
		//设置默认值
		pregnancyType:pregnancyTypeDefaultValue,
		pregnancyTypeName:pregnancyTypeDefaultName,
		pregnancyResult:pregnancyResultDefaultValue,
		pregnancyResultName:pregnancyResultDefaultName
};
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
						j_gridText({field:'pigInfo',title:'耳缺号    -  品种   -  胎次   -  状态(天)  -  日龄  - 猪舍  - 猪栏',width:350}),
		              	j_gridText({field:'enterDate',title:'妊检日期',width:150,editor:{type:'datebox',options:{editable:false}}}),
		              	j_gridText({field:'pregnancyType',title:'检查方式',width:80,
		              		formatter:function(value,row){
		              			return row.pregnancyTypeName;
		              		},
		              		editor:
		              		xnGridCdComboBox({
		              			field:'pregnancyType',
		              			data:pregnancyTypeData
		              		})
		              	}),
		              	j_gridText({field:'pregnancyResult',title:'检查结果',width:80,
		              		formatter:function(value,row){
		              			return row.pregnancyResultName;
		              		},
		              		editor:
		              		xnGridCdComboBox({
		              			field:'pregnancyResult',
		              			data:pregnancyResultData
		              		})
		              	}),
		              	j_gridNumber({field:'pregnancyNum',title:'妊检次数',width:80,min:1,		              		
		              		formatter:function(value,row){
	              			value = value == null || value == undefined || value == '' ? 1 : value;
		              		return value;
		              	}}),
		              	//技术员
		              	j_gridText(workerGridComboGrid({width:80})),
		              	j_gridText({field:'notes',title:'备注',width:80,editor:'textbox'})
				    ]],
		    onEndEdit:function(index,row){
		    	 var pregnancyType = $('#listTable').datagrid('getEditor', {
	                index: index,
	                field: 'pregnancyType'
	            });
		    	if(pregnancyType != null){
		    		row.pregnancyTypeName = $(pregnancyType.target).combogrid('getText');
		    	}
	            var pregnancyResult = $('#listTable').datagrid('getEditor', {
	                index: index,
	                field: 'pregnancyResult'
	            });
	            if(pregnancyResult != null ){
	            	row.pregnancyResultName = $(pregnancyResult.target).combogrid('getText');
	            }
	        }
		})
	);
	
	$('#preSaveRecordTable').datagrid(
			j_grid_view({
				fitColumns:false,
				haveCheckBox:false,
				columnFields:'pigId,earBrand,pigInfo,enterDate,pregnancyTypeName,pregnancyResultName,pregnancyNum,workerName,notes',
				columnTitles:'猪只ID,耳牌号,耳缺号    -  品种   -  胎次   -  状态(天)  -  日龄  - 猪舍  - 猪栏,妊检日期,检查方式,检查结果,妊检次数,技术员,备注',
				hiddenFields:'pigId,notes,workerName',
				columnWidths:'100,100,350,90,100,100,100,100,100',
				fit:false,
				width:'auto',
				height:'100%',
				pagination:false
			},'preSaveRecordTable')
	);
});