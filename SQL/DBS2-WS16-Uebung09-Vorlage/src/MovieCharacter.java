import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by leonard on 23.11.16.
 */
public class MovieCharacter {
    public long getMovieCharID() {
        return movieCharID;
    }

    public void setMovieCharID(long movieCharID) {
        this.movieCharID = movieCharID;
    }

    private long movieCharID = -1L;
    private long movieID;
    private long personID;
    private String rolle;
    private String alias;
    private int pos;

    public long getMovieID() {
        return movieID;
    }

    public void setMovieID(long movieID) {
        this.movieID = movieID;
    }

    public long getPersonID() {
        return personID;
    }

    public void setPersonID(long personID) {
        this.personID = personID;
    }

    public String getRolle() {
        return rolle;
    }

    public void setRolle(String rolle) {
        this.rolle = rolle;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public void insert() throws SQLException {
        setMovieID(getNewId());
        String sql = "INSERT INTO moviecharacter(movcharid, character, alias, position) VALUES ( ?, ?, ?, ?)";
        try (PreparedStatement stmt = MyConnectionManager.getConnection().prepareStatement(sql)) {
            stmt.setLong(1, getMovieCharID());
            stmt.setString(2, getRolle());
            stmt.setString(3, getAlias());
            stmt.setInt(4, getPos());
            stmt.executeUpdate();
        }
    }

    public long getNewId() throws SQLException {
        if (getMovieCharID() == -1) {
            String query = "select moviecharacter_sq.nextval from dual";
            try (PreparedStatement stmt = MyConnectionManager.getConnection().prepareStatement(query)) {
                ResultSet id = stmt.executeQuery();
                id.next();
                setMovieCharID(id.getLong(1));
            }
        }
        return getMovieCharID();
    }​


}
