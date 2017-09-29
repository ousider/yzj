var model = 'cdModule';
var searchMainListUrl='/production/searchBillToPage.do';
/** *********页面渲染开始*************************************************************************/
$(document).ready(function(){
	
	/**
	 * 主表加载
	 */
	$('#table').datagrid(
			j_grid({
				toolbar:'#tableToolbar',
				url:searchMainListUrl,
				columnFields:'businessCode,eventNameChinese,billDate,createName,createDate,notes,rowId,eventCode',
				columnTitles:'编码,事件类型,单据日期,录入员,创建日期,备注,ID,事件编码',
				hiddenFields:'rowId,eventCode',
				onDblClickRowFun:searchEventDetail,
			})
	);
	
	//事件类型
	xnCdCombobox({
		id:'eventCode',
		typeCode:'EVENT_NAME_SHOW',
		editable:false,
		hasAll:true
	});
});
/*************页面渲染结束******************************************************************************/
/**
 * 查看事件
 */
function searchEventDetailByBtn(){
	var record = $('#table').datagrid('getSelections');
	var length = record.length;
	if(length < 1){
		$.messager.alert('警告','请选择一条数据！');
	}else if(length > 1){
		$.messager.alert('警告','只能选择一条数据！');
	}else{
		var index=record[0].index;
		searchEventDetail(index,record[0]);
	}
}
/**
 * 双击事件,查询设计明细
 */
function searchEventDetail(index,row){
	$.messager.progress();	
	$('#eventDetailTbale').datagrid(choiceEvent(row.eventCode, row.rowId, row.eventNameChinese));
	leftSilpFun('eventDetailId');
}

