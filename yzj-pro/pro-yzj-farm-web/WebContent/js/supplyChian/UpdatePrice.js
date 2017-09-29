var title = '更新单价';
var editIndex = undefined;
var prUrl='/supplyChian/';
var saveUrl=prUrl+'editUpdatePrice.do';
var searchMainListUrl = prUrl+'searchUpdatePriceToPage.do';
var searchSupplierChanggeToListUrl =prUrl+ 'searchEditUpdatePriceDetailToList.do'
var searchMainToListUrl =prUrl+ 'searchUpdatePriceDetailToList.do'
//var deleteUrl=prUrl+'deleteUpdatePrice.do';

//purchaseFlag判断可以采购的类型[1]-全部[2]-饲料[3]-兽药
var purchaseFlag = '1';
var canPurchaseSupplierId = '';



/** *********页面渲染开始*************************************************************************/
$(document).ready(function(){

	/**
	 * 主表加载
	 */
	$('#table').datagrid(
		j_grid({
			toolbar:"#tableToolbarList",
			url:searchMainListUrl,
			columnFields:'rowId,supplierId,supplierName,monthName,dateRangeName,startDateEndDate,createName,notes',
			columnTitles:'ID,supplierId,供应商,月份,日期范围,日期范围,创建者,备注',
			hiddenFields:'rowId,supplierId',
			onDblClickRowFun:showUpdatePriceDetail
		},'table')
	);
	
	//延时加载细表
	setTimeout(function(){
		/**
		 * 细表加载
		 */
		$('#listTable').datagrid(
				j_detailGrid({
				columns:[[
			              	{field:'ck',checkbox:true},
			              	j_gridText({field:'rowId',title:'ID',hidden:true,sortable:false}),
			              	j_gridText({field:'deletedFlag',title:'deletedFlag',hidden:true,sortable:false}),
			              	j_gridText({field:'materialId',title:'物料ID',hidden:true,sortable:false}),
			              	j_gridText({field:'materialName',title:'物料名称',sortable:false}),
			              	j_gridText({field:'specAll',title:'规格',sortable:false}),
			              	j_gridText({field:'unit',title:'计量单位',sortable:false}),
			              	j_gridNumber({field:'price',title:'单价',formatter:function(value,row){
		              			value = value == null || value == undefined || value == '' ? 0 : value;
			              		return value+'元';
			              	},min:0,precision:3,width:80,sortable:false}),
			              	j_gridText({field:'notes',title:'备注',editor:'textbox',sortable:false})
			              	]]
			              	
			})
		);
		
		
		$('#updatePriceDetailInfoTable').datagrid(
				j_grid_view({
					haveCheckBox:false,
					columnFields:'materialName,specAll,unit,price,notes',
					columnTitles:'物料名称,规格,单位,价格,备注',
					hiddenFields:'',
					fit:false,
					pagination:false,
					height:'100%',
					width:'100%'
					//onDblClickRowFun:showInputInfoDetail
				}));
		
//		$('#updatePriceDetailTable').datagrid(
//				j_detailGrid({
//					columns:[[
//					          {field:'ck',checkbox:true},
//					          j_gridText({field:'rowId',title:'ID',hidden:true,sortable:false}),
//					          j_gridText({field:'deletedFlag',title:'deletedFlag',hidden:true,sortable:false}),
//					          j_gridText({field:'materialId',title:'物料ID',hidden:true,sortable:false}),
//					          j_gridText({field:'materialName',title:'物料名称',sortable:false}),
//					          j_gridText({field:'specAll',title:'规格',sortable:false}),
//					          j_gridText({field:'unit',title:'计量单位',sortable:false}),
//					          j_gridNumber({field:'price',title:'单价',formatter:function(value,row){
//					        	  value = value == null || value == undefined || value == '' ? 0 : value;
//					        	  return value+'元';
//					          },min:0,precision:3,width:80,sortable:false}),
//					          j_gridText({field:'notes',title:'备注',editor:'textbox',sortable:false})
//					          ]]
//				})
//		);
		
		
	
		// 供应商
		xnCombobox({
			id:"supplierId",
			url:'/supplyChian/searchUpdatePriceSupplierToList.do',
			valueField:"supplierId",
			textField:"supplierSortName",
			required:true,
			onChange:supplierChange
		});
		
		// 月份
		xnCdCombobox({
			id:'month',
			typeCode:'MONTH',
			editable:false,
			required:true
		});
		
		// 月份划分
		xnCdCombobox({
			id:'monthSplit',
			typeCode:'MONTH_SPLIT',
			editable:false,
			required:true
		});
		
		
		
		
		
		//高级查询 月份
		xnCdCombobox({
			id:'searchMonth',
			typeCode:'MONTH',
			editable:false,
			//multiple:true,
			//defaultValue:[1,4,5]
			hasAll:true
		});
		
		
		//高级查询 日期
		xnCdCombobox({
			id:'serchDateRange',
			typeCode:'MONTH_SPLIT',
			editable:false,
			hasAll:true
		
			//defaultValue:[1,4,5]
		});
		
		
		
		//高级查询 供应商
		xnCombobox({
			id:"searchSupplierId",
			url:'/supplyChian/searchUpdatePriceSupplierToList.do',
			valueField:"supplierId",
			textField:"supplierSortName",
			hasAll:true
		});
	

	});
})


