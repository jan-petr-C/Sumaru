package sumaru.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import sumaru.persistence.service.Adservice;
import sumaru.persistence.service.UserService;
import sumaru.web.domain.UserDetails;

@Controller
@RequestMapping("/admin")
public class AdminSiteController {

	@Autowired
	private UserService userService;

	@Autowired
	private Adservice adService;

	@RequestMapping(method = RequestMethod.GET)
	public String getAllUsers(Model model) {

		model.addAttribute("allUsers", userService.getAllUsers());
		model.addAttribute("name", "admin");

		return "/admin";
	}
	
	@RequestMapping("/categories")
	public String getAllCategories(Model model) {

		model.addAttribute("allCategories", adService.allCategories());
		model.addAttribute("name", "admin");

		return "/adminCategories";
	}
	
	@RequestMapping("/user")
	public String getUser(@RequestParam(value = "id") long id, Model model) {
		UserDetails user = userService.loadUserById(id);
		
		model.addAttribute("usersAds", adService.adminAll(user.getId()));
		model.addAttribute("name", "admin");
		model.addAttribute("user_name", user.getName());

		return "/adminUser";
	}

	@RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
	public String deleteUser(@RequestParam(value = "id") long id) {

		userService.deleteUser(id);

		return "redirect:/admin";
	}

	@RequestMapping(value = "/ads", method = RequestMethod.GET)
	public String getAllAds(Model model) {

		model.addAttribute("allAds", adService.getRecentAds());
		model.addAttribute("name", "admin");

		return "/adminAds";
	}

	@RequestMapping(value = "/deleteAd", method = RequestMethod.POST)
	public String removeAd(@RequestParam("ad_id") long id) {

		adService.removeAd(id);

		return "redirect:/admin/ads";
	}
	
	@RequestMapping(value = "/deleteCategory", method = RequestMethod.POST)
	public String removeCategory(@RequestParam("cate_id") int id) {

		adService.removeCategory(id);

		return "redirect:/admin/categories";
	}
	
	@RequestMapping(value = "/renameCategory", method = RequestMethod.POST)
	public String renameCategory(@RequestParam("cate_id") int id, @RequestParam("cate_element") String element) {

		adService.saveCategory(id,element);

		return "redirect:/admin/categories";
	}
		
	@RequestMapping(value = "/adCategory", method = RequestMethod.POST)
	public String adtegory(@RequestParam("cate_element") String element) {

		adService.adCategory(element);

		return "redirect:/admin/categories";
	}	
	
	
	
	

}
