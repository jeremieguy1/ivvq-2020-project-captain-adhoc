export default {
  addToCart (listCommandProduct, product) {
    if (listCommandProduct === null) {
      listCommandProduct = [
        {
          productName: product.productName,
          quantity: 1
        }
      ]
    } else {
      var find = false
      for (var commandProduct in listCommandProduct) {
        if (listCommandProduct[commandProduct].productName === product.productName) {
          listCommandProduct[commandProduct].quantity++
          find = true
        }
      }
      if (!find) {
        listCommandProduct.push({
          productName: product.productName,
          quantity: 1
        })
      }
    }
    return listCommandProduct
  }
}
