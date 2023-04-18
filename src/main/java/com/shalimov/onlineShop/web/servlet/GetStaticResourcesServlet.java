package com.shalimov.onlineShop.web.servlet;


import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class GetStaticResourcesServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uri = request.getRequestURI();
        response.setContentType("text/css; charset=utf-8");
        String a = "templates" + uri;
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(a)) {
            if (inputStream == null) {
                log.error("File with path : {} not found", uri);
            } else {
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                int nRead;//from  w  ww . j  av a  2  s.c om
                byte[] bytes = new byte[16384];

                while ((nRead = inputStream.read(bytes, 0, bytes.length)) != -1) {
                    buffer.write(bytes, 0, nRead);
                }
                buffer.flush();
                bytes = buffer.toByteArray();
                response.setContentLength(bytes.length);
                response.getOutputStream().write(bytes, 0, bytes.length);
            }
        }
    }
}

