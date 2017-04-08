import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;

import java.util.Random;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.MessageBox;

public class HW5View {
	HW5Controller controller;
	protected Shell shell;
	List lstOthers;
	List lstPendingRequests;
	List lstPendingFriends;
	List lstFriends;
	User _currentUser;
	
	HW5View(String currentUserName){
		controller = new HW5Controller();
		for (User u : controller.users) {
			if(u.username.equals(currentUserName)){
				_currentUser = u;

			}
		}
		
	}
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			HW5View window = new HW5View(args[0]);
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void refresh(){
		lstFriends.removeAll();
		lstPendingFriends.removeAll();
		lstPendingRequests.removeAll();
		lstOthers.removeAll();
		_currentUser.friends.sort(null);
		_currentUser.incomingRequests.sort(null);
		_currentUser.pendingRequests.sort(null);
		_currentUser.others.sort(null);
		for (Integer id : _currentUser.friends) {
			//lstFriends.add(id.toString());
			lstFriends.add(HW5Controller.getUserNameFromID(id, controller.users));
		}
		for (Integer id : _currentUser.incomingRequests) {
			lstPendingRequests.add(HW5Controller.getUserNameFromID(id, controller.users));
		}
		for (Integer id : _currentUser.pendingRequests) {
			lstPendingFriends.add(HW5Controller.getUserNameFromID(id, controller.users));
		}
		for (Integer id : _currentUser.others) {
			lstOthers.add(HW5Controller.getUserNameFromID(id, controller.users));
		}
	}
	public void open() {
		Display display = Display.getDefault();
		createContents();
		HW5Controller.getAspectsOfUser(_currentUser, controller.users);
		refresh();
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
		shell.setSize(665, 304);
		shell.setText("SWT Application");
		
		
		Label lblMyFriends = new Label(shell, SWT.NONE);
		lblMyFriends.setBounds(25, 33, 134, 20);
		lblMyFriends.setText("My Friends");
		
		Button btnRemoveFriend = new Button(shell, SWT.NONE);

		btnRemoveFriend.setBounds(25, 174, 134, 28);
		btnRemoveFriend.setText("Remove Friend");
		
		Label lblOthers = new Label(shell, SWT.NONE);
		lblOthers.setBounds(305, 33, 134, 20);
		lblOthers.setText("Not related");
		
		
		
		Button btnRequestFriend = new Button(shell, SWT.NONE);

		btnRequestFriend.setBounds(305, 174, 134, 28);
		btnRequestFriend.setText("Request Friend");
		

		Label lblFriendRequests = new Label(shell, SWT.NONE);
		lblFriendRequests.setBounds(165, 33, 134, 20);
		lblFriendRequests.setText("Pending Requests");
		
		

		
		 lstOthers = new List(shell, SWT.BORDER);
		lstOthers.setBounds(305, 53, 134, 115);
		
		 lstPendingRequests = new List(shell, SWT.BORDER);
		lstPendingRequests.setBounds(165, 53, 134, 115);
		
		 lstPendingFriends = new List(shell, SWT.BORDER);
		lstPendingFriends.setBounds(445, 53, 134, 115);
		
		 lstFriends = new List(shell, SWT.BORDER);
		lstFriends.setBounds(25, 53, 134, 115);
		
		Label lblPendingFriendRequests = new Label(shell, SWT.NONE);
		lblPendingFriendRequests.setText("Pending Friends");
		lblPendingFriendRequests.setBounds(445, 33, 134, 28);
		
		Button btnAcceptFriends = new Button(shell, SWT.NONE);

		btnAcceptFriends.setBounds(165, 174, 134, 28);
		btnAcceptFriends.setText("Accept Friends");
		
		
		Button btnGetRelation = new Button(shell, SWT.NONE);
		btnGetRelation.setBounds(305, 208, 134, 28);
		btnGetRelation.setText("Get Relation");
		
		Button btnDenyFriend = new Button(shell, SWT.NONE);
		btnDenyFriend.setBounds(165, 208, 134, 28);
		btnDenyFriend.setText("Reject Friend");
		
		btnAcceptFriends.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int j = lstPendingRequests.getSelectionIndex();
				if(j==-1){
					MessageBox m = new MessageBox(shell);
					m.setText("ERROR");
					m.setMessage("Please select a valid user");
					m.open();
					return;
				}
				String x = 	lstPendingRequests.getItem(j);
				int i = HW5Controller.getUserIDFromName(x, controller.users);
				controller.acceptFriend(i, _currentUser);
				refresh();
		//	controller.removeFriend(i);
			}
		});
		
		btnDenyFriend.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int j = lstPendingRequests.getSelectionIndex();
				if(j==-1){
					MessageBox m = new MessageBox(shell);
					m.setText("ERROR");
					m.setMessage("Please select a valid user");
					m.open();
					return;
				}
				String x = 	lstPendingRequests.getItem(j);
				int i = HW5Controller.getUserIDFromName(x, controller.users);
				controller.rejectFriend(i, _currentUser);
				refresh();
		//	controller.removeFriend(i);
			}
		});
		
		
		btnRequestFriend.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int j = lstOthers.getSelectionIndex();
				if(j==-1){
					MessageBox m = new MessageBox(shell);
					m.setText("ERROR");
					m.setMessage("Please select a valid user");
					m.open();
					return;
				}
				String x = 	lstOthers.getItem(j);
				int i = HW5Controller.getUserIDFromName(x, controller.users);
				controller.requestFriend(i, _currentUser);
				refresh();
			}
		});
		
		btnRemoveFriend.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int j = lstFriends.getSelectionIndex();
				if(j==-1){
					MessageBox m = new MessageBox(shell);
					m.setText("ERROR");
					m.setMessage("Please select a valid user");
					m.open();
					return;
				}
				String x = 	lstFriends.getItem(j);
				int i = HW5Controller.getUserIDFromName(x, controller.users);
				controller.removeFriend(i, _currentUser);
				refresh();
				
			}
		});
		
		

	}
	public boolean isFriend(String UserID){
		Random r = new Random();
		return r.nextBoolean();
	}
}
