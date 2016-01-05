package webserver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.LoggerFactory;

/**
 * 服务器类，用来监听客户端的http请求
 * 
 * @author: John
 * @Class: HttpServer
 * @date: 2015年12月25日
 */
public class HttpServer {
	private static org.slf4j.Logger logger = LoggerFactory
			.getLogger(HttpServer.class);
	public static int iport;
	public static String host;
	public static String Web_Root;

	/**
	 * 内部类，服务器的线程类
	 * 
	 * @author: John
	 * @Class: ThreadPool
	 * @date: 2015年12月30日
	 */
	static class ThreadPool implements Runnable {
		Socket socket;

		public ThreadPool(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			host = "localhost:" + iport;
			Application application = new Application();
			InputStream input = null;
			PrintStream output = null;
			try {
				input = socket.getInputStream();
				output = new PrintStream(socket.getOutputStream());
				Request request = new Request();
				request.setInput(input);
				request.parse();
				Response response = new Response(output);
				response.setOutput(output);
				application.setRequest(request);
				application.setResponse(response);
				application.getAdress();
			} catch (IOException e) {
				logger.error("服务器运行错误｛｝", e);
			} finally {
				try {
					socket.close();
				} catch (IOException e) {
				}
			}
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
		HttpServer server = new HttpServer();
		server.getConf();
		HttpServer.logger.info("web_root {} ", Web_Root);
		ServerSocket serverSocket = new ServerSocket(iport);
		ThreadPoolExecutor tpe = new ThreadPoolExecutor(10, 20, 20,
				TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(5));
		while (true) {
			Socket socket = serverSocket.accept();
			ThreadPool threadPool = new ThreadPool(socket);
			tpe.execute(threadPool);
		}
	}

}
