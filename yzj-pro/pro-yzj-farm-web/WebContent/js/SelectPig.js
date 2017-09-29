var init = 0;
$(function(){
    initSelectPig();
    initSearchPig();
}) 

function initSearchPig(){
	  $('#selectPigListTable').datagrid({
	    	url:basicUrl+'/production/searchValidPigToPage.do',
	    	queryParams:{
			queryType:2,
	        	pigType:oldPigType,
	        	specifyPigs:0,
	        	choice:1,
	        	eventName:eventName
	    	},
	  		onLoadSuccess:function(data){
	  			$('#selectPigSureBtn').attr('disabled',false).removeClass('btn-disabled');
	  		}
	    });
}

function initSelectPig(){
	if(eventName == 'SELECT_BREED_PIG'){
		$('#earBrandLabel').html('耳缺号：');
		$('#earBrand').parent().css('display','none');
		$('#earBrand').textbox('disable');
		$('#earShort').parent().css('display','inline-block');
		$('#earShort').textbox('enable');
		
		$('#swineryLabel').html('猪栏：');
		$('#swineryId').parent().css('display','none');
		$('#pigClassLabel').parent().css('display','none');
		$('#pigClassId').parent().css('display','none');
		//$('#swineryId').textbox('disable');
		$('#pigpenId').parent().css('display','inline-block');
		//$('#pigpenId').textbox('enable');
		
	}else{
		$('#earBrandLabel').html('耳牌号：');
		$('#earShort').parent().css('display','none');
		$('#earShort').textbox('disable');
		$('#earBrand').parent().css('display','inline-block');
		$('#earBrand').textbox('enable');
		
		$('#swineryLabel').html('批次：');
		$('#pigpenId').parent().css('display','none');
		//$('#pigpenId').textbox('disable');
		$('#swineryId').parent().css('display','inline-block');
		$('#pigClassLabel').parent().css('display','inline-block');
		$('#pigClassId').parent().css('display','inline-block');
		//$('#swineryId').textbox('enable');
	}
	//批次
	xnComboGrid({
		id:'swineryId',
		idField:'rowId',
		textField:'swineryName',
		width:550,
		columns:[[ 	
		           	{field:'rowId',title:'ID',width:100,hidden:true},
			        {field:'businessCode',title:'批次代码',width:100},
			        {field:'swineryName',title:'批次名称',width:100},
			        {field:'pigQty',title:'猪只数量',width:100},
			        {field:'notes',title:'备注',width:100}
			    ]]
	});
	//猪舍
	xnComboGrid({
		id:'houseId',
		idField:'rowId',
		textField:'houseName',
		url:'/basicInfo/searchHouseToList.do?lineId=0&eventName='+eventName,
		width:550,
		columns:[[ 	
		           	{field:'rowId',title:'ID',width:100,hidden:true},
			        {field:'businessCode',title:'猪舍代码',width:100},
			        {field:'houseName',title:'猪舍名称',width:100},
			        {field:'houseVolume',title:'猪舍容量',width:100},
			        {field:'pigQty',title:'猪只数量',width:100},
			        {field:'houseTypeName',title:'猪舍类别',width:100},
			        {field:'notes',title:'备注',fitColumns:true}
			    ]],
	    onChange:houseChange
	});
	//猪栏
	xnComboGrid({
		id:'pigpenId',
		idField:'rowId',
		textField:'pigpenName',
		width:550,
		columns:[[ 	
					{field:'rowId',title:'ID',width:100,hidden:true},
					{field:'businessCode',title:'猪栏代码',width:100},
					{field:'pigpenName',title:'猪栏名称',width:100},
					{field:'length',title:'长度',width:100},
					{field:'width',title:'宽度',width:100},
					{field:'pigNum',title:'猪只容量',width:100},
					{field:'pigQty',title:'已有猪只数',width:100},
					{field:'notes',title:'备注',fitColumns:true}
			    ]]
	});
	//猪只状态
	xnComboGrid({
		id:'pigClassId',
		idField:'rowId',
		textField:'pigClassName',
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
		width:550,
		columns:[[ 	
		           	{field:'rowId',title:'ID',width:100,hidden:true},
			        {field:'breedCode',title:'品种代码',width:100},
			        {field:'breedName',title:'品种名称',width:100},
			        {field:'notes',title:'备注',width:100}
			    ]]
	});
	//查询结果表
	$('#selectPigListTable').datagrid({
		wdith:'98.5%',
		height:'100%',
		fitColumns:true, 
		pagination:true, 
		rownumbers:true, 
		remoteSort:false, 
		multiSort:true, 
		onLoadSuccess:function(data){
			$('#selectPigSureBtn').attr('disabled',false).removeClass('btn-disabled');
			if(!data.success){
				$.messager.alert({
					title: '错误',
					msg: data.errorMsg
				});
	    	}
		},
		rowStyler: function(index,row){
			if ((index+1) % 2 == 0){
				return 'background-color:#f7f7f7;';
			}
		},
		columns:[[ 	
		          	{field:'ck',checkbox:true},
		           	{field:'rowId',title:'ID',width:100,hidden:true},
		           	{field:'earShort',title:'耳缺号',width:100},
			        {field:'earBrand',title:'耳牌号',width:100},
			        {field:'sexName',title:'性别',width:100},
			        {field:'breedName',title:'品种',width:100,hidden:true},
			        {field:'swineryName',title:'批次名称',width:100,hidden:true},
			        {field:'pigInfo',title:'耳缺号-品种-状态(天)-日龄-猪舍-猪栏',width:300},
			        {field:'pigpenName',title:'猪栏名称',width:100,hidden:true},
			        {field:'pigClassName',title:'生产状态',width:100},
			        {field:'notes',title:'备注',width:100,hidden:true}
			    ]]
	});
	if(eventName == 'SELECT_BREED_PIG'){
		$('#selectPigListTable').datagrid('hideColumn','pigClassName').datagrid('hideColumn','earBrand');
	}else{
		$('#selectPigListTable').datagrid('hideColumn','sexName').datagrid('hideColumn','earShort');
	}
}
function houseChange(newValue,oldValue){
	$('#pigpenId').combogrid('grid').datagrid('reload',basicUrl+'/basicInfo/searchPigpenToList.do?mainId='+newValue);
}
/**
 * 选择猪只
 */
