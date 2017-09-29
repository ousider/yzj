var prUrl='/portal';
var saveUrl=prUrl+'/ee.do';
var summaryDate = undefined;
var clickIndex = undefined;
var rows9997 = null;
var rows9998 = null;
var rows9999 = null;
var rows3030 = null;
var insertDate = null;
var listTable2Text = '商品猪';
var listTableLoadedFlag = false;
$(document).ready(function(){
	clickIndex = 0;
//	$('#table').datagrid(
//		j_detailGrid({	
//			width:'100%',
//			height:'100%',
//			fit:true,
//			url:'/production/searchSendSAPHana.do',
//			columns:[[
//			          	{field:'ck',checkbox:true},
//		              	j_gridText({field:'rowId',title:'ID',hidden:true}),		              	
//		              	j_gridText({field:'toSapDate',title:'数据日期',style:true,color:'#66CDAA'}),
//		              	j_gridText({field:'creatTime',title:'上传日期',style:true,color:'#66CDAA'}),
//		              	j_gridText({field:'deletedFlag',title:'删除',style:true,color:'#66CDAA'}),
//		              	j_gridText({field:'deletedTime',title:'删除时间',style:true,color:'#66CDAA'}),
//		              	j_gridText({field:'toSapAgin',title:'再次上传',style:true,color:'#66CDAA'})
//				    ]]
//		})
//	);
	
	/**
	 * 主表加载
	 */
	$('#table').datagrid(
			j_grid({
				toolbar:"#toolbar01",
				singleSelect:true,
				url:'/production/searchSendSAPHana.do',
				columnFields:'rowId,toSapDate,creatTime,deletedFlag,deletedTime,toSapAgin,summary',
				columnTitles:'ID,数据日期,上传日期,删除,删除时间,再次上传,是否汇总',
				hiddenFields:'rowId'
			}));
	
	$('#listTable2').datagrid(
			j_grid_view({	
				width:'99%',
				height:'90%',
				fit:false,
				// url:'/production/insertSAPDateBase.do?&code=9997&event=generateDetails',
				columns:[[
				          	{field:'ck',checkbox:true},
			              	j_gridText({field:'row_id',title:'id',hidden:true}),
			              	j_gridText({field:'mtc_BranchID',title:'分公司编码',style:true,color:'#66CDAA',hidden:true}),
			              	j_gridText({field:'mtc_DeptID',title:'猪场编码',style:true,color:'#66CDAA',hidden:true}),
			              	j_gridText({field:'mtc_FarmId',title:'猪场id',style:true,color:'#66CDAA',hidden:true}),
			              	j_gridText({field:'mtc_FarmName',title:'猪场名称',style:true,color:'#66CDAA'}),
			              	j_gridText({field:'mtc_Unit',title:'猪舍编号',style:true,color:'#66CDAA',hidden:true}),
			              	j_gridText({field:'mtc_HouseId',title:'猪舍id',style:true,color:'#66CDAA',hidden:true}),
			              	j_gridText({field:'mtc_HouseName',title:'猪舍名称',style:true,color:'#66CDAA'}),
			              	j_gridText({field:'mtc_PigType',title:'猪只类型',style:true,color:'#66CDAA'}),
			              	j_gridText({field:'mtc_Period',title:'期间',style:true,color:'#66CDAA'}),
			              	j_gridText({field:'mtc_DocDate',title:'日期',style:true,color:'#66CDAA'}),
			              	j_gridText({field:'mtc_BegQty',title:'期初存栏',style:true,color:'#66CDAA'}),
			              	j_gridText({field:'mtc_InQty',title:'初生仔猪',style:true,color:'#66CDAA'}),
			              	j_gridText({field:'mtc_PurQty',title:'新购入场',style:true,color:'#66CDAA'}),
			              	j_gridText({field:'mtc_InQty_Inner',title:'转群转入-内转',style:true,color:'#66CDAA'}),
			              	j_gridText({field:'mtc_InQty_Normal',title:'转群转入-常规',style:true,color:'#66CDAA'}),
			              	j_gridText({field:'mtc_OutQty_Inner',title:'转群转出-内转',style:true,color:'#66CDAA'}),
			              	j_gridText({field:'mtc_OutQty_Normal',title:'转群转出-常规',style:true,color:'#66CDAA'}),
			              	j_gridText({field:'mtc_TransToCF',title:'转出到产房',style:true,color:'#66CDAA'}),
			              	j_gridText({field:'mtc_TransToBY',title:'转出到保育',style:true,color:'#66CDAA'}),
			              	j_gridText({field:'mtc_TransToPY',title:'转出到培育',style:true,color:'#66CDAA'}),
			              	j_gridText({field:'mtc_TransToHB',title:'转出到后备',style:true,color:'#66CDAA'}),
			              	j_gridText({field:'mtc_TransToYF',title:'转出到育肥',style:true,color:'#66CDAA'}),
			              	j_gridText({field:'mtc_DieQty',title:'死亡',style:true,color:'#66CDAA'}),
			              	j_gridText({field:'mtc_KillQty',title:'自宰',style:true,color:'#66CDAA'}),
			              	j_gridText({field:'mtc_TransToSC',title:'后备转生产',style:true,color:'#66CDAA'}),
			              	j_gridText({field:'mtc_SalesQty_Inner',title:'内部销售',style:true,color:'#66CDAA'}),
			              	j_gridText({field:'mtc_SalesQty_Normar',title:'外部销售',style:true,color:'#66CDAA'}),
			              	j_gridText({field:'mtc_AdjQty',title:'存栏调整',style:true,color:'#66CDAA'}),
			              	j_gridText({field:'mtc_EndQty',title:'期末存栏',style:true,color:'#66CDAA'}),
			              	j_gridText({field:'mtc_DaySum',title:'日龄',style:true,color:'#66CDAA'}),
			              	j_gridText({field:'mtc_Weight',title:'估重',style:true,color:'#66CDAA'})
					    ]],
					    onLoadSuccess:function(data){
					    	$('#listTable2Text').html(listTable2Text);
					    	$(this).datagrid('loaded');
					    }
			})
		);
	
	$('#listTable3').datagrid(
		j_grid_view({	
			width:'99%',
			height:'100%',
			fit:false,
			// url:'/production/insertSAPDateBase.do?&code=9998&event=generateDetails',
			columns:[[
			          {field:'ck',checkbox:true},
			          j_gridText({field:'row_Id',title:'ID',hidden:true}),
			          j_gridText({field:'mtc_BranchID',title:'分公司编码',style:true,color:'#66CDAA',hidden:true}),
			          j_gridText({field:'mtc_DeptID',title:'猪场编码',style:true,color:'#66CDAA',hidden:true,hidden:true}),
			          j_gridText({field:'mtc_FarmId',title:'猪场id',style:true,color:'#66CDAA',hidden:true}),
			          j_gridText({field:'mtc_FarmName',title:'猪场名称',style:true,color:'#66CDAA'}),
			          j_gridText({field:'mtc_Unit',title:'猪舍编号',style:true,color:'#66CDAA',hidden:true}),
			          j_gridText({field:'mtc_HouseId',title:'猪舍id',style:true,color:'#66CDAA',hidden:true}),
			          j_gridText({field:'mtc_HouseName',title:'猪舍名称',style:true,color:'#66CDAA'}),
			          j_gridText({field:'mtc_PigType',title:'猪只类型',style:true,color:'#66CDAA'}),
			          j_gridText({field:'mtc_Period',title:'期间',style:true,color:'#66CDAA'}),
			          j_gridText({field:'mtc_DocDate',title:'日期',style:true,color:'#66CDAA'}),
			          j_gridText({field:'mtc_TransType',title:'业务类型',style:true,color:'#66CDAA'}),
			          j_gridText({field:'mtc_RelaDeptID',title:'关联猪场',style:true,color:'#66CDAA',hidden:true}),
			          j_gridText({field:'mtc_RelaDeptID_FarmId',title:'关联猪场id',style:true,color:'#66CDAA',hidden:true}),
			          j_gridText({field:'mtc_RelaDeptID_FarmName',title:'关联猪场名称',style:true,color:'#66CDAA'}),
			          j_gridText({field:'mtc_RelaUnit',title:'关联猪舍',style:true,color:'#66CDAA',hidden:true}),
			          j_gridText({field:'mtc_RelaUnit_HouseId',title:'关联猪舍id',style:true,color:'#66CDAA',hidden:true}),
			          j_gridText({field:'mtc_RelaUnit_HouseName',title:'关联猪舍名称',style:true,color:'#66CDAA'}),
			          j_gridText({field:'mtc_InQty',title:'调入数量',style:true,color:'#66CDAA'}),
			          j_gridText({field:'mtc_InWeight',title:'调入重量',style:true,color:'#66CDAA'}),
			          j_gridText({field:'mtc_OutQty',title:'调出数量',style:true,color:'#66CDAA'}),
			          j_gridText({field:'mtc_OutWeight',title:'调出重量',style:true,color:'#66CDAA'})
			          ]]
		})
	);

	$('#listTable4').datagrid(
			j_grid_view({	
				width:'99%',
				height:'100%',
				fit:false,
//				url:'/production/insertSAPDateBase.do?&code=9999&event=generateDetails',
				columns:[[
				          {field:'ck',checkbox:true},
				          j_gridText({field:'row_Id',title:'ID',hidden:true}),
				          j_gridText({field:'mtc_BranchID',title:'分公司编码',style:true,color:'#66CDAA',hidden:true}),
				          j_gridText({field:'mtc_DeptID',title:'猪场编码',style:true,color:'#66CDAA',hidden:true}),
				          j_gridText({field:'mtc_FarmId',title:'猪场id',style:true,color:'#66CDAA',hidden:true}),
				          j_gridText({field:'mtc_FarmName',title:'猪场名称',style:true,color:'#66CDAA'}),
				          j_gridText({field:'mtc_Unit',title:'猪舍编号',style:true,color:'#66CDAA',hidden:true}),
				          j_gridText({field:'mtc_HouseId',title:'猪舍id',style:true,color:'#66CDAA',hidden:true}),
				          j_gridText({field:'mtc_HouseName',title:'猪舍名称',style:true,color:'#66CDAA'}),
				          j_gridText({field:'mtc_Period',title:'期间',style:true,color:'#66CDAA'}),
				          j_gridText({field:'mtc_PregStep_1',title:'妊娠阶段1',style:true,color:'#66CDAA'}),
				          j_gridText({field:'mtc_PregStep_2',title:'妊娠阶段2',style:true,color:'#66CDAA'}),
				          j_gridText({field:'mtc_PregStep_3',title:'妊娠阶段3',style:true,color:'#66CDAA'})
				          ]]
			})
	);
	$('#listTable5').datagrid(
			j_grid_view({	
				width:'99%',
				height:'100%',
				fit:false,
//				url:'/production/insertSAPDateBase.do?clickIndex:'+clickIndex+'code:3030&event=generateDetails',
				columns:[[
				          {field:'ck',checkbox:true},
				          j_gridText({field:'row_Id',title:'ID',hidden:true}),
				          j_gridText({field:'mtc_Period',title:'期间',style:true,color:'#66CDAA'}),
				          j_gridText({field:'mtc_BranchID',title:'分公司编码',style:true,color:'#66CDAA',hidden:true}),
				          j_gridText({field:'mtc_ItemCode',title:'猪只耳号',style:true,color:'#66CDAA'}),
				          j_gridText({field:'mtc_ItemName',title:'猪只描述',style:true,color:'#66CDAA'}),
				          j_gridText({field:'mtc_DeptID',title:'猪场编号',style:true,color:'#66CDAA',hidden:true}),
				          j_gridText({field:'mtc_FarmId',title:'公司id',style:true,color:'#66CDAA',hidden:true}),
				          j_gridText({field:'mtc_FarmName',title:'公司名称',style:true,color:'#66CDAA'}),
				          j_gridText({field:'mtc_Unit',title:'棚舍编号',style:true,color:'#66CDAA',hidden:true}),
				          j_gridText({field:'mtc_HouseId',title:'棚舍id',style:true,color:'#66CDAA',hidden:true}),
				          j_gridText({field:'mtc_HouseName',title:'棚舍名称',style:true,color:'#66CDAA'}),
				          j_gridText({field:'mtc_Parity',title:'胎次',style:true,color:'#66CDAA'}),
				          j_gridText({field:'mtc_PregStatus',title:'母猪状态',style:true,color:'#66CDAA'}),
				          j_gridText({field:'mtc_PregDate',title:'状态日期',style:true,color:'#66CDAA'}),
				          j_gridText({field:'mtc_DaySum',title:'状态持续天数',style:true,color:'#66CDAA'}),
				          j_gridText({field:'mtc_PregLevel',title:'妊娠阶段',style:true,color:'#66CDAA'})
				          ]]
			})
	);
	
});

