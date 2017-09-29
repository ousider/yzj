var editIndex = undefined;
var prUrl='/material/';
var saveUrl='';
var deleteUrl=prUrl+'deleteMaterials.do';

var supplierPrUrl='/supplyChian/';
var searchMainListUrl=supplierPrUrl+'searchMaterialToPage.do';
var editMaterialToFarmsUrl=supplierPrUrl+'editMaterialToFarms.do'
//purchaseFlag判断可以采购的类型[1]-全部[2]-饲料[3]-兽药
var purchaseFlag = '1';
var canPurchaseSupplierId = '';
/** *********页面渲染开始*************************************************************************/
$(document).ready(function(){
	getPurchaserInfo();
	
	/**
	 * 主表加载
	 */
	
	$('#listTable').datagrid(
			j_detailGrid({
				width:'100%',
				height:'100%',
				toolbar:"#tableToolbar",
				url:searchMainListUrl+'?purchaseFlag='+purchaseFlag+'&canPurchaseSupplierId='+canPurchaseSupplierId,
				dbClickEdit:true,
//				fitColumns:false,
				pagination:true,
				checkOnSelect:true,
				columns:[[
	              	{field:'ck',checkbox:true},
	              	j_gridText({field:'rowId',title:'ID',hidden:true,sortable:false}),
	              	j_gridText({field:'materialName',title:'物料名称',width:100,sortable:false}),
//	              	j_gridText({field:'groupId',title:'物料组',width:100,hidden:true,sortable:false}),
	            	j_gridText({field:'materialFirstClassName',title:'大类',width:60,sortable:false}),
	            	j_gridText({field:'materialSecondClassName',title:'中类',width:60,sortable:false}),
	              	j_gridText({field:'unit',title:'单位',width:60,sortable:false}),
	              	j_gridText({field:'specAll',title:'规格',width:80,sortable:false}),
	              	j_gridNumber({field:'price',title:'基本价格',width:70,min:0,precision:3,sortable:false}),
	              	j_gridText({field:'freePercent',title:'赠送比率',width:70,editor:{type:'textbox',options:{validType:'numAddPercent'}},sortable:false}),
	              	j_gridText({field:'isWarehouse',title:'仓库物料',width:60,hidden:true,sortable:false,
	              		formatter:function(value,row){
	              			return row.isWarehouseName;
	              		},
	              		editor:
	              		xnGridCdComboBox({
	              			field:'isWarehouse',
	              			typeCode:'YES_OR_NO',
	              			editable:false
	              		})
	              	}),
	              	j_gridText({field:'isPurchase',title:'采购物料',width:60,sortable:false,
	              		formatter:function(value,row){
	              			return row.isPurchaseName;
	              		},
	              		editor:
	              		xnGridCdComboBox({
	              			field:'isPurchase',
	              			typeCode:'YES_OR_NO',
	              			editable:false
	              		})
	              	}),
	              	j_gridText({field:'isSell',title:'销售物料',width:60,hidden:true,sortable:false,
	              		formatter:function(value,row){
	              			return row.isSellName;
	              		},
	              		editor:
	              		xnGridCdComboBox({
	              			field:'isSell',
	              			typeCode:'YES_OR_NO',
	              			editable:false
	              		})
	              	}),
	              	j_gridText({field:'supplierSortName',title:'供应商',width:100,sortable:false}),
	              	j_gridText({field:'manufacturer',title:'厂家',width:100,sortable:false}),
	              	j_gridText({field:'alreadyUpdateFarmCount',title:'同步数量',width:60,sortable:false}),
	              	j_gridText({field:'requestUpdateFarm',title:'需同步的猪场',width:100,sortable:false,
	              		formatter:function(value,row){
	              			var rowIndex = $('#listTable').datagrid('getRowIndex',row);
	              			return '<a class="editcls" onclick="selectRequestUpdateFarm('+rowIndex+')" href="javascript:void(0)">需同步的猪场</a>'
	              			+'(<span id="selectCount'+rowIndex+'"></span>/<span id="allCount'+rowIndex+'" name="allCount"></span>)';
	              		}
	              	})
			    ]],
//			    onBeginEdit:function(index,row){

//			    },
			    onEndEdit:function(index,row){
			    	var isWarehouse = $('#listTable').datagrid('getEditor', {
		                index: index,
		                field: 'isWarehouse'
		            });
			    	if(isWarehouse != null){
			    		row.isWarehouseName = $(isWarehouse.target).combogrid('getText');
			    	}
			    	var isPurchase = $('#listTable').datagrid('getEditor', {
		                index: index,
		                field: 'isPurchase'
		            });
			    	if(isPurchase != null){
			    		row.isPurchaseName = $(isPurchase.target).combogrid('getText');
			    	}
			    	var isSell = $('#listTable').datagrid('getEditor', {
		                index: index,
		                field: 'isSell'
		            });
			    	if(isSell != null){
			    		row.isSellName = $(isSell.target).combogrid('getText');
			    	}
			    },
				onLoadSuccess : function(data) {
					if (!data.success) {
						$.messager.alert({
							title : '错误',
							msg : data.errorMsg
						});
					}else{
						selectAndAllCount(data.rows, $('#farmTable').datagrid('getRows'));
					}
				}
		})
	);
	
	
	$('#farmTable').datagrid(
			j_grid_view({
				width:'auto',
				height:'auto',
				url:supplierPrUrl+'searchCompanyByFarmIdToList.do',
				columnFields:'farmSortName',
				columnTitles:'猪场名称',
				hiddenFields:'',
				fit:false,
				pagination:false,
				onLoadSuccess : function(data) {
					if (!data.success) {
						$.messager.alert({
							title : '错误',
							msg : data.errorMsg
						});
					}else{
						selectAndAllCount($('#listTable').datagrid('getRows'), data.rows);
					}
				}
			},'farmTable')
		)
	
	setTimeout(function(){
		//仓库物料
		xnRadioBox({
			id:'isWarehouse',
			name:'isWarehouse',
			typeCode:'YES_OR_NO',
			//是否有监听事件
			onChange:false,
			defaultValue:'N'
		});

		//采购物料
		xnRadioBox({
			id:'isPurchase',
			name:'isPurchase',
			typeCode:'YES_OR_NO',
			//是否有监听事件
			onChange:false,
			defaultValue:'N'
		});

		//销售物料
		xnRadioBox({
			id:'isSell',
			name:'isSell',
			typeCode:'YES_OR_NO',
			//是否有监听事件
			onChange:false,
			defaultValue:'N'
		});

		//物料组
//		xnComboGrid({
//			id:'groupId',
//			idField:'rowId',
//			textField:'groupName',
//			url:'/material/searchCdMaterialGroupToList.do',
//			width:550,
//			editable:true,
//			columns:[[ 	
//			           	{field:'rowId',title:'ID',width:100,hidden:true},
//				        {field:'businessCode',title:'物料代码',width:100},
//				        {field:'groupName',title:'物料组名称',width:100},
//				    ]]
//		});

		//计量单位
		xnCdCombobox({
			id:'unit',
			required:true,
			sameField:true,
			typeCode:'UNIT',
			onChange:changeOutputMinQtyUnit
		});
		
		//物料类型
		xnCdCombobox({
			id:'materialType',
			readonly:true,
			typeCode:'MATERIAL_TYPE'
		});
		
		//供应商
		xnCombobox({
			id:"supplierId",
			valueField:"supplierId",
			required:true,
			textField:"supplierSortName",
			panelHeight:200
		});
		
		addFocus('supplierId','combobox','/supplyChian/searchCompanyFromRequireBillToList.do?searchType=All&canPurchaseSupplierId='+canPurchaseSupplierId);
		
		// 物料大类
		xnCdCombobox({
		    id:'materialFirstClass',
		    typeCode:'MATERIAL_FIRST_CLASS',
		    editable:false,
//		    panelHeight:300,
		    required:true,
		    linkField:'materialSecondClass',
		    linkCode:'MATERIAL_SECOND_CLASS'
		});
		
		// 物料中类
		xnCdCombobox({
		    id:'materialSecondClass',
		    required:true,
		    editable:false
		});
		
		//高级查询供应商
		xnCombobox({
			id:"supplierForSearch",
			url:'/supplyChian/searchCompanyFromRequireBillToList.do?searchType=All&canPurchaseSupplierId='+canPurchaseSupplierId,
			valueField:"supplierId",
			textField:"supplierName",
			editable:true,
			hasAll:true
		});
		
		//高级查询物料大类
		xnCdCombobox({
			id:'materialFirstClassForAdvancedSearch',
			typeCode:'MATERIAL_FIRST_CLASS',
		    editable:false,
		    hasAll:true,
		    linkField:'materialSecondClassForAdvancedSearch',
		    linkCode:'MATERIAL_SECOND_CLASS'
		});
		
		//高级查询物料中类
		xnCdCombobox({
			id:'materialSecondClassForAdvancedSearch',
		    editable:false,
			hasAll:true
		});
		
	},500);
});

