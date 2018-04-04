package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controllers.Application;

@Entity
public class Task {
	private static final Logger logger = LoggerFactory.getLogger(Application.class);
	
    @Id
    @GeneratedValue
    private Integer id;
    
    @play.data.validation.Constraints.Required
    private String contents;
    
    public Integer getId() {
    	logger.info("Getting ID");
    	return id;
    }
    
    public void setId(Integer id) {
    	logger.info("Setting ID to "+id);
    	this.id = id;
    }
    
    public String getContents() {
    	logger.info("Getting contents");
    	return contents;
    }
    
    public void setContents(String contents) {
    	logger.info("Setting contents to "+contents);
    	this.contents = contents;
    }
}
