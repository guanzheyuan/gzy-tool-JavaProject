package com.gzy.action.javaTool;

import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.gzy.action.stringTool.stringTool;
import com.gzy.action.verify.verify;

public class javaTool {
	
	public verify verify;
	public stringTool stringTool;

	/**
     * 获取实体类中的属性
     * @see 本方法用到了反射,其适用于所有的属性类型均为byte[]的JavaBean
     * @return String field11=value11 field22=value22 field33=value33
     */
    public static String getStringFromObjectForByte(Object obj){
        StringBuilder sb = new StringBuilder(); //局部的StringBuffer一律StringBuilder之
        sb.append(obj.getClass().getName()).append("@").append(obj.hashCode()).append("[");
        for(Field field : obj.getClass().getDeclaredFields()){
            String methodName = "get" + StringUtils.capitalize(field.getName()); //构造getter方法
            Object fieldValue = null;
            try{
                fieldValue = obj.getClass().getDeclaredMethod(methodName).invoke(obj); //执行getter方法,获取其返回值
            }catch(Exception e){
                //一旦发生异常,便将属性值置为UnKnown,故此处没必要一一捕获所有异常
                sb.append("\n").append(field.getName()).append("=UnKnown");
                continue;
            }
            if(fieldValue == null){
                sb.append("\n").append(field.getName()).append("=null");
            }else{
                sb.append("\n").append(field.getName()).append("=").append(new String((byte[])fieldValue));
            }
        }
        return sb.append("\n]").toString();
    }
    
    /**
     * 获取Map中的属性
     * @see 由于Map.toString()打印出来的参数值对,是横着一排的...参数多的时候,不便于查看各参数值
     * @see 故此仿照commons-lang.jar中的ReflectionToStringBuilder.toString()编写了本方法
     * @return String key11=value11 \n key22=value22 \n key33=value33 \n......
     */
    public static String getStringFromMap(Map<String, String> map){
        StringBuilder sb = new StringBuilder();
        sb.append(map.getClass().getName()).append("@").append(map.hashCode()).append("[");
        for(Map.Entry<String,String> entry : map.entrySet()){
            sb.append("\n").append(entry.getKey()).append("=").append(entry.getValue());
        }
        return sb.append("\n]").toString();
    }

    
    /**
     * 获取Map中的属性
     * @see 该方法的参数适用于打印Map<String, byte[]>的情况
     * @return String key11=value11 \n key22=value22 \n key33=value33 \n......
     */
    public static String getStringFromMapForByte(Map<String, byte[]> map){
        StringBuilder sb = new StringBuilder();
        sb.append(map.getClass().getName()).append("@").append(map.hashCode()).append("[");
        for(Map.Entry<String,byte[]> entry : map.entrySet()){
            sb.append("\n").append(entry.getKey()).append("=").append(new String(entry.getValue()));
        }
        return sb.append("\n]").toString();
    }
    
    
    /**
     * 获取Map中的属性
     * @see 该方法的参数适用于打印Map<String, Object>的情况
     * @return String key11=value11 \n key22=value22 \n key33=value33 \n......
     */
    public static String getStringFromMapForObject(Map<String, Object> map){
        StringBuilder sb = new StringBuilder();
        sb.append(map.getClass().getName()).append("@").append(map.hashCode()).append("[");
        for(Map.Entry<String,Object> entry : map.entrySet()){
            sb.append("\n").append(entry.getKey()).append("=").append(entry.getValue().toString());
        }
        return sb.append("\n]").toString();
    }
    
