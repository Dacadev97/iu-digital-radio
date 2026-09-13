# Onboarding — Llegué al repo, ¿qué hago?

Guía corta (≈10 min) para el equipo F30 · IU Digital Radio.

## Contexto en 30 segundos

- Estamos en modalidad **Software House**: cada rol toma **sus issues**.
- Este repositorio **no trae el MVP hecho**. Solo DevOps (CI, APK) + **tests en rojo** (contrato TDD).
- Tú implementas el producto en una rama y lo entregas por **Pull Request**.

## Checklist del día 1

1. **Entra al board:** https://github.com/users/Dacadev97/projects/4  
2. **Lee tu rol:** [ROLES.md](ROLES.md) — qué issues te tocan y el Definition of Done.  
3. **Filtra** issues por label `role-*` (ej. `role-ui`).  
4. **Toma un issue** (asígnatelo) y muévelo a **In Progress**.  
5. **Setup local:** sigue [CONTRIBUTING.md](../CONTRIBUTING.md) (clone → `develop` → rama).  
6. Corre `./gradlew testDebugUnitTest` — **debe fallar**. Eso es normal (Red).  
7. Implementa hasta verdear **tus** tests.  
8. Abre PR a `develop`, mueve tarjeta a **Testing**.  
9. Tras merge → **Done**.

## Mapa rápido de carpetas

```
iu-digital-radio/
├── .github/workflows/     ← CI y Release APK (DevOps)
├── app/src/main/          ← shell vacío + contratos api/ (sin MVP)
├── app/src/test/          ← tests RF-01…07 (QA / contrato)
├── docs/                  ← onboarding, roles, TDD, QA
├── scripts/               ← utilidades DevOps
├── README.md              ← portada del proyecto
└── CONTRIBUTING.md        ← setup paso a paso
```

## Reglas de oro

| Sí | No |
|----|----|
| Trabajar desde `develop` | Commitear directo a `main` |
| Una feature / un issue por rama | Implementar todo el MVP en un solo PR gigante |
| Pedir ayuda en el issue | Borrar o “suavizar” tests para pasar CI |
| Añadir Compose/Media3 en **tu** PR | Asumir que el shell ya trae dependencias de producto |

## Si te atoras

1. Relee el body del issue (sección **Rol:** y AC).  
2. Mira el test `Rf0X…Test.kt` — es la especificación.  
3. Comenta en el issue o escribe a **Davidson** (PO / DevOps / QA).

## Enlaces

- Repo: https://github.com/Dacadev97/iu-digital-radio  
- Project: https://github.com/users/Dacadev97/projects/4  
- Actions: https://github.com/Dacadev97/iu-digital-radio/actions  
- [TDD.md](TDD.md) · [BACKLOG.md](BACKLOG.md) · [QA_CHECKLIST.md](QA_CHECKLIST.md)
