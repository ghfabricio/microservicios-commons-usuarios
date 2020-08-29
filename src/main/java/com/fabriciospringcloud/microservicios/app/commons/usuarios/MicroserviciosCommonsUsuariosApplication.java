package com.fabriciospringcloud.microservicios.app.commons.usuarios;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/*Este proyecto commons lo creamos para poder guardar la clase usuario y role porque se van a reutilizar en
 * otros microservicios, para no duplicar el codigo y las clases en los microservicios usuarios y oauth.
 * Solo utiliza la dependencia Spring Data JPA porque los entitys tienen anotaciones JPA.
 * Como es un proyecto commons tenemos que quitar del pom.xml el plugun de Maven.
 * Tambien tenemos que quitar el metodo main de la clase principal.
 * Excluimos la auto-configuracion de Spring Data JPA que por defecto pide una conexion a la db de esta manera:
 * @EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class}).
 * Luego creamos un package entity donde colocamos las clases entity de usuario y role.
 * Creamos el .JAR para este proyecto de libreria para que despues lo podamos utilizar como dependencia en los
 * demas proyectos. Para essto dede la terminal sobre el directorio ejecutamos el comando:
 * mvnw.cmd install
 * El JAR se guarda en el directorio target con la version.
 * 
 * 
 * 
 */
@SpringBootApplication
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class MicroserviciosCommonsUsuariosApplication {


}
