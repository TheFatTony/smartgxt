--------------------------------------------------------
--  File created - Sunday-July-31-2011
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Table USER_STATES
--------------------------------------------------------

CREATE TABLE "SGXT"."USER_STATES"
    (
        "USER_NAME" VARCHAR2 (30 CHAR), "APPLICATION_ID" NUMBER, "NAME" VARCHAR2 (4000 CHAR), "VALUE" CLOB
    ) ;
--------------------------------------------------------
--  Constraints for Table USER_STATES
--------------------------------------------------------
ALTER TABLE "SGXT"."USER_STATES" MODIFY
(
    "USER_NAME" NOT NULL ENABLE
)
;
ALTER TABLE "SGXT"."USER_STATES" MODIFY
(
    "APPLICATION_ID" NOT NULL ENABLE
)
;
ALTER TABLE "SGXT"."USER_STATES" MODIFY
(
    "NAME" NOT NULL ENABLE
)
;
