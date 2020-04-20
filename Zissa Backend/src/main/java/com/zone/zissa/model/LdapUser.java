package com.zone.zissa.model;

/**
 * The LdapUser class
 * 
 */
public class LdapUser {

    private String sAMAccountName;
    private String name;
    private String email;
    private String firstName;
    private String lastName;

    /**
     * The getsAMAccountName method
     * 
     * @return sAMAccountName
     */
    public String getsAMAccountName() {
        return sAMAccountName;
    }

    /**
     * The setsAMAccountName method
     * 
     * @param sAMAccountName
     */
    public void setsAMAccountName(String sAMAccountName) {
        this.sAMAccountName = sAMAccountName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
