<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Cart" pageClass="product-list">
    <jsp:useBean id="cart" type="com.es.phoneshop.model.cart.Cart" scope="request"/>
    <c:if test="${not empty param.message}">
        <p class="success">${param.message}</p>
    </c:if>
    <c:if test="${not empty quantityErrors}">
        <p class="error">Failed to update cart.</p>
    </c:if>
    <form method="post" action="${pageContext.servletContext.contextPath}/cart">
        <p>
            <button>Update cart</button>
        </p>
        <table>
            <tr>
                <td></td>
                <td>Code</td>
                <td>Description</td>
                <td class="number">Price</td>
                <td class="number">Quantity</td>
                <td></td>
            </tr>
            <c:forEach var="item" items="${cart.cartItems}" varStatus="status">
                <tr>
                    <td>
                        <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${item.product.imageUrl}">
                    </td>
                    <td>${item.product.code}</td>
                    <td>${item.product.description}</td>
                    <td><fmt:formatNumber value="${item.product.price}" type="currency"
                                          currencySymbol="${item.product.currency.symbol}"/></td>
                    <td>
                        <input name="quantity"
                               value="${not empty quantityErrors[item.product.id] ? paramValues['quantity'][status.index] : item.quantity}"
                               class="number">
                        <input type="hidden" name="productId" value="${item.product.id}">
                        <c:if test="${not empty quantityErrors[item.product.id]}">
                            <p class="error">${quantityErrors[item.product.id]}</p>
                        </c:if>
                    </td>
                    <td>
                        <button formaction="${pageContext.servletContext.contextPath}/cart/${item.product.id}">Delete</button>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <p>
            <button>Update cart</button>
        </p>
    </form>
</tags:master>
<%--добавить кнопку перехода на карту(в хедере, чтобы на всех страницах была)

--%>