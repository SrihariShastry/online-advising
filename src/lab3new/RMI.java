//Name: Srihari Shasty
//UTA ID: 1001662267
package lab3new;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/*
 * Interface for RMI. Server implements this interface
 */
public interface RMI extends Remote{
	//	Store Requests to file
	public void storeMessages(ArrayList<RequestAndDecision> requestDecision) throws RemoteException;
	//	Retrieve requests from File
	public ArrayList<RequestAndDecision>getMessages() throws RemoteException;
	//	Notify the name of the client connected
	public void connectedClient(String client) throws RemoteException;
}
