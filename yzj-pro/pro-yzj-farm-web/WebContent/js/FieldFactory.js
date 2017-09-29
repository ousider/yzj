/**
 * 批次comboGird
 * @param pms
 * @returns
 */
function swineryComboGrid(pms){
	var result = xnComboGrid({
		id:pms.id == null ? 'swineryId' : pms.id,
		idField:pms.idField == null ? 'rowId' : pms.idField,
		textField:pms.textField == null ? 'swineryName' : pms.textField,
		width:pms.width == null ? 550 :pms.width,
		required:pms.required == null ? false : pms.required,
		url:pms.url == null ? null :pms.url,
		columns:pms.columns == null ? [[ 	
		           	{field:'rowId',title:'ID',width:100,hidden:true},
			        {field:'businessCode',title:'批次代码',width:100},
			        {field:'swineryName',title:'批次名称',width:100},
			        {field:'pigQty',title:'猪只数量',width:100},
			        {field:'notes',title:'备注',width:100}
			    ]] : pms.columns
			});
	return result;
}
/**
 * 猪舍comboGird
 * @param pms
 * @returns
 */
function houseComboGrid(pms){
	var result = xnComboGrid({
		id:pms.id == null ? 'houseId' : pms.id,
		idField:pms.idField == null ? 'rowId' : pms.idField,
		textField:pms.textField == null ? 'houseName' : pms.textField,
		url:pms.url == null ? null :pms.url,
		width:pms.width == null ? 550 :pms.width,
		required:pms.required == null ? false : pms.required,
		columns:pms.columns == null ? [[ 	
                	{field:'rowId',title:'ID',width:100,hidden:true},
			        {field:'businessCode',title:'猪舍代码',width:100},
			        {field:'houseName',title:'猪舍名称',width:100},
			        {field:'houseVolume',title:'猪舍容量',width:100},
			        {field:'pigQty',title:'猪只数量',width:100},
			        {field:'houseTypeName',title:'猪舍类别',width:100},
			        {field:'notes',title:'备注',fitColumns:true}
			    ]] : pms.columns
			});
	return result;
}
/**
 * 猪只状态ComboGrid
 * @param pms
 * @returns
 */
function pigClassComboGrid(pms){
	var result = xnComboGrid({
		id:pms.id == null ? 'pigClass' : pms.id,
		idField:pms.idField == null ? 'rowId' : pms.idField,
		textField:pms.textField == null ? 'pigClassName' : pms.textField,
		width:pms.width == null ? 550 :pms.width,
		url:pms.url,
		required:pms.required == null ? false : pms.required,
		columns:pms.columns == null ? [[ 	
                	{field:'rowId',title:'ID',width:100,hidden:true},
			        {field:'businessCode',title:'状态代码',width:100},
			        {field:'pigClassName',title:'状态名称',width:100},
			        {field:'notes',title:'备注',width:100}
			    ]] : pms.columns
			});
	return result;
}
/**
 * 供应商ComboGrid
 * @param pms
 * @returns
 */
function supplierComboGrid(pms){
	var result = xnComboGrid({
		id:pms.id == null ? 'supplierId' : pms.id,
		idField:pms.idField == null ? 'rowId' : pms.idField,
		textField:pms.textField == null ? 'companyName' : pms.textField,
		width:pms.width == null ? 550 :pms.width,
		url:pms.url == null ?  '/basicInfo/searchCustomerAndSupplierToList.do?cussupType=S' :pms.url,
		required:pms.required == null ? false : pms.required,
		columns:pms.columns == null ? [[ 	
                	{field:'rowId',title:'ID',width:100,hidden:true},
			        {field:'companyCode',title:'供应商编码',width:100},
			        {field:'companyName',title:'供应商名称',width:100},
			        {field:'companyNameEn',title:'英文名称',width:100}
			    ]] : pms.columns
			});
	return result;
}
/**
 * 客户ComboGrid
 * @param pms
 * @returns
 */
function customerComboGrid(pms){
	var result = xnComboGrid({
		id:pms.id == null ? 'customerId' : pms.id,
		idField:pms.idField == null ? 'rowId' : pms.idField,
		textField:pms.textField == null ? 'companyName' : pms.textField,
		width:pms.width == null ? 550 :pms.width,
		editable:true,
		url:pms.url == null ?  '/basicInfo/searchCustomerAndSupplierToList.do?cussupType=C' :pms.url,
		required:pms.required == null ? false : pms.required,
		columns:pms.columns == null ? [[ 	
                	{field:'rowId',title:'ID',width:100,hidden:true},
			        {field:'companyCode',title:'客户编码',width:100},
			        {field:'companyName',title:'客户名称',width:100},
			        {field:'companyNameEn',title:'英文名称',width:100}
			    ]] : pms.columns
			});
	return result;
}
/**
 * 物料名称comboGird
 * @param psm
 * @returns
 */
function materialComboGrid(pms){
	var result = xnComboGrid({
		id:pms.id == null ? 'materialId' : pms.id,
		idField:pms.idField == null ? 'rowId' : pms.idField,
		textField:pms.textField == null ? 'materialName' : pms.textField,
		width:pms.width == null ? 550 :pms.width,
		url:pms.url,
		required:pms.required == null ? false : pms.required,
		columns:pms.columns == null ? [[ 	
                	{field:'rowId',title:'ID',width:100,hidden:true},
			        {field:'businessCode',title:'物料代码',width:100},
			        {field:'materialName',title:'物料名称',width:100},
			        {field:'materialTypeName',title:'物料类型',width:100},
			        {field:'notes',title:'备注',width:100}
			    ]] : pms.columns
			});
	return result;
}
/**
 * 物料grid编辑
 * @param pms
 * @returns
 */
function materialGridComboGrid(pms){
	var result = {
		width:pms.width == null ? null : pms.width,
		field:pms.field == null ? 'materialId' : pms.field,
		title:pms.title == null ? '物料名称' : pms.title,
        formatter:pms.formatter == null ? function(value,row){
           return row.materialIdName;  
		} : pms.formatter,
		editor:
			xnGridComboGrid({
				field:pms.field == null ? 'materialId' : pms.field,
				idField:pms.idField == null ? 'rowId' : pms.idField,
				textField:pms.textField == null ? 'materialName' : pms.textField,
				width:pms.combogridWidth == null ? 550 : pms.combogridWidth,
				url:pms.url,
				columns:pms.columns == null ? [[ 	
							{field:'rowId',title:'ID',width:100,hidden:true},
							{field:'businessCode',title:'物料代码',width:100},
							{field:'materialName',title:'物料名称',width:100},
							{field:'materialTypeName',title:'物料类型',width:100},
							{field:'notes',title:'备注',width:100}
					    ]] : pms.columns
  			})
      	}	
	return result;
}
/**
 * 猪舍grid编辑
 * @param pms
 * @returns {___anonymous4549_5528}
 */
