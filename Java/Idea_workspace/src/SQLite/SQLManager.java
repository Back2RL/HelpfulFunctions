package SQLite;


import SortingBySelection.RatedImage;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLManager {

    private static final String tableName = "images";

    private Connection connection;
    private Statement statement;
    private String pathToDatabaseDir;

    public static void main(String[] args) {
        new SQLManager();
    }

    public SQLManager() {
        connection = null;

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (Exception e) {
            e.printStackTrace();
        }

        initDataDirectory();
        initConnection();
//        deleteTable();
        initDatabase();
//
//        try (PreparedStatement prep = connection.prepareStatement("insert into " + tableName + " values (?, ?, (select max(rating) from " + tableName + ") + 1);")) {
//            // TODO: write method to insert multiple
//            // TODO: take object array containing new data
//
//            checkDummyEntry();
//
//            for (int i = 0; i < 100; ++i) {
//                //insertSingleEntry(new Integer(i).toString(),new Integer(i).toString(),i);
//                addPreparedEntry(prep,
//                        Integer.toString(i),
//                        Integer.toString(i));
//            }
//
//            addPreparedEntry(prep, "Gandhi", "politics");
//            addPreparedEntry(prep, "Turing", "computers");
//            addPreparedEntry(prep, "Wittgenstein", "smartypants");
//            addPreparedEntry(prep, "Leo", "Student");
//
//            connection.setAutoCommit(false);
//            prep.executeBatch();
//            connection.setAutoCommit(true);
//
//        } catch (SQLException e) {
//            System.err.println(e.getLocalizedMessage());
//        }
//
//
//        try (ResultSet rs = statement.executeQuery("select * from " + tableName + ";")) {
//            while (rs.next()) {
//                System.out.println("md5 = " + rs.getString("md5") + "; filepath = " + rs.getString("filepath") + "; rating = " + rs.getInt("rating"));
//            }
//            rs.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//
//        System.out.println("Method call test:");
//        String occupation = getFilePathOf(connection, "Leo");
//        System.out.println(occupation);
//
//        try {
//            connection.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

    public static String getFilePathOf(Connection conn, String md5) {
        if (conn == null || md5 == null || md5.equals("")) {
            return null;
        }
        try (PreparedStatement prep = conn.prepareStatement("select filepath from " + tableName + " where md5 = ?;")) {
            prep.setString(1, md5);
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                String entry = rs.getString("filepath");
                System.out.println("filepath of " + md5 + " = " + entry);
                rs.close();
                return entry;
            }
            rs.close();
        } catch (SQLException e) {
            System.err.println(e.getLocalizedMessage());
        }
        return null;
    }

    private boolean initDataDirectory() {
        // create directory where database will be stored TODO: make path customizable
        String userDir = System.getProperty("user.home") + File.separatorChar;
        pathToDatabaseDir = userDir + ".SQL_storage" + File.separatorChar;
        if (!new File(pathToDatabaseDir).exists()) {
            System.out.println("Creating new Database directory: " + pathToDatabaseDir);
            return new File(pathToDatabaseDir).mkdir();
        } else {
            System.out.println("Database directory exits: " + pathToDatabaseDir);
        }
        return true;
    }

    private void initConnection() {
        if (pathToDatabaseDir == null) {
            System.err.println("initConnection: Path to database-directory is null!");
            return;
        }
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + pathToDatabaseDir + "data.db");
            statement = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initDatabase() {
        if (statement == null) {
            System.err.println("initDatabase: Statement is null");
            return;
        }
        try {
            //stat.executeUpdate("drop table if exists "+tableName"+;");
            statement.executeUpdate("create table " + tableName + " (md5 primary key not null, filepath not null, rating int);");
        } catch (SQLException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    private void addPreparedEntry(PreparedStatement prep, String key, String data) {
        if (prep == null || key == null || data == null) {
            System.err.println("addPreparedStatement: one or more parameters are null");
            return;
        }
        try {
            prep.setString(1, key);
            prep.setString(2, data);
            prep.addBatch();
        } catch (SQLException e) {
            System.err.println(e.getLocalizedMessage() + "; " + key + "; " + data);
        }
    }

    private void checkDummyEntry() {
        try (ResultSet rs = statement.executeQuery("select * from " + tableName + ";")) {
            if (!rs.next()) {
                insertSingleEntry("none", "none", -1);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTable() {
        try {
            statement.executeUpdate("drop table if exists " + tableName + ";");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean insertSingleEntry(String md5, String filepath, int rating) {
        try (PreparedStatement prep = connection.prepareStatement("insert into " + tableName + " values (?, ?, ?);")) {
            prep.setString(1, md5);
            prep.setString(2, filepath);
            prep.setString(3, String.valueOf(rating));
            prep.addBatch();
            connection.setAutoCommit(false);
            prep.executeBatch();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            System.err.println(e.getLocalizedMessage());
            return false;
        }
        return true;
    }

    public void insertMultipleEntries(List<RatedImage> elems) {

        int index = 0;
        while (index < elems.size()) {
            try (PreparedStatement prep = connection.prepareStatement("insert into " + tableName + " values (?, ?, ?);")) {
                while (index < elems.size()) {
                    System.out.println(elems.get(index).getMd5Hash()+"  "+elems.get(index).getPath()+"  "+index);

                    prep.setString(1, elems.get(index).getMd5Hash());
                    prep.setString(2, elems.get(index).getPath());
                    prep.setString(3, Integer.toString(index));
                    prep.addBatch();
                    // TODO: find a better solution for this
                    connection.setAutoCommit(false);
                    prep.executeBatch();
                    connection.setAutoCommit(true);
                    index++;
                }

//                addPreparedEntry(prep, "Gandhi", "politics");
//                addPreparedEntry(prep, "Turing", "computers");
//                addPreparedEntry(prep, "Wittgenstein", "smartypants");
//                addPreparedEntry(prep, "Leo", "Student");

                connection.setAutoCommit(false);
                prep.executeBatch();
                connection.setAutoCommit(true);

            } catch (SQLException e) {
                System.err.println("Insert Multiple: " + e.getLocalizedMessage());

                try (PreparedStatement prep = connection.prepareStatement("update " + tableName + " set rating = ? where md5 = ?;")) {

                    System.out.println("trying to update the entry");
                    prep.setString(1, Integer.toString(index));
                    prep.setString(2, elems.get(index).getMd5Hash());
                    prep.addBatch();


//                addPreparedEntry(prep, "Gandhi", "politics");
//                addPreparedEntry(prep, "Turing", "computers");
//                addPreparedEntry(prep, "Wittgenstein", "smartypants");
//                addPreparedEntry(prep, "Leo", "Student");

                    connection.setAutoCommit(false);
                    prep.executeBatch();
                    connection.setAutoCommit(true);
index++;
                } catch (SQLException ex) {
                    System.err.println("Update: " + ex.getLocalizedMessage());
                }
            }
        }

    }

    public List<RatedImage> getAllEntries() {

        List<RatedImage> result = new ArrayList<>();
        try (ResultSet rs = statement.executeQuery("select * from " + tableName + " order by rating;")) {
            while (rs.next()) {
                result.add(new RatedImage(rs.getString("md5"), rs.getString("filepath"), rs.getInt("rating")));
                System.out.println("md5 = " + rs.getString("md5") + "; filepath = " + rs.getString("filepath") + "; rating = " + rs.getInt("rating"));
            }
            rs.close();
        } catch (SQLException e) {
            System.err.println("get all Entries: " + e.getLocalizedMessage());
        }
        return result;
    }
}
