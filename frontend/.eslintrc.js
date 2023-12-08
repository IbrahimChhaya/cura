// https://eslint.org/docs/user-guide/configuring

module.exports = {
  root: true,
  parserOptions: {
    parser: 'babel-eslint'
  },
  env: {
    browser: true,
  },
  extends: [
    // https://github.com/vuejs/eslint-plugin-vue#priority-a-essential-error-prevention
    // consider switching to `plugin:vue/strongly-recommended` or `plugin:vue/recommended` for stricter rules.
    'plugin:vue/essential', 
    // https://github.com/standard/standard/blob/master/docs/RULES-en.md
    'standard'
  ],
  // required to lint *.vue files
  plugins: [
    'vue'
  ],
  // add your custom rules here
  rules: {
    // allow async-await
    'brace-style':                 'off',
    'comma-dangle':                'off',
    'generator-star-spacing':      'off',
    'indent':                      0,
    'key-spacing':                 'off',
    'no-multi-spaces':             'off',
    'padded-blocks':               'off',
    'quotes':                      'off',
    'spacing':                     'off',
    'space-before-function-paren': 0,
    'array-callback-return':       'off',
    'no-prototype-builtins':       'off',
    'no-tabs':                     0,
    'space-before-blocks':         0,
    'arrow-spacing':               0,
    'eol-last':                    0,
    // allow debugger during development
    'no-debugger': process.env.NODE_ENV === 'production' ? 'error' : 'off'
  }
}
