--------------------------------------------------------
--  File created - Sunday-July-31-2011
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Table UI_OBJECTS
--------------------------------------------------------

CREATE TABLE "SGXT"."UI_OBJECTS"
    (
        "ID" NUMBER ( *, 0), "PARENT_ID" NUMBER ( *, 0), "APLLICATION_ID" NUMBER ( *, 0), "CODE" VARCHAR2 (4000 BYTE), "OBJECT_TYPE"
             VARCHAR2 (35 BYTE), "LANGUAGE" CHAR (2 BYTE), "OBJECT_" "SGXT"."UI_WIDGET_T"
    ) ;
--------------------------------------------------------
--  Constraints for Table UI_OBJECTS
--------------------------------------------------------
ALTER TABLE "SGXT"."UI_OBJECTS" MODIFY
(
    "ID" NOT NULL ENABLE
)
;
ALTER TABLE "SGXT"."UI_OBJECTS" MODIFY
(
    "APLLICATION_ID" NOT NULL ENABLE
)
;
ALTER TABLE "SGXT"."UI_OBJECTS" MODIFY
(
    "CODE" NOT NULL ENABLE
)
;
ALTER TABLE "SGXT"."UI_OBJECTS" MODIFY
(
    "OBJECT_TYPE" NOT NULL ENABLE
)
;
ALTER TABLE "SGXT"."UI_OBJECTS" MODIFY
(
    "LANGUAGE" NOT NULL ENABLE
)
;
ALTER TABLE "SGXT"."UI_OBJECTS" MODIFY
(
    "OBJECT_" NOT NULL ENABLE
)
;
/

INSERT
INTO SGXT.UI_OBJECTS
    (
        ID, PARENT_ID, APLLICATION_ID, CODE, OBJECT_TYPE, LANGUAGE, OBJECT_
    )
    VALUES
    (
        1, 0, 1, 'com.smartgxt.rctest.client.metadata.menu', 'UI_MENU_BAR_ITEM_T', 'US', SGXT.UI_MENU_BAR_ITEM_T (NULL, NULL, 'Dynamic')
    ) ;

INSERT
INTO SGXT.UI_OBJECTS
    (
        ID, PARENT_ID, APLLICATION_ID, CODE, OBJECT_TYPE, LANGUAGE, OBJECT_
    )
    VALUES
    (
        1, 0, 1, 'com.smartgxt.rctest.client.metadata.menu', 'UI_MENU_BAR_ITEM_T', 'RU', SGXT.UI_MENU_BAR_ITEM_T (NULL, NULL,
        'Динамические')
    ) ;

INSERT
INTO SGXT.UI_OBJECTS
    (
        ID, PARENT_ID, APLLICATION_ID, CODE, OBJECT_TYPE, LANGUAGE, OBJECT_
    )
    VALUES
    (
        2, 1, 1, 'com.smartgxt.rctest.client.metadata.menu.item1', 'UI_MENU_ITEM_T', 'US', SGXT.UI_MENU_ITEM_T (NULL, NULL, 'Test 1', NULL)
    ) ;

INSERT
INTO SGXT.UI_OBJECTS
    (
        ID, PARENT_ID, APLLICATION_ID, CODE, OBJECT_TYPE, LANGUAGE, OBJECT_
    )
    VALUES
    (
        2, 1, 1, 'com.smartgxt.rctest.client.metadata.menu.item1', 'UI_MENU_ITEM_T', 'RU', SGXT.UI_MENU_ITEM_T (NULL, NULL, 'Тест 1', NULL)
    ) ;

INSERT
INTO SGXT.UI_OBJECTS
    (
        ID, PARENT_ID, APLLICATION_ID, CODE, OBJECT_TYPE, LANGUAGE, OBJECT_
    )
    VALUES
    (
        4, 2, 1, 'com.smartgxt.rctest.client.metadata.menu.item1.item1', 'UI_MENU_ITEM_T', 'RU', SGXT.UI_MENU_ITEM_T (NULL, NULL, 'Тест 1',
        NULL)
    ) ;

INSERT
INTO SGXT.UI_OBJECTS
    (
        ID, PARENT_ID, APLLICATION_ID, CODE, OBJECT_TYPE, LANGUAGE, OBJECT_
    )
    VALUES
    (
        4, 2, 1, 'com.smartgxt.rctest.client.metadata.menu.item1.item1', 'UI_MENU_ITEM_T', 'US', SGXT.UI_MENU_ITEM_T (NULL, NULL, 'Test 1',
        NULL)
    ) ;

INSERT
INTO SGXT.UI_OBJECTS
    (
        ID, PARENT_ID, APLLICATION_ID, CODE, OBJECT_TYPE, LANGUAGE, OBJECT_
    )
    VALUES
    (
        3, 1, 1, 'com.smartgxt.rctest.client.metadata.menu.item2', 'UI_MENU_ITEM_T', 'US', SGXT.UI_MENU_ITEM_T (NULL, NULL, 'Test 2', NULL)
    ) ;

INSERT
INTO SGXT.UI_OBJECTS
    (
        ID, PARENT_ID, APLLICATION_ID, CODE, OBJECT_TYPE, LANGUAGE, OBJECT_
    )
    VALUES
    (
        3, 1, 1, 'com.smartgxt.rctest.client.metadata.menu.item2', 'UI_MENU_ITEM_T', 'RU', SGXT.UI_MENU_ITEM_T (NULL, NULL, 'Тест 2', NULL)
    ) ;
COMMIT;