package com.shalimov.onlineShop.web.servlet;

import com.shalimov.onlineShop.entity.Product;
import com.shalimov.onlineShop.service.ProductService;
import com.shalimov.onlineShop.web.templeter.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EditProductServlet extends HttpServlet {
    private ProductService productService;
    private PageGenerator pageGenerator = PageGenerator.instance();

    public EditProductServlet(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idString = request.getParameter("id");
        if (idString == null) {
            throw new RuntimeException("id not received");
        }
        long id = Long.parseLong(idString);
        Product product = productService.getProductById(id);
        Map<String, Object> productParameters = new HashMap<>();
        productParameters.put("product", product);
        response.setContentType("text/html; charset=utf-8");
        response.getWriter().println(pageGenerator.getPage("edit-product.html", productParameters));
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long id = Long.parseLong(request.getParameter("id"));
        double price = Double.parseDouble(request.getParameter("price"));
        Product product = new Product();
        product.setId(id);
        product.setPrice(price);
        product.setName(request.getParameter("name"));
        product.setDescription(request.getParameter("description"));
        productService.editProduct(product);
        response.setContentType("text/html; charset=utf-8");
        response.sendRedirect("products");
    }
}