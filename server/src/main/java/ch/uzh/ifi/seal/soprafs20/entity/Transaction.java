package ch.uzh.ifi.seal.soprafs20.entity;

import javax.persistence.*;
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


    @Override
    public String toString() {
        return "Transaction{" +
                "accountId="+ accountId +
                ", transactionId=" + '\''+ TransactionId + '\'' +
                ", currency=" + '\''+ Currency + '\'' +
                ", Amount=" + '\''+ Amount + '\'' +
                ", BookingDateTime=" + '\''+ BookingDateTime + '\'' +
                '}';
    }


}