function loadSuccessFunction(data){
	if (!data.success) {
		$.messager.alert({
			title : '错误',
			msg : data.errorMsg
		});
	}
	// 防止左侧连点，右侧刷新跟不上的延迟BUG
	$.messager.progress('close');	
}
function choiceEvent(eventCode,rowId,eventNameChinese){
	// 明细撤销所用
	$('#billIdForDetail').val(rowId);
	$('#eventCodeForDetail').val(eventCode);
	$('#eventNameChineseForDetail').val(eventNameChinese);
	
	if("BILL_CANCEL"==eventCode || "PIG_EVENT_CANCEL"==eventCode || "EACH_RECORD_CANCEL"==eventCode || "GOOD_PIG_SELL"==eventCode || "PIG_MOVE_IN"==eventCode){
		$('#eventToolBar').css('display','none');
	}else{
		$('#eventToolBar').css('display','inline-block');
	}
	
	//入场
	if("PIG_MOVE_IN"==eventCode){
			return	j_grid_view({
//					singleSelect:true,
					url:'/production/searchDetailBillToList.do',
					queryParams:{eventCode:eventCode,billId:rowId},
					columnFields:'earBrand,earShort,earThorn,swineryName,houseName,materialName,pigTypeName,breedName,pigClassName,createName,createDate,workerName,enterDate,enterWeight,birthDate,birthWeight,enterParity,notes',
					columnTitles:'耳牌号,耳缺号,耳刺号,批次名称,猪舍名称,物料名字,猪类别,品种名字,猪只状态,录入员,录入时间,技术员,入场日期,入场体重,出生日期,出生体重(KG),入场胎次,备注',
					hiddenFields:'earShort,earThorn,swineryName,createName,createDate,workerName,enterDate,enterParity,birthDate,birthWeight,notes',
					columnWidths:'120,100,100,100,100,100,100,100,100,100,100,100,100,100,100,110,100,100',
					fit:false,
					fitColumns:false,
					pagination:false,
					height:'85%',
					width:'100%',
					onLoadSuccess:loadSuccessFunction
				},'eventDetailTbale')
	}
	//换耳号
	if("CHANGE_EAR_BAND"==eventCode){
		return	j_grid_view({
				singleSelect:true,
				url:'/production/searchDetailBillToList.do',
				queryParams:{eventCode:eventCode,billId:rowId},
				columnFields:'earBrand,earShort,earThorn,swineryName,earBrandNew,earShortNew,houseName,workerName,changeEarbrandDate,createName,createDate,notes',
				columnTitles:'耳牌号,耳缺号,耳刺号,批次名称,新耳牌号,新耳缺号,猪舍名称,技术员,事件时间,录入员,录入时间,备注',
				hiddenFields:'earThorn,createName,createDate,notes',
				columnWidths:'120,120,100,100,110,110,80,100,100,100,100,100',
				fit:false,
				fitColumns:false,
				pagination:false,
				height:'85%',
				width:'100%',
				onLoadSuccess:loadSuccessFunction
			},'eventDetailTbale')
	}
	//背膘测定
	if("BACKFAT"==eventCode){
		return	j_grid_view({
				singleSelect:true,
				url:'/production/searchDetailBillToList.do',
				queryParams:{eventCode:eventCode,billId:rowId},
				columnFields:'earBrand,swineryName,materialName,houseName,pigpenName,workerName,backfatDate,createName,createDate,backfat,score,notes',
				columnTitles:'耳牌号,批次名称,物料名称,猪舍名称,栏号,技术员,事件时间,录入员,录入时间,背膘,评分,备注',
				hiddenFields:'swineryName,materialName,pigpenName,createName,createDate,notes',
				columnWidths:'120,100,100,100,100,100,100,100,100,100,100,100',
				fit:false,
				fitColumns:false,
				pagination:false,
				height:'85%',
				width:'100%',
				onLoadSuccess:loadSuccessFunction
			},'eventDetailTbale')
	}
	//后备转生产公猪 或者 后备情期鉴定
	if("BOAR_RESERVE_TO_PRODUCT"==eventCode||"PREPUBERTAL_CHECK"==eventCode){
		return	j_grid_view({
				singleSelect:true,
				url:'/production/searchDetailBillToList.do',
				queryParams:{eventCode:eventCode,billId:rowId},
				columnFields:'earBrand,swineryName,materialName,houseName,pigpenName,workerName,productDate,earBrandNew,createName,createDate,notes',
				columnTitles:'耳牌号,批次名称,物料名称,猪舍名称,栏号,技术员,事件时间,新耳牌号,录入员,录入时间,备注',
				hiddenFields:'pigpenName,createName,createDate,notes',
				columnWidths:'120,100,100,100,100,90,100,120,100,100,100',
				fit:false,
				fitColumns:false,
				pagination:false,
				height:'85%',
				width:'100%',
				onLoadSuccess:loadSuccessFunction
			},'eventDetailTbale')
	}
	
	//采精
	if("SEMEN"==eventCode){
		return	j_grid_view({
				singleSelect:true,
				url:'/production/searchDetailBillToList.do',
				queryParams:{eventCode:eventCode,billId:rowId},
				columnFields:'earBrand,swineryName,materialName,houseName,pigpenName,workerName,semenDate,createName,createDate,semenBatchNo,semenColorName,semenOdourName,cohesionName,semenQty,anhNum,spermMotility,spermDensity,abnormationRate,spec,notes',
				columnTitles:'耳牌号,批次名称,物料名称,猪舍名称,栏号,技术员,事件时间,录入员,录入时间,精液批号,精液颜色,精液气味,精液凝聚度,精液量(ML),稀释份数,精子活力(%),精子密度,畸形率(%),规格(ML),备注',
				hiddenFields:'swineryName,materialName,pigpenName,createName,createDate,semenColorName,semenOdourName,cohesionName,spermMotility,spermDensity,abnormationRate,notes',
				columnWidths:'120,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100',
				fit:false,
				fitColumns:false,
				pagination:false,
				height:'85%',
				width:'100%',
				onLoadSuccess:loadSuccessFunction
			},'eventDetailTbale')
	}
	
	//精液检查 TODO
	if("SEMEN_CHECK"==eventCode){
		
	}
	
	//转产房 
	if("CHANGE_LABOR_HOUSE"==eventCode){
		return	j_grid_view({
				singleSelect:true,
				url:'/production/searchDetailBillToList.do',
				queryParams:{eventCode:eventCode,billId:rowId},
				columnFields:'earBrand,swineryName,materialName,changeTypeName,houseName,pigpenName,backfat,score,workerName,changeHouseDate,createName,createDate,notes',
				columnTitles:'耳牌号,批次名称,物料名称,转舍类型,转入猪舍,转入栏号,背膘,评分,技术员,事件时间,录入员,录入时间,备注',
				hiddenFields:'swineryName,materialName,createName,createDate,notes',
				columnWidths:'120,100,100,100,100,100,100,100,100,100,100,100,100',
				fit:false,
				fitColumns:false,
				pagination:false,
				height:'85%',
				width:'100%',
				onLoadSuccess:loadSuccessFunction
			},'eventDetailTbale')
	}
	
	//种猪转舍
	if("BREED_PIG_CHANGE_HOUSE"==eventCode){
		return	j_grid_view({
				singleSelect:true,
				url:'/production/searchDetailBillToList.do',
				queryParams:{eventCode:eventCode,billId:rowId},
				columnFields:'earBrand,houseName,pigpenName,workerName,changeHouseDate,createName,createDate,notes',
				columnTitles:'耳牌号,转入猪舍,转入栏号,技术员,事件时间,录入员,录入时间,备注',
				hiddenFields:'createName,createDate',
				columnWidths:'120,120,150,100,100,100,100,130',
				fit:false,
				fitColumns:false,
				pagination:false,
				height:'85%',
				width:'100%',
				onLoadSuccess:loadSuccessFunction
			},'eventDetailTbale')
	}
	
	//配种
	if("BREED"==eventCode){
		return	j_grid_view({
				singleSelect:true,
				url:'/production/searchDetailBillToList.do',
				queryParams:{eventCode:eventCode,billId:rowId},
				columnFields:'earBrand,swineryName,materialName,houseName,pigpenName,breedTypeName,breedDateFirst,breedBoarFirst,breedDateSecond,breedBoarSecond,breedDateThird,breedBoarThird,workerName,createName,createDate,notes',
				columnTitles:'耳牌号,批次名称,物料名称,猪舍名称,栏号,配种类型,一配时间,一配公猪,二配时间,二配公猪,三配时间,三配公猪,技术员,录入员,录入时间,备注',
				hiddenFields:'swineryName,materialName,pigpenName,breedDateSecond,breedBoarSecond,breedDateThird,breedBoarThird,createName,createDate',
				columnWidths:'120,100,100,100,100,100,100,130,100,100,100,100,100,100,100,140',
				fit:false,
				fitColumns:false,
				pagination:false,
				height:'85%',
				width:'100%',
				onLoadSuccess:loadSuccessFunction
			},'eventDetailTbale')
	}
	
	//妊娠检查 流产
	if("PREGNANCY_CHECK"==eventCode||"MISSCARRY"==eventCode){
		return	j_grid_view({
				singleSelect:true,
				url:'/production/searchDetailBillToList.do',
				queryParams:{eventCode:eventCode,billId:rowId},
				columnFields:'earBrand,swineryName,materialName,houseName,pigpenName,pregnancyTypeName,pregnancyResultName,workerName,pregnancyDate,createName,createDate,proNo,notes',
				columnTitles:'耳牌号,批次名称,物料名称,猪舍名称,栏号,检查方式,检查结果,技术员,事件时间,录入员,录入时间,生产编号,备注',
				hiddenFields:'pigpenName,createName,createDate,proNo,notes',
				columnWidths:'120,100,100,100,100,100,100,100,100,100,100,100,100',
				fit:false,
				fitColumns:false,
				pagination:false,
				height:'85%',
				width:'100%',
				onLoadSuccess:loadSuccessFunction
			},'eventDetailTbale')
	}
	
	//分娩 
	if("DELIVERY"==eventCode){
		return	j_grid_view({
				singleSelect:true,
				url:'/production/searchDetailBillToList.do',
				queryParams:{eventCode:eventCode,billId:rowId},
				columnFields:'earBrand,swineryName,materialName,houseName,pigpenName,deliveryTypeName,workerName,deliveryDate,aliveLitterY,aliveLitterX,aliveLitterWeight,labor,healthyNum,weakNum,stillbirthNum,mutantNum,mummyNum,blackNum,houseNum,createName,createDate,proNo,notes',
				columnTitles:'耳牌号,批次名称,物料名称,猪舍名称,栏号,分娩方式,技术员,事件时间,活仔公(头),活仔母(头),活仔窝重(KG),产程(H),健仔数,弱仔数,死胎,畸形,木乃伊,黑胎,出生窝号,录入员,录入时间,生产编号,备注',
				hiddenFields:'swineryName,materialName,pigpenName,createName,createDate,proNo,notes,weakNum,stillbirthNum,mutantNum,mummyNum,blackNum,houseNum',
				columnWidths:'120,100,100,100,100,100,100,100,100,100,110,100,100,100,100,100,100,100,100,100,100,100,100',
				fit:false,
				fitColumns:false,
				pagination:false,
				height:'85%',
				width:'100%',
				onLoadSuccess:loadSuccessFunction
			},'eventDetailTbale')
	}
	
	//寄养 
	if("FOSTER"==eventCode){
		return	j_grid_view({
				singleSelect:true,
				url:'/production/searchDetailBillToList.do',
				queryParams:{eventCode:eventCode,billId:rowId},
				columnFields:'earBrand,swineryName,materialName,houseName,pigpenName,parity,pigTypeName,sexName,fosterQty,fosterWeight,fosterReasonName,earBrandFoster,workerName,fosterDate,createName,createDate,notes',
				columnTitles:'耳牌号,批次名称,物料名称,猪舍名称,栏号,胎次,猪只类型,性别,寄养数量(头),寄养体重(KG),寄养原因,代养母猪,技术员,事件时间,录入员,录入时间,备注',
				hiddenFields:'swineryName,materialName,parity,pigpenName,pigTypeName,sexName,createName,createDate,notes',
				columnWidths:'120,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100',
				fit:false,
				fitColumns:false,
				pagination:false,
				height:'85%',
				width:'100%',
				onLoadSuccess:loadSuccessFunction
			},'eventDetailTbale')
	}
	
	//断奶
	if("WEAN"==eventCode){
		return	j_grid_view({
				singleSelect:true,
				url:'/production/searchDetailBillToList.do',
				queryParams:{eventCode:eventCode,billId:rowId},
				columnFields:'earBrand,swineryName,materialName,houseName,pigpenName,parity,weanNum,weanWeight,backfat,score,inHouseName,inPigpenName,leaveQty,workerName,weanDate,createName,createDate,notes',
				columnTitles:'耳牌号,批次名称,物料名称,猪舍名称,栏号,胎次,断奶仔猪(头),断奶窝重(KG),背膘,评分,转入猪舍,转入猪栏,死亡仔猪(头),技术员,事件时间,录入员,录入时间,备注',
				hiddenFields:'swineryName,materialName,parity,pigpenName,backfat,score,inHouseName,inPigpenName,createName,createDate',
				columnWidths:'120,100,100,100,100,100,100,110,110,100,100,100,100,100,100,100,100,100',
				fit:false,
				fitColumns:false,
				pagination:false,
				height:'85%',
				width:'100%',
				onLoadSuccess:loadSuccessFunction
			},'eventDetailTbale')
	}
	
	 // 种猪淘汰申请 
	if("BREED_PIG_OBSOLETE"==eventCode){
		return	j_grid_view({
				singleSelect:true,
				url:'/production/searchDetailBillToList.do',
				queryParams:{eventCode:eventCode,billId:rowId},
				columnFields:'earBrand,statusName,swineryName,houseName,pigpenName,pigTypeName,sexName,obsoleteReasonName,parity,workerName,obsoleteDate,createName,createDate,notes',
				columnTitles:'耳牌号,状态,批次名称,猪舍名称,栏号,猪只类型,猪只性别,淘汰原因,胎次,技术员,事件时间,录入员,录入时间,备注',
				hiddenFields:'swineryName,parity,pigpenName,sexName,createName,createDate,notes',
				columnWidths:'120,100,100,100,100,100,100,100,100,100,100,100,100,100',
				fit:false,
				fitColumns:false,
				pagination:false,
				height:'85%',
				width:'100%',
				onLoadSuccess:loadSuccessFunction
			},'eventDetailTbale')
	}
	
	 //  种猪死亡
	if("BREED_PIG_DIE"==eventCode){
		return	j_grid_view({
				singleSelect:true,
				url:'/production/searchDetailBillToList.do',
				queryParams:{eventCode:eventCode,billId:rowId},
				columnFields:'earBrand,swineryName,materialName,houseName,pigpenName,pigTypeName,sexName,leaveWeight,dieReasonName,parity,workerName,leaveDate,createName,createDate,notes',
				columnTitles:'耳牌号,批次名称,物料名称,猪舍名称,栏号,猪只类型,猪只性别,死亡体重(KG),死亡原因,胎次,技术员,事件时间,录入员,录入时间,备注',
				hiddenFields:'swineryName,materialName,parity,pigpenName,sexName,createName,createDate,notes',
				columnWidths:'120,100,100,100,100,100,100,100,100,100,100,100,100,100,100',
				fit:false,
				fitColumns:false,
				pagination:false,
				height:'85%',
				width:'100%',
				onLoadSuccess:loadSuccessFunction
			},'eventDetailTbale')
	}
	
	//仔猪死亡 
	if("CHILD_PIG_DIE"==eventCode){
		return	j_grid_view({
				singleSelect:true,
				url:'/production/searchDetailBillToList.do',
				queryParams:{eventCode:eventCode,billId:rowId},
				columnFields:'earBrand,swineryName,materialName,houseName,pigpenName,pigTypeName,sexName,leaveQty,leaveWeight,leaveReasonName,parity,workerName,leaveDate,createName,createDate,notes',
				columnTitles:'耳牌号,批次名称,物料名称,猪舍名称,栏号,猪只类型,猪只性别,死亡数量(头),死亡总重(KG),死亡原因,胎次,技术员,事件时间,录入员,录入时间,备注',
				hiddenFields:'swineryName,materialName,parity,pigpenName,sexName,createName,createDate,notes',
				columnWidths:'120,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100',
				fit:false,
				fitColumns:false,
				pagination:false,
				height:'85%',
				width:'100%',
				onLoadSuccess:loadSuccessFunction
			},'eventDetailTbale')
	}
	
	//转保育 
	if("TO_CHILD_CARE"==eventCode || "TO_CHILD_FATTEN"==eventCode){
		return	j_grid_view({
				singleSelect:true,
				url:'/production/searchDetailBillToList.do',
				queryParams:{eventCode:eventCode,billId:rowId},
				columnFields:'swineryName,houseName,pigpenName,changeHouseTypeName,inHouseName,inPigpenName,childQty,childWeight,workerName,childDate,createName,createDate,notes',
				columnTitles:'批次名称,转出猪舍,转出栏号,转舍类型,转入猪舍,转入猪栏,数量(头),体重(KG),技术员,事件时间,录入员,录入时间,备注',
				hiddenFields:'pigpenName,inPigpenName,createName,createDate,notes',
				columnWidths:'100,100,100,100,100,100,100,100,100,100,100,100,100',
				fit:false,
				fitColumns:false,
				pagination:false,
				height:'85%',
				width:'100%',
				onLoadSuccess:loadSuccessFunction
			},'eventDetailTbale')
	}
	
	//商品猪死亡 
	if("GOOD_PIG_DIE"==eventCode){
		return	j_grid_view({
				singleSelect:true,
				url:'/production/searchDetailBillToList.do',
				queryParams:{eventCode:eventCode,billId:rowId},
				columnFields:'swineryName,houseName,pigpenName,leaveQty,leaveWeight,leaveReasonName,parity,workerName,leaveDate,createName,createDate,notes',
				columnTitles:'批次名称,猪舍名称,栏号,死亡数量(头),死亡总重(KG),死亡原因,胎次,技术员,事件时间,录入员,录入时间,备注',
				hiddenFields:'pigpenName,createName,createDate,notes',
				columnWidths:'100,100,100,100,100,100,100,100,100,100,100,100',
				fit:false,
				fitColumns:false,
				pagination:false,
				height:'85%',
				width:'100%',
				onLoadSuccess:loadSuccessFunction
			},'eventDetailTbale')
	}
	
	// 猪只销售
	if("GOOD_PIG_SELL"==eventCode){
		return	j_grid_view({
				haveCheckBox:false,
				url:'/production/searchDetailBillToList.do',
				queryParams:{eventCode:eventCode,billId:rowId},
				columnFields:'saleName,swineryName,houseName,pigpenName,totalPrice,saleNum,totalWeight,leaveReasonName,customerName,saleBillTypeName,workerName,saleDate,createName,createDate,notes',
				columnTitles:'销售品名,批次名称,猪舍名称,栏号,销售金额(元),销售数量(头),销售重量(KG),销售类型,客户名称,销售单类型,技术员,事件时间,录入员,录入时间,备注',
				hiddenFields:'pigpenName,createName,totalWeight,createDate,notes',
				columnWidths:'100,100,100,100,100,100,100,100,100,100,100,100,100,100,100',
				fit:false,
				fitColumns:false,
				pagination:false,
				height:'93%',
				width:'100%',
				onLoadSuccess:loadSuccessFunction
			},'eventDetailTbale')
	}
	
	// 奶妈转舍
	if("NURSE_CHANGE_HOUSE"==eventCode){
		return j_grid_view({
				singleSelect:true,
				url:'/production/searchDetailBillToList.do',
				queryParams:{eventCode:eventCode,billId:rowId},
				columnFields:'earBrand,inHouseName,inPigpenName,weanNum,weanWeight,leaveQty,fosterNum,boardSowEarBrand,inPigEarBrand,inPigHouseName,inPigPigpenName,workerName,changeHouseDate,createName,createDate,notes',
				columnTitles:'耳牌号,奶妈转入猪舍,奶妈转入栏号,断奶数量(头),断奶窝重(KG),死亡数量,寄养数量,寄养母猪,原栏位母猪,原转入猪舍,原转入猪栏,技术员,事件时间,录入员,录入时间,备注',
				hiddenFields:'createName,createDate,notes',
				columnWidths:'100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100',
				fit:false,
				fitColumns:false,
				pagination:false,
				height:'85%',
				width:'100%',
				onLoadSuccess:loadSuccessFunction
			},'eventDetailTable')
	}
	
	// 留种
	if("SEED_PIG"==eventCode){
		return	j_grid_view({
				haveCheckBox:false,
				url:'/production/searchDetailBillToList.do',
				queryParams:{eventCode:eventCode,billId:rowId},
				columnFields:'pigId,earBrand,houseName,pigpenName,seedMale,seedFemale,seedDate,createDate,workerName,notes',
				columnTitles:'猪只ID,耳牌号,猪舍名称,猪栏,留种公,留种母,事件日期,录入时间,技术员,备注',
				hiddenFields:'pigId,notes,workerName',
				columnWidths:'100,100,100,100,100,100,100,100,100,100',
				fit:false,
				fitColumns:false,
				pagination:false,
				height:'93%',
				width:'100%',
				onLoadSuccess:loadSuccessFunction
			},'eventDetailTbale')
	}
	
	// 选种
	if("SELECT_BREED_PIG"==eventCode){
		return	j_grid_view({
				singleSelect:true,
				url:'/production/searchDetailBillToList.do',
				queryParams:{eventCode:eventCode,billId:rowId},
				columnFields:'pigId,lastMaterialId,earShort,selectBreedTypeName,earBrand,sexName,inHouseIdName,inPigpenIdName,seletcDate,createDate,workerName,notes',
				columnTitles:'猪只ID,物料id,耳缺号,选种类型,耳牌号,性别,转入猪舍,转入猪栏,事件日期,录入时间,技术员,备注',
				hiddenFields:'pigId,lastMaterialId,notes,workerName',
				columnWidths:'100,100,100,100,100,80,100,80,100,100,100,100',
				fit:false,
				fitColumns:false,
				pagination:false,
				height:'93%',
				width:'100%',
				onLoadSuccess:loadSuccessFunction
			},'eventDetailTbale')
	}
	
	// 肉猪盘点
	if("PORK_PIG_CHECK"==eventCode){
		return	j_grid_view({
				haveCheckBox:false,
				url:'/production/searchDetailBillToList.do',
				queryParams:{eventCode:eventCode,billId:rowId},
				columnFields:'checkQty,accountQty,avgWeight,totalWeight,houseName,swineryName,billDate,createDate,workerName,notes',
				columnTitles:'盘点头数,账上头数,均重,总重,猪舍名称,批次名称,事件日期,录入时间,技术员,备注',
				hiddenFields:'notes',
				columnWidths:'100,100,100,100,100,80,100,80,100,100',
				fit:false,
				fitColumns:false,
				pagination:false,
				height:'93%',
				width:'100%',
				onLoadSuccess:loadSuccessFunction
			},'eventDetailTbale')
	}
	
	// 精液入库
	if("SEMEN_INQUIRY"==eventCode){
		return	j_grid_view({
				//haveCheckBox:false,
				singleSelect:true,
				url:'/production/searchDetailBillToList.do',
				queryParams:{eventCode:eventCode,billId:rowId},
				columnFields:'semenBatchNo,earBrand,materialIdName,anhNum,warehouseName,validDate,inputDate,workerName,notes',
				columnTitles:'精液批号,公猪耳号,精液主数据,数量,仓库,有效期,入库日期,技术员,备注',
				hiddenFields:'notes',
				columnWidths:'100,100,100,100,100,100,100,100,100',
				fit:false,
				fitColumns:false,
				pagination:false,
				height:'93%',
				width:'100%',
				onLoadSuccess:loadSuccessFunction
			},'eventDetailTbale')
	}
	
	// 精液销售
	if("SALE_SEMEN"==eventCode){
		return	j_grid_view({
				singleSelect:true,
				url:'/production/searchDetailBillToList.do',
				queryParams:{eventCode:eventCode,billId:rowId},
				columnFields:'semenBatchNo,earBrand,validDate,warehouseName,saleNum,saleDate,workerName,notes',
				columnTitles:'精液批号,公猪耳号,有效日期,仓库,销售数量,销售日期,技术员,备注',
				hiddenFields:'notes',
				columnWidths:'100,100,100,100,100,100,100,100',
				fit:false,
				fitColumns:false,
				pagination:false,
				height:'93%',
				width:'100%',
				onLoadSuccess:loadSuccessFunction
			},'eventDetailTbale')
	}
	
	//单据撤销
	if("BILL_CANCEL"==eventCode){
    	var result = jAjax.submit('/production/searchWhichEventCancel.do',{billId:rowId});
    	var oldEventCode = result[0].eventCode;
    	var oldBillId = result[0].billId;
    	var columnFields = '';
    	var columnTitles = '';
    	var columnWidths = '';
    	var hiddenFields = '';
    	if(oldEventCode != "TO_CHILD_CARE" && oldEventCode != "TO_CHILD_FATTEN" && oldEventCode != "GOOD_PIG_DIE" && oldEventCode != "GOOD_PIG_SELL"
    		&& oldEventCode != "SALE_SEMEN" && oldEventCode != "SEMEN_INQUIRY"){
    		columnFields = 'earBrand,businessCode,eventName,employeeName';
    		columnTitles = '耳牌号,撤销的编码,撤销的事件类型,操作人员';
    		columnWidths = '170,180,200,200';
    	}else if(oldEventCode == "GOOD_PIG_SELL"){
    		columnFields = 'saleName,swineryName,totalPrice,saleNum,businessCode,eventName,employeeName';
    		columnTitles = '销售品名,批次名称,销售金额(元),销售数量(头),撤销的编码,撤销的事件类型,操作人员';
    		columnWidths = '100,170,100,100,180,200,100';
    	}else if(oldEventCode == "SALE_SEMEN"){
    		columnFields = 'eventName,semenBatchNo,earBrand,validDate,warehouseName,saleNum,saleDate,workerName,notes';
			columnTitles = '撤销的事件类型,精液批号,公猪耳号,有效日期,仓库,销售数量,销售日期,技术员,备注';
    		hiddenFields = 'notes';
			columnWidths = '100,100,100,100,100,100,100,100,100';
    	}else if(oldEventCode == "SEMEN_INQUIRY"){
			columnFields = 'eventName,semenBatchNo,materialIdName,earBrand,anhNum,warehouseName,validDate,inputDate,workerName,notes';
			columnTitles = '撤销的事件类型,精液批号,精液主数据,公猪耳号,数量,仓库,有效期,入库日期,技术员,备注';
    		hiddenFields = 'notes';
			columnWidths = '100,100,100,100,100,100,100,100,100,100';
    	}else{
    		columnFields = 'swineryName,pigQty,businessCode,eventName,employeeName';
    		columnTitles = '批次名称,数量,撤销的编码,撤销的事件类型,操作人员';
    		columnWidths = '170,100,180,200,100';
    	}
    	
		return	j_grid_view({
				haveCheckBox:false,
				url:'/production/searchDetailBillToList.do',
				queryParams:{eventCode:eventCode,billId:oldBillId,oldEventCode:oldEventCode,cancelBillId:rowId},
				columnFields:columnFields,
				columnTitles:columnTitles,
				hiddenFields:hiddenFields,
				columnWidths:columnWidths,
				fit:false,
				fitColumns:false,
				pagination:false,
				height:'93%',
				width:'100%',
				onLoadSuccess:loadSuccessFunction
			},'eventDetailTbale')
	}
	
	//猪只事件撤销
	if("PIG_EVENT_CANCEL"==eventCode){
    	var result = jAjax.submit('/production/searchWhichEventCancel.do',{billId:rowId});
    	var oldEventCode = result[0].eventCode;
    	var oldBillId = result[0].billId;
		return	j_grid_view({
				haveCheckBox:false,
				url:'/production/searchDetailBillToList.do',
				queryParams:{eventCode:eventCode,billId:oldBillId,oldEventCode:oldEventCode,cancelBillId:rowId},
				columnFields:'earBrand,businessCode,eventName,employeeName',
				columnTitles:'耳牌号,撤销的编码,撤销的事件类型,操作人员',
				hiddenFields:'',
				columnWidths:'170,180,200,200',
				fit:false,
				fitColumns:false,
				pagination:false,
				height:'93%',
				width:'100%',
				onLoadSuccess:loadSuccessFunction
			},'eventDetailTbale')
	}
	
	//单条事件撤销
	if("EACH_RECORD_CANCEL"==eventCode){
    	var result = jAjax.submit('/production/searchWhichEventCancel.do',{billId:rowId});
    	var oldEventCode = result[0].eventCode;
    	var oldBillId = result[0].billId;
    	var columnFields = '';
    	var columnTitles = '';
    	var columnWidths = '';
    	var hiddenFields = '';
    	if(oldEventCode == "SALE_SEMEN"){
    		columnFields = 'eventName,semenBatchNo,earBrand,validDate,warehouseName,saleNum,saleDate,workerName,notes';
			columnTitles = '撤销的事件类型,精液批号,公猪耳号,有效日期,仓库,销售数量,销售日期,技术员,备注';
			hiddenFields = 'notes';
			columnWidths = '100,100,100,100,100,100,100,100,100';
    	}else if(oldEventCode == "SEMEN_INQUIRY"){
			columnFields = 'eventName,semenBatchNo,materialIdName,earBrand,anhNum,warehouseName,validDate,inputDate,workerName,notes';
			columnTitles = '撤销的事件类型,精液批号,精液主数据,公猪耳号,数量,仓库,有效期,入库日期,技术员,备注';
			hiddenFields = 'notes';
			columnWidths = '100,100,100,100,100,100,100,100,100,100';
    	}else{
    		columnFields = 'swineryName,businessCode,eventName,employeeName';
    		columnTitles = '批次名称,撤销的编码,撤销的事件类型,操作人员';
    		columnWidths = '170,180,200,200';
    	}
    	
		return	j_grid_view({
				haveCheckBox:false,
				url:'/production/searchDetailBillToList.do',
				queryParams:{eventCode:eventCode,billId:oldBillId,oldEventCode:oldEventCode,cancelBillId:rowId},
				columnFields:columnFields,
				columnTitles:columnTitles,
				hiddenFields:hiddenFields,
				columnWidths:columnWidths,
				fit:false,
				fitColumns:false,
				pagination:false,
				height:'93%',
				width:'100%',
				onLoadSuccess:loadSuccessFunction
			},'eventDetailTbale')
	}
	
}

