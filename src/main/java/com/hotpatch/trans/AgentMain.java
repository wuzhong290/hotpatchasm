package com.hotpatch.trans;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

/**
 * Created by wuzhong on 2017/11/2.
 */
public class AgentMain {
    public static final String ROOT_PATH = "hotfiles";

    public static void premain(String agentArgs, Instrumentation inst)  throws ClassNotFoundException, UnmodifiableClassException, InterruptedException {
        inst.addTransformer(new Transformer (), true);
        System.out.println("Agent Main Done");
    }
}
