import java.util.Scanner;

import oracle.jdbc.pool.OracleDataSource;

import java.sql.*;
/**
 * Created by leonard on 16.11.16.
 */
public class DBConnection {
    private static DBConnection ourInstance = new DBConnection();

    public static DBConnection getInstance() {
        return ourInstance;
    }

    // Die Initialisierung der Connect-URL findet nur einmal statt.
    final String uid;
    final String pwd;
    private Connection con;

    private DBConnection() {
        System.out.print("Username: ");
        uid = new Scanner(System.in).nextLine();
        pwd = Password.readPasswordFromConsole();
        connect();
    }

    public Connection connect(){
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            con = DriverManager.getConnection("jdbc:oracle:thin@dboracleserv.inform.hs-hannover.de:1521:db01", uid,pwd);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }
}