    /**
     * 金额元转分
     * @see 注意:该方法可处理贰仟万以内的金额,且若有小数位,则不限小数位的长度
     * @see 注意:如果你的金额达到了贰仟万以上,则不推荐使用该方法,否则计算出来的结果会令人大吃一惊
     * @param amount  金额的元进制字符串
     * @return String 金额的分进制字符串
     */
    @SuppressWarnings("static-access")
    public static String moneyYuanToFen(String amount){
        if(new verify().isEmpty(amount)){
            return amount;
        }
        //传入的金额字符串代表的是一个整数
        if(-1 == amount.indexOf(".")){
            return Integer.parseInt(amount) * 100 + "";
        }
        //传入的金额字符串里面含小数点-->取小数点前面的字符串,并将之转换成单位为分的整数表示
        int money_fen = Integer.parseInt(amount.substring(0, amount.indexOf("."))) * 100;
        //取到小数点后面的字符串
        String pointBehind = (amount.substring(amount.indexOf(".") + 1));
        //amount=12.3
        if(pointBehind.length() == 1){
            return money_fen + Integer.parseInt(pointBehind)*10 + "";
        }
        //小数点后面的第一位字符串的整数表示
        int pointString_1 = Integer.parseInt(pointBehind.substring(0, 1));
        //小数点后面的第二位字符串的整数表示
        int pointString_2 = Integer.parseInt(pointBehind.substring(1, 2));
        //amount==12.03,amount=12.00,amount=12.30
        if(pointString_1 == 0){
            return money_fen + pointString_2 + "";
        }else{
            return money_fen + pointString_1*10 + pointString_2 + "";
        }
    }
    
    
    /**
     * 金额元转分
     * @see 该方法会将金额中小数点后面的数值,四舍五入后只保留两位....如12.345-->12.35
     * @see 注意:该方法可处理贰仟万以内的金额
     * @see 注意:如果你的金额达到了贰仟万以上,则非常不建议使用该方法,否则计算出来的结果会令人大吃一惊
     * @param amount  金额的元进制字符串
     * @return String 金额的分进制字符串
     */
    @SuppressWarnings("static-access")
    public static String moneyYuanToFenByRound(String amount){
        if(new verify().isEmpty(amount)){
            return amount;
        }
        if(-1 == amount.indexOf(".")){
            return Integer.parseInt(amount) * 100 + "";
        }
        int money_fen = Integer.parseInt(amount.substring(0, amount.indexOf("."))) * 100;
        String pointBehind = (amount.substring(amount.indexOf(".") + 1));
        if(pointBehind.length() == 1){
            return money_fen + Integer.parseInt(pointBehind)*10 + "";
        }
        int pointString_1 = Integer.parseInt(pointBehind.substring(0, 1));
        int pointString_2 = Integer.parseInt(pointBehind.substring(1, 2));
        //下面这种方式用于处理pointBehind=245,286,295,298,995,998等需要四舍五入的情况
        if(pointBehind.length() > 2){
            int pointString_3 = Integer.parseInt(pointBehind.substring(2, 3));
            if(pointString_3 >= 5){
                if(pointString_2 == 9){
                    if(pointString_1 == 9){
                        money_fen = money_fen + 100;
                        pointString_1 = 0;
                        pointString_2 = 0;
                    }else{
                        pointString_1 = pointString_1 + 1;
                        pointString_2 = 0;
                    }
                }else{
                    pointString_2 = pointString_2 + 1;
                }
            }
        }
        if(pointString_1 == 0){
            return money_fen + pointString_2 + "";
        }else{
            return money_fen + pointString_1*10 + pointString_2 + "";
        }
    }
    
    
    /**
     * 金额分转元
     * @see 注意:如果传入的参数中含小数点,则直接原样返回
     * @see 该方法返回的金额字符串格式为<code>00.00</code>,其整数位有且至少有一个,小数位有且长度固定为2
     * @param amount  金额的分进制字符串
     * @return String 金额的元进制字符串
     */
    @SuppressWarnings("static-access")
	public static String moneyFenToYuan(String amount){
        if(new verify().isEmpty(amount)){
            return amount;
        }
        if(amount.indexOf(".") > -1){
            return amount;
        }
        if(amount.length() == 1){
            return "0.0" + amount;
        }else if(amount.length() == 2){
            return "0." + amount;
        }else{
            return amount.substring(0, amount.length()-2) + "." + amount.substring(amount.length()-2);
        }
    }
    

   
    
