package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ConditionalExpressionTest {

	@Test
	void testConditionalExpression() {
		ConditionalExpression expr = new ConditionalExpression(
				FieldValueGetters.LAST_NAME,
				"Bos*",
				ComparisonOperators.LIKE
				);
		StudentRecord record = new StudentRecord("0036472239", "Karlo", "Bosan", 5);
		boolean recordSatisfies = expr.getOperator().satisfied(
				expr.getValueGetter().get(record),  // returns lastName from given record
				expr.getLiteral()             // returns "Bos*"
				);
		assertTrue(recordSatisfies);
	}

}
