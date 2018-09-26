package com.jiang.propcopy.entity;

/**
 * @ClassName Teacher
 * @Description
 * @Author jiangxy
 * @Date 2018\9\26 0026 19:46
 * @Version 1.0.0
 */
public class Teacher extends Person {

    private String classRoom;

    private Integer grade;

    private String duty;

    public Teacher() {
    }

    public String getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(String classRoom) {
        this.classRoom = classRoom;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    @Override
    public String toString() {
        return "Teacher{" + super.toString() +
                "classRoom='" + classRoom + '\'' +
                ", grade=" + grade +
                ", duty='" + duty + '\'' +
                '}';
    }
}
