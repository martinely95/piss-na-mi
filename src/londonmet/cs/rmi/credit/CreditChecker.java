package londonmet.cs.rmi.credit;





import java.rmi.Remote;
import java.rmi.RemoteException;



public interface CreditChecker extends Remote {

   public static final String NAME = "creditChecker";

   public boolean isOkay(Person person) 
      throws RemoteException;
}
