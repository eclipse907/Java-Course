package hr.fer.zemris.java.custom.scripting.parser;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;

class SmartScriptParserTest {

	@Test
	void testForLoop() {
		SmartScriptParser parser = new SmartScriptParser("{$ FOR i 4 \"\\\"-23\" src $}\n{$= i + i $}\n{$END$}");
		DocumentNode document = parser.getDocumentNode();
		assertEquals("{$ FOR i 4 \"\"-23\" src $}\n{$= i + i $}\n{$END$}", document.toString());
	}
	
	@Test
	void testWrongForLoop() {
		assertThrows(SmartScriptParserException.class, () -> {
			new SmartScriptParser("{$ FOR i 4 \"-23\" src $}\n{$= i + i $} \\bla\n{$END$}");
		});
	}
	
	@Test
	void testText() {
		SmartScriptParser parser = new SmartScriptParser("bla \\\\ bla fdfdsfs\n 3535 \\{ func.");
		DocumentNode document = parser.getDocumentNode();
		assertEquals("bla \\ bla fdfdsfs\n 3535 { func.", document.toString());
	}
	
	@Test
	void testWrongText() {
		assertThrows(SmartScriptParserException.class, () -> {
			new SmartScriptParser("bla \\ bla fdfdsfs\n 3535 func.");
		});
	}
	
	@Test
	void testEcho() {
		SmartScriptParser parser = new SmartScriptParser("{$= i \"bla \\\" bla \" @sin @decfun -3.4 $}\n bla.");
		DocumentNode document = parser.getDocumentNode();
		assertEquals("{$= i \"bla \" bla \" @sin @decfun -3.4 $}\n bla.", document.toString());
	}
	
	@Test
	void testWrongEcho() {
		assertThrows(SmartScriptParserException.class, () -> {
			new SmartScriptParser("{$= symb \"bla \\tr bla\" $}");
		});
	}
}
