var prUrl='/production/';
var saveUrl=prUrl+'changeLaborHouse.do';
var editIndex = undefined;
var eventName = 'BREED_PIG_CHANGE_HOUSE';
var oldPigType = '';
var detailDefaultValues = {
		status:'1',
		deletedFlag:'0',
		//设置默认值
		changeHouseType:7
};
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
						//耳牌号
						j_gridText(earBrandTextBox({width:100})),
						j_gridText({field:'pigInfo',title:'耳缺号    -  品种   -  胎次   -  状态(天)  -  日龄  - 猪舍  - 猪栏',width:450}),
						j_gridText({field:'enterDate',title:'转舍日期',width:100,editor:{type:'datebox',options:{editable:false}}}),
						//猪舍
		              	j_gridText(houseGridComboGrid({
		              		field:'inHouseId',
		              		title:'转入猪舍',
		              		width:80,
		              		formatter:function(value,row){
		              			return row.inHouseIdName
		              			},
		              		url:'/basicInfo/searchHouseToList.do?lineId=0&houseType=1,2,3,4,5,7', // 产房不允许转出转出
		              		onChange:inHouseChangeFun
	              		})),
		              	//猪栏
		              	j_gridText(pigpenGridComboGrid({
		              		field:'inPigpenId',
		              		title:'转入猪栏',
		              		width:80,
		              		formatter:function(value,row){
		              			return row.inPigpenIdName
		              			}
	              		})),
		              	//技术员
		              	j_gridText(workerGridComboGrid({width:80})),
		              	j_gridText({field:'notes',title:'备注',width:80,editor:'textbox'})
				    ]],
		    onEndEdit:function(index,row){
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
				columnFields:'pigId,earBrand,pigInfo,inHouseIdName,inPigpenIdName,enterDate,workerName,notes',
				columnTitles:'猪只ID,耳牌号,耳缺号    -  品种   -  胎次   -  状态(天)  -  日龄  - 猪舍  - 猪栏,转入猪舍,转入猪栏,转舍日期,技术员,备注',
				hiddenFields:'pigId,notes,workerName',
				columnWidths:'100,100,350,90,110,90,100,100',
				fit:false,
				width:'auto',
				height:'100%',
				pagination:false
			},'preSaveRecordTable')
	);
});
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
