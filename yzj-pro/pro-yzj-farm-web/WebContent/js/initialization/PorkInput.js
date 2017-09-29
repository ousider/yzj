var downTemplateParam="xmlName=PigInputPorkExcel.xml&typeName=INPUT_PORK";
var importParam={xmlName:'PigInputPorkExcel.xml'};
var prUrl='/SysInit/';
var deleteUrl=prUrl+'deleteInputPigs.do';
var editIndex = null;

/** *********页面渲染开始*************************************************************************/
$(document).ready(function(){
	/**
	 * 主表加载
	 */
	$('#table').datagrid(
			j_grid({		
				url:'/SysInit/searchInputPig.do',
				queryParams:{status:1},
				columnFields:'rowId',
				columnTitles:'ID',
				hiddenFields:'rowId',
				columns:[[
				                {field:'ck',checkbox:true},
								j_gridText({field:'materialName',title:'物料主数据',width:100, editor:{type:'text'}}),
								j_gridText({field:'houseName',title:'猪舍',width:100, editor:{type:'text'}}),
								j_gridText({field:'pigpenName',title:'猪栏',width:100,editor:{type:'text'},hidden:true}),
								j_gridText({field:'swineryName',title:'批次',width:100,editor:{type:'text'}}),
								j_gridText({field:'pigClassName',title:'猪只状态',width:100,editor:{type:'text'}}),
								j_gridText({field:'countDate',title:'统计日期',width:100, editor:{type:'text'}}),
								j_gridText({field:'dayAge',title:'日龄',width:100,editor:{type:'text'}}),
								j_gridText({field:'totalNum',title:'数量',width:100,editor:{type:'text'}}),
								j_gridText({field:'avWeight',title:'均重',width:100,editor:{type:'text'}}),
								j_gridText({field:'avPrice',title:'均价',width:100,editor:{type:'text'}}),
								j_gridText({field:'supplier',title:'供应商',width:100,editor:{type:'text'}})
				               ]],
				fitColumns:true,
				haveCheckBox:false,
			})
	);

});

/*************页面渲染结束******************************************************************************/

/**
 * 执行初始化功能
 */
function execute(){
	$.messager.progress();	
	var rows = $('#table').datagrid('getRows');
	var jsonString = JSON.stringify(rows);
	$.post({
		url:basicUrl + '/SysInit/inputPork.do',
		data:{
			gridList:jsonString
		},
		success:function(response){
			response=eval('('+response+')');
			if(response.success){
				$.messager.progress('close');
				$('#table').datagrid('loadData',{success:true,total: 0, rows: [] });
				$.messager.alert('提示','执行成功！');
			}else{
				$.messager.progress('close');	
				$.messager.alert({
                    title: '错误',
                    msg: response.errorMsg
                });
			}
		}
	});
}

/**
 * 查询选择的猪只类型
 * @returns 猪只类型
 */
function checkTemplate(){
    var pigTypeElements = document.getElementsByName('pigType');
    var pigType = 0;
    for(var i = 0 ; i < pigTypeElements.length ; i ++){
		if(pigTypeElements[i].checked){
			pigType = pigTypeElements[i].value;
		}
    }
    return pigType;
}
/**
 * 下载事件导入模板
 */
function downLoadEventTemplate(){
	
	var newPigType=checkTemplate(template);
	if(newPigType==1){
		var template = downTemplateParamOfBoar;
    }else{
    	 var template=downTemplateParam;
    }
	  
	var downLoadUrl = '/ExpImp/downExcelTemplate.do?'+template+'&downFlag=false'+'&pigType='+newPigType;
	jAjax.submit(downLoadUrl,null,
			function(response){
				window.location.href= basicUrl +'/ExpImp/downExcelTemplate.do?'+template+'&downFlag=true'+'&pigType='+newPigType;
			},null,null,true,null,false);
}

/**
 * 导入
 */
function onBtnImport(){
		
	var template = importParam;

    var formData = new FormData($("#importForm")[0]);
    formData.append("xmlName",template.xmlName);
    
    $.messager.progress();	
	$.ajax({
		url : basicUrl + "/excel/exportPork.do",
		type : "POST",
		data : formData,
		dataType:'text',
		async : true,
		cache : false,
		contentType : false,
		processData : false,
		success : function(response) {
			$.messager.progress('close');
			response = eval('(' + response + ')');
			if (response.success) {
		        //var data = response.rows;
		        $('#table').datagrid('loadData',response);
		        rightSlipFun('eventImport',390,true);
			} else {
				jAjax.errorFunc(response.errorMsg);
			}
		},
		error : function(response) {
			$.messager.progress('close');
			jAjax.errorFunc("网络异常。。。请联系维护人员");
		}
	});
}

/**
 * 重置
 */
function onBtnReImport(){
	$("#importForm").form("reset");
}

/**
 * 删除明细
 */
function detailDelete(){
	var listTable = $('#table');
	var record = listTable.datagrid('getSelections');
	var length = record.length;
	if(length < 1){
		$.messager.alert('警告','请选择一条数据！');
	}else{
		$.messager.confirm('提示', '确定要删除这'+record.length+'条记录吗？', function(r){
			if (r){
				if(endEditing()){
					$.each(record,function(index,data){
						var row = listTable.datagrid('getRowIndex',data);
						data.deletedFlag = "1";
						listTable.datagrid('deleteRow',row);
					});
				}
			}
		});
	}
}