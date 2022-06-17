package ge.tsu.demoexamcalc.models;

import ge.tsu.demoexamcalc.annotation.ColumnName;
import ge.tsu.demoexamcalc.annotation.Identifier;

import java.util.ArrayList;
import java.util.List;

public class SavedResult {
    @Identifier
    @ColumnName("ID")
    protected Integer ID;
    @ColumnName("GEO_P")
    protected Double geoP;
    @ColumnName("FOREIGN_LANG_P")
    protected Double fLangP;
    @ColumnName("THIRD_SUBJECT")
    protected String thirdSubject;
    @ColumnName("THIRD_SUBJECT_P")
    protected Double thirdSubjectP;
    @ColumnName("CALCULATED")
    protected Double calculated;
    @ColumnName("GRANT")
    protected Integer grant;
    @ColumnName("USER_ID")
    protected Integer userID;


    @Override
    public String toString() {
        return "SavedResult: " +
                "ID=" + ID +
                ", geoP=" + geoP +
                ", fLangP=" + fLangP +
                ", thirdSubject='" + thirdSubject + '\'' +
                ", thirdSubjectP=" + thirdSubjectP +
                ", calculated=" + calculated +
                ", grant=" + grant;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Double getGeoP() {
        return geoP;
    }

    public void setGeoP(Double geoP) {
        this.geoP = geoP;
    }

    public Double getfLangP() {
        return fLangP;
    }

    public void setfLangP(Double fLangP) {
        this.fLangP = fLangP;
    }

    public String getThirdSubject() {
        return thirdSubject;
    }

    public void setThirdSubject(String thirdSubject) {
        this.thirdSubject = thirdSubject;
    }

    public Double getThirdSubjectP() {
        return thirdSubjectP;
    }

    public void setThirdSubjectP(Double thirdSubjectP) {
        this.thirdSubjectP = thirdSubjectP;
    }

    public Double getCalculated() {
        return calculated;
    }

    public void setCalculated(Double calculated) {
        this.calculated = calculated;
    }

    public Integer getGrant() {
        return grant;
    }

    public void setGrant(Integer grant) {
        this.grant = grant;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public List<Object> getAll(){
        List<Object> data = new ArrayList<>();
        data.add(0, geoP);
        data.add(1, fLangP);
        data.add(2, thirdSubject);
        data.add(3, thirdSubjectP);
        data.add(4, calculated);
        data.add(5, grant);
        return data;
    }
}
