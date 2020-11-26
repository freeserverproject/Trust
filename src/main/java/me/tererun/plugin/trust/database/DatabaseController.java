package me.tererun.plugin.trust.database;

import me.tererun.plugin.trust.Trust;
import me.tererun.plugin.trust.trust.UserTrust;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DatabaseController {

    private static Connection connection;
    private static PreparedStatement ps;
    private static String url;

    public DatabaseController(String fileName, String tableName) {
        connection = null;
        String filePath = Trust.getPlugin().getDataFolder().getAbsolutePath() + File.separator + fileName;
        url = "jdbc:sqlite:" + filePath;
        File file = new File(filePath);
        if (!file.exists()) {
            createNewDatabase(fileName);
            createTable(tableName);
        }

    }

    /**
     * Connect to a sample database
     *
     * @param fileName the database file name
     */
    public void createNewDatabase(String fileName) {
        String url = "jdbc:sqlite:" + Trust.getPlugin().getDataFolder().getAbsolutePath() + File.separator + fileName;

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.err.println("Create database error");
            System.out.println(e.getMessage());
        }
    }

    /**
     * SELECT文
     */
    public UserTrust loadData(String tableName, String uuid) {
        String sql;
        if (uuid == null) {
            sql = "SELECT * FROM " + tableName;
        } else {
            sql = "SELECT * FROM " + tableName + " WHERE uuid = '" + uuid + "'";
        }
        try {
            connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            ResultSet rs = statement.executeQuery(sql);
            return new UserTrust(UUID.fromString(rs.getString("uuid")), rs.getDouble("score"));
        } catch(SQLException e) {
            System.err.println("select error");
            System.err.println(e.getMessage());
        } finally {
            try {
                if(connection != null) connection.close();
            } catch(SQLException e) {
                System.err.println(e);
            }
        }
        return null;
    }

    public List<UserTrust> loadAllData(String tableName, String uuid) {
        String sql;
        if (uuid == null) {
            sql = "SELECT * FROM " + tableName;
        } else {
            sql = "SELECT * FROM " + tableName + " WHERE uuid = '" + uuid + "'";
        }
        try {
            List<UserTrust> userTrusts = new ArrayList<>();
            connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            ResultSet resultSet = statement.executeQuery(sql);
            try {
                while (resultSet.next()) {
                    System.out.println("loading " + resultSet.getString("uuid") + ": " + resultSet.getDouble("score"));
                    userTrusts.add(new UserTrust(UUID.fromString(resultSet.getString("uuid")), resultSet.getDouble("score")));
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return userTrusts;
        } catch(SQLException e) {
            System.err.println("SelectAll error");
            System.err.println(e.getMessage());
            return null;
        } finally {
            try {
                if(connection != null) connection.close();
            } catch(SQLException e) {
                System.err.println(e);
            }
        }
    }

    /**
     * SELECT文
     */
    public int getCount(String tableName, String uuid) {
        String sql;
        if (uuid == null) {
            sql = "SELECT COUNT(*) FROM " + tableName;
        } else {
            sql = "SELECT COUNT(uuid) FROM " + tableName + " WHERE uuid = '" + uuid + "'";
        }
        int i = 0;
        try {
            connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                i = rs.getInt(1);
            }
        } catch(SQLException e) {
            System.err.println("Count error");
            System.err.println(e.getMessage());
        } finally {
            try {
                if(connection != null) connection.close();
            } catch(SQLException e) {
                System.err.println(e);
            }
        }
        return i;
    }

    public void addData(String tableName, UserTrust userTrust) {
        String uuid = userTrust.getUser().toString();
        double score = userTrust.getPoint();
        String sql = "INSERT OR REPLACE INTO " + tableName + " (uuid, score) VALUES('" + uuid + "', " + score + ")";

        try {
            connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            statement.executeUpdate(sql);
            Trust.getPlugin().getLogger().severe(Trust.prefix + "Insert or Replace data: uuid = " + uuid + ", score = " + score);
        } catch(SQLException e) {
            System.err.println("Insert or Replace error");
            System.err.println(e.getMessage());
        } finally {
            try {
                if(connection != null) connection.close();
            } catch(SQLException e) {
                System.err.println(e);
            }
        }

        /* This is not used!
        if (getCount(tableName, uuid) == 0) {
            System.out.println("addData to insert: " + Bukkit.getOfflinePlayer(UUID.fromString(uuid)).getName());
            insertData(tableName, uuid, year, day);
        } else {
            System.out.println("addData to update: " + Bukkit.getOfflinePlayer(UUID.fromString(uuid)).getName());
            updateData(tableName, uuid, year, day);
        }
         */
    }

    /**
     * INSERT文
     */
    public void insertData(String tableName, UserTrust userTrust) {
        String uuid = userTrust.getUser().toString();
        double score = userTrust.getPoint();
        String sql = "INSERT INTO " + tableName + " (uuid, score) VALUES('" + uuid + "', " + score + ")";

        try {
            connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            statement.executeUpdate(sql);
            Trust.getPlugin().getLogger().severe(Trust.prefix + "Insert data: uuid = " + uuid + ", score = " + score);
        } catch(SQLException e) {
            System.err.println("Insert error");
            System.err.println(e.getMessage());
        } finally {
            try {
                if(connection != null) connection.close();
            } catch(SQLException e) {
                System.err.println(e);
            }
        }
    }

    /**
     * UPDATE文
     */
    public void updateData(String tableName, UserTrust userTrust) {
        String uuid = userTrust.getUser().toString();
        double score = userTrust.getPoint();
        try {
            String sql = "UPDATE " + tableName + " SET score = " + score + " WHERE uuid = '" + uuid + "'";
            connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            statement.executeUpdate(sql);
            Trust.getPlugin().getLogger().severe(Trust.prefix + "Update data: uuid = " + uuid + ", score = " + score);
        } catch(SQLException e) {
            System.err.println("Update error");
            System.err.println(e.getMessage());
        } finally {
            try {
                if(connection != null) connection.close();
            } catch(SQLException e) {
                System.err.println(e);
            }
        }
    }

    /**
     * DELETE文
     */
    public void deleteData(String tableName, String uuid) {
        try {
            String sql = "DELETE FROM " + tableName + " WHERE uuid = '" + uuid + "'";
            connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            int result = statement.executeUpdate(sql);
            Trust.getPlugin().getLogger().severe(Trust.prefix + "Delete data: " + result);
        } catch(SQLException e) {
            System.err.println("Delete error");
            System.err.println(e.getMessage());
        } finally {
            try {
                if(connection != null) connection.close();
            } catch(SQLException e) {
                System.err.println(e);
            }
        }
    }

    /**
     * テーブル作成
     */
    public void createTable(String tableName) {
        try {
            connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            statement.executeUpdate("CREATE TABLE " + tableName + " (uuid text primary key, score real)");
        } catch(SQLException e) {
            System.err.println("Create table error");
            System.err.println(e.getMessage());
        } finally {
            try {
                if(connection != null)
                    connection.close();
            } catch(SQLException e) {
                System.err.println(e);
            }
        }
    }

    /**
     * テーブル削除
     */
    public void dropTable(String tableName) {
        try {
            connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            statement.executeUpdate("DROP TABLE IF EXISTS " + tableName);
        } catch(SQLException e) {
            System.err.println("Drop table error");
            System.err.println(e.getMessage());
        } finally {
            try {
                if(connection != null) connection.close();
            } catch(SQLException e) {
                System.err.println(e);
            }
        }
    }
}
