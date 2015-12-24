package webserver;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class HttpServer {
	public static String host;
	public static String Web_Root = System.getProperty("user.dir")+File.separator;
	public void await(String ihost, int iport) throws UnknownHostException,
			IOException {
		host="localhost:"+iport;
		ServerSocket serverSocket = null;
		serverSocket = new ServerSocket(iport, 1, InetAddress.getByName(ihost));
		while (true) {
			Application application=new Application();
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
	public static void main(String[] args) throws UnknownHostException,
			IOException {
		System.out.println(Web_Root);
		HttpServer server = new HttpServer();
		server.await("127.0.0.1", 8080);
	}

}
