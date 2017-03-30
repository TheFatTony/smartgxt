--------------------------------------------------------
--  DDL for Type UI_WIDGET_T 
--------------------------------------------------------

  CREATE OR REPLACE TYPE "SGXT"."UI_WIDGET_T" AS
    object
    (
        class_name VARCHAR2 (4000),
        CONSTRUCTOR
    FUNCTION ui_widget_t (
            SELF IN OUT NOCOPY UI_WIDGET_T)
        RETURN SELF
    AS
        RESULT,
        --        CONSTRUCTOR
        --    FUNCTION ui_widget_t (
        --            SELF IN OUT NOCOPY UI_WIDGET_T,
        --            class_name VARCHAR2)
        --        RETURN SELF
        --    AS
        --        RESULT,
        MEMBER PROCEDURE set_class_name (
            class_name VARCHAR2),
        MEMBER FUNCTION get_class_name
        RETURN VARCHAR2) NOT FINAL;
    
/
CREATE OR REPLACE TYPE BODY "SGXT"."UI_WIDGET_T" 
AS
    CONSTRUCTOR
FUNCTION ui_widget_t (
        SELF IN OUT NOCOPY ui_widget_t)
    RETURN SELF
AS
    RESULT
IS
BEGIN
    
    RETURN;

END;
--CONSTRUCTOR
--
--FUNCTION ui_widget_t (
--        SELF IN OUT NOCOPY ui_widget_t,
--        class_name VARCHAR2)
--    RETURN SELF
--AS
--    RESULT
--IS
--BEGIN
--    SELF.class_name := class_name;
--
--    RETURN;
--
--END;
MEMBER PROCEDURE set_class_name (
        class_name VARCHAR2)
AS
BEGIN
    SELF.class_name := class_name;

END;
MEMBER

FUNCTION get_class_name
    RETURN VARCHAR2
AS
BEGIN
    
    RETURN SELF.class_name;

END;

END;

/

