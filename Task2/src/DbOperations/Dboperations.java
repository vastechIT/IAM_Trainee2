package DbOperations;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.vastech.Constants.Dbconstants;
import com.vastech.Utils.Update;
import com.vastech.Utils.Utils;
/* 1.calling a initConnection method to establish the connection
 * 2.calling a create table method to create the table if it is not exists in the database
 * 3.calling insert employee method insert the data into the table created in the database
 * 4.calling update method to update the records in the table
 */

public class Dboperations {

	public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {

		try {
			Connection connect=Utils.initConnection();
			Utils.createTable(connect, Dbconstants.csvfile);
			Utils.insertEmployee(connect,Dbconstants.csvfile );
			System.out.println("data inserted");
			
			Update u1=new Update();
			Update.updateEmployee(connect,u1.empid, u1.ename, u1.emailid);
 			 connect.close();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}

		