    /**
     * 根据指定的签名密钥和算法签名Map<String,String>
     * @see 方法内部首先会过滤Map<String,String>参数中的部分键值对
     * @see 过滤规则:移除键名为"cert","hmac","signMsg"或者键值为null或者键值长度为零的键值对
     * @see 过滤结果:过滤完Map<String,String>后会产生一个字符串,其格式为[key11=value11|key22=value22|key33=value33]
     * @see And the calls {@link TradePortalUtil#getHexSign(String,String,String,boolean)}进行签名
     * @param param     待签名的Map<String,String>
     * @param charset   签名时转码用到的字符集
     * @param algorithm 签名时所使用的算法,从业务上看,目前其可传入两个值:MD5,SHA-1
     * @param signKey   签名用到的密钥
     * @return String algorithm digest as a lowerCase hex string
     */
    public static String getHexSign(Map<String, String> param, String charset, String algorithm, String signKey){
        StringBuilder sb = new StringBuilder();
        List<String> keys = new ArrayList<String>(param.keySet());
        Collections.sort(keys);
        for(int i=0; i<keys.size(); i++){
            String key = keys.get(i);
            String value = param.get(key);
            if(key.equalsIgnoreCase("cert") || key.equalsIgnoreCase("hmac") || key.equalsIgnoreCase("signMsg") || value==null || value.length()==0){
                continue;
            }
            sb.append(key).append("=").append(value).append("|");
        }
        sb.append("key=").append(signKey);
        return getHexSign(sb.toString(), charset, algorithm, true);
    }
    
    
    /**
     * 通过指定算法签名字符串
     * @see Calculates the algorithm digest and returns the value as a hex string
     * @see If system dosen't support this <code>algorithm</code>, return "" not null
     * @see It will Calls {@link TradePortalUtil#getBytes(String str, String charset)}
     * @see 若系统不支持<code>charset</code>字符集,则按照系统默认字符集进行转换
     * @see 若系统不支持<code>algorithm</code>算法,则直接返回""空字符串
     * @see 另外,commons-codec.jar提供的DigestUtils.md5Hex(String data)与本方法getHexSign(data, "UTF-8", "MD5", false)效果相同
     * @param data        Data to digest
     * @param charset     字符串转码为byte[]时使用的字符集
     * @param algorithm   目前其有效值为<code>MD5,SHA,SHA1,SHA-1,SHA-256,SHA-384,SHA-512</code>
     * @param toLowerCase 指定是否返回小写形式的十六进制字符串
     * @return String algorithm digest as a lowerCase hex string
     */
    @SuppressWarnings("static-access")
	public static String getHexSign(String data, String charset, String algorithm, boolean toLowerCase){
        char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        char[] DIGITS_UPPER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        //Used to build output as Hex
        char[] DIGITS = toLowerCase ? DIGITS_LOWER : DIGITS_UPPER;
        //get byte[] from {@link TradePortalUtil#getBytes(String, String)}
        byte[] dataBytes = new stringTool().getBytes(data, charset);
        byte[] algorithmData = null;
        try {
            //get an algorithm digest instance
            algorithmData = MessageDigest.getInstance(algorithm).digest(dataBytes);
        } catch (NoSuchAlgorithmException e) {
            //LogUtil.getLogger().error("签名字符串[" + data + "]时发生异常:System doesn't support this algorithm[" + algorithm + "]");
            return "";
        }
        char[] respData = new char[algorithmData.length << 1];
        //two characters form the hex value
        for(int i=0,j=0; i<algorithmData.length; i++){
            respData[j++] = DIGITS[(0xF0 & algorithmData[i]) >>> 4];
            respData[j++] = DIGITS[0x0F & algorithmData[i]];
        }
        return new String(respData);
    }
    

    


}