//再次上传
function doSaveNew(){
		$.post({
			url:basicUrl + '/production/editSendSAPHanaLimit.do',
			data:{},
			success:function(response){
				response=eval('('+response+')');
				if(response.success){
					$('#table').datagrid(
							j_grid({
								toolbar:"#toolbar01",
								singleSelect:true,
								url:'/production/searchSendSAPHana.do',
								columnFields:'rowId,toSapDate,creatTime,deletedFlag,deletedTime,toSapAgin,summary',
								columnTitles:'ID,数据日期,上传日期,删除,删除时间,再次上传,是否汇总',
								hiddenFields:'rowId'
							}));
					$.messager.alert('提示','保存成功！');
				}else{
					$.messager.alert({
	                    title: '错误',
	                    msg: response.errorMsg
	                });
				}
				$('#btnSaveNew').attr('disabled',false).removeClass('btn-disabled');
				$.messager.progress('close');
			}
		});

	
}

/**
 * 上传致SAP财务系统
 * }
 */
function onBtnSAPInsert(){
	var rows = $('#table').datagrid('getRows');
	clickIndex = clickIndex+1;
//	if(rows.length == 0){
//		$.messager.alert('警告','请选择一条数据！');
//		return;
//	};
	$.messager.progress();
	if(rows.length == 0){
		$.post(basicUrl + "/production/insertSAPDateBase.do",{
			billDate:null,
			billId:null,
			clickIndex:clickIndex,
			code:9997
		},
		function(response){
			response=eval('('+response+')');
			$.messager.progress('close');
			if(response.success){
				$.messager.alert('提示','上传成功！');
			}else{
				$.messager.alert({
	                title: '错误',
	                msg: response.errorMsg
	            });
			}
		}
		);
	}else{
	$.post(basicUrl + "/production/insertSAPDateBase.do",{
		billDate:rows[0].checkDate,
		billId:rows[0].billId,
		clickIndex:clickIndex,
		code:9997
	},
	function(response){
		response=eval('('+response+')');
		$.messager.progress('close');
		if(response.success){
			$.messager.alert('提示','上传成功！');
		}else{
			$.messager.alert({
                title: '错误',
                msg: response.errorMsg
            });
		}
	}
	
	);
	}
}


