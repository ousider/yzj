//var title = '';
var editIndex = undefined;
var prUrl='/production/';
//批量报废
var batchScrapUrl=prUrl+'editBatchScrapEdit.do';
//批量反报废
var batchTrunOverScrapUrl=prUrl+'editBatchTrunOverScrapEdit.do';
//精液管理主页面
var searchMainListUrl=prUrl+'searchSpermToPage.do';
//精液管理查看页面
var searchDetailListUrl=prUrl+'searchSpermDetailToPage.do';
//报废
var scrapUrl=prUrl+'editScrapEdit.do';
//反报废
var trunOverScrapUrl=prUrl+'editTrunOverScrapEdit.do';
//批量打印打印
var editBatchPrintUrl=prUrl+'editBatchPrint.do';

var searchSpermToSaleToListUrl=prUrl+'searchSpermToSaleToList.do';
/** *********页面渲染开始*************************************************************************/
//查询按钮
$(document).ready(function(){
	/**
	 * 主表加载
	 */
	$('#table').datagrid(
			j_grid({
				toolbar:'#tableToolbarList',
				url:searchMainListUrl+"?requestType=0"+"&ascending=0",
				columnFields:'rowId,earBrand,breedAndDayAge,semenBatchNo,semenQty,anhNum,spec,semenBatchNoSpec,semenDate,validDate,hasUsed,expired,scrapped,hasSaled,inventoryQuantity,supplierIdName,manufacturer,printNum',
				columnTitles:'rowId,公猪耳号,品种-日龄,精液批号,精液量,精液稀释份数,规格(ML/份),稀释比例,采精日期,过期日期,已使用,过期失效,已报废,已销售,库存数量,供应商,生产厂家,打印次数',
				hiddenFields:'rowId',
				columnWidths:'50,100,100,150,80,100,100,100,100,100,100,100,100,100,100,100,100,100',
				//pagination:false,
				onDblClickRowFun:showSpermDetail,
				formatterFields:'semenBatchNo',
				formatter:[semenBatchNoFormatter]
			}));
	
	//延时加载细表
	setTimeout(function(){
	
		//精液管理公猪品种下拉加载
		xnCombobox({
			id:'breedName',
			valueField:'rowId',
			textField:'breedName',
			url:'/backEnd/searchBreedToList.do',
			hasAll:true
		});
		
		//点击精液明细批号
		$('#semenBatchNoClick').datagrid(
				j_grid_view({
					toolbarList:'#semenBatchNoTaleToolbar',
					haveCheckBox:true,
					columnFields:'rowId,mianId,semenCode,statusName,statusReason,statusDate,scrapReason',
					columnTitles:'ID,mianId,精液明细批次号,状态,状态原因,状态日期,具体原因',
					hiddenFields:'rowId,mianId',
					columnWidths:'50,50,150,100,100,100,100',
					pagination:false,
					fit:false,
					height:'90%',
					width:'100%'
				},'semenBatchNoClick')
		);

	
});
})
/*************页面渲染结束******************************************************************************/

//批量报废
function batchScrap(){
		var record = $('#table').datagrid('getSelections');
		var length = record.length;
		if(length < 1){
			$.messager.alert('警告','至少一条数据！');
		}
		else{
			$('#commenScrapTypePanel').dialog('open');
		}
}

function dialogCancel(dialog){
	$('#'+dialog).dialog('close');
}
//批量报废确定
function showBatchScrapEnter(){
	$.messager.progress();
	var scrapReason = $('#scrapReason').textbox('getValue');
	var selectRows =  $('#table').datagrid('getSelections');
	
	var spermInfoArry = [];
	$.each(selectRows,function(i,item){
		var spermInfoItem = {rowId:item.rowId,semenCode:item.semenCode};
		spermInfoArry.push(spermInfoItem);
	});
	if(spermInfoArry.length > 0){
		var spermInfoArryJson = JSON.stringify(spermInfoArry);
		jAjax.submit(batchScrapUrl,{scrapReason:scrapReason,spermInfoArry:spermInfoArryJson},function(data){
			$.messager.progress('close');	
			$('#commenScrapTypePanel').dialog('close');
			$('#table').datagrid('reload');
			$('#scrapReason').textbox('setValue','');
			$.messager.alert('提示','报废成功！');
		},null,null,true);
	}else{
		$.messager.alert('前台提示','没有选择数据！');
	}
}

