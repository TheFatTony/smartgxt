--------------------------------------------------------
--  File created - Sunday-July-31-2011
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Table APPLICATIONS
--------------------------------------------------------

CREATE TABLE "SGXT"."APPLICATIONS"
    (
        "ID" NUMBER ( *, 0), "NAME" VARCHAR2 (4000 CHAR)
    ) ;
--------------------------------------------------------
--  Constraints for Table APPLICATIONS
--------------------------------------------------------
ALTER TABLE "SGXT"."APPLICATIONS" MODIFY
(
    "ID" NOT NULL ENABLE
)
;
ALTER TABLE "SGXT"."APPLICATIONS" MODIFY
(
    "NAME" NOT NULL ENABLE
)
;

INSERT INTO SGXT.APPLICATIONS
    (ID, NAME
    ) VALUES
    (sgxt.applications_id_seq.nextval, 'rctest'
    ) ;
COMMIT;
