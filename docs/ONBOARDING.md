# Onboarding — Llegué al repo de la célula, ¿qué hago?

Guía corta (≈10 min) · F30 · **modalidad célula** (4–6 personas).

## Contexto en 30 segundos

- Modalidad: **Célula** (Opción A1 · Software House en célula), no una “empresa” con muchos equipos en paralelo.
- Cada compañero toma **su issue** según el **rol** que le tocó en la célula.
- Este repo es **nuestra casa**: solo trae DevOps (CI, APK) + **tests en rojo** (contrato TDD).
- Tú implementas el producto en una rama y lo entregas por **Pull Request** a `develop`.
- **Davidson** = QA & DevOps / Release (+ PO/board/CI).

## Checklist del día 1

1. **Board de la célula:** https://github.com/users/Dacadev97/projects/4  
2. **Tu rol:** [ROLES.md](ROLES.md) — issues y Definition of Done.  
3. **Filtra** por label `role-*` (ej. `role-ui`).  
4. **Toma tu issue**, asígnatelo → **In Progress**.  
5. **Setup:** [CONTRIBUTING.md](../CONTRIBUTING.md) (clone → `develop` → rama).  
6. Corre `./gradlew testDebugUnitTest` — **debe fallar**. Eso es el Red del TDD.  
7. Implementa hasta verdear **tus** tests; coordina con el compañero si hay dependencia.  
8. Abre PR a `develop` → tarjeta **Testing**.  
9. Tras merge → **Done**.

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
| Trabajar desde `develop` | Commitear directo a `main` |
| Un issue / una rama | Meter todo el MVP en un solo PR gigante |
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
- [ROLES.md](ROLES.md) · [TDD.md](TDD.md) · [BACKLOG.md](BACKLOG.md) · [QA_CHECKLIST.md](QA_CHECKLIST.md)
