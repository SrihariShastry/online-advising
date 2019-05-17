//Name: Srihari Shasty
//UTA ID: 1001662267
package lab3new;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/*
 * Server Class handles all Client requests
 * Requests are written into a .ser file.
 * Implements RMI(Remote Method Invocation) Interface
 */
public class Server extends UnicastRemoteObject implements RMI{
	//File to store all Requests
	String messageFile = "studentRequests.ser";																		
	private static final long serialVersionUID = 1L;
	protected Server() throws RemoteException {
		super();
		Registry registry= LocateRegistry.createRegistry(1099);													
		registry.rebind("mqueue", this);											
	}

	@Override
	public void storeMessages(ArrayList<RequestAndDecision> requestDecision) {

		//	Storing all requests onto the file
		FileOutputStream outStream;
		ObjectOutputStream objOutStream;
		try {
			outStream = new FileOutputStream(messageFile);
			objOutStream = new ObjectOutputStream(outStream);
			//	Writing object onto the file
			objOutStream.writeObject(requestDecision);
			outStream.close();
			objOutStream.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	//	Printing out who connected to the server
	@Override
	public void connectedClient(String client) {
		System.out.println("Connected: "+ client);
	}

	//	Fetch requests posted by students 
	@Override
	public ArrayList<RequestAndDecision> getMessages() {
		FileInputStream fileInputStream;
		ObjectInputStream objectInputStream;

		ArrayList<RequestAndDecision> messages = new ArrayList<RequestAndDecision>();
		try {
			fileInputStream = new FileInputStream(messageFile);
			objectInputStream = new ObjectInputStream(fileInputStream);
			messages = (ArrayList<RequestAndDecision>) objectInputStream.readObject();

		}catch(Exception e) {
			e.printStackTrace();
		}
		return messages;
	}

	// Start the server and notify the user that the server is on
	public static void main(String[] args){
		try {
			new Server();																	
			System.err.println("Server ready");

		}catch (Exception e) {
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		}
	}


}