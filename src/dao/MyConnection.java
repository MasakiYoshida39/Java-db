package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//毎回接続処理だけするもの
public class MyConnection {
	/**
	 * データベースURL
	 */
	private final static String URL = "jdbc:mysql://localhost:3306/pc_shop_db";
	
	/**
	 * 接続ユーザ
	 */
	private final static String USER = "root";
	
	/**
	 * パスワード
	 */
	private final static String PASSWORD = "";

	/**
	 * データベース接続を行い 接続情報を返す
	 * @return 接続情報 (Connection)
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {

		return DriverManager.getConnection(URL, USER, PASSWORD);

	}

}
