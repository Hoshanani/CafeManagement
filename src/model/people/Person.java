package model.people;

import java.io.Serializable;

// Tüm kişi tipleri için soyut sınıf
public abstract class Person implements Serializable {

    protected String name;

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
