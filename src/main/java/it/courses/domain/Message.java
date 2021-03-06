/**
 * 
 */
package it.courses.domain;

/**
 * @author fvaleri
 * 
 */
public class Message {

	private String code;
	private String text;

	public Message(String text) {
		this.text = text;
	}

	public Message(String code, String text) {
		this.code = code;
		this.text = text;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
