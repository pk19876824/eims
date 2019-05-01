package com.junjie.eims;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.junjie.eims.dao")
public class EimsApplication {

  public static void main(String[] args) {
    SpringApplication.run(EimsApplication.class, args);
  }

}
