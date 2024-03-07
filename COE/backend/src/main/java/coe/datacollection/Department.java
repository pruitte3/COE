package coe.datacollection;

import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
@Table(name = "_dept", uniqueConstraints = {@UniqueConstraint(columnNames = {"id"})})
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
    private Integer deptId;

    @Column(name = "_dept", nullable = false)
    private String deptName;
}