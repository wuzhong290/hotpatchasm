package com.hotpatch.asm;

import org.apache.log4j.Logger;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * Created by wuzhong on 2017/11/2.
 */
public class AdviceWeaver  extends ClassVisitor implements Opcodes {
    private final static Logger logger = Logger.getLogger(Enhancer.class);

    public AdviceWeaver(final ClassVisitor cv){
        super(ASM5, cv);
    }

    @Override
    public MethodVisitor visitMethod(int access, final String name, String desc, String signature, String[] exceptions) {
        final MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        if (isIgnore(mv, access, name, desc)) {
            return mv;
        }
        logger.info("visitMethod name:"+name);
        if (name.equals("getNumber")) {
            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(J)V");
        }
        return mv;
//        return new AdviceAdapter(ASM5, new JSRInlinerAdapter(mv, access, name, desc, signature, exceptions), access, name, desc){
//            private final Type ASM_TYPE_SPY = Type.getType("Lcom/hotpatch/asm/Spy;");
//            private final Type ASM_TYPE_METHOD = Type.getType(java.lang.reflect.Method.class);
//            private final Method ASM_METHOD_METHOD_INVOKE = Method.getMethod("Object invoke(Object,Object[])");
//            @Override
//            public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
//                logger.info("visitMethodInsn begin name:"+name);
//                super.visitMethodInsn(opcode, owner, name, desc, itf);
//                logger.info("visitMethodInsn end name:"+name);
//            }
//
//            @Override
//            protected void onMethodEnter() {
//            }
//            @Override
//            protected void onMethodExit(int opcode) {
//            }
//
//            /**
//             * 加载通知方法
//             */
//            private void loadAdviceMethod() {
//                getStatic(ASM_TYPE_SPY, "ON_ASM_METHOD", ASM_TYPE_METHOD);
//            }
//        };
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
