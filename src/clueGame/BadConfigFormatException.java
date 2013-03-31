package clueGame;

public class BadConfigFormatException extends Exception {
	private String name;
	public BadConfigFormatException(String name) {
		super();
		this.name = name;
	}
	
	public String toString() {
		return "There was an invalid format in " + name;
	}

}
