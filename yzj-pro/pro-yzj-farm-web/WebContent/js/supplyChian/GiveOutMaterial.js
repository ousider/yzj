var title = '仓管发料';
var editIndex = undefined;
var prUrl = '/supplyChian/';
var searchMainListUrl = prUrl + 'searchOutputUseToPage.do';
var searchDetailListUrl = prUrl + 'searchOutputUseDetailToList.do';
var saveUrl = prUrl + 'editGiveOutTruePrepared.do'
var giveOutTrueUrl = prUrl + 'editGiveOutTrue.do'
var giveOutFalseUrl = prUrl + 'editGiveOutFalse.do';
/** *********页面渲染开始*************************************************************************/

$(document).ready(function(){
	dafengModelConfirm();
	
	/**
	 * 主表加载
	 */
	
	$('#table').datagrid(
			j_grid_view({
				toolbarList:"#tableToolbarList",
				url:searchMainListUrl+'?dafengModel='+$('#dafengModel').val(),
				columnFields:'rowId,outputUseNumber,billDate,outputWarehouseName,statusName,userName,notes',
				columnTitles:'ID,领料号,领用时间,出库仓库,状态,领用人,备注',
				hiddenFields:'rowId',
				onDblClickRowFun:searchGiveOutMaterialDetail,
			},'table')
	);
	
	setTimeout(function(){
		$('#listTable').datagrid(
				j_detailGrid({
					toolbar:"#sRMLTToolbarList",
					width:'100%',
					height:'100%',
					dbClickEdit:true,
					checkOnSelect:true,
					columns:[[
				      	{field:'ck',checkbox:true},
				      	j_gridText({field:'materialName',title:'物料名称',width:100}),
				      	j_gridText({field:'materialFirstClassName',title:'大类',width:100}),
				      	j_gridText({field:'materialSecondClassName',title:'中类',width:100}),
				      	j_gridText({field:'supplierSortName',title:'供应商',width:100}),
				      	j_gridText({field:'manufacturer',title:'厂家',width:100}),
		              	j_gridText({field:'specAll',title:'规格',width:100}),
//		              	j_gridText({field:'planQtyUnit',title:'领用量',width:100}),
		              	j_gridNumber({field:'outputQty',title:'出库量',formatter:function(value,row){
	              			value = value == null || value == undefined || value == '' ? 0 : value;
	              			return value+row.unit;
		              	},min:0,precision:3,width:80}),
		              	j_gridText({field:'outputWarehouseName',title:'出库仓库',width:100}),
	              		j_gridText({field:'effectiveDateId',title:'生产/有效日期',
	                  		formatter:function(value,row){
	                  			return row.effectiveDate;
	                  		},
	                  		editor:	xnGridComboGrid({
	                  				field:'effectiveDateId',
	    	              			idField:'rowId',
	    	            			textField:'effectiveDate',
	    	            			columns:[[ 	
	    	            						{field:'rowId',title:'ID',width:100,hidden:true},
	    	            						{field:'inputDate',title:'入库日期',width:100},
	    	            						{field:'effectiveDate',title:'生产/有效日期',width:120},
	    	            						{field:'existsQtyName',title:'库存数量',width:100},
	    	            						{field:'purchaseOrFree',title:'赠料/购料',width:100},
	    	            						{field:'actualPriceName',title:'买入单价',width:100}
	    	            				    ]]
	    	              		}),width:100})
				    ]],
				    onBeginEdit:function(index,row){
				    	var effectiveDateId = $('#listTable').datagrid('getEditor', {
			                index: index,
			                field: 'effectiveDateId'
			            });
						if(effectiveDateId != null){
							var effectiveDateGrid = $(effectiveDateId.target).combogrid('grid');
							var rows = $('#listTable').datagrid('getRows');
							var filterIds = [];
//							$.each(rows, function(i,data){
//								if(data.hasOwnProperty("effectiveDateId") && data.effectiveDateId && i != index){
//									filterIds.push(data.effectiveDateId);
//								}
//							});
							effectiveDateGrid.datagrid('load',basicUrl+prUrl + 'searchGiveOutMaterialByMaterialIdToList.do?materialId='+row.materialId+"&warehouseId="+row.outputWarehouseId+'&filterIds='+filterIds+'&roleFilterFlag=true'+'&dafengModel='+$('#dafengModel').val());
						}
				    },
				    onEndEdit:function(index,row){
				    	var effectiveDateId = $('#listTable').datagrid('getEditor', {
			                index: index,
			                field: 'effectiveDateId'
			            });
				    	if(effectiveDateId != null){
				    		row.effectiveDate = $(effectiveDateId.target).combogrid('getText');
				    	}
				    }
			})
		);

//		$('#giveOutMaterialDetailTable').datagrid(
//				j_grid_view({
//					haveCheckBox:false,
//					toolbarList:"#sRMLTToolbarList",
//					columnFields:'materialName,supplierSortName,manufacturer,specAll,outputQtyUnit,effectiveDate',
//					columnTitles:'物料名称,供应商,厂家,规格,领用量,有效日期',
//					hiddenFields:'',
//					fit:false,
//					pagination:false,
//					height:'100%',
//					width:'100%'
//				}));
	},500);
});

