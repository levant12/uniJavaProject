package ge.tsu.demoexamcalc.models;

import ge.tsu.demoexamcalc.annotation.ColumnName;
import ge.tsu.demoexamcalc.annotation.Identifier;

public class User {
    @Identifier
    @ColumnName("ID")
    protected Integer ID;
    @ColumnName("F_NAME")
    protected String fName;
    @ColumnName("L_NAME")
    protected String lName;
    @ColumnName("EMAIL")
    protected String email;
    @ColumnName("U_NAME")
    protected String uName;
    @ColumnName("PWD")
    protected String pwd;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public String toString() {
        return "User:" +
                "ID=" + ID +
                ", fName='" + fName + '\'' +
                ", lName='" + lName + '\'' +
                ", email='" + email + '\'' +
                ", uName='" + uName + '\'' +
                ", pwd='" + pwd + '\'';
    }

    public boolean checkPwd(String pwd) {
        return this.pwd.equals(pwd);
    }
}
