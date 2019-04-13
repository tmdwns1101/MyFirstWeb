package utill;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseUtill {
	
	public static Connection getConnection() {
		try {
			String dbURL = "jdbc:mysql://localhost:3306/LECTUREEVALUATION?serverTimezone=UTC&useUnicode=yes&characterEncoding=UTF-8";
			//String dbURL = "jdbc:mysql://localhost:3306//LECTUREEVALUATION_schema?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC/LECTUREEVALUATION";
			String dbID = "root";
			String dbPassword = "wlsrhks15";
			Class.forName("com.mysql.cj.jdbc.Driver");
			return DriverManager.getConnection(dbURL, dbID, dbPassword);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

}