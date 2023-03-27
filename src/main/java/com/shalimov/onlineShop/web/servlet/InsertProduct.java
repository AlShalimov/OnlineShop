package com.shalimov.onlineShop.web.servlet;

import com.shalimov.onlineShop.entity.Product;
import com.shalimov.onlineShop.service.ProductService;
import com.shalimov.onlineShop.web.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class InsertProduct extends HttpServlet {
    private PageGenerator pageGenerator ;
    private ProductService productService ;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().println(pageGenerator.getPage("add.html"));
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        double price = Double.parseDouble(request.getParameter("price"));

        Product product = new Product();
        product.setPrice(price);
        product.setName(request.getParameter("name"));
        product.setDescription(request.getParameter("description"));

        productService.insert(product);
        response.setContentType("text/html;charset=utf-8");
        response.sendRedirect("products");

    }
}