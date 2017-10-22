package entity;

import javafx.beans.property.*;
import utilities.IShared;

public class Student {
	private IntegerProperty id;
	private StringProperty firstName;
    private StringProperty lastName;    
    private IntegerProperty mark1;
    private IntegerProperty mark2;   
   
    public Student() {
    	this.id = new SimpleIntegerProperty(-1000);
        this.firstName = new SimpleStringProperty();
        this.lastName = new SimpleStringProperty();        
        this.mark1 = new SimpleIntegerProperty();
        this.mark2 = new SimpleIntegerProperty();       
    }   
    
    public int getId() {
		return id.get();
	}

	public void setId(int id) {
		this.id.set(id);
	}
	
	public IntegerProperty idProperty() {
        return id;
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

       public int getMark1() {
        return mark1.get();
    }

    public void setMark1(int m1) {
        this.mark1.set(m1);
    }

    public IntegerProperty mark1Property() {
        return mark1;
    }
    
    public int getMark2() {
        return mark2.get();
    }

    public void setMark2(int m2) {
        this.mark2.set(m2);
    }

    public IntegerProperty mark2Property() {
        return mark2;
    }
    
    public DoubleProperty calculateStudentAverage() {    
        return new SimpleDoubleProperty(0.5 * (mark1.intValue() + mark2.intValue()));
    }
    
    public String toString() {
    	StringBuilder str = new StringBuilder();
    	str.append(firstName.getValue()).append(IShared.delimeter).append(lastName.getValue()).
    	append(IShared.delimeter).append(mark1.intValue()).append(IShared.delimeter).append(mark2.intValue());    	
        return str.toString();
    }
}