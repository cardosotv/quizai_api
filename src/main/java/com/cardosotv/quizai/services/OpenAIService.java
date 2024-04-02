package com.cardosotv.quizai.services;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.util.Scanner;

import com.cardosotv.quizai.error.HandleException;
import com.cardosotv.quizai.error.NotFoundException;
import com.cardosotv.quizai.model.DTO.QuestionDTO;
import com.cardosotv.quizai.model.DTO.QuestionsListDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.lang.Objects;

public class OpenAIService {

    public static void main(String[] args) throws Exception{

        String result = "";
        //List<QuestionDTO> result = new OpenAIService().requestNewQuestionByUser(null, 10, "Movies");
        System.out.println(result);
    }

    public OpenAIService() {}

    public List<QuestionDTO> requestNewQuestionByUser(
                                    List<String> excludeList
                                    , int quantityQuestions
                                    , String subject
                                    , String apiKeyOpenAI
                                    , String endpointOpenAI){

        // Set up the prompt to request a questions to OpenAI
        String prompt = "Create a list with "+ quantityQuestions + " questions and answers like a multi choice quiz with "
                    + "four possible options but with just one correctly about the subject " + subject + " in english language. " 
                    + "This list should be as json format like key and value. "
                    + "The json should have the keys: question as string, list of option and for each option in this list we "
                    + "will need of the keys option as string and the attribute isCorrect as boolean, which this last one contains "
                    + "the value if the option is correct or not.";
        
        // Initializeted the result
        List<QuestionDTO> result = new ArrayList<>();

        // Set up the object mapper
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Use this bellow for test WITH connect to openAI
            String content = requestToOpenAI(prompt, apiKeyOpenAI, endpointOpenAI);
            
            // Use this bellow for test WITHOUT connect to openAI
            //String content = readMockResponseOpenAI(quantityQuestions);
            
            //Parse content to List of questions
            QuestionsListDTO questions = objectMapper.readValue(getContent(content), QuestionsListDTO.class);

            //Check if the list is empty
            if (Objects.isEmpty(questions)) {
                throw new NotFoundException("OpenAI", questions);
            }

            // Check if the amount of questions is the same that was requested
            if (questions.getQuestions().size() != quantityQuestions){
                // return de list of questions to result
                int index = 0;
                while(result.size() < quantityQuestions){
                    result.add(questions.getQuestions().get(index));
                    index++;
                }
            } else {
                // return de list of questions to result
                result = questions.getQuestions();
            }

        // Treat any error
        } catch (Exception e) {
            throw HandleException.handleException(e, prompt, "requestNewQuestionByUser");
        }

        // return the list of new questions from OpenAI
        return result; //modelMapper.map(result, QuestionDTO.class);
    }

    private String requestToOpenAI(String prompt, String apiKeyOpenAI, String endpointOpenAI){

        String requestBody = "{\n" +
                "  \"model\": \"gpt-3.5-turbo\",\n" +
                "  \"messages\": [\n" +
                "    {\"role\": \"system\", \"content\": \"You are a helpful assistant providing quiz questions.\"},\n" +
                "    {\"role\": \"user\", \"content\": \"" + prompt + "\"}\n" +
                "  ]\n" +
                "}";
        HttpResponse<String> response;

        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpointOpenAI))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apiKeyOpenAI)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (Throwable t) {
            throw HandleException.handleException(t, prompt, "OpenAI Request");
        }
        
        return response.body();
    }


    private static String readMockResponseOpenAI(int quantityQuestions) {

        String response = "";
        String filePath = "src/main/java/com/cardosotv/quizai/repositories/openai_response.json";
        StringBuilder stringBuilder = new StringBuilder();
        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);

            // Read each line of the file and append it into string
            while(scanner.hasNext()){
                stringBuilder.append(scanner.nextLine());
            }
            scanner.close();

            // Convert StringBuilder to String 
            response = stringBuilder.toString();

        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return getContent(response);
    }


    private static String getContent(String response) {

        ObjectMapper objectMapper = new ObjectMapper();
        String result = "";

        try{
            JsonNode rootNode = objectMapper.readTree(response);

            JsonNode contentNode = rootNode.get("choices").get(0).get("message").get("content");

            result = contentNode.asText();

            result = result.replace("```json", "");
            result = result.replace("```", "").trim();

        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return result;
    }
}
