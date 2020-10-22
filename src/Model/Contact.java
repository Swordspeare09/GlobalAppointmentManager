/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author corte
 */
public class Contact {
    
    //Priavte class variables
    private int id;
    private String name;
    private String email;
    
    //Constructor method for creating part objects
    public Contact (int id, String name, String email)
    {
        this.id = id;
        this.name = name;
        this.email = email;
    }
    
    //Public mutator methods
    public void setId(int id) {
	this.id = id;
    }
	
    public void setName(String name) {
	this.name = name;
    }
   public void setEmail(String email) {
	this.email = email;
    }
    
    //Public accessor methods
    public int getId() {
	return this.id;
    }
	
    public String getName() {
	return this.name;
    }
    
    public String getEmail() {
	return this.email;
    }
    
}
