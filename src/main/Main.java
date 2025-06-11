package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 実行用クラス
 * @author M.Takahashi
 */
public class Main {

	/**
	 * メインメソッド
	 * @param args ... 未使用
	 */
	public static void main(String[] args) {
		// データベースURL、ユーザID、パスワード 定義
		String url = "jdbc:mysql://localhost:3306/pc_shop_db";
		String user = "root";
		String password = "y19956030";
//		String password = "aaaa";

		// データベースへの接続
		try (Connection con = DriverManager.getConnection(url, user, password)) {
			System.out.println("\n接続しました。");
		} catch (SQLException e) {
			System.out.println("\nエラーが発生しました。");
		}

//		// try-with-resources を使わない場合
//		Connection con = null;
//		try {
//			con = DriverManager.getConnection(url, user, password);
//			System.out.println("\n接続しました。");
//		} catch (SQLException e) {
//			System.out.println("\nエラーが発生しました。");
//		} finally {
//			try {
//				if (con != null) {
//					con.close();
//				}
//			} catch (Exception e) {
//				System.out.println("\n終了処理エラー。");
//			}
//		}

	}

}
