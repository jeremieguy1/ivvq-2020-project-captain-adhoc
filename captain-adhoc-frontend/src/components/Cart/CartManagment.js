export default {
  addToCart (listCommandProduct, product) {
    if (listCommandProduct === null) {
      listCommandProduct = [
        {
          nom_produit: product.nom_produit,
          quantity: 1
        }
      ]
    } else {
      var find = false
      for (var commandProduct in listCommandProduct) {
        if (listCommandProduct[commandProduct].nom_produit === product.nom_produit) {
          listCommandProduct[commandProduct].quantity++
          find = true
        }
      }
      if (!find) {
        listCommandProduct.push({
          nom_produit: product.nom_produit,
          quantity: 1
        })
      }
    }
    return listCommandProduct
  }
}
