# Onboarding — Llegué al repo de la célula, ¿qué hago?

Guía corta (≈10 min) · F30 · **modalidad célula** (4–6 personas).

## Contexto en 30 segundos

- Modalidad: **Célula** (Opción A1 · Software House en célula), no una “empresa” con muchos equipos en paralelo.
- Cada compañero toma **su issue** según el **rol** que le tocó en la célula.
- Este repo es **nuestra casa** (**privado**): solo trae DevOps (CI, APK) + **tests en rojo** (contrato TDD).
- Tú implementas el producto en una rama `feature/*` y lo entregas por **Pull Request** a `develop` (o push a `develop` si ya coordinaron; **nunca a `main`**).
- **Davidson** (`@Dacadev97`) = único que **aprueba** PRs / mergea a `main`. También QA & DevOps / Release (+ PO/board/CI). Invita a la célula en **Settings → Collaborators**.
- Detalle de reglas: [BRANCH_PROTECTION.md](BRANCH_PROTECTION.md).

## Checklist del día 1

0. **Acceso:** acepta la invitación al repo privado (si no llega, pide a Davidson que te agregue en Settings → Collaborators).  
1. **Board de la célula:** https://github.com/users/Dacadev97/projects/4  
2. **Elige tu rol** en [ROLES.md](ROLES.md) (issues y Definition of Done).  
3. **Reclama el issue:** filtra por label `role-*` (ej. `role-ui`), asígnatelo → **In Progress**.  
4. **Pon tu nombre** en la tabla *Quién toma qué rol* de [ROLES.md](ROLES.md) (PR a `develop` o comentario en el issue; no borres filas).  
5. **Setup + rama:** [CONTRIBUTING.md](../CONTRIBUTING.md) (clone → `develop` → `feature/<rol>-…`).  
6. Corre `./gradlew testDebugUnitTest` — **debe fallar**. Eso es el Red del TDD.  
7. Implementa hasta verdear **tus** tests; coordina con el compañero si hay dependencia.  
8. Abre PR a `develop` → tarjeta **Testing** (Davidson revisa/aprueba).  
9. Tras merge → **Done**. No pushees a `main`.

> **Antes de codear:** elige rol → reclama issue → **pon tu nombre en la tabla de ROLES.md** → crea rama.

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

## Si te atoras

1. Relee el body del issue (sección **Rol:** y AC).  
2. Mira el test `Rf0X…Test.kt` — es la especificación.  
3. Comenta en el issue o escribe a **Davidson** (QA/DevOps).

## Enlaces

- Repo: https://github.com/Dacadev97/iu-digital-radio  
- Project: https://github.com/users/Dacadev97/projects/4  
- Actions: https://github.com/Dacadev97/iu-digital-radio/actions  
- [ROLES.md](ROLES.md) · [TDD.md](TDD.md) · [BACKLOG.md](BACKLOG.md) · [QA_CHECKLIST.md](QA_CHECKLIST.md) · [BRANCH_PROTECTION.md](BRANCH_PROTECTION.md)
