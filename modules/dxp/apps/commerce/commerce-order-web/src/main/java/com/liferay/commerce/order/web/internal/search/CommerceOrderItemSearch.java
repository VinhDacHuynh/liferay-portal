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

package com.liferay.commerce.order.web.internal.search;

import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceOrderItemSearch
	extends SearchContainer<CommerceOrderItem> {

	public static final String EMPTY_RESULTS_MESSAGE =
		"no-order-items-were-found";

	public static List<String> headerNames = new ArrayList<>();
	public static Map<String, String> orderableHeaders = new LinkedHashMap<>();

	static {
		headerNames.add("sku");
		headerNames.add("title");
		headerNames.add("quantity");
		headerNames.add("price");

		orderableHeaders.put("sku", "sku");
		orderableHeaders.put("title", "title");
		orderableHeaders.put("quantity", "quantity");
		orderableHeaders.put("price", "price");
	}

	public CommerceOrderItemSearch(
		PortletRequest portletRequest, PortletURL iteratorURL) {

		super(
			portletRequest, new CommerceOrderItemDisplayTerms(portletRequest),
			new CommerceOrderItemSearchTerms(portletRequest), DEFAULT_CUR_PARAM,
			DEFAULT_DELTA, iteratorURL, headerNames, EMPTY_RESULTS_MESSAGE);

		CommerceOrderItemDisplayTerms commerceOrderItemDisplayTerms =
			(CommerceOrderItemDisplayTerms)getDisplayTerms();

		iteratorURL.setParameter(
			CommerceOrderItemDisplayTerms.SKU,
			commerceOrderItemDisplayTerms.getSku());
		iteratorURL.setParameter(
			CommerceOrderItemDisplayTerms.TITLE,
			commerceOrderItemDisplayTerms.getTitle());

		try {
			PortalPreferences preferences =
				PortletPreferencesFactoryUtil.getPortalPreferences(
					portletRequest);

			String orderByCol = ParamUtil.getString(
				portletRequest, "orderByCol");
			String orderByType = ParamUtil.getString(
				portletRequest, "orderByType");

			if (Validator.isNotNull(orderByCol) &&
				Validator.isNotNull(orderByType)) {

				preferences.setValue(
					CommercePortletKeys.COMMERCE_ORDER,
					"commerce-order-items-order-by-col", orderByCol);
				preferences.setValue(
					CommercePortletKeys.COMMERCE_ORDER,
					"commerce-order-items-order-by-type", orderByType);
			}
			else {
				orderByCol = preferences.getValue(
					CommercePortletKeys.COMMERCE_ORDER,
					"commerce-order-items-order-by-col", "sku");
				orderByType = preferences.getValue(
					CommercePortletKeys.COMMERCE_ORDER,
					"commerce-order-items-order-by-type", "asc");
			}

			setOrderableHeaders(orderableHeaders);
			setOrderByCol(orderByCol);
			setOrderByType(orderByType);
		}
		catch (Exception e) {
			_log.error("Unable to initialize commerce order item search", e);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceOrderItemSearch.class);

}