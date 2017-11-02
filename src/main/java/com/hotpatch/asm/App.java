package com.hotpatch.asm;

import org.objectweb.asm.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class App extends ClassLoader implements Opcodes {
    public static void main(String[] args) throws IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SecurityException, InstantiationException {
        ClassReader cr=new ClassReader(HelloWorld.class.getName());
        ClassWriter cw=new ClassWriter(ClassWriter.COMPUTE_MAXS);
        CustomVisitor myv=new CustomVisitor(Opcodes.ASM5,cw);
        cr.accept(myv, 0);

        byte[] code=cw.toByteArray();

        //自定义加载器
        App loader=new App();
        Class<?> appClass=loader.defineClass(null, code, 0,code.length);
        appClass.getMethods()[0].invoke(appClass.newInstance(), new Object[]{});

		FileOutputStream f=new FileOutputStream(new File("E:"+File.separator+"Example.class"));
		f.write(code);
		f.close();

    }
}

/**
 * ClassVisitor的实现类
 * App.java:demoasm
 * Jul 17, 2014
 * @author kevin.zhai
 */
class CustomVisitor extends ClassVisitor implements Opcodes {

    public CustomVisitor(int api, ClassVisitor cv) {
        super(api, cv);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        if (name.equals("sayHello")) {
            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(J)V");
        }
        return mv;
    }
}