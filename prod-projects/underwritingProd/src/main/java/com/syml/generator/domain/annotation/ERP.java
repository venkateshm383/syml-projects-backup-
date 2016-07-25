package com.syml.generator.domain.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ERP {
	public enum OpenERPType {
		many2one,one2one,many2many,one2many,selection,bytes,Null, ignore
	}
	
	public OpenERPType type() default OpenERPType.Null;
	public String name() default "";
}
