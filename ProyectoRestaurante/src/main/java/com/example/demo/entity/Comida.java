package com.example.demo.entity;

import java.math.BigDecimal;

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
@Table(name = "TB_COMIDA")
public class Comida {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id_Comida")
	private Integer idComida;
	
	@Column(name = "Nombre_Comida", nullable= false, length = 20)
	private String nomComida; 
	
	@Column(name = "Precio", nullable =  false, scale = 2, columnDefinition = "DECIMAL(10,2)")
	private BigDecimal precio;
	
	@ManyToOne
	@JoinColumn(name = "Id_Categoria" , nullable = false)
	private Categoria idCategoria;
	
	@Column(name = "Url_Imagen" , nullable = false)
	private String urlImagen;
}
