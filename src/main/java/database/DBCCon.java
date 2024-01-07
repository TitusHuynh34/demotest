package database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBCCon {
	private static Connection con = null;
	private static String url = "jdbc:sqlserver://";
	private static String serverName = "TITUS";
	private static String portNumber = "1433";
	private static String databaseName = "c2212lswing";
	private static String username = "sa";
	private static String password = "3498";

//phương thức trả về chuỗi kết nối
	public static String getUrl() {
	// driver 9.4.1 trở về trước
		return url + serverName 
				+ ":" + portNumber 
				+ "; databaseName=" + databaseName 
				+ "; user=" + username 
				+ "; password=" + password ;
//	//sau 9.4.1
//		return url + serverName 
//				+ ":" + portNumber 
//				+ "; databaseName=" + databaseName 
//				+ "; user=" + username 
//				+ "; password=" + password 
//				+ "; Encrypt=true; trustServerCertuficate=trưe";
	}
	
//phương thức trả về connection
		public static Connection getCon() {
			try {
				con = DriverManager.getConnection(getUrl());
			} catch (Exception e) {
				e.printStackTrace();
				con = null;
				}
			return con;
			}
		}

