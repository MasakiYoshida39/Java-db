package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


public class Main {

	/**
	 * キーボード入力用のScannerオブジェクト（インスタンス）
	 */
	private static final Scanner scan = new Scanner(System.in);

	/**
	 * メインメソッド
	 * @param args ... 未使用
	 */
	public static void main(String[] args) {

		displayList();			// 一覧表示

		select();				// 検索
	}

	/**
	 * プロンプトを表示して入力を受け付ける
	 * @param prompt ... プロンプト文字列
	 * @return 入力結果文字列
	 */
	private static String inputPrompt(String prompt) {

		System.out.print(prompt);
		return scan.nextLine();
	}

	/**
	 * ユーザId一覧表示
	 */
	private static void displayList() {

		String url = "jdbc:mysql://localhost:3306/pc_shop_db";
		String user = "root";
		String password = "";

		String sql = "SELECT user_id FROM m_user ";

		System.out.println("\n---全てのユーザID---\n");
		try (Connection con = DriverManager.getConnection(url, user, password);
				Statement stmt = con.createStatement();
				ResultSet res = stmt.executeQuery(sql)) {

			while (res.next()) {
				System.out.println(res.getString("user_id"));
			}
		} catch (SQLException e) {
			System.out.println("エラーが発生しました。");
		}
	}
	
	/**
	 * ユーザIDの入力を受け付け、１件のユーザデータの検索表示
	 */
	private static void select() {

		String url = "jdbc:mysql://localhost:3306/pc_shop_db";
		String user = "root";
		String password = "";

		String sql = "SELECT * FROM m_user WHERE user_id = ? ";
		
		System.out.println("\n---IDで検索します---\n");
		String selectId = inputPrompt("【Ｉ　Ｄ】 > ");

		try (Connection con = DriverManager.getConnection(url, user, password);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			
			pstmt.setString(1, selectId);		// プレースホルダに値を設定
			
			try (ResultSet res = pstmt.executeQuery()) {	// 検索の実行
				if (res.next()) {
					// 検索結果が存在する場合に内容表示
					System.out.println("\n---検索結果---\n");
					System.out.println("【Ｉ　Ｄ】" + res.getString("user_id"));
					System.out.println("【名　前】" + res.getString("user_name"));
					System.out.println("【メール】" + res.getString("email"));
					if (res.getDate("birth_day") == null) {
						System.out.println("【誕生日】（未登録）");
					} else {
						System.out.println("【誕生日】" + res.getDate("birth_day"));
					}
				} else {
					System.out.println("\nレコードが存在しません。");
				}
			}
		} catch (SQLException e) {
			System.out.println("\nエラーが発生しました。");
		}
	}

}



















/*
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/* 実行用クラス

public class Main {

	/**
	 * メインメソッド
	 * @param args ... 未使用
	
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
*/