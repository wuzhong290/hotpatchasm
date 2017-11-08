package classloader;

/**
 * Created by wuzhong on 2017/11/8.
 */
public class Main {

    public static void main(String[] args) {
        final ClassLoader agentLoader;
        try {
            agentLoader = new AgentClassLoader(Main.class.getProtectionDomain().getCodeSource().getLocation().getFile());
            final Class<?> classOfGaServer = agentLoader.loadClass("classloader.GaServer");
            // 获取GaServer单例
            final Object objectOfGaServer = classOfGaServer
                    .getMethod("getInstance")
                    .invoke(null);
            classOfGaServer.getMethod("execute",String.class).invoke(objectOfGaServer,"line");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
