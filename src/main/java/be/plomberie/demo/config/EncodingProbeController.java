package be.plomberie.demo.config;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EncodingProbeController {

    private final MessageSource messageSource;

    public EncodingProbeController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    // 1) Texte dur en Java -> valide l'ENCODAGE DE RÉPONSE (HTTP + Thymeleaf/config serveur)
    @GetMapping("/__probe/raw")
    public ResponseEntity<byte[]> raw() {
        String body = "RAW: Éé Àà Çç œ Œ û ù — « » – ✓";
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE + ";charset=UTF-8")
            .body(body.getBytes(StandardCharsets.UTF_8));
    }

    // 2) Texte depuis messages.properties -> valide LECTURE DES .properties EN UTF-8
    @GetMapping("/__probe/msg")
    public ResponseEntity<byte[]> msg() {
        String val = messageSource.getMessage("i18n.probe", null, Locale.FRENCH);
        String body = "MSG: " + val;
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE + ";charset=UTF-8")
            .body(body.getBytes(StandardCharsets.UTF_8));
    }
}
