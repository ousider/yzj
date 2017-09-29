var prUrl='/production/';
var saveUrl=prUrl+'semen.do';
var editIndex = undefined;
var eventName = 'SEMEN_INQUIRY';
var oldPigType = 2;

//明细默认值仓库设置
var detailDefaultValues = {
	status:'1',
	deletedFlag:'0',
	houseId:'N',
};

$(document).ready(function(){
	//精液来源
	xnCdCombobox({
		id:'semenOrigin',
		editable:false,
		required:true,
		typeCode:'SEMEN_ORIGIN',
		excludeValue:['1'],
		onChange:semenOriginChange
	});
	
	// 销售单据
	xnComboGrid({
		id:'billId',
		idField:'billId',
		textField:'billCode',
		url:'/production/editSemenBill',
		width:500,
		disabled:true,
		editable:true,
		onSelect:billOnSelectfun,
		columns:[[
		          	{field:'rowId',title:'ID',width:100,hidden:true},
		          	{field:'billCode',title:'单据编号',width:60},
		          	{field:'billDate',title:'单据日期',width:60},
		          	{field:'supplierIdName',title:'供应商',width:100},
		          	{field:'saleNum',title:'数量',width:60}
		          ]]
	});
	var semenOrigin = $('#semenOrigin').combobox('getValue');
	
	$('#listTable').datagrid(
			j_detailGrid({
			dbClickEdit:true,
			columns:[[
			          	{field:'ck',checkbox:true},
		              	j_gridText({field:'rowId',title:'ID',hidden:true}),
		              	j_gridText({field:'pigId',title:'原场公猪Id',hidden:true}),
		              	j_gridText({field:'houseId',title:'猪舍Id',hidden:true}),
		              	j_gridText({field:'semenDate',title:'采精日期',hidden:true}),
		              	j_gridText({field:'deletedFlag',title:'deletedFlag',hidden:true}),
		              	//精液主数据
		              	j_gridText(materialGridComboGrid({title:'精液主数据',width:100})),
		              	j_gridText({field:'earBrand',title:'公猪耳号',width:100,editor:'textbox'}),
		              	//j_gridText({field:'breedAndDayAge',title:'品种-日龄',width:100,editor:'textbox'}),
		              //品库
		              	j_gridText({field:'breedId',title:'品种',
		              		formatter:function(value,row){
		              			return row.breedName;
		              		},
		              		editor:	xnGridComboGrid({
		              				field:'breedId',
			              			idField:'rowId',
			            			textField:'breedName',
			            			columns:[[ 	
			            						{field:'rowId',title:'ID',width:100,hidden:true},
			            						{field:'breedName',title:'品种',width:100},
			            				    ]]
			              		}),width:100}),
		            	j_gridText({field:'dayAge',title:'日龄',width:100,editor:'textbox'}),
		              	j_gridText({field:'semenBatchNo',title:'精液批号',width:100,editor:'textbox'}),
		              	j_gridNumber({field:'spermNum',title:'数量',width:80,min:1}),
		              	j_gridText({field:'validDate',title:'有效日期',width:80,editor:{type:'datebox',options:{editable:false}}}),
		            	j_gridText(supplierGridComboGrid({width:100})),
			            //仓库
		              	j_gridText({field:'warehouseId',title:'仓库',
		              		formatter:function(value,row){
		              			return row.warehouseIdName;
		              		},
		              		editor:	xnGridComboGrid({
		              				field:'warehouseId',
			              			idField:'rowId',
			            			textField:'warehouseName',
			            			columns:[[ 	
			            						{field:'rowId',title:'ID',width:100,hidden:true},
			            						{field:'warehouseName',title:'仓库名称',width:100},
			            				        {field:'warehouseAddress',title:'仓库地址',width:100}
			            				    ]]
			              		}),width:100}),

		              	j_gridText({field:'inputDate',title:'入库日期',width:100,editor:{
		    				type:'datebox',
		    				options:{editable:false}
		    			}

			              		
			              	
					              		
//					              	//品种
//					              		xnComboGrid({
//					              			id:'breedId',
//					              			idField:'rowId',
//					              			textField:'breedName',
//					              			url:'/backEnd/searchBreedToList.do',
//					              			width:550,
//					              			columns:[[ 	
//					              			           	{field:'rowId',title:'ID',width:100,hidden:true},
//					              				        {field:'breedCode',title:'品种代码',width:100},
//					              				        {field:'breedName',title:'品种名称',width:100},
//					              				        {field:'notes',title:'备注',width:100}
//					              				    ]]
//					              		});
		              	
		              	}),
		              	//技术员
		              	j_gridText(workerGridComboGrid({title:"负责人",width:80})),
		              	j_gridText({field:'notes',title:'备注',width:80,editor:'textbox'}),
				    ]],
		    onEndEdit:function(index,row){

		    },
		    onBeginEdit:function(index,row){

		    }
		})
	);

	
	$('#preSaveRecordTable').datagrid(
			j_grid_view({
				fitColumns:false,
				haveCheckBox:false,
				columnFields:'boarId,materialIdName,earBrand,breedName,dayAge,semenBatchNo,spermNum,warehouseIdName,validDate,inputDate,supplierIdName,workerName,notes',
				columnTitles:'采精公猪Id,精液主数据,公猪耳号,品种,日龄,精液批号,数量,仓库,有效日期,入库日期,供应商,负责人,备注',
				hiddenFields:'boarId,notes',
				columnWidths:'100,100,100,100,100,100,100,100,100,100,100,100,100',
				fit:false,
				width:'auto',
				height:'100%',
				pagination:false
			},'preSaveRecordTable')
	);
	
	//仓库设置
	jAjax.submit('/production/searchSpermWarehouseName.do',null
			,function(data){
				if(data.length == 1){
					detailDefaultValues.warehouseId = data[0].rowId;
					detailDefaultValues.warehouseIdName = data[0].warehouseName;
				}
			}
	,null,'GET',true);
});
/**
 * 寄养日期改变方法
 * @param newValue
 * @param oldValue
 */
