import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Paths;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class UserCreator {

	protected Shell shell;
	private Text txtUsername;
	private Text txtPassword;
private static String filename;
public UserCreator(String f){
	filename = f;
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
		shell.setSize(367, 198);
		shell.setText("User Creator");
		
		Button btnIncludeCommentWith = new Button(shell, SWT.CHECK);
		btnIncludeCommentWith.setBounds(88, 124, 269, 18);
		btnIncludeCommentWith.setText("Include Comment With Plaintext Password");
		
		txtUsername = new Text(shell, SWT.BORDER);
		txtUsername.setBounds(88, 32, 217, 19);
		
		txtPassword = new Text(shell, SWT.BORDER);
		txtPassword.setBounds(88, 57, 217, 19);
		
		Label lblUsername = new Label(shell, SWT.NONE);
		lblUsername.setBounds(23, 35, 59, 14);
		lblUsername.setText("Username");
		
		Label lblPassword = new Label(shell, SWT.NONE);
		lblPassword.setBounds(23, 60, 59, 14);
		lblPassword.setText("Password");
		
		Button btnCreateUser = new Button(shell, SWT.NONE);
		btnCreateUser.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(txtPassword.getText().equals("") || txtUsername.getText().equals("")){
					MessageBox m = new MessageBox(shell);
					m.setText("Fail");
					m.setMessage("Please include valid username and password");
					m.open();
				}else{
					try {
						
						try(FileWriter fw = new FileWriter(filename, true);
							    BufferedWriter bw = new BufferedWriter(fw);
							    PrintWriter out = new PrintWriter(bw))
							{
							out.println();
							if(btnIncludeCommentWith.getSelection()){
								out.println("//password = \"" + txtPassword.getText() + "\"");
							}
							out.println(txtUsername.getText());

							out.print(HW4Model.getHashFromString(txtPassword.getText()));
							out.close();
							MessageBox m = new MessageBox(shell);
							m.setText("Success");
							m.setMessage("User Added, click refresh on previous screen to update the list");
							HW5Data.createNewUser(txtUsername.getText());
							m.open();
						} catch (Exception iOE) {
							System.out.println(iOE.getMessage());
						    //exception handling left as an exercise for the reader
							MessageBox m = new MessageBox(shell);
							m.setText("Fail");
							m.setMessage("Something went wrong, user not created");
							
							m.open();
						} 
					}finally{
					}
				}
			}
		});
		btnCreateUser.setBounds(88, 82, 200, 28);
		btnCreateUser.setText("Create User");
		


	}
}
