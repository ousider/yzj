var title = '周月报';
var editIndex = undefined;
var prUrl='/production/';
var searchProduceDataCollectUrl=prUrl+'searchProduceDataCollectPage.do';
var editProduceDataCollectUrl=prUrl+'editProduceDataCollect.do';
var searchProduceDataCollectToListUrl=prUrl+'searchProduceDataCollectToList.do';
var saveUrl =prUrl+'editProduceDataCollectNotes.do';
var deleteUrl = prUrl+'deleteProductionCollect.do';
var weekRows = jAjax.submit('/param/getWeekInfo.do');
var reportId = 0;
$(document).ready(function(){
	$('#searchWeek').textbox('hide');
	$('#searchQuarter').textbox('hide');
	$('#searchYear').textbox('hide');
	$('#table').datagrid(
			j_grid({
				url:searchProduceDataCollectUrl,
				columnFields:'rowId,farmId,deletedFlag,reportTypeName,startDate,endDate,createDate,version,createName,notesName',
				columnTitles:'ID,farmId,deletedFlag,报表类型,开始时间,结束时间,创建时间,版本号,创建人,备注人',
				hiddenFields:'rowId,farmId,deletedFlag',
			})
	);
	xnCombotree({
		id:'selectFarm',
		url:'/OrganizationalStructure/searchCompanyTreeExceptDept.do',
		textField:'text'
	})
	//报表类型
	xnCdCombobox({ 
		id:'reportType',
		typeCode:'REPORT_TYPE',
		defaultValue:'2',
		onChange:selectReportType
	});
	//报表类型
	xnCdCombobox({ 
		id:'searchReportType',
		typeCode:'REPORT_TYPE'
	});
	xnMonthSelect({
		id:'searchMonth'
	});
	xnMonthSelect({
		id:'searchYear'
	});
	//季度
	xnCdCombobox({ 
		id:'searchQuarter',
		typeCode:'QUARTER_TYPE',
		onChange:searchQuarter
	});
	var currentDate = new Date();
	var startDate = new Date(currentDate.getFullYear(),currentDate.getMonth(),1);
	var endDate = new Date(currentDate.getFullYear(),currentDate.getMonth()+1,1);
	endDate = new Date(endDate.getTime() - 86400000);
	$('#startDate').datebox('setValue',startDate.format("yyyy-MM-dd"));
	$('#endDate').datebox('setValue',endDate.format("yyyy-MM-dd"));
	setTimeout(function(){
		$('#proDataListTable1').edatagrid(
			j_editableTable({
				columns:[[
							j_gridText({field:'rowId',title:'ID',hidden:true}),
			              	j_gridText({field:'proTypeName',title:'分类',width:80}),
			              	j_gridText({field:'indicatorName',title:'指标',width:80}),
			              	j_gridText({field:'pigQty',title:'头数',width:80}),
			              	j_gridText({field:'notes',title:'备注',width:80,editor:'textbox'})
					    ]]
			})	
		);
		$('#proDataListTable2').edatagrid(
			j_editableTable({
				columns:[[
							j_gridText({field:'rowId',title:'ID',hidden:true}),
			              	j_gridText({field:'proTypeName',title:'分类',width:80}),
			              	j_gridText({field:'indicatorName',title:'指标',width:80}),
			              	j_gridText({field:'groupTarget',title:'集团目标',width:80}),
			              	j_gridText({field:'groupActual',title:'集团实际生产',width:80}),
			              	j_gridText({field:'bestValue',title:'最优',width:80}),
			              	j_gridText({field:'bestFarmName',title:'最优猪场',width:80}),
			              	j_gridText({field:'bestPigQty',title:'最优头数',width:80}),
			              	j_gridText({field:'worstValue',title:'最差',width:80}),
			              	j_gridText({field:'worstFarmName',title:'最差猪场',width:80}),
			              	j_gridText({field:'worstPigQty',title:'最差头数',width:80}),
			              	j_gridText({field:'notes',title:'备注',width:80,editor:'textbox'})
					    ]]
			})	
		);
		$('#goodPigSellDataListTable').edatagrid(
			j_editableTable({
				fitColumns:false,
				columns:[[
				          j_gridText({field:'rowId',title:'ID',rowspan:2,hidden:true}),
				          j_gridText({field:'farmName',title:'猪场',rowspan:2,width:180}),
				          j_gridText({field:'totalNum',title:'上市总头数',rowspan:2,width:80}),
				          j_gridText({title:'苗猪上市',colspan:4,width:320}),
				          j_gridText({title:'肥猪上市',colspan:4,width:320}),
				          j_gridText({title:'种猪猪上市',colspan:4,width:320}),
				          j_gridText({field:'rate',title:'苗/肉比例',rowspan:2,width:80}),
				          j_gridText({field:'yearSaleTarget',title:'年销售目标',rowspan:2,width:80}),
				          j_gridText({field:'completeRate',title:'达成率',rowspan:2,width:80}),
				          j_gridText({field:'unblanceNum',title:'未结算头数',rowspan:2,width:80}),
				          j_gridText({field:'notes',title:'备注',rowspan:2,width:180,editor:'textbox'})
				          ],
				         [
				          	j_gridText({field:'mzNum',title:'头数',width:80}),
				          	j_gridText({field:'mzAvgWeight',title:'均重',width:80}),
				          	j_gridText({field:'mzPrice',title:'结算单价',width:80}),
				          	j_gridText({field:'mzTotalPrice',title:'销售总额',width:80}),
				          	j_gridText({field:'rzNum',title:'头数',width:80}),
				          	j_gridText({field:'rzAvgWeight',title:'均重',width:80}),
				          	j_gridText({field:'rzPrice',title:'结算单价',width:80}),
				          	j_gridText({field:'rzTotalPrice',title:'销售总额',width:80}),
				          	j_gridText({field:'zzNum',title:'头数',width:80}),
				          	j_gridText({field:'zzAvgWeight',title:'均重',width:80}),
				          	j_gridText({field:'zzPrice',title:'结算单价',width:80}),
				          	j_gridText({field:'zzTotalPrice',title:'销售总额',width:80})
					    ]]
			})	
		);
		$('#goodPigSellDataListTable').siblings('div').find('.datagrid-header').find('td').css('border','solid 1px #FFF')
		$('#weanDataListTable').edatagrid(
			j_editableTable({
				fitColumns:false,
				columns:[[
							j_gridText({field:'rowId',title:'ID',hidden:true}),
						    j_gridText({field:'farmName',title:'猪场',width:180}),
						    j_gridText({field:'weanTotalSize',title:'断奶总窝数',width:100}),
						    j_gridText({field:'liveNum',title:'产活仔数',width:100}),
						    j_gridText({field:'birthAvgWeight',title:'初生均重(KG)',width:100}),
						    j_gridText({field:'weanAvgDayage',title:'断奶平均日龄',width:100}),
						    j_gridText({field:'weanPigNum',title:'断奶头数',width:100}),
						    j_gridText({field:'avgWeanNum',title:'窝均断奶头数',width:100}),
						    j_gridText({field:'avgWeanWeight',title:'头均断奶重(KG)',width:180}),
						    j_gridText({field:'weanLitterWeight',title:'断奶窝重(KG)',width:180}),
						    j_gridText({field:'weanDayLitterWeight',title:'24日龄校正断奶均重(KG)',width:180}),
						    j_gridText({field:'avgDrfUsedNum',title:'头均代乳粉使用量(G)',width:180}),
						    j_gridText({field:'avgJcwUsedNum',title:'头均教槽王使用量(G)',width:180}),
			              	j_gridText({field:'notes',title:'备注',width:180,editor:'textbox'})
					    ]]
			})	
		);
		$('#keyIndicatorDataListTable').edatagrid(
			j_editableTable({
				columns:[
						[
						 j_gridText({field:'rowId',title:'ID',rowspan:2,hidden:true}),
						 j_gridText({field:'farmName',title:'猪场',rowspan:2,width:180}),
						 j_gridText({field:'sowPigNum',title:'母猪存栏数',rowspan:2,width:80}),
						 j_gridText({field:'goodPigNum',title:'商品猪存栏数',rowspan:2,width:80}),
						 j_gridText({title:'死亡率',colspan:2,width:160}),
						 j_gridText({title:'7030',colspan:2,width:160}),
						 j_gridText({title:'110KG出栏',colspan:2,width:160}),
						 j_gridText({title:'料肉比(月)',colspan:2,width:160}),
						 j_gridText({field:'notes',title:'备注',rowspan:2,width:180,editor:'textbox'})
						 ],
				         [
				          j_gridText({field:'allDeathRate',title:'全程(%)',width:80}),
				          j_gridText({field:'deathRateSort',title:'排名',width:80}),
				          j_gridText({field:'weight7030',title:'体重(KG)',width:80}),
				          j_gridText({field:'weightSort7030',title:'排名',width:80}),
				          j_gridText({field:'outDayage110kg',title:'出栏日龄',width:80}),
				          j_gridText({field:'outDayageSort110kg',title:'排名',width:80}),
				          j_gridText({field:'materialMeatRateWhole',title:'全程料肉比',width:80}),
				          j_gridText({field:'materialMeatRateFat',title:'育肥料肉比',width:80})
					    ]]
			})	
		);
		$('#keyIndicatorDataListTable').siblings('div').find('.datagrid-header').find('td').css('border','solid 1px #FFF');
	},500)
});
//textbox，datebox 显示与隐藏
$.extend($.fn.textbox.methods, {
	show: function(jq){
		return jq.each(function(){
			$(this).next().show();
		})
	},
	hide: function(jq){
		return jq.each(function(){
			$(this).next().hide();
		})
	}
});
function selectReportType(newValue,oldValue){
	if(newValue == 1){
		$('#week').css('display','inline-block');
		$('#searchWeek').textbox('show');
		$('#searchWeek').textbox('enable');
		$('#searchWeek').textbox('setValue',weekRows.year+'-'+weekRows.week);
		$('#startDate').datebox('setValue',weekRows.start_date);
		$('#endDate').datebox('setValue',weekRows.end_date);
		$('#month').css('display','none');
		$('#searchMonth').datebox('clear');
		$('#searchMonth').datebox('disable');
		$('#searchMonth').datebox('hide');
		$('#quarter').css('display','none');
		$('#searchQuarter').combobox('clear');
		$('#searchQuarter').combobox('disable');
		$('#searchQuarter').combobox('hide');
		$('#year').css('display','none');
		$('#searchYear').textbox('clear');
		$('#searchYear').textbox('disable');
		$('#searchYear').textbox('hide');
	}else if(newValue == 2){
		$('#week').css('display','none');
		$('#searchWeek').textbox('hide');
		$('#searchWeek').textbox('disable');
		$('#searchWeek').textbox('clear');
		$('#month').css('display','inline-block');
		$('#searchMonth').datebox('show');
		$('#searchMonth').datebox('setValue',getCurrentDate());
		$('#searchMonth').datebox('enable');
		$('#quarter').css('display','none');
		$('#searchQuarter').combobox('hide');
		$('#searchQuarter').combobox('disable');
		$('#searchQuarter').combobox('clear');
		$('#year').css('display','none');
		$('#searchYear').textbox('hide');
		$('#searchYear').textbox('disable');
		$('#searchYear').textbox('clear');
	}else if(newValue == 3){
		$('#week').css('display','none');
		$('#searchWeek').textbox('hide');
		$('#searchWeek').textbox('disable');
		$('#searchWeek').textbox('clear');
		$('#month').css('display','none');
		$('#searchMonth').datebox('clear');
		$('#searchMonth').datebox('disable');
		$('#searchMonth').datebox('hide');
		$('#quarter').css('display','inline-block');
		$('#searchQuarter').combobox('show');
		$('#searchQuarter').combobox('enable');
		var startDate,endDate,quarter;
		var date = new Date();
		var month=date.getMonth()+1;
		if(month <=3){
			startDate = date.getFullYear()+'-1-01';
			endDate = date.getFullYear()+'-3-31';
			quarter = 1;
		}else if(month <=6){
			startDate = date.getFullYear()+'-4-01';
			endDate = date.getFullYear()+'-6-30';
			quarter = 2;
		}else if(month <= 9){
			startDate = date.getFullYear()+'-10-01';
			endDate = date.getFullYear()+'-12-30';
			quarter = 3;
		}else if(month <= 12){
			startDate = date.getFullYear()+'-10-01';
			endDate = date.getFullYear()+'-12-31';
			quarter = 4;
		}
		$('#startDate').datebox('setValue',startDate);
		$('#endDate').datebox('setValue',endDate);
		$('#searchQuarter').combobox('setValue',quarter);
		$('#year').css('display','none');
		$('#searchYear').textbox('hide');
		$('#searchYear').textbox('disable');
		$('#searchYear').textbox('clear');
	}else if(newValue == 4){
		$('#week').css('display','none');
		$('#searchWeek').textbox('hide');
		$('#searchWeek').textbox('disable');
		$('#searchWeek').textbox('clear');
		$('#month').css('display','none');
		$('#searchMonth').datebox('clear');
		$('#searchMonth').datebox('disable');
		$('#searchMonth').datebox('hide');
		$('#quarter').css('display','none');
		$('#searchQuarter').combobox('hide');
		$('#searchQuarter').combobox('disable');
		$('#searchQuarter').combobox('clear');
		$('#year').css('display','inline-block');
		$('#searchYear').datebox('show');
		$('#searchYear').datebox('enable');
		$('#searchYear').datebox('clear');
		$('#searchYear').datebox('setValue',getCurrentDate());
	}
}

