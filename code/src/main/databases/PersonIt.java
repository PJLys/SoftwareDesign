package databases;

import iterator.Iterator;

public class PersonIt implements Iterator {
    private int index;
    public PersonIt() {

    }
    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public Object next() {
        return PersonDB.getInstance().get(index++);
    }

    @Override
    public int remove() {
        PersonDB db = PersonDB.getInstance();
        return db.removePerson(db.get(this.index));
    }
}
