package com.soguk.soguk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/*
(WebMvcConfigurer)Bu sınıfı implemente ederek, web yapılandırması üzerinde ince ayarlar yapabilirsin.
Burada özellikle CORS yapılandırmasını yönetmek için kullanılıyor.Yani aşağıda verilen originsden backend için verilen methodlara ulaşabilir
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:63342","http://localhost:8080")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}