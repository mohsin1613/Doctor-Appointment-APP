package com.project.meetingapp.models;

public class UserAllInfo {
    private String name,id,phone_num,date_of_birth,sex,email,pass,specialization,schedule,fee,typ;
    private UserAllInfo userAllInfo;
    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public UserAllInfo getUserAllInfo() {
        return userAllInfo;
    }

    public void setUserAllInfo(UserAllInfo userAllInfo) {
        this.userAllInfo = userAllInfo;
    }

    public UserAllInfo() {
    }

    public UserAllInfo(String name, String id, String phone_num, String date_of_birth,
                       String sex, String email, String pass, String specialization,
                       String schedule, String fee, String typ) {
        this.name = name;
        this.id = id;
        this.phone_num = phone_num;
        this.date_of_birth = date_of_birth;
        this.sex = sex;
        this.email = email;
        this.pass = pass;
        this.specialization = specialization;
        this.schedule = schedule;
        this.fee = fee;
        this.typ = typ;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }
}
