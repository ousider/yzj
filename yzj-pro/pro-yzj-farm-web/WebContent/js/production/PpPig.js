var title = '猪只';
var editIndex = undefined;
var prUrl='/production/';
var saveUrl=prUrl+'editPig.do';
var deleteUrl=prUrl+'deletePigs.do';
var searchMainListUrl=prUrl+'searchPigToPage.do';
var searchDetailListUrl=prUrl+'searchPigToList.do';
var pigCheckLogoUrl=prUrl+'pigCheckLogo.do';
var pigCancelLogoUrl=prUrl+'pigCancelLogo.do';
/** *********页面渲染开始*************************************************************************/
//查询按钮
$(document).ready(function(){
	/**
	 * 主表加载
	 */
	$('#table').datagrid(
			j_grid({
				toolbar:'#pigTableToolbar',
				url:searchMainListUrl,
				columnFields:'rowId,deletedFlag,earBrand,earShort,earThorn,swineryName,lineName,houseName,materialName,pigType,pigTypeName,sexName,breedName,lastClassAge,parity,boardEarBrand,status,uniquePigFlag',
				columnTitles:'ID,deletedFlag,耳牌号,耳缺号,耳刺号,批次名称,产线名称,猪舍名称,物料名称,猪类别,猪类别,性别,品种名称,猪只状态日龄,胎次,代养母猪,是否标识,猪只唯一标识',
				hiddenFields:'rowId,deletedFlag,pigType,lineName,earThorn,uniquePigFlag',
				formatterFields:'earBrand',
				formatter:[linkEarBrand]
			})
	);
	
	/*xnCdCombobox({
	    id:'swineryName',
	    typeCode:'SWINERY_NAME'
	});*/
	
	 // 批次名称
	xnCombobox({
	    id:'swineryName',
	    url:'/production/searchSwineryToList.do?lineId=0&pigType=1,2,3',
	    valueField:'rowId',
	    textField:'swineryName'
		
	});
	// 猪舍名称
	xnCombobox({
	    id:'houseName',
	    url:'/basicInfo/searchHouseToList.do?lineId=0',
	    valueField:'rowId',
	    textField:'houseName'
		
	});
	
	//物料名称
	xnCombobox({
	    id:'materialId',
	    url:'/material/searchMaterialToList.do?pigType=1,2,3',
	    valueField:'rowId',
	    textField:'materialName'
		
	});
	//猪只状态
	xnCombobox({
	    id:'pigClass',
	    url:'/backEnd/searchPigClassToList.do?pigType=',
	    valueField:'rowId',
	    textField:'pigClassName'
		
	});
	
	//猪只类别
	/*xnCombobox({
	    id:'pigTypeName',
	    url:saerchMainListUrl,
	    valueField:'rowId',
	    textField:'pigTypeName'
		
	});*/

	xnCdCombobox({
	    id:'pigTypeName',
	    typeCode:'PIG_TYPE'
	});
	
	//品种名称
	xnCombobox({
	    id:'breedName',
	    url:'/backEnd/searchBreedToList.do',
	    valueField:'rowId',
	    textField:'breedName'
		
	});
	$('#showPigHis').datagrid(
			j_grid_view({
				haveCheckBox:false,
				columnFields:'rowId,farmName,houseName,pigpenId,workerName,lastEventDate,eventNameChinese,createName,createDate,eventNotes',
				columnTitles:'ID,场区,猪舍,猪栏,技术员,事件时间,事件名称,录入员,录入时间,备注',
				hiddenFields:'rowId,houseName,pigpenId,createName,createDate',
				columnWidths:'5,60,100,100,70,100,100,100,100,250',
				fit:false,
				height:'67%',
				width:'100%'
			},'showPigHis')
	);
});
/*************页面渲染结束******************************************************************************/

/**
 * 耳号的超链接
 */
function linkEarBrand(value,rowData,rowIndex){
	
	//添加超链接 
	return "<a href='javacript:;' class='editcls' onclick='showPigHis("+rowData.rowId+","+rowData.pigType+");'>"+value+"</a>";  
}

/**
 * 根据猪只耳号显示历史
 */
function showPigHis(pigId,pigType){
	//事件类型
	xnCdCombobox({
		id:'eventName',
		typeCode:'EVENT_NAME',
		editable:false,
		hasAll:true
	},pigType);
	$('#showPigHis').datagrid('load',basicUrl+'/pig/searchPigHisToPage.do'+'?pigId='+pigId);
	leftSilpFun('pigCard');
	$('#pigId').textbox('setValue',pigId);
}

function searchPigHis(){
	 $("#showPigHis").datagrid("load",form2Json("showPigHisForm"));
}

function editPigEventCancelByBtn(){
	var record = $('#table').datagrid('getSelections');
	var length = record.length;
	if(length < 1){
		$.messager.alert('警告','请选择一条数据！');
	}else if(length > 1){
		$.messager.alert('警告','只能选择一条数据！');
	}else{
		$.messager.confirm('提示','确定要撤销 该猪只的<b>【'+record[0].eventNameChinese+'】</b>事件?',function(r){
		    if (r){
		    	var data ={billId:record[0].lastBillId,
		    			eventCode:record[0].eventCode,
		    			eventNameChinese:record[0].eventNameChinese,
		    			pigId:record[0].rowId
		    			}
		    	var url = "/cancel/editEventCancel.do";
		    	jAjax.submit(url,data,function(response){
		    		$('#table').datagrid('reload');
		    		$.messager.alert('提示','撤销成功！');
		    		$.messager.progress('close');
		    	},function(response){
		    		jAjax.errorFunc(response.errorMsg);
		    		$.messager.progress('close');
		    	},null,true,null,false);
		    }
		});
	}
}

