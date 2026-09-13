# QA Checklist — IU Digital Radio

## Smoke (cada PR que toque app)

- [ ] Unit tests verdes (`testDebugUnitTest`)
- [ ] `assembleDebug` genera APK
- [ ] Lint sin errores bloqueantes (warnings documentados)

## Funcional MVP (dispositivo / emulador)

| Caso | Pasos | Esperado |
|------|-------|----------|
| Perfil cámara | Abrir app → Cámara → capturar | Avatar se actualiza |
| Play / Pause | Pulsar Play luego Pause | Estado UI cambia; vibración |
| Mute | Pulsar mute | Icono mute; vibración |
| Lista | Scroll LazyColumn → seleccionar otra emisora | Player muestra nueva estación; play se reinicia a pausa |
| Audio (RF-07) | Play con stream | Audio audible; mute silencia |
| Permisos denegados | Negar CAMERA | App no crashea; mensaje o UI degradada |

## Regresión release

- [ ] APK instalable en API 26+
- [ ] Artifact subido en Actions o Drive del grupo
- [ ] SHA / versión anotada en issue de release

## Evidencia para Canvas

- [ ] Capturas de CI verde
- [ ] Link APK / artifact
- [ ] Video demo 2–4 min (grupo)
