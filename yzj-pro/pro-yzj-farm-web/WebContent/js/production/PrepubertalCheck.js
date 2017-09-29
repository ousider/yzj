var prUrl='/production/';
var saveUrl=prUrl+'prepubertalCheck.do';
var editIndex = undefined;
var eventName = 'PREPUBERTAL_CHECK';
//默认值为1
var oldPigType = 2;
$(document).ready(function(){
	$('#listTable').datagrid(
			j_eventGrid({
			dbClickEdit:true,
			columns:[[
		              	{field:'ck',checkbox:true},
		              	j_gridText({field:'rowId',title:'ID',hidden:true}),
		              	j_gridText({field:'pigId',title:'猪只ID',hidden:true}),
		              	j_gridText({field:'deletedFlag',title:'deletedFlag',hidden:true}),
		              	j_gridText({field:'minValidDate',title:'minValidDate',hidden:true}),
		              	j_gridText({field:'maxValidDate',title:'maxValidDate',hidden:true}),
		              	//耳牌号
		              	j_gridText(earBrandTextBox({width:100})),
		              	j_gridText({field:'pigInfo',title:'耳缺号    -  品种   -  胎次   -  状态(天)  -  日龄  - 猪舍  - 猪栏',width:300}),
		              	j_gridText({field:'enterDate',title:'鉴定日期',width:80,editor:{type:'datebox',options:{editable:false}}}),
		              	//技术员
		              	j_gridText(workerGridComboGrid({})),
		              	j_gridText({field:'newEarBrand',title:'新耳牌号',editor:{
		              		type:'textbox',
		              		options:{
		              			validType:'numOrLetterOr_'
		              		}
		              	}}),
		              	//猪舍
		              	j_gridText(houseGridComboGrid({
		              		field:'newHouseId',
		              		formatter:function(value,row){
		              			return row.newHouseIdName
		              			},
		              		url:'/basicInfo/searchHouseToList.do?lineId=0&eventName='+eventName,
		              		onChange:newHouseChangeFun
		              		})),
		              	//猪栏
		              	j_gridText(pigpenGridComboGrid({
		              		field:'newPigpen',
		              		formatter:function(value,row){
		              			return row.newPigpenName
		              			}
		              		})),
		              	j_gridText({field:'notes',title:'备注',editor:'textbox'})
				    ]],
		    onEndEdit:function(index,row){
	            var newHouse = $('#listTable').datagrid('getEditor', {
	                index: index,
	                field: 'newHouseId'
	            });
	            if(newHouse != null){
	            	row.newHouseIdName = $(newHouse.target).combogrid('getText');
	            }
	            var newPigpen = $('#listTable').datagrid('getEditor', {
	                index: index,
	                field: 'newPigpen'
	            });
	            if(newPigpen != null){
	            	 row.newPigpenName = $(newPigpen.target).combogrid('getText');
	            }
	        },
			onBeginEdit:function(index,row){
				var newPigpen = $('#listTable').datagrid('getEditor', {
			        index: index,
			        field: 'newPigpen'
			    });
				if(newPigpen != null){
					var newPigpenGrid = $(newPigpen.target).combogrid('grid');
					var newHouseId = row.newHouseId == null || row.newHouseId == undefined || row.newHouseId == ''? 0 : row.newHouseId;
					newPigpenGrid.datagrid('reload',basicUrl+'/basicInfo/searchPigpenToList.do?mainId='+newHouseId);
				}
			}
		})
	);
	
	$('#preSaveRecordTable').datagrid(
			j_grid_view({
				fitColumns:false,
				haveCheckBox:false,
				columnFields:'pigId,earBrand,pigInfo,enterDate,workerName,newEarBrand,newHouseIdName,newPigpenName,notes',
				columnTitles:'猪只ID,耳牌号,耳缺号    -  品种   -  胎次   -  状态(天)  -  日龄  - 猪舍  - 猪栏,鉴定日期,技术员,新耳号,新猪舍,新猪栏,备注',
				hiddenFields:'pigId,notes,workerName',
				columnWidths:'100,100,350,90,100,100,100,100,100',
				fit:false,
				width:'auto',
				height:'100%',
				pagination:false
			},'preSaveRecordTable')
	);
});
/**
 *  猪舍改变方法
 * @param newValue
 * @param oldValue
 */
function newHouseChangeFun(newValue,oldValue){
	if(oldValue == ''){
		return;
	}
	var rows = $('#listTable').datagrid('getRows');
	rows[editIndex].newPigpen = '';
	rows[editIndex].newPigpenName = '';
	changeTableDisplayValue('listTable',editIndex,[{
		field:'newPigpen',
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