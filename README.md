# IU Digital Radio

> **Este repo es un shell de DevOps + tests (TDD).**  
> El código de producto (UI Compose, cámara, player, catálogo, Media3) lo aportan los **squads por PR**.  
> No hay MVP implementado en `main`/`develop` a propósito.

App Android (**Kotlin**) — Evidencia **F30** · IU Digital de Antioquia · modalidad **Software House**.

| Recurso | URL |
|---------|-----|
| Repo | https://github.com/Dacadev97/iu-digital-radio |
| **Project board** | https://github.com/users/Dacadev97/projects/4 |
| Issues | https://github.com/Dacadev97/iu-digital-radio/issues |
| Actions (CI) | https://github.com/Dacadev97/iu-digital-radio/actions |
| PO / DevOps / QA | **Davidson Cadavid** |

## ¿Llegaste recién? Empieza aquí

1. Lee **[docs/ONBOARDING.md](docs/ONBOARDING.md)** (5 minutos).
2. Elige tu rol en **[docs/ROLES.md](docs/ROLES.md)**.
3. Sigue **[CONTRIBUTING.md](CONTRIBUTING.md)** para setup + primera PR.

## Cómo elegir tu issue

1. Abre el [Project board](https://github.com/users/Dacadev97/projects/4).
2. Filtra issues por label de rol:
   - `role-ui` · `role-state` · `role-hardware` · `role-audio` · `role-qa-devops` · `role-po`
3. Asigna el issue a ti y mueve la tarjeta: **Todo → In Progress → Testing → Done**.

## Rol → issues

| Rol (Software House) | Labels | Issues |
|----------------------|--------|--------|
| **UI/UX Compose** | `role-ui` | [#1 RF-01](https://github.com/Dacadev97/iu-digital-radio/issues/1), [#6 RF-06](https://github.com/Dacadev97/iu-digital-radio/issues/6) · (histórico [#13](https://github.com/Dacadev97/iu-digital-radio/issues/13)) |
| **Hardware & Permissions** | `role-hardware` | [#2 RF-02](https://github.com/Dacadev97/iu-digital-radio/issues/2), [#3 RF-03](https://github.com/Dacadev97/iu-digital-radio/issues/3), [#5 RF-05](https://github.com/Dacadev97/iu-digital-radio/issues/5) · (histórico [#14](https://github.com/Dacadev97/iu-digital-radio/issues/14)) |
| **State & Logic** | `role-state` | [#4 RF-04](https://github.com/Dacadev97/iu-digital-radio/issues/4) |
| **Audio & API** | `role-audio` | [#7 RF-07](https://github.com/Dacadev97/iu-digital-radio/issues/7) |
| **QA & DevOps / Release** | `role-qa-devops` | [#11 PLAT-04](https://github.com/Dacadev97/iu-digital-radio/issues/11), [#12 PLAT-05](https://github.com/Dacadev97/iu-digital-radio/issues/12) · (cerrados #8–#10) |
| **PO / Tech Lead** | `role-po` | Coordinación · (cerrado [#10](https://github.com/Dacadev97/iu-digital-radio/issues/10)) |

Detalle de DoD por rol: [docs/ROLES.md](docs/ROLES.md).

## Flujo Git (importante)

```
main ← develop ← feature/<rol>-<issue>
```

1. `git clone` el repo (o fork) y checkout **`develop`**.
2. Crea rama: `feature/ui-rf01`, `feature/hardware-rf03`, etc.
3. Implementa **solo** lo de tu issue hasta que pase tu test.
4. Abre **PR → `develop`**. CI debe quedar verde en tu alcance (ideal: suite completa al integrar).
5. Tras review, merge. Release APK: workflow manual **Release APK**.

**No merges a `main`/`develop` con CI en rojo** (salvo el estado inicial del contrato TDD que ya está en rojo a propósito en `develop` hasta que los squads verdeen).

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

| Incluido (DevOps / QA) | No incluido (lo hacen los squads) |
|------------------------|-----------------------------------|
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
| [ROLES.md](docs/ROLES.md) | Roles, issues, DoD |
| [TDD.md](docs/TDD.md) | Contrato de tests |
| [BACKLOG.md](docs/BACKLOG.md) | Mapa RF / PLAT |
| [QA_CHECKLIST.md](docs/QA_CHECKLIST.md) | Smoke emulador/dispositivo |
| [GUIA_F30.md](docs/GUIA_F30.md) | Resumen guía académica |
| [CONTRIBUTING.md](CONTRIBUTING.md) | Setup + PR paso a paso |

**Contacto:** Davidson (PO · DevOps · QA Automation) — dudas de repo, CI, Project board o tests.

Proyecto académico IU Digital · 2026-2 B1.
