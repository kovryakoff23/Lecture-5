import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PositionDAO implements DAO<Positions> {
    Connection connection;

    public PositionDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Positions get(int number) {
        try (Statement stmt = connection.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("SELECT Position_id , Price, Nomenclature, Quantity, Number_overhead FROM Position_overhead WHERE Position_id = " + number)) {
                while (rs.next()) {
                    return new Positions(rs.getInt("Position_id"),rs.getInt("Price"), rs.getInt("Nomenclature"), rs.getInt("Quantity"), rs.getInt("Number_overhead"));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        throw new IllegalStateException("Record with id " + number + "not found");
    }

    @Override
    public List<Positions> getAll() {
        final List<Positions> result = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("SELECT Position_id, Price, Nomenclature, Quantity, Number_overhead FROM Position_overhead")) {
                while (rs.next()) {
                    result.add(new Positions(rs.getInt("Position_id"),rs.getInt("Price"), rs.getInt("Nomenclature"), rs.getInt("Quantity"), rs.getInt("Number_overhead")));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return result;
    }

    @Override
    public void save(Positions entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Position_overhead(Position_id, Price, Nomenclature, Quantity, Number_overhead) VALUES(?,?,?,?,?)")) {
            preparedStatement.setInt(1, entity.getId());
            preparedStatement.setInt(2, entity.getPrice());
            preparedStatement.setInt(3, entity.getNomenclature());
            preparedStatement.setInt(4, entity.getQuantity());
            preparedStatement.setInt(5, entity.getNumber());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Positions entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Position_overhead SET Price = ?, Nomenclature = ?, Quantity = ?, Number_overhead = ?  WHERE Position_id = ?")) {
            preparedStatement.setInt(5, entity.getId());
            preparedStatement.setInt(1, entity.getPrice());
            preparedStatement.setInt(2, entity.getNomenclature());
            preparedStatement.setInt(3, entity.getQuantity());
            preparedStatement.setInt(4, entity.getNumber());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Positions entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Position_overhead WHERE Position_id = ?")) {
            preparedStatement.setInt(1, entity.getId());
            if (preparedStatement.executeUpdate() == 0) {
                throw new IllegalStateException("Record with id = " + entity.getNumber() + " not found");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
