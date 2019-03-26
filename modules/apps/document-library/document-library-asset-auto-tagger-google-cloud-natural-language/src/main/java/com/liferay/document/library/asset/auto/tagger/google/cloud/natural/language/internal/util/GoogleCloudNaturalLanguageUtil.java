/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.document.library.asset.auto.tagger.google.cloud.natural.language.internal.util;

import com.liferay.portal.kernel.json.JSONUtil;

import java.nio.charset.StandardCharsets;

import java.util.Arrays;

/**
 * @author Alicia García
 */
public class GoogleCloudNaturalLanguageUtil {

	public static String getDocumentPayload(String content, String type) {
		return JSONUtil.put(
			"document",
			JSONUtil.put(
				"type", type
			).put(
				"content", content
			)
		).toString();
	}

	public static String truncateToSize(String content, int size) {
		byte[] bytes = content.getBytes(StandardCharsets.UTF_8);

		if (bytes.length <= size) {
			return content;
		}

		bytes = Arrays.copyOf(bytes, size);

		int i = size - 1;

		while ((bytes[i] & 0x80) != 0) {
			i--;
		}

		if (i < (size - 1)) {
			bytes = Arrays.copyOf(bytes, i + 1);
		}

		return _truncateToWord(new String(bytes, StandardCharsets.UTF_8));
	}

	private static String _truncateToWord(String content) {
		int i = content.length() - 1;

		while ((i > 0) && !Character.isWhitespace(content.charAt(i))) {
			i--;
		}

		return content.substring(0, i);
	}

}