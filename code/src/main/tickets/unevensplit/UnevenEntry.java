package tickets.unevensplit;

import person.Person;

/**
 * Stores a person with a corresponding debt
 */
public class UnevenEntry {
    public Person p;
    public double val;

    public UnevenEntry(Person p, double val) {
        this.p = p;
        this.val = val;
    }

    @Override
    public String toString() {
        return "\n\t" + p.getName()+" : " + val;
    }
}
