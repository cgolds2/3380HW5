import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class HW5Data {
	public static ArrayList<User> loadFromData() throws FileNotFoundException {
		ArrayList<User> users = new ArrayList<>();

		// gets the file
		File f = new File(Paths.get("data.txt").toAbsolutePath().toString());
		Scanner s = new Scanner(f);
		boolean data = false;
		// while it has next
		while (s.hasNext()) {
			s.useDelimiter("\n");
			String _username = "";
			_username = s.next();
			// if its a comment or nothing, skip over the line
			if (!data) {

				if (_username.contains("--Mark Data--")) {
					data = true;
					continue;
				}
				continue;
			}
			if (_username.contains("//") || _username.equals("")) {
				continue;
			}

			// otherwise, valid username
			User u = new User();
			u.username = _username;
			// s.next();
			u.id = s.nextInt();
			// s.next();

			String friends = s.next();
			String requests = s.next();
			String[] farr = friends.split(" ");
			String[] rarr = requests.split(" ");

			u.friends.clear();
			u.incomingRequests.clear();

			for (String string : farr) {
				if(string.equals("")){continue;}
				u.friends.add(Integer.parseInt(string));
			}

			for (String string : rarr) {
				if(string.equals("")){continue;}
				u.incomingRequests.add(Integer.parseInt(string));
			}

			users.add(u);
		}
		s.close();
		return users;
	}

	public static void createNewUser(String name){
		try {
			ArrayList<User> users = loadFromData();
			User newU = new User();
			newU.username = name;
			newU.id = users.size();
			users.add(newU);
			writeOverDataFile(users);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void writeOverDataFile(ArrayList<User> users) {
		users.sort(null);
		ArrayList<String> preInfo = new ArrayList<>();
		File f = new File(Paths.get("data.txt").toAbsolutePath().toString());
		Scanner s;
		try {
			s = new Scanner(f);

			boolean data = false;
			// while it has next
			while (s.hasNext()) {
				s.useDelimiter("\n");
				String _line = "";
				_line = s.next();
				// if its a comment or nothing, skip over the line
				preInfo.add(_line);
				if (_line.contains("--Mark Data--")) {
					break;
				}
			}
			// now we have all the pre files
			try (FileWriter fw = new FileWriter(f, false);
					BufferedWriter bw = new BufferedWriter(fw);
					PrintWriter out = new PrintWriter(bw)) {
				for (String string : preInfo) {
					out.println(string);
				}
				for (User user : users) {
					out.println(user.username);
					out.println(user.id);
					for (int i : user.friends) {
						out.print(i + " ");
					}
					out.println();

					for (int i : user.incomingRequests) {
						out.print(i + " ");
					}
					out.println();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
