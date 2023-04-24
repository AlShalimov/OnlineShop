package com.shalimov.web.servlet;

import com.shalimov.entity.Product;
import com.shalimov.service.ProductService;
import com.shalimov.web.templeter.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class  AddProductServlet extends HttpServlet {
    private ProductService productService;
    private PageGenerator pageGenerator = PageGenerator.instance();

    public AddProductServlet(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=utf-8");
        pageGenerator.writePage(response.getWriter(), "add-product.html");
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
