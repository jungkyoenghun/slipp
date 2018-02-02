package net.slipp.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.slipp.domain.Question;
import net.slipp.domain.QuestionRepository;
import net.slipp.domain.User;

@Controller
@RequestMapping("/questions")
public class QuestionController {
	
	//spring 프레임 워크한테 나 지금 QuesionRepository가 필요한데 너가 관리하고 있는
	 // QuesionRepository의 인스턴스를 전달해 줘, 내부적으로 어떻게 구현체가 만들어지고
	 // 하는지는 알 필요가 현재 로서는 없다. 오토와이어드 안해주면 널 포인트 익셥션 난다.
	@Autowired
	private QuestionRepository questionRepository;

	@GetMapping ("/form")
	public String form(HttpSession session){
		System.out.println("this is qna form !!!!!");
		if(!HttpSessionUtils.isLoginUser(session)){
			return "/users/loginForm";
			

		}
		
		return "/qna/form";
	}
	
	@PostMapping("")
	public String create(String title, String contents, HttpSession session){
		if(!HttpSessionUtils.isLoginUser(session)){
			return "/users/loginForm";
		}
		System.out.println("this point !!!!");
		
		User sessionUser = HttpSessionUtils.getUserFromSession(session);
//		Question newQuestion = new Question(sessionUser.getUserId(), title, contents);
		Question newQuestion = new Question(sessionUser, title, contents);
		questionRepository.save(newQuestion);
		
		
		System.out.println("this point !!!!");
		return "redirect:/";
	}
	
}
