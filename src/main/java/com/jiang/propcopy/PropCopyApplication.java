package com.jiang.propcopy;

import com.jiang.propcopy.entity.Student;
import com.jiang.propcopy.entity.Teacher;
import com.jiang.propcopy.utils.BeanCopyUtil;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName PropCopyApplication
 * @Description
 * @Author jiangxy
 * @Date 2018\9\26 0026 19:52
 * @Version 1.0.0
 */
//@Configuration
public class PropCopyApplication {

    public static void main(String[] args) {

        Student s = new Student();
        s.setName("jiangBUG");
        s.setAge(25);
        s.setBirth("1993-02-25 00:00:00");
        s.setGrade(5);
        s.setClassRoom("3");
        s.setHobby("fixgear");
        s.setGender((byte)1);
        s.setHeight(183);
        s.setWeight(65);

        Teacher t = new Teacher();


        BeanCopyUtil.copy(t, s);

        System.out.println(t);
    }

}
