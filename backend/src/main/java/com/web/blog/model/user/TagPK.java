package com.web.blog.model.user;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagPK implements Serializable {
	private int bid;
	private String tag;
}
 