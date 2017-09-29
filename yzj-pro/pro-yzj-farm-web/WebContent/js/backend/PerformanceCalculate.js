var prUrl='/backEnd/';
var searchPerformanceManageToListSearchUrl = prUrl+'performanceCalculateToList.do';
var searchPerformanceManageRecordToListSearchUrl = prUrl+'searchPerformanceCalculateToList.do';
var editIndex = undefined;
$(document).ready(function(){
	$('#listTable').datagrid(
		j_detailGrid({	
			width:'100%',
			height:'100%',
			fit:true,
			//url:'/backEnd/performanceCalculateToList.do',
			columns:[[
		              	j_gridText({field:'rowId',title:'ID',hidden:true}),
		              	j_gridText({field:'assessRegion',title:'区域',width:100,formatter:function(value,row){
	              			return row.assessRegionName
	          			}}),
	          			j_gridText({field:'assessContent',title:'考核内容',width:200,formatter:function(value,row){
	              			return row.assessContentName
	          			}}),
		              	j_gridText({field:'minAssessIndex',title:'第一梯度',width:100,style:true}),
		              	j_gridText({field:'assessIndex',title:'达标指标',width:100,style:true}),
		              	j_gridText({field:'maxAssessIndex',title:'第二梯度',width:100,style:true}),
		              	j_gridText({field:'reward',title:'奖励金额',width:100,style:true}),
		              	j_gridText({field:'offset',title:'增减量',width:100,style:true}),
		              	j_gridText({field:'increasedAmount',title:'增加金额',width:100,style:true}),
		              	j_gridText({field:'systemIndex',title:'系统指标',width:100,style:true}),
		              	j_gridText({field:'handModulus',title:'各阶段存栏指标',width:100,style:true}),
		              	j_gridText({field:'acutalAvgHand',title:'实际平均存栏',width:200,style:true}),
		              	j_gridText({field:'modulusStandardHand',title:'系数标准存栏',width:200,style:true}),
		              	/*j_gridNumber({field:'ultimaIndex',title:'最终指标',width:100,style:true,precision:1,increment:0.1,min:0,color:'#66CDAA',onChange:amountChangeFun}),*/
		              	j_gridText({field:'systemAmount',title:'金额系统（元）',width:200,style:true}),
		              	j_gridNumber({field:'ultimaAmount',title:'金额调整（元）',width:200,style:true,precision:0,increment:100,min:0,color:'#66CDAA',onChange:amountChangeFun}),
		              	j_gridText({field:'differenceAmount',title:'金额差异（元）',width:200,style:true}),
		              	j_gridText({field:'notes',title:'数据说明',width:450,style:true}),
		              	j_gridText({field:'farmNotes',title:'场长修改备注',width:200,editor:'textbox',style:true,color:'#66CDAA'}),
				    ]]
		})
	);
	xnCdCombobox({
		id:'roleType',
		typeCode:'PERFORMANCE_ROLE_TYPE',
	});
	xnCdCombobox({
		id:'searchRoleType',
		editable:false,
		required:true,
		multiple:true,
		typeCode:'PERFORMANCE_ROLE_TYPE',
	});
	xnMonthSelect({
		id:'searchDate'
	});
});
function initListTableRecord(tableObj){
	tableObj.datagrid(
			j_detailGrid({
				toolbar:"#listTableToolbar",
				columns:[[
					    j_gridText({field:'rowId',title:'ID',hidden:true}),
						j_gridText({field:'assessRegion',title:'区域',width:100,formatter:function(value,row){
							return row.assessRegionName
						}}),
						j_gridText({field:'assessContent',title:'考核内容',width:200,formatter:function(value,row){
							return row.assessContentName
						}}),
						j_gridText({field:'minAssessIndex',title:'第一梯度',width:100,style:true}),
						j_gridText({field:'assessIndex',title:'达标指标',width:100,style:true}),
						j_gridText({field:'maxAssessIndex',title:'第二梯度',width:100,style:true}),
						j_gridText({field:'reward',title:'奖励金额',width:100,style:true}),
						j_gridText({field:'offset',title:'增减量',width:100,style:true}),
						j_gridText({field:'increasedAmount',title:'增加金额',width:100,style:true}),
						j_gridText({field:'systemIndex',title:'系统指标',width:100,style:true}),
						j_gridText({field:'handModulus',title:'各阶段存栏指标',width:100,style:true}),
		              	j_gridText({field:'acutalAvgHand',title:'实际平均存栏',width:200,style:true}),
		              	j_gridText({field:'modulusStandardHand',title:'系数标准存栏',width:200,style:true}),
						j_gridText({field:'systemAmount',title:'金额（元）系统',width:200,style:true}),
						j_gridText({field:'ultimaAmount',title:'金额（元）调整',width:200,style:true}),
						j_gridText({field:'differenceAmount',title:'金额（元）差异',width:200,style:true}),
						j_gridText({field:'notes',title:'数据说明',width:450,style:true}),
						j_gridText({field:'farmNotes',title:'场长备注',width:200,editor:'textbox',style:true}),
			    ]],
		})
	);
}
/**
 * 保存
 */
