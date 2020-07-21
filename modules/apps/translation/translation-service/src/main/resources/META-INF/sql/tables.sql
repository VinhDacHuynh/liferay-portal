create table TranslationEntry (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	translationEntryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
	content VARCHAR(75) null,
	contentType VARCHAR(75) null,
	languageId VARCHAR(75) null
);