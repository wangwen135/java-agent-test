## java agent 测试

>参考:  
>https://docs.oracle.com/javase/8/docs/jdk/api/attach/spec/com/sun/tools/attach/VirtualMachine.html#loadAgent-java.lang.String-  
>https://docs.oracle.com/javase/8/docs/api/java/lang/instrument/package-summary.html?is-external=true  
>https://www.ibm.com/developerworks/cn/java/j-lo-jse61/  


### 注意

#### pom文件中的
```
<manifestEntries>
	<!-- 参数方式启动agent需要这个 -->
	<Premain-Class>
		com.wwh.agentmain.AgentMain
	</Premain-Class>
	<!-- 启动后附加启动agent需要这个 -->
	<Agent-Class>
		com.wwh.agentmain.AgentMain
	</Agent-Class>
	<!-- 是否可以重新转换类 -->
	<Can-Retransform-Classes>
		true
	</Can-Retransform-Classes>
	<!-- 是否可以重新定义类 -->
	<Can-Redefine-Classes>
		true
	</Can-Redefine-Classes>
</manifestEntries>
```

**生成的 MANIFEST.MF 文件**
```
Manifest-Version: 1.0
Premain-Class: com.wwh.agentmain.AgentMain
Archiver-Version: Plexus Archiver
Built-By: Administrator
Agent-Class: com.wwh.agentmain.AgentMain
Can-Redefine-Classes: true
Can-Retransform-Classes: true
Created-By: Apache Maven 3.5.3
Build-Jdk: 1.8.0_151


```

#### AgentMain中的两个静态方法
```
// 在java程序启动后附加 agent
// vm.loadAgent(jar);
// 必须在jar包的 manifest文件中指定 Agent-Class 为当前类
public static void agentmain(String agentArgs, Instrumentation inst) 
	
// 启动参数的方式执行的是这个方法
// java -javaagent:xxxx-agrent.jar
// 必须在 jar包的 manifest文件中指定 Premain-Class 为当前类
public static void premain(String agentArgs, Instrumentation inst)
```