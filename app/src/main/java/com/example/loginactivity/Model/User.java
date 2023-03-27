package com.example.loginactivity.Model;

public class User {
    private String DOB, Email, Gender, Name, Password, Qualification, State, Status, Id, profileimageurl, Username;

    public User() {

    }

    public User(String DOB, String email, String gender, String name, String password, String qualification, String state, String status, String id, String profileimageurl, String username) {
        this.DOB = DOB;
        this.Email = email;
        this.Gender = gender;
        this.Name = name;
        this.Password = password;
        this.Qualification = qualification;
        this.State = state;
        this.Status = status;
        this.Id = id;
        this.profileimageurl = profileimageurl;
        this.Username = username;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getQualification() {
        return Qualification;
    }

    public void setQualification(String qualification) {
        Qualification = qualification;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getProfileimageurl() {
        return profileimageurl;
    }

    public void setProfileimageurl(String profileimageurl) {
        this.profileimageurl = profileimageurl;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }
}