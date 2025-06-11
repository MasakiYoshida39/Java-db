package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 実行用クラス
 *
 */
public class Main {

	/**
	 * メインメソッド
	 * @param args ... 未使用
	 */
	public static void main(String[] args) {
		// データベースURL、ユーザID、パスワード 定義
		String url = "jdbc:mysql://localhost:3306/pc_shop_db";
		String user = "shop_user";
		String password = "pass";

		// SQL定義(ユーザマスタに１レコード追加)
		String sql =  " INSERT INTO m_user                                      "
				 	+ "   (user_id, user_name, email, birth_day)                "
				 	+ " VALUES                                                  "
				 	+ "   ('ZU01', 'ZU01名前', 'zu01@sample.com', '2002-03-04') ";

		// 接続・Statement作成
		try (Connection con = DriverManager.getConnection(url, user, password);
				Statement stmt = con.createStatement()) {

			System.out.println("\n---登録します---\n");

			int cnt = stmt.executeUpdate(sql); // レコード追加処理の実行
			
			System.out.println("\n"+ cnt + "件 登録しました。");

		} catch (SQLException e) {
			System.out.println("\nエラーが発生しました。");
		}

	}

}