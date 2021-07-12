import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringJoiner;

public class Dbinsert {
	String s;
	String p;
	String[] login;
	Dbinsert(String s,String p,String[] login) throws SQLException,IOException{
				this.s=s;
				this.p=p;
				this.login=login;
				ArrayList<String> change = new ArrayList();
				String array[] = s.split(",");
				String concatArray[] = null;
				if (p.equalsIgnoreCase("EMPLOYEE")) {
					String namesArray[] = array[0].split(" ");
					concatArray = new String[array.length + namesArray.length - 1];
					System.arraycopy(namesArray, 0, concatArray, 0, namesArray.length);
					System.arraycopy(array, 1, concatArray, namesArray.length, array.length-1);

				}
				else {
					concatArray = new String[array.length];
					System.arraycopy(array, 0, concatArray, 0, array.length);
				}
				
				for(int i=0;i<concatArray.length;i++) {
					change.add("?");
				}
				
				StringJoiner sj = new StringJoiner(",");
				for (String j : change) {
				    sj.add(j);
				}
			       
		        Connection conn=null;
		        String update;
		       
		        
		        conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/"+login[0]+"?serverTimezone=UTC", login[1], login[2]);
		        String stmt2 = "INSERT INTO "+p+" VALUES ("+sj+")";//리스트 사이즈만큼 ?, 만들어서 +해주자
		        
		        PreparedStatement p2=conn.prepareStatement(stmt2);
		        p2.clearParameters();
		        
			    for(int i=1;i<concatArray.length+1;i++) {
			        p2.setString(i,concatArray[i-1]);
			    }

		   
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