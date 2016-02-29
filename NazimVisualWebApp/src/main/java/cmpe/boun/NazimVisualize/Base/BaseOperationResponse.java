package cmpe.boun.NazimVisualize.Base;

public class BaseOperationResponse {
	private String message;
	private boolean isSuccess;
	
	public BaseOperationResponse(){
		isSuccess = false;
		message = "";
	}
	
	public BaseOperationResponse(String message, boolean isSuccess){
		this.message = message;
		this.isSuccess = isSuccess;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	
	
}
