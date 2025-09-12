package com.kmii.kmiboard;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	
	@GetMapping(value="/") // cloud용 root 요청 처리
	//@GetMapping(value="/") //local용 root 요청 처리
	public String root() {
		return "redirect:/question/list";
	}

}
