package ru.job4j.orm1.tasks;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.orm1.DbUtils;
import ru.job4j.orm1.models.CarBrand;
import ru.job4j.orm1.models.CarModel;

import java.util.ArrayList;
import java.util.List;

public class LazyInitialization {

    private static final Logger LOG = LoggerFactory.getLogger(LazyInitialization.class);

    public static void main(String[] args) {
        List<CarBrand> brands = new ArrayList<>();
        try {
            SessionFactory sf = DbUtils.getSessionFactory();
            Session s = sf.openSession();
            Transaction tx = s.beginTransaction();
            String hql = "SELECT DISTINCT b FROM CarBrand b JOIN FETCH b.models";
            brands = s.createQuery(hql).list();
            tx.commit();
            s.close();
        } catch (Exception ex) {
            LOG.error("ORM: ошибка выборки данных", ex);
        } finally {
            DbUtils.releaseSessionFactory();
        }
        for (CarBrand brand : brands) {
            System.out.println(brand);
            for (CarModel model : brand.getModels()) {
                System.out.println('\t' + model.toString());
            }
        }
    }
}
