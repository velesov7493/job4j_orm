package ru.job4j.orm.tasks;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.orm.HibernateUtils;
import ru.job4j.orm.models.CarBrand;
import ru.job4j.orm.models.CarModel;

public class ToMany {

    private static final Logger LOG = LoggerFactory.getLogger(ToMany.class);

    public static void main(String[] args) {
        try {
            SessionFactory sf = HibernateUtils.getSessionFactory();
            Session s = sf.openSession();
            Transaction tx = s.beginTransaction();
            CarBrand brand = CarBrand.of("ВАЗ");
            s.save(CarModel.of("2101"));
            s.save(CarModel.of("2103"));
            s.save(CarModel.of("2105"));
            s.save(CarModel.of("2106"));
            s.save(CarModel.of("2107"));
            brand.addModel(s.load(CarModel.class, 1));
            brand.addModel(s.load(CarModel.class, 2));
            brand.addModel(s.load(CarModel.class, 3));
            brand.addModel(s.load(CarModel.class, 4));
            brand.addModel(s.load(CarModel.class, 5));
            s.save(brand);
            tx.commit();
            s.close();
        } catch (Exception ex) {
            LOG.error("ORM: ошибка сохранения данных", ex);
        } finally {
            HibernateUtils.releaseSessionFactory();
        }
    }
}
