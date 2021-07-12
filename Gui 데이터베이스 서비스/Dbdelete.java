import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Dbdelete {
	String s;
	String p;
	String[] login;
	Dbdelete(String s,String p,String[] login) throws SQLException,IOException{
				this.s=s;
				this.p=p;
				this.login=login;
				
			       
		        Connection conn=null;
		            
		       
		        
		        conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/"+login[0]+"?serverTimezone=UTC", login[1], login[2]);
		       
		        String stmt2 = "delete from "+p+" where "+s+" ";
		       
		        PreparedStatement p2=conn.prepareStatement(stmt2);
		        
		        p2.executeUpdate();
		   
		      
		      
		       
		       

		        try {
		            if (conn != null)
		                conn.close();
		        } catch (SQLException e) {
		        }
	}
	public static void main(String[] args) {
		

	}

}