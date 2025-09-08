package com.kmii.kmiboard.question;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class QuestionController {
	
	@Autowired
	private QuestionRepository questionRepository;
	
	@GetMapping(value="/") //root 요청 처리
	public String root() {
		return "redirect:/question/list";
	}

	@GetMapping(value="/question/list")
	// @ResponseBody  ->  return 옆에 적어준 문자열 그대로 화면에 출력
	public String list(Model model) {
		
		List<Question> questionList = questionRepository.findAll(); // 모든 질문글 불러오기
		model.addAttribute("questionList", questionList);
		
		
		return "question_list";
	}
	

}
