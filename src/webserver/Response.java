package webserver;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 将服务器的流返回给客户端
 * 
 * @author: John
 * @Class: Response
 * @date: 2015年12月25日
 */
public class Response {

	private OutputStream output;

	public void getOutput(byte[] result) throws IOException {
		output.write(result);
	}

	public void setOutput(OutputStream output) {
		this.output = output;
	}

	public Response(OutputStream output) {
		// super();
		this.output = output;
	}
}
