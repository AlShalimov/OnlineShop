package com.shalimov.onlineShop.web.servlet;

import com.shalimov.onlineShop.entity.Product;
import com.shalimov.onlineShop.service.ProductService;
import com.shalimov.onlineShop.web.PageGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class EditProduct extends HttpServlet {
    private final PageGenerator pageGenerator;
    private final ProductService productService;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long id = Long.parseLong(request.getParameter("id"));
        Product product = productService.getById(id);
        Map<String, Object> mapProduct = new HashMap<>();
        mapProduct.put("product", product);
        response.setContentType("text/html; charset=utf-8");
        response.getWriter().println(pageGenerator.getPage("edit.html", mapProduct));
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long id = Long.parseLong(request.getParameter("id"));
        double price = Double.parseDouble(request.getParameter("price"));
        Product product = new Product();
        product.setId(id);
        product.setPrice(price);
        product.setName(request.getParameter("name"));
        product.setDescription(request.getParameter("description"));
        productService.edit(product);
        response.setContentType("text/html; charset=utf-8");
        response.sendRedirect("products");
    }

}
