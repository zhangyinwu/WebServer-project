package webserver;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class Application {
	Response response;
	Request request;

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public byte[] getAdress(String get) throws IOException {
		StringBuilder sbr = new StringBuilder();
		System.out.println("1"+HttpServer.Web_Root);
		System.out.println("2"+get);
		File file = new File(HttpServer.Web_Root+get);
		File[] files = file.listFiles();
		// String get = request.parse();
		if(file.isDirectory()){
		for (File sonFile : files) {
           // file=sonFile;
			sbr.append("<html>\n");
			sbr.append("<head>\n");
			sbr.append("<title>Web����������ҳ��</title>\n");
			sbr.append("</head>\n");
			sbr.append("<body>\n");
			sbr.append("<div align=" + "center" + ">�������Ѿ��ɹ����� </div>\n");

			sbr.append("<a href=\"");
			String address =   get + File.separator + sonFile.getName();// HttpServer.host
																		// +
			System.out.println(address);
			sbr.append(address);
			sbr.append("\"/>");
			sbr.append(sonFile.getName()+"/");
			sbr.append("</a><br>");
			sbr.append("</body>\n");
			sbr.append("</html>");
		}
		return sbr.toString().getBytes();
		}
		else if(file.isFile()){
			sbr.append("<html>\n");
			sbr.append("<head>\n");
			sbr.append("<title>Web����������ҳ��</title>\n");
			sbr.append("</head>\n");
			sbr.append("<body>\n");
			sbr.append("<div align=" + "center" + ">�������Ѿ��ɹ����� </div>\n");

			sbr.append("<a href=\"");
			String address = "localhost:8080" +get+ File.separator + file.getName();														
			System.out.println("filename:" + file.getName());
			sbr.append(address);
			System.out.println(readFile(file));
			sbr.append(readFile(file));
			sbr.append("\"/>");
			sbr.append(file.getName()+"...");
			sbr.append("</a><br>");
			sbr.append("</body>\n");
			sbr.append("</html>");
			
		}
		return sbr.toString().getBytes();
	}
	public String readFile(File file) throws IOException{
		System.out.println(file.isDirectory());
		System.out.println(file.isFile());
		byte[] buffer=null;
		FileInputStream fis = new FileInputStream(file); // ����һ���ļ��Ķ�ȡ��
		long length = fis.available();
		ByteArrayOutputStream bos = new ByteArrayOutputStream((int) length); // ��ȡ�����ڵ�����,ת��������
		byte[] b = new byte[4096];
		int n;
		while ((n = fis.read(b)) != -1) { // ���ļ�file��ȡ����,���������b��
			bos.write(b, 0, n);
		}
		buffer = bos.toByteArray(); // �õ��������
        if(fis !=null){
        	fis.close();
        }
        if(bos !=null){
        	bos.close();
        }
        return new String(bos.toByteArray(), "UTF-8");
	}

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

}
