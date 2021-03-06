import java.sql.*;

public class JDBC_Example {
    private final String dbAdresse = "127.0.0.1"; // localhost
    private final String dbInstanz = "XE"; // XE-Version
    private Connection con = null;

    public static void main(final String[] s) {
        // final JDBC_Example db = new JDBC_Example();
        // db.verbinden("Kleuker", "Kleuker");
        // db.verbindungAnalysieren();
        // final String anfrage = "SELECT Tier.Tname, Tier.Gattung, Gehege.GNr,"
        // + " Gehege.GName " + "FROM Gehege,Tier "
        // + "WHERE Gehege.Gnr=Tier.Gnr " + " AND Tier.Gattung='Baer'";
        // db.anfragen(anfrage);
        //
        // db.verbindungTrennen();
        try (final Connection con = MyConnectionManager.getConnection()) {

            System.out.println("Connected.");
            final String text = "SELECT TABLE_NAME,NUM_ROWS FROM user_tables";
            final Statement stmt = con.createStatement();
            final ResultSet rs = stmt.executeQuery(text);
            stmt.close();
            // Matadaten des Anfrageergebnisses
            final ResultSetMetaData rsmd = rs.getMetaData();
            final int spalten = rsmd.getColumnCount();
            for (int i = 1; i <= spalten; i++) // nicht i=0
                System.out.println(i + ". Spaltenname: " + rsmd.getColumnName(i) + "\n Spaltentyp: "
                        + rsmd.getColumnTypeName(i) + "\n Javatyp: " + rsmd.getColumnClassName(i) + "\n");
            // Ergebnisausgabe
            while (rs.next()) {
                for (int i = 1; i <= spalten; i++)
                    System.out.print(rs.getString(i) + " ");
                System.out.print("\n");
            }
        } catch (final SQLException e) {
            e.printStackTrace();
        }

        try {
            MyConnectionManager.close();
        } catch (final SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void verbinden(final String nutzer, final String passwort) {
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            con = DriverManager.getConnection("jdbc:oracle:thin:@" + dbAdresse + ":1521:" + dbInstanz, nutzer,
                    passwort);
        } catch (final SQLException e) {
            ausnahmeAusgeben(e);
        }
    }

    private void ausnahmeAusgeben(SQLException e) {
        while (e != null) {
            System.err.println("ORACLE Fehlercode: " + e.getErrorCode());
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println(e);
            e = e.getNextException();
        }
    }

    public void verbindungTrennen() {
        if (con == null) {
            System.out.println("Trennen: es war sowieso keine Verbindung vorhanden");
            return;
        }
        try {
            con.close();
        } catch (final SQLException e) {
            ausnahmeAusgeben(e);
        }
    }

    public void verbindungAnalysieren() {
        if (con == null) {
            System.out.println("Analysieren: keine Verbindung vorhanden");
            return;
        }
        try {
            final DatabaseMetaData dbmd = con.getMetaData();
            System.out.println("DB-Name: " + dbmd.getDatabaseProductName() + "\nDB-Version: "
                    + dbmd.getDatabaseMajorVersion() + "\nDB-Release: " + dbmd.getDriverMinorVersion()
                    + "\nTransaktionen erlaubt: " + dbmd.supportsTransactions() + "\nbeachtet GroßKlein :"
                    + dbmd.storesMixedCaseIdentifiers() + "\nunterstützt UNION :" + dbmd.supportsUnion()
                    + "\nmax. Prozedurname: " + dbmd.getMaxProcedureNameLength());
        } catch (final SQLException e) {
            ausnahmeAusgeben(e);
        }
    }

    public void anfragen(final String anfrage) {
        if (con == null) {
            System.out.println("keine Verbindung");
            return;
        }
        try {
            final Statement stmt = con.createStatement();
            final ResultSet rs = stmt.executeQuery(anfrage);
            // Matadaten des Anfrageergebnisses
            final ResultSetMetaData rsmd = rs.getMetaData();
            final int spalten = rsmd.getColumnCount();
            for (int i = 1; i <= spalten; i++) // nicht i=0
                System.out.println(i + ". Spaltenname: " + rsmd.getColumnName(i) + "\n Spaltentyp: "
                        + rsmd.getColumnTypeName(i) + "\n Javatyp: " + rsmd.getColumnClassName(i) + "\n");
            // Ergebnisausgabe
            while (rs.next()) {
                for (int i = 1; i <= spalten; i++)
                    System.out.print(rs.getString(i) + " ");
                System.out.print("\n");
            }
        } catch (final SQLException e) {
            ausnahmeAusgeben(e);
        }
    }

    public void statementVorbereiten(final String anfrage) {
        if (con == null) {
            System.out.println("keine Verbindung");
            return;
        }

        try (PreparedStatement prep = con.prepareStatement(anfrage)) {
            prep.setString(1, "Baer");
            prep.setInt(2, 3);
        } catch (final SQLException e) {
            ausnahmeAusgeben(e);
        }
    }
}
