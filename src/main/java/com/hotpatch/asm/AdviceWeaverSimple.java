package com.hotpatch.asm;

import org.apache.log4j.Logger;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;
import org.objectweb.asm.commons.JSRInlinerAdapter;
import org.objectweb.asm.commons.Method;

/**
 * Created by wuzhong on 2017/11/2.
 */
public class AdviceWeaverSimple extends ClassVisitor implements Opcodes {
    private final static Logger logger = Logger.getLogger(Enhancer.class);

    public AdviceWeaverSimple(final ClassVisitor cv){
        super(ASM5, cv);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        if (isIgnore(mv, access, name, desc)) {
            return mv;
        }
//        if (name.equals("getNumber")) {
//            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
//            mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
//            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(J)V", false);
//        }
//        return mv;
          return new AdviceAdapter(ASM5, new JSRInlinerAdapter(mv, access, name, desc, signature, exceptions), access, name, desc){
            private final Type ASM_TYPE_SPY = Type.getType("LagentTest/Spy;");
            private final Type ASM_TYPE_METHOD = Type.getType(java.lang.reflect.Method.class);
            private final Method ASM_METHOD_METHOD_INVOKE = Method.getMethod("Object invoke(Object,Object[])");

            @Override
            public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
                logger.info("visitMethodInsn begin name:"+opcode+":"+owner+":"+name+":"+desc+":"+itf);
                mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
                //mv.visitLdcInsn("begin");
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(J)V", false);

                super.visitMethodInsn(opcode, owner, name, desc, itf);
                mv.visitMethodInsn(INVOKESTATIC, "agentTest/Spy", "log", "()V", false);

                mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
//                mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
//                mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(J)V", false);
                mv.visitLdcInsn("after");
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
                logger.info("visitMethodInsn end name:"+name);
            }

            @Override
            protected void onMethodEnter() {
            }
            @Override
            protected void onMethodExit(int opcode) {
            }

            /**
             * 加载通知方法
             */
            private void loadAdviceMethod() {
                getStatic(ASM_TYPE_SPY, "ON_ASM_METHOD", ASM_TYPE_METHOD);
                invokeVirtual(ASM_TYPE_METHOD, ASM_METHOD_METHOD_INVOKE);
            }
        };
    }
    /**
     * 是否需要忽略
     */
    private boolean isIgnore(MethodVisitor mv, int access, String name, String desc) {
        return null == mv
                || isAbstract(access)
                || isEquals(name, "<init>")
                || isEquals(name, "<clinit>");
    }
    /**
     * 是否抽象属性
     */
    private boolean isAbstract(int access) {
        return (ACC_ABSTRACT & access) == ACC_ABSTRACT;
    }

    public static <E> boolean isEquals(E src, E target) {

        return null == src
                && null == target
                || null != src
                && null != target
                && src.equals(target);

    }
}
