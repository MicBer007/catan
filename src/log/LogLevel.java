package log;

public enum LogLevel {
	
INFO, WARN, ERROR, FATAL;
	
	private int[] logValues = new int[] {4, 6, 8, 10};
	
	public Integer getValue() {
		return logValues[ordinal()];
	}

}
