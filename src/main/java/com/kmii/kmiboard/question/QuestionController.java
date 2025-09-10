package com.kmii.kmiboard.question;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.kmii.kmiboard.answer.AnswerForm;
import com.kmii.kmiboard.user.SiteUser;
import com.kmii.kmiboard.user.UserService;

import jakarta.validation.Valid;


@RequestMapping("/question")   // 접두사
@Controller
public class QuestionController {
	
	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private UserService userService;
	
	
	

	/* 페이징용 리스트
	@GetMapping(value="/list")
	// @ResponseBody  ->  return 옆에 적어준 문자열 그대로 화면에 출력
	public String list(Model model, @RequestParam(value="page", defaultValue = "0") int page) {  //defalutValue -> 페이지 null값으로 들어왔을때 처리 가능
		
		// List<Question> questionList = questionRepository.findAll(); // 모든 질문글 불러오기
		//List<Question> questionList = questionService.getList();
		Page<Question> paging = questionService.getList(page);
		//게시글 10개식 자른 리스트 -> 페이지당 2개
		model.addAttribute("paging", paging);
		
		
		return "question_list";
		
	} */
	
	@GetMapping(value="/list")
	// @ResponseBody  ->  return 옆에 적어준 문자열 그대로 화면에 출력
	public String list(Model model) {  //defalutValue -> 페이지 null값으로 들어왔을때 처리 가능
		
		// List<Question> questionList = questionRepository.findAll(); // 모든 질문글 불러오기
		List<Question> questionList = questionService.getList();
		model.addAttribute("paging", questionList);
		
		
		return "question_list";
		
	}
	
	@GetMapping(value="/detail/{id}")  // 파라미터이름 없이 값만 넘어 왔을때 처리
	public String detail(Model model, @PathVariable("id") Integer id , AnswerForm answerForm) {
		
		//service에 4(질문글 번호) 넣어서 호출
		Question question = questionService.getQuestion(id);
		model.addAttribute("question", question);
		return "question_detail"; // 타임리프 html의 이름
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping(value="/create")  // 게시판 리스트에서 버튼눌렀을땐 - 글작성 등록 폼만 매핑해주는 메서드( get)
	public String questionCreate(QuestionForm questionForm) {
		return "question_form";
	}
	
	
//validation 전 사용	
//	@PostMapping(value="/create")  // 글 작성후 완료 버튼눌렀을때 -  질문 내용을 DB에 저장하는 메서드 - post
//	public String questionCreate(@RequestParam(value="subject") String subject,@RequestParam(value="content") String content) { 
//		//@RequestParam("subject") String subject -> String subject =  request.getParameter("subject")
//		//@RequestParam("content") String content -> String content =  request.getParameter("content")
//		
//		//TODO: 질문을 DB에 저장하기
//		
//		questionService.create(subject, content);  // 질문 DB에 등록
//		return "redirect:/question/list"; // 질문 리스트로 이동 -> 반드시 redirect
//	}
	
	//validation 
	@PreAuthorize("isAuthenticated()")  //로그인 - 인증받지 않은 유저는 해당 메서드 호출 불가 - 자동으로 로그인페이지로 보내버린다 //form->action으로 넘어오지 않으면 권한인증이 안됨
	@PostMapping(value="/create")  // 글 작성후 완료 버튼눌렀을때 -  질문 내용을 DB에 저장하는 메서드 - post
	public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal ) { 
		
		
		if(bindingResult.hasErrors()) {  // 참이면 -> 유효성 체크에서 에러 발생
			return "question_form";  // 에러 발생시 다시 질문 등록 폼으로 이동, 다 묶어서 보낸다(에러,데이터값)
		}
		
		SiteUser siteUser = this.userService.getUser(principal.getName());
		
		questionService.create(questionForm.getSubject(), questionForm.getContent(),siteUser);  // 질문 DB에 등록
		
		return "redirect:/question/list"; // 질문 리스트로 이동 -> 반드시 redirect
	}
	
	@PreAuthorize("isAuthenticated()") 
	@GetMapping(value="/modify/{id}")
	public String questionModify(QuestionForm questionForm, @PathVariable("id") Integer id, Principal principal) {
		
		Question question = questionService.getQuestion(id);  //id에 해당하는ㄴ 엔티티가 반환 -> 수정하려는 글의 엔티니
		
		//글쓴 유저와 로그인한 유저의 동일 여부를 다시한번 검증 - 수정권한 검증
		if(!question.getAuthor().getUsername().equals(principal.getName())) {  //참이면 - 수정권한이 없음 , 에러처리
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
		}
		
		//question_form에 questionForm의 subject와 value값으로 출력하는 기능이 이미구현되어 있으므로
		// 해당 폼을 재활용하기위해 questionForm에 question_form 필드값을 저장하여 전송
		questionForm.setSubject(question.getSubject());
		questionForm.setContent(question.getContent());
		
		
		return "question_form";
	}
	
	
	
	@PreAuthorize("isAuthenticated()") 
	@PostMapping(value="/modify/{id}")
	public String questionModify(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal,
			@PathVariable("id") Integer id) {
		
		if(bindingResult.hasErrors()) {
			return "question_form";
			
		}
		
		Question question = questionService.getQuestion(id);
		questionService.modify(question, questionForm.getSubject(), questionForm.getContent());
		
		//글쓴 유저와 로그인한 유저의 동일 여부를 다시한번 검증 - 수정권한 검증
		if(!question.getAuthor().getUsername().equals(principal.getName())) {  //참이면 - 수정권한이 없음 , 에러처리
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
		}
				
		
		return String.format("redirect:/question/detail/%s",id);
		
		
		
		
	}
	

}