function selectMonth(){
	var month = $('#searchMonth').datebox("getValue");
	if(month != ''){
		var startDate = month+'-01';
		var startDateValue = new Date(startDate.replace('-','/').replace('-','/'));
		var endDateValue = new Date(startDateValue.getFullYear(),startDateValue.getMonth()+1,1);
		endDateValue = new Date(endDateValue.getTime() - 86400000);
		$('#startDate').datebox('setValue',startDate);
		$('#endDate').datebox('setValue',endDateValue.format("yyyy-MM-dd"));
	}
}
function selectWeek(newValue,oldValue){
	var arr = newValue.split('-');
	jAjax.submit('/param/getWeekInfo.do',{year:arr[0],week:arr[1]},function(result){
		$('#startDate').datebox('setValue',result.start_date);
		$('#endDate').datebox('setValue',result.end_date);
	});
}
function searchQuarter(){
	var quarter = $('#searchQuarter').combobox("getValue");
	var startDate,endDate;
	var date = new Date();
	if(quarter != ''){
		// 1-3
		if(quarter == 1){
			startDate = date.getFullYear()+'-1-01';
			endDate = date.getFullYear()+'-3-31';
		// 4-6
		}else if(quarter == 2){
			startDate = date.getFullYear()+'-4-01';
			endDate = date.getFullYear()+'-6-30';
		// 7-9
		}else if(quarter == 3){
			startDate = date.getFullYear()+'-7-01';
			endDate = date.getFullYear()+'-9-30';
		// 10-12
		}else if(quarter == 4){
			startDate = date.getFullYear()+'-10-01';
			endDate = date.getFullYear()+'-12-31';
		}
		$('#startDate').datebox('setValue',startDate);
		$('#endDate').datebox('setValue',endDate);
	}
}
function selectYear(){
	var date = $('#searchYear').datebox("getValue");
	var year = date.split("-",1);
	if(year != ''){
		$('#startDate').datebox('setValue',year+'-01-01');
		$('#endDate').datebox('setValue',year+'-12-31');
	}
}
//计算
function onBtnCalculate(){
	var selectFarm = $('#selectFarm').combobox('getValue');
	var reportType = $('#reportType').combobox('getValue');
	var startDate = $('#startDate').datebox('getValue');
	var endDate = $('#endDate').datebox('getValue');
	var searchMonth = $('#searchMonth').datebox('getValue');
	var searchWeek = $('#searchWeek').textbox('getValue');
	var searchQuarter = $('#searchQuarter').combobox('getValue');
	var searchYear = $('#searchYear').datebox('getValue');
	if(selectFarm == ''){
		$.messager.alert('警告','请选择猪场！');
	}
	if(reportType == ''){
		$.messager.alert('警告','请选择报表类型！');
	}
	if(startDate == ''){
		$.messager.alert('警告','请选择开始时间！');
	}
	if(endDate == ''){
		$.messager.alert('警告','请选择结束时间！');
	}
	if(searchMonth == '' && searchWeek == '' && searchQuarter == '' && searchYear == ''){
		$.messager.alert('警告','请选择年或者月或者季度或者年！');
	}
	$.messager.progress();	
	var data = {selectFarm:selectFarm,reportType:reportType,startDate:startDate,endDate:endDate,searchMonth:searchMonth,searchWeek:searchWeek,searchQuarter:searchQuarter,searchYear:searchYear};
	jAjax.submit(editProduceDataCollectUrl,data
	,function(response){
		$.messager.alert('警告','计算成功！');
		$('#table').datagrid('load');
		$.messager.progress('close');	
	},function(response){
		jAjax.errorFunc(response.errorMsg);
		$.messager.progress('close');
	},null,true,null,null);
}

