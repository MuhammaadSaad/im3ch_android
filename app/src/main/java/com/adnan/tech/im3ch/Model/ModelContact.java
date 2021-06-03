package com.adnan.tech.im3ch.Model;

public class ModelContact {
    String choice, name1, password1, email1, phone1, gender1;

    public ModelContact(String choice, String name1, String password1, String email1, String phone1, String gender1) {
        this.choice = choice;
        this.name1 = name1;
        this.password1 = password1;
        this.email1 = email1;
        this.phone1 = phone1;
        this.gender1 = gender1;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getEmail1() {
        return email1;
    }

    public void setEmail1(String email1) {
        this.email1 = email1;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getGender1() {
        return gender1;
    }

    public void setGender1(String gender1) {
        this.gender1 = gender1;
    }
}
