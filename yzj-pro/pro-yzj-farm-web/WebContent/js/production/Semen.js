var prUrl='/production/';
var saveUrl=prUrl+'semen.do';
var editIndex = undefined;
var eventName = 'SEMEN';
//采精默认仓库searchSpermWarehouseName
//默认值为1
var oldPigType = 1;
//明细默认值
var detailDefaultValues = {
	status:'1',
	deletedFlag:'0',
	//设置默认值
	semenQty:200,
	anhNum:20
};
$(document).ready(function(){
	$('#listTable').datagrid(
			j_eventGrid({
			dbClickEdit:true,
			fitColumns:false,
			//固定列
			frozenColumns:[[
			                {field:'ck',checkbox:true},
			              	j_gridText({field:'rowId',title:'ID',hidden:true}),
			              	j_gridText({field:'pigId',title:'猪只ID',hidden:true}),
			              	j_gridText({field:'houseId',title:'猪舍ID',hidden:true}),
			              	j_gridText({field:'minValidDate',title:'minValidDate',hidden:true}),
			              	j_gridText({field:'maxValidDate',title:'maxValidDate',hidden:true}),
			              	j_gridText({field:'deletedFlag',title:'deletedFlag',hidden:true}),
			              	//耳牌号
			              	j_gridText(earBrandTextBox({width:150}))
			              	]],
			columns:[[
			          	j_gridText({field:'pigInfo',title:'耳缺号-品种(胎次)-状态-日龄-猪舍-猪栏',width:450}),
			          	j_gridText(workerGridComboGrid({
			          		field:'analust',
			          		title:'化验员',
			          		width:100,
			          		formatter:function(value,row){
		              			return row.analustName;
		              		},
			          	})),
		              	j_gridText({field:'enterDate',title:'采精日期',width:150,editor:{
		              		type:'datebox',
		    				options:{editable:false}
		              	}
		              	}),
		              	j_gridText({field:'color',title:'精液颜色',width:80,
		              		formatter:function(value,row){
		              			return row.colorName;
		              		},
		              		editor:
		              		xnGridCdComboBox({
		              			field:'color',
		              			typeCode:'SEMEN_COLOR'
		              		})
		              	}),
		              	j_gridText({field:'odour',title:'精液气味',width:80,
		              		formatter:function(value,row){
		              			return row.odourName;
		              		},
		              		editor:
		              		xnGridCdComboBox({
		              			field:'odour',
		              			typeCode:'SEMEN_ODOUR'
		              		})
		              	}),
		              	j_gridText({field:'cohesion',title:'凝聚度',width:80,
		              		formatter:function(value,row){
		              			return row.cohesionName;
		              		},
		              		editor:
		              		xnGridCdComboBox({
		              			field:'cohesion',
		              			typeCode:'COHESION'
		              		})
		              	}),
		              	j_gridNumber({field:'semenQty',title:'精液量',min:0,
		              		precision:2,
							increment:0.1,
			              	formatter:function(value,row){
			              		value = value == null || value == undefined || value == '' ? 0 : value;
			              		return value+'ML';
			              	},
			            value:100,
		              	width:80}),
		              	j_gridNumber({field:'anhNum',title:'稀释份数',width:80,min:0,
							increment:1}),
		              	j_gridNumber({field:'spermMotility',title:'精子活力',min:0,
		              		precision:2,
							increment:0.1,
		              		formatter:function(value,row){
		              			value = value == null || value == undefined || value == '' ? 0 : value;
			              		return value+'%';
			              	},
			              	width:80}),
		              	j_gridNumber({field:'spermDensity',title:'精子密度',min:0,
		              		precision:2,
							increment:0.1,
		              		formatter:function(value,row){
		              			value = value == null || value == undefined || value == '' ? 0 : value;
		              			return value+'亿/ML';
		              		},
		              		width:80}),
		              	j_gridNumber({field:'abnormationRate',title:'畸形率',min:0,max:100,
		              		precision:2,
							increment:0.1,
		              		formatter:function(value,row){
		              			value = value == null || value == undefined || value == '' ? 0 : value;
			              		return value+'%';
			              	},
			              	width:80}),
		              	j_gridNumber({field:'spec',title:'规格',
		              		formatter:function(value,row){
		              			value = value == null || value == undefined || value == '' ? 0 : value;
			              		return value+'ML/瓶';
			              	},
			              	width:80}),
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
			            //技术员
		              	j_gridText(workerGridComboGrid({width:80})),
		              	j_gridText({field:'notes',title:'备注',width:80,editor:'textbox'})
				    ]],
		    onEndEdit:function(index,row){
		    	 var color = $('#listTable').datagrid('getEditor', {
	                index: index,
	                field: 'color'
	            });
		    	if(color != null){
		    		row.colorName = $(color.target).combogrid('getText');
		    	}
	            var odour = $('#listTable').datagrid('getEditor', {
	                index: index,
	                field: 'odour'
	            });
	            if(odour != null ){
	            	row.odourName = $(odour.target).combogrid('getText');
	            }
	            var cohesion = $('#listTable').datagrid('getEditor', {
	                index: index,
	                field: 'cohesion'
	            });
	            if(cohesion != null){
	            	row.cohesionName = $(cohesion.target).combogrid('getText');
	            }
	            var analust = $('#listTable').datagrid('getEditor', {
	                index: index,
	                field: 'analust'
	            });
	            if(analust != null){
	            	row.analustName = $(analust.target).combogrid('getText');
	            }
	            var warehouse = $('#listTable').datagrid('getEditor', {
	                index: index,
	                field: 'warehouseId'
	            });
		    	if(warehouse != null){
		    		row.warehouseIdName = $(warehouse.target).combogrid('getText');
		    	}
	        },
	        onBeginEdit:function(index,row){
	        	var analust = $('#listTable').datagrid('getEditor', {
			        index: index,
			        field: 'analust'
			    });
				if(analust != null){
					var analustGrid = $(analust.target).combogrid('grid');
					var houseId = row.houseId == null || row.houseId == undefined || row.houseId == ''? 0 : row.houseId;
					analustGrid.datagrid('reload',basicUrl+'/UserManage/searchEmployeeByIdToList.do?houseId='+houseId);
				}
				var warehouse = $('#listTable').datagrid('getEditor', {
	                index: index,
	                field: 'warehouseId'
	            });
				if(warehouse != null){
					var warehouseGrid = $(warehouse.target).combogrid('grid');
					warehouseGrid.datagrid('load',basicUrl+'/production/searchSpermWarehouseName.do');
				}
	        }
		})
	);
	
	$('#preSaveRecordTable').datagrid(
			j_grid_view({
				fitColumns:false,
				haveCheckBox:false,
				columnFields:'pigId,earBrand,pigInfo,analustName,enterDate,colorName,odourName,cohesionName,semenQty,anhNum,spermMotility,spermDensity,abnormationRate,spec,workerName,notes',
				columnTitles:'猪只ID,耳牌号,耳缺号-品种(胎次)-状态-日龄-猪舍-猪栏,化验员,采精日期,精液颜色,精液气味,凝聚度,精液量(ML),稀释份数,精子活力(%),精子密度(亿/ML),畸形率(%),规格(ML/瓶),技术员,备注',
				hiddenFields:'pigId,notes,workerName',
				columnWidths:'100,100,350,100,90,100,100,80,100,100,100,130,100,100,100,100',
				fit:false,
				width:'auto',
				height:'100%',
				pagination:false
			},'preSaveRecordTable')
	);
	//采精默认仓库searchSpermWarehouseName
	//采精事件仓库默认填写精液仓库，启用人工授精使用库存精液设置后，保存时没有选择精液仓库的提示需新建仓库
	jAjax.submit('/production/searchSpermWarehouseName.do',null
			,function(data){
//				if(data.length == 0){
//					$.messager.alert('警告','没有已经初始化过的仓库，请创建并初始化仓库！');
//					$('#toolbar').children().attr('disabled',true).addClass('btn-disabled');
//					$('#btnSave').attr('disabled',true).addClass('btn-disabled');
//				}else
				if(data.length == 1){
					detailDefaultValues.warehouseId = data[0].rowId;
					detailDefaultValues.warehouseIdName = data[0].warehouseName;
				}
			}
			,null,'GET',true);
});
/***********************************************************************************************************************************************/
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
				}
			}
		});
	}
}