//查看
function seartnView(){
	
	var record = $('#listTable').datagrid('getSelections');
	var length = record.length;
	if(length < 1){
		$.messager.alert('警告','请选择一条数据！');
	}else if(length > 1){
		$.messager.alert('警告','只能选择一条数据！');
	}else{
		var id = record[0].rowId;
		$.post(basicUrl + "/production/insertSAPDateBase.do?id="+id,{
			billDate:null,
			billId:null,
			clickIndex:clickIndex,
			code:9997
		},
		function(response){
			response=eval('('+response+')');
			$.messager.progress('close');
			if(response.success){
				$.messager.alert('提示','上传成功！');
			}else{
				$.messager.alert({
	                title: '错误',
	                msg: response.errorMsg
	            });
			}
		}
		);
	}
	
}	

//生成明细
function editToSapDetail(){
	//$("#listTableDIV").hide();
	$.post(basicUrl + "/production/searchHanaLogSign.do",{
	},
	function(response){
		response=eval('('+response+')');
		if(response.success){
			editToSapDetailList();
		}else{
			$.messager.alert({
                title: '错误',
                msg: response.errorMsg
            });
		}
	}
	);
}

//生成明细
function editToSapDetailList(){
	// **清空信息**
	$('#listTable2').datagrid('loadData',{success:true,total: 0, rows: [] });
	$('#listTable3').datagrid('loadData',{success:true,total: 0, rows: [] });
	$('#listTable4').datagrid('loadData',{success:true,total: 0, rows: [] });
	$('#listTable5').datagrid('loadData',{success:true,total: 0, rows: [] });
	 rows9997 = null;
	 rows9998 = null;
	 rows9999 = null;
	 rows3030 = null;
	 $('tr.collect').empty();
//	 $('tr.collect').html("");
	// **清空信息**
	var loadIndex = 4;
	$('#showCommitPanel').dialog({
		height:'100%'
	});
	$('#showCommitPanel').dialog('open');
	$('#listTable2').datagrid('loading');
	
	$.messager.progress();
	
	jAjax.submit('/production/editToSapSummaryDetail.do?&code=9997',null
	,function(data){
		rows9997 = data;
		var data01 = {success:true,total: data.SP.length, rows: data.SP };
		loadIndex--;
		if(loadIndex<=0){
			$.messager.progress('close');
		}
		$('#listTable2').datagrid('loadData',data01);
		listTable2Text = '商品猪';
	},null,'POST',true,null,null);
	
	jAjax.submit('/production/editToSapSummaryDetail.do?&code=9998',null
			,function(data){
				rows9998 = data;
				var data01 = {success:true,total: data.SP.length, rows: data.SP };
				loadIndex--;
				if(loadIndex<=0){
					$.messager.progress('close');
				}
				$('#listTable3').datagrid('loadData',data01);
		},null,'POST',true,null,null);
	
	jAjax.submit('/production/editToSapSummaryDetail.do?&code=9999',null
			,function(data){
		rows9999 = data;
		var data01 = {success:true,total: data.length, rows: data };
		loadIndex--;
		if(loadIndex<=0){
			$.messager.progress('close');
		}
		$('#listTable4').datagrid('loadData',data01);
	},null,'POST',true,null,null);
	
	jAjax.submit('/production/editToSapSummaryDetail.do?&code=3030',null
			,function(data){
		rows3030 = data;
		var data01 = {success:true,total: data.length, rows: data };
		loadIndex--;
		if(loadIndex<=0){
			$.messager.progress('close');
		}
		$('#listTable5').datagrid('loadData',data01);
	},null,'POST',true,null,null);
}

