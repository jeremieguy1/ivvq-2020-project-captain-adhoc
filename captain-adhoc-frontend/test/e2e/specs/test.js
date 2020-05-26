// For authoring Nightwatch tests, see
// http://nightwatchjs.org/guide#usage

module.exports = {
  // automatically uses dev Server port from /config.index.js
  // default: http://localhost:8080
  // see nightwatch.conf.js
  'step one: navigate to home': function (browser) {
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
  },

  'step two: click on sign-in button and navigate to sign-in page': function (browser) {
    browser
      .assert.elementPresent('a[href="#/sign-up"]')
      .click('a[href="#/sign-up"]')
      .waitForElementVisible('form', 1000)
      .assert.urlEquals(browser.globals.devServerURL + '/#/sign-up')
  },

  'step three: Fill fields correctly to signup and tick submit button': function (browser) {
    browser
      .setValue('#firstname', 'Jean')
      .setValue('#lastname', 'PeuxPlus')
      .setValue('#username', 'JppDuJt')
      .setValue('#password', 'password')
      .setValue('#repeatPassword', 'password')
      .click('button[type=submit]')
      .waitForElementVisible('form[id="login"]', 5000)
      .assert.urlEquals(browser.globals.devServerURL + '/#/log-in')
  },
  'step four: Fill fields correctly to login and tick submit button': function (browser) {
    browser
      .setValue('#username', 'JppDuJt')
      .setValue('#password', 'password')
      .click('button[type=submit]')
      .waitForElementVisible('div.hero-like', 5000)
      .assert.urlEquals(browser.globals.devServerURL + '/#/products')
      .assert.elementPresent('a[href="#/products"]')
      .assert.elementPresent('a[href="#/cart"]')
      .assert.elementPresent('a[href="#/purchases-history"]')
      .assert.containsText('button.navbar-item', 'Déconnexion')
  },
  'step five: Add a product to user basket by details modal': function (browser) {
    browser
      .click('div.card')
      .waitForElementVisible('div.modal-card', 2000)
      .assert.elementCount('Header', 1)
      .assert.elementCount('div.corps', 1)
      .assert.elementCount('footer', 1)
      .assert.elementPresent('button.is-success')
      .assert.containsText('button[name="Annuler"]', 'Annuler')
      .click('button.is-success')
  },
  'step six: Navigate to user basket': function (browser) {
    browser
      .click('button.is-success')
      .pause(500)
      .click('a[href="#/cart"]')
      .waitForElementVisible('div.cart', 5000)
      .assert.urlEquals(browser.globals.devServerURL + '/#/cart')
  },
  'step seven: Unfold our product': function (browser) {
    browser
      .click('header.card-header')
      .assert.elementPresent('figure.image')
      .assert.elementPresent('table')
  },
  'step heigh: Pay our basket': function (browser) {
    browser
      .useXpath()
      .click('//p[text()="Payez votre panier"]')
      .useCss()
      .end()
  }
}
