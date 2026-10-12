# J2DGameEngineAndroid

Projeto de faculdade, apresentado nas matérias Programação de Jogos Mobile e Programação Avançada em C++ e Java. Nasceu porque desenhar sprites pelo caminho comum do Android, com `View` e `Canvas`, ficava lento demais para um jogo. Em vez de adaptar um motor pronto, o desenho, o loop e as entidades foram feitos do zero em cima de OpenGL ES 1 (`GLSurfaceView`).

O código é de quem estava aprendendo: direto, com pouca abstração e com as marcas de um trabalho de curso. Ainda assim o motor roda de verdade. Carrega textura, anima sprite, lê o toque e desenha frame a frame sem depender do framework de interface. O jogo incluso é uma nave destruindo asteroides e acumulando um contador.

O código do app está em Java 17. O pacote é `com.posixprotogen.j2dgameengine`.

## Demonstração prática

![Nave atirando nos asteroides, com o contador na tela](docs/jogo_exemplo.gif)

É um jogo de nave. Asteroides descem pela tela e o objetivo é destruir o maior número possível, atirando neles. Cada asteroide destruído soma um no contador. Se um asteroide acerta a nave, o contador volta para zero.

Segurar o dedo em cima da nave move ela e mantém o tiro. Soltar para o disparo. O fundo rola enquanto os asteroides continuam aparecendo, então a partida não tem fim: a meta é sustentar a sequência.

`J2DGameEngineMain` carrega as texturas e os sons e coloca `SpaceShipMain` no motor. `CollisionCore` compara os tiros e a nave com os asteroides, e `Hud` desenha o número na tela.

## Como fazer um jogo

O jogo começa em `J2DGameEngineMain.setupGame`. O motor chama esse método uma vez, quando a superfície OpenGL abre. Ali se registram os arquivos e entra a primeira entidade. O resto nasce a partir dela.

Os arquivos ficam em `app/src/main/assets`. Textura é PNG. Efeito curto é WAV, OGG ou MP3 pequeno. Música é OGG ou MP3. O nome no código é o caminho dentro de `assets`, como `ship.png` ou `sounds/laser_shot.wav`.

```java
public void setupGame(GameContext gameContext) {
    SpriteLoader sprites = gameContext.getSpriteLoader();
    sprites.addTexture("ship.png");
    sprites.addTexture("explosionb.png", 4, 4);

    gameContext.getSoundPlayer().addSound("sounds/laser_shot.wav");

    gameContext.addEntity(new SpaceShipMain());
}
```

`addTexture` com dois números corta a imagem numa grade, da esquerda para a direita e de cima para baixo. No exemplo, `explosionb.png` tem 4 colunas e 4 linhas. `getTexture` devolve uma cópia, então o `resize` de uma entidade não altera o sprite de outra.

Cada peça estende `AbstractEntity` e preenche `setup`, `update` e `draw`. O motor guarda o `GameContext` na entidade antes do `setup`. Nos três métodos o acesso é `getGameContext()`.

```java
public class Asteroid extends AbstractEntity {
    private GLSprite sprite;
    private float x;
    private float y = 0f;

    @Override
    public void setup() {
        sprite = getGameContext().getSpriteLoader().getTexture("asteroidb.png");
        sprite.resize(0.15f);
    }

    @Override
    public void update() {
        y += 0.4f * getGameContext().getLastFrameTime();
        if (y > 1f) {
            die();
        }
    }

    @Override
    public void draw() {
        getGameContext().getJ2DCanvas().drawSprite(sprite, x, y);
    }
}
```

`setup` roda uma vez. `update` e `draw` rodam todo frame, nessa ordem. A ordem do desenho é a ordem de entrada no mundo: o fundo precisa ser adicionado antes da nave. Uma entidade criada com `addEntity` dentro do `setup` de outra só recebe o próprio `setup` no frame seguinte.

### O que o GameContext oferece

