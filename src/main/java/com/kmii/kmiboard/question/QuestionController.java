package com.kmii.kmiboard.question;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RequestMapping("/question")
@Controller
public class QuestionController {
	
	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private QuestionService questionService;
	
	
	@GetMapping(value="/") //root 요청 처리
	public String root() {
		return "redirect:/question/list";
	}

	@GetMapping(value="/list")
	// @ResponseBody  ->  return 옆에 적어준 문자열 그대로 화면에 출력
	public String list(Model model) {
		
		// List<Question> questionList = questionRepository.findAll(); // 모든 질문글 불러오기
		List<Question> questionList = questionService.getList();
		model.addAttribute("questionList", questionList);
		
		
		return "question_list";
		
	}
	
	@GetMapping(value="/detail/{id}")  // 파라미터이름 없이 값만 넘어 왔을때 처리
	public String detail(Model model, @PathVariable("id") Integer id) {
		
		//service에 4(질문글 번호) 넣어서 호출
		Question question = questionService.getQuestion(id);
		model.addAttribute("question", question);
		return "question_detail"; // 타임리프 html의 이름
	}
	
	@GetMapping(value="/create")  // 게시판 리스트에서 버튼눌렀을땐 - 글작성 등록 폼만 매핑해주는 메서드( get)
	public String questionCreate() {
		return "question_form";
	}
	
	@PostMapping(value="/create")  // 글 작성후 완료 버튼눌렀을때 -  질문 내용을 DB에 저장하는 메서드 - post
	public String questionCreate(@RequestParam(value="subject") String subject,@RequestParam(value="content") String content) {  // ()안에  인수 넣어서 이름 바꾸지 않아도 오버라이딩해서 사용가능
		//@RequestParam("subject") String subject -> String subject =  request.getParameter("subject")
		//@RequestParam("content") String content -> String content =  request.getParameter("content")
		
		//TODO: 질문을 DB에 저장하기
		
		questionService.create(subject, content);  // 질문 DB에 등록
		return "redirect:/question/list"; // 질문 리스트로 이동 -> 반드시 redirect
	}
	

}
