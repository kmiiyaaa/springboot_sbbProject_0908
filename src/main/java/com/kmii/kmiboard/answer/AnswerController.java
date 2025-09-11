package com.kmii.kmiboard.answer;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.kmii.kmiboard.question.Question;import com.kmii.kmiboard.question.QuestionForm;
import com.kmii.kmiboard.question.QuestionService;
import com.kmii.kmiboard.user.SiteUser;
import com.kmii.kmiboard.user.UserService;

import jakarta.validation.Valid;

@RequestMapping("/answer")
@Controller
public class AnswerController {

	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private AnswerService answerService;
	
	@Autowired
	private UserService userService;
	
	@PreAuthorize("isAuthenticated()")  // 로그인한(인정받은) 유저만 해당 메서드 실행되게 함
	@PostMapping(value="/create/{id}") // 답변 등록 요청 -> 넘어오는 파라미터 값 : 부모 질문글의 번호
	public String createAnswer(Model model, @PathVariable("id") Integer id, @Valid AnswerForm answerForm, BindingResult bindingResult, Principal principal) { //pricipal객체에 - 로그인한 사용자 명 알 수 있다
		Question question = questionService.getQuestion(id);
		
		// principal.getName(); //로그인한 유저의 ID 얻기
		SiteUser siteUser = userService.getUser(principal.getName());
		//principal.getName() = (String) session.getAttribute("sid"); - 세션에 올라가있는 로그인한 유저의 아이디 가져오기
		
		if(bindingResult.hasErrors()) {
			model.addAttribute("question", question);  // question 수동으로 보내주기
			return "question_detail";
		}
		
		
		Answer answer =  answerService.create(question, answerForm.getContent(),siteUser);  //DB에 답변 등록
		
	
		return String.format("redirect:/question/detail/%s#answer_%s", id, answer.getId() );  // 뒤 %s - 내가 단 답글의 번호
		
	}
	
	
	@PreAuthorize("isAuthenticated()") //로그인한(인증 받은) 유저만 해당 메서드가 실행되게 함
	@GetMapping(value = "/modify/{id}") //답변을 수정할 수 있는 form으로 이동하는 요청
	public String answerModify(AnswerForm answerForm, @PathVariable("id") Integer id, Principal principal) {
		Answer answer = answerService.getAnswer(id);
		
		//글쓴 유저와 로그인한 유저의 동일 여부를 다시한번 검증->수정 권한 검증
		if(!answer.getAuthor().getUsername().equals(principal.getName())) { //참->수정 권한 없음
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
		}
		
		answerForm.setContent(answer.getContent()); //model로 보내지 않아도 answerForm 이 answer_form에 전송
		return "answer_form";
	}
	
	@PreAuthorize("isAuthenticated()") //로그인한(인증 받은) 유저만 해당 메서드가 실행되게 함
	@PostMapping(value = "/modify/{id}") //질문 수정완료하기 위한 요청->DB 수정 요청
	public String answerModify(@Valid AnswerForm answerForm, BindingResult bindingResult, @PathVariable("id") Integer id, Principal principal) {
		if(bindingResult.hasErrors()) {
			return "answer_form";
		}
		Answer answer = answerService.getAnswer(id); //수정하기 전의 원본 답변 엔티티
		if(!answer.getAuthor().getUsername().equals(principal.getName())) { //참->수정 권한 없음
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
		}
		
		answerService.modify(answer, answerForm.getContent()); //수정 완료
		
		return String.format("redirect:/question/detail/%s", answer.getQuestion().getId());
		//redirect->부모글(해당 답변이 달린 질문글)의 번호로 이동
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping(value = "/delete/{id}")
	public String questionDelete(@PathVariable("id") Integer id, Principal principal) {
		
		Answer answer = answerService.getAnswer(id); //삭제할 답변 엔티티
		
		//글쓴 유저와 로그인한 유저의 동일 여부를 다시한번 검증->수정 권한 검증
		if(!answer.getAuthor().getUsername().equals(principal.getName())) { //참->수정 권한 없음
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
		}
		
		answerService.delete(answer); //답변 글 삭제
		
		return String.format("redirect:/question/detail/%s", answer.getQuestion().getId());
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping(value = "/vote/{id}")
	public String answerVote(@PathVariable("id") Integer id, Principal principal) {
		
		Answer answer = answerService.getAnswer(id);
		
		SiteUser siteUser = userService.getUser(principal.getName());
		//로그인한 유저의 아이디로 유저 엔티티 조회하기
		
		answerService.vote(answer, siteUser);
		
		return String.format("redirect:/question/detail/%s", answer.getQuestion().getId());
		//해당 답변이 달린 원글(질문글)로 이동
	}
	
}
