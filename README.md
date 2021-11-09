# Vinilos App

## Instalación del APK
- Todas las versiones de los APKs para instalación se encuentran el el folder / APKs.
- Descargue la versión 1.0.0 que es la última desde su teléfono o use [este link](https://github.com/macamargo45/MSIW-4203-202115-Grupo-404NotFound/blob/main/APKs/vinilos%201.0.0.apk).
- Asegurese de autorizar que se instale la aplicación desde un apk si su telefono le pide esas confirmaciones.

## Correr el código localmente
- Primero asegurese de tener Android studio instalado. Si no sabe como siga las instrucciones en [este tutorial](https://misovirtual.virtual.uniandes.edu.co/codelabs/android-setup-tutorial/index.html#0)
- Descargue el código de la rama [release/1.0.0](https://github.com/macamargo45/MSIW-4203-202115-Grupo-404NotFound/tree/release/1.0.0) que corresponde a la entrega 1 en su máquina local.
- Para abrir y correr el proyecto con Android studio siga [este tutorial](http://androidcodelabs.com/BlogDetails.aspx?BlogId=8&name=How-To-Open-Project-in-Android-Studio). Asegurese de tener configurado al menos uno de los emuladores descritos en los anexos de [la estrategia de pruebas](https://uniandes-my.sharepoint.com/:w:/g/personal/j_monterov_uniandes_edu_co/ETwsSjrjt3BMhb1xd7t2UQEBWcIWGVI3q6ygDyCzSrwHmA?e=dt1FPm)

## Historias incluidas en esta versión
Las historias incluidas en esta versión son las siguientes:
* HU001 - Listado de albumes
* HU002 - Ver el detalle de un album (esto no incluye visualizar la lista de canciones del album)

En [este wiki](https://github.com/macamargo45/MSIW-4203-202115-Grupo-404NotFound/wiki/Backlog-del-Producto) puede ver el detalle de las historias.

## Pruebas
Las pruebas de la aplicación se encuentran en el folder que se muestra en la imagen:
![image](https://user-images.githubusercontent.com/78028512/140682181-09c96cb7-607b-4b81-8b9d-20c3b4412e9a.png)

El detalle de los dispositivos en los que se probó está en [la estrategia de pruebas](https://uniandes-my.sharepoint.com/:w:/g/personal/j_monterov_uniandes_edu_co/ETwsSjrjt3BMhb1xd7t2UQEBWcIWGVI3q6ygDyCzSrwHmA?e=dt1FPm).
La historia HU001 se cubre con las pruebas AlbumListTest. Lo que se cubre es:
* Verificar que la lista se despliegue
* Verificar que todos los albumes esperados se desplieguen
* Verificar que los datos de los albumes desplegados con los correctos

La historia HU002 se cubre con las pruebas: 
* Nevagar a un album desde la lista de albumes
* Verificar que los datos del album seleccionado se despliega correctamente
* Devolverse a la lista de albumes

Addicionalmente se tiene un test de navegacion (NevigationTest) que verificar:
* Que la primer ventana que se ve sea la de la lista de albumes
* Que los cuatro botones de la barra de navegación inferior esten desplegados
