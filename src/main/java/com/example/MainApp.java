package com.example;

import com.example.model.Course;
import com.example.model.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class MainApp {
    public static void main(String[] args) {
        // Create a Hibernate session factory and open a session
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        // Create a course
        Course course = new Course();
        course.setName("Mathematics");

        // Create students and associate them with the course
        Student student1 = new Student();
        student1.setName("Alice");
        student1.setCourse(course);

        Student student2 = new Student();
        student2.setName("Bob");
        student2.setCourse(course);

        // Save the course and students
        session.save(course);
        session.save(student1);
        session.save(student2);

        session.getTransaction().commit();

        // Retrieve the course and list its students' names with JPQL query
        String jpql = "SELECT s.name FROM Student s WHERE s.course.id = :courseId";
        List<String> studentNames = session.createQuery(jpql, String.class)
                .setParameter("courseId", course.getId())
                .getResultList();

        System.out.println("Course: " + course.getName());
        System.out.println("Students enrolled in the course:");
        for (String studentName : studentNames) {
            System.out.println(studentName);
        }

        // Close the session and session factory
        session.close();
        sessionFactory.close();
    }
}
