//Name: Srihari Shasty
//UTA ID: 1001662267
package lab3new;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.LayoutStyle.ComponentPlacement;


import lab3new.RMI;
import lab3new.RequestAndDecision;

public class StudentGUI implements ActionListener, WindowListener{

	private JFrame frame;
	private JTextField txtFieldStudentName;
	private JTextField txtFieldCourseName;
	private JButton btnSubmitQuery;
	private static RMI rmiLookUp;
	private static StudentGUI window;

	/**
	 * Launch the application.
	 * @throws NotBoundException 
	 * @throws RemoteException 
	 * @throws MalformedURLException 
	 */

	/*
	 * Main Method. First method which runs at the start of the program. 
	 * We connect to the server and start sending requests for approval
	 * UI is inflated here
	 */
	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//					Look up the MQS server 
					rmiLookUp = (RMI) Naming.lookup("mqueue");
					//					Notify that Student connected 
					rmiLookUp.connectedClient("Student");
					window = new StudentGUI();
					window.frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public StudentGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {frame = new JFrame();
	frame.setBounds(100, 100, 424, 268);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	JLabel lblStudentQuery = new JLabel("Student Query");
	lblStudentQuery.setHorizontalAlignment(SwingConstants.CENTER);

	JLabel lblStudentName = new JLabel("Student Name");

	JLabel lblCourseName = new JLabel("Course Name\r\n");

	txtFieldStudentName = new JTextField();
	txtFieldStudentName.setToolTipText("Student Name");
	txtFieldStudentName.setColumns(10);

	txtFieldCourseName = new JTextField();
	txtFieldCourseName.setToolTipText("Course name\r\n");
	txtFieldCourseName.setColumns(10);

	btnSubmitQuery = new JButton("Submit Query\r\n");
	btnSubmitQuery.addActionListener(this);
	GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
	groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
			.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addGroup(groupLayout.createSequentialGroup()
									.addGap(29)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
											.addGroup(groupLayout.createSequentialGroup()
													.addComponent(lblStudentName)
													.addGap(18)
													.addComponent(txtFieldStudentName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
													.addPreferredGap(ComponentPlacement.RELATED, 365, Short.MAX_VALUE))
											.addGroup(groupLayout.createSequentialGroup()
													.addComponent(lblCourseName, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
													.addGap(18)
													.addComponent(txtFieldCourseName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
							.addGroup(groupLayout.createSequentialGroup()
									.addGap(82)
									.addComponent(btnSubmitQuery))
							.addGroup(groupLayout.createSequentialGroup()
									.addContainerGap()
									.addComponent(lblStudentQuery, GroupLayout.DEFAULT_SIZE, 382, Short.MAX_VALUE)))
					.addContainerGap())
			);
	groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
			.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblStudentQuery)
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblStudentName)
							.addComponent(txtFieldStudentName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblCourseName)
							.addComponent(txtFieldCourseName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(btnSubmitQuery)
					.addContainerGap(66, Short.MAX_VALUE))
			);
	frame.getContentPane().setLayout(groupLayout);

	}

	/*
	 * Handling button clicks
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(btnSubmitQuery)) {

			final String studentName = txtFieldStudentName.getText();
			final String courseName = txtFieldCourseName.getText();

			if(!studentName.isEmpty()&&!courseName.isEmpty()) {

				RequestAndDecision req = new RequestAndDecision();
				req.setRequest(studentName, courseName);			

				//	Add Request to the existing requests and store it onto the file				
				try {
					// read the existing requests
					ArrayList<RequestAndDecision> messageQueue = rmiLookUp.getMessages();
					RequestAndDecision query = new RequestAndDecision();
					query.setRequest(studentName, courseName);
					messageQueue.add(query);
					// store the request onto the file for advisor to see
					rmiLookUp.storeMessages(messageQueue);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
				txtFieldStudentName.setText("");
				txtFieldCourseName.setText("");
			}
			else {
//				if student name or course name is empty, ask user to enter Student name and course name
				JOptionPane.showMessageDialog(new JFrame(), "Student name and course name are mandatory", "Dialog",
						JOptionPane.ERROR_MESSAGE);
			}

		}

	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent e) {
		System.out.println("Student Disconnected");		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		System.out.println("Student Disconnected");

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

}

















