(function (global, factory) {
  if (typeof define === "function" && define.amd) {
    define(["exports"], factory);
  } else if (typeof exports !== "undefined") {
    factory(exports);
  } else {
    var mod = {
      exports: {}
    };
    factory(mod.exports);
    global.xregexpQuotemeta = mod.exports;
  }
})(typeof globalThis !== "undefined" ? globalThis : typeof self !== "undefined" ? self : this, function (_exports) {
  "use strict";

  Object.defineProperty(_exports, "__esModule", {
    value: true
  });
  _exports.addSupportTo = addSupportTo;

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
  function addSupportTo(XRegExp) {
    // See https://github.com/slevithan/xregexp/issues/85#issuecomment-108592346
    XRegExp.addToken(/\\Q([\s\S]*?)(?:\\E|$)/, function (match) {
      return XRegExp.escape(match[1]);
    }, {
      scope: 'all',
      leadChar: '\\'
    });
  }
});
