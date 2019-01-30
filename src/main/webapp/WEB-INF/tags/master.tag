<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="pageTitle" type="java.lang.String" required="true" %>
<%@ attribute name="pageClass" type="java.lang.String" required="false" %>
<%@ attribute name="showMiniCart" type="java.lang.String" required="false" %>
<%@ attribute name="showQuickOrder" type="java.lang.String" required="false" %>
<jsp:useBean id="cart" type="com.es.phoneshop.model.cart.Cart" scope="request"/>
<html>
<head>
    <title>${pageTitle}</title>
    <link href='http://fonts.googleapis.com/css?family=Lobster+Two' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/styles/main.css">
</head>
<body class="${pageClass}">
<header>
    <a href="${pageContext.servletContext.contextPath}">
        PhoneShop
    </a>
    <c:if test="${showMiniCart eq 'yes'}">
        <a href="${pageContext.servletContext.contextPath}/cart">Cart: ${cart.totalPrice}</a>
    </c:if>
    <%--<c:if test="${showQuickOrder eq 'yes'}">
        <a href="${pageContext.servletContext.contextPath}/quickOrder">Quick order</a>
    </c:if>--%>
</header>
<main>
    <%--<br>
    <form method="getEntity" action="${pageContext.servletContext.contextPath}/cart">
        <button>Go to cart</button>
    </form>--%>
    <jsp:doBody/>
</main>
</body>
</html>
