package agentTest;

/**
 * Created by wuzhong on 2017/11/2.
 */
public class AgentTest {
    public static void main(String[] args)
            throws InterruptedException
    {
        TClass c;
        for (;;)
        {
            c = new TClass();
            System.out.println(c.getNumber());
            Thread.sleep(30000L);
        }
    }
}
