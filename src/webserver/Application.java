package webserver;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import org.slf4j.LoggerFactory;

/**
 * 应用类，即应用程序服务器，将web服务器传过来的流进行处理
 * 
 * @author: John
 * @Class: Application
 * @date: 2015年12月25日
 */
public class Application {
	private org.slf4j.Logger logger=LoggerFactory.getLogger(Application.class);
	Response response;
	Request request;
	File contentTypeIni = new File(System.getProperty("user.dir"),
			"ContentType.ini");

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	/**
	 * 此方法用来生成显示文件或者文件夹的网页
	 * 
	 * @author:John
	 * @return:void 
	 * @date: 2015年12月25日
	 */
	public void getAdress() throws IOException {
		StringBuilder sbr = new StringBuilder();
		File file = new File(HttpServer.Web_Root, request.getUrl());
		File[] files = file.listFiles();
		if (file.isDirectory()) {
			for (File sonFile : files) {
				sbr.append("<html>\n");
				sbr.append("<head>\n");
				sbr.append("<title>WebServer Test</title>\n");
				sbr.append("</head>\n");
				sbr.append("<body>\n");
				sbr.append("<div align=" + "center" + ">test text </div>\n");

				sbr.append("<a href=\"");
				String address = "http://localhost:8080" + File.separator
						+ request.getUrl() + File.separator + sonFile.getName();
				logger.info("address{}",address);
				sbr.append(address);
				sbr.append("\"/>");
				sbr.append(sonFile.getName());
				sbr.append("</a><br>");
				sbr.append("</body>\n");
				sbr.append("</html>");
			}
			response.getOutput(sbr.toString().getBytes());

		} else if (file.isFile()) {
			String reString=request.getRequest();
			
			if(reString.indexOf("Range")==-1){
			response.getOutput("HTTP/1.1 200 OK");
			response.getOutput("MIME_version:1.0");
			//response.getOutput("Content_Type:"+getContentType(file));
			response.getOutput("Content-Length:"+file.length());
			response.getOutput("");
			response.getOutput(readFile(file));
			}
			else {
				int startRange =request.startRange();
				int endRange = request.endRange();
				response.getOutput("HTTP/1.1 206\n "+"MIME_version:1.0");
				response.getOutput("Content-Range: bytes "+startRange+"-"+endRange + "/" + file.length());//bytes=
				response.getOutput("Content-Length:"+(endRange-startRange+1));
				response.getOutput("Content_Type:"+getContentType(file));
				response.getOutput("");
				response.getOutput(readRangeFile(file, startRange, endRange));
			}
		}else {
			sbr.append("<html>\n");
			sbr.append("<head>\n");
			sbr.append("<title>WebServer Test</title>\n");
			sbr.append("</head>\n");
			sbr.append("<body>\n");
			sbr.append("<div align=" + "center" + ">网页找不到 </div>\n");
			sbr.append("\"/>");
			sbr.append("</a><br>");
			sbr.append("</body>\n");
			sbr.append("</html>");
			logger.info("网页错误或者文件找不到！");
		}

	}
	/**
	 * 获取文件的类型
	 * @author:John
	 * @return:String
	 * @date: 2016年1月5日
	 */
	private String getContentType(File file) throws FileNotFoundException,
	IOException {
		String fileName = file.getName();
		int index = fileName.indexOf('.');
			String fileType = fileName.substring(index);
			Properties ppsContentType = new Properties();
			ppsContentType.load(new FileInputStream(contentTypeIni));
			String contentType = ppsContentType.getProperty(fileType,
					"application/octet-stream");
			return contentType;


}

 
	/**
	 * 读取文件中的内容，并返回一个字节数组
	 * 
	 * @author:John
	 * @return:byte[]
	 * @throws IOException 
	 * @date: 2015年12月25日
	 */
	private byte[] readFile(File file) throws IOException  {
		FileInputStream fis =null;
		ByteArrayOutputStream bos = null;
		byte[] buffer = null;
		try{
		
		fis = new FileInputStream(file);
		bos = new ByteArrayOutputStream(4096);
		byte[] b = new byte[4096];
		int n;
		while ((n = fis.read(b)) != -1) {
			bos.write(b, 0, n);
		}
		buffer = bos.toByteArray();
		}
		finally{
		if (fis != null) {
			fis.close();
		}
		if (bos != null) {
			bos.close();
		}
		}
		return buffer;
	}
	/**
	 * 读取文件中的部分内容
	 * @author:John
	 * @return:byte[]
	 * @date: 2016年1月5日
	 */
	private byte[] readRangeFile (File file, int startRange, int endRange) throws IOException{
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		byte[] buff = null;
		try{
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			int filePartLength =  endRange - startRange + 1;
			bis.skip(startRange);
			buff = new byte[filePartLength];
			int count = 0;
			int n;
			while (count < filePartLength
				&& (n = bis.read(buff, count,
						Math.min(4096, filePartLength - count))) != -1) {
			count += n;
			}
			}
		finally{
		if(bis != null){
			bis.close();
		}
		if(fis !=null){
			fis.close();
		}
		}
		return buff;

	}

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

}
