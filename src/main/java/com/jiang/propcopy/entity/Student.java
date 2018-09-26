package com.jiang.propcopy.entity;

/**
 * @ClassName Student
 * @Description
 * @Author jiangxy
 * @Date 2018\9\26 0026 19:46
 * @Version 1.0.0
 */
public class Student extends Person{

    private String classRoom;

    private Integer grade;

    private String hobby;


    public Student() {
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

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    @Override
    public String toString() {
        return "Student{" + super.toString() +
                "classRoom='" + classRoom + '\'' +
                ", grade=" + grade +
                ", hobby='" + hobby + '\'' +
                '}';
    }
}
