package webserver;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 应用类，即应用程序服务器，将web服务器传过来的流进行处理
 * 
 * @author: John
 * @Class: Application
 * @date: 2015年12月25日
 */
public class Application {
	Response response;
	Request request;

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
				HttpServer.logger.info(address);
				sbr.append(address);
				sbr.append("\"/>");
				sbr.append(sonFile.getName());
				sbr.append("</a><br>");
				sbr.append("</body>\n");
				sbr.append("</html>");
			}
			response.getOutput(sbr.toString().getBytes());

		} else if (file.isFile()) {
			response.getOutput("HTTP/1.1 200 "+"MIME_version:1.0"+"Content_Type:text/html");
			response.getOutput("Content_Length:"+file.length());
			response.getOutput("");
			response.getOutput(readFile(file));
		}

	}
 
	/**
	 * 读取文件中的内容，并返回一个字节数组
	 * 
	 * @author:John
	 * @return:byte[]
	 * @date: 2015年12月25日
	 */
	public byte[] readFile(File file) throws IOException {
		byte[] buffer = null;
		FileInputStream fis = new FileInputStream(file);
		long length = fis.available();
		ByteArrayOutputStream bos = new ByteArrayOutputStream((int) length);
		byte[] b = new byte[4096];
		int n;
		while ((n = fis.read(b)) != -1) {
			bos.write(b, 0, n);
		}
		buffer = bos.toByteArray();
		if (fis != null) {
			fis.close();
		}
		if (bos != null) {
			bos.close();
		}
		return buffer;
	}

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

}
