package com.ai.SpringOpenAi;

import java.io.IOException;
import java.util.List;

import org.springframework.ai.image.ImageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class GenAIController {

	@Autowired
	private ChatService chatService;
	
	@Autowired
	private ImageService imageService;
	@Autowired
	private RecipeService recipeService;
	
	
	@GetMapping("ask-ai")
	public String getResponse(@RequestParam String prompt) {
		return chatService.getResponse(prompt);
	}
	
	@GetMapping("ask-ai-options")
	public String getResponseOptions(@RequestParam String prompt) {
		return chatService.getResponseOptions(prompt);
	}
	
	@GetMapping("generate-image")
	public void generateImage(HttpServletResponse response, @RequestParam String prompt) throws IOException {
		String imageUrl= imageService.generateImage(prompt).getResult().getOutput().getUrl();
		response.sendRedirect(imageUrl);
	}
	
	@GetMapping("generate-image-with-options")
	public List<String> generateImageWithOptions(HttpServletResponse response, @RequestParam String prompt,
			 @RequestParam(defaultValue = "hd") String quality,
			 @RequestParam(defaultValue = "1") int n,
			 @RequestParam(defaultValue = "1024") int width,
			 @RequestParam(defaultValue = "1024") int height) throws IOException {
		
		ImageResponse imageResponse= imageService.generateImageWithOptions(prompt,quality,n,width,height);
		
		List<String> imageUrls = imageResponse.getResults().stream()
				.map(result->result.getOutput().getUrl()).toList();
		
		return imageUrls;
		
	}
	
	@GetMapping("recipe=generator")
	public String generateRecipee(@RequestParam String ingredients,
			@RequestParam(defaultValue = "any") String cuisine,
			@RequestParam(defaultValue = "") String dietaryRestrictions) {
		return recipeService.createRecipe(ingredients,cuisine,dietaryRestrictions);
	}
}
