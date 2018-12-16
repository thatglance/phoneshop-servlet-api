<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Checkout" pageClass="product-list">
    <jsp:useBean id="cart" type="com.es.phoneshop.model.cart.Cart" scope="request"/>
    <form method="post" action="${pageContext.servletContext.contextPath}/checkout">
        <p>
            <button>Place order</button>
        </p>
        <table>
            <tr>
                <td></td>
                <td>Code</td>
                <td>Description</td>
                <td class="number">Price</td>
                <td class="number">Quantity</td>
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
                        ${item.quantity}
                    </td>
                </tr>
            </c:forEach>
            <tr>
                <td></td>
                <td></td>
                <td>Total:</td>
                <td><fmt:formatNumber value="${cart.totalPrice}" type="currency"
                                      currencySymbol="${cart.currency.symbol}"/></td>
                <td></td>
            </tr>
        </table>
        <c:if test="${not empty inputError}">
            <p class="error">${inputError}</p>
        </c:if>
        <br>
        <input name="name" placeholder="Name" value="${param.name}">
        <br>
        <br>
        <input name="deliveryAddress" placeholder="Delivery Address" value="${param.deliveryAddress}">
        <br>
        <br>
        <input name="phone" placeholder="Phone" value="${param.phone}">
        <p>
            <button>Place order</button>
        </p>
    </form>
</tags:master>
