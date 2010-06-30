package nl.elmar.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Accommodation {
    @Id
	private String id;
	private String name;
    @OneToMany(cascade =  {CascadeType.ALL})
	private List<Unit> units;
	
	public Accommodation() {}
	public Accommodation(String id) {
	    this.id = id;
    }
    public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Unit> getUnits() {
		return units;
	}
	public void setUnits(List<Unit> units) {
		this.units = units;
	}
}
