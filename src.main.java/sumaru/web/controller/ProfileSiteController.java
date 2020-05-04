package sumaru.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import sumaru.persistence.service.Adservice;
import sumaru.persistence.service.UserService;
import sumaru.web.domain.AdDetails;
import sumaru.web.domain.UserDetails;


@Controller
@RequestMapping("/profile")
public class ProfileSiteController {

	@Autowired
	private Adservice adService;

	@Autowired
	private UserService userService;
	
	@Autowired
	BCryptPasswordEncoder encoder;

	@RequestMapping(method = RequestMethod.GET)
	public String getCurrentMenu(Model model) {

		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();

		model.addAttribute("name", auth.getName());
		model.addAttribute("adDetails", new AdDetails());
		model.addAttribute("usersAds", adService.all());
	//	model.addAttribute("categories", stubList());
		model.addAttribute("categories", adService.allCategories());
	//model.addAttribute("category", new CategoryDetails());		

		return "/profile";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String saveAd(@ModelAttribute AdDetails adDetails, @RequestParam int category_id) {
				
		adService.saveAd(adDetails, category_id);

		return "redirect:/profile";
	}

	@RequestMapping(value = "/deleteAd", method = RequestMethod.POST)
	public String removeAd(@RequestParam("ad_id") long id) {

		adService.removeAd(id);

		return "redirect:/profile";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String editProfile(Model model) {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();

		model.addAttribute("userDetails",userService.loadUserByUsername(auth.getName()));
		model.addAttribute("name", auth.getName());

		return "profileEdit";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String saveEditedProfile(@ModelAttribute UserDetails userDetails) {

		userService.saveUser(userDetails);

		return "redirect:/profile";
	}
/*	
	private List<String> stubList(){
		List<String>categories = new LinkedList<String>();
		categories.add("Kovář");
		categories.add("Malíř");
		return categories;
		
	}
*/	
	
}
