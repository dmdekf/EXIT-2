package com.web.blog.controller.account;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.blog.dao.user.BoardDao;
import com.web.blog.dao.user.HeartDao;
import com.web.blog.model.BasicResponse;
import com.web.blog.model.user.Board;
import com.web.blog.model.user.Post;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping("feature/board")
public class BoardController {
   @Autowired
   BoardDao boardDao;
   @Autowired
   HeartDao heartDao;
   
   
   
   @ApiOperation(value="수정하기", response = BoardController.class)
   @PutMapping("/update")
   public Object like(@RequestBody Board request) throws Exception {
      Optional<Board> board = boardDao.findById(request.getId());
      ResponseEntity response = null;
      final BasicResponse result = new BasicResponse();
      if (board.isPresent()) {
         Board b = board.get();
         b.setSubject(request.getSubject());
         b.setContent(request.getContent());
         boardDao.save(b);
         result.status = true;
         result.data = "success";
         response = new ResponseEntity<>(result, HttpStatus.OK);
         return response;
      }
      result.status = false;
      result.data = "fail to Update";
      response = new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
      return response;
   }
	
	@ApiOperation(value = "모든 게시글의 정보를 반환한다.", response = List.class)
	@GetMapping("list")
	public List<Post> getBoardList() throws Exception {
		List<Board> list = boardDao.findAll();
		List<Post> plist = new ArrayList<Post>();
		for(Board b : list){
			int lnt = heartDao.findHeartByBid(b.getId()+"").size();
			int cnt = 0;
			plist.add(new Post(b,lnt, 0, false));
		}
		plist.sort((a,b)->b.getId()-a.getId());
		return plist;
	}

   @ApiOperation(value = "게시글번호에 해당하는 게시글의 정보를 반환한다.", response = BoardController.class)
   @GetMapping("/detail/{uid}/{id}")
   public Object detailBoard2(@PathVariable String uid, @PathVariable String id ) {
      System.out.println(id);
      System.out.println("좋아요 포함 ");
      System.out.println(uid);
      if(uid==null)return "로그인하세요";
      Optional<Board> board = boardDao.findById(Integer.parseInt(id));
      if (board.isPresent()) {
         
         // String uid = "test"; // 여기 수정 필요
         int lnt = heartDao.findHeartByBid(id).size();
         System.out.println(lnt);
         boolean ilike = heartDao.findHeartByBidAndUid(id, uid).isPresent();
         System.out.println(ilike);
         int cnt =0;
         System.out.println(cnt);
         return new Post(board.get(), lnt, cnt, heartDao.findHeartByBidAndUid(id, uid).isPresent());

      }
      return new Post(0,"삭제된 Board","",null,"","","",0,0,0,false);
   }

   @ApiOperation(value = "게시글번호에 해당하는 게시글의 정보를 반환한다.", response = BoardController.class)
   @GetMapping("/list/{id}")
   public Object detailBoard(@PathVariable String id) {
      System.out.println(id);
      Optional<Board> board = boardDao.findById(Integer.parseInt(id));
      if (board.isPresent()) {
         // String uid = "test"; // 여기 수정 필요
         int lnt = heartDao.findHeartByBid(id).size();
        // System.out.println(lnt);
         int cnt = 0;
         return new Post(board.get(), lnt, cnt, false);

      }
      return new Post(0,"삭제된 Board","",null,"","","",0,0,0,false);
   }

	
	@ApiOperation(value = "새로운 게시글 정보를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping("/write")
	public Board writeBoard(@RequestBody Board board) {
		

		return boardDao.save(board);
	}
	
	 
	 @ApiOperation(value = "게시글번호에 해당하는 게시글의 정보를 삭제한다.", response = String.class)    
	   @DeleteMapping("delete/{id}")
	   public Optional<Board> deleteBoard(@PathVariable("id") int id) {
		   Optional<Board> boardId = boardDao.findById(id);
//		   System.out.println(id); 
//		   System.out.println(boardId);
	      boardDao.deleteById(id);
	      return null;
	   }
  

}