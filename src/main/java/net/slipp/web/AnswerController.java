package net.slipp.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.slipp.domain.Answer;
import net.slipp.domain.AnswerRepository;
import net.slipp.domain.Question;
import net.slipp.domain.QuestionRepository;
import net.slipp.domain.User;

//answer는 항상 quesion id가 필요하다 종속적이다.

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {
	
	//AutoWired 해 주어야 스프링 프레임 웍이 answerRpository의 인스턴스를 만들어서
	  // 사용할수 있게 해 준다.
	
	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private AnswerRepository answerRpository;

	@PostMapping("")
	public String create(@PathVariable Long questionId, String contents, HttpSession session) {

		if (!HttpSessionUtils.isLoginUser(session)) {
			return "/users/loginForm";
		}

		User loginUser = HttpSessionUtils.getUserFromSession(session);
        
		Question question = questionRepository.findOne(questionId);
		Answer answer = new Answer(loginUser, question, contents);
		answerRpository.save(answer);
		
		//Answer answer = new Answer(loginUser, contents);
        //%d 문자열 인경우, 숫자인 경우 %s 쓰면 된다.   
		return String.format("redirect:/questions/%d", questionId);
	}

}
