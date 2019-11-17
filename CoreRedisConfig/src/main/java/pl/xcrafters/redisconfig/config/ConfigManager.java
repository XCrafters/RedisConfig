package pl.xcrafters.redisconfig.config;

public interface ConfigManager {

    void load();

    String getRedisHost();
    int getRedisPort();
    String getRedisPass();
    int getRedisBase();
    String getExportPath();

}