function houseGridComboGrid(pms){
	var result = {
			width:pms.width == null ? null : pms.width,
			field:pms.field == null ? 'houseId' : pms.field,
			title:pms.title == null ? '猪舍名称' : pms.title,
			pable:pms.pable == null ? true : pms.pable,
			plinkField:pms.plinkField,
			pfunction:pms.pfunction,
	        formatter:pms.formatter == null ? function(value,row){
	           return row.houseIdName;  
			} : pms.formatter,
			editor:
				xnGridComboGrid({
					field:pms.field == null ? 'houseId' : pms.field,
					idField:pms.idField == null ? 'rowId' : pms.idField,
					textField:pms.textField == null ? 'houseName' : pms.textField,
					width:pms.combogridWidth == null ? 550 : pms.combogridWidth,
					url:pms.url == null ? null : pms.url,
					editable:true,
					onChange:pms.onChange == null ? function(newValue,oldValue){
						return;
						}:pms.onChange,
					onSelect:pms.onSelect == null ? function(index,row){
					return;
					}:pms.onSelect,
					columns:pms.columns == null ? [[ 	
								{field:'rowId',title:'ID',width:100,hidden:true},
						        {field:'businessCode',title:'猪舍代码',width:100},
						        {field:'houseName',title:'猪舍名称',width:100},
						        {field:'houseVolume',title:'猪舍容量',width:100},
						        {field:'pigQty',title:'猪只数量',width:100},
						        {field:'houseTypeName',title:'猪舍类别',width:100},
						        {field:'notes',title:'备注',fitColumns:true}
						    ]] : pms.columns
	  			})
	      	}	
		return result;
}
/**
 * 猪栏grid编辑
 * @param pms
 * @returns {___anonymous4549_5528}
 */
function pigpenGridComboGrid(pms){
	var result = {
			width:pms.width == null ? null : pms.width,
			field:pms.field == null ? 'pigpen' : pms.field,
			title:pms.title == null ? '猪栏' : pms.title,
	        formatter:pms.formatter == null ? function(value,row){
	           return row.pigpenName;  
			} : pms.formatter,
			editor:
				xnGridComboGrid({
					editable:pms.editable == null?true:pms.editable,
					field:pms.field == null ? 'pigpen' : pms.field,
					idField:pms.idField == null ? 'rowId' : pms.idField,
					textField:pms.textField == null ? 'pigpenName' : pms.textField,
					width:pms.combogridWidth == null ? 550 : pms.combogridWidth,
					url:pms.url == null ? null : pms.url,
					multiple:pms.multiple == null ? null : pms.multiple,
					onChange:pms.onChange == null ? function(newValue,oldValue){
						return;
						}:pms.onChange,
					columns:pms.columns == null ? [[ 	
								{field:'rowId',title:'ID',width:100,hidden:true},
						        {field:'businessCode',title:'猪栏代码',width:100},
						        {field:'pigpenName',title:'猪栏名称',width:100},
						        {field:'length',title:'长度',width:100},
						        {field:'width',title:'宽度',width:100},
						        {field:'pigNum',title:'猪只容量',width:100},
						        {field:'pigQty',title:'已有猪只数',width:100},
						        {field:'notes',title:'备注',fitColumns:true}
						    ]] : pms.columns
	  			})
	      	}	
		return result;
}
/**
 * 批次grid编辑
 * @param pms
 * @returns {___anonymous4549_5528}
 */
function swineryGridComboGrid(pms){
	var field = pms.field == null ? 'swineryId' : pms.field;
	var result = {
			width:pms.width == null ? null : pms.width,
			field:field,
			title:pms.title == null ? '批次名称' : pms.title,
		    hidden:pms.hidden == null ? null : pms.hidden,
		    pable:pms.pable == null ? true : pms.pable,
		    plinkField:pms.plinkField,
		    pfunction:pms.pfunction,
	        formatter:pms.formatter == null ? function(value,row){
	           return row[field + 'Name'];  
			} : pms.formatter,
			editor:
				xnGridComboGrid({
					field:pms.field == null ? 'swineryId' : pms.field,
					idField:pms.idField == null ? 'rowId' : pms.idField,
					textField:pms.textField == null ? 'swineryName' : pms.textField,
					width:pms.combogridWidth == null ? 550 : pms.combogridWidth,
					url:pms.url == null ? null : pms.url,
					onChange:pms.onChange == null ? function(newValue,oldValue){
						return;
						}:pms.onChange,
					onSelect:pms.onSelect == null ? function(index,row){
					return;
					}:pms.onSelect,
					columns:pms.columns == null ? [[ 	
								{field:'rowId',title:'ID',width:100,hidden:true},
						        {field:'businessCode',title:'批次代码',width:100},
						        {field:'swineryName',title:'批次名称',width:100},
						        {field:'pigQty',title:'猪只数量',width:100},
						        {field:'notes',title:'备注',width:100}
						    ]] : pms.columns
	  			})
	      	}	
		return result;
}
/**
 * 猪只状态grid编辑
 * @param pms
 * @returns {___anonymous5759_6817}
 */
function pigClassGridComboGrid(pms){
	var result = {
			width:pms.width == null ? null : pms.width,
			field:pms.field == null ? 'pigClass' : pms.field,
			title:pms.title == null ? '猪只状态' : pms.title,
	        formatter:pms.formatter == null ? function(value,row){
	           return row.pigClassName;  
			} : pms.formatter,
			editor:
				xnGridComboGrid({
					field:pms.field == null ? 'pigClass' : pms.field,
					idField:pms.idField == null ? 'rowId' : pms.idField,
					textField:pms.textField == null ? 'pigClassName' : pms.textField,
					width:pms.combogridWidth == null ? 550 : pms.combogridWidth,
					url:pms.url,
					columns:pms.columns == null ? [[ 	
								{field:'rowId',title:'ID',width:100,hidden:true},
						        {field:'businessCode',title:'状态代码',width:100},
						        {field:'pigClassName',title:'状态名称',width:100},
						        {field:'notes',title:'备注',width:100}
						    ]] : pms.columns
	  			})
	      	}	
		return result;
}
/**
 * 人员grid编辑
 * @param pms
 * @returns {___anonymous5759_6817}
 */
function workerGridComboGrid(pms){
	var result = {
			width:pms.width == null ? 50 : pms.width,
			field:pms.field == null ? 'worker' : pms.field,
			title:pms.title == null ? '技术员' : pms.title,
	        formatter:pms.formatter == null ? function(value,row){
	           return row.workerName;  
			} : pms.formatter,
			editor:
				xnGridComboGrid({
					field:pms.field == null ? 'worker' : pms.field,
					idField:pms.idField == null ? 'rowId' : pms.idField,
					textField:pms.textField == null ? 'employeeName' : pms.textField,
					width:pms.combogridWidth == null ? 550 : pms.combogridWidth,
					url: pms.url,
					columns:pms.columns == null ? [[ 	
								{field:'rowId',title:'ID',width:100,hidden:true},
								{field:'employeeName',title:'人员名称',width:100},
						        {field:'employeeCode',title:'人员编码',width:100}
						    ]] : pms.columns
	  			})
	      	}	
		return result;
}
/**
 * 供应商grid编辑
 * @param pms
 * @returns {___anonymous5759_6817}
 */
function supplierGridComboGrid(pms){
	var result = {
			width:pms.width == null ? null : pms.width,
			field:pms.field == null ? 'supplierId' : pms.field,
			title:pms.title == null ? '供应商' : pms.title,
	        formatter:pms.formatter == null ? function(value,row){
	           return row.supplierIdName;  
			} : pms.formatter,
			editor:
				xnGridComboGrid({
					field:pms.field == null ? 'supplierId' : pms.field,
					idField:pms.idField == null ? 'rowId' : pms.idField,
					textField:pms.textField == null ? 'companyName' : pms.textField,
					editable:pms.editable == null ? true : pms.editable,
					disabled:pms.disabled == null?false:pms.disabled,
					width:pms.combogridWidth == null ? 550 : pms.combogridWidth,
					url:pms.url == null ? '/basicInfo/searchCustomerAndSupplierToList.do?cussupType=S': pms.url,
					columns:pms.columns == null ? [[ 	
								{field:'rowId',title:'ID',width:100,hidden:true},
						        {field:'companyCode',title:'客户编码',width:100},
						        {field:'companyName',title:'客户名称',width:100},
						        {field:'companyNameEn',title:'英文名称',width:100}
						    ]] : pms.columns
	  			})
	      	}	
		return result;
}
/**
 * 客户grid编辑
 * @param pms
 * @returns {___anonymous5759_6817}
 */
