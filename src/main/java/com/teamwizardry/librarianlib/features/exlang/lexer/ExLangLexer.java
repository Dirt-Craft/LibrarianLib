/* The following code was generated by JFlex 1.6.1 */

package com.teamwizardry.librarianlib.features.exlang.lexer;

import java.util.LinkedList;
import org.jetbrains.annotations.NotNull;
import net.minecraft.util.ResourceLocation;

/**
 * This class is a simple example lexer.
 */
@SuppressWarnings("all")

public class ExLangLexer {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int YYINITIAL = 0;
  public static final int MACRO_DEFINITION_IDENTIFIER = 2;
  public static final int MACRO_DEFINITION_PARAMS_OR_BEGIN = 4;
  public static final int MACRO_DEFINITION_PARAM = 6;
  public static final int MACRO_DEFINITION_NEXT_PARAM_OR_END = 8;
  public static final int MACRO_DEFINITION_BEGIN = 10;
  public static final int LANG_BLOCK_OR_VALUE = 12;
  public static final int EXPRESSION = 14;
  public static final int BARE_EXPRESSION = 16;
  public static final int DOUBLE_QUOTED_EXPRESSION = 18;
  public static final int SINGLE_QUOTED_EXPRESSION = 20;
  public static final int MACRO_REFERENCE_PARAMS_OR_END = 22;
  public static final int MACRO_REFERENCE_NEXT_PARAM_OR_END = 24;
  public static final int PATH_BEGIN = 26;
  public static final int PATH_DOUBLE_QUOTE = 28;
  public static final int PATH_SINGLE_QUOTE = 30;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = { 
     0,  0,  1,  1,  2,  2,  3,  3,  4,  4,  5,  5,  6,  6,  7,  7, 
     8,  8,  9,  9, 10, 10, 11, 11, 12, 12, 13, 13, 14, 14, 15, 15
  };

