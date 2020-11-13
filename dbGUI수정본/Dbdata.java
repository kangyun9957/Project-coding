import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Dbdata {
	String s;
	String p;
	String[] login;
	Dbdata(String s,String p,String[] login) throws SQLException,IOException{
				this.s=s;
				this.p=p;
				this.login=login;
				ArrayList<String> listB= new ArrayList();
				ArrayList<String> listA= new ArrayList();
				String dname,check,a1,a2,a3,a4,a5,a6,a7,a8;
				String [] tables = {"department","dependent","dept_locations","employee","project","works_on"}; 
			    String[] table = {"ºÎ¼­¸í","Research","Administration","Headquarters"};
		        Connection conn=null;
		         
		       
		        conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/"+login[0]+"?serverTimezone=UTC", login[1], login[2]);
		       
		        String stmt2 =  "select concat(e.fname,' ',e.minit,' ',e.lname) name,e.ssn,e.bdate,e.address,e.sex,e.salary,concat (s.fname,' ',s.minit,' ',s.lname) Super_name, d.dname\r\n from  ("+s+" as e left outer join "+s+" as s on e.super_ssn=s.ssn),department as d\r\n where e.dno=dnumber and d.dname='"+p+"'";
		       
		        PreparedStatement p2=conn.prepareStatement(stmt2);
		       
		   
		        ResultSet r3=p2.executeQuery();
		        listA.add("name");
	        	listA.add("ssn");
	        	listA.add("bdate");
	        	listA.add("address");
	        	listA.add("sex");
	        	listA.add("salary");
	        	listA.add("Super_ssn");
	        	listA.add("dname");
		        while(r3.next()){
		        	a1=r3.getString(1);
		        	a2=r3.getString(2);
		        	a3=r3.getString(3);
		        	a4=r3.getString(4);
		        	a5=r3.getString(5);
		        	a6=r3.getString(6);
		        	a7=r3.getString(7);
		        	a8=r3.getString(8);
		        	System.out.println(a1+"&"+a2+"&"+a3+"&"+a4+"&"+a5+"&"+a6+"&"+a7+"&"+a8);
	        		listB.add(a1+"&"+a2+"&"+a3+"&"+a4+"&"+a5+"&"+a6+"&"+a7+"&"+a8);
	        		
		        }
		        for(int i=0;i<6;i++) {
		        	if(tables[i]==s) {
		        		tables[i]=tables[0];
		        		tables[0]=s;
		        		
		        	}
		        }
		        Gui gui = new Gui(listA,listB,s,tables,login);
		       

		        try {
		            if (conn != null)
		                conn.close();
		        } catch (SQLException e) {
		        }
	}
	public ArrayList<String> getc(ArrayList<String> p){
		
		return p;
	}
	public static void main(String[] args) {
		

	}

}
