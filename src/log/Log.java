package log;

public class Log {
	
	private String className;
	
	private int logLevel;
	
	//constructors
	public Log(String className, int logLevel) {
		this.className = className;
		this.logLevel = logLevel;
	}
	
	public Log(String className, LogLevel logLevel) {
		this.className = className;
		this.logLevel = logLevel.getValue();
	}
	
	//getters and setters
	public int getLogLevel() {
		return logLevel;
	}

	public void setLogLevel(int logLevel) {
		this.logLevel = logLevel;
	}

	public void setLogLevel(LogLevel logLevel) {
		this.logLevel = logLevel.getValue();
	}

	public String getClassName() {
		return className;
	}

	//log methods
	public void logRawMessage(String rawMessage, int logLevel) {
		if(this.logLevel <= logLevel) {
			System.out.println(rawMessage);
		}
	}
	
	public void logRawMessage(String rawMessage, LogLevel logLevel) {
		if(this.logLevel <= logLevel.getValue()) {
			System.out.println(rawMessage);
		}
	}
	
	public void logMessage(String message, int logLevel) {
		logRawMessage("[" + className + ": " + logLevel + "] -> " + message, logLevel);
	}
	
	public void logMessage(String message, LogLevel logLevel) {
		logRawMessage("[" + className + ": " + logLevel.name() + "] -> " + message, logLevel);
	}
	
	public void logMessage(String message, int logValue, LogLevel logLevel) {
		logRawMessage("[" + className + ": " + logLevel.name() + "] -> " + message, logValue);
	}
	
	public void info(String message) {
		logMessage(message, LogLevel.INFO);
	}
	
	public void warn(String message) {
		logMessage(message, LogLevel.WARN);
	}
	
	public void error(String message) {
		logMessage(message, LogLevel.ERROR);
	}
	
	public void fatal(String message) {
		logMessage(message, LogLevel.FATAL);
	}
	
	public void info(String message, int logValue) {
		logMessage(message, logValue, LogLevel.INFO);
	}
	
	public void warn(String message, int logValue) {
		logMessage(message, logValue, LogLevel.WARN);
	}
	
	public void error(String message, int logValue) {
		logMessage(message, logValue, LogLevel.ERROR);
	}
	
	public void fatal(String message, int logValue) {
		logMessage(message, logValue, LogLevel.FATAL);
	}

}