<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Admin Login</title>
<link href="/css/bootstrap/bootstrap.min.css" rel="stylesheet">
<link href="/css/login.css" rel="stylesheet">
<link href="/css/style.css" rel="stylesheet">
<link href="/css/default.css" rel="stylesheet">
</head>
<body class="fix-header">
<div id="wrapper">
	    <div class="container-fluid">
      <div class="login-form white-box">
          <h1 class="login-heading-text color-gray">Welcome back admin!</h1>
          <div class="login-form-padding">
              <form method="POST" action="/admin/login" class="floating-labels">
                  <div class="form-group">
                      <input id="username" class="form-control" name="username" required="" type="text">
                      <span class="highlight"></span>
                      <span class="bar"></span>
                      <label for="username" class="color-gray">Username</label>
                      <i class="mdi mdi-email form-control-feedback color-gray"></i>
                  </div>
                  <div class="form-group">
                      <input id="password" class="form-control" name="password" required="" type="password">
                      <span class="highlight"></span>
                      <span class="bar"></span>
                      <label for="password" class="color-gray">Password</label>
                      <i class="mdi mdi-lock form-control-feedback color-gray"></i>
                  </div>
                  <!--div class="form-group" style="height: 38px">
                      <div class="checkbox checkbox-success">
                          <input id="remember" class="remember" name="remember" type="checkbox">
                          <label for="remember" class="color-light-gray">Keep me signed in</label>
                      </div>
                  </div-->
                  <button type="submit" class="btn btn-info waves-effect waves-light">Log In</button>

             </form>
          </div>
      </div>
  </div>
</div>


</body>
</html>