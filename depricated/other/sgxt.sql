-- 
CREATE USER concurrent IDENTIFIED BY concurrent123 ;
/
GRANT "CONNECT" TO concurrent ;
/
-- USER SQL
CREATE USER sgxt IDENTIFIED BY sgxt123 ;


GRANT "RESOURCE" TO sgxt ;
GRANT "CONNECT" TO sgxt ;

GRANT CREATE VIEW TO sgxt ;
/
--------------------------------------------------------
--  DDL for Type USER_STATES_T
--------------------------------------------------------

  CREATE OR REPLACE TYPE "SGXT"."USER_STATES_T" 
AS
    TABLE of user_state_t;

/

--------------------------------------------------------
--  DDL for Type USER_STATE_T
--------------------------------------------------------

  CREATE OR REPLACE TYPE "SGXT"."USER_STATE_T" 
AS
    object
    (
        name VARCHAR2 (4000),
        value CLOB)

/

--------------------------------------------------------
--  DDL for Table APPLICATIONS
--------------------------------------------------------

  CREATE TABLE "SGXT"."APPLICATIONS" 
   (	"ID" NUMBER(*,0), 
	"NAME" VARCHAR2(4000 CHAR)
   );
--------------------------------------------------------
--  DDL for Table USER_STATES
--------------------------------------------------------

  CREATE TABLE "SGXT"."USER_STATES" 
   (	"USER_NAME" VARCHAR2(30 CHAR), 
	"APPLICATION_ID" NUMBER, 
	"NAME" VARCHAR2(4000 CHAR), 
	"VALUE" CLOB
   );
--------------------------------------------------------
--  Constraints for Table APPLICATIONS
--------------------------------------------------------

  ALTER TABLE "SGXT"."APPLICATIONS" MODIFY ("ID" NOT NULL ENABLE);
 
  ALTER TABLE "SGXT"."APPLICATIONS" MODIFY ("NAME" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table USER_STATES
--------------------------------------------------------

  ALTER TABLE "SGXT"."USER_STATES" MODIFY ("USER_NAME" NOT NULL ENABLE);
 
  ALTER TABLE "SGXT"."USER_STATES" MODIFY ("APPLICATION_ID" NOT NULL ENABLE);
 
  ALTER TABLE "SGXT"."USER_STATES" MODIFY ("NAME" NOT NULL ENABLE);
--------------------------------------------------------
--  DDL for Package USER_STATES_API
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "SGXT"."USER_STATES_API" 
AS
    PROCEDURE update_states (
        p_states IN user_states_t) ;
    
    FUNCTION get_application_id
        RETURN pls_integer;
    
    FUNCTION get_states
        RETURN user_states_t PIPELINED;
    
END user_states_api;

/

--------------------------------------------------------
--  DDL for Package USER_STATES_PKG
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "SGXT"."USER_STATES_PKG" 
IS
type USER_STATE_T
IS
    record
    (
        NAME USER_STATES.NAME%type,
        USER_NAME USER_STATES.USER_NAME%type,
        VALUE USER_STATES.VALUE%type,
        APPLICATION_ID USER_STATES.APPLICATION_ID%type) ;
type USER_STATES_t
IS
    TABLE OF USER_STATE_t;


PROCEDURE insert_row (
        p_name IN user_states.name%type,
        p_user_name IN user_states.user_name%type,
        p_value IN user_states.value%type DEFAULT NULL,
        p_application_id IN user_states.application_id%type) ;
        
PROCEDURE delete_rows (
        p_user_name IN user_states.user_name%type,
        p_application_id IN user_states.application_id%type);

END user_states_pkg;

/

--------------------------------------------------------
--  DDL for Package Body USER_STATES_API
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE BODY "SGXT"."USER_STATES_API" 
AS
FUNCTION get_application_id
    RETURN pls_integer
AS
    v_module VARCHAR2 (100) ;
    v_action VARCHAR2 (1000) ;
    n_application_id pls_integer;
BEGIN
    dbms_application_info.read_module (module_name=> v_module, action_name =>v_action) ;
    
    SELECT id
    INTO n_application_id
    FROM applications
    WHERE name = v_module;
    
    RETURN n_application_id;

END;

PROCEDURE update_states (
        p_states IN user_states_t)
AS
    o_state user_state_t;
    v_user VARCHAR2 (30) := USER;
    n_application_id pls_integer := get_application_id () ;
BEGIN
    user_states_pkg.delete_rows (p_user_name =>v_user, p_application_id =>n_application_id) ;
    
    FOR i IN p_states.first .. p_states.last
    LOOP
        o_state := p_states (i) ;
        user_states_pkg.insert_row (p_name => o_state.name, p_value =>o_state.value, p_application_id => n_application_id, p_user_name =>
        v_user) ;
    
    END LOOP;
    COMMIT;

END update_states;

FUNCTION get_states
    RETURN user_states_t PIPELINED
AS
    v_user VARCHAR2 (30) := USER;
    n_application_id pls_integer := get_application_id () ;
    o_state user_state_t;
BEGIN
    
    FOR i IN
    (SELECT name, value FROM user_states WHERE user_name = v_user AND application_id = n_application_id
    )
    LOOP
        o_state := user_state_t (i.name, i.value) ;
        pipe row (o_state) ;
    
    END LOOP;
    
    RETURN;

END;

END user_states_api;

/

--------------------------------------------------------
--  DDL for Package Body USER_STATES_PKG
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE BODY "SGXT"."USER_STATES_PKG" 
IS
    -- insert

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

END user_states_pkg;

/

