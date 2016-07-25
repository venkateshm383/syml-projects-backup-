/**
 * 
 */
package com.syml.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@MappedSuperclass
public class BaseBean implements java.io.Serializable {
	
	private static final long serialVersionUID = -2357506968457033221L;
	@Id
//	@GeneratedValue(strategy = IDENTITY)
//	@GeneratedValue(generator = "idGenerator")   
//	@GenericGenerator(name = "paymentableGenerator", strategy = "sequence",   
//	          parameters = { @Parameter(name = "sequence", value = "seq_id") }) 
	@GenericGenerator(name = "idGenerator", strategy = "assigned") 
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	
	public BaseBean(){
		
	}
	
	public BaseBean(Integer id){
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	
}
