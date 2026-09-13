# Contribución — IU Digital Radio (TDD)

## Red → Green → Refactor

1. **Red:** los tests en `app/src/test/.../Rf0*.kt` definen el contrato. Al clonar deben **fallar**.
2. **Green:** implementar en `main` / `mvp` lo mínimo para pasar.
3. **Refactor:** mejorar diseño sin romper CI.

No bajar la barra del test para “hacer pasar” CI. Si el contrato está mal, abrir issue con label `po` / `qa`.

## Dónde tocar código

| Área | Archivos típicos |
|------|------------------|
| Contratos / stubs | `mvp/MvpContracts.kt`, `Station.kt` |
| UI Compose | `MainActivity.kt` + pantallas nuevas |
| Permisos | `AndroidManifest.xml` |
| Audio | Media3 en `app/build.gradle.kts` + implementación de `RadioSession` |

Punto de entrada de tests: `Mvp.profileCamera()` y `Mvp.radioSession(haptic)`.

## Ramas y PR

- Base: `develop`
- CI debe quedar **verde** (unit tests + assembleDebug + lint)
- Completar checklist del PR template
- Vincular issue RF (`Closes #N`)

## Labels

`rf-01`…`rf-07` · `plat` · `devops` · `qa` · `po` · `mvp`
