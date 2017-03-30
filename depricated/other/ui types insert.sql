DECLARE
    o_bar_item UI_MENU_BAR_ITEM_T;
    o_menu_item UI_MENU_ITEM_T;
BEGIN
    
    DELETE FROM UI_OBJECTS;
    
    o_bar_item := UI_MENU_BAR_ITEM_T () ;
    o_bar_item.set_text ('Dynamic') ;
    
    INSERT
    INTO UI_OBJECTS
        (
            ID, PARENT_ID, APLLICATION_ID, CODE, OBJECT_, OBJECT_TYPE, LANGUAGE
        )
        VALUES
        (
            1, 0, 1, 'com.smartgxt.rctest.client.metadata.menu', o_bar_item, 'UI_MENU_BAR_ITEM_T', 'US'
        ) ;
    ----------------
    o_bar_item := UI_MENU_BAR_ITEM_T () ;
    o_bar_item.set_text ('Динамические') ;
    
    INSERT
    INTO UI_OBJECTS
        (
            ID, PARENT_ID, APLLICATION_ID, CODE, OBJECT_, OBJECT_TYPE, LANGUAGE
        )
        VALUES
        (
            1, 0, 1, 'com.smartgxt.rctest.client.metadata.menu', o_bar_item, 'UI_MENU_BAR_ITEM_T', 'RU'
        ) ;
    ----------------
    o_menu_item := UI_MENU_ITEM_T () ;
    o_menu_item.set_text ('Test 1') ;
    
    INSERT
    INTO UI_OBJECTS
        (
            ID, PARENT_ID, APLLICATION_ID, CODE, OBJECT_, OBJECT_TYPE, LANGUAGE
        )
        VALUES
        (
            2, 1, 1, 'com.smartgxt.rctest.client.metadata.menu.item1', o_menu_item, 'UI_MENU_ITEM_T', 'US'
        ) ;
    ----------------
    o_menu_item := UI_MENU_ITEM_T () ;
    o_menu_item.set_text ('Тест 1') ;
    
    INSERT
    INTO UI_OBJECTS
        (
            ID, PARENT_ID, APLLICATION_ID, CODE, OBJECT_, OBJECT_TYPE, LANGUAGE
        )
        VALUES
        (
            2, 1, 1, 'com.smartgxt.rctest.client.metadata.menu.item1', o_menu_item, 'UI_MENU_ITEM_T', 'RU'
        ) ;
    ----------------
    o_menu_item := UI_MENU_ITEM_T () ;
    o_menu_item.set_text ('Тест 1') ;
    
    INSERT
    INTO UI_OBJECTS
        (
            ID, PARENT_ID, APLLICATION_ID, CODE, OBJECT_, OBJECT_TYPE, LANGUAGE
        )
        VALUES
        (
            4, 2, 1, 'com.smartgxt.rctest.client.metadata.menu.item1.item1', o_menu_item, 'UI_MENU_ITEM_T', 'RU'
        ) ;
    ----------------
    o_menu_item := UI_MENU_ITEM_T () ;
    o_menu_item.set_text ('Test 1') ;
    
    INSERT
    INTO UI_OBJECTS
        (
            ID, PARENT_ID, APLLICATION_ID, CODE, OBJECT_, OBJECT_TYPE, LANGUAGE
        )
        VALUES
        (
            4, 2, 1, 'com.smartgxt.rctest.client.metadata.menu.item1.item1', o_menu_item, 'UI_MENU_ITEM_T', 'US'
        ) ;
    ----------------
    o_menu_item := UI_MENU_ITEM_T () ;
    o_menu_item.set_text ('Test 2') ;
    
    INSERT
    INTO UI_OBJECTS
        (
            ID, PARENT_ID, APLLICATION_ID, CODE, OBJECT_, OBJECT_TYPE, LANGUAGE
        )
        VALUES
        (
            3, 1, 1, 'com.smartgxt.rctest.client.metadata.menu.item2', o_menu_item, 'UI_MENU_ITEM_T', 'US'
        ) ;
    ----------------
    o_menu_item := UI_MENU_ITEM_T () ;
    o_menu_item.set_text ('Тест 2') ;
    
    INSERT
    INTO UI_OBJECTS
        (
            ID, PARENT_ID, APLLICATION_ID, CODE, OBJECT_, OBJECT_TYPE, LANGUAGE
        )
        VALUES
        (
            3, 1, 1, 'com.smartgxt.rctest.client.metadata.menu.item2', o_menu_item, 'UI_MENU_ITEM_T', 'RU'
        ) ;
    COMMIT;

END;