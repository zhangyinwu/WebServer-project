package webserver;

import java.io.IOException;
import java.io.PrintStream;

/**
 * 将服务器的流返回给客户端
 * 
 * @author: John
 * @Class: Response
 * @date: 2015年12月25日
 */
public class Response {

	private PrintStream output;
	public void getOutput(String result){
		output.println(result);
	}

	public void getOutput(byte[] result) throws IOException {
		output.write(result);
	}

	public void setOutput(PrintStream output) {
		this.output = output;
	}

	public Response(PrintStream output) {
		// super();
		this.output = output;
	}
}
