package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import dto.User;


public class UserDAO {

	/**
	 * 全件検索
	 * @return 結果のユーザ情報リスト
	 * @throws SQLException
	 */
	public List<User> selectAll() throws SQLException {

		// 結果格納用
		List<User> userList = new ArrayList<User>();

		String sql = "SELECT * FROM m_user ";

		try (Connection con = MyConnection.getConnection();
				Statement stmt = con.createStatement();
				ResultSet res = stmt.executeQuery(sql)) {

			while (res.next()) {
				String userId 	= res.getString("user_id");
				String userName = res.getString("user_name");
				String email 	= res.getString("email");
				Date birthDay 	= res.getDate("birth_day");
				
				// １件分のオブジェクトを生成してリストに追加
				User user = new User(userId, userName, email, birthDay);
				userList.add(user);
			}
		}

		return userList;
	}
	
	/**
	 * １件検索 (userId指定)
	 * @param userId ... 検索条件
	 * @return 検索結果 (該当なしの場合は null )
	 * @throws SQLException
	 */
	public User selectById(String userId) throws SQLException {

		User user = null;		// 戻り値の初期化
		
		String sql = "SELECT * FROM m_user WHERE user_id = ? ";
		
		try (Connection db = MyConnection.getConnection();
				PreparedStatement pstmt = db.prepareStatement(sql)) {
			
			pstmt.setString(1, userId);		// プレースホルダに値を設定
			
			try (ResultSet res = pstmt.executeQuery()) {	// 検索の実行
				// 結果が存在する場合に結果のオブジェクトを生成
				if (res.next()) {
					String userName = res.getString("user_name");
					String email 	= res.getString("email");
					Date birthDay 	= res.getDate("birth_day");
					
					user = new User(userId, userName, email, birthDay);
				}
			}
			
		}
		
		return user;
	}
	
	/**
	 * レコード追加
	 * @param user ... 登録する Userオブジェクト
	 * @return 処理件数
	 * @throws SQLException
	 */
	//PreparedStatement と　PreparedStatement てなに
	public int insert(User user) throws SQLException {
		int cnt = 0;
		
		String sql =   " INSERT INTO m_user "
				     + " (user_id, user_name, email, birth_day) "
				     + " VALUES (?, ?, ?, ?) ";
		
		try (Connection db = MyConnection.getConnection();
				PreparedStatement pstmt = db.prepareStatement(sql)) {

			// プレースホルダに値を設定
			pstmt.setString(1, user.getUserId());
			pstmt.setString(2, user.getUserName());
			pstmt.setString(3, user.getEmail());
			Date birthDay = user.getBirthDay();
			if (birthDay == null) {
				pstmt.setNull(4, Types.DATE);
			} else {
				pstmt.setDate(4, birthDay);
			}

			cnt = pstmt.executeUpdate();	// レコード追加処理の実行
		}
		
		return cnt;
	}
	
	/**
	 * レコード更新
	 * @param user ... 更新する Userオブジェクト（userId がキー）
	 * @return 処理件数
	 * @throws SQLException
	 */
	public int update(User user) throws SQLException {
		int cnt = 0;
		
		String sql =   " UPDATE m_user "
		             + " SET    user_name = ?, email = ?, birth_day = ? "
                     + " WHERE  user_id = ? ";

		try (Connection db = MyConnection.getConnection();
				PreparedStatement pstmt = db.prepareStatement(sql)) {

			// プレースホルダの値を設定
			pstmt.setString(4, user.getUserId());
			pstmt.setString(1, user.getUserName());
			pstmt.setString(2, user.getEmail());
			Date birthDay = user.getBirthDay();
			if (birthDay == null) {
				pstmt.setNull(3, Types.DATE);
			} else {
				pstmt.setDate(3, user.getBirthDay());
			}

			cnt = pstmt.executeUpdate();	// レコード更新処理の実行
		}
		
		return cnt;
	}

	/**
	 * １件削除 (userId指定)
	 * @param userId ... 削除対象の user_id
	 * @return 処理件数
	 * @throws SQLException
	 */
	public int delete(String userId) throws SQLException {
		int cnt = 0;
		
		String sql = "DELETE FROM m_user WHERE user_id = ? ";
		
		try (Connection db = MyConnection.getConnection();
				PreparedStatement pstmt = db.prepareStatement(sql)) {
			
			pstmt.setString(1, userId);		// プレースホルダの値の設定
			
			cnt = pstmt.executeUpdate();	// 削除処理の実行
			
		}
		
		return cnt;
	}

}
