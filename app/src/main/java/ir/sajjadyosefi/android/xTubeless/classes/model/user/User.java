package ir.sajjadyosefi.android.xTubeless.classes.model.user;

import android.content.Context;
import ir.sajjadyosefi.android.xTubeless.classes.StaticValue;

public class User {

	private Context context;
	private long userId;
	private String userName;
	private String email;
	private String mobileNumber;
	private String userImage;
	private String profileImage;
	public long balance;

	private String Password;
	private String loginPhone = null;
	private String loginPassword = null;
	private boolean isAdmin = false;

	public int UserCode;

	public int getUserCode() {
		return UserCode;
	}

	public String getUserCodeAsString() {
		return UserCode + "";
	}

	public void setUserCode(int userCode) {
		UserCode = userCode;
	}

//_________________________________

//	@Column(ignore = true)
//	private String city_location = null;
//
//	@Column(ignore = true)
//	private List<Post> PostItems = null ;
//
//	@Column(ignore = true)
//	private List<Car> carList = null ;
//
//	@Column(ignore = true)
//	private String pushToken ;
//
//	@Column(ignore = true)
//	private String _cookie = "";
//	private Device device = null;
//	private int	ApplicationID;
//	private Boolean canSendPicture;

	public User(Context context) {
		this.context = context;
	}

	public User(User source) {
		setUserId(source.getUserId());
		setUserName(source.getUserName());
		setEmail(source.getEmail());
		setMobileNumber(source.getMobileNumber());
		setUserImage(source.getUserImage());
		setProfileImage(source.getProfileImage());

		setBalance(source.getBalance());
		setPassword(source.getPassword());
		setLoginPassword(getLoginPassword());
		setLoginPhone(source.getLoginPhone());
		setAdmin(source.isAdmin());
	}

	public User() {

	}


	public long getUserId() {
		return userId;
	}
	public int getUserIdAsInt() {
		return (int)userId;
	}
	public String getUserIdAsString() {
		return "" + userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getUserImage() {
		return userImage;
	}

	public void setUserImage(String userImage) {
		this.userImage = userImage;
	}

	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	public long getBalance() {
		return balance;
	}

	public void setBalance(long balance) {
		this.balance = balance;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public String getLoginPhone() {
		return loginPhone;
	}

	public void setLoginPhone(String loginPhone) {
		this.loginPhone = loginPhone;
	}

	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	public void setAdmin(boolean admin) {
		isAdmin = admin;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public boolean CheckUserIsAdmin(User user) {
		try {
			if (user.getUserId() == StaticValue.AdminUserID1 ||
					user.getUserId() == StaticValue.AdminUserID2 ||
					user.getUserId() == StaticValue.AdminUserID3 ||

					user.getUserName().equals(StaticValue.AdminMail1) ||
					user.getUserName().equals(StaticValue.AdminMail2) ||
					user.getUserName().equals(StaticValue.AdminMail3) ||

					user.getUserName().equals(StaticValue.AdminMobile1) ||
					user.getUserName().equals(StaticValue.AdminMobile2) ||
					user.getUserName().equals(StaticValue.AdminMobile3))
				return true;
			else
				return false;
		}catch (Exception ex){
			return false;
		}
	}
}
