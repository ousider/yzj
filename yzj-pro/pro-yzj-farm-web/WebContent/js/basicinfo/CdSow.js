$(document).ready(function(){

	//品种
	xnComboGrid({
		id:'breedId',
		idField:'rowId',
		textField:'breedName',
		url:'/backEnd/searchBreedToList.do',
		required:true,
		width:550,
		columns:[[ 	
		           	{field:'rowId',title:'ID',width:100,hidden:true},
			        {field:'breedCode',title:'品种代码',width:100},
			        {field:'breedName',title:'品种名称',width:100},
			        {field:'notes',title:'备注',width:100}
			    ]]
	});
	//体况
	xnCdCombobox({
		id:'bodyCondition',
		typeCode:'BODY_CONDITION',
		required:true
	});
	
	//品系
	xnCdCombobox({
		id:'strain',
		typeCode:'STRAIN',
		required:true
	});
	//毛色
	xnCdCombobox({
		id:'color',
		typeCode:'COLOR'
	});
	$('#rightTeatNum').numberspinner({
	    	min: 5,
		max: 10,
		value:7,
		prompt:'请输入右乳头数',
		required:true
	});
	$('#leftTeatNum').numberspinner({
	    	min: 5,
		max: 10,
		value:7,
		prompt:'请输入左乳头数',
		required:true
	});
	$('#birthWeight').numberspinner({
		prompt:'请输入出生体重',
		min: 0.8,
		max: 3,
		value:1.5,
		precision:2,
		increment:0.1,
		required:true
	});
	$('#enterDayAge').numberspinner({
	    	min: 0,
		max: 500,
		value:120,
		prompt:'请输入入场日龄'
	});
	$('#enterWeight').numberspinner({
	    	min: 0,
		max: 300,
		value: 60,
		prompt:'请输入入场体重',
		precision:2,
		increment:0.1
	});
	$('#pubertyDayAge').numberspinner({
	    	min: 150,
		max: 270,
		value: 180,
		prompt:'请输入初情日龄'
	});
	$('#pubertyWeight').numberspinner({
	    	min: 80,
		max: 150,
		value: 100,
		prompt:'请输入初情体重',
		precision:2,
		increment:0.1
	});
	$('#sexDayAge').numberspinner({
	    	min: 170,
		max: 240,
		value: 270,
		prompt:'请输入初配日龄'
	});
	$('#sexWeight').numberspinner({
	    	min: 130,
		max: 170,
		value: 140,
		prompt:'请输入初配体重',
		precision:2,
		increment:0.1
	});
	$('#useYear').numberspinner({
	    	min: 0,
		max: 5,
		value: 3,
		prompt:'请输入利用年限',
		required:true
	});
	$('#useParity').numberspinner({
	    	min: 0,
		max: 10,
		value: 4,
		prompt:'请输入利用胎次'
	});
	$('#heatCycle').numberspinner({
	    	min: 0,
		max: 3,
		value: 1,
		prompt:'请输入发情周期'
	});
	$('#pregnancyDays').numberspinner({
	    	min: 108,
		max: 120,
		value: 114,
		prompt:'请输入妊娠天数'
	});
	$('#errorLimit').numberspinner({
	    	min: 0,
	    	max:10,
		value: 3,
		prompt:'请输入误差',
		required:true
	});
	$('#pregnancyCheckDays').numberspinner({
	    	min: 25,
		max: 35,
		value: 28,
		prompt:'请输入妊检天数',
		required:true
	});
	$('#lacationDays').numberspinner({
	    	min: 0,
		max: 40,
		value: 24,
		prompt:'请输入哺乳天数',
		required:true
	});
	$('#dnhfqDays').numberspinner({
	    	min: 0,
		max: 30,
		value: 7,
		prompt:'请输入断奶后发情(天)'
	});
	$('#yearBabys').numberspinner({
	    	min: 0,
		max: 40,
		value: 27,
		prompt:'请输入年生产力(头数)'
	});
	$('#changeLabor').numberspinner({
	    	min: 107,
		max: 114,
		value: 110,
		prompt:'请输入配种后转产房区间',
		required:true
	});
	$('#errorLabor').numberspinner({
	    min: 0,
	    max:10,
	    value: 3,
	    prompt:'请输入配种后转产房区间',
	    required:true
	});
	
	/*$('#notes').textbox({
		prompt:'请输入备注',
		multiline:true
	});*/
});
/*************页面渲染结束******************************************************************************/
