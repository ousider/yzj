var prUrl='/production/';
var saveUrl=prUrl+'goodPigDie.do';
var editIndex = undefined;
var eventName = 'GOOD_PIG_DIE';
//默认值为3
var oldPigType = 3;
$(document).ready(function(){
	$('#listTable').datagrid(
			j_eventGrid({
			dbClickEdit:true,
			columns:[[
			          	{field:'ck',checkbox:true},
		              	j_gridText({field:'rowId',title:'ID',hidden:true}),
		              	j_gridText({field:'pigId',title:'猪只ID',hidden:true}),
		              	j_gridText({field:'minValidDate',title:'minValidDate',hidden:true}),
		              	j_gridText({field:'maxValidDate',title:'maxValidDate',hidden:true}),
		              	j_gridText({field:'deletedFlag',title:'deletedFlag',hidden:true}),
		              	j_gridText({field:'houseType',title:'猪舍类型',hidden:true}),
		              	j_gridText({field:'seedFlag',title:'是否留种猪',hidden:true}),
		              	//猪舍
		              	j_gridText(houseGridComboGrid({
		              		url:'/basicInfo/searchHouseToList.do?lineId=0&eventName='+eventName,
		              		width:100,
		              		onSelect:houseSelectFun,
		              		onChange:houseChangeFun
	              		})),
	              		//猪栏
	                  	j_gridText(pigpenGridComboGrid({
	                  		field:'pigpenId',
	                  		title:'猪栏名称',
	                  		width:120,
		                  	onChange:pigpenChangeFun,
	                  		formatter:function(value,row){
	                  			return row.pigpenIdName
	                  			}
	              		})),
	              		//批次
	              		j_gridText(swineryGridComboGrid({
		              		width:200,
		              		idField:'swineryId',
		              		onSelect:swinerySelectFun
	              		})),
	              		j_gridText({field:'pigInfo',title:'猪只信息',width:300}),
		              	j_gridText({field:'pigQty',title:'猪只数量',width:80}),
			          	j_gridText({field:'leaveReason',title:'死亡原因',width:80,
		              		formatter:function(value,row){
		              			return row.leaveReasonName;
		              		},
		              		editor:
		              		xnGridCdComboBox({
		              			field:'leaveReason',
		              			typeCode:'GOOD_DIE_REASON',
		              			editable:false
		              		})
		              	}),
		              	j_gridNumber({field:'leaveQty',title:'死亡数量',width:80,min:0,onChange:leaveQtyChange}),
		              	j_gridNumber({field:'leaveWeight',title:'死亡总重',min:0,
		              		precision:2,
							increment:0.1,
		              		formatter:function(value,row){
		              			value = value == null || value == undefined || value == '' ? 0.00 : value;
		              			row.leaveWeight = value;
		              			return value+'KG';
			              	},
			              	width:80}),
		              	j_gridText({field:'enterDate',title:'死亡日期',width:150,editor:{type:'datebox',options:{editable:false}}}),
		              //技术员
		              	j_gridText(workerGridComboGrid({width:80})),
		              	j_gridText({field:'notes',title:'备注',width:80,editor:'textbox'})
				    ]],
		    onEndEdit:function(index,row){
		    	var leaveReason = $('#listTable').datagrid('getEditor', {
	                index: index,
	                field: 'leaveReason'
	            });
		    	if(leaveReason != null){
	            	row.leaveReasonName = $(leaveReason.target).combobox('getText');
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
		    },
		    onBeginEdit:function(index,row){
				var pigpen = $('#listTable').datagrid('getEditor', {
			        index: index,
			        field: 'pigpenId'
			    });
				if(pigpen != null){
					if(row.houseType != null && row.houseType == 6){
						var pigpenGrid = $(pigpen.target).combogrid('grid');
						var houseId = row.houseId == null || row.houseId == undefined || row.houseId == ''? 0 : row.houseId;
						//pigpenGrid.datagrid('reload',basicUrl+'/basicInfo/searchPigpenToList.do?mainId='+houseId);						
						pigpenGrid.datagrid('reload',basicUrl+'/basicInfo/searchValidPigpenToList.do?lineId=0&eventName=' + eventName + '&houseId=' + houseId);
					}else{
						$(pigpen.target).combobox('disable');
					}
				};
		    	var swinery = $('#listTable').datagrid('getEditor', {
			        index: index,
			        field: 'swineryId'
			    });
				if(swinery != null){
					var swineryGrid = $(swinery.target).combogrid('grid');
					var houseId = row.houseId == null || row.houseId == undefined || row.houseId == ''? 0 : row.houseId;
					if(row.houseType == 6){
						var pigpenId = row.pigpenId == null || row.pigpenId == undefined || row.pigpenId == ''? 0 : row.pigpenId;
						swineryGrid.datagrid('reload',basicUrl+'/production/searchPorkToList2.do?lineId=0&pigType=3&houseId='+houseId+'&pigpenId='+pigpenId+'&eventName='+eventName);
					}else{
						swineryGrid.datagrid('reload',basicUrl+'/production/searchPorkToList.do?lineId=0&pigType=3&houseId='+houseId+'&eventName='+eventName);
					}
				}
		    }
		})
	);
	
	$('#preSaveRecordTable').datagrid(
			j_grid_view({
				fitColumns:false,
				haveCheckBox:false,
				columnFields:'pigId,houseIdName,swineryIdName,pigInfo,pigQty,leaveReasonName,leaveQty,leaveWeight,enterDate,workerName,notes',
				columnTitles:'猪只ID,猪舍名称,批次名称,猪只信息,猪只数量,死亡原因,死亡数量,死亡总重(KG),死亡日期,技术员,备注',
				hiddenFields:'pigId,workerName,notes',
				columnWidths:'100,100,100,350,100,100,100,110,100,100,100',
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
	//猪舍
	xnComboGrid({
		id:'houseId',
		idField:'rowId',
		textField:'houseName',
		url:'/basicInfo/searchHouseToList.do?lineId=0&eventName=' + eventName,
		editable:true,
		width:550,
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
	$('#selectPigListTable').datagrid(
			j_grid({
			width:'auto',
			height:'auto',
			columnFields:'pigId,pigType,earBrand,pigClassName,birthDate,swineryName,sexName,houseName,dayAge,houseId,swineryId,minValidDate,maxValidDate',
			columnTitles:'猪只ID,猪只类别,耳牌号,状态,出生日期,猪群,性别,猪舍,日龄,猪舍ID,批次ID,最小有效日期,最大有效日期',
			hiddenFields:'pigId,pigType,dayAge,houseId,swineryId,minValidDate,maxValidDate',
			onLoadSuccess:function(data){
	  			$('#selectPigSureBtn').attr('disabled',false).removeClass('btn-disabled');
	  		}
		},'selectPigListTable')
	);
});
/**
 * 猪舍改变方法
 */
function houseChangeFun(newValue,oldValue){
	if(oldValue == ''){
		return;
	}
	var rows = $('#listTable').datagrid('getRows');
	rows[editIndex].swineryId = '';
	rows[editIndex].swineryIdName = '';
	rows[editIndex].pigpenId = '';
	rows[editIndex].pigpenIdName = '';
	changeTableDisplayValue('listTable',editIndex,[{
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
/**
 * 猪栏改变方法
 */
function pigpenChangeFun(newValue,oldValue){
	if(oldValue == ''){
		return;
	}
	var rows = $('#listTable').datagrid('getRows');
	rows[editIndex].swineryId = '';
	rows[editIndex].swineryIdName = '';
	rows[editIndex].pigInfo = '';
	rows[editIndex].pigQty = '';
	changeTableDisplayValue('listTable',editIndex,[{
		field:'swineryId',
		value:''
	},{
		field:'pigInfo',
		value:''
	},{
		field:'pigQty',
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
 * 猪舍选择方法
 */
function houseSelectFun(index,row){
	var rows = $('#listTable').datagrid('getRows');
	rows[editIndex].pigQty = row.pigQty;
	rows[editIndex].minValidDate = row.minValidDate;
	rows[editIndex].maxValidDate = row.maxValidDate;
	rows[editIndex].houseType = row.houseType;
	//rows[editIndex].enterDate = row.lastEventDate;
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
function swinerySelectFun(index,row){
	var rows = $('#listTable').datagrid('getRows');
	rows[editIndex].pigQty = row.pigQty;
	rows[editIndex].minValidDate = row.minValidDate;
	rows[editIndex].maxValidDate = row.maxValidDate;
	//rows[editIndex].enterDate = row.minValidDate;
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
function leaveQtyChange(newValue,oldValue){
	if(editIndex == undefined){
		return;
	}
	var rows = $('#listTable').datagrid('getRows');
	var leaveQtySum = 0;
	if(newValue != ''){
		leaveQtySum = parseInt(newValue);
	}
	$.each(rows,function(i,item){
		if( i != editIndex && item.leaveQty != undefined && item.leaveQty != ''){
			leaveQtySum += parseInt(item.leaveQty);
		}
	})
	$('#leaveQtySum').html(leaveQtySum);
}
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
						$('#leaveQtySum').html('0');
					}else{
						var leaveQtySum = 0;
						$.each(listRows,function(i,row){
							leaveQtySum += parseInt(row.leaveQty == undefined ? 0 : row.leaveQty);
						});
						$('#leaveQtySum').html(leaveQtySum);
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
			$('#leaveQtySum').html('0');
		}
	});
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
	}else{
		queryParams = {
			status:1,
			deletedFlag:0,
		};
	}
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
				}else{
					//重置
					var preRecord = $('#listTable').datagrid('getData');
					$('#editForm').form('reset');
					leftSilpFun('preSaveRecord',true,9002);
					if(preRecord.success == undefined){
						preRecord.success = true;
					}
					$('#preSaveRecordTable').datagrid('loadData',preRecord);
					if(listTable!= null){
						$('#listTable').datagrid('loadData',{success:true,total: 0, rows: [] });
					}
					
					$('#leaveQtySum').html("0");
				}
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
function selectGoodPig(){
	leftSilpFun('selectPigWin',true);
}
/**
 * 搜索猪只
 */
function selectGoodPigSearch(){
	var houseIds = $('#houseId').combogrid('getValue');
	var swineryIds = $('#swineryId').combogrid('getValue');
	var pigClassId = $('#pigClassId').combogrid('getValue');
	var breedIds = $('#breedId').combogrid('getValue');
	var minDateage = Number($('#minDateage').numberspinner('getValue'));
	var maxDateage = Number($('#maxDateage').numberspinner('getValue'));
	var seedPigFlag = 0;
	if($('#seedPigFlag').is(':checked')){
		seedPigFlag = 1;
	}
	var hasSelectRows = $('#selectPigListTable').datagrid('getData').rows;
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
	/*$('#selectPigListTable').datagrid('load',{
    	queryType:2,
    	specifyPigs:0,
    	choice:1,
    	eventName:eventName,
    	houseId:houseId,
    	swineryId:swineryId,
    	earBrand:earBrand,
    	pigClassIds:pigClassId,
    	breedId:breedId,
    	minDateage:minDateage,
    	maxDateage:maxDateage,
    	pigIds:pigIds,
    	seedPigFlag:seedPigFlag
    });*/
	$('#selectPigListTable').datagrid('load',basicUrl+'/production/searchValidPigToPage.do?queryType='+2+'&choice='+1+'&eventName='+eventName
			+'&pigClassIds='+pigClassId+'&pigIds='+pigIds+'&houseIds='+houseIds+'&breedIds='+breedIds+'&swineryIds='+swineryIds+'&seedPigFlag='+seedPigFlag+'&minDateage='+minDateage+'&maxDateage='+maxDateage
	);
	
}
function selectGoodPigSure(){
	var selectRows = $('#selectPigListTable').datagrid('getSelections');
	if(selectRows.length < 1){
		$.messager.alert('警告', '请选择一条以上的数据！');
	}else{
		$('#selectPigSureBtn').attr('disabled',true).addClass('btn-disabled');
		var pigIds = "";
		var houseId = "";
		var swineryId = "";
		var errFlag = false;
		var minValidDate = '';
		var maxValidDate = '';
		var houseType = '';
		var dayAge = 0;
		$.each(selectRows,function(index,row){
			if(index == 0){
				pigIds += row.pigId;
				houseId = row.houseId;
				houseName = row.houseName;
				swineryId = row.swineryId;
				swineryName = row.swineryName;
				minValidDate = row.minValidDate;
				maxValidDate = row.maxValidDate;
				houseType = row.houseType;
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
			$('#selectPigSureBtn').attr('disabled',false).removeClass('btn-disabled');
			return;
		}else{
			var row = {
					houseId:houseId,
					houseIdName:houseName,
					swineryIdName:swineryName,
					swineryId:swineryId,
					pigInfo:'平均日龄：【'+parseInt(dayAge/selectRows.length)+'】',
					pigQty:selectRows.length,
					childQty:selectRows.length,
					minValidDate:minValidDate,
					maxValidDate:maxValidDate,
					pigIds:pigIds,
					houseType:houseType
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
		var houseIds = $('#houseId').combogrid('getValue');
		var swineryIds = $('#swineryId').combogrid('getValue');
		var pigClassId = $('#pigClassId').combogrid('getValue');
		var breedIds = $('#breedId').combogrid('getValue');
		var minDateage = Number($('#minDateage').numberspinner('getValue'));
		var maxDateage = Number($('#maxDateage').numberspinner('getValue'));
		var seedPigFlag = 0;
		if($('#seedPigFlag').is(':checked')){
			seedPigFlag = 1;
		}
		var hasSelectRows = $('#listTable').datagrid('getData').rows;
		/*$('#selectPigListTable').datagrid('load',{
        	queryType:2,
        	specifyPigs:0,
        	choice:1,
        	eventName:eventName,
        	houseId:houseId,
        	swineryId:swineryId,
        	earBrand:earBrand,
        	pigClassIds:pigClassId,
        	breedId:breedId,
        	minDateage:minDateage,
        	maxDateage:maxDateage,
        	pigIds:pigIds,
        	seedPigFlag:seedPigFlag
        });*/
		$('#selectPigListTable').datagrid('load',basicUrl+'/production/searchValidPigToPage.do?queryType='+2+'&choice='+1+'&eventName='+eventName
				+'&pigClassIds='+pigClassId+'&pigIds='+ids+'&houseIds='+houseIds+'&breedIds='+breedIds+'&swineryIds='+swineryIds+'&seedPigFlag='+seedPigFlag+'&minDateage='+minDateage+'&maxDateage='+maxDateage
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
function selectPigReset(){
	$('#selectPigSearchForm').form('reset');
}