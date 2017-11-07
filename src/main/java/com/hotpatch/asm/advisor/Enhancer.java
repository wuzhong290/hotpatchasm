package com.hotpatch.asm.advisor;

import com.hotpatch.asm.util.affect.EnhancerAffect;
import org.apache.commons.lang3.StringUtils;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * Created by wuzhong on 2017/11/2.
 */
public class Enhancer extends ClassLoader implements ClassFileTransformer {
    private static final Logger logger = LoggerFactory.getLogger(Enhancer.class);
    final EnhancerAffect affect = new EnhancerAffect();

    public Enhancer() {
        // 获取各种Hook
        final Class<?> adviceWeaverClass;
        try {
            adviceWeaverClass = this.loadClass("com.hotpatch.asm.advisor.AdviceWeaver");
            // 初始化全局间谍
            try {
                Spy.initForAgentLauncher(
                        this,
                        adviceWeaverClass.getMethod("methodOnBegin",
                                int.class,
                                ClassLoader.class,
                                String.class,
                                String.class,
                                String.class,
                                Object.class,
                                Object[].class),
                        adviceWeaverClass.getMethod("methodOnReturnEnd",
                                Object.class,
                                int.class),
                        adviceWeaverClass.getMethod("methodOnThrowingEnd",
                                Throwable.class,
                                int.class),
                        adviceWeaverClass.getMethod("methodOnInvokeBeforeTracing",
                                int.class,
                                Integer.class,
                                String.class,
                                String.class,
                                String.class),
                        adviceWeaverClass.getMethod("methodOnInvokeAfterTracing",
                                int.class,
                                Integer.class,
                                String.class,
                                String.class,
                                String.class),
                        adviceWeaverClass.getMethod("methodOnInvokeThrowTracing",
                                int.class,
                                Integer.class,
                                String.class,
                                String.class,
                                String.class,
                                String.class));
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

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
            cr.accept(new AdviceWeaver(1,true, null, affect, cw), ClassReader.EXPAND_FRAMES);
            byte[] enhanceClassByteArray = cw.toByteArray();
            if(null != enhanceClassByteArray){
                logger.info("enhanceClassByteArray size:{}", enhanceClassByteArray.length);
            }
            logger.info("transform className1:"+className);
            return enhanceClassByteArray;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}