<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<jsp:useBean id="order" type="com.es.phoneshop.model.order.Order" scope="request"/>

<tags:master pageTitle="Order overview" pageClass="product-list">
    <p class="success">Thank you for your order!</p>
    <table>
        <tr>
            <td></td>
            <td>Code</td>
            <td>Description</td>
            <td class="number">Price</td>
            <td class="number">Quantity</td>
        </tr>
        <c:forEach var="item" items="${order.cartItems}" varStatus="status">
            <tr>
                <td>
                    <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${item.product.imageUrl}">
                </td>
                <td>${item.product.code}</td>
                <td>${item.product.description}</td>
                <td><fmt:formatNumber value="${item.product.price}" type="currency" currencySymbol="${item.product.currency.symbol}"/></td>
                <td>
                        ${item.quantity}
                </td>
            </tr>
        </c:forEach>
        <tr>
            <td></td>
            <td></td>
            <td>Total:</td>
            <td>
                <fmt:formatNumber value="${order.totalPrice}" type="currency" currencySymbol="${order.currency.symbol}"/>
            </td>
            <td></td>
        </tr>
    </table>
    <br>
    <p>Name: ${order.name}</p>
    <p>Delivery Address: ${order.deliveryAddress}</p>
    <p>Phone: ${order.phone}</p>
</tags:master>
