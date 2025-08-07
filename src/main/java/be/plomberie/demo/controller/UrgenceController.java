package be.plomberie.demo.controller;

import java.util.List; // ✅ ← import manquant

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;

import be.plomberie.demo.model.UrgenceForm;
import be.plomberie.demo.service.StripeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class UrgenceController {

    @Autowired
    private StripeService stripeService;

    @GetMapping("/urgence")
    public String showUrgenceForm(@RequestParam(required = false) String success, Model model) {
        model.addAttribute("urgenceForm", new UrgenceForm());
        if ("true".equals(success)) {
            model.addAttribute("confirmation", true);
        }
        return "urgence/main";
    }

    @PostMapping("/urgence/payer")
    public RedirectView handleUrgenceForm(@ModelAttribute UrgenceForm urgenceForm) throws StripeException {
        Session session = stripeService.creerSessionPaiementUrgence(urgenceForm);
        return new RedirectView(session.getUrl());
    }

    // Vue admin
    @GetMapping("/admin/urgences")
    public String voirToutesLesUrgences(Model model) {
        model.addAttribute("urgences", List.of()); // à remplacer par service plus tard
        return "admin/urgences";
    }
}
