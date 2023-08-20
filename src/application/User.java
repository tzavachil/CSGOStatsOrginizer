package application;

import java.io.File;
import java.io.Serializable;

public class User implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String name;
	private String profileLink;
	private File file;
	
	public User(String name, String profileLink, File file) {
		this.name = name;
		this.profileLink = profileLink;
		this.file = file;
	}
	
	public String toString() {
		return this.name + "|" + this.profileLink + "|" + this.file.getName();
	}

	//Getters and Setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProfileLink() {
		return profileLink;
	}

	public void setProfileLink(String profileLink) {
		this.profileLink = profileLink;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
	
}