function searchGiveOutMaterialDetail(index,row){
	// 备料中
	if(row.status=='1'){
		$('#sRMLTToolbarList').css('display','block');
		$('#btnSave').css('display','inline-block');
	}else{
		$('#sRMLTToolbarList').css('display','none');
		$('#btnSave').css('display','none');
	}
	$('#rowId').val(row.rowId);
	$('#listTable').datagrid('load',basicUrl+searchDetailListUrl+'?outputId='+row.rowId+'&dafengModel='+$('#dafengModel').val());
	leftSilpFun('giveOutMaterialDetailId');
}

function onBtnGiveOutTrue(){
	var record = $('#table').datagrid('getSelections');
	var length = record.length;
	if(length < 1){
		$.messager.alert('警告','请选择一条数据！');
	}else{
		$.messager.progress();	
		var errorFlag = '0';
		var ids = [];
		$.each(record,function(index,data){
			if(data.status == '1'){
				var dataIndex = $('#table').datagrid('getRowIndex',data)+1;
				$.messager.alert('错误','第'+dataIndex+'行还在【仓管备料中】，无法确认领料！');
				$.messager.progress('close');
				errorFlag = '1';
				return false;
			}
			ids.push(data.rowId);
		});
		
		if(errorFlag=='0'){
			jAjax.submit(giveOutTrueUrl,{ids:ids}
			,function(response){
				$('#table').datagrid('reload');
				rightSlipFun('giveOutMaterialDetailId',780);
				$.messager.alert('提示','领用成功!');
				refreshGiveOutMaterialDetail();
				$.messager.progress('close');	
			},function(response){
	    		jAjax.errorFunc(response.errorMsg);
	    		$.messager.progress('close');
	    	},null,true,null,null);
		}
	}
}

function onBtnGiveOutFalse(){
	var record = $('#table').datagrid('getSelections');
	var length = record.length;
	if(length < 1){
		$.messager.alert('警告','请选择一条数据！');
	}else{
//		$.messager.confirm('提示', '确定要删除这'+record.length+'条记录吗？', function(r){
//			if (r){
				$.messager.progress();	
				var ids = [];
				$.each(record,function(index,data){
					ids.push(data.rowId);
				});
				
				jAjax.submit(giveOutFalseUrl,{ids:ids}
				,function(response){
					$('#table').datagrid('reload');
					rightSlipFun('giveOutMaterialDetailId',780);
					$.messager.alert('提示','操作成功!');
					refreshGiveOutMaterialDetail();
					$.messager.progress('close');	
				},function(response){
		    		jAjax.errorFunc(response.errorMsg);
		    		$.messager.progress('close');
		    	},null,true,null,null);
//			}
//		});
		
	}
}

