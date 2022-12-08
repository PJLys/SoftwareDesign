package controller;
import debts.Transaction;
import person.Person;
import tickets.ExpenseType;
import tickets.Ticket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface Controller {
    public int addEvenSplitTicket(Ticket t);
    public int addUnevenSplitTicket(Ticket t);
    public int addPerson(String name);
    public int removePerson(String name);
    public void makeEvenSplitTicket(ExpenseType type, String payer, double total, List<String> attendants);
    public void makeUnevenSplitTicket(ExpenseType type, String payer, HashMap<String, Double> entries);
    public Person getPerson(String name);
    public ArrayList<String> getPersonStringList();
    public ArrayList<Transaction> calcDebt();
}
