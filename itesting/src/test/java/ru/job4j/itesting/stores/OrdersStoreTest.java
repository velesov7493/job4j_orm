package ru.job4j.itesting.stores;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.job4j.itesting.AppSettings;
import ru.job4j.itesting.models.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;

public class OrdersStoreTest {

    private BasicDataSource pool;

    @Before
    public void setUp() {
        pool = AppSettings.getConnectionPool();
    }

    @After
    public void clear() throws SQLException {
        String sql = "DROP TABLE IF EXISTS tz_orders";
        try (
            Connection cn = pool.getConnection();
            PreparedStatement ps = cn.prepareStatement(sql);
        ) {
            ps.executeUpdate();
        }
    }

    @Test
    public void whenCreateOrder() {
        OrdersStore store = new OrdersStore(pool);
        Order in = new Order(
                0, "Тестовый заказ",
                "Заказ для проверки интеграции с БД", new Date()
        );
        Order result = store.save(in);
        assertTrue(result.getId() > 0);
        assertEquals("Тестовый заказ", result.getName());
        assertEquals("Заказ для проверки интеграции с БД", result.getDescription());
        assertEquals(in.getCreated(), result.getCreated());
    }

    @Test
    public void whenFindAllOrders() {
        OrdersStore store = new OrdersStore(pool);
        List<Order> in = List.of(
            new Order(0, "Тест1", "Интеграционный тест 1", new Date()),
            new Order(0, "Тест2", "Интеграционный тест 2", new Date()),
            new Order(0, "Тест3", "Интеграционный тест 3", new Date())
        );
        List<Order> expected = List.of(
            new Order(1, "Тест1", "Интеграционный тест 1", in.get(0).getCreated()),
            new Order(2, "Тест2", "Интеграционный тест 2", in.get(1).getCreated()),
            new Order(3, "Тест3", "Интеграционный тест 3", in.get(2).getCreated())
        );
        in.forEach(store::save);
        List<Order> result = (List<Order>) store.findAll();
        assertThat(result, is(expected));
    }

    @Test
    public void whenFindOrderById() {
        OrdersStore store = new OrdersStore(pool);
        Order in = new Order(
            0, "Тестовый заказ",
            "Заказ для проверки интеграции с БД", new Date()
        );
        Order expected = new Order(
            1, "Тестовый заказ",
            "Заказ для проверки интеграции с БД", new Date()
        );
        store.save(in);
        Order result = store.findById(1);
        assertEquals(expected, result);
    }

    @Test
    public void whenFindOrdersByName() {
        OrdersStore store = new OrdersStore(pool);
        List<Order> in = List.of(
            new Order(0, "Тест", "Интеграционный тест 1", new Date()),
            new Order(0, "Тест", "Интеграционный тест 2", new Date()),
            new Order(0, "Тест", "Интеграционный тест 3", new Date())
        );
        List<Order> expected = List.of(
            new Order(1, "Тест", "Интеграционный тест 1", in.get(0).getCreated()),
            new Order(2, "Тест", "Интеграционный тест 2", in.get(1).getCreated()),
            new Order(3, "Тест", "Интеграционный тест 3", in.get(2).getCreated())
        );
        in.forEach(store::save);
        List<Order> result = (List<Order>) store.findAllByName("Тест");
        assertThat(result, is(expected));
    }

    @Test
    public void whenUpdateOrder() {
        OrdersStore store = new OrdersStore(pool);
        Order in = new Order(
            0, "Тестовый заказ",
            "Заказ для проверки интеграции с БД", new Date()
        );
        Order result = store.save(in);
        in.setId(result.getId());
        in.setDescription("Проверка изменения заказа");
        store.save(in);
        result = store.findById(result.getId());
        assertTrue(result.getId() > 0);
        assertEquals("Тестовый заказ", result.getName());
        assertEquals("Проверка изменения заказа", result.getDescription());
        assertEquals(in.getCreated(), result.getCreated());
    }

    @Test
    public void whenDeleteOrder() {
        OrdersStore store = new OrdersStore(pool);
        Order in = new Order(
            0, "Тестовый заказ",
            "Заказ для проверки интеграции с БД", new Date()
        );
        Order result = store.save(in);
        assertTrue(store.deleteById(1));
        assertNull(store.findById(1));
    }
}