function selectAndAllCount(listTableData, farmTableData){
	var allCount = farmTableData.length;
	$.each(listTableData,function(index,data){
		$('#selectCount'+index).html(data.requestUpdateFarm.length);
		$('#allCount'+index).html(allCount);
	});
}

function selectRequestUpdateFarm(index){
	// 记录选中的行数
	$('#selectedIndex').val(index);
	// 清空选中的猪场
	$('#farmTable').datagrid('uncheckAll');
	
	// 获取上一次选中的猪场，并且勾选上一次勾选的猪场
	var data = $('#farmTable').datagrid('getRows');
	var requestFarm = $('#listTable').datagrid('getRows')[index].requestUpdateFarm;
	if(requestFarm != null){
		for(var i=0;i<data.length;i++){
			var eachFarmId = data[i].farmId;
			for(var j=0;j<requestFarm.length;j++){
				var requestFarmId = requestFarm[j].farmId;
				if(eachFarmId == requestFarmId){
					$('#farmTable').datagrid('checkRow',i);
					break;
				}
			}
		}
	}
	leftSilpFun('farmSelect');
}

function onBtnSelectFarm(){
	var data = $('#farmTable').datagrid('getSelections');
	var tableRow = $('#listTable').datagrid('getRows')[$('#selectedIndex').val()];
	tableRow.requestUpdateFarm = data;
	$('#selectCount'+$('#selectedIndex').val()).html(data.length);
	rightSlipFun('farmSelect',390);
//	$.messager.alert('提示','修改成功!');
	
}
/*************页面渲染结束******************************************************************************/

