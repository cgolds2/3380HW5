import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class HW4Model {
	ArrayList<userAndPsw> userCreds;

	public void refreshData(String filePath) throws FileNotFoundException {
		userCreds.clear();
		
		//gets the file
		File f = new File(filePath);
		Scanner s = new Scanner(f);
		//while it has next
		while (s.hasNext()) {
			
			String _username = "";
			_username = s.nextLine();
			//if its a comment or nothing, skip over the line
			if(_username.contains("//")|| _username.equals("")){
				continue;
			}
			//otherwise, check the password
			if (s.hasNext()) {
				String _password = s.next();
				if (!(_password.trim().equals(""))) {
					//if there is a password, then the username is valid, so add a credential
					userCreds.add(new userAndPsw(_username, _password));
				}

			}
		}
		s.close();
	}

	public HW4Model(String filePath) throws FileNotFoundException {
		//creats the new set of credentials
		userCreds = new ArrayList<userAndPsw>();
	
			//try to get the credentials from the file
			refreshData(filePath);
		
	}

	
	public static String getHashFromString(String s) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		//this gets the SHA-256 version of the string passed into it
		MessageDigest m = MessageDigest.getInstance("SHA-256");
		m.reset();
		//CPU must be using UTF-8 for this to work
		m.update(s.getBytes("UTF-8"));
		byte[] digest = m.digest();
		BigInteger bigInt = new BigInteger(1,digest);
		String hashtext = bigInt.toString(16);
		// Now we need to zero pad it if you actually want the full 32 chars.
		while(hashtext.length() < 64 ){
		  hashtext = "0"+hashtext;
		}
return hashtext;
	}
}

class userAndPsw {
	
	//simple class that holds each username and password
	
	private String _username = "";
	private String _password = "";

	public userAndPsw(String username, String password) {
		setPassword(password);
		setUsername(username);
	}

	public String getUsername() {
		return _username;
	}

	public void setUsername(String username) {
		this._username = username;
	}

	public String getPassword() {
		return _password;
	}

	public void setPassword(String password) {
		this._password = password;
	}
}
