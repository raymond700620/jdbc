package io.pivotal.pal.jdbc;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class CertExamRecord implements Serializable {
    private long ID;
    private String dataSource;
    private Date createDate;
    private Date updateDate;
    private String email;
    private String firstName;
    private String lastName;
    private String company;
    private String siteRegion;
    private String siteCountry;
    private String examCode;
    private String examTitle;
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy hh:mm a")
    private Date examDate;
    private int score;
    private String grade;

    //private String language;


    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CertExamRecord certExam = (CertExamRecord) o;
        return ID == certExam.ID || (
                Objects.equals(dataSource, certExam.dataSource) &&
                Objects.equals(email, certExam.email) &&
                Objects.equals(examCode, certExam.examCode) &&
                Objects.equals(examDate, certExam.examDate));
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, dataSource, email, examCode, examDate);
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Date getExamDate() {
        return examDate;
    }

    public void setExamDate(Date examDate) {
        this.examDate = examDate;
    }

    public String getExamTitle() {
        return examTitle;
    }

    public void setExamTitle(String examTitle) {
        this.examTitle = examTitle;
    }

    public String getExamCode() {
        return examCode;
    }

    public void setExamCode(String examCode) {
        this.examCode = examCode;
    }

    public String getSiteCountry() {
        return siteCountry;
    }

    public void setSiteCountry(String siteCountry) {
        this.siteCountry = siteCountry;
    }

    public String getSiteRegion() {
        return siteRegion;
    }

    public void setSiteRegion(String siteRegion) {
        this.siteRegion = siteRegion;
    }


    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }
}
