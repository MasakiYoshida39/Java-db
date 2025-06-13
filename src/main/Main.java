package main;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import dao.UserDAO;

public class Main {

	/**
	 * キーボード入力用のScannerオブジェクト（インスタンス）
	 */
	private static final Scanner scan = new Scanner(System.in);;

	/**
	 * ユーザ情報用のDAO（インスタンス）
	 */
	private static final UserDAO userDao = new UserDAO();
	
	/**
	 * メインメソッド
	 * @param args ... 未使用
	 */
	public static void main(String[] args) {
		String input;		// 文字列入力用
		
		System.out.println("\n****ユーザ情報を操作します****");

		// 「q」が入力されるまでループ
		do {
			displayGuide();			// ガイドメッセージ表示
			input = inputPrompt("\n上記の項目を選んでください > ");

			switch (input) {
			case "a":
				displayList();			// 一覧表示
				break;
			case "r":
				select();				// 検索
				break;
			case "q":
				break;
			default:
				System.out.println("\n入力内容をご確認ください。");
			}
		} while (!input.equals("q"));	// 「q」以外なら続行
		
		System.out.println("\n終了します。お疲れ様でした。");

	}

	/**
	 * 入力時のガイドメッセージの表示
	 */
	private static void displayGuide() {
		
		System.out.println("\n--------------------------------");
		System.out.println("選択項目＝a:一覧, r:検索, q:終了");
		System.out.println("--------------------------------");

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
	 * ユーザ一覧表示
	 */
	private static void displayList() {

		try {
			List<String> idList = userDao.selectAllId();	// ID一覧取得
			
			System.out.println("\n---全てのユーザ---\n");
			for (String id : idList) {

				System.out.println(id);

			}

		} catch (SQLException e) {
			System.out.println("\nエラーが発生しました。");
		}

	}

	/**
	 * ユーザIDの入力を受け付け、１件のユーザ名を検索表示
	 */
	private static void select() {

		System.out.println("\n---IDで検索します---\n");
		String selectId = inputPrompt("【Ｉ　Ｄ】 > ");
	
		try {

			String name = userDao.selectNameById(selectId);

			if (name != null) {
				
				// 検索結果が存在する場合に内容表示
				System.out.println("\n---検索結果---\n");
				System.out.println("【Ｉ　Ｄ】" + selectId);
				System.out.println("【名　前】" + name);
			} else {
				System.out.println("\nレコードが存在しません。");
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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Scanner;

 	/**
	 * キーボード入力用のScannerオブジェクト（インスタンス）

	private static final Scanner scan = new Scanner(System.in);;

	/**
	 * メインメソッド
	 * @param args ... 未使用
	public static void main(String[] args) {

		displayList();				// 一覧表示

		String userId = insert();	// 登録
		
		if (userId != null) {
			select(userId);			// 検索
		}
		select1();				// 検索
	
	}

	/**
	 * プロンプトを表示して入力を受け付ける
	 * @param prompt ... プロンプト文字列
	 * @return 入力結果文字列
	
	private static String inputPrompt(String prompt) {

		System.out.print(prompt);
		return scan.nextLine();
	}

	/**
	 * ユーザId一覧表示
	
	private static void displayList() {

		String url = "jdbc:mysql://localhost:3306/pc_shop_db";
		String user = "root";
		String password = "";

		String sql = "SELECT user_id FROM m_user ";

		System.out.println("\n---全てのユーザ---\n");
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
	 * ユーザデータの入力を受付けて登録する。
	 * @return 登録した ユーザID (null の場合 登録NG）
	private static String insert() {

		String url = "jdbc:mysql://localhost:3306/pc_shop_db";
		String user = "root";
		String password = "";

		String sql =  " INSERT INTO m_user "
			     	+ " (user_id, user_name, email, birth_day)"
			     	+ " VALUES (?, ?, ?, ?) ";

		System.out.println("\n---登録します---\n");

		// 登録データの入力を受け付ける
		String userId 			= inputPrompt("【Ｉ　Ｄ】 > ");
		String userName 		= inputPrompt("【名　前】 > ");
		String email 			= inputPrompt("【メール】 > ");
		String birthDayString 	= inputPrompt("【誕生日】 > ");
		// birthDay (java.sql.Date型)の設定
		// （誕生日入力が 空文字の場合 null、
		//    入力文字列がある場合 java.sql.Dateに変換して設定）
		Date birthDay = null;
		if (! birthDayString.equals("")) {
			try {
				birthDay = Date.valueOf(birthDayString);
			} catch (IllegalArgumentException e) {
				System.out.println("\n日付は yyyy-mm-dd で指定してください。");
				return null;
			}
		}

		// SQL の実行
		try (Connection con = DriverManager.getConnection(url, user, password);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			// プレースホルダに値を設定
			pstmt.setString(1, userId);
			pstmt.setString(2, userName);
			pstmt.setString(3, email);
			if (birthDay == null) {
				pstmt.setNull(4, Types.DATE);
			} else {
				pstmt.setDate(4, birthDay);
			}

			int cnt = pstmt.executeUpdate();	// 登録処理の実行
			
			System.out.println("\n"+ cnt + "件 登録しました。");

		} catch (SQLException e) {
			System.out.println("\nエラーが発生しました。");
			return null;
		}
	
		return userId;	// 登録した ユーザID を返す
	}

	/**
	 * 指定された １件のユーザデータの検索表示
	 * @param userId ユーザID

	private static void select(String userId) {

		String url = "jdbc:mysql://localhost:3306/pc_shop_db";
		String user = "root";
		String password = "";

		String sql = "SELECT * FROM m_user WHERE user_id = ? ";
		
		try (Connection db = DriverManager.getConnection(url, user, password);
				PreparedStatement pstmt = db.prepareStatement(sql)) {
			
			pstmt.setString(1, userId);		// プレースホルダに値を設定
			
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
	
	
	private static void select1() {

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