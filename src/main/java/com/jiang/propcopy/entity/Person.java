package com.jiang.propcopy.entity;

import java.util.Date;

/**
 * @ClassName Person
 * @Description
 * @Author jiangxy
 * @Date 2018\9\26 0026 19:45
 * @Version 1.0.0
 */
public class Person {

    private String name;

    private Integer age;

    private String birth;

    private Byte gender;

    private Integer height;

    private Integer weight;

    public Person() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return  "BaseInfo : " +
                "{name='" + name + '\'' +
                ", age=" + age +
                ", birth='" + birth + '\'' +
                ", gender=" + gender +
                ", height=" + height +
                ", weight=" + weight +
                '}';
    }
}
