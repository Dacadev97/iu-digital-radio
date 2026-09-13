# IU Digital Radio

> **Este repo es la casa de nuestra célula** — shell de **DevOps + tests (TDD)**.  
> El código de producto (UI Compose, cámara, player, catálogo, Media3) lo aportan los **compañeros de la célula por PR**.  
> No hay MVP implementado en `main`/`develop` a propósito.

App Android (**Kotlin**) — Evidencia **F30** · IU Digital de Antioquia.

| Campo | Valor |
|-------|-------|
| **Modalidad** | **Célula** (Opción A1 · Software House en célula de **4–6** personas) |
| Repo | https://github.com/Dacadev97/iu-digital-radio |
| **Project board** | https://github.com/users/Dacadev97/projects/4 |
| Issues | https://github.com/Dacadev97/iu-digital-radio/issues |
| Actions (CI) | https://github.com/Dacadev97/iu-digital-radio/actions |
| QA & DevOps / Release (+ PO/board/CI) | **Davidson Cadavid** |

## ¿Llegaste recién? Empieza aquí

1. Lee **[docs/ONBOARDING.md](docs/ONBOARDING.md)** (5 minutos).
2. Mira tu rol en la célula: **[docs/ROLES.md](docs/ROLES.md)**.
3. Setup + primera PR: **[CONTRIBUTING.md](CONTRIBUTING.md)**.

## Cómo elegir tu issue (célula)

1. Abre el [Project board](https://github.com/users/Dacadev97/projects/4).
2. Filtra por el label de **tu rol** en la célula:
   - `role-ui` · `role-state` · `role-hardware` · `role-audio` · `role-qa-devops` · `role-po`
3. Toma **tu** issue, asígnatelo y mueve la tarjeta: **Todo → In Progress → Testing → Done**.
4. Coordina en el chat del grupo si hay dependencia (p. ej. State antes de cablear Audio).

Somos un equipo pequeño: colaboración cercana, PRs cortas, comunicación en el issue.

## Rol en la célula → issues

| Rol en la célula | Label | Issues |
|------------------|-------|--------|
| **UI/UX Compose** | `role-ui` | [#1 RF-01](https://github.com/Dacadev97/iu-digital-radio/issues/1), [#6 RF-06](https://github.com/Dacadev97/iu-digital-radio/issues/6) |
| **Hardware & Permissions** | `role-hardware` | [#2 RF-02](https://github.com/Dacadev97/iu-digital-radio/issues/2), [#3 RF-03](https://github.com/Dacadev97/iu-digital-radio/issues/3), [#5 RF-05](https://github.com/Dacadev97/iu-digital-radio/issues/5) |
| **State & Logic** | `role-state` | [#4 RF-04](https://github.com/Dacadev97/iu-digital-radio/issues/4) |
| **Audio & API** | `role-audio` | [#7 RF-07](https://github.com/Dacadev97/iu-digital-radio/issues/7) |
| **QA & DevOps / Release** | `role-qa-devops` | [#11 PLAT-04](https://github.com/Dacadev97/iu-digital-radio/issues/11), [#12 PLAT-05](https://github.com/Dacadev97/iu-digital-radio/issues/12) · (cerrados #8–#10) |
| **PO / coordinación board** | `role-po` | Board + priorización · (cerrado [#10](https://github.com/Dacadev97/iu-digital-radio/issues/10)) — **Davidson** también cubre esto junto a DevOps/QA |

Detalle y Definition of Done: [docs/ROLES.md](docs/ROLES.md).

## Flujo Git (casa de la célula)

```
main ← develop ← feature/<rol>-<issue>
```

1. `git clone` este repo y checkout **`develop`**.
2. Rama: `feature/ui-rf01`, `feature/hardware-rf03`, etc.
3. Implementa **solo** lo de tu issue hasta pasar tu test.
4. **PR → `develop`**. Davidson (o quien revise) valida CI.
5. Release APK: workflow manual **Release APK** (rol QA/DevOps).

El contrato TDD en `develop` empieza en **rojo** a propósito; cada PR de la célula va verdeando su RF. No merges con CI roja por errores de compilación/lint.

## TDD (Red → Green → Refactor)

| Fase | Qué pasa |
|------|----------|
| **Red** | `./gradlew testDebugUnitTest` **falla** — contrato QA |
| **Green** | Tu PR implementa lo mínimo para pasar los tests de tu RF |
| **Refactor** | Limpia sin romper CI; habla con el compañero si tocas su área |

| RF | Test | Quién en la célula |
|----|------|--------------------|
| RF-01 | `Rf01ComposeUiTest` | UI |
| RF-02 | `Rf02ProfileCameraTest` | Hardware |
| RF-03 | `Rf03PermissionsTest` | Hardware |
| RF-04 | `Rf04PlayerStateTest` | State |
| RF-05 | `Rf05HapticTest` | Hardware (+ State) |
| RF-06 | `Rf06StationCatalogTest` | UI (+ Audio URLs) |
| RF-07 | `Rf07AudioStreamingTest` | Audio |

Contratos (interfaces, **sin lógica de producto**):  
`app/src/main/java/edu/iudigital/radio/api/ProductContracts.kt`

## Qué hay en el repo hoy

| Incluido (DevOps / QA) | Lo construye la célula en PRs |
|------------------------|-------------------------------|
| GitHub Actions CI + Release APK | UI Compose del radio |
| Templates issue/PR + board | Cámara / ExoPlayer / catálogo |
| Shell `MainActivity` vacío | Dependencias Media3, Compose, etc. |
| Tests RF-01…07 en **rojo** | Implementaciones de `Product.*` |
| Docs de onboarding de la célula | Features de producto en `main` |

## Comandos

```bash
./gradlew testDebugUnitTest   # ROJO al inicio (esperado)
./gradlew assembleDebug       # shell debe compilar
./gradlew lintDebug
```

JDK **17** + Android SDK. Setup: [CONTRIBUTING.md](CONTRIBUTING.md).

## Docs

| Doc | Para qué |
|-----|----------|
| [ONBOARDING.md](docs/ONBOARDING.md) | “Llegué al repo de la célula, ¿qué hago?” |
| [ROLES.md](docs/ROLES.md) | Roles de la célula, issues, DoD |
| [PARA_EL_EQUIPO.md](docs/PARA_EL_EQUIPO.md) | Atajo al onboarding |
| [TDD.md](docs/TDD.md) | Contrato de tests |
| [BACKLOG.md](docs/BACKLOG.md) | Mapa RF / PLAT |
| [QA_CHECKLIST.md](docs/QA_CHECKLIST.md) | Smoke emulador/dispositivo |
| [GUIA_F30.md](docs/GUIA_F30.md) | Resumen guía académica |
| [CONTRIBUTING.md](CONTRIBUTING.md) | Setup + PR paso a paso |

**Contacto:** Davidson — QA & DevOps / Release (+ PO/board/CI). Dudas de repo, Actions o board.

Proyecto académico IU Digital · célula F30 · 2026-2 B1.
