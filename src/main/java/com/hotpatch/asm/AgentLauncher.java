package com.hotpatch.asm;

import org.apache.log4j.Logger;

import java.lang.instrument.Instrumentation;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by wuzhong on 2017/11/2.
 */
public class AgentLauncher {
    private final static Logger logger = Logger.getLogger(AgentLauncher.class);

    public static void premain(String agentArgs, Instrumentation inst) {
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(
                new HotPatchASMThread(inst), 5, 50, TimeUnit.SECONDS);
        logger.info("hotPatch starting...");
    }
}
