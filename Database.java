import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class Database {
  private Connection db;

  public Database(String url) {
    //some setup goes here
    db = connect(url);
  }

  public getProjectPreference(Person p, int preference) {
    //reference the DB, return the project ID or some shit
    String sql = "SELECT projectID FROM data WHERE projectPreference = preference";

  }

  public boolean getGender(Person p) {
    //reference the DB, return the gender
    //NOTE: seriously
  }

  public int[] getPrevYears(Person p) {
    //returns a list of previous three years
  }

  private Connection connect(String url) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
}
