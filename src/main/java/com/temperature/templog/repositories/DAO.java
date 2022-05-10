package com.temperature.templog.repositories;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.temperature.templog.models.Measurement;

public class DAO {

    Properties p = new Properties();
    Connection con;

    public DAO() {
        try {
            p.load(new FileInputStream("secrets.properties"));
            con = DriverManager.getConnection(p.getProperty("connectionstring"),
                    p.getProperty("username"), p.getProperty("password"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Measurement> getAllMeasurements() {
        List<Measurement> allTemperatures = new ArrayList<>();

        try (Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(
                        "select measurement.idmeasurement 'id', measurement.value 'value', measurement.date 'date', type.type 'type', section.name 'section' from measurement, type, section where measurement.idsection = section.idsection and measurement.idtype = type.idtype")) {
            while (rs.next()) {
                allTemperatures
                        .add(new Measurement(rs.getInt("id"), rs.getFloat("value"), rs.getDate("date").toLocalDate(),
                                rs.getString("type"), rs.getString("section")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allTemperatures;
    }

    public List<Measurement> getAllMeasurements(String type) {
        List<Measurement> allTemperatures = new ArrayList<>();

        try (Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(
                        "select measurement.idmeasurement 'id', measurement.value 'value', measurement.date 'date', type.type 'type', section.name 'section' from measurement, type, section where measurement.idsection = section.idsection and measurement.idtype = type.idtype and type.type = '"
                                + type + "'")) {
            while (rs.next()) {
                allTemperatures
                        .add(new Measurement(rs.getInt("id"), rs.getFloat("value"), rs.getDate("date").toLocalDate(),
                                rs.getString("type"), rs.getString("section")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allTemperatures;
    }

    public List<Measurement> getAllMeasurements(String type, String section) {
        List<Measurement> allTemperatures = new ArrayList<>();

        try (Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(
                        "select measurement.idmeasurement 'id', measurement.value 'value', measurement.date 'date', type.type 'type', section.name 'section' from measurement, type, section where measurement.idsection = section.idsection and measurement.idtype = type.idtype and type.type = '"
                                + type + "' and section.name = '" + section + "'")) {
            while (rs.next()) {
                allTemperatures
                        .add(new Measurement(rs.getInt("id"), rs.getFloat("value"), rs.getDate("date").toLocalDate(),
                                rs.getString("type"), rs.getString("section")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allTemperatures;
    }

    /* 
    public boolean deleteChild(int id) {
        int rowChanged = 0;
        String query = "delete from child where id = ? ";
    
        try (PreparedStatement stmt = con.prepareStatement(query)) {
    
            stmt.setInt(1, id);
            System.out.println("deleted child " + id);
            rowChanged = stmt.executeUpdate();
            if (rowChanged == 1) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean updateChild(Child c) {
    
        String queryUpdate = "UPDATE child SET name = ?, address = ?, countryId = ?, nice = ? WHERE id = ?";
        String queryInsert = "insert into child (name, address, countryId, nice) values (?, ?, ?, ?)";
        int rowChanged = -1;
    
        try (PreparedStatement stmtUpdate = con.prepareStatement(queryUpdate);
                PreparedStatement stmtInsert = con.prepareStatement(queryInsert)) {
    
            stmtUpdate.setString(1, c.getName());
            stmtUpdate.setString(2, c.getAddress());
            stmtUpdate.setString(3, c.getCountryId());
            stmtUpdate.setBoolean(4, c.isNice());
            stmtUpdate.setInt(5, c.getId());
            rowChanged = stmtUpdate.executeUpdate(); // Returns 1 if successful
            System.out.println("child, rowChanged " + rowChanged);
    
            //Not found, insert (add new child)
            if (rowChanged == 0) {
                stmtInsert.setString(1, c.getName());
                stmtInsert.setString(2, c.getAddress());
                stmtInsert.setString(3, c.getCountryId());
                stmtInsert.setBoolean(4, c.isNice());
                rowChanged = stmtInsert.executeUpdate();
                System.out.println("Adding new child " + c.getName());
            }
            if (rowChanged == 1) { //Row was updated
                return true;
            }
    
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    } */

}