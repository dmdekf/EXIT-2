package com.web.blog.controller.account;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.web.blog.dao.user.BimgDao;
import com.web.blog.dao.user.BoardDao;
import com.web.blog.dao.user.CommentDao;
import com.web.blog.dao.user.HeartDao;
import com.web.blog.model.BasicResponse;
import com.web.blog.model.user.Bimg;
import com.web.blog.model.user.Board;
import com.web.blog.model.user.Post;

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
   @Autowired
   HeartDao heartDao;
   @Autowired
   CommentDao commentDao;
   @Autowired
   BimgDao bimgDao;

   @GetMapping("/search/{select}/{keyword}")
   @ApiOperation(value = "검색")
   public Object search(@PathVariable String select, @PathVariable String keyword) throws Exception {
      ResponseEntity response = null;
      System.out.println("검색 실행 ");
      List<Board> list = new ArrayList<Board>();
      if (select.equals("all")) {
         Set<Board> slist = new HashSet<Board>();
            list = dao.findBoardByUidLike("%"+keyword+"%");
            for(Board b : list) {
                slist.add(b);
            }
            list = dao.findBoardBySubjectLike("%"+keyword+"%");
            for(Board b : list) {
                slist.add(b);
            }
            list = dao.findBoardByContentLike("%"+keyword+"%");
            for(Board b : list) {
                slist.add(b);
            }
            list = dao.findBoardByTag(keyword);
            for(Board b : list) {
                slist.add(b);
            }
            final BasicResponse result = new BasicResponse();
            List<Post> plist = new ArrayList<Post>();
            for (Board b : slist) {
             int lnt = heartDao.findHeartByBid(b.getId() + "").size();
             int cnt = commentDao.findByBoardIdx(b.getId()).size();
             List<Bimg> bi = bimgDao.findByBid(b.getId());
             String bimg = (bi != null && bi.size() > 0) ? bi.get(0).getUimage() : "";
             plist.add(new Post(b, lnt, cnt, false, bimg));
          }
            result.status = true;
            result.data = "success";
            result.object = plist;
            response = new ResponseEntity<>(result, HttpStatus.OK);
            return response;
      } else if (select.equals("user")) {
         list = dao.findBoardByUidLike("%" + keyword + "%");
      } else if (select.equals("title")) {
         System.out.println("title" + keyword);
         list = dao.findBoardBySubjectLike("%" + keyword + "%");
      } else if (select.equals("content")) {
         list = dao.findBoardByContentLike("%" + keyword + "%");
      } else if (select.equals("tag")) {
         list = dao.findBoardByTag(keyword);
      }
      final BasicResponse result = new BasicResponse();
      result.status = true;
      result.data = "success";
      
      
      List<Post> plist = new ArrayList<Post>();
      for (Board b : list) {
         int lnt = heartDao.findHeartByBid(b.getId() + "").size();
         int cnt = commentDao.findByBoardIdx(b.getId()).size();
         List<Bimg> bi = bimgDao.findByBid(b.getId());
         String bimg = (bi != null && bi.size() > 0) ? bi.get(0).getUimage() : "";
         plist.add(new Post(b, lnt, cnt, false, bimg));
      }
      plist.sort((a, b) -> b.getId() - a.getId());

      for (Post b : plist) {
         System.out.println(b);
      }
      result.object = plist;
      response = new ResponseEntity<>(result, HttpStatus.OK);
      return response;
   }

}