`getLastFrameTime` é a duração do frame anterior, em segundos. A velocidade multiplicada por esse valor acompanha a taxa de quadros.

`getDisplay` é a área visível. `width` é a razão largura/altura da tela. `height` é 1. O x cresce para a direita, de 0 até `width`. O y cresce para baixo, de 0 no topo até 1 embaixo. A nave do exemplo usa `width * 0.5` no x e fica perto de `0.8` no y.

`addEntity` coloca uma entidade na fila do mundo. `die()` marca a entidade como morta: ela deixa de atualizar e de desenhar na hora, e sai das listas no começo do frame seguinte. `removeEntity` faz essa mesma saída.

`getEntities(Asteroid.class)` devolve as entidades daquela classe exata. Uma subclasse fica na lista da própria classe. Quem morreu neste frame ainda pode aparecer na lista até o próximo.

`addTouchListener` registra um `GestureListener`: `onDown`, `onUp` e `onMoving`. O x e o y já estão no mesmo espaço do `drawSprite`.

`getSpriteLoader` carrega e entrega as texturas. `addTexture` fica no `setupGame`. `getTexture` e `resize` ficam no `setup` da entidade. Numa grade, `nextSprite` avança o quadro e devolve verdadeiro quando a animação volta ao primeiro.

`getJ2DCanvas` desenha. `drawSprite(sprite, x, y)` coloca o sprite na posição. `drawRect` desenha um `GLRect`, para enxergar a área de colisão. `GLRect.intersect` compara dois retângulos.

`getSoundPlayer` toca o áudio.

- `addSound` no `setupGame`, uma vez por arquivo. `playSound(arquivo, volume)` no evento, como no `setup` do tiro. Tocar no mesmo instante do `addSound` pode sair mudo: o carregamento termina um pouco depois.
- `playMusic(arquivo, volume, repetir, segundo)` começa a faixa. Com `repetir` verdadeiro ela entra em loop. `segundo` é o ponto da primeira entrada. Na volta, a faixa recomeça no instante 0.
- O volume vai de 0 a 1. Um valor acima de 1 toca como 1.
- `pause`, `resume` e `release` estão no `SoundPlayer`. A `MainActivity` ainda não os chama, então a música segue se o app for para segundo plano.

## Como rodar

Abre a pasta do projeto no Android Studio e espera o Gradle sincronizar. O módulo executável é `app`. A configuração de execução também se chama `app`.

Requisitos:

- Android Studio com Android SDK 36
- JDK 17 ou mais novo para o Gradle (o JDK que vem com o Android Studio serve)
- Aparelho ou emulador com Android 7.0 (API 24) ou mais novo

## APK

Debug, para instalar direto:

```
./gradlew :app:assembleDebug
```

O arquivo fica em `app/build/outputs/apk/debug/app-debug.apk`.

No Android Studio: **Build → Generate App Bundles or APKs → Build APK(s)**.

O tipo `release` existe, mas o projeto não tem chave de assinatura. Para um APK de produção que instale no aparelho, usa **Build → Generate Signed App Bundle or APK**.

## Estrutura

| Pasta | Função |
|---|---|
| `GameEngine`, `RendererEngine`, `RendererSurfaceView`, `J2DCanvas` | Loop, desenho e superfície OpenGL |
| `GameContext`, `SoundPlayer`, `SpriteLoader` | Mundo, áudio e textura vistos pelo jogo |
| `abstractions` | `AbstractEntity`: `setup`, `update`, `draw` |
| `listeners` | Toque |
| `utils` | Sprite, retângulo e vetor |
| `J2DGameEngineMain` | Inicialização do jogo |
| `spaceshipgame` | Jogo de exemplo |
| `app/src/main/assets` | PNG, `sounds/` e `musics/` |

O passo a passo de um jogo novo está em [Como fazer um jogo](#como-fazer-um-jogo).

## Licença

MIT. Ver [LICENSE](LICENSE).
