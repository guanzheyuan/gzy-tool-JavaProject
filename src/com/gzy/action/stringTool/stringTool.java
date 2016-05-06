package com.gzy.action.stringTool;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;

import com.gzy.action.verify.verify;

public class stringTool {

	/**
     * 字符编码
     * @see 该方法默认会以UTF-8编码字符串
     * @see 若想自己指定字符集,可以使用<code>encode(String chinese, String charset)</code>方法
     */
    public static String encode(String chinese){
        return encode(chinese, "UTF-8");
    }
    
    
    /**
     * 字符编码
     * @see 该方法通常用于对中文进行编码
     * @see 若系统不支持指定的编码字符集,则直接将<code>chinese</code>原样返回
     */
    public static String encode(String chinese, String charset){
        chinese = (chinese==null ? "" : chinese);
        try {
            return URLEncoder.encode(chinese, charset);
        } catch (UnsupportedEncodingException e) {
            //LogUtil.getLogger().error("编码字符串[" + chinese + "]时发生异常:系统不支持该字符集[" + charset + "]");
            return chinese;
        }
    }
    
    
    /**
     * 字符解码
     * @see 该方法默认会以UTF-8解码字符串
     * @see 若想自己指定字符集,可以使用<code>decode(String chinese, String charset)</code>方法
     */
    public static String decode(String chinese){
        return decode(chinese, "UTF-8");
    }
    
    
    /**
     * 字符解码
     * @see 该方法通常用于对中文进行解码
     * @see 若系统不支持指定的解码字符集,则直接将<code>chinese</code>原样返回
     */
    public static String decode(String chinese, String charset){
        chinese = (chinese==null ? "" : chinese);
        try {
            return URLDecoder.decode(chinese, charset);
        } catch (UnsupportedEncodingException e) {
            //LogUtil.getLogger().error("解码字符串[" + chinese + "]时发生异常:系统不支持该字符集[" + charset + "]");
            return chinese;
        }
    }
    
    
    /**
     * 字符串右补空格
     * @see 该方法默认采用空格(其ASCII码为32)来右补字符
     * @see 若想自己指定所补字符,可以使用<code>rightPadForByte(String str, int size, int padStrByASCII)</code>方法
     */
    public static String rightPadForByte(String str, int size){
        return rightPadForByte(str, size, 32);
    }
    
    
    /**
     * 字符串右补字符
     * @see 若str对应的byte[]长度不小于size,则按照size截取str对应的byte[],而非原样返回str
     * @see 所以size参数很关键..事实上之所以这么处理,是由于支付处理系统接口文档规定了字段的最大长度
     * @see 若对普通字符串进行右补字符,建议org.apache.commons.lang.StringUtils.rightPad(...)
     * @param size          该参数指的不是字符串长度,而是字符串所对应的byte[]长度
     * @param padStrByASCII 该值为所补字符的ASCII码,如32表示空格,48表示0,64表示@等
     */
    public static String rightPadForByte(String str, int size, int padStrByASCII){
        byte[] srcByte = str.getBytes();
        byte[] destByte = null;
        if(srcByte.length >= size){
            //由于size不大于原数组长度,故该方法此时会按照size自动截取,它会在数组右侧填充'(byte)0'以使其具有指定的长度
            destByte = Arrays.copyOf(srcByte, size);
        }else{
            destByte = Arrays.copyOf(srcByte, size);
            Arrays.fill(destByte, srcByte.length, size, (byte)padStrByASCII);
        }
        return new String(destByte);
    }
    
    
    /**
     * 字符串左补空格
     * @see 该方法默认采用空格(其ASCII码为32)来左补字符
     * @see 若想自己指定所补字符,可以使用<code>leftPadForByte(String str, int size, int padStrByASCII)</code>方法
     */
    public static String leftPadForByte(String str, int size){
        return leftPadForByte(str, size, 32);
    }
    
    
    /**
     * 字符串左补字符
     * @see 若str对应的byte[]长度不小于size,则按照size截取str对应的byte[],而非原样返回str
     * @see 所以size参数很关键..事实上之所以这么处理,是由于支付处理系统接口文档规定了字段的最大长度
     * @param padStrByASCII 该值为所补字符的ASCII码,如32表示空格,48表示0,64表示@等
     */
    public static String leftPadForByte(String str, int size, int padStrByASCII){
        byte[] srcByte = str.getBytes();
        byte[] destByte = new byte[size];
        Arrays.fill(destByte, (byte)padStrByASCII);
        if(srcByte.length >= size){
            System.arraycopy(srcByte, 0, destByte, 0, size);
        }else{
            System.arraycopy(srcByte, 0, destByte, size-srcByte.length, srcByte.length);
        }
        return new String(destByte);
    }
    
    /**
     * 字符串转为字节数组
     * @see 该方法默认以ISO-8859-1转码
     * @see 若想自己指定字符集,可以使用<code>getBytes(String str, String charset)</code>方法
     */
    public static byte[] getBytes(String data){
        return getBytes(data, "ISO-8859-1");
    }
    
    
    /**
     * 字符串转为字节数组
     * @see 如果系统不支持所传入的<code>charset</code>字符集,则按照系统默认字符集进行转换
     */
    @SuppressWarnings("static-access")
    public static byte[] getBytes(String data, String charset){
        data = (data==null ? "" : data);
        if(new verify().isEmpty(charset)){
            return data.getBytes();
        }
        try {
            return data.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            //LogUtil.getLogger().error("将字符串[" + data + "]转为byte[]时发生异常:系统不支持该字符集[" + charset + "]");
            return data.getBytes();
        }
    }

}
