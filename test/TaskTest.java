//import org.junit.Test;
//    
//import static play.test.Helpers.fakeApplication;
//import static play.test.Helpers.running;
//    
//import static org.fest.assertions.Assertions.assertThat;
//    
//import models.Task;
//import play.db.jpa.JPA;
//    
//public class TaskTest {
//    
//    @Test
//    public void create() {
//        running(fakeApplication(), new Runnable() {
//            public void run() {
//            	JPA.withTransaction(new play.libs.F.Callback0() {
//                    public void invoke() throws Throwable {
//                        Task task = new Task();
//                        task.setContents("Write a test");
//                        JPA.em().persist(task);
//                        assertNotNull(task.getId());
//                    }
//                });
//            }
//        });
//    }
//    
//}