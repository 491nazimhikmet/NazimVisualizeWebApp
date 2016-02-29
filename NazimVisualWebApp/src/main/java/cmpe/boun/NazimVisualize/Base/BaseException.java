package cmpe.boun.NazimVisualize.Base;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.slf4j.LoggerFactory;

public class BaseException extends Exception{
	private final static org.slf4j.Logger logger = LoggerFactory.getLogger(BaseException.class);
	public BaseException() { super(); }
	public BaseException(String message) { 
		super(message);
		logger.error(message);
		}
	public BaseException(String message, Exception e){
		super(message);
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		String exceptionAsString = sw.toString();
		logger.error(message);
		logger.error(exceptionAsString);
	}
	public BaseException(String message, Throwable cause) { super(message, cause); }
	public BaseException(Throwable cause) { super(cause); }

}
