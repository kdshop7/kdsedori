<#setting url_escaping_charset='ISO-8859-1'>

<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    
    <!-- Bootstrap -->
    <link href="/css/bootstrap.min.css" rel="stylesheet">

   	<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="/js/bootstrap.min.js"></script>

        
    <!--<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>-->
    <!--<LINK href="${context_path}/css/main.css" rel="stylesheet" type="text/css"/>-->
    <!--<script src="${context_path}/js/jquery-1.4.2.min.js" type="text/javascript"></script>-->
    <script src="${context_path}/js/aw.js" type="text/javascript"></script>
    <script src="${context_path}/js/knockout-3.4.2.js" type="text/javascript"></script>
    <!--<script src="https://unpkg.com/vue"></script>-->

    <script src="${context_path}/js/main.js" type="text/javascript"></script>

    <title>ヤフアマせどり - <@yield to="title"/></title>
</head>
<body>
    
<div class="main">
<#include "header.ftl" >
    <div class="content">
    ${page_content}
    </div>
<#include "footer.ftl" >
</div>

</body>

</html>
