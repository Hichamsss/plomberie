package be.plomberie.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import be.plomberie.demo.model.UrgenceRequest;

@Controller
public class UrgenceController {

	@GetMapping("/urgence")
	public String showUrgenceForm(Model model) {
	    model.addAttribute("urgenceForm", new UrgenceRequest()); // ✅
	    return "urgence/main";
	}

	@PostMapping("/urgence")
	public String handleUrgenceForm(@ModelAttribute UrgenceRequest urgenceForm, Model model) {
	    model.addAttribute("confirmation", true);
	    return "urgence/main";
	}


}
