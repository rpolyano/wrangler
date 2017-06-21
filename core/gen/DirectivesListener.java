// Generated from /Users/kewang/Documents/wrangler/core/src/main/antlr/co/cask/wrangler/Directives.g4 by ANTLR 4.7
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link DirectivesParser}.
 */
public interface DirectivesListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link DirectivesParser#recipe}.
	 * @param ctx the parse tree
	 */
	void enterRecipe(DirectivesParser.RecipeContext ctx);
	/**
	 * Exit a parse tree produced by {@link DirectivesParser#recipe}.
	 * @param ctx the parse tree
	 */
	void exitRecipe(DirectivesParser.RecipeContext ctx);
	/**
	 * Enter a parse tree produced by {@link DirectivesParser#directives}.
	 * @param ctx the parse tree
	 */
	void enterDirectives(DirectivesParser.DirectivesContext ctx);
	/**
	 * Exit a parse tree produced by {@link DirectivesParser#directives}.
	 * @param ctx the parse tree
	 */
	void exitDirectives(DirectivesParser.DirectivesContext ctx);
	/**
	 * Enter a parse tree produced by {@link DirectivesParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(DirectivesParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link DirectivesParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(DirectivesParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link DirectivesParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(DirectivesParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link DirectivesParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(DirectivesParser.AssignmentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code identifierFunctionCall}
	 * labeled alternative in {@link DirectivesParser#functionCall}.
	 * @param ctx the parse tree
	 */
	void enterIdentifierFunctionCall(DirectivesParser.IdentifierFunctionCallContext ctx);
	/**
	 * Exit a parse tree produced by the {@code identifierFunctionCall}
	 * labeled alternative in {@link DirectivesParser#functionCall}.
	 * @param ctx the parse tree
	 */
	void exitIdentifierFunctionCall(DirectivesParser.IdentifierFunctionCallContext ctx);
	/**
	 * Enter a parse tree produced by {@link DirectivesParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void enterIfStatement(DirectivesParser.IfStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link DirectivesParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void exitIfStatement(DirectivesParser.IfStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link DirectivesParser#ifStat}.
	 * @param ctx the parse tree
	 */
	void enterIfStat(DirectivesParser.IfStatContext ctx);
	/**
	 * Exit a parse tree produced by {@link DirectivesParser#ifStat}.
	 * @param ctx the parse tree
	 */
	void exitIfStat(DirectivesParser.IfStatContext ctx);
	/**
	 * Enter a parse tree produced by {@link DirectivesParser#elseIfStat}.
	 * @param ctx the parse tree
	 */
	void enterElseIfStat(DirectivesParser.ElseIfStatContext ctx);
	/**
	 * Exit a parse tree produced by {@link DirectivesParser#elseIfStat}.
	 * @param ctx the parse tree
	 */
	void exitElseIfStat(DirectivesParser.ElseIfStatContext ctx);
	/**
	 * Enter a parse tree produced by {@link DirectivesParser#elseStat}.
	 * @param ctx the parse tree
	 */
	void enterElseStat(DirectivesParser.ElseStatContext ctx);
	/**
	 * Exit a parse tree produced by {@link DirectivesParser#elseStat}.
	 * @param ctx the parse tree
	 */
	void exitElseStat(DirectivesParser.ElseStatContext ctx);
	/**
	 * Enter a parse tree produced by {@link DirectivesParser#idList}.
	 * @param ctx the parse tree
	 */
	void enterIdList(DirectivesParser.IdListContext ctx);
	/**
	 * Exit a parse tree produced by {@link DirectivesParser#idList}.
	 * @param ctx the parse tree
	 */
	void exitIdList(DirectivesParser.IdListContext ctx);
	/**
	 * Enter a parse tree produced by {@link DirectivesParser#exprList}.
	 * @param ctx the parse tree
	 */
	void enterExprList(DirectivesParser.ExprListContext ctx);
	/**
	 * Exit a parse tree produced by {@link DirectivesParser#exprList}.
	 * @param ctx the parse tree
	 */
	void exitExprList(DirectivesParser.ExprListContext ctx);
	/**
	 * Enter a parse tree produced by the {@code gtExpression}
	 * labeled alternative in {@link DirectivesParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterGtExpression(DirectivesParser.GtExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code gtExpression}
	 * labeled alternative in {@link DirectivesParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitGtExpression(DirectivesParser.GtExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code numberExpression}
	 * labeled alternative in {@link DirectivesParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterNumberExpression(DirectivesParser.NumberExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code numberExpression}
	 * labeled alternative in {@link DirectivesParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNumberExpression(DirectivesParser.NumberExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code identifierExpression}
	 * labeled alternative in {@link DirectivesParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterIdentifierExpression(DirectivesParser.IdentifierExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code identifierExpression}
	 * labeled alternative in {@link DirectivesParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitIdentifierExpression(DirectivesParser.IdentifierExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code modulusExpression}
	 * labeled alternative in {@link DirectivesParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterModulusExpression(DirectivesParser.ModulusExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code modulusExpression}
	 * labeled alternative in {@link DirectivesParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitModulusExpression(DirectivesParser.ModulusExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code notExpression}
	 * labeled alternative in {@link DirectivesParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterNotExpression(DirectivesParser.NotExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code notExpression}
	 * labeled alternative in {@link DirectivesParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNotExpression(DirectivesParser.NotExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code multiplyExpression}
	 * labeled alternative in {@link DirectivesParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterMultiplyExpression(DirectivesParser.MultiplyExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code multiplyExpression}
	 * labeled alternative in {@link DirectivesParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitMultiplyExpression(DirectivesParser.MultiplyExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code gtEqExpression}
	 * labeled alternative in {@link DirectivesParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterGtEqExpression(DirectivesParser.GtEqExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code gtEqExpression}
	 * labeled alternative in {@link DirectivesParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitGtEqExpression(DirectivesParser.GtEqExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code andExpression}
	 * labeled alternative in {@link DirectivesParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAndExpression(DirectivesParser.AndExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code andExpression}
	 * labeled alternative in {@link DirectivesParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAndExpression(DirectivesParser.AndExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code stringExpression}
	 * labeled alternative in {@link DirectivesParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterStringExpression(DirectivesParser.StringExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code stringExpression}
	 * labeled alternative in {@link DirectivesParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitStringExpression(DirectivesParser.StringExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expressionExpression}
	 * labeled alternative in {@link DirectivesParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpressionExpression(DirectivesParser.ExpressionExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expressionExpression}
	 * labeled alternative in {@link DirectivesParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpressionExpression(DirectivesParser.ExpressionExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code nullExpression}
	 * labeled alternative in {@link DirectivesParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterNullExpression(DirectivesParser.NullExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code nullExpression}
	 * labeled alternative in {@link DirectivesParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNullExpression(DirectivesParser.NullExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code functionCallExpression}
	 * labeled alternative in {@link DirectivesParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterFunctionCallExpression(DirectivesParser.FunctionCallExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code functionCallExpression}
	 * labeled alternative in {@link DirectivesParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitFunctionCallExpression(DirectivesParser.FunctionCallExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code listExpression}
	 * labeled alternative in {@link DirectivesParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterListExpression(DirectivesParser.ListExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code listExpression}
	 * labeled alternative in {@link DirectivesParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitListExpression(DirectivesParser.ListExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ltEqExpression}
	 * labeled alternative in {@link DirectivesParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterLtEqExpression(DirectivesParser.LtEqExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ltEqExpression}
	 * labeled alternative in {@link DirectivesParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitLtEqExpression(DirectivesParser.LtEqExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ltExpression}
	 * labeled alternative in {@link DirectivesParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterLtExpression(DirectivesParser.LtExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ltExpression}
	 * labeled alternative in {@link DirectivesParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitLtExpression(DirectivesParser.LtExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code boolExpression}
	 * labeled alternative in {@link DirectivesParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterBoolExpression(DirectivesParser.BoolExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code boolExpression}
	 * labeled alternative in {@link DirectivesParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitBoolExpression(DirectivesParser.BoolExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code notEqExpression}
	 * labeled alternative in {@link DirectivesParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterNotEqExpression(DirectivesParser.NotEqExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code notEqExpression}
	 * labeled alternative in {@link DirectivesParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNotEqExpression(DirectivesParser.NotEqExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code divideExpression}
	 * labeled alternative in {@link DirectivesParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterDivideExpression(DirectivesParser.DivideExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code divideExpression}
	 * labeled alternative in {@link DirectivesParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitDivideExpression(DirectivesParser.DivideExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code orExpression}
	 * labeled alternative in {@link DirectivesParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterOrExpression(DirectivesParser.OrExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code orExpression}
	 * labeled alternative in {@link DirectivesParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitOrExpression(DirectivesParser.OrExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code unaryMinusExpression}
	 * labeled alternative in {@link DirectivesParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterUnaryMinusExpression(DirectivesParser.UnaryMinusExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code unaryMinusExpression}
	 * labeled alternative in {@link DirectivesParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitUnaryMinusExpression(DirectivesParser.UnaryMinusExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code powerExpression}
	 * labeled alternative in {@link DirectivesParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterPowerExpression(DirectivesParser.PowerExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code powerExpression}
	 * labeled alternative in {@link DirectivesParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitPowerExpression(DirectivesParser.PowerExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code eqExpression}
	 * labeled alternative in {@link DirectivesParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterEqExpression(DirectivesParser.EqExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code eqExpression}
	 * labeled alternative in {@link DirectivesParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitEqExpression(DirectivesParser.EqExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code addExpression}
	 * labeled alternative in {@link DirectivesParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAddExpression(DirectivesParser.AddExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code addExpression}
	 * labeled alternative in {@link DirectivesParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAddExpression(DirectivesParser.AddExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code subtractExpression}
	 * labeled alternative in {@link DirectivesParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterSubtractExpression(DirectivesParser.SubtractExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code subtractExpression}
	 * labeled alternative in {@link DirectivesParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitSubtractExpression(DirectivesParser.SubtractExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ternaryExpression}
	 * labeled alternative in {@link DirectivesParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterTernaryExpression(DirectivesParser.TernaryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ternaryExpression}
	 * labeled alternative in {@link DirectivesParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitTernaryExpression(DirectivesParser.TernaryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link DirectivesParser#list}.
	 * @param ctx the parse tree
	 */
	void enterList(DirectivesParser.ListContext ctx);
	/**
	 * Exit a parse tree produced by {@link DirectivesParser#list}.
	 * @param ctx the parse tree
	 */
	void exitList(DirectivesParser.ListContext ctx);
	/**
	 * Enter a parse tree produced by {@link DirectivesParser#indexes}.
	 * @param ctx the parse tree
	 */
	void enterIndexes(DirectivesParser.IndexesContext ctx);
	/**
	 * Exit a parse tree produced by {@link DirectivesParser#indexes}.
	 * @param ctx the parse tree
	 */
	void exitIndexes(DirectivesParser.IndexesContext ctx);
}