xregexp-quotemeta 1.0.7
====================================

xregexp-quotemeta augments [XRegExp](http://xregexp.com/) to support the [`\Q..\E`](http://www.regular-expressions.info/characters.html#special) construct for escaping special regular expression characters.
The functionality is added as an [XRegExp addon](http://xregexp.com/plugins/).
The specific semantics of quotemeta support are described in [slevithan/xregexp#85](https://github.com/slevithan/xregexp/issues/85).

## Installation and usage

In browsers (bundle XRegExp with all of its addons):

```html
<script src="xregexp-all.js"></script>
<script src="xregexp-quotemeta-inject.js"></script>
```

Using [npm](https://www.npmjs.com/):

```bash
npm install xregexp-quotemeta
```

In [Node.js](http://nodejs.org/):

```js
const XRegExp = require('xregexp');
const quotemeta = require('xregexp-quotemeta');
quotemeta.addSupportTo(XRegExp);
```

In an AMD loader like [RequireJS](http://requirejs.org/):

```js
require(['xregexp', 'xregexp-quotemeta'], function(XRegExp, quotemeta) {
    quotemeta.addSupportTo(XRegExp);
});
```

## About

xregexp-quotemeta copyright 2018 by Brandon Mintern and copyright 2015 by [Steven Levithan](http://stevenlevithan.com/).

All code, including addons, tools, and tests, is released under the terms of the [MIT License](http://mit-license.org/).
