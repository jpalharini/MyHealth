package android.palharini.myhealth.ws;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConectaSQL {

	private static final String URL = "jdbc:mysql://localhost/myhealth";
	private static final String USER = "turma4k";
	private static final String PASS = "1fsul2o14";
	
	public static Connection obtemConexao() throws SQLException{
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return DriverManager.getConnection(URL, USER, PASS);
	}
}
