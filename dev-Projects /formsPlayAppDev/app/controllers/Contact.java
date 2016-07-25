package controllers;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="res_partner")
public class Contact {

	@Id
	@GenericGenerator(name = "kaugen", strategy = "increment")
	@GeneratedValue(generator = "kaugen")
	@Column(name="id")
	private int id;
	
	

	@Column(name="name")
	private String name;
	
	@Column(name="last_name")
	private String last_name;
	
	@Column(name="email")
	private String email;
	
	@Column(name="notification_email_send")
	private boolean notification_email_send;

	public boolean isNotification_email_send() {
		return notification_email_send;
	}

	public void setNotification_email_send(boolean notification_email_send) {
		this.notification_email_send = notification_email_send;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Contact [id=" + id + ", name=" + name + ", last_name="
				+ last_name + ", email=" + email + "]";
	}
	
	
	
	

}
