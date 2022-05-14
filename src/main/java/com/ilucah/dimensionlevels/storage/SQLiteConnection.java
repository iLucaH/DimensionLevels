package com.ilucah.dimensionlevels.storage;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

public class SQLiteConnection {

    private final HikariConfig config = new HikariConfig();
    private final HikariDataSource ds;

    public SQLiteConnection(File file) {
        config.setPoolName("AuthMeSQLitePool");
        config.setDriverClassName("org.sqlite.JDBC");
        config.setJdbcUrl("jdbc:sqlite:" + file.getAbsolutePath());
        config.setConnectionTestQuery("SELECT 1");
        config.setMaxLifetime(60000);
        config.setIdleTimeout(45000);
        config.setMaximumPoolSize(50);
        ds = new HikariDataSource(config);
        createTable();
    }

    public void createTable() {
        PreparedStatement ps;
        try {
            ps = ds.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS userdata "
                    + "(NAME VARCHAR(100),UUID VARCHAR(100),UD VARCHAR(255),PRIMARY KEY (NAME))");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void submitUserData(SerializableUserData player) {
        try {
            if (!exists(player.getUuid())) {
                PreparedStatement ps2 = ds.getConnection().prepareStatement("INSERT IGNORE INTO" +
                        " userdata (NAME,UUID) VALUES (?,?)");
                ps2.setString(1, player.getName());
                ps2.setString(2, player.getUuid());
                ps2.setString(3, player.serialize());
                ps2.executeUpdate();
                return;
            } else {
                setUserDataInDB(player);
            }
        } catch (SQLException e) {

        }
    }

    public boolean exists(String uuid) {
        try {
            PreparedStatement ps = ds.getConnection().prepareStatement("SELECT * FROM userdata WHERE UUID=?");
            ps.setString(1, uuid);
            ResultSet results = ps.executeQuery();
            if (results.next()) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void setUserDataInDB(SerializableUserData userData) {
        System.out.println("Setting in db");
        try {
            PreparedStatement ps = ds.getConnection().prepareStatement("UPDATE userdata SET UD=? WHERE UUID=?");
            ps.setString(1, userData.serialize());
            ps.setString(2, userData.getUuid());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<SerializableUserData> getUserDataFromDB(String uuid) {
        try {
            PreparedStatement ps = ds.getConnection().prepareStatement("SELECT UD FROM userdata WHERE UUID=" + uuid);
            //ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            String query;
            if (rs.next()) {
                query = rs.getString("UD");
                System.out.println(query);
                return Optional.ofNullable(SerializableUserData.fromString(query));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("getting null");
        return Optional.ofNullable(null);
    }

    public Optional<SerializableUserData> getUserDataFromDB(UUID uuid) {
        return getUserDataFromDB(uuid.toString());
    }
}
