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
@Table(name = "TB_PEDIDO")
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id_Pedido")
	private Integer idPedido;
	
	@Column(name = "Cliente", nullable = false)
	private Integer cliente;
	
	@ManyToOne
	@JoinColumn(name = "Id_Comida" , nullable = false)
	private Comida idComida;
	
	@ManyToOne
	@JoinColumn(name = "Id_Bebida" , nullable = false)
	private Bebida idBebida;
	
	@ManyToOne
	@JoinColumn(name = "Id_Empleado" , nullable = false)
	private Empleado idEmpleado;
	
	@ManyToOne
	@JoinColumn(name = "Id_Mesa" , nullable = false)
	private Mesa idMesa;
}
