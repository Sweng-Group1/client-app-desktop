package sweng.group.one.client_app_desktop.data;

import java.io.File;

public class Map {
	
	private String name;
	private File file;
	
	public Map(String name) {
		this.name = name;
	}
	
	public Map(String name, File file) {
		this.name = name;
		this.file = file;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
	
	

}
