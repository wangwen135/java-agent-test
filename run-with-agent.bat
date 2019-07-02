echo off


echo 启动程序

echo 执行这个会打印2，因为在agent中替换了Class的字节码

echo 回车继续
pause

java -javaagent:target/java-agent-test-0.0.1-SNAPSHOT.jar -cp target/java-agent-test-0.0.1-SNAPSHOT.jar com.wwh.agentmain.TestMainInJar

echo 回车继续
pause