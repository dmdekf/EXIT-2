package com.web.blog.controller.account;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.web.blog.dao.user.BoardDao;
import com.web.blog.model.BasicResponse;
import com.web.blog.model.user.Board;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
		@ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
		@ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
		@ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@CrossOrigin(origins = { "*" })
@RestController
public class SearchController {

	@Autowired
	BoardDao dao;

	@GetMapping("/search/{select}/{keyword}")
    @ApiOperation(value = "검색")
    public Object search(@PathVariable String select, @PathVariable String keyword ) throws Exception {
        ResponseEntity response = null;
        List<Board> list = new ArrayList<Board>();
        if(select.equals("all")) {
        	// 고민중
        }else if(select.equals("user")) {
        	list = dao.findBoardByUidLike("%"+keyword+"%");
        }else if(select.equals("title")) {
        	System.out.println("title"+ keyword); 
        	list = dao.findBoardBySubjectLike("%"+keyword+"%");
        }else if(select.equals("content")) {
        	list = dao.findBoardByContentLike("%"+keyword+"%");
        }else if(select.equals("tag")) {
        	list = dao.findBoardByTag(keyword);
        }
        final BasicResponse result = new BasicResponse();
        result.status = true;
		result.data = "success";
		result.object = list;
		response = new ResponseEntity<>(result, HttpStatus.OK);
        for(Board b : list) {
        	System.out.println(b);
        }
        return response;
    }

}