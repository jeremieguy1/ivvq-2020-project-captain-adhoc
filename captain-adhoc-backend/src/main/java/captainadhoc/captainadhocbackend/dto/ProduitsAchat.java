package captainadhoc.captainadhocbackend.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ProduitsAchat {

    @ApiModelProperty(notes = "identifiant du produit")
    @NotNull
    private Long id_produit;

    @ApiModelProperty(notes = "quantite du produit dans la commande")
    @NotNull
    private int quantite;
}
