var title = '组织结构';
var model = 'cdSetting';
var editIndex = undefined;
var prUrl = '/OrganizationalStructure/';
var searchCompanyTreeUrl=prUrl+'searchCompanyTreeExceptDept.do'; 
var navTop = [];
var displayType = '';
var dateTypeFlag = '3';
$(function() {
	var navList = $('#changeNav').find('li');
	$.each(navList,function(i,nav){
		nav.onclick = function(){
			if(nav.className != 'selected'){
				$("#production_change_tab").animate({
					scrollTop : navTop[i]
				},500);
				setTimeout(function(){
					$(".verticalNav .selected").find('span').removeClass('solid-arrow');
					$(".verticalNav .selected").find('span').addClass('point');
					$(".verticalNav .selected").removeClass("selected");
					$(nav).addClass("selected");
					$(nav).find('span').removeClass('point')
					$(nav).find('span').addClass('solid-arrow')
				},500);
				
			}
		}
	});
	xnMonthSelect({
		id:'searchDate'
	});
	$("#production_change_tab").scroll(function() {
		var scrTop = $("#production_change_tab").scrollTop();
		var selected = '';
		$.each(navList,function(i,nav){
			var liTop = navTop[i];
			if(liTop <= scrTop){
				selected = $(this);
			}
		});
		$(".verticalNav .selected").find('span').removeClass('solid-arrow');
		$(".verticalNav .selected").find('span').addClass('point');
		$(".verticalNav .selected").removeClass("selected");
		$(selected).addClass("selected");
		$(selected).find('span').removeClass('point')
		$(selected).find('span').addClass('solid-arrow')
	});
	//初始化公司树
	initTreeData();
	//初始存栏
	initHand();
	var compareTypes = document.getElementsByName('compareType');
	$.each(compareTypes,function(i,compareType){
		$(compareType).bind('change',function(e){
			dateTypeFlag = $(this).val();
			var productionChangeData = [];
			var divList = $('.pd-c-table-thead').find('#itemName').nextAll();
			$.each(divList,function(j,div){
				var farmId = $(div).attr('changeFarmId');
				if(farmId){
					var farmName = $('div[changeFarmId="'+farmId+'"]').find('strong').html();
					if(farmId == 'group'){
						farmId = null;
					}
					if(divList.length == 1){
						productionChangeAddData(farmName,farmId,dateTypeFlag,true,true);
					}else{
						if(j == 0){
							productionChangeAddData(farmName,farmId,dateTypeFlag,true,true);
						}else{
							productionChangeAddData(farmName,farmId,dateTypeFlag,false,false);
						}
					}
				}
			});
		});
	});
	
})

