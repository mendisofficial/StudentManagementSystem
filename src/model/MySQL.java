/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author chathushamendis
 */
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.Connection;
import org.mindrot.jbcrypt.BCrypt;
import singleton.UserSession;

public class MySQL {

    private static final String DB_NAME = "dpitcDB";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASWORD = "root";

    public static Connection connection = null;

    public static void createConnection() throws Exception {
        if (connection == null) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL + DB_NAME, DB_USERNAME, DB_PASWORD);
        }
    }

    public static ResultSet executeSearch(String query) throws Exception {
        createConnection();
        return connection.createStatement().executeQuery(query);
    }

    public static Integer executeIUD(String query) throws Exception {
        createConnection();
        return connection.createStatement().executeUpdate(query);
    }

    public static boolean validateUser(String username, String password) throws Exception {
        createConnection();
        String query = "SELECT * FROM users WHERE username = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            String storedHash = resultSet.getString("password");
            if (BCrypt.checkpw(password, storedHash)) {
                UserSession userSession = UserSession.getInstance();
                userSession.setUsername(resultSet.getString("username"));
                userSession.setRole(resultSet.getString("role"));
                return true;
            }
        }
        return false;
    }

    public static ResultSet getStudentDetails() throws Exception {
        createConnection();
        String query = "SELECT s.student_id, s.full_name, s.address, s.dob, s.telephone, c.class_name, st.type_name, g.gender_name, ss.status_name "
                + "FROM students s "
                + "INNER JOIN classes c ON s.class_id = c.class_id "
                + "INNER JOIN student_types st ON s.student_type_id = st.student_type_id "
                + "INNER JOIN gender g ON s.gender_id = g.gender_id "
                + "INNER JOIN student_status ss ON s.status_id = ss.status_id";
        return executeSearch(query);
    }
}
