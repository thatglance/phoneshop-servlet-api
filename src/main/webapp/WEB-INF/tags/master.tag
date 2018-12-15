<%@ attribute name="pageTitle" type="java.lang.String" required="true" %>
<%@ attribute name="pageClass" type="java.lang.String" required="false" %>
<html>
<head>
    <title>${pageTitle}</title>
    <link href='http://fonts.googleapis.com/css?family=Lobster+Two' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/styles/main.css">
</head>
<body class="${pageClass}">
<header>
    <a href="${pageContext.servletContext.contextPath}">
        <%--<img src="${pageContext.servletContext.contextPath}/images/logo.svg"/>--%>
        PhoneShop
    </a>
</header>
<main>
    <br>
    <form method="get" action="${pageContext.servletContext.contextPath}/cart">
        <button>Go to cart</button>
    </form>
    <jsp:doBody/>
</main>
</body>
</html>
