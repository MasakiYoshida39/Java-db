package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dto.User;

/**
 * ユーザ情報用のDAO
 * @author M.Takahashi
 */
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
	
}
