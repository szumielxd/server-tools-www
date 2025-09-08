package me.szumielxd.tools_panel.utils;

import lombok.experimental.UtilityClass;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@UtilityClass
public class CollectionUtils {

	public <K, V> Map<K, V> insertOrderedMap(K k1, V v1) {
		return insertOrderedMapOfEntries(Map.entry(k1, v1));
	}

	public <K, V> Map<K, V> insertOrderedMap(K k1, V v1, K k2, V v2) {
		return insertOrderedMapOfEntries(
				Map.entry(k1, v1),
				Map.entry(k2, v2));
	}

	public <K, V> Map<K, V> insertOrderedMap(K k1, V v1, K k2, V v2, K k3, V v3) {
		return insertOrderedMapOfEntries(
				Map.entry(k1, v1),
				Map.entry(k2, v2),
				Map.entry(k3, v3));
	}

	public <K, V> Map<K, V> insertOrderedMap(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
		return insertOrderedMapOfEntries(
				Map.entry(k1, v1),
				Map.entry(k2, v2),
				Map.entry(k3, v3),
				Map.entry(k4, v4));
	}

	public <K, V> Map<K, V> insertOrderedMap(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
		return insertOrderedMapOfEntries(
				Map.entry(k1, v1),
				Map.entry(k2, v2),
				Map.entry(k3, v3),
				Map.entry(k4, v4),
				Map.entry(k5, v5));
	}

	@SafeVarargs
	public <K, V> Map<K, V> insertOrderedMapOfEntries(Map.Entry<K, V>... entries) {
		return Collections.unmodifiableMap(
				Stream.of(entries)
						.collect(
								Collectors.toMap(
										Map.Entry::getKey,
										Map.Entry::getValue,
										(a, b) -> a,
										LinkedHashMap::new)));
	}

}
