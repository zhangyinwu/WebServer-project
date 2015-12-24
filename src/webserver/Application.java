package webserver;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Application {
	Response response;
	Request request;

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public void getAdress() throws IOException {
		StringBuilder sbr = new StringBuilder();
		System.out.println("1" + HttpServer.Web_Root);
		System.out.println("2" + request.getUrl());
		File file = new File(HttpServer.Web_Root , request.getUrl());		
		File[] files = file.listFiles();
		if (file.isDirectory()) {
			for (File sonFile : files) {
				// file=sonFile;
				sbr.append("<html>\n");
				sbr.append("<head>\n");
				sbr.append("<title>WebServer Test</title>\n");
				sbr.append("</head>\n");
				sbr.append("<body>\n");
				sbr.append("<div align=" + "center" + ">test text </div>\n");

				sbr.append("<a href=\"");
				String address = "http://localhost:8080"+ File.separator+request.getUrl() + File.separator
						+ sonFile.getName();// HttpServer.host
				// +
				System.out.println(address);
				sbr.append(address);
				sbr.append("\"/>");
				sbr.append(sonFile.getName() );
				sbr.append("</a><br>");
				sbr.append("</body>\n");
				sbr.append("</html>");
			}
			response.getOutput(sbr.toString().getBytes());
						
		} else if (file.isFile()) {
			
			response.getOutput(readFile(file));
		}
		
	}

	public byte[] readFile(File file) throws IOException {
		System.out.println(file.isDirectory());
		System.out.println(file.isFile());
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
