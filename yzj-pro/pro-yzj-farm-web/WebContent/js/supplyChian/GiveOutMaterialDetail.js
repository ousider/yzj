var title = '仓管发料(明细显示)';
var editIndex = undefined;
var prUrl = '/supplyChian/';
var searchMainListUrl = prUrl + 'searchOutputUseDetailToList.do';
//var saveUrl = prUrl + 'editGiveOutTruePrepared.do'
var giveOutTrueUrl = prUrl + 'editGiveOutDetailTrue.do'
var giveOutFalseUrl = prUrl + 'editGiveOutDetailFalse.do';
/** *********页面渲染开始*************************************************************************/

$(document).ready(function(){
	dafengModelConfirm();
	
	/**
	 * 主表加载
	 */
	
	$('#listTable').datagrid(
			j_detailGrid({
				toolbar:"#tableToolbarList",
				width:'100%',
				height:'100%',
				url:searchMainListUrl+'?dafengModel='+$('#dafengModel').val(),
				dbClickEdit:true,
				checkOnSelect:true,
				columns:[[
			      	{field:'ck',checkbox:true},
			      	j_gridText({field:'outputUseNumber',title:'领料号',width:110}),
			      	j_gridText({field:'userName',title:'领用人',width:70}),
			      	j_gridText({field:'billDate',title:'时间',width:80}),
			      	j_gridText({field:'outputDestinationName',title:'去向',width:60}),
			      	j_gridText({field:'outputDeptName',title:'部门',width:60}),
			      	j_gridText({field:'outputHouseName',title:'猪舍',width:80}),
			      	j_gridText({field:'outputSwineryName',title:'批次',width:90}),
			      	j_gridText({field:'pigQty',title:'猪只数量',width:80}),
			      	j_gridText({field:'materialName',title:'物料名称',width:150}),
			      	j_gridText({field:'materialSecondClassName',title:'中类',width:60,sortable:false}),
			      	j_gridText({field:'specAll',title:'规格',width:100}),
			      	j_gridText({field:'supplierSortName',title:'供应商',width:100,hidden:true}),
			      	j_gridText({field:'manufacturer',title:'厂家',width:70}),
//	              	j_gridText({field:'planQtyUnit',title:'领用量',width:100}),
	              	j_gridNumber({field:'outputQty',title:'出库量',formatter:function(value,row){
              			value = value == null || value == undefined || value == '' ? 0 : value;
              			return value+row.unit;
	              	},min:0,precision:3,width:80}),
	              	j_gridText({field:'outputWarehouseName',title:'出库仓库',width:80}),
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
			    	var outputQty = $('#listTable').datagrid('getEditor', {
		                index: index,
		                field: 'outputQty'
		            });
			    	if(outputQty != null){
			    		if(row.status == '2'){
			    			$(outputQty.target).numberspinner('disable');
			    		}else{
			    			$(outputQty.target).numberspinner('enable');
			    		}
			    	}
			    	var effectiveDateId = $('#listTable').datagrid('getEditor', {
		                index: index,
		                field: 'effectiveDateId'
		            });
					if(effectiveDateId != null){
			    		if(row.status == '2'){
			    			$(effectiveDateId.target).combogrid('setText', row.effectiveDate);
			    			$(effectiveDateId.target).combogrid('disable');
			    		}else{
			    			$(effectiveDateId.target).combogrid('enable');
			    			var effectiveDateGrid = $(effectiveDateId.target).combogrid('grid');
							var rows = $('#listTable').datagrid('getRows');
							effectiveDateGrid.datagrid('load',basicUrl+prUrl + 'searchGiveOutMaterialByMaterialIdToList.do?materialId='+row.materialId+"&warehouseId="+row.outputWarehouseId+'&roleFilterFlag=true'+'&dafengModel='+$('#dafengModel').val());
			    		}
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
	
//	setTimeout(function(){

//	},500);
});

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
	//获取选中的行
	var insertRows = listTable.datagrid('getSelections');
	
	var validData = [];
	var notSubmitIndex = '';
	$.each(insertRows,function(index,data){
		if(data.hasOwnProperty('effectiveDateId') && data.effectiveDateId != ''){
			data.errorAlertLineNumber = index + 1;
			validData.push(data);
		}else{
			if(notSubmitIndex.length==0){
				notSubmitIndex = (listTable.datagrid('getRowIndex', data) + 1);
			}else{
				notSubmitIndex = notSubmitIndex + ',' + (listTable.datagrid('getRowIndex', data) + 1);
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

function onBtnGiveOutDetailTrue(){
	var record = $('#listTable').datagrid('getSelections');
	var length = record.length;
	if(length < 1){
		$.messager.alert('警告','请选择一条数据！');
	}else{
		var queryParams;
		var listTable = document.getElementById("listTable");
		//判断是否有明细表
		if(listTable!= null){
			queryParams = collectDetailData();
			if(queryParams == null){
				$.messager.alert('提示','前台提示--未添加明细不能提交！');
				return;
			}
			
			if(queryParams.gridList == '[]'){
				$.messager.alert('提示','选中行全部没有选择生产/有效日期！');
				return;
			}
		}else{
			queryParams = {
				status:1,
				deletedFlag:0,
			};
		}
	
		if(queryParams.notSubmitIndex!=''){
			$.messager.confirm('提示', '第【'+queryParams.notSubmitIndex+'】行的没有选择生产/有效日期，不会提交，确定吗？', function(r){
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
}

function onbtnSaveSubmit(queryParams){
	$('#trueBtn').attr('disabled',true).addClass('btn-disabled');
	$.messager.progress();	
	jAjax.submit(giveOutTrueUrl,queryParams
	,function(response){
		$('#listTable').datagrid('reload');
		$.messager.alert('提示','确认领用成功!');
		refreshGiveOutMaterial();
		$('#trueBtn').attr('disabled',false).removeClass('btn-disabled');
		$.messager.progress('close');	
	},function(response){
		jAjax.errorFunc(response.errorMsg);
		$('#trueBtn').attr('disabled',false).removeClass('btn-disabled');
		$.messager.progress('close');
	},null,true,null,null);
}

function onBtnGiveOutDetailFalse(){
	var record = $('#listTable').datagrid('getSelections');
	var length = record.length;
	if(length < 1){
		$.messager.alert('警告','请选择一条数据！');
	}else{
		$('#falseBtn').attr('disabled',true).addClass('btn-disabled');
		$.messager.progress();	
		var jsonString = JSON.stringify(record);
		var queryParams = {
				deletedFlag:0,
				gridList:jsonString
		}
		jAjax.submit(giveOutFalseUrl,queryParams
		,function(response){
			$('#listTable').datagrid('reload');
			$.messager.alert('提示','确认未领用成功!');
			refreshGiveOutMaterial();
			$('#falseBtn').attr('disabled',false).removeClass('btn-disabled');
			$.messager.progress('close');	
		},function(response){
    		jAjax.errorFunc(response.errorMsg);
    		$('#falseBtn').attr('disabled',false).removeClass('btn-disabled');
    		$.messager.progress('close');
    	},null,true,null,null);
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
    			$('#splitTrueBtn').attr('disabled',true).addClass('btn-disabled');
    			$.messager.progress();
				//拆分方法
				var row = $('#listTable').datagrid('getSelections')[0];
				jAjax.submit('/supplyChian/editSplitMaterial.do?editType=giveOut',{rowId:row.detailRowId,splitQty:outputQtyNew}
				,function(response){
					$('#splitMaterialWin').dialog('close');
					$('#listTable').datagrid('reload');
					refreshGiveOutMaterial();
					$('#splitTrueBtn').attr('disabled',false).removeClass('btn-disabled');
					$.messager.progress('close');	
				},function(response){
		    		jAjax.errorFunc(response.errorMsg);
		    		$('#splitTrueBtn').attr('disabled',false).removeClass('btn-disabled');
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
			$('#mergeBtn').attr('disabled',true).addClass('btn-disabled');
			$.messager.progress();
			var firstData = record[0].detailRowId;
			var secondData = record[1].detailRowId;
			jAjax.submit('/supplyChian/editMergeMaterial.do?editType=giveOut',{firstData:firstData,secondData:secondData}
			,function(response){
				$('#listTable').datagrid('reload');
				refreshGiveOutMaterial();
				$('#mergeBtn').attr('disabled',false).removeClass('btn-disabled');
				$.messager.progress('close');	
			},function(response){
	    		jAjax.errorFunc(response.errorMsg);
	    		$('#mergeBtn').attr('disabled',false).removeClass('btn-disabled');
	    		$.messager.progress('close');
	    	},null,true,null,null);
		}
	}
}

function giveOutMaterialDetailRefresh(){
	$('#listTable').datagrid('reload');
}

function refreshGiveOutMaterial(){
	var giveOutMaterialIframe = parent.document.getElementById("iframe_GiveOutMaterial");
	if(giveOutMaterialIframe){
		var giveOutMaterialContentWindow = giveOutMaterialIframe.contentWindow;
		giveOutMaterialContentWindow.giveOutMaterialRefresh();
	}
}