//批量反报废
function batchTrunOverScrap(){
	var record = $('#table').datagrid('getSelections');
	var length = record.length;
	if(length < 1){
		$.messager.alert('警告','请选择一条或一条以上数据！');
	} else{
		$.messager.confirm('提示', '确定要反报废吗？', function(r){
			if (r){
				$.messager.progress();
				var selectRows =  $('#table').datagrid('getSelections');
				var spermInfoArry = [];
				$.each(selectRows,function(i,item){
					var spermInfoItem = {rowId:item.rowId,semenCode:item.semenCode};
					spermInfoArry.push(spermInfoItem);
				})
				if(spermInfoArry.length > 0){
					var spermInfoArryJson = JSON.stringify(spermInfoArry);
					jAjax.submit(batchTrunOverScrapUrl,{spermInfoArry:spermInfoArryJson},function(data){
						$.messager.progress('close');	
						$('#commenScrapTypePanel').dialog('close');
						$('#table').datagrid('reload');
						$.messager.alert('提示','反报废成功！');
					},null,null,true);
				}else{
					$.messager.alert('前台提示','没有选择数据！');
				}
			}
		});
		
	}
}

//点击精液明细批号
function semenBatchNoFormatter(value,rowData,rowIndex){
	//添加超链接 
	return "<a href='javascript:void(0);' class='editcls' onclick='semenBatchNoClick("+rowData.rowId+");'>"+value+"</a>";  
}
function semenBatchNoClick(rowId){
	//右滑显示方法
	$('#semenBatchNoClick').datagrid('load',basicUrl+'/production/searchSemenBatchNoDetailToList.do'+'?rowId='+rowId);
	leftSilpFun('spermDetailId');
}


//标签批量打印
function tagBatchPrint(){
	var record = $('#table').datagrid('getSelections');
	var length = record.length;
	if(length < 1){
		$.messager.alert('警告','至少一条数据！');
	}else{
		var validLine = [];
		$.each(record,function(i,row){
			if(row.inventoryQuantity <= 0){
				var index = $('#table').datagrid('getRowIndex',row);
				validLine.push(index);
			}
		})
		if(validLine.length > 0){
			var strValidLine = validLine.join(',');
			strValidLine=parseInt(strValidLine)+1;
			$.messager.alert('警告','第'+strValidLine+'行库存数量为0不能打印！');
		}else{
			var printList = [];
			$.each(record,function(index,data){
				printList.push({
					id:data.rowId,
					inventoryQuantity:data.inventoryQuantity,
					printNum:data.printNum
				});
			});
			var str= JSON.stringify(printList);
			jAjax.submit(editBatchPrintUrl, {'printList':str},function(data){
				$.messager.alert('提示','打印成功！');
				$('#table').datagrid('reload');
				var dbUrl = $('#finereport_url').val(),
				dbUserName = $('#finereport_username').val(),
				farmId = $('#farmId').val(),
				farmName = $('#farmName').val(),
				userName = $('#employName').val();
				var semenBatchNo = [];
				$.each(record,function(index,data){
					var detailRows = jAjax.submit('/production/searchSemenBatchNoDetailToList.do',{rowId:data.rowId});
					$.each(detailRows,function(detailRowIndex,detailRow){
						if(detailRow.status == 1){
							semenBatchNo.push(detailRow.rowId);
						}
					})
				});
				var semenBatchNoStr = semenBatchNo.join(',');
				window.open("http://www.xnplus.cn:4380/WebReport/ReportServer?reportlet=XNPLUS%2FsemenCard.cpt&dbUrl="+dbUrl+"&dbUserName="+dbUserName+"&farmId="+farmId+"&semenBatchNo="+semenBatchNoStr+"&farmName="+farmName+"&userName="+userName);
			}); 
		}
		
	}
}
//打印
function print(){
	var record = $('#semenBatchNoClick').datagrid('getSelections');
	var length = record.length;
	if(length < 1){
		$.messager.alert('警告','至少一条数据！');
	}else{
		var validLine = [];
		$.each(record,function(i,row){
			if(row.status !=1){
				var index = $('#semenBatchNoClick').datagrid('getRowIndex',row);
				validLine.push(index);
			}
		})
		if(validLine.length > 0){
			var strValidLine = validLine.join(',');
			strValidLine=parseInt(strValidLine)+1;
			$.messager.alert('警告','第'+strValidLine+'行不是已使用状态不能打印！');
		}else{
			var printList = [];
			$.each(record,function(index,data){
				printList.push({
					id:data.mianId,
					//inventoryQuantity:data.inventoryQuantity,
					//printNum:data.printNum
				});
			});
			var str= JSON.stringify(printList);
			jAjax.submit(editBatchPrintUrl, {'printList':str},function(data){
				$.messager.alert('提示','打印成功！');
				$('#table').datagrid('reload');
				var dbUrl = $('#finereport_url').val(),
				dbUserName = $('#finereport_username').val(),
				farmId = $('#farmId').val(),
				farmName = $('#farmName').val(),
				userName = $('#employName').val();
				var semenBatchNo = [];
				$.each(record,function(index,data){
					semenBatchNo.push(data.rowId);
				});
				var semenBatchNoStr = semenBatchNo.join(',');
				window.open("http://www.xnplus.cn:4380/WebReport/ReportServer?reportlet=XNPLUS%2FsemenCard.cpt&dbUrl="+dbUrl+"&dbUserName="+dbUserName+"&farmId="+farmId+"&semenBatchNo="+semenBatchNoStr+"&farmName="+farmName+"&userName="+userName);
			}); 
		}
		
	}
	
}

