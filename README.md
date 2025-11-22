# Producto Service

Microservicio encargado de la gestión de productos y categorías dentro de la arquitectura.

---

## Paso 1: Activación de Actuator + Micrometer

### 📦 Dependencias necesarias
En el `pom.xml` añade:

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>

<dependency>
  <groupId>io.micrometer</groupId>
  <artifactId>micrometer-registry-prometheus</artifactId>
</dependency>

⚙️ Configuración en `application.yml`

spring:
  application:
    name: producto-service

management:
  server:
    base-path: /actuator
  endpoints:
    web:
      exposure:
        include: health,info,prometheus
  metrics:
    export:
      prometheus:
        enabled: true

🔎 Endpoints disponibles

•  GET /actuator/health → Estado del servicio (UP/DOWN).
•  GET /actuator/info → Información de la aplicación.
•  GET /actuator/prometheus → Métricas en formato Prometheus.

✅ Seguridad recomendada

Permitir acceso libre a observabilidad y proteger negocio con JWT:

http.csrf(AbstractHttpConfigurer::disable)
    .authorizeHttpRequests(auth -> auth
        .requestMatchers("/actuator/**").permitAll()
        .requestMatchers("/api/productos/**", "/api/categorias/**").authenticated()
    )
    .addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

📊 Qué métricas expone Micrometer

•  JVM: memoria, GC, hilos.
•  Sistema: CPU, uptime.
•  HTTP: latencia, throughput, errores por endpoint.
•  Base de datos: conexiones activas, tiempo de queries.
•  Custom metrics: puedes añadir contadores, gauges y timers propios.

Ejemplo de métrica personalizada:

Counter pedidosCounter = Counter.builder("productos_creados_total")
    .description("Número total de productos creados")
    .register(registry);

pedidosCounter.increment();

Con esto, el producto-service queda listo para ser scrapeado por Prometheus en el siguiente paso.

## 📊 Observabilidad con Prometheus

Este proyecto utiliza **Prometheus** para recolectar métricas de los microservicios.  
Cada servicio expone métricas a través del endpoint `/actuator/prometheus` de Spring Boot Actuator.

### Configuración de Prometheus solo se configura en prometheus-server

El archivo principal de configuración es `prometheus.yml`.  
Contiene las siguientes secciones clave:

- **global**: define intervalos de scrape y evaluación por defecto.
- **scrape_configs**: lista de jobs que indican qué servicios monitorear.
- **alerting** y **rule_files**: opcionales, para integrar con Alertmanager y reglas de alertas.

Ejemplo de configuración mínima adaptada a nuestra arquitectura:

```yaml
global:
  scrape_interval: 15s
  evaluation_interval: 15s

scrape_configs:
  - job_name: 'prometheus'
    static_configs:
      - targets: ['localhost:9090']

  - job_name: 'producto-service'
    metrics_path: '/actuator/prometheus'
    static_configs:
      - targets: ['localhost:8080']

  - job_name: 'pedido-service'
    metrics_path: '/actuator/prometheus'
    static_configs:
      - targets: ['localhost:8081']

  - job_name: 'cliente-service'
    metrics_path: '/actuator/prometheus'
    static_configs:
      - targets: ['localhost:8082']

  - job_name: 'auth-service'
    metrics_path: '/actuator/prometheus'
    static_configs:
      - targets: ['localhost:8083']