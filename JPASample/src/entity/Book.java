package entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String title;
	private String description;

	public Book(int inputId, String inputTitle, String inputDescription) {
		super();
		this.id = inputId;
		this.title = inputTitle;
		this.description = inputDescription;
	}

	public Book() {
		super();
	}

	public int getId() {
		return this.id;
	}

	public String getTitle() {
		return this.title;
	}

	public String getDescription() {
		return this.description;
	}

	public int setId(int inputId) {
		this.id = inputId;
		return this.id;
	}

	public String setTitle(String inputTitle) {
		this.title = inputTitle;
		return this.title;
	}

	public String setDescription(String inputDescription) {
		this.description = inputDescription;
		return this.description;
	}

	@Override
	public String toString() {
		return "Book: [id:" + this.id + ", title:" + this.title + "]";
	}
}
