# IU Digital Radio

App Android nativa (**Kotlin + Jetpack Compose**) — Evidencia F30 · Programación de Dispositivos Móviles · IU Digital.

**Modalidad:** Software House (trabajo colaborativo por roles)  
**Repo lead / roles activos en este bootstrap:** **PO · DevOps · QA Automation** (Davidson Cadavid)

## MVP (guía oficial)

| ID | Requerimiento | Estado scaffold |
|----|---------------|-----------------|
| RF-01 | UI 100% Jetpack Compose | ✅ estructura |
| RF-02 | Perfil + cámara (`TakePicturePreview`) | ✅ launcher |
| RF-03 | Permisos CAMERA / VIBRATE en Manifest | ✅ |
| RF-04 | Estado `mutableStateOf` / `rememberSaveable` | ✅ |
| RF-05 | Vibración en Play/Pause/Mute | ✅ |
| RF-06 | `LazyColumn` de emisoras | ✅ mock |
| RF-07 | Audio Media3 / streaming | ⏳ dependencia + pendiente Audio squad |

## Roles (Software House)

| Squad | Responsabilidad |
|-------|-----------------|
| PO / Tech Lead | Backlog, RF, priorización, merges |
| UI/UX Compose | Layout declarativo |
| State & Logic | Estado e interactividad |
| Hardware & Permissions | Cámara, vibración, runtime permissions |
| Audio & API | ExoPlayer + streams / Radio Browser |
| **QA & DevOps / Release** | CI, pruebas, APK, distribución |

## Flujo Git

```
main          # protegida — solo vía PR
develop       # integración continua del equipo
feature/*     # trabajo por squad
fix/*         # hotfixes
```

1. Crear rama desde `develop`.
2. Abrir PR → Actions debe pasar (unit test + assembleDebug + lint).
3. Review + merge.
4. Release APK: Action manual **Release APK** o artifact del job `build-apk`.

## Comandos locales

```bash
./gradlew testDebugUnitTest
./gradlew assembleDebug
# APK: app/build/outputs/apk/debug/
```

Requiere **JDK 17** y Android SDK (`local.properties` → `sdk.dir`).

## CI/CD (GitHub Actions)

- `.github/workflows/ci.yml` — en push/PR a `main` y `develop`
- `.github/workflows/release-apk.yml` — disparo manual para empaquetar APK

## Documentación

- [Backlog / Definition of Done](docs/BACKLOG.md)
- [QA checklist](docs/QA_CHECKLIST.md)
- [Guía F30 (Canvas/Drive)](docs/GUIA_F30.md)

## Licencia / uso académico

Proyecto académico IU Digital · Periodo 2026-2 B1.
