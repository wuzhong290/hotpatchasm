package agentTest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wuzhong on 2017/11/2.
 */
public class Spy {

    private static final Logger logger = LoggerFactory.getLogger(Spy.class);

    public static void log(){
        logger.info("spy.............");
    }
}
