package tickets;

import person.Person;

public abstract class Ticket {
    private ExpenseType type;
    private Person payer;
    public Ticket(ExpenseType t, Person p){
        this.type = t;
        this.payer = p;
    }
    public ExpenseType[] getExpenseType(){
        return ExpenseType.values();
    }
}
