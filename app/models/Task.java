package models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Task {
    
    @Id
    private String id;
    
    @play.data.validation.Constraints.Required
    private String contents;
    
    public String getId() {
    	return id;
    }
    
    public void setId(String id) {
    	this.id = id;
    }
    
    public String getContents() {
    	return contents;
    }
    
    public void setContents(String contents) {
    	this.contents = contents;
    }
}
