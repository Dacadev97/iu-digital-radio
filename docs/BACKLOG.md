# Backlog F30 — IU Digital Radio (TDD)

**PO / QA:** Davidson Cadavid  
**Definition of Ready:** issue RF + test `Rf0X` en rojo + AC claros.  
**Definition of Done:** PR verde (`testDebugUnitTest` + assemble + lint), AC cumplidos.

## Épica MVP

| ID | Título | Test | Squad |
|----|--------|------|-------|
| RF-01 | UI 100% Compose | Rf01ComposeUiTest | UI/UX |
| RF-02 | Perfil + cámara | Rf02ProfileCameraTest | Hardware |
| RF-03 | Permisos CAMERA / VIBRATE | Rf03PermissionsTest | Hardware |
| RF-04 | Estado sesión | Rf04PlayerStateTest | State |
| RF-05 | Háptica controles | Rf05HapticTest | Hardware |
| RF-06 | Catálogo emisoras | Rf06StationCatalogTest | UI/UX |
| RF-07 | Media3 streaming | Rf07AudioStreamingTest | Audio |

## Plataforma

| ID | Título | Estado |
|----|--------|--------|
| PLAT-01 | Repo + ramas | Done |
| PLAT-02 | CI Actions | Done (gate TDD) |
| PLAT-03 | Project board | Done |
| PLAT-04 | Checklist QA + smoke | Ready |
| PLAT-05 | Release APK manual | Done |

Ver [TDD.md](TDD.md).
