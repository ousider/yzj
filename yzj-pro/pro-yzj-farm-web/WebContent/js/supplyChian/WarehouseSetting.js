var title = '仓库';
var editIndex = undefined;
var prUrl='/supplyChian/';
var saveUrl='';
var deleteUrl=prUrl+'deleteWarehouseSetting.do';
var forbiddenUrl=prUrl+'editForbiddenWarehouseSetting.do';
var useUrl=prUrl+'editUseWarehouseSetting.do';
var searchMainListUrl=prUrl+'searchWarehouseSettingToPage.do';
/** *********页面渲染开始*************************************************************************/

//保存类型
var saveType=0;
$(document).ready(function(){
	
	/**
	 * 主表加载
	 */
	$('#table').datagrid(
			j_grid({
				toolbar:'#tableToolbarList',
				url:searchMainListUrl,
				columnFields:'rowId,status,warehouseName,warehouseTypeName,statusName,warehouseAddress,notes',
				columnTitles:'ID,状态,仓库名称,仓库类型,状态,地址,备注',
				hiddenFields:'rowId,status'
			})
	);
	setTimeout(function(){
		//创建类型
		xnCdCombobox({
			id:'warehouseType',
			materialCode:'Sperm',
			editable:false,
			required:true,
			typeCode:'WAREHOUSE_TYPE',
		});
		xnCdCombobox({
			id:'materialType',
			editable:false,
			required:true,
			multiple:true,
			typeCode:'MATERIAL_FIRST_CLASS',
		});
		
		// 管理角色
		xnCombobox({
			id:'operationRole',
			editable:false,
			required:true,
			valueField:"rowId",
			textField:"roleName",
			url:prUrl + 'searchRoleByFarmIdToList.do'
		});
		
		xnCdCombobox({
			id:'warehouseCategory',
			editable:false,
			required:true,
			typeCode:'WAREHOUSE_CATEGORY'
		});
	},500)
	
});
/*************页面渲染结束******************************************************************************/

/**
 * 新增
 */
function onBtnAdd(){
	saveType=1;
	saveUrl=prUrl+'editWarehouseSetting.do';
	$('#editForm').form('reset');
	$('#editWin').window({
		title:'新增'+ title
	});
	$('#hidePanel').panel('close');
	$('#editForm').css("display","block");
	$('#editWin').window('open');
	$('#btnSave').css('display','inline-block');
}

/**
 * 编辑
 */
function onBtnEdit(){
	saveType=1;
	saveUrl=prUrl+'editWarehouseSetting.do';
	$('#editForm').form('reset');
	var record = $('#table').datagrid('getSelections');
	var length = record.length;
	if(length < 1){
		$.messager.alert('警告','请选择一条数据！');
	}else if(length > 1){
		$.messager.alert('警告','只能选择一条数据！');
	}else{
		$('#editWin').window({
			title:'编辑'+ title
		});
		$('#editWin').window('open');
		$('#hidePanel').panel('close');
		$('#editForm').css("display","block");
		$('#editForm').form('load',record[0]);
		$('#btnSave').css('display','inline-block');
	}
}

/**
 * 禁用
 */
