<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.product.Product" scope="request"/>
<jsp:useBean id="viewedProductList" type="java.util.List" scope="request"/>

<tags:master pageTitle="${product.description}">
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
    <jsp:include page="recentlyViewed.jsp"/>
</tags:master>
