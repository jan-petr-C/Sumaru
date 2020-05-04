package sumaru.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sumaru.persistence.service.UserService;
import sumaru.web.domain.UserDetails;

@Controller
@RequestMapping("/registration")
public class RegistrationSiteController {

	@Autowired
	BCryptPasswordEncoder encoder;

	@Autowired
	private UserService userService;

	@RequestMapping(method = RequestMethod.GET)
	public String getCurrentMenu(Model model) {

		model.addAttribute("userDetails", new UserDetails());

		return "/registration";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String doTest(@ModelAttribute UserDetails userDetails, Model model) {

		userDetails.setPassword(encoder.encode(userDetails.getPassword()));
		userService.saveNewUser(userDetails);
		
		return "redirect:/profile";
	}

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public String authorize() {

		userService.authorize();

		return "redirect:/profile";
	}

}
