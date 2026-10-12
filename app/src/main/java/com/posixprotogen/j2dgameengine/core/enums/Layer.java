package com.posixprotogen.j2dgameengine.core.enums;

/**
 * Camadas de desenho, da mais funda para a mais da frente.
 * Cada constante é um balde. Os baldes são desenhados nesta ordem e, dentro
 * de um balde, na ordem em que os comandos entraram. Não há reordenação.
 */
public enum Layer {

    /**
     * Paralaxe mais distante, a que quase não acompanha a câmera.
     * Céu, estrelas, lua, silhueta de montanha no horizonte.
     */
    BACKGROUND_FAR,

    /**
     * Fundo do meio, entre o horizonte e o cenário jogável.
     * Nuvens, névoa, a segunda faixa de um cenário que rola.
     */
    BACKGROUND,

    /**
     * Fundo colado no que se pode pisar.
     * Prédios distantes, mata atrás do chão, o espaço logo atrás dos tiles.
     */
    BACKGROUND_NEAR,

    /**
     * A superfície em que o jogo acontece.
     * Tiles, plataformas, estrada, água opaca.
     */
    GROUND,

    /**
     * Marca em cima do chão e embaixo de qualquer ator.
     * Rachadura, sangue seco, pegada, detalhe que não entra na colisão.
     */
    DECAL,

    /**
     * Sombra no chão, ainda por baixo de quem a projeta.
     * A elipse debaixo da nave, a sombra do personagem.
     */
    SHADOW,

    /**
     * Parte do mundo que o ator cobre. O ator passa na frente.
     * Tronco, muro baixo, balcão, a face de trás de uma casa.
     */
    WORLD_BACK,

    /**
     * Atores e itens. É a camada padrão de quem age no mundo.
     * Nave, asteroide, inimigo, NPC, caixa, moeda.
     */
    WORLD,

    /**
     * O que sai de um ator e precisa aparecer por cima dele, ainda atrás do que cobre o cenário.
     * Laser, flecha, míssil. A copa e o beiral continuam na frente do tiro.
     */
    PROJECTILE,

    /**
     * Parte do mundo que cobre o ator e o que ele disparou.
     * Copa da árvore, beiral do telhado, folhagem na frente da câmera.
     */
    WORLD_FRONT,

    /**
     * Efeito curto, por cima do tiro e dos atores.
     * Explosão, impacto, flash de dano.
     */
    EFFECT,

    /**
     * Partícula miúda, por cima do efeito que a soltou.
     * Faísca, fumaça, poeira, folha caindo.
     */
    PARTICLE,

    /**
     * Efeito que cobre o mundo inteiro, inclusive a copa e a explosão local.
     * Chuva, neve, onda de choque, flash de dano sobre o cenário.
     * O placar continua na frente. Fade que cobre o menu fica em {@code OVERLAY}.
     */
    GLOBAL_EFFECT,

    /**
     * Texto e medidor presos a um ponto do mundo, não à borda da tela.
     * Número de dano subindo, barra de vida sobre o inimigo, nome do NPC.
     */
    WORLD_UI,

    /**
     * Interface presa à tela.
     * Placar, vida do jogador, minimapa, contador de combo.
     */
    HUD,

    /**
     * O que precisa cobrir o restante da interface.
     * Tooltip, cursor, ícone sendo arrastado, aviso pequeno.
     */
    HUD_FRONT,

    /**
     * Tela que toma o lugar da partida, por cima do HUD.
     * Pausa, inventário, diálogo em tela cheia.
     */
    MENU,

    /**
     * A última camada. Cobre o jogo e os menus.
     * Fade para preto, letterbox, vinheta, flash da tela inteira.
     */
    OVERLAY
}
