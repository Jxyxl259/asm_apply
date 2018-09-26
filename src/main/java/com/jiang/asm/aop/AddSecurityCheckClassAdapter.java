package com.jiang.asm.aop;


import com.sun.xml.internal.ws.org.objectweb.asm.ClassAdapter;
import com.sun.xml.internal.ws.org.objectweb.asm.ClassVisitor;
import com.sun.xml.internal.ws.org.objectweb.asm.MethodVisitor;

/**
 * @ClassName AddSecurityCheckClassAdapter
 * @Description
 * @Author jiangxy
 * @Date 2018\9\26 0026 16:43
 * @Version 1.0.0
 */
public class AddSecurityCheckClassAdapter extends ClassAdapter {

    private String enhancedSuperName;

    public AddSecurityCheckClassAdapter(ClassVisitor cv) {
        //Responsechain 的下一个 ClassVisitor，这里我们将传入 ClassWriter，
        // 负责改写后代码的输出
        super(cv);
    }

    /** 访问待增强的类 */
    @Override
    public void visit(final int version, final int access, final String name,
                      final String signature, final String superName, final String[] interfaces) {
        String enhancedName = name + "$SecurityCheckAccountEnhancedByASM";  // 改变类命名
        enhancedSuperName = name; // 改变父类，这里是”Account”
        super.visit(version, access, enhancedName, signature, enhancedSuperName, interfaces);
    }


    /** 对待增强类的特定方法实现增强 */
    @Override
    public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, final String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
        MethodVisitor wrappedMv = mv;
        if (mv != null) {
            if (name.equals("deposit")) {
                wrappedMv = new AddSecurityCheckMethodAdapter(mv);
            } else if (name.equals("<init>")) {
                /* <init>是最终得到的增强类是待增强类的子类，而不是对待增强类字节码做出的变更
                 *  这样更符合AOP的思想（产生一个新的增强类，而非改动原有类），
                 */
                wrappedMv = new ChangeToChildConstructorMethodAdapter(mv, enhancedSuperName);
            }
        }
        return wrappedMv;
    }

}
