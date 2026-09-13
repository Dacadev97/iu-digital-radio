# Guía de contribución — célula IU Digital Radio

Para compañeros de la **célula** F30 (equipo pequeño 4–6). También si es tu primer repo Android en equipo.

## Modalidad

Trabajamos en **célula** (Opción A1 · Software House en célula): cada persona toma **su issue** según el rol que le tocó. Un solo repo, colaboración cercana, PRs a `develop`.

**QA & DevOps / Release (+ board/CI):** Davidson Cadavid.

## 1. Requisitos

- **JDK 17** (Temurin / Oracle / el de Android Studio)
- **Android Studio** reciente
- Git + cuenta GitHub
- Android SDK 34 (Studio lo instala al abrir el proyecto)

```bash
java -version   # debe ser 17.x
```

## 2. Clonar y abrir (casa de la célula)

```bash
git clone https://github.com/Dacadev97/iu-digital-radio.git
cd iu-digital-radio
git checkout develop
git pull origin develop
```

Abre la carpeta en Android Studio → *Trust Project* → espera el sync de Gradle.

`local.properties` lo genera Studio (no lo subas; ya está en `.gitignore`).

## 3. Elige tu issue y crea rama

1. Board: https://github.com/users/Dacadev97/projects/4  
2. Filtra por tu label (`role-ui`, `role-hardware`, …).  
3. Abre **tu** issue, asígnatelo.  
4. Mueve la tarjeta: **Todo → In Progress**.

```bash
git checkout develop
git pull
git checkout -b feature/<rol>-<issue>
# ejemplos:
#   feature/ui-rf01
#   feature/hardware-rf03
#   feature/state-rf04
#   feature/audio-rf07
```

## 4. Implementar (TDD)

1. Corre los tests (deben fallar al inicio — es normal):

```bash
./gradlew testDebugUnitTest
```

2. Lee tu test `Rf0X…` y el contrato en `api/ProductContracts.kt`.
3. Implementa **solo** lo de tu issue; si dependes de un compañero, comenta en el issue.
4. Añade dependencias de producto **en tu PR** si las necesitas (Compose, Media3, etc.).
5. Verifica:

```bash
./gradlew testDebugUnitTest assembleDebug lintDebug
```

## 5. Columnas del Project (célula)

| Columna | Cuándo |
|--------|--------|
| **Todo** | Issue sin empezar |
| **In Progress** | Ya tienes rama y estás codeando |
| **Testing** | PR abierto / esperando CI o review |
| **Done** | Mergeado a `develop` y AC cumplidos |

## 6. Pull Request — checklist

- [ ] Base: **`develop`**
- [ ] Título: `[RF-0X] …` o `[PLAT-0X] …`
- [ ] Body: qué hiciste + cómo probar + `Closes #N`
- [ ] CI Actions OK en tu alcance (o nota si falta integración de otro rol de la célula)
- [ ] No subiste secretos / `local.properties`
- [ ] Tarjeta en **Testing** al abrir el PR; **Done** tras merge

Plantilla: `.github/pull_request_template.md`.

## 7. Después del merge

```bash
git checkout develop
git pull origin develop
```

## Dudas

- Repo / CI / board → **Davidson** (QA & DevOps / Release)
- Alcance de un RF → comenta en el issue (somos pocos: responde rápido)
- Contrato de test mal planteado → avisa a QA/PO (no bajes la barra del assert)

Más: [docs/ONBOARDING.md](docs/ONBOARDING.md) · [docs/ROLES.md](docs/ROLES.md) · [docs/TDD.md](docs/TDD.md)
