package com.sberbank.may.postgres;

import org.junit.Ignore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Ignore
public class DatabaseImplTest {
//
//
//    private static final String JDBC_URL = "jdbc:postgresql://localhost:5433/ElectronicDiary";
//    private static final String USERNAME = "user";
//    private static final String PASSWORD = "pass";
//
//    @BeforeEach
//    public void testDatabase() throws Exception {
//
//        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
//             Statement stmt = conn.createStatement()) {
//
//            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM users");
//            rs.next();
//            int count = rs.getInt(1);
//
//            assertEquals(12, count, "Ожидается 12 записей в таблице пользователей");
//        }
//    }
//
//
//
//    @Test
//    public void testUserTable() {
//        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
//             Statement stmt = conn.createStatement()) {
//
//            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM public.users");
//            rs.next();
//            int userCount = rs.getInt(1);
//
//            Assertions.assertEquals(12, userCount, "Ожидается 12 записей в таблице пользователей");
//        } catch (Exception e) {
//            e.printStackTrace();
//            Assertions.fail("Ошибка выполнения запроса: " + e.getMessage());
//        }
//    }
//
//    @Test
//    public void testStudentTable() {
//        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
//             Statement stmt = conn.createStatement()) {
//
//            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM public.students");
//            rs.next();
//            int studentCount = rs.getInt(1);
//
//            Assertions.assertEquals(3, studentCount, "Ожидается 3 записи в таблице студентов");
//        } catch (Exception e) {
//            e.printStackTrace();
//            Assertions.fail("Ошибка выполнения запроса: " + e.getMessage());
//        }
//    }
//    @Test
//    public void testPredmetsTable() {
//        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
//             Statement stmt = conn.createStatement()) {
//
//            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM public.predmets");
//            rs.next();
//            int predmetsCount = rs.getInt(1);
//
//            Assertions.assertEquals(3, predmetsCount, "Ожидается 3 записей в таблице predmets");
//        } catch (Exception e) {
//            e.printStackTrace();
//            Assertions.fail("Ошибка выполнения запроса: " + e.getMessage());
//        }
//    }
//
//    @Test
//    public void testStudentClassesTable() {
//        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
//             Statement stmt = conn.createStatement()) {
//
//            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM public.student_classes");
//            rs.next();
//            int studentClassesCount = rs.getInt(1);
//
//            Assertions.assertEquals(3, studentClassesCount, "Ожидается 3 записей в таблице student_classes");
//        } catch (Exception e) {
//            e.printStackTrace();
//            Assertions.fail("Ошибка выполнения запроса: " + e.getMessage());
//        }
//    }
//
//    @Test
//    public void testHomeworksTable() {
//        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
//             Statement stmt = conn.createStatement()) {
//
//            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM public.homeworks");
//            rs.next();
//            int homeworksCount = rs.getInt(1);
//
//            Assertions.assertEquals(1, homeworksCount, "Ожидается 1 записей в таблице homeworks");
//        } catch (Exception e) {
//            e.printStackTrace();
//            Assertions.fail("Ошибка выполнения запроса: " + e.getMessage());
//        }
//    }
//
//    @Test
//    public void testLessonsTable() {
//        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
//             Statement stmt = conn.createStatement()) {
//
//            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM public.lessons");
//            rs.next();
//            int lessonsCount = rs.getInt(1);
//
//            Assertions.assertEquals(2, lessonsCount, "Ожидается 2 записей в таблице lessons");
//        } catch (Exception e) {
//            e.printStackTrace();
//            Assertions.fail("Ошибка выполнения запроса: " + e.getMessage());
//        }
//    }
//
//    @Test
//    public void testStudentLessonTable() {
//        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
//             Statement stmt = conn.createStatement()) {
//
//            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM public.student_lesson");
//            rs.next();
//            int studentLessonCount = rs.getInt(1);
//
//            Assertions.assertEquals(1, studentLessonCount, "Ожидается 1 записей в таблице student_lesson");
//        } catch (Exception e) {
//            e.printStackTrace();
//            Assertions.fail("Ошибка выполнения запроса: " + e.getMessage());
//        }
//    }
//
//    @Test
//    public void testStudentUserTable() {
//        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
//             Statement stmt = conn.createStatement()) {
//
//            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM public.student_user");
//            rs.next();
//            int studentUserCount = rs.getInt(1);
//
//            Assertions.assertEquals(4, studentUserCount, "Ожидается 4 записей в таблице student_user");
//        } catch (Exception e) {
//            e.printStackTrace();
//            Assertions.fail("Ошибка выполнения запроса: " + e.getMessage());
//        }
//    }


}
