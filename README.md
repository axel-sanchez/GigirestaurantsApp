# GigirestaurantsApp
Este es el proyecto prueba de Mercado Libre, con esta app se puede buscar productos, acceder para ver sus detalles y abrir este mismo producto en la app propia de Mercado Libre.

# Experiencia de usuario
Este proyecto contiene las siguientes características:

* La pantalla principal donde hay un campo para ingresar lo que queremos buscar.
* La segunda pantalla donde se ve un listado de productos.
* Una vista con un producto específico con más información del mismo (se accede seleccionado un producto del listado de la segunda pantalla).
* La vista de este mismo producto abierta en la app oficial de Mercado Libre.
# Capturas de pantalla

<p align="center">
  <img width="270" height="555" src="https://github.com/axel-sanchez/GigirestaurantsApp/assets/51034538/6f637bc9-95ac-468f-8e58-fd34f30b4afd">
  <img width="270" height="555" src="https://github.com/axel-sanchez/GigirestaurantsApp/assets/51034538/273b40c5-13ab-4a12-98eb-1f100cfd220f">
  <img width="270" height="555" src="https://github.com/axel-sanchez/GigirestaurantsApp/assets/51034538/fc256a11-6520-436f-802c-58d0a987b52d">
</p>

## Guía de implementación
Traigo la información desde los servicios
- https://api.content.tripadvisor.com/api/v1/location/nearby_search
- https://api.content.tripadvisor.com/api/v1/location/{locationId}/details
- https://api.content.tripadvisor.com/api/v1/location/search

### Arquitectura
Este proyecto implementa el patrón de arquitectura MVVM y sigue buenas prácticas de Clean Architecture para hacer un código más independiente, mantenible y sencillo.

#### Capas
* Presentation: Fragments, Activities, Viewmodels.
* Data: contiene la implementación del repositorio y los sources donde se conecta con la api y con la base de datos.
* Domain: contiene los casos de uso y la definición del repositorio.
Este proyecto usa ViewModel para almacenar y manejar datos, así como comunicar cambios hacia la vista.

### Administrador de solicitudes: Retrofit

Este proyecto utiliza Retrofit para mostrar los productos desde una API.

### Inyección de dependencia - Dagger

Este proyecto utiliza Dagger para gestionar la inyección de dependencia.

### Persistencia de datos - Room

Este proyecto utiliza la base de datos de Room para almacenar los productos.

### Testing

La app posee tests hechos con JUnit5 y Espresso

### Patrones de diseño

Utilizo algunos patrones de diseño como Observer, Singleton, Builder

# Guía de instalación
En caso de no tener instalado Android Studio, descargue la última versión estable. Una vez que tenemos el programa instalado vamos a Get from Version Control y vamos a pegar https://github.com/axel-sanchez/GigirestaurantsApp.git Una vez hecho eso se va a clonar el proyecto, lo que resta sería conectar un celular y darle al botón verde de Run 'app'
