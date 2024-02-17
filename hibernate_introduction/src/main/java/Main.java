import entities.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Configuration cfg = new Configuration();
        cfg.configure();
        SessionFactory sessionFactory =
                cfg.buildSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();
//        Student student = new Student();
//
//        student.setName("Tosho");
//        Student student1 = new Student();
//        student1.setName("Gosho");
//
//        session.persist(student);
//
//        Student fromDB = session.get(Student.class, 0);
//        System.out.println(fromDB.getId() + " " + fromDB.getName());

        List<Student> fromStudent =
                session.createQuery("FROM Student AS s WHERE s.name = 'Tosho'", Student.class).list();


        for (Student student : fromStudent) {
            System.out.println(student.getId() + " " + student.getName());
        }
        session.getTransaction().commit();

        session.close();
    }
}