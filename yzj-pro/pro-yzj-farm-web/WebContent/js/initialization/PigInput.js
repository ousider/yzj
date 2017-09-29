var downTemplateParam="xmlName=PigInputEmportExcel.xml&downName=母猪猪只入场.xls&typeName=INPUT_PIG";
var downTemplateParamOfBoar="xmlName=PigInputEmportExcelOfBoar.xml&downName=公猪猪只入场.xls&typeName=INPUT_PIG";
var importParam={xmlName:'PigInputEmportExcel.xml'};
var importParamOfBoar = {xmlName:'PigInputEmportExcelOfBoar.xml'};
var prUrl='/SysInit/';
var deleteUrl=prUrl+'deleteInputPigs.do';

/** *********页面渲染开始*************************************************************************/
$(document).ready(function(){
	/**
	 * 主表加载
	 */
	$('#table').datagrid(
			j_grid({		
				url:'/SysInit/searchInputPig.do',
				queryParams:{status:1},
				columnFields:'rowId,productDate,parity,breedDate,breedBoarName,breedBoar,pregnancyDate,pregnancyResultName,pregnancyResult,changeHouseDate,changeHouseName,changeHouseId,changePigpenName,changePigpenId,deliveryDate,aliveLitterWeight,healthyNum,weakNum,stillbirthNum,mummyNum,weanDate,weanNum,weanWeight',
				columnTitles:'ID,转生产日期,胎次,配种日期,配种公猪,配种公猪ID,妊检日期,妊检结果,妊检结果ID,上产房日期,产房,产房ID,栏位,栏位ID,分娩日期,活仔窝重,健仔数,弱仔数,死胎,木乃伊,断奶日期,断奶数量,断奶窝重',
				hiddenFields:'rowId,changeHouseId,breedBoar,pregnancyResult,changePigpenId',
				frozenColumns:[[
				                {field:'ck',checkbox:true},
								j_gridText({field:'earBrand',title:'耳牌号',width:100, editor:{type:'text'}}),
								j_gridText({field:'pigTypeName',title:'猪类别',width:80,editor:{type:'text'}}),
								j_gridText({field:'materialName',title:'物料主数据',width:100, editor:{type:'text'}}),
								j_gridText({field:'houseName',title:'猪舍',width:100, editor:{type:'text'}}),
								j_gridText({field:'pigpenName',title:'猪栏',width:80, editor:{type:'text'}}),
								j_gridText({field:'birthDate',title:'出生日期',width:100, editor:{type:'text'}})
				               ]],
				fitColumns:false,
				haveCheckBox:false,
			})
	);
	//猪只类型
	xnRadioBox({
		id:'pigType',
		name:'pigType',
		typeCode:'PIG_TYPE',
		defaultValue:1,
		excludeValue:['3']
	});
	
	$('#errorTable').datagrid(
			j_grid_view({			
				columnFields:'notes,pigTypeName,birthDate,productDate,parity,breedBoarName,pregnancyDate,pregnancyResultName,changeHouseDate,changeHouseName,deliveryDate,aliveLitterWeight,healthyNum,weakNum,stillbirthNum,mummyNum,weanDate,weanNum,weanWeight',
				columnTitles:'错误信息,猪类别,出生日期,转生产日期,胎次,配种公猪,妊检日期,妊检结果,上产房日期,产房,分娩日期,活仔窝重,健仔数,弱仔数,死胎,木乃伊,断奶日期,断奶数量,断奶窝重',
				hiddenFields:'pigTypeName,birthDate,productDate,parity,breedBoarName,pregnancyDate,pregnancyResultName,changeHouseDate,changeHouseName,deliveryDate,aliveLitterWeight,healthyNum,weakNum,stillbirthNum,mummyNum,weanDate,weanNum,weanWeight',
				columnWidths:'380,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100',
				frozenColumns:[[
								j_gridText({field:'earBrand',title:'耳牌号',width:100,editor:{type:'text'}}),
								j_gridText({field:'materialName',title:'物料主数据',width:100,editor:{type:'text'}}),
								j_gridText({field:'houseName',title:'猪舍',width:80,editor:{type:'text'}}),
								j_gridText({field:'pigpenName',title:'猪栏',width:80,editor:{type:'text'}})
				               ]],
				fitColumns:false,
				fit:false,
				height:'92%',
				width:'100%',
				haveCheckBox:false,
			},'errorTable')
	);
	$('#successTable').datagrid(
			j_grid_view({			
				columnFields:'pigTypeName,birthDate,productDate,parity,breedBoarName,pregnancyDate,pregnancyResultName,changeHouseDate,changeHouseName,deliveryDate,aliveLitterWeight,healthyNum,weakNum,stillbirthNum,mummyNum,weanDate,weanNum,weanWeight',
				columnTitles:'猪类别,出生日期,转生产日期,胎次,配种公猪,妊检日期,妊检结果,上产房日期,产房,分娩日期,活仔窝重,健仔数,弱仔数,死胎,木乃伊,断奶日期,断奶数量,断奶窝重',
				hiddenFields:'breedBoarName,pregnancyDate,pregnancyResultName,changeHouseDate,changeHouseName,deliveryDate,aliveLitterWeight,healthyNum,weakNum,stillbirthNum,mummyNum,weanDate,weanNum,weanWeight',
				columnWidths:'80,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100',
				frozenColumns:[[
								j_gridText({field:'earBrand',title:'耳牌号',width:100,editor:{type:'text'}}),
								j_gridText({field:'materialName',title:'物料主数据',width:100,editor:{type:'text'}}),
								j_gridText({field:'houseName',title:'猪舍',width:80,editor:{type:'text'}}),
								j_gridText({field:'pigpenName',title:'猪栏',width:80,editor:{type:'text'}})
				               ]],
				fitColumns:false,
				fit:false,
				height:'92%',
				width:'100%',
				haveCheckBox:false,
			},'successTable')
	);
});

