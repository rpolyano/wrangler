// Generated from /Users/kewang/Documents/wrangler/core/src/main/antlr4/co/cask/wrangler/grammar/DirectivesGrammar.g4 by ANTLR 4.7
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link DirectivesGrammarParser}.
 */
public interface DirectivesGrammarListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by the {@code DirectiveList}
	 * labeled alternative in {@link DirectivesGrammarParser#directives}.
	 * @param ctx the parse tree
	 */
	void enterDirectiveList(DirectivesGrammarParser.DirectiveListContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DirectiveList}
	 * labeled alternative in {@link DirectivesGrammarParser#directives}.
	 * @param ctx the parse tree
	 */
	void exitDirectiveList(DirectivesGrammarParser.DirectiveListContext ctx);
	/**
	 * Enter a parse tree produced by the {@code DirectiveNoExp}
	 * labeled alternative in {@link DirectivesGrammarParser#directive}.
	 * @param ctx the parse tree
	 */
	void enterDirectiveNoExp(DirectivesGrammarParser.DirectiveNoExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DirectiveNoExp}
	 * labeled alternative in {@link DirectivesGrammarParser#directive}.
	 * @param ctx the parse tree
	 */
	void exitDirectiveNoExp(DirectivesGrammarParser.DirectiveNoExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code CommandWord}
	 * labeled alternative in {@link DirectivesGrammarParser#command}.
	 * @param ctx the parse tree
	 */
	void enterCommandWord(DirectivesGrammarParser.CommandWordContext ctx);
	/**
	 * Exit a parse tree produced by the {@code CommandWord}
	 * labeled alternative in {@link DirectivesGrammarParser#command}.
	 * @param ctx the parse tree
	 */
	void exitCommandWord(DirectivesGrammarParser.CommandWordContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ArgumentList}
	 * labeled alternative in {@link DirectivesGrammarParser#arguments}.
	 * @param ctx the parse tree
	 */
	void enterArgumentList(DirectivesGrammarParser.ArgumentListContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ArgumentList}
	 * labeled alternative in {@link DirectivesGrammarParser#arguments}.
	 * @param ctx the parse tree
	 */
	void exitArgumentList(DirectivesGrammarParser.ArgumentListContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Number}
	 * labeled alternative in {@link DirectivesGrammarParser#argument}.
	 * @param ctx the parse tree
	 */
	void enterNumber(DirectivesGrammarParser.NumberContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Number}
	 * labeled alternative in {@link DirectivesGrammarParser#argument}.
	 * @param ctx the parse tree
	 */
	void exitNumber(DirectivesGrammarParser.NumberContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ColumnNameArg}
	 * labeled alternative in {@link DirectivesGrammarParser#argument}.
	 * @param ctx the parse tree
	 */
	void enterColumnNameArg(DirectivesGrammarParser.ColumnNameArgContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ColumnNameArg}
	 * labeled alternative in {@link DirectivesGrammarParser#argument}.
	 * @param ctx the parse tree
	 */
	void exitColumnNameArg(DirectivesGrammarParser.ColumnNameArgContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExpressionArg}
	 * labeled alternative in {@link DirectivesGrammarParser#argument}.
	 * @param ctx the parse tree
	 */
	void enterExpressionArg(DirectivesGrammarParser.ExpressionArgContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExpressionArg}
	 * labeled alternative in {@link DirectivesGrammarParser#argument}.
	 * @param ctx the parse tree
	 */
	void exitExpressionArg(DirectivesGrammarParser.ExpressionArgContext ctx);
	/**
	 * Enter a parse tree produced by the {@code columnNameWord}
	 * labeled alternative in {@link DirectivesGrammarParser#columnName}.
	 * @param ctx the parse tree
	 */
	void enterColumnNameWord(DirectivesGrammarParser.ColumnNameWordContext ctx);
	/**
	 * Exit a parse tree produced by the {@code columnNameWord}
	 * labeled alternative in {@link DirectivesGrammarParser#columnName}.
	 * @param ctx the parse tree
	 */
	void exitColumnNameWord(DirectivesGrammarParser.ColumnNameWordContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expBlock}
	 * labeled alternative in {@link DirectivesGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpBlock(DirectivesGrammarParser.ExpBlockContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expBlock}
	 * labeled alternative in {@link DirectivesGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpBlock(DirectivesGrammarParser.ExpBlockContext ctx);
}