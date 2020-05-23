// For authoring Nightwatch tests, see
// http://nightwatchjs.org/guide#usage

module.exports = {
  'default e2e tests': function (browser) {
    // automatically uses dev Server port from /config.index.js
    // default: http://localhost:8080
    // see nightwatch.conf.js
    const devServer = browser.globals.devServerURL

    browser
      .url(devServer)
      .waitForElementVisible('#app', 5000)
      .assert.elementPresent('.navbar')
      .assert.elementPresent('.hero')
      .assert.elementPresent('.no-shadow')
      .assert.containsText('div.jeuxExlu', 'Le seul site où vous pouvez trouver des consoles de jeux exclusives !')
      .assert.containsText('h1', 'Captain Escroc Market')
      .assert.elementCount('figure.image.is-4by5', 2)
      .end()
  }
}
