package br.com.projetocopa.copa.service;

import br.com.projetocopa.copa.entity.Inventario;
import br.com.projetocopa.copa.entity.Jogadores;
import br.com.projetocopa.copa.entity.Usuario;
import br.com.projetocopa.copa.repository.InventarioRepository;
import br.com.projetocopa.copa.repository.JogadoresRepository;
import br.com.projetocopa.copa.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    public static final int GEMAS_INICIAIS = 1000;
    public static final int CUSTO_PACOTE = 200;

    private final UsuarioRepository usuarioRepository;
    private final InventarioService inventarioService;
    private final JogadoresService jogadoresService;
    private final InventarioRepository inventarioRepository;
    private final JogadoresRepository jogadoresRepository;

    public UsuarioService(UsuarioRepository usuarioRepository,
                          InventarioService inventarioService,
                          JogadoresService jogadoresService,
                          InventarioRepository inventarioRepository,
                          JogadoresRepository jogadoresRepository) {
        this.usuarioRepository = usuarioRepository;
        this.inventarioService = inventarioService;
        this.jogadoresService = jogadoresService;
        this.inventarioRepository = inventarioRepository;
        this.jogadoresRepository = jogadoresRepository;
    }

    public Usuario cadastrar(String nome, String senha) {
        if (nome == null || nome.isBlank() || senha == null || senha.isBlank()) {
            throw new IllegalArgumentException("Nome e senha são obrigatórios.");
        }
        if (usuarioRepository.existsByNome(nome.trim())) {
            throw new IllegalArgumentException("Já existe um usuário com esse nome.");
        }
        Usuario usuario = new Usuario();
        usuario.setNome(nome.trim());
        usuario.setSenha(senha);
        usuario.setGemas(GEMAS_INICIAIS);
        usuario.setPerfil("USER");
        usuario = usuarioRepository.save(usuario);
        inventarioService.getOrCreateInventario(usuario);
        return usuario;
    }

    public Optional<Usuario> autenticar(String nome, String senha) {
        if (nome == null || senha == null) {
            return Optional.empty();
        }
        return usuarioRepository.findByNome(nome.trim())
                .filter(u -> u.getSenha().equals(senha));
    }

    public Optional<Usuario> buscarPorNome(String nome) {
        return usuarioRepository.findByNome(nome);
    }

    public Optional<Usuario> buscarPorId(Integer id) {
        return usuarioRepository.findById(id);
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    @Transactional
    public Usuario atualizarGemas(Integer idUsuario, Integer gemas) {
        if (gemas == null || gemas < 0) {
            throw new IllegalArgumentException("A quantidade de gemas não pode ser negativa.");
        }
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));
        usuario.setGemas(gemas);
        return usuarioRepository.save(usuario);
    }

    /**
     * Exclui usuário e todo o vínculo (inventário + cartas).
     * Conta ADMIN não pode ser excluída.
     */
    @Transactional
    public void excluirUsuario(Integer idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));

        if ("ADMIN".equals(usuario.getPerfil())) {
            throw new IllegalArgumentException("A conta ADMIN não pode ser excluída.");
        }

        // remove cartas e inventário antes do usuário (evita erro de FK)
        inventarioRepository.findByUsuario(usuario).ifPresent(inv -> {
            jogadoresRepository.deleteByInventario(inv);
            inventarioRepository.delete(inv);
        });

        usuarioRepository.delete(usuario);
    }

    @Transactional
    public List<Jogadores> comprarPacote(String nome) {
        Usuario usuario = usuarioRepository.findByNome(nome)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));

        int gemas = usuario.getGemas() == null ? 0 : usuario.getGemas();
        if (gemas < CUSTO_PACOTE) {
            throw new IllegalArgumentException("Gemas insuficientes. Você precisa de " + CUSTO_PACOTE + " gemas.");
        }

        usuario.setGemas(gemas - CUSTO_PACOTE);
        usuarioRepository.save(usuario);

        Inventario inventario = inventarioService.getOrCreateInventario(usuario);
        return jogadoresService.gerarPacote(inventario);
    }
}
