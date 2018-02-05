package net.slipp.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.slipp.domain.Question;
import net.slipp.domain.QuestionRepository;
import net.slipp.domain.User;

@Controller
@RequestMapping("/questions")
public class QuestionController {

	// spring 프레임 워크한테 나 지금 QuesionRepository가 필요한데 너가 관리하고 있는
	// QuesionRepository의 인스턴스를 전달해 줘, 내부적으로 어떻게 구현체가 만들어지고
	// 하는지는 알 필요가 현재 로서는 없다. 오토와이어드 안해주면 널 포인트 익셥션 난다.
	@Autowired
	private QuestionRepository questionRepository;

	@GetMapping("/form")
	public String form(HttpSession session) {
		System.out.println("this is qna form !!!!!");
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "/users/loginForm";

		}

		return "/qna/form";
	}

	@PostMapping("")
	public String create(String title, String contents, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "/user/login";
		}
		System.out.println("this point !!!!");

		User sessionUser = HttpSessionUtils.getUserFromSession(session);
		// Question newQuestion = new Question(sessionUser.getUserId(), title,
		// contents);
		Question newQuestion = new Question(sessionUser, title, contents);
		questionRepository.save(newQuestion);

		System.out.println("this point !!!!");
		return "redirect:/";
	}

	@GetMapping("/{id}")
	public String show(@PathVariable Long id, Model model) {
		// Question question = questionRepository.findOne(id);
		// model.addAttribute("question", question);

		model.addAttribute("question", questionRepository.findOne(id));
		return "/qna/show";
	}

	@GetMapping("/{id}/form")
	public String updateForm(@PathVariable Long id, Model model, HttpSession session) {

		try {
			Question question = questionRepository.findOne(id);
			hasPermission(session, question);
			model.addAttribute("question", question);
			return "/qna/updateForm";

		} catch (IllegalStateException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "/user/login";
		}

		// if(!HttpSessionUtils.isLoginUser(session)){
		// return "/users/loginForm";
		// }
		//
		// User loginUser = HttpSessionUtils.getUserFromSession(session);
		//
		// Question question = questionRepository.findOne(id);
		//
		// // if(sessionedUser.equals(question, getUser()))
		//
		// //객체가 가지고 있는 데이터를 꺼내지 말고
		// // question 라는 객체에 메세지를 보내서 현재 로그인한 사용자와
		// // 일치하는지 확인 하는 것이다.
		// if(!question.isSameWriter(loginUser)){
		// return "/users/loginForm";
		// }

		//model.addAttribute("question", question);

		//return "/qna/updateForm";
	}

	private boolean hasPermission(HttpSession session, Question question) {

		if (!HttpSessionUtils.isLoginUser(session)) {
			throw new IllegalStateException("로그인이 필요합니다.");
		}

		User loginUser = HttpSessionUtils.getUserFromSession(session);

		if (!question.isSameWriter(loginUser)) {
			throw new IllegalStateException("자신이 쓴 글만 수정, 삭제가 가능합니다.");
		}

		return true;
	}

	@PutMapping("/{id}")
	public String update(@PathVariable Long id, String title, String contents,Model model, HttpSession session) {


		try {
			Question question = questionRepository.findOne(id);
			hasPermission(session, question);
			question.update(title, contents);
			questionRepository.save(question);
			return String.format("redirect:/questions/%d", id);

		} catch (IllegalStateException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "/user/login";
		}
		
		
		//		if (!HttpSessionUtils.isLoginUser(session)) {
//			return "/users/loginForm";
//		}
//
//		User loginUser = HttpSessionUtils.getUserFromSession(session);
//
//		Question question = questionRepository.findOne(id);
//
//		if (!question.isSameWriter(loginUser)) {
//			return "/users/loginForm";
//		}

		// Question question = questionRepository.findOne(id);
//		question.update(title, contents);
	//	questionRepository.save(question);

	//	return String.format("redirect:/questions/%d", id);
	}

	@DeleteMapping("/{id}")
	public String delete(@PathVariable Long id, Model model, HttpSession session) {
		
		
		try {
			Question question = questionRepository.findOne(id);
			hasPermission(session, question);
			model.addAttribute("question", question);
			questionRepository.delete(id);
			return "redirect:/";

		} catch (IllegalStateException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "/user/login";
		}
		
		

//		System.out.println("Enter this is !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//
//		if (!HttpSessionUtils.isLoginUser(session)) {
//			return "/users/loginForm";
//		}
//
//		User loginUser = HttpSessionUtils.getUserFromSession(session);
//
//		System.out.println("loginUser : " + loginUser);
//
//		Question question = questionRepository.findOne(id);
//
//		System.out.println("rrrrrrr :" + question.isSameWriter(loginUser));
//
//		if (!question.isSameWriter(loginUser)) {
//			return "/users/loginForm";
//		}
//
//		questionRepository.delete(id);
//		return "redirect:/";

	}

}
