package captainadhoc.captainadhocbackend.beans;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class Achat {

    @ApiModelProperty(notes = "code utilisé lors de l'achat")
    private String code;

    @ApiModelProperty(notes = "liste des produits achetés")
    @NotNull
    private List<ProduitsAchat> produitsAchat;
}
