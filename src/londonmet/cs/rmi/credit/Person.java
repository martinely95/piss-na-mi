package londonmet.cs.rmi.credit;

public class Person implements java.io.Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String firstName;
    private String secondName;
    private boolean creditRating;

    private Double balance;

    public Person(String firstName, String secondName, boolean creditRating, Double balance) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.creditRating = creditRating;
        this.balance = balance;
}
    public Person(String firstName, String secondName) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.creditRating = true;
    }

    public Person(String firstName, String secondName, boolean creditRating) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.creditRating = creditRating;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return secondName;
    }

    public boolean getCreditRating() {
        return creditRating;
    }

    public String toString() {
        return (secondName + ", " + firstName);
    }
}
