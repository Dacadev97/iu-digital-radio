# IU Digital Radio

App Android nativa (**Kotlin + Jetpack Compose**) — Evidencia F30 · IU Digital.

**Enfoque: TDD (Red → Green → Refactor)**  
Roles bootstrap: **PO · DevOps · QA Automation** — escriben el contrato (tests).  
**Developers** implementan el MVP hasta que CI quede en verde.

## Estado actual (intencional)

| Capa | Estado |
|------|--------|
| App | Stub mínimo (compila, sin MVP) |
| Tests unitarios RF-01…RF-07 | **ROJO** hasta implementar |
| CI | Corre tests + `assembleDebug` + lint |
| Producto | Lo construye el Software House |

## Flujo TDD

```
1. RED     → ./gradlew testDebugUnitTest   (falla: contrato)
2. GREEN   → implementar solo lo necesario para pasar tests
3. REFACTOR → limpiar sin romper CI
```

No merges a `main` con tests en rojo. Trabajar en `feature/*` → PR a `develop`.

## Requisitos (guía) ↔ tests

| ID | Requerimiento | Test |
|----|---------------|------|
| RF-01 | UI 100% Compose | `Rf01ComposeUiTest` |
| RF-02 | Perfil + cámara | `Rf02ProfileCameraTest` |
| RF-03 | Permisos CAMERA / VIBRATE | `Rf03PermissionsTest` |
| RF-04 | Estado play/pause/mute/estación | `Rf04PlayerStateTest` |
| RF-05 | Háptica en controles | `Rf05HapticTest` |
| RF-06 | Catálogo / LazyColumn ≥5 | `Rf06StationCatalogTest` |
| RF-07 | Streaming Media3 | `Rf07AudioStreamingTest` |

Contratos stub: `app/src/main/java/edu/iudigital/radio/mvp/MvpContracts.kt`

## Roles

| Squad | Qué hace |
|-------|----------|
| PO / QA / DevOps | Backlog, tests, CI, APK release |
| UI/UX Compose | RF-01, RF-06 UI |
| State & Logic | RF-04 (+ cableado) |
| Hardware | RF-02, RF-03, RF-05 |
| Audio & API | RF-07 Media3 |

## Git

```
main ← develop ← feature/<squad>-<corto>
```

1. Rama desde `develop`
2. Implementar hasta **verde** local: `./gradlew testDebugUnitTest assembleDebug`
3. PR → Actions verde
4. Release APK: workflow manual **Release APK**

## Comandos

```bash
./gradlew testDebugUnitTest   # debe fallar al inicio (RED)
./gradlew assembleDebug       # stub debe compilar
```

JDK **17** + Android SDK (`local.properties`).

## Docs

- [Backlog](docs/BACKLOG.md) · [QA](docs/QA_CHECKLIST.md) · [TDD](docs/TDD.md) · [Guía F30](docs/GUIA_F30.md) · [Contribuir](CONTRIBUTING.md)

Proyecto académico IU Digital · 2026-2 B1.
