# Protección de ramas — IU Digital Radio (F30 · célula)

Política de Git para el repo privado `Dacadev97/iu-digital-radio`.  
**Único aprobador / release:** Davidson (`@Dacadev97`).

## Modelo de ramas

```
main  ←  develop  ←  feature/<rol>-<issue>
```

| Rama | Quién escribe | Cómo entra el código |
|------|---------------|----------------------|
| `main` | Nadie por push directo | Solo **PR** + **1 approval de Davidson** + CI |
| `develop` | Collaborators (célula) | Push directo **o** PR desde `feature/*` |
| `feature/*` | Cada compañero | Trabajo diario; PR ideal → `develop` |

Ideal (recomendado en la célula): `feature/*` → PR a `develop` → (Davidson aprueba) → más tarde `develop` → PR a `main` (solo Davidson mergea release).

## Estado

Configurado vía API (`gh`) el **2026-09-13** sobre el repo privado.  
`restrictions` (allowlist de push) **no aplica** en repos de cuenta personal — queda `null`; el bloqueo de push a `main` lo dan PR obligatorio + `enforce_admins`.

## Reglas configuradas

### `main` (estricta)

- Require a pull request before merging
- Required approving reviews: **1**
- Require review from **Code Owners** (`.github/CODEOWNERS` → `@Dacadev97`)
- Dismiss stale reviews when new commits are pushed
- Enforce for administrators (`enforce_admins: true`) — sin bypass
- Block force pushes
- Block deletions
- Require conversation resolution before merging
- Required status checks (strict):
  - `Assemble debug APK`
  - `Android Lint`
  - *No* se exige `Unit tests (TDD contract)` todavía: el contrato está en **rojo a propósito**; activarlo cuando la célula lo verdee

### `develop` (trabajo de la célula)

- Collaborators **pueden pushear** (sin PR obligatorio)
- Block force pushes
- Block deletions
- Sin required reviews en la regla (para no frenar push directo)
- Si abren PR a `develop`: política de equipo = **Davidson aprueba** antes de merge

## Fallback UI (si hay que reaplicar)

**Settings → Branches → Add/Edit branch protection rule**

1. Pattern `main`: PR required, 1 approval, Code Owners, Dismiss stale, Include administrators, status checks (`Assemble debug APK`, `Android Lint`), Block force pushes / deletions, Require conversation resolution.
2. Pattern `develop`: Allow force pushes **off**, Allow deletions **off**; no exigir PR.

## Otras restricciones recomendadas

Ver [CONTRIBUTING.md](../CONTRIBUTING.md#política-de-ramas-célula-f30).
