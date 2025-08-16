# Usa una imagen base con Tomcat 10 y un JDK de OpenJDK 17.
FROM tomcat:10-jdk17-openjdk

# Copia tu archivo .war a la carpeta de despliegue de Tomcat.
# Asegúrate de que "nombre_de_tu_proyecto.war" coincida con el nombre de tu archivo.
COPY nombre_de_tu_proyecto.war /usr/local/tomcat/webapps/ROOT.war

# Expone el puerto por defecto de Tomcat.
EXPOSE 8080

# Comando para iniciar el servidor Tomcat.
CMD ["catalina.sh", "run"]