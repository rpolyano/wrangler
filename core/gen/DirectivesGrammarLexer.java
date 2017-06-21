// Generated from /Users/kewang/Documents/wrangler/core/src/main/antlr4/co/cask/wrangler/grammar/DirectivesGrammar.g4 by ANTLR 4.7
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class DirectivesGrammarLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		NL=1, WS=2, OBrace=3, CBrace=4, Bool=5, Number=6, Identifier=7, String=8, 
		Expression=9, Comment=10;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"NL", "WS", "OBrace", "CBrace", "Bool", "Number", "Identifier", "String", 
		"Expression", "Comment", "Int", "Digit"
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


	public DirectivesGrammarLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "DirectivesGrammar.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\f\u0089\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\3\2\3\2\3\3\6\3\37\n\3\r\3\16\3 \3\4\3\4\3\5\3\5"+
		"\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\5\6\60\n\6\3\7\3\7\3\7\7\7\65\n\7"+
		"\f\7\16\78\13\7\5\7:\n\7\3\b\3\b\7\b>\n\b\f\b\16\bA\13\b\3\t\3\t\3\t\3"+
		"\t\3\t\3\t\7\tI\n\t\f\t\16\tL\13\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\7\tU\n"+
		"\t\f\t\16\tX\13\t\3\t\5\t[\n\t\3\n\3\n\3\n\3\n\3\n\6\nb\n\n\r\n\16\nc"+
		"\3\13\3\13\3\13\3\13\7\13j\n\13\f\13\16\13m\13\13\3\13\3\13\3\13\3\13"+
		"\7\13s\n\13\f\13\16\13v\13\13\3\13\3\13\5\13z\n\13\3\13\3\13\3\f\3\f\7"+
		"\f\u0080\n\f\f\f\16\f\u0083\13\f\3\f\5\f\u0086\n\f\3\r\3\r\3t\2\16\3\3"+
		"\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\2\31\2\3\2\r\5\2\13\13\17"+
		"\17\"\"\7\2//<<C\\aac|\7\2//\62<C\\aac|\3\2$$\5\2\f\f\17\17$$\3\2))\5"+
		"\2\f\f\17\17))\7\2\f\f\17\17$$}}\177\177\4\2\f\f\17\17\3\2\63;\3\2\62"+
		";\2\u009a\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2"+
		"\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\3\33"+
		"\3\2\2\2\5\36\3\2\2\2\7\"\3\2\2\2\t$\3\2\2\2\13/\3\2\2\2\r\61\3\2\2\2"+
		"\17;\3\2\2\2\21Z\3\2\2\2\23a\3\2\2\2\25y\3\2\2\2\27\u0085\3\2\2\2\31\u0087"+
		"\3\2\2\2\33\34\7\f\2\2\34\4\3\2\2\2\35\37\t\2\2\2\36\35\3\2\2\2\37 \3"+
		"\2\2\2 \36\3\2\2\2 !\3\2\2\2!\6\3\2\2\2\"#\7}\2\2#\b\3\2\2\2$%\7\177\2"+
		"\2%\n\3\2\2\2&\'\7v\2\2\'(\7t\2\2()\7w\2\2)\60\7g\2\2*+\7h\2\2+,\7c\2"+
		"\2,-\7n\2\2-.\7u\2\2.\60\7g\2\2/&\3\2\2\2/*\3\2\2\2\60\f\3\2\2\2\619\5"+
		"\27\f\2\62\66\7\60\2\2\63\65\5\31\r\2\64\63\3\2\2\2\658\3\2\2\2\66\64"+
		"\3\2\2\2\66\67\3\2\2\2\67:\3\2\2\28\66\3\2\2\29\62\3\2\2\29:\3\2\2\2:"+
		"\16\3\2\2\2;?\t\3\2\2<>\t\4\2\2=<\3\2\2\2>A\3\2\2\2?=\3\2\2\2?@\3\2\2"+
		"\2@\20\3\2\2\2A?\3\2\2\2BJ\t\5\2\2CI\n\6\2\2DE\7^\2\2EI\7^\2\2FG\7^\2"+
		"\2GI\7$\2\2HC\3\2\2\2HD\3\2\2\2HF\3\2\2\2IL\3\2\2\2JH\3\2\2\2JK\3\2\2"+
		"\2KM\3\2\2\2LJ\3\2\2\2M[\t\5\2\2NV\t\7\2\2OU\n\b\2\2PQ\7^\2\2QU\7^\2\2"+
		"RS\7^\2\2SU\7)\2\2TO\3\2\2\2TP\3\2\2\2TR\3\2\2\2UX\3\2\2\2VT\3\2\2\2V"+
		"W\3\2\2\2WY\3\2\2\2XV\3\2\2\2Y[\t\7\2\2ZB\3\2\2\2ZN\3\2\2\2[\22\3\2\2"+
		"\2\\b\n\t\2\2]^\7^\2\2^b\7^\2\2_`\7^\2\2`b\7$\2\2a\\\3\2\2\2a]\3\2\2\2"+
		"a_\3\2\2\2bc\3\2\2\2ca\3\2\2\2cd\3\2\2\2d\24\3\2\2\2ef\7\61\2\2fg\7\61"+
		"\2\2gk\3\2\2\2hj\n\n\2\2ih\3\2\2\2jm\3\2\2\2ki\3\2\2\2kl\3\2\2\2lz\3\2"+
		"\2\2mk\3\2\2\2no\7\61\2\2op\7,\2\2pt\3\2\2\2qs\13\2\2\2rq\3\2\2\2sv\3"+
		"\2\2\2tu\3\2\2\2tr\3\2\2\2uw\3\2\2\2vt\3\2\2\2wx\7,\2\2xz\7\61\2\2ye\3"+
		"\2\2\2yn\3\2\2\2z{\3\2\2\2{|\b\13\2\2|\26\3\2\2\2}\u0081\t\13\2\2~\u0080"+
		"\5\31\r\2\177~\3\2\2\2\u0080\u0083\3\2\2\2\u0081\177\3\2\2\2\u0081\u0082"+
		"\3\2\2\2\u0082\u0086\3\2\2\2\u0083\u0081\3\2\2\2\u0084\u0086\7\62\2\2"+
		"\u0085}\3\2\2\2\u0085\u0084\3\2\2\2\u0086\30\3\2\2\2\u0087\u0088\t\f\2"+
		"\2\u0088\32\3\2\2\2\24\2 /\669?HJTVZackty\u0081\u0085\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}