function search9997SP(){
	$('#listTable2').datagrid('loading');
	listTable2Text = '商品猪';
	var data01 = {success:true,total: rows9997.SP.length, rows: rows9997.SP };
	$('#listTable2').datagrid('loadData',data01);
}

function search9997HB(){
	$('#listTable2').datagrid('loading');
	listTable2Text = '后备猪';
	var data01 = {success:true,total: rows9997.HB.length, rows: rows9997.HB };
	$('#listTable2').datagrid('loadData',data01);
}

function search9998SP(){
	$('#listTable2').datagrid('loading');
	listTable3Text = '商品猪';
	var data01 = {success:true,total: rows9998.SP.length, rows: rows9998.SP };
	$('#listTable2').datagrid('loadData',data01);
}

function search9998HB(){
	$('#listTable2').datagrid('loading');
	listTable3Text = '后备猪';
	var data01 = {success:true,total: rows9998.HB.length, rows: rows9998.HB };
	$('#listTable2').datagrid('loadData',data01);
}

//生成汇总数据
function editToSapSummaryDetail(){
	$.post(basicUrl + "/production/searchHanaLogSign.do",{
	},
	function(response){
		response=eval('('+response+')');
		if(response.success){
			editToSapSummary();
		}else{
			ret = true;
			$.messager.alert({
                title: '错误',
                msg: response.errorMsg
            });
		}
	}
	);
}

