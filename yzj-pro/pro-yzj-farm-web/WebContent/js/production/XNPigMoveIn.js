var prUrl='/production/';
var saveUrl=prUrl+'editXNPigMoveIn.do';
var editIndex = undefined;
var eventName = 'PIG_MOVE_IN';
//默认值为2
var oldPigType = 2;

var detailDefaultValues = {
		status:'1',
		deletedFlag:'0',
		//设置默认值
		pigClass:"2",
		pigClassName:"生产公猪"
};
$(document).ready(function(){
	
	//猪场
	xnCombobox({
	    id:'farmId',
	    url:'/production/searchXNFarm.do',
	    valueField:'rowId',
	    textField:'farmName',
	    required:true
	});
	addFocus('farmId','combobox');
	
	//猪只清单
	$('#total-plan').css('visibility','hidden');
	
	$("#addButton").attr({"disabled":"disabled"});
	$("#addButton").css('background-color','rgb(220, 220, 220)');
	
	$('#listTable').datagrid(
			j_detailGrid({
				height:'100%',
			dbClickEdit:true,
			//fitColumns:true,
			frozenColumns:[[
			                {field:'ck',checkbox:true},
			              	j_gridText({field:'rowId',title:'ID',hidden:true}),
			              	j_gridText({field:'deletedFlag',title:'deletedFlag',hidden:true}),
			              	j_gridText({field:'pigIds',title:'猪只ID',hidden:true}),
			              	j_gridText({field:'relationPigId',title:'猪只Id',hidden:true}),
			              	j_gridText({field:'leaveDate',title:'销售离场日期',hidden:true}),
			              	j_gridText({field:'earBrand',title:'耳牌号',width:100}),
			              	j_gridText({field:'earShort',title:'耳缺号',width:100})
			              	]],
			columns:[[
		              	//物料名称
		              	j_gridText({field:'materialIdName',title:'物料名称',width:80}),
		              	j_gridText({field:'onPrice',title:'初始成本',width:100,
		              		precision:2,
		              		increment:0.1,
		              		min:0,
		              		max:100000,
		              		formatter:function(value,row){
	              			value = value == null || value == undefined || value == '' ? 0.00 : value;
		              		return value+'元';
		              	}}),
		              	j_gridText({field:'fatherEar',title:'父亲耳号',width:100}),
		              	j_gridText({field:'motherEar',title:'母亲耳号',width:100}),
		              	//猪舍
		              	j_gridText(houseGridComboGrid({width:100})),
		              	//猪只状态
		              	j_gridText({field:'pigClassName',title:'猪只状态',width:80}),
		              	j_gridText({field:'leftTeatNum',width:100,title:'左乳头',min:1,max:10}),
		              	j_gridText({field:'rightTeatNum',width:100,title:'右乳头',min:1,max:10}),
		              	j_gridText({field:'parity',width:100,title:'胎次',min:0,max:12}),
		              	j_gridText({field:'birthDate',width:100,title:'出生日期',width:80}),
		              	j_gridText({field:'birthWeight',
		              		precision:2,
		              		increment:0.1,
		              		min:1,
		              		max:5,
		              		formatter:function(value,row){
		              			value = value == null || value == undefined || value == '' ? 0.00 : value;
		              			row.birthWeight = value;
		              			return value+'KG';
			              	},
		              		width:100,title:'出生体重'}),
		              	j_gridText({field:'enterWeight',
		              		precision:2,
		              		increment:0.1,
		              		min:1,
		              		max:500,
		              		formatter:function(value,row){
		              			value = value == null || value == undefined || value == '' ? 0.00 : value;
		              			row.enterWeight = value;
		              			return value+'KG';
			              	},
		              		width:100,title:'入场体重'}),
		              	//供应商
		              	j_gridText({field:'supplierName',title:'供应商',width:80}),
		              	j_gridText({field:'notes',title:'备注',width:100,editor:'textbox'})
				    ]],
		    onEndEdit:function(index,row){
	            var material = $(this).datagrid('getEditor', {
	                index: index,
	                field: 'materialId'
	            });
	            if(material != null){
	            	row.materialIdName = $(material.target).combogrid('getText');
	            }
	            var pigHouse = $(this).datagrid('getEditor', {
	                index: index,
	                field: 'houseId'
	            });
	            if(pigHouse != null){
	            	row.houseIdName = $(pigHouse.target).combogrid('getText');
	            }
	            var line = $(this).datagrid('getEditor', {
	                index: index,
	                field: 'lineId'
	            });
	            if(line != null){
	            	row.lineIdName = $(line.target).combogrid('getText');
	            }
	            var pigClass = $(this).datagrid('getEditor', {
	                index: index,
	                field: 'pigClass'
	            });
	            if(pigClass != null){
	            	row.pigClassName = $(pigClass.target).combogrid('getText');
	            }
	            var supplier = $(this).datagrid('getEditor', {
	                index: index,
	                field: 'supplierId'
	            });
	            if(supplier != null){
	            	row.supplierIdName = $(supplier.target).combogrid('getText');
	            }
	        },
	        onBeginEdit:function(index,row){
	        	var house = $('#listTable').datagrid('getEditor', {
	                index: index,
	                field: 'houseId'
	            });
	        	if(house != null){
	        		var houseGrid = $(house.target).combogrid('grid');
		        	houseGrid.datagrid('reload',basicUrl+'/basicInfo/searchHouseToList.do?lineId=0&eventName='+eventName);
	        	}
	        	var material = $('#listTable').datagrid('getEditor', {
	                index: index,
	                field: 'materialId'
	            });
	        	if(material != null){
	        		var materialGrid = $(material.target).combogrid('grid');
	        		materialGrid.datagrid('reload',basicUrl+'/material/searchMaterialToList.do?pigType='+oldPigType);
	        	}
	        	var pigClass = $('#listTable').datagrid('getEditor', {
	                index: index,
	                field: 'pigClass'
	            });
	        	if(pigClass != null){
	        		var pigClassGrid = $(pigClass.target).combogrid('grid');
	        		pigClassGrid.datagrid('reload',basicUrl+'/backEnd/searchPigClassToListByEvent.do?pigType='+oldPigType+'&eventName='+eventName);
	        	}
			}
		})
	);
	$('#preSaveRecordTable').datagrid(
			j_grid_view({
				fitColumns:false,
				haveCheckBox:false,
				columnFields:'earBrand,earShort,materialIdName,onPrice,fatherEar,motherEar,lineIdName,houseIdName,pigClassName,leftTeatNum,rightTeatNum,parity,birthDate,birthWeight,enterWeight,supplierIdName,notes',
				columnTitles:'耳牌号,耳缺号,物料名称,初始成本,父亲耳号,母亲耳号,产线,猪舍名称,猪只状态,左乳头,右乳头,胎次,出生日期,出生体重(KG),入场体重(KG),供应商,备注',
				hiddenFields:'fatherEar,motherEar,lineIdName,supplierIdName,notes',
				columnWidths:'100,100,100,100,100,100,100,100,100,100,100,100,90,110,110,100,100',
				fit:false,
				width:'auto',
				height:'100%',
				pagination:false
			},'preSaveRecordTable')
	);
});

