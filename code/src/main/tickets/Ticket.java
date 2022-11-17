package tickets;

import person.Person;

enum ExpenseType {
    AIRPLANE,
    RESTAURANT,
    TAXI,
    CONCERT,
    HOTEL
}

public abstract class Ticket {
    private ExpenseType type;
    private Person payer;
    public Ticket(ExpenseType t, Person p){
        this.type = t;
        this.payer = p;
    }
}
