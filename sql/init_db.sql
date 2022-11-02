DROP TABLE IF EXISTS "task";
CREATE TABLE "public"."task" (
    "id" bigint not null,
    "description" character varying(255),
    "points" integer,
    "title" character varying(255),
    CONSTRAINT "task_pkey" PRIMARY KEY ("id")
) WITH (oids = false);

INSERT INTO "task" ("id", "description", "points", "title") VALUES
(1,	'task one description',	0,	'title one'),
(1,	'task two description',	0,	'title two'),