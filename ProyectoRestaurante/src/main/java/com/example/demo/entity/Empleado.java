package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "TB_EMPLEADO")
public class Empleado {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id_Empleado")
	private Integer idEmpleado;
	
	@Column(name = "Nombre", nullable = false, length = 20)
	private String nombre;
	
	@Column(name = "Apellido", nullable = false, length = 20)
	private String apellido;
	
	@Column(name = "Dirección", nullable = false, length = 50)
	private String direccion;
	
	@Column(name = "Telefono", nullable = false, length = 9, columnDefinition = "CHAR(9)")
	private String telefono;
	
	@ManyToOne
	@JoinColumn(name = "Id_TipoEmpleado" , nullable = false)
	private TipoEmpleado Id_TipoEmpleado;
}
