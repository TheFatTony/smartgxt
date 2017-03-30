--------------------------------------------------------
--  DDL for Package APPLICATIONS_API
--------------------------------------------------------

CREATE OR REPLACE PACKAGE "SGXT"."APPLICATIONS_API" 
AS
FUNCTION get_application_id
    RETURN pls_integer;

PROCEDURE create_new_application (
        p_name IN VARCHAR2) ;

END applications_api;

/

--------------------------------------------------------
--  DDL for Package Body APPLICATIONS_API
--------------------------------------------------------

CREATE OR REPLACE PACKAGE BODY "SGXT"."APPLICATIONS_API" 
AS
FUNCTION get_application_id
    RETURN pls_integer
AS
    v_module VARCHAR2 (100) ;
    v_action VARCHAR2 (1000) ;
    n_application_id pls_integer;
BEGIN
    dbms_application_info.read_module (module_name=> v_module, action_name =>v_action) ;
    
    SELECT id INTO n_application_id FROM applications WHERE name = v_module;
    
    RETURN n_application_id;

END;

PROCEDURE create_new_application (
        p_name IN VARCHAR2)
IS
BEGIN
    
    INSERT INTO applications
        (id, name
        ) VALUES
        (applications_id_seq.nextval, p_name
        ) ;

END;

END applications_api;

/

