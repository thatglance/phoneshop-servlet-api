<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="viewedProductList" type="java.util.List" scope="request"/>

<%--<hr class="hr">--%>
<hr style="color: aquamarine; height: 5px; background-color: aquamarine">
<h3>Recently viewed</h3>
<div class="outer-div">
<%--<div style="display: flex">--%>
    <c:forEach var="viewedProduct" items="${viewedProductList}">
        <%--<div class="inner-div">--%>
        <div style="display: flex;
            flex-direction: column;
            align-items: center;
            text-align: center;
            padding: 10px">
            <div>
                <img style="max-width: 64px" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${viewedProduct.imageUrl}">
            </div>
            <div><a href="${pageContext.servletContext.contextPath}/products/${viewedProduct.id}">${viewedProduct.description}</a></div>
            <div>$${viewedProduct.price}</div>
        </div>
    </c:forEach>
</div>