// 生成汇总数据
function editToSapSummary(){
//	$.messager.alert('提示','开发中！')
	// **清空信息**
	$('#listTable2').datagrid('loadData',{success:true,total: 0, rows: [] });
	$('#listTable3').datagrid('loadData',{success:true,total: 0, rows: [] });
	$('#listTable4').datagrid('loadData',{success:true,total: 0, rows: [] });
	$('#listTable5').datagrid('loadData',{success:true,total: 0, rows: [] });
	 rows9997 = null;
	 rows9998 = null;
	 rows9999 = null;
	 rows3030 = null;
	 $('tr.collect').empty();
	// **清空信息**
	$("#listTable").parent().hide();
	$('#showCommitPanel').dialog('close');
	$('#showSummeryPanel').dialog({
		height:'100%'
	});
	$('#showSummeryPanel').dialog('open');
	
	$.messager.progress();
	
	jAjax.submit('/production/editToSapSummaryDetail.do',{
		'code':10000
	}
	,function(data){
		for(var i = 0; i< data.length; i++){
			var line = i+1;
			
			$('#sapTableTr'+line).append("<td>"+data[i].lineNumber+"</td>");
			$('#sapTableTr'+line).append("<td class='tilBold'>"+data[i].typeName+"</td>");
			$('#sapTableTr'+line).append("<td>"+data[i].begQty+"</td>");
			$('#sapTableTr'+line).append("<td>"+data[i].begMoney+"</td>");
			$('#sapTableTr'+line).append("<td>"+data[i].addQty+"</td>");
			$('#sapTableTr'+line).append("<td>"+data[i].addMoney+"</td>");
			$('#sapTableTr'+line).append("<td>"+data[i].reduceQty+"</td>");
			$('#sapTableTr'+line).append("<td>"+data[i].reduceMoney+"</td>");
			$('#sapTableTr'+line).append("<td>"+data[i].endQty+"</td>");
			$('#sapTableTr'+line).append("<td>"+data[i].endMoney+"</td>");
			$('#sapTableTr'+line).append("<td>"+data[i].other+"</td>");
		}
		$.messager.progress('close');
		summaryDate = data;
	}
	,function(response){
		jAjax.errorFunc(response.errorMsg);
		$.messager.progress('close');
	},'GET',true);
}

