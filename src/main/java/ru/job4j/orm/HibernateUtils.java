package ru.job4j.orm;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtils {

    private static final StandardServiceRegistry REGISTRY =
            new StandardServiceRegistryBuilder().configure().build();
    private static final SessionFactory SF =
            new MetadataSources(REGISTRY).buildMetadata().buildSessionFactory();

    public static SessionFactory getSessionFactory() {
        return SF;
    }

    public static void releaseSessionFactory() {
        SF.close();
        StandardServiceRegistryBuilder.destroy(REGISTRY);
    }
}
