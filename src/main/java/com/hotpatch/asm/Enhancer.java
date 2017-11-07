package com.hotpatch.asm;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.reflect.InvocationTargetException;
import java.security.ProtectionDomain;

/**
 * Created by wuzhong on 2017/11/2.
 */
public class Enhancer extends ClassLoader implements ClassFileTransformer {
    private final static Logger logger = Logger.getLogger(Enhancer.class);
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        logger.info("transform className:"+className);
        if(!StringUtils.contains(className,"TClass")){
          return null;
        }
        if(null != classfileBuffer){
            logger.info("classfileBuffer:"+classfileBuffer.length);
        }
        final ClassReader cr;
        try {
            cr = new ClassReader(classfileBuffer);
            // 字节码增强
            ClassWriter cw=new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
            // 生成增强字节码
            cr.accept(new AdviceWeaverSimple(cw), ClassReader.EXPAND_FRAMES);
            byte[] enhanceClassByteArray = cw.toByteArray();
            if(null != enhanceClassByteArray){
                Class<?> appClass=this.defineClass(null, enhanceClassByteArray, 0,enhanceClassByteArray.length);
                try {
                    Object obj = appClass.getMethods()[0].invoke(appClass.newInstance(), new Object[]{});
                    logger.info("appClass.getMethods:"+obj);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
                logger.info("enhanceClassByteArray:"+enhanceClassByteArray.length +":"+appClass.getName());
            }
            logger.info("transform className1:"+className);
            return enhanceClassByteArray;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
