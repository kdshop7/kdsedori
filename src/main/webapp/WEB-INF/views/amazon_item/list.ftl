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
<#list items as item>
  <#if !ignore_ng_filter && item.ng>
  <#else>

  	<form action="upsert"> 
     <tr>
		<td>  <a name="${item.asin}"><span id="${item.asin}"> </span></a>${item.asin} <br />${item.sales_rank}位</td>
		<td>
			<a target="_brank" href="https://www.amazon.co.jp/gp/offer-listing/${item.asin}/ref=dp_olp_used?ie=UTF8&condition=used">${item.title}</a></td>
		<td align="right" nowrap>
			${item.new_price2}円　 
			<br />
			${item.lowest_used_price!}円 (${item.total_used})
		</td>
		<td nowrap>
			<a target="_brank" href="http://mnrate.com/item/aid/${item.asin}">モノ</a>
			<br />
			<#if item.yahoo_auction_url??>
			<a target="_brank" href="${item.yahoo_auction_url}">ヤフ</a>
			<#if item.yahoo_auction_hit_count??>
		 	(<a target="_brank" href="yahoo-auction-hits?keyword=${item.title}&new_price=${item.new_price2}&yahoo_auction_contract_price=${item.yahoo_auction_contract_price!}">${item.yahoo_auction_hit_count}</a>)
			</#if>
			<br />
			</#if>
			<a target="_brank" href="https://www.mercari.com/jp/search/?sort_order=&keyword=${item.title}&category_root=5&category_child=74&brand_name=&brand_id=&size_group=&price_min=&price_max=&status_on_sale=1">メルカリ</a>
		</td>
		<td align="right" nowrap>
			<input type="text" name="sales_price" value="${item.sales_price!}" size="7" />円<br />
			${item.profit!}円</td>
		<td align="right" nowrap title="ROI : ${item.roi!}%">
			${item.yahoo_auction_contract_price!}円<br />
			<input type="text" name="shipping_costs" class="shipping_costs" value="${item.shipping_costs!}" size="5" />円
		</td>
		<td align="right" nowrap><textarea name="bid_memo" rows="2">${item.bid_memo!}</textarea></td>
		<td><input type="submit" value="更新"></td>		
	</tr>
	<input type="hidden" name="asin" value="${item.asin}" />
	</form>
  </#if> 		
</#list>
</tbody>
</table>	
