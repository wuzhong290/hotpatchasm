package com.hotpatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.instrument.Instrumentation;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 热替换
 * 
 * @author ksfzhaohui
 * 
 */
public class HotPatch {

	private static final Logger logger = LoggerFactory.getLogger(HotPatch.class);

	public static final String ROOT_PATH = "hotfiles";

	public static void premain(String agentArgs, Instrumentation inst) {
		Executors.newScheduledThreadPool(1).scheduleAtFixedRate(
				new HotPatchThread(inst), 5, 5, TimeUnit.SECONDS);
		clearRootPath();
		createRootPath();
		logger.info("hotPatch starting...");
	}

	/**
	 * 清空根目录
	 */
	private static void clearRootPath() {
		FileUtil.deleteAllFilesOfDir(new File(ROOT_PATH));
	}

	/**
	 * 创建根目录文件夹,用于存放要替换的文件
	 */
	private static void createRootPath() {
		File file = new File(ROOT_PATH);
		file.mkdir();
	}

}
