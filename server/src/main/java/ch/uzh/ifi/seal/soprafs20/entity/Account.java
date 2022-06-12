package ch.uzh.ifi.seal.soprafs20.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "ACCOUNT")
public class Account implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    private String AccountId;

    //default is NatWest for the current use case
    //can be modified in case of future extensions/integrations with other databases
    @Column(nullable = false)
    private String BankName = "NatWest";

    @Column(nullable = false)
    private String Currency;

    @Column(nullable = false)
    private String AccountType;

    @Column(nullable = false)
    private String AccountSubType;

    @Column(nullable = false)
    private Float Balance;


    //getters and setters for account variables
    public String getAccountId(){return AccountId;}
    public void setAccountId(String AccountId){this.AccountId = AccountId;}

    public String getBankName(){return BankName;}
    public void setBankName(String BankName){this.BankName = BankName;}

    public String getCurrency(){return Currency;}
    public void setCurrency(String Currency){this.Currency = Currency;}

    public String getAccountType(){return AccountType;}
    public void setAccountType(String AccountType){this.AccountType = AccountType;}

    public String getAccountSubType(){return AccountSubType;}
    public void setAccountSubType(String AccountSubType){this.AccountSubType = AccountSubType;}

    public Float getBalance(){return Balance;}
    public void setBalance(Float Balance){this.Balance = Balance;}


    @Override
    public String toString() {
        return "Account{" +
                "accountId="+ AccountId +
                ", bankName=" + '\''+ BankName + '\'' +
                ", currency=" + '\''+ Currency + '\'' +
                ", AccountType=" + '\''+ AccountType + '\'' +
                ", AccountSubtype=" + '\''+ AccountSubType + '\'' +
                ", Balance=" + Balance +
                '}';
    }


}
