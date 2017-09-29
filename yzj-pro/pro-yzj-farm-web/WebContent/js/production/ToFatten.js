var prUrl='/production/';
var saveUrl=prUrl+'toChildFatten.do';
var editIndex = undefined;
var eventName = 'TO_CHILD_FATTEN';
//默认值为3
var oldPigType = 3;
var changeHouseData = [];
var changeHouseDefaultValue = null;
var changeHouseDefaultName = '';
$.ajax({  
    url : basicUrl+'/param/searchByTypeCode.do?typeCode=FATTEN_CHANGE_TYPE',
    async : false, 
    type : "POST",  
    dataType : "json",  
    success : function(response) { 
    	changeHouseData = response;
    	$.each(changeHouseData,function(index,row){
    		if(row.isDefault == 'Y'){
    			changeHouseDefaultValue = row.codeValue;
    			changeHouseDefaultName = row.codeName;
    		}
    	});
    }  
});
var detailDefaultValues = {
		status:'1',
		deletedFlag:'0',
		//设置默认值
		changeHouseType:changeHouseDefaultValue,
		changeHouseTypeName:changeHouseDefaultName,
		mergeSwinery:'Y',
		mergeSwineryName:'是'
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
			              	j_gridText({field:'pigIds',title:'猪只IDs',hidden:true}),
			              	j_gridText({field:'minValidDate',title:'minValidDate',hidden:true}),
			              	j_gridText({field:'maxValidDate',title:'maxValidDate',hidden:true}),
			              	j_gridText({field:'deletedFlag',title:'deletedFlag',hidden:true}),
			              	j_gridText({field:'changeHouseType',title:'转舍类型',width:80,
								formatter:function(value,row){
									return row.changeHouseTypeName;
								},
								editor:
								xnGridCdComboBox({
									field:'changeHouseType',
									typeCode:'FATTEN_CHANGE_TYPE',
									editable:false,
									onChange:changeHouseTypeChangeFun
								})
							}),
							j_gridText({
			              		field:'mergeSwinery',
			              		title:'合并批次',
			              		width:80,
			              		formatter:function(value,row){
			              			return row.mergeSwineryName;
			              		},
			              		editor:
			              		xnGridCdComboBox({
			              			field:'mergeSwinery',
			              			typeCode:'YES_OR_NO'
			              		})
			              	}),
			              	//猪舍
			              	j_gridText(houseGridComboGrid({
			              		width:100,
			              		onSelect:houseSelectFun,
			              		onChange:houseChangeFun
		              		})),
		              	//猪栏
			              	j_gridText(pigpenGridComboGrid({
			              		field:'pigpenId',
			              		title:'猪栏名称',
			              		multiple:true,
			              		onChange:pigpenChangeFun,
			              		width:80,
			              		formatter:function(value,row){
			              			return row.pigpenIdName
			              			}
		              		})),
		              		//批次
		              		j_gridText(swineryGridComboGrid({
		              			field:'oldSwineryId',
			              		width:200,
			              		idField:'swineryId',
			              		onSelect:oldSwinerySelectFun
		              		})),
		              		j_gridText({field:'pigInfo',title:'猪只信息',width:150}),
			              	]],
			columns:[[
			          	j_gridText({field:'pigQty',title:'猪只数量',width:80}),
		              	j_gridNumber({field:'childQty',title:'转育肥数量',width:80}),
		              	j_gridNumber({field:'childWeight',title:'总重量',min:0,
		              		precision:2,
							increment:0.1,
		              		formatter:function(value,row){
		              			value = value == null || value == undefined || value == '' ? 0.00 : value;
		              			row.value = value;
		              			return value+'KG';
			              	},
			              	width:80}),
		              	//猪舍
		              	j_gridText(houseGridComboGrid({
		              		field:'inHouseId',
		              		title:'转入猪舍',
		              		width:80,
		              		formatter:function(value,row){
		              			return row.inHouseIdName
		              			},
		              		url:'/basicInfo/searchHouseToList.do?lineId=0&houseType=1,9',
		              		onChange:inHouseChangeFun
	              		})),
		              	//猪栏
		              	j_gridText(pigpenGridComboGrid({
		              		field:'inPigpenId',
		              		title:'转入猪栏',
		              		width:80,
		              		multiple:true,
		              		formatter:function(value,row){
		              			return row.inPigpenIdName
		              			}
	              		})),
	              		j_gridText(swineryGridComboGrid({
		              		width:200,
		              		title:'转入猪群',
		              		idField:'swineryId'
	              		})
	              		),
		              	j_gridText({field:'enterDate',title:'转育肥日期',width:150,editor:{type:'datebox',options:{editable:false}}}),
		              	//技术员
		              	j_gridText(workerGridComboGrid({width:80})),
		              	j_gridText({field:'notes',title:'备注',width:80,editor:'textbox'})
				    ]],
		    onEndEdit:function(index,row){
		    	var mergeSwinery = $('#listTable').datagrid('getEditor', {
	                index: index,
	                field: 'mergeSwinery'
	            });
		    	if(mergeSwinery != null){
		    		row.mergeSwineryName = $(mergeSwinery.target).combogrid('getText');
		    	}
		    	var changeHouseType = $('#listTable').datagrid('getEditor', {
	                index: index,
	                field: 'changeHouseType'
	            });
	            if(changeHouseType != null){
	            	row.changeHouseTypeName = $(changeHouseType.target).combogrid('getText');
	            }
	            var inHouse = $('#listTable').datagrid('getEditor', {
	                index: index,
	                field: 'inHouseId'
	            });
	            if(inHouse != null){
	            	returnTarName({
	            		tarType:'grid',
	            		tarName:'houseName',
	            		tarFiled:'inHouseId',
	            		target:inHouse.target
	            	},row)
	            }
	            var inPigpen = $('#listTable').datagrid('getEditor', {
	                index: index,
	                field: 'inPigpenId'
	            });
	            if(inPigpen != null){
	            	returnTarName({
	            		tarType:'grid',
	            		tarName:'pigpenName',
	            		tarFiled:'inPigpenId',
	            		target:inPigpen.target
	            	},row)
	            }
	            var house = $('#listTable').datagrid('getEditor', {
	                index: index,
	                field: 'houseId'
	            });
	            if(house != null){
	            	returnTarName({
	            		tarType:'grid',
	            		tarName:'houseName',
	            		tarFiled:'houseId',
	            		target:house.target
	            	},row)
	            }
	            var pigpen = $('#listTable').datagrid('getEditor', {
	                index: index,
	                field: 'pigpenId'
	            });
	            if(pigpen != null){
	            	returnTarName({
	            		tarType:'grid',
	            		tarName:'pigpenName',
	            		tarFiled:'pigpenId',
	            		target:pigpen.target
	            	},row)
	            }
	            var swinery = $('#listTable').datagrid('getEditor', {
	                index: index,
	                field: 'swineryId'
	            });
	            if(swinery != null){
	            	returnTarName({
	            		tarType:'grid',
	            		tarName:'swineryName',
	            		tarFiled:'swineryId',
	            		target:swinery.target
	            	},row)
	            }
	            var oldSwinery = $('#listTable').datagrid('getEditor', {
	                index: index,
	                field: 'oldSwineryId'
	            });
	            if(oldSwinery != null){
	            	returnTarName({
	            		tarType:'grid',
	            		tarName:'swineryName',
	            		tarFiled:'oldSwineryId',
	            		target:oldSwinery.target
	            	},row)
	            }
		    },
		    onBeginEdit:function(index,row){
		    	if(row.changeHouseType == '' || row.changeHouseType == undefined) {
					return;
				}
		    	var house = $('#listTable').datagrid('getEditor', {
			        index: index,
			        field: 'houseId'
			    });
				if(house != null){
					var houseGrid = $(house.target).combogrid('grid');
					if(row.changeHouseType == 5){
						houseGrid.datagrid('reload',basicUrl+'/basicInfo/searchHouseToList.do?lineId=0&houseType=1,9&eventName='+eventName +'&changeType=' + row.changeHouseType);
					}else{
						houseGrid.datagrid('reload',basicUrl+'/basicInfo/searchHouseToList.do?lineId=0&houseType=1,8&eventName='+eventName +'&changeType=' + row.changeHouseType);
					}
				}
				var swinery = $('#listTable').datagrid('getEditor', {
			        index: index,
			        field: 'swineryId'
			    });
				if(swinery != null){
					if(row.mergeSwinery == 'Y'){
						var swineryGrid = $(swinery.target).combogrid('grid');
						var inHouseId = row.inHouseId == null || row.inHouseId == undefined || row.inHouseId == ''? 0 : row.inHouseId;
						swineryGrid.datagrid('reload',basicUrl+'/production/searchPorkToList.do?lineId=0&pigType=3&houseId='+inHouseId+'&eventName='+ eventName +'&changeType=' + row.changeHouseType);
					}else{
						$(swinery.target).combogrid('disable');
					}
				}
				var oldSwinery = $('#listTable').datagrid('getEditor', {
			        index: index,
			        field: 'oldSwineryId'
			    });
				if(oldSwinery != null){
					var oldSwineryGrid = $(oldSwinery.target).combogrid('grid');
					var houseId = row.houseId == null || row.houseId == undefined || row.houseId == ''? 0 : row.houseId;
					if(row.pigpenId != undefined){
						oldSwineryGrid.datagrid('reload',basicUrl+'/production/searchPorkToList2.do?lineId=0&pigType=3&houseId='+houseId+'&eventName='+ eventName +'&changeType=' + row.changeHouseType + '&pigpenId=' + row.pigpenId);
					}else{
						oldSwineryGrid.datagrid('reload',basicUrl+'/production/searchPorkToList2.do?lineId=0&pigType=3&houseId='+houseId+'&eventName='+ eventName +'&changeType=' + row.changeHouseType );
					}
				}
				var inPigpen = $('#listTable').datagrid('getEditor', {
			        index: index,
			        field: 'inPigpenId'
			    });
				if(inPigpen != null){
					var inPigpenGrid = $(inPigpen.target).combogrid('grid');
					var inHouseId = row.inHouseId == null || row.inHouseId == undefined || row.inHouseId == ''? 0 : row.inHouseId;
					inPigpenGrid.datagrid('reload',basicUrl+'/basicInfo/searchPigpenToList.do?mainId='+inHouseId);
				}
				var pigpen = $('#listTable').datagrid('getEditor', {
			        index: index,
			        field: 'pigpenId'
			    });
				if(pigpen != null){
					var pigpenGrid = $(pigpen.target).combogrid('grid');
					var houseId = row.houseId == null || row.houseId == undefined || row.houseId == ''? 0 : row.houseId;
					if(row.changeHouseType == 5){
						pigpenGrid.datagrid('reload',basicUrl+'/basicInfo/searchValidPigpenToList.do?lineId=0&houseType=9&eventName=' + eventName + '&changeType='+row.changeHouseType + '&houseId=' + houseId);
					}else{
						pigpenGrid.datagrid('reload',basicUrl+'/basicInfo/searchValidPigpenToList.do?lineId=0&houseType=8&eventName=' + eventName + '&changeType='+row.changeHouseType + '&houseId=' + houseId);
					}
				}
			}
		})
	);
	
	$('#preSaveRecordTable').datagrid(
			j_grid_view({
				fitColumns:false,
				haveCheckBox:false,
				columnFields:'pigId,changeHouseTypeName,mergeSwineryName,houseIdName,pigpenIdName,swineryIdName,pigInfo,pigQty,childQty,childWeight,inHouseIdName,inPigpenIdName,enterDate,workerName,notes',
				columnTitles:'猪只ID,转舍类型,合并批次,猪舍名称,猪栏名称,批次名称,猪只信息,猪只数量,转育肥数量,重量(KG),转入猪舍,转入猪栏,转育肥日期,技术员,备注',
				hiddenFields:'pigId,workerName,notes',
				columnWidths:'100,100,100,100,100,100,350,90,100,100,100,100,100,100,100',
				fit:false,
				width:'auto',
				height:'100%',
				pagination:false
			},'preSaveRecordTable')
	);
	//猪只状态
	xnComboGrid({
		id:'pigClassId',
		idField:'rowId',
		textField:'pigClassName',
		url:'/backEnd/searchPigClassToListByEvent.do?pigType='+oldPigType+'&eventName='+eventName,
		width:550,
		columns:[[ 	
		           	{field:'rowId',title:'ID',width:100,hidden:true},
			        {field:'businessCode',title:'状态代码',width:100},
			        {field:'pigClassName',title:'状态名称',width:100},
			        {field:'notes',title:'备注',width:100}
			    ]]
	});
	//品种
	xnComboGrid({
		id:'breedId',
		idField:'rowId',
		textField:'breedName',
		url:'/backEnd/searchBreedToList.do',
		editable:true,
		width:550,
		columns:[[ 	
		           	{field:'rowId',title:'ID',width:100,hidden:true},
			        {field:'breedCode',title:'品种代码',width:100},
			        {field:'breedName',title:'品种名称',width:100},
			        {field:'notes',title:'备注',width:100}
			    ]]
	});
	//性别
	xnCdCombobox({
		id:'sex',
		typeCode:'PIG_SEX'
	});
	//转舍类型
	xnCdCombobox({
		id:'changeHouseType',
		typeCode:'FATTEN_CHANGE_TYPE',
		onChange:searchChangeHouseTypeChange
	});
	var houseUrl = '/basicInfo/searchHouseToList.do?lineId=0&eventName=' + eventName + '&changeType='+changeHouseDefaultValue;
	if(changeHouseDefaultValue == 5){
		houseUrl += '&houseType=9';
	}else{
		houseUrl += '&houseType=8';
	}
	//猪舍
	xnComboGrid({
		id:'houseId',
		idField:'rowId',
		textField:'houseName',
		url:houseUrl,
		editable:true,
		width:550,
		onChange:searchHouseChange,
		columns:[[ 	
					{field:'rowId',title:'ID',width:100,hidden:true},
			        {field:'houseName',title:'猪舍名称',width:100},
			        {field:'houseVolume',title:'猪舍容量',width:100},
			        {field:'pigQty',title:'猪只数量',width:100},
			        {field:'houseTypeName',title:'猪舍类别',width:100},
			        {field:'notes',title:'备注',fitColumns:true}
			    ]]
	});
	//猪群
	xnComboGrid({
		id:'swineryId',
		idField:'swineryId',
		textField:'swineryName',
		editable:true,
		width:550,
		columns:[[ 	
					{field:'rowId',title:'ID',width:100,hidden:true},
					{field:'swineryName',title:'批次名称',width:100},
					{field:'pigQty',title:'猪只数量',width:100},
					{field:'notes',title:'备注',width:100}
			    ]]
	});
	$('#searchToFattenPigTable').datagrid(
			j_grid({
			width:'auto',
			height:'auto',
			columnFields:'pigId,pigType,earBrand,pigClassName,birthDate,swineryName,sexName,houseName,dayAge,houseId,swineryId,minValidDate,maxValidDate',
			columnTitles:'猪只ID,猪只类别,耳牌号,状态,出生日期,猪群,性别,猪舍,日龄,猪舍ID,批次ID,最小有效日期,最大有效日期',
			hiddenFields:'pigId,pigType,dayAge,houseId,swineryId,minValidDate,maxValidDate',
		},'selectBreedPigTable')
	);
});
function searchChangeHouseTypeChange(newValue,oldValue){
	$('#houseId').combogrid('setValue','');
	var houseGrid = $('#houseId').combogrid('grid');
	if(newValue == 5){
		houseGrid.datagrid('reload',basicUrl+'/basicInfo/searchHouseToList.do?lineId=0&houseType=9&eventName=' + eventName + '&changeType='+newValue);
	}else{
		houseGrid.datagrid('reload',basicUrl+'/basicInfo/searchHouseToList.do?lineId=0&houseType=8&eventName=' + eventName + '&changeType='+newValue);
	}
}
function searchHouseChange(newValue,oldValue){
    $('#swineryId').combogrid('setValue','');
	var swineryGrid = $('#swineryId').combogrid('grid');
	var houseId = newValue == null || newValue == undefined || newValue == ''? 0 : newValue;
	var changeHouseType = $('#changeHouseType').combobox('getValue');
	swineryGrid.datagrid('reload',basicUrl+'/production/searchPorkToList.do?lineId=0&pigType=3&houseId='+houseId+'&eventName='+ eventName +'&changeType=' + changeHouseType);
}

