package com.hotpatch.asm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.instrument.Instrumentation;

public class HotPatchASMThread implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(HotPatchASMThread.class);
	private Instrumentation inst;

	public HotPatchASMThread(Instrumentation inst) {
		this.inst = inst;
	}

	public void run() {
		EnhancerSimple enhancer = new EnhancerSimple();
		logger.info("run begin");
		try {
			inst.addTransformer(enhancer, true);
		} catch (Exception e) {
			logger.error("hotpatching error", e);
		}finally {
			inst.removeTransformer(enhancer);
		}
	}
}
