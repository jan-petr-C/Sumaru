package sumaru.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sumaru.persistence.service.Adservice;

@Controller
@RequestMapping
public class SiteController {

	@Autowired
	private Adservice adService;

	@RequestMapping(value = { "/", "/home" })// userDao.insert(User.fromUserDetails(userDetails));
	public String geHome(Model model) {

		model.addAttribute("recentAds", adService.getRecentAds());
		getUser(model);

		return "/home";
	}

	@RequestMapping(value = "/podminky")
	public String getPodminky(Model model) {

		getUser(model);

		return "/podminky";
	}

	@RequestMapping(value = "/odkazy")
	public String getOdkazy(Model model) {

		getUser(model);

		return "/odkazy";
	}

	@RequestMapping(value = "/oprovozovateli")
	public String getOprovozovateli(Model model) {

		getUser(model);

		return "/oProvozovateli";
	}

	@RequestMapping(value = { "/katalog" })
	public String geKatalog(Model model) {

		model.addAttribute("allCategories", adService.allCategories());
		getUser(model);

		return "/katalog";
	}

	@RequestMapping("/katalog/category")
	public String Category(@RequestParam(value = "id") int id, Model model) {

		getUser(model);
		model.addAttribute("adsInCategory", adService.getAdsInCategory(id));
		model.addAttribute("element", adService.getCategory(id).getElement());

		return "/katalogCategory";
	}

	private void getUser(Model model) {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		model.addAttribute("name", auth.getName());
		model.addAttribute("logged", !(auth.getName().equals("anonymousUser")));

	}

}
