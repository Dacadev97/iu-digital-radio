#!/usr/bin/env bash
# Crear Project F30 y enganchar issues (requiere: gh auth refresh -s project,read:project)
set -euo pipefail
OWNER=Dacadev97
REPO=iu-digital-radio
TITLE="IU Digital Radio F30"

echo "Creando Project…"
PROJECT_JSON=$(gh project create --owner "$OWNER" --title "$TITLE" --format json)
PROJECT_NUMBER=$(echo "$PROJECT_JSON" | jq -r '.number')
PROJECT_URL=$(echo "$PROJECT_JSON" | jq -r '.url')
echo "Project #$PROJECT_NUMBER → $PROJECT_URL"

gh project link "$PROJECT_NUMBER" --owner "$OWNER" --repo "$OWNER/$REPO" || true

for n in $(seq 1 12); do
  echo "Añadiendo issue #$n…"
  gh project item-add "$PROJECT_NUMBER" --owner "$OWNER" --url "https://github.com/$OWNER/$REPO/issues/$n"
done

echo "Listo: $PROJECT_URL"
