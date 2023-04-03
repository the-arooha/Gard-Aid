package com.example.loginactivity;

public class ReadWriteUserDetails {

    public String DOB, Email, Gender, Name, Password, Qualification, State, Status, profileimageurl, Username;

    public ReadWriteUserDetails(){};

    public ReadWriteUserDetails(String DOB, String email, String gender, String name, String password, String qualification, String state, String status, String profileimageurl, String username) {
        this.DOB = DOB;
        this.Email = email;
        this.Gender = gender;
        this.Name = name;
        this.Password = password;
        this.Qualification = qualification;
        this.State = state;
        this.Status = status;
        this.profileimageurl = profileimageurl;
        this.Username = username;
    }
}
