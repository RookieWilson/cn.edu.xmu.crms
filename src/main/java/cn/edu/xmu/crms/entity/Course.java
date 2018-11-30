package cn.edu.xmu.crms.entity;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author 75
 * @date 2018/11/29
 */
public class Course {
    private BigInteger id;
    private String courseName;
    private String courseRequire;
    private BigDecimal presentationWeight;
    private BigDecimal questionWeight;
    private BigDecimal reportWeight;
    private Integer teamMaxLimit;
    private Integer teamMinLimit;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseRequire() {
        return courseRequire;
    }

    public void setCourseRequire(String courseRequire) {
        this.courseRequire = courseRequire;
    }

    public BigDecimal getPresentationWeight() {
        return presentationWeight;
    }

    public void setPresentationWeight(BigDecimal presentationWeight) {
        this.presentationWeight = presentationWeight;
    }

    public BigDecimal getQuestionWeight() {
        return questionWeight;
    }

    public void setQuestionWeight(BigDecimal questionWeight) {
        this.questionWeight = questionWeight;
    }

    public BigDecimal getReportWeight() {
        return reportWeight;
    }

    public void setReportWeight(BigDecimal reportWeight) {
        this.reportWeight = reportWeight;
    }

    public Integer getTeamMaxLimit() {
        return teamMaxLimit;
    }

    public void setTeamMaxLimit(Integer teamMaxLimit) {
        this.teamMaxLimit = teamMaxLimit;
    }

    public Integer getTeamMinLimit() {
        return teamMinLimit;
    }

    public void setTeamMinLimit(Integer teamMinLimit) {
        this.teamMinLimit = teamMinLimit;
    }
}