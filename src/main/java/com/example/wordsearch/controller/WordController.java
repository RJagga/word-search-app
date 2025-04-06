package com.example.wordsearch.controller;

import com.example.wordsearch.service.TrieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/words")
public class WordController {

    @Autowired
    private TrieService service;

    @Autowired
    private ResourceLoader loader;

    @PostConstruct
    public void init() throws IOException {
        Resource resource = loader.getResource("classpath:words.txt");
        service.loadWordsFromFile(resource);
    }

    @PostMapping("/insert")
    public ResponseEntity<String> insert(@RequestBody String word) {
        System.out.println("Received word: " + word); // ✅ Debug log
        try {
            service.insertWord(word.toLowerCase());
            return ResponseEntity.ok("Word inserted.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error inserting word.");
        }
    }

    // @PostMapping("/test")
    // public ResponseEntity<String> testInsertSearch(@RequestBody String word) {
    //     String lower = word.toLowerCase();
    //     service.insertWord(lower);
    //     boolean found = service.searchWord(lower);
    //     return ResponseEntity.ok("Inserted and found? " + found);
    // }

    @GetMapping("/search")
    public ResponseEntity<Boolean> search(@RequestParam String word) {
        boolean found = service.searchWord(word.toLowerCase());
        if (found)
            service.incrementRank(word.toLowerCase());
        return ResponseEntity.ok(found);
    }

    @GetMapping("/autocomplete")
    public ResponseEntity<List<String>> autocomplete(@RequestParam String prefix) {
        return ResponseEntity.ok(service.autocomplete(prefix.toLowerCase()));
    }

    @GetMapping("/rank")
    public ResponseEntity<Integer> getRank(@RequestParam String word) {
        return ResponseEntity.ok(service.getRank(word.toLowerCase()));
    }
}