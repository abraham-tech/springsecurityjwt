package miu.edu.cse.securitydemo.secured;

import miu.edu.cse.securitydemo.user.Permission;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/management")
public class MemberController {
    @GetMapping
    public String getMember() {
        return "Member: secured end point";
    }

    @GetMapping("/adminwrite")
    @PreAuthorize("hasAuthority('admin:write')")
    public String getMemberOnlyForAdmin() {
        return "Member controller: secured end point only for admin-write";
    }

    @GetMapping("/memberonly")
    @PreAuthorize("hasAuthority('member:read')")
    public String getMemberOnly() {
        return "Member controller: secured end point for member-read";
    }

    @GetMapping("/forall")
    @PreAuthorize("hasAnyAuthority('member:read', 'member:write', 'admin:write', 'admin:read')")
    public String getMemberForAll() {
        return "Member controller: secured end point for all";
    }

    @GetMapping("/foradmin")
    @PreAuthorize("hasAuthority('admin:write')")
    public String getMemberFromMember() {
        return "Member controller: secured end point from member-read " + Permission.ADMIN_WRITE.getPermission();
    }
}
