//Changelog: Jackson

import java.sql.DriverManager;  
import java.sql.Connection;  
import java.sql.ResultSet;  
import java.sql.SQLException;  
import java.sql.PreparedStatement;  

class Database {
    private String url;

    public Database(String url) {
        //some setup goes here
        this.url = url;
    }

    /**
        public int getPreference(int studentId, int preference)

        takes in a student ID and Preference number

        Precondition:
            studentId is valid
    */
    public int getPreference(int studentId, int preference) {
        //reference the DB, return whatever
        String sql = "SELECT ProjId FROM data WHERE StudentID = (?) AND PrefNum = (?)";

        try {
            Connection conn = this.connect();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDouble(1, studentId);
            stmt.setDouble(2, preference);

            ResultSet rs = stmt.executeQuery(sql)
            return rs.getInt(0);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
        public String getGender(int studentId,)

        takes in a student ID and returns the gender

        Precondition:
            studentId is valid
    */
    public String getGender(int studentId) {
    //reference the DB, return the gender
    //NOTE: seriously
    }

    /**
        public int[] getPrevYears(int studentId) 

        takes in a student ID, returns choices of previous 3 years

        Precondition:
            studentId is valid
    */
    public int[] getPrevYears(int studentId) {
        //returns a list of previous three years
    }

    /**
        public int[] getMinStudents(int projectId) 

        takes in a project ID, returns minimum number of students for the project 

        Precondition:  
            projectId is valud
    */
    public int getMinStudents(int projID) {
        String sql = "SELECT MinStudents FROM ProjStats WHERE ProjID = (?)";

        try {
            Connection conn = this.connect();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDouble(1, projID);

            ResultSet rs = stmt.executeQuery(sql)
            return rs.getInt(0);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    
    }

    /**
        public int[] getMaxStudents(int projectId) 

        takes in a project ID, returns maximum number of students for the project 

        Precondition:  
            projectId is valud
    */
    public int getMaxStudents(int projID) {
        String sql = "SELECT MaxStudents FROM ProjStats WHERE ProjID = (?)";

        try {
            Connection conn = this.connect();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDouble(1, projID);

            ResultSet rs = stmt.executeQuery(sql)
            return rs.getInt(0);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    private Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(this.url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
}
