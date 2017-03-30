--------------------------------------------------------
--  DDL for Type UI_GXT_COMPONENT_T 
--------------------------------------------------------

  CREATE OR REPLACE TYPE "SGXT"."UI_GXT_COMPONENT_T" UNDER "SGXT"."UI_WIDGET_T" (stateful CHAR (1), CONSTRUCTOR
FUNCTION ui_gxt_component_t (
        SELF IN OUT NOCOPY ui_gxt_component_t)
    RETURN SELF
AS
    RESULT,
    --CONSTRUCTOR
    --FUNCTION ui_gxt_component_t (
    --        SELF IN OUT NOCOPY ui_gxt_component_t,
    --        class_name VARCHAR2)
    --    RETURN SELF
    --AS
    --    RESULT,
    CONSTRUCTOR
FUNCTION ui_gxt_component_t (
        SELF IN OUT NOCOPY ui_gxt_component_t,
        class_name VARCHAR2,
        stateful   CHAR)
    RETURN SELF
AS
    RESULT, MEMBER PROCEDURE set_stateful (
        stateful CHAR), MEMBER FUNCTION is_stateful
    RETURN CHAR) NOT FINAL;
/
CREATE OR REPLACE TYPE BODY "SGXT"."UI_GXT_COMPONENT_T" 
AS
    CONSTRUCTOR
FUNCTION ui_gxt_component_t (
        SELF IN OUT NOCOPY ui_gxt_component_t)
    RETURN SELF
AS
    RESULT
IS
BEGIN
    
    RETURN;

END;
--CONSTRUCTOR
--
--FUNCTION ui_gxt_component_t (
--        SELF IN OUT NOCOPY ui_gxt_component_t,
--        class_name VARCHAR2)
--    RETURN SELF
--AS
--    RESULT
--IS
--BEGIN
--    SELF.class_name := class_name;
--    SELF.stateful := '0';
--    RETURN;
--
--END;
CONSTRUCTOR

FUNCTION ui_gxt_component_t (
        SELF IN OUT NOCOPY ui_gxt_component_t,
        class_name VARCHAR2,
        stateful   CHAR)
    RETURN SELF
AS
    RESULT
IS
BEGIN
    SELF.class_name := class_name;
    SELF.stateful := stateful;
    
    RETURN;

END;
MEMBER PROCEDURE set_stateful (
        stateful CHAR)
AS
BEGIN
    SELF.stateful := stateful;

END;
MEMBER

FUNCTION is_stateful
    RETURN CHAR
AS
BEGIN
    
    RETURN SELF.stateful;

END;

END;

/

