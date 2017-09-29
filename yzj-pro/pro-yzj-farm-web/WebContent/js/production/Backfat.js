var prUrl='/production/';
var saveUrl=prUrl+'backfat.do';
var editIndex = undefined;
var eventName = 'BACKFAT';
var oldPigType = '';
var backfatScoreArr = {};
$.ajax({  
    url : basicUrl+prUrl+'searchBackfatScore.do',
    async : false, 
    type : "POST",  
    dataType : "json",  
    success : function(response) { 
    	backfatScoreArr = response.rows;
    }  
});

$(document).ready(function(){
	$('#listTable').datagrid(
			j_eventGrid({
			dbClickEdit:true,
			columns:[[
			          	{field:'ck',checkbox:true},
						j_gridText({field:'rowId',title:'ID',hidden:true}),
						j_gridText({field:'pigId',title:'猪只ID',hidden:true}),
						j_gridText({field:'houseId',title:'猪舍ID',hidden:true}),
						j_gridText({field:'minValidDate',title:'minValidDate',hidden:true}),
						j_gridText({field:'maxValidDate',title:'maxValidDate',hidden:true}),
						j_gridText({field:'deletedFlag',title:'deletedFlag',hidden:true}),
						//耳牌号
						j_gridText(earBrandTextBox({width:100})),
						j_gridText({field:'pigInfo',title:'耳缺号    -  品种   -  胎次   -  状态(天)  -  日龄  - 猪舍  - 猪栏',width:450}),
		              	j_gridNumber({field:'backfat',title:'背膘(mm)',plinkField:['score'],width:80,max:30,min:1,onChange:changeBackfatScore}),
		              	j_gridText({field:'score',title:'评分',width:80,max:10,min:0}),
		              	j_gridText({field:'enterDate',title:'测定日期',width:150,editor:{type:'datebox',options:{editable:false}}}),
		              	//技术员
		              	j_gridText(workerGridComboGrid({width:80})),
		              	j_gridText({field:'notes',title:'备注',width:80,editor:'textbox'})
				    ]]
			})
	 );
	
	$('#preSaveRecordTable').datagrid(
			j_grid_view({
				fitColumns:false,
				haveCheckBox:false,
				columnFields:'pigId,earBrand,pigInfo,backfat,score,enterDate,workerName,notes',
				columnTitles:'猪只ID,耳牌号,耳缺号    -  品种   -  胎次   -  状态(天)  -  日龄  - 猪舍  - 猪栏,背膘(mm),评分,测定日期,技术员,备注',
				hiddenFields:'pigId,notes,workerName',
				columnWidths:'100,100,370,100,80,90,100,100',
				fit:false,
				width:'auto',
				height:'100%',
				pagination:false
			},'preSaveRecordTable')
	);
});

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
	changeTableDisplayValue('listTable',editIndex,[{
		field:'score',
		value:row.score
	}]);
//	setTimeout(function () {
//		$('#listTable').datagrid('endEdit',editIndex);
//		$('#listTable').datagrid('updateRow',{
//			index:editIndex,
//			row:row
//		});
//	 },100);
}

