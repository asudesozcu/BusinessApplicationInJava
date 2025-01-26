/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wipb.jsfcruddemo.web.model;

import java.util.LinkedList;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
@NamedQueries({
    @NamedQuery(name="Client.findAll", query="SELECT c FROM Client c")
})
@Entity
public class Client extends AbstractModel {
    
    private String firstName;
    private String lastName;
    @Email
    private String email;
    @OneToMany(mappedBy = "client", cascade = {CascadeType.ALL} , orphanRemoval = true, fetch = FetchType.EAGER )
    private List<Account> accounts = new LinkedList<>();
 private String notes ;
 
 public void setNotes(String notes) {
        this.notes = notes != null ? notes : "";
    }

    public String getNotes() {
        return notes;
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

    public List<Account> getAccounts() {
        return accounts;
    }

    public void add(Account a) {
        this.accounts.add(a);
        a.setClient(this);
    }

    public void remove(Account a) {
        this.accounts.remove(a);
        a.setClient(null);
    }

    @Override
    public String toString() {
        return "Client{" + "id=" + getId() + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + '}';
    }

    /**
     * @param accounts the accounts to set
     */
    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
    
    
}
