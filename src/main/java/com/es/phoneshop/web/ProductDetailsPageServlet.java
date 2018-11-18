package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Currency;

public class ProductDetailsPageServlet extends HttpServlet {
    private ArrayListProductDao dao;

    @Override
    public void init() throws ServletException {
        super.init();
        dao = ArrayListProductDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        int lastSlashIndex = uri.lastIndexOf("/");
        String id = uri.substring(lastSlashIndex + 1);
        request.setAttribute("product", dao.getProduct(Long.valueOf(id)));
        request.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(request, response);
    }
}
