package com.gzy.action.serialNumber;

import java.util.UUID;
/**
 * 获取随机数，自定义长度与是否包含数字、英文
 * @author sway
 *
 */
public class serial {
	
	/**
     * 获取系统流水号
     * @see 若欲指定返回值的长度or是否全由数字组成等,you can call {@link TradePortalUtil#getSysJournalNo(int, boolean)}
     * @return 长度为20的全数字
     */
    public static String getSysJournalNo(){
        return getSysJournalNo(20, true);
    }

	/**
     * 获取系统流水号
     * @param length   指定流水号长度
     * @param toNumber 指定流水号是否全由数字组成
     */
    public static String getSysJournalNo(int length, boolean isNumber){
        //replaceAll()之后返回的是一个由十六进制形式组成的且长度为32的字符串
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        if(uuid.length() > length){
            uuid = uuid.substring(0, length);
        }else if(uuid.length() < length){
            for(int i=0; i<length-uuid.length(); i++){
                uuid = uuid + Math.round(Math.random()*9);
            }
        }
        if(isNumber){
            return uuid.replaceAll("a", "1").replaceAll("b", "2").replaceAll("c", "3").replaceAll("d", "4").replaceAll("e", "5").replaceAll("f", "6");
        }else{
            return uuid;
        }
    }
    
    public static void main(String[] args) {
		System.out.println(getSysJournalNo());
	}
}
