package resources.java;

import java.util.Date;

import javax.persistence.Transient;

import org.hightides.annotations.Controller;
import org.hightides.annotations.Dao;
import org.hightides.annotations.Page;
import org.hightides.annotations.Service;
import org.hightides.annotations.TextArea;
import org.hightides.annotations.TextField;

@Dao
@Service
@Controller
@Page
public class TestClass {
	@TextField(requiredField=true)
	private String name;
	@TextArea(requiredField=true)
	private String description;
	@TextField(requiredField=false)
	private String category;
	
	@Transient
	private Date currentDate;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Date getCurrentDate() {
		return currentDate;
	}
	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}
}
