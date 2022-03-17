package com.example.demo.pizza;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.core.style.ToStringCreator;

@Entity
@Table(name = "pizza")
public class Pizza {
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "id")
	    @NotFound(action = NotFoundAction.IGNORE)
	    private Integer id;
	 
	    @Column(name = "crust")
	    @NotFound(action = NotFoundAction.IGNORE)
	    private String crust;
	    
	    @Column(name = "topping")
	    @NotFound(action = NotFoundAction.IGNORE)
	    private String topping;
	    
	    
	    public Integer getId()
	    {
	    	return id;
	    }
	    
	    public void setId(Integer id)
	    {
	    	this.id = id;
	    }
	    
	    public String getCrust()
	    {
	    	return crust;
	    }
	    
	    public void setCrust(String crust)
	    {
	    	this.crust = crust;
	    }
	    
	    public String getToping()
	    {
	    	return topping;
	    }
	    
	    public void setToping(String top)
	    {
	    	this.topping = top;
	    }
	    
	    @Override
	    public String toString() 
	    {
	    	return new ToStringCreator(this)
	    			.append("id" , this.getId())
	    			.append("crust", this.getCrust())
	    			.append("topping", this.getToping()).toString();
	    }
	

}
