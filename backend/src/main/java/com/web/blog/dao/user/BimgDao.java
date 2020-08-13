
package com.web.blog.dao.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.blog.model.user.Bimg;
public interface BimgDao extends JpaRepository<Bimg, Integer> {	
	List<Bimg> findByBid(int bid);
}
