//Name: Srihari Shasty
//UTA ID: 1001662267
package lab3new;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/*
 * Advisor process decides on the student requests for courses.
 * 
 */
public class Advisor {
	
	private static RMI rmi;
	
	public static int adviseStudent() throws InterruptedException, RemoteException {
//		get all requests from the file from the server
		ArrayList<RequestAndDecision> studentRequests = rmi.getMessages();
		ArrayList<RequestAndDecision> advisorDecisions = new ArrayList<>();
		Iterator<RequestAndDecision> it = studentRequests.iterator();
		RequestAndDecision request = new RequestAndDecision();
		while(it.hasNext()) {
			request = it.next();
			if(request.getDecision().equals("")) {
				request.setDecision(new Random().nextBoolean()? "Appproved":"Disapproved");
				advisorDecisions.add(request);
			}
			else {
				advisorDecisions.add(request);
			}
		}
		rmi.storeMessages(advisorDecisions);
//		wait for 3 seconds and check again for new requests
		TimeUnit.SECONDS.sleep(3);
		return adviseStudent();
	}
	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException, InterruptedException {
//		connect to server 
		rmi = (RMI)Naming.lookup("mqueue");
//		notify the advisor connected
		rmi.connectedClient("Advisor");
		adviseStudent();
	}

}
