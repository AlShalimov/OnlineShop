package com.shalimov.onlineShop;

import com.shalimov.onlineShop.dao.ProductDao;
import com.shalimov.onlineShop.dao.jdbs.DataSourceFactory;
import com.shalimov.onlineShop.dao.jdbs.JdbcProductDao;
import com.shalimov.onlineShop.service.ProductService;
import com.shalimov.onlineShop.service.impl.DefaultProductService;
import com.shalimov.onlineShop.util.PropertyReader;
import com.shalimov.onlineShop.web.servlet.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.util.Properties;


public class Starter {
    public static void main(String[] args) throws Exception {

        PropertyReader propertyReader = new PropertyReader("application.properties");
        Properties properties = propertyReader.readProperties();
        DataSourceFactory dataSource = new DataSourceFactory(properties);

        ProductDao productDao = new JdbcProductDao(dataSource.getDataSource());
        ProductService productService = new DefaultProductService(productDao);

        AddProductServlet addProductServlet = new AddProductServlet(productService);
        DeleteProductServlet deleteProductServlet = new DeleteProductServlet(productService);
        EditProductServlet editProductServlet = new EditProductServlet(productService);
        GetProductServlet getProductServlet = new GetProductServlet(productService);
        GetAllProductsServlet getAllProductsServlet = new GetAllProductsServlet(productService);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(addProductServlet), "/add");
        context.addServlet(new ServletHolder(deleteProductServlet), "/delete");
        context.addServlet(new ServletHolder(editProductServlet), "/edit");
        context.addServlet(new ServletHolder(getProductServlet), "/product");
        context.addServlet(new ServletHolder(getAllProductsServlet), "/");

        Server server = new Server(8080);
        server.setHandler(context);
        server.start();
    }

}
