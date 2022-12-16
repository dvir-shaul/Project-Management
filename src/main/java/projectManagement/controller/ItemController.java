package projectManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projectManagement.controller.entities.AddItemType;
import projectManagement.controller.entities.CreateItemDTO;
import projectManagement.controller.entities.ItemIdDTO;
import projectManagement.entities.Board;
import projectManagement.entities.Item;
import projectManagement.entities.Response;
import projectManagement.entities.User;
import projectManagement.service.BoardService;
import projectManagement.service.ItemService;
import projectManagement.service.UserService;

import java.util.Optional;

@RequestMapping("/item")
@CrossOrigin
@RestController
public class ItemController {
    @Autowired
    ItemService itemService;
    @Autowired
    UserService userService;
    @Autowired
    BoardService boardService;

    public ItemController() {
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<Response<Item>> createItem(@RequestBody CreateItemDTO item) {
        if (item == null) {
            return ResponseEntity.badRequest().body(Response.createFailureResponse("parameter could not be null"));
        }
        if (item.boardId == null) {
            return ResponseEntity.badRequest().body(Response.createFailureResponse("Board id could not be null"));
        }

        Optional<Board> optionalBoard = boardService.getBoard(item.boardId);
        if (!optionalBoard.isPresent()) {
            return ResponseEntity.badRequest().body(Response.createFailureResponse("Board not found"));
        }
        Board board = optionalBoard.get();

        if (item.creatorId == null) {
            return ResponseEntity.badRequest().body(Response.createFailureResponse("user creator id could not be null"));

        }

        Optional<User> optionalCreator = userService.getUser(item.creatorId);
        if (!optionalCreator.isPresent()) {
            return ResponseEntity.badRequest().body(Response.createFailureResponse("user creator not found"));
        }
        User creator = optionalCreator.get();

        User assignedToUser = null;
        if (item.assignedToUserId != null) {
            Optional<User> optionalAssignedToUser = userService.getUser(item.assignedToUserId);
            if (!optionalAssignedToUser.isPresent()) {
                return ResponseEntity.badRequest().body(Response.createFailureResponse("user assignedTo not found"));
            }
            assignedToUser = optionalAssignedToUser.get();

        }
        Item parentItem = null;
        if (item.parentId != null) {
            Optional<Item> optionalParentItem = itemService.getItem(item.parentId);
            if (!optionalParentItem.isPresent()) {
                return ResponseEntity.badRequest().body(Response.createFailureResponse("item parent not found"));
            }
            parentItem = optionalParentItem.get();

        }


        Response<Item> res = itemService.createItem(item, creator, board, assignedToUser, parentItem);

        if (res.isSucceed()) {
            return ResponseEntity.ok().body(res);
        } else {
            return ResponseEntity.badRequest().body(res);
        }


    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResponseEntity<Response<Long>> deleteItem(@RequestBody ItemIdDTO deleteItemId) {

        if (deleteItemId == null || deleteItemId.itemId == null) {
            return ResponseEntity.badRequest().body(Response.createFailureResponse("parameter could not be null"));
        }

        Long itemId = deleteItemId.itemId;
        Response<Long> response = itemService.deleteItem(itemId);
        if (response.isSucceed()) {
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }


    }


    @RequestMapping(value = "/type/remove", method = RequestMethod.PUT)
    public ResponseEntity<Response<Item>> removeItemType(@RequestBody ItemIdDTO updateItemId) {

        if (updateItemId == null || updateItemId.itemId == null) {
            return ResponseEntity.badRequest().body(Response.createFailureResponse("parameter could not be null"));
        }
        Long itemId = updateItemId.itemId;
        Response<Item> response = itemService.removeType(itemId);

        if (response.isSucceed()) {
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }


    }

    @RequestMapping(value = "/type/add", method = RequestMethod.PUT)
    public ResponseEntity<Response<Item>> addItemType(@RequestBody AddItemType addItemType) {

        if (addItemType == null || addItemType.itemId == null || addItemType.type == null) {
            return ResponseEntity.badRequest().body(Response.createFailureResponse("parameter could not be null"));
        }

        Response<Item> response = itemService.addType(addItemType);

        if (response.isSucceed()) {
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }


    }

}
