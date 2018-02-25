<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Error page</title>
</head>
<body>

<p>Application has encountered an error. Please contact support on ...</p>
<p>Failed URL: ${url}</p>
<p>Exception:  ${message}</p>
<p>ErrorCode:   ${code}</p>
</body>
</html>