function doSave(){
	var roleType = $('#roleType').combobox('getValue');
	$('#listTable').datagrid('endEdit',editIndex);
	var changes  = $('#listTable').datagrid('getRows');
	if(changes.length > 0){
		$.messager.progress();
		var rows = $('#listTable').datagrid('getRows');
		var jsonString = JSON.stringify(rows);
		$('#save').attr('disabled',true).addClass('btn-disabled');
		$.messager.progress();
		$.post({
			url:basicUrl + '/backEnd/editPerformanceCalculate.do',
			data:{
				roleType:roleType,
				gridList:jsonString
			},
			success:function(response){
				response=eval('('+response+')');
				if(response.success){
					$('#listTable').datagrid('loadData',{success:true,total:0,rows: []});
					$.messager.alert('提示','保存成功！');
				}else{
					$.messager.alert({
	                    title: '错误',
	                    msg: response.errorMsg
	                });
				}
				$('#save').attr('disabled',false).removeClass('btn-disabled');
				$.messager.progress('close');
			}
		});
	}else{
		$.messager.alert('提示','没有修改任何数据！');
	}
	
}

/**
 * 计算
 */
function doCalculate(){
	var roleType = $('#roleType').combobox('getValue');
	if(roleType == "" || roleType == undefined || roleType == null){
		$.messager.alert('提示','请输入员工类型！');
	}else{
		$.messager.progress();
		var data= {'roleType':roleType};
		 //jAjax.submit(searchPerformanceManageToListSearchUrl+'?roleType='+roleType);
		 jAjax.submit(searchPerformanceManageToListSearchUrl,data
			,function(response){
				$.messager.alert('提示','计算成功！');
				$('#listTable').datagrid('loadData',{success:true,total:0,rows: response});
				$.messager.progress('close');
			}
			,function(response){
				jAjax.errorFunc(response.errorMsg);
				$.messager.progress('close');
			},null,true,null,null);
		
		$("#save").removeAttr("disabled");
		$("#save").css('background-color','rgb(113, 175, 76)');
	}
}
/**
 * 最终指标
 * @param newValue
 * @param oldValue
 */
function amountChangeFun(newValue,oldValue){
	if(editIndex == undefined){
		return;
	}
	var row = $('#listTable').datagrid('getRows')[editIndex];
	//最终指标
	var ultimaIndex = 0;
	if(newValue != ''){
		c = strToFloat(newValue);
	}
	
	row.differenceAmount = (accMul(newValue,10) - accMul(row.systemAmount,10)) / 10;
	changeTableDisplayValue('listTable',editIndex,[{
		field:'differenceAmount',
		value:row.differenceAmount
	}]);
	
	/*//达标指标
	var assessIndex = row.assessIndex;
	//奖励金额
	var reward = row.reward;
	//增减量
	var offset = row.offset;
	//增加金额
	var increasedAmount = row.increasedAmount;
	//系统金额
	var systemAmount = row.systemAmount;
	//调整金额
	var ultimaAmount = 0;
	//差异金额
	var differenceAmount = 0;
	//第一梯度
	var minAssessIndex = row.minAssessIndex;
	//第二梯度
	var maxAssessIndex = row.maxAssessIndex;
	var unit =1;
	while(offset < 1){
		offset = offset * 10;
		unit=unit*10;
	}
	
	
	if(assessIndex != null && minAssessIndex == null && maxAssessIndex == null){
		if(newValue >= assessIndex){
			row.ultimaAmount = Math.floor(newValue*10 - assessIndex*10) / 10 / (offset*unit) * unit * increasedAmount + reward;
		}else{
			row.ultimaAmount = 0;
		}
	}
	if(assessIndex == null && minAssessIndex != null && maxAssessIndex != null){
		if(newValue < minAssessIndex){
			row.ultimaAmount = 0;
		}else if(newValue >= minAssessIndex && newValue < maxAssessIndex){
			row.ultimaAmount = reward;
		}else if(newValue >= maxAssessIndex){
			row.ultimaAmount = Math.floor(reward + increasedAmount);
		}
	}
	if(assessIndex != null && minAssessIndex != null && maxAssessIndex != null){
		if(newValue > maxAssessIndex){
			row.ultimaAmount = 0;
		}else if(newValue > assessIndex && newValue <= maxAssessIndex){
			row.ultimaAmount = reward;
		}else if(newValue>minAssessIndex && newValue<=assessIndex){
			row.ultimaAmount = Math.round(reward + increasedAmount);
		}else if(newValue<=minAssessIndex){
			row.ultimaAmount = Math.round(reward + increasedAmount*2);
		}
	}
	
	row.differenceAmount = (accMul(row.ultimaAmount,10) - accMul(systemAmount,10)) / 10;
	changeTableDisplayValue('listTable',editIndex,[{
		field:'ultimaAmount',
		value:row.ultimaAmount
	},{
		field:'differenceAmount',
		value:row.differenceAmount
	}]);*/
}