function enterDateChangeFun(newValue,oldValue){
	if(oldValue == ''){
		return;
	}
	if(newValue != null && newValue != ''){
		var isDate = checkDate(newValue);
		if(isDate){
			var rows = $('#listTable').datagrid('getRows');
			rows[editIndex].boardSowId = '';
			rows[editIndex].boardSowEarBrand = '';
			changeTableDisplayValue('listTable',editIndex,[{
				field:'boardSowId',
				value:''
			}]);
			setTimeout(function () {
//				$('#listTable').datagrid('endEdit',editIndex);
//				$('#listTable').datagrid('updateRow',{
//					index:editIndex,
//					row:rows[editIndex]
//				});
				focusEditCell('listTable',editIndex,'inputDate');
//				$('#listTable').datagrid('editCell', {index:editIndex,field:'enterDate'});
//				var editor = $('#listTable').datagrid('getEditor', {index:editIndex,field:'enterDate'});
//				var eidtCellInput = $(editor.target[0].parentNode).find('.textbox-text.validatebox-text');
//				eidtCellInput[0].focus();
//				eidtCellInput.keydown(function(e){
//			    	editCellTabDownFun(e,$('#listTable'),editIndex,'enterDate');
//			    });
			 },100);
		}
	}
}
function billOnSelectfun(index,row){
	$('#listTable').datagrid('load',basicUrl+'/production/editSemenListByBillId?billId='+row.billId);
}
function semenOriginChange(newValue, oldValue){
	$('#listTable').datagrid('load','');
	$('#listTable').datagrid('loadData',{success:true,total: 0, rows: [] });
	$('#billId').combogrid('setValue','');
	var columns;
	var onBeginEdit;
	var onEndEdit;
	if(newValue == 2){
		 $("#addDetail").attr({"disabled":"disabled"});
		 $("#addDetail").css('background-color','rgb(220, 220, 220)');
		 $("#deleteDetail").attr({"disabled":"disabled"});
		 $("#deleteDetail").css('background-color','rgb(220, 220, 220)');
		 $("#clearDetail").attr({"disabled":"disabled"});
		 $("#clearDetail").css('background-color','rgb(220, 220, 220)');
		 $('#billId').combogrid('enable');
		// 平台内
		columns = [
		          	{field:'ck',checkbox:true},
	              	j_gridText({field:'rowId',title:'ID',hidden:true}),
	              	j_gridText({field:'pigId',title:'原场公猪Id',hidden:true}),
	              	j_gridText({field:'houseId',title:'猪舍Id',hidden:true}),
	              	j_gridText({field:'semenDate',title:'采精日期',hidden:true}),
	              	j_gridText({field:'deletedFlag',title:'deletedFlag',hidden:true}),
	              	//精液主数据
	              	j_gridText({field:'materialIdName',title:'精液主数据',width:100}),
	              	j_gridText({field:'earBrand',title:'公猪耳号',width:100}),
	              //品库
	              	j_gridText({field:'breedName',title:'品种',width:100}),
	              	j_gridText({field:'dayAge',title:'日龄',width:100}),
	              	j_gridText({field:'semenBatchNo',title:'精液批号',width:100}),
	              	j_gridText({field:'spermNum',title:'数量',width:80}),
	              	j_gridText({field:'validDate',title:'有效日期',width:80}),
	              	j_gridText({field:'supplierIdName',title:'供应商',width:100}),
	              	j_gridText({field:'saleNotes',title:'销售备注',width:100}),
		            //仓库
	              	j_gridText({field:'warehouseId',title:'仓库',
	              		formatter:function(value,row){
	              			return row.warehouseIdName;
	              		},
	              		editor:	xnGridComboGrid({
	              				field:'warehouseId',
		              			idField:'rowId',
		            			textField:'warehouseName',
		            			columns:[[ 	
		            						{field:'rowId',title:'ID',width:100,hidden:true},
		            						{field:'warehouseName',title:'仓库名称',width:100},
		            				        {field:'warehouseAddress',title:'仓库地址',width:100}
		            				    ]]
              		}),width:100}),
	              	j_gridText({field:'inputDate',title:'入库日期',width:100,editor:{
	    				type:'datebox',
	    				options:{editable:false}
	    			}
	              	}),
	              	//技术员
	              	j_gridText(workerGridComboGrid({title:"负责人",width:80})),
	              	j_gridText({field:'notes',title:'备注',width:80,editor:'textbox'}),
			    ];
		onBeginEdit = function(index,row){
			var warehouse = $('#listTable').datagrid('getEditor', {
                index: index,
                field: 'warehouseId'
            });
			if(warehouse != null){
				var warehouseGrid = $(warehouse.target).combogrid('grid');
//				warehouseGrid.datagrid('load',basicUrl+'/supplyChian/searchWarehouseToList.do');
				warehouseGrid.datagrid('load',basicUrl+'/production/searchSpermWarehouseName.do');
			}
			var worker = $('#listTable').datagrid('getEditor', {
		        index: index,
		        field: 'worker'
		    });
			if(worker != null){
				var workerGrid = $(worker.target).combogrid('grid');
				workerGrid.datagrid('load',basicUrl+'/UserManage/searchEmployeeByIdToList.do');
			}
	    };
		onEndEdit = function(index,row){
            var warehouse = $('#listTable').datagrid('getEditor', {
                index: index,
                field: 'warehouseId'
            });
	    	if(warehouse != null){
	    		row.warehouseIdName = $(warehouse.target).combogrid('getText');
	    	}
			var worker = $('#listTable').datagrid('getEditor', {
                index: index,
                field: 'worker'
            });
            if(worker != null){
            	 row.workerName = $(worker.target).combogrid('getText');
            }
	    };
	}else{
		 $("#addDetail").removeAttr("disabled");
		 $("#addDetail").css('background-color','rgb(113, 175, 76)');
		 $("#deleteDetail").removeAttr("disabled");
		 $("#deleteDetail").css('background-color','rgb(113, 175, 76)');
		 $("#clearDetail").removeAttr("disabled");
		 $("#clearDetail").css('background-color','rgb(113, 175, 76)');
		 $('#billId').combogrid('disable');
		// 平台外
		columns = [
		          	{field:'ck',checkbox:true},
	              	j_gridText({field:'rowId',title:'ID',hidden:true}),
	              	j_gridText({field:'pigId',title:'原场公猪Id',hidden:true}),
	              	j_gridText({field:'houseId',title:'猪舍Id',hidden:true}),
	              	j_gridText({field:'semenDate',title:'采精日期',hidden:true}),
	              	j_gridText({field:'deletedFlag',title:'deletedFlag',hidden:true}),
	              	//精液主数据
	              	j_gridText(materialGridComboGrid({title:'精液主数据',width:100})),
	              	j_gridText({field:'earBrand',title:'公猪耳号',width:100,editor:'textbox'}),
	              	//品库
	              	j_gridText({field:'breedName',title:'品种',width:100}),
	              	j_gridNumber({field:'dayAge',title:'日龄',width:100,editor:'textbox',min:0}),
	              	j_gridText({field:'semenBatchNo',title:'精液批号',width:100,editor:'textbox'}),
	              	j_gridNumber({field:'spermNum',title:'数量',width:80,min:1}),
	              	j_gridText({field:'validDate',title:'有效日期',width:80,editor:{
	    				type:'datebox',
	    				options:{editable:false}
	    			}}),
	            	j_gridText(supplierGridComboGrid({width:100})),
		            //仓库
	              	j_gridText({field:'warehouseId',title:'仓库',
	              		formatter:function(value,row){
	              			return row.warehouseIdName;
	              		},
	              		editor:	xnGridComboGrid({
	              				field:'warehouseId',
		              			idField:'rowId',
		            			textField:'warehouseName',
		            			columns:[[ 	
		            						{field:'rowId',title:'ID',width:100,hidden:true},
		            						{field:'warehouseName',title:'仓库名称',width:100},
		            				        {field:'warehouseAddress',title:'仓库地址',width:100}
		            				    ]]
             		}),width:100}),
	              	j_gridText({field:'inputDate',title:'入库日期',width:100,editor:{
	    				type:'datebox',
	    				options:{editable:false}
	    			}
	              	}),
	              	//技术员
	              	j_gridText(workerGridComboGrid({title:"负责人",width:80})),
	              	j_gridText({field:'notes',title:'备注',width:80,editor:'textbox'}),
			    ];
		onBeginEdit = function(index,row){
        	var material = $('#listTable').datagrid('getEditor', {
                index: index,
                field: 'materialId'
            });
        	var origin = $('#semenOrigin').combobox('getValue');
        	if(material != null){
        		var materialGrid = $(material.target).combogrid('grid');
        		materialGrid.datagrid('reload',basicUrl+'/material/searchMaterialToPage.do?materialType=Sperm');		
        	};
			var warehouse = $('#listTable').datagrid('getEditor', {
                index: index,
                field: 'warehouseId'
            });
			if(warehouse != null){
				var warehouseGrid = $(warehouse.target).combogrid('grid');
//				warehouseGrid.datagrid('load',basicUrl+'/supplyChian/searchWarehouseToList.do');
				warehouseGrid.datagrid('load',basicUrl+'/production/searchSpermWarehouseName.do');
			}
			var worker = $('#listTable').datagrid('getEditor', {
		        index: index,
		        field: 'worker'
		    });
			if(worker != null){
				var workerGrid = $(worker.target).combogrid('grid');
				workerGrid.datagrid('load',basicUrl+'/UserManage/searchEmployeeByIdToList.do');
			}
	    };
		onEndEdit = function(index,row){
            var material = $('#listTable').datagrid('getEditor', {
                index: index,
                field: 'materialId'
            });
            if(material != null){
            	row.materialIdName = $(material.target).combogrid('getText');
	    		var data = $(material.target).combogrid('grid').datagrid('getSelected');
	    		if(data){
					row.breedId = data.breedId;
					row.breedName = data.breedName;
	    		}
            };
            var supplier = $('#listTable').datagrid('getEditor', {
                index: index,
                field: 'supplierId'
            });
            if(supplier != null){
            	row.supplierIdName = $(supplier.target).combogrid('getText');
            };
            var warehouse = $('#listTable').datagrid('getEditor', {
                index: index,
                field: 'warehouseId'
            });
	    	if(warehouse != null){
	    		row.warehouseIdName = $(warehouse.target).combogrid('getText');
	    	}
	    	
	    	var breed = $('#listTable').datagrid('getEditor', {
                index: index,
                field: 'breedId'
            });
	    	if(breed != null){
	    		row.breedName = $(breed.target).combogrid('getText');
	    	}
			var worker = $('#listTable').datagrid('getEditor', {
                index: index,
                field: 'worker'
            });
            if(worker != null){
            	 row.workerName = $(worker.target).combogrid('getText');
            }
	    };
	}
	
	$('#listTable').datagrid(
			j_detailGrid({
			dbClickEdit:true,
			columns:[columns],
		    onEndEdit:onEndEdit,
		    onBeginEdit:onBeginEdit
		})
	);
}
/**
 * 新增明细
 */
