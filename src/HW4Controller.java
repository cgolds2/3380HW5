import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class HW4Controller {
	HW4Model model ;
	private int _usernameIndex=-1;
	public HW4Controller(String filename) throws FileNotFoundException{
		model = new HW4Model(filename);
		
	}
	
	public void refreshData(String filename){
		try {
			model.refreshData(filename);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getIndexOfUsername(String username){
		//gets the index of the username, to match to the password later
		_usernameIndex =  -1;
		for (int i = 0; i < model.userCreds.size(); i++) {
			if(username.equals(model.userCreds.get(i).getUsername())){
				_usernameIndex = i;
			}
		}

	}
	
	public boolean doesUsernameExist(String username){
		getIndexOfUsername(username);
		//_usernameIndex is a public variable, if its -1 then the username does not exist
		//otherwise it will hold an index value
		return (_usernameIndex != -1);

	}
	public boolean doesPasswordMatch(String password){
		try {
			//gets SHA-256 version of password
			password = HW4Model.getHashFromString(password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return false;
		} 
		//if the username does not exist, return -1
		if(_usernameIndex==-1){
			return false;
		}
		//otherwise return whether or not the password is a match
		return model.userCreds.get(_usernameIndex).getPassword().equals(password);
	}
}