//function searchMaterialBySupplierToPage(newValue,oldValue){
//	$('#listTable').datagrid('reload',basicUrl+searchMainListUrl+'?supplierId='+newValue);
//}

/**
 * 新增
 */
function onBtnAdd(){
	$('#showMaterialTypePanel').dialog('open');
	$('#rowId').val(0);
	$("#manufacturer").textbox('readonly',false);
	$("#supplierId").combobox('readonly',false);
	$("#materialFirstClass").combobox('readonly',false);
	$("#materialSecondClass").combobox('readonly',false);
	xnCdCombobox({ 
		id:'showMaterialType',
		//typeCode:'MATERIAL_TYPE',
		typeCode:'MATERIAL_TYPE_SUPPLYCHIAN',
		editable:false,
		hasAll:false
	});
	$('#btnSave').attr('disabled',false).removeClass('btn-disabled');
}

function showMaterialTypeEnter(){
	var materialType = $('#showMaterialType').combobox('getValue');
	var materialTypeText = $('#showMaterialType').combobox('getText');
	if(materialType == null || materialType == ''){
		$.messager.alert('警告','请选择物料类型！');
	}
	/*else{
		saveUrl = prUrl+'editMaterial.do';
		editDisplay();
		loadDetatil('add','新增'+materialTypeText,null,materialType);
		
	}*/
	
	/*新增供应链通用信息全显示隐藏*/
	else{
	    // 是猪只和精液等猪信息
        if ("Boar"==materialType || "Sow"==materialType || "PorkPig"==materialType || "Sperm"==materialType){
        	saveUrl = prUrl+'editMaterial.do';
    		editDisplay();
    		loadDetatil('add','新增'+materialTypeText,null,materialType);
    		$('#supplyChianComInfo').css('display','none');
    		$("#manufacturer").textbox('disable');
    		$("#supplierId").combobox('disable');
    		$("#materialFirstClass").combobox('disable');
    		$("#materialSecondClass").combobox('disable');
       	}
        else{
        	saveUrl = prUrl+'editMaterial.do';
        	$('#supplyChianComInfo').css('display','block');
    		$("#manufacturer").textbox('enable');
    		$("#supplierId").combobox('enable');
    		$("#materialFirstClass").combobox('enable');
    		$("#materialSecondClass").combobox('enable');
    		editDisplay();
    		loadDetatil('add','新增'+materialTypeText,null,materialType);
        }
	}
	
	/*新增供应链通用信息全显示隐藏*/
}

