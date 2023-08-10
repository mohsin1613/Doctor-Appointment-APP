package com.project.meetingapp.models;

public class UserInfo {
    String nam,id,type;

    public UserInfo(String nam, String id, String type) {
        this.nam = nam;
        this.id = id;
        this.type = type;
    }

    public String getNam() {
        return nam;
    }

    public void setNam(String nam) {
        this.nam = nam;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
