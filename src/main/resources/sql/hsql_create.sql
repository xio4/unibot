CREATE TABLE classes (
	id						BIGINT IDENTITY PRIMARY KEY,
	name					VARCHAR(255),
	description			LONGVARCHAR
);
CREATE TABLE itemname (
	id						BIGINT IDENTITY PRIMARY KEY,
	name					VARCHAR(255),
	add_name				VARCHAR(255),
	description			LONGVARCHAR
);
CREATE TABLE npcname (
	id						BIGINT IDENTITY PRIMARY KEY,
	name					VARCHAR(255),
	description			LONGVARCHAR

);
CREATE TABLE races (
	id						BIGINT IDENTITY PRIMARY KEY,
	name					VARCHAR(255),
	description			LONGVARCHAR

);
CREATE TABLE skillname (
	id						BIGINT IDENTITY PRIMARY KEY,
	name					VARCHAR(255),
	description			LONGVARCHAR,

);
CREATE TABLE systemmsg (
	id						BIGINT IDENTITY PRIMARY KEY,
	name					LONGVARCHAR,
	description			LONGVARCHAR

);
CREATE TABLE sysstring (
	id						BIGINT IDENTITY PRIMARY KEY,
	name					LONGVARCHAR,
	description			LONGVARCHAR

);



