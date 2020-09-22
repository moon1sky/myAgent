package com.cn;

/**
 * 如果是Pre的拦截，启动之前执行 -javaagent:/Users/XXX/Desktop/gitlab/myAgent/myAgent/target/myAgent-1.0-SNAPSHOT.jar
 */
public class TestPreMain {

    public static void main(String[] args) {
        System.out.println("TestPreMain start args="+args.length);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int num = TestTransformClass.testMethod();
        System.out.println("TestTransformClass.testMethod():"+num);


        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("TestPreMain end");
    }


}
