<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.product.Product" scope="request"/>
<html>
<head>
    <title>Product Details</title>
    <jsp:include page="links.jsp"/>
</head>
<body class="product-list">
<jsp:include page="header.jsp"/>
<main>
    <p>
        Welcome to Expert-Soft training!
    </p>
    <table>
        <tr>
            <td>Image</td>
            <td>
                <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
            </td>
        </tr>
        <tr>
            <td>Description</td>
            <td>${product.description}</td>
        </tr>
        <tr>
            <td class="price">Price</td>
            <td class="price">
                <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
            </td>
        </tr>
        <tr>
            <td>Product Code</td>
            <td>${product.code}</td>
        </tr>
        <tr>
            <td>Product Stock</td>
            <td>${product.stock}</td>
        </tr>
    </table>
</main>
</body>
</html>