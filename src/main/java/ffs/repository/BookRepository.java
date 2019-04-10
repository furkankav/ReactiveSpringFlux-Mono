package ffs.repository;

import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;

import ffs.model.Book;

@Repository
public interface BookRepository extends ReactiveCassandraRepository<Book, String> {

}
