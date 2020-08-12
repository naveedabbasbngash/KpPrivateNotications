package kp.schools.notications.Model;

public class LoginResponseModel {
    String user_id,role_id,userTitle,userName,userEmail,gender,contactNumber,district_id,createdDate;

    public LoginResponseModel(String user_id, String role_id, String userTitle, String userName,
                              String userEmail, String gender, String contactNumber, String district_id, String createdDate) {
        this.user_id = user_id;
        this.role_id = role_id;
        this.userTitle = userTitle;
        this.userName = userName;
        this.userEmail = userEmail;
        this.gender = gender;
        this.contactNumber = contactNumber;
        this.district_id = district_id;
        this.createdDate = createdDate;
    }

    public LoginResponseModel() {
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }

    public String getUserTitle() {
        return userTitle;
    }

    public void setUserTitle(String userTitle) {
        this.userTitle = userTitle;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(String district_id) {
        this.district_id = district_id;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
