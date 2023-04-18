package com.shalimov.onlineShop;

import com.shalimov.onlineShop.dao.ProductDao;
import com.shalimov.onlineShop.dao.UserDao;
import com.shalimov.onlineShop.dao.jdbs.DataSourceFactory;
import com.shalimov.onlineShop.dao.jdbs.JdbcProductDao;
import com.shalimov.onlineShop.dao.jdbs.JdbcUserDao;
import com.shalimov.onlineShop.security.SecurityService;
import com.shalimov.onlineShop.security.impl.DefaultSecurityService;
import com.shalimov.onlineShop.service.ProductService;
import com.shalimov.onlineShop.service.UserService;
import com.shalimov.onlineShop.service.impl.DefaultProductService;
import com.shalimov.onlineShop.service.impl.DefaultUserService;
import com.shalimov.onlineShop.util.PropertyReader;
import com.shalimov.onlineShop.web.filter.AdminAuthenticationFilter;
import com.shalimov.onlineShop.web.filter.AuthenticationFilter;
import com.shalimov.onlineShop.web.filter.UserAuthenticationFilter;
import com.shalimov.onlineShop.web.servlet.*;
import com.shalimov.onlineShop.web.servlet.security.LoginServlet;
import com.shalimov.onlineShop.web.servlet.security.LogoutServlet;
import com.shalimov.onlineShop.web.servlet.security.RegisterUserServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.DispatcherType;
import java.util.EnumSet;
import java.util.Properties;


public class Starter {
    public static void main(String[] args) throws Exception {

        PropertyReader propertyReader = new PropertyReader("application.properties");
        Properties properties = propertyReader.readProperties();
        DataSourceFactory dataSource = new DataSourceFactory(properties);

        ProductDao productDao = new JdbcProductDao(dataSource.getDataSource());
        ProductService productService = new DefaultProductService(productDao);

        UserDao userDao = new JdbcUserDao(dataSource.getDataSource());
        UserService userService = new DefaultUserService(userDao);

        SecurityService securityService = new DefaultSecurityService(userService, properties.getProperty("password.parameter"));

        GetProductServlet getProductServlet = new GetProductServlet(productService);
        GetAllProductsServlet getAllProductsServlet = new GetAllProductsServlet(productService);
        LoginServlet loginServlet = new LoginServlet(securityService);
        LogoutServlet logoutServlet = new LogoutServlet(securityService);
        RegisterUserServlet registerUserServlet = new RegisterUserServlet(securityService);
        GetStaticResourcesServlet getStaticResourcesServlet = new GetStaticResourcesServlet();
        AddProductServlet addProductServlet = new AddProductServlet(productService);
        DeleteProductServlet deleteProductServlet = new DeleteProductServlet(productService);
        EditProductServlet editProductServlet = new EditProductServlet(productService);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context.addServlet(new ServletHolder(addProductServlet), "/add");
        context.addServlet(new ServletHolder(deleteProductServlet), "/delete");
        context.addServlet(new ServletHolder(editProductServlet), "/edit");
        context.addServlet(new ServletHolder(getProductServlet), "/product");
        context.addServlet(new ServletHolder(getAllProductsServlet), "/");
        context.addServlet(new ServletHolder(getStaticResourcesServlet), "/assets/*");
        context.addServlet(new ServletHolder(loginServlet), "/login");
        context.addServlet(new ServletHolder(registerUserServlet), "/register");
        context.addServlet(new ServletHolder(logoutServlet), "/logout");

        AdminAuthenticationFilter adminSecurityFilter = new AdminAuthenticationFilter();
        UserAuthenticationFilter userSecurityFilter = new UserAuthenticationFilter();
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(securityService);

        context.addFilter(new FilterHolder(authenticationFilter), "/", EnumSet.of(DispatcherType.REQUEST));
        context.addFilter(new FilterHolder(authenticationFilter), "/*", EnumSet.of(DispatcherType.REQUEST));

        context.addFilter(new FilterHolder(adminSecurityFilter), "/edit", EnumSet.of(DispatcherType.REQUEST));
        context.addFilter(new FilterHolder(adminSecurityFilter), "/add", EnumSet.of(DispatcherType.REQUEST));
        context.addFilter(new FilterHolder(adminSecurityFilter), "/delete", EnumSet.of(DispatcherType.REQUEST));

        context.addFilter(new FilterHolder(userSecurityFilter), "/cart", EnumSet.of(DispatcherType.REQUEST));
        context.addFilter(new FilterHolder(userSecurityFilter), "/cart/add", EnumSet.of(DispatcherType.REQUEST));

        Server server = new Server(8080);
        server.setHandler(context);
        server.start();
    }

}
