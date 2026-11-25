package br.com.projetocopa.copa.controller;

import br.com.projetocopa.copa.entity.Jogadores;
import br.com.projetocopa.copa.entity.Usuario;
import br.com.projetocopa.copa.service.InventarioService;
import br.com.projetocopa.copa.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class InventarioController {

    private final UsuarioService usuarioService;
    private final InventarioService inventarioService;

    public InventarioController(UsuarioService usuarioService, InventarioService inventarioService) {
        this.usuarioService = usuarioService;
        this.inventarioService = inventarioService;
    }

    @GetMapping("/inventario")
    public String inventario(HttpSession session, Model model) {
        String nome = (String) session.getAttribute("nomeUsuario");
        if (nome == null) {
            return "redirect:/login";
        }
        Usuario usuario = usuarioService.buscarPorNome(nome).orElse(null);
        if (usuario == null) {
            session.invalidate();
            return "redirect:/login";
        }
        List<Jogadores> jogadores = inventarioService.listarJogadoresDoUsuario(usuario);
        model.addAttribute("nomeUsuario", usuario.getNome());
        model.addAttribute("gemas", usuario.getGemas() == null ? 0 : usuario.getGemas());
        model.addAttribute("jogadores", jogadores);
        return "inventario";
    }
}
