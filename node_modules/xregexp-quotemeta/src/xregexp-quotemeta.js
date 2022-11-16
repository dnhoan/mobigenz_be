/*!
 * quotemeta support for xregexp
 * <https://github.com/mintern/xregexp-quotemeta>
 * Brandon Mintern (c) 2016 MIT License
 * Steven Levithan (c) 2015 MIT License
 */

/**
 * Adds support for \Q..\E escape sequences, used in Java and Perl regular
 * expressions. All intervening characters are interpreted as literals instead
 * of special regex characters.
 */
export function addSupportTo(XRegExp) {
    // See https://github.com/slevithan/xregexp/issues/85#issuecomment-108592346
    XRegExp.addToken(/\\Q([\s\S]*?)(?:\\E|$)/, match => XRegExp.escape(match[1]), {
        scope: 'all',
        leadChar: '\\'
    });
}