/**
 * 删除明细
 */
function detailDelete(){
	var listTable = $('#listTable');
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
					var listRows = listTable.datagrid('getRows');
					if(listRows.length <= 0){
						$('#weanNumSum').html('0');
						$('#dieNumSum').html('0');
					}else{
						var weanNumSum = 0,dieNumSum = 0,weanWeightSum = 0;
						$.each(listRows,function(i,row){
							weanNumSum += strToInt(row.weanNum == undefined ? 0 : row.weanNum);
							dieNumSum += strToInt(row.dieNum == undefined ? 0 : row.dieNum);
							weanWeightSum += strToInt(row.weanWeight == undefined ? 0 : row.weanWeight);
						});
						$('#weanNumSum').html(weanNumSum);
						$('#dieNumSum').html(dieNumSum);
						$('#weanNumAvg').html((weanNumSum/listRows.length).toFixed(2));
						$('#weanWeightAvg').html((weanWeightSum/listRows.length).toFixed(2));
					}
				}
			}
		});
	}
}
/**
 * 清空
 */
function detailClear(){
	var listTable = $('#listTable');
	$.messager.confirm('提示', '确定要清空吗？', function(r){
		if (r){
			$('#listTable').datagrid('loadData',{success:true,total: 0, rows: [] });
			$('#weanNumSum').html('0');
			$('#dieNumSum').html('0');
			$('#weanWeightAvg').html('0');
			$('#weanNumAvg').html('0');
		}
	});
}

