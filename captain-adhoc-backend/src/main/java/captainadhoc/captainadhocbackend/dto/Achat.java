package captainadhoc.captainadhocbackend.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@Builder
@Data
public class Achat {

    @ApiModelProperty(notes = "code utilisé lors de l'achat")
    private String code;

    @ApiModelProperty(notes = "liste des produits achetés")
    @NotNull
    private List<ProduitsAchat> produitsAchat;
}