function onBtnReSearch(){
	$('#selectPigSearchForm').form('reset');
}
/**
 * 转舍类型改变方法
 */
function changeHouseTypeChangeFun(newValue,oldValue){
	if(editIndex == undefined){
		return;
	}
	var rows = $('#listTable').datagrid('getRows');
	rows[editIndex].houseId = '';
	rows[editIndex].houseIdName = '';
	rows[editIndex].inHouseId = '';
	rows[editIndex].inHouseIdName = '';
	rows[editIndex].swineryId = '';
	rows[editIndex].oldSwineryId = '';
	rows[editIndex].swineryIdName = '';
	rows[editIndex].oldSwineryName = '';
	rows[editIndex].pigInfo = '';
	rows[editIndex].pigQty = '';
	rows[editIndex].inPigpenId = '';
	rows[editIndex].minValidDate = '';
	rows[editIndex].maxValidDate = '';
	rows[editIndex].pigpenId = '';
	
	changeTableDisplayValue('listTable',editIndex,[{
		field:'houseId',
		value:''
	},{
		field:'swineryId',
		value:''
	},{
		field:'oldSwineryId',
		value:''
	},{
		field:'houseId',
		value:''
	},{
		field:'inHouseId',
		value:''
	},{
		field:'pigInfo',
		value:''
	},{
		field:'pigQty',
		value:''
	},{
		field:'pigQty',
		value:''
	},{
		field:'inPigpenId',
		value:''
	},{
		field:'minValidDate',
		value:''
	},{
		field:'maxValidDate',
		value:''
	},{
		field:'pigpenId',
		value:''
	}]);
//	setTimeout(function () {
//		$('#listTable').datagrid('endEdit',editIndex);
//		$('#listTable').datagrid('updateRow',{
//			index:editIndex,
//			row:rows[editIndex]
//		});
//	 },100);
}
/**
 * 猪舍改变方法
 */
