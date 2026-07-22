package com.simple_shop.item_service.item.dto.formatter;

import java.math.BigDecimal;

import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;

public class PlainBigDecimalSerializer extends ValueSerializer<BigDecimal> {
	public PlainBigDecimalSerializer() {
		super();
	}

	@Override
	public void serialize(final BigDecimal value, final JsonGenerator gen, final SerializationContext context) {
		if (value != null) {
			gen.writeString(value.toPlainString());
		} else {
			gen.writeNull();
		}
	}
}
