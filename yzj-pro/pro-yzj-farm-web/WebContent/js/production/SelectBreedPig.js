var prUrl='/event/';
var saveUrl=prUrl+'editSelectionPig.do';
var editIndex = undefined;
var eventName = 'SELECT_BREED_PIG';
var oldPigType = '';

$(document).ready(function(){
	$('#listTable').datagrid(
			j_eventGrid({
			dbClickEdit:true,
			fitColumns:false,
			frozenColumns:[[
						{field:'ck',checkbox:true},
						j_gridText({field:'rowId',title:'ID',hidden:true}),
						j_gridText({field:'pigId',title:'猪只ID',hidden:true}),
						j_gridText({field:'houseId',title:'猪舍ID',hidden:true}),
						j_gridText({field:'materialId',title:'物料ID',hidden:true}),
						j_gridText({field:'earCodeId',title:'耳号ID',hidden:true}),
						j_gridText({field:'swineryId',title:'批次ID',hidden:true}),
						j_gridText({field:'pigpenId',title:'猪栏ID',hidden:true}),
						j_gridText({field:'minValidDate',title:'minValidDate',hidden:true}),
						j_gridText({field:'maxValidDate',title:'maxValidDate',hidden:true}),
						j_gridText({field:'deletedFlag',title:'deletedFlag',hidden:true}),
						//耳牌号
						j_gridText(earBrandTextBox({field:'earShort',textField:'earShort',valueField:'earShort',title:'耳缺号',width:150})),
						j_gridText({
								field:'selectBreedType',
								title:'选种类型',
								width:80,
								formatter:function(value,row){
									return row.selectBreedTypeName;
								},
								editor:
								xnGridCdComboBox({
									field:'selectBreedType',
									typeCode:'SELECT_BREED_TYPE',
									editable:false,
									onChange: changeSelectBreedType
								})
							}),
							j_gridText({field:'sex',title:'性别',width:80,formatter:function(value,row){
								return row.sexName;
							}}),     
			]],
			columns:[[
			          	
						j_gridText({field:'pigInfo',title:'耳缺号    -  品种   -  胎次   -  状态(天)  -  日龄  - 猪舍  - 猪栏',width:500}),
						j_gridText({field:'newEarBrand',title:'耳牌号',width:150,editor:{
		              		type:'textbox',
		              		options:{
		              			validType:'numOrLetterOr_'
		              		}
		              	}}),
						//猪舍
		              	j_gridText(houseGridComboGrid({
		              		field:'inHouseId',
		              		title:'转入猪舍',
		              		width:100,
		              		formatter:function(value,row){
		              			return row.inHouseIdName
		              			},
		              		url:'/basicInfo/searchHouseToList.do?lineId=0&eventName='+eventName,
		              		onChange:inHouseChangeFun
	              		})),
		              	//猪栏
		              	j_gridText(pigpenGridComboGrid({
		              		field:'inPigpenId',
		              		title:'转入猪栏',
		              		width:100,
		              		formatter:function(value,row){
		              			return row.inPigpenIdName
		              			}
	              		})),
	              		j_gridNumber({field:'weight',title:'重量',min:0,
		              		precision:2,
							increment:0.1,
		              		formatter:function(value,row){
		              			value = value == null || value == undefined || value == '' ? 0.00 : value;
		              			row.weight = value;
		              			return value+'KG';
			              	},
			              	width:100}),
		              	j_gridText({field:'enterDate',title:'选种日期',width:150,editor:{type:'datebox',options:{editable:false}}}),
		              	//技术员
		              	j_gridText(workerGridComboGrid({width:100})),
		              	j_gridText({field:'notes',title:'备注',width:80,editor:'textbox'})
				    ]],
			    onEndEdit:function(index,row){
			    	 var inHouse = $('#listTable').datagrid('getEditor', {
		                index: index,
		                field: 'inHouseId'
		            });
			    	 if(inHouse != null){
			            	row.inHouseIdName = $(inHouse.target).combogrid('getText');
			            }
		            var inPigpen = $('#listTable').datagrid('getEditor', {
		                index: index,
		                field: 'inPigpenId'
		            });
		            if(inPigpen != null){
		            	 row.inPigpenIdName = $(inPigpen.target).combogrid('getText');
		            }
		            var selectBreedType = $('#listTable').datagrid('getEditor', {
		                index: index,
		                field: 'selectBreedType'
		            });
		            if(selectBreedType != null){
		            	returnTarName({
		            		tarType:'combobox',
		            		tarName:'codeName',
		            		tarFiled:'selectBreedType',
		            		target:selectBreedType.target
		            	},row)
		            }
			    },
			    onBeginEdit:function(index,row){
			    	var inPigpen = $('#listTable').datagrid('getEditor', {
				        index: index,
				        field: 'inPigpenId'
				    });
					if(inPigpen != null){
						var inPigpenGrid = $(inPigpen.target).combogrid('grid');
						var inHouseId = row.inHouseId == null || row.inHouseId == undefined || row.inHouseId == ''? 0 : row.inHouseId;
						inPigpenGrid.datagrid('reload',basicUrl+'/basicInfo/searchPigpenToList.do?mainId='+inHouseId);
					}
			    }
			})
	 );
	
	$('#preSaveRecordTable').datagrid(
			j_grid_view({
				fitColumns:false,
				haveCheckBox:false,
				columnFields:'pigId,earShort,selectBreedTypeName,sexName,pigInfo,inHouseIdName,inPigpenIdName,weight,enterDate,workerName,notes',
				columnTitles:'猪只ID,耳缺号,选种类型,性别,耳缺号    -  品种   -  胎次   -  状态(天)  -  日龄  - 猪舍  - 猪栏,转入猪舍,转入猪栏,重量,选种日期,技术员,备注',
				hiddenFields:'pigId,notes,workerName',
				columnWidths:'100,100,100,100,370,100,100,100,100,80,90',
				fit:false,
				width:'auto',
				height:'100%',
				pagination:false
			},'preSaveRecordTable')
	);
});
//选种类型改变
var selectBreedTypeChangeFlag = true;
function changeSelectBreedType(newValue,oldValue){
	if(editIndex == undefined){
		return;
	}
	if(selectBreedTypeChangeFlag){
		selectBreedTypeChangeFlag = false;
		$.messager.confirm('提示', '修改选种类型会改变猪只性别，确定要修改吗？', function(r){
			if (!r){
				var selectBreedType = $('#listTable').datagrid('getEditor', {
	                index: editIndex,
	                field: 'selectBreedType'
	            });
				if(selectBreedType !=  null){
					$(selectBreedType.target).combobox('setValue',oldValue);
				}
				
			}else{
				var row = $('#listTable').datagrid('getRows')[editIndex];
				if(newValue == '1'){
					row.sex = '1';
					row.sexName = '公';
				}else if(newValue == '2'){
					row.sex = '2';
					row.sexName = '母';
				}
				changeTableDisplayValue('listTable',editIndex,[{
					field:'sex',
					value:row.sexName
				}]);
				selectBreedTypeChangeFlag = true;
			}
		});
	}else{
		selectBreedTypeChangeFlag = true;
	}
}
/**
 *  转入猪舍改变方法
 * @param newValue
 * @param oldValue
 */
function inHouseChangeFun(newValue,oldValue){
	if(editIndex == undefined){
		return;
	}
	var rows = $('#listTable').datagrid('getRows');
	rows[editIndex].inPigpenId = '';
	rows[editIndex].inPigpenIdName = '';
	changeTableDisplayValue('listTable',editIndex,[{
		field:'inPigpenId',
		value:''
	}]);
}