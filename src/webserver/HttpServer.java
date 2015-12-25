package webserver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Properties;

/**
 * 服务器类，用来监听客户端的http请求
 * 
 * @author: John
 * @Class: HttpServer
 * @date: 2015年12月25日
 */
public class HttpServer {
	public static int iport;
	public static String host;
	public static String Web_Root;

	/**
	 * 用来监听客户端的请求
	 * 
	 * @author:John
	 * @return:void
	 * @date: 2015年12月25日
	 */
	public void await(String ihost) throws Exception {
		getConf();
		host = "localhost:" + iport;
		ServerSocket serverSocket = null;
		serverSocket = new ServerSocket(iport, 1, InetAddress.getByName(ihost));
		while (true) {

			Application application = new Application();
			InputStream input = null;
			OutputStream output = null;
			Socket socket = serverSocket.accept();
			input = socket.getInputStream();
			output = socket.getOutputStream();
			Request request = new Request();
			request.setInput(input);
			request.parse();
			Response response = new Response(output);
			response.setOutput(output);
			application.setRequest(request);
			application.setResponse(response);
			application.getAdress();
			socket.close();
		}

	}

	/**
	 * 获取配置文件中的信息
	 * 
	 * @author:John
	 * @return:void
	 * @date: 2015年12月25日
	 */
	public void getConf() throws Exception {
		File iniFile = new File("D:/eclipse/workspace/web server/configer.ini");
		Properties ppsIni = new Properties();// FileInputStream(iniFile);
		ppsIni.load(new FileInputStream(iniFile));
		Web_Root = ppsIni.getProperty("Web_Root");
		System.out.println("1" + Web_Root);
		iport = Integer.parseInt(ppsIni.getProperty("port"));
		System.out.println(iport);
	}

	public static void main(String[] args) throws Exception {
		System.out.println(Web_Root);
		HttpServer server = new HttpServer();
		server.await("127.0.0.1");
	}

}
