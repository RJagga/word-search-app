package com.example.wordsearch.service;

import com.example.wordsearch.trie.TrieNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TrieService {

    private final TrieNode root = new TrieNode();
    private final Map<String, Integer> rankMap = new ConcurrentHashMap<>();

    public void insertWord(String word) {
        TrieNode node = root;
        for (char ch : word.toCharArray()) {
            node = node.children.computeIfAbsent(ch, c -> new TrieNode());
        }
        node.isWord = true;
        node.word = word;
        rankMap.putIfAbsent(word, 0);
    }

    public boolean searchWord(String word) {
        TrieNode node = root;
        for (char ch : word.toCharArray()) {
            if (!node.children.containsKey(ch)) return false;
            node = node.children.get(ch);
        }
        return node.isWord;
    }

    public List<String> autocomplete(String prefix) {
        TrieNode node = root;
        for (char ch : prefix.toCharArray()) {
            if (!node.children.containsKey(ch)) return Collections.emptyList();
            node = node.children.get(ch);
        }
        List<String> results = new ArrayList<>();
        dfs(node, results);
        results.sort(Comparator.comparingInt(rankMap::get).reversed());
        return results;
    }

    private void dfs(TrieNode node, List<String> results) {
        if (node.isWord) results.add(node.word);
        for (var entry : node.children.entrySet()) {
            dfs(entry.getValue(), results);
        }
    }

    public void incrementRank(String word) {
        if (rankMap.containsKey(word)) {
            rankMap.put(word, rankMap.get(word) + 1);
        }
    }

    public int getRank(String word) {
        return rankMap.getOrDefault(word, 0);
    }

    public void loadWordsFromFile(Resource resource) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                insertWord(line.trim().toLowerCase());
            }
        }
    }
}