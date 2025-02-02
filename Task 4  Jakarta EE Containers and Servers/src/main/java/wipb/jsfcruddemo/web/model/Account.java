/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wipb.jsfcruddemo.web.model;

import java.math.BigDecimal;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.validation.constraints.NotNull;

@NamedQueries({
    @NamedQuery(name="Account.findAll", query="SELECT a FROM Account a")
})
@Entity
public class Account extends AbstractModel{

    
   public static enum Type { SAVING, CREDIT };
  
   @Column(precision = 13, scale = 2)
   private BigDecimal balance;
   private String notes;
   @Enumerated(EnumType.STRING)
   private Type type;
   
    private boolean disabled = false; 
    
   @ManyToOne @NotNull
   private Client client;
   
    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }
    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getNotes() {
        return notes;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Account{" + "id=" + getId() + ", balance=" + balance + ", notes=" + notes + ", type=" + type+"}";
    }
   
}
