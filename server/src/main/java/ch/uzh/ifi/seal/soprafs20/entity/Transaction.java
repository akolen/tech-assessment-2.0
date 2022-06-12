package ch.uzh.ifi.seal.soprafs20.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "TRANSACTION")
public class Transaction implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    private String TransactionId;

    @Column(nullable = false)
    private String accountId;

    @Column(nullable = false)
    private String Currency;

    @Column(nullable = false)
    private Float Amount;

    @Column(nullable = false)
    private Date BookingDateTime;

    @Column(nullable = false)
    private String creditDebitIndicator;

    @Column(nullable = false)
    private String transactionInformation;




    //getters and setters for account variables
    public String getAccountId(){return accountId;}
    public void setAccountId(String accountId){this.accountId = accountId;}

    public String getTransactionId(){return TransactionId;}
    public void setTransactionId(String TransactionId){this.TransactionId = TransactionId;}

    public String getCurrency(){return Currency;}
    public void setCurrency(String Currency){this.Currency = Currency;}

    public Float getAmount(){return Amount;}
    public void setAmount(Float Amount){this.Amount = Amount;}

    public Date getBookingDateTime(){return BookingDateTime;}
    public void setBookingDateTime(Date BookingDateTime){this.BookingDateTime = BookingDateTime;}

    public String getCreditDebitIndicator(){return creditDebitIndicator;}
    public void setCreditDebitIndicator(String creditDebitIndicator){this.creditDebitIndicator = creditDebitIndicator;}

    public String getTransactionInformation(){return transactionInformation;}
    public void setTransactionInformation(String transactionInformation){this.transactionInformation = transactionInformation;}


    @Override
    public String toString() {
        return "Transaction{" +
                "accountId="+ accountId +
                ", transactionId=" + '\''+ TransactionId + '\'' +
                ", currency=" + '\''+ Currency + '\'' +
                ", Amount=" + '\''+ Amount + '\'' +
                ", BookingDateTime=" + '\''+ BookingDateTime + '\'' +
                ", CreditDebitIndicator=" + '\''+ creditDebitIndicator + '\'' +
                ", TransactionInfo=" + '\''+ transactionInformation + '\'' +
                '}';
    }


}
