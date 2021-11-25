package ru.job4j.itesting.stores;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.itesting.models.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class OrdersStore {

    private static final Logger LOG = LoggerFactory.getLogger(OrdersStore.class);

    private final BasicDataSource pool;

    public OrdersStore(BasicDataSource pool) {
        this.pool = pool;
    }

    private Order create(Order order) {
        String query = "INSERT INTO tz_orders(name, description, created) VALUES (?, ?, ?)";
        try (
            Connection con = pool.getConnection();
            PreparedStatement pr = con.prepareStatement(
                    query, PreparedStatement.RETURN_GENERATED_KEYS
            )
        ) {
            pr.setString(1, order.getName());
            pr.setString(2, order.getDescription());
            pr.setTimestamp(3, Timestamp.from(order.getCreated().toInstant()));
            pr.execute();
            ResultSet id = pr.getGeneratedKeys();
            if (id.next()) {
                order.setId(id.getInt(1));
            }
        } catch (SQLException ex) {
            LOG.error("Ошибка создания заказа. ", ex);
        }
        return order;
    }

    private void update(Order order) {
        String query = "UPDATE tz_orders SET name=?, description=? WHERE id=?";
        try (
            Connection con = pool.getConnection();
            PreparedStatement pr = con.prepareStatement(query)
        ) {
            pr.setString(1, order.getName());
            pr.setString(2, order.getDescription());
            pr.setInt(3, order.getId());
            pr.execute();
        } catch (SQLException ex) {
            LOG.error("Ошибка изменения заказа. ", ex);
        }
    }

    public Collection<Order> findAll() {
        List<Order> result = new ArrayList<>();
        try (
            Connection con = pool.getConnection();
            PreparedStatement pr = con.prepareStatement("SELECT * FROM tz_orders")
        ) {
            try (ResultSet rs = pr.executeQuery()) {
                while (rs.next()) {
                    result.add(
                        new Order(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("description"),
                            new Date(rs.getTimestamp("created").getTime())
                        )
                    );
                }
            }
        } catch (SQLException ex) {
            LOG.error("Ошибка чтения списка заказов. ", ex);
        }
        return result;
    }

    public Order findById(int id) {
        Order result = null;
        try (
            Connection con = pool.getConnection();
            PreparedStatement pr = con.prepareStatement("SELECT * FROM tz_orders WHERE id = ?")
        ) {
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                result = new Order(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getTimestamp("created")
                );
            }
        } catch (SQLException ex) {
            LOG.error("Ошибка чтения заказа c id=" + id, ex);
        }
        return result;
    }

    public Order save(Order value) {
        Order result;
        if (value.getId() == 0) {
            result = create(value);
        } else {
            update(value);
            result = value;
        }
        return result;
    }

    public Collection<Order> findAllByName(String name) {
        List<Order> result = new ArrayList<>();
        String query = "SELECT * FROM tz_orders WHERE name=?";
        try (
            Connection con = pool.getConnection();
            PreparedStatement pr = con.prepareStatement(query)
        ) {
            pr.setString(1, name);
            try (ResultSet rs = pr.executeQuery()) {
                while (rs.next()) {
                    result.add(
                        new Order(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("description"),
                            new Date(rs.getTimestamp("created").getTime())
                        )
                    );
                }
            }
        } catch (SQLException ex) {
            LOG.error("Ошибка чтения списка заказов. ", ex);
        }
        return result;
    }

    public boolean deleteById(int id) {
        String sql = "DELETE FROM tz_orders WHERE id=?";
        boolean result = false;
        try (
            Connection cn = pool.getConnection();
            PreparedStatement ps = cn.prepareStatement(sql)
        ) {
            ps.setInt(1, id);
            result = ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOG.error("Ошибка удаления заказа с id=" + id, ex);
        }
        return result;
    }
}