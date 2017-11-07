package com.hotpatch.asm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.instrument.Instrumentation;

/**
 * Created by wuzhong on 2017/11/2.
 */
public class AgentLauncher {
    private static final Logger logger = LoggerFactory.getLogger(AgentLauncher.class);

    public static void premain(String agentArgs, Instrumentation inst) {
//        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(
//                new HotPatchASMThread(inst), 5, 50, TimeUnit.SECONDS);
        inst.addTransformer(new Enhancer(), true);
        System.out.println("Agent Main Done");
    }
}
