package cn.edu.xmu.crms.entity;

import java.math.BigInteger;

/**
 * @ClassName Klass
 * @Author Hongqiwu
 * @Description 班级
 **/
public class Klass {
    private BigInteger id;
    private BigInteger courseID;
    private Integer grade;
    private Integer klassSerial;
    private String klassTime;
    private String klassLocation;

    public BigInteger getID() {
        return id;
    }

    public void setID(BigInteger id) {
        this.id = id;
    }

    public BigInteger getCourseID() {
        return courseID;
    }

    public void setCourseID(BigInteger courseID) {
        this.courseID = courseID;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Integer getKlassSerial() {
        return klassSerial;
    }

    public void setKlassSerial(Integer klassSerial) {
        this.klassSerial = klassSerial;
    }

    public String getKlassTime() {
        return klassTime;
    }

    public void setKlassTime(String klassTime) {
        this.klassTime = klassTime;
    }

    public String getKlassLocation() {
        return klassLocation;
    }

    public void setKlassLocation(String klassLocation) {
        this.klassLocation = klassLocation;
    }
}
