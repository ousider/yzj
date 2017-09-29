<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<link href="${base_url}/lib/jquery-easyui-1.4.5/themes/default/easyui.css?v=${webVersion}" rel="stylesheet">
	<link href="${base_url}/lib/jquery-easyui-1.4.5/themes/icon.css?v=${webVersion}" rel="stylesheet">
</head>
<body>
	<div style="display:none;">
		<input type="hidden" id="farmId" name="farmId" value="${userDetail.farmId}"/>
		<input type="hidden" id="farmName" name="farmName" value="${userDetail.farmName}"/>
		<input type="hidden" id="employName" name="farmId" value="${userDetail.employName}"/>
		<input type="hidden" id="finereport_url" name="finereport_url" value="${finereport_url}"/>
		<input type="hidden" id="finereport_username" name="finereport_username" value="${finereport_username}"/>
	</div>
	<ul class="verticalNav fl" id="verticalNav">
	    <li class="selected" data-href="SCCS"><span class="solid-arrow"></span>生产参数</li>
	    <li data-href="ZHZB_CLGM"><span class="point"></span>综合指标</li>
	    <li data-href="ZHZB_CLGM"><span class="point"></span>存栏规模</li>
	    <li data-href="SCTX"><span class="point"></span>生产提醒</li>
	    <li data-href="SCFX"><span class="point"></span>生产分析</li>
	    <li data-href="LSZZ_SWFX"><span class="point"></span>栏舍周转</li>
	    <li data-href="LSZZ_SWFX"><span class="point"></span>死亡分析</li>
	    <li data-href="MZQLT"><span class="point"></span>母猪蓝图</li>
	    <li data-href="TCFB"><span class="point"></span>胎次分布</li>
	</ul>
	<div class="productionPara fr">
		<div id="SCCS" class="mapRow fl">
			<div class="commonMap amp-width-100 fl">
				<div class="mapTitle fl">
					<strong>生产参数</strong>
				</div>
				<div class="fr">
					<span onclick="reloadAll()" class="closeOrOpen">刷新</span>
					<!-- <span onclick="productionParamAddData()" class="closeOrOpen">刷新</span> -->
					<span onclick="closeOrOpen(this)" class="closeOrOpen showPanel">展开</span>
				</div>
				<div class="widgetGroup fl">
					<div class="wd-12 fl">
						<div class="wd-com wd-2 text-center">
							<span id="baseSow" class="sildeValue-span"></span>
							<div class="canHide" style="display:none;">
								<input id="slider_baseSow" style="height:100px;">
							</div>
							<span class="silder-span">基础母猪</span>
						</div>
						<div class="wd-com wd-2 text-center">
							<span id="yearParity" class="sildeValue-span"></span>
							<div class="canHide" style="display:none;">
								<input id="slider_yearParity" style="width:100%;height:100px">
							</div>
							<span class="silder-span">年产胎次</span>
						</div>
						<div class="wd-com wd-2 text-center">
							<span id="deliveryRate" class="sildeValue-span"></span>
							<div class="canHide" style="display:none;">
								<input id="slider_deliveryRate" style="width:100%;height:100px">
							</div>
							<span class="silder-span">分娩率(%)</span>
						</div>
						<div class="wd-com wd-2 text-center">
							<span id="liveNum" class="sildeValue-span"></span>
							<div class="canHide" style="display:none;">
								<input id="slider_liveNum" style="width:100%;height:100px">
							</div>
							<span class="silder-span">每胎产仔活数</span>
						</div>
						<div class="wd-com wd-2 text-center">
							<span id="pigletsLiveRate" class="sildeValue-span"></span>
							<div class="canHide" style="display:none;">
								<input id="slider_pigletsLiveRate" style="width:100%;height:100px">
							</div>
							<span class="silder-span">哺乳期成活率(%)</span>
						</div>
						<div class="wd-com wd-2 text-center">
							<span id="childCareLiveRate" class="sildeValue-span"></span>
							<div class="canHide" style="display:none;">
								<input id="slider_childCareLiveRate" style="width:100%;height:100px">
							</div>
							<span class="silder-span">保育期成活率(%)</span>
						</div>
						<div class="wd-com wd-2 text-center">
							<span id="fattenLiveRate" class="sildeValue-span"></span>
							<div class="canHide" style="display:none;">
								<input id="slider_fattenLiveRate" style="width:100%;height:100px">
							</div>
							<span class="silder-span">育肥期成活率(%)</span>
						</div>
						<div class="wd-com wd-2 text-center">
							<span id="productionRhythm" class="sildeValue-span"></span>
							<div class="canHide" style="display:none;">
								<input id="slider_productionRhythm" style="width:100%;height:100px">
							</div>
							<span class="silder-span">生产节律</span>
						</div>
						<div class="wd-com wd-2 text-center">
							<span id="updateRate" class="sildeValue-span"></span>
							<div class="canHide" style="display:none;">
								<input id="slider_updateRate" style="width:100%;height:100px">
							</div>
							<span class="silder-span">更新率(%)</span>
						</div>
						<div class="wd-com wd-2 text-center">
							<span id="preSowSucRate" class="sildeValue-span"></span>
							<div class="canHide" style="display:none;">
								<input id="slider_preSowSucRate" style="width:100%;height:100px">
							</div>
							<span class="silder-span">后备育成率(%)</span>
						</div>
						<div class="wd-com wd-2 text-center">
							<span id="outPigpen" class="sildeValue-span"></span>
							<div class="canHide" style="display:none;">
								<input id="slider_outPigpen" style="width:100%;height:100px">
							</div>
							<span class="silder-span">出栏(天)</span>
						</div>
					</div>
					<div class="wd-12 fr">
						<div class="wd-com wd-3 text-center">
							<span id="yearBreedNumValue" class="sildeValue-span"></span>
							<div id="yearBreedNum" class="rectangle canHide" style="display:none;">
								<div class="rectangle_gary"></div>
								<div class="rectangle_blue"></div>
							</div>
							<span class="silder-span">年配种母猪头次</span>
						</div>
						<div class="wd-com wd-3 text-center">
							<span id="yearDeliverySizeValue" class="sildeValue-span"></span>
							<div id="yearDeliverySize" class="rectangle canHide" style="display:none;">
								<div class="rectangle_gary"></div>
								<div class="rectangle_green"></div>
							</div>
							<span class="silder-span">年分娩窝数</span>
						</div>
						<div class="wd-com wd-3 text-center">
							<span id="breedNumValue" class="sildeValue-span"></span>
							<div id="breedNum" class="rectangle canHide" style="display:none;">
								<div class="rectangle_gary"></div>
								<div class="rectangle_red"></div>
							</div>
							<span class="silder-span">配种头数/批</span>
						</div>
						<div class="wd-com wd-3 text-center">
							<span id="litterSizeValue" class="sildeValue-span"></span>
							<div id="litterSize" class="rectangle canHide" style="display:none;">
								<div class="rectangle_gary"></div>
								<div class="rectangle_greyYellow"></div>
							</div>
							<span class="silder-span">产仔窝数/批</span>
						</div>
						<div class="wd-com wd-3 text-center">
							<span id="childPigValue" class="sildeValue-span"></span>
							<div id="childPig" class="rectangle canHide" style="display:none;">
								<div class="rectangle_gary"></div>
								<div class="rectangle_skyBule"></div>
							</div>
							<span class="silder-span">产仔头数/批</span>
						</div>
						<div class="wd-com wd-3 text-center">
							<span id="wean-childCareValue" class="sildeValue-span"></span>
							<div id="wean-childCare" class="rectangle canHide" style="display:none;">
								<div class="rectangle_gary"></div>
								<div class="rectangle_roseRed"></div>
							</div>
							<span class="silder-span">断奶&转保育/批</span>
						</div>
						<div class="wd-com wd-2 text-center">
							<span id="fattenValue" class="sildeValue-span"></span>
							<div id="fatten" class="rectangle canHide" style="display:none;">
								<div class="rectangle_gary"></div>
								<div class="rectangle_greyViolet"></div>
							</div>
							<span class="silder-span">转育肥/批</span>
						</div>
						<div class="wd-com wd-2 text-center">
							<span id="sellValue" class="sildeValue-span"></span>
							<div id="sell" class="rectangle canHide" style="display:none;">
								<div class="rectangle_gary"></div>
								<div class="rectangle_yellow"></div>
							</div>
							<span class="silder-span">销售/批</span>
						</div>
						<div class="wd-com wd-2 text-center">
							<span id="yearUpdateSowValue" class="sildeValue-span"></span>
							<div id="yearUpdateSow" class="rectangle canHide" style="display:none;">
								<div class="rectangle_gary"></div>
								<div class="rectangle_darkBlue"></div>
							</div>
							<span class="silder-span">年更母猪</span>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="contain fr" style="padding-top:182px;">
		<div id="ZHZB_CLGM" class="mapRow fl">
			<div class="paddingBigerMap paddomhBig-amp-width-49 fl" style="height:350px;">
				<div class="mapTitle fl">
					<strong>综合指标</strong>
				</div>
				<div class="fr">
					<select id="ZHZB_searchRange" name="searchRange" onChange="syntheticalIndicatorChange(this)">
					    <option value="year">近一年</option>
						<option value="month">近一月</option>
				    </select>
				</div>
				<div class="widgetGroup fl top_line bottom_line padding_top">
					<div class="wd-com wd-6">
						非生产天数:
					</div>
					<div class="wd-com wd-6">
						<strong class="font-yellow" id="nonProductionDays"></strong>
					</div>
					<div class="wd-com wd-6">
						产房全程成活率:
					</div>
					<div class="wd-com wd-6">
						<strong class="font-yellow" id="searchDeliverySurvivalRate"></strong>
					</div>
				</div>
				<div class="widgetGroup fl bottom_line padding_top">
					<div class="wd-com wd-6">
						七天断配率:
					</div>
					<div class="wd-com wd-6">
						<strong class="font-yellow" id="weanBreedOf7Day"></strong>
					</div>
					<div class="wd-com wd-6">
						保育全程成活率:
					</div>
					<div class="wd-com wd-6">
						<strong class="font-yellow" id="searchWelfareSurvivalRate"></strong>
					</div>
				</div>
				<div class="widgetGroup fl bottom_line padding_top">
					<div class="wd-com wd-6">
						分娩率:
					</div>
					<div class="wd-com wd-6">
						<strong class="font-yellow" id="searchDeliveryProbability"></strong>
					</div>
					<div class="wd-com wd-6">
						育肥全程成活率:
					</div>
					<div class="wd-com wd-6">
						<strong class="font-yellow" id="searchFattenSurvivalRate"></strong>
					</div>
				</div>
				<div class="widgetGroup fl bottom_line padding_top">
					<div class="wd-com wd-6">
						年产胎次:
					</div>
					<div class="wd-com wd-6">
						<strong class="font-yellow" id="searchYearParity"></strong>
					</div>
					<div class="wd-com wd-6">
						全场全程成活率:
					</div>
					<div class="wd-com wd-6">
						<strong class="font-yellow" id="searchCompleteSurvivalRate"></strong>
					</div>
				</div>
				<div class="widgetGroup fl bottom_line padding_top">
					<div class="wd-com wd-6">
						初生重:
					</div>
					<div class="wd-com wd-6">
						<strong class="font-yellow" id="searchPigBirthWeight"></strong>
					</div>
					<div class="wd-com wd-6">
						110KG出栏日龄:
					</div>
					<div class="wd-com wd-6">
						<strong class="font-yellow" id="search110kgOutHouse"></strong>
					</div>
				</div>
				<div class="widgetGroup fl bottom_line padding_top">
					<div class="wd-com wd-6">
						24日龄断奶重:
					</div>
					<div class="wd-com wd-6">
						<strong class="font-yellow" id="search24DayWeanPigWeight"></strong>
					</div>
					<div class="wd-com wd-6">
						PSY(预测):
					</div>
					<div class="wd-com wd-6">
						<strong class="font-yellow" id="searchPSYByDate"></strong>
					</div>
				</div>
				<div class="widgetGroup fl bottom_line padding_top">
					<div class="wd-com wd-6">
						7030重:
					</div>
					<div class="wd-com wd-6">
						<strong class="font-yellow" id="search7030PigWeight"></strong>
					</div>
					<div class="wd-com wd-6">
						MSY(预测):
					</div>
					<div class="wd-com wd-6">
						<strong class="font-yellow" id="searchMSYByDate"></strong>
					</div>
				</div>
			</div>
			<div class="paddingBigerMap paddomhBig-amp-width-49 fr" style="height:350px;">
				<div class="mapTitle fl">
					<strong>存栏规模</strong>
				</div>
				<div class="fr">
					<select id="CLGM_searchRange" name="searchRange" onChange="handScaleChange(this)">
				        <option value="week">近一周</option>
						<option value="month">近一月</option>
						<option value="year">近一年</option>
				    </select>
				</div>
				<div class="fl" id="handScale" style="width:100%;height:300px;"></div>
			</div>
		</div>
		<div id="SCTX" class="mapRow fl">
			<div class="paddingBigerMap paddomhBig-amp-width-100 fl">
				<div class="mapTitle fl">
					<strong>生产提醒</strong>
				</div>
				<div class="fr">
					<span onclick="productionWarnAddData()" class="closeOrOpen">刷新</span>
				</div>
				<div class="widgetGroup fl">
					<div class="wd-6 fl">
						<div class="abnormalRemind-title rectangle_blue">配种提醒</div>
						<div class="abnormalRemind-thead">
							<div class="wd-com wd-3">序号</div>
							<div class="wd-com wd-16">种类</div>
							<div class="wd-com wd-5">头数</div>
						</div>
						<div class="abnormalRemind-tbody">
							<table class="common-table wd-24">
								<tbody id="breedWarn">
								</tbody>
							</table>
						</div>
					</div>
					<div class="wd-12 fl">
						<div class="wd-24">
							<div class="wd-com wd-1"></div>
							<div class="wd-com wd-10">
								<div class="abnormalRemind-title rectangle_green">上产床提醒</div>
								<table class="abnormalRemind-small wd-24" id="laborWarn" style="height:130px;">
								</table>
							</div>
							<div class="wd-com wd-1"></div>
							<div class="wd-com wd-11">
								<div class="abnormalRemind-title rectangle_skyBule">待分娩提醒</div>
								<table class="abnormalRemind-small wd-24" id="deliveryWarn" style="height:130px;">
								</table>
							</div>
							<div class="wd-com wd-1"></div>
							<div class="wd-com wd-1"></div>
							<div class="wd-com wd-10">
								<div class="abnormalRemind-title rectangle_roseRed">待断奶提醒</div>
								<table class="abnormalRemind-small wd-24" id="weanWarn" style="height:130px;">
								</table>
							</div>
							<div class="wd-com wd-1"></div>
							<div class="wd-com wd-11">
								<div class="abnormalRemind-title rectangle_greyViolet">待出栏提醒</div>
								<table class="abnormalRemind-small wd-24" id="sellWarn" style="height:130px;">
								</table>
							</div>
							<div class="wd-com wd-1"></div>
							<!-- <div class="wd-com wd-1"></div>
							<div class="wd-com wd-7">
								<div class="abnormalRemind-title rectangle_yellow">待免疫异常</div>
								<table class="abnormalRemind-small wd-24" style="height:130px;">
									<tr>
										<td>功能开发中……</td>
									</tr>
								</table>
							</div>
							<div class="wd-com wd-1"></div>
							<div class="wd-com wd-7">
								<div class="abnormalRemind-title rectangle_darkBlue">物资预警</div>
								<table class="abnormalRemind-small wd-24" style="height:130px;">
									<tr>
										<td>功能开发中……</td>
									</tr>
								</table>
							</div> -->
						</div>
					</div>
					<div class="wd-6 fr">
						<div class="abnormalRemind-title rectangle_yellow">种猪淘汰提醒</div>
						<div class="abnormalRemind-thead">
							<div class="wd-com wd-3">序号</div>
							<div class="wd-com wd-16">种类</div>
							<div class="wd-com wd-5">头数</div>
						</div>
						<div class="abnormalRemind-tbody">
							<table class="common-table wd-24">
								<tbody id="obsoleteWarn">
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div id="SCFX" class="mapRow fl">
			<div class="paddingBigerMap paddomhBig-amp-width-100 fl">
				<div class="mapTitle fl">
					<strong>生产分析</strong>
				</div>
				<div class="fr">
					<select id="SCFX_searchRange" name="searchRange" onChange="productionAnalyze(this)">
						<option value="week">周</option>
					    <option value="year">年</option>
				    </select>
				</div>
				<div class="widgetGroup fl">
					<div class="wd-14 fl">
						<table class="common-table fl" style="width:100%;">
							<tr>
								<th id="weekNum" style="width:80px; padding:0;">
									<div class="table-th-bias">
										<b>月/周</b>
										<em>操作</em>
									</div>
								</th>
							</tr>
							<tr>
								<td id="MRHB_PIG">买入后备</td>
							</tr>
							<tr>
								<td id="ZRDP_PIG">转入待配</td>
							</tr>
							<tr>
								<td id="PZ_PIG">配种</td>
							</tr>
							<tr>
								<td id="FM_PIG">分娩</td>
							</tr>
							<tr>
								<td id="DN_SOW_PIG">断奶母猪</td>
							</tr>
							<tr>
								<td id="DN_PIG">断奶仔猪</td>
							</tr>
							<tr>
								<td id="ZBY_PIG">转保育</td>
							</tr>
							<tr>
								<td id="ZYF_PIG">转育肥</td>
							</tr>
							<tr>
								<td id="MZ_SALE_PIG">苗猪销售</td>
							</tr>
							<tr>
								<td id="FZ_SALE_PIG">肥猪销售</td>
							</tr>
							<tr>
								<td id="CC_SALE_PIG">残次猪销售</td>
							</tr>
							<tr>
								<td id="ZZ_SALE_PIG">种猪销售</td>
							</tr>
							<tr>
								<td id="ZZ_OUT_PIG">种猪淘汰</td>
							</tr>
							<tr>
								<td id="FARM_KILL_PIG">种猪自宰</td>
							</tr>
						</table>
					</div>
					<div class="wd-10 fr">
						<div id="productionAnalyze" style="width:100%;height:500px;"></div>
					</div>
				</div>
			</div>
		</div>
		<div id="LSZZ_SWFX" class="mapRow fl">
			<div class="commonMap amp-width-49 fl" style="height:400px;">
				<div class="mapTitle fl">
					<strong>栏舍周转</strong>
				</div>
				<div class="fr">
					<select id="LSZZ__searchRange" name="searchRange" onChange="houseAndPigpenChange(this)">
						<option value="month">月</option>
						<option value="year">年</option>
						<!-- <option value="week">周</option> -->
				    </select>
				</div>
				<div class="widgetGroup fl">
					<div class="housePenChange-tablist wd100-hasBorder">
						<ul id="changeHouseAndPigpenUl">
							<li class="selected" onclick="displayByPigType(this,'brPig')">哺乳猪</li>
							<li onclick="displayByPigType(this,'byPig')">保育猪</li>
							<li onclick="displayByPigType(this,'yfPig')">育肥猪</li>
