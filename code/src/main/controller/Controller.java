package controller;
import debts.Transaction;
import person.Person;
import tickets.ExpenseType;
import tickets.Ticket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface Controller {
    int addPerson(String name);

    boolean isPersonUsed(String name);
    int removePerson(String name);
    Ticket makeEvenSplitTicket(ExpenseType type, String payer, double total, List<String> attendants);
    Ticket makeUnevenSplitTicket(ExpenseType type, String payer, HashMap<String, Double> entries);
    Person getPerson(String name);
    ArrayList<String> getPersonStringList();
    ArrayList<Transaction> calcDebt();
}
