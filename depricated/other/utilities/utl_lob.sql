--------------------------------------------------------
--  DDL for Package UTL_LOB
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "ISIN"."UTL_LOB" 
AS
   --исключения
   no_data_in_blob EXCEPTION; --передан пустой блоб
   error_process_blob EXCEPTION; --ошибка обработки блоба

ch_set         CONSTANT VARCHAR2 (16) := 'UTF8';

   gv_Dir_Name   CONSTANT VARCHAR2 (1000) := '/u01/app/oracle/logs/';

   TYPE Array_Varchar IS TABLE OF VARCHAR2 (2000)
                            INDEX BY BINARY_INTEGER;

   FUNCTION BlobToClob (Src BLOB)
      RETURN CLOB; --для конверта blob -> clob

   PROCEDURE WRITE_LOB (av_filename IN VARCHAR2, ab_data IN BLOB); --записывает файл в
                                                                   --директрии UTL_FILE_DIR (gv_Dir_Name)
                                                                   -- из блоба

   FUNCTION get_str_from_blob (ab_str_data BLOB)
      RETURN Array_Varchar; --разбивает на массив строк блоб

   FUNCTION get_str_from_blob (ab_str_data BLOB, as_charset_in VARCHAR2, as_charset_out VARCHAR2)
      RETURN Array_Varchar;

   --разбивает на массив строк блоб
   --возвращает в кодировке


   PROCEDURE WRITE_CLOB (av_filename IN VARCHAR2, ab_data IN CLOB); --записывает файл в

   --директрии UTL_FILE_DIR (gv_Dir_Name)
   -- из блоба
   FUNCTION get_str_from_clob (ab_str_data CLOB)
      RETURN Array_Varchar; --разбивает на массив строк блоб

   FUNCTION get_str_from_clob (ab_str_data CLOB, as_charset_in VARCHAR2, as_charset_out VARCHAR2)
      RETURN Array_Varchar;

   --разбивает на массив строк блоб
   --возвращает в кодировке

   FUNCTION ClobToBlob (Src CLOB)
      RETURN BLOB;
END UTL_LOB;

