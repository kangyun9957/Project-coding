import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Dbupdate {
	String s;
	String p;
	ArrayList <String> ssn;
	String[] login;
	Dbupdate(ArrayList<String> ssn,String s,String p,String[] login) throws SQLException,IOException{
				this.s=s;
				this.p=p;
				this.ssn=ssn;
				this.login=login;
			       
		        Connection conn=null;
		        ArrayList<String> listA = new ArrayList();        
		       
		        
		        
		        conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/"+login[0]+"?serverTimezone=UTC", login[1], login[2]);
		        for(int i=0;i<ssn.size();i++) {
		        String stmt2 = "Update "+p+" set "+s+" where ssn = '"+ssn.get(i)+"' ";//p에 테이블, set 열='원하는값', where 조건절
		       
		        PreparedStatement p2=conn.prepareStatement(stmt2);
		        p2.executeUpdate();
		   
		        }
		       

		        try {
		            if (conn != null)
		                conn.close();
		        } catch (SQLException e) {
		        }
	}
	public static void main(String[] args) {
		

	}

}