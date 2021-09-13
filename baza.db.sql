BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "status" (
	"id"	INTEGER UNIQUE,
	"name"	TEXT,
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
	"name"	TEXT,
	"type"	INTEGER,
	FOREIGN KEY("type") REFERENCES "type"("id"),
	PRIMARY KEY("id" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "notes" (
	"id"	INTEGER UNIQUE,
	"text"	TEXT,
	"name"	TEXT,
	"subject"	INTEGER,
	"user"	INTEGER,
	"sort"	INTEGER,
	FOREIGN KEY("subject") REFERENCES "subjects"("id"),
	FOREIGN KEY("user") REFERENCES "users"("id"),
	PRIMARY KEY("id" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "referencess" (
	"id"	INTEGER UNIQUE,
	"comment"	TEXT,
	"rate"	INTEGER,
	"note"	INTEGER,
	FOREIGN KEY("note") REFERENCES "notes"("id"),
	PRIMARY KEY("id" AUTOINCREMENT)
);
INSERT INTO "status" VALUES (1,'Ucenik');
INSERT INTO "status" VALUES (2,'Student');
INSERT INTO "status" VALUES (3,'Nijedno');
INSERT INTO "type" VALUES (1,'Srednja');
INSERT INTO "type" VALUES (2,'Fakultet');
INSERT INTO "subjects" VALUES (1,'IM2',2);
INSERT INTO "subjects" VALUES (2,'Matematika',1);
INSERT INTO "subjects" VALUES (3,'IM1',2);
INSERT INTO "subjects" VALUES (4,'Bosanski jezik',1);
INSERT INTO "subjects" VALUES (5,'Fizika',1);
INSERT INTO "subjects" VALUES (6,'LAG',2);
INSERT INTO "subjects" VALUES (7,'VIS',2);
INSERT INTO "subjects" VALUES (8,'Historija',1);
COMMIT;
