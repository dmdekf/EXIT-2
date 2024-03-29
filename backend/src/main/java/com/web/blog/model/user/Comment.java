package com.web.blog.model.user;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Entity (name="tb_comment")
@Data
@NoArgsConstructor
@AllArgsConstructor 
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Comment {
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private int idx;
	
	private int boardIdx;
	
	private String content;
	
	private String writer;
	
	private String deleteYn;
	
	private Date insertTime;
	private Date updateTime;
	private Date deleteTime;
	
	 
}
