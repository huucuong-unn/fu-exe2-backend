package com.exe01.backend.dto.request.coze;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CozeCreateChatRequest {
    @JsonProperty("bot_id")
    private String botId = "7383998577670160392";
    @JsonProperty("user_id")
    private String userId ="demo";
    private boolean stream = false;
    @JsonProperty("auto_save_history")
    private boolean autoSaveHistory=true;
    @JsonProperty("additional_messages")
    private List<AdditionalMessage> additionalMessages;

    @Data
    public static class AdditionalMessage {
        private String role = "user";
        @JsonProperty("content_type")
        private String contentType = "object_string";
        private String content;
        public AdditionalMessage(String fileId) {
            this.content = "[{\"type\":\"text\",\"text\":\"Review CV following this format:\\nHead1 - Spelling\\n   spe1 - \\n   spe2 - \\n   spe3 - \\nHead2 - Sentences to Improve\\n   sent1 - \\n   sent2 - \\nHead3 - Suitable Positions\\n   sp1 - In the \\\"\\\"... .Perhaps,\\\"\\\"\\n   sp2 - \\n   sp2-1 - \\n   sp2-2 - \\n   sp2-3 - \\n\\nFor example:\\nHead1 - Spelling\\nspe1 - \\\"Builted\\\" should be \\\"Built\\\" in the \\\"FPT Software HCM\\\" section.\\nspe2 - \\\"Gitlad\\\" should be \\\"GitLab\\\" in the \\\"Technical Skills\\\" section.\\nspe3 - \\\"apllying\\\" should be \\\"applying\\\" in the \\\"Career Objective\\\" section.\\n\\nHead2 - Sentences to Improve\\nsent1 - In the \\\"FPT Software HCM\\\" section, the sentence \\\"Worked with English-language documentation for technical and project requirements\\\" could be rephrased for better flow. Perhaps, \\\"I utilized English-language documentation for both technical and project requirements.\\\"\\nsent2 - In the \\\"Tortee - Web Application\\\" section, the sentence \\\"Pick up user stories, analyze, design, discuss with the team to tackle them and write documentations to clarify my work\\\" is a bit long and could be broken down into two sentences. Perhaps, \\\"I picked up user stories, analyzed them, and designed solutions. I then collaborated with the team to discuss how to tackle these tasks and wrote clear documentation to clarify my work.\\\"\\n\\nHead3 - Suitable Positions\\nsp1 - Based on your CV, you're showcasing a strong skillset in full-stack development. You've demonstrated experience with various technologies, including ReactJS, Spring Boot, ASP.NET Core, and databases like MySQL and PostgreSQL. You've also participated in multiple projects, showcasing your ability to work in a team environment.\\nsp2 - I would recommend focusing on positions like:\\nsp2-1 - Full-Stack Developer: This is a natural fit given your skills and experience.\\nsp2-2 - Software Engineer: This is a broader term, but your CV demonstrates the technical skills needed for this role.\\nsp2-3 - Web Developer: If you want to highlight your front-end expertise, this is a good option.\"}, {\"type\":\"file\",\"file_id\":\"" + fileId + "\"}]";         }

    }

}
