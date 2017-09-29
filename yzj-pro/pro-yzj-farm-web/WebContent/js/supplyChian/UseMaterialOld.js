var editIndex = undefined;
var prUrl = '/supplyChian/';
var searchMainListUrl = prUrl + 'searchOutputStoreToPage.do';
var searchDetailListUrl = prUrl + 'searchOutputBillDetailToList.do';
var saveUrl=prUrl + 'editOutputUse.do';
var deleteUrl=prUrl + '';
/** *********页面渲染开始*************************************************************************/
$(document).ready(function(){
	dafengModelConfirm();
	
	/**
	 * 主表加载
	 */
	$('#table').datagrid(
		j_grid({
			toolbar:"#tableToolbarList",
			url:searchMainListUrl + '?searchType=personal&eventCode=OUTPUT_USE',
			columnFields:'rowId,billCode,billDate,eventCodeName,statusName,userName,outputerName,notes',
			columnTitles:'ID,出库单编号,出库日期,出库类型,状态,领用人,出库员,备注',
			hiddenFields:'rowId',
			onDblClickRowFun:showOutputBillDetail
		},'table')
	);
	
	setTimeout(function(){
//		//领用人
//		xnComboGrid({
//			id:'userId',
//			idField:'rowId',
//			textField:'employeeName',
//			url:'/supplyChian/searchEmployeeByIdToList.do',
//			required:true,
//			columns:[[ 	
//						{field:'rowId',title:'ID',width:100,hidden:true},
//						{field:'employeeName',title:'人员名称',width:100},
//				        {field:'employeeCode',title:'人员编码',width:100}
//				    ]]
//		});
		//领用仓库
		xnComboGrid({
			id:'warehouseId',
			idField:'rowId',
			textField:'warehouseName',
			url:'/supplyChian/searchWarehouseByMaterialTypeToList.do?dafengModel='+$('#dafengModel').val(),
			required:true,
			columns:[[ 	
						{field:'rowId',title:'ID',width:100,hidden:true},
						{field:'warehouseName',title:'仓库名称',width:100},
				        {field:'warehouseTypeName',title:'仓库类型',width:100}
				    ]]
		});
		//领用去向
		xnCdCombobox({
			id:'outputDestination',
			editable:false,
			typeCode:'OUTPUT_DESTINATION',
			onChange:outputDestinationChange
		});
		//领用去向猪群
		swineryComboGrid({
			id:'outputDesSwineryId',
			url:'/production/searchSwineryToList.do?lineId=0',
			required:true
		});
		//领用去向猪舍
		houseComboGrid({
			id:'outputDesHouseId',
			url:'/basicInfo/searchHouseToList.do?lineId=0&eventName='+eventName,
			required:true
		});
		//领用去向部门
		jAjax.submit('/supplyChian/searchDepTree.do',null,function(data){
			$('#outputDesDeptId').combotree({
				data:data,
				panelHeight:300,
				required:true
			})
		});
		$('#listTable').datagrid(
				j_detailGrid({
					toolbar:"#receptionListTableToolbar",
					dbClickEdit:true,
					columns:[[
				      	{field:'ck',checkbox:true},
		              	j_gridText({field:'materialName',title:'物料名称',width:100}),
		              	j_gridText({field:'materialFirstClassName',title:'大类',width:60,sortable:false}),
		            	j_gridText({field:'materialSecondClassName',title:'中类',width:60,sortable:false}),
		              	j_gridText({field:'specAll',title:'规格',width:100}),
		              	j_gridText({field:'warehouseName',title:'所属仓库',width:100}),
		              	j_gridText({field:'existsQty',title:'库存数量',formatter:function(value,row){
	              			value = value == null || value == undefined || value == '' ? 0 : value;
		              		return value+row.unit;
		              	},width:80}),
		              	j_gridText({field:'outputMinQty',title:'最小领用数量',formatter:function(value,row){
	              			value = value == null || value == undefined || value == '' ? 0 : value;
		              		return value+row.unit;
		              	},width:80}),
		              	j_gridNumber({field:'outputQty',title:'领用数量',formatter:function(value,row){
	              			value = value == null || value == undefined || value == '' ? 0 : value;
		              		return value+row.unit;
		              	},min:0,precision:3,width:80}),
		              	j_gridText({field:'notes',title:'备注',width:80,editor:'textbox'})
				    ]],
				    onEndEdit:function(index,row){
				    	var outputQty = $('#listTable').datagrid('getEditor', {
			                index: index,
			                field: 'outputQty'
			            });
				    	if(outputQty != null){
				    		var outputQtyValue;
				    		if($(outputQty.target).numberspinner('getValue')){
				    			outputQtyValue = new BigDecimal($(outputQty.target).numberspinner('getValue'));
				    		}else{
				    			outputQtyValue = new BigDecimal('0');
				    		}
				    		var outputMinQty = row.outputMinQty == '' || row.outputMinQty == undefined ? new BigDecimal("1") : new BigDecimal(row.outputMinQty);
				    		if(outputQtyValue.remainder(outputMinQty) != 0){
				    			$.messager.alert('提示','第'+(index+1)+'行物料'+row.materialName+'领用数量输入不符合要求，不是最小领用量的整数倍,请重新输入！');
				    			row.outputQty = 0;
				    		}
				    	}
				    }
			})
		);
	},500)
});

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
		showOutputBillDetail(index,record[0]);
	}
}

