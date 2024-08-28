package com.soguk.soguk.utils;

import com.soguk.soguk.utils.JwtRequestFilter;
import com.soguk.soguk.utils.JwtUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
//Bu sınıfı jwtUtil ve jwtReqFilter'ın bean kontrolünü loglamak için yaptım geçici bir sınıf

@Component
public class BeanCheck {

    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    public void checkBeans() {
        System.out.println("JwtUtil bean: " + applicationContext.getBean(JwtUtil.class));
        System.out.println("JwtRequestFilter bean: " + applicationContext.getBean(JwtRequestFilter.class));
    }
}
