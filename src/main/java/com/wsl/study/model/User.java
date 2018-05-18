package com.wsl.study.model;

public class User {
    private String userID;

    private String userName;

    private Integer platEntID;

    private String loginAccount;

    private String loginPwd;

    private String agentPersonID;

    private String adminFlag;

    private String stopFlag;

    private Integer displaySeq;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID == null ? null : userID.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public Integer getPlatEntID() {
        return platEntID;
    }

    public void setPlatEntID(Integer platEntID) {
        this.platEntID = platEntID;
    }

    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount == null ? null : loginAccount.trim();
    }

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd == null ? null : loginPwd.trim();
    }

    public String getAgentPersonID() {
        return agentPersonID;
    }

    public void setAgentPersonID(String agentPersonID) {
        this.agentPersonID = agentPersonID == null ? null : agentPersonID.trim();
    }

    public String getAdminFlag() {
        return adminFlag;
    }

    public void setAdminFlag(String adminFlag) {
        this.adminFlag = adminFlag == null ? null : adminFlag.trim();
    }

    public String getStopFlag() {
        return stopFlag;
    }

    public void setStopFlag(String stopFlag) {
        this.stopFlag = stopFlag == null ? null : stopFlag.trim();
    }

    public Integer getDisplaySeq() {
        return displaySeq;
    }

    public void setDisplaySeq(Integer displaySeq) {
        this.displaySeq = displaySeq;
    }
}