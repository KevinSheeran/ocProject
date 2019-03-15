package com.online.college.common.web.shiro;

import com.online.college.common.web.shiro.freemarker.ShiroTags;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
public class FreeMarkerConfig {

    @Bean(name = "freeMarkerConfigurer")
    public FreeMarkerConfigurer freeMarkerConfigurer()  {
        ShiroTags shiroTag=new ShiroTags();
        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
        configurer.setTemplateLoaderPath("classpath:/templates");
        Map<String, Object> variables = new HashMap<>(1);
        //将权限放在shiro
        variables.put("shiro", shiroTag);
        configurer.setFreemarkerVariables(variables);
        System.out.println();
        Properties settings = new Properties();
        settings.setProperty("default_encoding", "utf-8");
        settings.setProperty("number_format", "0.######");
        settings.setProperty("tag_syntax", "auto_detect");
        settings.setProperty("template_update_delay", "1");
        settings.setProperty("url_escaping_charset", "UTF-8");
        settings.setProperty("locale", "zh_CN");
        settings.setProperty("boolean_format", "true,false");
        settings.setProperty("datetime_format", "yyyy-MM-dd HH:mm:ss");
        settings.setProperty("date_format", "yyyy-MM-dd");
        settings.setProperty("time_format", "HH:mm:ss");
        settings.setProperty("whitespace_stripping", "true");
        settings.setProperty("auto_import", "ftl/spring.ftl as s");
        configurer.setFreemarkerSettings(settings);
        return configurer;
    }
}
