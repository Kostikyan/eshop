package manager;

import db.DBConnectionProvider;
import model.Category;
import model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductManager {
    private Connection connection = DBConnectionProvider.getInstance().getConnection();
    private CategoryManager cm = new CategoryManager();

    public void save(Product product) {
        String sql = "insert into `eshop`.`product`(`name`, `description`, `price`, `quantity`, `category`) values (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setInt(3, product.getPrice());
            ps.setInt(4, product.getQuantity());
            ps.setInt(5, product.getCategory().getId());
            ps.executeUpdate();
            ResultSet resultSet = ps.getGeneratedKeys();
            if (resultSet.next()) {
                product.setId(resultSet.getInt(1));
            }
            System.out.println("inserted to DB");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Product product) {
        String sql = "UPDATE `eshop`.`product` " +
                "SET name = '%s', description = '%s', price = '%d', quantity = '%d', category = '%d'" +
                "WHERE id = '%d'";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(String.format(sql, product.getName(),
                    product.getDescription(), product.getPrice(),
                    product.getQuantity(), product.getCategory().getId(), product.getId()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Product> getAll() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM `eshop`.`product`";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                products.add(getProductFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;
    }

    public Product getById(int id) {
        String sql = "SELECT * FROM `eshop`.`product` WHERE `id` = " + id;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                return getProductFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private Product getProductFromResultSet(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getInt("id"));
        product.setName(resultSet.getString("name"));
        product.setDescription(resultSet.getString("description"));
        product.setPrice(resultSet.getInt("price"));
        product.setQuantity(resultSet.getInt("quantity"));
        int categoryId = resultSet.getInt("category");
        product.setCategory(cm.getById(categoryId));
        return product;
    }

    public void removeById(int id) {
        String sql = "DELETE FROM `eshop`.`product` WHERE id = " + id;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
