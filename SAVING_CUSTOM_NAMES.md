# Guía: Cómo guardar nombres personalizados correctamente

## Resumen de cambios

Se han implementado mejoras en `TechniqueNameManager.java` para garantizar que los nombres personalizados se guarden de forma robusta y confiable.

## Características implementadas

### 1. **Flag de cambios "dirty"**
- `private static volatile boolean dirty = false;` - Rastrea si hay cambios sin guardar
- Todos los métodos de modificación (`setCustomName`, `clearCustomName`, `clearAllForPlayer`) establecen `dirty = true`
- El método `saveAll()` solo actúa si `dirty` es verdadero, evitando escrituras innecesarias

### 2. **Thread-safe saving**
- Usa `synchronized (SAVE_LOCK)` para evitar condiciones de carrera
- Implementa el patrón "double-check" para verificar `dirty` dentro del bloque sincronizado
- Múltiples hilos pueden marcar cambios sin bloquear, pero solo uno guardará a la vez

### 3. **Mejor manejo de errores**
- Valida parámetros en `setCustomName()`:
  - Evita `null` para player o techniqueId
  - Evita strings vacíos o en blanco
- Captura excepciones en `saveAll()` y `loadAll()` con logging
- Intenta recuperarse ante errores en lugar de fallar silenciosamente

### 4. **Guardado automático periódico**
- En `TechAPI.java` se agregó: `Bukkit.getScheduler().scheduleSyncRepeatingTask(this, TechniqueNameManager::saveAll, 6000L, 6000L);`
- Se ejecuta cada 5 minutos (6000 ticks)
- Previene pérdida de datos en caso de caídas inesperadas del servidor

### 5. **Logging mejorado**
- `saveAll()` informa cuántos jugadores tienen nombres personalizados guardados
- `loadAll()` informa cuántos jugadores tuvieron datos cargados
- Los errores se registran con nivel SEVERE y detalles completos

## Cómo usar correctamente

### Para establecer un nombre personalizado:
```java
TechniqueNameManager.setCustomName(player, "technique-id", "Mi nombre personalizado");
```
- Se establece automáticamente el flag dirty
- Se guardará automáticamente en los próximos 5 minutos o al apagar el servidor

### Para limpiar un nombre personalizado:
```java
TechniqueNameManager.clearCustomName(player, "technique-id");
```
- También establece el flag dirty

### Para limpiar todos los nombres de un jugador:
```java
TechniqueNameManager.clearAllForPlayer(player);
```

### Para obtener el nombre que debe mostrar un jugador:
```java
String displayName = TechniqueNameManager.getDisplayName(player, technique);
```
- Retorna el nombre personalizado si existe, sino el nombre por defecto

### Para verificar si existe un nombre personalizado:
```java
boolean hasCustom = TechniqueNameManager.hasCustomName(player, "technique-id");
```

## Ciclo de vida

1. **Inicio del servidor**: `loadAll()` se ejecuta al cargar el plugin
2. **Durante la sesión**: Los cambios se marcan como dirty inmediatamente
3. **Guardado automático**: Cada 5 minutos se guarda si hay cambios
4. **Cierre del servidor**: `saveAll()` se ejecuta al desactivar el plugin (garantía final)

## Datos almacenados

Los datos se guardan en el archivo `config.yml` bajo la clave `TechniqueNames`:

```yaml
TechniqueNames:
  "player-uuid-1":
    technique-id-1: "Nombre Personalizado 1"
    technique-id-2: "Nombre Personalizado 2"
  "player-uuid-2":
    technique-id-3: "Otro Nombre"
```

## Validaciones

✅ Parámetros nulos/vacíos se rechazan  
✅ UUIDs inválidos se registran y se omiten  
✅ Mapas vacíos no se guardan  
✅ Cambios se rastrea con eficiencia  
✅ Guardado es thread-safe  
✅ Errores se registran con contexto  

