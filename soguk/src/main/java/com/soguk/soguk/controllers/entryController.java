package com.soguk.soguk.controllers;

import com.soguk.soguk.models.Entry;
import com.soguk.soguk.services.entryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/entries")
@CrossOrigin(origins = "http://localhost:3000")
public class entryController {

    @Autowired
    private entryService entryService;

    @GetMapping("/{id}")
    public Entry getEntry(@PathVariable String id) {
        return entryService.getEntryById(id);
    }

    @GetMapping
    public List<Entry> getAllEntries() {
        return entryService.getAllEntries();
    }


    @PostMapping
    public Entry createEntry(@RequestBody Entry entry) {
        ResponseEntity.ok("Entry oluşturuldu");
        return entryService.createEntry(entry);

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
    public Entry likeEntry(@PathVariable String id) {
        ResponseEntity.ok("like atıldı");
        return entryService.likeEntry(id);

    }
    @GetMapping("/topic/{topicId}")
    public List<Entry> getEntriesByTopicId(@PathVariable String topicId) {
        return entryService.getEntriesByTopicId(topicId);
    }
}
