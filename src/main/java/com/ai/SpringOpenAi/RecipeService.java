package com.ai.SpringOpenAi;

import java.util.Map;

import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;

@Service
public class RecipeService {

	private OpenAiChatModel openAiChatModel;

	public RecipeService(OpenAiChatModel openAiChatModel) {
		super();
		this.openAiChatModel = openAiChatModel;
	}
	
	public String createRecipe(String ingredients,
								String cuisine,
								String dietaryRestrictions) {
		
		var template = """
				I want to create a recipe using following ingredients:{ingredients}.
				The cuisine type I prefer is {cuisine}.
				Please consider the following dietary restrictions:{dietaryRestrictions}.
				Please provide me with a detailed recipe including title, list of ingredients, and cooking instructions.				
				""";
		
		PromptTemplate promptTemplate = new PromptTemplate(template);
		Map<String,Object> params = Map.of(
				"ingredients",ingredients,
				"cuisine",cuisine,
				"dietaryRestrictions",dietaryRestrictions
				);
		
	
		Prompt prompt = promptTemplate.create(params);
		return openAiChatModel.call(prompt).getResult().getOutput().getContent();
	}
}
