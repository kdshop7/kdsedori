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
|<a target="_brank" href="/crawler/offer-listing">Offer</a>
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
  <#if !ignore_ng_filter && (item.ng || item.ng2)>
  <#else>

<div class="container">
<div class="row">
	<hr />
 	<form  action="upsert" class="form-horizontal"> 

	<div class="col-lg-3 col-md-3 col-sm-3 col-xs-12">
		<span class="border-1">
 			<#if item.image_url??>
				<img src="${item.image_url}" /><br />
			</#if>  
			<a name="${item.asin}"><span id="${item.asin}"> </span></a>${item.asin} <br />${item.sales_rank!}位
		</span>
	</div>
	<div class="col-lg-9 col-md-9 col-sm-9 col-xs-12 form-inline">
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
			<textarea class="form-control" id="InputTextarea" name="bid_memo" rows="2" cols="40">${item.bid_memo!}</textarea>
	<br /> 
                        <table bgcolor="#FFFFFF">
                        <tr>
                        <td>
			  販売:
                        </td>
                        <td><input type="text" class="form-control" id="InputNumber" name="sales_price" value="${item.sales_price!}" size="7" /></td><td>円</td>
                        </tr>
                        </table>
                        <table><tr>
                        <td>
			仕入：
			${item.yahoo_auction_contract_price!}円
			+ 
                        </td>
                        <td>
			<input type="text" class="form-control" id="InputNumber" name="shipping_costs" class="shipping_costs" value="${item.shipping_costs!}" size="5" />
                        </td><td>円</td>
                        </tr>
                        </table>
                        <table>
                        <tr><td>
			利益 : ${item.profit!}円(${item.roi!}%)
                        </td></tr></table>
                        <input type="submit" class="btn btn-default center-block" value="更新"><a href="delete?asin=${item.asin}">削除</a>
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
