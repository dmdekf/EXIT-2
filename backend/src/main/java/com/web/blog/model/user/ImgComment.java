package com.web.blog.model.user;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@ToString
@Entity (name="ImgComment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ImgComment {
	@Id
	private int id;
	
	private int boardIdx;
	
	private String content;
	
	private String writer;
	
	private String deleteYn;
	
	private Date insertTime;
	private Date updateTime;
	private Date deleteTime;
	
	private String uimage;

	public ImgComment(Comment c, String uimage) {
		super();
		this.id = c.getIdx();
		this.boardIdx = c.getBoardIdx();
		this.content = c.getContent();
		this.writer = c.getWriter();
		this.deleteYn = c.getDeleteYn();
		this.insertTime = c.getInsertTime();
		this.updateTime = c.getUpdateTime();
		this.deleteTime = c.getDeleteTime();
		this.uimage = uimage;
	}
	
	
}
