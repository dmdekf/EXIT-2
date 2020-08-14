package com.web.blog.model.user;

import java.util.Date;

import com.web.blog.model.user.Board;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@ToString
@Entity (name="post")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Post {
	@Id
	private int id;
	
	private String subject;
	private String content;
	private Date created;
	private String uid;
	private String email;
	private String tag;
	private int hit;
	
	private int lnt;
	private int cnt;
	private boolean ilike;
	private String bimg;
	
	public int getId() {
      return id;
   }
   public void setId(int id) {
      this.id = id;
   }
   public String getSubject() {
      return subject;
   }
   public void setSubject(String subject) {
      this.subject = subject;
   }
   public String getContent() {
      return content;
   }
   public void setContent(String content) {
      this.content = content;
   }
   public Date getCreated() {
      return created;
   }
   public void setCreated(Date created) {
      this.created = created;
   }
   public String getEmail() {
      return email;
   }
   public void setEmail(String email) {
      this.email = email;
   }
   public String getUid() {
      return uid;
   }
   public void setUid(String uid) {
      this.uid = uid;
   }
   public String getTag() {
      return tag;
   }
   public void setTag(String tag) {
      this.tag = tag;
   }
   public int getHit() {
      return hit;
   }

	public void setHit(int hit) {
		this.hit = hit;
	}
   public int getLnt() {
      return lnt;
   }
   public void setLnt(int lnt) {
      this.lnt = lnt;
   }
   public int getCnt() {
      return cnt;
   }
   public void setCnt(int cnt) {
      this.cnt = cnt;
   }
   public boolean isIlike() {
      return ilike;
   }
   public void setIlike(boolean ilike) {
      this.ilike = ilike;
   }
	public Post(Board board, int lnt, int cnt, boolean ilike) {
		super();
		this.id = board.getId();
		this.subject = board.getSubject();
		this.content = board.getContent();
		this.created = board.getCreated();
		this.uid = board.getUid();
		this.email = board.getEmail();
		this.tag = board.getEmail();
		this.hit = board.getHit();
		this.lnt = lnt;
		this.cnt = cnt;
		this.ilike = ilike;
	}
	public Post(Board board, int lnt, int cnt, boolean ilike,String bimg) {
		super();
		this.id = board.getId();
		this.subject = board.getSubject();
		this.content = board.getContent();
		this.created = board.getCreated();
		this.uid = board.getUid();
		this.email = board.getEmail();
		this.tag = board.getEmail();
		this.hit = board.getHit();
		this.lnt = lnt;
		this.cnt = cnt;
		this.ilike = ilike;
		this.bimg = bimg;
	}
	public Post(int id, String subject, String content, Date created, String uid, String email, String tag, int hit,
			int lnt, int cnt, boolean ilike) {
		super();
		this.id = id;
		this.subject = subject;
		this.content = content;
		this.created = created;
		this.uid = uid;
		this.email = email;
		this.tag = tag;
		this.hit = hit;
		this.lnt = lnt;
		this.cnt = cnt;
		this.ilike = ilike;
	}
	
	
	
}
