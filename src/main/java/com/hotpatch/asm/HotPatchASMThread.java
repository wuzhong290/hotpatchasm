package com.hotpatch.asm;

import org.apache.log4j.Logger;

import java.lang.instrument.Instrumentation;

public class HotPatchASMThread implements Runnable {

	private final static Logger logger = Logger.getLogger(HotPatchASMThread.class);
	private Instrumentation inst;

	public HotPatchASMThread(Instrumentation inst) {
		this.inst = inst;
	}

	public void run() {
		Enhancer enhancer = new Enhancer();
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
