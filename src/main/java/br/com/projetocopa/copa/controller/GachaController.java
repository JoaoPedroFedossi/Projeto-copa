package br.com.projetocopa.copa.controller;

import br.com.projetocopa.copa.entity.Jogadores;
import br.com.projetocopa.copa.entity.Usuario;
import br.com.projetocopa.copa.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class GachaController {

    private final UsuarioService usuarioService;

    public GachaController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/gacha")
    public String gacha(HttpSession session, Model model) {
        String nome = (String) session.getAttribute("nomeUsuario");
        if (nome == null) {
            return "redirect:/login";
        }
        Usuario usuario = usuarioService.buscarPorNome(nome).orElse(null);
        if (usuario == null) {
            session.invalidate();
            return "redirect:/login";
        }
        model.addAttribute("nomeUsuario", usuario.getNome());
        model.addAttribute("gemas", usuario.getGemas() == null ? 0 : usuario.getGemas());
        model.addAttribute("custoPacote", UsuarioService.CUSTO_PACOTE);
        return "gacha";
    }

    @PostMapping("/gacha/comprar")
    public String comprarPacote(HttpSession session, RedirectAttributes redirectAttributes) {
        String nome = (String) session.getAttribute("nomeUsuario");
        if (nome == null) {
            return "redirect:/login";
        }
        try {
            List<Jogadores> obtidos = usuarioService.comprarPacote(nome);
            redirectAttributes.addFlashAttribute("sucesso",
                    "Pacote aberto! Você recebeu 5 jogadores.");
            redirectAttributes.addFlashAttribute("jogadoresObtidos", obtidos);
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/gacha";
    }
}