function houseChangeFun(newValue,oldValue){
	if(oldValue == ''){
		return;
	}
	var rows = $('#listTable').datagrid('getRows');
	rows[editIndex].oldSwineryId = '';
	rows[editIndex].oldSwineryName = '';
	rows[editIndex].minValidDate = '';
	rows[editIndex].maxValidDate = '';
	rows[editIndex].pigpenId = '';
	changeTableDisplayValue('listTable',editIndex,[{
		field:'oldSwineryId',
		value:''
	},{
		field:'minValidDate',
		value:''
	},{
		field:'maxValidDate',
		value:''
	},{
		field:'pigpenId',
		value:''
	}]);
	/*setTimeout(function () {
		$('#listTable').datagrid('endEdit',editIndex);
		$('#listTable').datagrid('updateRow',{
			index:editIndex,
			row:rows[editIndex]
		});
	 },100);*/
}
/**
 * 猪栏改变方法
 */
function pigpenChangeFun(newValue,oldValue){
	if(oldValue == ''){
		return;
	}
	var rows = $('#listTable').datagrid('getRows');
	rows[editIndex].oldSwineryId = '';
	rows[editIndex].oldSwineryIdName = '';
	rows[editIndex].pigInfo = '';
	rows[editIndex].pigQty = '';
	changeTableDisplayValue('listTable',editIndex,[{
		field:'oldSwineryId',
		value:''
	},{
		field:'pigInfo',
		value:''
	},{
		field:'pigQty',
		value:''
	}]);
}
/**
 * 猪舍选择方法
 */