  /** 
   * Translates characters to character classes
   */
  private static final String ZZ_CMAP_PACKED = 
    "\11\7\1\3\1\2\1\0\1\3\1\1\16\7\4\0\1\3\1\0"+
    "\1\21\1\12\1\41\2\0\1\22\1\30\1\32\1\5\1\0\1\31"+
    "\1\0\1\10\1\4\12\37\3\0\1\27\3\0\6\36\24\6\1\0"+
    "\1\34\2\0\1\6\1\0\3\40\1\23\1\24\1\25\2\11\1\13"+
    "\3\11\1\14\1\26\1\16\1\15\1\11\1\17\1\11\1\20\1\35"+
    "\5\11\1\33\1\0\1\42\1\0\41\7\2\0\4\6\4\0\1\6"+
    "\2\0\1\7\7\0\1\6\4\0\1\6\5\0\27\6\1\0\37\6"+
    "\1\0\u01ca\6\4\0\14\6\16\0\5\6\7\0\1\6\1\0\1\6"+
    "\21\0\160\7\5\6\1\0\2\6\2\0\4\6\10\0\1\6\1\0"+
    "\3\6\1\0\1\6\1\0\24\6\1\0\123\6\1\0\213\6\1\0"+
    "\5\7\2\0\236\6\11\0\46\6\2\0\1\6\7\0\47\6\7\0"+
    "\1\6\1\0\55\7\1\0\1\7\1\0\2\7\1\0\2\7\1\0"+
    "\1\7\10\0\33\6\5\0\3\6\15\0\5\7\6\0\1\6\4\0"+
    "\13\7\5\0\53\6\37\7\4\0\2\6\1\7\143\6\1\0\1\6"+
    "\10\7\1\0\6\7\2\6\2\7\1\0\4\7\2\6\12\7\3\6"+
    "\2\0\1\6\17\0\1\7\1\6\1\7\36\6\33\7\2\0\131\6"+
    "\13\7\1\6\16\0\12\7\41\6\11\7\2\6\4\0\1\6\5\0"+
    "\26\6\4\7\1\6\11\7\1\6\3\7\1\6\5\7\22\0\31\6"+
    "\3\7\104\0\1\6\1\0\13\6\67\0\33\7\1\0\4\7\66\6"+
    "\3\7\1\6\22\7\1\6\7\7\12\6\2\7\2\0\12\7\1\0"+
    "\7\6\1\0\7\6\1\0\3\7\1\0\10\6\2\0\2\6\2\0"+
    "\26\6\1\0\7\6\1\0\1\6\3\0\4\6\2\0\1\7\1\6"+
    "\7\7\2\0\2\7\2\0\3\7\1\6\10\0\1\7\4\0\2\6"+
    "\1\0\3\6\2\7\2\0\12\7\4\6\7\0\1\6\5\0\3\7"+
    "\1\0\6\6\4\0\2\6\2\0\26\6\1\0\7\6\1\0\2\6"+
    "\1\0\2\6\1\0\2\6\2\0\1\7\1\0\5\7\4\0\2\7"+
    "\2\0\3\7\3\0\1\7\7\0\4\6\1\0\1\6\7\0\14\7"+
    "\3\6\1\7\13\0\3\7\1\0\11\6\1\0\3\6\1\0\26\6"+
    "\1\0\7\6\1\0\2\6\1\0\5\6\2\0\1\7\1\6\10\7"+
    "\1\0\3\7\1\0\3\7\2\0\1\6\17\0\2\6\2\7\2\0"+
    "\12\7\1\0\1\6\17\0\3\7\1\0\10\6\2\0\2\6\2\0"+
    "\26\6\1\0\7\6\1\0\2\6\1\0\5\6\2\0\1\7\1\6"+
    "\7\7\2\0\2\7\2\0\3\7\10\0\2\7\4\0\2\6\1\0"+
    "\3\6\2\7\2\0\12\7\1\0\1\6\20\0\1\7\1\6\1\0"+
    "\6\6\3\0\3\6\1\0\4\6\3\0\2\6\1\0\1\6\1\0"+
    "\2\6\3\0\2\6\3\0\3\6\3\0\14\6\4\0\5\7\3\0"+
    "\3\7\1\0\4\7\2\0\1\6\6\0\1\7\16\0\12\7\11\0"+
    "\1\6\7\0\3\7\1\0\10\6\1\0\3\6\1\0\27\6\1\0"+
    "\12\6\1\0\5\6\3\0\1\6\7\7\1\0\3\7\1\0\4\7"+
    "\7\0\2\7\1\0\2\6\6\0\2\6\2\7\2\0\12\7\22\0"+
    "\2\7\1\0\10\6\1\0\3\6\1\0\27\6\1\0\12\6\1\0"+
    "\5\6\2\0\1\7\1\6\7\7\1\0\3\7\1\0\4\7\7\0"+
    "\2\7\7\0\1\6\1\0\2\6\2\7\2\0\12\7\1\0\2\6"+
    "\17\0\2\7\1\0\10\6\1\0\3\6\1\0\51\6\2\0\1\6"+
    "\7\7\1\0\3\7\1\0\4\7\1\6\10\0\1\7\10\0\2\6"+
    "\2\7\2\0\12\7\12\0\6\6\2\0\2\7\1\0\22\6\3\0"+
    "\30\6\1\0\11\6\1\0\1\6\2\0\7\6\3\0\1\7\4\0"+
    "\6\7\1\0\1\7\1\0\10\7\22\0\2\7\15\0\60\6\1\7"+
    "\2\6\7\7\4\0\10\6\10\7\1\0\12\7\47\0\2\6\1\0"+
    "\1\6\2\0\2\6\1\0\1\6\2\0\1\6\6\0\4\6\1\0"+
    "\7\6\1\0\3\6\1\0\1\6\1\0\1\6\2\0\2\6\1\0"+
    "\4\6\1\7\2\6\6\7\1\0\2\7\1\6\2\0\5\6\1\0"+
    "\1\6\1\0\6\7\2\0\12\7\2\0\4\6\40\0\1\6\27\0"+
    "\2\7\6\0\12\7\13\0\1\7\1\0\1\7\1\0\1\7\4\0"+
    "\2\7\10\6\1\0\44\6\4\0\24\7\1\0\2\7\5\6\13\7"+
    "\1\0\44\7\11\0\1\7\71\0\53\6\24\7\1\6\12\7\6\0"+
    "\6\6\4\7\4\6\3\7\1\6\3\7\2\6\7\7\3\6\4\7"+
    "\15\6\14\7\1\6\17\7\2\0\46\6\1\0\1\6\5\0\1\6"+
    "\2\0\53\6\1\0\u014d\6\1\0\4\6\2\0\7\6\1\0\1\6"+
    "\1\0\4\6\2\0\51\6\1\0\4\6\2\0\41\6\1\0\4\6"+
    "\2\0\7\6\1\0\1\6\1\0\4\6\2\0\17\6\1\0\71\6"+
    "\1\0\4\6\2\0\103\6\2\0\3\7\40\0\20\6\20\0\125\6"+
    "\14\0\u026c\6\2\0\21\6\1\0\32\6\5\0\113\6\3\0\3\6"+
    "\17\0\15\6\1\0\4\6\3\7\13\0\22\6\3\7\13\0\22\6"+
    "\2\7\14\0\15\6\1\0\3\6\1\0\2\7\14\0\64\6\40\7"+
    "\3\0\1\6\3\0\2\6\1\7\2\0\12\7\41\0\3\7\2\0"+
    "\12\7\6\0\130\6\10\0\51\6\1\7\1\6\5\0\106\6\12\0"+
    "\35\6\3\0\14\7\4\0\14\7\12\0\12\7\36\6\2\0\5\6"+
    "\13\0\54\6\4\0\21\7\7\6\2\7\6\0\12\7\46\0\27\6"+
    "\5\7\4\0\65\6\12\7\1\0\35\7\2\0\13\7\6\0\12\7"+
    "\15\0\1\6\130\0\5\7\57\6\21\7\7\6\4\0\12\7\21\0"+
    "\11\7\14\0\3\7\36\6\15\7\2\6\12\7\54\6\16\7\14\0"+
    "\44\6\24\7\10\0\12\7\3\0\3\6\12\7\44\6\122\0\3\7"+
    "\1\0\25\7\4\6\1\7\4\6\3\7\2\6\11\0\300\6\47\7"+
    "\25\0\4\7\u0116\6\2\0\6\6\2\0\46\6\2\0\6\6\2\0"+
    "\10\6\1\0\1\6\1\0\1\6\1\0\1\6\1\0\37\6\2\0"+
    "\65\6\1\0\7\6\1\0\1\6\3\0\3\6\1\0\7\6\3\0"+
    "\4\6\2\0\6\6\4\0\15\6\5\0\3\6\1\0\7\6\16\0"+
    "\5\7\32\0\5\7\20\0\2\6\23\0\1\6\13\0\5\7\5\0"+
    "\6\7\1\0\1\6\15\0\1\6\20\0\15\6\3\0\33\6\25\0"+
    "\15\7\4\0\1\7\3\0\14\7\21\0\1\6\4\0\1\6\2\0"+
    "\12\6\1\0\1\6\3\0\5\6\6\0\1\6\1\0\1\6\1\0"+
    "\1\6\1\0\4\6\1\0\13\6\2\0\4\6\5\0\5\6\4\0"+
    "\1\6\21\0\51\6\u0a77\0\57\6\1\0\57\6\1\0\205\6\6\0"+
    "\4\6\3\7\2\6\14\0\46\6\1\0\1\6\5\0\1\6\2\0"+
    "\70\6\7\0\1\6\17\0\1\7\27\6\11\0\7\6\1\0\7\6"+
    "\1\0\7\6\1\0\7\6\1\0\7\6\1\0\7\6\1\0\7\6"+
    "\1\0\7\6\1\0\40\7\57\0\1\6\u01d5\0\3\6\31\0\11\6"+
    "\6\7\1\0\5\6\2\0\5\6\4\0\126\6\2\0\2\7\2\0"+
    "\3\6\1\0\132\6\1\0\4\6\5\0\51\6\3\0\136\6\21\0"+
    "\33\6\65\0\20\6\u0200\0\u19b6\6\112\0\u51cd\6\63\0\u048d\6\103\0"+
    "\56\6\2\0\u010d\6\3\0\20\6\12\7\2\6\24\0\57\6\1\7"+
    "\4\0\12\7\1\0\31\6\7\0\1\7\120\6\2\7\45\0\11\6"+
    "\2\0\147\6\2\0\4\6\1\0\4\6\14\0\13\6\115\0\12\6"+
    "\1\7\3\6\1\7\4\6\1\7\27\6\5\7\20\0\1\6\7\0"+
    "\64\6\14\0\2\7\62\6\21\7\13\0\12\7\6\0\22\7\6\6"+
    "\3\0\1\6\4\0\12\7\34\6\10\7\2\0\27\6\15\7\14\0"+
    "\35\6\3\0\4\7\57\6\16\7\16\0\1\6\12\7\46\0\51\6"+
    "\16\7\11\0\3\6\1\7\10\6\2\7\2\0\12\7\6\0\27\6"+
    "\3\0\1\6\1\7\4\0\60\6\1\7\1\6\3\7\2\6\2\7"+
    "\5\6\2\7\1\6\1\7\1\6\30\0\3\6\2\0\13\6\5\7"+
    "\2\0\3\6\2\7\12\0\6\6\2\0\6\6\2\0\6\6\11\0"+
    "\7\6\1\0\7\6\221\0\43\6\10\7\1\0\2\7\2\0\12\7"+
    "\6\0\u2ba4\6\14\0\27\6\4\0\61\6\u2104\0\u016e\6\2\0\152\6"+
    "\46\0\7\6\14\0\5\6\5\0\1\6\1\7\12\6\1\0\15\6"+
    "\1\0\5\6\1\0\1\6\1\0\2\6\1\0\2\6\1\0\154\6"+
    "\41\0\u016b\6\22\0\100\6\2\0\66\6\50\0\15\6\3\0\20\7"+
    "\20\0\7\7\14\0\2\6\30\0\3\6\31\0\1\6\6\0\5\6"+
    "\1\0\207\6\2\0\1\7\4\0\1\6\13\0\12\7\7\0\32\6"+
    "\4\0\1\6\1\0\32\6\13\0\131\6\3\0\6\6\2\0\6\6"+
    "\2\0\6\6\2\0\3\6\3\0\2\6\3\0\2\6\22\0\3\7"+
    "\4\0\14\6\1\0\32\6\1\0\23\6\1\0\2\6\1\0\17\6"+
    "\2\0\16\6\42\0\173\6\105\0\65\6\210\0\1\7\202\0\35\6"+
    "\3\0\61\6\57\0\37\6\21\0\33\6\65\0\36\6\2\0\44\6"+
    "\4\0\10\6\1\0\5\6\52\0\236\6\2\0\12\7\u0356\0\6\6"+
    "\2\0\1\6\1\0\54\6\1\0\2\6\3\0\1\6\2\0\27\6"+
    "\252\0\26\6\12\0\32\6\106\0\70\6\6\0\2\6\100\0\1\6"+
    "\3\7\1\0\2\7\5\0\4\7\4\6\1\0\3\6\1\0\33\6"+
    "\4\0\3\7\4\0\1\7\40\0\35\6\203\0\66\6\12\0\26\6"+
    "\12\0\23\6\215\0\111\6\u03b7\0\3\7\65\6\17\7\37\0\12\7"+
    "\20\0\3\7\55\6\13\7\2\0\1\7\22\0\31\6\7\0\12\7"+
    "\6\0\3\7\44\6\16\7\1\0\12\7\100\0\3\7\60\6\16\7"+
    "\4\6\13\0\12\7\u04a6\0\53\6\15\7\10\0\12\7\u0936\0\u036f\6"+
    "\221\0\143\6\u0b9d\0\u042f\6\u33d1\0\u0239\6\u04c7\0\105\6\13\0\1\6"+
    "\56\7\20\0\4\7\15\6\u4060\0\2\6\u2163\0\5\7\3\0\26\7"+
    "\2\0\7\7\36\0\4\7\224\0\3\7\u01bb\0\125\6\1\0\107\6"+
    "\1\0\2\6\2\0\1\6\2\0\2\6\2\0\4\6\1\0\14\6"+
    "\1\0\1\6\1\0\7\6\1\0\101\6\1\0\4\6\2\0\10\6"+
    "\1\0\7\6\1\0\34\6\1\0\4\6\1\0\5\6\1\0\1\6"+
    "\3\0\7\6\1\0\u0154\6\2\0\31\6\1\0\31\6\1\0\37\6"+
    "\1\0\31\6\1\0\37\6\1\0\31\6\1\0\37\6\1\0\31\6"+
    "\1\0\37\6\1\0\31\6\1\0\10\6\2\0\62\7\u1600\0\4\6"+
    "\1\0\33\6\1\0\2\6\1\0\1\6\2\0\1\6\1\0\12\6"+
    "\1\0\4\6\1\0\1\6\1\0\1\6\6\0\1\6\4\0\1\6"+
    "\1\0\1\6\1\0\1\6\1\0\3\6\1\0\2\6\1\0\1\6"+
    "\2\0\1\6\1\0\1\6\1\0\1\6\1\0\1\6\1\0\1\6"+
    "\1\0\2\6\1\0\1\6\2\0\4\6\1\0\7\6\1\0\4\6"+
    "\1\0\4\6\1\0\1\6\1\0\12\6\1\0\21\6\5\0\3\6"+
    "\1\0\5\6\1\0\21\6\u1144\0\ua6d7\6\51\0\u1035\6\13\0\336\6"+
    "\u3fe2\0\u021e\6\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\u05ee\0"+
    "\1\7\36\0\140\7\200\0\360\7\uffff\0\uffff\0\ufe12\0";

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\20\0\2\1\1\0\1\2\1\0\1\3\1\4\1\5"+
    "\1\6\1\7\1\10\1\11\1\12\1\13\1\1\1\14"+
    "\1\15\1\16\2\17\1\20\1\0\1\21\1\16\3\0"+
    "\1\16\2\22\1\23\1\24\1\25\1\26\1\27\1\30"+
    "\1\31\1\1\3\0\2\32\1\33\1\0\1\34\11\0"+
    "\1\35\4\0\1\36\5\0\1\37\1\40\1\41";

