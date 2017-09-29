$(document).ready(function(){
	//品种
	xnComboGrid({
		id:'breedId',
		idField:'rowId',
		textField:'breedName',
		required:true,
		url:'/backEnd/searchBreedToList.do',
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
		prompt:'请输入右乳头数',
		required:true
	});
	$('#leftTeatNum').numberspinner({
		prompt:'请输入左乳头数',
		required:true
	});
	$('#birthWeight').numberspinner({
		prompt:'请输入出生体重',
		min: 1.5,
		max: 3,
		value:1.3,
		precision:2,
		increment:0.1,
		required:true
	});
	$('#enterDayAge').numberspinner({
	    	min: 21,
		max: 210,
		value:70,
		prompt:'请输入入场日龄'
	});
	$('#enterWeight').numberspinner({
	    	min: 7,
		max: 150,
		value: 25,
		prompt:'请输入入场体重',
		precision:2,
		increment:0.1
	});
	$('#mewDayAge').numberspinner({
	    	min: 10,
		max: 35,
		value: 24,
		prompt:'请输入断奶日龄'
	});
	$('#mewWeight').numberspinner({
	    	min: 3,
		max: 10,
		value: 7,
		prompt:'请输入断奶体重',
		precision:2,
		increment:0.1
	});
	$('#childCareDayAge').numberspinner({
	    	min: 15,
		max: 35,
		value: 24,
		prompt:'请输入转保育日龄',
		required:true
	});
	$('#childCareWeight').numberspinner({
	    	min: 4,	
		max: 10,
		value: 7,
		prompt:'请输入转保育体重',
		required:true,
		precision:2,
		increment:0.1
	});
	$('#fattenDayAge').numberspinner({
	    	min: 40,	
		max: 90,
		value: 70,
		prompt:'请输入转育肥日龄',
		required:true
	});
	$('#fattenWeight').numberspinner({
	    	min: 4,	
		max: 10,
		value: 7,
		prompt:'请输入转育肥体重',
		precision:2,
		increment:0.1
	});
	$('#sellDayAge').numberspinner({
	    	min: 18,
		max: 220,
		value: 175,
		prompt:'请输入出售日龄'
	});
	$('#sellWeight').numberspinner({
	    	min: 3,	
		max: 150,
		value: 110,
		prompt:'请输入出售体重'
	});
	$('#frc').numberspinner({
		prompt:'请输入料肉比'
	});
	
	//对应公猪主数据
	xnComboGrid({
		id:'boarId',
		idField:'rowId',
		textField:'materialName',
		required:true,
		url:'/material/searchMaterialToList.do?pigType=1',
		width:550,
		columns:[[ 	
		           	{field:'rowId',title:'ID',width:100,hidden:true},
			        {field:'businessCode',title:'物料代码',width:100},
			        {field:'materialName',title:'物料名称',width:100},
			        {field:'materialSource',title:'物料来源',width:100},
			        {field:'unit',title:'单位',width:100},
			        {field:'notes',title:'备注',width:100}
			    ]]
	});
	addFocus('boarId','grid');
	//对应母猪主数据
	xnComboGrid({
		id:'sowId',
		idField:'rowId',
		textField:'materialName',
		required:true,
		url:'/material/searchMaterialToList.do?pigType=2',
		width:550,
		columns:[[ 	
		           	{field:'rowId',title:'ID',width:100,hidden:true},
			        {field:'businessCode',title:'物料代码',width:100},
			        {field:'materialName',title:'物料名称',width:100},
			        {field:'materialSource',title:'物料来源',width:100},
			        {field:'unit',title:'单位',width:100},
			        {field:'notes',title:'备注',width:100}
			    ]]
	})
	addFocus('boarId','grid');
	//对应公猪主数据
	xnComboGrid({
		id:'stockBoarId',
		idField:'rowId',
		textField:'materialName',
		url:'/material/searchMaterialToList.do?pigType=1',
		width:550,
		columns:[[ 	
		           	{field:'rowId',title:'ID',width:100,hidden:true},
			        {field:'businessCode',title:'物料代码',width:100},
			        {field:'materialName',title:'物料名称',width:100},
			        {field:'materialSource',title:'物料来源',width:100},
			        {field:'unit',title:'单位',width:100},
			        {field:'notes',title:'备注',width:100}
			    ]]
	});
	addFocus('stockBoarId','grid');
	//对应母猪主数据
	xnComboGrid({
		id:'broodSowId',
		idField:'rowId',
		textField:'materialName',
		url:'/material/searchMaterialToList.do?pigType=2',
		width:550,
		columns:[[ 	
		           	{field:'rowId',title:'ID',width:100,hidden:true},
			        {field:'businessCode',title:'物料代码',width:100},
			        {field:'materialName',title:'物料名称',width:100},
			        {field:'materialSource',title:'物料来源',width:100},
			        {field:'unit',title:'单位',width:100},
			        {field:'notes',title:'备注',width:100}
			    ]]
	})
	addFocus('broodSowId','grid');
	//是否选种
	xnRadioBox({
		id:'isSelect',
		name:'isSelect',
		typeCode:'IS_SELECT',
		//是否有监听事件
		onChange:true,
		defaultValue:1
	});
	//分娩区分性别
	xnRadioBox({
		id:'isDifSex',
		name:'isDifSex',
		typeCode:'IS_DIF_SEX',
		//是否有监听事件
		onChange:false,
		defaultValue:1
	});
	//批量留种
	xnRadioBox({
		id:'isSeed',
		name:'isSeed',
		typeCode:'IS_SEED',
		onChange:false,
		defaultValue:1
	});
});
/**
 * 是否选中改变事件方法
 */
function onIsSelectChange(){
    var obj_isSelect = document.getElementsByName('isSelect');
    var isSelectValue = 1;
    for(var i = 0 ; i < obj_isSelect.length ; i ++){
	 if(obj_isSelect[i].checked == true){
	     isSelectValue = obj_isSelect[i].value;
	 }
    }
    var required = isSelectValue == 1 ? false : true;
    $('#broodSowId').combogrid({
	required:required
    });
    $('#stockBoarId').combogrid({
	required:required
    });
}