function houseSelectFun(index,row){
	var rows = $('#listTable').datagrid('getRows');
	rows[editIndex].pigQty = row.pigQty;
	rows[editIndex].minValidDate = row.minValidDate;
	rows[editIndex].maxValidDate = row.maxValidDate;
	rows[editIndex].enterDate = row.lastEventDate;
	rows[editIndex].pigInfo = row.pigInfo;
	changeTableDisplayValue('listTable',editIndex,[{
		field:'pigQty',
		value:rows[editIndex].pigQty
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
		field:'pigInfo',
		value:rows[editIndex].pigInfo
	}]);
//	setTimeout(function () {
//		$('#listTable').datagrid('endEdit',editIndex);
//		$('#listTable').datagrid('updateRow',{
//			index:editIndex,
//			row:rows[editIndex]
//		});
//	 },100);
}
/**
 * 批次选择方法
 */
function oldSwinerySelectFun(index,row){
	var rows = $('#listTable').datagrid('getRows');
	rows[editIndex].minValidDate = row.minValidDate;
	rows[editIndex].maxValidDate = row.maxValidDate;
	rows[editIndex].pigQty = row.pigQty;
	//rows[editIndex].enterDate = row.minValidDate;
	rows[editIndex].pigInfo = row.pigInfo;
	changeTableDisplayValue('listTable',editIndex,[{
		
		field:'minValidDate',
		value:rows[editIndex].minValidDate
	},{
		field:'maxValidDate',
		value:rows[editIndex].maxValidDate
	},{
		field:'pigInfo',
		value:rows[editIndex].pigInfo
	},{
		field:'pigQty',
		value:rows[editIndex].pigQty
	}]);
	
}
/**
 *  猪舍改变方法
 * @param newValue
 * @param oldValue
 */
