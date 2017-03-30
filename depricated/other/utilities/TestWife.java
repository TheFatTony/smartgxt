import java.lang.*;
import java.io.*;
import java.sql.SQLException;

import com.prowidesoftware.swift.io.ConversionService;
import com.prowidesoftware.swift.io.IConversionService;
import com.prowidesoftware.swift.model.SwiftBlock1;
import com.prowidesoftware.swift.model.SwiftBlock2Output;
import com.prowidesoftware.swift.model.SwiftBlock3;
import com.prowidesoftware.swift.model.SwiftMessage;
import com.prowidesoftware.swift.model.Tag;

public class TestWife {

	public static void executeCommand() {
		 #sql {
		 begin
		 DBMS_JAVA.set_output (10000000);
		 end;
		 };
		IConversionService srv = new ConversionService();
		SwiftMessage msg = new SwiftMessage();

		SwiftBlock1 b1 = new SwiftBlock1();

		b1.setApplicationId("F");
		b1.setServiceId("01");
		b1.setLogicalTerminal("BICFOOYYAXXX");
		b1.setSessionNumber("1234");
		b1.setSequenceNumber("123456");
		msg.setBlock1(b1);

		msg.setBlock2(new SwiftBlock2Output(
				"O1030831051017BICFUUYYAXXX10194697810510170831N"));

		msg.setBlock3(new SwiftBlock3());
		msg.getBlock3().addTag(new Tag("113:NOMT"));
		msg.getBlock3().addTag(new Tag("108", "P22ABCD43C6J3XYZ"));

		String fin = srv.getFIN(msg);
		System.out.println(fin);
	}
};