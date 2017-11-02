package com.hotpatch.asm;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.io.FileOutputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import static org.objectweb.asm.ClassReader.EXPAND_FRAMES;
import static org.objectweb.asm.ClassWriter.COMPUTE_FRAMES;
import static org.objectweb.asm.ClassWriter.COMPUTE_MAXS;

/**
 * Created by wuzhong on 2017/11/2.
 */
public class Enhancer implements ClassFileTransformer {
    private final static Logger logger = Logger.getLogger(Enhancer.class);
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        logger.info("transform className:"+className);
        if(!StringUtils.contains(className,"TClass")){
          return null;
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("E:\\Example.class");
            fos.write(classfileBuffer);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        final ClassReader cr = new ClassReader(classfileBuffer);
        // 字节码增强
        final ClassWriter cw = new ClassWriter(cr, COMPUTE_FRAMES | COMPUTE_MAXS);
        // 生成增强字节码
        cr.accept(new AdviceWeaver(cw), EXPAND_FRAMES);
        final byte[] enhanceClassByteArray = cw.toByteArray();
        if(null != enhanceClassByteArray){
            logger.info("enhanceClassByteArray:"+enhanceClassByteArray.length);
        }
        logger.info("transform className1:"+className);
        return enhanceClassByteArray;
    }
}
