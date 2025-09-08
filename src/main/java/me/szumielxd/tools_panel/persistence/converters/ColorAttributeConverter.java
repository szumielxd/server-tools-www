package me.szumielxd.tools_panel.persistence.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.lang.Nullable;

import java.awt.*;

@Converter(autoApply = true)
public class ColorAttributeConverter implements AttributeConverter<Color, Integer> {


	@Override
	public @Nullable Integer convertToDatabaseColumn(@Nullable Color color) {
		return color != null ? color.getRGB() & 0xFFFFFF : null;
	}

	@Override
	public @Nullable Color convertToEntityAttribute(@Nullable Integer dbData) {
		return dbData != null ? new Color(dbData) : null;
	}
}
