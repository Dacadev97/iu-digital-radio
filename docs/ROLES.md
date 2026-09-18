# Roles — Célula F30

**Modalidad:** Célula (Opción A1 · Software House en célula de **4–6**).  
Roles de la guía del tutor, repartidos **dentro de la misma célula** (un repo, colaboración cercana).

| Quién | Qué cubre |
|-------|-----------|
| **Davidson Cadavid** (`@Dacadev97`) | **PO + PM + QA + DevOps** — Product Owner, Project Manager, QA automation/smoke y DevOps/Release (CI, board, approvals, APK). Cubre el rol guía **QA & DevOps / Release**. |
| Compañeros de la célula | UI, Hardware, State, Audio (según issue asignado) |

**Board:** https://github.com/users/Dacadev97/projects/4

---

## Quién toma qué rol (escribe tu nombre)

Antes de codear: elige un rol libre → reclama el issue en el board → **pon tu nombre en esta tabla** → crea tu rama.

**Cómo actualizarla:** edita esta tabla en un PR a `develop` o comenta en el issue de tu rol. **No borres filas.**

| Rol | Nombre (compañero/a) | Issues típicos | GitHub user    |
|-----|----------------------|----------------|----------------|
| UI/UX Compose | Camilo Velásquez     | #1 #6 | Kamilo         |
| State & Logic | Brayan y Pedro       | #4 | Pedromonvel94               |
| Hardware & Permissions | Victor               | #2 #3 #5 | Victor-Quiceno |
| Audio & API | Pedro Montoya        | #7 | Pedromonvel94  |
| PO (Product Owner) | Davidson Cadavid     | Backlog, prioridades, AC | Dacadev97      |
| PM (Project Manager) | Davidson Cadavid     | Board, kickoff #17, seguimiento | Dacadev97      |
| QA (Quality Assurance) | Davidson Cadavid     | Checklist, smoke #11, gate Testing | Dacadev97      |
| DevOps / Release | Davidson Cadavid     | CI, APK #12, approvals, repo | Dacadev97      |

---

## Resumen técnico

| Rol en la célula | Label | Issues abiertos | Colabora con |
|------------------|-------|-----------------|--------------|
| UI/UX Compose | `role-ui` | #1, #6 | State, Audio |
| Hardware & Permissions | `role-hardware` | #2, #3, #5 | UI, State |
| State & Logic | `role-state` | #4 | UI, Audio, Hardware |
| Audio & API | `role-audio` | #7 | State, UI |
| QA | `role-qa-devops` | #11, checklist | Toda la célula (Testing) |
| DevOps / Release | `role-qa-devops` | #12, CI, APK | Toda la célula |
| PO + PM | `role-po` | Board, #17, backlog | Toda la célula |

Issues cerrados de plataforma (#8–#10) y TDD históricos (#13–#14) **ya están hechos** — no los tomen.  
Trabajo de producto: **#1–#7**. Primer paso de la célula: **[#17 Kickoff](https://github.com/Dacadev97/iu-digital-radio/issues/17)**.  
Mapa issue → rol: [PARA_EL_EQUIPO.md](PARA_EL_EQUIPO.md).

---

## UI/UX Compose (`role-ui`)

**Issues:** [#1 RF-01](https://github.com/Dacadev97/iu-digital-radio/issues/1), [#6 RF-06](https://github.com/Dacadev97/iu-digital-radio/issues/6)

**Qué hacer**

- Añadir Jetpack Compose al módulo `app` (deps en tu PR).
- Pantallas composables: home/player, perfil, lista.
- Catálogo ≥5 emisoras en `StationCatalog.defaults` (acuerda URLs con Audio).
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
- Háptica: `RadioSession` debe llamar `HapticPort.tick()` en play/pause/mute (coordina con State).

**DoD**

- [ ] `Rf02`, `Rf03`, `Rf05` en verde
- [ ] App no crashea si el usuario deniega permisos

---

## State & Logic (`role-state`)

**Issues:** [#4 RF-04](https://github.com/Dacadev97/iu-digital-radio/issues/4)

**Qué hacer**

- Implementar `RadioSession` con estado mutable (play/pause/mute/estación).
- Exponer vía `Product.radioSession(haptic)`.
- Integrar con Compose en coordinación con UI.

**DoD**

- [ ] `Rf04PlayerStateTest` en verde
- [ ] Estado predecible y testeable

---

## Audio & API (`role-audio`)

**Issues:** [#7 RF-07](https://github.com/Dacadev97/iu-digital-radio/issues/7)

**Qué hacer**

- Añadir `androidx.media3:media3-exoplayer` en `app/build.gradle.kts`.
- Cablear ExoPlayer a `RadioSession` (play/pause/mute/cambio de estación).
- Acordar URLs de stream con UI para el catálogo.

**DoD**

- [ ] `Rf07AudioStreamingTest` en verde
- [ ] Audio real (no solo flags de UI)

---

## QA + DevOps / Release (`role-qa-devops`) — Davidson

**También:** PO + PM (ver sección siguiente).

**Issues:** [#11 PLAT-04](https://github.com/Dacadev97/iu-digital-radio/issues/11), [#12 PLAT-05](https://github.com/Dacadev97/iu-digital-radio/issues/12)

**Qué hacer**

- Mantener CI, ramas y el board de la célula.
- Smoke con [QA_CHECKLIST.md](QA_CHECKLIST.md) cuando el MVP esté integrado.
- Distribuir APK (workflow **Release APK** + Drive del grupo).
- Aprobar PRs / merge a `main` (único Code Owner).

**DoD**

- [ ] Evidencias de smoke para el PDF Canvas
- [ ] APK instalable compartido a la célula

---

## PO + PM + QA + DevOps (`role-po` / `role-qa-devops`) — Davidson

En esta célula **Davidson** asume **cuatro roles**:

**PO (Product Owner)**

- Priorizar backlog RF-01…07 y AC.
- Definir qué entra en el MVP de entrega.

**PM (Project Manager)**

- Kickoff [#17](https://github.com/Dacadev97/iu-digital-radio/issues/17), seguimiento del board (Todo → In Progress → Testing → Done).
- Desbloquear dependencias entre roles de la célula.
- Asegurar integración en `develop` antes de Canvas.
- Coordinar documento escrito / video con el equipo.

**QA (Quality Assurance)**

- Mantener [QA_CHECKLIST.md](QA_CHECKLIST.md) y el gate de la columna **Testing**.
- Smoke en emulador/dispositivo (#11) cuando el MVP esté integrado.
- No mergear a `main` si CI o smoke fallan.

**DevOps / Release**

- CI Actions, release APK (#12), CODEOWNERS, invitaciones al repo privado.
- Único aprobador de merges a `main`.

## Contratos técnicos (punto de partida)

Archivo: `app/src/main/java/edu/iudigital/radio/api/ProductContracts.kt`

| Símbolo | Quién lo completa |
|---------|-------------------|
| `ComposeUiContract` | UI |
| `Product.profileCamera()` | Hardware |
| `Product.radioSession()` | State (+ Audio + háptica Hardware) |
| `StationCatalog.defaults` | UI (+ Audio URLs) |

Hoy lanzan `NotImplementedError` o están en rojo a propósito. **Eso es el contrato TDD de la célula.**
