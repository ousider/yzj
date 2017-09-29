var prUrl='/production/';
var saveUrl=prUrl+'changeEarBand.do';
var editIndex = undefined;
var eventName = 'CHANGE_EAR_BAND';
//获取猪只类型值
var obj_pigType = document.getElementsByName('pigType');
//默认值为2
var oldPigType = 2;
$(document).ready(function(){
	//猪只类型
	xnRadioBox({
		id:'pigType',
		name:'pigType',
		typeCode:'PIG_TYPE',
		//是否有监听事件
		onChange:true,
		defaultValue:2
	});
	//
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
		              	j_gridText({field:'pigInfo',title:'耳缺号-品种-状态(胎次)-日龄-猪舍-猪栏',width:350}),
		              	j_gridText({field:'newEarBrand',title:'新耳牌号',width:100,editor:{
		              		type:'textbox',
		              		options:{
		              			validType:'numOrLetterOr_'
		              		}
		              	}}),
		            	j_gridText({field:'newEarShort',title:'新耳缺号',width:100,editor:{
		              		type:'textbox',
		              		options:{
		              			validType:'numOrLetterOr_'
		              		}
		              	}}),
		            	j_gridText({field:'enterDate',title:'更换日期',width:100,editor:{type:'datebox',options:{editable:false}}}),
		            	//技术员
		              	j_gridText(workerGridComboGrid({width:100})),
		              	j_gridText({field:'notes',title:'备注',width:100,editor:'textbox'})
				    ]]
		})
	);
	
	$('#preSaveRecordTable').datagrid(
			j_grid_view({
				fitColumns:false,
				haveCheckBox:false,
				columnFields:'pigId,earBrand,pigInfo,newEarBrand,newEarShort,enterDate,workerName,notes',
				columnTitles:'猪只ID,耳牌号,耳缺号-品种-状态(胎次)-日龄-猪舍-猪栏,新耳牌号,新耳缺号,更换日期,技术员,备注',
				hiddenFields:'pigId,workerName,notes',
				columnWidths:'100,100,350,100,100,90,100,100',
				fit:false,
				width:'auto',
				height:'100%',
				pagination:false
			},'preSaveRecordTable')
	);
});
/**
 * 当猪只类型改变事方法（命名规则on+控件ID+Change）
 */
function onPigTypeChange(){
	var newPigType = null;
	var index = null;
	var listTable = $('#listTable');
	 for(var i = 0 ; i < obj_pigType.length ; i ++){
		 if(obj_pigType[i].checked){
			 newPigType = obj_pigType[i].value;
			 index = i;
		 }
	 }
	 if(index != null && newPigType != oldPigType){
		 if(oldPigType == null){
			 oldPigType = obj_pigType[index].value;
		 }else{
			 $.messager.confirm('提示', '改变会清空数据，确定改变吗？', function(r){
				 if(r){
					//清空明细表数据
					 oldPigType = obj_pigType[index].value;
					 listTable.datagrid('loadData', { success:true,total: 0, rows: [] });
				 }else{
					 for(var j = 0 ; j < obj_pigType.length ; j ++){
						 if(obj_pigType[j].checked){
							 obj_pigType[j].checked = false;
						 }else if(obj_pigType[j].value == oldPigType){
							 obj_pigType[j].checked = true;
						 }
					 }
				 }
			 });
		 }
	 }
}
