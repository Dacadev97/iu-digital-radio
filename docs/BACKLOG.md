# Backlog F30 — IU Digital Radio

**Product Owner (bootstrap):** Davidson Cadavid  
**Definition of Ready:** issue con RF, AC, squad y estimado.  
**Definition of Done:** código en `develop` vía PR, CI verde, AC verificados, captura/evidencia si aplica UI.

## Épica: MVP guía oficial

| ID | Título | Prioridad | Squad | AC (resumen) |
|----|--------|-----------|-------|--------------|
| RF-01 | UI 100% Compose | P0 | UI/UX | Sin XML de layouts; pantallas en `@Composable` |
| RF-02 | Perfil + cámara preview | P0 | Hardware | `TakePicturePreview` actualiza avatar en estado |
| RF-03 | Permisos CAMERA / VIBRATE | P0 | Hardware | Declarados en Manifest; runtime CAMERA si aplica |
| RF-04 | Estado Compose | P0 | State | `mutableStateOf` / `rememberSaveable` en player/perfil |
| RF-05 | Vibración controles | P0 | Hardware | Play/Pause/Mute disparan háptica corta |
| RF-06 | LazyColumn emisoras | P0 | UI/UX | Lista scrolleable; selección cambia estación activa |
| RF-07 | Media3 streaming | P0 | Audio | Play/Pause/Mute afectan audio real (no solo UI) |

## Épica: Plataforma (PO / DevOps / QA)

| ID | Título | Prioridad | Owner |
|----|--------|-----------|-------|
| PLAT-01 | Repo GitHub + ramas main/develop | P0 | DevOps |
| PLAT-02 | CI Actions (test + APK + lint) | P0 | DevOps |
| PLAT-03 | Project board + labels | P0 | PO |
| PLAT-04 | Checklist QA + smoke APK | P0 | QA |
| PLAT-05 | Release APK manual Action | P1 | DevOps |
| PLAT-06 | Protección de rama main | P1 | DevOps |

## Columnas del Project

`Backlog` → `Ready` → `In Progress` → `In Review` → `Done`
