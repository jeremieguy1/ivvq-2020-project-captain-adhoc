package captainadhoc.captainadhocbackend.domain;


import lombok.ToString;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Column;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@Entity
@ToString(of = {"firstName", "lastName", "userName"})
public class Member {

    @Id
    @GeneratedValue
    private Long idMember;

    @NotEmpty
    @Pattern(regexp = "^[A-Za-z]*$")
    private String firstName;

    @NotEmpty
    @Pattern(regexp = "^[A-Za-z]*$")
    private String lastName;

    @NotEmpty
    @Column(unique = true)
    @Pattern(regexp = "^[A-Za-z0-9]*$")
    private String userName;

    @NotEmpty
    private String password;

    @NotNull
    private Boolean isAdmin = false;

    @OneToMany(mappedBy = "member")
    private List<Purchase> purchaseList;

}
