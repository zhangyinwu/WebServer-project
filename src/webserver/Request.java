package webserver;

import java.io.IOException;
import java.io.InputStream;

public class Request {
	private InputStream input;
	private String url;
	public Request(){
		
	}

	public Request(InputStream input) {
    this.input = input;
	}

	public String parse() throws IOException {
		StringBuffer request = new StringBuffer(2048);
		int i;
		byte[] buffer = new byte[2048];
		i = input.read(buffer);
		for (int j = 0; j < i; j++) {
			request.append((char) buffer[j]);
		}
		System.out.println(request.toString());
		url = parseUrl(request.toString());
		return url;
	}

	private String parseUrl(String requestString) {
		int index1 = requestString.indexOf(" ");
		int index2;
		if (index1 != -1) {
			index2 = requestString.indexOf(" ", index1 + 1);
			if (index2 > index1) {
				return requestString.substring(index1 + 2, index2);
			}
		}
		return null;
	}

	
}
