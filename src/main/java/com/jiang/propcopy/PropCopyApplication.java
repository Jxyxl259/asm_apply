package com.jiang.propcopy;

import com.jiang.propcopy.entity.Student;
import com.jiang.propcopy.entity.Teacher;
import com.jiang.propcopy.utils.BeanCopyUtil;
import com.jiang.propcopy.utils.CopyProp;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

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

        Map<Teacher,Student> test = new HashMap<>();

        for(int i = 0 ; i < 1000000 ; i++){
            Student s = new Student();
            s.setName("jiangBUG");
            s.setAge(25);
            s.setBirth("1993-02-25 00:00:00");
            s.setGrade(i);
            s.setClassRoom("3");
            s.setHobby("fixgear");
            s.setGender((byte)1);
            s.setHeight(183);
            s.setWeight(65);
            test.put(new Teacher(),s);
        }


        Set<Map.Entry<Teacher, Student>> entries = test.entrySet();
        Iterator<Map.Entry<Teacher, Student>> iterator = entries.iterator();

        long start = System.currentTimeMillis();

        while(iterator.hasNext()){
            Map.Entry<Teacher, Student> next = iterator.next();
//            BeanCopyUtil.copy(next.getKey(), next.getValue()); // 可以实现父类中的属性拷贝，效率一般
            BeanUtils.copyProperties(next.getValue(),next.getKey()); // 可以实现父类中的属性拷贝，效率很高
//            CopyProp.copyProperties(next.getKey(), next.getValue()); // 只能实现本类中的属性拷贝，父类中的属性无法拷贝
        }

        long end = System.currentTimeMillis();
        System.out.println("耗时" + (end - start)/1000D);

    }


//    public static void main(String[] args) {
//
//        Student s = new Student();
//        s.setName("jiangBUG");
//        s.setAge(25);
//        s.setBirth("1993-02-25 00:00:00");
//        s.setGrade(5);
//        s.setClassRoom("3");
//        s.setHobby("fixgear");
//        s.setGender((byte)1);
//        s.setHeight(183);
//        s.setWeight(65);
//
//        Teacher t = new Teacher();
//
//       // BeanCopyUtil.copy(t, s);
//        //CopyProp.copyProperties(t, s);
//        BeanUtils.copyProperties(s, t);
//
//
//        System.out.println(t);
//
//    }

}
