import java.util.ArrayList;

public class User implements Comparable<User>{
	String username;
	int id;
	ArrayList<Integer> friends;
	ArrayList<Integer> pendingRequests;
	ArrayList<Integer> incomingRequests;
	ArrayList<Integer> others;

	public User() {
		friends = new ArrayList<>();
		pendingRequests = new ArrayList<>();
		incomingRequests = new ArrayList<>();
		others = new ArrayList<>();	
		}

	@Override
	public int compareTo(User o) {
		// TODO Auto-generated method stub
		return Integer.compare(id, o.id);
	}	
		
	}


