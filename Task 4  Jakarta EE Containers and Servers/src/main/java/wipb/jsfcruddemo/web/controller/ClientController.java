/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wipb.jsfcruddemo.web.controller;

import wipb.jsfcruddemo.web.model.Account;
import wipb.jsfcruddemo.web.model.Client;
import java.io.Serializable;
import java.util.List;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.control.RequestContextController;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;
import org.primefaces.PrimeFaces;
import wipb.jsfcruddemo.web.service.ClientService;

@Named
@ViewScoped
public class ClientController implements Serializable {
    @EJB
    private ClientService clientService;
    private List<Client> clients;
    private Client editedClient;
    private Account editedAccount;

  
   @PostConstruct
public void init() {
      editedClient = new Client();
    editedClient.setAccounts(new ArrayList<>());
    clients = clientService.findAll();
 }


    public List<Client> getClients() {
        return clients;
    }

    public Account.Type[] getAccountTypes() {
        return Account.Type.values();
    }

    public Client getEditedClient() {
        return editedClient;
    }

    public void setEditedClient(Client editedClient) {
        this.editedClient = editedClient;
    }

    public Account getEditedAccount() {
        return editedAccount;
    }

    public void setEditedAccount(Account editedAccount) {
        this.editedAccount = editedAccount;
    }
public void onAddClient() {
            editedClient = new Client();

    
    
    
}



   public void onEditClient(Client client) {
    editedClient = client;

    // Ensure accounts are loaded
    if (editedClient.getAccounts() == null) {
        editedClient.setAccounts(new ArrayList<>());
    } else {
        editedClient.setAccounts(new ArrayList<>(editedClient.getAccounts())); // To avoid lazy loading issues
    }
    
        System.out.println("Accounts: " + editedClient.getAccounts()); // Debugging output

}
 public void onSaveClient() {
     if (editedClient.getId() == null) {
            clients.add(editedClient);
        }

        /* Operacja zapisu może spowodować zmianę stanu obiektów, np.: dla nowych obiektów baza nadaje id.
          Jeśli stan obiektu jest aktualizowany z użyciem operacji EntityManager.merge, to obiekt ze zmianami nadanymi przez bazę bedzie zwrócony jako wartość.
          Dlatego po zapisie poprzedni obiekt jest podmieniany na zwrócony przez save.
         */
        Client saved = clientService.save(editedClient);
        clients.replaceAll(c-> c != editedClient ? c : saved);

        editedClient = null;
        
    }

 

    public void onRemoveClient(Client c) {

        if (c != null) {
            // Delete the client
            clientService.delete(c.getId());
            clients.remove(c);
            FacesMessage msg = new FacesMessage("Client deleted successfully!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            PrimeFaces.current().ajax().update(":mainForm:clientTable");
        }
        //clientService.delete(c.getId());
        //clients.remove(c);
    }
public void logClients() {
    clients.forEach(client -> System.out.println(client));
}
    public void onCancelClient() {
        // Revert changes to the client, including its notes
        clients.replaceAll(c -> c != editedClient ? c : clientService.findById(editedClient.getId()));

        editedClient = null;
    }
public void onAddAccount() {
    // Initialize the editedAccount if it's not already initialized
    editedAccount = new Account();  // Initialize the new account

    // Generate a unique ID for the account (this can be done inside the constructor of the Account object)
  editedClient.setId(Long.parseLong(UUID.randomUUID().toString()));
    // Set default values for the account
    editedAccount.setType(Account.Type.SAVING);  // Set default account type
    editedAccount.setBalance(BigDecimal.ZERO);  // Set default balance
    editedAccount.setNotes("New account");  // Default notes
    editedAccount.setDisabled(true);  // Initially disable the account

    // Show the account creation dialog
    PrimeFaces.current().executeScript("PF('accountDlg').show()");  // Open the dialog
}

   

    public void onEditAccount(Account a) {
        editedAccount = a;
    }

 
    public void onSaveAccount() {
        if (!editedClient.getAccounts().contains(editedAccount)) {
            editedClient.add(editedAccount);
        }
        editedAccount = null;
    }
    
    public void onRemoveAccount(Account a) {
        a.getClient().getAccounts().remove(a);
    }
    
    public void onCancelAccount() {
        editedAccount = null;
    }
    
    public void onDisableAccount(Account account) {
        account.setDisabled(true);
        saveAccountChanges(); // Save changes via the parent Client
    }

   public void onEnableAccount(Account account) {
        account.setDisabled(false);
        saveAccountChanges(); // Save changes via the parent Client
    }

   public void updateAccountTable() {
    // Refresh the account table
       PrimeFaces.current().ajax().update(":clientDlgForm:accountTable");
}
   
    private void saveAccountChanges() {
        // Save the edited client, including its updated accounts
        clientService.save(editedClient);
    }
}
