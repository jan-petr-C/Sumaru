package sumaru.web.controller;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/403")
public class DeniedSiteController {

	@RequestMapping(method = RequestMethod.GET)
	public String solveDenial(Model model) {

		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		model.addAttribute("name", auth.getName());

		Collection<? extends GrantedAuthority> authorities = auth
				.getAuthorities();

		for (GrantedAuthority grantedAuthority : authorities) {
			if (grantedAuthority.getAuthority().equals("ROLE_PRE")) {

				return "pre";
			}

			if (grantedAuthority.getAuthority().equals("ROLE_ADMIN"))
				return "redirect:/admin";
		}

		return "/403";
	}

}
