package com.jiang.asm.util;

import com.jiang.asm.aop.AddSecurityCheckClassAdapter;
import com.jiang.asm.entity.Account;
import com.sun.xml.internal.ws.org.objectweb.asm.ClassAdapter;
import com.sun.xml.internal.ws.org.objectweb.asm.ClassReader;
import com.sun.xml.internal.ws.org.objectweb.asm.ClassWriter;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @ClassName SecureAccountGenerator
 * @Description
 * @Author jiangxy
 * @Date 2018\9\26 0026 17:04
 * @Version 1.0.0
 */
@Component("secAcctGenerator")
public class SecureAccountGenerator {

    private static AccountGeneratorClassLoader classLoader = new AccountGeneratorClassLoader();

    private static Class secureAccountClass;

    public Account generateSecureAccount() throws ClassFormatError, InstantiationException, IllegalAccessException, IOException {
        if (null == secureAccountClass) {

            // 将需要通过 ASM 增强的类通过 ClassReader进行封装
            ClassReader cr = new ClassReader("com.jiang.asm.entity.Account");
            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
            ClassAdapter classAdapter = new AddSecurityCheckClassAdapter(cw);
            cr.accept(classAdapter, ClassReader.SKIP_DEBUG);

            // ASM增强后的 .class 字节码 字节数组
            byte[] data = cw.toByteArray();
            secureAccountClass = classLoader.defineClassFromClassFile("com.jiang.asm.entity.Account$SecurityCheckAccountEnhancedByASM", data);
        }
        // 返回增强后的类的实例
        return (Account) secureAccountClass.newInstance();
    }


    /**
     * 自定义类的加载器，实现在运行期间加载通过 ASM 增强后的 .class字节码文件
     */
    private static class AccountGeneratorClassLoader extends ClassLoader {

        public Class defineClassFromClassFile(String className, byte[] classFile) throws ClassFormatError {
            // 定义一个类 返回对象是 Class 类型
            return defineClass("com.jiang.asm.entity.Account$SecurityCheckAccountEnhancedByASM", classFile, 0, classFile.length);
        }
    }

}
