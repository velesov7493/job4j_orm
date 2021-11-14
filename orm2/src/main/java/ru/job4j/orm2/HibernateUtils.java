package ru.job4j.orm2;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HibernateUtils {

    private static final Logger LOG = LoggerFactory.getLogger(HibernateUtils.class);
    private static StandardServiceRegistry registry;
    private static SessionFactory sf;

    public static SessionFactory getSessionFactory() {
        if (sf == null) {
            try {
                registry = new StandardServiceRegistryBuilder().configure().build();
                sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            } catch (Throwable ex) {
                LOG.error("Ошибка инициализации SessionFactory", ex);
            }
        }
        return sf;
    }

    public static void releaseSessionFactory() {
        if (sf == null) {
            return;
        }
        sf.close();
        StandardServiceRegistryBuilder.destroy(registry);
    }
}