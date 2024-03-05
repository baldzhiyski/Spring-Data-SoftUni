package bg.softuni.bookshopsystemsecond.repositories;

import bg.softuni.bookshopsystemsecond.domain.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AuthorRepository  extends JpaRepository<Author,Long> {

    @Query("SELECT DISTINCT a FROM Author a JOIN FETCH a.books b WHERE b.releaseDate < :date")
    List<Author> findAllAuthorsWithBooksReleasedBefore1990(@Param("date") LocalDate date);

    @Query("Select a from Author a order by size(a.books) ")
    List<Author> findAllDistinctOrderByBooks();

    List<Author> findAllByFirstNameEndingWith(String suffix);

    @Query("select concat_ws(' ',a.firstName,a.lastName,' - ',sum(b.copies)) from Author a join a.books as b group by a.id")
    List<String> findAuthorsCopiesCountForEveryBook();

}
