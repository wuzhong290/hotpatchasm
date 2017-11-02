package com.hotpatch.asm;

import org.apache.log4j.Logger;

import java.lang.reflect.Method;

/**
 * Created by wuzhong on 2017/11/2.
 */
public class Spy {

    private final static Logger logger = Logger.getLogger(Enhancer.class);

    public static volatile Method ON_ASM_METHOD ;

    static{
        try {
            ON_ASM_METHOD = Spy.class.getMethod("log");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public static void log(){
        logger.info("spy.............");
    }

    public static void main(String[] args) {
        Spy.log();
        System.out.println("");
    }
}
