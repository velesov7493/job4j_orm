package ru.job4j.orm.tasks;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.orm.HibernateUtils;
import ru.job4j.orm.models.CarBrand;
import ru.job4j.orm.models.CarModel;

import java.util.ArrayList;
import java.util.List;

public class LazyInitialization {

    private static final Logger LOG = LoggerFactory.getLogger(LazyInitialization.class);

    public static void main(String[] args) {
        List<CarBrand> brands = new ArrayList<>();
        try {
            SessionFactory sf = HibernateUtils.getSessionFactory();
            Session s = sf.openSession();
            Transaction tx = s.beginTransaction();
            String hql = "SELECT DISTINCT b FROM CarBrand b JOIN FETCH b.models";
            brands = s.createQuery(hql).list();
            tx.commit();
            s.close();
        } catch (Exception ex) {
            LOG.error("ORM: ошибка выборки данных", ex);
        } finally {
            HibernateUtils.releaseSessionFactory();
        }
        for (CarBrand brand : brands) {
            System.out.println(brand);
            for (CarModel model : brand.getModels()) {
                System.out.println('\t' + model.toString());
            }
        }
    }
}
