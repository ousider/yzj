var prUrl='/event/';
var saveUrl=prUrl+'editSeedPig.do';
var editIndex = undefined;
var eventName = 'SEED_PIG';
//默认值为2
var oldPigType = 2;

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
			              	j_gridText({field:'parity',title:'母猪胎次',hidden:true}),
			              	j_gridText({field:'minValidDate',title:'minValidDate',hidden:true}),
			              	j_gridText({field:'maxValidDate',title:'maxValidDate',hidden:true}),
			              	j_gridText({field:'aliveLitterX',title:'活仔公',hidden:true}),
			              	j_gridText({field:'aliveLitterY',title:'活仔母',hidden:true}),
			              	j_gridText({field:'deletedFlag',title:'deletedFlag',hidden:true}),
			              	//耳牌号
			              	j_gridText(earBrandTextBox({width:150}))
			              	]],
			columns:[[
			          	j_gridText({field:'pigInfo',title:'耳缺号    -  品种   -  胎次   -  状态(天)  -  日龄  - 猪舍  - 猪栏',width:500}),
			          	//猪舍
						j_gridText({field:'houseName',title:'猪舍名称',width:80}),
						//猪栏
						j_gridText({field:'pigpenName',title:'猪栏',width:80}),
						j_gridText({field:'pigQty',title:'带仔数',width:80}),
		              	j_gridNumber({field:'seedMale',title:'留种公',width:80,min:0,formatter:function(value,row){
		              		if(value == ''){
		              			row.seedMale = 0;
		              		}
		              		return row.seedMale;
		              	},onChange:seedMaleChange}),
		              	j_gridText({field:'seedMaleEarShort',title:'留种公耳缺号',width:100,formatter:function(value,row){
		              		var index = $('#listTable').datagrid('getRowIndex',row);
		              		return '<a href="javacript:;" style="text-decoration:none;" onclick="inputSeedEarShort('+index+',\'male\')">录入公耳缺号</a>'
		              	}}),
		              	j_gridNumber({field:'seedFemale',title:'留种母',width:80,min:0,formatter:function(value,row){
		              		if(value == ''){
		              			row.seedFemale = 0;
		              		}
		              		return row.seedFemale;
		              	},onChange:seedFemaleChange}),
		              	j_gridText({field:'seedFemaleEarShort',title:'留种母耳缺号',width:100,formatter:function(value,row){
		              		var index = $('#listTable').datagrid('getRowIndex',row);
		              		return '<a href="javacript:;" style="text-decoration:none;" onclick="inputSeedEarShort('+index+',\'female\')">录入母耳缺号</a>'
		              	}}),
		              	j_gridText({field:'enterDate',title:'留种日期',width:150,editor:{type:'datebox',options:{editable:false}}}),
		              	j_gridText(workerGridComboGrid({width:80})),
		              	j_gridText({field:'notes',title:'备注',width:80,editor:'textbox'})
	              	]],
		})
	);
	
	$('#preSaveRecordTable').datagrid(
			j_grid_view({
				fitColumns:false,
				haveCheckBox:false,
				columnFields:'pigId,earBrand,pigInfo,houseName,pigpenName,pigQty,seedMale,seedFemale,enterDate,workerName,notes',
				columnTitles:'猪只ID,耳牌号,耳缺号    -  品种   -  胎次   -  状态(天)  -  日龄  - 猪舍  - 猪栏,猪舍名称,猪栏,带仔数,留种公,留种母,留种日期,技术员,备注',
				hiddenFields:'pigId,notes,workerName',
				columnWidths:'100,100,350,100,100,100,100,100,100,100,100',
				fit:false,
				width:'auto',
				height:'100%',
				pagination:false
			},'preSaveRecordTable')
	);
});
//留种公改变方法
function seedMaleChange(newValue,oldValue){
	if(editIndex == undefined){
		return;
	}
	var row = $('#listTable').datagrid('getRows')[editIndex];
	var total = 0;
	if(row.seedFemale == null || row.seedFemale == undefined){
		total = strToInt(newValue);
	}else{
		total = strToInt(row.seedFemale) + strToInt(newValue);
	}
	
	var pigQty = strToInt(row.pigQty);
	if((row.pigQty == null || row.pigQty == undefined) && newValue != ''){
		$.messager.alert('警告','请先选择猪只后重新输入！');
		var seedMale = $('#listTable').datagrid('getEditor', {
	        index: editIndex,
	        field: 'seedMale'
	    });
		if(seedMale != null){
			$(seedMale.target).numberspinner('setValue',oldValue);
		}
	}else if(pigQty < total){
		$.messager.alert('警告','留种公加留种母数量大于带仔数，请重新输入！');
		var seedMale = $('#listTable').datagrid('getEditor', {
	        index: editIndex,
	        field: 'seedMale'
	    });
		if(seedMale != null){
			$(seedMale.target).numberspinner('setValue',oldValue);
		}
	}
}
//留种母改变方法
function seedFemaleChange(newValue,oldValue){
	if(editIndex == undefined){
		return;
	}
	var row = $('#listTable').datagrid('getRows')[editIndex];
	var total = 0;
	if(row.seedMale == null || row.seedMale == undefined){
		total = strToInt(newValue);
	}else{
		total = strToInt(row.seedMale) + strToInt(newValue);
	}
	
	var pigQty = strToInt(row.pigQty);
	if((row.pigQty == null || row.pigQty == undefined) && newValue != ''){
		$.messager.alert('警告','请先选择猪只后重新输入！');
		var seedFemale = $('#listTable').datagrid('getEditor', {
	        index: editIndex,
	        field: 'seedFemale'
	    });
		if(seedFemale != null){
			$(seedFemale.target).numberspinner('setValue',oldValue);
		}
	}else if(pigQty < total){
		$.messager.alert('警告','留种公加留种母数量大于带仔数，请重新输入！');
		var seedFemale = $('#listTable').datagrid('getEditor', {
	        index: editIndex,
	        field: 'seedFemale'
	    });
		if(seedFemale != null){
			$(seedFemale.target).numberspinner('setValue',oldValue);
		}
	}
}
//根据留种数量输入耳缺号
function inputSeedEarShort(index,type){
	var seedNum = 0;
	$('#listTable').datagrid('endEdit',index);
	if(type == 'male'){
		seedNum = $('#listTable').datagrid('getRows')[index].seedMale
		if(seedNum == 0 || seedNum == '' || seedNum == null){
			$.messager.alert('提示','请先输入留种公数量！');
			return;
		}
	}else{
		seedNum = $('#listTable').datagrid('getRows')[index].seedFemale
		if(seedNum == 0 || seedNum == '' || seedNum == null){
			$.messager.alert('提示','请先输入留种母数量！');
			return;
		}
	}
	leftSilpFun('inputSeedEarShortWin',true,9001);
	$('#inputSeedEarShortFrom').empty();
	for(var i = 0 ; i < seedNum ; i ++){
		var html = '<div class="wd-com wd-8"><label class="label">耳缺号'+(i+1)+':</label></div>'
			+ '<div class="wd-com wd-15">'
			+ '<input id="seedEarShort'+type+(i+1)+'" name="seedEarShort'+type+(i+1)+'" style="width:100%;height:35px">'
			+ '</div>';
		$('#inputSeedEarShortFrom').append(html);
		$('#seedEarShort'+type+(i+1)).textbox({
			prompt:'请输入第'+(i+1)+'个耳缺号',
			validType:'numOrLetterOr_',
			required:true
		})
	}
	var row = $('#listTable').datagrid('getRows')[index];
	if(type == 'male'){
		if(row.maleEarShortList){
			var maleEarShortObject = {};
			$.each(row.maleEarShortList,function(i,item){
				maleEarShortObject['seedEarShort'+type+(i+1)] = item.earShort;
			})
			$('#inputSeedEarShortFrom').form('load',maleEarShortObject);
		}
	}else{
		if(row.femaleEarShortList){
			var femaleEarShortObject = {};
			$.each(row.femaleEarShortList,function(i,item){
				femaleEarShortObject['seedEarShort'+type+(i+1)] = item.earShort;
			})
			$('#inputSeedEarShortFrom').form('load',femaleEarShortObject);
		}
	}
	$('#inputSeedEarShortSure').unbind('click');
	$('#inputSeedEarShortSure').bind('click',function(e){
			inputSeedEarShortSure(row,type);
		}
	)
	if(seedNum > 0){
		$('#seedEarShort'+type+'1').parent().find('.textbox-text.validatebox-text').focus();
	}
}
function inputSeedEarShortSure(row,type){
	var isValid = $('#inputSeedEarShortFrom').form('validate');
	if(!isValid){
		return;
	}
	var data = $('#inputSeedEarShortFrom').serializeArray();
	var dataList = [];
	$.each(data,function(i,item){
		var dataObject = {};
		dataObject['earShort'] = item.value;
		dataList.push(dataObject);
	});
	if(type == 'male'){
		row.maleEarShortList = dataList;
	}else{
		row.femaleEarShortList = dataList;
	}
	rightSlipFun('inputSeedEarShortWin',780,true);
}
function inputSeedEarShortReset(){
	$('#inputSeedEarShortFrom').form('reset');
}
function seedEarShortKeyBoardDown(e){
	var target = document.activeElement;
	var preLength = $(target).parent().parent().prevAll('.wd-com.wd-15').length;
	var nextLength = $(target).parent().parent().nextAll('.wd-com.wd-15').length;
	if(e.keyCode == 38){
		if(preLength > 0){
			$($(target).parent().parent().prevAll('.wd-com.wd-15')[0]).find('.textbox-text.validatebox-text').focus();
		}else{
			$($(target).parent().parent().nextAll('.wd-com.wd-15')[nextLength - 1]).find('.textbox-text.validatebox-text').focus();
		}
	}else if(e.keyCode == 40){
		if(nextLength > 0){
			$($(target).parent().parent().nextAll('.wd-com.wd-15')[0]).find('.textbox-text.validatebox-text').focus();
		}else{
			$($(target).parent().parent().prevAll('.wd-com.wd-15')[preLength - 1]).find('.textbox-text.validatebox-text').focus();
		}
	}
}