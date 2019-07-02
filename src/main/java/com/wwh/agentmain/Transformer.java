package com.wwh.agentmain;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

class Transformer implements ClassFileTransformer {

	/**
	 * 要改变的类名
	 */
	private static final String replaceClassName = "com/wwh/agentmain/TransClass";

	/**
	 * 要改变的类的字节码
	 */
	private static final String classNumberReturns2 = "TransClass.class.2";

	public static byte[] getBytesFromFile(String fileName) {
		try {

			File file = new File(fileName);
			InputStream is = new FileInputStream(file);
			long length = file.length();
			byte[] bytes = new byte[(int) length];

			// 读取文件内容
			int offset = 0;
			int numRead = 0;
			while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
				offset += numRead;
			}

			is.close();
			if (offset < bytes.length) {

				throw new IOException("Could not completely read file " + file.getName());
			}
			return bytes;
		} catch (Exception e) {
			System.out.println("error occurs in _ClassTransformer!" + e.getClass().getName());
			return null;
		}
	}

	public byte[] transform(ClassLoader l, String className, Class<?> c, ProtectionDomain pd, byte[] b)
			throws IllegalClassFormatException {

		System.out.println("加载了类：" + className);

		if (!className.equals(replaceClassName)) {
			return null;
		}

		System.out.println("从文件中加载新的字节码进行替换");
		return getBytesFromFile(classNumberReturns2);

	}
}