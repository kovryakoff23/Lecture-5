import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrganizationDAO implements DAO<Organization> {
    Connection connection;

    public OrganizationDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Organization get(int number) {
        try (Statement stmt = connection.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("SELECT Org_id, Name_org, INN, Account FROM Organization WHERE Org_id = " + number)) {
                while (rs.next()) {
                    return new Organization(rs.getInt("Org_id"), rs.getString("Name_org"), rs.getInt("INN"), rs.getInt("Account"));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        throw new IllegalStateException("Record with Org_id " + number + "not found");
    }

    @Override
    public List<Organization> getAll() {
        final List<Organization> result = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("SELECT Org_id, Name_org, INN, Account FROM Organization ")) {
                while (rs.next()) {
                    result.add(new Organization(rs.getInt("Org_id"), rs.getString("Name_org"), rs.getInt("INN"), rs.getInt("Account")));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return result;
    }

    @Override
    public void save(Organization entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Organization(Org_id, Name_org, INN, Account) VALUES(?,?,?,?)")) {
            preparedStatement.setInt(1, entity.getId());
            preparedStatement.setString(2, entity.getName());
            preparedStatement.setInt(3, entity.getInn());
            preparedStatement.setInt(4, entity.getAccount());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Organization entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Organization SET Name_org = ?, INN = ?, Account = ?  WHERE Org_id = ?")) {
            preparedStatement.setInt(4, entity.getId());
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setInt(2, entity.getInn());
            preparedStatement.setInt(3, entity.getAccount());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Organization entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Organization WHERE Org_id = ?")) {
            preparedStatement.setInt(1, entity.getId());
            if (preparedStatement.executeUpdate() == 0) {
                throw new IllegalStateException("Record with Org_id = " + entity.getName() + " not found");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
