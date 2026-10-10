# J2DGameEngineAndroid

Projeto de faculdade, apresentado nas matérias Programação de Jogos Mobile e Programação Avançada em C++ e Java. Nasceu porque desenhar sprites pelo caminho comum do Android, com `View` e `Canvas`, ficava lento demais para um jogo. Em vez de adaptar um motor pronto, o desenho, o loop e as entidades foram feitos do zero em cima de OpenGL ES 1 (`GLSurfaceView`).

O código é de quem estava aprendendo: direto, com pouca abstração e com as marcas de um trabalho de curso. Ainda assim o motor roda de verdade. Carrega textura, anima sprite, lê o toque e desenha frame a frame sem depender do framework de interface. O jogo incluso é uma nave destruindo asteroides e acumulando um contador.

O código do app está em Java 17. O pacote é `com.asuran.j2dgameengine`.

## Demonstração prática

![Nave atirando nos asteroides, com o contador na tela](docs/jogo_exemplo.gif)

É um jogo de nave. Asteroides descem pela tela e o objetivo é destruir o maior número possível, atirando neles. Cada asteroide destruído soma um no contador. Se um asteroide acerta a nave, o contador volta para zero.

Segurar o dedo em cima da nave move ela e mantém o tiro. Soltar para o disparo. O fundo rola enquanto os asteroides continuam aparecendo, então a partida não tem fim: a meta é sustentar a sequência.

`J2DMain` carrega as texturas e coloca `SpaceShipMain` no motor. `CollisionCore` compara os tiros e a nave com os asteroides, e `ComboText` desenha o número na tela.

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
| `GameEngine`, `RendererGL`, `RenderView`, `J2DCanvas` | Loop, desenho e superfície OpenGL |
| `entitiesclasses` | `IEntity`: `setup`, `update`, `draw` |
| `listeners` | Toque e frame |
| `utils` | Sprite, retângulo e vetor |
| `spaceshipgame` | Jogo de exemplo |
| `app/src/main/assets` | Texturas (`ship.png`, `asteroidb.png`, e as outras) |

Uma entidade nova implementa `IEntity` (ou estende `AbstractEntity`), é adicionada com `GameEngine.addEntity`, e a textura correspondente entra em `J2DMain.loadTextures`.

## Licença

MIT. Ver [LICENSE](LICENSE).
