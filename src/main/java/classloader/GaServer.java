package classloader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by wuzhong on 2017/11/8.
 */
public class GaServer {
    private static final Logger logger = LoggerFactory.getLogger(GaServer.class);

    private static volatile GaServer gaServer;
    private final DefaultCommandHandler commandHandler;
    private final Thread jvmShutdownHooker = new Thread("ga-shutdown-hooker") {

        @Override
        public void run() {
            GaServer.this._destroy();
        }
    };

    private GaServer() {
        this.commandHandler = new DefaultCommandHandler();

        Runtime.getRuntime().addShutdownHook(jvmShutdownHooker);

    }

    private void _destroy() {
        logger.info("ga-server destroy completed.");
    }
    /**
     * 单例
     *
     * @return GaServer单例
     */
    public static GaServer getInstance() {
        if (null == gaServer) {
            synchronized (GaServer.class) {
                if (null == gaServer) {
                    gaServer = new GaServer();
                }
            }
        }
        return gaServer;
    }

    public void execute(final String line) throws IOException {
        logger.info("line:{}-{}-{}", line, this.getClass().getClassLoader().toString(), logger.getClass().getClassLoader().toString());
        commandHandler.executeCommand(line);
    }
}
