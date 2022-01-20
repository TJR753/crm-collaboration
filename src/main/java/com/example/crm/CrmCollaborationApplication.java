package com.example.crm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication
@MapperScan(basePackages = {"com.example.crm.settings.dao","com.example.crm.workbench.dao"})
public class CrmCollaborationApplication {
    public static void main(String[] args) {
        SpringApplication.run(CrmCollaborationApplication.class, args);
    }

}
