package webserver;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;

import org.slf4j.LoggerFactory;

/**
 * 接收客户端的http请求，并截取出URI
 * 
 * @author: John
 * @Class: Request
 * @date: 2015年12月25日
 */
public class Request {
	private org.slf4j.Logger logger=LoggerFactory.getLogger(Request.class);
	private InputStream input;
	private String url;
	private String requestString;
	
	public String getRequest() {
		return requestString;
	}

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
		requestString=request.toString();
		//String range=getRange(requestString);
		logger.info("用户请求{}",request.toString());
		url = parseUrl(request.toString());
		url = URLDecoder.decode(url, "utf-8");
		logger.info("uri{}",url);
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
		int index1 = requestString.indexOf(' ');
		int index2;
		if (index1 != -1) {
			index2 = requestString.indexOf(' ', index1 + 1);
			if (index2 > index1) {
				return requestString.substring(index1 + 2, index2);
			}
		}
		return null;
	}
	/**
	 * 截取报头中的range属性的值
	 * @author:John
	 * @return:String
	 * @date: 2016年1月5日
	 */
	private String getRange(String request){
		requestString=request;
		int index1 = request.indexOf('=');
		int index2 = request.indexOf("\r", index1);
		logger.info("index1 {} index2 {}",index1, index2);
		String range = request.substring(index1+1, index2);
		logger.info("range {}", range);
		return range;
	}
	/**
	 * 截取range属性值中的开始字节值
	 * @author:John
	 * @return:int
	 * @date: 2016年1月5日
	 */
	public int startRange(){
		String range =getRange(requestString);
		logger .info("startRange {} "+range.substring(0,range.indexOf("-")));	
		return Integer.parseInt(range.substring(0,range.indexOf('-')));
	}
	/**
	 *截取range属性值中的结束字节值 
	 * @author:John
	 * @return:int
	 * @date: 2016年1月5日
	 */
	public int endRange(){
		String range =getRange(requestString);
		String endrange = range.substring(range.indexOf('-')+1);
		logger.info("endRange {} "+endrange);
		return Integer.parseInt(endrange);
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
