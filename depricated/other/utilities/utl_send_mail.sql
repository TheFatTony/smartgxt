--------------------------------------------------------
--  DDL for Table MAIL_ATTACHMENTS
--------------------------------------------------------

  CREATE TABLE "UTL"."MAIL_ATTACHMENTS" 
   (	"MESSAGE_ID" NUMBER, 
	"CONTENT" BLOB, 
	"NAME" VARCHAR2(4000 CHAR)
   ) PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 32768 NEXT 32768 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "TBS_BUFFER" 
 LOB ("CONTENT") STORE AS "SYS_LOB0000196132C00002$$"(
  TABLESPACE "TBS_GENERAL" ENABLE STORAGE IN ROW CHUNK 4096 PCTVERSION 10
  NOCACHE LOGGING 
  STORAGE(INITIAL 32768 NEXT 32768 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)) ;
 

   COMMENT ON COLUMN "UTL"."MAIL_ATTACHMENTS"."MESSAGE_ID" IS 'Ссылка на сообщение';
 
   COMMENT ON COLUMN "UTL"."MAIL_ATTACHMENTS"."CONTENT" IS 'Файл вложения';
 
   COMMENT ON COLUMN "UTL"."MAIL_ATTACHMENTS"."NAME" IS 'Наименование файла вложения';
 
   COMMENT ON TABLE "UTL"."MAIL_ATTACHMENTS"  IS 'Вложения почтовых сообщений';
--------------------------------------------------------
--  DDL for Table MAIL_MESSAGES
--------------------------------------------------------

  CREATE TABLE "UTL"."MAIL_MESSAGES" 
   (	"ID" NUMBER, 
	"SENDER" VARCHAR2(200 BYTE), 
	"RECIPIENTS" VARCHAR2(200 BYTE), 
	"BODY" CLOB, 
	"SEND_DATE" DATE DEFAULT sysdate, 
	"SUBJECT" VARCHAR2(1000 CHAR)
   ) PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 32768 NEXT 32768 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "TBS_BUFFER" 
 LOB ("BODY") STORE AS (
  TABLESPACE "TBS_BUFFER" ENABLE STORAGE IN ROW CHUNK 4096 PCTVERSION 10
  NOCACHE LOGGING 
  STORAGE(INITIAL 32768 NEXT 32768 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)) ;
 

   COMMENT ON COLUMN "UTL"."MAIL_MESSAGES"."ID" IS 'Уникальный ID';
 
   COMMENT ON COLUMN "UTL"."MAIL_MESSAGES"."SENDER" IS 'Отправитель';
 
   COMMENT ON COLUMN "UTL"."MAIL_MESSAGES"."RECIPIENTS" IS 'Получатели';
 
   COMMENT ON COLUMN "UTL"."MAIL_MESSAGES"."BODY" IS 'Тело сообщения';
 
   COMMENT ON COLUMN "UTL"."MAIL_MESSAGES"."SEND_DATE" IS 'Дата отправки';
 
   COMMENT ON COLUMN "UTL"."MAIL_MESSAGES"."SUBJECT" IS 'Заголовок сообщения';
 
   COMMENT ON TABLE "UTL"."MAIL_MESSAGES"  IS 'Очередь почтовых сообщений';
