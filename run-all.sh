#!/usr/bin/env bash
# Compiles and runs the demo in every topic folder.
set -e
root="$(cd "$(dirname "$0")" && pwd)"

for dir in "$root"/[0-9][0-9]-*/; do
  demo=$(ls "$dir"*Demo.java 2>/dev/null | head -n 1)
  [ -z "$demo" ] && continue
  name=$(basename "$demo" .java)
  echo "===== $(basename "$dir") ====="
  javac -d "$dir/out" "$dir"*.java
  java -cp "$dir/out" "$name"
  echo
done
