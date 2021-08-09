BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "status" (
	"id"	INTEGER UNIQUE,
	"naziv"	TEXT,
	PRIMARY KEY("id" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "users" (
	"id"	INTEGER UNIQUE,
	"name"	TEXT,
	"surname"	TEXT,
	"username"	TEXT,
	"email"	TEXT,
	"password"	TEXT,
	"status"	INTEGER,
	FOREIGN KEY("status") REFERENCES "status"("id"),
	PRIMARY KEY("id" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "type" (
	"id"	INTEGER UNIQUE,
	"name"	TEXT,
	PRIMARY KEY("id" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "subjects" (
	"id"	INTEGER UNIQUE,
	"naziv"	TEXT,
	"type"	INTEGER,
	FOREIGN KEY("type") REFERENCES "type"("id"),
	PRIMARY KEY("id" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "notes" (
	"id"	INTEGER UNIQUE,
	"name"	TEXT,
	"subject"	INTEGER,
	"user"	INTEGER,
	FOREIGN KEY("subject") REFERENCES "subjects"("id"),
	FOREIGN KEY("user") REFERENCES "users"("id"),
	PRIMARY KEY("id" AUTOINCREMENT)
);
INSERT INTO "status" VALUES (1,'ucenik');
INSERT INTO "status" VALUES (2,'student');
INSERT INTO "status" VALUES (3,'nijedno');
INSERT INTO "type" VALUES (1,'srednja');
INSERT INTO "type" VALUES (2,'fakultet');
COMMIT;
