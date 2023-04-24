package com.shalimov.web.servlet;

import com.shalimov.entity.Product;
import com.shalimov.service.ProductService;
import com.shalimov.web.templeter.PageGenerator;

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
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        long id = Long.parseLong(request.getParameter("id"));
        Product product = productService.getProductById(id);
        Map<String, Object> mapProduct = new HashMap<>();
        mapProduct.put("product", product);
        response.setContentType("text/html; charset=utf-8");
        response.getWriter().println(pageGenerator.getPage("product.html", mapProduct));
    }
}
