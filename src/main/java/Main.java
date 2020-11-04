
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.flywaydb.core.Flyway;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        final Flyway flyway=Flyway.configure().dataSource("jdbc:postgresql://127.0.0.1:5432/Test5","postgres","2357")
                .locations("db")
                .load();
        flyway.clean();
        flyway.migrate();
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Test5","postgres","2357")) {
            System.out.println("Connection Ok.");
            final NomenclaturesDAO nomenclaturesDAO = new NomenclaturesDAO(connection);
            final OrganizationDAO organizationDAO = new OrganizationDAO(connection);
            final OverheadDAO overheadDAO = new OverheadDAO(connection);
            final PositionDAO positionDAO = new PositionDAO(connection);

            String fileName = "nomenclature.txt";
            ArrayList<Nomenclature> nomenclatures = (ArrayList<Nomenclature>)Nomenclature.read(fileName);
            for (Nomenclature nomenclature : nomenclatures){
                nomenclaturesDAO.save(nomenclature);
            }
            fileName = "org.txt";
            ArrayList<Organization> organizations = (ArrayList<Organization>)Organization.read(fileName);
            for (Organization organization : organizations){
                organizationDAO.save(organization);
            }
            fileName = "Overhead.txt";
            ArrayList<Overhead> overheads = (ArrayList<Overhead>)Overhead.read(fileName);
            for (Overhead overhead : overheads){
                overheadDAO.save(overhead);
            }
            fileName = "Position.txt";
            ArrayList<Positions> positions = (ArrayList<Positions>)Positions.read(fileName);
            for (Positions position : positions){
                positionDAO.save(position);
            }
            ArrayList<Organization> result = new ArrayList<>();
            int t = 10;
            try {
                Statement stmt = connection.createStatement();
                ResultSet rs;
                rs = stmt.executeQuery("SELECT Org_id, Name_org, INN, Account FROM Organization WHERE Org_id IN " +
                        "(SELECT Organization_sender FROM Overhead WHERE Number_overhead IN" +
                        " (SELECT A.Number_overhead FROM (SELECT Number_overhead, SUM(Quantity)" +
                        " FROM Position_overhead GROUP BY Number_overhead ORDER BY SUM DESC) AS A ORDER BY Number_overhead LIMIT 10)) ");
                while (rs.next()) {
                    result.add(new Organization(rs.getInt("Org_id"), rs.getString("Name_org"), rs.getInt("INN"), rs.getInt("Account")));
                }
            } catch (SQLException e) {
                e.getStackTrace();
            }
            for (Organization organization : result){
                System.out.println(organization.toString());
            }
        }
        catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        }

    }
}
