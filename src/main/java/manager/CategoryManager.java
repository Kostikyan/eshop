package manager;

import db.DBConnectionProvider;
import model.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryManager {

    private static Connection connection = DBConnectionProvider.getInstance().getConnection();

    public void save(Category category) {
        String sql = "insert into `eshop`.`category`(`name`) values (?)";
        try(PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1, category.getName());
            ps.executeUpdate();
            ResultSet resultSet = ps.getGeneratedKeys();
            if(resultSet.next()){
                category.setId(resultSet.getInt(1));
            }
            System.out.println("inserted to DB");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Category> getAll() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM `eshop`.`category`";
        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                categories.add(getCategoryFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return categories;
    }

    public Category getById(int id) {
        String sql = "SELECT * FROM `eshop`.`category` WHERE `id` = " + id;
        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery(sql);
            if(rs.next()) {
                return getCategoryFromResultSet(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void update(Category category) {
        String sql = "UPDATE `eshop`.`category` SET name = '%s' WHERE id = '%d'";
        try(Statement statement = connection.createStatement()){
            statement.executeUpdate(String.format(sql, category.getName(),category.getId()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Category getCategoryFromResultSet(ResultSet resultSet) throws SQLException {
        Category category = new Category();
        category.setId(resultSet.getInt("id"));
        category.setName(resultSet.getString("name"));
        return category;
    }


    public void removeById(int id) {
        String sql = "DELETE FROM `eshop`.`category` WHERE id = " + id;
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
