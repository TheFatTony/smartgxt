--------------------------------------------------------
--  DDL for Package USER_STATES_API
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "SGXT"."USER_STATES_API" 
AS
PROCEDURE update_states (
        p_states IN user_states_t) ;
    
    FUNCTION get_states (
            p_current_user IN VARCHAR2,
            p_current_version IN NUMBER)
        RETURN user_states_t PIPELINED;
    
    END user_states_api;

/

--------------------------------------------------------
--  DDL for Package Body USER_STATES_API
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE BODY "SGXT"."USER_STATES_API" 
AS
PROCEDURE insert_row (
        p_name IN user_states.name%type,
        p_user_name IN user_states.user_name%type,
        p_value IN user_states.value%type DEFAULT NULL,
        p_application_id IN user_states.application_id%type)
IS
BEGIN
    
    INSERT INTO user_states
        (name, user_name, value, application_id
        ) VALUES
        (p_name, p_user_name, p_value, p_application_id
        ) ;

END insert_row;
-- update

PROCEDURE update_row
    (
        p_name IN user_states.name%type,
        p_user_name IN user_states.user_name%type,
        p_value IN user_states.value%type DEFAULT NULL,
        p_application_id IN user_states.application_id%type
    )
IS
BEGIN
    
    UPDATE USER_STATES SET NAME = p_NAME, USER_NAME = p_USER_NAME, VALUE = p_VALUE, APPLICATION_ID = p_APPLICATION_ID ;

END;
-- del

PROCEDURE delete_rows (
        p_user_name IN user_states.user_name%type,
        p_application_id IN user_states.application_id%type)
IS
BEGIN
    
    DELETE FROM USER_STATES WHERE application_id = p_application_id AND user_name = p_user_name ;

END;

PROCEDURE update_states (
        p_states IN user_states_t)
AS
    o_state user_state_t;
    v_user VARCHAR2 (30) := USER;
    n_application_id pls_integer := applications_api.get_application_id () ;
BEGIN
    delete_rows (p_user_name =>v_user, p_application_id =>n_application_id) ;
    
    IF p_states IS NOT NULL THEN
        
        FOR i IN p_states.first .. p_states.last
        LOOP
            o_state := p_states (i) ;
            insert_row (p_name => o_state.name, p_value =>o_state.value, p_application_id => n_application_id, p_user_name => v_user) ;
        
        END LOOP;
    
    END IF;
    COMMIT;

END update_states;

FUNCTION get_states (
        p_current_user IN VARCHAR2,
        p_current_version IN NUMBER)
    RETURN user_states_t PIPELINED
AS
    v_user VARCHAR2 (30) := USER;
    n_application_id pls_integer := applications_api.get_application_id () ;
    o_state user_state_t;
    n_current_version NUMBER;
    is_export         BOOLEAN := false;
BEGIN
    
    IF (p_current_user = v_user) THEN
        BEGIN
            
            SELECT to_number (value)
            INTO n_current_version
            FROM user_states
            WHERE user_name = v_user
            AND application_id = n_application_id
            AND name = 'sgxt.states.version';
        
        EXCEPTION
        
        WHEN NO_DATA_FOUND THEN
           n_current_version := 0;
        
        END;
        
        IF (n_current_version > p_current_version) THEN
            is_export := true;
        
        END IF;
    
    ELSE
        is_export := true;
    
    END IF;
    
    IF is_export THEN
        
        FOR i IN
        (SELECT name, value FROM user_states WHERE user_name = v_user AND application_id = n_application_id
        )
        LOOP
            o_state := user_state_t (i.name, i.value) ;
            pipe row (o_state) ;
        
        END LOOP;
    
    END IF;
    
    RETURN;

END;

END user_states_api;

/

