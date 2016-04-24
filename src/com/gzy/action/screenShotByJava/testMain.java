package com.gzy.action.screenShotByJava;

import java.io.File;

/**
 * 截图操作测试类
 * @author Sway
 *
 */
public class testMain {
	public static void main(String[] args) {
		try {
			new screenShot("https://www.baidu.com", new File("C:/Users/Sway/Desktop/file.jpg"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
