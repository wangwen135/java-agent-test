package com.wwh.agentmain;

import java.util.List;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

/**
 * <pre>
 * 需要先打包 jar 包
 * mvn package
 * 
 * 先启动这个类 
 * 
 * 再启动 TestMainInJar
 * </pre>
 * 
 * @author wwh
 *
 */
public class AttachThread extends Thread {

	public static final String JAR_PATH = "target/java-agent-test-0.0.1-SNAPSHOT.jar";

	private final List<VirtualMachineDescriptor> listBefore;

	private final String jar;

	AttachThread(String attachJar, List<VirtualMachineDescriptor> vms) {
		listBefore = vms; // 记录程序启动时的 VM 集合
		jar = attachJar;
	}

	public static void main(String[] args) throws InterruptedException {

		System.out.println(System.getProperty("user.dir"));

		// new AttachThread(System.getProperty("user.dir") + JAR_PATH,
		// VirtualMachine.list()).start();

		List<VirtualMachineDescriptor> vmList = VirtualMachine.list();

		System.out.println("当前已经启动的JVM");
		for (VirtualMachineDescriptor virtualMachineDescriptor : vmList) {
			System.out.println(virtualMachineDescriptor.id() + "\t" + virtualMachineDescriptor);
		}

		new AttachThread(JAR_PATH, vmList).start();

	}

	public void run() {
		VirtualMachine vm = null;
		List<VirtualMachineDescriptor> listAfter = null;
		try {

			int count = 0;
			while (true) {

				listAfter = VirtualMachine.list();

				System.out.println("寻找新启动的JVM...");
				for (VirtualMachineDescriptor vmd : listAfter) {

					if (!listBefore.contains(vmd)) {
						// 如果 VM 有增加，我们就认为是被监控的 VM 启动了
						// 这时，我们开始监控这个 VM

						System.out.println("找到新启动的虚拟机：" + vmd.id() + "\t" + vmd.displayName());

						System.out.println("附加到该虚拟机");
						vm = VirtualMachine.attach(vmd);

						System.out.println("加载java Agent ：" + jar);
						vm.loadAgent(jar);
						// vm.loadAgent(jar, options);
						
						vm.detach();
						System.out.println("从该虚拟机中分离");

						break;
					}
				}
				Thread.sleep(500);
				count++;
				if (null != vm || count >= 100) {
					break;
				}
			}

			System.out.println("退出！");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