//报废
function scrap(){
	var record = $('#semenBatchNoClick').datagrid('getSelections');
	var length = record.length;
	if(length < 1){
		$.messager.alert('警告','至少一条数据！');
	}
	else{
		$('#ScrapTypePanel').dialog('open');
	}
}

//报废确定
function showScrapEnter(){
	var scrapReason = $('#scrapReason2').textbox('getValue');;
	var selectRows =  $('#semenBatchNoClick').datagrid('getSelections');
	var spermInfoArry = [];
	$.each(selectRows,function(i,item){
		var spermInfoItem = {rowId:item.rowId,semenCode:item.semenCode};
		spermInfoArry.push(spermInfoItem);
	})
	if(spermInfoArry.length > 0){
		var spermInfoArryJson = JSON.stringify(spermInfoArry);
		jAjax.submit(scrapUrl,{scrapReason:scrapReason,spermInfoArry:spermInfoArryJson},function(data){
			$.messager.progress('close');	
			$('#ScrapTypePanel').dialog('close');
			$('#semenBatchNoClick').datagrid('reload');
			$('#table').datagrid('reload');
			$('#scrapReason').textbox('setValue','');
			$.messager.alert('提示','报废成功！');
			},null,null,true);
		}
	else{
		$.messager.alert('前台提示','没有选择数据！');
	}
}

//反报废
function trunOverScrap(){
	var record = $('#semenBatchNoClick').datagrid('getSelections');
	var length = record.length;
	if(length < 1){
		$.messager.alert('警告','请选择一条或一条以上数据！');
	} else{
		$.messager.confirm('提示', '确定要反报废吗？', function(r){
			if (r){
				var selectRows =  $('#semenBatchNoClick').datagrid('getSelections');
				var spermInfoArry = [];
				$.each(selectRows,function(i,item){
					var spermInfoItem = {rowId:item.rowId,semenCode:item.semenCode};
					spermInfoArry.push(spermInfoItem);
				})
				if(spermInfoArry.length > 0){
					var spermInfoArryJson = JSON.stringify(spermInfoArry);
					jAjax.submit(trunOverScrapUrl,{spermInfoArry:spermInfoArryJson},function(data){
						$.messager.progress('close');	
						//$('#ScrapTypePanel').dialog('close');
						$('#semenBatchNoClick').datagrid('reload');
						$('#table').datagrid('reload');
						$('#scrapReason').textbox('setValue','');
						$.messager.alert('提示','反报废成功！');
						},null,null,true);
					
				}else{
					$.messager.alert('前台提示','没有选择数据！');
				}
			}
		});
		
	}
}