function onBtnNotes(){
	var selections = $('#table').datagrid('getSelections');
	if(selections.length < 1){
		$.messager.alert('提示','请选择一条数据！');
	}else if(selections.length > 1){
		$.messager.alert('提示','只能选择一条数据！');
	}else{
		$('#editWin').window('open');
		var row = selections[0];
		reportId = row.rowId;
		//明细表加载数据
		$('#proDataListTable1').datagrid('load',basicUrl+searchProduceDataCollectToListUrl+'?rowId='+row.rowId+'&searchType='+1);
		$('#proDataListTable2').datagrid('load',basicUrl+searchProduceDataCollectToListUrl+'?rowId='+row.rowId+'&searchType='+2);
		$('#goodPigSellDataListTable').datagrid('load',basicUrl+searchProduceDataCollectToListUrl+'?rowId='+row.rowId+'&searchType='+3);
		$('#weanDataListTable').datagrid('load',basicUrl+searchProduceDataCollectToListUrl+'?rowId='+row.rowId+'&searchType='+4);
		$('#keyIndicatorDataListTable').datagrid('load',basicUrl+searchProduceDataCollectToListUrl+'?rowId='+row.rowId+'&searchType='+5);
		
		var data = jAjax.submit(searchProduceDataCollectToListUrl+'?rowId='+row.rowId+'&searchType='+6);
		if(data != null){
			$('#productionNotes').textbox('setValue',data[0].productionNotes);
			$('#biologicalSafetyNotes').textbox('setValue',data[0].biologicalSafetyNotes);
			$('#populationPlanningNotes').textbox('setValue',data[0].populationPlanningNotes);
			$('#saleNotes').textbox('setValue',data[0].saleNotes);
		}
	}
}

