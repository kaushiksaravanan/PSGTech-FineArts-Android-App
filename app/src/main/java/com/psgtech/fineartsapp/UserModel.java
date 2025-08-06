package com.psgtech.fineartsapp;

public class UserModel {
    String name, roleNo, role, dept, phoneno, email, year;
    boolean priv;

    // todo need to add gender, achivements links

    public UserModel() {}

    public UserModel(String name, String roleNo, String role, String dept, String phoneno, String email, String year, boolean  priv) {
        this.name = name;
        this.roleNo = roleNo;
        this.role = role;
        this.dept = dept;
        this.phoneno = phoneno;
        this.email = email;
        this.year = year;
        this.priv = priv;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getYear() {
        return year;
    }

    public void setRoleNo(String roleNo) {
        this.roleNo = roleNo;
    }

    public String getRoleNo() {
        return roleNo;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPriv() {
        return priv;
    }

    public void setPriv(boolean priv) {
        this.priv = priv;
    }
}