function editEventCancelByBtn(tableId){
	var record = $('#'+tableId).datagrid('getSelections');
	var length = record.length;
	if(length < 1){
		$.messager.alert('警告','请选择一条数据！');
	}else if(length > 1){
		$.messager.alert('警告','只能选择一条数据！');
	}else{
		$.messager.confirm('提示','确定要撤销这个事件?',function(r){
		    if (r){
		    	$.messager.progress();	
		    	var data;
		    	if(tableId=='table'){
		    		data ={billId:record[0].rowId,
		    				eventCode:record[0].eventCode,
		    				eventNameChinese:record[0].eventNameChinese
		    				}
		    	}else if(tableId=='eventDetailTbale'){
		    		data ={billId:$('#billIdForDetail').val(),
		    				eventCode:$('#eventCodeForDetail').val(),
		    				eventNameChinese:$('#eventNameChineseForDetail').val(),
		    				pigId:record[0].pigId,
		    				inPigId:record[0].inPigId,
		    				pigIds:record[0].pigIds,
		    				semenId:record[0].semenId,
		    				lineNumber:record[0].lineNumber
		    				}
		    	}
		    	
		    	var url = "/cancel/editEventCancel.do";
		    	jAjax.submit(url,data,function(response){
		    		$('#table').datagrid('reload');
		    		if(tableId=='table'){
		    			// 当删除单据时，如果明显打开的就是该单据的明细，那么明细菜单隐藏
		    			if($('#billIdForDetail').val() == record[0].rowId){
		    				rightSlipFun('eventDetailId',780);
		    			}
		    		}else if(tableId=='eventDetailTbale'){
		    			// 当删除明细，明细是最后一条时，将明细菜单隐藏
		    			$('#eventDetailTbale').datagrid('reload');
		    			$('#eventDetailTbale').datagrid({
		    				onLoadSuccess:function(data){
		    					if(data.rows.length <=0){
				    				rightSlipFun('eventDetailId',780);
				    			}
		    				}
		    			});
		    		}
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
