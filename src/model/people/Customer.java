package model.people;

// Müşteri sınıfı, Person sınıfından türetilmiştir
public class Customer extends Person {

   private final String phone;


    public Customer(String name, String phone) {
        super(name);
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }
}
