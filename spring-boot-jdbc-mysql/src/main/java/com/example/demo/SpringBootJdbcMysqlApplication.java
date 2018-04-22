package com.example.demo;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.dao.CustomerRepository;
import com.example.demo.model.Customer;

@SpringBootApplication
public class SpringBootJdbcMysqlApplication {


	
    
    
	public static void main(String[] args) {
		SpringApplication.run(SpringBootJdbcMysqlApplication.class, args);
	}
}
