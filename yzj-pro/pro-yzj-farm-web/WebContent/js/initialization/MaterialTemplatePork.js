var downTemplateParam="xmlName=MaterialTemplatePork.xml&typeName=MATERIAL_TEMPLATE_PORK";
var importParam={xmlName:'MaterialTemplatePork.xml'};
var prUrl='/SysInit/';
var editIndex = null;
var deleteUrl=prUrl+'deleteInputPigs.do';
//var saveUrl='/material/editMaterial.do';


/** *********页面渲染开始*************************************************************************/
$(document).ready(function(){
	/**
	 * 主表加载
	 */
	$('#table').datagrid(
			j_grid({		
				url:'',
				queryParams:{status:1},
				columnFields:'rowId,materialType,groupName,groupId,isWarehouseName,isWarehouse,isPurchaseName,isPurchase,isSellName,isSell,unitName,unit,manufacturer,supplierName,supplierId,specNum,spec,price,rmvaluerate,outputMinQty,breedName,breedId,strainName,strain,bodyConditionName,bodyCondition,colorName,color,rightTeatNum,leftTeatNum,birthWeight,enterDayAge,enterWeight,mewDayAge,mewWeight,childCareDayAge,childCareWeight,fattenDayAge,freePercent,sellDayAge,sellWeight,frc,boarName,boarId,sowName,sowId,stockBoarName,stockBoarId,broodSowName,broodSowId,isSelectName,isSelect,isDifSexName,isDifSex,notes',
				columnTitles:'ID,物料类型ID,物料组名称,物料组名称ID,仓库物料,仓库物料ID,采购物料,采购物料ID,销售物料,销售物料ID,计量单位,计量单位ID,生产厂家,供应商,供应商ID,规格-数量,规格-单位,单价,赠送比率,领用最小规格(头),品种,品种ID,品系,品系ID,体况,体况ID,毛色,毛色ID,右乳头数,左乳头数,出生体重(kg),入场日龄,入场体重(kg),断奶日龄,断奶体重(kg),转保育日龄,转保育体重(kg),转育肥日龄,转育肥体重(kg),出售日龄,出售体重(kg),料肉比,父本,父本ID,母本,母本ID,种公猪主数据,种公猪主数据ID,种母猪主数据,种母猪主数据ID,是否选种,是否选种ID,分娩区分性别,分娩区分性别ID,备注',
				hiddenFields:'rowId',
				frozenColumns:[[
				                {field:'ck',checkbox:true},
				                j_gridText({field:'materialTypeName',title:'物料类型',width:100,value:'肉猪主数据' , editor:{type:'text'}}),
								j_gridText({field:'materialName',title:'物料名称',width:100, editor:{type:'text'}})
				               ]],
				fitColumns:false,
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
		url:basicUrl + '/SysInit/inputMaterialPork.do?materialType=PorkPig',
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
		  
	var downLoadUrl = '/ExpImp/downExcelTemplate.do?'+downTemplateParam+'&downFlag=false';
	jAjax.submit(downLoadUrl,null,
			function(response){
				window.location.href= basicUrl +'/ExpImp/downExcelTemplate.do?'+downTemplateParam+'&downFlag=true';
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
		url : basicUrl + "/excel/exportMaterialTemplatePork.do",
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