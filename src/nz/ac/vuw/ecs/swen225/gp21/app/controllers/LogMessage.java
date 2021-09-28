package nz.ac.vuw.ecs.swen225.gp21.app.controllers;

/**
 * A LogMessage is a wrapper for a String message.
 * Each LogMessage contains a flag saying whether it is a warning message or not.
 * @author Sam
 *
 */
public final class LogMessage {
	
	/**
	 * The message.
	 */
	public final String msg;
	
	/**
	 * If this message is a warning message or not.
	 */
	public final boolean isWarning;
	
	/**
	 * Construct a LogMessage.
	 * @param msg : the message
	 * @param isWarning : whether this message is warning or not.
	 */
	public LogMessage(String msg, boolean isWarning) {
		this.msg = msg;
		this.isWarning = isWarning;
	}
	
}
