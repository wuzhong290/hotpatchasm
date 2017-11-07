package com.hotpatch.asm;

import com.hotpatch.asm.advisor.Enhancer;
import com.hotpatch.asm.util.affect.EnhancerAffect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

/**
 * Created by wuzhong on 2017/11/2.
 */
public class AgentLauncher {
    private static final Logger logger = LoggerFactory.getLogger(AgentLauncher.class);

    public static void premain(String agentArgs, Instrumentation inst) {
//        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(
//                new HotPatchASMThread(inst), 5, 50, TimeUnit.SECONDS);
        //inst.addTransformer(new EnhancerSimple(), true);
        try {
            final EnhancerAffect enhancerAffect = Enhancer.enhance(
                    inst,
                    1,
                    true
            );
        } catch (UnmodifiableClassException e) {
            e.printStackTrace();
        }
        logger.info("Agent Main Done");
    }
}
