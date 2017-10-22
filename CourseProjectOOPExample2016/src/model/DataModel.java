package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import dao.DataManager;
import entity.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utilities.IShared;

/**
 * 
 * @author Yuriy
 */
public class DataModel {

	private static DataModel instance;
	private static ObservableList<Student> cache;

	private DataModel() {
		cache = FXCollections.observableArrayList();
	}

	public static DataModel GetInstance() {
		if (instance == null) {
			instance = new DataModel();
		}
		return instance;
	}

	public ObservableList<Student> getCache() {
		return cache;
	}

	public void clear() {
		cache.clear();
	}

	public void add(Student s) {
		cache.add(s);
	}

	public void addAll(List<Student> s) {
		cache.addAll(s);
	}

	public void delete(int i) {
		cache.remove(i);
	}

	public void edit(int i) {
		Student tmp = cache.get(i);
		cache.set(i, tmp);
	}

	public void load(File file) throws FileNotFoundException, IOException, ClassNotFoundException {
		List<String> tmp = DataManager.readTextData(file.getAbsolutePath());
		String[] tokens;
		for (String line : tmp) {
			Student p = new Student();
			tokens = line.split(IShared.delimeter);
			p.setFirstName(tokens[0]);
			p.setLastName(tokens[1]);
			p.setMark1(Integer.parseInt(tokens[2]));
			p.setMark2(Integer.parseInt(tokens[3]));
			add(p);
		}
	}

	public void persist(File file) throws IOException {
		DataManager.saveTextData(cache, file.getAbsolutePath());
	}
	
	public double calculateAverageMark1() {
		double sum = 0;		
			for (Student st : cache) 			
				sum += st.getMark1();				
		return sum / cache.size();
	}
	
	public double calculateAverageMark2() {
		double sum = 0;		
		for (Student st : cache) 			
				sum += st.getMark2();				
		return sum / cache.size();
	}
}