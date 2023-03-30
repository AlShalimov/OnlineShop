package com.shalimov.onlineShop.web.servlet;

import com.shalimov.onlineShop.entity.Product;
import com.shalimov.onlineShop.service.ProductService;
import com.shalimov.onlineShop.web.templeter.PageGenerator;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AddProductServlet extends HttpServlet {
    private ProductService productService;
    private PageGenerator pageGenerator = PageGenerator.instance();

    public AddProductServlet(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long id = Long.parseLong(request.getParameter("id"));
        Product product = productService.getProductById(id);
        Map<String, Object> mapProduct = new HashMap<>();
        mapProduct.put("product", product);
        response.setContentType("text/html; charset=utf-8");
        response.getWriter().println(pageGenerator.getPage("add-product.html", mapProduct));
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        String priceString = request.getParameter("price");
        double price = 0;
        if (!priceString.equals("")) {
            price = Double.parseDouble(priceString);
        }
        String description = request.getParameter("description");

        Product product = Product.builder()
                .name(name)
                .price(price)
                .description(description).build();

        productService.addProduct(product);
        response.sendRedirect("/");
    }
}