function customerGridComboGrid(pms){
	var result = {
			width:pms.width == null ? null : pms.width,
			field:pms.field == null ? 'customerId' : pms.field,
			title:pms.title == null ? '客户' : pms.title,
	        formatter:pms.formatter == null ? function(value,row){
	           return row.customerName;  
			} : pms.formatter,
			editor:
				xnGridComboGrid({
					field:pms.field == null ? 'customerId' : pms.field,
					idField:pms.idField == null ? 'rowId' : pms.idField,
					textField:pms.textField == null ? 'companyName' : pms.textField,
					width:pms.combogridWidth == null ? 550 : pms.combogridWidth,
					url:pms.url == null ? '/basicInfo/searchCustomerAndSupplierToList.do?cussupType=S': pms.url,
					columns:pms.columns == null ? [[ 	
								{field:'rowId',title:'ID',width:100,hidden:true},
						        {field:'companyCode',title:'客户编码',width:100},
						        {field:'companyName',title:'客户名称',width:100},
						        {field:'companyNameEn',title:'英文名称',width:100}
						    ]] : pms.columns
	  			})
	      	}	
		return result;
}
/**
 * 耳牌号gird编辑
 * @param pms
 * @returns {___anonymous6847_6856}
 */
function earBrandGridComboGrid(pms){
	var result = {
			width:pms.width == null ? 100 : pms.width,
			field:pms.field == null ? 'earBrand' : pms.field,
			title:pms.title == null ? '耳牌号' : pms.title,
			editor:{
				type:'combogrid',
				options:{
					panelWidth:pms.combogridWidth == null ? 500 : pms.combogridWidth,
					idField:pms.idField == null ? 'earBrand' : pms.idField,
					textField:pms.textField == null ? 'earBrand' : pms.idField,
					required:pms.required == null ? false : pms.required,
					validType:'numOrLetterOr_',
					rowStyler: function(index,row){
						if ((index+1) % 2 == 0){
							return 'background-color:#f7f7f7;';
						}
					},
					columns:pms.columns == null ? [[
								{field:'rowId',title:'ID',width:100,hidden:true},
								{field:'houseId',title:'HID',width:100,hidden:true},
								{field:'earBrand',title:'耳牌号',width:200},
								{field:'breedName',title:'品种',width:80},
								{field:'swineryName',title:'批次名称',width:100},
								{field:'houseName',title:'猪舍名称',width:100},
								{field:'pigTypeName',title:'猪只类别',width:100},
								{field:'pigpenName',title:'猪栏名称',width:100},
								{field:'pigClassName',title:'生产状态',width:100}  
	                       ]] : pms.columns,
				    fitColumns: true,
				    rownumbers:true,
				    onChange:pms.onChange == null ? function(newValue,oldValue){
				    	if(editIndex == undefined){
				    		return;
				    	}
				    	var hasSelectRows = $('#listTable').datagrid('getData').rows;
			        	var pigIdsArray = [];
			        	$.each(hasSelectRows,function(i,data){
			        		if(data.pigId != null && data.pigId != '' && i != editIndex){
			        			pigIdsArray.push(data.pigId);
			        		}
			        	});
			        	var pigIds = '';
			        	$.each(pigIdsArray,function(i,data){
			        		if(i == pigIdsArray.length - 1){
			        			pigIds += data;
			        		}else{
			        			pigIds += data + ',';
			        		}
			        	});
				    	var earBrandGrid = $(this).combogrid('grid');
				    	var earBrandCombogrid = $(this);
				    	var newData = [];
				    	$.ajax({  
				            url : basicUrl+'/production/searchValidPigToPage.do', 
				            data:{
				            	queryType:1,
				            	query:newValue,
				            	pigType:oldPigType,
				            	specifyPigs:0,
				            	choice:1,
				            	eventName:eventName,
				            	pigIds:pigIds
				            },
				            async : false, 
				            type : "POST",  
				            dataType : "json",  
				            success : function(response) {
				            	if(response.success){
				            		newData = response.rows;
				            		if(!response.searchSuccess){
				            			earBrandCombogrid.combogrid('hidePanel');
				            			 $.messager.alert({
					                         title: '错误',
					                         msg: response.errorMsg
					                     });
				            		}
				            	}else{
				            		 $.messager.alert({
				                         title: '错误',
				                         msg: response.errorMsg
				                     });
				            	}
				            }  
				        }); 
				    	earBrandGrid.datagrid('loadData',newData)
					}:pms.onChange,
					onSelect:pms.onSelect == null ? function(index,row){
						if(editIndex == undefined){
							return;
						}
						var rows = $('#listTable').datagrid('getRows');
						rows[editIndex].pigId = row.pigId;
						rows[editIndex].earBrand = row.earBrand;
						rows[editIndex].earshort = row.earshort;
						rows[editIndex].pigInfo = row.pigInfo;
						rows[editIndex].houseName = row.houseName;
						rows[editIndex].pigpenName = row.pigpenName;
						rows[editIndex].minValidDate = row.minValidDate;
						rows[editIndex].maxValidDate = row.maxValidDate;
						rows[editIndex].enterDate = row.lastEventDate;
						rows[editIndex].houseId = row.houseId;
						rows[editIndex].pigQty = row.pigQty;
						rows[editIndex].sex = row.sex;
						
						var changeValue = [{
							field:'pigId',
							value:rows[editIndex].pigId
						},{
							field:'pigInfo',
							value:rows[editIndex].pigInfo
						},{
							field:'houseName',
							value:rows[editIndex].houseName
						},{
							field:'pigpenName',
							value:rows[editIndex].pigpenName
						},{
							field:'earshort',
							value:rows[editIndex].earshort
						},{
							field:'minValidDate',
							value:rows[editIndex].minValidDate
						},{
							field:'maxValidDate',
							value:rows[editIndex].maxValidDate
						},{
							field:'enterDate',
							value:rows[editIndex].enterDate
						},{
							field:'pigQty',
							value:rows[editIndex].pigQty
						},{
							field:'sex',
							value:rows[editIndex].sex
						}];
						
						if(eventName == 'WEAN' || eventName == 'CHILD_PIG_DIE' || eventName == 'FOSTER'){
							rows[editIndex].pigQty = row.pigQty;
							if(eventName == 'WEAN' && (dnflag == 'ON' || dnflag == 'on')){
								rows[editIndex].weanNum = row.pigQty;
								rows[editIndex].dieNum = 0;
								var weanNumSum = 0,dieNumSum = 0;
								$.each(rows,function(i,item){
									weanNumSum += strToInt(item.weanNum);
									dieNumSum += strToInt(item.dieNum);
								});
								$('#weanNumSum').html(weanNumSum);
								$('#dieNumSum').html(dieNumSum);
								changeValue.push({
									field:'dieNum',
									value:rows[editIndex].dieNum
								},{
									field:'weanNum',
									value:rows[editIndex].weanNum
								});
							}
						}
						changeTableDisplayValue('listTable',editIndex,changeValue);
						setTimeout(function () {
							//$('#listTable').datagrid('endEdit',editIndex);
//							$('#listTable').datagrid('updateRow',{
//								index:editIndex,
//								row:rows[editIndex]
//							});
							var field = pms.field == null ? 'earBrand' : pms.field;
							focusEditCell('listTable',editIndex,field);
//							$('#listTable').datagrid('editCell', {index:editIndex,field:field});
//							var editor = $('#listTable').datagrid('getEditor', {index:editIndex,field:field});
//							if(editor != undefined){
//								var eidtCellInput = $(editor.target[0].parentNode).find('.textbox-text.validatebox-text');
//								eidtCellInput[0].focus();
//								eidtCellInput.keydown(function(e){
//							    	editCellTabDownFun(e,$('#listTable'),editIndex,field);
//							    });
//							}
						 },100);
					}:pms.onSelect,
					onShowPanel:function(){
						var hasSelectRows = $('#listTable').datagrid('getData').rows;
			        	var pigIdsArray = [];
			        	$.each(hasSelectRows,function(i,data){
			        		if(data.pigId != null && data.pigId != '' && i != editIndex){
			        			pigIdsArray.push(data.pigId);
			        		}
			        	});
			        	var pigIds = '';
			        	$.each(pigIdsArray,function(i,data){
			        		if(i == pigIdsArray.length - 1){
			        			pigIds += data;
			        		}else{
			        			pigIds += data + ',';
			        		}
			        	});
				    	var earBrandGrid = $(this).combogrid('grid');
				    	var data = earBrandGrid.datagrid('getData');
				    	if(data.rows.length > 0){
				    		return;
				    	}
				    	/*var newData = [];
				    	$.ajax({  
				            url : basicUrl+'/production/searchValidPigToPage.do', 
				            data:{
				            	queryType:1,
				            	pigType:oldPigType,
				            	specifyPigs:0,
				            	choice:1,
				            	eventName:eventName,
				            	pigIds:pigIds
				            },
				            async : false, 
				            type : "POST",  
				            dataType : "json",  
				            success : function(response) {
				            	if(response.success){
				            		newData = response.rows;
				            	}else{
				            		 $.messager.alert({
				                         title: '错误',
				                         msg: response.errorMsg
				                     });
				            	}
				            }  
				        }); */
				    	var earBrandOptions = earBrandGrid.datagrid('options');
				    	if(earBrandOptions.url == null){
				    		earBrandGrid.datagrid({
				    			url:basicUrl+'/production/searchValidPigToPage.do'
				    		});
				    	}
				    	earBrandGrid.datagrid('load',{
				    		queryType:1,
			            	pigType:oldPigType,
			            	specifyPigs:0,
			            	choice:1,
			            	eventName:eventName,
			            	pigIds:pigIds
				    	});
					},
					filter: function(q, row){
						var opts = $(this).combogrid('options');
						return row[opts.textField].indexOf(q) == 0;
					}
				}
			}
	}
	return result;
}