/**
 * 保存
 * pms{
 * 	model:模块代码
 * }
 */
function onBtnSave(){
	var queryParams;
	var proDataListTable1 = $('#proDataListTable1');
	var proDataListTable2 = $('#proDataListTable2');
	var goodPigSellDataListTable = $('#goodPigSellDataListTable');
	var weanDataListTable = $('#weanDataListTable');
	var keyIndicatorDataListTable = $('#keyIndicatorDataListTable');
	//判断是否有明细表
	if(proDataListTable1!= null || proDataListTable2 != null || goodPigSellDataListTable != null || weanDataListTable != null || keyIndicatorDataListTable != null){
		queryParams = collectDetailData();
		if(queryParams == null){
			$.messager.alert('提示','前台提示--未添加明细不能提交！');
			return;
		}
	}else{
		queryParams = {
			status:1,
			deletedFlag:0,
		};
	}
	$('#btnSave').attr('disabled',true).addClass('btn-disabled');
	$.messager.progress();
	$('#editForm').form('submit',{
		url:basicUrl+saveUrl,
		queryParams:queryParams,
		onSubmit: function(){
			var isValid = $('#editForm').form('validate');
			if (!isValid){
				$('#btnSave').attr('disabled',false).removeClass('btn-disabled');
				$.messager.progress('close');	
			}
			return isValid;
		},
		success: function(response){
			response=eval('('+response+')');
			if(response.success){
				$('#editWin').window('close');
				$.messager.alert('提示','保存成功！');
				
				var table = document.getElementById("table");
				if(table != null){
					$('#table').datagrid('reload');
				}
			}else{
				 $.messager.alert({
                     title: '错误',
                     msg: response.errorMsg
                 });
			}
			$('#btnSave').attr('disabled',false).removeClass('btn-disabled');
			$.messager.progress('close');
		}
	});
}

