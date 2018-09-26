package com.jiang.propcopy.utils;

import com.esotericsoftware.reflectasm.MethodAccess;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
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

    private static Map<String,List<String>> clzNameMethodMappingCache = new ConcurrentHashMap<>();


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

        List<String> sourceMethodNameList = clzNameMethodMappingCache.get(sourceClassName);

        if(CollectionUtils.isEmpty(sourceMethodNameList)) {
            sourceMethodNameList = cacheClzNameMethodMapping(sourceClassName, sourceMethodAccess);
        }

        Class<?> targetClass = target.getClass();
        String targetClassName = targetClass.getName();

        MethodAccess targetMethodAccess = getMethodAccess(targetClass);

        List<String> targetMethodNameList = clzNameMethodMappingCache.get(targetClassName);

        if(CollectionUtils.isEmpty(targetMethodNameList)) {
            targetMethodNameList = cacheClzNameMethodMapping(targetClassName, targetMethodAccess);
        }

        for(String targetMethodName : targetMethodNameList){
            if(SET_METHOD_PRIFIX.equals(targetMethodName.substring(0,3))
                    && sourceMethodNameList.contains(targetMethodName)){
                targetMethodAccess.invoke(target, targetMethodName,
                        sourceMethodAccess.invoke(source, GET_METHOD_PRIFIX + targetMethodName.substring(3, targetMethodName.length())));
            }
        }
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
        }
        return methodAccess;
    }


    private static List<String> cacheClzNameMethodMapping(String clzName, MethodAccess access){

        String[] methodNameArr = access.getMethodNames();
        List<String> methodNameList = new ArrayList<>(methodNameArr.length);
        for (String methodName : methodNameArr) {
            if (GET_METHOD_PRIFIX.equals(methodName.substring(0, 3))
                    || SET_METHOD_PRIFIX.equals(methodName.substring(0, 3))) {
                methodNameList.add(methodName);
            }
            clzNameMethodMappingCache.put(clzName, methodNameList);
        }
        return methodNameList;
    }
}
