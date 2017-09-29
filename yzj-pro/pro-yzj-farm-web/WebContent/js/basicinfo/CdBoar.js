$(document).ready(function(){
	//品种
	xnComboGrid({
		id:'breedId',
		idField:'rowId',
		textField:'breedName',
		url:'/backEnd/searchBreedToList.do',
		width:550,
		required:true,
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
		prompt:'请输入右乳头数',
		required:true
	});
	$('#leftTeatNum').numberspinner({
		prompt:'请输入左乳头数',
		required:true
	});
	$('#useYear').numberspinner({
	    	value:2,
        	min: 0,
        	max: 3,
		prompt:'请输入利用年限',
		required:true
	});
	$('#birthWeight').numberspinner({
	    	value:1.5,
        	min: 0.8,
        	max: 3,
		prompt:'请输入出生体重',
		precision:2,
		increment:0.1,
		required:true
	});
	$('#enterDayAge').numberspinner({
	    	value:150,
        	min: 0,
        	max: 250,
		prompt:'请输入入场日龄'
	});
	$('#enterWeight').numberspinner({
	    	value:120,
        	min: 0,
        	max: 210,
		prompt:'请输入入场体重',
		precision:2,
		increment:0.1
	});
	$('#sexRatio').numberspinner({
		prompt:'请输入公母比',
		required:true
	});
	/*$('#teachDayAge').numberspinner({
		prompt:'请输入调教日龄',
		required:true
	});
	$('#errorLimit').numberspinner({
		prompt:'请输入调教区间'
	});*/
	$('#productionDayAge').numberspinner({
	    	value:230,
        	min: 210,
        	max: 270,
		prompt:'请输入转生产日龄',
		required:true
	});
	$('#dayAgeSection').numberspinner({
	    	min:0,
	    	value:0,
		prompt:'请输入转生产日龄区间'
	});
//	$('#dayAgeSection2').numberspinner({
//	    prompt:'请输入转生产日龄区间'
//	});
	$('#eliminateDayAge').numberspinner({
	    	value:730,
        	min: 400,
        	max: 800,
		prompt:'请输入淘汰日龄'
	});
	$('#collectionNum').numberspinner({
	    	value:0,
        	min: 105,
        	max: 105,
		prompt:'请输入总采精次数',
		required:true
	});
	$('#spermQty').numberspinner({
	    	value:10500,
        	min: 0,
        	max: 30000,
		prompt:'请输入总精液量'
	});
	$('#litterNum').numberspinner({
		prompt:'请输入预期总仔数'
	});
});
/*************页面渲染结束******************************************************************************/
