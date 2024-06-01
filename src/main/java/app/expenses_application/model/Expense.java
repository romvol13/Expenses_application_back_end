package app.expenses_application.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Here you can see the data which Expense object includes. Unique object id generates automatically.
 * Expense is mapped with Person by person_id
 */

@Data
@Component
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Expense {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Category category;
	@Column(nullable = false)
	private double price;
	@Column(nullable = false, updatable = false)
	private LocalDate date;
	private String description;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "person_id")
	private Person person;
}