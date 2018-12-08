<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.product.Product" scope="request"/>
<jsp:useBean id="viewedProductList" type="java.util.List" scope="request"/>

<html>
<head>
    <title>Product Details</title>
    <jsp:include page="links.jsp"/>
    <style type="text/css">
        .success {
            color: green;
        }

        .error {
            color: red;
        }

        .hr {
            color: #8f03cb;
        }

        .outer-div {
            display: flex;
        }

        .inner-div {
            display: flex;
            flex-direction: column;
            align-items: center;
            text-align: center;
            padding: 10px;
        }

        .inner-div img {
            max-width: 64px;
        }
    </style>
</head>
<body>
<jsp:include page="header.jsp"/>
<main>
    <br>
    <p>Cart: ${cart}</p>
    <c:if test="${not empty param.message}">
        <p class="success">${param.message}</p>
    </c:if>
    <table>
        <tr>
            <td>
                <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
            </td>
            <td>
                <h1>${product.description}</h1>
                <p>Code: ${product.code}</p>
                <p>Stock: ${product.stock}</p>
                <p>Price: <fmt:formatNumber value="${product.price}" type="currency"
                                            currencySymbol="${product.currency.symbol}"/></p>
                <form method="post" action="${pageContext.servletContext.contextPath}/products/${product.id}">
                    <input name="quantity" value="${not empty param.quantity ? param.quantity : 1}" class="number">
                    <button>Add to cart</button>
                    <c:if test="${not empty quantityError}">
                        <p class="error">${quantityError}</p>
                    </c:if>
                </form>
            </td>
        </tr>
    </table>
</main>
<hr class="hr">
<h3>Recently viewed</h3>
<div class="outer-div">
    <c:forEach var="viewedProduct" items="${viewedProductList}">
        <div class="inner-div">
            <div>
                <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${viewedProduct.imageUrl}">
            </div>
            <div><a href="${pageContext.servletContext.contextPath}/products/${viewedProduct.id}">${viewedProduct.description}</a></div>
            <div>$${viewedProduct.price}</div>
        </div>
    </c:forEach>
</div>
</body>
</html>