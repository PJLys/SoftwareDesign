package tickets;

import person.Person;

/**
 * Abstract ticket, will be stored in the DB
 */
public abstract class Ticket {
    private final ExpenseType type;
    private final Person payer;
    public Ticket(ExpenseType t, Person p){
        this.type = t;
        this.payer = p;
    }
    public Person getPayer() {
        return this.payer;
    }
    public ExpenseType getType() {
        return this.type;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "type= " + type +
                ", payer=" + payer +
                '}';
    }
}
