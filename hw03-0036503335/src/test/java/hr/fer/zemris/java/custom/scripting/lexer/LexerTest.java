package hr.fer.zemris.java.custom.scripting.lexer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LexerTest {

	@Test
	void testPrimjer1() {
		Lexer lexer = new Lexer("Ovo je \n" + 
				"sve jedan text node\n" + 
				"");
		assertEquals("Ovo je \n" + 
				"sve jedan text node\n" + 
				"", lexer.nextToken().getValue());
		assertEquals(TokenType.TEXT, lexer.getLastToken().getType());
	}
	
	@Test
	void testPrimjer2() {
		Lexer lexer = new Lexer("Ovo je \n" + 
				"sve jedan \\{$ text node\n" + 
				"");
		assertEquals("Ovo je \n" + 
				"sve jedan {$ text node\n" + 
				"", lexer.nextToken().getValue());
		assertEquals(TokenType.TEXT, lexer.getLastToken().getType());
	}
	
	@Test
	void testPrimjer3() {
		Lexer lexer = new Lexer("Ovo je \n" + 
				"sve jedan \\\\\\{$text node\n" + 
				"");
		assertEquals("Ovo je \n" + 
				"sve jedan \\{$text node\n" + 
				"", lexer.nextToken().getValue());
		assertEquals(TokenType.TEXT, lexer.getLastToken().getType());
	}
	
	@Test
	void testPrimjer4() {
		assertThrows(LexerException.class, () -> {
			new Lexer("Ovo se ruši s iznimkom \\n \n" + 
					"jer je escape ilegalan ovdje.\n" + 
					"").nextToken();
		});
	}
	
	@Test
	void testPrimjer5() {
		assertThrows(LexerException.class, () -> {
			new Lexer("Ovo se ruši \"s iznimkom \\n\" \n" + 
					"jer je escape ilegalan ovdje.\n" + 
					"").nextToken();
		});
	}
	
	@Test
	void testPrimjer6() {
		Lexer lexer = new Lexer("Ovo je OK {$ = \"String ide\n" + 
				"u više redaka\n" + 
				"čak tri\" $}\n" + 
				"");
		assertEquals("Ovo je OK ", lexer.nextToken().getValue());
		assertEquals(TokenType.TEXT, lexer.getLastToken().getType());
		assertEquals("{$", lexer.nextToken().getValue());
		assertEquals(TokenType.TAG_START, lexer.getLastToken().getType());
		lexer.setMode(LexerMode.TAG_NAME);
		assertEquals("=", lexer.nextToken().getValue());
		assertEquals(TokenType.TAG_NAME, lexer.getLastToken().getType());
		lexer.setMode(LexerMode.TAG_BODY);
		assertEquals("String ide\nu više redaka\nčak tri", lexer.nextToken().getValue());
		assertEquals(TokenType.STRING, lexer.getLastToken().getType());
		assertEquals("$}", lexer.nextToken().getValue());
		assertEquals(TokenType.TAG_END, lexer.getLastToken().getType());
		lexer.setMode(LexerMode.TEXT);
		assertEquals("\n", lexer.nextToken().getValue());
		assertEquals(TokenType.TEXT, lexer.getLastToken().getType());
		assertEquals(null, lexer.nextToken().getValue());
		assertEquals(TokenType.EOF, lexer.getLastToken().getType());
	}
	
	@Test
	void testPrimjer7() {
		Lexer lexer = new Lexer("Ovo je isto OK {$ = \"String ide\n" + 
				"u \\\"više\\\"\nredaka\n" + 
				"ovdje a stvarno četiri\" $}\n" + 
				"");
		assertEquals("Ovo je isto OK ", lexer.nextToken().getValue());
		assertEquals(TokenType.TEXT, lexer.getLastToken().getType());
		assertEquals("{$", lexer.nextToken().getValue());
		assertEquals(TokenType.TAG_START, lexer.getLastToken().getType());
		lexer.setMode(LexerMode.TAG_NAME);
		assertEquals("=", lexer.nextToken().getValue());
		assertEquals(TokenType.TAG_NAME, lexer.getLastToken().getType());
		lexer.setMode(LexerMode.TAG_BODY);
		assertEquals("String ide\nu \"više\"\nredaka\novdje a stvarno četiri", lexer.nextToken().getValue());
		assertEquals(TokenType.STRING, lexer.getLastToken().getType());
		assertEquals("$}", lexer.nextToken().getValue());
		assertEquals(TokenType.TAG_END, lexer.getLastToken().getType());
		lexer.setMode(LexerMode.TEXT);
		assertEquals("\n", lexer.nextToken().getValue());
		assertEquals(TokenType.TEXT, lexer.getLastToken().getType());
		assertEquals(null, lexer.nextToken().getValue());
		assertEquals(TokenType.EOF, lexer.getLastToken().getType());
	}
	
	@Test
	void testPrimjer8() {
		Lexer lexer = new Lexer("Ovo se ruši {$ = \"String ide\n" + 
				"u više \\{$ redaka\n" + 
				"čak tri\" $}\n" + 
				"");
		assertEquals("Ovo se ruši ", lexer.nextToken().getValue());
		assertEquals(TokenType.TEXT, lexer.getLastToken().getType());
		assertEquals("{$", lexer.nextToken().getValue());
		assertEquals(TokenType.TAG_START, lexer.getLastToken().getType());
		lexer.setMode(LexerMode.TAG_NAME);
		assertEquals("=", lexer.nextToken().getValue());
		assertEquals(TokenType.TAG_NAME, lexer.getLastToken().getType());
		lexer.setMode(LexerMode.TAG_BODY);
		assertThrows(LexerException.class, () -> {
			lexer.nextToken();
		});
	}
	
	@Test
	void testPrimjer9() {
		Lexer lexer = new Lexer("Ovo se ruši {$ = \\n $}\n" + 
				"");
		assertEquals("Ovo se ruši ", lexer.nextToken().getValue());
		assertEquals(TokenType.TEXT, lexer.getLastToken().getType());
		assertEquals("{$", lexer.nextToken().getValue());
		assertEquals(TokenType.TAG_START, lexer.getLastToken().getType());
		lexer.setMode(LexerMode.TAG_NAME);
		assertEquals("=", lexer.nextToken().getValue());
		assertEquals(TokenType.TAG_NAME, lexer.getLastToken().getType());
		lexer.setMode(LexerMode.TAG_BODY);
		assertThrows(LexerException.class, () -> {
			lexer.nextToken();
		});
	}
	
}
