import com.sberbank.may.services.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

public class EmailServiceTest {
//
//    @Mock
//    private JavaMailSender javaMailSender;
//
//    @Mock
//    private TemplateEngine templateEngine;
//
//    @InjectMocks
//    private EmailService emailService;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    public void sendEmail_ShouldSendEmailSuccessfully() throws MessagingException {
//        // Arrange
//        String parentEmail = "parent@example.com";
//        String subject = "Test Subject";
//        String templateName = "test_template";
//        Context context = new Context();
//        context.setVariable("name", "John Doe");
//
//        MimeMessage mimeMessage = mock(MimeMessage.class);
//
//        when(templateEngine.process(templateName, context)).thenReturn("<html><body>Hello, <span th:text=\"${name}\"></span>!</body></html>");
//        doNothing().when(javaMailSender).send(any(MimeMessage.class));
//
//        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
//
//        // Act
//        emailService.sendEmail(parentEmail, subject, templateName, context);
//
//        // Assert
//        verify(javaMailSender, times(1)).send(any(MimeMessage.class));
//    }
//
//    @Test
//    public void sendEmail_ShouldHandleMessagingException() throws MessagingException {
//        // Arrange
//        String parentEmail = "parent@example.com";
//        String subject = "Test Subject";
//        String templateName = "test_template";
//        Context context = new Context();
//        context.setVariable("name", "John Doe");
//
//        when(templateEngine.process(templateName, context)).thenReturn("<html><body>Hello, <span th:text=\"${name}\"></span>!</body></html>");
//        doThrow(MessagingException.class).when(javaMailSender).send(any(MimeMessage.class));
//
//        // Act and Assert
//        try {
//            emailService.sendEmail(parentEmail, subject, templateName, context);
//            fail("Expected MessagingException to be thrown");
//        } catch (MessagingException e) {
//            // Handle or assert the exception if needed
//        }
//    }
}

