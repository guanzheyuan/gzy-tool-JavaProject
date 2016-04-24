package com.gzy.action.fileTool;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import org.springframework.web.multipart.MultipartFile;


/**
 * 文件管理类
 * @author Sway
 *
 */
public class FileTool {

	
	/**
	 *上传文件到服务器
	 */
	public void doUploadFile(MultipartFile file,String outPath, String name) {
		// TODO Auto-generated method stub
		OutputStream os = null;//�����
		InputStream is = null;//������
		if(null!=file){
			try {
				 is = file.getInputStream();
				 os = new FileOutputStream(outPath+""+this.doChangeFileName(file, name));
				 int byteRead=0;
				 byte[] buffer = new byte[1024];
				  while ((byteRead = is.read(buffer, 0,1024)) != -1) {
					  os.write(buffer, 0, byteRead);// ���ļ�д��
				    }
			} catch (IOException e) {
				e.printStackTrace();
			}finally {
				 try {
					 if(null!=os){
						 os.close();
					 }
					 if(null!=is){
						 is.close();
					 }
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * 复制照片到制定目录（单张、多张）
	 * @param start 开始地址
	 * @param end   结束地址
	 * @return
	 */
	public static boolean CopyPhoto(String start,String end){
		BufferedInputStream bis = null;//拷贝的地址
		BufferedOutputStream bos = null;//目标地址
		try {
			bis = new BufferedInputStream(new FileInputStream(new File(start)));
			bos = new BufferedOutputStream(new FileOutputStream(new File(end)));
			int val;
			while((val=bis.read())!=-1){
				 bos.write(val);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				 bis.close();
				 bos.flush();
				 bos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	/**
	 * 读取txt文件内容
	 * @param filePath
	 * @return
	 */
	@SuppressWarnings("unused")
	public static String readFile(String filePath[]){
		File files=null;
		String line="";
		String temp="";
		String textContent="";
		BufferedReader br =null;
		InputStreamReader read =null;
		try{
			for (int i = 0; i < filePath.length; i++) {
				 files = new File(filePath[i]);
				 read = new InputStreamReader(new FileInputStream(files),"gb2312");
				 br = new BufferedReader(read);
				//读取文件中的内容
				while((line=br.readLine())!=null){
					 temp = br.readLine();
					textContent+=""+temp+"\r\n";
				}
			}
		}catch(Exception e){e.printStackTrace();
		}finally{
			try{
				br.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return textContent;
	}
	/**
	 * 功能描述：字符串转换成byte类型
	 * @param hexstr
	 * @return
	 */
	public static byte[] getByte(String hexstr){
		 byte[] b=hexstr.getBytes();
		 return b;
	}
	/**
	 * 替换文件名
	 */
	public String doChangeFileName(MultipartFile file, String name) {
		String  fileName = file.getOriginalFilename();//��ȡ�ļ�����
		int fileNameLen = fileName.lastIndexOf(".");//��ȡ��׺��.��λ��
		String fileExtension=fileName.substring(fileNameLen, fileName.length());//��ȡ��׺��
		return name+""+fileExtension;//����������
	}
}
