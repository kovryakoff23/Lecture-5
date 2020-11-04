import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NomenclaturesDAO implements DAO<Nomenclature> {
    Connection connection;

    public NomenclaturesDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Nomenclature get(int number) {
        try (Statement stmt = connection.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("SELECT Name, Code FROM  Nomenclature WHERE Code = " + number)) {
                while (rs.next()) {
                    return new Nomenclature(rs.getString("Name"), rs.getInt("Code"));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        throw new IllegalStateException("Record with Code " + number + "not found");
    }

    @Override
    public List<Nomenclature> getAll() {
        final List<Nomenclature> result = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("SELECT Name, Code  FROM  Nomenclature")) {
                while (rs.next()) {
                    result.add(new Nomenclature(rs.getString("Name"), rs.getInt("Code")));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return result;
    }

    @Override
    public void save(Nomenclature entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Nomenclature(Name, Code) VALUES(?,?)")) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setInt(2, entity.getNumber());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Nomenclature entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE  Nomenclature SET Name  = ?  WHERE Code  = ?")) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setInt(2, entity.getNumber());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Nomenclature entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM  Nomenclature WHERE Code  = ?")) {
            preparedStatement.setInt(1, entity.getNumber());
            if (preparedStatement.executeUpdate() == 0) {
                throw new IllegalStateException("Record with Code = " + entity.getNumber() + " not found");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
