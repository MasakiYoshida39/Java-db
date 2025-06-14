package dto;

import java.sql.Date;


public class User {
	/******** フィールド ******************************************/
	/**
	 * ユーザID
	 */
	private String userId;
	
	/**
	 * ユーザ名
	 */
	private String userName;
	
	/**
	 * メールアドレス
	 */
	private String email;
	
	/**
	 * 誕生日
	 */
	private Date birthDay;

	/******** コンストラクタ **************************************/
	/**
	 * 引数なしコンストラクタ
	 */
	public User() {
	}

	/**
	 * 引数ありコンストラクタ
	 * @param userId ... ユーザID初期値
	 * @param userName ... ユーザ名初期値
	 * @param email ... メールアドレス初期値
	 * @param birthDay ... 誕生日初期値
	 */
	public User(String userId, String userName, String email, Date birthDay) {
		this.userId = userId;
		this.userName = userName;
		this.email = email;
		this.birthDay = birthDay;
	}

	/******** メソッド ********************************************/
	/*--------------------getter/setter--------------------*/
	/**
	 * ユーザID取得
	 * @return ユーザID
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * ユーザID設定
	 * @param userId ... ユーザID
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * ユーザ名取得
	 * @return ユーザ名
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * ユーザ名設定
	 * @param userName ... ユーザ名
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * メールアドレス取得
	 * @return メールアドレス
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * メールアドレス設定
	 * @param email メールアドレス
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 誕生日取得
	 * @return 誕生日
	 */
	public Date getBirthDay() {
		return birthDay;
	}

	/**
	 * 誕生日設定
	 * @param birthDay ...誕生日
	 */
	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

}
