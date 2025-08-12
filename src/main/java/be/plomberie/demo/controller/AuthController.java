package be.plomberie.demo.controller;

import be.plomberie.demo.model.Client;
import be.plomberie.demo.model.Compte;
import be.plomberie.demo.model.PasswordResetToken;
import be.plomberie.demo.repository.ClientRepository;
import be.plomberie.demo.repository.CompteRepository;
import be.plomberie.demo.repository.PasswordResetTokenRepository;
import be.plomberie.demo.service.MailService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Controller
public class AuthController {

    private final ClientRepository clientRepo;
    private final CompteRepository compteRepo;
    private final PasswordResetTokenRepository tokenRepo;
    private final PasswordEncoder encoder;
    private final MailService mail;

    public AuthController(ClientRepository clientRepo, CompteRepository compteRepo,
                          PasswordResetTokenRepository tokenRepo,
                          PasswordEncoder encoder, MailService mail) {
        this.clientRepo = clientRepo;
        this.compteRepo = compteRepo;
        this.tokenRepo = tokenRepo;
        this.encoder = encoder;
        this.mail = mail;
    }

    @GetMapping("/login")
    public String login() { return "auth/login"; }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("client", new Client());
        model.addAttribute("compte", new Compte());
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerSubmit(@ModelAttribute("client") Client client,
                                 @RequestParam String email,
                                 @RequestParam String password,
                                 Model model) {
        if (!StringUtils.hasText(email) || !StringUtils.hasText(password)) {
            model.addAttribute("error", "Email et mot de passe requis.");
            return "auth/register";
        }
        if (compteRepo.existsByEmail(email)) {
            model.addAttribute("error", "Un compte existe déjà avec cet email.");
            return "auth/register";
        }

        // Création du client
        clientRepo.save(client);

        // Création du compte (pas de champ role dans Compte -> rôle géré côté Security par défaut USER)
        Compte c = new Compte();
        c.setEmail(email);
        c.setMotDePasse(encoder.encode(password));
        c.setNom(client.getFirstname() != null ? client.getFirstname() : email);
        c.setClient(client);
        compteRepo.save(c);

        return "redirect:/login";
    }

    @GetMapping("/mot-de-passe/oublie")
    public String forgotForm() { return "auth/forgot"; }

 // dans forgotSubmit(...)
    @PostMapping("/mot-de-passe/oublie")
    public String forgotSubmit(@RequestParam String email, HttpServletRequest req, Model model) {
        var compteOpt = compteRepo.findByEmailIgnoreCase(email);  // ✅
        if (compteOpt.isPresent()) {
            var token = new PasswordResetToken();
            token.setCompte(compteOpt.get());
            token.setToken(UUID.randomUUID().toString());
            token.setExpiresAt(LocalDateTime.now().plusHours(2));
            tokenRepo.save(token);

            String base = req.getRequestURL().toString().replace(req.getRequestURI(), "");
            String link = base + "/mot-de-passe/reinitialiser?token=" + token.getToken();
            mail.send(email, "Réinitialisation de mot de passe",
                    "Bonjour,\n\nCliquez pour réinitialiser : " + link + "\n(Valide 2h)\n\nLe Bon Tuyau");
        }
        model.addAttribute("sent", true);
        return "auth/forgot";
    }


    @GetMapping("/mot-de-passe/reinitialiser")
    public String resetForm(@RequestParam String token, Model model) {
        var t = tokenRepo.findByToken(token).orElse(null);
        if (t == null || t.isUsed() || t.getExpiresAt().isBefore(LocalDateTime.now())) {
            model.addAttribute("invalid", true);
            return "auth/reset";
        }
        model.addAttribute("token", token);
        return "auth/reset";
    }

    @PostMapping("/mot-de-passe/reinitialiser")
    public String resetSubmit(@RequestParam String token, @RequestParam String password, Model model) {
        var tOpt = tokenRepo.findByToken(token);
        if (tOpt.isEmpty() || tOpt.get().isUsed() || tOpt.get().getExpiresAt().isBefore(LocalDateTime.now())) {
            model.addAttribute("invalid", true);
            return "auth/reset";
        }
        var t = tOpt.get();
        var c = t.getCompte();
        c.setMotDePasse(encoder.encode(password));
        compteRepo.save(c);
        t.setUsed(true);
        tokenRepo.save(t);
        return "redirect:/login";
    }
}
