package com.teamwizardry.librarianlib.features.lang.lexer

enum class LangSymbol {
    LANG_KEY,

    BLOCK_BEGIN,
    BLOCK_END,

    MACRO_DEFINE,
    IDENTIFIER,
    MACRO_DEFINITION_PARAMS_BEGIN,
    MACRO_DEFINITION_PARAMS_END,

    EXPRESSION_BEGIN,
    STRING,
    ESCAPED_CHARACTER,
    ESCAPED_CODEPOINT,
    MACRO_REFERENCE,
    MACRO_REFERENCE_PARAMS_BEGIN,
    MACRO_REFERENCE_PARAMS_END,
    EXPRESSION_END;
}
