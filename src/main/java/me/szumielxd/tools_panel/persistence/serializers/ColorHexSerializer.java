package me.szumielxd.tools_panel.persistence.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.awt.*;
import java.io.IOException;

public class ColorHexSerializer extends JsonSerializer<Color> {

	@Override
	public void serialize(Color color, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		gen.writeString("#%06X".formatted(color.getRGB() & 0xFFFFFF));
	}

}
