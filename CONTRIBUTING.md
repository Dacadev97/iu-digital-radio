# Guía de contribución — IU Digital Radio

Para compañeros del Software House (también si es tu primer repo Android en equipo).

## 1. Requisitos

- **JDK 17** (Temurin / Oracle / Android Studio embebido)
- **Android Studio** Hedgehog o más reciente (recomendado)
- Git + cuenta GitHub con acceso al repo
- Android SDK 34 (Studio lo instala al abrir el proyecto)

Comprobar Java:

```bash
java -version   # debe ser 17.x
```

## 2. Clonar y abrir

```bash
git clone https://github.com/Dacadev97/iu-digital-radio.git
cd iu-digital-radio
git checkout develop
git pull origin develop
```

Abre la carpeta en Android Studio → *Trust Project* → espera el sync de Gradle.

Si falta SDK, crea `local.properties` (Studio lo genera solo):

```properties
sdk.dir=/ruta/a/tu/Android/Sdk
```

**No subas** `local.properties` (ya está en `.gitignore`).

## 3. Elige issue y crea rama

1. Entra al [Project](https://github.com/users/Dacadev97/projects/4).
2. Filtra por tu label (`role-ui`, `role-hardware`, …).
3. Abre **un** issue, asígnatelo.
4. Mueve la tarjeta: **Todo → In Progress**.

```bash
git checkout develop
git pull
git checkout -b feature/<rol>-<issue>
# ejemplos:
#   feature/ui-rf01
#   feature/hardware-rf03
#   feature/state-rf04
#   feature/audio-rf07
```

## 4. Implementar (TDD)

1. Corre los tests (deben fallar al inicio):

```bash
./gradlew testDebugUnitTest
```

2. Lee tu test `Rf0X…` y el contrato en `api/ProductContracts.kt`.
3. Implementa **solo** lo de tu issue.
4. Añade dependencias de producto **en tu PR** si las necesitas (Compose, Media3, etc.). No las asumas ya en el shell.
5. Verifica:

```bash
./gradlew testDebugUnitTest assembleDebug lintDebug
```

Cuando tu RF esté verde (y no rompas lo demás), sigue al PR.

## 5. Columnas del Project

| Columna | Cuándo |
|--------|--------|
| **Todo** | Issue sin empezar |
| **In Progress** | Ya tienes rama y estás codeando |
| **Testing** | PR abierto / esperando CI o review de QA |
| **Done** | Mergeado a `develop` y AC del issue cumplidos |

## 6. Pull Request — checklist

- [ ] Base branch: **`develop`**
- [ ] Título claro: `[RF-0X] …` o `[PLAT-0X] …`
- [ ] Body: qué hiciste + cómo probar + `Closes #N`
- [ ] CI Actions en verde (o justificado si aún falta integración de otro squad)
- [ ] No subiste secretos / `local.properties`
- [ ] Tarjeta en **Testing** al abrir el PR; **Done** tras merge

Plantilla: `.github/pull_request_template.md`.

## 7. Después del merge

```bash
git checkout develop
git pull origin develop
```

Borra la rama local si quieres: `git branch -d feature/...`

## Dudas

- Repo / CI / board → **Davidson** (DevOps/QA)
- Alcance de un RF → comenta en el issue
- Contrato de test mal planteado → issue con labels `qa` + `po` (no bajes la barra del assert)

Más contexto: [docs/ONBOARDING.md](docs/ONBOARDING.md) · [docs/ROLES.md](docs/ROLES.md) · [docs/TDD.md](docs/TDD.md)
