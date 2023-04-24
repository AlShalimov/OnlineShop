package com.shalimov;

import com.shalimov.dao.UserDao;
import com.shalimov.dao.jdbc.DataSourceFactory;
import com.shalimov.dao.jdbc.JdbcProductDao;
import com.shalimov.dao.jdbc.JdbcUserDao;
import com.shalimov.dao.ProductDao;
import com.shalimov.security.SecurityService;
import com.shalimov.security.impl.DefaultSecurityService;
import com.shalimov.service.ProductService;
import com.shalimov.service.UserService;
import com.shalimov.service.impl.DefaultProductService;
import com.shalimov.service.impl.DefaultUserService;
import com.shalimov.util.PropertyReader;
import com.shalimov.web.filter.AdminAuthenticationFilter;
import com.shalimov.web.filter.AuthenticationFilter;
import com.shalimov.web.filter.UserAuthenticationFilter;
import com.shalimov.web.servlet.*;
import com.shalimov.web.servlet.security.LoginServlet;
import com.shalimov.web.servlet.security.LogoutServlet;
import com.shalimov.web.servlet.security.RegisterUserServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.flywaydb.core.Flyway;
import org.postgresql.ds.PGSimpleDataSource;

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

        final String jdbcUrl = properties.getProperty("jdbc.url");
        final String jdbcUser = properties.getProperty("jdbc.user");
        final String jdbcPassword = properties.getProperty("jdbc.password");
        Flyway flyway = Flyway.configure().dataSource(jdbcUrl, jdbcUser, jdbcPassword)
                .load();
        flyway.migrate();

        SecurityService securityService = new DefaultSecurityService(userService,properties);

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