//查看
function showOutputBillDetail(index,row){
	var columnTitles = '';
	if(row.eventCode == 'OUTPUT_USE'){
		columnTitles = '物料名称,大类,中类,规格,购料/赠料,出库仓库,出库数量';
	}else{
		columnTitles = '物料名称,大类,中类,规格,购料/赠料,出库仓库,领用数量';
	}

	$('#outputBillDetailTable').datagrid(
		j_grid_view({
			haveCheckBox:false,
			url:searchDetailListUrl+'?outputId='+row.rowId,
			columnFields:'materialName,materialFirstClassName,materialSecondClassName,specAll,purchaseOrFree,outputWarehouseName,outputQtyName',
			columnTitles:columnTitles,
			hiddenFields:'',
			fit:false,
			pagination:false,
			height:'100%',
			width:'100%'
		})
	);
	leftSilpFun('outputBillDetailId');
}

//物资领用
function materialReception(){
	$('#editWin').window({
		title:'物资领用'
	});
	$('#editWin').window('open');
	$('#editForm').form('reset');
	$('#btnSave').css('display','inline-block');
	$('#listTable').datagrid('loadData',{success:true,total: 0, rows: [] });
	$('#supplychianEventCode').val('OUTPUT_USE');
	$('#outputDesSwineryId').parent().css('display','none');
	$('#outputDesHouseId').parent().css('display','none');
	$('#outputDesDeptId').parent().css('display','none');
	$('#outputDesSwineryId').combogrid('disable');
	$('#outputDesHouseId').combogrid('disable');
	$('#outputDesDeptId').combotree('disable');
}
//选择仓库物料
function selectWarehouseMaterial(){
	var wareHouseId = $('#warehouseId').combobox('getValue');
	if(wareHouseId == null || wareHouseId == ''){
		$.messager.alert('提示','请选择领用仓库');
		return;
	}
	var oldWareHouseId = $('#outputWarehouseId').combobox('getValue');
	$('#outputWarehouseId').combobox('setValue',wareHouseId);
	$('#outputWarehouseId').combobox('disable');
	$('#showWarehouseMaterialName').searchbox('setValue','');
	
//	$('#selectWarehouseMaterialListTable').datagrid({url:''});
	$('#selectWarehouseMaterialListTable').datagrid('load','');
	if(oldWareHouseId == wareHouseId){
		var rows = $('#listTable').datagrid('getRows');
		var filterMaterialIds = [];
		$.each(rows, function(i,data){
			filterMaterialIds.push(data.materialId);
		});
		
		$('#selectWarehouseMaterialListTable').datagrid('reload',{warehouseId:wareHouseId,filterMaterialIds:filterMaterialIds.join(",")});
	}
	
	$('#selectWarehouseMaterialListTable').datagrid('load',basicUrl + prUrl + "searchWarehouseMaterialToPage.do?eventName="+$('#supplychianEventCode').val()+'&dafengModel='+$('#dafengModel').val());
	leftSilpFun('selectWarehouseMaterialWin',true,9001);
}
//function changeTabContent(eventName){
//	$.each($('.tabList', parent.document).find('li'),function(index,data){
//		if(data.dataset.eventname == eventName){
//			if(data.className == ''){
//				data.className = 'listSelected';
//				$('#tabContent_'+data.dataset.eventname, parent.document).css('display','block');
//			}
//		}else{
//			if(data.className == 'listSelected'){
//				data.className = '';
//				$('#tabContent_'+data.dataset.eventname, parent.document).css('display','none');
//			}
//		}
//	});
//	if($('.hompageBorder', parent.document)[0].className == 'hompageBorder fl listSelected'){
//		$('.hompageBorder', parent.document)[0].className = 'hompageBorder fl';
//		$('#tabContent_homePage', parent.document).css('display','none');
//	}
//}
//领用去向改变方法
function outputDestinationChange(newValue,oldValue){
	$('#outputDesSwineryId').combogrid('reset');
	$('#outputDesHouseId').combogrid('reset');
	$('#outputDesDeptId').combotree('reset');
	if(newValue == 'swinery'){
		$('#outputDestinationLabel').css('display','inline-block');
		$('#outputDestinationLabel').next().css('display','inline-block');
		$('#outputDestinationLabel').find('label').html('批次');
		$('#outputDesSwineryId').parent().css('display','inline-block');
		$('#outputDesHouseId').parent().css('display','none');
		$('#outputDesDeptId').parent().css('display','none');
		$('#outputDesSwineryId').combogrid('enable');
		$('#outputDesHouseId').combogrid('disable');
		$('#outputDesDeptId').combotree('disable');
	}else if(newValue == 'pigHouse'){
		$('#outputDestinationLabel').css('display','inline-block');
		$('#outputDestinationLabel').next().css('display','inline-block');
		$('#outputDestinationLabel').find('label').html('猪舍');
		$('#outputDesHouseId').parent().css('display','inline-block');
		$('#outputDesDeptId').parent().css('display','none');
		$('#outputDesSwineryId').parent().css('display','none');
		$('#outputDesSwineryId').combogrid('disable');
		$('#outputDesHouseId').combogrid('enable');
		$('#outputDesDeptId').combotree('disable');
	}else if(newValue == 'dept'){
		$('#outputDestinationLabel').css('display','inline-block');
		$('#outputDestinationLabel').next().css('display','inline-block');
		$('#outputDestinationLabel').find('label').html('部门');
		$('#outputDesDeptId').parent().css('display','inline-block');
		$('#outputDesSwineryId').parent().css('display','none');
		$('#outputDesHouseId').parent().css('display','none');
		$('#outputDesSwineryId').combogrid('disable');
		$('#outputDesHouseId').combogrid('disable');
		$('#outputDesDeptId').combotree('enable');
	}else{
		$('#outputDestinationLabel').css('display','none');
		$('#outputDestinationLabel').next().css('display','none');
		$('#outputDesSwineryId').parent().css('display','none');
		$('#outputDesHouseId').parent().css('display','none');
		$('#outputDesDeptId').parent().css('display','none');
		$('#outputDesSwineryId').combogrid('disable');
		$('#outputDesHouseId').combogrid('disable');
		$('#outputDesDeptId').combotree('disable');
	}
}
/**
 * 获取明细表提交数据数据（有一个明细表，如有多个明细表需重写）
 */
