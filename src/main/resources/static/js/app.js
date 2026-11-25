/**
 * Projeto Copa — filtros do inventário (sem animações)
 */
document.addEventListener("DOMContentLoaded", () => {
    const botoes = document.querySelectorAll(".filtro-btn");
    const cartas = document.querySelectorAll(".carta-jogador[data-raridade]");
    const vazio = document.getElementById("filtro-vazio");
    const contador = document.getElementById("inventario-contador");
    const total = cartas.length;

    if (!botoes.length || !cartas.length) return;

    function aplicarFiltro(raridade) {
        let visiveis = 0;
        cartas.forEach((carta) => {
            const r = (carta.getAttribute("data-raridade") || "").toUpperCase();
            const mostrar = raridade === "TODAS" || r === raridade;
            carta.classList.toggle("filtro-hidden", !mostrar);
            if (mostrar) visiveis += 1;
        });

        if (vazio) {
            vazio.hidden = visiveis > 0;
        }

        if (contador) {
            if (raridade === "TODAS") {
                contador.textContent = "Você possui " + total + " carta(s)";
            } else {
                contador.textContent = visiveis + " de " + total + " carta(s) · " + raridade;
            }
        }
    }

    botoes.forEach((btn) => {
        btn.addEventListener("click", () => {
            botoes.forEach((b) => b.classList.remove("active"));
            btn.classList.add("active");
            aplicarFiltro((btn.getAttribute("data-raridade") || "TODAS").toUpperCase());
        });
    });
});
