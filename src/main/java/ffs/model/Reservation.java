package ffs.model;

import org.springframework.data.annotation.Transient;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Reservation {
	
	@PrimaryKey
	private String id;
	@Transient
	private Book book;
	private String reservationDate;
	private String user;
}
