package com.syml.hibernate.dao.factory;

import com.syml.hibernate.dao.IPostGresDaoService;
import com.syml.hibernate.dao.impl.PostgresDAO;

public class DAOFactory {

	public static IPostGresDaoService getInstance() {
		return new PostgresDAO();
	}
}
