package ru.job4j.itesting;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class AppSettings {

    private static final Logger LOG = LoggerFactory.getLogger(AppSettings.class.getName());
    private static SoftReference<Properties> settings;
    private static BasicDataSource pool;

    private static void initDatabase(String sqlResourceName) {
        String sql = "";
        try (
                InputStream inSQL =
                     AppSettings.class
                     .getClassLoader()
                     .getResourceAsStream(sqlResourceName)
        ) {
            if (inSQL != null) {
                sql = new String(inSQL.readAllBytes(), StandardCharsets.UTF_8);
            }
        } catch (Throwable ex) {
            LOG.error(
                "Критическая ошибка - невозможно прочитать sql-скрипт инициализации: ", ex
            );
            LOG.info("Выключаюсь...");
            System.exit(2);
        }
        try (
                Connection cn = pool.getConnection();
                PreparedStatement ps = cn.prepareStatement(sql)
        ) {
            ps.executeUpdate();
        } catch (SQLException ex) {
            LOG.error("Ошибка при выполнении sql-скрипта инициализации: ", ex);
            LOG.info("Выключаюсь...");
            System.exit(2);
        }
    }

    public static Properties loadProperties() {
        Properties s = settings == null ? null : settings.get();
        if (s == null) {
            s = new Properties();
            try (
                    InputStream in =
                        AppSettings.class
                        .getClassLoader()
                        .getResourceAsStream("app.properties")
            ) {
                if (in != null) {
                    s.load(in);
                    settings = new SoftReference<>(s);
                }
            } catch (Throwable ex) {
                LOG.error(
                    "Критическая ошибка - невозможно прочитать свойства подключения к БД: ", ex
                );
                LOG.info("Выключаюсь...");
                System.exit(2);
            }
        }
        return s;
    }

    public static synchronized BasicDataSource getConnectionPool() {
        Properties cfg = loadProperties();
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
        } catch (ClassNotFoundException ex) {
            throw new IllegalStateException(ex);
        }
        pool = new BasicDataSource();
        pool.setDriverClassName(cfg.getProperty("jdbc.driver"));
        pool.setUrl(cfg.getProperty("jdbc.url"));
        pool.setUsername(cfg.getProperty("jdbc.username"));
        pool.setPassword(cfg.getProperty("jdbc.password"));
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);
        String initSqlResName = cfg.getProperty("jdbc.init-script");
        if (initSqlResName != null) {
            initDatabase(initSqlResName);
        }
        return pool;
    }
}
