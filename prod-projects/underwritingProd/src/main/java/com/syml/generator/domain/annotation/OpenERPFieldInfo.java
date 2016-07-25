package com.syml.generator.domain.annotation;

import java.util.ArrayList;
import java.util.List;

import com.debortoliwines.openerp.api.SelectionOption;

public class OpenERPFieldInfo {
	public String name;
	public String type;
	public List<SelectionOption> typeValues; //selection
	//used many2one, one2many, many2many
	public List relationInfo= new ArrayList();
	@Override
	public String toString() {
		return "OpenERPFieldInfo [name=" + name + ", type=" + type + "]";
	}
}
