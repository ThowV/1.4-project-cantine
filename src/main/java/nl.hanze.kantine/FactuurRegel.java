package nl.hanze.kantine;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "factuur_regel")
public class FactuurRegel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, updatable = false, nullable = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "factuur")
    private Factuur factuur;

    @Transient
    private Artikel artikel;

    @Column(name = "artikel_naam")
    private String artikel_naam;

    @Column(name = "artikel_prijs")
    private BigDecimal artikel_prijs;

    @Column(name = "artikel_korting")
    private BigDecimal artikel_korting;

    public FactuurRegel() {}

    public FactuurRegel(Factuur factuur, Artikel artikel) {
        this.factuur = factuur;
        this.artikel = artikel;

        artikel_naam = artikel.getNaam();
        artikel_prijs = artikel.getPrijs();
        artikel_korting = artikel.getKorting();
    }

    /**
     * @return een printbare factuurregel
     */
    public String toString() {
        return artikel.toString();
    }
}