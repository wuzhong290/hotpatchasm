package agentTest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wuzhong on 2017/11/2.
 */
public class AgentTest {

    private static final Logger logger = LoggerFactory.getLogger(TClass.class);

    public static void main(String[] args)
            throws InterruptedException
    {
        TClass c;
        for (;;)
        {
            c = new TClass();
            logger.info("result:{}",c.getNumber());
            Thread.sleep(30000L);
        }
    }
}
