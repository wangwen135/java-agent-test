package com.wwh.agentmain;

/**
 * <pre>
 * 先启动 AttachThread
 * 在启动这个
 * 可以看到执行结果再运行时被改变了：
1
执行了agentmain 方法 
重新加载类：class com.wwh.agentmain.TransClass
加载了类：com/wwh/agentmain/TransClass
从文件中加载新的字节码进行替换
Agent Main Done
2
2
 * 
 * 
 * </pre>
 * 
 * @author wwh
 *
 */
public class TestMainInJar {

	public static void main(String[] args) throws InterruptedException {
		System.out.println(new TransClass().getNumber());
		int count = 0;
		while (true) {
			Thread.sleep(500);
			count++;
			int number = new TransClass().getNumber();
			System.out.println(number);
			if (3 == number || count >= 1000) {
				break;
			}
		}
	}
}