--------------------------------------------------------
--  DDL for Index MAIL_MESSAGES_ID_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "UTL"."MAIL_MESSAGES_ID_PK" ON "UTL"."MAIL_MESSAGES" ("ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 32768 NEXT 32768 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "TBS_BUFFER" ;
--------------------------------------------------------
--  Constraints for Table MAIL_ATTACHMENTS
--------------------------------------------------------

  ALTER TABLE "UTL"."MAIL_ATTACHMENTS" MODIFY ("MESSAGE_ID" NOT NULL ENABLE);
 
  ALTER TABLE "UTL"."MAIL_ATTACHMENTS" MODIFY ("CONTENT" NOT NULL ENABLE);
 
  ALTER TABLE "UTL"."MAIL_ATTACHMENTS" MODIFY ("NAME" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table MAIL_MESSAGES
--------------------------------------------------------

  ALTER TABLE "UTL"."MAIL_MESSAGES" ADD CONSTRAINT "MAIL_MESSAGES_ID_PK" PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 32768 NEXT 32768 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "TBS_BUFFER"  ENABLE;
 
  ALTER TABLE "UTL"."MAIL_MESSAGES" MODIFY ("ID" NOT NULL ENABLE);
 
  ALTER TABLE "UTL"."MAIL_MESSAGES" MODIFY ("SENDER" NOT NULL ENABLE);
 
  ALTER TABLE "UTL"."MAIL_MESSAGES" MODIFY ("RECIPIENTS" NOT NULL ENABLE);
 
  ALTER TABLE "UTL"."MAIL_MESSAGES" MODIFY ("BODY" NOT NULL ENABLE);
 
  ALTER TABLE "UTL"."MAIL_MESSAGES" MODIFY ("SEND_DATE" NOT NULL ENABLE);
--------------------------------------------------------
--  Ref Constraints for Table MAIL_ATTACHMENTS
--------------------------------------------------------

  ALTER TABLE "UTL"."MAIL_ATTACHMENTS" ADD CONSTRAINT "MAIL_ATTACHMENTS_MAIL_FK" FOREIGN KEY ("MESSAGE_ID")
	  REFERENCES "UTL"."MAIL_MESSAGES" ("ID") ON DELETE CASCADE ENABLE;
/
--------------------------------------------------------
--  DDL for Type ATTACHMENT_T
--------------------------------------------------------

  CREATE OR REPLACE TYPE "UTL"."ATTACHMENT_T" as object 
( 
  file_name varchar2(4000 CHAR),
  file_content blob
)

/

--------------------------------------------------------
--  DDL for Type ATTACHMENTS_T
--------------------------------------------------------

  CREATE OR REPLACE TYPE "UTL"."ATTACHMENTS_T" 
AS
    TABLE OF attachment_t;

/
--------------------------------------------------------
--  DDL for Package UTL_SEND_MAIL
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "UTL"."UTL_SEND_MAIL" 
    /**
    * Пакет для отправки почтовых сообщений.
    * Функционал:
    * -1- Рассылка сообщений - серия процедур send_mail.
    * -2- Рассылка сообщений с применением очереди сообщений - серия процедур schedule_mail.
    * -3- Генерация сообщения с помошью API процедцры: begin_mail, attach_body, attach_blob, end_mail
    *
    * Посылка обычного сообщения:
    * BEGIN
    * utl_send_mail.send_mail (sender => 'Notifier <notifier@mail90.csdepo.net>',
    * recipients => 'anto2n@mail90.csdepo.net', subject => 'Тест',
    * MESSAGE=> 'бла бла бла', attachments => null) ;
    * END;
    *
    * Постановка сообщения в очередь:
    * BEGIN
    * utl_send_mail.schedule_mail (sender => 'Notifier <notifier@mail90.csdepo.net>',
    * recipients => 'anton@mail90.csdepo.net', subject => 'Тест', MESSAGE=> 'бла бла бла', attachments => NULL) ;
    * END;
    *
    * Постановка сообщения в очередь с вложениями:
    * DECLARE
    * attachments attachments_t;
    * BEGIN
    * attachments := attachments_t () ;
    * FOR i IN (SELECT column1, column2 FROM table1)
    * LOOP
    *    attachments.extend;
    *    attachments (attachments.last) := attachment_t (i.column2, i.column1) ;
    *
    * END LOOP;
    * utl_send_mail.schedule_mail (sender => 'Notifier <notifier@mail90.csdepo.net>',
    * recipients => 'anton@mail90.csdepo.net',
    * subject => 'Тест', MESSAGE=> 'бла бла бла', attachments => attachments) ;
    * END;
    *
    * Обработка очереди сообщений:
    * BEGIN
    * utl_send_mail.send_scheduled_mail;
    * END;
    */
IS
    smtp_host VARCHAR2 (256) := '192.168.90.234';
    smtp_port PLS_INTEGER := 25;
    smtp_domain    VARCHAR2 (256) := 'smtp_host';
    MAILER_ID      CONSTANT VARCHAR2 (256) := 'Mailer by Oracle UTL_SMTP';
    boundary       CONSTANT VARCHAR2 (256) := '-----7D81B75CCC90D2974F7A1CBD';
    first_boundary CONSTANT VARCHAR2 (256) := '--' || BOUNDARY || utl_tcp.CRLF;
    last_boundary  CONSTANT VARCHAR2 (256) := '--' || BOUNDARY || '--' || utl_tcp.CRLF;
    -- A MIME type that denotes multi-part email (MIME) messages.
    max_base64_line_width CONSTANT PLS_INTEGER := 76 / 4 * 3;
    multipart_mime_type   CONSTANT VARCHAR2 (256) := 'multipart/mixed; boundary="'|| boundary || '"';
    html_mime_type        CONSTANT VARCHAR2 (256) := 'text/html; charset=UTF-8';
    /**
    * Процедура отправки почты.
    * @param sender отпарвитель
    * @param recipients получатели
    * @param subject тема сообщения
    * @param MESSAGE тело сообщения
    * @param attachments массив вложений
    */

PROCEDURE send_mail (
        sender IN VARCHAR2,
        recipients IN VARCHAR2,
        subject IN VARCHAR2,
        MESSAGE IN CLOB,
        attachments IN attachments_t DEFAULT NULL) ;
    /**
    * Процедура отправки почты.
    * @param sender отпарвитель
    * @param recipients получатели
    * @param subject тема сообщения
    * @param MESSAGE тело сообщения
    * @param attachment вложение
    */

PROCEDURE send_mail (
        sender IN VARCHAR2,
        recipients IN VARCHAR2,
        subject IN VARCHAR2,
        MESSAGE IN CLOB,
        attachment IN attachment_t DEFAULT NULL) ;
    /**
    * Процедура отправки почты.
    * @param sender отпарвитель
    * @param recipients получатели
    * @param subject тема сообщения
    * @param MESSAGE тело сообщения
    * @param attachment_name наименование файла вложения
    * @param attachment_content файл вложения
    */

PROCEDURE send_mail (
        sender IN VARCHAR2,
        recipients IN VARCHAR2,
        subject IN VARCHAR2,
        MESSAGE IN CLOB,
        attachment_name IN VARCHAR2 DEFAULT NULL,
        attachment_content IN BLOB DEFAULT NULL) ;
    /**
    * Процедура отправки почты с использованием очереди сообщений.
    * Добавляет запись в таблицу MAIL_MESSAGES сообщение, а также вложения в таблицу MAIL_ATTACHMENTS
    * из которых в последствии производится отправка сообщений процедурой send_scheduled_mail.
    * Сообщения удаляются из очереди после их успешной отправки.
    * Существует возможность отложить отпраку сообщения до поределенного момента путем заполнения параметра send_date.
    * @param sender отпарвитель
    * @param recipients получатели
    * @param subject тема сообщения
    * @param MESSAGE тело сообщения
    * @param send_date дата отпаравки
    * @param attachments массив вложений
    */

PROCEDURE schedule_mail (
        sender IN VARCHAR2,
        recipients IN VARCHAR2,
        subject IN VARCHAR2,
        MESSAGE IN CLOB,
        send_date IN DATE DEFAULT sysdate,
        attachments IN attachments_t DEFAULT NULL) ;
    /**
    * Процедура отправки почты с использованием очереди сообщений.
    * Добавляет запись в таблицу MAIL_MESSAGES сообщение, а также вложения в таблицу MAIL_ATTACHMENTS
    * из которых в последствии производится отправка сообщений процедурой send_scheduled_mail.
    * Сообщения удаляются из очереди после их успешной отправки.
    * Существует возможность отложить отпраку сообщения до поределенного момента путем заполнения параметра send_date.
    * @param sender отпарвитель
    * @param recipients получатели
    * @param subject тема сообщения
    * @param MESSAGE тело сообщения
    * @param send_date дата отпаравки
    * @param attachment вложение
    */

PROCEDURE schedule_mail (
        sender IN VARCHAR2,
        recipients IN VARCHAR2,
        subject IN VARCHAR2,
        MESSAGE IN CLOB,
        send_date IN DATE DEFAULT sysdate,
        attachment IN attachment_t DEFAULT NULL) ;
    /**
    * Процедура отправки почты с использованием очереди сообщений.
    * Добавляет запись в таблицу MAIL_MESSAGES сообщение, а также вложения в таблицу MAIL_ATTACHMENTS
    * из которых в последствии производится отправка сообщений процедурой send_scheduled_mail.
    * Сообщения удаляются из очереди после их успешной отправки.
    * Существует возможность отложить отпраку сообщения до поределенного момента путем заполнения параметра send_date.
    * @param sender отпарвитель
    * @param recipients получатели
    * @param subject тема сообщения
    * @param MESSAGE тело сообщения
    * @param send_date дата отпаравки
    * @param attachment_name наименование файла вложения
    * @param attachment_content файл вложения
    */

PROCEDURE schedule_mail (
        sender IN VARCHAR2,
        recipients IN VARCHAR2,
        subject IN VARCHAR2,
        MESSAGE IN CLOB,
        send_date IN DATE DEFAULT sysdate,
        attachment_name IN VARCHAR2 DEFAULT NULL,
        attachment_content IN BLOB DEFAULT NULL) ;
    /**
    * Процедура отправки сообщений из очереди.
    */

PROCEDURE send_scheduled_mail;
    /**
    * API для непосредственной работы с сообщением.
    * Создание почтового сообщения
    * @return utl_smtp.connection
    * @param sender отправитель
    * @param recipients получатели
    * @param subject тема сообщения
    * @param mime_type MIME тип сообщения
    * @param priority приоритет сообщения
    */
    
    FUNCTION begin_mail (
            sender IN VARCHAR2,
            recipients IN VARCHAR2,
            subject IN VARCHAR2,
            mime_type IN VARCHAR2 DEFAULT multipart_mime_type,
            priority IN PLS_INTEGER DEFAULT NULL)
        RETURN utl_smtp.connection;
        /**
        * API для непосредственной работы с сообщением.
        * Добавление тела сообщения.
        * Данный вызов доглжен быть сделан перед добавлением вложений.
        * @param conn соединение которое возвратила функция begin_mail
        * @param data тело сообщения
        */
    
    PROCEDURE attach_body (
            conn IN OUT NOCOPY utl_smtp.connection,
            data IN CLOB) ;
        /**
        * API для непосредственной работы с сообщением.
        * Добавление вложения.
        * Данный вызов доглжен быть сделан после добавлением тела сообщения.
        * @param conn соединение которое возвратила функция begin_mail
        * @param data BLOB с файлом вложения
        * @param mime_type MIME тип сообщения
        * @param inline будет ли файл добавлен как вложение или непосредственно в тело письма
        * @param filename наименование файла вложения
        * @param last является ли сообщение последним
        */
    
    PROCEDURE attach_blob (
            conn IN OUT NOCOPY utl_smtp.connection,
            data IN BLOB,
            mime_type IN VARCHAR2 DEFAULT 'application/octet',
            inline IN BOOLEAN DEFAULT true,
            filename IN VARCHAR2 DEFAULT NULL,
            last IN BOOLEAN DEFAULT FALSE) ;
        /**
        * API для непосредственной работы с сообщением.
        * Завершение и отправка сообщения.
        * @param conn соединение которое возвратила функция begin_mail
        */
    
    PROCEDURE end_mail (
            conn IN OUT NOCOPY utl_smtp.connection) ;
    
    END;

/
--------------------------------------------------------
--  DDL for Package Body UTL_SEND_MAIL
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE BODY "UTL"."UTL_SEND_MAIL" 
IS

FUNCTION encode (
        p_text VARCHAR2)
    RETURN VARCHAR2
IS
    res VARCHAR2 (32000) ;
BEGIN
    res := CONVERT (p_text, 'CL8MSWIN1251') ;
    res := UTL_RAW.cast_to_varchar2 (UTL_ENCODE.base64_encode (UTL_RAW.cast_to_raw (res))) ;
    res := ' =?windows-1251?B?' || res || '?= ';
    
    RETURN res;

END;
-- Return the next email address in the list of email addresses, separated
-- by either a "," or a ";".  The format of mailbox may be in one of these:
--   someone@some-domain
--   "Someone at some domain" <someone@some-domain>
--   Someone at some domain <someone@some-domain>

FUNCTION get_address (
        addr_list IN OUT VARCHAR2)
    RETURN VARCHAR2
IS
    addr VARCHAR2 (256) ;
    i pls_integer;

FUNCTION lookup_unquoted_char (
        str IN VARCHAR2,
        chrs IN VARCHAR2)
    RETURN pls_integer
AS
    c VARCHAR2 (5) ;
    i pls_integer;
    LEN pls_integer;
    inside_quote BOOLEAN;
BEGIN
    inside_quote := false;
    i := 1;
    LEN := LENGTH (str) ;
    
    WHILE (i <= LEN)
    LOOP
        c := SUBSTR (str, i, 1) ;
        
        IF (inside_quote) THEN
            
            IF (c = '"') THEN
                inside_quote := false;
            
            ELSIF (c = '\') THEN
                i := i + 1; -- Skip the quote character
            
            END IF;
            GOTO next_char;
        
        END IF;
        
        IF (c = '"') THEN
            inside_quote := true;
            GOTO next_char;
        
        END IF;
        
        IF (instr (chrs, c) >= 1) THEN
            
            RETURN i;
        
        END IF;
        << next_char >> i := i + 1;
    
    END LOOP;
    
    RETURN 0;

END;
BEGIN
    addr_list := ltrim (addr_list) ;
    i := lookup_unquoted_char (addr_list, ',;') ;
    
    IF (i >= 1) THEN
        addr := SUBSTR (addr_list, 1, i - 1) ;
        addr_list := SUBSTR (addr_list, i + 1) ;
    
    ELSE
        addr := addr_list;
        addr_list := '';
    
    END IF;
    i := lookup_unquoted_char (addr, '<') ;
    
    IF (i >= 1) THEN
        addr := SUBSTR (addr, i + 1) ;
        i := instr (addr, '>') ;
        
        IF (i >= 1) THEN
            addr := SUBSTR (addr, 1, i - 1) ;
        
        END IF;
    
    END IF;
    
    RETURN addr;

END;

PROCEDURE write_mime_header (
        conn IN OUT NOCOPY utl_smtp.connection,
        name IN VARCHAR2,
        value IN VARCHAR2)
IS
BEGIN
    utl_smtp.write_data (conn, name || ': ' || value || utl_tcp.CRLF) ;

END;

PROCEDURE write_raw (
        conn IN OUT NOCOPY utl_smtp.connection,
        MESSAGE IN RAW)
IS
BEGIN
    utl_smtp.write_raw_data (conn, MESSAGE) ;

END;

PROCEDURE write_text (
        conn IN OUT NOCOPY utl_smtp.connection,
        data IN VARCHAR2)
IS
BEGIN
    write_raw (conn, utl_raw.cast_to_raw (data)) ;

END;

FUNCTION begin_session
    RETURN utl_smtp.connection
IS
    conn utl_smtp.connection;
BEGIN
    -- open SMTP connection
    conn := utl_smtp.open_connection (smtp_host, smtp_port) ;
    utl_smtp.helo (conn, smtp_domain) ;
    
    RETURN conn;

END;

PROCEDURE end_mail_in_session (
        conn IN OUT NOCOPY utl_smtp.connection)
IS
BEGIN
    utl_smtp.close_data (conn) ;

END;

PROCEDURE end_session (
        conn IN OUT NOCOPY utl_smtp.connection)
IS
BEGIN
    utl_smtp.quit (conn) ;

END;

PROCEDURE end_mail (
        conn IN OUT NOCOPY utl_smtp.connection)
IS
BEGIN
    end_mail_in_session (conn) ;
    end_session (conn) ;

END;

PROCEDURE begin_mail_in_session (
        conn IN OUT NOCOPY utl_smtp.connection,
        sender IN VARCHAR2,
        recipients IN VARCHAR2,
        subject IN VARCHAR2,
        mime_type IN VARCHAR2 DEFAULT 'text/plain',
        priority IN PLS_INTEGER DEFAULT NULL)
IS
    my_recipients VARCHAR2 (32767) := recipients;
    my_sender     VARCHAR2 (32767) := sender;
BEGIN
    -- Specify sender's address (our server allows bogus address
    -- as long as it is a full email address (xxx@yyy.com).
    utl_smtp.mail (conn, get_address (my_sender)) ;
    -- Specify recipient(s) of the email.
    
    WHILE (my_recipients IS NOT NULL)
    LOOP
        utl_smtp.rcpt (conn, get_address (my_recipients)) ;
    
    END LOOP;
    -- Start body of email
    utl_smtp.open_data (conn) ;
    -- Set "From" MIME header
    write_mime_header (conn, 'From', sender) ;
    -- Set "To" MIME header
    write_mime_header (conn, 'To', recipients) ;
    -- Set "Subject" MIME header
    write_mime_header (conn, 'Subject', encode (subject)) ;
    -- Set "Content-Type" MIME header
    write_mime_header (conn, 'Content-Type', mime_type) ;
    -- Set "X-Mailer" MIME header
    write_mime_header (conn, 'X-Mailer', MAILER_ID) ;
    -- Set priority:
    --   High      Normal       Low
    --   1     2     3     4     5
    
    IF (priority IS NOT NULL) THEN
        write_mime_header (conn, 'X-Priority', priority) ;
    
    END IF;
    -- Send an empty line to denotes end of MIME headers and
    -- beginning of message body.
    utl_smtp.write_data (conn, utl_tcp.CRLF) ;
    
    IF (mime_type LIKE 'multipart/mixed%') THEN
        write_text (conn, 'This is a multi-part message in MIME format.' || utl_tcp.crlf) ;
    
    END IF;

END;

PROCEDURE write_boundary (
        conn IN OUT NOCOPY utl_smtp.connection,
        last IN BOOLEAN DEFAULT FALSE)
AS
BEGIN
    
    IF (last) THEN
        utl_smtp.write_data (conn, LAST_BOUNDARY) ;
    
    ELSE
        utl_smtp.write_data (conn, FIRST_BOUNDARY) ;
    
    END IF;

END;
--PROCEDURE write_mb_text (
--        conn IN OUT NOCOPY utl_smtp.connection,
--        MESSAGE IN VARCHAR2)
--IS
--BEGIN
--    utl_smtp.write_raw_data (conn, utl_raw.cast_to_raw (MESSAGE)) ;
--END;
--PROCEDURE attach_text (
--        conn IN OUT NOCOPY utl_smtp.connection,
--        data IN VARCHAR2,
--        mime_type IN VARCHAR2 DEFAULT 'text/plain',
--        inline IN BOOLEAN DEFAULT TRUE,
--        filename IN VARCHAR2 DEFAULT NULL,
--        last IN BOOLEAN DEFAULT FALSE)
--IS
--BEGIN
--    begin_attachment (conn, mime_type, inline, filename) ;
--    --    write_text (conn, convert (data, 'utf8')) ;
--    write_raw (conn, utl_raw.cast_to_raw (data)) ;
--    --    write_text (conn, data) ;
--    --    write_text (conn, encode (data)) ;
--    end_attachment (conn, last) ;
--
--END;
--PROCEDURE write_body (
--        conn IN OUT NOCOPY utl_smtp.connection,
--        data IN VARCHAR2)
--IS
--BEGIN
--    --begin_attachment (conn, mime_type, true, NULL) ;
--    write_raw (conn, utl_raw.cast_to_raw (data)) ;
--    --    end_attachment (conn, last) ;
--
--END;

PROCEDURE begin_attachment (
        conn IN OUT NOCOPY utl_smtp.connection,
        mime_type IN VARCHAR2 DEFAULT 'text/plain',
        inline IN BOOLEAN DEFAULT TRUE,
        filename IN VARCHAR2 DEFAULT NULL,
        transfer_enc IN VARCHAR2 DEFAULT NULL)
IS
BEGIN
    write_boundary (conn) ;
    write_mime_header (conn, 'Content-Type', mime_type) ;
    
    IF (filename IS NOT NULL) THEN
        
        IF (inline) THEN
            write_mime_header (conn, 'Content-Disposition', 'inline; filename="'||filename||'"') ;
        
        ELSE
            write_mime_header (conn, 'Content-Disposition', 'attachment; filename="'||filename||'"') ;
        
        END IF;
    
    END IF;
    
    IF (transfer_enc IS NOT NULL) THEN
        write_mime_header (conn, 'Content-Transfer-Encoding', transfer_enc) ;
    
    END IF;
    utl_smtp.write_data (conn, utl_tcp.CRLF) ;

END;

PROCEDURE end_attachment (
        conn IN OUT NOCOPY utl_smtp.connection,
        last IN BOOLEAN DEFAULT FALSE)
IS
BEGIN
    utl_smtp.write_data (conn, utl_tcp.CRLF) ;
    
    IF (last) THEN
        write_boundary (conn, last) ;
    
    END IF;

END;

PROCEDURE begin_body (
        conn IN OUT nocopy utl_smtp.connection,
        mime_type IN VARCHAR2 DEFAULT html_mime_type)
IS
BEGIN
    begin_attachment (conn, mime_type, true, NULL) ;

END;

PROCEDURE end_body (
        conn IN OUT NOCOPY utl_smtp.connection)
IS
BEGIN
    end_attachment (conn, false) ;

END;

PROCEDURE attach_body (
        conn IN OUT NOCOPY utl_smtp.connection,
        data IN CLOB)
IS
    v_raw         VARCHAR2 (57 CHAR) ;
    v_length      INTEGER := 0;
    v_buffer_size INTEGER := 57;
    v_offset      INTEGER := 1;
    attachment attachment_t;
BEGIN
    begin_body (conn) ;
    v_length := dbms_lob.getlength (data) ;
    
    WHILE v_offset < v_length
    LOOP
        dbms_lob.read (data, v_buffer_size, v_offset, v_raw) ;
        write_text (conn, data) ;
        v_offset := v_offset + v_buffer_size;
    
    END LOOP;
    end_body (conn) ;

END;

PROCEDURE attach_blob (
        conn IN OUT NOCOPY utl_smtp.connection,
        data IN BLOB,
        mime_type IN VARCHAR2 DEFAULT 'application/octet',
        inline IN BOOLEAN DEFAULT true,
        filename IN VARCHAR2 DEFAULT NULL,
        last IN BOOLEAN DEFAULT FALSE)
IS
    v_raw raw (57) ;
    v_length      INTEGER := 0;
    v_buffer_size INTEGER := 57;
    v_offset      INTEGER := 1;
BEGIN
    begin_attachment (conn, mime_type, inline, filename, 'base64') ;
    v_length := dbms_lob.getlength (data) ;
    
    WHILE v_offset < v_length
    LOOP
        dbms_lob.read (data, v_buffer_size, v_offset, v_raw) ;
        utl_smtp.write_raw_data (conn, utl_encode.base64_encode (v_raw)) ;
        utl_smtp.write_data (conn, utl_tcp.crlf) ;
        v_offset := v_offset + v_buffer_size;
    
    END LOOP;
    end_attachment (conn, last) ;

END;

PROCEDURE attach_base64 (
        conn IN OUT NOCOPY utl_smtp.connection,
        data IN RAW,
        mime_type IN VARCHAR2 DEFAULT 'application/octet',
        inline IN BOOLEAN DEFAULT TRUE,
        filename IN VARCHAR2 DEFAULT NULL,
        last IN BOOLEAN DEFAULT FALSE)
IS
    i PLS_INTEGER;
    LEN PLS_INTEGER;
BEGIN
    begin_attachment (conn, mime_type, inline, filename, 'base64') ;
    -- Split the Base64-encoded attachment into multiple lines
    i := 1;
    LEN := utl_raw.length (data) ;
    
    WHILE (i < LEN)
    LOOP
        
        IF (i + max_base64_line_width < LEN) THEN
            utl_smtp.write_data (conn, utl_encode.base64_encode (utl_raw.substr (data, i, MAX_BASE64_LINE_WIDTH))) ;
        
        ELSE
            utl_smtp.write_data (conn, utl_encode.base64_encode (utl_raw.substr (data, i))) ;
        
        END IF;
        utl_smtp.write_data (conn, utl_tcp.CRLF) ;
        i := i + max_base64_line_width;
    
    END LOOP;
    end_attachment (conn, last) ;

END;

FUNCTION begin_mail (
        sender IN VARCHAR2,
        recipients IN VARCHAR2,
        subject IN VARCHAR2,
        mime_type IN VARCHAR2 DEFAULT multipart_mime_type,
        priority IN PLS_INTEGER DEFAULT NULL)
    RETURN utl_smtp.connection
IS
    conn utl_smtp.connection;
BEGIN
    conn := begin_session;
    begin_mail_in_session (conn, sender, recipients, subject, mime_type, priority) ;
    
    RETURN conn;

END;

PROCEDURE send_mail (
        sender IN VARCHAR2,
        recipients IN VARCHAR2,
        subject IN VARCHAR2,
        MESSAGE IN CLOB,
        attachments IN attachments_t DEFAULT NULL)
IS
    conn utl_smtp.connection;
    v_raw         VARCHAR2 (57) ;
    v_length      INTEGER := 0;
    v_buffer_size INTEGER := 57;
    v_offset      INTEGER := 1;
    attachment attachment_t;
BEGIN
    conn := begin_mail (sender => sender, recipients=> recipients, subject=> subject) ;
    attach_body (conn =>conn, data => MESSAGE) ;
    
    IF attachments IS NOT NULL AND attachments.last > 0 THEN
        
        FOR i IN attachments.first .. attachments.last
        LOOP
            attachment := attachments (i) ;
            attach_blob (conn=>conn, data => attachment.file_content, filename=> attachment.file_name) ;
        
        END LOOP;
    
    END IF;
    end_mail (conn) ;

END;

PROCEDURE send_mail (
        sender IN VARCHAR2,
        recipients IN VARCHAR2,
        subject IN VARCHAR2,
        MESSAGE IN CLOB,
        attachment IN attachment_t DEFAULT NULL)
IS
BEGIN
    
    IF attachment IS NOT NULL THEN
        send_mail (sender => sender, recipients=> recipients, subject=> subject, MESSAGE =>MESSAGE, attachments =>attachments_t (attachment
        )) ;
    
    ELSE
        send_mail (sender => sender, recipients=> recipients, subject=> subject, MESSAGE =>MESSAGE, attachments =>NULL) ;
    
    END IF;

END;

PROCEDURE send_mail (
        sender IN VARCHAR2,
        recipients IN VARCHAR2,
        subject IN VARCHAR2,
        MESSAGE IN CLOB,
        attachment_name IN VARCHAR2 DEFAULT NULL,
        attachment_content IN BLOB DEFAULT NULL)
IS
BEGIN
    
    IF attachment_name IS NOT NULL AND attachment_content IS NOT NULL THEN
        send_mail (sender => sender, recipients=> recipients, subject=> subject, MESSAGE =>MESSAGE, attachments =>attachments_t (
        attachment_t (attachment_name, attachment_content))) ;
    
    ELSE
        send_mail (sender => sender, recipients=> recipients, subject=> subject, MESSAGE =>MESSAGE, attachments =>NULL) ;
    
    END IF;

END;

PROCEDURE schedule_mail (
        sender IN VARCHAR2,
        recipients IN VARCHAR2,
        subject IN VARCHAR2,
        MESSAGE IN CLOB,
        send_date IN DATE DEFAULT sysdate,
        attachments IN attachments_t DEFAULT NULL)
IS
    PRAGMA AUTONOMOUS_TRANSACTION;
    n_id mail_messages.id%type;
    attachment attachment_t;
BEGIN
    
    SELECT MAIL_MESSAGES_ID_SEQ.nextval INTO n_id FROM dual;
    
    INSERT
    INTO UTL.MAIL_MESSAGES
        (
            id, sender, recipients, body, send_date, subject
        )
        VALUES
        (
            n_id, sender, recipients, MESSAGE, send_date, subject
        ) ;
    
    IF attachments IS NOT NULL AND attachments.last > 0 THEN
        
        FOR i IN attachments.first .. attachments.last
        LOOP
            attachment := attachments
            (
                i
            )
            ;
            
            INSERT
            INTO UTL.MAIL_ATTACHMENTS
                (
                    MESSAGE_ID, CONTENT, NAME
                )
                VALUES
                (
                    n_id, attachment.file_content, attachment.file_name
                ) ;
        
        END LOOP;
    
    END IF;
    COMMIT;

END;

PROCEDURE schedule_mail
    (
        sender IN VARCHAR2,
        recipients IN VARCHAR2,
        subject IN VARCHAR2,
        MESSAGE IN CLOB,
        send_date IN DATE DEFAULT sysdate,
        attachment IN attachment_t DEFAULT NULL
    )
IS
BEGIN
    
    IF attachment IS NOT NULL THEN
        schedule_mail (sender => sender, recipients => recipients, subject=>subject, MESSAGE=>MESSAGE, send_date=>send_date, attachments =>
        attachments_t (attachment)) ;
    
    ELSE
        schedule_mail (sender => sender, recipients => recipients, subject=>subject, MESSAGE=>MESSAGE, send_date=>send_date, attachments =>
        NULL) ;
    
    END IF;

END ;

PROCEDURE schedule_mail
    (
        sender IN VARCHAR2,
        recipients IN VARCHAR2,
        subject IN VARCHAR2,
        MESSAGE IN CLOB,
        send_date IN DATE DEFAULT sysdate,
        attachment_name IN VARCHAR2 DEFAULT NULL,
        attachment_content IN BLOB DEFAULT NULL
    )
IS
BEGIN
    
    IF attachment_name IS NOT NULL AND attachment_content IS NOT NULL THEN
        schedule_mail (sender => sender, recipients => recipients, subject=>subject, MESSAGE=>MESSAGE, send_date=>send_date, attachments =>
        attachments_t (attachment_t (attachment_name, attachment_content))) ;
    
    ELSE
        schedule_mail (sender => sender, recipients => recipients, subject=>subject, MESSAGE=>MESSAGE, send_date=>send_date, attachments =>
        NULL) ;
    
    END IF;

END ;

PROCEDURE send_scheduled_mail
IS
    attachments attachments_t ;
BEGIN
    
    FOR i IN
    (SELECT id, sender, recipients, body, send_date, subject FROM mail_messages WHERE send_date <= sysdate
    )
    LOOP
        attachments := attachments_t
        (
        )
        ;
        
        FOR j IN
        (SELECT message_id, content, name FROM mail_attachments WHERE message_id = i.id
        )
        LOOP
            attachments.extend;
            attachments (attachments.last) := attachment_t (j.name, j.content) ;
        
        END LOOP;
        BEGIN
            send_mail (sender => i.sender, recipients => i.recipients, subject=> i.subject, MESSAGE => i.body, attachments => attachments)
            ;
        
        EXCEPTION
        
        WHEN OTHERS THEN
            -- TODO анализ ошибок от SMPT сервера и их обработка
            DELETE FROM mail_messages WHERE id = i.id;
            COMMIT;
            RAISE;
        
        END;
        
        DELETE FROM mail_messages WHERE id = i.id;
        COMMIT;
    
    END LOOP;

END;

END;

/