function inHouseChangeFun(newValue,oldValue){
	if(oldValue == ''){
		return;
	}
	var rows = $('#listTable').datagrid('getRows');
	rows[editIndex].inPigpenId = '';
	rows[editIndex].inPigpenIdName = '';
	rows[editIndex].swineryId = '';
	rows[editIndex].swineryIdName = '';
	changeTableDisplayValue('listTable',editIndex,[{
		field:'inPigpenId',
		value:''
	},{
		field:'swineryId',
		value:''
	
	}]);
	/*setTimeout(function () {
		$('#listTable').datagrid('endEdit',editIndex);
		$('#listTable').datagrid('updateRow',{
			index:editIndex,
			row:rows[editIndex]
		});
	 },100);*/
}
function changemergeSwinery(newValue,oldValue)
{
	if(oldValue == ''){
		return;
	}
	var rows = $('#listTable').datagrid('getRows');
		rows[editIndex].minValidDate = '';
		rows[editIndex].maxValidDate = '';
		rows[editIndex].pigInfo = '';
		rows[editIndex].swineryId = '';
		rows[editIndex].oldSwineryId = '';
		rows[editIndex].swineryName = '';
		rows[editIndex].oldSwineryName = '';
		rows[editIndex].pigQty = '';
		changeTableDisplayValue('listTable',editIndex,[{
			field:'minValidDate',
			value:rows[editIndex].minValidDate
		},{
			field:'maxValidDate',
			value:rows[editIndex].maxValidDate
		},{
			field:'pigInfo',
			value:rows[editIndex].pigInfo
		},{
			field:'swineryId',
			value:rows[editIndex].swineryId
		},{
			field:'oldSwineryId',
			value:rows[editIndex].oldSwineryId
		},{
			field:'pigQty',
			value:rows[editIndex].pigQty
		}]);
}
function selectToFattenPig(){
	leftSilpFun('selectToFattenPigWin',true);
}

