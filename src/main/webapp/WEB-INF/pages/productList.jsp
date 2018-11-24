<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<html>
<head>
    <title>Product List</title>
    <jsp:include page="links.jsp"/>
</head>
<body class="product-list">
<jsp:include page="header.jsp"/>
<main>
    <p>
        Welcome to Expert-Soft training!
    </p>
    <form>
        <input name="query" value="${param.query}">
        <button>Search</button>
    </form>
    <table>
        <thead>
        <tr>
            <td>Image</td>
            <td>
                Description
                <a href="${pageContext.servletContext.contextPath}/products?sortField=description&sortMode=asc&query=${param.query}">asc</a>
                <a href="${pageContext.servletContext.contextPath}/products?sortField=description&sortMode=desc&query=${param.query}">desc</a>
            </td>
            <td class="price">
                Price
                <a href="${pageContext.servletContext.contextPath}/products?sortField=price&sortMode=asc&query=${param.query}">asc</a>
                <a href="${pageContext.servletContext.contextPath}/products?sortField=price&sortMode=desc&query=${param.query}">desc</a>
            </td>
        </tr>
        </thead>
        <c:forEach var="product" items="${products}">
            <tr>
                <td>
                    <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
                </td>
                <td>
                    <a href="${pageContext.servletContext.contextPath}/products/${product.id}">${product.description}</a>
                </td>
                <td class="price">
                    <fmt:formatNumber value="${product.price}" type="currency"
                                      currencySymbol="${product.currency.symbol}"/>
                </td>
            </tr>
        </c:forEach>
    </table>
</main>
</body>
</html>