/**
 * 配种公猪gird编辑
 * @param pms
 * @returns {___anonymous6847_6856}
 */
function breedBoarGridComboBox(pms){
	var breedBoarField = pms.field == null ? 'breedBoarFirst' : pms.field;
	var breedBoarTextField = pms.textField == null ? 'earBrand' : pms.textField;
	var listTableId = pms.listTableId == null ? 'listTable' : pms.listTableId;
	var result = {
			width:pms.width == null ? null : pms.width,
			field:breedBoarField,
			title:pms.title == null ? '配种公猪' : pms.title,
			pable:false,
			formatter:pms.formatter == null ? function(value,row){
	           return row.breedBoarFirstEarBrand;  
			} : pms.formatter,
			editor:{
				type:'combobox',
				options:{
					valueField:pms.valueField == null ? 'pigId' : pms.valueField,
					textField:breedBoarTextField,
					required:pms.required == null ? false : pms.required,
					panelHeight:200,
					validType:'grid_combobox_validateExist["'+breedBoarField+'","'+breedBoarTextField+'","'+listTableId+'"]',
				    onChange:pms.onChange == null ? function(newValue,oldValue){
				    	if(oldValue == '' || newValue == ''){
				    		return;
				    	}
				    	var field = pms.field == null ? 'breedBoarFirst' : pms.field;
				    	var hasSelectRows = $('#listTable').datagrid('getData').rows;
				    	var pigId = null;
				    	if(field == 'breedBoarFirst'){
				    		pigId = hasSelectRows[editIndex].breedBoarFirst;
				    	}else if(field == 'breedBoarSecond'){
				    		pigId = hasSelectRows[editIndex].breedBoarSecond;
				    	}else{
				    		pigId = hasSelectRows[editIndex].breedBoarThird;
				    	}
				    	var sememIds = '';
			        	$.each(sememIdsArray,function(i,data){
			        		if(data != pigId){
			        			if(i == 0){
			        				sememIds += data;
				        		}else{
				        			sememIds += ',' + data;
				        		}
			        		}
			        	});
			        	if(sememIds.substring(0,1) == ','){
			        		sememIds = sememIds.substring(1,sememIds.length);
			        	}
				    	var breedType = $('#listTable').datagrid('getEditor', {
					        index: editIndex,
					        field: 'breedType'
					    });
				    	var enterDate = $('#listTable').datagrid('getEditor', {
					        index: editIndex,
					        field: 'enterDate'
					    });
				    	if(breedType != null && enterDate != null){
				    		var breedDateValue = $(enterDate.target).combobox('getValue');
				    		var breedTypeValue = $(breedType.target).combobox('getValue');
				    		if(breedDateValue != null && breedDateValue != '' && breedTypeValue != null && breedTypeValue != ''){
								var data = [];
								$.ajax({  
							        url : basicUrl+'/production/searchSemenToList.do', 
							        data:{
							        	queryType:1,
							        	query:$(this).combobox('getText'),
							        	breedType:breedTypeValue,
							        	breedDate:breedDateValue,
							        	sememIds:sememIds
							        },
							        async : false, 
							        type : "POST",  
							        dataType : "json",  
							        success : function(response) { 
							        	data = response.rows;
							        }  
							    }); 
								$(this).combobox('loadData',data);
				    		}
				    	}
					}:pms.onChange,
					onSelect:pms.onSelect == null ? function(row){
						var field = pms.field == null ? 'breedBoarFirst' : pms.field;
						var hasSelectRows = $('#listTable').datagrid('getData').rows;
						sememIdsArray = [];
						var breedBoarFirst = $('#listTable').datagrid('getEditor', {
					        index: editIndex,
					        field: 'breedBoarFirst'
					    });
						if(breedBoarFirst != null){
							var pigId = $(breedBoarFirst.target).combobox('getValue');
							if(pigId != '' && pigId != null){
								sememIdsArray.push(pigId);
							}
						}else{
							if(hasSelectRows[editIndex].breedBoarFirst){
								sememIdsArray.push(hasSelectRows[editIndex].breedBoarFirst);
							}
						}
						var breedBoarSecond = $('#listTable').datagrid('getEditor', {
					        index: editIndex,
					        field: 'breedBoarSecond'
					    });
						if(breedBoarSecond != null){
							var pigId = $(breedBoarSecond.target).combobox('getValue');
							if(pigId != '' && pigId != null){
								sememIdsArray.push(pigId);
							}
						}else{
							if(hasSelectRows[editIndex].breedBoarSecond){
								sememIdsArray.push(hasSelectRows[editIndex].breedBoarSecond);
							}
						}
						var breedBoarThird = $('#listTable').datagrid('getEditor', {
					        index: editIndex,
					        field: 'breedBoarThird'
					    });
						if(breedBoarThird != null){
							var pigId = $(breedBoarThird.target).combobox('getValue');
							if(pigId != '' && pigId != null){
								sememIdsArray.push(pigId);
							}
						}else{
							if(hasSelectRows[editIndex].breedBoarThird){
								sememIdsArray.push(hasSelectRows[editIndex].breedBoarThird);
							}
						}
						$.each(hasSelectRows,function(i,data){
							if(i != editIndex){
								if(data.breedBoarFirst != null && data.breedBoarFirst != ''){
				        			sememIdsArray.push(data.breedBoarFirst);
				        		}
				        		if(data.breedBoarSecond != null && data.breedBoarSecond != ''){
				        			sememIdsArray.push(data.breedBoarSecond);
				        		}
				        		if(data.breedBoarThird != null && data.breedBoarThird != ''){
				        			sememIdsArray.push(data.breedBoarThird);
				        		}
							}
			        	});
						var enterDate = $('#listTable').datagrid('getEditor', {
					        index: editIndex,
					        field: 'enterDate'
					    });
						var breedDateValue = enterDate == null ? '' : $(enterDate.target).datebox('getValue');
						var breedType = $('#listTable').datagrid('getEditor', {
					        index: editIndex,
					        field: 'breedType'
					    });
						var breedTypeValue =  breedType == null ? '' : $(breedType.target).combobox('getValue');
			    		if(breedTypeValue != null && breedTypeValue != '' && breedDateValue != null && breedDateValue != ''){
			    			if(breedBoarFirst != null){
			    				var pigId = $(breedBoarFirst.target).combobox('getValue');
			    				var sememIds = '';
					        	$.each(sememIdsArray,function(i,data){
					        		if(data != pigId){
					        			if(i == 0){
					        				sememIds += data;
						        		}else{
						        			sememIds += ',' + data;
						        		}
					        		}
					        	});
					        	if(sememIds.substring(0,1) == ','){
					        		sememIds = sememIds.substring(1,sememIds.length);
					        	}
					        	$.ajax({  
							        url : basicUrl+'/production/searchSemenToList.do', 
							        data:{
							        	queryType:1,
							        	breedType:breedTypeValue,
							        	breedDate:breedDateValue,
							        	sememIds:sememIds
							        },
							        async : false, 
							        type : "POST",  
							        dataType : "json",  
							        success : function(response) { 
						        		$(breedBoarFirst.target).combobox('loadData',response.rows);
							        }  
							    }); 
			    			}
			    			if(breedBoarSecond != null){
			    				var pigId = $(breedBoarSecond.target).combobox('getValue');
			    				var sememIds = '';
					        	$.each(sememIdsArray,function(i,data){
					        		if(data != pigId){
					        			if(i == 0){
					        				sememIds += data;
						        		}else{
						        			sememIds += ',' + data;
						        		}
					        		}
					        	});
					        	if(sememIds.substring(0,1) == ','){
					        		sememIds = sememIds.substring(1,sememIds.length);
					        	}
					        	$.ajax({  
							        url : basicUrl+'/production/searchSemenToList.do', 
							        data:{
							        	queryType:1,
							        	breedType:breedTypeValue,
							        	breedDate:breedDateValue,
							        	sememIds:sememIds
							        },
							        async : false, 
							        type : "POST",  
							        dataType : "json",  
							        success : function(response) { 
						        		$(breedBoarSecond.target).combobox('loadData',response.rows);
							        }  
							    }); 
			    			}
			    			if(breedBoarThird != null){
			    				var pigId = $(breedBoarThird.target).combobox('getValue');
			    				var sememIds = '';
					        	$.each(sememIdsArray,function(i,data){
					        		if(data != pigId){
					        			if(i == 0){
					        				sememIds += data;
						        		}else{
						        			sememIds += ',' + data;
						        		}
					        		}
					        	});
					        	if(sememIds.substring(0,1) == ','){
					        		sememIds = sememIds.substring(1,sememIds.length);
					        	}
					        	$.ajax({  
							        url : basicUrl+'/production/searchSemenToList.do', 
							        data:{
							        	queryType:1,
							        	breedType:breedTypeValue,
							        	breedDate:breedDateValue,
							        	sememIds:sememIds
							        },
							        async : false, 
							        type : "POST",  
							        dataType : "json",  
							        success : function(response) { 
						        		$(breedBoarThird.target).combobox('loadData',response.rows);
							        }  
							    }); 
			    			}
			    		}
					}:pms.onSelect,
					filter: function(q, row){
						var opts = $(this).combobox('options');
						return row[opts.textField].indexOf(q) == 0;
					}
				}
			}
	}
	return result;
}

