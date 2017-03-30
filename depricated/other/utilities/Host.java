import java.lang.*;
import java.io.*;
import java.sql.SQLException;

public class Host {

	public static void executeCommand (String command) throws SQLException,IOException
    {
        #sql {
            begin
                DBMS_JAVA.set_output (10000000);
            end;
        };
        String uFullCommand = "";         String nameOS = System.getProperty("os.name");

        if ((nameOS.toLowerCase()).indexOf("lin") != -1)
        {
            uFullCommand = "/bin/sh" + " -c " + command;
        }
        else if ((nameOS.toLowerCase()).indexOf("win") != -1)
        {
            uFullCommand = "%systemroot%\\system32\\cmd.exe" + " /y" + " /c " + command;
        }
        else if ((nameOS.toLowerCase()).indexOf("nix") != -1)
        {
            uFullCommand = command;
        }
        else 
        {
            uFullCommand = command;
        }

        Runtime rt = Runtime.getRuntime();
        Process p = rt.exec(uFullCommand);
        BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));

        String line = null;
        while ((line = in.readLine()) != null)
        {
            System.out.println(line);
        }
    }
};