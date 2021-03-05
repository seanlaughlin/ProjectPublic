package bean;
import java.io.OutputStream;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.*;

public class PassHash implements java.io.Serializable {
        private String generatedPassword = null;
        public String hashPassword(String passwordToHash) {
             generatedPassword = BCrypt.hashpw(passwordToHash, BCrypt.gensalt(12));
            return generatedPassword;
        }
       public boolean checkPassword(String email, String password) throws SQLException{
            try {
                Class.forName("org.sqlite.JDBC");
                String dataPass;
                try (Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\seanl\\GCUSkillsDb.db")) {
                   Statement stat = conn.createStatement();
                   ResultSet rs2 = stat.executeQuery("SELECT * FROM students WHERE emailaddress=\""+email+"\";");
                   dataPass = rs2.getString("Password");
               }
               return BCrypt.checkpw(password, dataPass);
            }
            catch (ClassNotFoundException e) {
                return false;
            }  
        }
}
