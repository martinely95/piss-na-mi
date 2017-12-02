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

    private List<PersonUI> personUIs = new ArrayList<>();
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

            operateWithMoney(true, this.personUI);
        }
    }

    private void operateWithMoney(boolean deposit, PersonUI personUi) {
//        Person person = new Person("Peter", "Cambridge", true, 1110.0);

        // set new value to persons
        double amount = Double.parseDouble(personUi.inputText.getText());

        // invoke rmi method
        try {
            if (deposit) {
                personUi.person = creditCheckerClient.getChecker().deposit(personUi.person, amount);
            } else {
                personUi.person = creditCheckerClient.getChecker().withdrawal(personUi.person, amount);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        // update label with result from rmi
        personUi.balance.setText(String.valueOf(personUi.person.getBalance()));
        personUi.creditRating.setText(String.valueOf(personUi.person.getCreditRating()));
    }

    private class Withdraw implements ActionListener {
        private PersonUI personUI;

        public Withdraw(PersonUI personUI) {
            this.personUI = personUI;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            operateWithMoney(false, this.personUI);
        }
    }

    public UI(Person[] persons, CreditCheckerClient creditCheckerClient) throws HeadlessException {

        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        for (Person person : persons) {
            PersonUI personUI = new PersonUI();
            personUI.person = person;
            personUIs.add(personUI);
            JPanel jPanel = createjPanel(personUI);
            jPanels.add(jPanel);
            this.add(jPanel);
        }

        this.pack();
        this.setVisible(true);

        this.persons = persons;

        this.creditCheckerClient = creditCheckerClient;
    }

    private JPanel createjPanel(PersonUI personUI) {
        JPanel jPanel = new JPanel();
        Person person = personUI.getPerson();

        jPanel.add(personUI.name = new JLabel(person.getFirstName()));
        jPanel.add(personUI.secondName = new JLabel(person.getLastName()));
        jPanel.add(personUI.balance = new JLabel(String.valueOf(person.getBalance())));
        jPanel.add(personUI.creditRating = new JLabel(String.valueOf(person.getCreditRating())));

        jPanel.add(personUI.inputText = new JTextField(5));
        jPanel.add(personUI.deposit = new JButton("Deposit"));
        jPanel.add(personUI.withdraw = new JButton("Withdraw"));

        personUI.deposit.addActionListener(new Deposit(personUI));
        personUI.withdraw.addActionListener(new Withdraw(personUI));
        personUI.person = person;

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
