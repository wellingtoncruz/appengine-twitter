<%@page import="com.mediacode.twittermachine.statics.URLS"%>
<!DOCTYPE html>
<html lang="pt-br">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>Login</title>

    <!-- Bootstrap -->
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <link href="/css/paralax.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
  <body>
	<div class="container">
	    <div class="row vertical-offset-100">
	    	<div class="col-md-4 col-md-offset-4">
	    		<div class="panel panel-default">
				  	<div class="panel-heading">
				    	<h3 class="panel-title">Please sign in</h3>
				 	</div>
				  	<div class="panel-body">
				    	<form accept-charset="UTF-8" role="form" action="<%=URLS.LOGIN_VALIDATE_URL %>" method=post>
	                    <fieldset>
				    	  	<div class="form-group">
				    		    <input class="form-control" placeholder="Usu&aacute;rio" name="username" type="text">
				    		</div>
				    		<div class="form-group">
				    			<input class="form-control" placeholder="Senha" name="password" type="password" value="">
				    		</div>
				    		<div class="checkbox">
				    	    	<label>
				    	    		<input name="remember" type="checkbox" value="Remember Me"> Lembrar
				    	    	</label>
				    	    </div>
				    		<input class="btn btn-lg btn-success btn-block" type="submit" value="Login">
				    	</fieldset>
				      	</form>
				    </div>
				</div>
			</div>
		</div>
	</div>

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="/js/jquery.min.js"></script>
    <script src="/js/bootstrap.min.js"></script>
    <script src="/js/TweenLite.min.js"></script>
    <script src="/js/paralax.js"></script>
  </body>
</html>