/**
 * 搜索猪只
 */
function searchPigSearch(){
	var earBrand =  $('#earBrand').textbox('getValue');
	var pigClassId = $('#pigClassId').combogrid('getValue');
	var houseIds = $('#houseId').combogrid('getValue');
	var breedIds = $('#breedId').combogrid('getValue');
	var sex = $('#sex').combogrid('getValue');
	var swineryIds = $('#swineryId').combogrid('getValue');
	var changeHouseType = $('#changeHouseType').combogrid('getValue');
	var seedPigFlag = 0;
	if($('#seedPigFlag').is(':checked')){
		seedPigFlag = 1;
	}
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
	if(changeHouseType == 5){
		if(pigClassId != '' && pigClassId != 16){
			$.messager.alert('警告', '【育肥内转】，猪只状态只能为【育肥】状态，请重新选择猪只状态！');
			return;
		}
		pigClassId = 16;
	}else if(changeHouseType == 6){
		if(pigClassId != '' && pigClassId != 15){
			$.messager.alert('警告', '【保育转育肥】，猪只状态只能为【保育】状态，请重新选择猪只状态！');
			return;
		}
		pigClassId = 15;
	}
	$('#searchToFattenPigTable').datagrid('load',basicUrl+'/production/searchValidPigToPage.do?queryType='+2+'&choice='+1+'&eventName='+eventName+'&earBrand='+earBrand
			+'&pigClassIds='+pigClassId+'&pigIds='+pigIds+'&houseIds='+houseIds+'&breedIds='+breedIds+'&sex='+sex+'&swineryIds='+swineryIds+'&seedPigFlag='+seedPigFlag
			/*{
    	queryType:2,
    	pigType:4,
    	specifyPigs:0,
    	choice:1,
    	eventName:eventName,
    	earBrand:earBrand,
    	pigClassIds:pigClassId,
    	pigIds:pigIds
    }*/);
	
}

