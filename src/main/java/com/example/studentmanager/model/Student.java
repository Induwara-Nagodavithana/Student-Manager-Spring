package com.example.studentmanager.model;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "student")
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Integer id;
	@Column
	private String name;
	@Column
	private String address;
	@Column
	private String contactNo;
	@Column
	private Date dob;

	public Student() {

	}

	public Student(String name, String address, String contactNo, Date dob) {
		this.name = name;
		this.address = address;
		this.contactNo = contactNo;
		this.dob = dob;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	@Override
	public String toString() {
		return "Student{" +
				"id=" + id +
				", name='" + name + '\'' +
				", address='" + address + '\'' +
				", contactNo='" + contactNo + '\'' +
				", dob=" + dob +
				'}';
	}
}
