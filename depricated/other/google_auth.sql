CREATE OR REPLACE
PROCEDURE "TEST"."AUTHENTICATE_SERVICE" (
        p_email IN VARCHAR2,
        p_password IN VARCHAR2)
IS
    l_request utl_http.req;
    l_response utl_http.resp;
    l_params    VARCHAR2 (255) ;
    l_resp_data VARCHAR2 (32000) ;
BEGIN
    -- access the oracle wallet to allow us to make an https request
    utl_http.set_wallet (path => 'file:/home/oracle/wallet',
    password => 'walletloc123') ;
    -- set up the request body with our credentials
    l_params := 'Email=' || p_email || chr (38) ||'Passwd=' || p_password ||chr (38) || 'service=cl' ||chr (38) || 'source=e-DBA-test-1.0';
    l_request := utl_http.begin_request ('https://www.google.com/accounts/ClientLogin', 'POST', 'HTTP/1.1') ;
    -- set the request headers
    utl_http.set_cookie_support (l_request, true) ;
    utl_http.set_header (l_request, 'Content-Type', 'application/x-www-form-urlencoded') ;
    utl_http.set_header (l_request, 'Content-Length', LENGTH (l_params)) ;
    -- write out the request body
    utl_http.write_text (l_request, l_params) ;
    -- get the response
    l_response := utl_http.get_response (r => l_request) ;
    dbms_output.put_line ('Status Code: '||l_response.status_code) ;
    BEGIN
        LOOP
            utl_http.read_line (r => l_response, data => l_resp_data) ;
            dbms_output.put_line (l_resp_data) ;
        
        END LOOP;
    
    EXCEPTION
    
    WHEN utl_http.end_of_body THEN
        NULL;
    
    END;
    utl_http.end_response (l_response) ;

EXCEPTION

WHEN Utl_Http.request_failed THEN
    DBMS_OUTPUT.put_line ('Request failed: '||Utl_Http.Get_Detailed_Sqlerrm) ;

END authenticate_service;
/