function pigCard(farmId,farmName,userName,dbUrl,dbUserName){
	var record = $('#table').datagrid('getSelections');
	var length = record.length;
	if(length < 1){
		$.messager.alert('警告','请选择一条数据！');
	}else{
		var pigId = "";
		var pigType = "";
		var flag = 1;
		var errorEarBrand = "";
		var uniquePigFlag = "";
		$.each(record,function(i,data){
			if(pigType == "" || pigType == data.pigType){
				if(data.pigType == 2){
					pigType = 2;
					if(i < length -1){
						pigId += data.rowId + ',';
						uniquePigFlag += '\''+data.uniquePigFlag + '\',';
					}else{
						pigId += data.rowId;
						uniquePigFlag += '\''+data.uniquePigFlag+'\'';
					}
				}else if(data.pigType == 1){
					pigType = 1;
					if(i < length -1){
						pigId += data.rowId + ',';
						uniquePigFlag += '\''+data.uniquePigFlag + '\',';
					}else{
						pigId += data.rowId;
						uniquePigFlag += '\''+data.uniquePigFlag+'\'';
					}
				}else{
					if(flag == 1){
						flag = 2;
					}
					if(i < length -1){
						errorEarBrand += data.earBrand + ','
					}else{
						errorEarBrand += data.earBrand
					}
				}
			}else{
				flag = 3;
			}
		})
		if(flag == 1){
			if(pigType == 1){
				window.open("http://www.xnplus.cn:4380/WebReport/ReportServer?reportlet=XNPLUS%2FboarCard.cpt&dbUrl="+dbUrl+"&dbUserName="+dbUserName+"&farmId="+farmId+"&pigId="+pigId+"&uniquePigFlag="+uniquePigFlag+"&farmName="+farmName+"&userName="+userName);
//				window.open("http://localhost:8075/WebReport/ReportServer?reportlet=XNPLUS%2FboarCard.cpt&dbUrl="+dbUrl+"&dbUserName="+dbUserName+"&farmId="+farmId+"&pigId="+pigId+"&uniquePigFlag="+uniquePigFlag+"&farmName="+farmName+"&userName="+userName);
			}else{
				window.open("http://www.xnplus.cn:4380/WebReport/ReportServer?reportlet=XNPLUS%2FsowCard.cpt&dbUrl="+dbUrl+"&dbUserName="+dbUserName+"&farmId="+farmId+"&pigId="+pigId+"&uniquePigFlag="+uniquePigFlag+"&farmName="+farmName+"&userName="+userName);
			}
		}else if(flag == 2){
			$.messager.alert("警告","耳牌号："+errorEarBrand+"为肉猪，请重新选择！")
		}else{
			$.messager.alert("警告","请选择全部为公猪，或者全部为母猪，请重新选择！")
		}
	}
}

function pigCheckLogo(){
	var record = $('#table').datagrid('getSelections');
	var length = record.length;
	if(length < 1){
		$.messager.alert('警告','请选择一条数据！');
	}else{
		$.messager.confirm('提示', '确定要标识这'+record.length+'条记录吗？', function(r){
			if (r){
				$.messager.progress();	
				var ids = [];
				$.each(record,function(index,data){
					if(data.ROW_ID!=null){
						ids.push(data.ROW_ID);
					}else{
						ids.push(data.rowId);
					}
					
				});
				$.ajax({
				    type: 'POST',
				    url: basicUrl+pigCheckLogoUrl ,
				    data: {
				    	'ids':ids
				    } ,
				    success: function(response){
				    	$.messager.progress('close');
				    	if(response.success){
				    		$.messager.alert('警告','标识成功！');
				    		$('#table').datagrid('reload');
				    	}else{
				    		$.messager.alert({
			                     title: '错误',
			                     msg: response.errorMsg
			                 });
				    	}
				    	
				    },
				    dataType:'JSON'
				});
			}
		});
		
	}
}

function pigCancelLogo(){
	var record = $('#table').datagrid('getSelections');
	var length = record.length;
	if(length < 1){
		$.messager.alert('警告','请选择一条数据！');
	}else{
		$.messager.confirm('提示', '确定要取消标识这'+record.length+'条记录吗？', function(r){
			if (r){
				$.messager.progress();	
				var ids = [];
				$.each(record,function(index,data){
					if(data.ROW_ID!=null){
						ids.push(data.ROW_ID);
					}else{
						ids.push(data.rowId);
					}
					
				});
				$.ajax({
				    type: 'POST',
				    url: basicUrl+pigCancelLogoUrl ,
				    data: {
				    	'ids':ids
				    } ,
				    success: function(response){
				    	$.messager.progress('close');
				    	if(response.success){
				    		$.messager.alert('警告','取消标识成功！');
				    		$('#table').datagrid('reload');
				    	}else{
				    		$.messager.alert({
			                     title: '错误',
			                     msg: response.errorMsg
			                 });
				    	}
				    	
				    },
				    dataType:'JSON'
				});
			}
		});
		
	}
}

