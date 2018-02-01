package net.slipp.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.slipp.domain.User;
import net.slipp.domain.UserRepository;

@Controller
@RequestMapping("/users")
public class UserController {

	// private List<User> users = new ArrayList<>();

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/loginForm")
	public String loginForm() {
		return "/user/login";
	}

	@PostMapping("/login")
	// session을 사용 하기 위해 HttpSession을 이용한다.
	public String login(String userId, String password, HttpSession session) {

		// userId 정보를 이용하여 조회하기 위하여 UserRepository에 findbyUserId 를 만들어 사용한다
		// User user = userRepository.findbyUserId(userId);
		User user = userRepository.findByUserId(userId);
		// user에 정보가 없다면
		if (user == null) {

			System.out.println("Login Failure 1 !!!!!");

			System.out.println(user);

			return "redirct:/users/loginForm";
		}

		// 패스워드가 일치하지 않다면, getPassword User에 만들어 줌
		// if (!password.equals(user.getPassword())) {
		if (!user.matchPassword(password)) {

			System.out.println("Login Failure 2 !!!!!");

			System.out.println(user.getPassword());

			return "redirct:/users/loginForm";

		}

		System.out.println("Login Success  !!!!!");
		// session에 user의 정보를 담는다
		session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, user);

		Object sessionedUser = session.getAttribute("sessionedUser");

		System.out.println(sessionedUser);

		return "redirect:/";

	}

	@GetMapping("logout")
	public String logout(HttpSession session) {

		session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);

		return "redirect:/";
	}

	@GetMapping("/form")
	public String form() {
		return "/user/form";
	}

	@PostMapping("")
	public String create(User user) {
		// System.out.println("UserId : " + userId);
		System.out.println("User : " + user);
		// users.add(user);
		userRepository.save(user);
		return "redirect:/users";
	}

	@GetMapping("")
	public String list(Model model) {
		model.addAttribute("users", userRepository.findAll());
		return "/user/list";
	}

	@GetMapping("/{id}/form")
	public String updateform(@PathVariable Long id, Model model, HttpSession session) {

		Object tempUser = session.getAttribute("sessionedUser");

		System.out.println(tempUser);

		// if(tempUser == null){

		if (!HttpSessionUtils.isLoginUser(session)) {

			return "redirect:/users/loginForm";

		}
		// User sessionedUser = (User)tempUser;
		User sessionedUser = HttpSessionUtils.getUserFromSession(session);

		// if (!id.equals(sessionedUser.getId())){
		if (!sessionedUser.matchId(id)) {
			throw new IllegalStateException("You can not update another user !!!.");
		}

		User user = userRepository.findOne(id);
		model.addAttribute("user", user);
		return "/user/updateForm";
	}

	// @PostMapping("/{id}")
	@PutMapping("/{id}")
	public String update(@PathVariable Long id, User updatedUser, HttpSession session) {

		Object tempUser = session.getAttribute("sessionedUser");

		System.out.println(tempUser);

		// if(tempUser == null){

		if (!HttpSessionUtils.isLoginUser(session)) {

			return "redirect:/users/loginForm";

		}
		// User sessionedUser = (User)tempUser;
		User sessionedUser = HttpSessionUtils.getUserFromSession(session);
		if (!sessionedUser.matchId(id)) {
			throw new IllegalStateException("You can not update another user !!!.");
		}

		User user = userRepository.findOne(id);
		user.update(updatedUser);
		userRepository.save(user);
		return "redirect:/users";
	}
}