/**
 * 重置方法
 */
function onBtnReset(){
	$.messager.confirm('提示', '重置将清空数据，确定要重置吗？', function(r){
		if (r){
			$('#editForm').form('reset');
			var listTable = document.getElementById("listTable");
			if(listTable!= null){
				$('#listTable').datagrid('loadData',{success:true,total: 0, rows: [] });
				$('#weanNumSum').html('0');
				$('#dieNumSum').html('0');
			}
		}
	});
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
//						        {field:'businessCode',title:'猪舍代码',width:100},
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

function autoFosterDieFun(autoFosterDie){
	var checkedFlag = $(autoFosterDie).is(':checked');
	var rows = $('#listTable').datagrid('getRows');
	if(rows.length > 0){
		$.messager.confirm('提示', '重置将清空数据，确定要重置吗？', function(r){
			if (r){
				if(!checkedFlag){
					$('#listTable').datagrid('showColumn', 'dieNum'); 
				}else{
					$('#listTable').datagrid('hideColumn', 'dieNum');
				}
				$('#listTable').datagrid('loadData',{success:true,total: 0, rows: [] });
				$('#weanNumSum').html('0');
				$('#dieNumSum').html('0');
			}
		});
	}else{
		if(!checkedFlag){
			$('#listTable').datagrid('showColumn', 'dieNum'); 
		}else{
			$('#listTable').datagrid('hideColumn', 'dieNum');
		}
	}
}

function selectBreedPig(){
	leftSilpFun('selectBreedPigWin');
	var pigIds = "";
	var rows = $('#listTable').datagrid('getRows');
	if(rows.length > 0){
		$.each(rows,function(i,row){
			if(row.pigIds != undefined){
				if(i == 0){
					pigIds += row.pigIds;
				}else{
					pigIds += ','+row.pigIds;
				}
			}
		});
	}
	if(pigIds == ""){
		pigIds = 0;
	}
	var earBrand = $('#earBrand').textbox('getValue');
	var farmId = $('#farmId').combobox('getValue');
//	var pigClassGrid = $('#pigClassId').combogrid('grid');
//	pigClassGrid.datagrid('reload',basicUrl+'/backEnd/searchPigClassToListByEvent.do?pigType='+oldPigType+'&eventName='+eventName);
	var columnFields='pigIds,pigId,pigType,earBrand,pigInfo,birthDate,sexName,earShort,materialId,materialName,onPrice,fatherEarBrand,motherEarBrand,pigClass,leftTeatNum,rightTeatNum,parity,enterWeight,supplierId,supplierName,pigClassName';
	var columnTitles='猪只IDS,猪只ID,猪只类别,耳牌号,耳缺号-品种(胎次)-状态-日龄-猪舍-猪栏,出生日期,性别,耳缺号,物料ID,物料名称,初始成本,父亲耳号,母亲耳号,猪只状态ID,左乳头,右乳头,胎次,入场体重,供应商ID,供应商,猪只状态';
	var columnWidths='80,80,80,100,400,100,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80';
	var url = '/production/searchXNpigToPage?earBrand='+earBrand+'&farmId='+farmId+'&pigIds='+pigIds;
	$('#selectBreedPigTable').datagrid(
			j_grid({
			width:'auto',
			height:'auto',
			url:url,
			columnFields:columnFields,
			columnTitles:columnTitles,
			columnWidths:columnWidths,
			hiddenFields:'pigIds,pigType,earShort,materialId,materialName,onPrice,fatherEarBrand,motherEarBrand,pigClass,leftTeatNum,rightTeatNum,parity,enterWeight,supplierId,supplierIdName,pigClassName',
		},'selectBreedPigTable')
	);
}

/**
 * 搜索猪只
 */
function searchPigSearch(){
	var earBrand =  $('#earBrand').textbox('getValue');
	var farmId = $('#farmId').combobox('getValue');
	var hasSelectRows = $('#listTable').datagrid('getData').rows;
	var pigIdsArray = [];
	$.each(hasSelectRows,function(i,data){
		if(data.pigIds != null && data.pigIds != ''){
			pigIdsArray.push(data.pigIds);
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
	var isValid = $('#selectPigSearchForm').form('validate');
	if(!isValid){
		$.messager.alert({
			title:'提示',
			msg:'请先选择场区'
		});
		return;
	}
//	$('#selectPigSearchForm').form('submit',{
//		url:basicUrl+'/production/searchXNpigToPage.do',
//		onSubmit: function(){
//			var isValid = $('#selectPigSearchForm').form('validate');
//			if (!isValid){
//				$.messager.progress('close');	
//				$('#btnSave').attr('disabled',false).removeClass('btn-disabled');
//			}
//			return isValid;
//		},
//		success: function(response){
//			response=eval('('+response+')');
//			if(response.success){
//				$('#selectBreedPigTable').datagrid('loadData',response);
//			}else{
//				 $.messager.alert({
//                     title: '错误',
//                     msg: response.errorMsg
//                 });
//			}
//			$.messager.progress('close');
//		}
//	})
	$('#selectBreedPigTable').datagrid('load',basicUrl+'/production/searchXNpigToPage.do?earBrand='+earBrand+'&farmId='+farmId+"&pigIds="+pigIds);
}

function selectBreedPigSure(){
	var rows =  $('#selectBreedPigTable').datagrid('getSelections');
	if(rows.length < 1){
		$.messager.alert('提示',"请选择数据！");
	}else if(rows.length > 1){
		$.messager.alert('提示',"只能选择一条数据！");
	}else{
		var pigIds = "";
		$.each(rows,function(i,row){
			if(i == 0){
				pigIds += row.pigId;
			}else{
				pigIds += ',' + row.pigId;
			}
		});
		rows = rows[0];
		var row = {
				status:'1',
				deletedFlag:'0',
				relationPigId:rows.relationPigId,
				earBrand:rows.earBrand,
				earShort:rows.earShort,
				materialId:rows.materialId,
				materialIdName:rows.materialName,
				onPrice:rows.onPrice,
				fatherEar:rows.fatherEarBrand,
				motherEar:rows.motherEarBrand,
				pigClass:rows.pigClass,
				pigClassName:rows.pigClassName,
				leftTeatNum:rows.leftTeatNum,
				rightTeatNum:rows.rightTeatNum,
				parity:rows.parity,
				birthDate:rows.birthDate,
				birthWeight:rows.birthWeight,
				enterWeight:rows.enterWeight,
				supplierId:rows.supplierId,
				supplierIdName:rows.supplierName,
				pigIds:pigIds
		};
		$('#listTable').datagrid('appendRow',row);
		rightSlipFun('selectBreedPigWin',780);
	}
}

//种猪销售重置
function onBtnReSearch(){
	$('#selectPigSearchForm').form('reset');
}


function isCheckChange(para){
	$('#listTable').datagrid('loadData',{success:true,total: 0, rows: [] });
	var checkedFalg = $(para).is(':checked');
	if(checkedFalg){
		$("#addButton").attr({"disabled":"disabled"});
		$("#addButton").css('background-color','rgb(220, 220, 220)');
		$("#selectButton").removeAttr("disabled");
		$("#selectButton").css('background-color','rgb(113, 175, 76)');
		selectButton
		$('#listTable').datagrid(
				j_detailGrid({
					height:'100%',
				dbClickEdit:true,
				//fitColumns:true,
				frozenColumns:[[
				                {field:'ck',checkbox:true},
				              	j_gridText({field:'rowId',title:'ID',hidden:true}),
				              	j_gridText({field:'deletedFlag',title:'deletedFlag',hidden:true}),
				              	j_gridText({field:'pigIds',title:'猪只ID',hidden:true}),
				              	j_gridText({field:'relationPigId',title:'猪只Id',hidden:true}),
				              	j_gridText({field:'leaveDate',title:'销售离场日期',hidden:true}),
				              	j_gridText({field:'earBrand',title:'耳牌号',width:100}),
				              	j_gridText({field:'earShort',title:'耳缺号',width:100})
				              	]],
				columns:[[
			              	//物料名称
			              	j_gridText({field:'materialIdName',title:'物料名称',width:80}),
			              	j_gridText({field:'onPrice',title:'初始成本',width:100,
			              		precision:2,
			              		increment:0.1,
			              		min:0,
			              		max:100000,
			              		formatter:function(value,row){
		              			value = value == null || value == undefined || value == '' ? 0.00 : value;
			              		return value+'元';
			              	}}),
			              	j_gridText({field:'fatherEar',title:'父亲耳号',width:100}),
			              	j_gridText({field:'motherEar',title:'母亲耳号',width:100}),
			              	//猪舍
			              	j_gridText(houseGridComboGrid({width:100})),
			              	//猪只状态
			              	j_gridText({field:'pigClassName',title:'猪只状态',width:80}),
			              	j_gridText({field:'leftTeatNum',width:100,title:'左乳头',min:1,max:10}),
			              	j_gridText({field:'rightTeatNum',width:100,title:'右乳头',min:1,max:10}),
			              	j_gridText({field:'parity',width:100,title:'胎次',min:0,max:12}),
			              	j_gridText({field:'birthDate',width:100,title:'出生日期',width:80}),
			              	j_gridText({field:'birthWeight',
			              		precision:2,
			              		increment:0.1,
			              		min:1,
			              		max:5,
			              		formatter:function(value,row){
			              			value = value == null || value == undefined || value == '' ? 0.00 : value;
			              			row.birthWeight = value;
			              			return value+'KG';
				              	},
			              		width:100,title:'出生体重'}),
			              	j_gridText({field:'enterWeight',
			              		precision:2,
			              		increment:0.1,
			              		min:1,
			              		max:500,
			              		formatter:function(value,row){
			              			value = value == null || value == undefined || value == '' ? 0.00 : value;
			              			row.enterWeight = value;
			              			return value+'KG';
				              	},
			              		width:100,title:'入场体重'}),
			              	//供应商
			              	j_gridText({field:'supplierName',title:'供应商',width:80}),
			              	j_gridText({field:'notes',title:'备注',width:100,editor:'textbox'})
					    ]],
			    onEndEdit:function(index,row){
		            var material = $(this).datagrid('getEditor', {
		                index: index,
		                field: 'materialId'
		            });
		            if(material != null){
		            	row.materialIdName = $(material.target).combogrid('getText');
		            }
		            var pigHouse = $(this).datagrid('getEditor', {
		                index: index,
		                field: 'houseId'
		            });
		            if(pigHouse != null){
		            	row.houseIdName = $(pigHouse.target).combogrid('getText');
		            }
		            var line = $(this).datagrid('getEditor', {
		                index: index,
		                field: 'lineId'
		            });
		            if(line != null){
		            	row.lineIdName = $(line.target).combogrid('getText');
		            }
		            var pigClass = $(this).datagrid('getEditor', {
		                index: index,
		                field: 'pigClass'
		            });
		            if(pigClass != null){
		            	row.pigClassName = $(pigClass.target).combogrid('getText');
		            }
		            var supplier = $(this).datagrid('getEditor', {
		                index: index,
		                field: 'supplierId'
		            });
		            if(supplier != null){
		            	row.supplierIdName = $(supplier.target).combogrid('getText');
		            }
		        },
		        onBeginEdit:function(index,row){
		        	var house = $('#listTable').datagrid('getEditor', {
		                index: index,
		                field: 'houseId'
		            });
		        	if(house != null){
		        		var houseGrid = $(house.target).combogrid('grid');
			        	houseGrid.datagrid('reload',basicUrl+'/basicInfo/searchHouseToList.do?lineId=0&eventName='+eventName);
		        	}
		        	var material = $('#listTable').datagrid('getEditor', {
		                index: index,
		                field: 'materialId'
		            });
		        	if(material != null){
		        		var materialGrid = $(material.target).combogrid('grid');
		        		materialGrid.datagrid('reload',basicUrl+'/material/searchMaterialToList.do?pigType='+oldPigType);
		        	}
		        	var pigClass = $('#listTable').datagrid('getEditor', {
		                index: index,
		                field: 'pigClass'
		            });
		        	if(pigClass != null){
		        		var pigClassGrid = $(pigClass.target).combogrid('grid');
		        		pigClassGrid.datagrid('reload',basicUrl+'/backEnd/searchPigClassToListByEvent.do?pigType='+oldPigType+'&eventName='+eventName);
		        	}
				}
			})
		);
	}else{
		$("#addButton").removeAttr("disabled");
		$("#addButton").css('background-color','rgb(113, 175, 76)');
		$("#selectButton").attr({"disabled":"disabled"});
		$("#selectButton").css('background-color','rgb(220, 220, 220)');
		$('#listTable').datagrid(
				j_detailGrid({
					height:'100%',
				dbClickEdit:true,
				//fitColumns:true,
				frozenColumns:[[
				                {field:'ck',checkbox:true},
				              	j_gridText({field:'rowId',title:'ID',hidden:true}),
				              	j_gridText({field:'deletedFlag',title:'deletedFlag',hidden:true}),
				              	j_gridText({field:'pigIds',title:'猪只ID',hidden:true}),
				              	j_gridText({field:'relationPigId',title:'猪只Id',hidden:true}),
				              	j_gridText({field:'leaveDate',title:'销售离场日期',hidden:true}),
				              	j_gridText({field:'earBrand',title:'耳牌号',width:100,editor:{
				              		type:'textbox',
				              		options:{
				              				validType:'numOrLetterOr_',
				              			}
			              			}
			                    }),
				              	j_gridText({field:'earShort',title:'耳缺号',width:100,editor:{
				              		type:'textbox',
				              		options:{
				              				validType:'numOrLetterOr_',
				              			}
			              			}
			              		})
				              	]],
				columns:[[
			              	//物料名称
			              	j_gridText(materialGridComboGrid({width:100})),
			              	j_gridNumber({field:'onPrice',title:'初始成本',width:100,editor:'textbox',
			              		precision:2,
			              		increment:0.1,
			              		min:0,
			              		max:100000,
			              		formatter:function(value,row){
		              			value = value == null || value == undefined || value == '' ? 0.00 : value;
			              		return value+'元';
			              	}}),
			              	j_gridText({field:'fatherEar',title:'父亲耳号',width:100,editor:{
			              		type:'textbox',
			              		options:{
			              				validType:'numOrLetterOr_',
			              			}
		              			}}),
			              	j_gridText({field:'motherEar',title:'母亲耳号',width:100,editor:{
			              		type:'textbox',
			              		options:{
			              				validType:'numOrLetterOr_',
			              			}
		              			}}),
			              	//猪舍
			              	j_gridText(houseGridComboGrid({width:100})),
			              	//猪只状态
			              	j_gridText({field:'pigClassName',width:100,title:'猪只状态'}),
			              	j_gridNumber({field:'leftTeatNum',width:100,title:'左乳头',min:1,max:10}),
			              	j_gridNumber({field:'rightTeatNum',width:100,title:'右乳头',min:1,max:10}),
			              	j_gridNumber({field:'parity',width:100,title:'胎次',min:0,max:12}),
			              	j_gridText({field:'birthDate',width:100,title:'出生日期',width:80,editor:{type:'datebox',options:{editable:false}}}),
			              	j_gridNumber({field:'birthWeight',
			              		precision:2,
			              		increment:0.1,
			              		min:1,
			              		max:5,
			              		formatter:function(value,row){
			              			value = value == null || value == undefined || value == '' ? 0.00 : value;
			              			row.birthWeight = value;
			              			return value+'KG';
				              	},
			              		width:100,title:'出生体重'}),
			              	j_gridNumber({field:'enterWeight',
			              		precision:2,
			              		increment:0.1,
			              		min:1,
			              		max:500,
			              		formatter:function(value,row){
			              			value = value == null || value == undefined || value == '' ? 0.00 : value;
			              			row.enterWeight = value;
			              			return value+'KG';
				              	},
			              		width:100,title:'入场体重'}),
			              	//供应商
			              	j_gridText(supplierGridComboGrid({width:100})),
			              	j_gridText({field:'notes',title:'备注',width:100,editor:'textbox'})
					    ]],
			    onEndEdit:function(index,row){
		            var material = $(this).datagrid('getEditor', {
		                index: index,
		                field: 'materialId'
		            });
		            if(material != null){
		            	row.materialIdName = $(material.target).combogrid('getText');
		            }
		            var pigHouse = $(this).datagrid('getEditor', {
		                index: index,
		                field: 'houseId'
		            });
		            if(pigHouse != null){
		            	row.houseIdName = $(pigHouse.target).combogrid('getText');
		            }
		            var line = $(this).datagrid('getEditor', {
		                index: index,
		                field: 'lineId'
		            });
		            if(line != null){
		            	row.lineIdName = $(line.target).combogrid('getText');
		            }
		            var pigClass = $(this).datagrid('getEditor', {
		                index: index,
		                field: 'pigClass'
		            });
		            if(pigClass != null){
		            	row.pigClassName = $(pigClass.target).combogrid('getText');
		            }
		            var supplier = $(this).datagrid('getEditor', {
		                index: index,
		                field: 'supplierId'
		            });
		            if(supplier != null){
		            	row.supplierIdName = $(supplier.target).combogrid('getText');
		            }
		        },
		        onBeginEdit:function(index,row){
		        	var house = $('#listTable').datagrid('getEditor', {
		                index: index,
		                field: 'houseId'
		            });
		        	if(house != null){
		        		var houseGrid = $(house.target).combogrid('grid');
			        	houseGrid.datagrid('reload',basicUrl+'/basicInfo/searchHouseToList.do?lineId=0&eventName='+eventName);
		        	}
		        	var material = $('#listTable').datagrid('getEditor', {
		                index: index,
		                field: 'materialId'
		            });
		        	if(material != null){
		        		var materialGrid = $(material.target).combogrid('grid');
		        		materialGrid.datagrid('reload',basicUrl+'/material/searchMaterialToList.do?pigType='+1);
		        	}
		        	var pigClass = $('#listTable').datagrid('getEditor', {
		                index: index,
		                field: 'pigClass'
		            });
		        	if(pigClass != null){
		        		var pigClassGrid = $(pigClass.target).combogrid('grid');
		        		pigClassGrid.datagrid('reload',basicUrl+'/backEnd/searchPigClassToListByEvent.do?pigType='+1+'&eventName='+eventName);
		        	}
				}
			})
		);
	}
}

