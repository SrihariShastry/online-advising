//Name: Srihari Shasty
//UTA ID: 1001662267
package lab3new;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

/*
 * Notification process notifies the student about the Advisor's decision about the request
 * 
 */
public class Notification {

	private static RMI rmiLookUp;
	
	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException, InterruptedException {
		rmiLookUp = (RMI) Naming.lookup("mqueue");
		rmiLookUp.connectedClient("Notification Process");
		new Notification();
	}

	public Notification() throws RemoteException, InterruptedException {
		getNotification();
	}
	public int getNotification() throws InterruptedException, RemoteException {
		ArrayList<RequestAndDecision> newData = rmiLookUp.getMessages();

//		iterate through the student requests and see if any of the requests were decided by the advisors
		Iterator<RequestAndDecision> rmiIterator = newData.iterator();
		while(rmiIterator.hasNext()) {
			RequestAndDecision notif = rmiIterator.next();
			if(notif.getDecision().equals("")) {
				String decision = "\nCourse: "+ notif.getCourse() + "\n"+ 
						"Requested By: "+
						"Student Name: "+ notif.getStudentName()+ "\n" ;
				System.out.println(decision);
				newData.add(notif);
			}else if(!notif.getDecision().equals("")) {
				String decision = "\nCourse: "+ notif.getCourse() + "\n"+ 
						"Student Name: "+ notif.getStudentName()+ "\n" +
						"Advisor Decision: "+ notif.getDecision()+ "\n";
//				remove request from queue if the decision was made
				rmiIterator.remove();
				System.out.println(decision);
			}
			rmiLookUp.storeMessages(newData);
		}

		//		Store the queries which have not been viewed by the advisor process
		if(newData.isEmpty()==false)
			rmiLookUp.storeMessages(newData);

//		wait for 7 seconds and look for new decisions again
		TimeUnit.SECONDS.sleep(7);
		return getNotification();

	}
}