function dialogCancel(dialog){
	$('#'+dialog).dialog('close');
}

/**
 * 保存
 * pms{
 * 	model:模块代码
 * }
 */
function onBtnSave(){
	$('#btnSave').attr('disabled',true).addClass('btn-disabled');
	$.messager.progress();	
	jAjax.submit_form('editForm',saveUrl
	,function(response){
		$('#listTable').datagrid('reload');
		$('#editWin').window('close');
		$.messager.alert('提示','保存成功！');
		$('#btnSave').attr('disabled',false).removeClass('btn-disabled');
		$.messager.progress('close');
	}
	,function(response){
		jAjax.errorFunc(response.errorMsg);
		$('#btnSave').attr('disabled',false).removeClass('btn-disabled');
		$.messager.progress('close');
	},null,true,null);

}

/**
 * 编辑
 */
function onBtnEdit(){
	var record = $('#listTable').datagrid('getSelections');
	var length = record.length;
	if(length < 1){
		$.messager.alert('警告','请选择一条数据！');
	}else if(length > 1){
		$.messager.alert('警告','只能选择一条数据！');
	}else{
		$('#btnSave').attr('disabled',false).removeClass('btn-disabled');
		saveUrl = supplierPrUrl+'editUpdateMaterialDetailToFarms.do';
		editHidden();
		loadDetatil('edit','编辑',record[0],null);
		
		$("#manufacturer").textbox('readonly',true);
		$("#supplierId").combobox('readonly',true);
		$("#materialFirstClass").combobox('readonly',true);
		$("#materialSecondClass").combobox('readonly',true);
		
	}
}
/**
 * 双击查看
 * @param index
 * @param row
 */
function onDblClickRow(index,row){
	loadDetatil('view','查看',row,null);
}
/**
 * 查看
 */
function onBtnView(){
	var record = $('#listTable').datagrid('getSelections');
	var length = record.length;
	if(length < 1){
		$.messager.alert('警告','请选择一条数据！');
	}else if(length > 1){
		$.messager.alert('警告','只能选择一条数据！');
	}else{
		editDisplay();
		loadDetatil('view','查看',record[0],null);
	}
}

/**
 *加载明细的操作
 */
function loadDetatil(oprType, title, record,type) {
	
	var materialType;
	var mainRowId;
	
	// 查看操作
	if (oprType == "view") {
		
		$('#btnSave').css('display','none');
	} else {
		$('#btnSave').css('display','inline-block');
	}
	
	// 新增操作,title直接根据下拉框组合，传值
	if (oprType == "add") {
		materialType=type;
	} else {
		materialType=record.materialType
		title=title+record.materialTypeName;
		mainRowId=record.rowId;
	}
	//物料组名称
//	$('#groupId').combogrid('grid').datagrid('load',{pigType:materialType});
	
	$('#include').load(basicUrl + '/jsp/basicinfo/Cd'+materialType + '.jsp', function() {
		
				$('#editWin').window({
					title : title,
				});
				$('#editWin').window('open');

				$('#editForm').form('reset');
				// 新增操作
				if (oprType != "add"){
					var data = searchDetailMaterial(record);
					//复制新增操作
					if (oprType == "copyadd") {
						data.rowId = 0;
					}else if(oprType == "edit"){
						data.rowId = mainRowId;
					}
					
					$('#editForm').form('load', data);
				}
				$('#materialType').combobox('setValue',materialType);
				if (oprType == "add") {
					$('#showMaterialTypePanel').dialog('close');
				}
			});

}

