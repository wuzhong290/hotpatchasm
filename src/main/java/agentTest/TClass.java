package agentTest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wuzhong on 2017/11/2.
 */
public class TClass {
    private static final Logger logger = LoggerFactory.getLogger(TClass.class);
    private int k = 10;

    public int getNumber()
    {
        logger.info("getNumber");
        return this.k + 5;
    }
}
