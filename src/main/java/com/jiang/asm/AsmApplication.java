package com.jiang.asm;

import com.jiang.asm.entity.Account;
import com.jiang.asm.util.SecureAccountGenerator;
import com.jiang.asm.util.SecurityCheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

//@SpringBootApplication
@Configuration
@ComponentScan("com.jiang.asm")
public class AsmApplication {


    private static Logger log = LoggerFactory.getLogger(AsmApplication.class);


    public static void main(String[] args) {

        ApplicationContext ioc = new AnnotationConfigApplicationContext(AsmApplication.class);

        log.info("----------------------- 原始 Account 类，执行存款操作 ---------------------------");

        Account a1 = ioc.getBean("account",Account.class);
        log.info(a1.toString());

        a1.deposit();

        log.info("----------------------- 通过ASM 生成带有安全检查的 Account 增强类，执行存款操作 ---------------------------");

        Account a2 = null;
        try {
            SecureAccountGenerator secAcctGenerator = ioc.getBean("secAcctGenerator", SecureAccountGenerator.class);

            Account account = secAcctGenerator.generateSecureAccount();
            log.info(account.toString());
            a2 = account;
        } catch (Exception e) {
            e.printStackTrace();
        }

        a2.deposit();

        log.info("----------------------- 原始 Account 类，再次执行存款操作 ---------------------------");

        a1.deposit();
    }


}
