package net.slipp.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import net.slipp.domain.QuestionRepository;

@Controller
public class HomeController {
	
	@Autowired
<<<<<<< HEAD
	private QuestionRepository questionRpository;
	
	@GetMapping("")
	public String home(Model model){
		
		//"questions"라는 이름으로 모델에 데이터를 담아서 전달
		
		model.addAttribute("questions", questionRpository.findAll());
			
=======
	private QuestionRepository questionRepository;
	
	@GetMapping("")
	public String home(Model model){
			model.addAttribute("questions", questionRepository.findAll());
>>>>>>> new
		return "index";
	}

}
