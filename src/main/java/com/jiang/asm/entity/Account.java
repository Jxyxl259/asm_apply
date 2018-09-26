package com.jiang.asm.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @ClassName Account
 * @Description
 * @Author jiangxy
 * @Date 2018\9\26 0026 16:27
 * @Version 1.0.0
 */
@Component
public class Account {

    public static Logger log = LoggerFactory.getLogger(Account.class);

    /**
     * 存钱操作,希望在存钱操作之前执行安全检查
     */
    public void deposit(){
        log.info("user deposit...");
        // TODO The real deposit operate...
    }

}
