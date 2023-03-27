package com.shalimov.onlineShop.web.servlet;

import com.shalimov.onlineShop.entity.Product;
import com.shalimov.onlineShop.service.ProductService;
import com.shalimov.onlineShop.web.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowAllProducts extends HttpServlet {
    private PageGenerator pageGenerator;
    private ProductService productService;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Product> products = productService.getAll();
        Map<String, Object> mapProducts = new HashMap<>();
        mapProducts.put("products", products);
        response.setContentType("text/html; charset=utf-8");
        response.getWriter().println(pageGenerator.getPage("index.html", mapProducts));
    }
}
