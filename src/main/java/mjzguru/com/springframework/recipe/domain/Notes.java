package mjzguru.com.springframework.recipe.domain;

import lombok.*;

import javax.persistence.*;

//@Data
@Getter
@Setter
@EqualsAndHashCode(exclude = {"recipe"}) // added to avoid getting into endless loop because of bi-drectional references and relations (lombok)
@Entity
public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Recipe recipe;

    @Lob
    private String recipeNotes;

}
