package xyz.dreature.cms.common.entity;

public class User {
    private Integer userId;
    private String userName;
    private String userPassword;
    private String userNickname;
    private Integer userSex;
    private String userBirthday;
    private String userTelephone;
    private String userEmail;
    private String userExtra;
    private String userProfilePictureUrl;
    private Integer userType = 0;// 默认都是0
    private Integer userStatus = 1;// 默认都是1 0:被封禁 1：正常

    public User() {

    }

//	public User(OutUser user) {
//		this.userId=user.getUserId();
//		this.userName=user.getUserName();
//		this.userPassword="";
//		this.userNickname=user.getUserNickname();
//		this.userEmail=user.getUserEmail();
//		this.userType=IndexStringMapper.getInstance().UserString2Type(user.getUserType());
//		this.userState=IndexStringMapper.getInstance().UserString2State(user.getUserState());
//	}

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public Integer getUserSex() {
        return userSex;
    }

    public void setUserSex(Integer userSex) {
        this.userSex = userSex;
    }

    public String getUserBirthday() {
        return userBirthday;
    }

    public void setUserBirthday(String userBirthday) {
        this.userBirthday = userBirthday;
    }

    public String getUserTelephone() {
        return userTelephone;
    }

    public void setUserTelephone(String userTelephone) {
        this.userTelephone = userTelephone;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserExtra() {
        return userExtra;
    }

    public void setUserExtra(String userExtra) {
        this.userExtra = userExtra;
    }

    public String getUserProfilePictureUrl() {
        return userProfilePictureUrl;
    }

    public void setUserProfilePictureUrl(String userProfilePictureUrl) {
        this.userProfilePictureUrl = userProfilePictureUrl;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", userName=" + userName
                + ", userPassword=" + userPassword + ", userNickname=" + userNickname
                + ", userSex=" + userSex + ", userBirthday=" + userBirthday
                + ", userTelephone=" + userTelephone + ", userEmail=" + userEmail
                + ", userExtra=" + userExtra + ", userProfilePictureUrl=" + userProfilePictureUrl
                + ", userType=" + userType + ", userStatus=" + userStatus + "]";
    }
}
