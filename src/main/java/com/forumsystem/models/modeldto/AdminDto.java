package com.forumsystem.models.modeldto;

public class AdminDto {
    private String phoneNumber;

    public AdminDto() {
    }

    public AdminDto(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
