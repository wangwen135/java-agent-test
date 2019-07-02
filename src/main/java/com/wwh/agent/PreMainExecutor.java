package com.wwh.agent;

import java.lang.instrument.Instrumentation;

public class PreMainExecutor {

    public static void premain(String agentOps, Instrumentation inst) {
        System.out.println("premain execute..........");
        System.out.println("参数：" + agentOps);
        // 添加Transformer
        inst.addTransformer(new PrintClassFileAgent(agentOps));

        // 可以用这个来加载jar包
        // inst.appendToSystemClassLoaderSearch(jarfile);
    }
}
