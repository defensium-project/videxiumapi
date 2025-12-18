package br.com.videxium.videxiumapi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void enviarEmail(String toEmail, String token) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(fromEmail);
            simpleMailMessage.setTo(toEmail);
            simpleMailMessage.setSubject("Videxium: Verificação de E-mail");

        String linkVerificacao = "http://127.0.1.1:8080/api/autenticador/verificar-conta?token=" + token;

        String body = """
            Clique no link abaixo para validar sua conta.
        """.concat(linkVerificacao);

        simpleMailMessage.setText(body);

        javaMailSender.send(simpleMailMessage);
    }

    public void enviarEmailRecuperSenha(String toEmail, String token) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromEmail);
        simpleMailMessage.setTo(toEmail);
        simpleMailMessage.setSubject("[VidexiumService] Recuperação de Senha");

        String linkVerificacao = "https://www.videxium.com.br/reset-password?token".concat(token);

        String body = "Bem vindo ao sistema Videxium. Clique no link ".concat(linkVerificacao).concat(" para resetar sua senha!");

        simpleMailMessage.setText(body);

        javaMailSender.send(simpleMailMessage);
    }

}
