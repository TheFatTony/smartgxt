-- Start of DDL Script for Procedure APPS.HOST_COMMAND_PROC
-- Generated 16-Jul-2009 11:26:17 from APPS@BTA_PROD_NEW

CREATE OR REPLACE 
PROCEDURE host_command_proc (p_command IN VARCHAR2) AS
   LANGUAGE JAVA
   NAME 'Host.executeCommand (java.lang.String)';
/
