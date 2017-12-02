package londonmet.cs.rmi.credit.server.impl;

import londonmet.cs.rmi.credit.CreditChecker;
import londonmet.cs.rmi.credit.Person;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class CreditCheckerImpl extends UnicastRemoteObject
        implements CreditChecker {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public CreditCheckerImpl() throws RemoteException {
    }

    public boolean isOkay(Person person)
            throws RemoteException {
        return person.getCreditRating();
    }

}
