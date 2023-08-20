package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class FileManager {

	public static void writeDataFile(ArrayList<Match> matches) {
		
		File file = new File("data.dat");
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			FileOutputStream fos = new FileOutputStream(file.getPath(),false);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			for(Match match : matches) {
				oos.writeObject(match);
			}
			oos.flush();
			oos.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public static ArrayList<Match> readDataFile() {
		
		ArrayList<Match> matches = new ArrayList<>();
		
		try {
			FileInputStream fis = new FileInputStream("data.dat");
			ObjectInputStream ois = new ObjectInputStream(fis); 
			boolean emptyFile = false;
			Match match = null;
			while(!emptyFile) {
				match = (Match) ois.readObject();
				if(match != null)
					matches.add(match);
				else
					emptyFile = true;
			}
			ois.close();
			
		} catch (IOException ex) {
			//EOF
		} catch ( ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		
		return matches;
		
	}
	
	public static void writeUserData(User user) {
		
		File file = new File("user_data.dat");

		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			FileOutputStream fos = new FileOutputStream(file.getPath(),false);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(user);
			oos.flush();
			oos.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
	}
	
	public static User readUserData() {
		
		User user = null;
		
		try {
			FileInputStream fis = new FileInputStream("user_data.dat");
			ObjectInputStream ois = new ObjectInputStream(fis); 
			user = (User) ois.readObject();
			ois.close();
			
		} catch (IOException ex) {
			//EOF
		} catch ( ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		
		return user;
	}
}
