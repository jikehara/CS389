package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.MinLength;

@Entity
public class Task {
	
    @Id
    @GeneratedValue
    private Integer id;
    
    @MinLength(value = 3)
    @MaxLength(value = 20)
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
