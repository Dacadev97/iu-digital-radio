# Contribución — IU Digital Radio

## Ramas

- `main` — estable / entregable
- `develop` — integración del sprint
- `feature/<squad>-<corto>` — trabajo por rol
- `fix/<corto>` — correcciones

## Pull requests

1. Base: `develop` (salvo hotfix urgente → `main`).
2. CI debe pasar (unit tests + assembleDebug + lint).
3. Completar template de PR (checklist QA).
4. Vincular issue (`Closes #N` / `Refs #N`).

## Labels sugeridos

`rf-01` … `rf-07` · `plat` · `devops` · `qa` · `po` · `bug` · `enhancement` · `blocked`

## Roles

PO prioriza; DevOps mantiene CI/APK; QA automation valida checklist y gates de Actions.
