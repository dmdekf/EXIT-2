package com.web.blog.model.user;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class  Bimg{
	
	@Id 
	private int id;
	
	private int bid;
	private String uimage;
}
