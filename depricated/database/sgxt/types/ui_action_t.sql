--------------------------------------------------------
--  DDL for Type UI_ACTION_T 
--------------------------------------------------------

CREATE OR REPLACE TYPE "SGXT"."UI_ACTION_T"
AS
    object
    (
        action_name VARCHAR2 (4000),
        CONSTRUCTOR
    FUNCTION UI_ACTION_T (
            SELF IN OUT NOCOPY UI_ACTION_T)
        RETURN SELF
    AS
        RESULT) NOT FINAL;
    /
    --------------------------------------------------------
    --  DDL for Type UI_ACTION_T
    --------------------------------------------------------

CREATE OR REPLACE TYPE "SGXT"."UI_ACTION_T"
AS
    object
    (
        action_name VARCHAR2 (4000),
        CONSTRUCTOR
    FUNCTION UI_ACTION_T (
            SELF IN OUT NOCOPY UI_ACTION_T)
        RETURN SELF
    AS
        RESULT) NOT FINAL;
    /

CREATE OR REPLACE TYPE BODY "SGXT"."UI_ACTION_T"
AS
    CONSTRUCTOR
FUNCTION UI_ACTION_T (
        SELF IN OUT NOCOPY UI_ACTION_T)
    RETURN SELF
AS
    RESULT
IS
BEGIN
    
    RETURN;

END;

END;
/
