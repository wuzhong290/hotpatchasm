package agentTest;

import org.apache.log4j.Logger;

/**
 * Created by wuzhong on 2017/11/2.
 */
public class TClass {
    private final static Logger logger = Logger.getLogger(TClass.class);
    private int k = 10;

    public int getNumber()
    {
        logger.info("getNumber");
        return this.k + 5;
    }
}
