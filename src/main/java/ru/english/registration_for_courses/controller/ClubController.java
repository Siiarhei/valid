package ru.english.registration_for_courses.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/club")
@Slf4j
public class ClubController {

    @Autowired
    private ClubService clubService;

    @PostMapping
    public ResponseEntity<Club> saveClub(@RequestBody @Valid ClubDto clubDto) {
        log.info("Creating new club");
        Club club = clubDto.toEntity();

        // Обработка labels
        Map<String, Object> labels = new HashMap<>();
        if (clubDto.getLabels() != null) {
            labels.put("tags", clubDto.getLabels());
        }
        club.setLabel(labels);

        // Валидация email
        club.setEmail(clubDto.getEmail());

        // Валидация мобильного телефона
        club.setPhone(clubDto.getPhone());

        // Валидация Telegram ID
        club.setTelegramNick(clubDto.getTelegramNick());

        Club savedClub = clubService.save(club);
        return ResponseEntity.ok(savedClub);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Club> getClubById(@PathVariable Long id) {
        log.info("Fetching club by id: {}", id);
        Club club = clubService.findById(id)
                .orElseThrow(() -> new ClubNotFoundException("Club not found with id: " + id));
        return ResponseEntity.ok(club);
    }

    @GetMapping
    public ResponseEntity<List<Club>> getAllClubs(@PageableDefault(size = 20, sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
        log.info("Fetching all clubs");
        List<Club> clubs = clubService.findAll(pageable);
        return ResponseEntity.ok(clubs);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Club> updateClub(@PathVariable Long id, @RequestBody @Valid ClubDto clubDto) {
        log.info("Updating club with id: {}", id);
        Club club = clubService.findById(id)
                .orElseThrow(() -> new ClubNotFoundException("Club not found with id: " + id));

        club.setName(clubDto.getName());
        club.setDescription(clubDto.getDescription());

        // Обновление email
        club.setEmail(clubDto.getEmail());

        // Обновление мобильного телефона
        club.setPhone(clubDto.getPhone());

        // Обновление Telegram ID
        club.setTelegramNick(clubDto.getTelegramNick());

        Club updatedClub = clubService.save(club);
        return ResponseEntity.ok(updatedClub);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClub(@PathVariable Long id) {
        log.info("Deleting club with id: {}", id);
        clubService.delete(id);
        return ResponseEntity.noContent().build();
    }
}