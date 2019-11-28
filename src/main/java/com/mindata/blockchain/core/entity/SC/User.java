package com.mindata.blockchain.core.entity.SC;

/**
 * Created by aiya on 2019/3/17 下午9:55
 */
public class User {
    //工号
    private String usrId;
    private String usrName;
    private String password;
    //员工所在部门
    private String segment;
    private int permission;

    public User(String usrId, String usrname, String password, String segment, int permission) {
        this.usrId = usrId;
        this.usrName = usrname;
        this.password = password;
        this.segment = segment;
        this.permission = permission;
    }

    public String getUsrId() {
        return usrId;
    }

    public void setUsrId(String usrId) {
        this.usrId = usrId;
    }

    public String getUsrname() {
        return usrName;
    }

    public void setUsrname(String usrname) {
        this.usrName = usrname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }

    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }
}
