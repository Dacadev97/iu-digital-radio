# TDD — contrato del MVP (célula)

Los tests unitarios son la **especificación ejecutable**.  
El repo es un **shell DevOps** (casa de la célula): no hay implementación de producto; cada compañero la aporta por PR según su rol.

## Estado esperado al clonar

| Tarea | Resultado |
|-------|-----------|
| `./gradlew testDebugUnitTest` | **BUILD FAILED** (Red) |
| `./gradlew assembleDebug` | **SUCCESS** (shell vacío) |
| `./gradlew lintDebug` | **SUCCESS** |

## Qué debe pasar (Green)

| Test | Condición Green | Rol en la célula |
|------|-----------------|------------------|
| Rf01 | `ComposeUiContract.homeScreenImplemented = true` + sin `res/layout` | UI |
| Rf02 | `Product.profileCamera()` → captura marca `hasProfilePhoto` | Hardware |
| Rf03 | Manifest declara CAMERA, VIBRATE, INTERNET | Hardware |
| Rf04 | `RadioSession` muta play/pause/mute/estación | State |
| Rf05 | Play+Pause+Mute llaman `HapticPort.tick()` ≥ 3 | Hardware (+ State) |
| Rf06 | `StationCatalog.defaults` ≥ 5, ids únicos, URLs | UI (+ Audio) |
| Rf07 | Sesión reproduce/mutea; Gradle incluye `media3-exoplayer` | Audio |

## Dónde está el contrato

- Tests: `app/src/test/java/edu/iudigital/radio/Rf0*.kt`
- Interfaces / entry points: `app/src/main/java/edu/iudigital/radio/api/ProductContracts.kt`
- Roles: [ROLES.md](ROLES.md) · Onboarding: [ONBOARDING.md](ONBOARDING.md)

## Regla

No rebajar asserts para “hacer pasar” CI. Si el contrato está mal, avisa a QA/PO (Davidson).