//查看
function showSpermDetail(index,row){
	$('#editWin').window({
		title:'查看'
	});
	$('#editWin').window('open');
	$('#btnSave').css('display','none');
	var mianId = row.rowId;
	var columnFields='rowId,semenBatchNo,semenColorName,semenOdourName,cohesionName,spermMotility,spermDensity,abnormationRate,statusName,statusReason,statusDate,earBrand,breedDateFirst,breedName';
	var columnTitles='rowId,精液批号,精液颜色,精液气味,凝聚度,精子活力,精子密度,畸形率,状态,状态原因,状态日期,与配母猪耳号,配种日期,品种';
	//columnWidths:'50,100,100,150,80,100,100,100,100,100,100,80,100,100',
	$('#spermDetailTable2').datagrid(
			j_grid_view({
				haveCheckBox:false,
				url:searchDetailListUrl+'?rowId='+mianId,
				columnFields:columnFields,
				columnTitles:columnTitles,
				hiddenFields:'rowId',
				fit:false,
				//pagination:false,
				height:'100%',
				width:'100%'
			},'spermDetailTable2')
		);
	
}
function onBtnView(){
	var record = $('#table').datagrid('getSelections');
	var length = record.length;
	if(length < 1){
		$.messager.alert('警告','请选择一条数据！');
	}else if(length > 1){
		$.messager.alert('警告','只能选择一条数据！');
	}else{
//		$('#editWin').window({
//			title:'查看'
//		});
//		$('#editWin').window('open');
//		var mianId = record[0].rowId;
		showSpermDetail(0,record[0]);
//		var columnFields='rowId,semenBatchNo,color,odour,cohesion,spermMotility,spermDensity,abnormationRate,statusName,statusReason,statusDate,sowPigId,breedDateFirst,breedName';
//		var columnTitles='rowId,精液批号,精液颜色,精液气味,凝聚度,精子活力,精子密度,畸形率,状态,状态原因,状态日期,与配母猪耳号,配种日期,品种';
//		$('#spermDetailTable2').datagrid(
//				j_grid_view({
//					haveCheckBox:false,
//					url:searchDetailListUrl+'?rowId='+mianId,
//					columnFields:columnFields,
//					columnTitles:columnTitles,
//					hiddenFields:'rowId',
//					fit:false,
//					pagination:false,
//					height:'100%',
//					width:'100%'
//				})
//			);
	}
}

