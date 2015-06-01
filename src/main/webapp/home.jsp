<%@page import="com.mediacode.twittermachine.machines.messages.MessageMachine"%>
<%@page import="com.mediacode.twittermachine.entities.*"%>
<%@page import="com.mediacode.twittermachine.statics.*"%>
<%@page import="java.util.*"%>
<%@page import="twitter4j.*"%>
<%
	Profile profile = (Profile) request.getAttribute("profile");
%>
<!DOCTYPE html>
<html lang="en">
  <head>

    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>Twitter Machine</title>

    <!-- Bootstrap -->
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <link href="/css/home.css" rel="stylesheet">
    <link href="/css/drop-select.min.css" rel="stylesheet">
	<link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css'>
	<link href="//maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
  <body>

	<div class="container" style="margin-top: 20px; margin-bottom: 20px;">
		<div class="row panel">
			<div class="col-md-4 bg_blur" style="background-image:url('<%=profile.getBannerPicture()%>');">
	    	    <a href="http://www.twitter.com/<%=profile.getScreenName() %>" class="follow_btn hidden-xs" target="_blank">Follow</a>
			</div>
	        <div class="col-md-8  col-xs-12">
	           <img src="<%=profile.getProfilePicture() %>" class="img-thumbnail picture hidden-xs" />
	           <img src="<%=profile.getProfilePicture() %>" class="img-thumbnail visible-xs picture_mob" />
	           <div class="header">
	                <h1><%=profile.getName() %></h1>
	                <h1><%=profile.getTwitterId() %></h1>
	                <h4>@<%=profile.getScreenName() %></h4>
	                <span><%=profile.getDescription() %></span>
	                <span>Updated: <%=profile.getUpdated().toString() %></span>
	           </div>
	        </div>
	    </div>   
	    
		<div class="row nav">    
	        <div class="col-md-4"></div>
	        <div class="col-md-8 col-xs-12" style="margin: 0px;padding: 0px;">
	            <div class="col-md-4 col-xs-4 well"><small>Twittes</small> <i class="fa fa-weixin fa-lg"></i> <%=profile.getStatuses() %></div>
	            <div class="col-md-4 col-xs-4 well"><small>Seguindo</small> <i class="fa fa-users fa-lg"></i> <%=profile.getFriends() %></div>
	            <div class="col-md-4 col-xs-4 well"><small>Seguidores</small> <i class="fa fa-users fa-lg"></i> <%=profile.getFollowers() %></div>
	        </div>
	    </div>
	</div>
	
<form name="settings_form" action="" method="POST">
	<div class="container panel" style="margin-top: 20px; margin-bottom: 20px;">

		<div class="row">
			<div class="col-md-4">

			</div>
			
		        <div class="col-md-8 col-xs-12 settings">
				<h3>Twitter Machine Settings</h3>
		          </div>
		    </div>

	<div class="row">
			<div class="col-md-4">

			</div>
			
		        <div class="col-md-8 col-xs-12 settings">
				<div class="col-md-4">
					<span>Messages Machine:</span>
				</div>
				<div class="col-md-4">
					<select name="message_machine">
                        <%
                            for (Map.Entry<Integer, Class> entry : Machines.messagesMachines.entrySet()) {
                                Integer code = entry.getKey();
                                Class value = entry.getValue();
                            %>
                            <option value="<%=code%>"><%=value.getName()%></option>
                            <% } %>
					</select>
				</div>

		          </div>
		    </div>   

	<div class="row">
			<div class="col-md-4">

			</div>
			
		        <div class="col-md-8 col-xs-12 settings">
				<div class="col-md-4">
					<span>Posting Machine:</span>
				</div>
				<div class="col-md-4">
					<select name="posting_machine">
                        <%
                            for (Map.Entry<Integer, Class> entry : Machines.postingMachines.entrySet()) {
                                Integer code = entry.getKey();
                                Class value = entry.getValue();
                            %>
                            <option value="<%=code%>"><%=value.getName()%></option>
                            <% } %>
					</select>
				</div>
		    </div>      

	<div class="row">
			<div class="col-md-4">

			</div>
			
		        <div class="col-md-8 col-xs-12 settings">
				<div class="col-md-4">

				</div>
				<div class="col-md-4">
					<input type="submit" value="salvar" />
				</div>

		          </div>
		    </div>      
	</div>

</form>	   


    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="/js/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="/js/bootstrap.min.js"></script>
    
  </body>
</html>	
