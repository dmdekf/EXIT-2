package com.web.blog.controller.account;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.blog.dao.user.UserDao;
import com.web.blog.model.BasicResponse;
import com.web.blog.model.user.SignupRequest;
import com.web.blog.model.user.User;
import com.web.blog.service.JwtService;
import com.web.blog.service.MailSendService;
import com.web.blog.service.UserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
      @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
      @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })
//port 2개를 열어놓을때 ... Vue를 3000번으로 열어줘야한다
@CrossOrigin(origins = { "*" })
@RestController
public class AccountController {

   @Autowired
   UserDao userDao;

   @Autowired
   private JwtService jwtService;

   @Autowired
   private MailSendService mailSendService;

   @Autowired
   private UserService userService;
   
   @GetMapping("/account/login")
   @ApiOperation(value = "로그인")
   public Object login(@RequestParam(required = true) final String email,
         @RequestParam(required = true) final String password,HttpServletResponse res) {

      Optional<User> userOpt = userDao.findUserByEmailAndPassword(email, password);

      ResponseEntity response = null;
      final BasicResponse result = new BasicResponse();
      if (userOpt.isPresent()) {
         User loginUser = userOpt.get();

         String token = jwtService.create(loginUser);
         
         res.setHeader("jwt-auth-token", token);

         result.status = true;
         result.data = "success";
         result.object = loginUser;
         
         response = new ResponseEntity<>(result, HttpStatus.OK);
      } else {
         response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
      }

      return response;
   }

   
   @PostMapping("/account/signup")
   @ApiOperation(value = "가입하기")
   public Object signup(@Valid @RequestBody SignupRequest request) {
      ResponseEntity response = null;
      // 이메일, 닉네임 중복처리 필수
      Optional<User> userNickname = userDao.findUserByUid(request.getNickname());
      Optional<User> userEmail = userDao.findUserByEmail(request.getEmail());
      final BasicResponse result = new BasicResponse();

      if(userNickname.isPresent()) {//닉네임(별명) 중복시
         result.status = false;
         result.data = "nickname";
         response = new ResponseEntity<>(result,HttpStatus.BAD_REQUEST);
      }else if(userEmail.isPresent()) {//이메일 중복시
         result.status = false;
         result.data = "email";
         response = new ResponseEntity<>(result,HttpStatus.BAD_REQUEST);
      }else {// 이메일 혹은 닉네임이 중복되지 않으면 회원가입 후 이메일 발송
         LocalDateTime currentTime = LocalDateTime.now();
         User u = new User(request.getNickname(), request.getPassword(), request.getEmail(),currentTime,null);
         u = userDao.save(u);

         String key = mailSendService.getKey(false,20);
         u.setUserkey(key);
         userDao.save(u);
         mailSendService.mailSendWithUserKey(u.getEmail(), u.getUid(), key);


         result.status = true;
         result.data = "success";
         response = new ResponseEntity<>(result,HttpStatus.OK);
      }

      return response;
   }
   @PostMapping("/user/signin")
   public ResponseEntity<Map<String, Object>> signin(@RequestBody User user, HttpServletResponse res) {
      Map<String, Object> resultMap = new HashMap<>();
      HttpStatus status = null;
      try {
         User loginUser = userService.signin(user.getEmail(), user.getPassword());
         // 로그인 성공했다면 토큰을 생성한다.
         String token = jwtService.create(loginUser);
         // 토큰 정보는 request의 헤더로 보내고 나머지는 Map에 담아주자.
         res.setHeader("jwt-auth-token", token);
         // resultMap.put("auth_token", token);

         resultMap.put("status", true);
         resultMap.put("data", loginUser);
         status = HttpStatus.ACCEPTED;
      } catch (RuntimeException e) {
         resultMap.put("message", e.getMessage());
         status = HttpStatus.INTERNAL_SERVER_ERROR;
      }
      return new ResponseEntity<Map<String, Object>>(resultMap, status);
   }
   @PostMapping("/user/info")
   public ResponseEntity<Map<String, Object>> getInfo(HttpServletRequest req, @RequestBody User user) {
      Map<String, Object> resultMap = new HashMap<>();
      HttpStatus status = null;
      try {
         // 사용자에게 전달할 정보이다.
         String info = userService.getServerInfo();
         // 보너스로 토큰에 담긴 정보도 전달해보자. 서버에서 토큰을 사용하는 방법임을 알 수 있다.
         resultMap.putAll(jwtService.get(req.getHeader("jwt-auth-token")));
         
         resultMap.put("status", true);
         resultMap.put("info", info);
         resultMap.put("request_body", user);
         status = HttpStatus.ACCEPTED;
      } catch (RuntimeException e) {
         resultMap.put("message", e.getMessage());
         status = HttpStatus.INTERNAL_SERVER_ERROR;
      }
      return new ResponseEntity<Map<String, Object>>(resultMap, status);
   }
   @GetMapping("/account/key_alter")
    @ApiOperation(value="이메일 인증 완료")
    public void keyAlterConfirm(HttpServletResponse httpServletResponse,@RequestParam String uid,@RequestParam String userkey) throws IOException {
        Optional<User> userOpt = userDao.findUserByUidAndUserkey(uid, userkey);

    ResponseEntity response = null;
    if (userOpt.isPresent()) {
        User user = userOpt.get();
        user.setUserkey("Y");
        userDao.save(user);
        final BasicResponse result = new BasicResponse(); 
        result.status = true;
        result.data = "success";
        response = new ResponseEntity<>(result, HttpStatus.OK);
    } else {
        response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
    System.out.println("인증완료");
    httpServletResponse.sendRedirect("http://i3a501.p.ssafy.io/");
   }
   @PostMapping("/user/socialsignin")
   public ResponseEntity<Map<String, Object>> socialsignin(@RequestBody String email, HttpServletResponse res) {
       Map<String, Object> resultMap = new HashMap<>();
       HttpStatus status = null;
       System.out.println(email);
       //email = email.replace("\"", "");
       //email = email.replace("}", "");
       email = email.trim();
       System.out.println(email.length());
       System.out.println(email.indexOf("+")+1);
       String email2 = email.substring(email.indexOf("+")+1,email.indexOf("}")-1);
       System.out.println(email2);
       
       String uid = email2.substring(0,email2.indexOf("@"));
       uid = uid.length()>8?uid.substring(0, 9):uid;
       System.out.println(uid);
       Optional<User> u = userDao.findUserByUidOrEmail(uid, email2);
       if(u.isPresent()) {
          System.out.println("토큰 만들기");
           String token = jwtService.create(u.get());
           System.out.println("가지고와서 ");
           res.setHeader("jwt-auth-token", token);
           resultMap.put("status", true);
           resultMap.put("data", u.get());
           System.out.println("저장하고 리턴");
           status = HttpStatus.ACCEPTED;
       }else {
          
           User user = new User(uid,null, email2, null,"Y");
           System.out.println(user);
           try {
               userDao.save(user);
               String token = jwtService.create(user);
               System.out.println(token);
               res.setHeader("jwt-auth-token", token);
               resultMap.put("status", true);
               resultMap.put("data", user);
               status = HttpStatus.ACCEPTED;
           }catch(Exception e) {
               resultMap.put("status", false);
               resultMap.put("data", "중복된 메일입니다. ");
               status = HttpStatus.BAD_REQUEST;
           }
           
       }
       return new ResponseEntity<Map<String, Object>>(resultMap, status);
   }
}