package com.fabriciospringcloud.microservicios.app.commons.usuarios.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
// @Table permite modificar el nombre de la tabla
@Table(name = "usuarios")
public class Usuario implements Serializable {
 
	// @Id es el identificador en la tabla. Tiene por defecto el mismo nombre el campo en la tabla igual que el atributo.
	// Si se llama distinto usamos Column.
	// @GeneratedValue define la estrategia de generacion para el id, para db sql, h2, sql server.
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	//@Column define como unico en la tabla y un largo de 20 caracteres.
	@Column(unique = true, length = 20)
	@NotEmpty
	@Size(min=4, max=20)
	private String username;

	//@Column define un largo de 60 caracteres para el password porque se va a encirptar con BCript.
	@Column(length = 60)
	@NotEmpty
	@Size(min=8, max=60)
	private String password;
	
	private Boolean enabled;
	
	@NotEmpty
	@Size(min=2, max=60)
	private String nombre;
	
	@NotEmpty
	@Size(min=2, max=60)
	private String apellido;
	
	@NotEmpty
	@Size(min=2, max=255)
	private String dependencia;
		
	@NotEmpty
	@Email
	@Column(unique = true, length = 100)
	@Size(min=5, max=60)
	private String email;
	
	@Column(name = "create_at")
	private Date createAt;
	
	@Lob
	@JsonIgnore
	private byte[] foto;
	
	public Integer getFotoHashCode() {
		return (this.foto != null)? this.foto.hashCode(): null;
	}
	
	@PrePersist
	public void prePersist() {
		this.createAt = new Date();
		this.enabled = true;
	}
	
	private Integer intentos;

/*  Mapear y configurar la cardinalidad entre usuarios con roles. Muchos usuarios pueden tener muchos roles.
 *	una relacion muchos a muchos. Para este caso la relacion seria UNIDIRECCIONAL, es decir solamente en usuarios
 *	ya que no tiene sentido mostrar los usuarios de un rol especifico.
 *	
 *	Si quermos la relacion BIDIRECCIONAL debemos ir a la clase roles y:
 *	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles") y los geters and seters de usuarios.
 *	
 *	fetch = FetchType.LAZY configura como se obtienen los datos de la relacion. Por defecto es EAGER que trae todo en una sola consulta
 *	LAZY o carga peresosa solo trae al usuario y los roles se obtienen por el metodo getRoles().
 *	
 *	@JoinTable(name="usuarios_roles") define el nombre de la clase intermedia de la relacion.
 *	@JoinTable(joinColumns= @JoinColumn(name="usuario_id") indica la llave foranea de la clase principal usuario, dueña de la relacion.
 *	@JoinTable(inverseJoinColumns =  @JoinColumn(name="role_id") indicamos el nombre de la foren key para roles.
 *	@JoinTable(uniqueConstraints = @UniqueConstraint(columnNames = {"usuario_id", "role_id"})) crea una restrincion para que usuario_id y roles_id sean unicos.
*/
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="usuarios_roles", 
	           joinColumns= @JoinColumn(name="usuario_id"), 
	           inverseJoinColumns =  @JoinColumn(name="role_id"),
	           uniqueConstraints = @UniqueConstraint(columnNames = {"usuario_id", "role_id"}))
	private List<Role> roles;
	
	//Contructor para inicializar la lista de roles
	public Usuario() {
		this.roles = new ArrayList<>();
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

    /*Permite agregar roles a la lista (asignar un rol a usuario). Luego debemos tener unos metodo handler
     *en el controlador de Usuario que nos permita realizar estas tareas, para añadir y eliminar roles
     *del usuario, 
    */
	
	public void addRole(Role role) {
		this.roles.add(role);
	}
	
    /*Permite eliminar roles de la lista (eliminar un rol del usuario).
     *Para poder eliminar el remove en una lista  arrayList es necesario, va a buscar el role en la lista
     *y va a preguntar si es igual para eliminar, por lo tanto tenemos que implemenar y sobrescribir el
     *metodo equals en el models Role.
     */
	public void removeRole(Role role) {
		this.roles.remove(role);
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getDependencia() {
		return dependencia;
	}

	public void setDependencia(String dependencia) {
		this.dependencia = dependencia;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}
	public Integer getIntentos() {
		return intentos;
	}

	public void setIntentos(Integer intentos) {
		this.intentos = intentos;
	}

	
	private static final long serialVersionUID = 1L;

}
