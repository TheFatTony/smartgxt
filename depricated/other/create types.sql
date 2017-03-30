DELETE FROM UI_OBJECTS;

ALTER TABLE UI_OBJECTS
DROP COLUMN OBJECT_;
/
ALTER TABLE UI_OBJECTS ADD (OBJECT_ SGXT.UI_WIDGET_T NOT NULL) ;
/

DROP type "SGXT"."UI_MENU_BAR_ITEM_T" force;

DROP type "SGXT"."UI_MENU_ITEM_T" force;

DROP type "SGXT"."UI_GXT_COMPONENT_T" force;

DROP type "SGXT"."UI_WIDGET_T" force;
/

CREATE OR REPLACE TYPE "SGXT"."UI_WIDGET_T"
AS
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

CREATE OR REPLACE TYPE "SGXT"."UI_MENU_ITEM_T" UNDER "SGXT"."UI_GXT_COMPONENT_T" (text VARCHAR2 (4000), icon VARCHAR2 (4000), CONSTRUCTOR
FUNCTION ui_menu_item_t (
        SELF IN OUT NOCOPY ui_menu_item_t)
    RETURN SELF
AS
    RESULT, CONSTRUCTOR
FUNCTION ui_menu_item_t (
        SELF IN OUT NOCOPY ui_menu_item_t,
        class_name VARCHAR2)
    RETURN SELF
AS
    RESULT, CONSTRUCTOR
FUNCTION ui_menu_item_t (
        SELF IN OUT NOCOPY ui_menu_item_t,
        class_name VARCHAR2,
        stateful   CHAR)
    RETURN SELF
AS
    RESULT, CONSTRUCTOR
FUNCTION ui_menu_item_t (
        SELF IN OUT NOCOPY ui_menu_item_t,
        class_name VARCHAR2,
        stateful   CHAR,
        text       VARCHAR2)
    RETURN SELF
AS
    RESULT, CONSTRUCTOR
FUNCTION ui_menu_item_t (
        SELF IN OUT NOCOPY ui_menu_item_t,
        class_name VARCHAR2,
        stateful   CHAR,
        text       VARCHAR2,
        icon       VARCHAR2)
    RETURN SELF
AS
    RESULT, MEMBER PROCEDURE set_text (
        text VARCHAR2), MEMBER FUNCTION get_text
    RETURN VARCHAR2, MEMBER PROCEDURE set_icon (
        icon VARCHAR2), MEMBER FUNCTION get_icon
    RETURN VARCHAR2) NOT FINAL;
/

CREATE OR REPLACE TYPE BODY "SGXT"."UI_MENU_ITEM_T"
AS
    CONSTRUCTOR
FUNCTION ui_menu_item_t (
        SELF IN OUT NOCOPY ui_menu_item_t)
    RETURN SELF
AS
    RESULT
IS
BEGIN
    
    RETURN;

END;
CONSTRUCTOR

FUNCTION ui_menu_item_t (
        SELF IN OUT NOCOPY ui_menu_item_t,
        class_name VARCHAR2)
    RETURN SELF
AS
    RESULT
IS
BEGIN
    SELF.class_name := class_name;
    
    RETURN;

END;
CONSTRUCTOR

FUNCTION ui_menu_item_t (
        SELF IN OUT NOCOPY ui_menu_item_t,
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
CONSTRUCTOR

FUNCTION ui_menu_item_t (
        SELF IN OUT NOCOPY ui_menu_item_t,
        class_name VARCHAR2,
        stateful   CHAR,
        text       VARCHAR2)
    RETURN SELF
AS
    RESULT
IS
BEGIN
    SELF.class_name := class_name;
    SELF.stateful := stateful;
    SELF.text := text;
    
    RETURN;

END;
CONSTRUCTOR

FUNCTION ui_menu_item_t (
        SELF IN OUT NOCOPY ui_menu_item_t,
        class_name VARCHAR2,
        stateful   CHAR,
        text       VARCHAR2,
        icon       VARCHAR2)
    RETURN SELF
AS
    RESULT
IS
BEGIN
    SELF.class_name := class_name;
    SELF.stateful := stateful;
    SELF.text := text;
    SELF.icon := icon;
    
    RETURN;

END;
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
MEMBER PROCEDURE set_icon (
        icon VARCHAR2)
AS
BEGIN
    SELF.icon := icon;

END;
MEMBER

FUNCTION get_icon
    RETURN VARCHAR2
AS
BEGIN
    
    RETURN SELF.icon;

END;

END;
/
CREATE OR REPLACE TYPE "SGXT"."UI_EVENT_TYPE_T"
AS
    object
    (
        event_code varchar2) NOT FINAL;
/
CREATE OR REPLACE TYPE "SGXT"."UI_HANDLER_T"
AS
    object
    (
        event_code NUMBER,
        CONSTRUCTOR
    FUNCTION UI_ACTION_T (
            SELF IN OUT NOCOPY UI_HANDLER_T)
        RETURN SELF
    AS
        RESULT) NOT FINAL;
    /

CREATE OR REPLACE TYPE BODY "SGXT"."UI_HANDLER_T"
AS
    CONSTRUCTOR
FUNCTION UI_HANDLER_T (
        SELF IN OUT NOCOPY UI_HANDLER_T)
    RETURN SELF
AS
    RESULT
IS
BEGIN
    
    RETURN;

END;
MEMBER PROCEDURE set_event_code (
        event_code NUMBER)
AS
BEGIN
    SELF.event_code := event_code;

END;
MEMBER

FUNCTION get_event_code
    RETURN NUMBER
AS
BEGIN
    
    RETURN SELF.event_code;

END;

END;
/

CREATE OR REPLACE TYPE "SGXT"."UI_ACTION_T"
AS
    object
    (
        event_code NUMBER,
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
MEMBER PROCEDURE set_event_code (
        event_code NUMBER)
AS
BEGIN
    SELF.event_code := event_code;

END;
MEMBER

FUNCTION get_event_code
    RETURN NUMBER
AS
BEGIN
    
    RETURN SELF.event_code;

END;

END;