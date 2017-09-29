var eventCode = 'BREED_PIG_OBSOLETE';
var searchMainListUrl='/production/searchBoarObsolete.do';

$(document).ready(function(){
	
	
	$('#table').datagrid(
			j_grid({
				url:searchMainListUrl,
				columnFields:'rowId,earBrand,earShort,breedName,pigClassName,birthDate,parity,houseName,pigpenName,obsoleteDate,obsoleteReason,workerName,notes',
				columnTitles:'ID,耳牌号,耳缺号,品种,状态,出生日期,胎次,猪舍,猪栏,预淘汰日期,淘汰原因,技术员,备注',
				hiddenFields:'rowId'
			})
	);
	

	xnCombobox({
		id:"businessCode",
		url:'/production/searchObsoleteBill.do?eventCode='+eventCode,
		valueField:"rowId",
		textField:"businessCode",
		onChange:searchBoarObsolete
	});
	
	function searchBoarObsolete(newValue,oldValue){
		if(newValue == ''){
			return;
		}
		$('#table').datagrid({
			url:basicUrl+searchMainListUrl+"?billId="+newValue,
		});
	}
});

// 审批通过
	function pass(){
		$.messager.progress();
		$('#passBtn').attr('disabled',true).addClass('btn-disabled');
		var rows = $('#table').datagrid('getSelections');
		var ids = [];
		var pigIds = [];
		$.each(rows,function(index,data){
			if(data.rowId != -1){
				ids.push(data.rowId);
			}
			if(data.pigId != -1){
				pigIds.push(data.pigId);
			}
		});
		
		$.ajax({
		    type: 'POST',
		    url:basicUrl + "/production/editBoarObsolete.do?status=2&billId="+$('#businessCode').combobox('getValue')+"&enterDate="+$('#enterDate').combobox('getValue'),
		    data:{
			ids:ids,
			pigIds:pigIds
			
		    },
		    success: function(response){
//			response=eval('('+response+')');
			if(response.success){
				$('#table').datagrid('reload');
				judge();
				$.messager.alert('提示','操作成功！');
			}else{
				$.messager.alert({
                    title: '错误',
                    msg: response.errorMsg
                });
			}
			$('#passBtn').attr('disabled',false).removeClass('btn-disabled');
			$.messager.progress('close');
		},
		    dataType: "json"
		  });

	}
	
	// 退回
	function notPass(){
		$.messager.progress();
		$('#notPassBtn').attr('disabled',true).addClass('btn-disabled');
		var rows = $('#table').datagrid('getSelections');
		var ids = [];
		$.each(rows,function(index,data){
			if(data.rowId != -1){
				ids.push(data.rowId);
			}
		});
		
		$.post({
			url:basicUrl + "/production/editBoarObsolete.do?status=3&billId="+$('#businessCode').combobox('getValue')+"&enterDate="+$('#enterDate').combobox('getValue'),
			data:{
				ids:ids,
			},
			success:function(response){
				response=eval('('+response+')');
				if(response.success){
					$('#table').datagrid('reload');
					judge();
					$.messager.alert('提示','操作成功！');
				}else{
					$.messager.alert({
	                    title: '错误',
	                    msg: response.errorMsg
	                });
				}
				$('#notPassBtn').attr('disabled',false).removeClass('btn-disabled');
				$.messager.progress('close');
			}
		});

	}
	
	function judge(){
		var rows = $('#table').datagrid("getRows");
		var selectRows = $('#table').datagrid("getSelections");
		if(rows.length==selectRows.length && $('#businessCode').combobox('getValue')!=''){
			$('#businessCode').combobox('setValue','')
			addFocus('businessCode','combobox','/production/searchObsoleteBill.do?eventCode='+eventCode);
		}
	}