/**
 * @param recode 选中的那行数据
 */
function searchDetailMaterial(recode){

	return jAjax.submit(prUrl+'searchDetailMaterialToList.do', recode);
}

/**
 * 高级查询搜索
 */
function onBtnSearch(para,url){
	var winId = $(para).parent().parent().attr('id');
    rightSlipFun(winId,390);
	 $("#listTable").datagrid('load',form2Json("searchForm"));
}

function editMaterialToFarms(methodType){
	var record = $('#listTable').datagrid('getSelections');
	var length = record.length;
	if(length < 1){
		$.messager.alert('警告','请选择一条数据！');
	}else{
//		for(var i=0;i<record.length;i++){
//			var requestFarm = record[i].requestUpdateFarm;
//			if(requestFarm==null || requestFarm.length==0){
//				$.messager.alert('警告','物料名称【'+record[i].materialName+'】没有勾选同步猪场！');
//				return;
//			}
//		}
		
		var submitData = [];
		for(var i=0;i<record.length;i++){
			var requestFarm = record[i].requestUpdateFarm;
			if(requestFarm!=null && requestFarm.length!=0){
				submitData.push(record[i]);
			}
		}
		if(submitData.length!=0){
			$.messager.progress();	
			jAjax.submit(editMaterialToFarmsUrl,{json:JSON.stringify(submitData),methodType:methodType}
				,function(response){
					$('#listTable').datagrid('reload');
					$.messager.alert('提示','同步更新成功!');
					$.messager.progress('close');	
				},function(response){
		    		jAjax.errorFunc(response.errorMsg);
		    		$.messager.progress('close');
		    	},null,true,null,false);
		}else{
			$('#listTable').datagrid('reload');
			$.messager.alert('提示','同步更新成功!');
		}
	}
}

/**
 * 删除
 * pms{
 * 	model:模块代码
 * }
 */
function onBtnDelete(){
	var record = $('#listTable').datagrid('getSelections');
	var length = record.length;
	if(length < 1){
		$.messager.alert('警告','请选择一条数据！');
	}else{
		$.messager.confirm('提示', '确定要删除这'+record.length+'条记录吗？', function(r){
			if (r){
				$.messager.progress();	
				var materialList = [];
				$.each(record,function(index,data){
					materialList.push({
						materialId:data.rowId,
						materialType:data.materialType
						});
				});
				
				jAjax.submit(deleteUrl,{materialList:JSON.stringify(materialList)}
				,function(response){
					$('#listTable').datagrid('reload');
					$.messager.alert('提示','删除成功!');
					$.messager.progress('close');	
				},function(response){
		    		jAjax.errorFunc(response.errorMsg);
		    		$.messager.progress('close');
		    	},null,true,null,null);
			}
		});
		
	}
}

/**
 * 自动填写最小领用的单位
 */
function changeOutputMinQtyUnit(newValue,oldValue){
	$('#outputMinQtyUnit').html(newValue);
}
function editDisplay(){
	$('#editHidden01').css('display','inline');
	$('#editHidden02').css('display','inline');
}
function editHidden(){
	$('#editHidden01').css('display','none');
	$('#editHidden02').css('display','none');
}

function getPurchaserInfo(){
	var data = jAjax.submit('/supplyChian/searchPurchaseTypeByEmployeeIdToList.do')[0];
	purchaseFlag = data.purchaseType;
	if(data.canPurchaseSupplierId){
		canPurchaseSupplierId = data.canPurchaseSupplierId;
	}
	if(purchaseFlag != '1' && purchaseFlag != '2' && purchaseFlag != '3'){
		// 非采购人员进入此页面
		// 是页面崩溃，无法操作
		$('#eventCode').combobox('11122', '');
	}
}