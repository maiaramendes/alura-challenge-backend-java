package br.alura.controller;

import br.alura.config.security.UserEdit;
import br.alura.config.security.UserView;
import br.alura.dto.VideoDTO;
import br.alura.dto.VideoRequestDTO;
import br.alura.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/videos")
public class VideoController {

    private final VideoService videoService;

    @UserView
    @GetMapping
    public List<VideoDTO> getAll() {
        return videoService.getAll();
    }

    @UserView
    @GetMapping("/category")
    public List<VideoDTO> getAllByCategory(@RequestParam final String search) {
        return videoService.findAllByCategory(search);
    }

    @UserView
    @GetMapping("/{id}")
    public VideoDTO find(@PathVariable final String id) {
        return videoService.findById(id);
    }

    @UserEdit
    @PostMapping
    public VideoDTO create(@RequestBody final VideoRequestDTO dto) {
        return videoService.createVideo(dto);
    }

    @UserEdit
    @PatchMapping
    public VideoDTO edit(@RequestBody final VideoDTO dto) {
        return videoService.editVideo(dto);
    }

    @UserEdit
    @DeleteMapping("/{id}")
    public void delete(@PathVariable final String id) {
        videoService.deleteVideo(id);
    }

    @UserEdit
    @DeleteMapping("/all")
    public void deleteAll() {
        videoService.deleteAll();
    }
}
