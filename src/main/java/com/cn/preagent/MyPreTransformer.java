package com.cn.preagent;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

public class MyPreTransformer implements ClassFileTransformer {


    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer){
        //操作自己定义的类
        if ("com/cn/TestTransformClass".equals(className)) {
            try {
                System.out.println("我要把原TestTransformClass的方法体改了");
                // 从ClassPool获得CtClass对象
                final ClassPool classPool = ClassPool.getDefault();
                final CtClass clazz = classPool.get("com.cn.TestTransformClass");
                CtMethod convertToAbbr = clazz.getDeclaredMethod("testMethod");
                //这里对 java.util.Date.convertToAbbr() 方法进行了改写，在 return之前增加了一个 打印操作
                String methodBody = "{return 2;}";
                convertToAbbr.setBody(methodBody);

                // 返回字节码，并且detachCtClass对象
                byte[] byteCode = clazz.toBytecode();
                //detach的意思是将内存中曾经被javassist加载过的Date对象移除，如果下次有需要在内存中找不到会重新走javassist加载
                clazz.detach();
                System.out.println("TestTransformClass的方法体改了,结束");
                return byteCode;
            } catch (Exception ex) {
                System.out.println("exception__"+ex);
                ex.printStackTrace();
            }
        }
        // 如果返回null则字节码不会被修改
        return null;
    }
}
