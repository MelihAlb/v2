package com.soguk.soguk.controllers;

import com.soguk.soguk.models.Entry;
import com.soguk.soguk.services.entryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public Entry updateEntry(@PathVariable String id, @RequestBody Entry entry) {
        entry.setId(id);
        return entryService.updateEntry(entry);
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
