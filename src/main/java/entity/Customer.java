package entity;

import java.time.LocalDate;

public class Customer {
	private int id;
	private String fullname;
	private boolean gender;
	private String picture;
	private LocalDate dob;
	
	public Customer() {}
	
	public Customer(int id, String fullname, boolean gender, String picture, LocalDate dob) {
		super();
		this.id = id;
		this.fullname = fullname;
		this.gender = gender;
		this.picture = picture;
		this.dob = dob;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public boolean isGender() {
		return gender;
	}

	public void setGender(boolean gender) {
		this.gender = gender;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", fullname=" + fullname + ", gender=" + gender + ", picture=" + picture
				+ ", dob=" + dob + "]";
	}
}
