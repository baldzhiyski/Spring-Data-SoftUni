package entities;


import entities.enums.Prediction;
import jakarta.persistence.*;

@Entity
@Table(name = "result_prediciton")
public class ResultPrediction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Prediction prediction;
}
