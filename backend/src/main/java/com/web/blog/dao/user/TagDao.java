
package com.web.blog.dao.user;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.blog.model.user.Taglist;
import com.web.blog.model.user.TagPK;
//User.java 참조 
public interface TagDao extends JpaRepository<Taglist, TagPK> {
    
	
}