//确定上传 - 明细新增 保存到数据库并上传至SAP
function editToSapData(){
	$.messager.progress();
	$.post(basicUrl + "/production/editInsertSendSap.do",{
		'isSummery':false
	},
	function(response){
		response=eval('('+response+')');
		$.messager.progress('close');
		if(response.success){
			dialogCancel('showCommitPanel');
			$('#table').datagrid('reload');
			$.messager.alert('提示','保存成功！');
			// **清空信息**
			$('#listTable2').datagrid('loadData',{success:true,total: 0, rows: [] });
			$('#listTable3').datagrid('loadData',{success:true,total: 0, rows: [] });
			$('#listTable4').datagrid('loadData',{success:true,total: 0, rows: [] });
			$('#listTable5').datagrid('loadData',{success:true,total: 0, rows: [] });
			 rows9997 = null;
			 rows9998 = null;
			 rows9999 = null;
			 rows3030 = null;
			 $('tr.collect').empty();
			// **清空信息**
			// 回到日志主表
			dialogCancel('showCommitPanel');
		}else{
			$.messager.alert({
              title: '错误',
              msg: response.errorMsg
          });
		}
	}
	);
}

//确定上传 - 新增汇总
function editToSapSummaryData(){
	$.messager.progress();
	if(summaryDate == null){
		$.messager.alert('提示','数据加载中！');
	}
	jAjax.submit('/production/editInsertSendSap.do',{
		'isSummery':true,
		'name':'summaryDate',
		'summaryDate':JSON.stringify(summaryDate)
	},function(response){
		if(response.success){
			dialogCancel('showSummeryPanel');
			$.messager.alert('提示','上传成功！');
			$('#table').datagrid('reload');
			$('tr.collect').empty();
			$.messager.progress('close');
			// 回到日志主表
			dialogCancel('showCommitPanel');
		}
	},function(response){
		jAjax.errorFunc(response.errorMsg);
		$.messager.progress('close');
	},'POST',false,'JSON',true);
}