function onBtnForbidden(){
var record = $('#table').datagrid('getSelections');
var length = record.length;
if(length < 1){
	$.messager.alert('警告','请选择一条数据！');
}else{
	$.messager.confirm('提示', '确定要禁用这'+record.length+'条记录吗？', function(r){
		if (r){
			$.messager.progress();	
			var ids = [];
			var errorFlag = '0';
			$.each(record,function(index,data){
				if(data.status == '1'){
					var dataIndex = $('#table').datagrid('getRowIndex',data)+1;
					$.messager.alert('错误','第'+dataIndex+'行为初始化状态，无法禁用！');
					$.messager.progress('close');
					errorFlag = '1';
					return false;
				}
				ids.push(data.rowId);
			});
			if(errorFlag=='0'){
				$.ajax({
				    type: 'POST',
				    url: basicUrl+forbiddenUrl ,
				    data: {
				    	'ids':ids
				    } ,
				    success: function(response){
				    	$.messager.progress('close');
				    	if(response.success){
				    		$.messager.alert('警告','禁用成功！');
				    		$('#table').datagrid('reload');
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
		}
	});
  }
}

/**
 * 启用
 */
function onBtnUse(){
var record = $('#table').datagrid('getSelections');
var length = record.length;
if(length < 1){
	$.messager.alert('警告','请选择一条数据！');
}else{
	$.messager.confirm('提示', '确定要启用这'+record.length+'条记录吗？', function(r){
		if (r){
			$.messager.progress();	
			var ids = [];
			var errorFlag = '0';
			$.each(record,function(index,data){
				if(data.status == '1'){
					var dataIndex = $('#table').datagrid('getRowIndex',data)+1;
					$.messager.alert('错误','第'+dataIndex+'行为初始化状态，无法启用，请初始化！');
					$.messager.progress('close');
					errorFlag = '1';
					return false;
				}
				ids.push(data.rowId);
			});
			if(errorFlag=='0'){
				$.ajax({
				    type: 'POST',
				    url: basicUrl+useUrl ,
				    data: {
				    	'ids':ids
				    } ,
				    success: function(response){
				    	$.messager.progress('close');
				    	if(response.success){
				    		$.messager.alert('警告','启用成功！');
				    		$('#table').datagrid('reload');
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
		}
	});
  }
}

/**
 * 库存初始化
 */
function onBtnInitialize(){
	var record = $('#table').datagrid('getSelections');
	var length = record.length;
	if(length < 1){
		$.messager.alert('警告','请选择一条数据！');
	}else if(length > 1){
		$.messager.alert('警告','只能选择一条数据！');
	}else{
		if(record[0].status != '1'){
			$.messager.alert('提示', '已经初始化过，无法再次初始化！');
			return;
		}
		
		xnCombobox({
			id:'warehouseId',
			editable:false,
			readonly:true,
			valueField:"rowId",
			textField:"warehouseName",
			url:'/supplyChian/searchWarehouseToList.do',
			onChange:searchMaterialByWarehouseToList
		});
		
		$('#listTable').datagrid(
				j_detailGrid({
					height:'100%',
					toolbar:"#listTableToolbar",
					columns:[[
				      	{field:'ck',checkbox:true},
				      	j_gridText({field:'rowId',title:'rowId',hidden:true,sortable:false}),
		              	j_gridText({field:'materialName',title:'物料名称',width:100,sortable:false}),
		            	j_gridText({field:'materialFirstClassName',title:'大类',width:100,sortable:false}),
		            	j_gridText({field:'materialSecondClassName',title:'中类',width:100,sortable:false}),
		              	j_gridText({field:'specAll',title:'规格',width:100,sortable:false}),
		              	j_gridText({field:'productionDate',title:'生产日期',width:80,editor:{type:'datebox',options:{editable:false}},sortable:false}),
		              	j_gridText({field:'effectiveDate',title:'有效日期',width:80,editor:{type:'datebox',options:{editable:false}},sortable:false}),
		              	j_gridNumber({field:'actualQty',title:'结存量',formatter:function(value,row){
	              			value = value == null || value == undefined || value == '' ? 0 : value;
		              		return value+row.unit;
		              	},min:0,precision:3,width:80,sortable:false}),
		              	j_gridNumber({field:'initActualPrice',title:'单价',formatter:function(value,row){
	              			value = value == null || value == undefined || value == '' ? 0 : value;
		              		return value+'元';
		              	},min:0,precision:3,width:80,sortable:false}),
				    ]],
				    onEndEdit:function(index,row){
				    	var actualQty = $('#listTable').datagrid('getEditor', {
			                index: index,
			                field: 'actualQty'
			            });
				    	if(actualQty != null){
				    		var actualQtyValue;
				    		if($(actualQty.target).numberspinner('getValue')){
				    			actualQtyValue = new BigDecimal($(actualQty.target).numberspinner('getValue'));
				    		}else{
				    			actualQtyValue = new BigDecimal('0');
				    		}
				    		var outputMinQty = row.outputMinQty == '' || row.outputMinQty == undefined ? new BigDecimal("1") : new BigDecimal(row.outputMinQty);
				    		if(actualQtyValue.remainder(outputMinQty) != 0){
				    			$.messager.alert('提示','第'+(index+1)+'行物料'+row.materialName+'入库数量输入不符合要求，不是最小领用量的整数倍,请重新输入！');
				    			row.actualQty = null;
				    		}
				    	}
				    }
			})
		);
		
		$('#warehouseId').combobox('setValue',record[0].rowId);
		
		saveType=2;
		saveUrl = prUrl+'editWarehouseStoreMaterial.do?warehouseId='+$('#warehouseId').combobox('getValue');
		$('#editWin').window({
			title:title + '初始化'
		});
		$('#editWin').window('open');
		$('#editForm').form('reset');
		$('#btnSave').css('display','inline-block');
		$('#hidePanel').panel('open');
		$('#editForm').css('display', 'none');
		
		var listTable = document.getElementById('listTable');
		if(listTable!=null){
			$('#listTable').datagrid('loadData',{success:true,total: 0, rows: [] });
		}
	}
}

/**
 * 默认仓库设置
 */
function onBtnDefaultHouse(){
	var record = $('#table').datagrid('getSelections');
	var length = record.length;
	if(length < 1){
		$.messager.alert('警告','请选择一条数据！');
	}else if(length > 1){
		$.messager.alert('警告','只能选择一条数据！');
	}else{
		
		xnCombobox({
			id:'warehouseId',
			editable:false,
			readonly:true,
			valueField:"rowId",
			textField:"warehouseName",
			url:'/supplyChian/searchWarehouseToList.do',
			onChange:searchMaterialByWarehouseToList
		});
		
		$('#listTable').datagrid(
				j_detailGrid({
					height:'100%',
					toolbar:"#listTableToolbar",
					columns:[[
				      	{field:'ck',checkbox:true},
				      	j_gridText({field:'rowId',title:'rowId',hidden:true,sortable:false}),
		              	j_gridText({field:'materialName',title:'物料名称',width:100,sortable:false}),
		             	j_gridText({field:'materialFirstClassName',title:'大类',width:100,sortable:false}),
		            	j_gridText({field:'materialSecondClassName',title:'中类',width:100,sortable:false}),
		              	j_gridText({field:'specAll',title:'规格',width:100,sortable:false}),
				    ]]
			})
		);
		
		$('#selectMaterialListTable').datagrid(
				j_grid_view({
					fit:false,
					height:'70%',
					width:'100%',
//					url:searchSelectMaterialListTableUrl,
					columnFields:'rowId,materialName,materialFirstClassName,materialSecondClassName,supplierSortName,manufacturer,specAll,materialNotes,defaultWarehouseName',
					columnTitles:'ID,物料名称,大类,中类,供应商,厂家,规格,备注,默认仓库',
					hiddenFields:'rowId',
				})
		);
		
		$('#warehouseId').combobox('setValue',record[0].rowId);
		
		saveType=2;
		saveUrl = prUrl+'editDefaultHouse.do?warehouseId='+$('#warehouseId').combobox('getValue');
		$('#editWin').window({
			title:title + '初始化'
		});
		$('#editWin').window('open');
		$('#editForm').form('reset');
		$('#btnSave').css('display','inline-block');
		$('#hidePanel').panel('open');
		$('#editForm').css('display', 'none');
		
		var listTable = document.getElementById('listTable');
		if(listTable!=null){
			$('#listTable').datagrid('loadData',{success:true,total: 0, rows: [] });
		}
	}
}

function searchMaterialByWarehouseToList(newValue,oldValue){
//	// 物料大类
//	xnCdCombobox({ 
//		id:'showMaterialFirstClass',
//		typeCode:'MATERIAL_FIRST_CLASS',
//		editable:false,
//		hasAll:true,
//	    linkField:'showMaterialSecondClass',
//	    linkCode:'MATERIAL_SECOND_CLASS',
//		onChange:searchMaterialBySupplierByMaterialFirstClassToPage
//	});
	//物料大类
	xnCombobox({
		id:'showMaterialFirstClass',
		valueField:"materialFirstClass",
		textField:"materialFirstClassName",
		editable:false,
		url:'/supplyChian/searchMaterialTypeByWarehouseToList.do?warehouseId='+newValue,
		firstDataIsDefault:true,
		onChange:searchMaterialBySupplierByMaterialFirstClassToPage01
	});
	
	var materialFirstClass = $('#showMaterialFirstClass').combobox('getValue');
	var supplierId = $('#supplier').combobox('getValue');
	
	$('#selectMaterialListTable').datagrid('load', basicUrl + searchSelectMaterialListTableUrl + '?dafengModel='+$('#dafengModel').val() + '&supplierId='+supplierId + '&materialFirstClass='+materialFirstClass+'&isPurchase='+$('#isPurchase').val());
}

function searchMaterialBySupplierByMaterialFirstClassToPage01(newValue,oldValue){
		var linkValue;
		if(!newValue){
			linkValue = -1;
		}else{
			linkValue = newValue;
		}
		var linkCode = 'MATERIAL_SECOND_CLASS';
		var linkData = jGetCdCode(linkCode,linkValue);
		var linkDefaultValue = '';
		$.each(linkData,function(index,row){
			if(row.isDefault == 'Y'){
				linkDefaultValue = row.codeValue;
			}
		});
		if(oldValue != '' && oldValue != null){
			$('#showMaterialSecondClass').combobox('setValue', linkDefaultValue);
		}
		
		$('#showMaterialSecondClass').combobox('loadData', linkData);
		
		$('#showMaterialName').searchbox('setValue','');

		var supplierId = $('#supplier').combobox('getValue');
		var materialSecondClass = $('#showMaterialSecondClass').combobox('getValue');

		searchMaterialByConditionToPage(supplierId,newValue,materialSecondClass,'');
}

//选择物料
function selectMaterial(){
	var warehouseId = $('#warehouseId').combobox('getValue');
	if(warehouseId==''){
		$.messager.alert('提示', '请选择仓库名称！');
	}else{
		$('#listTable').datagrid('reload');
		leftSilpFun('selectMaterialWin',true,9001);
	}
}
/**
 * 双击事件
 * @param index
 * @param row
 */
function onDblClickRow(index,row){
    $('#editWin').window({
	title:'查看'+ title
    });
    $('#editWin').window('open');
    $('#hidePanel').panel('close');
	$('#editForm').css("display","block");
    $('#btnSave').css('display','none');
    $('#editForm').form('load',row);
    }

/**
 * 保存
 */
function onBtnSave(){
	//新增编辑时候保存
	if(saveType==1)
	{
		$('#btnSave').attr('disabled',true).addClass('btn-disabled');
		$.messager.progress();	
		$('#editForm').form('submit',{
			url:basicUrl+saveUrl,
			onSubmit: function(){
				var isValid = $('#editForm').form('validate');
				if (!isValid){
					$('#btnSave').attr('disabled',false).removeClass('btn-disabled');
					$.messager.progress('close');	
				}
				return isValid;
			},
			success: function(response){
				response=eval('('+response+')');
				if(response.success){
					if(typeof(eventName) == "undefined"){
						$('#editWin').window('close');
						$.messager.alert('提示','保存成功！');
					}else{
						$('#editForm').form('reset');
					}
					var table = document.getElementById("table");
					if(table != null){
						$('#table').datagrid('reload');
					}
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

	}
	//库存初始化保存	
	else if(saveType==2)
	{
		var queryParams = null;
		var listTable = document.getElementById("listTable");
		//判断是否有明细表
		if(listTable!= null){
			queryParams = collectDetailData();
		}else{
			queryParams = {
				status:1,
				deletedFlag:0,
			};
		}
		
		$.messager.progress();	
		$.ajax({  
		    url : basicUrl+saveUrl,
		    async : false, 
		    type : "POST",  
		    dataType : "json",  
		    data:queryParams,
		    success : function(response) { 
				if(response.success){
					if(typeof(eventName) == "undefined"){
						$('#editWin').window('close');
						$.messager.alert('提示','保存成功！');
					}else{
						$('#editForm').form('reset');
					}
					var table = document.getElementById("table");
					if(table != null){
						$('#table').datagrid('reload');
					}
				}else{
					 $.messager.alert({
	                     title: '错误',
	                     msg: response.errorMsg
	                 });
				}
				$.messager.progress('close');
		    }  
		});
	}
}