/*************页面渲染结束******************************************************************************/
/**
 * 查询执行异常记录
 */
function searchExceptionalRecords(){
	leftSilpFun('errorWin');
	$('#errorTable').datagrid('reload',basicUrl+'/SysInit/searchInputPig.do?status='+4);
	rightSlipFun('successWin',780)
}
/**
 * 查询执行成功记录
 */
function searchSuccessRecords(){
	leftSilpFun('successWin');
	$('#successTable').datagrid('reload',basicUrl+'/SysInit/searchInputPig.do?status='+3);
	rightSlipFun('errorWin',780)
}
/**
 * 执行初始化功能
 */
function execute(){
	$.messager.progress();	
	jAjax.submit('/SysInit/inputPig.do', null, 
		function(data){
			var successMsg='';
			if(data.totalSowNum!=0){
				successMsg='导入母猪：'+data.totalSowNum+'头，失败'+data.errorSowNum+'头。';
			}
			if(data.totalBoarNum!=0){
				successMsg='导入公猪：'+data.totalBoarNum+'头，失败'+data.errorBoarNum+'头。';
			}
			$('#table').datagrid('reload');
			$.messager.progress('close');	
			$.messager.alert({
				title : '成功',
				msg : successMsg
			});
		},function(data){
			$.messager.progress('close');	
			$.messager.alert({
				title : '错误',
				msg : data.errorMsg
			});
		},null,true);
	
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
	var newPigType=checkTemplate(template);
	if(newPigType==1){
		var template = importParamOfBoar;
    }else{
    	 var template=importParam;
    }

    var formData = new FormData($("#importForm")[0]);
    formData.append("xmlName",template.xmlName);
    
    $.messager.progress();	
	$.ajax({
		url : basicUrl + "/excel/exportPig.do?pigType="+newPigType,
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
				rightSlipFun('eventImport', 390, true);
				$('#table').datagrid('reload');
				$("#importForm").form("reset");
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
    