<@content for="title">仕入れ候補リスト</@content>





<h2>仕入れ候補リスト</h2>


  
<div align="right">
|
  <#if ignore_ng_filter>
	<a href="/amazon-item/list">NGフィルタを有効にする</a>
  <#else>
	<a href="?ignore_ng_filter=true">NGフィルタを無効にする</a>
  </#if> 
|<a target="_brank" href="/crawler/update-yahoo-hits">Y!更新</a>
|<a target="_brank" href="/crawler/run">データ取込</a>
|</div>

<!--
<table class="table table-striped table-bordered">
  <thead>
	<tr>
		<th>ASIN<br />Rank</th>
		<th>タイトル<br />　</th>
		<th>新品<br />中古</th>
		<th>外部<br />　</th>
		<th>販売金額<br/>利益額</th>
		<th>入札額<br/>送料</th>
		<th>メモ<br />　</th>
		<th></th>		
	</tr>
  </thead>
  <tbody>
-->

  	  	
<#list items as item>
  <#if !ignore_ng_filter && item.ng>
  <#else>

<div class="container">
<div class="row">
	<hr />
 	<form  class="form-inline" action="upsert"> 

	<div class="col-lg-3 col-md-3 col-sm-3 col-xs-4">
		<span class="border-1">
 			<#if item.image_url??>
				<img src="${item.image_url}" /><br />
			</#if>  
			<a name="${item.asin}"><span id="${item.asin}"> </span></a>${item.asin} <br />${item.sales_rank!}位
		</span>
	</div>
	<div class="col-lg-5 col-md-5 col-sm-5 col-xs-8">
			<a target="_brank" href="https://www.amazon.co.jp/gp/offer-listing/${item.asin}/ref=dp_olp_used?ie=UTF8&condition=used">${item.title}</a>
			<br />
			<br />
			| ${item.new_price2}円 | ${item.lowest_used_price!}円 (${item.total_used}) | 
			<a target="_brank" href="http://mnrate.com/item/aid/${item.asin}">モノ</a>
			|
			<#if item.yahoo_auction_url??>
			<a target="_brank" href="${item.yahoo_auction_url}">ヤフ</a>
			<#if item.yahoo_auction_hit_count??>
		 	(<a target="_brank" href="yahoo-auction-hits?keyword=${item.title}&new_price=${item.new_price2}&yahoo_auction_contract_price=${item.yahoo_auction_contract_price!}">${item.yahoo_auction_hit_count}</a>)
			</#if>
			|
			</#if>
			<a target="_brank" href="https://www.mercari.com/jp/search/?sort_order=&keyword=${item.title}&category_root=5&category_child=74&brand_name=&brand_id=&size_group=&price_min=&price_max=&status_on_sale=1">メルカリ</a>
			|
			<br />
			<textarea class="form-control" id="InputTextarea" name="bid_memo" rows="2" >${item.bid_memo!}</textarea>
	</div>
	<div class="col-lg-2 col-md-2 col-sm-2 col-xs-9">
		<div class="pull-right">
			<span class="text-nowrap"><input type="text" class="form-control" id="InputNumber" name="sales_price" value="${item.sales_price!}" size="7" />円</span>
			<br />
			${item.yahoo_auction_contract_price!}円
			<br />
			<input type="text" class="form-control" id="InputNumber" name="shipping_costs" class="shipping_costs" value="${item.shipping_costs!}" size="5" />円
			<br />
			${item.profit!}円(${item.roi!}%)
		</div>
	</div>
	<div class="col-lg-2 col-md-2 col-sm-2 col-xs-3">
			<input type="submit" class="btn btn-default center-block" value="更新">	
			<br />
			<input type="hidden" name="asin" value="${item.asin}" />
	</div>		
	</form>
</div><!--/.row -->
</div><!--/.container -->	
  </#if> 		
</#list>
<!--
</tbody>
</table>	
-->
