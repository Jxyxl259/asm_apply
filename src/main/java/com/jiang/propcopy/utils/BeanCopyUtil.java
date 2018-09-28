package com.jiang.propcopy.utils;

import com.esotericsoftware.reflectasm.MethodAccess;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName BeanCopyUtil
 * @Description
 * @Author jiangxy
 * @Date 2018\9\26 0026 20:01
 * @Version 1.0.0
 */
public class BeanCopyUtil {

    private static String GET_METHOD_PRIFIX = "get";
    private static String SET_METHOD_PRIFIX = "set";

    private static Map<String,MethodAccess> clzNameMethodAccessMappingCache = new ConcurrentHashMap<>();

    private static Map<String,Integer> methodNameCache = new ConcurrentHashMap<>();

    private static Map<String,List<String>> clzNameFeildMappingCache = new ConcurrentHashMap<>();

    /**
     * 使用高性能反射 ReflectASM
     * 实现不同对象中 同名同数据类型的属性的拷贝
     * @param target 空对象
     * @param source 源对象
     */
    public static void copy(Object target, Object source){

        if(source == null)return;

        Class<?> sourceClass = source.getClass();
        String sourceClassName = sourceClass.getName();

        MethodAccess sourceMethodAccess = getMethodAccess(sourceClass);

        Class<?> targetClass = target.getClass();
        String targetClassName = targetClass.getName();

        MethodAccess targetMethodAccess = getMethodAccess(targetClass);

        List<String> targetFeildList = clzNameFeildMappingCache.get(targetClassName);

        if(CollectionUtils.isEmpty(targetFeildList)){
            targetFeildList = new ArrayList<>();
            getAllField(targetClass, targetFeildList);
            clzNameFeildMappingCache.put(targetClassName, targetFeildList);
        }



        /** 将 源对象中有的属性进行copy */
        for(String targetFeildName : targetFeildList){
            // 目标对象的属性
            String getMethod = sourceClassName + "." + GET_METHOD_PRIFIX + StringUtils.capitalize(targetFeildName);
            Integer getMethodIndex = methodNameCache.get(getMethod);
            if(getMethodIndex != null) {
                String setMethod = targetClassName + "." + SET_METHOD_PRIFIX + StringUtils.capitalize(targetFeildName);
                Integer setMethodIndex = methodNameCache.get(setMethod);
                targetMethodAccess.invoke(target, setMethodIndex, sourceMethodAccess.invoke(source, getMethodIndex));
            }
        }
    }

    /**
     * 递归获得目标对象的所有（包括父类中的）私有非静态属性
     * @param clz
     * @param Field
     * @return
     */
    private static List<String> getAllField(Class clz, List<String> Field){

        Field[] fields = clz.getDeclaredFields();
        for(Field f : fields){
            if(Modifier.isPrivate(f.getModifiers())
                    && !Modifier.isStatic(f.getModifiers())) {
                String fName = f.getName();
                Field.add(fName);
            }
        }

        Class superclass = clz.getSuperclass();

        if(superclass != null){
            getAllField(superclass,Field);
        }
        return Field;
    }

    /**
     * 先从缓存中取 MethodAccess对象，取不到再通过 Clazz 对象获取，获取之后放进缓存
     * @param clz
     * @return
     */
    private static MethodAccess getMethodAccess(Class clz) {
        MethodAccess methodAccess = clzNameMethodAccessMappingCache.get(clz.getName());
        if(methodAccess == null) {
            methodAccess = MethodAccess.get(clz);
            clzNameMethodAccessMappingCache.put(clz.getName(), methodAccess);

            String clzName = clz.getName();
            String[] methodNames = methodAccess.getMethodNames();
            for(String methodSimpleName : methodNames){
                String methodFullName = clzName + "." + methodSimpleName;
                methodNameCache.put(methodFullName, methodAccess.getIndex(methodSimpleName));
            }
        }
        return methodAccess;
    }


}