/**
 * 获取明细表提交数据数据（有一个明细表，如有多个明细表需重写）
 */
function collectDetailData(){
	var queryParams;
	var proDataListTable1 = $('#proDataListTable1');
	var rowProData1s =  proDataListTable1.datagrid('getRows');
	for(var i = 0 ; i < rowProData1s.length ; i ++){
		proDataListTable1.datagrid('endEdit',i);
	}
	var proDataListTable2 = $('#proDataListTable2');
	var rowProData2s =  proDataListTable2.datagrid('getRows');
	for(var i = 0 ; i < rowProData2s.length ; i ++){
		proDataListTable2.datagrid('endEdit',i);
	}
	var goodPigSellDataListTable = $('#goodPigSellDataListTable');
	var rowGoodPigSellData =  goodPigSellDataListTable.datagrid('getRows');
	for(var i = 0 ; i < rowGoodPigSellData.length ; i ++){
		goodPigSellDataListTable.datagrid('endEdit',i);
	}
	var weanDataListTable = $('#weanDataListTable');
	var rowWeanData =  weanDataListTable.datagrid('getRows');
	for(var i = 0 ; i < rowWeanData.length ; i ++){
		weanDataListTable.datagrid('endEdit',i);
	}
	var keyIndicatorDataListTable = $('#keyIndicatorDataListTable');
	var rowKeyIndicatorData =  keyIndicatorDataListTable.datagrid('getRows');
	for(var i = 0 ; i < rowKeyIndicatorData.length ; i ++){
		keyIndicatorDataListTable.datagrid('endEdit',i);
	}
	
	$.each(rowProData1s,function(index,data){
		rowProData1s[index].lineNumber = index+1;
	});
	$.each(rowProData2s,function(index,data){
		rowProData2s[index].lineNumber = index+1;
	});
	$.each(rowGoodPigSellData,function(index,data){
		rowGoodPigSellData[index].lineNumber = index+1;
	});
	$.each(rowWeanData,function(index,data){
		rowWeanData[index].lineNumber = index+1;
	});
	$.each(rowKeyIndicatorData,function(index,data){
		rowKeyIndicatorData[index].lineNumber = index+1;
	});
	var listTableProData1JsonString = JSON.stringify(rowProData1s);
	var listTableProData2JsonString = JSON.stringify(rowProData2s);
	var listTableGoodPigSellJsonString = JSON.stringify(rowGoodPigSellData);
	var listTableWeanDataJsonString = JSON.stringify(rowWeanData);
	var listTableKeyIndicatorJsonString = JSON.stringify(rowKeyIndicatorData);
	
	var productionNotesValue = $('#productionNotes').textbox('getValue');
	var biologicalSafetyNotesValue = $('#biologicalSafetyNotes').textbox('getValue');
	var populationPlanningNotesValue = $('#populationPlanningNotes').textbox('getValue');
	var saleNotesValue = $('#saleNotes').textbox('getValue');
	
	queryParams = {
			status:1,
			deletedFlag:0,
			reportId:reportId,
			productionNotesKey:productionNotesValue,
			biologicalSafetyNotesKey:biologicalSafetyNotesValue,
			populationPlanningNotesKey:populationPlanningNotesValue,
			saleNotesKey:saleNotesValue,
			listTableProData1:listTableProData1JsonString,
			listTableProData2:listTableProData2JsonString,
			listTableGoodPigSell:listTableGoodPigSellJsonString,
			listTableWeanData:listTableWeanDataJsonString,
			listTableKeyIndicator:listTableKeyIndicatorJsonString
		};
	return queryParams;
}
//查看
function onBtnCheck(){
	var dateTime = new Date().toLocaleDateString();
	var selections = $('#table').datagrid('getSelections');
	var row = selections[0];
	var finereportUrl = $('#finereport_url').val();
	var finereportUsername = $('#finereport_username').val();
	var employName = $('#employName').val();
	var farmName = $('#farmName').val();
	var farmId = $('#farmId').val();
	window.open('http://www.xnplus.cn:4380/WebReport/ReportServer?reportlet=XNPLUS%2FProduceDateReport.cpt'+'&dbUrl='
			+finereportUrl+"&dbUserName="+finereportUsername+"&farmId="+farmId+"&farmName="+farmName+"&userName="
			+employName+"&reportId="+row.rowId+'&__bypagesize__=false');
}
//高级查询
function onBtnSearch(){
	var searchReportType = $('#searchReportType').combobox('getValue');
	var startDate = $('#searchStartDate').datebox('getValue');
	var endDate = $('#searchEndDate').datebox('getValue');
	var version = $('#searchVersion').numberspinner('getValue');
	var isHis = 0;
	if($('#isHis').is(':checked')){
		isHis = 1;
	}
	var data = {searchReportType:searchReportType,startDate:startDate,endDate:endDate,version:version,isHis:isHis};
	$('#table').datagrid('load',basicUrl+searchProduceDataCollectUrl+'?searchReportType='+searchReportType+'&startDate='+startDate+'&endDate='+endDate+'&version='+version+'&isHis='+isHis);
}
