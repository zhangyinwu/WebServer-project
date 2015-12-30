package webserver;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;
import org.slf4j.LoggerFactory;

/**
 * 服务器类，用来监听客户端的http请求
 * 
 * @author: John
 * @Class: HttpServer
 * @date: 2015年12月25日
 */
public class HttpServer {
	public static org.slf4j.Logger logger=LoggerFactory.getLogger(HttpServer.class);
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
			PrintStream output = null;
			Socket socket = serverSocket.accept();
			input = socket.getInputStream();
			output = new PrintStream(socket.getOutputStream()) ;
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
		iport = Integer.parseInt(ppsIni.getProperty("port"));
	}

	public static void main(String[] args) throws Exception {
		logger.info(Web_Root);
		HttpServer server = new HttpServer();
		server.await("127.0.0.1");
	}

}
