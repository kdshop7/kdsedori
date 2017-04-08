<@content for="title">仕入れ候補リスト</@content>

 <div id="app-4">
 <table>
 <thead>
    <tr>
        <th>Gift name</th>
        <th>Price</th>
        <th>落札価格</th>
        <th>落札価格</th>

    </tr>
</thead>
<tbody v-for="(todo, index) in todos">
    <tr>
    <td>
      {{ todo.asin }}
    </td>
    <td>
      {{ evenNumbers }}
    </td>
    <td>
      <input v-model="todo.price">
    </td>    
    <td>
 aa
    </td>
    </tr>
</tbody>
</table>  
</div>

<script type="text/javascript">
var app4 = new Vue({
  el: '#app-4',
  data: {
    todos: [
	    { asin : "99999999", title: "Tall Hat", price: "39", bidPrice : 123},
	    { asin : "88888888", title: "Long Cloak", price: "120", bidPrice : 333}
    ]
  },
computed: {
  evenNumbers: function () {
    return this.todos[index].price * 2;
  }
}  
});

</script>
