# Roles — Software House F30

Modalidad de la guía: equipos por especialidad.  
**PO / DevOps / QA (bootstrap):** Davidson Cadavid.

Board: https://github.com/users/Dacadev97/projects/4

## Resumen

| Rol | Label | Issues abiertos | Colabora con |
|-----|-------|-----------------|--------------|
| UI/UX Compose | `role-ui` | #1, #6 | State, Audio |
| Hardware & Permissions | `role-hardware` | #2, #3, #5 | UI, State |
| State & Logic | `role-state` | #4 | UI, Audio, Hardware |
| Audio & API | `role-audio` | #7 | State, UI |
| QA & DevOps / Release | `role-qa-devops` | #11, #12 | Todos (smoke/release) |
| PO / Tech Lead | `role-po` | Coordinación | Todos |

Issues cerrados de plataforma/TDD (#8–#10, #13–#14) ya están resueltos en el shell; el trabajo de producto vive en #1–#7.

---

## UI/UX Compose (`role-ui`)

**Issues:** [#1 RF-01](https://github.com/Dacadev97/iu-digital-radio/issues/1), [#6 RF-06](https://github.com/Dacadev97/iu-digital-radio/issues/6)

**Qué hacer**

- Añadir Jetpack Compose al módulo `app` (deps en tu PR).
- Pantallas composables: home/player, perfil, lista.
- Catálogo ≥5 emisoras en `StationCatalog.defaults` (con Audio para URLs).
- `ComposeUiContract.homeScreenImplemented = true` cuando la home exista.
- `MainActivity` con `setContent { … }` (hoy es Activity vacía).

**DoD**

- [ ] `Rf01ComposeUiTest` y `Rf06StationCatalogTest` en verde
- [ ] Sin `res/layout/*.xml`
- [ ] PR a `develop` con CI OK en tu alcance

---

## Hardware & Permissions (`role-hardware`)

**Issues:** [#2](https://github.com/Dacadev97/iu-digital-radio/issues/2), [#3](https://github.com/Dacadev97/iu-digital-radio/issues/3), [#5](https://github.com/Dacadev97/iu-digital-radio/issues/5)

**Qué hacer**

- Declarar `CAMERA`, `VIBRATE`, `INTERNET` en `AndroidManifest.xml`.
- Implementar `ProfileCamera` y cablear `Product.profileCamera()`.
- Háptica: `RadioSession` debe llamar `HapticPort.tick()` en play/pause/mute.

**DoD**

- [ ] `Rf02`, `Rf03`, `Rf05` en verde
- [ ] App no crashea si el usuario deniega permisos (manejar en UI/State)

---

## State & Logic (`role-state`)

**Issues:** [#4 RF-04](https://github.com/Dacadev97/iu-digital-radio/issues/4)

**Qué hacer**

- Implementar `RadioSession` con estado mutable (play/pause/mute/estación).
- Exponer vía `Product.radioSession(haptic)`.
- Integrar con Compose (`mutableStateOf` / `rememberSaveable`) en coordinación con UI.

**DoD**

- [ ] `Rf04PlayerStateTest` en verde
- [ ] Estado predecible y testeable (sin lógica solo en la Activity)

---

## Audio & API (`role-audio`)

**Issues:** [#7 RF-07](https://github.com/Dacadev97/iu-digital-radio/issues/7)

**Qué hacer**

- Añadir `androidx.media3:media3-exoplayer` (y ui si aplica) en `app/build.gradle.kts`.
- Cablear ExoPlayer a `RadioSession` (play/pause/mute/cambio de estación).
- Acordar URLs de stream con UI para el catálogo.

**DoD**

- [ ] `Rf07AudioStreamingTest` en verde
- [ ] Audio real (no solo flags de UI)

---

## QA & DevOps / Release (`role-qa-devops`)

**Issues:** [#11 PLAT-04](https://github.com/Dacadev97/iu-digital-radio/issues/11), [#12 PLAT-05](https://github.com/Dacadev97/iu-digital-radio/issues/12)

**Qué hacer**

- Mantener CI (ya bootstrap).
- Smoke con [QA_CHECKLIST.md](QA_CHECKLIST.md) cuando el MVP esté integrado.
- Distribuir APK (workflow **Release APK** + Drive del grupo).

**DoD**

- [ ] Evidencias de smoke (capturas) para el PDF Canvas
- [ ] APK instalable compartido al equipo

---

## PO / Tech Lead (`role-po`)

**Qué hacer**

- Priorizar board, desbloquear dependencias entre squads.
- Asegurar integración en `develop` antes de la entrega Canvas.
- No implementar features de producto salvo acuerdo del equipo.

---

## Contratos técnicos (punto de partida)

Archivo: `app/src/main/java/edu/iudigital/radio/api/ProductContracts.kt`

| Símbolo | Quién lo completa |
|---------|-------------------|
| `ComposeUiContract` | UI |
| `Product.profileCamera()` | Hardware |
| `Product.radioSession()` | State (+ Audio + Hardware háptica) |
| `StationCatalog.defaults` | UI (+ Audio URLs) |

Hoy lanzan `NotImplementedError` o están en rojo a propósito. **Eso es el contrato TDD.**