/
--------------------------------------------------------
--  DDL for Package Body UTL_LOB
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE BODY "ISIN"."UTL_LOB" 
AS
   /***************************************************************************
   * Конвертит полученный BLOB в удобоваримый для дальнейшей работы CLOB
   *
   ***************************************************************************/
   FUNCTION BlobToClob (Src BLOB)
      RETURN CLOB
   IS
      lb_Result   CLOB;
      iLen        INTEGER := DBMS_LOB.lobmaxsize;
      iSrcOffs    INTEGER := 1;
      iDstOffs    INTEGER := 1;
      iLang       INTEGER := DBMS_LOB.default_lang_ctx;
      iWarn       INTEGER;
   BEGIN
      IF NOT ( (Src IS NULL) OR (DBMS_LOB.GetLength (Src) = 0))
      THEN
         DBMS_LOB.CreateTemporary (lb_Result, TRUE);
         DBMS_LOB.ConvertToClob (lb_Result, Src, iLen, iSrcOffs, iDstOffs, NLS_CHARSET_ID (CH_SET), iLang, iWarn);
      ELSE
         lb_Result := NULL;
      END IF;

      RETURN (lb_Result);
   END BlobToClob;

   /***************************************************************************
   * Конвертит полученный BLOB в удобоваримый для дальнейшей работы CLOB
   *
   ***************************************************************************/
   FUNCTION ClobToBlob (Src CLOB)
      RETURN BLOB
   IS
      lb_Result   BLOB;
      iLen        INTEGER := DBMS_LOB.lobmaxsize;
      iSrcOffs    INTEGER := 1;
      iDstOffs    INTEGER := 1;
      iLang       INTEGER := DBMS_LOB.default_lang_ctx;
      iWarn       INTEGER;
   BEGIN
      IF NOT ( (Src IS NULL) OR (DBMS_LOB.GetLength (Src) = 0))
      THEN
         DBMS_LOB.CreateTemporary (lb_Result, TRUE);
         DBMS_LOB.converttoblob (lb_Result, Src, iLen, iSrcOffs, iDstOffs, NLS_CHARSET_ID (CH_SET), iLang, iWarn);
      ELSE
         lb_Result := NULL;
      END IF;

      RETURN (lb_Result);
   END ClobToBlob;

   /**********************************************************************************************/


   /**********************************************************************************************/
   FUNCTION get_str_from_clob (ab_str_data CLOB, as_charset_in VARCHAR2, as_charset_out VARCHAR2)
      RETURN Array_Varchar
   IS
      --счётчики
      i          BINARY_INTEGER;
      j          BINARY_INTEGER;
      k          BINARY_INTEGER;

      li_len     BINARY_INTEGER; --длина блоба
      li_loops   BINARY_INTEGER; --кол-во циклов
      arr_out    Array_Varchar; --возвращаемый массив
      lv_temp    VARCHAR2 (32767); --для чтения блоба
      li_pos     BINARY_INTEGER; --позиция в блобе
      lc_char    VARCHAR2 (4);
      lv_str     VARCHAR2 (2000); --для строки
   BEGIN
      IF ab_str_data IS NULL
      THEN
         --нет данных
         RAISE no_data_in_blob;
         RETURN arr_out;
      END IF;

      li_len := DBMS_LOB.getlength (ab_str_data); --получить длину блоба
      li_loops := CEIL (li_len / 15000);
      k := 1; --счётчик для возвращаемого массива
      lv_str := ''; --стереть строку

      --открыть цикл по блобу
      FOR j IN 1 .. li_loops
      LOOP
         --взять часть в temp и сконвертить
         li_pos := j * 15000 - 14999;
         lv_temp :=
            UTL_RAW.cast_to_varchar2(UTL_RAW.CONVERT (
                                        DBMS_LOB.SUBSTR (ab_str_data, 15000, li_pos)
                                       ,'RUSSIAN_CIS.' || as_charset_out
                                       ,'RUSSIAN_CIS.' || as_charset_in
                                     ));

         --разобрать по строкам lv_temp

         FOR i IN 1 .. LENGTH (lv_Temp)
         LOOP
            lc_char := SUBSTR (lv_Temp, i, 1); --по символьно

            IF lc_char = CHR (13) OR lc_char = CHR (14806029)
            THEN
               --новая строка Cr (0D) - записать в массив, дать новый индекс
               --      If substr(lv_Temp, i - 1, 1) <> chr(13) and i > 1 Then
               --предыдущий был не 0D
               arr_out (k) := RTRIM (lv_str);
               k := k + 1;
               lv_str := ''; --стереть строку
            --       End If;
            ELSE
               IF lc_char <> CHR (10)
               THEN
                  --не Lf (0A)
                  lv_str := lv_str || lc_char;
               END IF;
            END IF;
         END LOOP;
      END LOOP;

      arr_out (k) := RTRIM (lv_str); -- последняя
      --arr_out(k) := ''; -- последняя
      RETURN (arr_out);
   END get_str_from_clob;

   /**********************************************************************************************/

   FUNCTION get_str_from_clob (ab_str_data CLOB)
      RETURN Array_Varchar
   IS
      --счётчики
      i          BINARY_INTEGER;
      j          BINARY_INTEGER;
      k          BINARY_INTEGER;

      li_len     BINARY_INTEGER; --длина блоба
      li_loops   BINARY_INTEGER; --кол-во циклов
      arr_out    Array_Varchar; --возвращаемый массив
      lv_temp    VARCHAR2 (32767); --для чтения блоба
      li_pos     BINARY_INTEGER; --позиция в блобе
      lc_char    VARCHAR2 (4);
      lv_str     VARCHAR2 (2000); --для строки
   BEGIN
      IF ab_str_data IS NULL
      THEN
         --нет данных
         RAISE no_data_in_blob;
         RETURN arr_out;
      END IF;

      li_len := DBMS_LOB.getlength (ab_str_data); --получить длину блоба
      li_loops := CEIL (li_len / 32767);
      k := 1; --счётчик для возвращаемого массива

      --открыть цикл по блобу
      FOR j IN 1 .. li_loops
      LOOP
         --взять часть в temp
         li_pos := j * 32767 - 32766;
         lv_temp := UTL_RAW.cast_to_varchar2 (DBMS_LOB.SUBSTR (ab_str_data, 32767, li_pos));

         --разобрать по строкам lv_temp
         FOR i IN 1 .. LENGTH (lv_Temp)
         LOOP
            lc_char := SUBSTR (lv_Temp, i, 1); --по символьно

            IF lc_char = CHR (13)
            THEN
               --новая строка Cr (0D) - записать в массив, дать новый индекс
               arr_out (k) := lv_str;
               k := k + 1;
               lv_str := ''; --стереть строку
            ELSE
               IF lc_char <> CHR (10)
               THEN
                  --не Lf (0A)
                  lv_str := lv_str || lc_char;
               END IF;
            END IF;
         END LOOP;
      END LOOP;

      arr_out (k) := lv_str; -- последняя
      RETURN (arr_out);
   END get_str_from_clob;

   /**********************************************************************************************/


   /**********************************************************************************************/
   FUNCTION get_str_from_blob (ab_str_data BLOB, as_charset_in VARCHAR2, as_charset_out VARCHAR2)
      RETURN Array_Varchar
   IS
      --счётчики
      i          BINARY_INTEGER;
      j          BINARY_INTEGER;
      k          BINARY_INTEGER;

      li_len     BINARY_INTEGER; --длина блоба
      li_loops   BINARY_INTEGER; --кол-во циклов
      arr_out    Array_Varchar; --возвращаемый массив
      lv_temp    VARCHAR2 (32767); --для чтения блоба
      li_pos     BINARY_INTEGER; --позиция в блобе
      lc_char    VARCHAR2 (4);
      lv_str     VARCHAR2 (2000); --для строки
   BEGIN
      IF ab_str_data IS NULL
      THEN
         --нет данных
         RAISE no_data_in_blob;
         RETURN arr_out;
      END IF;

      li_len := DBMS_LOB.getlength (ab_str_data); --получить длину блоба
      li_loops := CEIL (li_len / 15000);
      k := 1; --счётчик для возвращаемого массива
      lv_str := ''; --стереть строку

      --открыть цикл по блобу
      FOR j IN 1 .. li_loops
      LOOP
         --взять часть в temp и сконвертить
         li_pos := j * 15000 - 14999;
         lv_temp :=
            UTL_RAW.cast_to_varchar2(UTL_RAW.CONVERT (
                                        DBMS_LOB.SUBSTR (ab_str_data, 15000, li_pos)
                                       ,'RUSSIAN_CIS.' || as_charset_out
                                       ,'RUSSIAN_CIS.' || as_charset_in
                                     ));

         --разобрать по строкам lv_temp

         FOR i IN 1 .. LENGTH (lv_Temp)
         LOOP
            lc_char := SUBSTR (lv_Temp, i, 1); --по символьно

            IF lc_char = CHR (13) OR lc_char = CHR (14806029)
            THEN
               --новая строка Cr (0D) - записать в массив, дать новый индекс
               --      If substr(lv_Temp, i - 1, 1) <> chr(13) and i > 1 Then
               --предыдущий был не 0D
               arr_out (k) := RTRIM (lv_str);
               k := k + 1;
               lv_str := ''; --стереть строку
            --       End If;
            ELSE
               IF lc_char <> CHR (10)
               THEN
                  --не Lf (0A)
                  lv_str := lv_str || lc_char;
               END IF;
            END IF;
         END LOOP;
      END LOOP;

      arr_out (k) := RTRIM (lv_str); -- последняя
      --arr_out(k) := ''; -- последняя
      RETURN (arr_out);
   END get_str_from_blob;

   /**********************************************************************************************/

   FUNCTION get_str_from_blob (ab_str_data BLOB)
      RETURN Array_Varchar
   IS
      --счётчики
      i          BINARY_INTEGER;
      j          BINARY_INTEGER;
      k          BINARY_INTEGER;

      li_len     BINARY_INTEGER; --длина блоба
      li_loops   BINARY_INTEGER; --кол-во циклов
      arr_out    Array_Varchar; --возвращаемый массив
      lv_temp    VARCHAR2 (32767); --для чтения блоба
      li_pos     BINARY_INTEGER; --позиция в блобе
      lc_char    VARCHAR2 (4);
      lv_str     VARCHAR2 (2000); --для строки
   BEGIN
      IF ab_str_data IS NULL
      THEN
         --нет данных
         RAISE no_data_in_blob;
         RETURN arr_out;
      END IF;

      li_len := DBMS_LOB.getlength (ab_str_data); --получить длину блоба
      li_loops := CEIL (li_len / 32767);
      k := 1; --счётчик для возвращаемого массива

      --открыть цикл по блобу
      FOR j IN 1 .. li_loops
      LOOP
         --взять часть в temp
         li_pos := j * 32767 - 32766;
         lv_temp := UTL_RAW.cast_to_varchar2 (DBMS_LOB.SUBSTR (ab_str_data, 32767, li_pos));

         --разобрать по строкам lv_temp
         FOR i IN 1 .. LENGTH (lv_Temp)
         LOOP
            lc_char := SUBSTR (lv_Temp, i, 1); --по символьно

            IF lc_char = CHR (13)
            THEN
               --новая строка Cr (0D) - записать в массив, дать новый индекс
               arr_out (k) := lv_str;
               k := k + 1;
               lv_str := ''; --стереть строку
            ELSE
               IF lc_char <> CHR (10)
               THEN
                  --не Lf (0A)
                  lv_str := lv_str || lc_char;
               END IF;
            END IF;
         END LOOP;
      END LOOP;

      arr_out (k) := lv_str; -- последняя
      RETURN (arr_out);
   END get_str_from_blob;

   /**********************************************************************************************/
   PROCEDURE WRITE_LOB (av_filename IN VARCHAR2, ab_data IN BLOB)
   IS
      lv_open_mode   CONSTANT VARCHAR2 (2) := 'w';
      i              BINARY_INTEGER;
      li_count       INTEGER;
      l_array        Array_Varchar;
      l_fnum         UTL_FILE.FILE_TYPE; --указатель на файл
   BEGIN
      IF ab_data IS NULL
      THEN
         --нет данных
         RAISE no_data_in_blob;
         RETURN;
      END IF;

      l_fnum := UTL_FILE.FOPEN (gv_Dir_Name, av_filename, lv_open_mode, 32767);
      --файл открылся
      l_array := get_str_from_blob (ab_data);

      IF l_array IS NULL
      THEN
         RAISE error_process_blob;
      END IF;

      FOR i IN l_array.FIRST .. l_array.LAST
      LOOP
         UTL_FILE.PUT_LINE (l_fnum, l_array (i));
      END LOOP;

      UTL_FILE.FCLOSE (l_fnum);
   EXCEPTION
      WHEN no_data_in_blob
      THEN
         Raise_Application_Error (-20901, 'The Blob Is Null. Передан пустые данные.');
         RETURN;
      WHEN error_process_blob
      THEN
         Raise_Application_Error (-20902, 'Error While Processed Blob. Ошибка обработки(разбивки) данных.');
         RETURN;
      WHEN UTL_FILE.invalid_path
      THEN
         Raise_Application_Error (-20903, 'File Error: Invalid Path. Ошибка файла: неверный путь к файлу.');
         RETURN;
      WHEN UTL_FILE.invalid_filehandle
      THEN
         Raise_Application_Error (-20903, 'File Error: Invalid File Handle. Ошибка файла: неверный указатель на файл.');
         RETURN;
      WHEN UTL_FILE.invalid_operation
      THEN
         Raise_Application_Error (-20903, 'File Error: Invalid File Invalid Operation. Ошибка файла: неверная операция.');
         RETURN;
      WHEN UTL_FILE.read_error
      THEN
         Raise_Application_Error (-20903, 'File Error: Read Error. Ошибка чтения файла.');
         RETURN;
      WHEN UTL_FILE.write_error
      THEN
         Raise_Application_Error (-20903, 'File Error: Write Error. Ошибка записи файла.');
         RETURN;
      WHEN UTL_FILE.internal_error
      THEN
         Raise_Application_Error (-20903, 'File Error: Internal Error. Внутренняя ошибка.');
         RETURN;
   END WRITE_LOB;

   /**********************************************************************************************/

   /**********************************************************************************************/
   PROCEDURE WRITE_CLOB (av_filename IN VARCHAR2, ab_data IN CLOB)
   IS
      lv_open_mode   CONSTANT VARCHAR2 (2) := 'w';
      i              BINARY_INTEGER;
      li_count       INTEGER;
      li_len         INTEGER;
      li_buff        INTEGER;
      l_fnum         UTL_FILE.FILE_TYPE; --указатель на файл
      lv             VARCHAR2 (32767);
   BEGIN
      IF ab_data IS NULL
      THEN
         --нет данных
         RAISE no_data_in_blob;
         RETURN;
      END IF;

      l_fnum := UTL_FILE.FOPEN (gv_Dir_Name, av_filename, lv_open_mode, 32767);
      --файл открылся
      li_len := DBMS_LOB.getlength (ab_data);
      li_count := TRUNC (li_len / 32767) + 1;
      DBMS_OUTPUT.put_line (TO_CHAR (li_len));

      FOR i IN 1 .. li_count
      LOOP
         IF i * 32767 > li_len
         THEN
            --остаток
            li_buff := li_len - 32767 * (i - 1);
         ELSE
            li_buff := 32767;
         END IF;

         DBMS_OUTPUT.put_line (TO_CHAR (i));
         DBMS_OUTPUT.put_line (TO_CHAR (li_buff));
         lv := DBMS_LOB.SUBSTR (ab_data, li_buff, (i * 32767 - 32766));
         UTL_FILE.PUT (l_fnum, lv);
      END LOOP;

      UTL_FILE.FCLOSE (l_fnum);
   EXCEPTION
      WHEN no_data_in_blob
      THEN
         Raise_Application_Error (-20901, 'The Blob Is Null. Передан пустые данные.');
         RETURN;
      WHEN error_process_blob
      THEN
         Raise_Application_Error (-20902, 'Error While Processed Blob. Ошибка обработки(разбивки) данных.');
         RETURN;
      WHEN UTL_FILE.invalid_path
      THEN
         Raise_Application_Error (-20903, 'File Error: Invalid Path. Ошибка файла: неверный путь к файлу.');
         RETURN;
      WHEN UTL_FILE.invalid_filehandle
      THEN
         Raise_Application_Error (-20903, 'File Error: Invalid File Handle. Ошибка файла: неверный указатель на файл.');
         RETURN;
      WHEN UTL_FILE.invalid_operation
      THEN
         Raise_Application_Error (-20903, 'File Error: Invalid File Invalid Operation. Ошибка файла: неверная операция.');
         RETURN;
      WHEN UTL_FILE.read_error
      THEN
         Raise_Application_Error (-20903, 'File Error: Read Error. Ошибка чтения файла.');
         RETURN;
      WHEN UTL_FILE.write_error
      THEN
         Raise_Application_Error (-20903, 'File Error: Write Error. Ошибка записи файла.');
         RETURN;
      WHEN UTL_FILE.internal_error
      THEN
         Raise_Application_Error (-20903, 'File Error: Internal Error. Внутренняя ошибка.');
         RETURN;
   END WRITE_CLOB;
/**********************************************************************************************/
END utl_lob;

/


