import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.eclipse.ui.progress.PendingUpdateAdapter;

public class HW5Controller {
	ArrayList<User> users;
	ArrayList<String> preInfo;

	public HW5Controller() {
		try {
			loadUsers();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void loadUsers() throws FileNotFoundException {
		users = HW5Data.loadFromData();
	}

	public void acceptFriend(int id, User currentUser) {
		
		currentUser.friends.add(id);
		
		for (int i = 0; i < currentUser.incomingRequests.size(); i++) {
			if(currentUser.incomingRequests.get(i) == id){
				currentUser.incomingRequests.remove(i);
			}
		}

		for (User user : users) {
			System.out.println(id);
			System.out.println(user.id);
			if (user.id == id) {
				// add to his friends list too
				user.friends.add(currentUser.id);

//				for (int i = 0; i < user.pendingRequests.size(); i++) {
//					if (user.pendingRequests.get(i) == currentUser.id) {
//						user.pendingRequests.remove(i);
//					}
//				}
			}
		}
		HW5Data.writeOverDataFile(users);

	}
	
	public void rejectFriend(int id, User currentUser){
		for (int i = 0; i < currentUser.incomingRequests.size(); i++) {
			if(currentUser.incomingRequests.get(i) == id){
				currentUser.incomingRequests.remove(i);
			}
		}
		HW5Data.writeOverDataFile(users);

	}

	public void requestFriend(int id, User currentUser){
		for (int i = 0; i < currentUser.others.size(); i++) {
			if(currentUser.others.get(i) == id){
				currentUser.others.remove(i);
			}
		}
		for (User user : users) {
			if(user.id == id){
				user.incomingRequests.add(currentUser.id);
			}
		}
		currentUser.pendingRequests.add(id);
		HW5Data.writeOverDataFile(users);

	}
	
	public void removeFriend(int id, User currentUser) {
		for (User user : users) {
			if (user.id == id) {
				// clear his friends list too
				for (int i = 0; i < user.friends.size(); i++) {
					if (user.friends.get(i) == currentUser.id) {
						user.friends.remove(i);
					}
				}
			} else if (user == currentUser) {
				// remove from current user friends
				for (int i = 0; i < currentUser.friends.size(); i++) {
					if (currentUser.friends.get(i) == id) {
						currentUser.friends.remove(i);
					}
				}

			}
		}
		currentUser.others.add(id);
		HW5Data.writeOverDataFile(users);
	}

	public static void getAspectsOfUser(User user, ArrayList<User> others) {

		for (User user2 : others) {
			if (user2 == user) {
				continue;
			}
			if (user2.incomingRequests.contains(user.id)) {
				user.pendingRequests.add(user2.id);
			} else if (!(user.friends.contains(user2.id) || user.incomingRequests.contains(user2.id))) {
				user.others.add(user2.id);
			}
		}

	}

	public static String getUserNameFromID(int ID, ArrayList<User> users) {
		for (User user : users) {
			if(user.id == ID){
				return user.username;
			}
		}

		return "";
	}
	public static int getUserIDFromName(String name, ArrayList<User> users) {
		for (User user : users) {
			if(user.username.equals(name)){
				return user.id;
			}
		}
		return -1;
	}


}
