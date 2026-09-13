# Para el equipo (célula)

Trabajamos en **modalidad célula** (4–6 personas).

→ Empieza en **[ONBOARDING.md](ONBOARDING.md)** · kickoff: [#17](https://github.com/Dacadev97/iu-digital-radio/issues/17).

También: [ROLES.md](ROLES.md) · [TDD.md](TDD.md) · [../CONTRIBUTING.md](../CONTRIBUTING.md) · [../README.md](../README.md)

**Board de la célula:** https://github.com/users/Dacadev97/projects/4  
**Repo (casa, privado):** https://github.com/Dacadev97/iu-digital-radio  
**Documento escrito de la célula (Google Docs):** https://docs.google.com/document/d/1A2VFSnmZf0X946tLOFOfcxVCrduWtBdiIYpUAiPPZgo/edit?usp=sharing  
→ De ahí sale el **PDF técnico grupal** para Canvas (F30).  
**Acceso:** Davidson invita a cada integrante en GitHub → **Settings → Collaborators** → *Add people*.  
**PO + PM + QA + DevOps:** Davidson Cadavid (`@Dacadev97`) — backlog, board, QA/smoke, CI, approvals, APK.

## Antes de codear

1. Elige rol en [ROLES.md](ROLES.md).  
2. Reclama tu issue en el board (**Assignees → Assign yourself**).  
3. **Pon tu nombre** en la tabla *Quién toma qué rol* (PR a `develop` o comentario en el issue; no borres filas).  
4. Crea tu rama desde `develop`.

---

## Mapa rápido: issue → rol

| Issue | Título corto | Rol | Label | ¿La tomo? |
|-------|--------------|-----|-------|-----------|
| **#17** | Kickoff — nombre en ROLES | Toda la célula | — | **Sí, primero** |
| **#1** | UI Compose | UI/UX Compose | `role-ui` | Sí (producto) |
| **#2** | Cámara perfil | Hardware | `role-hardware` | Sí |
| **#3** | Permisos Manifest | Hardware | `role-hardware` | Sí |
| **#4** | Estado player | State & Logic | `role-state` | Sí |
| **#5** | Vibración | Hardware | `role-hardware` | Sí |
| **#6** | Lista emisoras | UI/UX Compose | `role-ui` | Sí |
| **#7** | Audio Media3 | Audio & API | `role-audio` | Sí |
| #11 | Smoke QA | Davidson | `role-qa-devops` | **No** (solo QA) |
| #12 | Release APK | Davidson | `role-qa-devops` | **No** |
| #8–#10 | PLAT hechos | — | `plat` | **No** (Done / cerrados) |
| #13–#14 | TDD viejos | — | — | **No** (cerrados; contrato = tests `Rf0X`) |

### Cómo leer el board

| Columna | Significado |
|---------|-------------|
| **Todo** | Libre o listo para reclamar (RF + kickoff) |
| **In Progress** | Alguien lo está haciendo |
| **Testing** | PR listo → Davidson hace smoke |
| **Done** | Terminado (incluye PLAT de infra) |

Filtra por label `role-ui` / `role-hardware` / `role-state` / `role-audio` para ver solo lo tuyo.
