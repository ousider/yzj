var title = '统计用料记录';
var prUrl = '/supplyChian/';
var searchMainListUrl = prUrl + 'searchDailyRecordToPage.do?searchType=group';
var searchDetailListUrl = prUrl + 'searchDailyRecordDetailToList.do';
var editWarehouseEnoughUrl = prUrl + 'editWarehouseEnough.do'
/** *********页面渲染开始*************************************************************************/

$(document).ready(function(){
	dafengModelConfirm();
	
	/**
	 * 主表加载
	 */
	
	$('#table').datagrid(
		j_grid_view({
			toolbarList:"#tableToolbarList",
			url:searchMainListUrl+'&dafengModel='+$('#dafengModel').val(),
			columnFields:'materialName,materialFirstClassName,materialSecondClassName,supplierSortName,manufacturer,specAll,existsQtyUnit,quantityUnit',
			columnTitles:'物料名称,大类,中类,供应商,厂家,规格,库存量,需求量',
			hiddenFields:'',
			onDblClickRowFun:searchDailyRecordDetail,
		},'table')
	);
	//申请类型
	xnCdCombobox({
		id:'showApplyType',
		typeCode:'MATERIAL_FIRST_CLASS',
		hasAll:true,
		includeValue:['21','22','30','40','50','90'],
		onChange:changeShowApplyType
	});
	setTimeout(function(){
		$('#dailyRecordDetailTable').datagrid(
				j_grid_view({
					haveCheckBox:false,
					columnFields:'planDate,materialName,materialSecondClassName,supplierSortName,manufacturer,specAll,quantityUnit,worker,notes',
					columnTitles:'计划需求日期,物料名称,中类,供应商,厂家,规格,需求量,申请人,备注',
					hiddenFields:'',
					fit:false,
					pagination:false,
					height:'100%',
					width:'100%'
				}));
	},500);
});

function searchDailyRecordDetail(index,row){
	$('#dailyRecordDetailTable').datagrid('load',basicUrl+searchDetailListUrl+'?ids[]='+row.detailRowIds);
	leftSilpFun('dailyRecordDetailId');
}

function onBtnSearchDailyRecordDetail(){
	var record = $('#table').datagrid('getSelections');
	var length = record.length;
	if(length < 1){
		$.messager.alert('警告','请选择一条数据！');
	}else if(length > 1){
		$.messager.alert('警告','只能选择一条数据！');
	}else{
		var index = $('#table').datagrid('getRowIndex',record[0]);
		searchDailyRecordDetail(index,record[0]);
	}
}

//生成需求单
function onBtnCreateRequireBill(){
	var applyType = $('#showApplyType').combobox('getValue');
	if(applyType == ''){
		$.messager.alert('警告','请选择具体的物料类型！');
	}else{
		var record = $('#table').datagrid('getSelections');
		var length = record.length;
		if(length < 1){
			$.messager.alert('警告','请选择一条数据！');
		}else{
			var tabNum = parseInt((window.parent.document.getElementById("header").clientWidth - 175 - 74 -20)/130);
			var liLength = $(".tabList", parent.document).length;
			//判断打开的tab是否已经超过最大值
			if(liLength >= 15){
				$.messager.alert('警告', '最多只能打开15个页面，请关闭无用页面重新点击！');
			}else{
				//获取英文事件名称
				var eventName = 'RequiredBill';
				//获取中文事件名称
				var chinseEventName = '需求单';
				var eventUrl = '/jsp/supplyChian/RequiredBill.jsp';

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
						var requireBillContentWindow = parent.document.getElementById("iframe_"+eventName).contentWindow;
						requireBillContentWindow.createBillType = 'other';
						requireBillContentWindow.onBtnAddMaterialRequire();
						requireBillContentWindow.requireBillAddData(record,{
							applyType:applyType
						});
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
					var requireBillContentWindow = parent.document.getElementById("iframe_"+eventName).contentWindow;
					var editWinDisplay = $('#editWin',requireBillContentWindow.document).parent().css('display');
					if(editWinDisplay == 'block'){
						requireBillContentWindow.onBtnCancel();
					}
					requireBillContentWindow.createBillType = 'other';
					requireBillContentWindow.onBtnAddMaterialRequire();
					requireBillContentWindow.requireBillAddData(record,{
						applyType:applyType
					});
				}
			}
		}
	}
}
/**
 * tab左移方法
 */
function turnLeftFun(){
	var tabList = $(".tabList", parent.document).find('li');
	for(var i in tabList){
		if(tabList[i].style.display != 'none'){
			tabList[i].style.display = 'none';
			hideTabNum ++ ;
			var hideTableHtml = '<li data-eventname="'+tabList[i].dataset.eventname+'" onClick="showHideTab(this)">'+tabList[i].innerText+'</li>';
			$("#hideTabList", parent.document).find('ul').append(hideTableHtml);
			$("#hideTabNum", parent.document).html(hideTabNum);
			break;
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

function changeShowApplyType(newValue,oldValue){
	$('#table').datagrid('load',{applyType:newValue});
}

function tableRefresh(){
	$('#table').datagrid('reload');
}

function onBtnWarehouseEnough(){
	var record = $('#table').datagrid('getSelections');
	var length = record.length;
	if(length < 1){
		$.messager.alert('警告','请选择一条数据！');
	}else{
		var ids = [];
		$.each(record,function(index,data){
			var arr = data.detailRowIds.split(",")
			ids = ids.concat(arr);
		});
		
		$.messager.progress();
		jAjax.submit(editWarehouseEnoughUrl,{'ids':ids}
		,function(response){
			$.messager.alert('提示','操作成功！');
			$('#table').datagrid('reload');
    		$.messager.progress('close');
		}
		,function(response){
    		jAjax.errorFunc(response.errorMsg);
    		$.messager.progress('close');
		},null,true,null,null);
	}
}