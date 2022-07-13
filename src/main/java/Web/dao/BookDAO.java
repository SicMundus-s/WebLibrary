package Web.dao;


import Web.models.Book;
import Web.models.Person;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {

    private final JdbcTemplate jdbcTemplate;

    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book show(int id) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE id = ?", new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class)).stream().findAny().orElse(null);
    }

     public void save(Book book) {
        jdbcTemplate.update("INSERT INTO book(title, author, yearbook) VALUES (?, ?, ?::date)",
                book.getTitle(), book.getAuthor(), book.getyear());
    }

    public Optional<Person> getBooksOwner(int id) {
        return jdbcTemplate.query("SELECT * FROM Person LEFT JOIN Book ON person.id = book.personid WHERE person.id = ?", new Object[]{id},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }
}