/**
 * 代养母猪gird编辑
 * @param pms
 * @returns {___anonymous6847_6856}
 */
function boardSowGridComboGrid(pms){
	var listTableId = pms.listTableId == null ? 'listTable' : pms.listTableId;
	var result = {
			width:pms.width == null ? null : pms.width,
			field:pms.field == null ? 'boardSowId' : pms.field,
			title:pms.title == null ? '代养母猪' : pms.title,
			pable:false,
			formatter:pms.formatter == null ? function(value,row){
	           return row.boardSowEarBrand;  
			} : pms.formatter,
			editor:{
				type:'combobox',
				options:{
					valueField:pms.valueField == null ? 'pigId' : pms.valueField,
					textField:pms.textField == null ? 'earBrand' : pms.textField,
					required:pms.required == null ? false : pms.required,
					panelHeight:200,
					validType:'grid_combobox_validateExist["'+(pms.field == null?'boardSowId':pms.field)+'","'+(pms.textField == null ? 'earBrand':pms.textField) +'","'+listTableId+'"]',
					onChange:function(newValue,oldValue){
						if(editIndex == undefined){
				    		return;
				    	}
						var rows = $('#listTable').datagrid('getRows');
						var sowCombobox = $(this);
			    		$.ajax({  
					        url : basicUrl+'/production/searchValidPigToPage.do', 
					        data:{
					        	queryType:1,
					        	query:sowCombobox.combobox('getText'),
				            	pigType:2,
				            	specifyPigs:0,
				            	choice:1,
				            	eventName:'BOAR_FOSTER',
				            	date1:rows[editIndex].enterDate,
				            	pigIds:rows[editIndex].pigId,
				            	houseIds:rows[editIndex].houseId
					        },
					        type : "POST",  
					        dataType : "json",  
					        success : function(response) { 
					        	sowCombobox.combobox('loadData',response.rows);
					        }  
					    }); 
					},
					filter: function(q, row){
						var opts = $(this).combobox('options');
						return row[opts.textField].indexOf(q) == 0;
					}
				}
			}
	}
	return result;
}
function earBrandTextBox(pms){
	var earFieldName = pms.field == null ? 'earBrand' : pms.field;
	var result = {
			width:pms.width == null ? 100 : pms.width,
			field:pms.field == null ? 'earBrand' : pms.field,
			title:pms.title == null ? '耳牌号' : pms.title,
			pable:false,
			editor:{
				type:'combobox',
				options:{
					valueField:pms.valueField == null ? 'earBrand' : pms.valueField,
					textField:pms.textField == null ? 'earBrand' : pms.textField,
					required:pms.required == null ? false : pms.required,
					validType:'numOrLetterOr_',
					panelHeight:'auto',
					panelMaxHeight:200,
					rowStyler: function(index,row){
						if ((index+1) % 2 == 0){
							return 'background-color:#f7f7f7;';
						}
					},
					hasDownArrow:false,
					onSelect:function(record){
						if(editIndex == undefined){
							return;
						}
        				var row = record;
						var rows = $('#listTable').datagrid('getRows');
						rows[editIndex].pigId = row.pigId;
						rows[editIndex].status = 1;
						rows[editIndex][earFieldName] = row[earFieldName];
						rows[editIndex].pigInfo = row.pigInfo;
						rows[editIndex].minValidDate = row.minValidDate;
						rows[editIndex].maxValidDate = row.maxValidDate;
						rows[editIndex].enterDate = row.lastEventDate;
						rows[editIndex].houseId = row.houseId;
						rows[editIndex].pigClass = row.pigClass;
						var changeValue = [{
							field:'pigId',
							value:rows[editIndex].pigId
						},{
							field:'pigInfo',
							value:rows[editIndex].pigInfo
						},{
							field:'minValidDate',
							value:rows[editIndex].minValidDate
						},{
							field:'maxValidDate',
							value:rows[editIndex].maxValidDate
						},{
							field:'pigClass',
							value:rows[editIndex].pigClass
						},{
							field:'enterDate',
							value:rows[editIndex].enterDate
						}];
						
						if(eventName == 'WEAN' || eventName == 'CHILD_PIG_DIE' || eventName == 'FOSTER' || eventName == 'NURSE_CHANGE_HOUSE'){
							rows[editIndex].pigQty = row.pigQty;
							changeValue.push({
								field:'pigQty',
								value:rows[editIndex].pigQty
							});
							if(eventName == 'WEAN' && (dnflag == 'ON' || dnflag == 'on')){
								rows[editIndex].weanNum = row.pigQty;
								rows[editIndex].dieNum = 0;
								var weanNumSum = 0,dieNumSum = 0,weightSum = 0;
								$.each(rows,function(i,item){
									weanNumSum += strToInt(item.weanNum);
									dieNumSum += strToInt(item.dieNum);
									weightSum += parseFloat(item.weanWeight);
								});
								$('#weanNumSum').html(weanNumSum);
								$('#dieNumSum').html(dieNumSum);
								$('#weanNumAvg').html((weanNumSum/rows.length).toFixed(2));
								$('#weanWeightAvg').html((weightSum/rows.length).toFixed(2));

								changeValue.push({
									field:'dieNum',
									value:rows[editIndex].dieNum
								},{
									field:'weanNum',
									value:rows[editIndex].weanNum
								});
							}
						}
						if(eventName == 'SELECT_BREED_PIG'){
							rows[editIndex].rowId = row.pigId;
							rows[editIndex].selectBreedType = row.selectBreedType;
							rows[editIndex].selectBreedTypeName = row.selectBreedTypeName;
							rows[editIndex].sex = row.sex;
							rows[editIndex].sexName = row.sexName;
							rows[editIndex].earCodeId = row.earCodeId;
							rows[editIndex].swineryId = row.swineryId;
							rows[editIndex].pigpenId = row.pigpenId;
							rows[editIndex].materialId = row.materialId;
							changeValue.push({
								field:'rowId',
								value:rows[editIndex].rowId
							},{
								field:'selectBreedType',
								value:rows[editIndex].selectBreedTypeName
							},{
								field:'sex',
								value:rows[editIndex].sexName
							},{
								field:'earCodeId',
								value:rows[editIndex].earCodeId
							},{
								field:'swineryId',
								value:rows[editIndex].swineryId
							},{
								field:'pigpenId',
								value:rows[editIndex].pigpenId
							},{
								field:'materialId',
								value:rows[editIndex].materialId
							});
						}
						if(eventName == 'SEED_PIG'){
							rows[editIndex].rowId = row.pigId;
							rows[editIndex].parity = row.parity;
							rows[editIndex].houseName = row.houseName;
							rows[editIndex].pigpenName = row.pigpenName;
							rows[editIndex].pigQty = row.pigQty;
							rows[editIndex].seedMale = row.seedMale;
							rows[editIndex].seedFemale = row.seedFemale;
							rows[editIndex].aliveLitterX = row.seedMale;
							rows[editIndex].aliveLitterY = row.seedFemale;
							changeValue.push({
								field:'rowId',
								value:rows[editIndex].rowId
							},{
								field:'parity',
								value:rows[editIndex].parity
							},{
								field:'houseName',
								value:rows[editIndex].houseName
							},{
								field:'pigpenName',
								value:rows[editIndex].pigpenName
							},{
								field:'pigQty',
								value:rows[editIndex].pigQty
							},{
								field:'seedMale',
								value:rows[editIndex].seedMale
							},{
								field:'seedFemale',
								value:rows[editIndex].seedFemale
							},{
								field:'aliveLitterX',
								value:rows[editIndex].aliveLitterX
							},{
								field:'aliveLitterY',
								value:rows[editIndex].aliveLitterY
							});
						}
						if(eventName == 'BREED_PIG_DIE'){
							if(earFieldName == 'earShort'){
								rows[editIndex].earBrand = '';
								rows[editIndex].weanWeight = 0.0;
								changeValue.push({
									field:'earBrand',
									value:rows[editIndex].earBrand
								},{
	    							field:'weanWeight',
	    							value:rows[editIndex].weanWeight
								});
							}else if(earFieldName == 'earBrand'){
								rows[editIndex].earShort = '';
								rows[editIndex].pigType = row.pigType;
								rows[editIndex].pigClass = row.pigClass;
								rows[editIndex].pigpenId = row.pigpenId;
								rows[editIndex].pigQty = row.pigQty;
								changeValue.push({
									field:'earShort',
									value:rows[editIndex].earShort
								},{
	    							field:'pigType',
	    							value:rows[editIndex].pigType
	    						},{
	    							field:'pigClass',
	    							value:rows[editIndex].pigClass
	    						},{
	    							field:'houseId',
	    							value:rows[editIndex].houseId
	    						},{
	    							field:'pigpenId',
	    							value:rows[editIndex].pigpenId
	    						},{
	    							field:'pigQty',
	    							value:rows[editIndex].pigQty
								});
								if(row.pigType != 2 || row.pigClass != 10){
									rows[editIndex].weanWeight = 0.0;
									changeValue.push({
										field:'weanWeight',
										value:rows[editIndex].weanWeight
									});
								}
							}else {
								rows[editIndex].earShort = '';
								changeValue.push({
									field:'earShort',
									value:rows[editIndex].earShort
								});
							}
						}
						if(eventName == 'SALE_SEMEN'){
							rows[editIndex].semenId = row.rowId;
							/*rows[editIndex].semenBatchNo = row.semenBatchNo;*/
							rows[editIndex].materialId = row.materialId;
							rows[editIndex].boarId = row.pigId;
							rows[editIndex].semenNum = row.semenNum;
							rows[editIndex].validDate = row.validDate;
							rows[editIndex].semenDate = row.semenDate;
							rows[editIndex].warehouseId = row.warehouseId;
							rows[editIndex].breedAndDayAge = row.breedAndDayAge;
							rows[editIndex].earBrand = row.earBrand;
							rows[editIndex].warehouseName = row.warehouseName;
							changeValue.push({
								field:'semenId',
								value:rows[editIndex].semenId
							/*},{
								field:'semenBatchNo',
								value:rows[editIndex].semenBatchNo*/
							},{
    							field:'materialId',
    							value:rows[editIndex].materialId
    						},{
    							field:'boarId',
    							value:rows[editIndex].boarId
    						},{
    							field:'semenNum',
    							value:rows[editIndex].semenNum
    						},{
    							field:'validDate',
    							value:rows[editIndex].validDate
    						},{
    							field:'semenDate',
    							value:rows[editIndex].semenDate
    						},
    						{
    							field:'earBrand',
    							value:rows[editIndex].earBrand
    							},
    							{
    							field:'breedAndDayAge',
    							value:rows[editIndex].breedAndDayAge
    							},
    						{
    							field:'warehouseId',
    							value:rows[editIndex].warehouseName
							});
						}
						changeTableDisplayValue('listTable',editIndex,changeValue);
						setTimeout(function () {
							var field = pms.field == null ? 'earBrand' : pms.field;
							focusEditCell('listTable',editIndex,field);
						 },100);
					},
					icons: [{
						iconCls:'icon-search',
						handler: function(e){
							var text = $(e.data.target).combobox('getText');
							if(text == null || text == ''){
								return;
							}
							var index = editIndex;
							var hasSelectRows = $('#listTable').datagrid('getData').rows;
				        	var pigIdsArray = [];
				        	var semenIdsArray = [];
				        	$.each(hasSelectRows,function(i,data){
				        		if(data.pigId != null && data.pigId != '' && i != editIndex){
				        			pigIdsArray.push(data.pigId);
				        		}
				        		if(data.semenId != null && data.semenId != '' && i != editIndex && data.semenId != undefined){
				        			semenIdsArray.push(data.semenId);
				        		}
				        	});
				        	var pigIds = '';
				        	var semenIds = '';
				        	$.each(pigIdsArray,function(i,data){
				        		if(i == pigIdsArray.length - 1){
				        			pigIds += data;
				        		}else{
				        			pigIds += data + ',';
				        		}
				        	});
				        	$.each(semenIdsArray,function(i,data){
				        		if(i == semenIdsArray.length - 1){
				        			semenIds += data;
				        		}else{
				        			semenIds += data + ',';
				        		}
				        	});
				        	var queryDate = {
					            	queryType:1,
					            	query:text,
					            	pigType:oldPigType,
					            	specifyPigs:0,
					            	choice:1,
					            	eventName:eventName,
					            	pigIds:pigIds,
					            	earCodeType:earFieldName,
					            	semenIds:semenIds
					            };
				        	jAjax.submit('/production/searchValidPigToPage.do',queryDate,function(response){
				        		if(response.success){
				            		if(!response.searchSuccess){
//				            			 $.messager.alert({
//					                         title: '错误',
//					                         msg: response.errorMsg
//					                     });
				            			 var rows = $('#listTable').datagrid('getRows');
			    						 rows[index].status = -1;
			    						 if(earFieldName == 'semenBatchNo'){
			    							 rows[index].validDate = '<span style="color:red">'+response.errorMsg+'</span>';
				    						 var changeValue = [{
					    							field:'validDate',
					    							value:rows[index].validDate
					    						}];
			    						 }else{
			    							 rows[index].pigInfo = '<span style="color:red">'+response.errorMsg+'</span>';
				    						 var changeValue = [{
					    							field:'pigInfo',
					    							value:rows[index].pigInfo
					    						}];
			    						 }
			    						 
			    						 changeTableDisplayValue('listTable',index,changeValue);
				            		}else{
				            			var responseRows = response.rows;
				            			var validIndex = null;
				            			$.each(responseRows,function(i,item){
				            				if(item[earFieldName] == text){
				            					validIndex = i;
				            					return false;
				            				}
				            			});
				            			if(validIndex != null){
				            				if(index == undefined){
				    							return;
				    						}
				            				var row = responseRows[validIndex];
				    						var rows = $('#listTable').datagrid('getRows');
				    						rows[index].pigId = row.pigId;
				    						rows[index].status = 1;
				    						rows[index][earFieldName] = row[earFieldName];
				    						rows[index].pigInfo = row.pigInfo;
				    						rows[index].minValidDate = row.minValidDate;
				    						rows[index].maxValidDate = row.maxValidDate;
				    						rows[index].enterDate = row.lastEventDate;
				    						rows[index].houseId = row.houseId;
				    						rows[index].pigClass = row.pigClass;
				    						var changeValue = [{
				    							field:'pigId',
				    							value:rows[index].pigId
				    						},{
				    							field:'pigInfo',
				    							value:rows[index].pigInfo
				    						},{
				    							field:'minValidDate',
				    							value:rows[index].minValidDate
				    						},{
				    							field:'maxValidDate',
				    							value:rows[index].maxValidDate
				    						},{
				    							field:'pigClass',
				    							value:rows[index].pigClass
				    						},{
				    							field:'enterDate',
				    							value:rows[index].enterDate
				    						}];
				    						
				    						if(eventName == 'WEAN' || eventName == 'CHILD_PIG_DIE' || eventName == 'FOSTER' || eventName == 'NURSE_CHANGE_HOUSE'){
				    							rows[editIndex].pigQty = row.pigQty;
				    							changeValue.push({
				    								field:'pigQty',
				    								value:rows[editIndex].pigQty
				    							});
				    							if(eventName == 'WEAN' && (dnflag == 'ON' || dnflag == 'on')){
				    								rows[index].weanNum = row.pigQty;
				    								rows[index].dieNum = 0;
				    								var weanNumSum = 0,dieNumSum = 0,weightSum = 0;
				    								$.each(rows,function(i,item){
				    									weanNumSum += strToInt(item.weanNum);
				    									dieNumSum += strToInt(item.dieNum);
				    									weightSum += parseFloat(item.weanWeight);
				    								});
				    								$('#weanNumSum').html(weanNumSum);
				    								$('#dieNumSum').html(dieNumSum);
				    								$('#weanNumAvg').html((weanNumSum/rows.length).toFixed(2));
				    								$('#weanWeightAvg').html((weightSum/rows.length).toFixed(2));
				    								changeValue.push({
				    									field:'dieNum',
				    									value:rows[index].dieNum
				    								},{
				    									field:'weanNum',
				    									value:rows[index].weanNum
				    								});
				    							}
				    						}
				    						if(eventName == 'SELECT_BREED_PIG'){
				    							rows[index].rowId = row.pigId;
				    							rows[editIndex].selectBreedType = row.selectBreedType;
				    							rows[editIndex].selectBreedTypeName = row.selectBreedTypeName;
				    							rows[editIndex].sex = row.sex;
				    							rows[editIndex].sexName = row.sexName;
				    							rows[editIndex].earCodeId = row.earCodeId;
				    							rows[editIndex].swineryId = row.swineryId;
				    							rows[editIndex].pigpenId = row.pigpenId;
				    							rows[editIndex].materialId = row.materialId;
				    							changeValue.push({
				    								field:'rowId',
				    								value:rows[editIndex].rowId
				    							},{
				    								field:'selectBreedType',
				    								value:rows[editIndex].selectBreedTypeName
				    							},{
				    								field:'sex',
				    								value:rows[editIndex].sexName
				    							},{
				    								field:'earCodeId',
				    								value:rows[editIndex].earCodeId
				    							},{
				    								field:'swineryId',
				    								value:rows[editIndex].swineryId
				    							},{
				    								field:'pigpenId',
				    								value:rows[editIndex].pigpenId
				    							},{
				    								field:'materialId',
				    								value:rows[editIndex].materialId
				    							});
				    						}
				    						if(eventName == 'SEED_PIG'){
				    							rows[index].rowId = row.pigId;
				    							rows[index].parity = row.parity;
				    							rows[index].houseName = row.houseName;
				    							rows[index].pigpenName = row.pigpenName;
				    							rows[index].pigQty = row.pigQty;
				    							rows[index].seedMale = row.seedMale;
				    							rows[index].seedFemale = row.seedFemale;
				    							rows[index].aliveLitterX = row.seedMale;
				    							rows[index].aliveLitterY = row.seedFemale;
				    							changeValue.push({
				    								field:'rowId',
				    								value:rows[index].rowId
				    							},{
				    								field:'parity',
				    								value:rows[index].parity
				    							},{
				    								field:'houseName',
				    								value:rows[index].houseName
				    							},{
				    								field:'pigpenName',
				    								value:rows[index].pigpenName
				    							},{
				    								field:'pigQty',
				    								value:rows[index].pigQty
				    							},{
				    								field:'seedMale',
				    								value:rows[index].seedMale
				    							},{
				    								field:'seedFemale',
				    								value:rows[index].seedFemale
				    							},{
				    								field:'aliveLitterX',
				    								value:rows[index].aliveLitterX
				    							},{
				    								field:'aliveLitterY',
				    								value:rows[index].aliveLitterY
				    							});
				    						}
				    						if(eventName == 'BREED_PIG_DIE'){
				    							if(earFieldName == 'earShort'){
				    								rows[editIndex].earBrand = '';
				    								rows[editIndex].weanWeight = 0.0;
				    								changeValue.push({
				    									field:'earBrand',
				    									value:rows[editIndex].earBrand
				    								},{
				    	    							field:'weanWeight',
				    	    							value:rows[editIndex].weanWeight
				    								});
				    							}else if(earFieldName == 'earBrand'){
				    								rows[editIndex].earShort = '';
				    								rows[editIndex].pigType = row.pigType;
				    								rows[editIndex].pigClass = row.pigClass;
				    								rows[editIndex].pigpenId = row.pigpenId;
				    								rows[editIndex].pigQty = row.pigQty;
				    								changeValue.push({
				    									field:'earShort',
				    									value:rows[editIndex].earShort
				    								},{
				    	    							field:'pigType',
				    	    							value:rows[editIndex].pigType
				    	    						},{
				    	    							field:'pigClass',
				    	    							value:rows[editIndex].pigClass
				    	    						},{
				    	    							field:'houseId',
				    	    							value:rows[editIndex].houseId
				    	    						},{
				    	    							field:'pigpenId',
				    	    							value:rows[editIndex].pigpenId
				    	    						},{
				    	    							field:'pigQty',
				    	    							value:rows[editIndex].pigQty
				    								});
				    								if(row.pigType != 2 || row.pigClass != 10){
				    									rows[editIndex].weanWeight = 0.0;
				    									changeValue.push({
				    										field:'weanWeight',
				    										value:rows[editIndex].weanWeight
				    									});
				    								}
				    							}else {
				    								rows[editIndex].earShort = '';
				    								changeValue.push({
				    									field:'earShort',
				    									value:rows[editIndex].earShort
				    								});
				    							}
				    						}
				    						if(eventName == 'SALE_SEMEN'){
				    							rows[editIndex].semenId = row.rowId;
				    							//rows[editIndex].semenBatchNo = row.semenBatchNo;
			    								rows[editIndex].materialId = row.materialId;
			    								rows[editIndex].boarId = row.pigId;
			    								rows[editIndex].semenNum = row.semenNum;
			    								rows[editIndex].validDate = row.validDate;
			    								rows[editIndex].semenDate = row.semenDate;
			    								rows[editIndex].warehouseId = row.warehouseId;
			    								rows[editIndex].warehouseName = row.warehouseName;
			    								rows[editIndex].earBrand = row.earBrand;
			    								rows[editIndex].breedAndDayAge = row.breedAndDayAge;
			    								changeValue.push({
			    									field:'semenId',
			    									value:rows[editIndex].semenId
			    								},{
			    								/*	field:'semenBatchNo',
			    									value:rows[editIndex].semenBatchNo
			    								},{*/
			    	    							field:'materialId',
			    	    							value:rows[editIndex].materialId
			    	    						},
			    	    						{
			    	    							field:'boarId',
			    	    							value:rows[editIndex].boarId
			    	    						},
			    	    						{
			    	    							field:'semenNum',
			    	    							value:rows[editIndex].semenNum
			    	    						},
			    	    						{
			    	    							field:'validDate',
			    	    							value:rows[editIndex].validDate
			    	    						},
			    	    						{
			    	    							field:'semenDate',
			    	    							value:rows[editIndex].semenDate
			    	    						},
			    	    						{
			    	    							field:'earBrand',
			    	    							value:rows[editIndex].earBrand
			    	    						},
			    	    						{
			    	    							field:'breedAndDayAge',
			    	    							value:rows[editIndex].breedAndDayAge
			    	    						},
			    	    						
			    	    						{
			    	    							field:'warehouseId',
			    	    							value:rows[editIndex].warehouseName
			    								});
				    							
				    						}
				    						/*if(eventName == 'BREED_PIG_DIE'){
			    								rows[index].pigType = row.pigType;
			    								rows[index].pigClass = row.pigClass;
			    								rows[editIndex].pigpenId = row.pigpenId;
			    								rows[editIndex].pigQty = row.pigQty;
			    								changeValue.push({
			    									field:'earShort',
			    									value:rows[index].earShort
			    								},{
			    	    							field:'pigType',
			    	    							value:rows[index].pigType
			    	    						},{
			    	    							field:'pigClass',
			    	    							value:rows[index].pigClass
			    	    						},{
			    	    							field:'houseId',
			    	    							value:rows[index].houseId
			    	    						},{
			    	    							field:'pigpenId',
			    	    							value:rows[index].pigpenId
			    	    						},{
			    	    							field:'pigQty',
			    	    							value:rows[index].pigQty
			    								});
				    						}
				    						*/
				    						if(eventName == 'DELIVERY'){
				    							var healthyNumSum = 0,weakNumSum = 0,weightSum = 0,stillbirthNumSum = 0,mummyNumSum = 0,mutantNumSum = 0;
				    							$.each(rows,function(i,row){
				    								healthyNumSum += strToInt(row.healthyNum == undefined ? 0 : row.healthyNum);
				    								weakNumSum += strToInt(row.weakNum == undefined ? 0 : row.weakNum);
				    								weightSum += parseFloat(row.weakNum == undefined ? 0 : row.aliveLitterWeight);
				    								stillbirthNumSum += strToInt(row.stillbirthNum == undefined ? 0 : row.stillbirthNum);
				    								mummyNumSum += strToInt(row.mummyNum == undefined ? 0 : row.mummyNum);
				    								mutantNumSum += strToInt(row.mutantNum == undefined ? 0 : row.mutantNum);
				    							});
				    							$('#healthyNumSum').html(healthyNumSum);
				    							$('#weakNumSum').html(weakNumSum);
				    							$('#stillbirthNumSum').html(stillbirthNumSum);
				    							$('#mummyNumSum').html(mummyNumSum);
				    							$('#mutantNumSum').html(mutantNumSum);
				    							$('#healthyNumAvg').html((healthyNumSum/rows.length).toFixed(2));
				    							$('#weightAvg').html((weightSum/rows.length).toFixed(2));
				    						}
				    						changeTableDisplayValue('listTable',index,changeValue);
				    						if(responseRows.length > 1){
				    							$(e.data.target).combobox('loadData',response.rows);
					            				$(e.data.target).combobox('showPanel');
				    						}
				            			}else{
				            				$(e.data.target).combobox('loadData',response.rows);
				            				$(e.data.target).combobox('showPanel');
				            			}
				            		}
				            	}else{
				            		 $.messager.alert({
				                         title: '错误',
				                         msg: response.errorMsg
				                     });
				            	}
				        	},null,null,true,null,true);
						}
					}]
				}
			}
	}
	return result;
}