//初始化组织结构树
function initTreeData() {
	$('#treeTable').treegrid(
			j_treeGrid({
				url :searchCompanyTreeUrl,
			    columns:[[
			        {field:'text',title:'名称',width:600}
			    ]],
			    toolbar : '#treeTableToolbar',
				//onContextMenu : onContextMenu,
				onCheckNode:onCheckNodeFun,
				checkbox:true,
				cascadeCheck:false
			},function(){
				$('#treeTable').treegrid('collapseAll');
				var node = $('#treeTable').treegrid('getSelected');
				if(node){
					var parentId = [node.id];
					getParentIds(node,parentId);
					$.each(parentId,function(index,data){
						$('#treeTable').treegrid('expand',data);
					})
				}
				var farmId = $('#farmId').val();
				$('#treeTable').treegrid('checkNode',farmId);
			})
	);
}	
function onCheckNodeFun(row,checked){
	if(row.id == $('#farmId').val()){
		$('#treeTable').treegrid('checkNode',row.id);
		return;
	}
	if(checked){
		addCompareCompany(row);
	}else{
		deleteCompareCompany(row);
	}
//	if (row.type == '公司' && row.children.length < 1) {	
//		if(checked){
//			addCompareCompany(row);
//		}else{
//			deleteCompareCompany(row); 
//		} 
//	}else{
//		if(checked){
//			$.messager.alert('提示','请选择子猪场！');
//			$('#treeTable').treegrid('uncheckNode',row.id);
//		}
//	}
}
//获取元素父节点Id
function getParentIds(node,parentIds){
	if(node.parentId != null){
		parentIds.push(node.parentId);
		var parentNode = $('#treeTable').treegrid('find',node.parentId);
		getParentIds(parentNode,parentIds);
	}
}
//折叠
function collapseAll(){
	var node = $('#treeTable').treegrid('getSelected');
	if (node) {
		$('#treeTable').treegrid('collapseAll', node.id);
	} else {
		$('#treeTable').treegrid('collapseAll');
	}
}
//展开
function expandAll(){
	var node = $('#treeTable').treegrid('getSelected');
	if (node) {
		$('#treeTable').treegrid('expandAll', node.id);
	} else {
		$('#treeTable').treegrid('expandAll');
	}
}
//刷新
function refresh(){
	$('#treeTable').treegrid('reload');
}
//右键菜单
function onContextMenu(e, row) {
	e.preventDefault();
	$(this).treegrid('unselectAll');
	$(this).treegrid('select', row.id);
	if (row.type == '公司' && row.children.length < 1) {		
		$('#rightMenu').menu('show', {
			left : e.pageX,
			top : e.pageY
		});
	}
}
function initHand(){
	$.messager.progress();
	jAjax.submit("/production/searchLivestockByProduceToList.do",null,function(response){
		$.messager.progress('close');
		$('.pd-table-thead').append('<div compareFarmId="group"><strong>全部</strong></div>');
		var dataHtml = '<div compareFarmId="group">'
					+'<table class="pd-table">'
					+'<tbody>'
					+'<tr><td>'+response[0].SCGZpigQty+'</td></tr>'
					+'<tr><td>'+response[0].HBGZpigQty+'</td></tr>'
					+'<tr><td>'+response[0].totalGZPigQty+'</td></tr>'
					+'<tr><td>'+response[0].CCMZpigQty+'</td></tr>'
					+'<tr><td>'+response[0].HBMZpigQty+'</td></tr>'
					+'<tr><td>'+response[0].totalMZPigQty+'</td></tr>'
					+'<tr><td>'+response[0].BRZZpigQty+'</td></tr>'
					+'<tr><td>'+response[0].DNZZpigQty+'</td></tr>'
					+'<tr><td>'+response[0].BYZpigQty+'</td></tr>'
					+'<tr><td>'+response[0].YFZpigQty+'</td></tr>'
					+'<tr><td>'+response[0].LZGZpigQty+'</td></tr>'
					+'<tr><td>'+response[0].LZMXpigQty+'</td></tr>'
					+'<tr><td>'+response[0].TTZpigQty+'</td></tr>'
					+'<tr><td>'+response[0].totalRZPigQty+'</td></tr>'						
					+'</tbody>'
					+'</table>'
					+'</div>';
		$('.pd-table-tbody').append(dataHtml);
	},null,null,true);
}
function addCompareCompany(node){
	//var node = $('#treeTable').treegrid('getSelected');
	if(displayType == 'houseHand'){
		if($('.pd-table-thead').find('div[compareFarmId="'+node.farmId+'"]').length > 0){
			$('div[compareFarmId="'+node.farmId+'"]').css('display','block');
			checkIsDisplay(node.farmId,false);
		}else{
			$.messager.progress();
			jAjax.submit("/production/searchLivestockByProduceToList.do",{
				farmId:node.farmId
			},function(response){
				$.messager.progress('close');
				$('.pd-table-thead').append('<div compareFarmId="'+node.farmId+'"><strong>'+node.text.replace('有限','').replace('公司','')+'</strong></div>');
				var dataHtml = '<div compareFarmId="'+node.farmId+'">'
							+'<table class="pd-table">'
							+'<tbody>'
							+'<tr><td>'+response[0].SCGZpigQty+'</td></tr>'
							+'<tr><td>'+response[0].HBGZpigQty+'</td></tr>'
							+'<tr><td>'+response[0].totalGZPigQty+'</td></tr>'
							+'<tr><td>'+response[0].CCMZpigQty+'</td></tr>'
							+'<tr><td>'+response[0].HBMZpigQty+'</td></tr>'
							+'<tr><td>'+response[0].totalMZPigQty+'</td></tr>'
							+'<tr><td>'+response[0].BRZZpigQty+'</td></tr>'
							+'<tr><td>'+response[0].DNZZpigQty+'</td></tr>'
							+'<tr><td>'+response[0].BYZpigQty+'</td></tr>'
							+'<tr><td>'+response[0].YFZpigQty+'</td></tr>'
							+'<tr><td>'+response[0].LZGZpigQty+'</td></tr>'
							+'<tr><td>'+response[0].LZMXpigQty+'</td></tr>'
							+'<tr><td>'+response[0].TTZpigQty+'</td></tr>'
							+'<tr><td>'+response[0].totalRZPigQty+'</td></tr>'						
							+'</tbody>'
							+'</table>'
							+'</div>';
				$('.pd-table-tbody').append(dataHtml);
				checkIsDisplay(node.farmId,true);
			},null,null,true);
		}
	}else if(displayType == 'productionChange'){
		if($('.pd-c-table-thead').find('div[changeFarmId="'+node.farmId+'"]').length > 0){
			$('div[changeFarmId="'+node.farmId+'"]').css('display','block');
			checkIsDisplay(node.farmId,false);
		}else{
			var farmName = node.text.replace('有限','').replace('公司',''),farmId = node.farmId;
			productionChangeAddData(farmName,farmId,dateTypeFlag);
		}
	}else if(displayType == 'performanceChange'){
		if($('.pd-p-table-thead').find('div[perFormanceFarmId="'+node.farmId+'"]').length > 0){
			$('div[perFormanceFarmId="'+node.farmId+'"]').css('display','block');
			checkIsDisplay(node.farmId,false);
		}else{
			var farmName = node.text.replace('有限','').replace('公司',''),farmId = node.farmId;
			performanceChangeAddData(farmName,farmId,$('#searchDate').datebox('getValue'));
		}
	}
}
function checkIsDisplay(farmId,deletedFlag){
	var overFalg = false;
	var tableHeadDiv = null;
	if(displayType == 'houseHand'){
		tableHeadDiv = $('.pd-table-thead div strong');
	}else if(displayType == 'productionChange'){
		tableHeadDiv = $('.pd-c-table-thead div strong');
	}else if(displayType == 'performanceChange'){
		tableHeadDiv = $('.pd-p-table-thead div strong');
	}
	$.each(tableHeadDiv,function(i,item){
		if($(item).height() > 19){
			overFalg = true;
			return false;
		}
	});
	if(overFalg){
		if(deletedFlag){
			if(displayType == 'houseHand'){
				$('div[compareFarmId="'+farmId+'"]').remove();
			}else if(displayType == 'productionChange'){
				$('div[changeFarmId="'+farmId+'"]').remove();
			}else if(displayType == 'performanceChange'){
				$('div[perFormanceFarmId="'+farmId+'"]').remove();
			}
		}else{
			if(displayType == 'houseHand'){
				$('div[compareFarmId="'+farmId+'"]').css('display','none');
			}else if(displayType == 'productionChange'){
				$('div[changeFarmId="'+farmId+'"]').css('display','none');
			}else if(displayType == 'performanceChange'){
				$('div[perFormanceFarmId="'+farmId+'"]').css('display','none');
			}
		}
		$.messager.alert('提示','请删除多余猪场再添加！');
		$('#treeTable').treegrid('uncheckNode',farmId);
	}
}
function deleteCompareCompany(node){
	//var node = $('#treeTable').treegrid('getSelected');
	if(displayType == 'houseHand'){
		if($('div[compareFarmId="'+node.farmId+'"]').length == 0){
			return;
		}
		$('div[compareFarmId="'+node.farmId+'"]').css('display','none');
	}else if(displayType == 'productionChange'){
		if($('div[changeFarmId="'+node.farmId+'"]').length == 0){
			return;
		}
		$('div[changeFarmId="'+node.farmId+'"]').css('display','none');
	}else if(displayType == 'performanceChange'){
		if($('div[perFormanceFarmId="'+node.farmId+'"]').length == 0){
			return;
		}
		$('div[perFormanceFarmId="'+node.farmId+'"]').css('display','none');
	}
}
function tabsOnselect(title,index){
	if(title == '存栏'){
		$('#changeNav').css('display','none');
		displayType = 'houseHand';
		$('#productionDynamicLayout').layout('collapse','east');
		if($('#treeTable[class="datagrid-f"]').length>0){
			var checkedNodes = $('#treeTable').treegrid('getCheckedNodes');
			var farmList = $('.pd-table-thead').find('div[compareFarmId="group"]').nextAll();
			var validFarmList = [];
			$.each(farmList,function(i,farmDiv){
				if($(farmDiv).css('display') != 'none'){
					validFarmList.push($(farmDiv).attr('compareFarmId'));
				}
			});
			$.each(checkedNodes,function(i,node){
				$('#treeTable').treegrid('uncheckNode',node.id);
			});
			$.each(validFarmList,function(i,farmId){
				$('#treeTable').treegrid('checkNode',farmId);
			});
		}
	}else if( title == '生产变动'){
		displayType = 'productionChange';
		$('#productionDynamicLayout').layout('expand','east');
		$('#changeNav').css('display','block');
		if(navTop.length == 0){
			var navList = $('#changeNav').find('li');
			$.each(navList,function(i,nav){
				navTop.push($("#"+nav.dataset.href).offset().top-$("#changeFiexd").offset().top-$("#changeFiexd").height());
			})
		}
		var changeFlag = $('#production_change_tab')[0].dataset.changeflag;
		if(changeFlag == 'false'){
			productionChangeAddData('集团总数',null,'3');
		}
		$('#production_change_tab')[0].dataset.changeflag = true;
		if($('#treeTable[class="datagrid-f"]').length>0){
			var checkedNodes = $('#treeTable').treegrid('getCheckedNodes');
			var farmList = $('.pd-c-table-thead').find('div[changeFarmId="group"]').nextAll();
			var validFarmList = [];
			$.each(farmList,function(i,farmDiv){
				if($(farmDiv).css('display') != 'none'){
					validFarmList.push($(farmDiv).attr('changeFarmId'));
				}
			});
			$.each(checkedNodes,function(i,node){
				$('#treeTable').treegrid('uncheckNode',node.id);
			});
			$.each(validFarmList,function(i,farmId){
				$('#treeTable').treegrid('checkNode',farmId);
			});
		}
	}else if(title == '绩效对比'){
		displayType = 'performanceChange';
		$('#productionDynamicLayout').layout('collapse','east');
		$('#changeNav').css('display','none');
		var changeFlag = $('#performance_change_tab')[0].dataset.changeflag;
		if(changeFlag == 'false'){
			var date = $('#searchDate').datebox('getValue');
			addPerformanceType();
			var farmName = $('#farmName').val().replace('有限','').replace('公司','');
			performanceChangeAddData(farmName,$('#farmId').val(),date);
		}
		$('#performance_change_tab')[0].dataset.changeflag = true;
		if($('#treeTable[class="datagrid-f"]').length>0){
			var checkedNodes = $('#treeTable').treegrid('getCheckedNodes');
			var farmList = $('.pd-p-table-thead').find('div[perFormanceFarmId="'+$('#farmId').val()+'"]').nextAll();
			var validFarmList = [];
			$.each(farmList,function(i,farmDiv){
				if($(farmDiv).css('display') != 'none'){
					validFarmList.push($(farmDiv).attr('perFormanceFarmId'));
				}
			});
			$.each(checkedNodes,function(i,node){
				$('#treeTable').treegrid('uncheckNode',node.id);
			});
			$.each(validFarmList,function(i,farmId){
				$('#treeTable').treegrid('checkNode',farmId);
			});
		}
	}
}
function addPerformanceType(){
	$.ajax({  
        url : basicUrl+'/param/searchByTypeCode.do?typeCode=ASSESS_CONTENT',
        async : false, 
        type : "POST",  
        dataType : "json",  
        success : function(response) { 
        	$.each(response,function(index,row){
        		$('#performanceChangeTable').append('<tr data-codevalue="'+row.codeValue+'"><td>'+row.codeName+'</td></tr>');
        	});
        }  
    });
}
function productionChangeAddData(farmName,farmId,dateTye,progressS,progressE){
	progressS = progressS == null? true : progressS;
	progressE = progressE == null? true : progressE;
	if(progressS){
		$.messager.progress();
	}
	jAjax.submit("/production/searchProduceChangeToList.do",{
		farmId:farmId,
		dateType:dateTye
	},function(response){
		if(farmId == null){
			farmId = 'group';
		}
		productionChangeAddHtml(response,farmId,farmName);
		if(progressE){
			$.messager.progress('close');
		}
		checkIsDisplay(farmId,true);
	},null,null,true);
}
function tarDivAddHtml(id,farmId,dataHtml){
	var tarDiv= $('#'+id).find('.fieldContent').find('.pd-c-table-tbody').find('div[changeFarmId="'+farmId+'"]');
	if(tarDiv.length > 0){
		tarDiv.replaceWith(dataHtml);
		if($('.pd-c-table-thead').find('div[changeFarmId="'+farmId+'"]').css('display') == 'none'){
			$('#'+id).find('.fieldContent').find('.pd-c-table-tbody').find('div[changeFarmId="'+farmId+'"]').css('display','none');
		}
	}else{
		$('#'+id).find('.fieldContent').find('.pd-c-table-tbody').append(dataHtml);
	}
}
function productionChangeAddHtml(response,farmId,farmName){
	if($('.pd-c-table-thead').find('div[changeFarmId="'+farmId+'"]').length == 0){
		$('.pd-c-table-thead').append('<div changeFarmId="'+farmId+'" class="line-30"><strong>'+farmName+'</strong><br>累计 | 今日</div>');
	}
	$.each(response,function(i,row){
		if(row.changeType == 'breed'){
			var dataHtml = '<div changeFarmId="'+farmId+'">'
								+'<table class="pd-table">'
									+'<tbody>';
									if(farmId == 'group'){
										 dataHtml +='<tr><td class="border-right">'+row.DNbreed+'</td><td>'+row.todayDNbreed+'</td></tr>'
												   +'<tr><td class="border-right">'+row.FQbreed+'</td><td>'+row.todayFQbreed+'</td></tr>'
												   +'<tr><td class="border-right">'+row.HBbreed+'</td><td>'+row.todayHBbreed+'</td></tr>'
												   +'<tr><td class="border-right">'+row.KHbreed+'</td><td>'+row.todayKHbreed+'</td></tr>'
												   +'<tr><td class="border-right">'+row.LCbreed+'</td><td>'+row.todayLCbreed+'</td></tr>'
									}else{
										 dataHtml +='<tr><td class="border-right"><a class="fineReport-a" href="#" onClick=openReport("breed",11,'+farmId+',"'+farmName+'",'+dateTypeFlag+')>'+row.DNbreed+'</a></td>'
										   		   +'<td><a class="fineReport-a" href="#" onClick=openReport("breed",11,'+farmId+',"'+farmName+'")>'+row.todayDNbreed+'</a></td></tr>'
												   +'<tr><td class="border-right"><a class="fineReport-a" href="#" onClick=openReport("breed",6,'+farmId+',"'+farmName+'",'+dateTypeFlag+')>'+row.FQbreed+'</a></td>'
												   +'<td><a class="fineReport-a" href="#" onClick=openReport("breed",6,'+farmId+',"'+farmName+'")>'+row.todayFQbreed+'</a></td></tr>'
												   +'<tr><td class="border-right"><a class="fineReport-a" href="#" onClick=openReport("breed",4,'+farmId+',"'+farmName+'",'+dateTypeFlag+')>'+row.HBbreed+'</a></td>'
												   +'<td><a class="fineReport-a" href="#" onClick=openReport("breed",4,'+farmId+',"'+farmName+'")>'+row.todayHBbreed+'</a></td></tr>'
												   +'<tr><td class="border-right"><a class="fineReport-a" href="#" onClick=openReport("breed",8,'+farmId+',"'+farmName+'",'+dateTypeFlag+')>'+row.KHbreed+'</a></td>'
												   +'<td><a class="fineReport-a" href="#" onClick=openReport("breed",8,'+farmId+',"'+farmName+'")>'+row.todayKHbreed+'</a></td></tr>'
												   +'<tr><td class="border-right"><a class="fineReport-a" href="#" onClick=openReport("breed",7,'+farmId+',"'+farmName+'",'+dateTypeFlag+')>'+row.LCbreed+'</a></td>'
												   +'<td><a class="fineReport-a" href="#" onClick=openReport("breed",7,'+farmId+',"'+farmName+'")>'+row.todayLCbreed+'</a></td></tr>'
									}
									dataHtml +='</tbody>'
								+'</table>'
							+'</div>';
			tarDivAddHtml('PZBD',farmId,dataHtml);
		}
		if(row.changeType == 'pregnancy'){
			var dataHtml = '<div changeFarmId="'+farmId+'">'
								+'<table class="pd-table">'
									+'<tbody>'
									if(farmId == 'group'){
										dataHtml +='<tr><td class="border-right">'+row.FQpregnancy+'</td><td>'+row.todayFQpregnancy+'</td></tr>'
												  +'<tr><td class="border-right">'+row.KHpregnancy+'</td><td>'+row.todayKHpregnancy+'</td></tr>'
												  +'<tr><td class="border-right">'+row.LCpregnancy+'</td><td>'+row.todayLCpregnancy+'</td></tr>'
												  +'<tr><td class="border-right">'+row.YXpregnancy+'</td><td>'+row.todayYXpregnancy+'</td></tr>'
									}else{
										dataHtml +='<tr><td class="border-right"><a class="fineReport-a" href="#" onClick=openReport("pregnancyCheck",4,'+farmId+',"'+farmName+'",'+dateTypeFlag+')>'+row.FQpregnancy+'</a></td>'
												  +'<td><a class="fineReport-a" href="#" onClick=openReport("pregnancyCheck",4,'+farmId+',"'+farmName+'")>'+row.todayFQpregnancy+'</a></td></tr>'
												  +'<tr><td class="border-right"><a class="fineReport-a" href="#" onClick=openReport("pregnancyCheck",6,'+farmId+',"'+farmName+'",'+dateTypeFlag+')>'+row.KHpregnancy+'</a></td>'
												  +'<td><a class="fineReport-a" href="#" onClick=openReport("pregnancyCheck",6,'+farmId+',"'+farmName+'")>'+row.todayKHpregnancy+'</a></td></tr>'
												  +'<tr><td class="border-right"><a class="fineReport-a" href="#" onClick=openReport("pregnancyCheck",5,'+farmId+',"'+farmName+'",'+dateTypeFlag+')>'+row.LCpregnancy+'</a></td>'
												  +'<td><a class="fineReport-a" href="#" onClick=openReport("pregnancyCheck",5,'+farmId+',"'+farmName+'")>'+row.todayLCpregnancy+'</a></td></tr>'
												  +'<tr><td class="border-right"><a class="fineReport-a" href="#" onClick=openReport("pregnancyCheck",2,'+farmId+',"'+farmName+'",'+dateTypeFlag+')>'+row.YXpregnancy+'</a></td>'
												  +'<td><a class="fineReport-a" href="#" onClick=openReport("pregnancyCheck",2,'+farmId+',"'+farmName+'")>'+row.todayYXpregnancy+'</a></td></tr>'
									}
								dataHtml +='</tbody>'
								+'</table>'
							+'</div>';
			tarDivAddHtml('RJBD',farmId,dataHtml);
		}
		if(row.changeType == 'delivery'){
			var dataHtml = '<div changeFarmId="'+farmId+'">'
								+'<table class="pd-table">'
									+'<tbody>'
									if(farmId == 'group'){
										dataHtml +='<tr><td class="border-right">'+row.healthyNum+'</td><td>'+row.todayHealthyNum+'</td></tr>'
											      +'<tr><td class="border-right">'+row.weakNum+'</td><td>'+row.todayWeakNum+'</td></tr>'
											      +'<tr><td class="border-right">'+row.stillbirthNum+'</td><td>'+row.todayStillbirthNum+'</td></tr>'
											      +'<tr><td class="border-right">'+row.mutantNum+'</td><td>'+row.todayMutantNum+'</td></tr>'
											      +'<tr><td class="border-right">'+row.mummyNum+'</td><td>'+row.todayMummyNum+'</td></tr>'
											      +'<tr><td class="border-right">'+row.blackNum+'</td><td>'+row.todayBlackNum+'</td></tr>'
											      +'<tr><td class="border-right">'+row.aliveLitterY+'</td><td>'+row.todayAliveLitterY+'</td></tr>'
											      +'<tr><td class="border-right">'+row.aliveLitterX+'</td><td>'+row.todayAliveLitterX+'</td></tr>'
											      +'<tr><td class="border-right">'+row.totalQty+'</td><td>'+row.todayTotalQty+'</td></tr>'
											      +'<tr><td class="border-right">'+(row.avgWeight).toFixed(2)+'</td><td>'+(row.todayAvgWeight).toFixed(2)+'</td></tr>'
											      +'<tr><td class="border-right">'+(row.avgPigQty).toFixed(2)+'</td><td>'+(row.todayAvgPigQty).toFixed(2)+'</td></tr>'
									}else{
										dataHtml +='<tr><td class="border-right"><a class="fineReport-a" href="#" onClick=openReport("delivery",null,'+farmId+',"'+farmName+'",'+dateTypeFlag+')>'+row.healthyNum+'</a></td>'
												  +'<td><a class="fineReport-a" href="#" onClick=openReport("delivery",null,'+farmId+',"'+farmName+'")>'+row.todayHealthyNum+'</a></td></tr>'
										          +'<tr><td class="border-right"><a class="fineReport-a" href="#" onClick=openReport("delivery",null,'+farmId+',"'+farmName+'",'+dateTypeFlag+')>'+row.weakNum+'</a></td>'
										          +'<td><a class="fineReport-a" href="#" onClick=openReport("delivery",null,'+farmId+',"'+farmName+'")>'+row.todayWeakNum+'</a></td></tr>'
										          +'<tr><td class="border-right"><a class="fineReport-a" href="#" onClick=openReport("delivery",null,'+farmId+',"'+farmName+'",'+dateTypeFlag+')>'+row.stillbirthNum+'</a></a></td>'
										          +'<td><a class="fineReport-a" href="#" onClick=openReport("delivery",null,'+farmId+',"'+farmName+'")>'+row.todayStillbirthNum+'</a></td></tr>'
										          +'<tr><td class="border-right"><a class="fineReport-a" href="#" onClick=openReport("delivery",null,'+farmId+',"'+farmName+'",'+dateTypeFlag+')>'+row.mutantNum+'</a></td>'
										          +'<td><a class="fineReport-a" href="#" onClick=openReport("delivery",null,'+farmId+',"'+farmName+'")>'+row.todayMutantNum+'</a></td></tr>'
										          +'<tr><td class="border-right"><a class="fineReport-a" href="#" onClick=openReport("delivery",null,'+farmId+',"'+farmName+'",'+dateTypeFlag+')>'+row.mummyNum+'</a></td>'
										          +'<td><a class="fineReport-a" href="#" onClick=openReport("delivery",null,'+farmId+',"'+farmName+'")>'+row.todayMummyNum+'</a></td></tr>'
										          +'<tr><td class="border-right"><a class="fineReport-a" href="#" onClick=openReport("delivery",null,'+farmId+',"'+farmName+'",'+dateTypeFlag+')>'+row.blackNum+'</a></td>'
										          +'<td><a class="fineReport-a" href="#" onClick=openReport("delivery",null,'+farmId+',"'+farmName+'")>'+row.todayBlackNum+'</a></td></tr>'
										          +'<tr><td class="border-right"><a class="fineReport-a" href="#" onClick=openReport("delivery",null,'+farmId+',"'+farmName+'",'+dateTypeFlag+')>'+row.aliveLitterY+'</a></td>'
										          +'<td><a class="fineReport-a" href="#" onClick=openReport("delivery",null,'+farmId+',"'+farmName+'")>'+row.todayAliveLitterY+'</a></td></tr>'
										          +'<tr><td class="border-right"><a class="fineReport-a" href="#" onClick=openReport("delivery",null,'+farmId+',"'+farmName+'",'+dateTypeFlag+')>'+row.aliveLitterX+'</a></td>'
										          +'<td><a class="fineReport-a" href="#" onClick=openReport("delivery",null,'+farmId+',"'+farmName+'")>'+row.todayAliveLitterX+'</a></td></tr>'
										          +'<tr><td class="border-right"><a class="fineReport-a" href="#" onClick=openReport("delivery",null,'+farmId+',"'+farmName+'",'+dateTypeFlag+')>'+row.totalQty+'</a></td>'
										          +'<td><a class="fineReport-a" href="#" onClick=openReport("delivery",null,'+farmId+',"'+farmName+'")>'+row.todayTotalQty+'</a></td></tr>'
										          +'<tr><td class="border-right"><a class="fineReport-a" href="#" onClick=openReport("delivery",null,'+farmId+',"'+farmName+'",'+dateTypeFlag+')>'+(row.avgWeight).toFixed(2)+'</a></td>'
										          +'<td><a class="fineReport-a" href="#" onClick=openReport("delivery",null,'+farmId+',"'+farmName+'")>'+(row.todayAvgWeight).toFixed(2)+'</a></td></tr>'
										          +'<tr><td class="border-right"><a class="fineReport-a" href="#" onClick=openReport("delivery",null,'+farmId+',"'+farmName+'",'+dateTypeFlag+')>'+(row.avgPigQty).toFixed(2)+'</a></td>'
										          +'<td><a class="fineReport-a" href="#" onClick=openReport("delivery",null,'+farmId+',"'+farmName+'")>'+(row.todayAvgPigQty).toFixed(2)+'</a></td></tr>'
									}
								dataHtml +='</tbody>'
								+'</table>'
							+'</div>';
			tarDivAddHtml('FMBD',farmId,dataHtml);
		}
		if(row.changeType == 'wean'){
			var dataHtml = '<div changeFarmId="'+farmId+'">'
								+'<table class="pd-table">'
									+'<tbody>'
									if(farmId == 'group'){
										dataHtml +='<tr><td class="border-right">'+row.weanNum+'</td><td>'+row.todayWeanNum+'</td></tr>'
												  +'<tr><td class="border-right">'+(row.weanWeight).toFixed(2)+'</td><td>'+(row.todayWeanWeight).toFixed(2)+'</td></tr>'
												  +'<tr><td class="border-right">'+(row.avgPigQty).toFixed(2)+'</td><td>'+(row.todayAvgPigQty).toFixed(2)+'</td></tr>'
												  +'<tr><td class="border-right">'+(row.avgWeight).toFixed(2)+'</td><td>'+(row.todayWeanWeight).toFixed(2)+'</td></tr>'
									}else{
										dataHtml +='<tr><td class="border-right"><a class="fineReport-a" href="#" onClick=openReport("wean",null,'+farmId+',"'+farmName+'",'+dateTypeFlag+')>'+row.weanNum+'</a></td>'
												  +'<td><a class="fineReport-a" href="#" onClick=openReport("wean",null,'+farmId+',"'+farmName+'">'+row.todayWeanNum+'</a></td></tr>'
												  +'<tr><td class="border-right"><a class="fineReport-a" href="#" onClick=openReport("wean",null,'+farmId+',"'+farmName+'",'+dateTypeFlag+')>'+(row.weanWeight).toFixed(2)+'</a></td>'
												  +'<td><a class="fineReport-a" href="#" onClick=openReport("wean",null,'+farmId+',"'+farmName+'">'+(row.todayWeanWeight).toFixed(2)+'</a></td></tr>'
												  +'<tr><td class="border-right"><a class="fineReport-a" href="#" onClick=openReport("wean",null,'+farmId+',"'+farmName+'",'+dateTypeFlag+')>'+(row.avgPigQty).toFixed(2)+'</a></td>'
												  +'<td><a class="fineReport-a" href="#" onClick=openReport("wean",null,'+farmId+',"'+farmName+'">'+(row.todayAvgPigQty).toFixed(2)+'</a></td></tr>'
												  +'<tr><td class="border-right"><a class="fineReport-a" href="#" onClick=openReport("wean",null,'+farmId+',"'+farmName+'",'+dateTypeFlag+')>'+(row.avgWeight).toFixed(2)+'</a></td>'
												  +'<td><a class="fineReport-a" href="#" onClick=openReport("wean",null,'+farmId+',"'+farmName+'">'+(row.todayWeanWeight).toFixed(2)+'</a></td></tr>'
									}
								dataHtml +='</tbody>'
								+'</table>'
							+'</div>';
			tarDivAddHtml('DNBD',farmId,dataHtml);
		}
		if(row.changeType == 'toChildCare'){
			var dataHtml = '<div changeFarmId="'+farmId+'">'
								+'<table class="pd-table">'
									+'<tbody>'
									if(farmId == 'group'){
										dataHtml +='<tr><td class="border-right">'+row.nestQty+'</td><td>'+row.todayNestQty+'</td></tr>'
												  +'<tr><td class="border-right">'+row.childQty+'</td><td>'+row.todayChildQty+'</td></tr>'
												  +'<tr><td class="border-right">'+(row.childWeight).toFixed(2)+'</td><td>'+(row.todayChildWeight).toFixed(2)+'</td></tr>'
												  +'<tr><td class="border-right">'+(row.avgChildWeight).toFixed(2)+'</td><td>'+(row.todayAvgChildWeight).toFixed(2)+'</td></tr>'
												  +'<tr><td class="border-right">'+(row.avgDateage).toFixed(2)+'</td><td>'+(row.todayAvgDateage).toFixed(2)+'</td></tr>'
									}else{
										dataHtml +='<tr><td class="border-right"><a class="fineReport-a" href="#" onClick=openReport("child","3,4",'+farmId+',"'+farmName+'",'+dateTypeFlag+')>'+row.nestQty+'</a></td>'
												  +'<td><a class="fineReport-a" href="#" onClick=openReport("child","3,4",'+farmId+',"'+farmName+'")>'+row.todayNestQty+'</a></td></tr>'
										 		  +'<tr><td class="border-right"><a class="fineReport-a" href="#" onClick=openReport("child","3,4",'+farmId+',"'+farmName+'",'+dateTypeFlag+')>'+row.childQty+'</a></td>'
										 		  +'<td><a class="fineReport-a" href="#" onClick=openReport("child","3,4",'+farmId+',"'+farmName+'")>'+row.todayChildQty+'</a></td></tr>'
										 		  +'<tr><td class="border-right"><a class="fineReport-a" href="#" onClick=openReport("child","3,4",'+farmId+',"'+farmName+'",'+dateTypeFlag+')>'+(row.childWeight).toFixed(2)+'</a></td>'
										 		  +'<td><a class="fineReport-a" href="#" onClick=openReport("child","3,4",'+farmId+',"'+farmName+'")>'+(row.todayChildWeight).toFixed(2)+'</a></td></tr>'
										 		  +'<tr><td class="border-right"><a class="fineReport-a" href="#" onClick=openReport("child","3,4",'+farmId+',"'+farmName+'",'+dateTypeFlag+')>'+(row.avgChildWeight).toFixed(2)+'</a></td>'
										 		  +'<td><a class="fineReport-a" href="#" onClick=openReport("child","3,4",'+farmId+',"'+farmName+'")>'+(row.todayAvgChildWeight).toFixed(2)+'</a></td></tr>'
										 		  +'<tr><td class="border-right"><a class="fineReport-a" href="#" onClick=openReport("child","3,4",'+farmId+',"'+farmName+'",'+dateTypeFlag+')>'+(row.avgDateage).toFixed(2)+'</a></td>'
										 		  +'<td><a class="fineReport-a" href="#" onClick=openReport("child","3,4",'+farmId+',"'+farmName+'")>'+(row.todayAvgDateage).toFixed(2)+'</a></td></tr>'
									}
									+'</tbody>'
								+'</table>'
							+'</div>';
			tarDivAddHtml('ZBYBD',farmId,dataHtml);
		}
		if(row.changeType == 'toFatten'){
			var dataHtml = '<div changeFarmId="'+farmId+'">'
								+'<table class="pd-table">'
									+'<tbody>'
									if(farmId == 'group'){
										dataHtml +='<tr><td class="border-right">'+row.childQty+'</td><td>'+row.todayChildQty+'</td></tr>'
												  +'<tr><td class="border-right">'+(row.childWeight).toFixed(2)+'</td><td>'+(row.todayChildWeight).toFixed(2)+'</td></tr>'
												  +'<tr><td class="border-right">'+(row.avgWeight).toFixed(2)+'</td><td>'+(row.todayAvgWeight).toFixed(2)+'</td></tr>'
												  +'<tr><td class="border-right">'+(row.dateage).toFixed(2)+'</td><td>'+(row.todayDateage).toFixed(2)+'</td></tr>'
									}else{
										dataHtml +='<tr><td class="border-right"><a class="fineReport-a" href="#" onClick=openReport("child","5,6",'+farmId+',"'+farmName+'",'+dateTypeFlag+')>'+row.childQty+'</a></td>'
												  +'<td><a class="fineReport-a" href="#" onClick=openReport("child","5,6",'+farmId+',"'+farmName+'")>'+row.todayChildQty+'</a></td></tr>'
												  +'<tr><td class="border-right"><a class="fineReport-a" href="#" onClick=openReport("child","5,6",'+farmId+',"'+farmName+'",'+dateTypeFlag+')>'+(row.childWeight).toFixed(2)+'</a></td>'
												  +'<td><a class="fineReport-a" href="#" onClick=openReport("child","5,6",'+farmId+',"'+farmName+'")>'+(row.todayChildWeight).toFixed(2)+'</a></td></tr>'
												  +'<tr><td class="border-right"><a class="fineReport-a" href="#" onClick=openReport("child","5,6",'+farmId+',"'+farmName+'",'+dateTypeFlag+')>'+(row.avgWeight).toFixed(2)+'</a></td>'
												  +'<td><a class="fineReport-a" href="#" onClick=openReport("child","5,6",'+farmId+',"'+farmName+'")>'+(row.todayAvgWeight).toFixed(2)+'</a></td></tr>'
												  +'<tr><td class="border-right"><a class="fineReport-a" href="#" onClick=openReport("child","5,6",'+farmId+',"'+farmName+'",'+dateTypeFlag+')>'+(row.dateage).toFixed(2)+'</a></td>'
												  +'<td><a class="fineReport-a" href="#" onClick=openReport("child","5,6",'+farmId+',"'+farmName+'")>'+(row.todayDateage).toFixed(2)+'</a></td></tr>'
									}
								dataHtml +='</tbody>'
								+'</table>'
							+'</div>';
			tarDivAddHtml('ZYFBD',farmId,dataHtml);
		}
		if(row.changeType == 'breedPigDie'){
			var dataHtml = '<div changeFarmId="'+farmId+'">'
								+'<table class="pd-table">'
									+'<tbody>'
									if(farmId == 'group'){
										dataHtml +='<tr><td class="border-right">'+row.gttHbPigQty+'</td><td>'+row.todayGttHbPigQty+'</td></tr>'
												  +'<tr><td class="border-right">'+row.gttScPigQty+'</td><td>'+row.todayGttScPigQty+'</td></tr>'
												  +'<tr><td class="border-right">'+row.gswHbpigQty+'</td><td>'+row.todayGswHbpigQty+'</td></tr>'
												  +'<tr><td class="border-right">'+row.gswScPigQty+'</td><td>'+row.todayGswScPigQty+'</td></tr>'
												  +'<tr><td class="border-right">'+row.mttHbPigQty+'</td><td>'+row.todayMttHbPigQty+'</td></tr>'
												  +'<tr><td class="border-right">'+row.mttScPigQty+'</td><td>'+row.todayMttScPigQty+'</td></tr>'
												  +'<tr><td class="border-right">'+row.mswHbPigQty+'</td><td>'+row.todayMswHbPigQty+'</td></tr>'
												  +'<tr><td class="border-right">'+row.mswScPigQty+'</td><td>'+row.todayMswScPigQty+'</td></tr>'
									}else{
										dataHtml +='<tr><td class="border-right"><a class="fineReport-a" href="#" onClick=openReport("leave","1",'+farmId+',"'+farmName+'",'+dateTypeFlag+',5)>'+row.gttHbPigQty+'</a></td>'
												  +'<td><a class="fineReport-a" href="#" onClick=openReport("leave","1",'+farmId+',"'+farmName+'",null,5)>'+row.todayGttHbPigQty+'</a></td></tr>'
												  +'<tr><td class="border-right"><a class="fineReport-a" href="#" onClick=openReport("leave","2",'+farmId+',"'+farmName+'",'+dateTypeFlag+',5)>'+row.gttScPigQty+'</a></td>'
												  +'<td><a class="fineReport-a" href="#" onClick=openReport("leave","2",'+farmId+',"'+farmName+'",null,5)>'+row.todayGttScPigQty+'</a></td></tr>'
												  +'<tr><td class="border-right"><a class="fineReport-a" href="#" onClick=openReport("leave","1",'+farmId+',"'+farmName+'",'+dateTypeFlag+',2)>'+row.gswHbpigQty+'</a></td>'
												  +'<td><a class="fineReport-a" href="#" onClick=openReport("leave","1",'+farmId+',"'+farmName+'",null,2)>'+row.todayGswHbpigQty+'</a></td></tr>'
												  +'<tr><td class="border-right"><a class="fineReport-a" href="#" onClick=openReport("leave","2",'+farmId+',"'+farmName+'",'+dateTypeFlag+',2)>'+row.gswScPigQty+'</a></td>'
												  +'<td><a class="fineReport-a" href="#" onClick=openReport("leave","2",'+farmId+',"'+farmName+'",null,2)>'+row.todayGswScPigQty+'</a></td></tr>'
												  +'<tr><td class="border-right"><a class="fineReport-a" href="#" onClick=openReport("leave","3",'+farmId+',"'+farmName+'",'+dateTypeFlag+',5)>'+row.mttHbPigQty+'</a></td>'
												  +'<td><a class="fineReport-a" href="#" onClick=openReport("leave","3",'+farmId+',"'+farmName+'",null,5)>'+row.todayMttHbPigQty+'</a></td></tr>'
												  +'<tr><td class="border-right"><a class="fineReport-a" href="#" onClick=openReport("leave","4,6,7,8,9,11",'+farmId+',"'+farmName+'",'+dateTypeFlag+',5)>'+row.mttScPigQty+'</a></td>'
												  +'<td><a class="fineReport-a" href="#" onClick=openReport("leave","4,6,7,8,9,11",'+farmId+',"'+farmName+'",null,5)>'+row.todayMttScPigQty+'</a></td></tr>'
												  +'<tr><td class="border-right"><a class="fineReport-a" href="#" onClick=openReport("leave","3",'+farmId+',"'+farmName+'",'+dateTypeFlag+',2)>'+row.mswHbPigQty+'</a></td>'
												  +'<td><a class="fineReport-a" href="#" onClick=openReport("leave","3",'+farmId+',"'+farmName+'",null,2)>'+row.todayMswHbPigQty+'</a></td></tr>'
												  +'<tr><td class="border-right"><a class="fineReport-a" href="#" onClick=openReport("leave","4,6,7,8,9,11",'+farmId+',"'+farmName+'",'+dateTypeFlag+',2)>'+row.mswScPigQty+'</a></td>'
												  +'<td><a class="fineReport-a" href="#" onClick=openReport("leave","4,6,7,8,9,11",'+farmId+',"'+farmName+'",null,2)>'+row.todayMswScPigQty+'</a></td></tr>'
									}
								dataHtml +='</tbody>'
								+'</table>'
							+'</div>';
			tarDivAddHtml('ZZSWBD',farmId,dataHtml);
		}
		if(row.changeType == 'childPigDie'){
			var dataHtml = '<div changeFarmId="'+farmId+'">'
								+'<table class="pd-table">'
									+'<tbody>'
									if(farmId == 'group'){
										dataHtml +='<tr><td class="border-right">'+row.pigQty+'</td><td>'+row.todayPigQty+'</td></tr>'
									}else{
										dataHtml +='<tr><td class="border-right"><a class="fineReport-a" href="#" onClick=openReport("childDie",null,'+farmId+',"'+farmName+'",'+dateTypeFlag+')>'+row.pigQty+'</a></td>'
												  +'<td><a class="fineReport-a" href="#" onClick=openReport("childDie",null,'+farmId+',"'+farmName+'")>'+row.todayPigQty+'</a></td></tr>'
									}
								dataHtml +='</tbody>'
								+'</table>'
							+'</div>';
			tarDivAddHtml('CFSWBD',farmId,dataHtml);
		}
		if(row.changeType == 'childCareDie'){
			var dataHtml = '<div changeFarmId="'+farmId+'">'
								+'<table class="pd-table">'
									+'<tbody>'
									if(farmId == 'group'){
										dataHtml +='<tr><td class="border-right">'+row.pigQty+'</td><td>'+row.todayPigQty+'</td></tr>'
									}else{
										dataHtml +='<tr><td class="border-right"><a class="fineReport-a" href="#" onClick=openReport("CommercialDie",8,'+farmId+',"'+farmName+'",'+dateTypeFlag+')>'+row.pigQty+'</a></td>'
												  +'<td><a class="fineReport-a" href="#" onClick=openReport("CommercialDie",8,'+farmId+',"'+farmName+'")>'+row.todayPigQty+'</a></td></tr>'
									}
								dataHtml +='</tbody>'
								+'</table>'
							+'</div>';
			tarDivAddHtml('BYSWBD',farmId,dataHtml);
		}
		if(row.changeType == 'fattenDie'){
			var dataHtml = '<div changeFarmId="'+farmId+'">'
								+'<table class="pd-table">'
									+'<tbody>'
									if(farmId == 'group'){
										dataHtml +='<tr><td class="border-right">'+row.pigQty+'</td><td>'+row.todayPigQty+'</td></tr>'
									}else{
										dataHtml +='<tr><td class="border-right"><a class="fineReport-a" href="#" onClick=openReport("CommercialDie",9,'+farmId+',"'+farmName+'",'+dateTypeFlag+')>'+row.pigQty+'</a></td>'
												  +'<td><a class="fineReport-a" href="#" onClick=openReport("CommercialDie",9,'+farmId+',"'+farmName+'")>'+row.todayPigQty+'</a></td></tr>'
									}
								dataHtml +='</tbody>'
								+'</table>'
							+'</div>';
			tarDivAddHtml('YFSWBD',farmId,dataHtml);
		}
		if(row.changeType == 'childCareSale'){
			var dataHtml = '<div changeFarmId="'+farmId+'">'
								+'<table class="pd-table">'
									+'<tbody>'
									if(farmId == 'group'){
										dataHtml +='<tr><td class="border-right">'+row.saleNum+'</td><td>'+row.todaySaleNum+'</td></tr>'
												  +'<tr><td class="border-right">'+(row.totalWeight).toFixed(2)+'</td><td>'+(row.todayTotalWeight).toFixed(2)+'</td></tr>'
												  +'<tr><td class="border-right">'+(row.avgWeight).toFixed(2)+'</td><td>'+(row.todayAvgWeight).toFixed(2)+'</td></tr>'
												  +'<tr><td class="border-right">'+(row.totalPrice).toFixed(2)+'</td><td>'+(row.todayTotalPrice).toFixed(2)+'</td></tr>'
									}else{
										dataHtml +='<tr><td class="border-right"><a class="fineReport-a" href="#" onClick=openReport("sale",3,'+farmId+',"'+farmName+'",'+dateTypeFlag+',8)>'+row.saleNum+'</a></td>'
												  +'<td><a class="fineReport-a" href="#" onClick=openReport("sale",3,'+farmId+',"'+farmName+'",null,8)>'+row.todaySaleNum+'</a></td></tr>'
												  +'<tr><td class="border-right"><a class="fineReport-a" href="#" onClick=openReport("sale",3,'+farmId+',"'+farmName+'",'+dateTypeFlag+',8)>'+(row.totalWeight).toFixed(2)+'</a></td>'
												  +'<td><a class="fineReport-a" href="#" onClick=openReport("sale",3,'+farmId+',"'+farmName+'",null,8)>'+(row.todayTotalWeight).toFixed(2)+'</a></td></tr>'
												  +'<tr><td class="border-right"><a class="fineReport-a" href="#" onClick=openReport("sale",3,'+farmId+',"'+farmName+'",'+dateTypeFlag+',8)>'+(row.avgWeight).toFixed(2)+'</a></td>'
												  +'<td><a class="fineReport-a" href="#" onClick=openReport("sale",3,'+farmId+',"'+farmName+'",null,8)>'+(row.todayAvgWeight).toFixed(2)+'</a></td></tr>'
												  +'<tr><td class="border-right"><a class="fineReport-a" href="#" onClick=openReport("sale",3,'+farmId+',"'+farmName+'",'+dateTypeFlag+',8)>'+(row.totalPrice).toFixed(2)+'</a></td>'
												  +'<td><a class="fineReport-a" href="#" onClick=openReport("sale",3,'+farmId+',"'+farmName+'",null,8)>'+(row.todayTotalPrice).toFixed(2)+'</a></td></tr>'
									}
								dataHtml +='</tbody>'
								+'</table>'
							+'</div>';
			tarDivAddHtml('BYXSBD',farmId,dataHtml);
		}
		if(row.changeType == 'fattenSale'){
			var dataHtml = '<div changeFarmId="'+farmId+'">'
								+'<table class="pd-table">'
									+'<tbody>'
									if(farmId == 'group'){
										dataHtml +='<tr><td class="border-right">'+row.saleNum+'</td><td>'+row.todaySaleNum+'</td></tr>'
												  +'<tr><td class="border-right">'+(row.totalWeight).toFixed(2)+'</td><td>'+(row.todayTotalWeight).toFixed(2)+'</td></tr>'
												  +'<tr><td class="border-right">'+(row.avgWeight).toFixed(2)+'</td><td>'+(row.todayAvgWeight).toFixed(2)+'</td></tr>'
												  +'<tr><td class="border-right">'+(row.totalPrice).toFixed(2)+'</td><td>'+(row.todayTotalPrice).toFixed(2)+'</td></tr>'
									}else{
										dataHtml +='<tr><td class="border-right"><a class="fineReport-a" href="#" onClick=openReport("sale",3,'+farmId+',"'+farmName+'",'+dateTypeFlag+',9)>'+row.saleNum+'</a></td>'
												  +'<td><a class="fineReport-a" href="#" onClick=openReport("sale",3,'+farmId+',"'+farmName+'",null,9)>'+row.todaySaleNum+'</a></td></tr>'
												  +'<tr><td class="border-right"><a class="fineReport-a" href="#" onClick=openReport("sale",3,'+farmId+',"'+farmName+'",'+dateTypeFlag+',9)>'+(row.totalWeight).toFixed(2)+'</a></td>'
												  +'<td><a class="fineReport-a" href="#" onClick=openReport("sale",3,'+farmId+',"'+farmName+'",null,9)>'+(row.todayTotalWeight).toFixed(2)+'</a></td></tr>'
												  +'<tr><td class="border-right"><a class="fineReport-a" href="#" onClick=openReport("sale",3,'+farmId+',"'+farmName+'",'+dateTypeFlag+',9)>'+(row.avgWeight).toFixed(2)+'</a></td>'
												  +'<td><a class="fineReport-a" href="#" onClick=openReport("sale",3,'+farmId+',"'+farmName+'",null,9)>'+(row.todayAvgWeight).toFixed(2)+'</a></td></tr>'
												  +'<tr><td class="border-right"><a class="fineReport-a" href="#" onClick=openReport("sale",3,'+farmId+',"'+farmName+'",'+dateTypeFlag+',9)>'+(row.totalPrice).toFixed(2)+'</a></td>'
												  +'<td><a class="fineReport-a" href="#" onClick=openReport("sale",3,'+farmId+',"'+farmName+'",null,9)>'+(row.todayTotalPrice).toFixed(2)+'</a></td></tr>'
									}
								dataHtml +='</tbody>'
								+'</table>'
							+'</div>';
			tarDivAddHtml('YFXSBD',farmId,dataHtml);
		}
		if(row.changeType == 'defectsSale'){
			var dataHtml = '<div changeFarmId="'+farmId+'">'
								+'<table class="pd-table">'
									+'<tbody>'
									if(farmId == 'group'){
										dataHtml +='<tr><td class="border-right">'+row.saleNum+'</td><td>'+row.todaySaleNum+'</td></tr>'
												  +'<tr><td class="border-right">'+(row.totalWeight).toFixed(2)+'</td><td>'+(row.todayTotalWeight).toFixed(2)+'</td></tr>'
												  +'<tr><td class="border-right">'+(row.totalPrice).toFixed(2)+'</td><td>'+(row.todayTotalPrice).toFixed(2)+'</td></tr>'
									}else{
										dataHtml +='<tr><td class="border-right"><a class="fineReport-a" href="#" onClick=openReport("sale",5,'+farmId+',"'+farmName+'",'+dateTypeFlag+')>'+row.saleNum+'</a></td>'
												 +'<td><a class="fineReport-a" href="#" onClick=openReport("sale",5,'+farmId+',"'+farmName+'")>'+row.todaySaleNum+'</a></td></tr>'
												 +'<tr><td class="border-right"><a class="fineReport-a" href="#" onClick=openReport("sale",5,'+farmId+',"'+farmName+'",'+dateTypeFlag+')>'+(row.totalWeight).toFixed(2)+'</a></td>'
												 +'<td><a class="fineReport-a" href="#" onClick=openReport("sale",5,'+farmId+',"'+farmName+'")>'+(row.todayTotalWeight).toFixed(2)+'</a></td></tr>'
												 +'<tr><td class="border-right"><a class="fineReport-a" href="#" onClick=openReport("sale",5,'+farmId+',"'+farmName+'",'+dateTypeFlag+')>'+(row.totalPrice).toFixed(2)+'</a></td>'
												 +'<td><a class="fineReport-a" href="#" onClick=openReport("sale",5,'+farmId+',"'+farmName+'")>'+(row.todayTotalPrice).toFixed(2)+'</a></td></tr>'
									}
								dataHtml +='</tbody>'
								+'</table>'
							+'</div>';
			tarDivAddHtml('CCZXSBD',farmId,dataHtml);
		}
	});
}
/**
 * field收缩展开方法
 */
