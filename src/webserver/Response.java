package webserver;



import java.io.IOException;
import java.io.OutputStream;

public class Response {
	
    private  OutputStream output;

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
