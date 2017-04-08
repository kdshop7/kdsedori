<@content for="title">ヤフオク</@content>

<h2>ヤフオクリサーチ</h2>
キーワード：${keyword!}<br/>
新品価格：${new_price!}円

<table class="table table-striped table-bordered">
<thead>
<tr><th>オークションID</th><th>タイトル</th><th>現在価格</th><th>即決価格</th><th>出品者</th><th>判定</th></tr>
</thead>
<tbody>
<#list items as item>
<tr>
	<td><a href="http://aucview.aucfan.com/yahoo/${item.auctionId!}#colInfoArea" target="_blank">${item.auctionId!}</a></td>
	<td><a href="${item.auctionItemUrl!}" target="_blank">${item.title!}</a></td>
	<td align=right nowrap>${item.currentPrice!}円</td>
	<td align=right nowrap>${item.bidOrBuy!}<#if item.bidOrBuy??>円</#if></td>
	<td><a href="http://sellinglist.auctions.yahoo.co.jp/user/${item.sellerId}">${item.sellerId}</a></td>
	<td><#if item.ng>NG</#if></td>
</tr>
</#list>
</tbody>
</table>

