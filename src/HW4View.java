import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;

import java.awt.Window;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Control;

public class HW4View {

	protected Shell shell;
	private Text txtUserName;
	private Text txtPassword;
	private HW4Controller controller;
	static String filePath;
	public HW4View(String fname) throws FileNotFoundException{
		controller = new HW4Controller(fname);
	}
	

	public static void main(String[] args) {
		try {
			//gets auth.txt
			filePath = Paths.get("auth.txt").toAbsolutePath().toString();
			File f = new File(filePath);
			//checks if auth.txt exists
			if(!(f.exists() && !f.isDirectory())) { 
				System.out.println("Could not find autorization file, please name a file \"auth.txt\" and put it next to the jar file");
			
			}else{
			//creates new gui window and opens it
			HW4View window = new HW4View(filePath);
			window.open();
			}
		} catch (FileNotFoundException e) {
			System.out.println("Could not find autorization file, please name a file \"auth.txt\" and put it next to the jar file");

		}
	}
	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(397, 204);
		shell.setText("3380 HW 4");
		
		//creates all of the elements for the GUI designer
		txtUserName = new Text(shell, SWT.BORDER);

		txtUserName.setBounds(75, 18, 164, 19);
		Button btnUsernameVerified = new Button(shell, SWT.CHECK);
		btnUsernameVerified.setEnabled(false);
		Button btnPasswordVerified = new Button(shell, SWT.CHECK);
		Label lblStatus = new Label(shell, SWT.NONE);


		Label lblUsername = new Label(shell, SWT.NONE);
		lblUsername.setBounds(10, 21, 59, 14);
		lblUsername.setText("Username");
		
		Label lblPassword = new Label(shell, SWT.NONE);
		lblPassword.setBounds(10, 46, 59, 14);
		lblPassword.setText("Password");
		
		txtPassword = new Text(shell, SWT.BORDER | SWT.PASSWORD);
		txtPassword.setBounds(75, 43, 164, 19);
		
		Button btnCheckUsername = new Button(shell, SWT.NONE);
		btnCheckUsername.setBounds(250, 14, 137, 28);
		btnCheckUsername.setText("Check Username");
		
		Button btnCheckPassword = new Button(shell, SWT.NONE);
		btnCheckPassword.setBounds(250, 39, 137, 28);
		btnCheckPassword.setText("Check Password");
		btnUsernameVerified.setBounds(24, 99, 15, 18);
		
		Label lblUsernameVerified = new Label(shell, SWT.NONE);
		lblUsernameVerified.setBounds(41, 100, 137, 14);
		lblUsernameVerified.setText("Username Verified");
		
		Label lblProgress = new Label(shell, SWT.NONE);
		lblProgress.setBounds(31, 78, 59, 14);
		lblProgress.setText("Progress");
		
		btnPasswordVerified.setEnabled(false);
		btnPasswordVerified.setBounds(24, 121, 15, 18);
		
		Label lblPasswordVerified = new Label(shell, SWT.NONE);
		lblPasswordVerified.setBounds(45, 122, 137, 14);
		lblPasswordVerified.setText("Password Verified");
		
		lblStatus.setBounds(24, 145, 353, 19);
		lblStatus.setText("Status = Currently Logged Out");
		
		btnCheckUsername.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//Username Check
				boolean didItWork = controller.doesUsernameExist(txtUserName.getText());
				//sets the username verified checkbox
				btnUsernameVerified.setSelection(didItWork);
				
				//creates a message box that tells the user if they were sucessful
				MessageBox m = new MessageBox(shell);
				m.setText((didItWork?"SUCCESS! ":"ERROR"));
				m.setMessage("The username" + (didItWork?" exists ":" does not exist ") + "in the database");
				m.open();
				
			}
		});

		btnCheckPassword.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//Password check
				
				//if the username was correct
				if(btnUsernameVerified.getSelection()){
						//asks the controller if the password matches the username
						boolean didItWork = controller.doesPasswordMatch(txtPassword.getText());
					//sets to the result
						btnPasswordVerified.setSelection(didItWork);
						lblStatus.setText("Status = Currently Logged" + (didItWork?" In As User:" + txtUserName.getText():" Out"));
						
						//creates a message box telling the user the result
						MessageBox m = new MessageBox(shell);
						m.setText((didItWork?"SUCCESS! ":"ERROR"));
						m.setMessage("The password " + (didItWork?"matches ":" does not match ") + "the correct password");
						m.open();
						if(didItWork){
							HW5View mainwindow = new HW5View(txtUserName.getText());
							mainwindow.open();
					
							//shell.close();
						}
						
				}else{
					//if username not checked, alerts user
					MessageBox m = new MessageBox(shell);
					m.setText("ERROR");
					m.setMessage("The username must be verified first");
					m.open();
				}
			
			}
		});
		
		txtUserName.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				//User text was modified, must invalidate and recheck username/password
			btnUsernameVerified.setSelection(false);
			btnPasswordVerified.setSelection(false);
			lblStatus.setText("Status: Currently Logged Out");

			}
		});
		
		
		/*
		 * This adds an optional element.  What this form does is creates a way to add and 
		*	remove usernames and passwords from the auth.txt file easily
		**/
		Button btnUserCreator = new Button(shell, SWT.NONE);
		btnUserCreator.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				UserCreator uWindow = new UserCreator(filePath);
				uWindow.open();

			}
		});
		btnUserCreator.setBounds(250, 114,137, 50);
		btnUserCreator.setText("User Creator");
		
		//this is the refresh button the users can click after modifying the text file
		//the file is not refreshed automatically for efficiency 
		Button btnRefresh = new Button(shell, SWT.NONE);
		btnRefresh.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				controller.refreshData(filePath);
			}
		});
		btnRefresh.setBounds(250, 89, 94, 28);
		btnRefresh.setText("Refresh");
		shell.setTabList(new Control[]{txtUserName, btnCheckUsername, txtPassword, btnCheckPassword});

	}
}
