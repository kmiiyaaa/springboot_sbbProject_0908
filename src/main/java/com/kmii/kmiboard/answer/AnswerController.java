package com.kmii.kmiboard.answer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kmii.kmiboard.question.Question;import com.kmii.kmiboard.question.QuestionForm;
import com.kmii.kmiboard.question.QuestionService;

import jakarta.validation.Valid;

@RequestMapping("/answer")
@Controller
public class AnswerController {

	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private AnswerService answerService;
	
	@PostMapping(value="/create/{id}") // 답변 등록 요청 -> 넘어오는 파라미터 값 : 부모 질문글의 번호
	public String createAnswer(Model model, @PathVariable("id") Integer id, @Valid AnswerForm answerForm, BindingResult bindingResult) {
		Question question = questionService.getQuestion(id);
		
		if(bindingResult.hasErrors()) {
			model.addAttribute("question", question);  // question 수동으로 보내주기
			return "question_detail";
		}
		
		
		answerService.create(question, answerForm.getContent());
		
		
		//TODO : 답변을 저장한다
		return String.format("redirect:/question/detail/%s", id);
	}
	
}
