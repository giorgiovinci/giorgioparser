package nl.elmar.model;

import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Accommodation {
    @Id
    private String uuid = UUID.randomUUID().toString();
	private String id;
	private String name;
    @OneToMany(cascade =  {CascadeType.ALL})
	private List<Unit> units;
	
	public String getUuid() {
        return uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
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
