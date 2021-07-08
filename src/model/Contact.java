package model;

// this was going to be my model but as it turns out JTable is designed to deal with 2 dimensional arrays of objects so i just used that
public class Contact implements Comparable<Contact>{
    private String fullName;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    public Contact(String firstName, String lastName, String email, String phoneNumber) {
        this.fullName = lastName + ", " + firstName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        // TODO Auto-generated constructor stub
    }
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
        this.fullName = lastName + ", " + firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
        this.fullName = lastName + ", " + firstName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    @Override
    public String toString() {
        return "\"" + firstName + "\", \""  + lastName + "\", \""  + email + "\", \""  + phoneNumber + "\"";
    }
    @Override
    public int compareTo(Contact o) {
        return this.fullName.compareTo(o.fullName);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o == null) {
            return false;
        } else if (o instanceof Contact) {
            return this.fullName.equals(((Contact) o).fullName);
        } else {
            return false;
        }
    }
    @Override
    public int hashCode() {
        return this.fullName.hashCode();
    }
}
