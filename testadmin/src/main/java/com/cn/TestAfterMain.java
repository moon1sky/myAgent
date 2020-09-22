package com.cn;

import com.sun.tools.attach.*;

import java.io.IOException;
import java.util.List;

public class TestAfterMain {
    public static void main(String[] args) throws IOException, AttachNotSupportedException,
            AgentLoadException, AgentInitializationException {

        //获取当前系统中所有 运行中的 虚拟机
        System.out.println("running JVM start ");
        List<VirtualMachineDescriptor> list = VirtualMachine.list();
        for (VirtualMachineDescriptor vmd : list) {
            //如果虚拟机的名称为 xxx 则 该虚拟机为目标虚拟机，获取该虚拟机的 pid
            //然后加载 agent.jar 发送给该虚拟机
            System.out.println(vmd.displayName());
            if (vmd.displayName().endsWith("com.cn.TestAfterMain")) {
                VirtualMachine virtualMachine = VirtualMachine.attach(vmd.id());
                virtualMachine.loadAgent("/Users/XXX/Desktop/gitlab/myAgent/myAgent/target/myAgent-1.0-SNAPSHOT.jar");
                virtualMachine.detach();
            }
        }

        System.out.println("测试修改方法体："+TestTransformClass.testMethod());
        System.out.println("end........");
    }
}
