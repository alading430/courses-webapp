/**
 * 
 */
package it.courses.domain;

/**
 * @author fvaleri
 * 
 */
public class PageRequest {

	private String direction;
	private Long lastSeen;
	private Long size;
	
	public PageRequest() {
	}
	
	public PageRequest(String command) {
		String[] split = command.split("-");
		this.direction = split[0];
		this.lastSeen = new Long(split[1]);
		this.size = 5L;
	}
	
	public PageRequest(String direction, Long lastSeen) {
		this.direction = direction;
		this.lastSeen = lastSeen;
		this.size = 5L;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}
	
	public Long getLastSeen() {
		return lastSeen;
	}

	public void setLastSeen(Long lastSeen) {
		this.lastSeen = lastSeen;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

}
