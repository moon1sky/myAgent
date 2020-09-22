package com.cn.preagent;


import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

/**
 * JDK 1.5中提供的，只能在被代理程序main加载之前，执行premain
 * 存在的问题是：如果代理程序有问题，会影响被代理程序的启动或执行
 * 所以，在Java SE 6 的Instrumentation当中，提供了一个新的代理操作方法：agentmain，可以在 main 函数开始运行之后再运行
 * 可见 com.cn.agent.AfterMainTraceAgent
 */
public class PreMainTraceAgent {


    public static void premain(String args, Instrumentation ins) {
        System.out.println("PreMainTraceAgent premain start....");
//        if(true)throw new RuntimeException("测试该异常会不会影响被代理程序的启动加载");//结果是:会
        Class[] allLoadedClasses = ins.getAllLoadedClasses();
        for (Class clazz : allLoadedClasses) {
            String name = clazz.getName();
            System.out.println("allLoadedClasses= " + name);
        }
        System.out.println("PreMainTraceAgent premain end....");

        ins.addTransformer(new PreDefineTransformer());
        //测试：动态修改一个类的方法
        ins.addTransformer(new MyPreTransformer());
    }


    static class PreDefineTransformer implements ClassFileTransformer {

        public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                                ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
            System.out.println("premain load Class:" + className);
            return classfileBuffer;
        }
    }


}
