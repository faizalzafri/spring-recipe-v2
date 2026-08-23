package com.github.recipe.app.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.github.recipe.app.commands.RecipeCommand;
import com.github.recipe.app.services.ImageService;
import com.github.recipe.app.services.RecipeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ImageController {

	private final ImageService imageService;
	private final RecipeService recipeService;

	public ImageController(ImageService imageService, RecipeService recipeService) {
		this.imageService = imageService;
		this.recipeService = recipeService;
	}

	@PostMapping("recipe/{id}/image")
	public String handleImagePost(@PathVariable("id") String id, @RequestParam("imagefile") MultipartFile file) {
		log.debug("Handling image upload for recipe id: {}", id);
		imageService.save(Long.valueOf(id), file);
		return "redirect:/recipe/" + id + "/show";
	}

	@GetMapping("/recipe/{id}/image")
	public String showUploadForm(@PathVariable String id, Model model) {
		model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));
		return "recipe/imageuploadform";
	}

	@GetMapping("/recipe/{id}/recipeimage")
	public void renderImageFromDb(@PathVariable String id, HttpServletResponse response) throws IOException {
		RecipeCommand reCommand = recipeService.findCommandById(Long.valueOf(id));

		if (reCommand != null && reCommand.getImage() != null) {
			byte[] byteArray = new byte[reCommand.getImage().length];
			int i = 0;

			for (Byte wrappedByte : reCommand.getImage()) {
				byteArray[i++] = wrappedByte; // auto unboxing
			}

			response.setContentType("image/jpeg");
			InputStream is = new ByteArrayInputStream(byteArray);
			StreamUtils.copy(is, response.getOutputStream());
		}
	}
}
