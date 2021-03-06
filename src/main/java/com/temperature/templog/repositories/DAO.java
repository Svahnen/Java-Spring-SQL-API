package com.temperature.templog.repositories;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.temperature.templog.models.Measurement;
import com.temperature.templog.models.Price;

public class DAO {
    private static DAO single_instance = null;

    Properties p = new Properties();
    Connection con;
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public DAO() {
        try {
            p.load(new FileInputStream("secrets.properties"));
            con = DriverManager.getConnection(p.getProperty("connectionstring"),
                    p.getProperty("username"), p.getProperty("password"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static DAO getInstance() {
        if (single_instance == null)
            single_instance = new DAO();

        return single_instance;
    }

    public List<Measurement> getAllMeasurements() {
        List<Measurement> allMeasurements = new ArrayList<>();

        try (Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(
                        "select measurement.idmeasurement 'id', measurement.value 'value', measurement.date 'date', type.type 'type', section.name 'section' from measurement, type, section where measurement.idsection = section.idsection and measurement.idtype = type.idtype")) {
            while (rs.next()) {
                allMeasurements
                        .add(new Measurement(rs.getInt("id"), rs.getFloat("value"),
                                LocalDateTime.parse(rs.getString("date"), dateTimeFormatter),
                                rs.getString("type"), rs.getString("section")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allMeasurements;
    }

    public List<Measurement> getAllMeasurements(String type) {
        List<Measurement> allMeasurements = new ArrayList<>();

        try (Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(
                        "select measurement.idmeasurement 'id', measurement.value 'value', measurement.date 'date', type.type 'type', section.name 'section' from measurement, type, section where measurement.idsection = section.idsection and measurement.idtype = type.idtype and type.type = '"
                                + type + "'")) {
            while (rs.next()) {
                allMeasurements
                        .add(new Measurement(rs.getInt("id"), rs.getFloat("value"),
                                LocalDateTime.parse(rs.getString("date"), dateTimeFormatter),
                                rs.getString("type"), rs.getString("section")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allMeasurements;
    }

    public List<Measurement> getAllMeasurements(String type, String section) {
        List<Measurement> allMeasurements = new ArrayList<>();

        try (Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(
                        "select measurement.idmeasurement 'id', measurement.value 'value', measurement.date 'date', type.type 'type', section.name 'section' from measurement, type, section where measurement.idsection = section.idsection and measurement.idtype = type.idtype and type.type = '"
                                + type + "' and section.name = '" + section + "'")) {
            while (rs.next()) {
                allMeasurements
                        .add(new Measurement(rs.getInt("id"), rs.getFloat("value"),
                                LocalDateTime.parse(rs.getString("date"), dateTimeFormatter),
                                rs.getString("type"), rs.getString("section")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allMeasurements;
    }

    public boolean deleteMeasurement(int id) {
        int rowChanged = 0;
        String query = "delete from measurement where idmeasurement = ? ";

        try (PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setInt(1, id);
            rowChanged = stmt.executeUpdate();
            if (rowChanged == 1) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Measurement getMeasurement(int id) {
        String query = "select measurement.idmeasurement 'id', measurement.value 'value', measurement.date 'date', type.type 'type', section.name 'section' from measurement, type, section where measurement.idsection = section.idsection and measurement.idtype = type.idtype and measurement.idmeasurement = ?";

        try (PreparedStatement stmt = con.prepareStatement(query);) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                return new Measurement(rs.getInt("id"), rs.getFloat("value"),
                        LocalDateTime.parse(rs.getString("date"), dateTimeFormatter),
                        rs.getString("type"), rs.getString("section"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Measurement();
    }

    public Measurement getLatestMeasurement(String type) {
        String query = "select measurement.idmeasurement 'id', measurement.value 'value', measurement.date 'date', type.type 'type', section.name 'section' from measurement, type, section where measurement.idsection = section.idsection and measurement.idtype = type.idtype and type.type = ? order by measurement.date desc limit 1";

        try (PreparedStatement stmt = con.prepareStatement(query);) {
            stmt.setString(1, type);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                return new Measurement(rs.getInt("id"), rs.getFloat("value"),
                        LocalDateTime.parse(rs.getString("date"), dateTimeFormatter),
                        rs.getString("type"), rs.getString("section"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Measurement();
    }

    public Measurement getLatestMeasurement(String type, String section) {
        String query = "select measurement.idmeasurement 'id', measurement.value 'value', measurement.date 'date', type.type 'type', section.name 'section' from measurement, type, section where measurement.idsection = section.idsection and measurement.idtype = type.idtype and type.type = ? and measurement.idsection = ? order by measurement.date desc limit 1";

        try (PreparedStatement stmt = con.prepareStatement(query);) {
            stmt.setString(1, type);
            if (section.equals("a")) {
                stmt.setInt(2, 1);
            } else if (section.equals("b")) {
                stmt.setInt(2, 2);
            } else if (section.equals("c")) {
                stmt.setInt(2, 3);
            }
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                return new Measurement(rs.getInt("id"), rs.getFloat("value"),
                        LocalDateTime.parse(rs.getString("date"), dateTimeFormatter),
                        rs.getString("type"), rs.getString("section"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Measurement();
    }

    public float getAverageMeasurement(String type, String section) {
        String query = "SELECT AVG (value) FROM measurement inner join section on measurement.idsection=section.idsection inner join type on measurement.idtype=type.idtype where type.type = ? and section.name = ?";

        try (PreparedStatement stmt = con.prepareStatement(query);) {
            stmt.setString(1, type);
            stmt.setString(2, section);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                return rs.getFloat(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int addMeasurement(Measurement m) {

        String queryInsert = "insert into measurement (value, date, idtype, idsection) values (?, ?, ?, ?)";

        try (PreparedStatement stmtInsert = con.prepareStatement(queryInsert)) {

            stmtInsert.setFloat(1, m.getValue());
            stmtInsert.setString(2, m.getDate().format(dateTimeFormatter));

            //TODO: a better fix for this is to use a lookup table
            if (m.getType().equals("temperature")) {
                stmtInsert.setInt(3, 1);
            } else if (m.getType().equals("moisture")) {
                stmtInsert.setInt(3, 2);
            }
            if (m.getSection().equals("a")) {
                stmtInsert.setInt(4, 1);
            } else if (m.getSection().equals("b")) {
                stmtInsert.setInt(4, 2);
            } else if (m.getSection().equals("c")) {
                stmtInsert.setInt(4, 3);
            }

            return stmtInsert.executeUpdate(); // Returns 1 if successful

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int addPrice(Price p) {
        String query = "insert into price (price, date) values (?, ?)";

        try (PreparedStatement stmt = con.prepareStatement(query);) {
            stmt.setFloat(1, p.getPrice());
            stmt.setString(2, p.getDate().format(dateTimeFormatter));

            return stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Price> getAllPrices() {
        List<Price> allPrices = new ArrayList<>();

        try (Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(
                        "SELECT * FROM temperature.price;")) {
            while (rs.next()) {
                allPrices
                        .add(new Price(rs.getInt("idprice"), rs.getFloat("price"),
                                LocalDateTime.parse(rs.getString("date"), dateTimeFormatter)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allPrices;
    }

    public Price getLatestPrice() {
        String query = "SELECT * FROM temperature.price order by date desc limit 1;";

        try (PreparedStatement stmt = con.prepareStatement(query);) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                return new Price(rs.getInt("idprice"), rs.getFloat("price"),
                        LocalDateTime.parse(rs.getString("date"), dateTimeFormatter));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Price();
    }

}