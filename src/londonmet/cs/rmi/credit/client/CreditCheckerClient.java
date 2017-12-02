package londonmet.cs.rmi.credit.client;

import londonmet.cs.rmi.credit.Constants;
import londonmet.cs.rmi.credit.CreditChecker;
import londonmet.cs.rmi.credit.Person;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class CreditCheckerClient {

    public static void main(String args[]) {

        Person[] people = new Person[3];
        people[0] = new Person("Mark", "Campbell", true, 1000.0);
        people[1] = new Person("Vassil", "Vassilev", false, 2000.0);
        people[2] = new Person("Peter", "Cambridge", true, 1234.0);

        String host = (args.length < 1) ? Constants.DEFAULT_HOST_PORT_STRING : args[0];

        try {
            System.out.println("locating RMI Registry");
            int realHostNumber = Integer.parseInt(host);
            Registry registry = LocateRegistry.getRegistry(realHostNumber);

            System.out.println("looking up object: " + CreditChecker.NAME);
            CreditChecker checker = (CreditChecker) registry.lookup(CreditChecker.NAME);
            System.out.println("***********************************************");
            System.out.println("* invoking the remote method with a parameter *");
            System.out.println("***********************************************");
            for (int i = 0; i < people.length; i++) {
                System.out.println(people[i].toString() +
                        " -> good credit = " + checker.isOkay(people[i]));

                System.out.println((people[i] = checker.deposit(people[i], 20.0)).getBalance());
                System.out.println((people[i] = checker.withdrawal(people[i], 20.0)).getBalance());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
