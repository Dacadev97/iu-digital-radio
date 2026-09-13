# Onboarding — Llegué al repo de la célula, ¿qué hago?

Guía corta (≈10 min) · F30 · **modalidad célula** (4–6 personas).

## Contexto en 30 segundos

- Modalidad: **Célula** (Opción A1 · Software House en célula), no una “empresa” con muchos equipos en paralelo.
- Cada compañero toma **su issue** según el **rol** que le tocó en la célula.
- Este repo es **nuestra casa** (**privado**): solo trae DevOps (CI, APK) + **tests en rojo** (contrato TDD).
- Tú implementas el producto en una rama `feature/*` y lo entregas por **Pull Request** a `develop` (o push a `develop` si ya coordinaron; **nunca a `main`**).
- **Davidson** (`@Dacadev97`) = **PO + PM + QA + DevOps** (prioridades, board, pruebas/smoke, CI/APK, aprueba PRs / mergea `main`). Cubre el rol guía QA & DevOps / Release. Invita a la célula en **Settings → Collaborators**.
- Detalle de reglas: [BRANCH_PROTECTION.md](BRANCH_PROTECTION.md).

## Checklist del día 1

0. **Acceso:** acepta la invitación al repo privado (si no llega, pide a Davidson que te agregue en Settings → Collaborators).  
1. **Kickoff:** abre [#17](https://github.com/Dacadev97/iu-digital-radio/issues/17) y síguelo.  
2. **Board:** https://github.com/users/Dacadev97/projects/4 — mira **Todo** y filtra por tu `role-*`.  
3. **Mapa issue → rol:** [PARA_EL_EQUIPO.md](PARA_EL_EQUIPO.md) (qué tomar / qué ignorar).  
4. **Elige tu rol** en [ROLES.md](ROLES.md).  
5. **Reclama el issue:** Assignees → **Assign yourself** → mueve a **In Progress**.  
6. **Pon tu nombre** en la tabla *Quién toma qué rol* de [ROLES.md](ROLES.md) (PR a `develop` o comentario; no borres filas).  
7. **Setup + rama:** [CONTRIBUTING.md](../CONTRIBUTING.md) (clone → `develop` → `feature/<rol>-…`).  
8. Corre `./gradlew testDebugUnitTest` — **debe fallar**. Eso es el Red del TDD.  
9. Implementa hasta verdear **tus** tests; coordina con el compañero si hay dependencia.  
10. Abre PR a `develop` → tarjeta **Testing** (Davidson revisa/aprueba).  
11. Tras merge → **Done**. No pushees a `main`.

> **Antes de codear:** kickoff #17 → elige rol → reclama issue → **nombre en ROLES.md** → crea rama.  
> **No tomes:** PLAT Done (#8–#10), TDD cerrados (#13–#14), ni #11/#12 (solo Davidson).

## Mapa rápido de carpetas

```
iu-digital-radio/          ← casa de la célula
├── .github/workflows/     ← CI y Release APK (DevOps)
├── app/src/main/          ← shell vacío + contratos api/
├── app/src/test/          ← tests RF-01…07 (contrato QA)
├── docs/                  ← onboarding, roles, TDD, QA
├── scripts/               ← utilidades DevOps
├── README.md
└── CONTRIBUTING.md
```

## Reglas de oro (equipo pequeño)

| Sí | No |
|----|----|
| Rama `feature/*` desde `develop` | Commitear o pushear a `main` |
| PR a `develop` (ideal) o push a `develop` | Force-push a `develop` / `main` |
| Un issue / una rama | Meter todo el MVP en un solo PR gigante |
| Esperar approval de **Davidson** | Auto-merge sin review del dueño |
| Hablar con el compañero del rol dependiente | Borrar o “suavizar” tests para pasar CI |
| Añadir Compose/Media3 en **tu** PR | Asumir que el shell ya trae deps de producto |

## Mapa express (issue → rol)

| Tú eres… | Toma | Tests |
|----------|------|-------|
| UI | #1, #6 | `Rf01…`, `Rf06…` |
| Hardware | #2, #3, #5 | `Rf02…`, `Rf03…`, `Rf05…` |
| State | #4 | `Rf04…` |
| Audio | #7 | `Rf07…` |
| Todos al llegar | **#17 Kickoff** | — |

Detalle: [PARA_EL_EQUIPO.md](PARA_EL_EQUIPO.md).

## Si te atoras

1. Relee el body del issue (bloques **Pasos exactos**, **Tests**, **No hagas**).  
2. Mira el test `Rf0X…Test.kt` — es la especificación.  
3. Comenta en el issue o escribe a **Davidson** (QA/DevOps).

## Enlaces

- Repo: https://github.com/Dacadev97/iu-digital-radio  
- Project: https://github.com/users/Dacadev97/projects/4  
- Kickoff: https://github.com/Dacadev97/iu-digital-radio/issues/17  
- Actions: https://github.com/Dacadev97/iu-digital-radio/actions  
- [ROLES.md](ROLES.md) · [PARA_EL_EQUIPO.md](PARA_EL_EQUIPO.md) · [TDD.md](TDD.md) · [QA_CHECKLIST.md](QA_CHECKLIST.md) · [BRANCH_PROTECTION.md](BRANCH_PROTECTION.md)
