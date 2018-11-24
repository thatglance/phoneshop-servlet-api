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
        String stringId = uri.substring(lastSlashIndex + 1);
        try {
            Long id = Long.valueOf(stringId);
            Product product = dao.getProduct(id);
            if (product != null) {
                request.setAttribute("product", product);
                request.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(request, response);
            } else {
                throw new IllegalArgumentException("Product is not found!");
            }
        } catch (IllegalArgumentException e) {
            response.sendError(404);
        }
    }
}
