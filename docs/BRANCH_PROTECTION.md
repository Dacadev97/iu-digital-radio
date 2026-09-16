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
`restrictions` (allowlist de push) **no aplica** en repos de cuenta personal — queda `null`; el bloqueo de push a `main` para la célula lo dan **PR obligatorio + 1 approval + Code Owners**.

## Reglas configuradas

### `main` (estricta para la célula; bypass admin consciente)

- Require a pull request before merging
- Required approving reviews: **1**
- Require review from **Code Owners** (`.github/CODEOWNERS` → `@Dacadev97`)
- Dismiss stale reviews when new commits are pushed
- **`enforce_admins: false` (permanente en este repo académico)** — Davidson es el único admin/owner; si `true`, no puede mergear sus propios syncs `develop→main` (nadie más puede aprobar). Con `false`, el resto de la célula sigue obligado a PR + review; el owner puede bypass solo para syncs/admin.
- Block force pushes
- Block deletions
- Require conversation resolution before merging
- Required status checks (strict):
  - `Assemble debug APK`
  - `Android Lint`
  - `Unit tests (TDD contract)` — **bloqueante solo en `main`** (release). En PRs a `develop` el mismo job es informativo (`continue-on-error`) para no frenar PRs parciales por RF ajenos todavía en rojo.

### Sync `develop` → `main` (owner)

Flujo normal: PR + CI verde. Si Code Owners se bloquea a sí mismo (cuenta personal, único reviewer):

1. Preferido: merge del PR con bypass de admin (`gh pr merge <n> --admin`) **solo si el token lo permite**.
2. Si `--admin` no basta: bajar temporalmente `required_approving_review_count` a `0`, mergear, restaurar a `1` + Code Owners, dejando **`enforce_admins: false`**.
3. No uses force-push a `main`.

### `develop` (trabajo de la célula)

- Collaborators **pueden pushear** (sin PR obligatorio)
- Block force pushes
- Block deletions
- Sin required reviews en la regla (para no frenar push directo)
- Si abren PR a `develop`: política de equipo = **Davidson aprueba** antes de merge

## Fallback UI (si hay que reaplicar)

**Settings → Branches → Add/Edit branch protection rule**

1. Pattern `main`: PR required, 1 approval, Code Owners, Dismiss stale, **Do not include administrators** (`enforce_admins: false`), status checks (`Assemble debug APK`, `Android Lint`, `Unit tests (TDD contract)`), Block force pushes / deletions, Require conversation resolution.
2. Pattern `develop`: Allow force pushes **off**, Allow deletions **off**; no exigir PR.

## Identidad Git (sin Cursor Agent en contributors)

Author/committer unificado en este repo:

```bash
git config user.name "Davidson Cadavid"
git config user.email "149860458+Dacadev97@users.noreply.github.com"
```

(Compañeros de la célula: usen **su** nombre + email noreply de GitHub.)

### Evitar `Co-authored-by: Cursor <cursoragent@cursor.com>`

Ese trailer hace que GitHub liste **cursoragent / Cursor Agent** en Contributors aunque el author sea Davidson. Tras un rewrite de ramas, los commits viejos **siguen vivos** en `refs/pull/*/head` (read-only) y GitHub no deja borrarlos.

**En Cursor IDE (Davidson / quien use Agent):**

1. Cursor Settings → busca *co-author* / *Attribution* / *Agent commit*.
2. Desactiva la opción que añade automáticamente `Co-authored-by: Cursor` a los commits (nombre exacto varía por versión).
3. Antes de push, revisa: `git log -1 --format='%B'` — no debe aparecer esa línea.

Si ya se coló en un commit local: `git commit --amend` **solo si no está en remoto compartido**, o un commit nuevo sin el trailer (nunca reescribir `develop`/`main` compartidos).

## Otras restricciones recomendadas

Ver [CONTRIBUTING.md](../CONTRIBUTING.md#política-de-ramas-célula-f30).
