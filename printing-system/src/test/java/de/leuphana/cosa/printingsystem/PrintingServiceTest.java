package de.leuphana.cosa.printingsystem;

import de.leuphana.cosa.printingsystem.behaviour.PrintingServiceImpl;
import de.leuphana.cosa.printingsystem.structure.Printable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class PrintingServiceTest {

	private Printable printable;
	private PrintingServiceImpl printingSystem;

	@BeforeEach
	void setUp() {
		printingSystem = new PrintingServiceImpl();
		printable = new Printable() {
			@Override
			public String getTitle() {
				return "Test Document";
			}

			@Override
			public List<String> getContent() {
				List<String> content = new ArrayList<>();

				content.add("              _,.");
				content.add("           ,''   `.     __....__ ");
				content.add("         ,'        >.-''        ``-.__,)");
				content.add("       ,'      _,''           _____ _,'");
				content.add("      /      ,'           _.:':::_`:-._ ");
				content.add("     :     ,'       _..-''  \\`'.;.`-:::`:. ");
				content.add("     ;    /       ,'  ,::'  .\\,'`.`. `\\::)`  ");
				content.add("    /    /      ,'        \\   `. '  )  )/ ");
				content.add("   /    /      /:`.     `--`'   \\     '`");
				content.add("   `-._/      /::::)             )");
				content.add("      /      /,-.:(   , _   `.-' ");
				content.add("     ;      :(,`.`-' ',`.     ;");
				content.add("    :       |:\\`' )      `-.._\\ _");
				content.add("    |         `:-(             `)``-._ ");
				content.add("    |           `.`.        /``'      ``:-.-__,");
				content.add("    :           / `:\\ .     :            ` \\`-");
				content.add("     \\        ,'   '}  `.   |");
				content.add("  _..-`.    ,'`-.   }   |`-'    ");
				content.add(",'__    `-'' -.`.'._|   | ");
				content.add("    ```--..,.__.(_|.|   |::._");
				content.add("      __..','/ ,' :  `-.|::)_`.");
				content.add("      `..__..-'   |`.      __,' ");
				content.add("                  :  `-._ `  ;");
				content.add("                   \\ \\   )  /");
				content.add("                   .\\ `.   /");
				content.add("                    ::    /");
				content.add("                    :|  ,'");
				content.add("                    :;,'");
				content.add("                    `'");

				return content;
			}
		};
	}

	@AfterEach
	void tearDown() {
		printingSystem = null;
	}

	@Test
	void canDocumentBePrintedTest() {
		Assertions.assertNotNull(printingSystem.print(printable));
	}
}
