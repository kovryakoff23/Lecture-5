import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OverheadDAO implements DAO<Overhead> {
    Connection connection;

    public OverheadDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Overhead get(int number) {
        try (Statement stmt = connection.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("SELECT Number_overhead, Date_overhead, Organization_sender FROM Overhead WHERE Number_overhead = " + number)) {
                while (rs.next()) {
                    return new Overhead(rs.getInt("Number_overhead"), rs.getDate("Date_overhead"), rs.getInt("Organization_sender"));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        throw new IllegalStateException("Record with Number_overhead " + number + "not found");
    }

    @Override
    public List<Overhead> getAll() {
        final List<Overhead> result = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("SELECT Number_overhead, Date_overhead, Organization_sender FROM Overhead")) {
                while (rs.next()) {
                    result.add(new Overhead(rs.getInt("Number_overhead"), rs.getDate("Date_overhead"), rs.getInt("Organization_sender")));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return result;
    }

    @Override
    public void save(Overhead entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Overhead(Number_overhead, Date_overhead, Organization_sender) VALUES(?,?,?)")) {
            preparedStatement.setInt(1, entity.getNumber());
            preparedStatement.setDate(2, entity.getDate());
            preparedStatement.setInt(3, entity.getOrganization());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Overhead entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Overhead SET  Date_overhead = ?, Organization_sender = ? WHERE Number_overhead = ?")) {
            int cnt = 1;
            preparedStatement.setInt(2, entity.getOrganization());
            preparedStatement.setDate(1, entity.getDate());
            preparedStatement.setInt(3, entity.getNumber());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Overhead entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Overhead WHERE Number_overhead = ?")) {
            preparedStatement.setInt(1, entity.getNumber());
            if (preparedStatement.executeUpdate() == 0) {
                throw new IllegalStateException("Record with Number_overhead = " + entity.getNumber() + " not found");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