function doPrint(farmId,farmName,userName,dbUrl,dbUserName){
	var dateTime = new Date().toLocaleDateString();
	var searchRoleType = $('#searchRoleType').combobox('getValues');
	if(searchRoleType == "" || searchRoleType == undefined || searchRoleType == null){
		$.messager.alert('提示','请输入员工类型！');
	}else{
		var str = "";
		for(var i=0;i<searchRoleType.length;i++){
			if(searchRoleType[i] != ""){
				if(i == searchRoleType.length - 1){
					str += searchRoleType[i];
				}else{
					str += searchRoleType[i]+',';
				}
			}
		}
		window.open("http://www.xnplus.cn:4380/WebReport/ReportServer?reportlet=XNPLUS%2FAchievements.cpt&dbUrl="+dbUrl+"&dbUserName="+dbUserName+"&farmId="+farmId+"&farmName="+farmName+"&userName="+userName+"&yearMonth="+dateTime+"&searchRoleType="+str);
	}
}
function accMul(arg1,arg2) {
    var m=0,s1=arg1.toString(),s2=arg2.toString();
    try{m+=s1.split(".")[1].length}catch(e){}
    try{m+=s2.split(".")[1].length}catch(e){}
    return  Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m);
  }
function doSearCh(){
	$('#editWin').window({
		title:'查询绩效'
	});
	$('#editWin').window('open');
	$('#editForm').form('reset');
	$('#btnSave').css('display','inline-block');
//	$('#requirePigOnly').css('display','none');
//	$('#requireDestId').combogrid('disable');
	$('#billDate').datebox('setValue',getCurrentDate());
	initListTableRecord($('#listTableRecord'));
	//saveUrl = prUrl+'editRequireStore.do?editType=insert';
	/*var listTable = document.getElementById('listTable');
	if(listTable!=null){
		$('#listTable').datagrid('loadData',{success:true,total: 0, rows: [] });
	}*/
}

function onSearchRecord(){
	var searchDate = $('#searchDate').combobox('getValue');
	var searchRoleType = $('#searchRoleType').combobox('getValues');
	var validFlag = true;
	if(searchDate == "" || searchDate == undefined || searchDate == null){
		$.messager.alert('提示','请输入日期！');
		validFlag = false;
	}
	if(searchRoleType == "" || searchRoleType == undefined || searchRoleType == null){
		$.messager.alert('提示','请输入员工类型！');
		validFlag = false;
	}else{
		var str = "";
		for(var i=0;i<searchRoleType.length;i++){
			if(searchRoleType[i] != ""){
				if(i == searchRoleType.length - 1){
					str += searchRoleType[i];
				}else{
					str += searchRoleType[i]+',';
				}
			}
		}
	}
	if(validFlag){
		$.messager.progress();
		var data= {'searchDate':searchDate,'searchRoleType':str};
		 //jAjax.submit(searchPerformanceManageToListSearchUrl+'?roleType='+roleType);
		 jAjax.submit(searchPerformanceManageRecordToListSearchUrl,data
			,function(response){
				$('#listTableRecord').datagrid('loadData',{success:true,total:0,rows: response});
				$.messager.progress('close');
			}
			,function(response){
				jAjax.errorFunc(response.errorMsg);
				$.messager.progress('close');
			},null,true,null,null);
	}
}

