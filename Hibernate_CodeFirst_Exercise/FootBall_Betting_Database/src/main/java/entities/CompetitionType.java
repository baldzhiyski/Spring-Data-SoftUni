package entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(schema = "competition_types")
public class CompetitionType extends IdName{
    public CompetitionType() {
    }
}