function detailAdd(){
	var isValid = $('#addNum').numberspinner('isValid');
	if(isValid){
		var listTable = $('#listTable');
		var allRows = listTable.datagrid('getRows');
		var length = allRows.length;
		var num = parseInt($('#addNum').val(),10);
		if(length + num > 999){
			$.messager.alert('警告','明细表数据不能超过999行！');
		}else{
			if (endEditing()){
				var semenOrigin = $('#semenOrigin').combobox('getValue');
				if(semenOrigin == "" || semenOrigin == null){
					$.messager.alert('提示','请先选择精液来源！');
					return;
				}
				var rows = [];
				for(var i = 0 ; i < num ; i++){
					var defaultValues = new Object(); 
					if(typeof(detailDefaultValues) == "undefined"){
						defaultValues ={
							status:'1',
							deletedFlag:'0',
							houseId:'N',
						};
					}else{
						//复制detailDefaultValues
						for(var p in detailDefaultValues) { 
							var name=p;//属性名称 
							var value=detailDefaultValues[p];//属性对应的值 
							defaultValues[name]=detailDefaultValues[p]; 
						} 
					}
					var data = {success:true,total: length + num};
					if(length != 0){
						rows = allRows;
					}
					rows.push(defaultValues);
				}
				data.rows = rows;
				listTable.datagrid('loadData',data);
				editIndex = length;
				var fistEditField = getFistEditField(listTable);
				listTable.datagrid('editCell', {index:editIndex,field:fistEditField});
				var editor = listTable.datagrid('getEditor', {index:editIndex,field:fistEditField});
				var eidtCellInput = $(editor.target[0].parentNode).find('.textbox-text.validatebox-text');
				if(eidtCellInput.length == 0){
					$(editor.target)[0].focus();
					$(editor.target).keydown(function(e){
						editCellTabDownFun(e,listTable,editIndex,fistEditField);
				    });
				}else{
					eidtCellInput[0].focus();
					eidtCellInput.keydown(function(e){
						editCellTabDownFun(e,listTable,editIndex,fistEditField);
				    });
				}
			}
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
	var listTable = $('#listTable');
	var billId = $("#billId").combogrid('getValue');
	var rows =  listTable.datagrid('getRows');
	for(var i = 0 ; i < rows.length ; i ++){
		listTable.datagrid('endEdit',i);
	}
	//获取新增行
	var insertRows = listTable.datagrid('getRows');
	//给提交数据加上行号
	$.each(insertRows,function(index,data){
		insertRows[index].lineNumber = listTable.datagrid('getRowIndex',data)+1;
	})
	var jsonString = JSON.stringify(insertRows);
    queryParams = {
			status:1,
			deletedFlag:0,
			relateBillId:billId,
			gridList:jsonString
		};
	//判断是否是事件保存
	if(typeof(eventName) != "undefined"){
		queryParams.eventName = eventName;
		var saveValidFalg = true;
		var errorEarBrand = null;
		$.each(eval(queryParams.gridList),function(i,item){
			if(item.status == -1){
				saveValidFalg = false;
				errorEarBrand = item.earBrand;
				return false;
			}
		});
		if(!saveValidFalg){
			$.messager.alert('提示','前台提示--耳牌号：【'+errorEarBrand+'】没有对应的猪只数据，请重新选择！');
			return;
		}
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
				if(typeof(eventName) == "undefined"){
					$('#editWin').window('close');
					$.messager.alert('提示','保存成功！');
					// 销售单据
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
				var iframeSperm = parent.document.getElementById("iframe_Sperm");
				if(iframeSperm){
					var spermContentWindow = iframeSperm.contentWindow;
					spermContentWindow.tableReload();
				}
				xnComboGrid({
					id:'billId',
					idField:'billId',
					textField:'billCode',
					url:'/production/editSemenBill',
					width:500,
//					disabled:true,
//					editable:true,
					onSelect:billOnSelectfun,
					columns:[[
					          	{field:'rowId',title:'ID',width:100,hidden:true},
					          	{field:'billCode',title:'单据编号',width:60},
					          	{field:'billDate',title:'单据日期',width:60},
					          	{field:'supplierIdName',title:'供应商',width:100},
					          	{field:'saleNum',title:'数量',width:60}
					          ]]
				});
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
