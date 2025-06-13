package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * ユーザ情報用のDAO
 * @author M.Takahashi
 */
public class UserDAO {

	/**
	 * 全件id検索
	 * @return 結果のidリスト
	 * @throws SQLException
	 */
	public List<String> selectAllId() throws SQLException {

		// 結果格納用
		List<String> idList = new ArrayList<String>();

		String sql = "SELECT user_id FROM m_user ";

		try (Connection con = MyConnection.getConnection();
				Statement stmt = con.createStatement();
				ResultSet res = stmt.executeQuery(sql)) {

			while (res.next()) {
				String userId = res.getString("user_id");
				
				// １件分を結果リストに追加
				idList.add(userId);
			}
		}

		return idList;
	}
	
	/**
	 * １件検索 (名前を取得、userId指定)
	 * @param userId ... 検索条件
	 * @return 検索結果 (該当なしの場合は null )
	 * @throws SQLException
	 */
	public String selectNameById(String userId) throws SQLException {

		String name = null;		// 戻り値の初期化
		
		String sql = "SELECT user_name FROM m_user WHERE user_id = ? ";
		
		try (Connection con = MyConnection.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			
			pstmt.setString(1, userId);		// プレースホルダに値を設定
			
			try (ResultSet res = pstmt.executeQuery()) {	// 検索の実行
				// 結果が存在する場合に値を取得
				if (res.next()) {
					name = res.getString("user_name");
				}
			}
			
		}
		
		return name;
	}
	
}
