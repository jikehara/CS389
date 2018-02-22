package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Task {
    
    @Id
    @GeneratedValue
    private Integer id;
    
    @play.data.validation.Constraints.Required
    private String contents;
    
    public Integer getId() {
    	return id;
    }
    
    public void setId(Integer id) {
    	this.id = id;
    }
    
    public String getContents() {
    	return contents;
    }
    
    public void setContents(String contents) {
    	this.contents = contents;
    }
}
