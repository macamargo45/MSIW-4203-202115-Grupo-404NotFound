# Vinilos App

## Instalación del APK
- Todas las versiones de los APKs para instalación se encuentran el el folder / APKs.
- Descargue la versión 3.0.0 que es la última desde su teléfono o use [este link](https://github.com/macamargo45/MSIW-4203-202115-Grupo-404NotFound/blob/main/APKs/vinilos%203.0.0.apk).
- Asegurese de autorizar que se instale la aplicación desde un apk si su telefono le pide esas confirmaciones.

## Correr el código localmente
- Primero asegurese de tener Android studio instalado. Si no sabe como siga las instrucciones en [este tutorial](https://misovirtual.virtual.uniandes.edu.co/codelabs/android-setup-tutorial/index.html#0)
- Descargue el código de la rama [release/3.0.0](https://github.com/macamargo45/MSIW-4203-202115-Grupo-404NotFound/tree/release/3.0.0) que corresponde a la entrega 3 en su máquina local.
- Para abrir y correr el proyecto con Android studio siga [este tutorial](http://androidcodelabs.com/BlogDetails.aspx?BlogId=8&name=How-To-Open-Project-in-Android-Studio). Asegurese de tener configurado al menos uno de los emuladores descritos en los anexos de [la estrategia de pruebas](https://uniandes-my.sharepoint.com/:w:/g/personal/j_monterov_uniandes_edu_co/EXM_dCCmPmtJvjlnZ8Q5qQkBW5o6NKCHZkepwi1gSEkD0Q?e=qb3bsb)

## Historias incluidas en esta versión
Las historias incluidas en esta versión son las siguientes:
* HU001 - Listado de albumes
* HU002 - Ver el detalle de un album (esto no incluye visualizar la lista de canciones del album)
* HU003 - Consultar el listado de artistas
* HU004 - Consultar la información detallada de un artista
* HU005 - Consultar listado de coleccionistas
* HU006 - Consultar la información detallada de coleccionista
* HU007 - Crear un album
* HU015 - Asociar album con un artista

Además se incluyenron optimizaciones para el uso de recursos de red, memoria y CPU.

En [este wiki](https://github.com/macamargo45/MSIW-4203-202115-Grupo-404NotFound/wiki/Backlog-del-Producto) puede ver el detalle de las historias.

## Pruebas
Las pruebas de la aplicación se encuentran en el folder que se muestra en la imagen:
![image](https://user-images.githubusercontent.com/78028512/140682181-09c96cb7-607b-4b81-8b9d-20c3b4412e9a.png)

El detalle de los dispositivos en los que se probó está en [la estrategia de pruebas](https://uniandes-my.sharepoint.com/:w:/g/personal/j_monterov_uniandes_edu_co/EXM_dCCmPmtJvjlnZ8Q5qQkBW5o6NKCHZkepwi1gSEkD0Q?e=qb3bsb).
La historia HU001 se cubre con las pruebas AlbumListTest. Lo que se cubre es:
* Verificar que la lista se despliegue
* Verificar que todos los albumes esperados se desplieguen
* Verificar que los datos de los albumes desplegados son los correctos

La historia HU002 se cubre con las pruebas: 
* Nevagar a un album desde la lista de albumes
* Verificar que los datos del album seleccionado se despliega correctamente
* Devolverse a la lista de albumes

La historia HU003 se cubre con las pruebas: 
* Verificar que la lista se despliegue
* Verificar que todos los artistas esperados se desplieguen
* Verificar que los datos de los artistas desplegados son los correctos

La historia HU004 se cubre con las pruebas: 
* Nevagar a un artista desde la lista de artistas
* * Verificar que los datos del artista seleccionado se despliega correctamente

La historia HU005 se cubre con las pruebas: 
* Verificar que la lista se despliegue
* Verificar que todos los coleccionistas esperados se desplieguen
* Verificar que los datos de los coleccionistas desplegados son los correctos

La historia HU006 se cubre con las pruebas: 
* Navagar a un coleccionista desde la lista de coleccionistas
* Verificar que los datos del coleccionista seleccionado se despliega correctamente

La historia HU007 se cubre con las pruebas: 
* Crear un nuevo album
* Verificar que el nuevo album se despliega en la lista de álbumes

La historia HU015 se cubre con las pruebas: 
* Asignar un album a un músico
* Verificar que el album ligado se despliega en la lista de albumes del músico

Addicionalmente se tiene un test de navegacion (NevigationTest) que verificar:
* Que la primer ventana que se ve sea la de la lista de albumes
* Que los cuatro botones de la barra de navegación inferior esten desplegados
