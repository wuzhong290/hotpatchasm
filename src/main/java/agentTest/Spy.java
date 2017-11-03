package agentTest;

import org.apache.log4j.Logger;

/**
 * Created by wuzhong on 2017/11/2.
 */
public class Spy {

    private final static Logger logger = Logger.getLogger(Spy.class);

    public static void log(){
        logger.info("spy.............");
    }
}