// 取消
function dialogCancel(id){
	$('#'+ id).dialog('close');
	// **清空信息**
	$('#listTable2').datagrid('loadData',{success:true,total: 0, rows: [] });
	$('#listTable3').datagrid('loadData',{success:true,total: 0, rows: [] });
	$('#listTable4').datagrid('loadData',{success:true,total: 0, rows: [] });
	$('#listTable5').datagrid('loadData',{success:true,total: 0, rows: [] });
	 rows9997 = null;
	 rows9998 = null;
	 rows9999 = null;
	 rows3030 = null;
	 $('tr.collect').empty();
//	 $('tr.collect').html("");
	// **清空信息**
	$("[onClick$='editToSapData()']").show();
}

// 查看明细数据
function searchBtnView(){
	var record = $('#table').datagrid('getSelections');
	var length = record.length;
	if(length < 1){
		$.messager.alert('警告','请选择一条数据！');
	}else if(length > 1){
		$.messager.alert('警告','只能选择一条数据！');
	}else{
		var rowId=record[0].rowId;
		var isSummary="N";
		searchSendToHanaLogtDetail(rowId,isSummary);
	}
}

//查看汇总数据
function searchBtnViewSummary(){
	var record = $('#table').datagrid('getSelections');
	var length = record.length;
	if(length < 1){
		$.messager.alert('警告','请选择一条数据！');
	}else if(length > 1){
		$.messager.alert('警告','只能选择一条数据！');
	}else{
		var rowId=record[0].rowId;
		var isSummary=record[0].summary;
		searchSendToHanaLogtDetail(rowId,true);
	}
}


