package webserver;

import java.io.IOException;
import java.io.InputStream;

/**
 * 接收客户端的http请求，并截取出URI
 * 
 * @author: John
 * @Class: Request
 * @date: 2015年12月25日
 */
public class Request {
	private InputStream input;
	private String url;

	/**
	 * 截取http请求中的uri
	 * 
	 * @author:John
	 * @return:String
	 * @date: 2015年12月25日
	 */
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

	/**
	 * 截取http请求中uri
	 * 
	 * @author:John
	 * @return:String
	 * @date: 2015年12月25日
	 */
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

	public InputStream getInput() {
		return input;
	}

	public void setInput(InputStream input) {
		this.input = input;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