/**
 * 查看
 * pms{
 * 	model:模块代码
 * 	title:模块名称
 * }
 */
function onBtnView(){
	var record = $('#table').datagrid('getSelections');
	var length = record.length;
	if(length < 1){
		$.messager.alert('警告','请选择一条数据！');
	}else if(length > 1){
		$.messager.alert('警告','只能选择一条数据！');
	}else{
		var index = $('#table').datagrid('getRowIndex',record[0]);
		showUpdatePriceDetail(index,record[0]);
	}
}

 //查看
function showUpdatePriceDetail(index,row){
	$('#updatePriceDetailInfoTable').datagrid('load',basicUrl + searchMainToListUrl+'?supplierId='+row.supplierId+'&startDateEndDate='+row.startDateEndDate);
	leftSilpFun('updatePriceDetailId');
	
}

function rightSlipAll(){
	rightSlipFun('updatePriceDetailId',780);
}


function supplierChange(newValue, oldValue){
	$('#listTable').datagrid('load',basicUrl+searchSupplierChanggeToListUrl+'?supplierId='+newValue);
	//$('#listTable').datagrid('load',basicUrl+'/backEndarchModuleExcludeById.do?ids[]='+ids);
}

/**
 * 保存
 * pms{
 * 	model:模块代码
 * }
 */
function onBtnSave(){
	var queryParams;
	$('#btnSave').attr('disabled',true).addClass('btn-disabled');
	var listTable = document.getElementById("listTable");
	//判断是否有明细表
	if(listTable!= null){
		queryParams = collectDetailData();
		if(queryParams == null){
			$.messager.alert('提示','前台提示--未添加明细不能提交！');
			$('#btnSave').attr('disabled',false).removeClass('btn-disabled');
			return;
		}
	}else{
		queryParams = {
			status:1,
			deletedFlag:0,
		};
	}

	$.messager.progress();
	jAjax.submit_form('editForm',saveUrl+"?isCheck=true",function(response){
		$('#btnSave').attr('disabled',false).removeClass('btn-disabled');
		$('#editWin').window('close');
		$.messager.alert('提示','保存成功！');
		$('#editForm').form('reset');
		var table = document.getElementById("table");
		if(table != null){
			$('#table').datagrid('reload');
		}
	},function(response){
		$('#btnSave').attr('disabled',false).removeClass('btn-disabled');
		if(response.errorMsg == 'SHOW_PROMPT_MESSAGE'){
			$.messager.confirm('提示', '供应商在此时间区间内存在更新价格单，提交会覆盖原有价格单，确定吗？', function(r){
				if(r){
					onbtnSaveSubmit(queryParams);
				}
			});
		}else{
			jAjax.errorFunc(response.errorMsg);
		}
	},queryParams);
}

function onbtnSaveSubmit(queryParams){
	$.messager.progress();
	$('#editForm').form('submit',{
		url:basicUrl+saveUrl+"?isCheck=false",
		queryParams:queryParams,
		onSubmit: function(){
			var isValid = $('#editForm').form('validate');
			if (!isValid){
				$.messager.progress('close');	
				$('#btnSave').attr('disabled',false).removeClass('btn-disabled');
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
					//重置
					var preRecord = $('#listTable').datagrid('getData');
					leftSilpFun('preSaveRecord',true,9002);
					if(preRecord.success == undefined){
						preRecord.success = true;
					}
					$('#preSaveRecordTable').datagrid('loadData',preRecord);
					if(listTable!= null){
						$('#listTable').datagrid('loadData',{success:true,total: 0, rows: [] });
					}
				}
				$('#editForm').form('reset');
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
			$('#btnSave').attr('disabled',false).removeClass('btn-disabled');
		}
	});
}

