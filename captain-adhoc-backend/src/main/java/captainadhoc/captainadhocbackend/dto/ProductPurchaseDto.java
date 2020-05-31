package captainadhoc.captainadhocbackend.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Builder
@Data
public class ProductPurchaseDto {

    @ApiModelProperty(notes = "identifiant du produit")
    @NotNull
    private Long idProduct;

    @ApiModelProperty(notes = "quantite du produit dans la commande")
    @NotNull
    private int quantity;
}
