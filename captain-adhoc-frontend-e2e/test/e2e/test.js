module.exports = {
    'step one: navigate to home': function (browser) {
      browser
        .url(browser.launch_url)
        .waitForElementVisible('#app', 5000)
        .assert.elementPresent('.navbar')
        .assert.elementPresent('.hero')
        .assert.elementPresent('.no-shadow')
        .assert.elementCount('figure.image.is-4by5', 2)
        .assert.containsText('div.jeuxExlu', 'Le seul site où vous pouvez trouver des consoles de jeux exclusives !')
        .assert.containsText('h1', 'Captain Escroc Market')
    },
  
    'step two: click on sign-in button and navigate to sign-in page': function (browser) {
      browser
        .assert.elementPresent('a[href="#/sign-up"]')
        .click('a[href="#/sign-up"]')
        .waitForElementVisible('form', 1000)
        .assert.urlEquals(`${browser.launch_url}/#/sign-up`)
    },
  
    'step three: Fill fields correctly to signup and tick submit button': function (browser) {
      browser
        .setValue('#firstname', 'Jean')
        .setValue('#lastname', 'PeuxPlus')
        .setValue('#username', 'JppDuJt')
        .setValue('#password', 'password')
        .setValue('#repeatPassword', 'password')
        .click('button[type=submit]')
        .waitForElementVisible('form[id="login"]', 10000)
        .assert.urlEquals(`${browser.launch_url}/#/log-in`)
    },
    'step four: Fill fields correctly to login and tick submit button': function (browser) {
      browser
        .setValue('#username', 'JppDuJt')
        .setValue('#password', 'password')
        .click('button[type=submit]')
        .waitForElementVisible('div.hero-like', 10000)
        .assert.urlEquals(`${browser.launch_url}/#/products`)
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
    },
    'step six: Navigate to user basket': function (browser) {
      browser
        .click('button.is-success')
        .pause(1000)
        .click('a[href="#/cart"]')
        .waitForElementVisible('div.cart', 10000)
        .assert.urlEquals(`${browser.launch_url}/#/cart`)
    },
    'step seven: Unfold our product': function (browser) {
      browser
        .click('header.card-header')
        .assert.elementPresent('figure.image')
        .assert.elementPresent('table.table')
    },
    'step height: Click on pay our basket': function (browser) {
      browser
        .useXpath()
        .click('//p[text()="Payez votre panier"]')
        .useCss()
        .waitForElementVisible('input#credit-card')
        .assert.urlEquals(`${browser.launch_url}/#/payment`)
    },
    'step nine: Fill the payment form': function (browser) {
      browser
        .assert.elementPresent('input[id="credit-card"]')
        .assert.elementPresent('select[id="select-Month"]')
        .assert.elementPresent('select[id="select-Year"]')
        .assert.elementPresent('input[id="cvc"]')

        .setValue('input[id="credit-card"]', '5279461390734033')
        .setValue('input[id="cvc"]', '123')

        .click('select[id="select-Month"] option[value="1"]')
        .click('select[id="select-Year"] option[value="2030"]')
        .useXpath()
        .click('//button[text()="Payez votre panier"]')
        .useCss()
        .waitForElementVisible('div.modal-card', 2000)  
    },
    'step ten: Thanks modal open on valid infos': function (browser) {
      browser
        .assert.elementPresent('button[name="Retourner aux escroqueries"]')
    },
    'step eleven (last): Be redirect automatically after some seconds': function (browser) {
      browser
      .waitForElementVisible('div.hero-like', 15000)
      .assert.urlEquals(`${browser.launch_url}/#/products`)
      .end()
    }
  }
  