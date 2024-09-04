package com.soguk.soguk.controllers;

import com.soguk.soguk.DTO.entryDTO;
import com.soguk.soguk.models.Entry;
import com.soguk.soguk.models.Topic;
import com.soguk.soguk.models.User;
import com.soguk.soguk.services.entryService;
import com.soguk.soguk.services.userService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/entries")
@CrossOrigin(origins = "http://localhost:3000")
public class entryController {

    private final userService userService;
    private final entryService entryService;
    public entryController(entryService entryService,userService userService) {
        this.entryService = entryService;
        this.userService=userService;
    }

    @GetMapping("/{id}")
    public Entry getEntry(@PathVariable String id) {
        return entryService.getEntryById(id);
    }

    @GetMapping
    public List<Entry> getAllEntries() {
        return entryService.getAllEntries();
    }


    @PostMapping
    public ResponseEntity<Entry> createEntry(@RequestHeader("Authorization") String authHeader, @RequestBody Entry entry) {
        String token = authHeader.replace("Bearer ", "");
        Entry createdEntry = entryService.createEntry(token, entry);

        return ResponseEntity.ok(createdEntry);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEntry(@PathVariable String id, @RequestBody Entry updatedEntry, Principal principal) {

        Entry existingEntry = entryService.findById(id);


        if (existingEntry == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Entry bulunamadı.");
        }
        String loggedInUserNick = principal.getName();
        if (!existingEntry.getAuthorNick().equals(loggedInUserNick)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Bu entry'yi güncellemeye yetkiniz yok.");
        }
        updatedEntry.setId(id);
        Entry updated = entryService.updateEntry(updatedEntry);
        return ResponseEntity.ok(updated);
    }
    @DeleteMapping("/{id}")
    public void deleteEntry(@PathVariable String id) {
        entryService.deleteEntry(id);
    }
    @PostMapping("/{id}/like")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> likeEntry(@PathVariable String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Giriş yapılmamış.");
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String nick = userDetails.getUsername();

        User user = userService.findByNick(nick);

        boolean alreadyLiked = entryService.checkIfUserLiked(id, user);

        if (alreadyLiked) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Zaten beğenildi.");
        }

        entryService.likeEntry(id, user);
        return ResponseEntity.ok("Beğeni başarılı.");
    }
    @GetMapping("/topic/{topicId}")
    public List<Entry> getEntriesByTopicId(@PathVariable String topicId) {
        return entryService.getEntriesByTopicId(topicId);
    }
    @GetMapping("/{id}/likes") //Entry id'sine göre beğenenleri çeker
    public ResponseEntity<List<String>> getLikesByEntryId(@PathVariable String id) {
        List<String> likedBy = entryService.getLikesByEntryId(id);
        return ResponseEntity.ok(likedBy);
    }
    @GetMapping("/by/{creatorId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Entry>> getEntriesByCreatorId(@PathVariable String creatorId) {
        List<Entry> entries = entryService.getEntriesByCreatorId(creatorId);
        return ResponseEntity.ok(entries);
    }
    @GetMapping("/user/{nick}/liked-entries")
    public ResponseEntity<List<entryDTO>> getEntriesLikedByUser(@PathVariable String nick, @RequestHeader("Authorization") String token) {
        List<entryDTO> likedEntries = entryService.getEntriesLikedByUser(nick);

        if (likedEntries.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(likedEntries);
    }
}
