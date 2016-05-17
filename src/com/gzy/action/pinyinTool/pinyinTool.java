package com.gzy.action.pinyinTool;

import java.text.Collator;
import java.util.Arrays;
import java.util.Comparator;
public class pinyinTool {
 
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) {
		 //Collator 类是用来执行区分语言环境的 String 比较的，这里选择使用CHINA
		  Comparator comparator = Collator.getInstance(java.util.Locale.CHINA);
		  String[] arrStrings = { "乔峰", "郭靖", "杨过", "张无忌","韦小宝","admin" };
		  // 使根据指定比较器产生的顺序对指定对象数组进行排序。
		  Arrays.sort(arrStrings, comparator);
		  for (int i = 0; i < arrStrings.length; i++){
		   System.out.println(arrStrings[i]);
		 }
	}
}