function collectDetailData(){
	var listTable = $('#listTable');
	var rows =  listTable.datagrid('getRows');
	for(var i = 0 ; i < rows.length ; i ++){
		listTable.datagrid('endEdit',i);
	}
	var queryParams;
	//明细表获取数据
	
	if(rows.length < 1){
		return null;
	}
	//获取新增行
	var insertRows = listTable.datagrid('getRows');
	
	var validData = [];
	var notSubmitIndex = '';
	$.each(insertRows,function(index,data){
		if(data.hasOwnProperty('outputQty') && data.outputQty != 0){
			validData.push(data);
		}else{
			if(notSubmitIndex.length==0){
				notSubmitIndex = index + 1;
			}else{
				notSubmitIndex = notSubmitIndex + ',' + (index + 1);
			}
		}
	})
	
	//给提交数据加上行号
	$.each(validData,function(index,data){
		validData[index].lineNumber = index+1;
	})
	var jsonString = JSON.stringify(validData);
	queryParams = {
			deletedFlag:0,
			gridList:jsonString,
			notSubmitIndex:notSubmitIndex
		};
	
	return queryParams;
}

/**
 * 保存
 * pms{
 * 	model:模块代码
 * }
 */
function onBtnSave(){
	var queryParams;
	var listTable = document.getElementById("listTable");
	
	var isValid = $('#editForm').form('validate');
	if (!isValid){
		$.messager.alert('提示','必填项没有填写！');
		$('#btnSave').attr('disabled',false).removeClass('btn-disabled');
		$.messager.progress('close');	
		return;
	}
	
	//判断是否有明细表
	if(listTable!= null){
		queryParams = collectDetailData();
		if(queryParams == null){
			$.messager.alert('提示','前台提示--未添加明细不能提交！');
			return;
		}
		
		if(queryParams.gridList == '[]'){
			$.messager.alert('提示','填写数量都为零！');
			return;
		}
	}else{
		queryParams = {
			status:1,
			deletedFlag:0,
		};
	}

	if(queryParams.notSubmitIndex!=''){
		$.messager.confirm('提示', '第【'+queryParams.notSubmitIndex+'】行的数量为零，不会提交，确定吗？', function(r){
			delete queryParams.notSubmitIndex;
			if(r){
				onbtnSaveSubmit(queryParams);
			}
		});
	}else{
		delete queryParams.notSubmitIndex;
		onbtnSaveSubmit(queryParams);
	}
}

function onbtnSaveSubmit(queryParams){
	$('#btnSave').attr('disabled',true).addClass('btn-disabled');
	$.messager.progress();
	$('#editForm').form('submit',{
		url:basicUrl+saveUrl+'?dafengModel='+$('#dafengModel').val(),
		queryParams:queryParams,
		success: function(response){
			response=eval('('+response+')');
			if(response.success){
				$('#editWin').window('close');
				$.messager.alert('提示','保存成功！');
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
			$('#btnSave').attr('disabled',false).removeClass('btn-disabled');
			$.messager.progress('close');
		}
	});
}