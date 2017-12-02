package londonmet.cs.rmi.credit;


import java.rmi.Remote;
import java.rmi.RemoteException;


public interface CreditChecker extends Remote {

    public static final String NAME = "creditChecker";

    public Person deposit(Person person, double amount)
            throws RemoteException;

    public Person withdrawal(Person person, double amount)
            throws RemoteException;

    public boolean isOkay(Person person)
            throws RemoteException;
}
