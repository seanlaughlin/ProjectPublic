package bean;

import java.io.OutputStream;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.*;

public class PassHash implements java.io.Serializable {

    private final String driver = "net.ucanaccess.jdbc.UcanaccessDriver";
    private final String connectionString = "jdbc:ucanaccess://C:\\Users\\seanl\\Documents\\NetBeansProjects\\GCU_Skills\\data\\GCU_SkillsDB.accdb";
    private String generatedPassword = null;

    public String hashPassword(String passwordToHash) {
        generatedPassword = BCrypt.hashpw(passwordToHash, BCrypt.gensalt(12));
        return generatedPassword;
    }

    public boolean checkPassword(String email, String password) throws SQLException {
        String dataPass = "";
        try {
            Class.forName(driver);
            try (Connection conn = DriverManager.getConnection(connectionString)) {
                Statement stat = conn.createStatement();
                ResultSet rs = stat.executeQuery("SELECT * FROM Students WHERE EmailAddress=\"" + email + "\"");
                while (rs.next()) {
                    dataPass = rs.getString("Password");
                }
            }

            return BCrypt.checkpw(password, dataPass);
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}
