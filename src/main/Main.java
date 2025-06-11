package main;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/* 実行用クラス

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
		String password = "";

		// SQL定義(ユーザマスタより 3項目 取得)SQLのコードそのまま
		String sql = "SELECT user_id, user_name, birth_day FROM m_user ";

		// 接続・Statement作成・実行
		try (Connection con = DriverManager.getConnection(url, user, password);
				Statement stmt = con.createStatement();
				ResultSet res = stmt.executeQuery(sql)) {

			System.out.println("\n---全件表示します---");
			//nextで次々（データベース）と見ていく
			while (res.next()) {
				// 3項目取得（データベースから）
				String uId = res.getString("user_id");
				String name = res.getString("user_name");
				Date bDay = res.getDate("birth_day");
				
				// １レコード分の表示
				System.out.println("【Ｉ　Ｄ】" + uId);
				System.out.println("【名　前】" + name);
				if (bDay == null) {
					System.out.println("【誕生日】(未登録)");
				} else {
					System.out.println("【誕生日】" + bDay);
				}
				System.out.println("----------------");
			}
		} catch (SQLException e) {
			System.out.println("\nエラーが発生しました。");
		}
		
		
		
		
		
		
		// SQL定義(ユーザマスタに１レコード追加)
				String sql1 =  " INSERT INTO m_user                                      "
						 	+ "   (user_id, user_name, email, birth_day)                "
						 	+ " VALUES                                                  "
						 	+ "   ('ZU01', 'ZU01名前', 'zu01@sample.com', '2002-03-04') ";

				// 接続・Statement作成
				try (Connection con = DriverManager.getConnection(url, user, password);
						Statement stmt = con.createStatement()) {

					System.out.println("\n---登録します---\n");
					//executeUpdateは更新の時使う 61行目の文
					int cnt = stmt.executeUpdate(sql1); // レコード追加処理の実行
					
					System.out.println("\n"+ cnt + "件 登録しました。");

				} catch (SQLException e) {
					System.out.println("\nエラーが発生しました。");
				}
				
				
				
				
				
				
				
				
				
				
				
				String sql2 = "SELECT user_id, user_name, birth_day FROM m_user ";

				// 接続・Statement作成・実行
				try (Connection con = DriverManager.getConnection(url, user, password);
						Statement stmt = con.createStatement();
						ResultSet res = stmt.executeQuery(sql2)) {

					System.out.println("\n---全件表示します---");
					//nextで次々（データベース）と見ていく
					while (res.next()) {
						// 3項目取得（データベースから）
						String uId = res.getString("user_id");
						String name = res.getString("user_name");
						Date bDay = res.getDate("birth_day");
						
						// １レコード分の表示
						System.out.println("【Ｉ　Ｄ】" + uId);
						System.out.println("【名　前】" + name);
						if (bDay == null) {
							System.out.println("【誕生日】(未登録)");
						} else {
							System.out.println("【誕生日】" + bDay);
						}
						System.out.println("----------------");
					}
				} catch (SQLException e) {
					System.out.println("\nエラーが発生しました。");
				}
				

		
	}

}
