// Generated from /Users/kewang/Documents/wrangler/core/src/main/antlr4/co/cask/wrangler/grammar/DirectivesGrammar.g4 by ANTLR 4.7
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class DirectivesGrammarParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		NL=1, WS=2, OBrace=3, CBrace=4, Bool=5, Number=6, Identifier=7, String=8, 
		Expression=9, Comment=10;
	public static final int
		RULE_directives = 0, RULE_directive = 1, RULE_command = 2, RULE_arguments = 3, 
		RULE_argument = 4, RULE_columnName = 5, RULE_expression = 6;
	public static final String[] ruleNames = {
		"directives", "directive", "command", "arguments", "argument", "columnName", 
		"expression"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'\n'", null, "'{'", "'}'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "NL", "WS", "OBrace", "CBrace", "Bool", "Number", "Identifier", 
		"String", "Expression", "Comment"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "DirectivesGrammar.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public DirectivesGrammarParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class DirectivesContext extends ParserRuleContext {
		public DirectivesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_directives; }
	 
		public DirectivesContext() { }
		public void copyFrom(DirectivesContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class DirectiveListContext extends DirectivesContext {
		public TerminalNode EOF() { return getToken(DirectivesGrammarParser.EOF, 0); }
		public List<TerminalNode> NL() { return getTokens(DirectivesGrammarParser.NL); }
		public TerminalNode NL(int i) {
			return getToken(DirectivesGrammarParser.NL, i);
		}
		public List<DirectiveContext> directive() {
			return getRuleContexts(DirectiveContext.class);
		}
		public DirectiveContext directive(int i) {
			return getRuleContext(DirectiveContext.class,i);
		}
		public DirectiveListContext(DirectivesContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DirectivesGrammarListener ) ((DirectivesGrammarListener)listener).enterDirectiveList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DirectivesGrammarListener ) ((DirectivesGrammarListener)listener).exitDirectiveList(this);
		}
	}

	public final DirectivesContext directives() throws RecognitionException {
		DirectivesContext _localctx = new DirectivesContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_directives);
		int _la;
		try {
			int _alt;
			_localctx = new DirectiveListContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(17);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,0,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(14);
					match(NL);
					}
					} 
				}
				setState(19);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,0,_ctx);
			}
			{
			setState(23);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==Identifier) {
				{
				{
				setState(20);
				directive();
				}
				}
				setState(25);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
			{
			setState(29);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NL) {
				{
				{
				setState(26);
				match(NL);
				}
				}
				setState(31);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
			setState(32);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DirectiveContext extends ParserRuleContext {
		public DirectiveContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_directive; }
	 
		public DirectiveContext() { }
		public void copyFrom(DirectiveContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class DirectiveNoExpContext extends DirectiveContext {
		public CommandContext command() {
			return getRuleContext(CommandContext.class,0);
		}
		public ArgumentsContext arguments() {
			return getRuleContext(ArgumentsContext.class,0);
		}
		public TerminalNode NL() { return getToken(DirectivesGrammarParser.NL, 0); }
		public List<TerminalNode> WS() { return getTokens(DirectivesGrammarParser.WS); }
		public TerminalNode WS(int i) {
			return getToken(DirectivesGrammarParser.WS, i);
		}
		public DirectiveNoExpContext(DirectiveContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DirectivesGrammarListener ) ((DirectivesGrammarListener)listener).enterDirectiveNoExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DirectivesGrammarListener ) ((DirectivesGrammarListener)listener).exitDirectiveNoExp(this);
		}
	}

	public final DirectiveContext directive() throws RecognitionException {
		DirectiveContext _localctx = new DirectiveContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_directive);
		int _la;
		try {
			_localctx = new DirectiveNoExpContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(34);
			command();
			{
			setState(36); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(35);
				match(WS);
				}
				}
				setState(38); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==WS );
			}
			setState(40);
			arguments();
			setState(41);
			match(NL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CommandContext extends ParserRuleContext {
		public CommandContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_command; }
	 
		public CommandContext() { }
		public void copyFrom(CommandContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class CommandWordContext extends CommandContext {
		public TerminalNode Identifier() { return getToken(DirectivesGrammarParser.Identifier, 0); }
		public CommandWordContext(CommandContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DirectivesGrammarListener ) ((DirectivesGrammarListener)listener).enterCommandWord(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DirectivesGrammarListener ) ((DirectivesGrammarListener)listener).exitCommandWord(this);
		}
	}

	public final CommandContext command() throws RecognitionException {
		CommandContext _localctx = new CommandContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_command);
		try {
			_localctx = new CommandWordContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(43);
			match(Identifier);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArgumentsContext extends ParserRuleContext {
		public ArgumentsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arguments; }
	 
		public ArgumentsContext() { }
		public void copyFrom(ArgumentsContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ArgumentListContext extends ArgumentsContext {
		public List<ArgumentContext> argument() {
			return getRuleContexts(ArgumentContext.class);
		}
		public ArgumentContext argument(int i) {
			return getRuleContext(ArgumentContext.class,i);
		}
		public List<TerminalNode> WS() { return getTokens(DirectivesGrammarParser.WS); }
		public TerminalNode WS(int i) {
			return getToken(DirectivesGrammarParser.WS, i);
		}
		public ArgumentListContext(ArgumentsContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DirectivesGrammarListener ) ((DirectivesGrammarListener)listener).enterArgumentList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DirectivesGrammarListener ) ((DirectivesGrammarListener)listener).exitArgumentList(this);
		}
	}

	public final ArgumentsContext arguments() throws RecognitionException {
		ArgumentsContext _localctx = new ArgumentsContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_arguments);
		int _la;
		try {
			_localctx = new ArgumentListContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(45);
			argument();
			setState(54);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==WS) {
				{
				{
				{
				setState(47); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(46);
					match(WS);
					}
					}
					setState(49); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==WS );
				}
				setState(51);
				argument();
				}
				}
				setState(56);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArgumentContext extends ParserRuleContext {
		public ArgumentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_argument; }
	 
		public ArgumentContext() { }
		public void copyFrom(ArgumentContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class NumberContext extends ArgumentContext {
		public TerminalNode Number() { return getToken(DirectivesGrammarParser.Number, 0); }
		public NumberContext(ArgumentContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DirectivesGrammarListener ) ((DirectivesGrammarListener)listener).enterNumber(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DirectivesGrammarListener ) ((DirectivesGrammarListener)listener).exitNumber(this);
		}
	}
	public static class ExpressionArgContext extends ArgumentContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ExpressionArgContext(ArgumentContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DirectivesGrammarListener ) ((DirectivesGrammarListener)listener).enterExpressionArg(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DirectivesGrammarListener ) ((DirectivesGrammarListener)listener).exitExpressionArg(this);
		}
	}
	public static class ColumnNameArgContext extends ArgumentContext {
		public ColumnNameContext columnName() {
			return getRuleContext(ColumnNameContext.class,0);
		}
		public ColumnNameArgContext(ArgumentContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DirectivesGrammarListener ) ((DirectivesGrammarListener)listener).enterColumnNameArg(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DirectivesGrammarListener ) ((DirectivesGrammarListener)listener).exitColumnNameArg(this);
		}
	}

	public final ArgumentContext argument() throws RecognitionException {
		ArgumentContext _localctx = new ArgumentContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_argument);
		try {
			setState(60);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Number:
				_localctx = new NumberContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(57);
				match(Number);
				}
				break;
			case Identifier:
				_localctx = new ColumnNameArgContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(58);
				columnName();
				}
				break;
			case OBrace:
				_localctx = new ExpressionArgContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(59);
				expression();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ColumnNameContext extends ParserRuleContext {
		public ColumnNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_columnName; }
	 
		public ColumnNameContext() { }
		public void copyFrom(ColumnNameContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ColumnNameWordContext extends ColumnNameContext {
		public TerminalNode Identifier() { return getToken(DirectivesGrammarParser.Identifier, 0); }
		public ColumnNameWordContext(ColumnNameContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DirectivesGrammarListener ) ((DirectivesGrammarListener)listener).enterColumnNameWord(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DirectivesGrammarListener ) ((DirectivesGrammarListener)listener).exitColumnNameWord(this);
		}
	}

	public final ColumnNameContext columnName() throws RecognitionException {
		ColumnNameContext _localctx = new ColumnNameContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_columnName);
		try {
			_localctx = new ColumnNameWordContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(62);
			match(Identifier);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionContext extends ParserRuleContext {
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
	 
		public ExpressionContext() { }
		public void copyFrom(ExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ExpBlockContext extends ExpressionContext {
		public TerminalNode OBrace() { return getToken(DirectivesGrammarParser.OBrace, 0); }
		public TerminalNode Expression() { return getToken(DirectivesGrammarParser.Expression, 0); }
		public TerminalNode CBrace() { return getToken(DirectivesGrammarParser.CBrace, 0); }
		public ExpBlockContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DirectivesGrammarListener ) ((DirectivesGrammarListener)listener).enterExpBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DirectivesGrammarListener ) ((DirectivesGrammarListener)listener).exitExpBlock(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_expression);
		try {
			_localctx = new ExpBlockContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(64);
			match(OBrace);
			setState(65);
			match(Expression);
			setState(66);
			match(CBrace);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\fG\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\3\2\7\2\22\n\2\f\2\16\2\25"+
		"\13\2\3\2\7\2\30\n\2\f\2\16\2\33\13\2\3\2\7\2\36\n\2\f\2\16\2!\13\2\3"+
		"\2\3\2\3\3\3\3\6\3\'\n\3\r\3\16\3(\3\3\3\3\3\3\3\4\3\4\3\5\3\5\6\5\62"+
		"\n\5\r\5\16\5\63\3\5\7\5\67\n\5\f\5\16\5:\13\5\3\6\3\6\3\6\5\6?\n\6\3"+
		"\7\3\7\3\b\3\b\3\b\3\b\3\b\2\2\t\2\4\6\b\n\f\16\2\2\2G\2\23\3\2\2\2\4"+
		"$\3\2\2\2\6-\3\2\2\2\b/\3\2\2\2\n>\3\2\2\2\f@\3\2\2\2\16B\3\2\2\2\20\22"+
		"\7\3\2\2\21\20\3\2\2\2\22\25\3\2\2\2\23\21\3\2\2\2\23\24\3\2\2\2\24\31"+
		"\3\2\2\2\25\23\3\2\2\2\26\30\5\4\3\2\27\26\3\2\2\2\30\33\3\2\2\2\31\27"+
		"\3\2\2\2\31\32\3\2\2\2\32\37\3\2\2\2\33\31\3\2\2\2\34\36\7\3\2\2\35\34"+
		"\3\2\2\2\36!\3\2\2\2\37\35\3\2\2\2\37 \3\2\2\2 \"\3\2\2\2!\37\3\2\2\2"+
		"\"#\7\2\2\3#\3\3\2\2\2$&\5\6\4\2%\'\7\4\2\2&%\3\2\2\2\'(\3\2\2\2(&\3\2"+
		"\2\2()\3\2\2\2)*\3\2\2\2*+\5\b\5\2+,\7\3\2\2,\5\3\2\2\2-.\7\t\2\2.\7\3"+
		"\2\2\2/8\5\n\6\2\60\62\7\4\2\2\61\60\3\2\2\2\62\63\3\2\2\2\63\61\3\2\2"+
		"\2\63\64\3\2\2\2\64\65\3\2\2\2\65\67\5\n\6\2\66\61\3\2\2\2\67:\3\2\2\2"+
		"8\66\3\2\2\289\3\2\2\29\t\3\2\2\2:8\3\2\2\2;?\7\b\2\2<?\5\f\7\2=?\5\16"+
		"\b\2>;\3\2\2\2><\3\2\2\2>=\3\2\2\2?\13\3\2\2\2@A\7\t\2\2A\r\3\2\2\2BC"+
		"\7\5\2\2CD\7\13\2\2DE\7\6\2\2E\17\3\2\2\2\t\23\31\37(\638>";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}