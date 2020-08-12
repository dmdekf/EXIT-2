package com.web.blog.dao.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.blog.model.user.TagPK;
import com.web.blog.model.user.Taglist;
//User.java 참조 
public interface TagDao extends JpaRepository<Taglist, TagPK> {
    
	List<Taglist> findByTag(String tag);
	List<Taglist> findByBid(int bid);
}
