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


public class GetProductServlet extends HttpServlet {
    private PageGenerator pageGenerator = PageGenerator.instance();
    private ProductService productService;

    public GetProductServlet(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idString = request.getParameter("id");
        if (idString == null) {
            throw new RuntimeException("id not received");
        }
        long id = Long.parseLong(request.getParameter("id"));
        Product product = productService.getProductById(id);
        Map<String, Object> mapProduct = new HashMap<>();
        mapProduct.put("product", product);
        response.setContentType("text/html; charset=utf-8");
        response.getWriter().println(pageGenerator.getPage("product.html", mapProduct));
    }
}
