--------------------------------------------------------
--  DDL for Type UI_MENU_BAR_ITEM_T
--------------------------------------------------------

CREATE OR REPLACE TYPE "SGXT"."UI_MENU_BAR_ITEM_T" UNDER "SGXT"."UI_GXT_COMPONENT_T" (text VARCHAR2 (4000), CONSTRUCTOR
FUNCTION ui_menu_bar_item_t (
        SELF IN OUT NOCOPY ui_menu_bar_item_t)
    RETURN SELF
AS
    RESULT,
    --    CONSTRUCTOR
    --FUNCTION ui_menu_bar_item_t (
    --        SELF IN OUT NOCOPY ui_menu_bar_item_t,
    --        class_name VARCHAR2)
    --    RETURN SELF
    --AS
    --    RESULT, CONSTRUCTOR
    --FUNCTION ui_menu_bar_item_t (
    --        SELF IN OUT NOCOPY ui_menu_bar_item_t,
    --        class_name VARCHAR2,
    --        stateful   CHAR)
    --    RETURN SELF
    --AS
    --    RESULT, CONSTRUCTOR
    --FUNCTION ui_menu_bar_item_t (
    --        SELF IN OUT NOCOPY ui_menu_bar_item_t,
    --        class_name VARCHAR2,
    --        stateful   CHAR,
    --        text       VARCHAR2)
    --    RETURN SELF
    --AS
    --    RESULT,
    MEMBER PROCEDURE set_text (
        text VARCHAR2), MEMBER FUNCTION get_text
    RETURN VARCHAR2) NOT FINAL;
/

CREATE OR REPLACE TYPE BODY "SGXT"."UI_MENU_BAR_ITEM_T"
AS
    CONSTRUCTOR
FUNCTION ui_menu_bar_item_t (
        SELF IN OUT NOCOPY ui_menu_bar_item_t)
    RETURN SELF
AS
    RESULT
IS
BEGIN
    
    RETURN;

END;
--CONSTRUCTOR
--
--FUNCTION ui_menu_bar_item_t (
--        SELF IN OUT NOCOPY ui_menu_bar_item_t,
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
--CONSTRUCTOR
--
--FUNCTION ui_menu_bar_item_t (
--        SELF IN OUT NOCOPY ui_menu_bar_item_t,
--        class_name VARCHAR2,
--        stateful   CHAR)
--    RETURN SELF
--AS
--    RESULT
--IS
--BEGIN
--    SELF.class_name := class_name;
--    SELF.stateful := stateful;
--
--    RETURN;
--
--END;
--CONSTRUCTOR
--
--FUNCTION ui_menu_bar_item_t (
--        SELF IN OUT NOCOPY ui_menu_bar_item_t,
--        class_name VARCHAR2,
--        stateful   CHAR,
--        text       VARCHAR2)
--    RETURN SELF
--AS
--    RESULT
--IS
--BEGIN
--    SELF.class_name := class_name;
--    SELF.stateful := stateful;
--    SELF.text := text;
--
--    RETURN;
--
--END;
MEMBER PROCEDURE set_text (
        text VARCHAR2)
AS
BEGIN
    SELF.text := text;

END;
MEMBER

FUNCTION get_text
    RETURN VARCHAR2
AS
BEGIN
    
    RETURN SELF.text;

END;

END;
/
