package com.simple_shop.item_service.item.persistence;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.springframework.util.StringUtils;

public final class CamelToPascalNamingStrategy extends PhysicalNamingStrategyStandardImpl {
	private static final long serialVersionUID = -1461529994984597004L;

	public CamelToPascalNamingStrategy() {
		super();
	}

	@Override
	public Identifier toPhysicalColumnName(final Identifier name, final JdbcEnvironment context) {
		return Identifier.toIdentifier(StringUtils.capitalize(name.getText()));
	}
}