// 精液入库
function spermInput(){
	openNewTab('SemenInquiry','精液入库','/jsp/production/SemenInquiry.jsp');
}
//精液销售
function spermSell(){
	var selectRows = $('#table').datagrid('getSelections');
	if(selectRows.length<1){
		var mainIds = '';
		openNewTab('SaleSemen','精液销售','/jsp/production/SaleSemen.jsp',mainIds);
	}else{
 		var mainIds = '';
 		$.each(selectRows,function(i,data){
			if(i == selectRows.length - 1){
 				mainIds += data.rowId;
 			}else{
 				mainIds += data.rowId + ',';
 			}
 		});
		openNewTab('SaleSemen','精液销售','/jsp/production/SaleSemen.jsp',mainIds);
		
//		var hasSelectRows = $('#table').datagrid('getData').rows;

// 		$('#table').datagrid("load", basicUrl + searchSpermToSalePageUrl+"?mainIds="+mainIds);
		
	}
//	$('#table').datagrid('reload');
}
//打开一个新的标签页
//eventName:英文名称
//chinseEventName:中文名称
//eventUrl:地址
function openNewTab(eventName,chinseEventName,eventUrl,mainIds){
	var tabNum = parseInt((window.parent.document.getElementById("header").clientWidth - 175 - 74 -20)/130);
	var liLength = $(".tabList", parent.document).length;
	//判断打开的tab是否已经超过最大值
	if(liLength >= 15){
		$.messager.alert('警告', '最多只能打开15个页面，请关闭无用页面重新点击！');
	}else{
		//获取英文事件名称
		var eventName = eventName;
		//获取中文事件名称
		var chinseEventName = chinseEventName;
		var eventUrl = eventUrl;

		//判断是否已经存在事件tab flag=true不存在 false存在
		var flag = true;
		$.each($(".tabTetx", parent.document),function(index,data){
			if(data.innerText == chinseEventName){
				flag = false;
			}
		});
		//如果已经存在则显示tab内容且选中，否则添加事件tab并选中
		if(flag){
			if(liLength >= tabNum){
				$("#tabMore", parent.document).css('display','block');
				turnLeftFun();
			}
			//动态生成html
			var addHtml = '<li class="listSelected" data-eventname="'+eventName+'">'+
								'<div class="tabTetx fl" onClick="tabClickFun(this)">'+chinseEventName+'</div>'+
								'<div class="close fl" onClick="tabCloseFun(this)"></div>'+
						  '</li>';
			var randomnumber=Math.floor(Math.random()*100000);
			$('#main_right', parent.document).append('<div id="tabContent_'+eventName+'" style="height:100%"><iframe scrolling="no" frameborder="0" id="iframe_'+eventName+'" src="'+basicUrl+eventUrl+'?randomnumber='+randomnumber+'&fontSize='+parent.baseFontSize+'" style="width:100%;height:100%;"></iframe></div>');
			$('#tabContent_'+eventName+ ' iframe', parent.document).on('load',function(){
				$('.tabList', parent.document).find('ul').append(addHtml);
				changeTabContent(eventName);
				if(mainIds != ''){
					if(eventName == 'SaleSemen'){
						var saleSemenContentWindow = parent.document.getElementById("iframe_"+eventName).contentWindow;
						saleSemenContentWindow.loadListTableFromSperm(mainIds);
					}
				}
			});
		}else{
			if(liLength >= tabNum){
				var isHide = false;
				$.each($('.tabList', parent.document).find('li'),function(index,data){
					if(data.dataset.eventname == eventName && data.style.display == 'none'){
						isHide = true;
					}
				});
				if(isHide){
					$('#tabMore', parent.document).css('display','block');
					showHideTab(para);
				}
			}
			changeTabContent(eventName);
			
			if(mainIds != ''){
				if(eventName == 'SaleSemen'){
					var saleSemenContentWindow = parent.document.getElementById("iframe_"+eventName).contentWindow;
					saleSemenContentWindow.loadListTableFromSperm(mainIds);
				}
			}
		}
	}
}
function changeTabContent(eventName){
	$.each($('.tabList', parent.document).find('li'),function(index,data){
		if(data.dataset.eventname == eventName){
			if(data.className == ''){
				data.className = 'listSelected';
				$('#tabContent_'+data.dataset.eventname, parent.document).css('display','block');
			}
		}else{
			if(data.className == 'listSelected'){
				data.className = '';
				$('#tabContent_'+data.dataset.eventname, parent.document).css('display','none');
			}
		}
	});
	if($('.hompageBorder', parent.document)[0].className == 'hompageBorder fl listSelected'){
		$('.hompageBorder', parent.document)[0].className = 'hompageBorder fl';
		$('#tabContent_homePage', parent.document).css('display','none');
	}
}

/**
 * 高级查询搜索
 */
function onBtnSearch(para,url){
	var earBrand =  $('#earBrand').textbox('getValue');
	var breedName = $('#breedName').combogrid('getValue');
	var semenBatchNo =  $('#semenBatchNo').textbox('getValue');
	var semenDate = $('#semenDate').datebox('getValue');
	var validDate = $('#validDate').datebox('getValue');
	
	var winId = $(para).parent().parent().attr('id');
    rightSlipFun(winId,390);
    var requestType = 0;
    var ascending = 0;
	if($('#requestType').is(':checked')){
		requestType = 1;
	}
	if($('#ascending').is(':checked')){
		ascending = 1;
	}
	
    $('#table').datagrid("load", basicUrl + searchMainListUrl+"?requestType="+requestType+"&ascending="+ascending
    		+'&earBrand='+earBrand+'&breedName='+breedName+'&semenBatchNo='+semenBatchNo+'&semenDate='+semenDate+'&validDate='+validDate);
}
function tableReload(){
	$('#table').datagrid("reload");
}