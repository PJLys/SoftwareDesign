package factory;

import person.Person;
import tickets.*;
import tickets.evensplit.EvenSplitTicket;
import tickets.unevensplit.UnevenEntry;
import tickets.unevensplit.UnevenSplitTicket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class TicketFactory {
    public TicketFactory(){}

    public Ticket createUnevenSplitTicket_terminalIF(Person payer, ArrayList<Person> attendants) {
        Scanner in = new Scanner(System.in);

        //Expense type
        System.out.println("What's the type of expense?");
        ExpenseType[] values = ExpenseType.values();
        for (int i=0; i< values.length; i++){
            System.out.println(i+ " "+values[i]);
        }
        int index = Integer.parseInt(in.nextLine());

        //Create entries
        ArrayList<UnevenEntry> entries = new ArrayList<UnevenEntry>();
        for (Person attendant : attendants) {
            System.out.println("How much did " + attendant.getName() + " spend?");
            double expense = Double.parseDouble(in.nextLine());
            entries.add(new UnevenEntry(attendant, expense));
        }

        return new UnevenSplitTicket(values[index], payer, entries);
    }


    public Ticket createEvenSplitTicket(ExpenseType type, Person payer, double total, ArrayList<Person> attendants) {
        return new EvenSplitTicket(type, payer, total/(1+attendants.size()), attendants);
    }

    public Ticket createUnevenSplitTicket(ExpenseType type, Person payer, ArrayList<UnevenEntry> entries){
        return new UnevenSplitTicket(type, payer, entries);
    }
}