//单据拆分
function onBtnSplit(){
	var record = $('#listTable').datagrid('getSelections');
	var length = record.length;
	if(length < 1){
		$.messager.alert('警告','请选择一条数据！');
	}else if(length > 1){
		$.messager.alert('警告','只能选择一条数据！');
	}else{
		$('#splitMaterialWin').dialog({
			modal:true
		});
		$('#splitMaterialWin').dialog('open');
		$('#splitMaterialForm').form('reset');
		$('#outputQty').val(record[0].outputQty);
		$('#outputMinQty').val(record[0].outputMinQty);
	}
}
//取消拆分
function splitMaterialCancel(){
	$('#splitMaterialWin').dialog('close');
}
//确定拆分
function splitMaterialSure(){
	var isValid = $('#splitMaterialForm').form('validate');
	if(isValid){
		var outputQty = strToFloat($('#outputQty').val());
		var outputQtyNew = strToFloat($('#outputQtyNew').numberspinner('getValue'));
		if(outputQtyNew >= outputQty){
			$.messager.alert('警告','拆分后的需求量大于原需求量，请重新填写！');
		}else if(outputQtyNew <= 0){
			$.messager.alert('警告','拆分后的需求量不能小于等于零，请重新填写！');
		}else{
    		var outputQtyNewValue = new BigDecimal(outputQtyNew.toString());
    		var outputMinQty = $('#outputMinQty').val();
    		outputMinQty = outputMinQty == '' || outputMinQty == undefined ? new BigDecimal("1") : new BigDecimal(outputMinQty);
    		if(outputQtyNewValue.remainder(outputMinQty) != 0){
    			$.messager.alert('提示','拆分数量一定要是最小领用量：'+outputMinQty+'的整数倍,请重新输入！');
    		}else{
				//拆分方法
				var row = $('#listTable').datagrid('getSelections')[0];
				jAjax.submit('/supplyChian/editSplitMaterial.do?editType=giveOut',{rowId:row.detailRowId,splitQty:outputQtyNew}
				,function(response){
					$('#splitMaterialWin').dialog('close');
					$('#listTable').datagrid('reload');
					refreshGiveOutMaterialDetail();
					$.messager.progress('close');	
				},function(response){
		    		jAjax.errorFunc(response.errorMsg);
		    		$.messager.progress('close');
		    	},null,true,null,null);
    		}
		}
	}
}

//单据合并
function onBtnMerge(){
	var record = $('#listTable').datagrid('getSelections');
	var length = record.length;
	if(length != 2){
		$.messager.alert('警告','请选择两条数据！');
	}else{
		if(record[0].materialId != record[1].materialId){
			$.messager.alert('警告','选择的物料类型不一致，请重新选择！');
		}else{
			var firstData = record[0].detailRowId;
			var secondData = record[1].detailRowId;
			jAjax.submit('/supplyChian/editMergeMaterial.do?editType=giveOut',{firstData:firstData,secondData:secondData}
			,function(response){
				$('#listTable').datagrid('reload');
				refreshGiveOutMaterialDetail();
				$.messager.progress('close');	
			},function(response){
	    		jAjax.errorFunc(response.errorMsg);
	    		$.messager.progress('close');
	    	},null,true,null,null);
		}
	}
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
	//判断是否有明细表
	if(listTable!= null){
		queryParams = collectDetailData();
		if(queryParams == null){
			$.messager.alert('提示','未添加明细不能提交！');
			return;
		}
	}else{
		queryParams = {
			status:1,
			deletedFlag:0,
		};
	}
	//判断是否是事件保存
	if(typeof(eventName) != "undefined"){
		queryParams.eventName = eventName;
	}
	$('#btnSave').attr('disabled',true).addClass('btn-disabled');
	$.messager.progress();	
	$('#editForm').form('submit',{
		url:basicUrl+saveUrl,
		queryParams:queryParams,
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
				$('#editWin').window('close');
				$('#sRMLTToolbarList').css('display','none');
				$('#btnSave').css('display','none');
				$.messager.alert('提示','保存成功！');
				refreshGiveOutMaterialDetail();
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
/**
 * 取消
 * pms{
 * 	model:模块代码
 * }
 */
function onBtnCancel(){
	rightSlipFun('giveOutMaterialDetailId',780);
}

function giveOutMaterialRefresh(){
	$('#table').datagrid('reload');
	rightSlipFun('giveOutMaterialDetailId',780);
}

function refreshGiveOutMaterialDetail(){
	var giveOutMaterialDetailIframe = parent.document.getElementById("iframe_GiveOutMaterialDetail");
	if(giveOutMaterialDetailIframe){
		var giveOutMaterialDetailContentWindow = giveOutMaterialDetailIframe.contentWindow;
		giveOutMaterialDetailContentWindow.giveOutMaterialDetailRefresh();
	}
}