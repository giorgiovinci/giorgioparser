package nl.elmar.model;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Price {
    @Id 
	private String uuid = UUID.randomUUID().toString();
	private String currency;
	private String pricePer;
	private String amount;
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
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
        return "Price Id: " + uuid + " amount: " + amount;
    }
}
