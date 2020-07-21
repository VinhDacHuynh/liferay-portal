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

package com.liferay.translation.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;
import com.liferay.translation.exception.NoSuchEntryException;
import com.liferay.translation.model.TranslationEntry;
import com.liferay.translation.service.TranslationEntryLocalServiceUtil;
import com.liferay.translation.service.persistence.TranslationEntryPersistence;
import com.liferay.translation.service.persistence.TranslationEntryUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class TranslationEntryPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.translation.service"));

	@Before
	public void setUp() {
		_persistence = TranslationEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<TranslationEntry> iterator = _translationEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		TranslationEntry translationEntry = _persistence.create(pk);

		Assert.assertNotNull(translationEntry);

		Assert.assertEquals(translationEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		TranslationEntry newTranslationEntry = addTranslationEntry();

		_persistence.remove(newTranslationEntry);

		TranslationEntry existingTranslationEntry =
			_persistence.fetchByPrimaryKey(newTranslationEntry.getPrimaryKey());

		Assert.assertNull(existingTranslationEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addTranslationEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		TranslationEntry newTranslationEntry = _persistence.create(pk);

		newTranslationEntry.setMvccVersion(RandomTestUtil.nextLong());

		newTranslationEntry.setUuid(RandomTestUtil.randomString());

		newTranslationEntry.setGroupId(RandomTestUtil.nextLong());

		newTranslationEntry.setCompanyId(RandomTestUtil.nextLong());

		newTranslationEntry.setUserId(RandomTestUtil.nextLong());

		newTranslationEntry.setUserName(RandomTestUtil.randomString());

		newTranslationEntry.setCreateDate(RandomTestUtil.nextDate());

		newTranslationEntry.setModifiedDate(RandomTestUtil.nextDate());

		newTranslationEntry.setClassNameId(RandomTestUtil.nextLong());

		newTranslationEntry.setClassPK(RandomTestUtil.nextLong());

		newTranslationEntry.setContent(RandomTestUtil.randomString());

		newTranslationEntry.setContentType(RandomTestUtil.randomString());

		newTranslationEntry.setLanguageId(RandomTestUtil.randomString());

		_translationEntries.add(_persistence.update(newTranslationEntry));

		TranslationEntry existingTranslationEntry =
			_persistence.findByPrimaryKey(newTranslationEntry.getPrimaryKey());

		Assert.assertEquals(
			existingTranslationEntry.getMvccVersion(),
			newTranslationEntry.getMvccVersion());
		Assert.assertEquals(
			existingTranslationEntry.getUuid(), newTranslationEntry.getUuid());
		Assert.assertEquals(
			existingTranslationEntry.getTranslationEntryId(),
			newTranslationEntry.getTranslationEntryId());
		Assert.assertEquals(
			existingTranslationEntry.getGroupId(),
			newTranslationEntry.getGroupId());
		Assert.assertEquals(
			existingTranslationEntry.getCompanyId(),
			newTranslationEntry.getCompanyId());
		Assert.assertEquals(
			existingTranslationEntry.getUserId(),
			newTranslationEntry.getUserId());
		Assert.assertEquals(
			existingTranslationEntry.getUserName(),
			newTranslationEntry.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(existingTranslationEntry.getCreateDate()),
			Time.getShortTimestamp(newTranslationEntry.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(existingTranslationEntry.getModifiedDate()),
			Time.getShortTimestamp(newTranslationEntry.getModifiedDate()));
		Assert.assertEquals(
			existingTranslationEntry.getClassNameId(),
			newTranslationEntry.getClassNameId());
		Assert.assertEquals(
			existingTranslationEntry.getClassPK(),
			newTranslationEntry.getClassPK());
		Assert.assertEquals(
			existingTranslationEntry.getContent(),
			newTranslationEntry.getContent());
		Assert.assertEquals(
			existingTranslationEntry.getContentType(),
			newTranslationEntry.getContentType());
		Assert.assertEquals(
			existingTranslationEntry.getLanguageId(),
			newTranslationEntry.getLanguageId());
	}

	@Test
	public void testCountByUuid() throws Exception {
		_persistence.countByUuid("");

		_persistence.countByUuid("null");

		_persistence.countByUuid((String)null);
	}

	@Test
	public void testCountByUUID_G() throws Exception {
		_persistence.countByUUID_G("", RandomTestUtil.nextLong());

		_persistence.countByUUID_G("null", 0L);

		_persistence.countByUUID_G((String)null, 0L);
	}

	@Test
	public void testCountByUuid_C() throws Exception {
		_persistence.countByUuid_C("", RandomTestUtil.nextLong());

		_persistence.countByUuid_C("null", 0L);

		_persistence.countByUuid_C((String)null, 0L);
	}

	@Test
	public void testCountByG_S() throws Exception {
		_persistence.countByG_S(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(), "");

		_persistence.countByG_S(0L, 0L, "null");

		_persistence.countByG_S(0L, 0L, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		TranslationEntry newTranslationEntry = addTranslationEntry();

		TranslationEntry existingTranslationEntry =
			_persistence.findByPrimaryKey(newTranslationEntry.getPrimaryKey());

		Assert.assertEquals(existingTranslationEntry, newTranslationEntry);
	}

	@Test(expected = NoSuchEntryException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<TranslationEntry> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"TranslationEntry", "mvccVersion", true, "uuid", true,
			"translationEntryId", true, "groupId", true, "companyId", true,
			"userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "classNameId", true, "classPK", true,
			"content", true, "contentType", true, "languageId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		TranslationEntry newTranslationEntry = addTranslationEntry();

		TranslationEntry existingTranslationEntry =
			_persistence.fetchByPrimaryKey(newTranslationEntry.getPrimaryKey());

		Assert.assertEquals(existingTranslationEntry, newTranslationEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		TranslationEntry missingTranslationEntry =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingTranslationEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		TranslationEntry newTranslationEntry1 = addTranslationEntry();
		TranslationEntry newTranslationEntry2 = addTranslationEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newTranslationEntry1.getPrimaryKey());
		primaryKeys.add(newTranslationEntry2.getPrimaryKey());

		Map<Serializable, TranslationEntry> translationEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, translationEntries.size());
		Assert.assertEquals(
			newTranslationEntry1,
			translationEntries.get(newTranslationEntry1.getPrimaryKey()));
		Assert.assertEquals(
			newTranslationEntry2,
			translationEntries.get(newTranslationEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, TranslationEntry> translationEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(translationEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		TranslationEntry newTranslationEntry = addTranslationEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newTranslationEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, TranslationEntry> translationEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, translationEntries.size());
		Assert.assertEquals(
			newTranslationEntry,
			translationEntries.get(newTranslationEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, TranslationEntry> translationEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(translationEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		TranslationEntry newTranslationEntry = addTranslationEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newTranslationEntry.getPrimaryKey());

		Map<Serializable, TranslationEntry> translationEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, translationEntries.size());
		Assert.assertEquals(
			newTranslationEntry,
			translationEntries.get(newTranslationEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			TranslationEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<TranslationEntry>() {

				@Override
				public void performAction(TranslationEntry translationEntry) {
					Assert.assertNotNull(translationEntry);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		TranslationEntry newTranslationEntry = addTranslationEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			TranslationEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"translationEntryId",
				newTranslationEntry.getTranslationEntryId()));

		List<TranslationEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		TranslationEntry existingTranslationEntry = result.get(0);

		Assert.assertEquals(existingTranslationEntry, newTranslationEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			TranslationEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"translationEntryId", RandomTestUtil.nextLong()));

		List<TranslationEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		TranslationEntry newTranslationEntry = addTranslationEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			TranslationEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("translationEntryId"));

		Object newTranslationEntryId =
			newTranslationEntry.getTranslationEntryId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"translationEntryId", new Object[] {newTranslationEntryId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingTranslationEntryId = result.get(0);

		Assert.assertEquals(existingTranslationEntryId, newTranslationEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			TranslationEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("translationEntryId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"translationEntryId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		TranslationEntry newTranslationEntry = addTranslationEntry();

		_persistence.clearCache();

		TranslationEntry existingTranslationEntry =
			_persistence.findByPrimaryKey(newTranslationEntry.getPrimaryKey());

		Assert.assertTrue(
			Objects.equals(
				existingTranslationEntry.getUuid(),
				ReflectionTestUtil.invoke(
					existingTranslationEntry, "getOriginalUuid",
					new Class<?>[0])));
		Assert.assertEquals(
			Long.valueOf(existingTranslationEntry.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingTranslationEntry, "getOriginalGroupId",
				new Class<?>[0]));

		Assert.assertEquals(
			Long.valueOf(existingTranslationEntry.getClassNameId()),
			ReflectionTestUtil.<Long>invoke(
				existingTranslationEntry, "getOriginalClassNameId",
				new Class<?>[0]));
		Assert.assertEquals(
			Long.valueOf(existingTranslationEntry.getClassPK()),
			ReflectionTestUtil.<Long>invoke(
				existingTranslationEntry, "getOriginalClassPK",
				new Class<?>[0]));
		Assert.assertTrue(
			Objects.equals(
				existingTranslationEntry.getLanguageId(),
				ReflectionTestUtil.invoke(
					existingTranslationEntry, "getOriginalLanguageId",
					new Class<?>[0])));
	}

	protected TranslationEntry addTranslationEntry() throws Exception {
		long pk = RandomTestUtil.nextLong();

		TranslationEntry translationEntry = _persistence.create(pk);

		translationEntry.setMvccVersion(RandomTestUtil.nextLong());

		translationEntry.setUuid(RandomTestUtil.randomString());

		translationEntry.setGroupId(RandomTestUtil.nextLong());

		translationEntry.setCompanyId(RandomTestUtil.nextLong());

		translationEntry.setUserId(RandomTestUtil.nextLong());

		translationEntry.setUserName(RandomTestUtil.randomString());

		translationEntry.setCreateDate(RandomTestUtil.nextDate());

		translationEntry.setModifiedDate(RandomTestUtil.nextDate());

		translationEntry.setClassNameId(RandomTestUtil.nextLong());

		translationEntry.setClassPK(RandomTestUtil.nextLong());

		translationEntry.setContent(RandomTestUtil.randomString());

		translationEntry.setContentType(RandomTestUtil.randomString());

		translationEntry.setLanguageId(RandomTestUtil.randomString());

		_translationEntries.add(_persistence.update(translationEntry));

		return translationEntry;
	}

	private List<TranslationEntry> _translationEntries =
		new ArrayList<TranslationEntry>();
	private TranslationEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}