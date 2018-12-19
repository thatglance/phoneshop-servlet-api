<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="popularProductList" type="java.util.List" scope="request"/>

<hr class="hr">
<h3>Most popular products</h3>
<div class="outer-div">
    <c:forEach var="popularProduct" items="${popularProductList}" end="2">
        <div style="display: flex;
            flex-direction: column;
            align-items: center;
            text-align: center;
            padding: 10px">
            <div>
                <img style="max-width: 64px" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${popularProduct.imageUrl}">
            </div>
            <div><a href="${pageContext.servletContext.contextPath}/products/${popularProduct.id}">${popularProduct.description}</a></div>
            <div>$${popularProduct.price}</div>
        </div>
    </c:forEach>
</div>