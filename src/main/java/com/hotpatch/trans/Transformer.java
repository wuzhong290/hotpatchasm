package com.hotpatch.trans;

import com.hotpatch.FileUtil;
import com.hotpatch.HotPatch;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.List;

public class Transformer implements ClassFileTransformer {

    public static byte[] getBytesFromFile() {
        try {
            List<File> list = FileUtil.readfile(HotPatch.ROOT_PATH);
            if (list != null && list.size() > 0) {
                for (File file : list) {
                    byte[] array = FileUtil.getBytesFromFile(file.getPath());
                    System.out.println(file.getPath());
                    file.delete();
                    return array;
                }
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    public byte[] transform(ClassLoader l, String className, Class<?> c,
                            ProtectionDomain pd, byte[] b) throws IllegalClassFormatException {
        System.out.println("className:"+className);
        if (!StringUtils.contains(className,"TransClass")) {
            return null;
        }
        System.out.println("classNameddd:"+className);
        return getBytesFromFile();
    }
}
