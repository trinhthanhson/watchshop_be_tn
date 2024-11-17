package ptithcm.tttn.controller.manager;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ptithcm.tttn.entity.Role;
import ptithcm.tttn.function.MessageSuccess;
import ptithcm.tttn.response.ListEntityResponse;
import ptithcm.tttn.service.RoleService;

@RestController
@RequestMapping("/api/manager/role")
@AllArgsConstructor
public class ManagerRoleController {

    private final RoleService roleService;

    @GetMapping("/all")
    public ResponseEntity<ListEntityResponse<Role>> findRoleHandle(@RequestHeader("Authorization") String jwt){
        ListEntityResponse<Role> res = new ListEntityResponse<>();
        res.setStatus(HttpStatus.OK);
        res.setCode(HttpStatus.OK.value());
        res.setMessage(MessageSuccess.E01.getMessage());
        res.setData(roleService.findAll());
        return new ResponseEntity<>(res,res.getStatus());
    }
}
