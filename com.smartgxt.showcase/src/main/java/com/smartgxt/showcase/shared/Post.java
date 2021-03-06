package com.smartgxt.showcase.shared;

import java.io.Serializable;
import java.util.Date;

import com.smartgxt.ui.shared.KeyBean;

public class Post implements KeyBean {

	private static int ID = 0;

	private String username;
	private String forum;
	private Date date;
	private String subject;
	private int id;

	public Post() {
		setId(ID++);
	}

	public Post(String username, String forum, Date date, String subject) {
		setId(ID++);

		setUsername(username);
		setForum(forum);
		setSubject(subject);
		setDate(date);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getForum() {
		return forum;
	}

	public void setForum(String forum) {
		this.forum = forum;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Override
	public String getKey() {
		return "" + getId();
	}
}
