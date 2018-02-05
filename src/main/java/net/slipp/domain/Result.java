package net.slipp.domain;

public class Result {
	
	private boolean valid;
	
	private String errorMessage;
	
	private Result(){
		this(true, null);
	}
	
	private Result(boolean valid, String errorMessage){
		this.valid = valid;
		this.errorMessage = errorMessage;
	}
	
	
	//추가 해서 현제 상태를 얻어 오고
	public boolean isValid() {
		return valid;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	
	public static Result ok(){
		return new Result();
	}
	
	public static Result fail(String errorMessage) {
		return new Result(false, errorMessage);
	}
	

}
