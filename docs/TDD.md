# TDD — contrato del MVP

Los tests unitarios son la **especificación ejecutable**. El stub de producto es deliberadamente incompleto.

## Qué debe pasar (Green)

| Test class | Condición Green |
|------------|-----------------|
| Rf01 | `ComposeUiContract.homeScreenImplemented = true` + sin `res/layout` |
| Rf02 | `ProfileCamera.onPreviewCaptured()` → `hasProfilePhoto == true` |
| Rf03 | Manifest declara CAMERA, VIBRATE, INTERNET |
| Rf04 | `RadioSession` muta play/pause/mute/estación |
| Rf05 | Play+Pause+Mute llaman `HapticPort.tick()` ≥ 3 veces |
| Rf06 | `StationCatalog.defaults` ≥ 5, ids únicos, URLs |
| Rf07 | Sesión reproduce/mutea; Gradle incluye `media3-exoplayer` |

## Comando

```bash
./gradlew testDebugUnitTest
```

Salida esperada al inicio del sprint: **BUILD FAILED** (Red).  
Al cerrar MVP: **BUILD SUCCESSFUL**.
