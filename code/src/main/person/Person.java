package person;

/**
 * Person class represents people.
 */
public class Person {

    private final String name;

    /**
     * @param name will create a person with a certain name.
     */
    public Person(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    @Override
    public String toString(){
        return "Person: "+this.name;
    }
}
