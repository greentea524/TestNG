package TestNG;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DBValidation {
	DBConnection db;
	ResultSet rs;
	
//	public void dbtest() throws SQLException {
//		//public DBConnection(String url, String user, String password, String dbName, int port, boolean isOracle)
//		db = new DBConnection("192.168.54.43", "root","dorado", "owbusdb", 3306, false);
//		db.getAccess();
//		String query = "SELECT name FROM `owbusdb`.`nfv_vnfdescriptor`";
//		rs = db.executeQuery(query);
//		while (rs.next())
//		{
//			//System.out.println(rs.getString(1));
//			if(rs.getString(1).equalsIgnoreCase("test-vm1"))System.out.println("test-vm1");
//		}
//		db.closeConnection();
//	}
	DBValidation(){
		
	}
	public void dbtest(String url, String user, String password, String dbName, int port, boolean isOracle){
		db = new DBConnection(url, user, password, dbName, port, isOracle);
		db.getAccess();
	}
	public boolean checkValueInTable(String name, String table) throws SQLException{
		String query = "SELECT name FROM `owbusdb`."+table+"`";
		rs = db.executeQuery(query);
		while (rs.next())
		{
			//System.out.println(rs.getString(1));
			if(rs.getString(1).equalsIgnoreCase(name)) return true;
		}
		return false;
	}
	public void close(){
		db.closeConnection();
	}

}
