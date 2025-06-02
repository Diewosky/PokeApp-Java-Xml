# Aplicación Android PokeAPI (Java + XML)

Una aplicación Android moderna que consume la [PokeAPI](https://pokeapi.co/) para mostrar información de Pokemon. Construida con Java y XML siguiendo principios de arquitectura limpia y mejores prácticas de Android.

## Características

- **Lista de Pokemon**: Navega a través de una lista paginada de Pokemon
- **Detalles de Pokemon**: Ve información detallada incluyendo estadísticas, habilidades, tipos, altura, peso y experiencia base
- **Carga de Imágenes**: Imágenes de Pokemon de alta calidad con transiciones suaves
- **Deslizar para Actualizar**: Desliza hacia abajo para refrescar la lista de Pokemon
- **Scroll Infinito**: Paginación automática al hacer scroll hasta abajo
- **Manejo de Errores**: Manejo elegante de errores con funcionalidad de reintentar
- **Colores de Tipos**: Tipos de Pokemon mostrados con colores auténticos
- **UI Responsiva**: Interfaz limpia y moderna con Material Design

## Arquitectura

La aplicación sigue principios de arquitectura limpia con clara separación de responsabilidades:

```
app/
├── src/main/java/com/example/pokeapixmljava/
│   ├── model/              # Modelos de datos (Pokemon, Sprites, Types, etc.)
│   ├── network/            # Servicio API y configuración del cliente
│   ├── repository/         # Capa de repositorio de datos
│   ├── adapter/            # Adaptador de RecyclerView
│   ├── util/               # Clases utilitarias
│   ├── MainActivity.java   # Actividad principal con lista de Pokemon
│   └── PokemonDetailActivity.java  # Vista de detalles de Pokemon
└── src/main/res/
    ├── layout/             # Layouts XML
    ├── drawable/           # Recursos drawable
    └── values/             # Recursos de strings y temas
```

## Tecnologías Utilizadas

- **Lenguaje**: Java
- **UI**: Layouts XML con componentes de Material Design
- **Networking**: Retrofit 2 + OkHttp + Gson
- **Carga de Imágenes**: Glide
- **Arquitectura**: Patrón Repository con interfaces de callback
- **API**: [PokeAPI v2](https://pokeapi.co/api/v2/)

## Dependencias

```gradle
// Networking
implementation 'com.squareup.retrofit2:retrofit:2.9.0'
implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
implementation 'com.squareup.okhttp3:logging-interceptor:4.12.0'

// Carga de imágenes
implementation 'com.github.bumptech.glide:glide:4.16.0'

// Componentes UI
implementation 'androidx.recyclerview:recyclerview:1.3.2'
implementation 'androidx.cardview:cardview:1.0.0'
implementation 'androidx.swiperefreshlayout:swiperefreshlayout:1.1.0'
```

## Componentes Clave

### 1. Modelos de Datos
- **Pokemon**: Modelo principal de Pokemon con todos los atributos
- **Sprites**: URLs de imágenes de Pokemon
- **TypeSlot**: Tipos de Pokemon con colores
- **StatSlot**: Estadísticas de Pokemon (HP, Ataque, Defensa, etc.)
- **AbilitySlot**: Habilidades de Pokemon
- **PokemonListResponse**: Wrapper de respuesta de API

### 2. Capa de Red
- **PokeApiService**: Interfaz Retrofit definiendo endpoints de API
- **ApiClient**: Singleton proporcionando instancia configurada de Retrofit
- **PokemonRepository**: Repositorio manejando llamadas API con callbacks

### 3. Componentes UI
- **MainActivity**: Muestra lista de Pokemon con paginación y refresh
- **PokemonDetailActivity**: Muestra información detallada de Pokemon
- **PokemonAdapter**: Adaptador de RecyclerView con carga eficiente de imágenes

## Endpoints de API Utilizados

- `GET /pokemon?limit={limit}&offset={offset}` - Obtener lista paginada de Pokemon
- `GET /pokemon/{id}` - Obtener información detallada de Pokemon por ID
- `GET /pokemon/{name}` - Obtener información detallada de Pokemon por nombre

## Mejores Prácticas Implementadas

1. **Código Limpio**: Código bien estructurado y legible con convenciones de nomenclatura apropiadas
2. **Manejo de Errores**: Manejo comprensivo de errores con mensajes amigables al usuario
3. **Gestión de Memoria**: Carga y cache eficiente de imágenes con Glide
4. **Optimización de Red**: Timeouts de request y logging para debugging
5. **UI/UX**: Animaciones suaves, estados de carga y diseño responsivo
6. **Separación de Responsabilidades**: Clara separación entre datos, lógica de negocio y UI
7. **Gestión de Recursos**: Uso apropiado de recursos de strings y assets drawable

## Instrucciones de Configuración

1. Clona el repositorio
2. Abre el proyecto en Android Studio
3. Sincroniza el proyecto con archivos Gradle
4. Ejecuta la aplicación en un emulador o dispositivo físico

## Requisitos

- Android SDK 24 (Android 7.0) o superior
- Java 11
- Conexión a internet para llamadas API

## Mejoras Futuras

- Agregar funcionalidad de búsqueda
- Implementar cache local con base de datos Room
- Agregar función de Pokemon favoritos
- Incluir cadenas de evolución de Pokemon
- Agregar más información detallada de Pokemon (movimientos, ubicaciones, etc.)
- Implementar soporte para tema oscuro

## Tecnologías Confirmadas

### ✅ **Utilizadas:**
- **Java** - Lenguaje de programación principal
- **XML** - Layouts de interfaz de usuario
- **findViewById()** - Método tradicional para referenciar vistas
- **Callbacks** - Patrón tradicional para manejo asíncrono
- **RecyclerView** - Para listas eficientes
- **Material Design** - Componentes de diseño

### ❌ **NO Utilizadas:**
- Kotlin
- Jetpack Compose
- Data Binding
- View Binding
- Corrutinas
- LiveData
- ViewModel 