function selectToFattenPigSure(){
	var selectRows = $('#searchToFattenPigTable').datagrid('getSelections');
	if(selectRows.length < 1){
		$.messager.alert('警告', '请选择一条以上的数据！');
	}else{
		var pigIds = "";
		var houseId = "";
		var swineryId = "";
		var changeHouseType = "";
		var errFlag = false;
		var changeHouseType = $('#changeHouseType').combogrid('getValue');
		var changeHouseTypeName = $('#changeHouseType').combogrid('getText');
		var minValidDate = '';
		var maxValidDate = '';
		var dayAge = 0;
		$.each(selectRows,function(index,row){
			if(index == 0){
				pigIds += row.pigId;
				houseId = row.houseId;
				houseName = row.houseName;
				oldSwineryId = row.swineryId;
				oldSwineryName = row.swineryName;
				minValidDate = row.minValidDate;
				maxValidDate = row.maxValidDate;
			}else{
				if(houseId != row.houseId && swineryId != row.swineryId){
					errFlag = true;
				}
				pigIds += ','+ row.pigId;
				if(compareDate(minValidDate,row.minValidDate)){
					minValidDate = row.minValidDate;
				}
				if(compareDate(maxValidDate,row.maxValidDate)){
					maxValidDate = row.maxValidDate;
				}
			}
			dayAge = dayAge+parseInt(row.dateAge);
		});
		if(errFlag){
			$.messager.alert('警告',"只能选择同一猪舍，同一批次的猪！");
			return;
		}else{
			var row = {
					changeHouseType:changeHouseType,
					changeHouseTypeName:changeHouseTypeName,
					houseId:houseId,
					houseIdName:houseName,
					oldSwineryIdName:oldSwineryName,
					oldSwineryId:oldSwineryId,
					pigInfo:'平均日龄：【'+parseInt(dayAge/selectRows.length)+'】',
					pigQty:selectRows.length,
					childQty:selectRows.length,
					minValidDate:minValidDate,
					maxValidDate:maxValidDate,
					pigIds:pigIds
			}
			$('#listTable').datagrid('appendRow',row);
		}
		var ids = '';
		var rows = $('#listTable').datagrid('getRows');
		$.each(rows,function(index,row){
			if(index == 0){
				ids += row.pigIds;
			}else{
				if(houseId != row.houseId && swineryId != row.swineryId){
					errFlag = true;
				}
				ids += ','+ row.pigIds;
			}
		});
		var earBrand =  $('#earBrand').textbox('getValue');
		var pigClassId = $('#pigClassId').combogrid('getValue');
		var houseIds = $('#houseId').combogrid('getValue');
		var breedIds = $('#breedId').combogrid('getValue');
		var sex = $('#sex').combogrid('getValue');
		var swineryIds = $('#swineryId').combogrid('getValue');
		var seedPigFlag = 0;
		if($('#seedPigFlag').is(':checked')){
			seedPigFlag = 1;
		}
		var hasSelectRows = $('#listTable').datagrid('getData').rows;
		
		$('#searchToFattenPigTable').datagrid('load',basicUrl+'/production/searchValidPigToPage.do?queryType='+2+'&choice='+1+'&eventName='+eventName+'&earBrand='+earBrand
				+'&pigClassIds='+pigClassId+'&pigIds='+ids+'&houseIds='+houseIds+'&breedIds='+breedIds+'&sex='+sex+'&swineryIds='+swineryIds+'&seedPigFlag='+seedPigFlag
		);
	}	
}
function compareDate(startDate, endDate) {
	  var arrStart = startDate.split("-");
	  var startTime = new Date(arrStart[0], arrStart[1], arrStart[2]);
	  var startTimes = startTime.getTime();
	  var arrEnd = endDate.split("-");
	  var endTime = new Date(arrEnd[0], arrEnd[1], arrEnd[2]);
	  var endTimes = endTime.getTime();
	  if (endTimes<startTimes) {
	    return true;
	  }
	  return false;
}