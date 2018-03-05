parser grammar ExLangParser;

options { tokenVocab=ExLangLexer; }

//start_rule: file;

file        : element* EOF;

element : (comment | directive | entry | block);

comment: COMMENT;

entry: LANGUAGE_KEY EQUALS expression;

block: LANGUAGE_KEY? BLOCK_BEGIN element* BLOCK_END;

directive: undefineDirective | defineVarDirective | defineFuncDirective | importDirective;

importDirective: IMPORT STRING;
undefineDirective: UNDEFINE IDENTIFIER;
defineVarDirective: DEFINE IDENTIFIER EQUALS expression;
defineFuncDirective: DEFINE IDENTIFIER PARAMS_BEGIN defineParams PARAMS_END EQUALS expression;
defineParams: IDENTIFIER (PARAM_SEPARATOR IDENTIFIER)*;

expression: (stringExpression | macroCallExpression | macroReferenceExpression);//(macroReference | macroCall | string);

stringExpression: STRING_BEGIN (ESCAPE_SEQUENCE | ESCAPE_UNICODE | TEXT | macroCallExpression | macroReferenceExpression)* STRING_END;
macroCallExpression: MACRO_CALL (expression (PARAM_SEPARATOR expression)*)? PARAMS_END;
macroReferenceExpression: MACRO_REF;

