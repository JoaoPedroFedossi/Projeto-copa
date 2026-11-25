package br.com.projetocopa.copa.controller;

import br.com.projetocopa.copa.entity.Usuario;
import br.com.projetocopa.copa.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping({"/", "/login"})
    public String loginPage(HttpSession session) {
        if (session.getAttribute("nomeUsuario") != null) {
            return "ADMIN".equals(session.getAttribute("perfilUsuario")) ? "redirect:/admin" : "redirect:/home";
        }
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("nome") String nome, @RequestParam("senha") String senha,
                        HttpSession session, RedirectAttributes redirectAttributes) {
        return usuarioService.autenticar(nome, senha).map(usuario -> {
            session.setAttribute("nomeUsuario", usuario.getNome());
            session.setAttribute("perfilUsuario", usuario.getPerfil() == null ? "USER" : usuario.getPerfil());
            return "ADMIN".equals(usuario.getPerfil()) ? "redirect:/admin" : "redirect:/home";
        }).orElseGet(() -> {
            redirectAttributes.addFlashAttribute("erro", "Nome ou senha incorretos.");
            return "redirect:/login";
        });
    }

    @GetMapping("/cadastro")
    public String cadastroPage(HttpSession session) {
        if (session.getAttribute("nomeUsuario") != null) return "redirect:/home";
        return "cadastro";
    }

    @PostMapping("/cadastro")
    public String cadastrar(@RequestParam("nome") String nome, @RequestParam("senha") String senha,
                            @RequestParam("confirmarSenha") String confirmarSenha, RedirectAttributes redirectAttributes) {
        if (senha == null || !senha.equals(confirmarSenha)) {
            redirectAttributes.addFlashAttribute("erro", "As senhas não coincidem.");
            return "redirect:/cadastro";
        }
        try {
            usuarioService.cadastrar(nome, senha);
            redirectAttributes.addFlashAttribute("sucesso", "Cadastro realizado! Você ganhou 1000 gemas. Faça o login.");
            return "redirect:/login";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage() != null ? e.getMessage() : "Erro ao cadastrar.");
            return "redirect:/cadastro";
        }
    }

    @GetMapping("/home")
    public String home(HttpSession session, Model model) {
        String nome = (String) session.getAttribute("nomeUsuario");
        if (nome == null) return "redirect:/login";
        if ("ADMIN".equals(session.getAttribute("perfilUsuario"))) return "redirect:/admin";
        Usuario usuario = usuarioService.buscarPorNome(nome).orElse(null);
        if (usuario == null) {
            session.invalidate();
            return "redirect:/login";
        }
        model.addAttribute("nomeUsuario", usuario.getNome());
        model.addAttribute("gemas", usuario.getGemas() == null ? 0 : usuario.getGemas());
        return "home";
    }

    @GetMapping("/admin")
    public String admin(HttpSession session, Model model) {
        if (!"ADMIN".equals(session.getAttribute("perfilUsuario"))) {
            return "redirect:/login";
        }
        model.addAttribute("usuarios", usuarioService.listarTodos());
        return "admin";
    }

    @PostMapping("/admin/usuario/{id}/gemas")
    public String alterarGemas(@PathVariable("id") Integer id,
                               @RequestParam("gemas") Integer gemas,
                               HttpSession session,
                               RedirectAttributes ra) {
        if (!"ADMIN".equals(session.getAttribute("perfilUsuario"))) {
            return "redirect:/login";
        }
        try {
            Usuario u = usuarioService.atualizarGemas(id, gemas);
            ra.addFlashAttribute("sucesso", "Gemas de \"" + u.getNome() + "\" atualizadas para " + u.getGemas() + ".");
        } catch (IllegalArgumentException e) {
            ra.addFlashAttribute("erro", e.getMessage());
        } catch (Exception e) {
            ra.addFlashAttribute("erro", "Erro ao salvar gemas: " + e.getMessage());
        }
        return "redirect:/admin";
    }

    @PostMapping("/admin/usuario/{id}/excluir")
    public String excluirUsuario(@PathVariable("id") Integer id,
                                 HttpSession session,
                                 RedirectAttributes ra) {
        if (!"ADMIN".equals(session.getAttribute("perfilUsuario"))) {
            return "redirect:/login";
        }
        try {
            usuarioService.excluirUsuario(id);
            ra.addFlashAttribute("sucesso", "Usuário excluído com sucesso.");
        } catch (IllegalArgumentException e) {
            ra.addFlashAttribute("erro", e.getMessage());
        } catch (Exception e) {
            ra.addFlashAttribute("erro", "Erro ao excluir usuário: " + e.getMessage());
        }
        return "redirect:/admin";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
