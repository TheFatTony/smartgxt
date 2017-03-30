CREATE OR REPLACE PACKAGE logger
AS
PROCEDURE log (
        error_message IN VARCHAR2,
        stack_trace IN VARCHAR2) ;


END logger;
/


CREATE OR REPLACE PACKAGE body logger
AS
    g_path      CONSTANT VARCHAR2 (1000) := 'F:\ORACLE\ADMIN\DRSCB\LOG';
    g_open_mode CONSTANT VARCHAR2 (2) := 'a';
    g_file_name VARCHAR2 (200) := 'file'|| '.log';
    g_file UTL_FILE.file_type;
    g_tab  VARCHAR2 (1) := chr (9) ;
    g_crfl VARCHAR2 (2) := CHR (13) ||CHR (10) ;

PROCEDURE log (
        error_message IN VARCHAR2,
        stack_trace IN VARCHAR2)
AS
    v_error_message VARCHAR2 (32767) ;
    v_stack_trace   VARCHAR2 (32767) ;
    b_file_exists   BOOLEAN;
    n_file_length   NUMBER;
    n_block_size BINARY_INTEGER;
BEGIN
    
    IF NOT UTL_FILE.is_open (g_file) THEN
        g_file := UTL_FILE.fopen (g_path, g_file_name, g_open_mode, 32767) ;
    
    END IF;
    --    v_error_message := error_message;
    --    v_stack_trace := stack_trace;
    v_error_message := sqlerrm;
    v_stack_trace := dbms_utility.format_error_backtrace;
    --    UTL_FILE.fgetattr (g_path, g_file_name, b_file_exists, n_file_length, n_block_size) ;
    --    UTL_FILE.put_line (g_file, n_file_length) ;
    UTL_FILE.put_line (g_file, '<error>') ;
    UTL_FILE.put_line (g_file, '<effective_date>'||TO_CHAR (sysdate, 'yyyy.MM.dd HH24:mm:ss') ||'</effective_date>') ;
    UTL_FILE.put_line (g_file, '<message>'||v_error_message||'</message>') ;
    UTL_FILE.put_line (g_file, '<stack_trace>'||v_stack_trace||'</stack_trace>') ;
    UTL_FILE.put_line (g_file, '</error>') ;
    utl_file.fflush (g_file) ;
    
    IF UTL_FILE.is_open (g_file) THEN
        UTL_FILE.fclose (g_file) ;
    
    END IF;

EXCEPTION

WHEN OTHERS THEN
    sys.dbms_system.ksdwrt (2, sqlerrm||chr (13) || dbms_utility.format_error_backtrace) ;
    
    IF UTL_FILE.is_open (g_file) THEN
        UTL_FILE.fclose (g_file) ;
    
    END IF;

END;
BEGIN
    --    open_file;
    NULL;

END logger;
/
