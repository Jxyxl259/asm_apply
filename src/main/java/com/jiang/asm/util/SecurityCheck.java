package com.jiang.asm.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @ClassName SecurityCheck
 * @Description
 * @Author jiangxy
 * @Date 2018\9\26 0026 16:28
 * @Version 1.0.0
 */
@Component
public class SecurityCheck {

    private static Logger log = LoggerFactory.getLogger(SecurityCheck.class);

    /**
     * 此方法希望在账户进行操作前进行一些必要的安全检查
     * 与主业务逻辑不相干，横向扩展的需要解耦的 功能代码，像日志输出/事务
     */
    public static void checkSecurity(){

        log.info("security check...");
        // TODO The real security check work...
    }

}
