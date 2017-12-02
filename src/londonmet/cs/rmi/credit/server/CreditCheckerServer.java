package londonmet.cs.rmi.credit.server;


import londonmet.cs.rmi.credit.CreditChecker;
import londonmet.cs.rmi.credit.server.impl.CreditCheckerImpl;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import static londonmet.cs.rmi.credit.Constants.DEFAULT_HOST_NAME;
import static londonmet.cs.rmi.credit.Constants.DEFAULT_HOST_PORT_INT;


public class CreditCheckerServer {
    public static void main(String args[]) {
        try {
            System.setProperty("java.rmi.server.hostname", DEFAULT_HOST_NAME);

            Registry registry = LocateRegistry.createRegistry(DEFAULT_HOST_PORT_INT);

            System.out.println("Server ready");

            System.out.println("creating credit checker");
            CreditCheckerImpl creditChecker =
                    new CreditCheckerImpl();

            System.out.println("locating local RMI Registry");

            System.out.println("registering credit checker with RMI Registry");
            registry.rebind(CreditChecker.NAME, creditChecker);
            System.out.println("*********************************");
            System.out.println("* waiting for client requests...*");
            System.out.println("*********************************");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
