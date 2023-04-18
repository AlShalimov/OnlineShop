package com.shalimov.onlineShop.web.servlet;

import com.shalimov.onlineShop.entity.Product;
import com.shalimov.onlineShop.service.ProductService;
import com.shalimov.onlineShop.web.templeter.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetAllProductsServlet extends HttpServlet {
    private ProductService productService;
    private PageGenerator pageGenerator = PageGenerator.instance();

    public GetAllProductsServlet(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Product> products;
        String searchWord = request.getParameter("search");

        if (searchWord != null && !searchWord.equals("")) {
            products = productService.searchProducts(searchWord);
        } else {
            products = productService.getAllProducts();
        }
        Map<String, Object> mapProducts = new HashMap<>();
        mapProducts.put("products", products);
        response.setContentType("text/html; charset=utf-8");
        response.getWriter().println(pageGenerator.getPage("all-products.html", mapProducts));
    }
}
