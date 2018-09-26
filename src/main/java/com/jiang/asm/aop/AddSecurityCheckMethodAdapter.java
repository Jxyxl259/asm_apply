package com.jiang.asm.aop;

import com.sun.xml.internal.ws.org.objectweb.asm.MethodAdapter;
import com.sun.xml.internal.ws.org.objectweb.asm.MethodVisitor;
import com.sun.xml.internal.ws.org.objectweb.asm.Opcodes;

/**
 * @ClassName AddSecurityCheckMethodAdapter
 * @Description
 * @Author jiangxy
 * @Date 2018\9\26 0026 16:55
 * @Version 1.0.0
 */
public class AddSecurityCheckMethodAdapter extends MethodAdapter {

    public AddSecurityCheckMethodAdapter(MethodVisitor mv) {
        super(mv);
    }

    /**
     * ClassReader读到每个方法的首部时调用 visitCode()，
     * 在这个重写方法里，我们用 visitMethodInsn(Opcodes.INVOKESTATIC, “SecurityChecker”,”checkSecurity”, “()V”);
     * 插入了SecurityCheck类的静态方法SecurityCheck ———— 安全检查功能(扩展功能)
     * 实现对字节码的修改
     */
    public void visitCode() {// 注：这里的 owner 的写法是通过"/"分隔的包路径，而非 "."
        visitMethodInsn(Opcodes.INVOKESTATIC, "com/jiang/asm/util/SecurityCheck", "checkSecurity", "()V");
    }
}