<!-- 							<li onclick="displayByPigType(this,'szPig')">生长猪</li> -->
						</ul>
					</div>
					<div class="housePenChange-tabContent wd100-hasBorder">
						<div id="tabContent_header"></div>
						<div id="brPigHouseList" class="pigHouseList"></div>
						<div id="byPigHouseList" class="pigHouseList" style="display:none;"></div>
						<div id="yfPigHouseList" class="pigHouseList" style="display:none;"></div>
						<div id="szPigHouseList" class="pigHouseList" style="display:none;"></div>
					</div>
				</div>
			</div>
			<div class="paddingBigerMap paddomhBig-amp-width-49 fr" style="height:400px;">
				<div class="mapTitle fl">
					<strong>死亡分析</strong>
				</div>
				<div class="fr">
					<select id="SWFX_searchRange" name="searchRange" onChange="deathAnalyzeChange(this)">
						<option value="week">近一周</option>
						<option value="month">近一月</option>
					    <option value="year">近一年</option>
				    </select>
				</div>
 				<div class="fl" id="deathAnalyze" style="width:100%;height:350px;"></div>
			</div>
		</div>
	    <div id="MZQLT" class="mapRow fl">
			<div class="paddingBigerMap paddomhBig-amp-width-100 fl">
				<div class="mapTitle fl">
					<strong>母猪蓝图(批次)</strong>
				</div>
				<div class="fr">
					<span onclick="sowSwineryBlueMapAddData()" class="closeOrOpen">刷新</span>
				</div>
				<div class="widgetGroup fl" style="height:260px;overflow-y:auto;">
				 	<div id="swineryName" class="wd-2 fl">
				 	</div>
					<div class="wd-20 fl">
						<div id="week-line"></div>	
				 		<div id="sowSwinery"></div>
				 	</div>
					<div class="wd-2 fl delivery-rate">
						<span class="y-week-s">分娩率</span>
					</div>
				</div>
				<div class="widgetGroup fl">
					<div class="exception"></div>
			 		<div class="legend">
			 			<span class="shoud-delivery-line-small"></span><span>应分娩</span>
			 			<span class="rec-legend rectangle_gary"></span><span>应配种</span>
			 			<span class="rec-legend rectangle_greyViolet"></span><span>配种</span>
			 			<span class="rec-legend rectangle_blue"></span><span>怀孕</span>
			 			<span class="rec-legend rectangle_yellow"></span><span>分娩</span>
			 			<span class="rec-legend rectangle_green"></span><span>断奶</span>
			 		</div>
				</div>
			</div>
		</div>
		<div id="TCFB" class="mapRow fl">
			<div class="paddingBigerMap paddomhBig-amp-width-100 fl" style="height:400px;">
				<div class="mapTitle fl">
					<strong>胎次分布</strong>
				</div>
				<div class="fr">
					<span onclick="parityDescInit(basicSowNum)" class="closeOrOpen">刷新</span>
				</div>
				<div class="wd-20 fl" style="margin-top:40px;">
					<div id="parityDesc" style="width:100%;height:360px;"></div>
				</div>
			</div>
		</div>
	</div>
</body>
	<script src="${base_url}/lib/echarts/echarts.min.js?v=${webVersion}"></script>
	<script id="reloadJs" src="${base_url}/js/homePage/BusinessAnalysis.js?v=${webVersion}"></script>
</html>