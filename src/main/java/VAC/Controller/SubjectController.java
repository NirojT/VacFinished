package VAC.Controller;

import java.util.HashMap;
import java.util.List;

import javax.persistence.Id;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import VAC.Dto.SubjectsDto;
import VAC.Services.SubjectService;

@RestController
@RequestMapping("api/subject/")
@CrossOrigin(origins = { "http://127.0.0.1:5173/", "http://192.168.0.102:5173/" }, allowCredentials = "true")
public class SubjectController {

          @Autowired
          private SubjectService subjectService;

          @PostMapping("create/{id}")
          public ResponseEntity<?> createSubject(@RequestBody SubjectsDto subjectsDto,
                              @PathVariable("id") Integer courseId) {
                    HashMap<String, Object> response = new HashMap<>();
                    Boolean createSubject = this.subjectService.createSubject(subjectsDto, courseId);

                    if (createSubject) {
                              response.put("message", "Subject created successfully");
                              response.put("status", 200);
                              return ResponseEntity.status(200).body(response);

                    }
                    response.put("message", "Subject didn't created ");
                    response.put("status", 400);
                    return ResponseEntity.status(200).body(response);

          }

          @DeleteMapping("delete/{id}")
          public ResponseEntity<?> updateSubject( @PathVariable Integer id) {
                    HashMap<String, Object> response = new HashMap<>();
                    Boolean deleteSub = this.subjectService.deleteSubjects(id);
                    if (deleteSub) {
                              response.put("message", "Subject deleted successfully");
                              response.put("status", 200);
                              return ResponseEntity.status(200).body(response);

                    }
                    response.put("message", "Subject didn't deleted ");
                    response.put("status", 400);
                    return ResponseEntity.status(200).body(response);

          }

          @GetMapping("get-all")
          public ResponseEntity<?> getAllSubject() {
                    HashMap<String, Object> response = new HashMap<>();
                    List<SubjectsDto> allSubjects = this.subjectService.getAllSubjects();
                    if (allSubjects.size() > 0) {
                              response.put("Subjects", allSubjects);
                              response.put("status", 200);
                              return ResponseEntity.status(200).body(response);
                    }
                    response.put("message", "Subject is empty ");
                    response.put("status", 400);
                    return ResponseEntity.status(200).body(response);
          }

          @GetMapping("get/{id}")

          public ResponseEntity<?> getSubjectById(@PathVariable Integer id) {
                    HashMap<String, Object> response = new HashMap<>();
                    HashMap<String, Object> subById = this.subjectService.getAllSubjectsById(id);
                    if (subById.isEmpty()) {
                              response.put("messsage", "no subject found");
                              response.put("status", 400);
                              return ResponseEntity.status(200).body(response);
                    }
                    response.put("subject", subById);
                    response.put("status", 200);
                    return ResponseEntity.status(200).body(response);
          }

}
