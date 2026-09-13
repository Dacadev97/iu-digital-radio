# Guía de contribución — IU Digital Radio

Para compañeros de la célula (también si es tu primer repo Android en equipo).

## 1. Requisitos

- **JDK 17** (Temurin / Oracle / Android Studio embebido)
- **Android Studio** Hedgehog o más reciente (recomendado)
- Git + cuenta GitHub **con acceso al repo** (es **privado**: Davidson debe invitarte en **Settings → Collaborators**)
- Android SDK 34 (Studio lo instala al abrir el proyecto)

Si al clonar ves *404* o *Repository not found*, aún no tienes la invitación: acéptala en el correo de GitHub o pide acceso.

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
3. Abre **un** issue, asígnatelo (cada persona de la célula toma el suyo).
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

## 6. Política de ramas (célula F30)

```
main  ←  develop  ←  feature/<rol>-<issue>
```

- **`main`:** protegida. Nadie de la célula pushea. Solo entra por **PR** con **approval de Davidson** (`@Dacadev97`, Code Owner) + checks `Assemble debug APK` y `Android Lint`.
- **`develop`:** base de integración. Puedes **pushear** como collaborator; **force-push y borrar la rama están bloqueados**. Lo ideal sigue siendo `feature/*` → PR a `develop`.
- **PRs:** solo Davidson **aprueba** (y mergea a `main`). En PRs a `develop`, también espera su review cuando abras PR.
- Code Owners: `.github/CODEOWNERS` → `* @Dacadev97`.

Documento completo: [docs/BRANCH_PROTECTION.md](docs/BRANCH_PROTECTION.md).

### Otras restricciones recomendadas

- Un issue → una rama → un PR (PRs pequeños).
- No force-push a ramas compartidas; no reescribir historial de `develop`.
- No subir secretos, `local.properties`, keystores ni APK firmados al repo.
- No “suavizar” asserts del contrato TDD para pasar CI.
- Borrar `feature/*` tras merge; no tocar `main`/`develop` desde el cliente Git.
- Coordinar deps compartidas (Compose/Media3) en el issue o un PR de plataforma.
- **No** incluir trailers `Co-authored-by: Cursor` (ensucia Contributors). Si usas Cursor Agent: desactiva la co-autoría automática en Settings del IDE — detalle en [docs/BRANCH_PROTECTION.md](docs/BRANCH_PROTECTION.md#identidad-git-sin-cursor-agent-en-contributors).

## 7. Pull Request — checklist

- [ ] Base branch: **`develop`** (nunca `main`, salvo Davidson en release)
- [ ] Título claro: `[RF-0X] …` o `[PLAT-0X] …`
- [ ] Body: qué hiciste + cómo probar + `Closes #N`
- [ ] CI Actions: assemble/lint OK; tests de tu RF avanzando a verde
- [ ] No subiste secretos / `local.properties`
- [ ] Tarjeta en **Testing** al abrir el PR; **Done** tras merge de Davidson

Plantilla: `.github/pull_request_template.md`.

## 8. Después del merge

```bash
git checkout develop
git pull origin develop
```

Borra la rama local si quieres: `git branch -d feature/...`

## Dudas

- Repo / CI / board / accesos → **Davidson** (QA & DevOps / Release)
- Alcance de un RF → comenta en el issue
- Contrato de test mal planteado → issue con labels `qa` + `po` (no bajes la barra del assert)

Más contexto: [docs/ONBOARDING.md](docs/ONBOARDING.md) · [docs/ROLES.md](docs/ROLES.md) · [docs/TDD.md](docs/TDD.md) · [docs/BRANCH_PROTECTION.md](docs/BRANCH_PROTECTION.md)
