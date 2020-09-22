package com.cn.afteragent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

/**
 * Java6 以后实现启动后加载的新实现是Attach api:
 * com.sun.tools.attach VirtualMachine 和 VirtualMachineDescriptor
 */
public class AfterMainTraceAgent {

    public static void agentmain(String args, Instrumentation ins) {
        System.out.println("AfterMainTraceAgent agentmain start....");
        Class[] allLoadedClasses = ins.getAllLoadedClasses();
        for (Class clazz : allLoadedClasses) {
            String name = clazz.getName();
            System.out.println("allLoadedClasses===" + name);
        }

        ins.addTransformer(new AfterDefineTransformer());

        System.out.println("AfterMainTraceAgent agentmain end....");
    }

    static class AfterDefineTransformer implements ClassFileTransformer {

        public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                                ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
            System.out.println("after load Class:" + className);
            return classfileBuffer;
        }
    }
}
