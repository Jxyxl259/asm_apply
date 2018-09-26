package com.jiang.asm.aop;

import com.sun.xml.internal.ws.org.objectweb.asm.MethodAdapter;
import com.sun.xml.internal.ws.org.objectweb.asm.MethodVisitor;
import com.sun.xml.internal.ws.org.objectweb.asm.Opcodes;

/**
 * @ClassName ChangeToChildConstructorMethodAdapter
 * @Description
 * @Author jiangxy
 * @Date 2018\9\26 0026 16:53
 * @Version 1.0.0
 */
/* 该类的作用是负责把 Account 的构造函数改造成其子类 Account$SecurityCheckAccountEnhancedByASM 的构造函数 */
public class ChangeToChildConstructorMethodAdapter extends MethodAdapter {

    private String superClassName;

    public ChangeToChildConstructorMethodAdapter(MethodVisitor mv, String superClassName) {
        super(mv);
        this.superClassName = superClassName;
    }

    public void visitMethodInsn(int opcode, String owner, String name, String desc) {
        // 调用父类的构造函数时
        if (opcode == Opcodes.INVOKESPECIAL && name.equals("<init>")) {
            owner = superClassName;
        }
        super.visitMethodInsn(opcode, owner, name, desc);// 改写父类为 superClassName
    }

}
