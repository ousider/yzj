<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="/jsp/Head.jsp" />
	<script src="${base_url}/js/production/ProductionDynamic.js?v=${param.randomnumber}"></script>
</head>
<body>
	<div style="display:none;">
		<input type="hidden" id="farmName" name="farmName" value="${userDetail.farmName}"/>
		<input type="hidden" id="farmId" name="farmId" value="${userDetail.farmId}"/>
		<input type="hidden" id="employName" name="employName" value="${userDetail.employName}"/>
		<input type="hidden" id="finereport_url" name="finereport_url" value="${finereport_url}"/>
		<input type="hidden" id="finereport_username" name="finereport_username" value="${finereport_username}"/>
	</div>
		<div id="productionDynamicLayout" class="easyui-layout" data-options="fit:true,border:false" >
			<div data-options="region:'west',title:'组织架构',split:true"  style="width:21%;min-width:270px">
				<div id="treeTable"></div>
			</div>
			<div data-options="region:'center'" >
				<div id="tt" class="easyui-tabs" data-options="onSelect:tabsOnselect"style="width:100%;height:100%;">
				    <div title="存栏" style="padding:1px;display:none;">
				        <div class="pd-table-thead">
							<div><strong>对比项</strong></div>
						</div>
						<div class="pd-table-tbody">
							<div>
								<table class="pd-table">
									<tbody>
										<tr>
											<td rowspan="3" class="border-right">公猪</td>
											<td>生产公猪</td>
										</tr>
										<tr><td>后备公猪</td></tr>
										<tr><td>小计</td></tr>
										<tr>
											<td rowspan="3" class="border-right">母猪</td>
											<td>经产母猪</td>
										</tr>
										<tr><td>后备母猪</td></tr>
										<tr><td>小计</td></tr>
										<tr>
											<td rowspan="7" class="border-right">肉猪</td>
											<td>哺乳仔猪</td>
										</tr>
										<tr><td>断奶仔猪</td></tr>
										<tr><td>保育猪</td></tr>
										<tr><td>育肥猪</td></tr>
										<tr><td>留种公</td></tr>
										<tr><td>留种母</td></tr>
										<tr><td>淘汰猪</td></tr>
										<tr><td colspan="2">小计</td></tr>
									</tbody>
								</table>
							</div>
						</div>
				    </div>
				    <div title="生产变动" style="overflow:auto;padding:1px;display:none;" data-changeflag="false" id="production_change_tab">
				    	<div class="change-fiexd fl" id="changeFiexd">
				    		<div class="change-heead">
				    			对比时间：<input type="radio" name="compareType" value="1"/>本周&nbsp;&nbsp;&nbsp;&nbsp;
				    			<input type="radio" name="compareType" value="2"/>本月&nbsp;&nbsp;&nbsp;&nbsp;
				    			<input type="radio" name="compareType" value="3" checked="checked"/>本季度&nbsp;&nbsp;&nbsp;&nbsp;
				    			<input type="radio" name="compareType" value="4"/>本年&nbsp;&nbsp;&nbsp;&nbsp;
				    		</div>
				    		<div class="pd-c-table-thead">
								<div class="line-60" id="itemName"><strong>项目名称</strong></div>
							</div>
				    	</div>
				    	<div class="change-move fl">
				    		<div class="collapseField change fl" id="PZBD">
								<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>配种变动</div>
								<div class="fieldContent change">
									<div class="pd-c-table-tbody">
										<div>
											<table class="pd-table">
												<tbody>
													<tr><td>断奶</td></tr>
													<tr><td>返情</td></tr>
													<tr><td>后备</td></tr>
													<tr><td>空怀</td></tr>
													<tr><td>流产</td></tr>
												</tbody>
											</table>
										</div>
									</div>
								</div>
							</div>
							<div class="collapseField change fl" id="RJBD">
								<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>妊检变动</div>
								<div class="fieldContent change">
									<div class="pd-c-table-tbody">
										<div>
											<table class="pd-table">
												<tbody>
													<tr><td>返情</td></tr>
													<tr><td>空怀/假孕</td></tr>
													<tr><td>流产</td></tr>
													<tr><td>阴性</td></tr>
												</tbody>
											</table>
										</div>
									</div>
								</div>
							</div>
							<div class="collapseField change" id="FMBD">
								<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>分娩变动</div>
								<div class="fieldContent change">
									<div class="pd-c-table-tbody">
										<div>
											<table class="pd-table">
												<tbody>
													<tr><td>健仔数</td></tr>
													<tr><td>弱仔数</td></tr>
													<tr><td>死胎</td></tr>
													<tr><td>畸形</td></tr>
													<tr><td>木乃伊</td></tr>
													<tr><td>黑胎</td></tr>
													<tr><td>活仔公</td></tr>
													<tr><td>活仔母</td></tr>
													<tr><td>产仔总数</td></tr>
													<tr><td>平均出生重</td></tr>
													<tr><td>平均产活仔数</td></tr>
												</tbody>
											</table>
										</div>
									</div>
								</div>
							</div>
							<div class="collapseField change" id="DNBD">
								<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>断奶变动</div>
								<div class="fieldContent change">
									<div class="pd-c-table-tbody">
										<div>
											<table class="pd-table">
												<tbody>
													<tr><td>断奶数</td></tr>
													<tr><td>断奶重量</td></tr>
													<tr><td>平均断奶头数</td></tr>
													<tr><td>平均断奶窝重</td></tr>
												</tbody>
											</table>
										</div>
									</div>
								</div>
							</div>
							<div class="collapseField change" id="ZBYBD"> 
								<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>转保育变动</div>
								<div class="fieldContent change">
									<div class="pd-c-table-tbody">
										<div>
											<table class="pd-table">
												<tbody>
													<tr><td>窝数</td></tr>
													<tr><td>转出头数</td></tr>
													<tr><td>转出总重</td></tr>
													<tr><td>转出均重</td></tr>
													<tr><td>平均日龄</td></tr>
												</tbody>
											</table>
										</div>
									</div>
								</div>
							</div>
							<div class="collapseField change" id="ZYFBD">
								<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>转育肥变动</div>
								<div class="fieldContent change">
									<div class="pd-c-table-tbody">
										<div>
											<table class="pd-table">
												<tbody>
													<tr><td>转出头数</td></tr>
													<tr><td>转出重量</td></tr>
													<tr><td>平均体重</td></tr>
													<tr><td>平均日龄</td></tr>
												</tbody>
											</table>
										</div>
									</div>
								</div>
							</div>
							<div class="collapseField change" id="ZZSWBD">
								<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>种猪死亡变动</div>
								<div class="fieldContent change">
									<div class="pd-c-table-tbody">
										<div>
											<table class="pd-table">
												<tbody>
													<tr><td>公猪后备淘汰</td></tr>
													<tr><td>公猪生产淘汰</td></tr>
													<tr><td>公猪后备死亡</td></tr>
													<tr><td>公猪生产死亡</td></tr>
													<tr><td>母猪后备淘汰</td></tr>
													<tr><td>母猪生产淘汰</td></tr>
													<tr><td>母猪后备死亡</td></tr>
													<tr><td>母猪后备死亡</td></tr>
												</tbody>
											</table>
										</div>
									</div>
								</div>
							</div>
							<div class="collapseField change" id="CFSWBD">
								<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>产房死亡变动</div>
								<div class="fieldContent change">
									<div class="pd-c-table-tbody">
										<div>
											<table class="pd-table">
												<tbody>
													<tr><td>死亡数量</td></tr>
												</tbody>
											</table>
										</div>
									</div>
								</div>
							</div>
							<div class="collapseField change" id="BYSWBD">
								<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>保育死亡变动</div>
								<div class="fieldContent change">
									<div class="pd-c-table-tbody">
										<div>
											<table class="pd-table">
												<tbody>
													<tr><td>死亡数量</td></tr>
												</tbody>
											</table>
										</div>
									</div>
								</div>
							</div>
							<div class="collapseField change" id="YFSWBD">
								<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>育肥死亡变动</div>
								<div class="fieldContent change">
									<div class="pd-c-table-tbody">
										<div>
											<table class="pd-table">
												<tbody>
													<tr><td>死亡数量</td></tr>
												</tbody>
											</table>
										</div>
									</div>
								</div>
							</div>
							<div class="collapseField change" id="BYXSBD">
								<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>保育销售变动</div>
								<div class="fieldContent change">
									<div class="pd-c-table-tbody">
										<div>
											<table class="pd-table">
												<tbody>
													<tr><td>销售数量</td></tr>
													<tr><td>总重</td></tr>
													<tr><td>均重</td></tr>
													<tr><td>总金额</td></tr>
												</tbody>
											</table>
										</div>
									</div>
								</div>
							</div>
							<div class="collapseField change" id="YFXSBD">
								<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>育肥销售变动</div>
								<div class="fieldContent change">
									<div class="pd-c-table-tbody">
										<div>
											<table class="pd-table">
												<tbody>
													<tr><td>销售数量</td></tr>
													<tr><td>总重</td></tr>
													<tr><td>均重</td></tr>
													<tr><td>总金额</td></tr>
												</tbody>
											</table>
										</div>
									</div>
								</div>
							</div>
							<div class="collapseField change" id="CCZXSBD">
								<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>残次猪销售变动</div>
								<div class="fieldContent change">
									<div class="pd-c-table-tbody">
										<div>
											<table class="pd-table">
												<tbody>
													<tr><td>销售数量</td></tr>
													<tr><td>总重</td></tr>
													<tr><td>总金额</td></tr>
												</tbody>
											</table>
										</div>
									</div>
								</div>
							</div>
				    	</div>
				    </div>
				    <div title="绩效对比" style="overflow:auto;padding:1px;display:none;" data-changeflag="false" id="performance_change_tab">
				    	<div class="change-fiexd per-change fl" id="changeFiexd" >
							<div class="wd-com wd-3"><label class="label">对比日期:</label></div>
							<div class="wd-com wd-5">
								<input class="easyui-datebox" id="searchDate" name="searchDate" data-options="prompt:'请输入查询日期',required:true,value:getCurrentDate(),onChange:searchDateChange" style="width:100%;height:32px" >
							</div>
				    	</div>
				    	 <div class="pd-p-table-thead" style="margin-top:40px;">
							<div><strong>绩效项目</strong></div>
						</div>
						<div class="pd-p-table-tbody">
							<div>
								<table class="pd-table">
									<tbody id="performanceChangeTable">
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div data-options="region:'east',split:true,title:'变动项目'"  style="width:12%;min-width:170px">
				<ul class="verticalNav" id="changeNav" style="width:100%;">
				    <li class="selected" data-href="PZBD"><span class="solid-arrow"></span>配种变动</li>
				    <li data-href="RJBD"><span class="point"></span>妊检变动</li>
				    <li data-href="FMBD"><span class="point"></span>分娩变动</li>
				    <li data-href="DNBD"><span class="point"></span>断奶变动</li>
				    <li data-href="ZBYBD"><span class="point"></span>转保育变动</li>
				    <li data-href="ZYFBD"><span class="point"></span>转育肥变动</li>
				    <li data-href="ZZSWBD"><span class="point"></span>种猪死亡变动</li>
				    <li data-href="CFSWBD"><span class="point"></span>产房死亡变动</li>
				    <li data-href="BYSWBD"><span class="point"></span>保育死亡变动</li>
				    <li data-href="YFSWBD"><span class="point"></span>育肥死亡变动</li>
				    <li data-href="BYXSBD"><span class="point"></span>保育销售变动</li>
				    <li data-href="YFXSBD"><span class="point"></span>育肥销售变动</li>
				    <li data-href="CCZXSBD"><span class="point"></span>残次猪销售变动</li>
				</ul>
			</div>
		</div>
	<div id="treeTableToolbar" style="text-align:center;">
		<button type="button" onclick="collapseAll()" class="tableToolbarBtn btn-middle" style="width:70px;">折叠</button>
		<button type="button" onclick="expandAll()" class="tableToolbarBtn btn-middle" style="width:70px;">展开</button>
		<button type="button" onclick="refresh()" class="tableToolbarBtn btn-middle" style="width:70px;">刷新</button>
	</div>
	</body>
</html>