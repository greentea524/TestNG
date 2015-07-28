package TestNG;

import java.sql.* ;

public class DBConnection {
	static final  String oracleDriver = "oracle.jdbc.driver.OracleDriver";
	static final String mySQLDriver = "com.mysql.jdbc.Driver";
	private String url;
	private String user;
	private String password;
	private String dbName;
	private int port;
	private boolean isOracle;
	
	private Connection conn;
	private Statement stmt;
	private ResultSet result;
	
	public DBConnection(String url, String user, String password, String dbName, int port, boolean isOracle)
	{
		this.url = url;
		this.user = user;
		this.password = password;
		this.dbName = dbName;
		this.port = port;
		this.isOracle = isOracle;
	}
	public String getURL()
	{
		return url;
	}
	public boolean getAccess()
	{
		try
		{	
			if (isOracle)
			{
				
				Class.forName(oracleDriver);
				try {
					conn = DriverManager.getConnection("jdbc:oracle:thin:@"+url+":"+port+":"+dbName,user,password);
					DatabaseMetaData meta = conn.getMetaData();
					System.out.println("JDBC driver version is " + meta.getDriverVersion());
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
			else // is mySQL
			{
				Class.forName(mySQLDriver);
				try {
					conn = DriverManager.getConnection("jdbc:mysql://"+url+":"+port+"/"+dbName,user,password);
					DatabaseMetaData meta = conn.getMetaData();
					System.out.println("JDBC driver version is " + meta.getDriverVersion());
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
				
			}	
/*		
			Statement stmt = conn.createStatement();	
			ResultSet rset3 = stmt.executeQuery("select owkey  FROM fx_fileserver");	
			while (rset3.next())
			{System.out.println(rset3.getString(1));}
			rset3.close();
			stmt.close();
*/			
		//	conn.close();	
		}catch(ClassNotFoundException ex) {
			   System.out.println("Error: unable to load driver class!");
		}
		return false;
	}
	public boolean closeConnection()
	{
		try{
			if(!conn.isClosed())
			{
				conn.close();
				stmt.close();
				result.close();
			}	
			System.out.println("DB connection close successful");
			return true;
		}catch(SQLException e){
			return false;
		}
	}
	public boolean closeResult()
	{
		try{
			if(!conn.isClosed())
			{
				stmt.close();
				result.close();
			}	
		//	System.out.println("DB connection close successful");
			return true;
		}catch(SQLException e){
			return false;
		}
	}
	public boolean isClose(){
		try {
			return conn.isClosed();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	public ResultSet executeQuery(String query) throws SQLException
	{
		stmt = conn.createStatement();	
		result = stmt.executeQuery(query);
		return result;
	}
}