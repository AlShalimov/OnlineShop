package com.shalimov.web.templeter;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

public class PageGenerator {
    private static final String HTML_DIR = "/templates";

    private static PageGenerator pageGenerator;
    private final Configuration cfg;

    public static PageGenerator instance() {
        if (pageGenerator == null)
            pageGenerator = new PageGenerator();
        return pageGenerator;
    }

    public String getPage(String filename, Map<String, Object> data) {
        Writer stream = new StringWriter();
        try {
            cfg.setClassForTemplateLoading(this.getClass(), HTML_DIR);
            cfg.setDefaultEncoding("UTF-8");
            Template template = cfg.getTemplate(filename);
            template.process(data, stream);
        } catch (IOException | TemplateException e) {
            throw new RuntimeException(e);
        }
        return stream.toString();
    }

    public void writePage(Writer writer, String templatePath) {
        try {
            cfg.setClassForTemplateLoading(this.getClass(), HTML_DIR);
            cfg.setDefaultEncoding("UTF-8");
            Template template = cfg.getTemplate(templatePath);
            template.process(null, writer);
        } catch (IOException | TemplateException e) {
            throw new RuntimeException(e);
        }
    }

    private PageGenerator() {
        cfg = new Configuration();
    }
}
