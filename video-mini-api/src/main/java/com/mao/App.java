package com.mao;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages={"com.mao.mapper"})
@ComponentScan(basePackages = {"com.mao","org.n3r.idworker"})
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
