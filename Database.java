/**
    Database.java

    Utility class for SQL Database

    Changelog:

    Jackson Bremen
    -November 4, 2019, 23:00: Implemented V1
*/

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.*;

public class Database {
    private final String url;

    public Database(String url) {
        //some setup goes here
        this.url = url;
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

    /**
        public int getPreference(int studentId, int preference)

        takes in a student ID and Preference number

        Precondition:
            studentId is valid
    */
    public int getPreference(int studentId, int preference) {
        //reference the DB, return whatever
        String sql = "SELECT ProjId FROM Raw WHERE StudentID = (?) AND PrefNum = (?)";

        try {
            Connection conn = this.connect();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDouble(1, studentId);
            stmt.setDouble(2, preference);

            ResultSet rs = stmt.executeQuery();
            return rs.getInt(1);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

    /**
        public String getGender(int studentId,)

        takes in a student ID and returns the gender

        Precondition:
            studentId is valid
    */
    public String getGender(int studentId) {
        //reference the DB, return the gender
        //NOTE: might want to make this more PC in the future
        String sql = "SELECT Gender FROM Raw WHERE StudentID = (?)";

        try {
            Connection conn = this.connect();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDouble(1, studentId);

            ResultSet rs = stmt.executeQuery();
            return rs.getString(1);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return "-1";

    }

    /**
        public String getGrade(int studentId,)

        takes in a student ID and returns the grade/class year

        Precondition:
            studentId is valid
    */
    public int getGrade(int studentId) {
        //reference the DB, return the gender
        //NOTE: might want to make this more PC in the future
        String sql = "SELECT GradYear FROM Raw WHERE StudentID = (?)";

        try {
            Connection conn = this.connect();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDouble(1, studentId);

            ResultSet rs = stmt.executeQuery();
            return rs.getInt(1);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;

    }

    /**
        public int[] getPrevYears(int studentId)

        takes in a student ID, returns choices of previous 3 years

        Precondition:
            studentId is valid
    */
    public int[] getPrevYears(int studentId) {
        //returns a list of previous three years
        int prevYears[] = new int[3];
        String sql;

        sql = "SELECT Rank18, Rank17, Rank16 FROM StudentPrevAvgs WHERE StudentID = (?)";

        try {
            Connection conn = this.connect();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setDouble(1, studentId);

            ResultSet rs = stmt.executeQuery();

            for (int i=0; i<3; i++) {
              prevYears[i] = rs.getInt(i+1);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return prevYears;
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

            ResultSet rs = stmt.executeQuery();
            return rs.getInt(1);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
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

            ResultSet rs = stmt.executeQuery();
            return rs.getInt(1);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

    /**
        public ArrayList<Integer> getAllStudentIds()

        Returns integer ArrayList holding all student ids
    */
    public ArrayList getAllStudentIds() {
        String sql = "SELECT StudentId FROM StudentPrevAvgs";
        ArrayList<Integer> IDValues = new ArrayList<Integer>();

        try {
            Connection conn = this.connect();
            PreparedStatement stmt = conn.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                IDValues.add(rs.getInt(1));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return IDValues;
    }

    /**
        public ArrayList<Integer> getAllStudentIds()

        Returns integer ArrayList holding all student ids
    */
    public ArrayList getAllCourseIds() {
        String sql = "SELECT ProjID FROM ProjStats";
        ArrayList<Integer> IDValues = new ArrayList<Integer>();

        try {
            Connection conn = this.connect();
            PreparedStatement stmt = conn.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                IDValues.add(rs.getInt(1));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return IDValues;
    }

}