function selectPig(){

	var selectClassAndSwineryPigType = '';
	if(eventName == "BREED_PIG_DIE" || eventName == "BREED_PIG_OBSOLETE"){
		selectClassAndSwineryPigType = '1,2';
	}else{
		selectClassAndSwineryPigType = oldPigType;
	}
	leftSilpFun('selectPigWin',true,9001);
	var swineryGrid = $('#swineryId').combogrid('grid');
	swineryGrid.datagrid('load',basicUrl+'/production/searchSwineryToList.do?lineId=0&pigType='+selectClassAndSwineryPigType);
	var pigClassGrid = $('#pigClassId').combogrid('grid');
	pigClassGrid.datagrid('reload',basicUrl+'/backEnd/searchPigClassToListByEvent.do?pigType='+selectClassAndSwineryPigType+'&eventName='+eventName);
	var hasSelectRows = $('#listTable').datagrid('getData').rows;
	var pigIdsArray = [];
	$.each(hasSelectRows,function(i,data){
		if(data.pigId != null && data.pigId != ''){
			pigIdsArray.push(data.pigId);
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
	selectPigSearch();
}

/**
 * 搜索猪只
 */
function selectPigSearch(){
	var houseId = $('#houseId').combogrid('getValue');
	var pigpenId = $('#pigpenId').combogrid('getValue');
	var swineryId = $('#swineryId').combogrid('getValue');
	var earBrand =  $('#earBrand').textbox('getValue');
	var earShort =  $('#earShort').textbox('getValue');
	var pigClassId = $('#pigClassId').combogrid('getValue');
	var breedId = $('#breedId').combogrid('getValue');
	var minDateage = Number($('#minDateage').numberspinner('getValue'));
	var maxDateage = Number($('#maxDateage').numberspinner('getValue'));
	var goodPigFlag = 0;
	if($('#goodPigFlag').is(':checked')){
		goodPigFlag = 1;
	}
	var hasSelectRows = $('#listTable').datagrid('getData').rows;
	var pigIdsArray = [];
	$.each(hasSelectRows,function(i,data){
		if(data.pigId != null && data.pigId != ''){
			pigIdsArray.push(data.pigId);
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
	$('#selectPigListTable').datagrid('load',{
    	queryType:2,
    	pigType:oldPigType,
    	specifyPigs:0,
    	choice:1,
    	eventName:eventName,
    	houseIds:houseId,
    	pigpenIds:pigpenId,
    	swineryIds:swineryId,
    	earBrand:earBrand,
    	earShort:earShort,
    	pigClassIds:pigClassId,
    	breedIds:breedId,
    	minDateage:minDateage,
    	maxDateage:maxDateage,
    	pigIds:pigIds,
    	goodPigFlag:goodPigFlag
    });
}
/**
 * 确定
 */
function selectPigSure(){
	var selectRows = $('#selectPigListTable').datagrid('getSelections');
	if(selectRows.length < 1){
		$.messager.alert('警告', '请选择一条以上的数据！');
	}else{
		$('#selectPigSureBtn').attr('disabled',true).addClass('btn-disabled');
		var weanNum = 0;
		$.each(selectRows,function(index,data){
			var row = new Object(); 
			if(typeof(detailDefaultValues) == "undefined"){
				row ={
					status:'1',
					deletedFlag:'0'	
				};
			}else{
				for(var p in detailDefaultValues) { 
					var name=p;//属性名称 
					var value=detailDefaultValues[p];//属性对应的值 
					row[name]=detailDefaultValues[p]; 
				} 
			}
			row.pigId = data.pigId;
			row.earBrand = data.earBrand;
			row.earShort = data.earShort;
			row.pigInfo = data.pigInfo,
			row.minValidDate = data.minValidDate;
			row.maxValidDate = data.maxValidDate;
			row.enterDate = data.lastEventDate;
			row.pigQty = data.pigQty;
			row.houseId = data.houseId;
			row.pigClass = data.pigClass;
			if(eventName == 'WEAN' || eventName == 'CHILD_PIG_DIE' || eventName == 'FOSTER'){
				if(eventName == 'WEAN' && (dnflag == 'ON' || dnflag == 'on')){
					row.weanNum = data.pigQty;
					row.dieNum = 0;
					weanNum += parseInt(data.pigQty);
				}
			}else if(eventName == 'SELECT_BREED_PIG'){
				row.rowId = data.pigId;
				row.selectBreedType = data.selectBreedType;
				row.selectBreedTypeName = data.selectBreedTypeName;
				row.sex = data.sex;
				row.sexName = data.sexName;
				row.earCodeId = data.earCodeId;
				row.swineryId = data.swineryId;
				row.pigpenId = data.pigpenId;
				row.materialId = data.materialId;
			}else if(eventName == 'SEED_PIG'){
				row.rowId = data.pigId;
				row.parity = data.parity;
				row.houseName = data.houseName;
				row.pigpenName = data.pigpenName;
				row.pigQty = data.pigQty;
				row.seedMale = data.seedMale;
				row.seedFemale = data.seedFemale;
				row.aliveLitterX = data.seedMale;
				row.aliveLitterY = data.seedFemale;
			}else if(eventName == 'BREED_PIG_DIE'){
				row.pigType = data.pigType;
				row.pigClass = data.pigClass;
				row.pigpenId = data.pigpenId;
				row.pigQty = data.pigQty;
			}
			$('#listTable').datagrid('appendRow',row);
		});
		if(eventName == 'WEAN' && (dnflag == 'ON' || dnflag == 'on')){
			var weanNumSum = parseInt($('#weanNumSum').html());
			var rows = $('#listTable').datagrid('getRows');
			var weanWeightSum = 0;
			$.each(rows,function(i,item){
				if( i != editIndex && item.weanNum != undefined && item.weanNum != ''){
					weanWeightSum += parseFloat(item.weanWeight);
				}
			});
			weanNumSum += weanNum;
			$('#weanNumSum').html(weanNumSum);
			$('#weanNumAvg').html((weanNumSum/rows.length).toFixed(2));
			$('#weanWeightAvg').html((weanWeightSum/rows.length).toFixed(2));
		}else if(eventName == 'DELIVERY'){
			var rows = $('#listTable').datagrid('getRows');
			var healthyWeightSum = 0,healthyNumSum = 0;
			$.each(rows,function(i,item){
				if( i != editIndex && item.healthyNum != undefined && item.healthyNum != ''){
					healthyWeightSum += parseFloat(item.aliveLitterWeight);
					healthyNumSum += parseInt(item.healthyNum);
				}
			});
			$('#healthyNumAvg').html((healthyNumSum/rows.length).toFixed(2));
			$('#weightAvg').html((healthyWeightSum/rows.length).toFixed(2));
		}
		var hasSelectRows = $('#listTable').datagrid('getData').rows;
		var pigIdsArray = [];
		$.each(hasSelectRows,function(i,data){
			if(data.pigId != null && data.pigId != ''){
				pigIdsArray.push(data.pigId);
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
		var houseId = Number($('#houseId').combogrid('getValue'));
		var pigpenId = Number($('#pigpenId').combogrid('getValue'));
		var swineryId = Number($('#swineryId').combogrid('getValue'));
		var earBrand =  $('#earBrand').textbox('getValue');
		var earShort = $('#earShort').textbox('getValue');
		var pigClassId = $('#pigClassId').combogrid('getValue');
		var breedId = Number($('#breedId').combogrid('getValue'));
		var minDateage = Number($('#minDateage').numberspinner('getValue'));
		var maxDateage = Number($('#maxDateage').numberspinner('getValue'));
		$('#selectPigListTable').datagrid('load',{
        	queryType:2,
        	pigType:oldPigType,
        	specifyPigs:0,
        	choice:1,
        	eventName:eventName,
        	houseId:houseId,
        	pigpenId:pigpenId,
        	swineryId:swineryId,
        	earBrand:earBrand,
        	earShort:earShort,
        	pigClassIds:pigClassId,
        	breedId:breedId,
        	minDateage:minDateage,
        	maxDateage:maxDateage,
        	pigIds:pigIds
        });
	}
}
/**
 * 重置
 */
function selectPigReset(){
	$('#selectPigSearchForm').form('reset');
	var hasSelectRows = $('#listTable').datagrid('getData').rows;
	var pigIdsArray = [];
	$.each(hasSelectRows,function(i,data){
		if(data.pigId != null && data.pigId != ''){
			pigIdsArray.push(data.pigId);
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
	$('#selectPigListTable').datagrid('load',{
    	queryType:2,
    	pigType:oldPigType,
    	specifyPigs:0,
    	choice:1,
    	eventName:eventName,
    	pigIds:pigIds
    });
}
