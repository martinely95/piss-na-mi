package londonmet.cs.rmi.credit.client;

import londonmet.cs.rmi.credit.Person;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

public class UI extends JFrame {

    private Person person;

    private JLabel name;
    private JLabel secondName;
    private JLabel creditRating;
    private JLabel balance;

    private JTextField inputText;

    private JButton deposit;
    private JButton withdraw;

//    private JPanel[] jPanels;

    private class Deposit implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            System.out.println("hi");
            // set new value to person
            person.setBalance(person.getBalance() + Double.parseDouble(inputText.getText()));

            // invoke rmi method


            // update label with result from rmi
            balance.setText(String.valueOf(person.getBalance()));
        }
    }
    private class Withdraw implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            System.out.println("hi 2");
        }
    }

    public UI(Person person) throws HeadlessException {

        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        JPanel jPanel = new JPanel();

        jPanel.add(name = new JLabel(person.getFirstName()));
        jPanel.add(secondName =  new JLabel(person.getLastName()));
        jPanel.add(balance = new JLabel(String.valueOf(person.getBalance())));
        jPanel.add(creditRating = new JLabel(String.valueOf(person.getCreditRating())));

        jPanel.add(inputText = new JTextField(5));
        jPanel.add(deposit = new JButton("Deposit"));
        jPanel.add(withdraw = new JButton("Withdraw"));

        deposit.addActionListener(new Deposit());
        withdraw.addActionListener(new Withdraw());

        jPanel.setVisible(true);

//        jPanels = new JPanel[]{jPanel};

        this.add(jPanel);

        this.person = person;

        this.pack();
        this.setVisible(true);
    }

    public static void main(String[] args) {
        Person person = new Person("Peter", "Cambridge", true, 1110.0);

        UI ui = new UI(person);

        Scanner scanner = new Scanner(System.in);

    }
}
