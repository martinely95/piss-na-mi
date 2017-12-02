package londonmet.cs.rmi.credit.client;

import londonmet.cs.rmi.credit.Person;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UI extends JFrame {

    private final PersonUI personUI = new PersonUI();
    private Person[] persons;

    private CreditCheckerClient creditCheckerClient;

    private List<JPanel> jPanels = new ArrayList<>();

    public static class PersonUI implements Serializable {
        JLabel name;
        JLabel secondName;
        JLabel creditRating;
        JLabel balance;
        JTextField inputText;
        JButton deposit;
        JButton withdraw;

        public Person getPerson() {
            return person;
        }

        Person person;

        public PersonUI() {
        }
    }

    private class Deposit implements ActionListener {

        private PersonUI personUI;

        public Deposit(PersonUI personUI) {
            this.personUI = personUI;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            operateWithMoney(true, this.personUI.getPerson());
        }
    }

    private void operateWithMoney(boolean deposit, Person person) {
//        Person person = new Person("Peter", "Cambridge", true, 1110.0);

        // set new value to persons
        double amount = Double.parseDouble(personUI.inputText.getText());

        // invoke rmi method
        try {
            if (deposit) {
                person = creditCheckerClient.getChecker().deposit(person, amount);
            } else {
                person = creditCheckerClient.getChecker().withdrawal(person, amount);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        // update label with result from rmi
        personUI.balance.setText(String.valueOf(person.getBalance()));
        personUI.creditRating.setText(String.valueOf(person.getCreditRating()));
    }

    private class Withdraw implements ActionListener {
        private PersonUI personUI;

        public Withdraw(PersonUI personUI) {
            this.personUI = personUI;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            operateWithMoney(false, this.personUI.getPerson());
        }
    }

    public UI(Person[] persons, CreditCheckerClient creditCheckerClient) throws HeadlessException {

        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        for (Person person : persons) {

            JPanel jPanel = createjPanel(person);
            jPanels.add(jPanel);
            this.add(jPanel);
        }

        this.pack();
        this.setVisible(true);

        this.persons = persons;

        this.creditCheckerClient = creditCheckerClient;
    }

    private JPanel createjPanel(Person person) {
        JPanel jPanel = new JPanel();

        jPanel.add(personUI.name = new JLabel(person.getFirstName()));
        jPanel.add(personUI.secondName = new JLabel(person.getLastName()));
        jPanel.add(personUI.balance = new JLabel(String.valueOf(person.getBalance())));
        jPanel.add(personUI.creditRating = new JLabel(String.valueOf(person.getCreditRating())));

        jPanel.add(personUI.inputText = new JTextField(5));
        jPanel.add(personUI.deposit = new JButton("Deposit"));
        jPanel.add(personUI.withdraw = new JButton("Withdraw"));
        personUI.person = person;

        personUI.deposit.addActionListener(new Deposit(personUI));
        personUI.withdraw.addActionListener(new Withdraw(personUI));

        jPanel.setVisible(true);
        return jPanel;
    }

    public static void main(String[] args) throws RemoteException, NotBoundException {
//        Person person = new Person("Peter", "Cambridge", true, 1110.0);
//
//        Person[] persons = new Person[]{person};

        Person[] persons = new Person[3];
        persons[0] = new Person("Mark", "Campbell", true, 120.0);
        persons[1] = new Person("Vassil", "Vassilev", true, 320.0);
        persons[2] = new Person("Peter", "Cambridge", true, 1110.0);


        UI ui = new UI(persons, new CreditCheckerClient(args));

        Scanner scanner = new Scanner(System.in);

    }
}
