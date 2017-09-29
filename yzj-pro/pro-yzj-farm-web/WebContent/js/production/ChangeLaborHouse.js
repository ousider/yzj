var prUrl='/production/';
var saveUrl=prUrl+'changeLaborHouse.do';
var editIndex = undefined;
var eventName = 'CHANGE_LABOR_HOUSE';
//默认值为2
var oldPigType = 2;
var changeHouseData = [];
var changeHouseDefaultValue = null;
var changeHouseDefaultName = '';
var zcfxycbbFlag = 'off';
var backfatScoreArr = {};
$.ajax({  
    url : basicUrl+'/param/searchByTypeCode.do?typeCode=LABER_CHANGE_TYPE',
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
$.ajax({  
    url : basicUrl+'/param/getSettingValueByCode.do?settingCode=ZCFXYCBB', 
    async:false,
    type : "POST",  
    success : function(data) { 
    	var obj = eval('(' + data + ')');
    	zcfxycbbFlag = obj.rows;
    }
});
$.ajax({  
    url : basicUrl+prUrl+'searchBackfatScore.do',
    async : false, 
    type : "POST",  
    dataType : "json",  
    success : function(response) { 
    	backfatScoreArr = response.rows;
    }  
});
var detailDefaultValues = {
		status:'1',
		deletedFlag:'0',
		//设置默认值
		changeHouseType:changeHouseDefaultValue,
		changeHouseTypeName:changeHouseDefaultName
};
$(document).ready(function(){
	var columns = [
	              	{field:'ck',checkbox:true},
	              	j_gridText({field:'rowId',title:'ID',hidden:true}),
	              	j_gridText({field:'pigId',title:'猪只ID',hidden:true}),
	              	j_gridText({field:'houseId',title:'猪舍ID',hidden:true}),
	              	j_gridText({field:'pigClass',title:'猪只状态',hidden:true}),
	              	j_gridText({field:'deletedFlag',title:'deletedFlag',hidden:true}),
	              	j_gridText({field:'minValidDate',title:'minValidDate',hidden:true}),
	              	j_gridText({field:'maxValidDate',title:'maxValidDate',hidden:true}),
	              	//耳牌号
	              	j_gridText(earBrandTextBox({width:80,onSelect:selectSow})),
	            	j_gridText({field:'changeHouseType',title:'转舍类型',width:50,
						formatter:function(value,row){
							return row.changeHouseTypeName;
						},
						editor:
						xnGridCdComboBox({
							field:'changeHouseType',
							data:changeHouseData,
							excludeValue:['8']
						})
					}),
	              	j_gridText({field:'pigInfo',title:'耳缺号-品种-状态(胎次)-日龄-猪舍-猪栏',width:250}),
	            	//猪舍
	              	j_gridText(houseGridComboGrid({
	              		field:'inHouseId',
	              		formatter:function(value,row){
	              			return row.inHouseIdName
	              			},
	              		url:'/basicInfo/searchHouseToList.do?lineId=0&houseType=6',
	              		onChange:inHouseChangeFun
	              		})),
	              	//猪栏
	              	j_gridText(pigpenGridComboGrid({
	              		field:'inPigpenId',
	              		formatter:function(value,row){
	              			return row.inPigpenIdName
	              			}
	              		})),
	              	j_gridText({field:'enterDate',title:'转产房日期',width:80,editor:{type:'datebox',options:{editable:false}}}),
			    ];
	if(zcfxycbbFlag=='on' || zcfxycbbFlag=='ON'){
		columns.push(j_gridNumber({field:'backfat',title:'背膘',plinkField:['score'],width:80,min:5,max:30,onChange:changeBackfatScore}),
		j_gridText({field:'score',title:'评分',width:80}));
	}
	
  	columns.push(
  	// 技术员
  	j_gridText(workerGridComboGrid({})),
  	j_gridText({field:'notes',title:'备注',editor:'textbox'}));
  	
	$('#listTable').datagrid(
			j_eventGrid({
			dbClickEdit:true,
			columns:[columns],
		    onEndEdit:function(index,row){
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
	            	row.inHouseIdName = $(inHouse.target).combogrid('getText');
	            }
	            var inPigpen = $('#listTable').datagrid('getEditor', {
	                index: index,
	                field: 'inPigpenId'
	            });
	            if(inPigpen != null){
	            	 row.inPigpenIdName = $(inPigpen.target).combogrid('getText');
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
		    	var backfat = $('#listTable').datagrid('getEditor', {
	                index: index,
	                field: 'backfat'
	            });
	            if(backfat != null){
	            	if(row.changeHouseType == 1){
						$(backfat.target).combogrid('disable');
	            	}
	            }
			}
		})
	);
	
	$('#preSaveRecordTable').datagrid(
			j_grid_view({
				fitColumns:false,
				haveCheckBox:false,
				columnFields:'pigId,earBrand,changeHouseTypeName,pigInfo,inHouseIdName,inPigpenIdName,enterDate,backfat,score,workerName,notes',
				columnTitles:'猪只ID,耳牌号,转舍类型,耳缺号-品种-状态(胎次)-日龄-猪舍-猪栏,猪舍名称,猪栏,转产房日期,背膘,评分,技术员,备注',
				hiddenFields:'pigId,notes,workerName'+((zcfxycbbFlag=='on' || zcfxycbbFlag=='ON')?'':'backfat,score'),
				columnWidths:'100,100,100,350,100,100,90,100,80,100,100',
				fit:false,
				width:'auto',
				height:'100%',
				pagination:false
			},'preSaveRecordTable')
	);
	
	// 是否测试背膘，后台验证用hidden项
	$('#zcfxycbbFlag').val(zcfxycbbFlag);
});
/**
 *  猪舍改变方法
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
	//2016-09-22 修改刷新行值方法 begin
	changeTableDisplayValue('listTable',editIndex,[{
		field:'inPigpenId',
		value:''
	}]);
	focusEditCell('listTable',editIndex,'inHouseId');
	// end
	// 删除 begin
	//	setTimeout(function () {
	//		
	//		$('#listTable').datagrid('updateRow',{
	//			index:editIndex,
	//			row:rows[editIndex]
	//		});
	//	 },100);
	// 删除 end
}

//背膘评分
function changeBackfatScore(newValue,oldValue){
	if(editIndex==undefined){
		return;
	}
	var row = $('#listTable').datagrid('getRows')[editIndex];
	if(newValue == ''){
		row.score = '';
	}else if(newValue<12){
		row.score = backfatScoreArr.LVL1;
	}else if(newValue>=12 && newValue<=13){
		row.score = backfatScoreArr.LVL2;
	}else if(newValue>=14 && newValue<=15){
		row.score = backfatScoreArr.LVL3;
	}else if(newValue>=16 && newValue<=17){
		row.score = backfatScoreArr.LVL4;
	}else if(newValue>=18 && newValue<=19){
		row.score = backfatScoreArr.LVL5;
	}else if(newValue>=20){
		row.score = backfatScoreArr.LVL6;
	}
	//2016-09-22 修改刷新行值方法 begin
	changeTableDisplayValue('listTable',editIndex,[{
		field:'score',
		value:row.score
	}]);
	// end
	// 删除 begin
//	
//	setTimeout(function () {
//		$('#listTable').datagrid('endEdit',editIndex);
//		$('#listTable').datagrid('updateRow',{
//			index:editIndex,
//			row:row
//		});
//	 },100);
	// 删除 end
}

function selectSow(index,row){

	if(editIndex == undefined){
		return;
	}
	var rows = $('#listTable').datagrid('getRows');
	rows[editIndex].pigId = row.pigId;
	rows[editIndex].earBrand = row.earBrand;
	rows[editIndex].pigInfo = row.pigInfo;
	rows[editIndex].minValidDate = row.minValidDate;
	rows[editIndex].maxValidDate = row.maxValidDate;
	rows[editIndex].enterDate = row.lastEventDate;
	rows[editIndex].houseId = row.houseId;
	if(row.pigClass==10){
		rows[editIndex].notes = "哺乳母猪内转，小猪一同转舍";
	}
	
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
		field:'enterDate',
		value:rows[editIndex].enterDate
	},{
		field:'notes',
		value:rows[editIndex].notes
	}];
	
	changeTableDisplayValue('listTable',editIndex,changeValue);
	setTimeout(function () {
		//$('#listTable').datagrid('endEdit',editIndex);
//		$('#listTable').datagrid('updateRow',{
//			index:editIndex,
//			row:rows[editIndex]
//		});
		var field = pms.field == null ? 'earBrand' : pms.field;
		focusEditCell('listTable',editIndex,field);
//		$('#listTable').datagrid('editCell', {index:editIndex,field:field});
//		var editor = $('#listTable').datagrid('getEditor', {index:editIndex,field:field});
//		if(editor != undefined){
//			var eidtCellInput = $(editor.target[0].parentNode).find('.textbox-text.validatebox-text');
//			eidtCellInput[0].focus();
//			eidtCellInput.keydown(function(e){
//		    	editCellTabDownFun(e,$('#listTable'),editIndex,field);
//		    });
//		}
	 },100);

}
