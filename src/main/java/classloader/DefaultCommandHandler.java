package classloader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by wuzhong on 2017/11/8.
 */
public class DefaultCommandHandler {
    private static final Logger logger = LoggerFactory.getLogger(DefaultCommandHandler.class);

    public void executeCommand(final String line) throws IOException {
        logger.info("line:{}-{}-{}", line, this.getClass().getClassLoader().toString(), logger.getClass().getClassLoader().toString());
    }
}