  private static int [] zzUnpackAction() {
    int [] result = new int[85];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** 
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\43\0\106\0\151\0\214\0\257\0\322\0\365"+
    "\0\u0118\0\u013b\0\u015e\0\u0181\0\u01a4\0\u01c7\0\u01ea\0\u020d"+
    "\0\u0230\0\u0253\0\u0276\0\u0299\0\u02bc\0\u0253\0\u02df\0\u0253"+
    "\0\u0253\0\u0302\0\u0253\0\u0253\0\u0253\0\u0253\0\u0325\0\u0253"+
    "\0\u0253\0\u0348\0\u036b\0\u0253\0\u0253\0\u038e\0\u03b1\0\u03d4"+
    "\0\u03f7\0\u041a\0\u043d\0\u0460\0\u0253\0\u0483\0\u0253\0\u0253"+
    "\0\u0253\0\u0253\0\u0253\0\u0253\0\u0253\0\u04a6\0\u04c9\0\u04ec"+
    "\0\u050f\0\u0253\0\u0532\0\u0555\0\u0578\0\u041a\0\u059b\0\u05be"+
    "\0\u0483\0\u05e1\0\u0604\0\u0627\0\u064a\0\u066d\0\u0690\0\u05be"+
    "\0\u06b3\0\u06d6\0\u06f9\0\u071c\0\u0253\0\u073f\0\u0762\0\u0785"+
    "\0\u07a8\0\u07cb\0\u0253\0\u0253\0\u0253";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[85];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /** 
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\0\1\21\2\22\1\23\3\0\2\24\1\25\6\24"+
    "\2\0\4\24\4\0\1\26\1\0\1\24\2\0\1\24"+
    "\3\0\1\21\2\22\1\23\1\0\1\27\2\0\1\27"+
    "\1\0\6\27\2\0\4\27\6\0\2\27\1\0\2\27"+
    "\2\0\1\21\2\22\1\23\22\0\1\30\1\31\13\0"+
    "\1\21\2\22\1\23\1\0\1\32\2\0\1\32\1\0"+
    "\6\32\2\0\4\32\6\0\2\32\1\0\2\32\2\0"+
    "\1\21\2\22\1\23\24\0\1\33\1\34\11\0\1\21"+
    "\2\22\1\23\22\0\1\30\14\0\1\21\2\22\1\23"+
    "\22\0\1\35\3\0\1\26\7\0\3\36\1\37\15\36"+
    "\1\40\1\41\20\36\1\42\1\43\1\44\26\42\1\45"+
    "\2\42\1\46\4\42\1\47\1\42\1\50\1\51\1\52"+
    "\16\50\1\44\7\50\1\45\2\50\1\53\4\50\1\47"+
    "\1\50\1\54\1\51\1\52\17\54\1\44\6\54\1\45"+
    "\2\54\1\53\4\54\1\47\1\54\3\55\1\56\24\55"+
    "\1\57\12\55\1\0\1\21\2\22\1\23\24\0\1\60"+
    "\1\61\11\0\1\21\2\22\1\23\14\0\1\62\1\63"+
    "\20\0\1\64\2\0\16\64\1\65\22\64\2\0\17\64"+
    "\1\65\20\64\2\0\1\22\107\0\1\66\1\67\45\0"+
    "\2\24\1\0\6\24\2\0\4\24\6\0\1\24\2\0"+
    "\1\24\15\0\1\70\7\0\1\71\25\0\2\27\1\0"+
    "\1\27\1\0\6\27\2\0\4\27\6\0\5\27\7\0"+
    "\2\32\1\0\1\32\1\0\6\32\2\0\4\32\6\0"+
    "\5\32\4\0\1\37\37\0\1\42\2\0\26\42\1\0"+
    "\2\42\1\0\4\42\1\0\1\42\2\0\1\44\40\0"+
    "\1\72\1\51\1\52\32\72\1\73\5\72\6\0\1\74"+
    "\2\0\1\74\1\0\6\74\2\0\4\74\4\0\1\75"+
    "\1\0\2\74\1\0\2\74\1\0\1\50\2\0\16\50"+
    "\1\0\7\50\1\0\2\50\1\0\4\50\1\0\1\50"+
    "\2\0\1\52\1\76\42\0\1\76\37\0\1\72\1\77"+
    "\1\100\32\72\1\73\5\72\1\54\2\0\17\54\1\0"+
    "\6\54\1\0\2\54\1\0\4\54\1\0\1\54\3\0"+
    "\1\101\24\0\1\57\12\0\1\66\1\21\1\22\40\66"+
    "\5\102\1\103\35\102\14\0\1\104\52\0\1\105\41\0"+
    "\3\106\10\0\3\106\10\0\2\74\1\0\1\74\1\0"+
    "\6\74\2\0\4\74\6\0\5\74\7\0\1\107\2\0"+
    "\1\107\1\0\6\107\2\0\4\107\6\0\2\107\1\0"+
    "\2\107\3\0\1\100\1\110\42\0\1\110\37\0\5\102"+
    "\1\111\35\102\4\0\1\22\1\103\52\0\1\112\52\0"+
    "\1\113\40\0\3\114\10\0\3\114\10\0\2\107\1\0"+
    "\1\107\1\0\6\107\2\0\4\107\6\0\5\107\1\115"+
    "\4\102\1\22\1\111\35\102\16\0\1\116\37\0\1\117"+
    "\52\0\3\120\10\0\3\120\21\0\1\121\51\0\1\122"+
    "\37\0\3\123\10\0\3\123\22\0\1\124\46\0\1\125"+
    "\16\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[2030];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /* error messages for the codes above */
  private static final String ZZ_ERROR_MSG[] = {
    "Unknown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\20\0\1\1\1\11\1\0\1\1\1\0\1\11\1\1"+
    "\2\11\1\1\4\11\1\1\2\11\2\1\2\11\1\0"+
    "\2\1\3\0\1\1\1\11\1\1\7\11\1\1\3\0"+
    "\1\11\2\1\1\0\1\1\11\0\1\1\4\0\1\11"+
    "\5\0\3\11";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[85];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the input device */
  private java.io.Reader zzReader;

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private char zzBuffer[] = new char[ZZ_BUFFERSIZE];

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /** number of newlines encountered up to the start of the matched text */
  private int yyline;

  /** the number of characters up to the start of the matched text */
  private int yychar;

  /**
   * the number of characters from the last newline up to the start of the 
   * matched text
   */
  private int yycolumn;

  /** 
   * zzAtBOL == true <=> the scanner is currently at the beginning of a line
   */
  private boolean zzAtBOL = true;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /** denotes if the user-EOF-code has already been executed */
  private boolean zzEOFDone;
  
  /** 
   * The number of occupied positions in zzBuffer beyond zzEndRead.
   * When a lead/high surrogate has been read from the input stream
   * into the final zzBuffer position, this will have a value of 1;
   * otherwise, it will have a value of 0.
   */
  private int zzFinalHighSurrogate = 0;

  /* user code: */
    public ResourceLocation file = new ResourceLocation("missingno");

    private ExLangToken peekedToken = null;

    public @NotNull ExLangToken nextToken() throws java.io.IOException {
        if(peekedToken != null) {
            ExLangToken token = peekedToken;
            peekedToken = null;
            return token;
        } else {
            return yylex();
        }
    }

    public @NotNull ExLangToken peekToken() throws java.io.IOException {
        if(peekedToken == null) {
            peekedToken = yylex();
        }
        return peekedToken;
    }

    private LinkedList<Integer> stateStack = new LinkedList<>();

    private ExLangToken symbol(ExLangSymbol type) {
        return new ExLangToken(type, file, yyline, yycolumn, "");
    }
    private ExLangToken symbol(ExLangSymbol type, String value) {
        return new ExLangToken(type, file, yyline, yycolumn, value);
    }

    private void pushState() {
        stateStack.push(yystate());
    }

    private void pushState(int state) {
        stateStack.push(state);
    }

    private void popState() {
        if(!stateStack.isEmpty()) {
            yybegin(stateStack.pop());
        }
    }

    private boolean _expressionEndOnComma = false;


  /**
   * Creates a new scanner
   *
   * @param   in  the java.io.Reader to read input from.
   */
  public ExLangLexer(java.io.Reader in) {
    this.zzReader = in;
  }


  /** 
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] zzUnpackCMap(String packed) {
    char [] map = new char[0x110000];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < 2824) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }


  /**
   * Refills the input buffer.
   *
   * @return      <code>false</code>, iff there was new input.
   * 
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {

    /* first: make room (if you can) */
    if (zzStartRead > 0) {
      zzEndRead += zzFinalHighSurrogate;
      zzFinalHighSurrogate = 0;
      System.arraycopy(zzBuffer, zzStartRead,
                       zzBuffer, 0,
                       zzEndRead-zzStartRead);

      /* translate stored positions */
      zzEndRead-= zzStartRead;
      zzCurrentPos-= zzStartRead;
      zzMarkedPos-= zzStartRead;
      zzStartRead = 0;
    }

    /* is the buffer big enough? */
    if (zzCurrentPos >= zzBuffer.length - zzFinalHighSurrogate) {
      /* if not: blow it up */
      char newBuffer[] = new char[zzBuffer.length*2];
      System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
      zzBuffer = newBuffer;
      zzEndRead += zzFinalHighSurrogate;
      zzFinalHighSurrogate = 0;
    }

    /* fill the buffer with new input */
    int requested = zzBuffer.length - zzEndRead;
    int numRead = zzReader.read(zzBuffer, zzEndRead, requested);

    /* not supposed to occur according to specification of java.io.Reader */
    if (numRead == 0) {
      throw new java.io.IOException("Reader returned 0 characters. See JFlex examples for workaround.");
    }
    if (numRead > 0) {
      zzEndRead += numRead;
      /* If numRead == requested, we might have requested to few chars to
         encode a full Unicode character. We assume that a Reader would
         otherwise never return half characters. */
      if (numRead == requested) {
        if (Character.isHighSurrogate(zzBuffer[zzEndRead - 1])) {
          --zzEndRead;
          zzFinalHighSurrogate = 1;
        }
      }
      /* potentially more input available */
      return false;
    }

    /* numRead < 0 ==> end of stream */
    return true;
  }

    
  /**
   * Closes the input stream.
   */
  public final void yyclose() throws java.io.IOException {
    zzAtEOF = true;            /* indicate end of file */
    zzEndRead = zzStartRead;  /* invalidate buffer    */

    if (zzReader != null)
      zzReader.close();
  }


  /**
   * Resets the scanner to read from a new input stream.
   * Does not close the old reader.
   *
   * All internal variables are reset, the old input stream 
   * <b>cannot</b> be reused (internal buffer is discarded and lost).
   * Lexical state is set to <tt>ZZ_INITIAL</tt>.
   *
   * Internal scan buffer is resized down to its initial length, if it has grown.
   *
   * @param reader   the new input stream 
   */
  public final void yyreset(java.io.Reader reader) {
    zzReader = reader;
    zzAtBOL  = true;
    zzAtEOF  = false;
    zzEOFDone = false;
    zzEndRead = zzStartRead = 0;
    zzCurrentPos = zzMarkedPos = 0;
    zzFinalHighSurrogate = 0;
    yyline = yychar = yycolumn = 0;
    zzLexicalState = YYINITIAL;
    if (zzBuffer.length > ZZ_BUFFERSIZE)
      zzBuffer = new char[ZZ_BUFFERSIZE];
  }


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final String yytext() {
    return new String( zzBuffer, zzStartRead, zzMarkedPos-zzStartRead );
  }


  /**
   * Returns the character at position <tt>pos</tt> from the 
   * matched text. 
   * 
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch. 
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBuffer[zzStartRead+pos];
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of 
   * yypushback(int) and a match-all fallback rule) this method 
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  } 


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public ExLangToken yylex() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char [] zzBufferL = zzBuffer;
    char [] zzCMapL = ZZ_CMAP;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      boolean zzR = false;
      int zzCh;
      int zzCharCount;
      for (zzCurrentPosL = zzStartRead  ;
           zzCurrentPosL < zzMarkedPosL ;
           zzCurrentPosL += zzCharCount ) {
        zzCh = Character.codePointAt(zzBufferL, zzCurrentPosL, zzMarkedPosL);
        zzCharCount = Character.charCount(zzCh);
        switch (zzCh) {
        case '\u000B':
        case '\u000C':
        case '\u0085':
        case '\u2028':
        case '\u2029':
          yyline++;
          yycolumn = 0;
          zzR = false;
          break;
        case '\r':
          yyline++;
          yycolumn = 0;
          zzR = true;
          break;
        case '\n':
          if (zzR)
            zzR = false;
          else {
            yyline++;
            yycolumn = 0;
          }
          break;
        default:
          zzR = false;
          yycolumn += zzCharCount;
        }
      }

      if (zzR) {
        // peek one character ahead if it is \n (if we have counted one line too much)
        boolean zzPeek;
        if (zzMarkedPosL < zzEndReadL)
          zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        else if (zzAtEOF)
          zzPeek = false;
        else {
          boolean eof = zzRefill();
          zzEndReadL = zzEndRead;
          zzMarkedPosL = zzMarkedPos;
          zzBufferL = zzBuffer;
          if (eof) 
            zzPeek = false;
          else 
            zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        }
        if (zzPeek) yyline--;
      }
      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;
  
      zzState = ZZ_LEXSTATE[zzLexicalState];

      // set up zzAction for empty match case:
      int zzAttributes = zzAttrL[zzState];
      if ( (zzAttributes & 1) == 1 ) {
        zzAction = zzState;
      }


      zzForAction: {
        while (true) {
    
          if (zzCurrentPosL < zzEndReadL) {
            zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL, zzEndReadL);
            zzCurrentPosL += Character.charCount(zzInput);
          }
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL, zzEndReadL);
              zzCurrentPosL += Character.charCount(zzInput);
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMapL[zzInput] ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
        zzAtEOF = true;
          {     return symbol(ExLangSymbol.BLOCK_END);
 }
      }
      else {
        switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
          case 1: 
            { /* ignore */
            }
          case 34: break;
          case 2: 
            { yybegin(LANG_BLOCK_OR_VALUE);
    return symbol(ExLangSymbol.LANG_KEY, yytext());
            }
          case 35: break;
          case 3: 
            { yybegin(YYINITIAL); // blocks parse identically to the root, any difference is handled in the parser
        return symbol(ExLangSymbol.BLOCK_BEGIN);
            }
          case 36: break;
          case 4: 
            { yybegin(MACRO_DEFINITION_PARAMS_OR_BEGIN);
    return symbol(ExLangSymbol.IDENTIFIER, yytext());
            }
          case 37: break;
          case 5: 
            { pushState(YYINITIAL);
        yybegin(EXPRESSION);
            }
          case 38: break;
          case 6: 
            { yybegin(MACRO_DEFINITION_PARAM);
        return symbol(ExLangSymbol.MACRO_DEFINITION_PARAMS_BEGIN);
            }
          case 39: break;
          case 7: 
            { yybegin(MACRO_DEFINITION_NEXT_PARAM_OR_END);
    return symbol(ExLangSymbol.IDENTIFIER, yytext());
            }
          case 40: break;
          case 8: 
            { yybegin(MACRO_DEFINITION_PARAM);
            }
          case 41: break;
          case 9: 
            { yybegin(MACRO_DEFINITION_BEGIN);
        return symbol(ExLangSymbol.MACRO_DEFINITION_PARAMS_END);
            }
          case 42: break;
          case 10: 
            { pushState(YYINITIAL);
        yybegin(EXPRESSION);
        return symbol(ExLangSymbol.EXPRESSION_BEGIN);
            }
          case 43: break;
          case 11: 
            { yypushback(1);
        yybegin(BARE_EXPRESSION);
        return symbol(ExLangSymbol.EXPRESSION_BEGIN);
            }
          case 44: break;
          case 12: 
            { yybegin(DOUBLE_QUOTED_EXPRESSION);
        return symbol(ExLangSymbol.EXPRESSION_BEGIN);
            }
          case 45: break;
          case 13: 
            { yybegin(SINGLE_QUOTED_EXPRESSION);
        return symbol(ExLangSymbol.EXPRESSION_BEGIN);
            }
          case 46: break;
          case 14: 
            { return symbol(ExLangSymbol.STRING, yytext());
            }
          case 47: break;
          case 15: 
            { _expressionEndOnComma = false;

        popState();
        return symbol(ExLangSymbol.EXPRESSION_END);
            }
          case 48: break;
          case 16: 
            { if(_expressionEndOnComma) {
            _expressionEndOnComma = false;
            yypushback(1);

            popState();
            return symbol(ExLangSymbol.EXPRESSION_END);
        } else {
            return symbol(ExLangSymbol.STRING, ",");
        }
            }
          case 49: break;
          case 17: 
            { return symbol(ExLangSymbol.STRING, "$");
            }
          case 50: break;
          case 18: 
            { popState();
            }
          case 51: break;
          case 19: 
            { pushState(MACRO_REFERENCE_NEXT_PARAM_OR_END);

        _expressionEndOnComma = true;
        yybegin(EXPRESSION);
        return symbol(ExLangSymbol.MACRO_REFERENCE_PARAMS_BEGIN);
            }
          case 52: break;
          case 20: 
            { pushState();

        _expressionEndOnComma = true;
        yybegin(EXPRESSION);
            }
          case 53: break;
          case 21: 
            { popState();
        return symbol(ExLangSymbol.MACRO_REFERENCE_PARAMS_END);
            }
          case 54: break;
          case 22: 
            { yybegin(PATH_DOUBLE_QUOTE);
            }
          case 55: break;
          case 23: 
            { yybegin(PATH_SINGLE_QUOTE);
            }
          case 56: break;
          case 24: 
            { return symbol(ExLangSymbol.PATH_COMPONENT, yytext());
            }
          case 57: break;
          case 25: 
            { yybegin(YYINITIAL);
        return symbol(ExLangSymbol.PATH_END);
            }
          case 58: break;
          case 26: 
            { return symbol(ExLangSymbol.ESCAPED_CHARACTER, yytext().substring(1));
            }
          case 59: break;
          case 27: 
            { pushState();
        yybegin(MACRO_REFERENCE_PARAMS_OR_END);
        return symbol(ExLangSymbol.MACRO_REFERENCE, yytext().substring(1));
            }
          case 60: break;
          case 28: 
            { return symbol(ExLangSymbol.STRING, yytext().replaceAll("\n\\s+", "\n"));
            }
          case 61: break;
          case 29: 
            { return symbol(ExLangSymbol.STRING, yytext().replaceAll("\n\\s+", ""));
            }
          case 62: break;
          case 30: 
            { pushState();
        yybegin(MACRO_REFERENCE_PARAMS_OR_END);
        return symbol(ExLangSymbol.MACRO_REFERENCE, yytext().substring(2, yytext().length()-1));
            }
          case 63: break;
          case 31: 
            { return symbol(ExLangSymbol.ESCAPED_CODEPOINT, yytext().substring(2));
            }
          case 64: break;
          case 32: 
            { yybegin(PATH_BEGIN);
    return symbol(ExLangSymbol.IMPORT_BEGIN);
            }
          case 65: break;
          case 33: 
            { yybegin(MACRO_DEFINITION_IDENTIFIER);
    return symbol(ExLangSymbol.MACRO_DEFINE);
            }
          case 66: break;
          default:
            zzScanError(ZZ_NO_MATCH);
        }
      }
    }
  }


}
