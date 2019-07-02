package com.wwh.agent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class PrintClassFileAgent implements ClassFileTransformer {

    public static final String OUT_FILE_DIR = "/opt/logs/wwh/classFile/";

    private File outFileDir;

    public PrintClassFileAgent(){

    }

    public PrintClassFileAgent(String fileDir){
        String fileOutDir = OUT_FILE_DIR;
        if (fileDir != null && !"".equals(fileDir)) {
            fileOutDir = fileDir;
        }
        outFileDir = new File(fileOutDir);
        outFileDir.mkdirs();

    }

    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain,
                            byte[] classfileBuffer) throws IllegalClassFormatException {
        System.out.println("类加载器：" + loader);
        System.out.println("类名称：" + className);

        String pathName = className.replaceAll("[.]", "/");
        pathName = pathName + ".class";

        File f = new File(OUT_FILE_DIR + pathName);

        f.getParentFile().mkdirs();
        try {
            f.createNewFile();
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(classfileBuffer);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
