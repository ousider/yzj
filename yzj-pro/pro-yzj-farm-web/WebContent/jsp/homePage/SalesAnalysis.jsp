<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<link href="${base_url}/lib/jquery-easyui-1.4.5/themes/default/easyui.css?v=${webVersion}" rel="stylesheet">
	<link href="${base_url}/lib/jquery-easyui-1.4.5/themes/icon.css?v=${webVersion}" rel="stylesheet">
</head>
<body>
	<div class="sales">
		<div class="search-bar">
			<span>search</span>
		</div>
		<div class="search-win">
			<div class="search-win-title">
				<span>查询条件</span>
			</div>
			<div class="search-win-content">
				<div class="search-class">
					<ul>
						<li>查询区间</li>
						<li>集团</li>
						<li>猪场</li>
					</ul>
				</div>
				<div class="search-selection">
					<ul>
						<li>
							<a href="#" data-value="week">近一周</a>
							<a href="#" data-value="month">近一月</a>
							<a href="#" data-value="year">近一年</a>
						</li>
						<li>
							<a href="#">大丰</a>
							<a href="#">大丰</a>
							<a href="#">大丰</a>
						</li>
						<li>
							<a href="#">繁殖一场</a>
							<a href="#">繁殖二场</a>
							<a href="#">繁殖三场</a>
						</li>
					</ul>
				</div>
			</div>
			<div class="search-win-font">
				<span class="sw-btn-sure" onclick="salesSearchSure()">确定</span>
				<span class="sw-btn-cancel" onclick="salesSearchCancel()">取消</span>
			</div>
		</div>
		<div id="sales-portal" class="sales-content">
			<div class="sales-content-title">
				<span class="sc-t-big">新农集团销售实时统计</span>
				<span class="sc-t-time">
					<span class="time-date"></span>
					<span class="time-hour"></span>
				</span>
			</div>
			<div class="sales-content-data">
				<div class="sc-map">
					<span></span>
					<span class="sc-map-bg" id="customerAddressMap"></span>
				</div>
				<div class="sc-data">
					<span class="sc-data-v"></span>
					<span class="sc-data-content">
						<span class="data-row">
							<span class="data-com-col">
								<span class="col-title">本月销售量</span>
								<span class="col-data">
									<span class="data-num" id="saleNumThisMonth"></span>
									<span class="data-trend">
										<span class="trend-icon"></span><br>
										<span id="saleProbability"></span>
									</span>
								</span>
								<span class="data-detail">
									<span class="detail-title">
										<span>本日</span>
										<span>最近一周</span>
										<span>上月销售</span>
									</span>
									<span class="detail-num">
										<span id="saleNumThisDay"></span>
										<span id="saleNumLastWeek"></span>
										<span id="saleNumLastMonth"></span>
									</span>
								</span>
							</span>
							<span class="data-com-col">
								<span class="col-title">本月出栏量</span>
								<span class="col-data">
									<span class="data-num" id="marketingThisMonth"></span>
									<span class="data-trend">
										<span class="trend-icon"></span><br>
										<span id="marketingProbability">%</span>
									</span>
								</span>
								<span class="data-detail">
									<span class="detail-title">
										<span>本日</span>
										<span>最近一周</span>
										<span>上月出栏</span>
									</span>
									<span class="detail-num">
										<span id="marketingThisDay"></span>
										<span id="marketingLasWeek"></span>
										<span id="marketingLastMonth"></span>
									</span>
								</span>
							</span>
							<span class="data-com-col">
								<span class="col-title">月新增客户</span>
								<span class="col-data">
									<span class="data-num" id="companyThisMonth"></span>
									<span class="data-trend">
										<span class="trend-icon red-down"></span><br>
										<span id="companyProbability"></span>
									</span>
								</span>
								<span class="data-detail">
									<span class="detail-title">
										<span>本日</span>
										<span>最近一周</span>
										<span>上月新增</span>
									</span>
									<span class="detail-num">
										<span id="companyThisDay"></span>
										<span id="companyLastWeek"></span>
										<span id="companyLastMonth"></span>
									</span>
								</span>
							</span>
							<span class="data-com-col">
								<span class="col-title">年累计客户</span>
								<span class="col-data">
									<span class="data-num" id="companyYear"></span>
									<span class="data-trend">
										<span class="trend-icon red-down"></span><br>
										<span>5.8%</span>
									</span>
								</span>
								<span class="data-detail">
									<span class="detail-title">
										<span>活跃客户</span>
										<span>优质客户</span>
										<span>僵尸客户</span>
									</span>
									<span class="detail-num">
										<span>123</span>
										<span>725</span>
										<span>3425</span>
									</span>
								</span>
							</span>
						</span>
						<span class="data-row">
							<span class="data-com-row">
								<span class="row-title">年商品猪销售总量 </span>
								<span class="row-data" id="saleNumThisYear"></span>
								<span>万头/年</span>
							</span>
						</span>
						<span class="data-row">
							<span class="data-com-row">
								<span class="row-title">年商品猪销售总额</span>
								<span class="row-data" id="marketingTotalPriceThisYear"></span>
								<span>万元/年</span>
							</span>
						</span>
					</span>
				</div>
			</div>
		</div>
		<!-- 销售分析begin -->
		<div id="sales-analysis" class="salse-pic">
			<div class="sales-content-title">
				<div class="display-search">
				</div>
			</div>
			<div class="sales-content-data">
				<div class="pic-title-list">
					<ul data-pictype="sales-analysis">
						<li class="selected" data-picname="groupGoodPigSale" data-first=false>集团商品猪销售</li>
						<li data-picname="groupFarmSalePrice" data-first=true>集团厂区销售额</li>
						<li data-picname="goodPigSaleType" data-first=true>商品猪销售品类</li>
					</ul>
				</div>
				<div class="sales-pic">
					<div id="groupGoodPigSale"></div>
					<div id="groupFarmSalePrice" style="display:none;"></div>
					<div id="goodPigSaleType" style="display:none;"></div>
				</div>
			</div>
		</div>
		<!-- 销售分析end -->
		<!-- 销售计划begin -->
		<div id="sales-plan" class="salse-pic">
			<div class="sales-content-title">
				<div class="display-search">
				</div>
			</div>
			<div class="sales-content-data">
				<div class="pic-title-list">
					<ul>
						<li class="selected">图表一</li>
						<li>图表二</li>
						<li>图表三</li>
					</ul>
				</div>
				<div class="sales-pic"></div>
			</div>
		</div>
		<!-- 销售计划end -->
		<!-- 销售预警begin -->
		<div id="sales-warn" class="salse-pic">
			<div class="sales-content-title">
				<div class="display-search">
				</div>
			</div>
			<div class="sales-content-data">
				<div class="pic-title-list">
					<ul>
						<li class="selected">图表一</li>
						<li>图表二</li>
						<li>图表三</li>
					</ul>
				</div>
				<div class="sales-pic"></div>
			</div>
		</div>
		<!-- 销售预警end -->
		<!-- 行情走势begin -->
		<div id="quotation-trend" class="salse-pic">
			<div class="sales-content-title">
				<div class="display-search">
				</div>
			</div>
			<div class="sales-content-data">
				<div class="pic-title-list">
					<ul>
						<li class="selected">图表一</li>
						<li>图表二</li>
						<li>图表三</li>
					</ul>
				</div>
				<div class="sales-pic"></div>
			</div>
		</div>
		<!-- 行情走势end -->
		<!-- 质量跟踪begin -->
		<div id="quality-trace" class="salse-pic">
			<div class="sales-content-title">
				<div class="display-search">
				</div>
			</div>
			<div class="sales-content-data">
				<div class="pic-title-list">
					<ul>
						<li class="selected">图表一</li>
						<li>图表二</li>
						<li>图表三</li>
					</ul>
				</div>
				<div class="sales-pic"></div>
			</div>
		</div>
		<!-- 质量跟踪end -->
		<!-- 盈利能力begin -->
		<div id="profitability" class="salse-pic">
			<div class="sales-content-title">
				<div class="display-search">
				</div>
			</div>
			<div class="sales-content-data">
				<div class="pic-title-list">
					<ul data-pictype="profitability">
						<li class="selected" data-picname="imperfectPigSaleChange" data-first=false>残次猪销售变动</li>
					</ul>
				</div>
				<div class="sales-pic">
					<div id="imperfectPigSaleChange"></div>
				</div>
			</div>
		</div>
		<!-- 盈利能力end -->
		<!-- 客户管理begin -->
		<div id="customer-manage" class="salse-pic">
			<div class="sales-content-title">
				<div class="display-search">
				</div>
			</div>
			<div class="sales-content-data">
				<div class="pic-title-list">
					<ul data-pictype="customer-manage">
						<li class="selected" data-picname="salePriceTop10Customer" data-first=false>前十客户销售额</li>
						<li data-picname="bigCustomerSaleCollection" data-first=true>大客户销售收款</li>
					</ul>
				</div>
				<div class="sales-pic">
					<div id="salePriceTop10Customer"></div>
					<div id="bigCustomerSaleCollection"></div>
				</div>
			</div>
		</div>
		<!-- 客户管理end -->
		<!-- 销售监控begin -->
		<div id="sales-monitoring" class="salse-pic">
			<div class="sales-content-title">
				<div class="display-search">
				</div>
			</div>
			<div class="sales-content-data">
				<div class="pic-title-list">
					<ul>
						<li class="selected">图表一</li>
						<li>图表二</li>
						<li>图表三</li>
					</ul>
				</div>
				<div class="sales-pic"></div>
			</div>
		</div>
		<!-- 销售监控end -->
		<div class="sales-class">
			<span class="sc-icon">
				<span class="sc-icon-com sales-portal selected" data-first=true></span>
				<span class="sc-icon-com-title">销售门户</span>
			</span>
			<span class="sc-icon">
				<span class="sc-icon-com sales-analysis" data-first=true></span>
				<span class="sc-icon-com-title">销售分析</span>
			</span>
			<span class="sc-icon">
				<span class="sc-icon-com sales-plan" data-first=true></span>
				<span class="sc-icon-com-title">销售计划</span>
			</span>
			<span class="sc-icon">
				<span class="sc-icon-com sales-warn" data-first=true></span>
				<span class="sc-icon-com-title">销售预警</span>
			</span>
			<span class="sc-icon">
				<span class="sc-icon-com quotation-trend" data-first=true></span>
				<span class="sc-icon-com-title">行情走势</span>
			</span>
			<span class="sc-icon">
				<span class="sc-icon-com quality-trace" data-first=true></span>
				<span class="sc-icon-com-title">质量跟踪</span>
			</span>
			<span class="sc-icon">
				<span class="sc-icon-com profitability" data-first=true></span>
				<span class="sc-icon-com-title">盈利能力</span>
			</span>
			<span class="sc-icon">
				<span class="sc-icon-com customer-manage" data-first=true></span>
				<span class="sc-icon-com-title">客户管理</span>
			</span>
			<span class="sc-icon">
				<span class="sc-icon-com sales-monitoring" data-first=true></span>
				<span class="sc-icon-com-title">销售监控</span>
			</span>
		</div>
	</div>
</body>
<script src="${base_url}/js/ChineseDate.js?v=${webVersion}"></script>
<script src="${base_url}/lib/echarts/echarts.min.js?v=${webVersion}"></script>
<script src="${base_url}/lib/echarts/china.js?v=${webVersion}"></script>
<script src="${base_url}/js/homePage/SalesAnalysis.js?v=${webVersion}"></script>
</html>