package projectManagement.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Data
@Setter
@Getter
@Entity
@NoArgsConstructor
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String title;

    @ManyToOne()
    @JoinColumn(nullable = false)
    @JsonIgnore
    private User admin;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserRoleInBoard> userRoleInBoards = new HashSet<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Item> items = new HashSet<>();

    @JsonIncludeProperties(value = {"id", "status"})
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Status> statuses = new HashSet<>();

    @JsonIncludeProperties(value = {"id", "type"})
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Type> types = new HashSet<>();

    public Board(String title, User admin) {
        this.title = title;
        this.admin = admin;
    }

    public Set<UserRoleInBoard> addUserRole(UserRoleInBoard userRoleInBoard){
        this.userRoleInBoards.add(userRoleInBoard);
        return this.userRoleInBoards;
    }

    public Set<UserRoleInBoard> removeUserRole(UserRoleInBoard userRoleInBoard){
        this.userRoleInBoards.remove(userRoleInBoard);
        return this.userRoleInBoards;
    }

    public Set<Status> addStatus(Status status){
        this.statuses.add(status);
        return this.statuses;
    }

    public Set<Status> removeStatus(Status status){
        this.statuses.remove(status);
        return this.statuses;
    }

//    public Set<Status> removeStatusById(long statusId){
////        Status status = this.statuses.stream().filter(s -> s.getId() == statusId).findFirst().orElse(null);
//        this.statuses.remove(this.statuses.stream().filter(s -> s.getId() == statusId).findFirst().orElse(null));
//        return this.statuses;
//    }


}
