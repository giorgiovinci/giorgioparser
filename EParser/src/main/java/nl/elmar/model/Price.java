package nl.elmar.model;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Price {
    @Id 
	private String id = UUID.randomUUID().toString();
	private String currency;
	private String pricePer;
	private String amount;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getPricePer() {
		return pricePer;
	}
	public void setPricePer(String pricePer) {
		this.pricePer = pricePer;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
    @Override
    public String toString() {
        return "Price Id: " + id + " amount: " + amount;
    }
}