// 查询上传记录数据
function searchSendToHanaLogtDetail(rowId,isSummary){
	// 明细部分
	if(isSummary == 'N'){
		// **清空信息**
		$('#listTable2').datagrid('loadData',{success:true,total: 0, rows: [] });
		$('#listTable3').datagrid('loadData',{success:true,total: 0, rows: [] });
		$('#listTable4').datagrid('loadData',{success:true,total: 0, rows: [] });
		$('#listTable5').datagrid('loadData',{success:true,total: 0, rows: [] });
		 rows9997 = null;
		 rows9998 = null;
		 rows9999 = null;
		 rows3030 = null;
		 $('tr.collect').empty();
//		 $('tr.collect').html("");
		// **清空信息**
		 
		$('#showCommitPanel').dialog({
			height:'100%'
		});
		$('#showCommitPanel').dialog('open');
		$("[onClick$='editToSapData()']").hide();
		
		$.messager.progress();
		
		var closeIndex = 4;
		jAjax.submit('/production/searchSendToHanaLogtDetail.do',{
			rowId:rowId,
			code:9997
		},function(data){
//			console.log(data);
			rows9997 = data.rows;
			var data01 = {success:true,total: rows9997.SP.length, rows: rows9997.SP };
			$('#listTable2').datagrid('loadData',data01);
			listTable2Text = '商品猪';
			closeIndex--;
			if(closeIndex <= 0){
				$.messager.progress('close');
			}
		},function(response){
			jAjax.errorFunc(response.errorMsg);
			$.messager.progress('close');
		},'POST',true,'JSON',true);
		
		jAjax.submit('/production/searchSendToHanaLogtDetail.do',{
			rowId:rowId,
			code:9998
		},function(data){
//			console.log(data);
			var data01 = {success:true,total: data.rows.length, rows: data.rows };
			$('#listTable3').datagrid('loadData',data01);
			closeIndex--;
			if(closeIndex <= 0){
				$.messager.progress('close');
			}
		},function(response){
			jAjax.errorFunc(response.errorMsg);
			$.messager.progress('close');
		},'POST',true,'JSON',true);
		
		jAjax.submit('/production/searchSendToHanaLogtDetail.do',{
			rowId:rowId,
			code:9999
		},function(data){
//			console.log(data);
			var data01 = {success:true,total: data.rows.length, rows: data.rows };
			$('#listTable4').datagrid('loadData',data01);
			closeIndex--;
			if(closeIndex <= 0){
				$.messager.progress('close');
			}
		},function(response){
			jAjax.errorFunc(response.errorMsg);
			$.messager.progress('close');
		},'POST',true,'JSON',true);
		
		jAjax.submit('/production/searchSendToHanaLogtDetail.do',{
			rowId:rowId,
			code:3030
		},function(data){
//			console.log(data);
			var data01 = {success:true,total: data.rows.length, rows: data.rows };
			$('#listTable5').datagrid('loadData',data01);
			closeIndex--;
			if(closeIndex <= 0){
				$.messager.progress('close');
			}
		},function(response){
			jAjax.errorFunc(response.errorMsg);
			$.messager.progress('close');
		},'POST',true,'JSON',true);
		
		// 汇总数据
	}else{
		// **清空信息**
		$('#listTable2').datagrid('loadData',{success:true,total: 0, rows: [] });
		$('#listTable3').datagrid('loadData',{success:true,total: 0, rows: [] });
		$('#listTable4').datagrid('loadData',{success:true,total: 0, rows: [] });
		$('#listTable5').datagrid('loadData',{success:true,total: 0, rows: [] });
		rows9997 = null;
		rows9998 = null;
		rows9999 = null;
		rows3030 = null;
		 $('tr.collect').empty();
//		$('tr.collect').html("");
		// **清空信息**
		
		$("#listTable").parent().hide();
		$('#showCommitPanel').dialog('close');
		$('#showSummeryPanel').dialog({
			height:'100%'
		});
		$('#showSummeryPanel').dialog('open');
		$("[onClick$='editToSapData()']").hide();
		
		$.messager.progress();
		
		jAjax.submit('/production/searchSendToHanaLogtDetail.do',{
			rowId:rowId,
			code:10000
		}
		,function(data){
			for(var i = 0; i< data.length; i++){
				var line = i+1;
				$('#sapTableTr'+line).append("<td>"+data[i].lineNumber+"</td>");
				$('#sapTableTr'+line).append("<td class='tilBold'>"+data[i].typeName+"</td>");
				$('#sapTableTr'+line).append("<td>"+data[i].begQty+"</td>");
				$('#sapTableTr'+line).append("<td>"+data[i].begMoney+"</td>");
				$('#sapTableTr'+line).append("<td>"+data[i].addQty+"</td>");
				$('#sapTableTr'+line).append("<td>"+data[i].addMoney+"</td>");
				$('#sapTableTr'+line).append("<td>"+data[i].reduceQty+"</td>");
				$('#sapTableTr'+line).append("<td>"+data[i].reduceMoney+"</td>");
				$('#sapTableTr'+line).append("<td>"+data[i].endQty+"</td>");
				$('#sapTableTr'+line).append("<td>"+data[i].endMoney+"</td>");
				$('#sapTableTr'+line).append("<td>"+data[i].other+"</td>");
			}
			$.messager.progress('close');
			summaryDate = data;
			
		},function(response){
			jAjax.errorFunc(response.errorMsg);
			$.messager.progress('close');
		},'GET',true);
	}
}