function upOrDown(para){
	if(para.className == 'arrowUp'){
		para.className = 'arrowDown';
		para.parentNode.nextElementSibling.style.display = 'none';
	}else{
		para.className = 'arrowUp';
		para.parentNode.nextElementSibling.style.display = 'block';
	}
	navTop = [];
	var navList = $('#changeNav').find('li');
	$.each(navList,function(i,nav){
		navTop.push($("#"+nav.dataset.href).offset().top-$("#changeFiexd").offset().top-$("#changeFiexd").height());
	})
}
//打开报表
function openReport(reportName,type,farmId,farmName,dateType,leaveType){
	var finereportUrl = $('#finereport_url').val(),
	finereportUsername = $('#finereport_username').val(),
	employName = $('#employName').val();
	var reportUrl = 'http://www.xnplus.cn:4380/WebReport/ReportServer?reportlet=XNPLUS%2F'+reportName+'.cpt&dbUrl='
		+finereportUrl+'&dbUserName='+finereportUsername+'&farmId='+farmId+'&farmName='+farmName+'&userName='
		+employName;
	var endDate = new Date(),startDate = new Date();
	if(dateType == '1'){
		startDate.setDate(endDate.getDate() - endDate.getDay());
	}else if(dateType == '2'){
		startDate.setDate(1);
	}else if(dateType == '3'){
	    var spring=0; //春  
        var summer=3; //夏  
        var fall=6;   //秋  
        var winter=9;//冬
        if(startDate.getMonth() < 3){
        	startDate.setMonth(spring);
        }else if(startDate.getMonth() < 6){
        	startDate.setMonth(summer);
        }else if(startDate.getMonth() < 9){
        	startDate.setMonth(fall);
        }else{
        	startDate.setMonth(winter);
        }
        startDate.setDate(1);
	}else if(dateType == '4'){
		startDate.setMonth(0);
		startDate.setDate(1);
	}
	startDate = startDate.format("yyyy-MM-dd");
	endDate = endDate.format("yyyy-MM-dd");
	if(reportName == 'breed'){
		reportUrl += '&breedDateStart='+startDate+'&breedDateEnd='+endDate+'&breedType='+type;
	}
	if(reportName == 'pregnancyCheck'){
		reportUrl += '&pregnancyDateStart='+startDate+'&pregnancyDateEnd='+endDate+'&PREGNANCY_RESULT='+type;
	}
	if(reportName == 'delivery'){
		reportUrl += '&deliveryDateStart='+startDate+'&deliveryDateEnd='+endDate;
	}
	if(reportName == 'wean'){
		reportUrl += '&weanDateStart='+startDate+'&weanDateEnd='+endDate;
	}
	if( reportName == 'child'){
		reportUrl += '&childDateStart='+startDate+'&childDateStart='+endDate+'&changeType='+type;
	}
	if( reportName == 'leave'){
		reportUrl += '&leaveDateStart='+startDate+'&leaveDateEnd='+endDate+'&pigClass='+type+'&leaveType='+leaveType;
	}
	if( reportName == 'childDie'){
		reportUrl += '&childDieDateStart='+startDate+'&childDieDateEnd='+endDate;
	}
	if(reportName == 'CommercialDie'){
		reportUrl += '&leaveDateStart='+startDate+'&leaveDateEnd='+endDate+'&houseType='+type;
	}
	if(reportName == 'sale'){
		reportUrl += '&saleDateStart='+startDate+'&saleDateEnd='+endDate+'&saleDescribe='+type;
		if(leaveType){
			reportUrl +='&houseType='+leaveType;
		}
	}
	window.open(reportUrl); 
}
function performanceChangeAddData(farmName,farmId,date,emptyFlag){
	emptyFlag = emptyFlag == undefined ? false : emptyFlag;
	if(emptyFlag){
		$('#performanceChangeTable').empty();
		addPerformanceType();
	}
	if(farmName){
		$('.pd-p-table-thead').append('<div perFormanceFarmId="'+farmId+'"><strong>'+farmName+'</strong></div>');
	}
	jAjax.submit("/production/searchPerformanceComparisonToList.do",{
		farmId:farmId,
		dateContrast:date
	},function(response){
		var display = null;
		if($('div[perFormanceFarmId='+farmId+']')[1]){
			display = $($('div[perFormanceFarmId='+farmId+']')[1]).css('display');
			$($('div[perFormanceFarmId='+farmId+']')[1]).remove();
		}
		var html = '';
		if(display){
			html = '<div perFormanceFarmId="'+farmId+'"  style="display:'+display+'"><table class="pd-table"><tbody>';
		}else{
			html = '<div perFormanceFarmId="'+farmId+'"><table class="pd-table"><tbody>';
		}
		var trList = $('#performanceChangeTable').find('tr');
		$.each(trList,function(i,tr){
			var exitFlag = false;
			$.each(response,function(j,item){
				if(item.ASSESS_CONTENT == tr.dataset.codevalue){
					html += '<tr><td>'+item.ASSESS_INDEX+'</td></tr>';
					exitFlag = true;
					return false;
				}
			});
			if(!exitFlag){
				html += '<tr><td>0</td></tr>';
			}
		});
		html += '</tbody></table></div>';
		$('.pd-p-table-tbody').append(html);
		checkIsDisplay(farmId,true);
	},null,null,true);
}
function searchDateChange(newValue,oldValue){
	var farmDiv = $('.pd-p-table-thead').find('div');
	$.each(farmDiv,function(i,div){
		var farmId = $(div).attr('performancefarmid');
		if(farmId){
			performanceChangeAddData(null,farmId,newValue,true);
		}
	})
}