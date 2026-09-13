# IU Digital Radio

> **Este repo es un shell de DevOps + tests (TDD).**  
> El código de producto (UI Compose, cámara, player, catálogo, Media3) lo aportan los **compañeros de la célula por PR**.  
> No hay MVP implementado en `main`/`develop` a propósito.

App Android (**Kotlin**) — Evidencia **F30** · IU Digital de Antioquia · modalidad **Célula** (Opción A1 · equipo 4–6).

| Recurso | URL |
|---------|-----|
| Repo | https://github.com/Dacadev97/iu-digital-radio (**privado**) |
| **Project board** | https://github.com/users/Dacadev97/projects/4 |
| **Documento escrito de la célula** | [Google Docs](https://docs.google.com/document/d/1A2VFSnmZf0X946tLOFOfcxVCrduWtBdiIYpUAiPPZgo/edit?usp=sharing) → PDF técnico Canvas |
| Issues | https://github.com/Dacadev97/iu-digital-radio/issues |
| Actions (CI) | https://github.com/Dacadev97/iu-digital-radio/actions |
| PO + PM + QA + DevOps | **Davidson Cadavid** (`@Dacadev97`) |

> **Visibilidad:** el repositorio es **privado**. Cada integrante de la célula necesita invitación como collaborator  
> (GitHub → **Settings → Collaborators** → *Add people*). Sin invitación no podrán clonar ni abrir PRs.

## ¿Llegaste recién? Empieza aquí

1. Confirma que te invitaron al repo (si no, pide acceso a Davidson).
2. Lee **[docs/ONBOARDING.md](docs/ONBOARDING.md)** (5 minutos).
3. Elige tu rol en **[docs/ROLES.md](docs/ROLES.md)**, reclama el issue y **pon tu nombre en la tabla** de ese doc.
4. Crea tu rama y sigue **[CONTRIBUTING.md](CONTRIBUTING.md)** para setup + primera PR.

> **Antes de codear:** elige rol → reclama issue → pon tu nombre en [ROLES.md](docs/ROLES.md) → crea rama.

## Cómo elegir tu issue

1. Abre el [Project board](https://github.com/users/Dacadev97/projects/4).
2. Filtra issues por label de rol:
   - `role-ui` · `role-state` · `role-hardware` · `role-audio` · `role-qa-devops` · `role-po`
3. Asigna el issue a ti y mueve la tarjeta: **Todo → In Progress → Testing → Done**.

Cada persona de la célula toma **su** issue (uno a la vez).

## Rol → issues (dentro de la célula)

| Rol (célula) | Labels | Issues |
|--------------|--------|--------|
| **Toda la célula (día 1)** | — | **[#17 Kickoff](https://github.com/Dacadev97/iu-digital-radio/issues/17)** — nombre en ROLES |
| **UI/UX Compose** | `role-ui` | [#1](https://github.com/Dacadev97/iu-digital-radio/issues/1), [#6](https://github.com/Dacadev97/iu-digital-radio/issues/6) |
| **Hardware & Permissions** | `role-hardware` | [#2](https://github.com/Dacadev97/iu-digital-radio/issues/2), [#3](https://github.com/Dacadev97/iu-digital-radio/issues/3), [#5](https://github.com/Dacadev97/iu-digital-radio/issues/5) |
| **State & Logic** | `role-state` | [#4](https://github.com/Dacadev97/iu-digital-radio/issues/4) |
| **Audio & API** | `role-audio` | [#7](https://github.com/Dacadev97/iu-digital-radio/issues/7) |
| **QA & DevOps (Davidson)** | `role-qa-devops` | [#11](https://github.com/Dacadev97/iu-digital-radio/issues/11), [#12](https://github.com/Dacadev97/iu-digital-radio/issues/12) — *no las tomen compañeros* |
| **Ignorar** | — | PLAT Done #8–#10 · TDD cerrados #13–#14 |

Detalle de DoD por rol: [docs/ROLES.md](docs/ROLES.md).

## Flujo Git (importante)

```
main ← develop ← feature/<rol>-<issue>
```

| Rama | Compañeros de la célula | Davidson (`@Dacadev97`) |
|------|-------------------------|-------------------------|
| `main` | **Sin push.** Solo PR; él aprueba y mergea | Único aprobador / release |
| `develop` | Pueden **pushear** (o PR desde `feature/*`) | Aprueba PRs a `develop` si los hay |
| `feature/*` | Trabajo diario recomendado | — |

1. `git clone` el repo y checkout **`develop`**.
2. Crea rama: `feature/ui-rf01`, `feature/hardware-rf03`, etc.
3. Implementa **solo** lo de tu issue hasta que pase tu test.
4. Ideal: abre **PR → `develop`**. CI assemble/lint en verde; tests TDD según tu RF.
5. **Solo Davidson** aprueba merges (sobre todo `develop` → `main`). Release APK: workflow manual **Release APK**.

Detalle: [docs/BRANCH_PROTECTION.md](docs/BRANCH_PROTECTION.md).

**No pushees a `main`.** No hagas force-push a `develop`/`main`. El contrato TDD puede estar en rojo al inicio a propósito hasta que la célula lo verdee.

## TDD (Red → Green → Refactor)

| Fase | Qué pasa |
|------|----------|
| **Red** | `./gradlew testDebugUnitTest` **falla** — contrato QA |
| **Green** | Tu PR implementa lo mínimo para pasar los tests de tu RF |
| **Refactor** | Limpia sin romper CI |

| RF | Test | Qué debes crear |
|----|------|-----------------|
| RF-01 | `Rf01ComposeUiTest` | UI Compose + `ComposeUiContract.homeScreenImplemented = true` |
| RF-02 | `Rf02ProfileCameraTest` | `Product.profileCamera()` real |
| RF-03 | `Rf03PermissionsTest` | Permisos en `AndroidManifest.xml` |
| RF-04 | `Rf04PlayerStateTest` | `Product.radioSession()` con estado |
| RF-05 | `Rf05HapticTest` | Háptica en play/pause/mute |
| RF-06 | `Rf06StationCatalogTest` | `StationCatalog.defaults` ≥ 5 |
| RF-07 | `Rf07AudioStreamingTest` | Media3 + sesión de audio |

Contratos (interfaces, **sin lógica de producto**):  
`app/src/main/java/edu/iudigital/radio/api/ProductContracts.kt`

## Qué hay en el repo hoy

| Incluido (DevOps / QA) | No incluido (lo hacen compañeros de la célula) |
|------------------------|------------------------------------------------|
| GitHub Actions CI + Release APK | UI Compose del radio |
| Templates issue/PR | Cámara / ExoPlayer / catálogo real |
| Shell `MainActivity` vacío | Dependencias Media3, icons-extended, etc. |
| Tests RF-01…07 en **rojo** | Implementaciones de `Product.*` |
| Docs de onboarding | Features de producto en `main` |

## Comandos

```bash
./gradlew testDebugUnitTest   # ROJO al inicio (esperado)
./gradlew assembleDebug       # shell debe compilar
./gradlew lintDebug
```

JDK **17** + Android SDK. Guía setup: [CONTRIBUTING.md](CONTRIBUTING.md).

## Docs

| Doc | Para qué |
|-----|----------|
| [ONBOARDING.md](docs/ONBOARDING.md) | “Llegué al repo, ¿qué hago?” |
| [ROLES.md](docs/ROLES.md) | Roles de la célula, issues, DoD |
| [TDD.md](docs/TDD.md) | Contrato de tests |
| [BACKLOG.md](docs/BACKLOG.md) | Mapa RF / PLAT |
| [QA_CHECKLIST.md](docs/QA_CHECKLIST.md) | Smoke emulador/dispositivo |
| [GUIA_F30.md](docs/GUIA_F30.md) | Resumen guía académica |
| [BRANCH_PROTECTION.md](docs/BRANCH_PROTECTION.md) | Reglas de `main` / `develop` |
| [CONTRIBUTING.md](CONTRIBUTING.md) | Setup + PR paso a paso |

**Contacto:** Davidson (QA & DevOps / Release de la célula) — dudas de repo, CI, Project board o tests.

Proyecto académico IU Digital · 2026-2 B1.
