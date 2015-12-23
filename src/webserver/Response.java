package webserver;



import java.io.IOException;
import java.io.OutputStream;

public class Response {
	//private static final int BUFFER_SIZE = 1024;
	//Request request;
	//OutputStream output;
    private  OutputStream output;
	/*public Response(OutputStream output) {
		this.output = output;
	}*/

	/*public void sendStaticResource() throws IOException {
		byte[] bytes = new byte[BUFFER_SIZE];
		FileInputStream fis = null;
		System.out.println(HttpServer.Web_Root);
		System.out.println(request.getUrl());
		File file = new File(HttpServer.Web_Root, request.getUrl());

		if (file.exists()) {
			fis = new FileInputStream(file);
			int ch = fis.read(bytes, 0, BUFFER_SIZE);
			while (ch != -1) {
				output.write(bytes, 0, BUFFER_SIZE);
				ch = fis.read(bytes, 0, BUFFER_SIZE);
			}
			if (fis != null) {
				fis.close();
			}
		} else {
			String errorMessage = "HTTP/1.1 404 File Not Found\r\n"
					+ "Content-type:text/html\r\n" + "Content-Length:23\r\n"
					+ "\r\n" + "File Not Found";
			output.write(errorMessage.getBytes());
			output.flush();

		}
	}*/

	/*public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}*/

	/*public OutputStream getOutput() {
		return output;
	}

	public void setOutput(OutputStream output) {
		this.output = output;
	}*/

	

public  void  getOutput(byte[] result) throws IOException {
		output.write(result);
	}

	public void setOutput(OutputStream output) {
		this.output = output;
	}

public Response(OutputStream output) {
//	super();
	this.output = output;
}
}
