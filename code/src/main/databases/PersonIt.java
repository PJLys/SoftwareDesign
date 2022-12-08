package databases;

import iterator.Iterator;

public class PersonIt implements Iterator {
    private int index;
    public PersonIt() {
        this.index = -1;
    }

    /**
     * Checks if current index < size -1
     * @return true if not last element
     */
    @Override
    public boolean hasNext() {
        return this.index < (PersonDB.getInstance().size() - 1) ;
    }

    /**
     * Increases current index and returns object at new index
     * @return Person at new index
     */
    @Override
    public Object next() {
        return PersonDB.getInstance().get(++index);
    }

    /**
     * Removes person at current index
     * @return error code (-1 if person could not be found)
     */
    @Override
    public int remove() {
        PersonDB db = PersonDB.getInstance();
        return db.removePerson(db.get(this.index));
    }
}
