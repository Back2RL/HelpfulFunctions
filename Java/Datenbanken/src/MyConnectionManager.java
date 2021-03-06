
//package de.fhhannover.inform.vl.db.u13;

import oracle.jdbc.pool.OracleDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;

public class MyConnectionManager {

    private static String dburl;

    /**
     * Dient dem Connection-Pooling
     */
    private static volatile Vector<Connection> connectionPool = new Vector<Connection>(5);

    static {
        // Die Initialisierung der Connect-URL findet nur einmal statt.
        final String uid = "user"; // TODO Be auf der Datenbank angeben
        final String sid = "db01";
        final String pwd = "password"; // TODO Passwort zum obigen Benutzer
        // angeben
        final String driver = "thin";
        final String server = "dboracleserv.inform.hs-hannover.de";
        dburl = "jdbc:oracle:" + driver + ":" + uid + "/" + pwd + "@" + server + ":1521:" + sid;
    }

    // ###########################################################################

    /**
     * Liefert eine vorhandene Connection aus dem Pool (erzeugt wenn nötig dafür
     * eine neue)
     *
     * @return Connection
     */
    public static synchronized Connection getConnection() throws SQLException {
        if (connectionPool.size() == 0)
            connectionPool.addElement(newConnection());
        final Connection conn = connectionPool.remove(0);
        return conn;
    }

    // ###########################################################################

    /**
     * Gibt eine Connection wieder frei und damit zurück in den Pool
     *
     * @param Connection
     */
    public static void release(final Connection conn) {
        if (!connectionPool.contains(conn))
            connectionPool.add(conn);
    }

    // ###########################################################################

    /**
     * Beendet alle Datenbank-Connections und löscht sie aus dem
     * Connection-Pool.
     */
    public static void close() throws SQLException {
        for (final Connection c : connectionPool) {
            c.commit();
            c.close();
        }
        connectionPool.removeAllElements();
    }

    // ###########################################################################

    /**
     * Erzeuge neue Connection. Autocommit wird nicht explizit gesetzt, ist also
     * per defaul aktiv.
     *
     * @return Connection
     */
    private static Connection newConnection() throws SQLException {
        final OracleDataSource ods = new OracleDataSource();
        ods.setURL(dburl);
        final Connection conn = ods.getConnection();
        return conn;
    }
}